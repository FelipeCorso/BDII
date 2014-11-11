package br.furb.json.ui.panel.treeMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import br.furb.json.ui.Principal;
import br.furb.json.ui.action.NewAction;
import br.furb.json.ui.action.OpenAction;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class TreeMenuPanel extends JPanel {

	private static final String DATA_BASE_STR = "DataBase";

	private static final long serialVersionUID = 8080924607252341731L;

	protected static final String TABLES_STR = "Tabelas";
	protected static final String INDEX_STR = "�ndices";

	private DefaultMutableTreeNode dataBaseNode;

	private JTree jTree;

	public TreeMenuPanel(Principal principal) {
		setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
		addKeyListener(principal.getKeyListener());

		defineLookAndFeel();
		setLayout(new BorderLayout(0, 0));

		JPanel panelBotoes = new JPanel();
		panelBotoes.addKeyListener(principal.getKeyListener());
		add(panelBotoes, BorderLayout.NORTH);

		JScrollPane scrollPaneTree = new JScrollPane();
		scrollPaneTree.addKeyListener(principal.getKeyListener());
		add(scrollPaneTree, BorderLayout.CENTER);

		dataBaseNode = new DefaultMutableTreeNode(DATA_BASE_STR);
		jTree = new JTree(new javax.swing.tree.DefaultTreeModel(dataBaseNode));
		jTree.addKeyListener(principal.getKeyListener());
		jTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				TreePath selectionPath = jTree.getSelectionPath();

				if (selectionPath != null) {
					javax.swing.tree.DefaultMutableTreeNode dmt = (javax.swing.tree.DefaultMutableTreeNode) selectionPath.getLastPathComponent();

					if (!dmt.getUserObject().toString().equalsIgnoreCase(DATA_BASE_STR)) {
						if (dmt.getParent().toString().equalsIgnoreCase(DATA_BASE_STR)) {
							// � uma base, fazer select
							principal.getTabbedPanel().createTabDataBase(dmt.toString());
						} else {
							if (dmt.getParent().toString().equalsIgnoreCase(TABLES_STR)) {
								// � uma tabela, describe
							} else {
								if (dmt.getParent().toString().equalsIgnoreCase(TABLES_STR)) {
									// � um �ndice, apresentar nome, colunas
								}
							}
						}

					}
				}

			}
		});
		scrollPaneTree.setViewportView(jTree);

		JLabel lblOpenDB = new JLabel("");
		lblOpenDB.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblOpenDB.setToolTipText("Abrir Database [Ctrl+A]");
		lblOpenDB.addKeyListener(principal.getKeyListener());
		lblOpenDB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				principal.doSafely(OpenAction::executeAction);
			}
		});
		panelBotoes.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("center:50px"), ColumnSpec.decode("center:50px"), ColumnSpec.decode("center:50px"), },
				new RowSpec[] { RowSpec.decode("32px"), }));

		JLabel lblNewDB = new JLabel("");
		lblNewDB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				principal.doSafely(NewAction::executeAction);
			}
		});
		lblNewDB.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblNewDB.setToolTipText("Nova Database [Ctrl+N]");
		lblNewDB.setIcon(new ImageIcon(TreeMenuPanel.class.getResource("/Images/Add folder.png")));
		lblNewDB.addKeyListener(principal.getKeyListener());
		panelBotoes.add(lblNewDB, "1, 1, center, fill");

		lblOpenDB.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblOpenDB.setIcon(new ImageIcon(TreeMenuPanel.class.getResource("/Images/openFile.png")));
		panelBotoes.add(lblOpenDB, "2, 1, center, fill");

		JLabel lblRemoveDB = new JLabel("");
		lblRemoveDB.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblRemoveDB.setToolTipText("Remover Database [Del]");
		lblRemoveDB.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblRemoveDB.setIcon(new ImageIcon(TreeMenuPanel.class.getResource("/Images/Remove Folder.png")));
		lblRemoveDB.addKeyListener(principal.getKeyListener());
		lblRemoveDB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
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

		panelBotoes.add(lblRemoveDB, "3, 1, center, fill");

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

	public JTree getjTree() {
		return jTree;
	}

	public DefaultMutableTreeNode getDataBaseNode() {
		return dataBaseNode;
	}

}
