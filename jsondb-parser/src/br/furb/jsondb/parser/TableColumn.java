package br.furb.jsondb.parser;

import java.util.Objects;
import java.util.Optional;

public class TableColumn {

	public static final TableColumn ALL = new TableColumn("*");

	private Optional<TableIdentifier> maybeTable;
	private String columnName;

	public TableColumn(TableIdentifier table, String columnName) {
		this.maybeTable = Optional.ofNullable(table);
		this.columnName = Objects.requireNonNull(columnName, "é necessário informar o nome da coluna");
	}

	public TableColumn(String columnName) {
		this(null, columnName);
	}

	public Optional<TableIdentifier> getTable() {
		return maybeTable;
	}

	public final String getColumnName() {
		return columnName;
	}

	public String toPrettyString() {
		String qualifier = maybeTable.isPresent() ? maybeTable.get().getIdentifier() + "." : "";
		return qualifier + columnName;
	}

}
