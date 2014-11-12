package br.furb.jsondb.core.command;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.Result;
import br.furb.jsondb.core.util.JsonDBUtils;
import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.Index;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.statement.CreateStatement;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.IndexMetadata;
import br.furb.jsondb.store.metadata.TableMetadata;

public class CreateIndexCommand implements ICommand {

	private CreateStatement createStatement;

	public CreateIndexCommand(CreateStatement createStatement) {
		this.createStatement = createStatement;
	}

	@Override
	public IResult execute() throws SQLException {
		JsonDBUtils.validateHasCurrentDatabase();

		String database = JsonDB.getInstance().getCurrentDatabase();
		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database);

		Index index = (Index) createStatement.getStructure();

		//validar se tabela e coluna existem
		ColumnIdentifier tableColumn = index.getTableColumn();
		TableIdentifier tableIdentifier = tableColumn.getTable().get();

		validateTable(databaseMetadata, tableIdentifier);

		TableMetadata table = databaseMetadata.getTable(tableIdentifier.getIdentifier());

		validateColumn(tableColumn, table);

		// validar se ainda não existe um índice com este nome:

		for (IndexMetadata indexMetadata : table.getIndexes()) {
			if (indexMetadata.getName().equals(index.getIdentifier())) {
				throw new SQLException(String.format("Duplicate index name '%s'", index.getIdentifier()));
			}
		}

		// Criar o índice no disco e gravar metadados

		JsonDBStore.getInstance().createIndex(database, index);

		return new Result();
	}

	private void validateColumn(ColumnIdentifier tableColumn, TableMetadata table) throws SQLException {
		if (!table.containsColumn(tableColumn.getColumnName())) {
			throw new SQLException(String.format("Column '%s' not found on table '%s'", tableColumn.getColumnName(), table.getName()));
		}
	}

	private void validateTable(DatabaseMetadata databaseMetadata, TableIdentifier tableIdentifier) throws SQLException {
		if (!databaseMetadata.hasTable(tableIdentifier.getIdentifier())) {
			throw new SQLException(String.format("Table '%s' not found", tableIdentifier.getIdentifier()));
		}
	}

}
