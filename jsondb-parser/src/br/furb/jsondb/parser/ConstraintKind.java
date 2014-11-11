package br.furb.jsondb.parser;

import java.util.Optional;

public enum ConstraintKind {

	NULL("n"), //
	NOT_NULL("nn", "NOT NULL"), //
	PRIMARY_KEY("pk", "PRIMARY KEY"), //
	FOREIGN_KEY("fk", "FOREIGN KEY");

	private final Optional<String> representation;
	private String shortName;

	private ConstraintKind(String shortName) {
		this.shortName = shortName;
		representation = Optional.empty();
	}

	private ConstraintKind(String shortName, String representation) {
		this.shortName = shortName;
		this.representation = Optional.ofNullable(representation);
	}

	@Override
	public String toString() {
		return representation.orElse(name());
	}

	public String getShortName() {
		return shortName;
	}

}
