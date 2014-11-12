package br.furb.jsondb.store.metadata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableMetadata {

	private String name;
	private Map<String, ColumnMetadata> columns = new LinkedHashMap<String, ColumnMetadata>();
	private List<String> primaryKey;
	private List<IndexMetadata> indexes = new ArrayList<IndexMetadata>();

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

	public ColumnMetadata getColumn(String columnName) {
		return columns.get(columnName);
	}

	public boolean containsColumn(String columnName) {
		return columns.containsKey(columnName);
	}

	public List<String> getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(List<String> primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void addIndexMetadata(IndexMetadata indexMetadata) {
		this.indexes.add(indexMetadata);
	}

	public void removeIndex(String index) {

		Iterator<IndexMetadata> iterator = indexes.iterator();

		while (iterator.hasNext()) {
			IndexMetadata indexMetadata = iterator.next();
			if (indexMetadata.getName().equals(index)) {
				iterator.remove();
				break;
			}
		}

	}

	public List<IndexMetadata> getIndexes() {
		return indexes;
	}

	@Override
	public String toString() {
		return "TableMetadata [name=" + name + ", fields=" + columns + ", primaryKey=" + primaryKey + "]";
	}
}
