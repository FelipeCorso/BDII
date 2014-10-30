package br.furb.json.ui.button;

import javax.swing.JButton;

import br.furb.json.ui.Principal;
import br.furb.json.ui.action.Action;
import br.furb.json.ui.status.EStatus;

public class NewButton extends JButton implements Action {
	private static final long serialVersionUID = 1L;

	public NewButton() {
		super();
	}

	public NewButton(String texto) {
		super(texto);
	}

	@Override
	public void executaAcao(Principal principal) {
		principal.getCommandPanel().getTextEditor().setText("");
		principal.getCommandPanel().getTextMsg().setText("");
		principal.getCommandPanel().getLbFilePath().setText("");
		principal.getKeyListener().setTextoEditor("");
		principal.getCommandPanel().getLbStatus().setText(EStatus.NAO_MODIFICADO.toString());
	}
}
