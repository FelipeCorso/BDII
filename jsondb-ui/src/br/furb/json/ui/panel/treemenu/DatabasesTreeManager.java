package br.furb.json.ui.panel.treemenu;

import java.util.Map.Entry;

import javax.swing.tree.DefaultMutableTreeNode;

import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.IndexMetadata;
import br.furb.jsondb.store.metadata.TableMetadata;

public class DatabasesTreeManager {

	public static void createNodesDatabase(DefaultMutableTreeNode top, DatabaseMetadata database) {
		DefaultMutableTreeNode databaseNode = new DefaultMutableTreeNode(database.getName());
		top.add(databaseNode);

		createNodesTable(databaseNode, database);
	}

	public static void createNodesTable(DefaultMutableTreeNode databaseNode, DatabaseMetadata database) {
		DefaultMutableTreeNode tableRootNode = new DefaultMutableTreeNode("Tabelas");
		databaseNode.add(tableRootNode);

		for (Entry<String, TableMetadata> tableEntry : database.getTables().entrySet()) {
			DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(tableEntry.getKey());
			tableRootNode.add(tableNode);
			createNodesIndex(tableNode, tableEntry.getValue());
		}
	}

	public static void createNodesIndex(DefaultMutableTreeNode databaseNode, TableMetadata tableMetadata) {
		DefaultMutableTreeNode indexRootNode = new DefaultMutableTreeNode("Índices");
		databaseNode.add(indexRootNode);

		for (IndexMetadata index : tableMetadata.getIndexes()) {
			DefaultMutableTreeNode indexNode = new DefaultMutableTreeNode(index.getName());
			indexRootNode.add(indexNode);
		}
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
