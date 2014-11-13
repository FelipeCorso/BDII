package br.furb.json.ui.panel.treeMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.MatteBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import br.furb.json.ui.Principal;

public class TreeMenuPanel extends JPanel {

	private static final String DATA_BASE_STR = "DataBase";

	private static final long serialVersionUID = 8080924607252341731L;

	protected static final String TABLES_STR = "Tabelas";
	protected static final String INDEX_STR = "Índices";

	private DefaultMutableTreeNode dataBaseNode;

	private JTree jTree;

	public TreeMenuPanel(Principal principal) {
		setPreferredSize(new Dimension(150, 0));
		setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
		//		addKeyListener(principal.getKeyListener());

		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPaneTree = new JScrollPane();
		//		scrollPaneTree.addKeyListener(principal.getKeyListener());
		add(scrollPaneTree, BorderLayout.CENTER);

		dataBaseNode = new DefaultMutableTreeNode(DATA_BASE_STR);
		jTree = new JTree(new DefaultTreeModel(dataBaseNode));
		//		jTree.addKeyListener(principal.getKeyListener());
		jTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				TreePath selectionPath = jTree.getSelectionPath();

				if (selectionPath != null) {
					DefaultMutableTreeNode dmt = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();

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
		
		/*JPanel panelBotoes = new JPanel();
		//		panelBotoes.addKeyListener(principal.getKeyListener());
		add(panelBotoes, BorderLayout.NORTH);
		panelBotoes.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("center:50px"), ColumnSpec.decode("center:50px"), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50px;default)"), }, new RowSpec[] { RowSpec.decode("32px"), }));

		JButton btnNewDB = new JButton(new ImageIcon(TreeMenuPanel.class.getResource("/Images/Add folder.png")));
		btnNewDB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				principal.doSafely(Actions::newDatabase);
			}
		});
		btnNewDB.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnNewDB.setToolTipText("Nova Database [Ctrl+N]");
		//		lblNewDB.addKeyListener(principal.getKeyListener());
		panelBotoes.add(btnNewDB, "1, 1, center, fill");

		JButton btnDropDB = new JButton(new ImageIcon(TreeMenuPanel.class.getResource("/Images/Remove Folder.png")));
		btnDropDB.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnDropDB.setToolTipText("Remover Database [Del]");
		//		btnDropDB.setFont(new Font("Tahoma", Font.PLAIN, 10));
		//		lblDropDB.addKeyListener(principal.getKeyListener());
		btnDropDB.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				TreePath selectionPath = jTree.getSelectionPath();

				if (selectionPath != null) {
					DefaultMutableTreeNode dmt = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();

					if (!dmt.toString().equalsIgnoreCase(DATA_BASE_STR)) {
						dmt = getDatabaseNode(dmt);

						//nodeChanged
						((javax.swing.tree.DefaultTreeModel) jTree.getModel()).removeNodeFromParent(dmt);
						principal.getTabbedPanel().remove(dmt.toString());
						principal.getDatabases().remove(dmt.toString());
					}
				}
			}

			private DefaultMutableTreeNode getDatabaseNode(DefaultMutableTreeNode dmt) {
				if (!dmt.getParent().toString().equalsIgnoreCase(DATA_BASE_STR)) {
					return getDatabaseNode((DefaultMutableTreeNode) dmt.getParent());
				}
				return dmt;
			}
		});

		panelBotoes.add(btnDropDB, "2, 1, center, fill");*/

	}

	public JTree getjTree() {
		return jTree;
	}

	public DefaultMutableTreeNode getDataBaseNode() {
		return dataBaseNode;
	}

}
