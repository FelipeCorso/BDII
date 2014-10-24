package br.furb.jsondb.core.result;

import java.util.List;

import br.furb.jsondb.store.RowData;

public class ResultSet extends Result implements IResultSet {

	private List<RowData> rows;

	public ResultSet(List<RowData> rows, String... messages) {
		super(false, messages);
		this.rows = rows;
	}

	@Override
	public List<RowData> getRows() {
		return rows;
	}
}
