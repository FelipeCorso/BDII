package br.furb.jsondb.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ForeignKeyDefinition extends KeyDefinition {

	private TableIdentifier targetTable;
	private List<ColumnIdentifier> targetColumns;

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

}
