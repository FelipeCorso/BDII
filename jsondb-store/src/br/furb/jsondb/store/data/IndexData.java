package br.furb.jsondb.store.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexData {

	private String name;

	private List<String> columns = new ArrayList<String>();

	private Map<List<String>, List<Integer>> mapEntries = new HashMap<List<String>, List<Integer>>();

	public List<String> getColumns() {
		return columns;
	}

	public void addColumn(String column) {
		this.columns.add(column);
	}

	public void addEntry(int index, String... values) {
		List<Integer> indexes = null;
		List<String> multiKey = Arrays.asList(values);
		if (mapEntries.containsKey(multiKey)) {
			indexes = mapEntries.get(multiKey);
		} else {
			indexes = new ArrayList<Integer>();
			mapEntries.put(multiKey, indexes);
		}
		indexes.add(index);
	}

	public boolean containsEntry(String... values) {
		return mapEntries.containsKey(Arrays.asList(values));
	}

	public List<Integer> getEntry(String... values) {
		return mapEntries.get(Arrays.asList(values));
	}

	public Map<List<String>, List<Integer>> getMapEntries() {
		return mapEntries;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
