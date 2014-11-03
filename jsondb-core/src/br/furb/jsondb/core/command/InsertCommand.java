package br.furb.jsondb.core.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.Result;
import br.furb.jsondb.core.util.JsonDBUtils;
import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.DataType;
import br.furb.jsondb.parser.InsertStatement;
import br.furb.jsondb.parser.NumberValue;
import br.furb.jsondb.parser.StringValue;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.Value;
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
	public IResult execute() {
		IResult result = JsonDBUtils.validateHasCurrentDatabase();

		if (result != null) {
			assert result.hasError();
			return result;
		}

		// verificar se a tabela existe
		TableIdentifier table = statement.getTable();

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(JsonDB.getInstance().getCurrentDatabase());

		if (!databaseMetadata.hasTable(table.getIdentifier())) {
			return new Result(true, String.format("Table %s not found", table.getIdentifier()));
		}

		// verificar se os campos existem

		List<ColumnIdentifier> columns = statement.getColumns();

		TableMetadata tableMetadata = databaseMetadata.getTable(table.getIdentifier());

		List<String> columnNames = new ArrayList<String>();

		for (ColumnIdentifier column : columns) {
			if (!tableMetadata.getColumns().containsKey(column.getColumnName())) {
				return new Result(true, String.format("Column %s not found on table %s", column.getColumnName(), table.getIdentifier()));
			}
			columnNames.add(column.getColumnName());
		}

		List<Value<?>> values = statement.getValues();

		Map<String, Value<?>> mapValues = new HashMap<String, Value<?>>();

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i).getColumnName();

			Value<?> value = values.get(i);
			mapValues.put(columnName, value);

			ColumnMetadata columnMetadata = tableMetadata.getColumns().get(columnName);

			// verificar se o valor corresponde ao tipo de dados da coluna
			Result resultDataTypeValidation = validateDataType(columnMetadata, value);
			if (resultDataTypeValidation != null) {
				return resultDataTypeValidation;
			}

			// verificar se o tamanho do valor corresponde aos limites da coluna

			Result resultSizeValidation = validateSizeAndPrecision(columnMetadata, value);
			if (resultSizeValidation != null) {
				return resultSizeValidation;
			}
		}

		// verificar se foi informado valores para os campos da pk e campos not null
		Set<String> primaryKey = tableMetadata.getPrimaryKey();
		List<String> notNullFields = new ArrayList<String>(primaryKey);
		notNullFields.addAll(TableMetadataUtils.getNotNullFields(tableMetadata));

		for (String field : notNullFields) {
			if (!columnNames.contains(field) || values.get(columnNames.indexOf(field)).getBaseValue() == Value.NULL) {
				return new Result(true, String.format("Column %s can't be null.", field));
			}
		}

		// já validou tudo, pode gravar no disco
		int rowId;
		try {
			rowId = JsonDBStore.getInstance().insertData(databaseMetadata.getName(), statement);
		} catch (StoreException e) {
			return new Result(true, "Was not possible to insert data", e.getCause().getMessage());
		}

		// atualiza os arquivos de índice

		List<IndexMetadata> indexes = tableMetadata.getIndexes();

		for (IndexMetadata index : indexes) {

			Collection<String> indexColumns = CollectionUtils.intersection(index.getColumns(), columnNames);

			if (!indexColumns.isEmpty()) {
				try {
					IndexData indexData = IndexDataProvider.getInstance(databaseMetadata.getName()).getIndexData(table.getIdentifier(), index.getName());

					List<String> valuesIndex = new ArrayList<String>();

					for (String col : index.getColumns()) {
						valuesIndex.add(mapValues.get(col).stringValue());
					}

					indexData.addEntry(rowId, valuesIndex.toArray(new String[valuesIndex.size()]));
					
					File databaseDir = JsonDBStore.getInstance().getDatabaseDir(databaseMetadata.getName());
					File tableDir = new File(databaseDir, table.getIdentifier());

					File indexDataFile = new File(tableDir, index + ".index");
					
					try {
						JsonUtils.write(indexData, IndexData.class, indexDataFile);
					} catch (IOException e) {
						return new Result(true, "Was not possible to add index entry", e.getCause().getMessage());
					}

				} catch (StoreException e) {
					return new Result(true, "Was not possible to read index", e.getCause().getMessage());
				}
			}

		}

		return new Result(false, "Insert performed with success.");

	}

	private Result validateSizeAndPrecision(ColumnMetadata columnMetadata, Value<?> value) {

		DataType type = columnMetadata.getType();

		String columnName = columnMetadata.getName();
		int columnLenght = columnMetadata.getLenght();

		switch (type) {
		case VARCHAR:

			if (columnLenght > 0) {

				String v = (String) value.getBaseValue();

				if (v.length() > columnLenght) {
					return invalidValueSizeResult(columnName, value);
				}

			}

			break;
		case CHAR:
			//TODO

			break;
		case DATE:
			//TODO

			break;
		case NUMBER:
			//TODO

			break;
		default:
			assert false;
			break;
		}
		return null;
	}

	private Result invalidValueResult(String columnName, Value<?> value) {
		return new Result(true, String.format("Valor '%s' inválido para a coluna %s", value.getBaseValue(), columnName));
	}

	private Result invalidValueSizeResult(String columnName, Value<?> value) {
		return new Result(true, String.format("Valor '%s' com tamanho inválido para a coluna %s", value.getBaseValue(), columnName));
	}

	private Result validateDataType(ColumnMetadata columnMetadata, Value<?> value) {
		DataType type = columnMetadata.getType();

		String columnName = columnMetadata.getName();

		switch (type) {
		case VARCHAR:

			if (!(value instanceof StringValue)) {
				return invalidValueResult(columnName, value);
			}

			break;
		case CHAR:
			//TODO

			break;
		case DATE:
			//TODO

			break;
		case NUMBER:
			if (!(value instanceof NumberValue)) {
				return invalidValueResult(columnName, value);
			}

			break;
		default:
			assert false;
			break;
		}
		return null;
	}
}
