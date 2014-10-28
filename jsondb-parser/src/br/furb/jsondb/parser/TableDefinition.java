package br.furb.jsondb.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TableDefinition implements IStructure {

	private TableIdentifier tableName;
	private final List<ColumnDefinition> columns;

	public TableDefinition(TableIdentifier tableName) {
		this.tableName = Objects.requireNonNull(tableName, "a table identifier must be provided for the table definition");
		this.columns = new LinkedList<>();
	}

	@Override
	public String getIdentifier() {
		return tableName.getIdentifier();
	}

	public final void addColumnDefinition(ColumnDefinition column) {
		this.columns.add(column);
	}

	public final TableIdentifier getTableIdentifier() {
		return tableName;
	}

}