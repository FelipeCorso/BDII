package br.furb.json.ui.dialog;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import br.furb.json.ui.Principal;
import br.furb.json.ui.fileFilter.MetadataFileFilter;
import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.utils.ui.UIUtils;

public class Dialog extends JFileChooser {

	private static final long serialVersionUID = -2748601639233642085L;
	private static final String CREATE_FOLDER_DB = "Crie uma pasta para a nova database";
	private static final String CREATE_ERROR = "Este diretório já contém um arquivo de metadados!";
	private static final String CREATE_DATABASE = "CREATE DATABASE %s;";
	private static Dialog intance = new Dialog();

	private Dialog() {
		super("C://"); // FIXME: informar programaticamente o root (existe algum modo simples pelo Java)
		setFileFilter(MetadataFileFilter.FILE_FILTER); // FIXME: parece não estar sendo invocado oO
		setAcceptAllFileFilterUsed(false);
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	public static Dialog getInstance() {
		return intance;
	}

	public String loadDatabaseDir(Principal principal) {
		showOpenDialog(principal);

		File selectedFile = getSelectedFile();

		if (selectedFile != null && selectedFile.isDirectory()) {
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
		if (showOpenDialog(principal) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = getSelectedFile();

			if (selectedFile.isDirectory()) {
				File metadata = getMetadataFile(selectedFile);
				if (metadata != null) {
					throw new IOException(CREATE_ERROR);
				}
				JsonDB.getInstance().executeSQL(String.format(CREATE_DATABASE, selectedFile.getName()));
			} else {
				UIUtils.showMessage(principal, "O diretório informado não existe.", "JsonDB", JOptionPane.ERROR_MESSAGE);
			}
		}

		return null;

	}

	private static File getMetadataFile(File databaseFolder) {
		File[] listFiles = databaseFolder.listFiles(new MetadataFileFilter());
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
