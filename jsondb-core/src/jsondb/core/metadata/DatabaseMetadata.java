package jsondb.core.metadata;

import java.util.Map;

public class DatabaseMetadata {

	private String databaseName;
	private Map<String, TableMetadata> tables;

	public void addTable(TableMetadata table) {
		tables.put(table.getTableName(), table);
	}

	public TableMetadata getTable(String tableName){
		return tables.get(tableName);
	}
	
	public Map<String, TableMetadata> getTables() {
		return tables;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
}
