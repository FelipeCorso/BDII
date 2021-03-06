package br.furb.jsondb.core.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.Result;
import br.furb.jsondb.core.util.JsonDBUtils;
import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.DataType;
import br.furb.jsondb.parser.Date;
import br.furb.jsondb.parser.NumberValue;
import br.furb.jsondb.parser.StringValue;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.Value;
import br.furb.jsondb.parser.statement.InsertStatement;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.StoreException;
import br.furb.jsondb.store.data.IndexData;
import br.furb.jsondb.store.data.IndexDataProvider;
import br.furb.jsondb.store.metadata.ColumnMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.IndexMetadata;
import br.furb.jsondb.store.metadata.TableMetadata;
import br.furb.jsondb.store.utils.JsonUtils;
import br.furb.jsondb.store.utils.TableMetadataUtils;

public class InsertCommand implements ICommand {

	private InsertStatement statement;

	public InsertCommand(InsertStatement statement) {
		this.statement = statement;
	}

	@Override
	public IResult execute() throws SQLException {
		JsonDBUtils.validateHasCurrentDatabase();

		// verificar se a tabela existe
		TableIdentifier table = statement.getTable();

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(JsonDB.getInstance().getCurrentDatabase());

		if (!databaseMetadata.hasTable(table.getIdentifier())) {
			throw new SQLException(String.format("Table '%s' not found", table.getIdentifier()));
		}

		// verificar se os campos existem

		TableMetadata tableMetadata = databaseMetadata.getTable(table.getIdentifier());

		List<String> columns = validateColumns(table, tableMetadata);

		if (columns.isEmpty()) {
			// adiciona todas as colunas da tabela
			columns.addAll(tableMetadata.getColumns().keySet());
		}

		if (columns.size() != statement.getValues().size()) {
			throw new SQLException("Column count does not match");
		}

		List<Value<?>> values = statement.getValues();

		Map<String, Value<?>> mapValues = new LinkedHashMap<String, Value<?>>();

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);

			Value<?> value = values.get(i);
			mapValues.put(columnName, value);

			ColumnMetadata columnMetadata = tableMetadata.getColumn(columnName);

