package br.furb.jsondb.parser;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class InsertStatement implements IStatement {

	private final List<ColumnIdentifier> columns;
	private final TableIdentifier table;
	private final List<Value> values;

	public InsertStatement(TableIdentifier table,
			Collection<ColumnIdentifier> columns, Collection<Value> values) {
		this.table = Objects.requireNonNull(table,
				"a table name must be provided");
		this.columns = new LinkedList<ColumnIdentifier>(columns);
		// TODO: non-empty
		this.values = new LinkedList<Value>(values);
	}

}
