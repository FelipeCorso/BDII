package br.furb.jsondb.store.metadata;

public class FieldMetadata {

	private String name;
	private FieldType type;
	private int lenght;

	public FieldMetadata() {
		super();
	}

	public FieldMetadata(String name, FieldType type, int lenght) {
		super();
		this.name = name;
		this.type = type;
		this.lenght = lenght;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	@Override
	public String toString() {
		return "FieldMetadata [name=" + name + ", type=" + type + ", lenght="
				+ lenght + "]";
	}

}
