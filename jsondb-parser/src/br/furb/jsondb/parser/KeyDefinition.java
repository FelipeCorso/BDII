package br.furb.jsondb.parser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class KeyDefinition extends ConstraintDefinition {

	private List<ColumnIdentifier> columns;

	public KeyDefinition(String name, ConstraintKind kind) {
		this(name, kind, (List<ColumnIdentifier>) null);
	}

	public KeyDefinition(String name, ConstraintKind kind, ColumnIdentifier column) {
		this(name, kind, Arrays.asList(column));
	}

	public KeyDefinition(String name, ConstraintKind kind, List<ColumnIdentifier> column) {
		super(name, kind);
		this.columns = new LinkedList<ColumnIdentifier>();
		if (column != null && !column.isEmpty()) {
			this.columns.addAll(column);
		}
	}

	public void addColumn(ColumnIdentifier column) {
		this.columns.add(Objects.requireNonNull(column, "cannot add null as a column"));
	}

}
