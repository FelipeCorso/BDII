package br.furb.jsondb.core.command;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.Result;
import br.furb.jsondb.core.util.JsonDBUtils;
import br.furb.jsondb.parser.Index;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.statement.DropStatement;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.IndexMetadata;
import br.furb.jsondb.store.metadata.TableMetadata;

public class DropIndexCommand implements ICommand {

	private DropStatement<Index> dropStatement;

	public DropIndexCommand(DropStatement<Index> dropStatement) {
		this.dropStatement = dropStatement;
	}

	@Override
	public IResult execute() throws SQLException {
		JsonDBUtils.validateHasCurrentDatabase();

		Index index = dropStatement.getStructure();

		String database = JsonDB.getInstance().getCurrentDatabase();
		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database);

		//validar se a tabela existe

		TableIdentifier tableIdentifier = index.getTableColumn().getTable().get();
		validateTable(databaseMetadata, tableIdentifier);

		TableMetadata table = databaseMetadata.getTable(tableIdentifier.getIdentifier());

		// validar se o índice existe:

		boolean found = false;
		for (IndexMetadata indexMetadata : table.getIndexes()) {
			if (indexMetadata.getName().equals(index.getIdentifier())) {
				found = true;
				break;
			}
		}

		if (!found) {
			throw new SQLException(String.format("Index '%s' not found on table '%s'", index.getIdentifier(), table.getName()));
		}

		// apagar o índice

		JsonDBStore.getInstance().dropIndex(database, index);

		return new Result();
	}

	private void validateTable(DatabaseMetadata databaseMetadata, TableIdentifier tableIdentifier) throws SQLException {
		if (!databaseMetadata.hasTable(tableIdentifier.getIdentifier())) {
			throw new SQLException(String.format("Table '%s' not found", tableIdentifier.getIdentifier()));
		}
	}

}
