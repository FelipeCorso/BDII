package br.furb.jsondb.core.command;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.Result;
import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.StoreException;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;

public class CreateTableCommand implements ICommand {

	public CreateTableCommand(/* TODO receber IStatement */) {

	}

	@Override
	public IResult execute() {
		IResult result = null;

		String database = JsonDB.getInstance().getCurrentDatabase();

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider
				.getInstance().getDatabaseMetadata(database);

		String tableName = ""; // TODO obter do IStatement

		// valida se a tabela já existe
		if (databaseMetadata.getTable(tableName) != null) {
			result = new Result(true, String.format(
					"Table %s already exists in database %s", tableName,
					database));
		} else {
			// FIXME considerar que os campos já estão ok???

			try {
				JsonDBStore.getInstance().createTable();
				result = new Result(false, String.format( "Table %s created with success", tableName));
			} catch (StoreException e) {
				result = new Result(true, e.getMessage());
			}

		}

		return result;
	}

}
