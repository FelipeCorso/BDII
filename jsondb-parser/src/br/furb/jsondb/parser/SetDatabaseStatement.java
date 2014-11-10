package br.furb.jsondb.parser;

import java.util.Objects;

public class SetDatabaseStatement implements IStatement {

	private DatabaseIdentifier database;

	public SetDatabaseStatement(DatabaseIdentifier database) {
		this.database = database;
	}

	public final DatabaseIdentifier getDatabase() {
		return database;
	}

	public final void setDatabase(DatabaseIdentifier database) {
		this.database = Objects.requireNonNull(database, "database cannot be null");
	}

}
