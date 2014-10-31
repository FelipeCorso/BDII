package br.furb.jsondb.store.metadata;

import java.util.List;

import br.furb.jsondb.parser.ConstraintKind;
import br.furb.jsondb.parser.DataType;

public class FieldMetadata {

	private String name;
	private DataType type;
	private int lenght;
	private int precision;
	private ConstraintKind constraint;

	public FieldMetadata() {
		super();
	}

	public FieldMetadata(String name, DataType type, int lenght) {
		this(name, type, lenght, 0);
	}

	public FieldMetadata(String name, DataType type, int lenght, int precision) {
		super();
		this.name = name;
		this.type = type;
		this.lenght = lenght;
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

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public ConstraintKind getConstraint() {
		return constraint;
	}

	public void setConstraint(ConstraintKind constraint) {
		this.constraint = constraint;
	}

}
