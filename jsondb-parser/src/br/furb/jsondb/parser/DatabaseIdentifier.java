package br.furb.jsondb.parser;

public class DatabaseIdentifier implements IStructure {

	private String databaseName;

	public DatabaseIdentifier(String databaseName) {
		if (databaseName == null || databaseName.isEmpty()) {
			throw new IllegalArgumentException("é necessário informar o nome da base de dados");
		}
		this.databaseName = databaseName;
	}

	@Override
	public String getIdentifier() {
		return "'".concat(databaseName).concat("'");
	}
	
	@Override
	public String toString() {
		return getIdentifier();
	}

}
