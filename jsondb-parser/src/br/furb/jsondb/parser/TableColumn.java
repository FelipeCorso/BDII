package br.furb.jsondb.parser;

public class TableColumn {

	private TableIdentifier table;
	private String columnName;

	public TableColumn(TableIdentifier table, String columnName) {
		if (columnName == null) {
			throw new IllegalArgumentException("é necessário informar o nome da coluna");
		}
		this.table = table;
		this.columnName = columnName;
	}

	public TableIdentifier getTable() {
		return table;
	}

	public final String getColumnName() {
		return columnName;
	}

	public String toPrettyString() {
		String result = table == null ? "" : table.getIdentifier() + ".";
		return result + columnName;
	}

}
