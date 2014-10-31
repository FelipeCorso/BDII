package br.furb.jsondb.parser;

import java.util.Optional;

public enum ConstraintKind {

	NULL, //
	NOT_NULL("NOT NULL"), //
	PRIMARY_KEY("PRIMARY KEY"), //
	FOREIGN_KEY("FOREIGN KEY");

	private final Optional<String> representation;

	private ConstraintKind() {
		representation = Optional.empty();
	}

	private ConstraintKind(String representation) {
		this.representation = Optional.ofNullable(representation);
	}

	@Override
	public String toString() {
		return representation.orElse(name());
	}

}
