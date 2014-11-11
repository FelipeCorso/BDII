package br.furb.jsondb.parser.core;

@SuppressWarnings("serial")
public class SyntaticError extends AnalysisError {

	public SyntaticError(String msg, int position) {
		super(msg, position);
	}

	public SyntaticError(String msg) {
		super(msg);
	}

	public SyntaticError(int lastState, int position, Token token) {
		super(geraMensagem(lastState, token), position);
	}

	public static String geraMensagem(int lastState, Token token) {
		if (token.isFimProg()) {
			return "Erro - encontrado fim de programa, " + ParserConstants.PARSER_ERROR[lastState];
		}
		return "Erro - encontrado " + token.getClassificacao() + ", " + ParserConstants.PARSER_ERROR[lastState];
	}
}
