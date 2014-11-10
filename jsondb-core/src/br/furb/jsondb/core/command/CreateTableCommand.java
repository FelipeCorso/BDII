package br.furb.jsondb.core.command;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.SQLException;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.Result;
import br.furb.jsondb.core.util.JsonDBUtils;
import br.furb.jsondb.parser.CreateStatement;
import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.StoreException;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;

public class CreateTableCommand implements ICommand {

	private CreateStatement statement;

	public CreateTableCommand(CreateStatement statement) {
		this.statement = statement;
	}

	@Override
	public IResult execute() throws SQLException {
		IResult result = null;

		String database = JsonDB.getInstance().getCurrentDatabase();

		JsonDBUtils.validateHasCurrentDatabase();

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database);

		String tableName = statement.getStructure().getIdentifier();

		// valida se a tabela já existe
		if (databaseMetadata.getTable(tableName) != null) {
			throw new SQLException(String.format("Table %s already exists in database %s", tableName, database));
		}
		
		try {
			JsonDBStore.getInstance().createTable(database, statement);
			result = new Result(String.format("Table %s created with success", tableName));
		} catch (StoreException e) {
			throw new SQLException("Was not possible to create table.", e);
		}

		return result;
	}

}
