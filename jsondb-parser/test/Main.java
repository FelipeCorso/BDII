import java.util.Scanner;

import br.furb.jsondb.parser.Lexico;
import br.furb.jsondb.parser.Semantico;
import br.furb.jsondb.parser.Sintatico;

public class Main {

	public static void main(String[] args) throws Exception {
		try (Scanner sc = new Scanner(System.in)) {
			//			sc.useDelimiter(PROGRAM_DELIMITER_REGEXP);
			String program = sc.next();
			Sintatico sintatico = new Sintatico();
			Lexico lexico = new Lexico(program);
			Semantico semantico = new Semantico();
			sintatico.parse(lexico, semantico);
			System.out.println("Programa compilado com sucesso!");
		}
	}

}