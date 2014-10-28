package br.furb.jsondb.parser;


public class Index implements IStructure {

	private ColumnIdentifier tableColumn;

	public Index(ColumnIdentifier identifier) {
		if (identifier.getTable() == null) {
			throw new IllegalArgumentException("é necessário informar a tabela");
		}
		this.tableColumn = identifier;
	}

	@Override
	public String getIdentifier() {
		return tableColumn.toPrettyString();
	}

	public ColumnIdentifier getTableColumn() {
		return tableColumn;
	}

}
