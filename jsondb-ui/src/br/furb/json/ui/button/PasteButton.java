package br.furb.json.ui.button;

import javax.swing.JButton;

import br.furb.json.ui.Principal;
import br.furb.json.ui.action.Action;
import br.furb.json.ui.status.EStatus;

public class PasteButton extends JButton implements Action {
	private static final long serialVersionUID = 1L;

	public PasteButton() {
		super();
	}

	public PasteButton(String texto) {
		super(texto);
	}

	@Override
	public void executeAction(Principal principal) {
		principal.getCommandPanel().getTextEditor().paste();
		principal.getCommandPanel().getLbStatus().setText(EStatus.MODIFICADO.toString());
	}
}
