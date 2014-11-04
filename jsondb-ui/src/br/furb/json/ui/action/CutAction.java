package br.furb.json.ui.action;

import br.furb.json.ui.Principal;
import br.furb.json.ui.status.EStatus;

public class CutAction {
	public static void executeAction(Principal principal) {
		principal.getCommandPanel().getTextEditor().cut();
		principal.getCommandPanel().getLbStatus().setText(EStatus.MODIFICADO.toString());
	}
}
