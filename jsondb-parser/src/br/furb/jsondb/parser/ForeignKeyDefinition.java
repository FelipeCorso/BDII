package br.furb.jsondb.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ForeignKeyDefinition extends KeyDefinition {

	private TableIdentifier targetTable;
	private List<ColumnIdentifier> targetColumns;

	public ForeignKeyDefinition(String name, ConstraintKind kind, ColumnIdentifier sourceColum, ColumnIdentifier targetColumn) {
		super(name, kind, sourceColum);

		targetColumn = Objects.requireNonNull(targetColumn, "a target column must be provided for the foreign key definition");
		if (!targetColumn.getTable().isPresent()) {
			throw new NullPointerException("a target table must be provided for the foreign key definition");
		}
		this.targetTable = targetColumn.getTable().get();

		this.targetColumns = new LinkedList<>();
		this.targetColumns.add(targetColumn);
	}

}
