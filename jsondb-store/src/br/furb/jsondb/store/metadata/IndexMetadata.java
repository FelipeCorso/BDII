package br.furb.jsondb.store.metadata;

import java.util.ArrayList;
import java.util.List;

public class IndexMetadata {

	private String name;
	private List<String> columns = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void addColumn(String column) {
		this.columns.add(column);
	}

}
