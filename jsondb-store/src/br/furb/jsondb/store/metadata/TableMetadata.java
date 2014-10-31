package br.furb.jsondb.store.metadata;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TableMetadata {

	private String name;
	private Map<String, FieldMetadata> fields = new HashMap<String, FieldMetadata>();
	private Set<String> primaryKey;
	private IndexMetadata indexMetadata;

	public String getName() {
		return name;
	}

	public void setName(String tableName) {
		this.name = tableName;
	}

	public void addField(FieldMetadata field) {
		this.fields.put(field.getName(), field);
	}

	public Map<String, FieldMetadata> getFields() {
		return fields;
	}

	public Set<String> getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Set<String> primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setIndexMetadata(IndexMetadata indexMetadata) {
		this.indexMetadata = indexMetadata;
	}

	public IndexMetadata getIndexMetadata() {
		return indexMetadata;
	}

	@Override
	public String toString() {
		return "TableMetadata [name=" + name + ", fields=" + fields
				+ ", primaryKey=" + primaryKey + "]";
	}
}
