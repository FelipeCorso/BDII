package br.furb.jsondb.core.command;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.ResultRow;
import br.furb.jsondb.core.result.ResultSet;
import br.furb.jsondb.core.util.JsonDBUtils;
import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.statement.SelectStatement;
import br.furb.jsondb.sql.SQLException;
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
		// validar se há um banco corrente
		JsonDBUtils.validateHasCurrentDatabase();

		String database = JsonDB.getInstance().getCurrentDatabase();
		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database);

		if (selectStatement.getTables().size() > 1) {
			throw new SQLException("Join is not supported yet");
		}

		// validar se as tabelas existem

		validateTables(databaseMetadata);

		// validar se os campos existem

		List<ColumnIdentifier> columns = selectStatement.getColumns();

		if (columns.size() == 1 && columns.get(0) == ColumnIdentifier.ALL) {
			columns = getAllColumns(selectStatement.getTables());
		} else if (!columns.isEmpty()) {
			validateColumns(databaseMetadata);
		}

		//TODO validar a clausula where

		List<ResultRow> resultRows = new ArrayList<ResultRow>();

		for (TableIdentifier tableIdentifier : selectStatement.getTables()) {

			TableData tableData = TableDataProvider.getInstance().getTableData(database, tableIdentifier.getIdentifier());

			//TODO se o where tiver campo que possui indice, buscar os registros direto do indice, não todos os registros da tabela
			//if (selectStatement.getWhereClause().isPresent()) {

			Map<Integer, RowData> rows = tableData.getRows();

			for (RowData rowData : rows.values()) {

				Map<String, Object> columnsValues = new LinkedHashMap<String, Object>();

				for (ColumnIdentifier columnIdentifier : columns) {

					ColumnData columnData = null;

					if (/**/!columnIdentifier.getTable().isPresent()
					/**/|| (columnIdentifier.getTable().isPresent() && columnIdentifier.getTable().get().getIdentifier().equals(tableIdentifier.getIdentifier())))
					/**/{
						columnData = rowData.getColumn(columnIdentifier.getColumnName());
						if (columnData != null) {

							boolean filterResult = true;

							if (selectStatement.getWhereClause().isPresent()) {

								//TODO filtro where

								//	filterResult = //resultado do where;
							}

							if (filterResult) {
								columnsValues.put(columnData.getName(), columnData.getValue());
							}

						}
					}

					//						}
				}

				ResultRow resultRow = new ResultRow(columnsValues);
				resultRows.add(resultRow);
			}

		}

		return new ResultSet(resultRows);
	}

	private static List<ColumnIdentifier> getAllColumns(List<TableIdentifier> tables) {

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(JsonDB.getInstance().getCurrentDatabase());

		List<ColumnIdentifier> columns = new ArrayList<ColumnIdentifier>();

		for (TableIdentifier tableIdentifier : tables) {
			TableMetadata tableMetadata = databaseMetadata.getTable(tableIdentifier.getIdentifier());

			for (String col : tableMetadata.getColumns().keySet()) {
				columns.add(new ColumnIdentifier(tableIdentifier, col));
			}

		}

		return columns;
	}

	private void validateColumns(DatabaseMetadata databaseMetadata) throws SQLException {
		List<ColumnIdentifier> columns = selectStatement.getColumns();

		for (ColumnIdentifier columnIdentifier : columns) {

			String columnName = columnIdentifier.getColumnName();
			Optional<TableIdentifier> table = columnIdentifier.getTable();

			if (!table.isPresent()) {

				if (selectStatement.getTables().size() > 1) {
					// verificar se o campo  é ambíguo

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

				if (!tableMetadata.getColumns().containsKey(columnName)) {
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

	@SuppressWarnings("unused")
	private static List<ResultRow> joinResults(Map<String, List<ResultRow>> mapResults) {

		/*
		 * Join não é suportado por enquanto
		 * Aqui está o inicio da implementação :( 
		 */

		List<ResultRow> joinedResults = new ArrayList<ResultRow>();

		int qtdRows = 0;

		for (List<ResultRow> resultRow : mapResults.values()) {
			qtdRows = qtdRows * resultRow.size();
		}

		for (int i = 0; i < qtdRows; i++) {

			Map<String, Object> columns = new LinkedHashMap<String, Object>();

			joinedResults.add(new ResultRow(columns));

		}

		for (List<ResultRow> resultRows : mapResults.values()) {
			for (ResultRow resultRow : resultRows) {

				for (int i = 0; i < qtdRows; i++) {
					joinedResults.get(i).getColumns().putAll(resultRow.getColumns());

				}
			}
		}

		return joinedResults;
	}
}
