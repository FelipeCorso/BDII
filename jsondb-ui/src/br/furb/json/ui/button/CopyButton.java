package br.furb.json.ui.button;

import javax.swing.JButton;

import br.furb.json.ui.Principal;
import br.furb.json.ui.action.Action;

public class CopyButton extends JButton implements Action {
	private static final long serialVersionUID = 1L;

	public CopyButton() {
		super();
	}

	public CopyButton(String texto) {
		super(texto);
	}

	@Override
	public void executeAction(Principal principal) {
		principal.getCommandPanel().getTextEditor().copy();
	}
}
