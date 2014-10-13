package br.furb.jsondb.parser;

public class Index implements IStructure {

	private TableColumn tableColumn;

	public Index(TableColumn identifier) {
		if (identifier.getTable() == null) {
			throw new IllegalArgumentException("é necessário informar a tabela");
		}
		this.tableColumn = identifier;
	}

	@Override
	public String getIdentifier() {
		return tableColumn.toPrettyString();
	}

	public TableColumn getTableColumn() {
		return tableColumn;
	}

}
