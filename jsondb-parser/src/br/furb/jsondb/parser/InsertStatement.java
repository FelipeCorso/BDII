package br.furb.jsondb.parser;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class InsertStatement implements IStatement {

	private final List<ColumnIdentifier> columns;
	private final TableIdentifier table;
	private final List<Value<?>> values;

	public InsertStatement(TableIdentifier table,
			Collection<ColumnIdentifier> columns, Collection<Value<?>> values) {
		this.table = table;
		this.columns = new LinkedList<>();
		this.values = new LinkedList<>();

		if (columns != null && !columns.isEmpty()) {
			this.columns.addAll(columns);
		}
		if (values != null && !values.isEmpty()) {
			this.values.addAll(values);
		}
	}

	public InsertStatement() {
		this(null, null, null);
	}

	public void addValues(List<Value<?>> valuesStack) {
		valuesStack.forEach(value -> this.values.add(Objects.requireNonNull(
				value, "null values must be specified as Value.NULL")));
	}

	public List<ColumnIdentifier> getColumns() {
		return columns;
	}

	public TableIdentifier getTable() {
		return table;
	}

	public List<Value<?>> getValues() {
		return values;
	}
}
