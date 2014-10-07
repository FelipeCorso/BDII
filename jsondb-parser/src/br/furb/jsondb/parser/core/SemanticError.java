package br.furb.jsondb.parser.core;

@SuppressWarnings("serial")
public class SemanticError extends AnalysisError {

	public SemanticError(String msg, int position) {
		super(msg, position);
	}

	public SemanticError(String msg) {
		super(msg);
	}
}
