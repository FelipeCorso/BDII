package br.furb.jsondb.parser;

import java.util.Objects;
import java.util.Optional;

public class ColumnType {

	private DataType dataType;
	private Optional<Integer> maybeSize = Optional.empty();
	private Optional<Integer> maybePrecision = Optional.empty();

	public ColumnType(DataType dataType) {
		this.dataType = Objects.requireNonNull(dataType, "a data type must be provided as the column type");
	}

	public Optional<Integer> getSize() {
		return maybeSize;
	}

	public void setSize(Integer size) {
		this.maybeSize = Optional.ofNullable(size);
	}

	public Optional<Integer> getPrecision() {
		return maybePrecision;
	}

	public void setPrecision(Integer precision) {
		this.maybePrecision = Optional.ofNullable(precision);
	}

	public DataType getDataType() {
		return dataType;
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder(getDataType().toString());
		if (getSize().isPresent()) {
			ret.append(" (").append(getSize().get());
			if (getPrecision().isPresent()) {
				ret.append(", ").append(getPrecision().get());
			}
			ret.append(")");
		}
		return ret.toString();
	}
}
