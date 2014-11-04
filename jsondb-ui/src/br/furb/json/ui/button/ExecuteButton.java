package br.furb.json.ui.button;

import javax.swing.JButton;

import br.furb.json.ui.Principal;
import br.furb.json.ui.action.Action;

public class ExecuteButton extends JButton implements Action {
	private static final long serialVersionUID = 1L;

	// BuscaClasse buscaClasse = new BuscaClasse();

	public ExecuteButton() {
		super();
	}

	public ExecuteButton(String texto) {
		super(texto);
	}

	@Override
	public void executeAction(Principal principal) {
		if (!principal.getCommandPanel().getTextEditor().getText().isEmpty()) {
			// FIXME: AJUSTAR
			// AcaoCompilar.compilar(principal, "",
			// "\tPrograma compilado com sucesso!");
		} else {
			principal.getCommandPanel().getTextMsg().setText("Nenhum programa para compilar!");
		}
	}
}
