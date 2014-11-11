package br.furb.jsondb.parser.statement;

import java.util.List;
import java.util.Optional;

import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.WhereClause;

public class SelectStatement implements IStatement {

	private List<ColumnIdentifier> columns;
	private Optional<WhereClause> maybeWhere;
	private List<TableIdentifier> tables;

	public SelectStatement() {
		this(null, null);
	}

	public SelectStatement(List<ColumnIdentifier> columns) {
		this(columns, null);
	}

	public SelectStatement(List<ColumnIdentifier> columns, WhereClause whereClause) {
		this.columns = columns;
		this.maybeWhere = Optional.ofNullable(whereClause);
	}

	public final List<ColumnIdentifier> getColumns() {
		return columns;
	}

	public final void setColumns(List<ColumnIdentifier> columns) {
		this.columns = columns;
	}

	public final Optional<WhereClause> getWhereClause() {
		return maybeWhere;
	}

	public final void setWhereClause(WhereClause whereClause) {
		this.maybeWhere = Optional.ofNullable(whereClause);
	}

	public void setTables(List<TableIdentifier> tables) {
		this.tables = tables;
	}

	public List<TableIdentifier> getTables() {
		return tables;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("SELECT ");
		columns.forEach(column -> sb.append(column).append(", "));
		int charCount = sb.length();
		if (!columns.isEmpty()) {
			sb.delete(charCount - 2, charCount);
		}
		sb.append(" FROM ");
		tables.forEach(table -> sb.append(table).append(", "));
		charCount = sb.length();
		if (!tables.isEmpty()) {
			sb.delete(charCount - 2, charCount);
		}
		if (maybeWhere.isPresent()) {
			sb.append(" ").append(maybeWhere.get());
		}
		return sb.toString();
	}

}
