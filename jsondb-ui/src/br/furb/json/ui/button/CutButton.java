package br.furb.json.ui.button;

import javax.swing.JButton;

import br.furb.json.ui.Principal;
import br.furb.json.ui.action.Action;
import br.furb.json.ui.status.EStatus;

public class CutButton extends JButton implements Action {
	private static final long serialVersionUID = 1L;

	public CutButton() {
		super();
	}

	public CutButton(String texto) {
		super(texto);
	}

	@Override
	public void executaAcao(Principal principal) {
		principal.getCommandPanel().getTextEditor().cut();
		principal.getCommandPanel().getLbStatus().setText(EStatus.MODIFICADO.toString());
	}
}
