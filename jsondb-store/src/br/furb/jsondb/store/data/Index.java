package br.furb.jsondb.store.data;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import br.furb.jsondb.store.utils.JsonUtils;

public class Index {
	
	private String field;
	
	private Map<String, Integer> mapEntries = new HashMap<String, Integer>();

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Map<String, Integer> getMapEntries() {
		return mapEntries;
	}

	public void addEntry(String value, Integer index){
		mapEntries.put(value, index);
	}
	
	public static void main(String[] args) {
		
		Index index = new Index();
		
		index.setField("Codigo");
		
		index.addEntry("100", 1);
		index.addEntry("102", 2);
		index.addEntry("103", 4);
		index.addEntry("105", 3);
		
		StringWriter writer = new StringWriter();
		JsonUtils.parseObjectToJson(writer, index, Index.class);
		System.out.println(writer.toString());
	}
}
