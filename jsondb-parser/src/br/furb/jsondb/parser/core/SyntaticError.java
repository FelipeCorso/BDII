package br.furb.jsondb.parser.core;

@SuppressWarnings("serial")
public class SyntaticError extends AnalysisError {

	public SyntaticError(String msg, int position) {
		super(msg, position);
	}

	public SyntaticError(String msg) {
		super(msg);
	}
}
