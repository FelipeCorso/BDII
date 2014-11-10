package br.furb.jsondb.store.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;

public class IndexData {

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
		MultiKey<String> multiKey = new MultiKey<String>(values);
		if (mapEntries.containsKey(multiKey)) {
			indexes = mapEntries.get(multiKey);
		} else {
			indexes = new ArrayList<Integer>();
			mapEntries.put(multiKey, indexes);
		}

		indexes.add(index);

	}

	public boolean containsEntry(String... values) {
		return mapEntries.containsKey(new MultiKey<String>(values));
	}

	public List<Integer> getEntry(String... values) {
		return mapEntries.get(new MultiKey<String>(values));
	}

	// public static void main(String[] args) {
	//
	// IndexData index = new IndexData();
	//
	// index.addColumn("NumEmp");
	// index.addColumn("TipCol");
	//
	// index.addEntry(1, "1", "1");
	// index.addEntry(2, "1", "2");
	// index.addEntry(3, "2", "3");
	//
	// StringWriter writer = new StringWriter();
	// JsonUtils.parseObjectToJson(writer, index, IndexData.class);
	// System.out.println(writer.toString());
	//
	// index = JsonUtils.parseJsonToObject(new StringReader(writer.toString()),
	// IndexData.class);
	//
	// for (Entry<MultiKey<? extends String>, List<Integer>> entry :
	// index.mapEntries.entrySet()) {
	// System.out.println(entry.getKey() + " = " + entry.getValue());
	//
	// }
	//
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void main(String[] args) {
		MultiKeyMap<String, List<Integer>> map = new MultiKeyMap<String, List<Integer>>();

		map.put("100", "200", Arrays.asList(1));
		map.put("200", "100", Arrays.asList(2));

		System.out.println(map.size());

		System.out.println(map.get("100", "200"));
		System.out.println(map.get("200", "100"));
	}
}
