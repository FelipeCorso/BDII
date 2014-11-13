package br.furb.json.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.tree.DefaultMutableTreeNode;

import br.furb.json.ui.dialog.DatabaseDialog;
import br.furb.json.ui.panel.command.CommandPanel;
import br.furb.json.ui.panel.treeMenu.ManagerTreeMenu;
import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.utils.JsonUtils;
import br.furb.jsondb.utils.StringUtils;
import br.furb.jsondb.utils.ui.UIUtils;

public final class Actions {

	private static int NO_BASE_SCRIPTS;

	private Actions() {
	}

	public static void changeWorkingDir(Principal principal) {
		File newDir = DatabaseDialog.getInstance().changeWorkingDir(principal, principal.getWorkingDir());
		if (newDir != null) {
			principal.setWorkingDir(newDir);
		}
	}

	public static void showTeam(Principal principal) {
		// TODO: mostrar em uma modal
		CommandPanel commandPanel = principal.getActiveCommandPanel();
		commandPanel.getTextMsg().setText("Integrantes Equipe: Felipe Loose Corso, Janaína Carraro Mendonça Lima, William Leander Seefeld\n");
	}

	public static void copy(Principal principal) {
		CommandPanel commandPanel = principal.getActiveCommandPanel();
		if (commandPanel != null) {
			commandPanel.getTextEditor().copy();
		}
	}

	public static void cut(Principal principal) {
		CommandPanel commandPanel = principal.getActiveCommandPanel();
		if (commandPanel != null) {
			commandPanel.getTextEditor().cut();
			commandPanel.setModified(true);
		}
	}

	public static void paste(Principal principal) {
		CommandPanel commandPanel = principal.getActiveCommandPanel();
		if (commandPanel != null) {
			commandPanel.getTextEditor().paste();
			commandPanel.setModified(true);
		}
	}

