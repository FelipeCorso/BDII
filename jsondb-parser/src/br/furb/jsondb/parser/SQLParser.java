package br.furb.jsondb.parser;

import java.util.Collection;

import br.furb.jsondb.parser.core.LexicalError;
import br.furb.jsondb.parser.core.Lexico;
import br.furb.jsondb.parser.core.Semantico;
import br.furb.jsondb.parser.core.Sintatico;
import br.furb.jsondb.parser.core.SyntaticError;
import br.furb.jsondb.parser.statement.IStatement;
import br.furb.jsondb.parser.statement.RawStatement;

/**
 * Reúne todos os métodos para interpretação de comandos SQL.
 */
public class SQLParser {

	/**
	 * Faz a conversão do comando SQL representado pela string informada e
	 * retorna o {@link IStatement} correspondente, com seus atributos
	 * devidamente configurados.
	 * 
	 * @param command
	 *            comando SQL completo, incluindo o ponto-e-vírgula (";").
	 * @return {@link IStatement} correspondente, com seus atributos devidamente
	 *         configurados.
	 */
	public static IStatement parse(String command) throws SQLParserException {
		Sintatico sintatico = new Sintatico();
		Lexico lexico = new Lexico(command);
		Semantico semantico = new Semantico();
		try {
			sintatico.parse(lexico, semantico);
		} catch (SyntaticError | LexicalError e) {
			throw new SQLParserException("Problem parsing SQL command", e, command);
		}
		return semantico.getStatement();
	}

	/**
	 * Interpreta uma string como um arquivo de comandos SQL e retorna apenas o
	 * conteúdo que siga o formato "{@code <instrucoes> ;}
	 * ", ignorando quebras de linha e todo o conteúdo precedido por "--".<br>
	 * O símbolo ";" é retornado junto a cada comando reconhecido.
	 * 
	 * @param plainText
	 *            string com conjunto de comandos SQL e comentários.
	 * @return coleção ordenada por ordem de reconhecimento com todas as
	 *         ocorrências do formato descrito acima.
	 */
	public static Collection<RawStatement> extractCommands(String plainText) {
		// TODO: criar gramática para reconhecer adequadamente o que foi descrito no JavaDoc
		return null;
	}

}
