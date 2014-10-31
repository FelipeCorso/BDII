package br.furb.jsondb.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ForeignKeyDefinition extends KeyDefinition {

	private TableIdentifier targetTable;
	private final List<ColumnIdentifier> targetColumns;

	public ForeignKeyDefinition(String name) {
		this(name, null, null);
	}

	public ForeignKeyDefinition(String name, ColumnIdentifier sourceColum, ColumnIdentifier targetColumn) {
		super(name, ConstraintKind.FOREIGN_KEY, sourceColum);
		this.targetColumns = new LinkedList<>();

		if (targetColumn != null) {
			this.targetTable = targetColumn.getTable().get();
			this.targetColumns.add(targetColumn);
		}
	}

	public void addTargetColumn(ColumnIdentifier column) {
		this.targetColumns.add(Objects.requireNonNull(column, "cannot add null as a target column"));
	}

	public TableIdentifier getTargetTable() {
		return targetTable;
	}

	public void setTargetTable(TableIdentifier targetTable) {
		this.targetTable = Objects.requireNonNull(targetTable, "cannot set null as target table");
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		if (getName().isPresent()) {
			ret.append("CONSTRAINT '").append(getName().get()).append("' ");
		}
		if (isFinal()) {
			ret.append(getKind().toString()).append(' ');
			ret.append('(');
			columns.forEach(column -> ret.append("'").append(column.getColumnName()).append("', "));
			if (!columns.isEmpty()) {
				int length = ret.length();
				ret.delete(length - 2, length);
			}
			ret.append(") ");
		}

		ret.append("REFERENCES ").append(targetTable == null ? "«undefined»" : targetTable.getIdentifier());
		if (!this.targetColumns.isEmpty()) {
			ret.append('(');
			this.targetColumns.forEach(column -> ret.append("'").append(column.getColumnName()).append("', "));
			int length = ret.length();
			ret.delete(length - 2, length);
			ret.append(')');
		}
		return ret.toString();
	}

}
