package br.furb.json.ui.action;

import br.furb.json.ui.Principal;

public class TeamAction {

	public static void executeAction(Principal principal) {
		principal.getCommandPanel().getTextMsg().setText("Integrantes Equipe: Felipe Loose Corso, Janaína Carraro Mendonça Lima, William Leander Seefeld\n");
	}

}
