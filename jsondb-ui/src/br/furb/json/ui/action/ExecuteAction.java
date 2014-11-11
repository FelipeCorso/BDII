package br.furb.json.ui.action;

import br.furb.json.ui.Principal;
import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.sql.SQLException;

public class ExecuteAction {

	public static void executeAction(Principal principal) {
		try {
			IResult result = JsonDB.getInstance().executeSQL(principal.getTabbedPanel().getCommandPanel().getTextEditor().getText());
			result.getMessages().forEach(message -> principal.getTabbedPanel().getCommandPanel().getTextMsg().append(message));

		} catch (SQLParserException e) {
			System.err.println("Não foi possível executar o comando: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Não foi possível executar o comando: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
