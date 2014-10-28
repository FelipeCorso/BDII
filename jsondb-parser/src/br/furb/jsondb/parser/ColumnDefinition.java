package br.furb.jsondb.parser;

import java.util.Objects;

import br.furb.jsondb.utils.ArgumentValidator;

public class ColumnDefinition {

	private ColumnIdentifier identifier;
	private ColumnType columnType;
	private ConstraintDefinition constraint;

	public ColumnDefinition(String name) {
		this.identifier = new ColumnIdentifier(ArgumentValidator.requireNonEmpty(name, "a name must be provided for the column definition"));
	}

	public final ColumnType getColumnType() {
		return columnType;
	}

	public final void setColumnType(ColumnType columnType) {
		this.columnType = Objects.requireNonNull(columnType, "a column type must be provided for the column definition");
	}

	public final ConstraintDefinition getConstraint() {
		return constraint;
	}

	public final void setConstraint(ConstraintDefinition constraint) {
		this.constraint = constraint;
	}

	public final String getName() {
		return identifier.getColumnName();
	}

	public ColumnIdentifier getIdentifier() {
		return identifier;
	}

}
