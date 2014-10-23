package br.furb.jsondb.parser;

import java.util.List;

public class SelectStatement implements IStatement {

	private List<TableColumn> columns;
	private WhereClause whereClause;
	private List<TableIdentifier> tables;

	public SelectStatement() {
		this(null, null);
	}

	public SelectStatement(List<TableColumn> columns) {
		this(columns, null);
	}

	public SelectStatement(List<TableColumn> columns, WhereClause whereClause) {
		this.columns = columns;
		this.whereClause = whereClause;
	}

	public final List<TableColumn> getColumns() {
		return columns;
	}

	public final void setColumns(List<TableColumn> columns) {
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
