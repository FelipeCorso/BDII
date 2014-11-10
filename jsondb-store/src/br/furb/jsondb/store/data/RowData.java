package br.furb.jsondb.store.data;

import java.util.LinkedHashMap;
import java.util.Map;

public class RowData {

	private int rowId;

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

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	@Override
	public String toString() {
		return "RowData [rowId=" + rowId + ", columns=" + columns + "]";
	}

}
