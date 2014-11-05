package br.furb.json.ui.panel.treeMenu;

import javax.swing.tree.DefaultMutableTreeNode;

import br.furb.jsondb.store.metadata.DatabaseMetadata;

public class ManagerTreeMenu {
	public static void createNodesDatabase(DefaultMutableTreeNode top, DatabaseMetadata database) {
		DefaultMutableTreeNode databaseNode = new DefaultMutableTreeNode(database.getName());
		top.add(databaseNode);

		createNodesTable(databaseNode, database);
		createNodesIndex(databaseNode, database);
	}

	public static void createNodesTable(DefaultMutableTreeNode databaseNode, DatabaseMetadata database) {
		DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode("Tabelas");
		databaseNode.add(tableNode);

		for (String tableName : database.getTables().keySet()) {
			DefaultMutableTreeNode table = new DefaultMutableTreeNode(tableName);
			tableNode.add(table);
		}
	}

	public static void createNodesIndex(DefaultMutableTreeNode databaseNode, DatabaseMetadata database) {
		DefaultMutableTreeNode indexNode = new DefaultMutableTreeNode("Índices");
		databaseNode.add(indexNode);

		//		for (String indexName : database.getIndex().keySet()) {
		//			DefaultMutableTreeNode index = new DefaultMutableTreeNode(indexName);
		//			indexNode.add(index);
		//		}
	}

	public static DefaultMutableTreeNode sort(DefaultMutableTreeNode node) {
		//sort alphabetically
		for (int i = 0; i < node.getChildCount() - 1; i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			String nt = child.getUserObject().toString();

			for (int j = i + 1; j <= node.getChildCount() - 1; j++) {
				DefaultMutableTreeNode prevNode = (DefaultMutableTreeNode) node.getChildAt(j);
				String np = prevNode.getUserObject().toString();

				System.out.println(nt + " " + np);
				if (nt.compareToIgnoreCase(np) > 0) {
					node.insert(child, j);
					node.insert(prevNode, i);
				}
			}
			if (child.getChildCount() > 0) {
				sort(child);
			}
		}

		return node;
	}
}
