package br.furb.json.ui.panel.treeMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import br.furb.json.ui.Principal;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataIO;

public class TreeMenuPanel extends JPanel {

	private static final long serialVersionUID = 8080924607252341731L;

	private DefaultMutableTreeNode top = new DefaultMutableTreeNode("DataBase");

	private Principal principal;

	public TreeMenuPanel(Principal principal) {
		this.principal = principal;
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		defineLookAndFeel();
		setLayout(new BorderLayout(0, 0));

		JPanel panelBotoes = new JPanel();
		add(panelBotoes, BorderLayout.NORTH);
		panelBotoes.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panel_1 = new JPanel();
		// add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblAdd = new JLabel("Adicionar");
		lblAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					String databaseDir = loadDataBaseDir(TreeMenuPanel.this.principal);

					DatabaseMetadata database = DatabaseMetadataIO.read(databaseDir);
					TreeMenuPanel.this.principal.addDataBase(database);

					createNodesDatabase(top, database);

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
		panelBotoes.add(lblRemover);

		JScrollPane scrollPaneTree = new JScrollPane();
		add(scrollPaneTree, BorderLayout.CENTER);

		JTree tree = new JTree(top);
		scrollPaneTree.setViewportView(tree);

	}

	private void createNodesDatabase(DefaultMutableTreeNode top, DatabaseMetadata database) {
		DefaultMutableTreeNode databaseNode = new DefaultMutableTreeNode(database.getName());
		top.add(databaseNode);

		createNodesTable(databaseNode, database);
		// createNodesIndex(databaseNode, database);
	}

	private void createNodesTable(DefaultMutableTreeNode databaseNode, DatabaseMetadata database) {
		DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode("Tabelas");
		databaseNode.add(tableNode);

		for (String tableName : database.getTables().keySet()) {
			DefaultMutableTreeNode table = new DefaultMutableTreeNode(tableName);
			tableNode.add(table);
		}

	}

	// FIXME: Aguardando definição
	private void createNodesIndex(DefaultMutableTreeNode databaseNode, DatabaseMetadata database) {
		DefaultMutableTreeNode indexNode = new DefaultMutableTreeNode("Tabelas");
		databaseNode.add(indexNode);

		for (String indexName : database.getTables().keySet()) {
			DefaultMutableTreeNode index = new DefaultMutableTreeNode(indexName);
			indexNode.add(index);
		}
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
