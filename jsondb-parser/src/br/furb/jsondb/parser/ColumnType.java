package br.furb.jsondb.parser;

import java.util.Objects;
import java.util.Optional;

public class ColumnType {

	private DataType dataType;
	private int size;
	private Optional<Integer> precision;

	public ColumnType(DataType dataType) {
		this.dataType = Objects.requireNonNull(dataType, "a data type must be provided as the column type");
	}

	public final int getSize() {
		return size;
	}

	public final void setSize(int size) {
		this.size = size;
	}

	public final Optional<Integer> getPrecision() {
		return precision;
	}

	public final void setPrecision(Optional<Integer> precision) {
		this.precision = precision;
	}

	public final DataType getDataType() {
		return dataType;
	}

}
