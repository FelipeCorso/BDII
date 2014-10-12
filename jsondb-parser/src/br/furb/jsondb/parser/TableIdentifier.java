package br.furb.jsondb.parser;

public class TableIdentifier {

	private String tableName;

	public TableIdentifier(String tableName) {
		this.tableName = tableName;
	}

	public final String getTableName() {
		return tableName;
	}

}
