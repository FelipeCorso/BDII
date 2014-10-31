package br.furb.jsondb.parser;

public class TableIdentifier implements IStructure {

	private String tableName;

	public TableIdentifier(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public final String getIdentifier() {
		return "'".concat(tableName).concat("'");
	}

}
