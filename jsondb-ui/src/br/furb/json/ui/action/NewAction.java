package br.furb.json.ui.action;

import java.io.File;
import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

import br.furb.json.ui.Principal;
import br.furb.json.ui.dialog.Dialog;
import br.furb.json.ui.panel.treeMenu.ManagerTreeMenu;
import br.furb.json.ui.status.EStatus;
import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.utils.JsonUtils;

public class NewAction {

	public static void executeAction(Principal principal) {
		DefaultMutableTreeNode dataBaseNode;
		DatabaseMetadata database;
		try {
			String databaseDir = Dialog.getInstance().createDatabaseDir(principal);
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
}