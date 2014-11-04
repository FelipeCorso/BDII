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
				new NewButton().executeAction(compUi);
			}
			break;
		case KeyEvent.VK_A:
			if (isCtrlDown) {
				new OpenButton().executeAction(compUi);
			}
			break;
		case KeyEvent.VK_S:
			if (isCtrlDown) {
				new SaveButton().executeAction(compUi);
			}
			break;
		case KeyEvent.VK_C:
			if (isCtrlDown) {
				new CopyButton().executeAction(compUi);
			}
			break;
		case KeyEvent.VK_V:
			if (isCtrlDown) {
				new PasteButton().executeAction(compUi);
			}
			break;
		case KeyEvent.VK_X:
			if (isCtrlDown) {
				new CutButton().executeAction(compUi);
			}
			break;
		// case KeyEvent.VK_F8:
		// new BotaoCompilar().executeAction(compUi);
		// break;
		// case KeyEvent.VK_F9:
		// new BotaoGerarCodigo().executeAction(compUi);
		// break;
		case KeyEvent.VK_F1:
			new TeamButton().executeAction(compUi);
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
