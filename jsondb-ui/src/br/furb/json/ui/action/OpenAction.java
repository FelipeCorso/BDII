package br.furb.json.ui.action;

import java.io.File;
import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

import br.furb.json.ui.Principal;
import br.furb.json.ui.dialog.Dialog;
import br.furb.json.ui.panel.treeMenu.ManagerTreeMenu;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.utils.JsonUtils;

public class OpenAction {

	public static void executeAction(Principal principal) {
		try {
			String databaseDir = Dialog.getInstance().loadDatabaseDir(principal);

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

}
