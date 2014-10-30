package br.furb.jsondb.parser;

import java.util.Objects;
import java.util.Optional;

import br.furb.jsondb.utils.ArgumentValidator;

public class ColumnDefinition {

	private ColumnIdentifier identifier;
	private ColumnType columnType;
	private Optional<ConstraintDefinition> maybeConstraint;

	public ColumnDefinition(String name) {
		this.identifier = new ColumnIdentifier(ArgumentValidator.requireNonEmpty(name, "a name must be provided for the column definition"));
	}

	public final ColumnType getColumnType() {
		return columnType;
	}

	public final void setColumnType(ColumnType columnType) {
		this.columnType = Objects.requireNonNull(columnType, "a column type must be provided for the column definition");
	}

	public final Optional<ConstraintDefinition> getConstraint() {
		return maybeConstraint;
	}

	public final void setConstraint(ConstraintDefinition constraint) {
		this.maybeConstraint = Optional.ofNullable(constraint);
	}

	public final String getName() {
		return identifier.getColumnName();
	}

	public ColumnIdentifier getIdentifier() {
		return identifier;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getName());
		sb.append(" ").append(getColumnType());
		if (maybeConstraint.isPresent()) {
			sb.append(" CONSTRAINT ").append(maybeConstraint.get());
		}
		return sb.toString();
	}

}
