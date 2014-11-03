package br.furb.jsondb.parser;

public class TableIdentifier implements IStructure {

	private String tableName;

	public TableIdentifier(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public final String getIdentifier() {
		return tableName;
	}
	
	@Override
	public String toString() {
		return "'".concat(getIdentifier()).concat("'");
	}

}
