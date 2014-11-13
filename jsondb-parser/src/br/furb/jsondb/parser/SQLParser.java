package br.furb.jsondb.parser;

import java.util.List;

import br.furb.jsondb.parser.core.LexicalError;
import br.furb.jsondb.parser.core.Lexico;
import br.furb.jsondb.parser.core.Semantico;
import br.furb.jsondb.parser.core.Sintatico;
import br.furb.jsondb.parser.core.SyntaticError;
import br.furb.jsondb.parser.statement.IStatement;

/**
 * Reúne todos os métodos para interpretação de comandos SQL.
 */
public class SQLParser {

	/**
	 * Faz a conversão dos comandos SQL representados pela string informada e
	 * retorna uma lista de {@link IStatement} correspondentes, com seus
	 * atributos devidamente configurados.<br>
	 * Os comandos são dispostos na lista de acordo com a ordem de
	 * reconhecimento.
	 * 
	 * @param command
	 *            comando SQL completo, incluindo o ponto-e-vírgula (";").
	 * @return lista {@link IStatement} correspondentes, com seus atributos
	 *         devidamente configurados.
	 */
	public static List<IStatement> parse(String command) throws SQLParserException {
		Sintatico sintatico = new Sintatico();
		Lexico lexico = new Lexico(command);
		Semantico semantico = new Semantico();
		try {
			sintatico.parse(lexico, semantico);
		} catch (SyntaticError | LexicalError e) {
			throw new SQLParserException("Problem parsing SQL commands", e, command);
		}
		return semantico.getStatements();
	}

}
