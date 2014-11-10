package br.furb.jsondb.core.result;

import java.util.Map;

public class ResultRow {

	private Map<String, Object> columns;

	public ResultRow(Map<String, Object> columns) {
		this.columns = columns;
	}

	public Map<String, Object> getColumns() {
		return columns;
	}

	public Object getColumnValue(String column) {
		return columns.get(column);
	}

	@Override
	public String toString() {
		return "ResultRow [columns=" + columns + "]";
	}

}
