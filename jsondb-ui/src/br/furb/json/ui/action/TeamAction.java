package br.furb.json.ui.action;

import br.furb.json.ui.Principal;

public class TeamAction {

	public static void executeAction(Principal principal) {
		principal.getCommandPanel().getTextMsg().setText("Integrantes Equipe: Felipe Loose Corso, Jana�na Carraro Mendon�a Lima, William Leander Seefeld\n");
	}

}
