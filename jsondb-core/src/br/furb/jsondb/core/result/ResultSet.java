package br.furb.jsondb.core.result;

import java.util.List;

public class ResultSet extends Result implements IResultSet {

	private List<ResultRow> rows;

	public ResultSet(List<ResultRow> rows, String... messages) {
		super(messages);
		this.rows = rows;
	}

	@Override
	public List<ResultRow> getRows() {
		return rows;
	}
}