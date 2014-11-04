package br.furb.json.ui.panel.treeMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import br.furb.json.ui.Principal;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.utils.JsonUtils;

public class TreeMenuPanel extends JPanel {

	private static final String DATA_BASE_STR = "DataBase";

	private static final long serialVersionUID = 8080924607252341731L;

	protected static final String TABLES_STR = "Tabelas";
	protected static final String INDEX_STR = "Índices";

	private DefaultMutableTreeNode dataBaseNode;

	public TreeMenuPanel(Principal principal) {
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		defineLookAndFeel();
		setLayout(new BorderLayout(0, 0));

		JPanel panelBotoes = new JPanel();
		add(panelBotoes, BorderLayout.NORTH);
		panelBotoes.setLayout(new GridLayout(0, 2, 0, 0));

		JScrollPane scrollPaneTree = new JScrollPane();
		add(scrollPaneTree, BorderLayout.CENTER);

		dataBaseNode = new DefaultMutableTreeNode(DATA_BASE_STR);
		JTree jTree = new JTree(new javax.swing.tree.DefaultTreeModel(dataBaseNode));
		jTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				TreePath selectionPath = jTree.getSelectionPath();

				if (selectionPath != null) {
					javax.swing.tree.DefaultMutableTreeNode dmt = (javax.swing.tree.DefaultMutableTreeNode) selectionPath.getLastPathComponent();

					if (!dmt.getUserObject().toString().equalsIgnoreCase(DATA_BASE_STR)) {
						if (dmt.getParent().toString().equalsIgnoreCase(DATA_BASE_STR)) {
							// é uma base, fazer select
							principal.getTabbedPanel().createTabDataBase(dmt.toString());
						} else {
							if (dmt.getParent().toString().equalsIgnoreCase(TABLES_STR)) {
								// é uma tabela, describe
							} else {
								if (dmt.getParent().toString().equalsIgnoreCase(TABLES_STR)) {
									// é um índice, apresentar nome, colunas
								}
							}
						}

					}
				}

			}
		});
		scrollPaneTree.setViewportView(jTree);

		JLabel lblAdd = new JLabel("Adicionar");
		lblAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					String databaseDir = loadDataBaseDir(principal);

					DatabaseMetadata database = JsonUtils.parseJsonToObject(new File(databaseDir), DatabaseMetadata.class);
					principal.addDataBase(database);
					lblAdd.toString();

					createNodesDatabase(dataBaseNode, database);

					((javax.swing.tree.DefaultTreeModel) jTree.getModel()).reload(sort(dataBaseNode));

				} catch (IOException io) {
					System.err.println("ERRO FATAL!\nNão foi possível realizar a leitura do arquivo!");
					io.printStackTrace();
				}
			}
		});

		lblAdd.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAdd.setIcon(principal.getImageIcon("Add folder.png"));
		panelBotoes.add(lblAdd);

		JLabel lblRemover = new JLabel("Remover");
		lblRemover.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblRemover.setIcon(principal.getImageIcon("Remove folder.png"));
		lblRemover.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TreePath selectionPath = jTree.getSelectionPath();

				if (selectionPath != null) {
					javax.swing.tree.DefaultMutableTreeNode dmt = (javax.swing.tree.DefaultMutableTreeNode) selectionPath.getLastPathComponent();

					if (!dmt.getUserObject().toString().equalsIgnoreCase(DATA_BASE_STR)) {
						//nodeChanged
						((javax.swing.tree.DefaultTreeModel) jTree.getModel()).removeNodeFromParent(dmt);
						principal.getTabbedPanel().remove(dmt.toString());
					}
				}
			}
		});

		panelBotoes.add(lblRemover);

	}

	public DefaultMutableTreeNode sort(DefaultMutableTreeNode node) {
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

	private void createNodesDatabase(DefaultMutableTreeNode top, DatabaseMetadata database) {
		DefaultMutableTreeNode databaseNode = new DefaultMutableTreeNode(database.getName());
		top.add(databaseNode);

		createNodesTable(databaseNode, database);
		createNodesIndex(databaseNode, database);
	}

	private void createNodesTable(DefaultMutableTreeNode databaseNode, DatabaseMetadata database) {
		DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode("Tabelas");
		databaseNode.add(tableNode);

		for (String tableName : database.getTables().keySet()) {
			DefaultMutableTreeNode table = new DefaultMutableTreeNode(tableName);
			tableNode.add(table);
		}

	}

	private void createNodesIndex(DefaultMutableTreeNode databaseNode, DatabaseMetadata database) {
		DefaultMutableTreeNode indexNode = new DefaultMutableTreeNode("Índices");
		databaseNode.add(indexNode);

		//		for (String indexName : database.getIndex().keySet()) {
		//			DefaultMutableTreeNode index = new DefaultMutableTreeNode(indexName);
		//			indexNode.add(index);
		//		}
	}

	private String loadDataBaseDir(Frame frame) {
		String filePath = "";

		FileDialog fileDialog = new FileDialog(frame, "Abrir", FileDialog.LOAD);
		fileDialog.setDirectory("C:\\");
		fileDialog.setVisible(true);

		filePath = fileDialog.getDirectory() + fileDialog.getFile();

		if (!filePath.equalsIgnoreCase("C:\\null")) {
			// try {
			// // 1º tenta ler depois seta o caminho do arquivo e as demais
			// informações
			// frame.getTextEditor().setText(textFileRead(filePath));
			// frame.getLbFilePath().setText(filePath);
			// frame.getKeyListener().setTextoEditor("");
			// frame.getTextMsg().setText("");
			// frame.getLbStatus().setText(EStatus.NAO_MODIFICADO.toString());
			// frame.getKeyListener().setTextoEditor(frame.getTextEditor().getText());
			// } catch (ClassNotFoundException | IOException e) {
			// System.err.println("ERRO FATAL!\nNão foi possível realizar a leitura do arquivo!");
			// e.printStackTrace();
			// }
			return filePath;
		}
		return "";
	}

	private static boolean useSystemLookAndFeel = true;

	private static void defineLookAndFeel() {
		if (useSystemLookAndFeel) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				System.err.println("Couldn't use system look and feel.");
			}
		}
	}

}
