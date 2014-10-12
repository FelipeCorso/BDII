package br.furb.jsondb.parser;

public class SetDatabaseStatement implements IStatement {

	private TableIdentifier table;

	public SetDatabaseStatement(TableIdentifier table) {
		this.table = table;
	}

	public final TableIdentifier getTable() {
		return table;
	}

	public final void setTable(TableIdentifier table) {
		this.table = table;
	}

}
