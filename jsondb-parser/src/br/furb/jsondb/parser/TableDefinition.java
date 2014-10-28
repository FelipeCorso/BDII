package br.furb.jsondb.parser;

import java.util.Objects;

public class TableDefinition implements IStructure {

	private TableIdentifier tableName;
	private ColumnDefinition columnDefinition;

	public TableDefinition(TableIdentifier tableName) {
		this.tableName = Objects.requireNonNull(tableName, "a table identifier must be provided for the table definition");
	}

	@Override
	public String getIdentifier() {
		return tableName.getIdentifier();
	}

	public final ColumnDefinition getColumnDefinition() {
		return columnDefinition;
	}

	public final void setColumnDefinition(ColumnDefinition columnDefinition) {
		this.columnDefinition = columnDefinition;
	}

	public final TableIdentifier getTableName() {
		return tableName;
	}

}