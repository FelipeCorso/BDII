package br.furb.json.ui.action;

import br.furb.json.ui.Principal;
import br.furb.json.ui.status.EStatus;

public class PasteAction {
	public static void executeAction(Principal principal) {
		principal.getCommandPanel().getTextEditor().paste();
		principal.getCommandPanel().getLbStatus().setText(EStatus.MODIFICADO.toString());
	}
}
