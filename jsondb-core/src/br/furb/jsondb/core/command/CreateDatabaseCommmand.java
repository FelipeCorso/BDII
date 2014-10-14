package br.furb.jsondb.core.command;

import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.Result;
import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.StoreException;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;

public class CreateDatabaseCommmand implements ICommand {

	public CreateDatabaseCommmand(/* TODO receber o IStatement de create database */) {

	}

	@Override
	public IResult execute() {
		String database = ""; // TODO obter do IStatement

		IResult result = null;

		if (DatabaseMetadataProvider.getInstance().containsDatabase(database)) {
			result = new Result(true, String.format(
					"Database %s already exists", database));
		} else {

			boolean success = false;
			try {
				success = JsonDBStore.getInstance().createDatabase(database);

				if (success) {

					result = new Result(false, String.format(
							"Database %s created with success", database));
				} else {
					result = new Result(true,
							"Was not possible to create database " + database);
				}
				
			} catch (StoreException e) {
				result = new Result(true,
						"Was not possible to create database " + database,
						e.getMessage());
			}

		}

		return result;
	}

}
