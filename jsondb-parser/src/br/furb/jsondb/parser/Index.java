package br.furb.jsondb.parser;

import java.util.Objects;

public class Index implements IStructure {

	private String identifier;
	private TableIdentifier table;
	private ColumnIdentifier tableColumn;

	public Index(String identifier, ColumnIdentifier tableColumn) {
		this.identifier = Objects.requireNonNull(identifier, "an identifier must be provided");
		this.tableColumn = tableColumn;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	public ColumnIdentifier getTableColumn() {
		return tableColumn;
	}

	public void setTableColumn(ColumnIdentifier tableColumn) {
		this.tableColumn = tableColumn;
	}

	public TableIdentifier getTable() {
		return tableColumn == null ? table : tableColumn.getTable().get();
	}

	@Override
	public String toString() {
		String indexPart;
		if (getTableColumn() != null && getTableColumn().getTable().isPresent()) {
			String table = getTableColumn().getTable().get().toString();
			String column = getTableColumn().getColumnName();
			indexPart = " ON ".concat(table.concat("('").concat(column).concat("')"));
		} else if (getTable() != null) {
			indexPart = " ON ".concat(getTable().toString());
		} else {
			indexPart = "";
		}
		return "INDEX '".concat(getIdentifier()).concat("'").concat(indexPart);
	}

	public void setTable(TableIdentifier table) {
		this.tableColumn = null;
		this.table = table;
	}

}
