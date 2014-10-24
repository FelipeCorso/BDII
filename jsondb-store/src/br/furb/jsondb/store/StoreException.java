package br.furb.jsondb.store;

public class StoreException extends Exception {

	private static final long serialVersionUID = 1L;

	public StoreException(String message) {
		super(message);
	}

	public StoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public StoreException(Throwable cause) {
		super(cause);
	}

}
