package br.furb.jsondb.core;

public class SQLException extends Exception {

	private static final long serialVersionUID = 1L;

	public SQLException(String message) {
		super(message);
	}

	public SQLException(String message, Throwable cause) {
		super(message, cause);
	}
}
