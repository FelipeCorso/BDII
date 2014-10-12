package br.furb.jsondb.parser.core;

import br.furb.jsondb.parser.core.$helper.StatementParser;

public class Semantico implements Constants {

	private StatementParser stmParser;

	public Semantico() {
		stmParser = new StatementParser();
	}

	public void executeAction(int action, Token token) {
		System.out.println("Ação #" + action + ", Token: " + token);
		stmParser.executeAction(action, token);
	}
}
