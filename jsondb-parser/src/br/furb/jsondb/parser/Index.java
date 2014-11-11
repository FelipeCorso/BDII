package br.furb.jsondb.parser;

import java.util.Objects;

public class Index implements IStructure {

	private String identifier;
	private ColumnIdentifier tableColumn;

	public Index(String identifier, ColumnIdentifier tableColumn) {
		this.identifier = Objects.requireNonNull(identifier, "an identifier must be provided");
		this.tableColumn = Objects.requireNonNull(tableColumn, "a qualified column must be provided");
		Objects.requireNonNull(tableColumn.getTable(), "a table must be provided");
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	public ColumnIdentifier getTableColumn() {
		return tableColumn;
	}

	@Override
	public String toString() {
		String indexPart;
		if (getTableColumn().getTable().isPresent()) {
			String table = getTableColumn().getTable().get().toString();
			String column = getTableColumn().getColumnName();
			indexPart = " ON ".concat(table.concat("('").concat(column).concat("')"));
		} else {
			indexPart = "";
		}
		return "INDEX '".concat(getIdentifier()).concat("'").concat(indexPart);
	}

}
