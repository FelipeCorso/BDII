package br.furb.json.ui;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

import br.furb.json.ui.dialog.DatabaseDialog;
import br.furb.json.ui.panel.treeMenu.ManagerTreeMenu;
import br.furb.json.ui.status.EStatus;
import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.utils.JsonUtils;

public final class Actions {

	private Actions() {
	}

	public static void showTeam(Principal principal) {
		// TODO: mostrar em uma modal
		principal.getTabbedPanel().getCommandPanel().getTextMsg().setText("Integrantes Equipe: Felipe Loose Corso, Janaína Carraro Mendonça Lima, William Leander Seefeld\n");
	}

	public static void copy(Principal principal) {
		// FIXME: copiar só a seleção
		principal.getTabbedPanel().getCommandPanel().getTextEditor().copy();
	}

	public static void cut(Principal principal) {
		// FIXME: recortar só a seleção
		principal.getTabbedPanel().getCommandPanel().getTextEditor().cut();
		principal.getTabbedPanel().getCommandPanel().getLbStatus().setText(EStatus.MODIFICADO.toString());
	}

	public static void executeScript(Principal principal) {
		try {
			IResult result = JsonDB.getInstance().executeSQL(principal.getTabbedPanel().getCommandPanel().getTextEditor().getText());
			result.getMessages().forEach(message -> principal.getTabbedPanel().getCommandPanel().getTextMsg().append(message));

		} catch (SQLParserException e) {
			System.err.println("Não foi possível executar o comando: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Não foi possível executar o comando: " + e.getMessage());
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

			principal.getTabbedPanel().getCommandPanel().getTextEditor().setText("");
			principal.getTabbedPanel().getCommandPanel().getTextMsg().setText("");
			principal.getTabbedPanel().getCommandPanel().getLbFilePath().setText("");
			principal.getKeyListener().setTextoEditor("");
			principal.getTabbedPanel().getCommandPanel().getLbStatus().setText(EStatus.NAO_MODIFICADO.toString());
		}
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

	public static void paste(Principal principal) {
		principal.getTabbedPanel().getCommandPanel().getTextEditor().paste();
		principal.getTabbedPanel().getCommandPanel().getLbStatus().setText(EStatus.MODIFICADO.toString());
	}

	public static void saveScript(Principal principal) {
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
					Actions.saveFile(absolutePath, principal.getTabbedPanel().getCommandPanel().getTextEditor().getText());
					principal.getTabbedPanel().getCommandPanel().getLbFilePath().setText(absolutePath);
					principal.getTabbedPanel().getCommandPanel().getTextMsg().setText("");
					principal.getTabbedPanel().getCommandPanel().getLbStatus().setText(EStatus.NAO_MODIFICADO.toString());
				}
			} catch (IOException e) {
				throw new RuntimeException("Não foi possível salvar o arquivo: \n\t" + e.getMessage());
			}
		}
	}

	public static void saveFile(String absolutePath, String buffer) throws IOException {
		try (FileWriter fw = new FileWriter(absolutePath, false)) {
			fw.write(buffer);
			fw.flush();
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
