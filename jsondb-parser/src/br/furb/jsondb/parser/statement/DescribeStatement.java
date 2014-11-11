package br.furb.jsondb.parser.statement;

import java.util.Objects;

import br.furb.jsondb.parser.TableIdentifier;

public class DescribeStatement implements IStatement {

	private TableIdentifier table;

	public DescribeStatement(TableIdentifier table) {
		this.table = Objects.requireNonNull(table, "a table must be provided to be described");
	}

	public TableIdentifier getTable() {
		return table;
	}

	@Override
	public String toString() {
		return "DESCRIBE ".concat(table.toString());
	}

}
