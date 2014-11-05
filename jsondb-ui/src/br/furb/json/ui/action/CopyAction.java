package br.furb.json.ui.action;

import br.furb.json.ui.Principal;

public class CopyAction {
	public static void executeAction(Principal principal) {
		principal.getTabbedPanel().getCommandPanel().getTextEditor().copy();
	}
}
