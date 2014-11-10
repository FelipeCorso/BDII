package br.furb.jsondb.core.command;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.SQLException;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.ResultRow;
import br.furb.jsondb.core.result.ResultSet;
import br.furb.jsondb.core.util.JsonDBUtils;
import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.statement.SelectStatement;
import br.furb.jsondb.store.StoreException;
import br.furb.jsondb.store.data.ColumnData;
import br.furb.jsondb.store.data.RowData;
import br.furb.jsondb.store.data.TableData;
import br.furb.jsondb.store.data.TableDataProvider;
import br.furb.jsondb.store.metadata.ColumnMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.TableMetadata;

public class SelectCommand implements ICommand {

	private SelectStatement selectStatement;

	public SelectCommand(SelectStatement selectStatement) {
		this.selectStatement = selectStatement;
	}

	@Override
	public IResult execute() throws SQLException {
		// validar se h� um banco corrente
		JsonDBUtils.validateHasCurrentDatabase();

		String database = JsonDB.getInstance().getCurrentDatabase();
		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database);

		// validar se as tabelas existem

		validateTables(databaseMetadata);

		// validar se os campos existem

		validateColumns(databaseMetadata);

		// validar a clausula where
		//TODO

		// Obter os dados

		List<ResultRow> resultRows = new ArrayList<ResultRow>();

		if (selectStatement.getWhereClause() == null) {

			List<ColumnIdentifier> columns = selectStatement.getColumns();

			for (TableIdentifier tableIdentifier : selectStatement.getTables()) {

				try {
					TableData tableData = TableDataProvider.getInstance().getTableData(database, tableIdentifier.getIdentifier());

					Map<Integer, RowData> rows = tableData.getRows();

					for (RowData rowData : rows.values()) {

						Map<String, Object> columnsValues = new LinkedHashMap<String, Object>();
						if (columns.isEmpty()) {
							//seleciona todas as colunas
							rowData.getColumns().values().forEach(columnData -> columnsValues.put(columnData.getName(), columnData.getValue()));

						} else {

							for (ColumnIdentifier columnIdentifier : columns) {

								ColumnData columnData = null;

								if (/**/!columnIdentifier.getTable().isPresent()
								/**/|| (columnIdentifier.getTable().isPresent() && columnIdentifier.getTable().get().equals(tableIdentifier.getIdentifier())))
								/**/{
									columnData = rowData.getColumn(columnIdentifier.getColumnName());
									if (columnData != null) {
										columnsValues.put(columnData.getName(), columnData.getValue());
									}
								}

							}
						}

						ResultRow resultRow = new ResultRow(columnsValues);
						resultRows.add(resultRow);
					}

				} catch (StoreException e) {
					e.printStackTrace();
					throw new SQLException(e.getMessage(), e);
				}
			}

		}

		return new ResultSet(resultRows);
	}

	private void validateColumns(DatabaseMetadata databaseMetadata) throws SQLException {
		List<ColumnIdentifier> columns = selectStatement.getColumns();

		for (ColumnIdentifier columnIdentifier : columns) {

			String columnName = columnIdentifier.getColumnName();
			Optional<TableIdentifier> table = columnIdentifier.getTable();

			if (!table.isPresent()) {

				if (selectStatement.getTables().size() > 1) {
					// verificar se o campo � amb�guo

					boolean containsField = false;

					for (TableIdentifier t : selectStatement.getTables()) {
						Map<String, ColumnMetadata> columnsT = databaseMetadata.getTable(t.getIdentifier()).getColumns();

						if (columnsT.containsKey(columnName)) {

							if (containsField) {
								throw new SQLException(String.format("Column '%s' in field list is ambiguous", columnName));
							}

							containsField = true;
						}
					}

				} else {
					// verificar se o campo existe na tabela
					String tableName = selectStatement.getTables().get(0).getIdentifier();
					TableMetadata tableMetadata = databaseMetadata.getTable(tableName);

					if (!tableMetadata.getColumns().containsKey(columnName)) {
						throw new SQLException(String.format("Unknown column '%s' in 'field list'", columnName));
					}
				}

			} else {

				// verificar se o campo existe na tabela

				TableIdentifier tableIdentifier = table.get();

				TableMetadata tableMetadata = databaseMetadata.getTable(tableIdentifier.getIdentifier());

				if (tableMetadata.getColumns().containsKey(columnName)) {
					throw new SQLException(String.format("Unknown column '%s.%s' in 'field list'", tableIdentifier.getIdentifier(), columnName));
				}
			}
		}

	}

	private void validateTables(DatabaseMetadata databaseMetadata) throws SQLException {
		List<TableIdentifier> tables = selectStatement.getTables();

		for (TableIdentifier tableIdentifier : tables) {
			if (!databaseMetadata.hasTable(tableIdentifier.getIdentifier())) {
				throw new SQLException(String.format("Table '%s.%s' doesn't exist", databaseMetadata.getName(), tableIdentifier.getIdentifier()));
			}
		}
	}

}
