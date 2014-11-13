package br.furb.json.ui.dialog;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import br.furb.json.ui.Principal;
import br.furb.json.ui.filefilter.MetadataFileFilter;
import br.furb.json.ui.filefilter.WorkDirFileFilter;
import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.JsonDBProperty;
import br.furb.jsondb.utils.ui.UIUtils;

/**
 * Diálogo de seleção de database, tanto criação quanto carregamento.
 */
public class DatabaseDialog extends JFileChooser {

	private static final long serialVersionUID = -2748601639233642085L;

	private static final String CREATE_FOLDER_DB = "Crie uma pasta para a nova database";
	private static final String CHANGE_WORKDIR_APPROVE_BUTTON = "Alterar";
	private static final String CHANGE_WORKDIR_TITLE = "Alterar pasta de trabalho";

	private static final String CREATE_ERROR = "Este diretório já contém um arquivo de metadados!";

	private static final String CREATE_DATABASE = "CREATE DATABASE %s;";

	private static DatabaseDialog INSTANCE = new DatabaseDialog();

	private DatabaseDialog() {
		super("C://"); // FIXME: informar programaticamente o root (existe algum modo simples pelo Java)
		setFileFilter(MetadataFileFilter.FILE_FILTER); // FIXME: parece não estar sendo invocado oO
		setAcceptAllFileFilterUsed(false);
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		setMultiSelectionEnabled(false);
	}

	public static DatabaseDialog getInstance() {
		return INSTANCE;
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

	/**
	 * Abre um diálogo de seleção de pastas posicionado na pasta atual.<br>
	 * Ao escolher:<br>
	 * - retorna a escolha se a mesma tiver a subpasta ".jsondb";<br>
	 * - questiona se deseja criar a pasta ".jsondb":<br>
	 * -- se confirmado: cria a pasta ".jsondb" e retorna a escolha<br>
	 * -- senão: permite que outra pasta seja escolhida<br>
	 * altera a pasta de trabalho do banco e resetar o que for preciso<br>
	 * 
	 * @param principal
	 *            janela principal a ser atualizada.
	 * @param currentDir
	 *            pasta de trabalho atual, a ser exibida ao abrir a janela de
	 *            seleção.
	 * @return nova pasta de trabalho ou {@code null}, caso o usuário tenha
	 *         desistido da alteração.
	 */
	public File changeWorkingDir(Principal principal, File currentDir) {
		// TODO: verificar se há arquivos não salvos e questionar o usuário
		setDialogType(JFileChooser.OPEN_DIALOG);
		setDialogTitle(CHANGE_WORKDIR_TITLE);
		setApproveButtonText(CHANGE_WORKDIR_APPROVE_BUTTON);

		setCurrentDirectory(currentDir != null ? currentDir : new File(JsonDBProperty.JSON_DB_DIR.get()));

		File newWorkDir = null;
		while (newWorkDir == null && showOpenDialog(principal) == JFileChooser.APPROVE_OPTION) {
			File chosenFile = getSelectedFile();
			if (WorkDirFileFilter.INSTANCE.accept(chosenFile)) {
				newWorkDir = chosenFile;
			} else if (!chosenFile.isDirectory()) {
				UIUtils.showMessage(principal, "Uma pasta existente deve ser escolhida.", "JsonDB", JOptionPane.ERROR_MESSAGE);
			} else if (UIUtils.promptConfirmation(principal, "A pasta informada não é uma pasta de trabalho do JsonDB. Deseja transformá-la em uma?", "Criar pasta de trabalho")) {
				try {
					JsonDB.setWorkingDir(chosenFile);
					newWorkDir = chosenFile;
				} catch (IllegalStateException ex) {
					UIUtils.showError(principal, ex);
				}
			}
		}

		return newWorkDir;
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
