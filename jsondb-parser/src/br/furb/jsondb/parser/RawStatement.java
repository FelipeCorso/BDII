package br.furb.jsondb.parser;

/**
 * Representa um comando SQL ainda não reconhecido pelo interpretador, mantendo
 * apenas a string correspondente ao comando ainda indefinido.
 */
public class RawStatement {

	private String sqlCommand;

	public RawStatement(String sqlCommand) {
		this.sqlCommand = sqlCommand;
	}

	public String getSQLCommand() {
		return sqlCommand;
	}

}
