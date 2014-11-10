package br.furb.json.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import br.furb.json.ui.action.CopyAction;
import br.furb.json.ui.action.CutAction;
import br.furb.json.ui.action.NewAction;
import br.furb.json.ui.action.OpenAction;
import br.furb.json.ui.action.PasteAction;
import br.furb.json.ui.action.SaveAction;
import br.furb.json.ui.action.TeamAction;
import br.furb.json.ui.panel.tab.TabbedPanel;
import br.furb.json.ui.panel.treeMenu.TreeMenuPanel;
import br.furb.json.ui.shortcut.ShortCutListener;
import br.furb.jsondb.store.metadata.DatabaseMetadata;

public class Principal extends JFrame {

	private static final long serialVersionUID = 6173738200605246314L;

	private static final String DIR_IMAGES = "/Images/";

	private TreeMenuPanel treeMenu;
	private Map<String, DatabaseMetadata> databases = new LinkedHashMap<String, DatabaseMetadata>();
	private JPanel contentPane;

	private ShortCutListener keyListener;
	private JMenuBar menuBar;

	private JMenu mnFile;
	private JMenuItem mntmNew;
	private JMenuItem mntmOpen;
	private JMenuItem mntmSave;
	private JMenu mnEdit;
	private JMenuItem mntmCopy;
	private JMenuItem mntmCut;
	private JMenuItem mntmPaste;
	private JMenu mnSobre;
	private JMenuItem mntmHelp;
	private JMenuItem mntmEquipe;

	private final TabbedPanel tabbedPanel;
	private JPanel centerPanel;

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
		setTitle("JsOnDb");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 660);
		keyListener = new ShortCutListener(this);
		addKeyListener(keyListener);

		menuBar = new JMenuBar();
		createJMenuBar();

		contentPane = new JPanel();
		contentPane.addKeyListener(keyListener);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		getContentPane().setLayout(null);

		contentPane.setLayout(new BorderLayout(0, 0));

		centerPanel = new JPanel();
		centerPanel.addKeyListener(keyListener);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));

		treeMenu = new TreeMenuPanel(this);
		treeMenu.addKeyListener(keyListener);
		treeMenu.setMinimumSize(new Dimension(590, 150));
		treeMenu.setSize(170, 591);
		contentPane.add(treeMenu, BorderLayout.WEST);

		tabbedPanel = new TabbedPanel(this, JTabbedPane.TOP);
		centerPanel.add(tabbedPanel, BorderLayout.CENTER);

	}

	private void createJMenuBar() {
		menuBar.addKeyListener(keyListener);
		setJMenuBar(menuBar);

		mnFile = new JMenu("Arquivo");
		mnFile.addKeyListener(keyListener);
		menuBar.add(mnFile);

		mntmNew = new JMenuItem("Novo Ctrl+N");
		mntmNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				NewAction.executeAction(Principal.this, treeMenu.getjTree(), treeMenu.getDataBaseNode());
			}
		});
		mnFile.add(mntmNew);

		mntmOpen = new JMenuItem("Abrir Ctrl+A");
		mntmOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenAction.executeAction(Principal.this, treeMenu.getjTree(), treeMenu.getDataBaseNode());
			}
		});
		mnFile.add(mntmOpen);

		mntmSave = new JMenuItem("Salvar Ctrl+S");
		mntmSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SaveAction.executeAction(Principal.this);
			}
		});
		mnFile.add(mntmSave);

		mnEdit = new JMenu("Editar");
		mnEdit.addKeyListener(keyListener);
		menuBar.add(mnEdit);

		mntmCopy = new JMenuItem("Copiar Ctrl+C");
		mntmCopy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CopyAction.executeAction(Principal.this);
			}
		});
		mnEdit.add(mntmCopy);

		mntmCut = new JMenuItem("Recortar Ctrl+X");
		mntmCut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CutAction.executeAction(Principal.this);
			}
		});
		mnEdit.add(mntmCut);

		mntmPaste = new JMenuItem("Colar Ctrl+V");
		mntmPaste.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PasteAction.executeAction(Principal.this);
			}
		});
		mnEdit.add(mntmPaste);

		mnSobre = new JMenu("Sobre");
		mnSobre.addKeyListener(keyListener);
		menuBar.add(mnSobre);

		mntmHelp = new JMenuItem("Help");
		mnSobre.add(mntmHelp);

		mntmEquipe = new JMenuItem("Equipe");
		mntmEquipe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TeamAction.executeAction(Principal.this);
			}
		});
		mnSobre.add(mntmEquipe);
	}

	public Map<String, DatabaseMetadata> getDatabases() {
		return databases;
	}

	public void addDataBase(DatabaseMetadata database) {
		databases.put(database.getName(), database);
	}

	public ImageIcon getImageIcon(String iconName) {
		return new ImageIcon(this.getClass().getResource(DIR_IMAGES + iconName));
	}

	public ShortCutListener getKeyListener() {
		return keyListener;
	}

	public TabbedPanel getTabbedPanel() {
		return tabbedPanel;
	}

	public TreeMenuPanel getTreeMenu() {
		return treeMenu;
	}

}
