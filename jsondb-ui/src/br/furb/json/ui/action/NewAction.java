package br.furb.json.ui.action;

import br.furb.json.ui.Principal;
import br.furb.json.ui.status.EStatus;

public class NewAction {
	public static void executeAction(Principal principal) {
		principal.getTabbedPanel().getCommandPanel().getTextEditor().setText("");
		principal.getTabbedPanel().getCommandPanel().getTextMsg().setText("");
		principal.getTabbedPanel().getCommandPanel().getLbFilePath().setText("");
		principal.getKeyListener().setTextoEditor("");
		principal.getTabbedPanel().getCommandPanel().getLbStatus().setText(EStatus.NAO_MODIFICADO.toString());
	}
}
