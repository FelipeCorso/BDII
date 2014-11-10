package br.furb.jsondb.core.command;

import br.furb.jsondb.core.SQLException;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.Result;
import br.furb.jsondb.parser.CreateStatement;
import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.StoreException;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;

public class CreateDatabaseCommand implements ICommand {

	private CreateStatement createStatement;

	public CreateDatabaseCommand(CreateStatement createStatement) {
		this.createStatement = createStatement;

	}

	@Override
	public IResult execute() throws SQLException {
		String database = createStatement.getStructure().getIdentifier();

		IResult result = null;

		if (DatabaseMetadataProvider.getInstance().containsDatabase(database)) {
			throw new SQLException(String.format("Database %s already exists", database));
		}

		boolean success = false;
		try {
			success = JsonDBStore.getInstance().createDatabase(database);

			if (success) {

				result = new Result(String.format("Database %s created with success", database));
			} else {
				throw new SQLException("Was not possible to create database " + database);
			}

		} catch (StoreException e) {
			throw new SQLException("Was not possible to create database " + database, e);
		}

		return result;
	}

}
