package br.furb.jsondb.parser;

import java.util.List;

public class SelectStatement implements IStatement {

	private List<TableColumn> columns;
	private WhereClause whereClause;

}
