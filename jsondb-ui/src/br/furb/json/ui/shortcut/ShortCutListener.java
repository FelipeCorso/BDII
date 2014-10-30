package br.furb.json.ui.shortcut;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import br.furb.json.ui.Principal;
import br.furb.json.ui.button.CopyButton;
import br.furb.json.ui.button.CutButton;
import br.furb.json.ui.button.NewButton;
import br.furb.json.ui.button.OpenButton;
import br.furb.json.ui.button.PasteButton;
import br.furb.json.ui.button.SaveButton;
import br.furb.json.ui.button.TeamButton;
import br.furb.json.ui.status.EStatus;

public class ShortCutListener implements KeyListener {

	private Principal compUi;
	private String textEditor;

	public ShortCutListener(Principal compUi) {
		this.compUi = compUi;
		textEditor = "";
	}

	@Override
	public void keyPressed(KeyEvent event) {
		boolean isCtrlDown = event.isControlDown();
		switch (event.getKeyCode()) {
		case KeyEvent.VK_N:
			if (isCtrlDown) {
				new NewButton().executaAcao(compUi);
			}
			break;
		case KeyEvent.VK_A:
			if (isCtrlDown) {
				new OpenButton().executaAcao(compUi);
			}
			break;
		case KeyEvent.VK_S:
			if (isCtrlDown) {
				new SaveButton().executaAcao(compUi);
			}
			break;
		case KeyEvent.VK_C:
			if (isCtrlDown) {
				new CopyButton().executaAcao(compUi);
			}
			break;
		case KeyEvent.VK_V:
			if (isCtrlDown) {
				new PasteButton().executaAcao(compUi);
			}
			break;
		case KeyEvent.VK_X:
			if (isCtrlDown) {
				new CutButton().executaAcao(compUi);
			}
			break;
		// case KeyEvent.VK_F8:
		// new BotaoCompilar().executaAcao(compUi);
		// break;
		// case KeyEvent.VK_F9:
		// new BotaoGerarCodigo().executaAcao(compUi);
		// break;
		case KeyEvent.VK_F1:
			new TeamButton().executaAcao(compUi);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if (!textEditor.equalsIgnoreCase(compUi.getCommandPanel().getTextEditor().getText())) {
			compUi.getCommandPanel().getLbStatus().setText(EStatus.MODIFICADO.toString());
		} else {
			compUi.getCommandPanel().getLbStatus().setText(EStatus.NAO_MODIFICADO.toString());
		}

	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub

	}

	public String getTextoEditor() {
		return textEditor;
	}

	public void setTextoEditor(String textEditor) {
		this.textEditor = textEditor;
	}

}
