package br.furb.json.ui.dialog;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import br.furb.json.ui.Principal;
import br.furb.json.ui.fileFilter.FileFilter;
import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.sql.SQLException;

public class Dialog extends JFileChooser {

	private static final String CREATE_FOLDER_DB = "Crie uma pasta para a nova database";
	private static final long serialVersionUID = 1213370325572949237L;
	private static final String CREATE_ERROR = "Este diret�rio j� cont�m um arquivo de meta dados!";
	private static final String CREATE_DATABASE = "CREATE DATABASE %s;";
	private static Dialog intance = new Dialog();

	private Dialog() {
		super("C://"); // FIXME: informar programaticamente o root (existe algum modo simples pelo Java)
		setFileFilter(FileFilter.getFileFilter());
		setAcceptAllFileFilterUsed(false);
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	public static Dialog getInstance() {
		return intance;
	}

	public String loadDatabaseDir(Principal principal) {
		showOpenDialog(principal);

		File selectedFile = getSelectedFile();

		if (selectedFile.isDirectory()) {
			File metadata = getMetadataFile(selectedFile);
			if (metadata != null) {
				return metadata.getAbsolutePath();
			}
		}
		return null;
	}

	public String createDatabaseDir(Principal principal) throws IOException, SQLParserException, SQLException {
		setDialogType(JFileChooser.SAVE_DIALOG);
		setDialogTitle(CREATE_FOLDER_DB);
		showOpenDialog(principal);

		File selectedFile = getSelectedFile();

		if (selectedFile.isDirectory()) {
			File metadata = getMetadataFile(selectedFile);
			if (metadata != null) {
				throw new IOException(CREATE_ERROR);
			}
			JsonDB.getInstance().executeSQL(String.format(CREATE_DATABASE, selectedFile.getName()));
		}

		return null;

	}

	private File getMetadataFile(File databaseFolder) {
		File[] listFiles = databaseFolder.listFiles(new FileFilter());
		if (listFiles.length > 0) {
			return listFiles[0];
		}
		return null;
	}

	//	JFileChooser fileChooser = new JFileChooser("C://");
	//			fileChooser.setFileFilter(FileFilter.getFileFilter());
	//	fileChooser.setAcceptAllFileFilterUsed(false);
	//	fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	//	fileChooser.showOpenDialog(frame);
	//
	//	File selectedFile = fileChooser.getSelectedFile();
	//
	//	if (selectedFile.isDirectory()) {
	//		File[] listFiles = selectedFile.listFiles(new FileFilter());
	//		if (listFiles.length > 0) {
	//			return listFiles[0].getAbsolutePath();
	//		}
	//	}

}
