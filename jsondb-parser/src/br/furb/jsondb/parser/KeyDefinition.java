package br.furb.jsondb.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class KeyDefinition extends ConstraintDefinition {

	protected final List<ColumnIdentifier> columns;
	private boolean isFinal;

	public KeyDefinition(String name, ConstraintKind kind) {
		this(name, kind, (List<ColumnIdentifier>) null);
	}

	public KeyDefinition(String name, ConstraintKind kind, ColumnIdentifier column) {
		this(name, kind, (List<ColumnIdentifier>) null);
		if (column != null) {
			addColumn(column);
		}
	}

	public KeyDefinition(String name, ConstraintKind kind, List<ColumnIdentifier> columns) {
		super(name, kind);
		this.columns = new LinkedList<ColumnIdentifier>();
		if (columns != null && !columns.isEmpty()) {
			this.columns.addAll(columns);
		}
	}

	public void addColumn(ColumnIdentifier column) {
		this.columns.add(Objects.requireNonNull(column, "cannot add null as a column"));
	}

	public List<ColumnIdentifier> getColumns() {
		return columns;
	}

	public final boolean isFinal() {
		return isFinal;
	}

	public final void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder(super.toString());
		if (!columns.isEmpty()) {
			ret.append(" (");
			columns.forEach(column -> ret.append("'").append(column.getColumnName()).append("', "));
			int length = ret.length();
			ret.delete(length - 2, length);
			ret.append(")");
		}
		return ret.toString();
	}

}
