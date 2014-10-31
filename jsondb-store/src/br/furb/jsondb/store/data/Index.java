package br.furb.jsondb.store.data;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;

import br.furb.jsondb.store.utils.JsonUtils;

public class Index {

	private String name;

	private List<String> columns = new ArrayList<String>();

	private MultiKeyMap<String, List<Integer>> mapEntries = new MultiKeyMap<String, List<Integer>>();

	public List<String> getColumns() {
		return columns;
	}

	public void addColumn(String column) {
		this.columns.add(column);
	}

	public void addEntry(int index, String... values) {
		List<Integer> indexes = null;
		if (mapEntries.containsKey(values)) {
			indexes = mapEntries.get(values);
		} else {
			indexes = new ArrayList<Integer>();
			mapEntries.put(new MultiKey<String>(values), indexes);
		}

		indexes.add(index);

	}

	public static void main(String[] args) {

		Index index = new Index();

		index.addColumn("NumEmp");
		index.addColumn("TipCol");

		index.addEntry(1, "1", "1");
		index.addEntry(2, "1", "2");
		index.addEntry(3, "2", "3");

		StringWriter writer = new StringWriter();
		JsonUtils.parseObjectToJson(writer, index, Index.class);
		System.out.println(writer.toString());

		index = JsonUtils.parseJsonToObject(
				new StringReader(writer.toString()), Index.class);

		for (Entry<MultiKey<? extends String>, List<Integer>> entry : index.mapEntries
				.entrySet()) {
			System.out.println(entry.getKey() + " = " + entry.getValue());

		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
