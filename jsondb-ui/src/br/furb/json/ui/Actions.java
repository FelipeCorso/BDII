package br.furb.json.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.tree.DefaultMutableTreeNode;

import br.furb.json.ui.dialog.DatabaseDialog;
import br.furb.json.ui.panel.command.CommandPanel;
import br.furb.json.ui.panel.treemenu.DatabasesTreeManager;
import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.JsonDBProperty;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.utils.JsonUtils;
import br.furb.jsondb.utils.StringUtils;
import br.furb.jsondb.utils.ui.UIUtils;

public final class Actions {

	private static int BASELESS_SCRIPTS;

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

	/**
	 * Seleciona todo o texto do editor, se houver uma aba ativa.
	 * 
	 * @param principal
	 *            janela principal do compilador, na qual são exibidas janelas
	 *            modais e tratados erros ocorridos na execução das ações.
	 */
	public static void selectAll(Principal principal) {
		CommandPanel commandPanel = principal.getActiveCommandPanel();
		if (commandPanel != null) {
			JTextArea textEditor = commandPanel.getTextEditor();
			textEditor.setSelectionStart(0);
			textEditor.setSelectionEnd(textEditor.getText().length());
		}
	}

	public static void executeScript(Principal principal) {
		CommandPanel commandPanel = principal.getActiveCommandPanel();
		JTextArea textMsg = commandPanel.getTextMsg();
		textMsg.setText(StringUtils.EMPTY_STR);
		try {
			List<IResult> results = JsonDB.executeSQL(commandPanel.getTextEditor().getText());
			for (IResult result : results) {
				if (result == null) {
					textMsg.append("Comando não suportado");
				} else {
					result.getMessages().forEach(message -> textMsg.append(message));
					principal.updateDatabasesTree();
				}
				textMsg.append("\n");
			}
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

			DatabasesTreeManager.createNodesDatabase(dataBaseNode, database);

			((javax.swing.tree.DefaultTreeModel) principal.getTreeMenu().getTree().getModel()).reload(DatabasesTreeManager.sort(dataBaseNode));

			CommandPanel activeCommandPanel = principal.getActiveCommandPanel();
			activeCommandPanel.getTextEditor().setText("");
			activeCommandPanel.getTextMsg().setText("");
			activeCommandPanel.setModified(false);
		}
	}

	/**
	 * Elimina a base selecionada.<br>
	 * Uma janela de confirmação é exibida antes de efetuar a eliminação.<br>
	 * Ao ser eliminada a base, todos os documentos associados serão fechados.
	 * Para tanto, antes de efetivar o comando, uma mensagem alerta o usuário
	 * para que salve os documentos associados antes de continuar.
	 * 
	 * @param principal
	 *            janela principal do compilador, na qual são exibidas janelas
	 *            modais e tratados erros ocorridos na execução das ações.
	 */
	public static void dropDatabase(Principal principal) {
		// TODO
	}

	/**
	 * Cria uma nova aba de código.
	 * 
	 * @param principal
	 *            janela principal do compilador, na qual são exibidas janelas
	 *            modais e tratados erros ocorridos na execução das ações.
	 */
	public static void newScript(Principal principal) {
		principal.addCommandPanel(new CommandPanel("Script sem base " + ++BASELESS_SCRIPTS, principal));
	}

