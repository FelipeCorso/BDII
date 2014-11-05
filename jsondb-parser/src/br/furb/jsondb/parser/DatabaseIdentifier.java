package br.furb.jsondb.parser;

public class DatabaseIdentifier implements IStructure {

	private String databaseName;

	public DatabaseIdentifier(String databaseName) {
		if (databaseName == null || databaseName.isEmpty()) {
			throw new IllegalArgumentException("a database name is required");
		}
		this.databaseName = databaseName;
	}

	@Override
	public String getIdentifier() {
		return databaseName;
	}
	
	@Override
	public String toString() {
		return "'".concat(getIdentifier()).concat("'") ;
	}

}
