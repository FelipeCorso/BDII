package br.furb.jsondb.parser.core;

import java.util.List;

import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.parser.core.$helper.StatementParser;
import br.furb.jsondb.parser.statement.IStatement;

public class Semantico implements Constants {

	private StatementParser stmParser;

	public Semantico() {
		stmParser = new StatementParser();
	}

	public void executeAction(int action, Token token) throws SQLParserException {
		System.out.println("Ação #" + action + ", Token: " + token);
		stmParser.executeAction(action, token);
	}

	public List<IStatement> getStatements() {
		return stmParser.getStatements();
	}
}
