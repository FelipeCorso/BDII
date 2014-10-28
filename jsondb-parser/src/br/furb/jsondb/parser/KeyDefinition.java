package br.furb.jsondb.parser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class KeyDefinition extends ConstraintDefinition {

	private List<ColumnIdentifier> sourceColumns;

	public KeyDefinition(String name, ConstraintKind kind, ColumnIdentifier sourceColum) {
		this(name, kind, Arrays.asList(sourceColum));
	}

	public KeyDefinition(String name, ConstraintKind kind, List<ColumnIdentifier> sourceColumns) {
		super(name, kind);
		this.sourceColumns = new LinkedList<ColumnIdentifier>(Objects.requireNonNull(sourceColumns, "source columns must be provided for the key constraint definition"));
	}

}
