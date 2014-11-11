package br.furb.jsondb.store.metadata;

import br.furb.jsondb.parser.ConstraintKind;

public class ConstraintMetadata {

	private String name;
	private String table;
	private String column;
	private ConstraintKind kind;

	public ConstraintMetadata(String name, String table, ConstraintKind kind) {
		this(name, table, kind, null);
	}

	public ConstraintMetadata(String name, String table, ConstraintKind kind,
			String column) {
		super();
		this.name = name;
		this.table = table;
		this.column = column;
		this.kind = kind;
	}

	public String getName() {
		return name;
	}

	public ConstraintKind getKind() {
		return kind;
	}

	public String getTable() {
		return table;
	}

	public String getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return "ConstraintMetadata [name=" + name + ", table=" + table
				+ ", column=" + column + ", kind=" + kind + "]";
	}

}
