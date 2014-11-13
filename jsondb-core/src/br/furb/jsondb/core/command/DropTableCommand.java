package br.furb.jsondb.core.command;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.Result;
import br.furb.jsondb.core.util.JsonDBUtils;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.statement.DropStatement;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.StoreException;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;

public class DropTableCommand implements ICommand {

	private DropStatement<TableIdentifier> statement;

	public DropTableCommand(DropStatement<TableIdentifier> statement) {
		this.statement = statement;
	}

	@Override
	public IResult execute() throws SQLException {
		JsonDBUtils.validateHasCurrentDatabase();

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(JsonDB.getInstance().getCurrentDatabase());
		String tableName = statement.getStructure().getIdentifier();

		if (!databaseMetadata.hasTable(tableName)) {
			throw new SQLException(String.format("Table %s not found", tableName));
		}

		try {
			JsonDBStore.dropTable(JsonDB.getInstance().getCurrentDatabase(), tableName);
		} catch (StoreException e) {
			throw new SQLException("Was not possible to drop table");
		}

		return new Result("Table deleted with success");
	}

}
