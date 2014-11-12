package br.furb.json.ui.shortcut;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import br.furb.json.ui.Actions;
import br.furb.json.ui.Principal;
import br.furb.json.ui.status.EStatus;

public class ShortCutListener implements KeyListener {

	private Principal compUi;
	private String textEditor;

	public ShortCutListener(Principal compUi) {
		this.compUi = compUi;
		textEditor = "";
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void keyPressed(KeyEvent event) {
		boolean isCtrlDown = event.isControlDown();
		switch (event.getKeyCode()) {
		case KeyEvent.VK_N:
			if (isCtrlDown) {
				compUi.doSafely(Actions::newDatabase);
			}
			break;
		case KeyEvent.VK_A:
			if (isCtrlDown) {
				compUi.doSafely(Actions::openDatabase);
			}
			break;
		case KeyEvent.VK_S:
			if (isCtrlDown) {
				compUi.doSafely(Actions::saveScript);
			}
			break;
		case KeyEvent.VK_C:
			if (isCtrlDown) {
				compUi.doSafely(Actions::copy);
			}
			break;
		case KeyEvent.VK_V:
			if (isCtrlDown) {
				compUi.doSafely(Actions::paste);
			}
			break;
		case KeyEvent.VK_X:
			if (isCtrlDown) {
				compUi.doSafely(Actions::cut);
			}
			break;
		// case KeyEvent.VK_F8:
		// new BotaoCompilar().executeAction(compUi);
		// break;
		// case KeyEvent.VK_F9:
		// new BotaoGerarCodigo().executeAction(compUi);
		// break;
		case KeyEvent.VK_F1:
			Actions.showTeam(compUi);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// FIXME: COMANDO DEVE SER EXECUTADO NA ABA SELECIONADA
		if (!textEditor.equalsIgnoreCase(compUi.getTabbedPanel().getCommandPanel().getTextEditor().getText())) {
			compUi.getTabbedPanel().getCommandPanel().getLbStatus().setText(EStatus.MODIFICADO.toString());
		} else {
			compUi.getTabbedPanel().getCommandPanel().getLbStatus().setText(EStatus.NAO_MODIFICADO.toString());
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
