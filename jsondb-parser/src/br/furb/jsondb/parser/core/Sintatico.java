package br.furb.jsondb.parser.core;

import java.util.Stack;

import br.furb.jsondb.parser.SQLParserException;

public class Sintatico implements Constants {

	private Stack<Integer> stack = new Stack<>();
	private Token currentToken;
	private Token previousToken;
	private Lexico scanner;
	private Semantico semanticAnalyser;

	private static final boolean isTerminal(int x) {
		return x < FIRST_NON_TERMINAL;
	}

	private static final boolean isNonTerminal(int x) {
		return x >= FIRST_NON_TERMINAL && x < FIRST_SEMANTIC_ACTION;
	}

	private boolean step() throws LexicalError, SyntaticError, SQLParserException {
		if (currentToken == null) {
			int pos = 0;
			if (previousToken != null)
				pos = previousToken.getPosition() + previousToken.getLexeme().length();

			currentToken = new Token(DOLLAR, "$", pos, true);
		}

		int x = stack.pop().intValue();
		int a = currentToken.getId();

		if (x == EPSILON) {
			return false;
		} else if (isTerminal(x)) {
			if (x == a) {
				if (stack.empty())
					return true;
				previousToken = currentToken;
				currentToken = scanner.nextToken();
				return false;
			}
			throw new SyntaticError(x, currentToken.getPosition(), currentToken);
		} else if (isNonTerminal(x)) {
			if (pushProduction(x, a))
				return false;
			throw new SyntaticError(x, currentToken.getPosition(), currentToken);
		} else // isSemanticAction(x)
		{
			semanticAnalyser.executeAction(x - FIRST_SEMANTIC_ACTION, previousToken);
			return false;
		}
	}

	private boolean pushProduction(int topStack, int tokenInput) {
		int p = PARSER_TABLE[topStack - FIRST_NON_TERMINAL][tokenInput - 1];
		if (p >= 0) {
			int[] production = PRODUCTIONS[p];
			//empilha a produção em ordem reversa
			for (int i = production.length - 1; i >= 0; i--) {
				stack.push(new Integer(production[i]));
			}
			return true;
		}
		return false;
	}

	public void parse(Lexico scanner, Semantico semanticAnalyser) throws LexicalError, SyntaticError, SQLParserException {
		this.scanner = scanner;
		this.semanticAnalyser = semanticAnalyser;

		stack.clear();
		stack.push(new Integer(DOLLAR));
		stack.push(new Integer(START_SYMBOL));

		currentToken = scanner.nextToken();

		while (!step())
			{}
	}
}