	public static void executeScript(Principal principal) {
		CommandPanel commandPanel = principal.getActiveCommandPanel();
		JTextArea textMsg = commandPanel.getTextMsg();
		textMsg.setText(StringUtils.EMPTY_STR);
		try {
			IResult result = JsonDB.getInstance().executeSQL(commandPanel.getTextEditor().getText());
			result.getMessages().forEach(message -> textMsg.append(message));
		} catch (SQLParserException e) {
			textMsg.setText(e.getCause().getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			textMsg.setText(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void newDatabase(Principal principal) {
		DefaultMutableTreeNode dataBaseNode;
		DatabaseMetadata database;
		try {
			String databaseDir = DatabaseDialog.getInstance().createDatabaseDir(principal);
			if (databaseDir == null) {
				return;
			}
			dataBaseNode = principal.getTreeMenu().getDataBaseNode();
			database = JsonUtils.parseJsonToObject(new File(databaseDir), DatabaseMetadata.class);
		} catch (IOException | SQLParserException | SQLException e) {
			throw new RuntimeException(e);
		}

		if (!principal.getDatabases().containsKey(database.getName())) {
			principal.addDataBase(database);

			ManagerTreeMenu.createNodesDatabase(dataBaseNode, database);

			((javax.swing.tree.DefaultTreeModel) principal.getTreeMenu().getjTree().getModel()).reload(ManagerTreeMenu.sort(dataBaseNode));

			CommandPanel activeCommandPanel = principal.getActiveCommandPanel();
			activeCommandPanel.getTextEditor().setText("");
			activeCommandPanel.getTextMsg().setText("");
			activeCommandPanel.setModified(false);
		}
	}

	/**
	 * Cria uma nova aba de código
	 * 
	 * @param principal
	 */
	public static void newScript(Principal principal) {
		principal.addCommandPanel(new CommandPanel("Script sem base " + ++NO_BASE_SCRIPTS, principal));
	}

	public static void openDatabase(Principal principal) {
		try {
			String databaseDir = DatabaseDialog.getInstance().loadDatabaseDir(principal);
			if (databaseDir == null) {
				return;
			}

			DatabaseMetadata database = JsonUtils.parseJsonToObject(new File(databaseDir), DatabaseMetadata.class);

			if (!principal.getDatabases().containsKey(database.getName())) {
				principal.addDataBase(database);

				DefaultMutableTreeNode dataBaseNode = principal.getTreeMenu().getDataBaseNode();
				ManagerTreeMenu.createNodesDatabase(dataBaseNode, database);

				((javax.swing.tree.DefaultTreeModel) principal.getTreeMenu().getjTree().getModel()).reload(ManagerTreeMenu.sort(dataBaseNode));
			}
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível realizar a leitura do arquivo: " + e.getMessage());
		}
	}

	/**
	 * Fecha a aba de código informada.
	 * 
	 * @param principal
	 *            janela principal do compilador, na qual são exibidas janelas
	 *            modais e tratados erros ocorridos na execução das ações.
	 */
	public static void closeTab(Principal principal) {
		CommandPanel commandPanel = principal.getActiveCommandPanel();
		if (commandPanel != null) {
			if (commandPanel.isModified()) {
				int confirmation = canSaveDocument(principal, commandPanel);
				if (confirmation == JOptionPane.YES_OPTION) {
					try {
						saveDocument(principal, commandPanel, false);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				} else if (confirmation == JOptionPane.CANCEL_OPTION) {
					return;
				}
			}
			principal.closeCommandPanel(commandPanel);
		}
	}

	/**
	 * Pergunta ao usuário se o documento informado pode ser salvo
	 * automaticamente.<br>
	 * Esta confirmação geralmente é necessária quando o usuário solicitou uma
	 * ação que exige que o documento esteja salvo, mas o mesmo se encontra
	 * modificado.
	 * 
	 * @param principal
	 *            janela principal do compilador, na qual será exibida a janela
	 *            de confirmação.
	 * @param commandPanel
	 *            documento a ser salvo.
	 * @return {@link JOptionPane#YES_OPTION} caso o usuário deseje salvar o
	 *         documento;<br>
	 *         {@link JOptionPane#NO_OPTION} caso o usuário deseje continuar sem
	 *         salvar o documento;<br>
	 *         {@link JOptionPane#CANCEL_OPTION} caso o usuário deseje abortar a
	 *         ação.
	 * 
	 */
	public static int canSaveDocument(Principal principal, CommandPanel commandPanel) {
		String message = String.format("O documento \"%s\" possui alterações não salvas. Deseja salvá-las antes de continuar?", commandPanel.getTitle());
		return UIUtils.promptConfirmationWithAbortion(principal, message, "Confirmação");
	}

	public static boolean saveDocument(Principal principal, CommandPanel commandPanel, boolean askNewLocation) throws IOException {
		File file = commandPanel.getCorrespondingFile();
		if (askNewLocation || file == null) {
			File suggestedFile = file != null ? file : new File(commandPanel.getTitle() + ".sql");
			boolean fileIsValid = false;
			do {
				JFileChooser saveDialog = new JFileChooser(suggestedFile);
				if (saveDialog.showSaveDialog(principal) != JFileChooser.APPROVE_OPTION) {
					return false;
				}
				file = saveDialog.getSelectedFile();
				fileIsValid = file != null && !file.isDirectory();
				if (!fileIsValid) {
					principal.handleUIException("Informe um arquivo.");
				}
			} while (!fileIsValid);
		}
		file = file.getAbsoluteFile();
		try (FileWriter fw = new FileWriter(file, false)) {
			fw.write(commandPanel.getTextEditor().getText());
			fw.flush();
		}
		commandPanel.setModified(false);
		commandPanel.setCorrespondingFile(file);
		commandPanel.setTitle(file.getName().replaceFirst("(?i)\\.sql$", ""));
		return true;
	}

	public static void saveScript(Principal principal) {
		CommandPanel commandPanel = principal.getActiveCommandPanel();
		if (commandPanel.isModified()) {
			try {
				saveDocument(principal, commandPanel, false);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void saveFile(String absolutePath, String buffer) throws IOException {
		try (FileWriter fw = new FileWriter(absolutePath, false)) {
			fw.write(buffer);
			fw.flush();
		}
	}

}
