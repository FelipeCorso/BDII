package br.furb.json.ui.action;

import java.util.List;

import br.furb.json.ui.Principal;
import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.parser.SQLParserException;

public class ExecuteAction {
	public static void executeAction(Principal principal) {
		try {
			List<IResult> result = JsonDB.getInstance().executeSQL(principal.getTabbedPanel().getCommandPanel().getTextEditor().getText());

			for (IResult iResult : result) {
				for (String message : iResult.getMessages()) {
					principal.getTabbedPanel().getCommandPanel().getTextMsg().append(message);
				}
			}

		} catch (SQLParserException e) {
			System.err.println("ERRO FATAL!\nNão foi possível executar o comando! Por favor, verifique a sintaxe.");
			e.printStackTrace();
		}
	}
}
