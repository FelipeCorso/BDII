package br.furb.jsondb.store.data;

public class ColumnData {

	private String name;
	private Object value;

	public ColumnData() {
	}

	public ColumnData(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ColumnData [name=" + name + ", value=" + value + "]";
	}

}
