package br.furb.jsondb.parser;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class InsertStatement implements IStatement {

	private final List<TableColumn> columns;
	private final TableIdentifier table;
	private final List<Value> values;

	public InsertStatement(TableIdentifier table,
			Collection<TableColumn> columns, Collection<Value> values) {
		this.table = Objects.requireNonNull(table,
				"a table name must be provided");
		this.columns = new LinkedList<TableColumn>(columns);
		// TODO: non-empty
		this.values = new LinkedList<Value>(values);
	}

}
