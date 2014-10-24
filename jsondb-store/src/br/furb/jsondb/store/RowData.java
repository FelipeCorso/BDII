package br.furb.jsondb.store;

import java.util.LinkedHashMap;
import java.util.Map;

public class RowData {

	private Map<String, ColumnData> columns = new LinkedHashMap<String, ColumnData>();

	public void addColumn(ColumnData columnData) {
		this.columns.put(columnData.getName(), columnData);
	}

	public Map<String, ColumnData> getColumns() {
		return columns;
	}

	public ColumnData getColumn(String columnName) {
		return columns.get(columnName);
	}

}
