package br.furb.jsondb.core.command;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.SQLException;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.Result;
import br.furb.jsondb.parser.SetDatabaseStatement;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;

public class SetDatabaseCommand implements ICommand {

	private SetDatabaseStatement statement;

	public SetDatabaseCommand(SetDatabaseStatement statement) {
		this.statement = statement;
	}

	@Override
	public IResult execute() throws SQLException {

		IResult result = null;

		String database = statement.getDatabase().getIdentifier();
		if (!DatabaseMetadataProvider.getInstance().containsDatabase(database)) {
			throw new  SQLException( String.format("Database %s not found", database));
		} else {

			JsonDB.getInstance().setCurrentDatabase(database);

			result = new Result( "Current database is " + database);
		}

		return result;
	}

}
