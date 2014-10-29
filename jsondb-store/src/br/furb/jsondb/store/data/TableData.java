package br.furb.jsondb.store.data;

import java.util.ArrayList;
import java.util.List;

public class TableData {

	private List<RowData> rows = new ArrayList<RowData>();

	public List<RowData> getRows() {
		return rows;
	}
	
	public RowData getRow(int index) {
		return rows.get(index);
	}
	
	public void addRow(RowData row){
		this.rows.add(row);
	}
}
