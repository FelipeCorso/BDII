package jsondb.core.metadata;

import java.util.Map;
import java.util.Set;

public class TableMetadata {

	private String tableName;
	private Map<String, FieldMetadata> fields;
	private Set<String> primaryKey;

	// FIXME references ?

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

}
