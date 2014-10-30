package br.furb.json.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.furb.json.ui.panel.command.CommandPanel;
import br.furb.json.ui.panel.treeMenu.TreeMenuPanel;
import br.furb.json.ui.shortcut.ShortCutListener;
import br.furb.jsondb.store.metadata.DatabaseMetadata;

public class Principal extends JFrame {

	private static final long serialVersionUID = 6173738200605246314L;

	private static final String DIR_IMAGES = "/Images/";

	private TreeMenuPanel treeMenu;
	private CommandPanel commandPanel;
	private Map<String, DatabaseMetadata> databases = new LinkedHashMap<String, DatabaseMetadata>();
	private JPanel contentPane;

	private ShortCutListener keyListener;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 650));
		setBounds(100, 100, 1024, 660);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		getContentPane().setLayout(null);

		keyListener = new ShortCutListener(this);
		contentPane.setLayout(new BorderLayout(0, 0));

		treeMenu = new TreeMenuPanel(this);
		treeMenu.setBounds(0, 0, 165, 620);
		getContentPane().add(treeMenu, BorderLayout.WEST);

		commandPanel = new CommandPanel(this);
		commandPanel.setBounds(243, 0, 541, 620);
		getContentPane().add(commandPanel);

	}

	public void addDataBase(DatabaseMetadata database) {
		databases.put(database.getName(), database);
	}

	public CommandPanel getCommandPanel() {
		return commandPanel;
	}

	public ImageIcon getImageIcon(String iconName) {
		return new ImageIcon(this.getClass().getResource(DIR_IMAGES + iconName));
	}

	public ShortCutListener getKeyListener() {
		return keyListener;
	}

}
