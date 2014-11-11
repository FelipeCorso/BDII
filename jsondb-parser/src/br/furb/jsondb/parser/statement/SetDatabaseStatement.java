package br.furb.jsondb.parser.statement;

import java.util.Objects;

import br.furb.jsondb.parser.DatabaseIdentifier;

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

	@Override
	public String toString() {
		return "SET DATABASE ".concat(String.valueOf(getDatabase()));
	}
}
