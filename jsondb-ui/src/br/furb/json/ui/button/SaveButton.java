package br.furb.json.ui.button;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.IOException;

import javax.swing.JButton;

import br.furb.json.ui.Principal;
import br.furb.json.ui.action.Action;
import br.furb.json.ui.action.SaveAction;
import br.furb.json.ui.status.EStatus;

public class SaveButton extends JButton implements Action {
	private static final long serialVersionUID = 1L;

	public SaveButton() {
		super();
	}

	public SaveButton(String texto) {
		super(texto);
	}

	@Override
	public void executeAction(Principal principal) {
		if (principal.getCommandPanel().getLbStatus().getText().equalsIgnoreCase(EStatus.MODIFICADO.toString())) {
			String absolutePath = principal.getCommandPanel().getLbFilePath().getText();

			if (absolutePath.isEmpty()) {
				Dialog dialog = new Dialog(principal, "Informe o diretório e o nome do arquivo", FileDialog.SAVE);

				dialog.abrirFrame();
				absolutePath = dialog.getAbsolutePath();
			}

			try {
				if (!absolutePath.equalsIgnoreCase("C:\\null")) {
					SaveAction.salvar(absolutePath, principal.getCommandPanel().getTextEditor().getText());
					principal.getCommandPanel().getLbFilePath().setText(absolutePath);
					principal.getCommandPanel().getTextMsg().setText("");
					principal.getCommandPanel().getLbStatus().setText(EStatus.NAO_MODIFICADO.toString());
				}
			} catch (IOException e) {
				System.err.println("ERRO FATAL!\nNão foi possível salvar o arquivo!");
				e.printStackTrace();
			}
		}
	}

	private class Dialog extends FileDialog {

		public Dialog(Frame parent, String title, int mode) {
			super(parent, title, mode);
		}

		private static final long serialVersionUID = 1L;
		private String absolutePath;

		public void abrirFrame() {
			this.setDirectory("C:\\");
			this.setVisible(true);

			absolutePath = this.getDirectory() + this.getFile();
		}

		public String getAbsolutePath() {
			return absolutePath;
		}
	}
}
