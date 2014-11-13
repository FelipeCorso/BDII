package br.furb.jsondb.core.result;

import java.util.Map;
import java.util.Map.Entry;

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

		StringBuilder sb = new StringBuilder();

		for (Entry<String, Object> entry : columns.entrySet()) {
			sb.append(entry.getKey()).append(" = ").append(entry.getValue());
			sb.append("; ");
		}

		sb.append("");
		return sb.toString();
		//		return "ResultRow [columns=" + columns + "]";
	}

}