			if (value != Value.NULL) {
				// verificar se o valor corresponde ao tipo de dados da coluna
				validateDataType(columnMetadata, value);

				// verificar se o tamanho do valor corresponde aos limites da coluna
				validateSizeAndPrecision(columnMetadata, value);
			}

		}

		// verificar se foi informado valores para os campos da pk e campos not
		// null
		List<String> primaryKey = tableMetadata.getPrimaryKey();
		List<String> notNullFields = new ArrayList<String>(primaryKey);
		notNullFields.addAll(TableMetadataUtils.getNotNullFields(tableMetadata, databaseMetadata));

		for (String field : notNullFields) {
			if (!columns.contains(field) || values.get(columns.indexOf(field)).getBaseValue() == Value.NULL) {
				throw new SQLException(String.format("Column '%s' can't be null.", field));
			}
		}

		// verificar se ainda não existe registro com a chave informada

		if (!tableMetadata.getPrimaryKey().isEmpty()) {

			try {
				IndexData indexData = IndexDataProvider.getInstance(databaseMetadata.getName()).getIndexData(tableMetadata.getName(), "PRIMARY");

				String[] keyValues = new String[primaryKey.size()];

				for (int i = 0; i < keyValues.length; i++) {
					keyValues[i] = mapValues.get(primaryKey.get(i)).getBaseValue().toString();
				}

				if (indexData.containsEntry(keyValues)) {
					throw new SQLException("Primary key violation");
				}

			} catch (StoreException e) {
				throw new SQLException(e.getMessage(), e);
			}
		}

		// já validou tudo, pode gravar no disco
		int rowId;
		try {
			rowId = JsonDBStore.insertData(databaseMetadata.getName(), table.getIdentifier(), mapValues);
		} catch (StoreException e) {
			e.printStackTrace();
			throw new SQLException("Was not possible to insert data", e);
		}

		// atualiza os arquivos de �ndice

		try {
			updateIndexes(tableMetadata, mapValues, rowId);
		} catch (StoreException | IOException e) {
			e.printStackTrace();
			throw new SQLException("Was not possible to add index entry", e);
		}

		return new Result("Insert performed with success.");
	}

	private List<String> validateColumns(TableIdentifier table, TableMetadata tableMetadata) throws SQLException {
		List<String> columns = new ArrayList<String>();

		for (ColumnIdentifier column : statement.getColumns()) {
			if (!tableMetadata.containsColumn(column.getColumnName())) {
				throw new SQLException(String.format("Column '%s' not found on table '%s'", column.getColumnName(), table.getIdentifier()));
			}
			columns.add(column.getColumnName());
		}
		return columns;
	}

	private static void updateIndexes(TableMetadata tableMetadata, Map<String, Value<?>> mapValues, int rowId) throws StoreException, IOException {
		List<IndexMetadata> indexes = tableMetadata.getIndexes();

		String database = JsonDB.getInstance().getCurrentDatabase();

		for (IndexMetadata index : indexes) {

			// pega as colunas que fazem parte deste indice.
			Collection<String> indexColumns = CollectionUtils.intersection(index.getColumns(), mapValues.keySet());

			if (!indexColumns.isEmpty()) {
				IndexData indexData = IndexDataProvider.getInstance(database).getIndexData(tableMetadata.getName(), index.getName());

				List<String> valuesIndex = new ArrayList<String>();

				for (String col : index.getColumns()) {
					valuesIndex.add(mapValues.get(col).stringValue());
				}

				indexData.addEntry(rowId, valuesIndex.toArray(new String[valuesIndex.size()]));

				File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);
				File tableDir = new File(databaseDir, tableMetadata.getName());

				File indexDataFile = new File(tableDir, index.getName() + ".index");

				JsonUtils.write(indexData, IndexData.class, indexDataFile);

			}

		}
	}

	private static void validateSizeAndPrecision(ColumnMetadata columnMetadata, Value<?> value) throws SQLException {

		DataType type = columnMetadata.getType();

		String columnName = columnMetadata.getName();
		int columnLength = columnMetadata.getLength();

		boolean hasError = false;

		switch (type) {
		case VARCHAR:

			if (columnLength > 0) {
				String v = (String) value.getBaseValue();
				if (v.length() > columnLength) {
					hasError = true;
				}
			}

			break;
		case CHAR:
			String v = (String) value.getBaseValue();
			if (v.length() > 1) {
				hasError = true;
			}

			break;
		case DATE:
			// TODO precisa validar???

			break;
		case NUMBER:
			NumberValue numberValue = (NumberValue) value;
			Double baseValue = numberValue.getBaseValue();

			int[] valueLength = getValueLength(baseValue);

			if (columnLength > 0 && valueLength[0] > columnLength) {
				hasError = true;
			} else {
				int precision = columnMetadata.getPrecision();

				if (valueLength[1] > precision) {
					hasError = true;
				}
			}

			break;
		default:
			assert false;
			break;
		}

		if (hasError) {
			throw new SQLException(String.format("Invalid value length. Value: '%s'. Column: '%s'", value.stringValue(), columnName));
		}

	}

	private static void validateDataType(ColumnMetadata columnMetadata, Value<?> value) throws SQLException {
		DataType type = columnMetadata.getType();

		String columnName = columnMetadata.getName();

		boolean hasError = false;

		switch (type) {
		case VARCHAR:
		case CHAR:

			if (!(value instanceof StringValue)) {
				hasError = true;
			}

			break;
		case DATE:
			if (!(value instanceof Date)) {
				hasError = true;
			}

			break;
		case NUMBER:
			if (!(value instanceof NumberValue)) {
				hasError = true;
			}
			break;
		default:
			assert false;
			break;
		}

		if (hasError) {
			throw new SQLException(String.format("Invalid value type. Value: '%s'. Column: '%s'", value.stringValue(), columnName));
		}
	}

	/**
	 * Retorna os tamanhos do valor passado (Inteiro e Decimal)<br>
	 */
	private static int[] getValueLength(double value) {
		if (value < 0) {
			value = -value;
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.####################");
		char decimalSeparator = df.getDecimalFormatSymbols().getDecimalSeparator();

		String formated = df.format(value);

		int separatorIndex = formated.indexOf(decimalSeparator);
		if (separatorIndex < 0) {
			return new int[] { formated.length(), 0 };
		}

		return new int[] { separatorIndex, formated.length() - separatorIndex - 1 };
	}

}
