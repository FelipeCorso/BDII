package br.furb.jsondb.parser;

import java.util.Objects;
import java.util.Optional;

public class ConstraintDefinition {

	private Optional<String> name;
	private ConstraintKind kind;

	public ConstraintDefinition(ConstraintKind kind) {
		this(null, kind);
	}

	public ConstraintDefinition(String name, ConstraintKind kind) {
		this.name = Optional.ofNullable(name);
		this.kind = Objects.requireNonNull(kind, "a kind must be provided for the constraint definition");
	}

	public Optional<String> getName() {
		return name;
	}

	public void setName(Optional<String> name) {
		this.name = name;
	}

	public ConstraintKind getKind() {
		return kind;
	}

	public void setKind(ConstraintKind kind) {
		this.kind = kind;
	}
	
	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		if (getName().isPresent()) {
			ret.append("CONSTRAINT '").append(getName().get()).append("' ");
		}
		ret.append(kind);
		
		return ret.toString();
	}

}
