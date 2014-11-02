package br.furb.jsondb.core.command;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.Result;
import br.furb.jsondb.core.util.JsonDBUtils;
import br.furb.jsondb.parser.DropStatement;
import br.furb.jsondb.parser.TableIdentifier;
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
	public IResult execute() {
		IResult result = JsonDBUtils.validateHasCurrentDatabase();

		if (result != null) {
			return result;
		}

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(JsonDB.getInstance().getCurrentDatabase());
		String tableName = statement.getStructure().getIdentifier();

		if (!databaseMetadata.hasTable(tableName)) {
			result = new Result(true, String.format("Table %s not found", tableName));
		} else {
			try {
				JsonDBStore.getInstance().dropTable(JsonDB.getInstance().getCurrentDatabase(), tableName);
			} catch (StoreException e) {
				result = new Result(true, "Was not possible to drop table", e.getMessage());
			}
		}

		return result;
	}

}
