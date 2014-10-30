package br.furb.jsondb.parser;

/**
 * @deprecated A estrutura gramática será alterada
 * @author william.seefeld
 *
 */
@Deprecated
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
		return tableColumn.toString();
	}

	public ColumnIdentifier getTableColumn() {
		return tableColumn;
	}

	@Override
	public String toString() {
		return getIdentifier();
	}

}
