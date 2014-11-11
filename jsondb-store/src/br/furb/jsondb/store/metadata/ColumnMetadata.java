package br.furb.jsondb.store.metadata;

import br.furb.jsondb.parser.DataType;

public class ColumnMetadata {

	private String name;
	private DataType type;
	private int length;
	private int precision;
	private String constraint;

	public ColumnMetadata() {
		super();
	}

	public ColumnMetadata(String name, DataType type, int lenght) {
		this(name, type, lenght, 0);
	}

	public ColumnMetadata(String name, DataType type, int lenght, int precision) {
		super();
		this.name = name;
		this.type = type;
		this.length = lenght;
		this.precision = precision;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataType getType() {
		return type;
	}

	public void setType(DataType type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "ColumnMetadata [name=" + name + ", type=" + type + ", length=" + length + "]";
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public String getConstraint() {
		return constraint;
	}

	public void setConstraint(String constraint) {
		this.constraint = constraint;
	}

}
