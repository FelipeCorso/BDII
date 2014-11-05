package br.furb.json.ui.action;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.FileWriter;
import java.io.IOException;

import br.furb.json.ui.Principal;
import br.furb.json.ui.status.EStatus;

public class SaveAction {
	public static void executeAction(Principal principal) {
		if (principal.getTabbedPanel().getCommandPanel().getLbStatus().getText().equalsIgnoreCase(EStatus.MODIFICADO.toString())) {
			String absolutePath = principal.getTabbedPanel().getCommandPanel().getLbFilePath().getText();

			if (absolutePath.isEmpty()) {
				// FIXME AJUSTAR
				//				Dialog dialog = new Dialog(principal, "Informe o diretório e o nome do arquivo", FileDialog.SAVE);
				//
				//				dialog.abrirFrame();
				//				absolutePath = dialog.getAbsolutePath();
			}

			try {
				if (!absolutePath.equalsIgnoreCase("C:\\null")) {
					SaveAction.salvar(absolutePath, principal.getTabbedPanel().getCommandPanel().getTextEditor().getText());
					principal.getTabbedPanel().getCommandPanel().getLbFilePath().setText(absolutePath);
					principal.getTabbedPanel().getCommandPanel().getTextMsg().setText("");
					principal.getTabbedPanel().getCommandPanel().getLbStatus().setText(EStatus.NAO_MODIFICADO.toString());
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

	public static void salvar(String absolutePath, String buffer) throws IOException {
		FileWriter fw = new FileWriter(absolutePath, false);
		fw.write(buffer);
		fw.flush();
		fw.close();
	}
}
