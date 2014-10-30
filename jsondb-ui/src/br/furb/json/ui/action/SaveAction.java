package br.furb.json.ui.action;

import java.io.FileWriter;
import java.io.IOException;

public class SaveAction {

	public static void salvar(String absolutePath, String buffer) throws IOException {
		FileWriter fw = new FileWriter(absolutePath, false);
		fw.write(buffer);
		fw.flush();
		fw.close();
	}
}
