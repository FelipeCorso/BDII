package br.furb.jsondb.store.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TableMetadata {

	private String name;
	private Map<String, ColumnMetadata> columns = new HashMap<String, ColumnMetadata>();
	private Set<String> primaryKey;
	private List< IndexMetadata> indexes;
	private int lastRowId = -1;

	public String getName() {
		return name;
	}

	public void setName(String tableName) {
		this.name = tableName;
	}

	public void addColumn(ColumnMetadata field) {
		this.columns.put(field.getName(), field);
	}

	public Map<String, ColumnMetadata> getColumns() {
		return columns;
	}

	public Set<String> getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Set<String> primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void addIndexMetadata(IndexMetadata indexMetadata) {
		this.indexes.add(indexMetadata);
	}

	public List<IndexMetadata> getIndexes() {
		return indexes;
	}

	@Override
	public String toString() {
		return "TableMetadata [name=" + name + ", fields=" + columns + ", primaryKey=" + primaryKey + "]";
	}

	public int getLastRowId() {
		return lastRowId;
	}

	public void setLastRowId(int lastRowId) {
		this.lastRowId = lastRowId;
	}
}
