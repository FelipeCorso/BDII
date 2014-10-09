import java.util.Scanner;

import br.furb.jsondb.parser.core.Lexico;
import br.furb.jsondb.parser.core.Semantico;
import br.furb.jsondb.parser.core.Sintatico;

public class Main {

	private static final String DELIMITER = ";";

	public static void main(String[] args) throws Exception {
		try (Scanner sc = new Scanner(System.in)) {
			sc.useDelimiter(DELIMITER);
			String program = sc.next() + DELIMITER;
			Sintatico sintatico = new Sintatico();
			Lexico lexico = new Lexico(program);
			Semantico semantico = new Semantico();
			sintatico.parse(lexico, semantico);
			System.out.println("Programa compilado com sucesso!");
		}
	}

}
