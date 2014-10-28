package br.furb.jsondb.parser;

import java.util.Objects;

import br.furb.jsondb.utils.ArgumentValidator;

public class ColumnDefinition {

	private String name;
	private ColumnType columnType;
	private ColumnRestriction columnRestriction;

	public ColumnDefinition(String name) {
		this.name = ArgumentValidator.requireNonEmpty(name, "a name must be provided for the column definition");
	}

	public final ColumnType getColumnType() {
		return columnType;
	}

	public final void setColumnType(ColumnType columnType) {
		this.columnType = Objects.requireNonNull(columnType, "a column type must be provided for the column definition");
	}

	public final ColumnRestriction getColumnRestriction() {
		return columnRestriction;
	}

	public final void setColumnRestriction(ColumnRestriction columnRestriction) {
		this.columnRestriction = columnRestriction;
	}

	public final String getName() {
		return name;
	}

}
