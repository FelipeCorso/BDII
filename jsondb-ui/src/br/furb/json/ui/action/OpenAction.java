package br.furb.json.ui.action;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import br.furb.json.ui.Principal;
import br.furb.json.ui.status.EStatus;

public class OpenAction {
	public static void executeAction(Principal principal) {
		String filePath = "";
		FileDialog fileDialog = new FileDialog(principal, "Abrir", FileDialog.LOAD);
		fileDialog.setDirectory("C:\\");
		fileDialog.setVisible(true);

		filePath = fileDialog.getDirectory() + fileDialog.getFile();

		if (!filePath.equalsIgnoreCase("C:\\null")) {
			try {
				// 1º tenta ler depois seta o caminho do arquivo e as demais
				// informações
				principal.getCommandPanel().getTextEditor().setText(textFileRead(filePath));
				principal.getCommandPanel().getLbFilePath().setText(filePath);
				principal.getKeyListener().setTextoEditor("");
				principal.getCommandPanel().getTextMsg().setText("");
				principal.getCommandPanel().getLbStatus().setText(EStatus.NAO_MODIFICADO.toString());
				principal.getKeyListener().setTextoEditor(principal.getCommandPanel().getTextEditor().getText());
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("ERRO FATAL!\nNão foi possível realizar a leitura do arquivo!");
				e.printStackTrace();
			}

		}
	}

	private static String textFileRead(String fileName) throws ClassNotFoundException, IOException {
		FileReader fr = new FileReader(fileName);
		StringBuilder sb = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(fr);

		int c = 0;
		while ((c = bufferedReader.read()) != -1) {
			sb.append((char) c);
		}

		bufferedReader.close();
		fr.close();

		return sb.toString();
	}

}