	/**
	 * Cria uma nova aba de código e faz com que todos os comandos executados
	 * por esta aba sejam dirijidos a base selecionada no momento de sua
	 * criação.
	 * 
	 * @param principal
	 *            janela principal do compilador, na qual são exibidas janelas
	 *            modais e tratados erros ocorridos na execução das ações.
	 */
	public static void newScriptForBase(Principal principal) {
		// TODO
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
				DatabasesTreeManager.createNodesDatabase(dataBaseNode, database);

				((javax.swing.tree.DefaultTreeModel) principal.getTreeMenu().getTree().getModel()).reload(DatabasesTreeManager.sort(dataBaseNode));
			}
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível realizar a leitura do arquivo: " + e.getMessage());
		}
	}

	/**
	 * Fecha a aba de código ativa.
	 * 
	 * @param principal
	 *            janela principal do compilador, na qual são exibidas janelas
	 *            modais e tratados erros ocorridos na execução das ações.
	 */
	public static void closeActiveTab(Principal principal) {
		CommandPanel commandPanel = principal.getActiveCommandPanel();
		if (commandPanel != null) {
			closeTab(principal, commandPanel);
		}
	}

	/**
	 * Fecha a aba de código informada.
	 * 
	 * @param principal
	 *            janela principal do compilador, na qual são exibidas janelas
	 *            modais e tratados erros ocorridos na execução das ações.
	 * @param commandPanel
	 *            documento a ser fechado.
	 * @return {@code true} se o documento foi fechado. {@code false} se a ação
	 *         foi cancelada.
	 */
	public static boolean closeTab(Principal principal, CommandPanel commandPanel) {
		if (commandPanel.isModified()) {
			int confirmation = canSaveDocument(principal, commandPanel);
			if (confirmation == JOptionPane.YES_OPTION) {
				try {
					if (!saveDocument(principal, commandPanel, false)) {
						return false;
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			} else if (confirmation != JOptionPane.NO_OPTION) {
				return false;
			}
		}
		principal.closeCommandPanel(commandPanel);
		return true;
	}

	/**
	 * Solicita um arquivo para o usuário, o qual será aberto em uma nova aba.
	 * 
	 * @param principal
	 *            janela principal do compilador, na qual são exibidas janelas
	 *            modais e tratados erros ocorridos na execução das ações.
	 */
	public static void openScript(Principal principal) {
		File suggestedFile = principal.getActiveCommandPanel() != null ? principal.getActiveCommandPanel().getCorrespondingFile() : new File(JsonDBProperty.JSON_DB_DIR.get());
		File file;
		boolean fileIsValid = false;
		do {
			JFileChooser openDialog = new JFileChooser(suggestedFile);
			if (openDialog.showOpenDialog(principal) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			file = openDialog.getSelectedFile();
			fileIsValid = file != null && file.isFile();
			if (!fileIsValid) {
				principal.handleUIException("Informe um arquivo existente.");
			}
		} while (!fileIsValid);

		StringBuilder documentContent = new StringBuilder();
		try (Scanner scanner = new Scanner(file)) {
			scanner.useDelimiter("$");
			scanner.forEachRemaining(line -> documentContent.append(line).append('\n'));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		CommandPanel document = new CommandPanel(getDocumentTitle(file), principal);
		document.setCorrespondingFile(file);
		document.getTextEditor().setText(documentContent.toString());
		document.setModified(false);
		principal.addCommandPanel(document);
	}

	/**
	 * Salva o documento ativo. Se não ainda tiver sido persistido, solicita ao
	 * usuário um arquivo para a gravação.
	 * 
	 * @param principal
	 *            janela principal do compilador, na qual são exibidas janelas
	 *            modais e tratados erros ocorridos na execução das ações.
	 */
	public static void saveScript(Principal principal) {
		CommandPanel commandPanel = principal.getActiveCommandPanel();
		if (commandPanel != null) {
			if (commandPanel.isModified()) {
				try {
					saveDocument(principal, commandPanel, false);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * Salva o documento ativo com um novo nome.
	 * 
	 * @param principal
	 *            janela principal do compilador, na qual são exibidas janelas
	 *            modais e tratados erros ocorridos na execução das ações.
	 */
	public static void saveScriptAs(Principal principal) {
		CommandPanel commandPanel = principal.getActiveCommandPanel();
		if (commandPanel != null) {
			try {
				saveDocument(principal, commandPanel, true);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Confirma se o usuário realmente deseja sair, abortando a ação em caso
	 * negativo.<br>
	 * Do contrário, verifica se existe algum documento modificado, e para cada
	 * um encontrado, questiona o usuário se deseja salvar.<br>
	 * Após ter salvo ou descartado todos os documentos, sai da aplicação.
	 * 
	 * @param principal
	 *            janela principal do compilador, na qual são exibidas janelas
	 *            modais e tratados erros ocorridos na execução das ações.
	 */
	public static void exit(Principal principal) {
		if (!UIUtils.promptConfirmation(principal, "Tem ceteza de que deseja fechar todos os documentos e sair?", "Sair")) {
			return;
		}
		for (CommandPanel document : principal.getCommandPanels()) {
			if (!closeTab(principal, document)) {
				return;
			}
		}
		principal.exit();
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
		String message = String.format("O documento \"%s\" possui alterações não salvas.\nDeseja salvá-las antes de continuar?", commandPanel.getTitle());
		return UIUtils.promptConfirmationWithAbortion(principal, message, "Confirmação");
	}

	/**
	 * Salva o documento no respectivo arquivo, questionando o usuário por um,
	 * caso ainda não exista.<br>
	 * É possível informar um novo arquivo mesmo que o documento já possua um
	 * arquivo associado.
	 * 
	 * @param principal
	 *            janela principal do compilador, na qual são exibidas janelas
	 *            modais e tratados erros ocorridos na execução das ações.
	 * @param commandPanel
	 *            documento a ser salvo.
	 * @param askNewLocation
	 *            {@code true} se o usuário pode informar um novo arquivo para
	 *            salvar o documento, {@code false} se o usuário só deve ser
	 *            questionado caso o documento ainda não possua um documento
	 *            associado.
	 * @return {@code true} se o documento foi salvo e associado a um arquivo,
	 *         {@code false} se ocorreu algum erro ou o usuário cancelou a
	 *         operação.
	 * @throws IOException
	 *             caso ocorra algum erro ao persistir o documento.
	 */
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
		commandPanel.setTitle(getDocumentTitle(file));
		return true;
	}

	private static String getDocumentTitle(File document) {
		return document.getName().replaceFirst("(?i)\\.sql$", "");
	}

}
