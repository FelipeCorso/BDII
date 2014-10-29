package br.furb.jsondb.store.metadata;

import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseMetadata {

	private String name;
	private Map<String, TableMetadata> tables = new LinkedHashMap<String, TableMetadata>();

	public void addTable(TableMetadata table) {
		tables.put(table.getName(), table);
	}

	public void removeTable(String tableName) {
		tables.remove(tableName);
	}

	public boolean hasTable(String tableName) {
		return tables.containsKey(tableName);
	}

	public TableMetadata getTable(String tableName) {
		return tables.get(tableName);
	}

	public Map<String, TableMetadata> getTables() {
		return tables;
	}

	public String getName() {
		return name;
	}

	public void setName(String databaseName) {
		this.name = databaseName;
	}

	@Override
	public String toString() {
		return "DatabaseMetadata [name=" + name + ", tables=" + tables + "]";
	}

}
