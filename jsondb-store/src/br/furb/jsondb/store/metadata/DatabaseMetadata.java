package br.furb.jsondb.store.metadata;

import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseMetadata {

	private String name;
	private Map<String, TableMetadata> tables = new LinkedHashMap<String, TableMetadata>();
	private Map<String, ConstraintMetadata> constraints = new LinkedHashMap<String, ConstraintMetadata>();

	public void addTable(TableMetadata table) {
		tables.put(table.getName(), table);
	}

	public void addConstraint(ConstraintMetadata constraintMetadata) {
		constraints.put(constraintMetadata.getName(), constraintMetadata);
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

	public ConstraintMetadata getConstraint(String constraintName) {
		return constraints.get(constraintName);
	}

	public Map<String, ConstraintMetadata> getConstraints() {
		return constraints;
	}

	public String getName() {
		return name;
	}

	public void setName(String databaseName) {
		this.name = databaseName;
	}

	@Override
	public String toString() {
		return "DatabaseMetadata [name=" + name + ", tables=" + tables + ", constraints=" + constraints + "]";
	}

}
