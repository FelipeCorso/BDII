package br.furb.json.ui.action;

import br.furb.json.ui.Principal;
import br.furb.json.ui.status.EStatus;

public class NewAction {
	public static void executeAction(Principal principal) {
		principal.getCommandPanel().getTextEditor().setText("");
		principal.getCommandPanel().getTextMsg().setText("");
		principal.getCommandPanel().getLbFilePath().setText("");
		principal.getKeyListener().setTextoEditor("");
		principal.getCommandPanel().getLbStatus().setText(EStatus.NAO_MODIFICADO.toString());
	}
}
