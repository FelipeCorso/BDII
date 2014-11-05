package br.furb.json.ui.action;

import br.furb.json.ui.Principal;
import br.furb.jsondb.core.JsonDB;

public class ExecuteAction {
	public static void executeAction(Principal principal) {
		JsonDB.getInstance().executeSQL(principal.getTabbedPanel().getCommandPanel().getTextEditor().getText());
	}
}
