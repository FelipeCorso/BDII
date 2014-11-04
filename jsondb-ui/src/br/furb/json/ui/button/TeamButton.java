package br.furb.json.ui.button;

import javax.swing.JButton;

import br.furb.json.ui.Principal;
import br.furb.json.ui.action.Action;

public class TeamButton extends JButton implements Action {
	private static final long serialVersionUID = 1L;

	public TeamButton() {
		super();
	}

	public TeamButton(String texto) {
		super(texto);
	}

	@Override
	public void executeAction(Principal principal) {
		principal.getCommandPanel().getTextMsg().setText("Integrantes Equipe: Felipe Loose Corso, Jana�na Carraro Mendon�a Lima, William Leander Seefeld\n");
	}
}
