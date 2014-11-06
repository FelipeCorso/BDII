package br.furb.jsondb.parser.statement;

import java.util.List;

import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.WhereClause;

public class SelectStatement implements IStatement {

	private List<ColumnIdentifier> columns;
	private WhereClause whereClause;
	private List<TableIdentifier> tables;

	public SelectStatement() {
		this(null, null);
	}

	public SelectStatement(List<ColumnIdentifier> columns) {
		this(columns, null);
	}

	public SelectStatement(List<ColumnIdentifier> columns, WhereClause whereClause) {
		this.columns = columns;
		this.whereClause = whereClause;
	}

	public final List<ColumnIdentifier> getColumns() {
		return columns;
	}

	public final void setColumns(List<ColumnIdentifier> columns) {
		this.columns = columns;
	}

	public final WhereClause getWhereClause() {
		return whereClause;
	}

	public final void setWhereClause(WhereClause whereClause) {
		this.whereClause = whereClause;
	}

	public void setTables(List<TableIdentifier> tables) {
		this.tables = tables;
	}

	public List<TableIdentifier> getTables() {
		return tables;
	}

}
