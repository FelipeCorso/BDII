package br.furb.jsondb.parser;

import java.util.Optional;

import br.furb.jsondb.utils.ArgumentValidator;

public class ColumnIdentifier {

	public static final ColumnIdentifier ALL = new ColumnIdentifier("*") {

		@Override
		public String toString() {
			return getColumnName();
		}

	};

	private Optional<TableIdentifier> maybeTable;
	private String columnName;

	public ColumnIdentifier(TableIdentifier table, String columnName) {
		this.maybeTable = Optional.ofNullable(table);
		this.columnName = ArgumentValidator.requireNonEmpty(columnName, "a column name must be provided as identifier");
	}

	public ColumnIdentifier(String columnName) {
		this(null, columnName);
	}

	public Optional<TableIdentifier> getTable() {
		return maybeTable;
	}

	public final String getColumnName() {
		return columnName;
	}

	@Override
	public String toString() {
		String qualifier = maybeTable.isPresent() ? maybeTable.get().toString() + "." : "";
		return qualifier + "'".concat(columnName).concat("'");
	}

}
