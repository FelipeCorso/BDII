package br.furb.json.ui.shortcut;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import br.furb.json.ui.Actions;
import br.furb.json.ui.Principal;

public class ShortCutListener extends KeyAdapter {

	private Principal compUi;

	public ShortCutListener(Principal compUi) {
		this.compUi = compUi;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void keyPressed(KeyEvent event) {
		boolean isCtrlDown = event.isControlDown();
		switch (event.getKeyCode()) {
//		case KeyEvent.VK_N:
//			if (isCtrlDown) {
//				compUi.doSafely(Actions::newDatabase);
//			}
//			break;
//		case KeyEvent.VK_A:
//			if (isCtrlDown) {
//				compUi.doSafely(Actions::openDatabase);
//			}
//			break;
//		case KeyEvent.VK_S:
//			if (isCtrlDown) {
//				compUi.doSafely(Actions::saveScript);
//			}
//			break;
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
//		case KeyEvent.VK_W:
//			if (isCtrlDown) {
//				compUi.doSafely(Actions::closeTab);
//			}
//			break;
		// case KeyEvent.VK_F8:
		// new BotaoCompilar().executeAction(compUi);
		// break;
		// case KeyEvent.VK_F9:
		// new BotaoGerarCodigo().executeAction(compUi);
		// break;
		case KeyEvent.VK_F1:
			compUi.doSafely(Actions::showTeam);
			break;
		}
	}

}
