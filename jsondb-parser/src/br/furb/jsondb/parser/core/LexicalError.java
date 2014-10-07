package br.furb.jsondb.parser.core;

@SuppressWarnings("serial")
public class LexicalError extends AnalysisError {

	public LexicalError(String msg, int position) {
		super(msg, position);
	}

	public LexicalError(String msg) {
		super(msg);
	}
}
