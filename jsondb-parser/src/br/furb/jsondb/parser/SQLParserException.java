package br.furb.jsondb.parser;

/**
 * Exceção lançada para indicar que houve um erro ao tentar reconhecer um
 * comando SQL.
 *
 */
public class SQLParserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1933922140234321455L;
	private String sqlCommand;

	public SQLParserException(String message) {
		super(message);
	}

	public SQLParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public SQLParserException(String message, Throwable cause, String sqlCommand) {
		super(message, cause);
		this.sqlCommand = sqlCommand;
	}

	public SQLParserException(Throwable cause, String sqlCommand) {
		this(null, cause, sqlCommand);
	}

	public SQLParserException(Throwable cause) {
		this(cause, null);
	}

	public String getSqlCommand() {
		return sqlCommand;
	}

}
