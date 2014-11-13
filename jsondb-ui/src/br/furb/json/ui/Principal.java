package br.furb.json.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import br.furb.json.ui.panel.command.CommandPanel;
import br.furb.json.ui.panel.tab.TabbedPanel;
import br.furb.json.ui.panel.treeMenu.TreeMenuPanel;
import br.furb.json.ui.shortcut.ShortCutListener;
import br.furb.jsondb.store.JsonDBProperty;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.utils.ui.UIUtils;

public class Principal extends JFrame {

	private static final long serialVersionUID = 6173738200605246314L;

	private static final String DIR_IMAGES = "/Images/";

	private TreeMenuPanel treeMenu;
	private Map<String, DatabaseMetadata> databases = new LinkedHashMap<String, DatabaseMetadata>();
	private JPanel contentPane;

	private ShortCutListener keyListener;
	private JMenuBar menuBar;

	private JMenu mnFile;
	private JMenuItem mntmNewScript;
	private JMenuItem mntmOpenScript;
	private JMenuItem mntmSave;
	private JMenu mnEdit;
	private JMenuItem mntmCopy;
	private JMenuItem mntmCut;
	private JMenuItem mntmPaste;
	private JMenu mnHelp;
	private JMenuItem mntmDoc;
	private JMenuItem mntmTeam;

	private final TabbedPanel tabbedPanel;
	private JPanel centerPanel;
	private JMenuItem mntmSaveScriptAs;
	private JMenuItem mntmChangeWorkDir;
	private JMenuItem mntmExit;
	private JMenu mnDatabase;
	private JMenuItem mntmNewScript_noBase;
	private JMenuItem mntmNewScript_forBase;
	private JMenuItem mntmCreateDatabase;
	private JMenuItem mntmDropDatabase;

	private Collection<JMenuItem> baseDependantMenus;

	private File workingDir;
	private JMenuItem mntmFecharScript;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		UIUtils.changeLookAndFeelIfPossible(UIUtils.SupportedLookAndFeel.SYSTEM_DEFAULT);

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
					UIUtils.centerOnScreen(frame);
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
		setBounds(100, 100, 1024, 660);
		keyListener = new ShortCutListener(this);
		addKeyListener(keyListener);

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

		// $hide>>$
		tabbedPanel = new TabbedPanel(this, SwingConstants.TOP);
		centerPanel.add(tabbedPanel, BorderLayout.CENTER);
		// $hide<<$

		menuBar = new JMenuBar();
		createJMenuBar();

		setWorkingDir(new File(JsonDBProperty.JSON_DB_DIR.get()));

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				doSafely(Actions::exit);
			}
		});
	}

	private void createJMenuBar() {
		menuBar.addKeyListener(keyListener);
		setJMenuBar(menuBar);

		mnFile = new JMenu("Arquivo");
		mnFile.setMnemonic(KeyEvent.VK_A);
		mnFile.addKeyListener(keyListener);
		menuBar.add(mnFile);

		mntmNewScript = new JMenuItem(createSafeAction(Actions::newScript, "Novo script sem base"));
		mntmNewScript.setMnemonic(KeyEvent.VK_N);
		mntmNewScript.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnFile.add(mntmNewScript);

		mntmOpenScript = new JMenuItem(createSafeAction(Actions::openScript, "Abrir script"));
		mntmOpenScript.setMnemonic(KeyEvent.VK_A);
		mntmOpenScript.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(mntmOpenScript);

		mntmSave = new JMenuItem(createSafeAction(Actions::saveScript, "Salvar script"));
		mntmSave.setMnemonic(KeyEvent.VK_S);
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);

		mntmSaveScriptAs = new JMenuItem(createSafeAction(Actions::saveScriptAs, "Salvar script como..."));
		mntmSaveScriptAs.setMnemonic(KeyEvent.VK_C);
		mntmSaveScriptAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnFile.add(mntmSaveScriptAs);

		mntmFecharScript = new JMenuItem(createSafeAction(Actions::closeActiveTab, "Fechar script"));
		mntmFecharScript.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mnFile.add(mntmFecharScript);

		mntmChangeWorkDir = new JMenuItem(createSafeAction(Actions::changeWorkingDir, "Alterar pasta de trabalho"));
		mntmChangeWorkDir.setMnemonic(KeyEvent.VK_T);
		mntmChangeWorkDir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
		mnFile.add(mntmChangeWorkDir);

		mntmExit = new JMenuItem(createSafeAction(Actions::exit, "Sair"));
		mntmExit.setMnemonic(KeyEvent.VK_I);
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		mnFile.add(mntmExit);

		mnEdit = new JMenu("Editar");
		mnEdit.setMnemonic(KeyEvent.VK_E);
		mnEdit.addKeyListener(keyListener);
		menuBar.add(mnEdit);

		mntmCopy = new JMenuItem("Copiar");
		mntmCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		mntmCopy.setMnemonic(KeyEvent.VK_C);
		mntmCopy.addMouseListener(createSafeMouseListener(Actions::copy));

		mntmCut = new JMenuItem("Recortar");
		mntmCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		mntmCut.setMnemonic(KeyEvent.VK_T);
		mntmCut.addMouseListener(createSafeMouseListener(Actions::cut));
		mnEdit.add(mntmCut);
		mnEdit.add(mntmCopy);

		mntmPaste = new JMenuItem("Colar");
		mntmPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		mntmPaste.setMnemonic(KeyEvent.VK_L);
		mntmPaste.addMouseListener(createSafeMouseListener(Actions::paste));
		mnEdit.add(mntmPaste);

		mnHelp = new JMenu("Ajuda");
		mnHelp.setMnemonic(KeyEvent.VK_J);
		mnHelp.addKeyListener(keyListener);

		mnDatabase = new JMenu("Base de dados");
		mnDatabase.setMnemonic(KeyEvent.VK_B);
		menuBar.add(mnDatabase);

		// "Novo script sem base"
		mntmNewScript_noBase = new JMenuItem(mntmNewScript.getAction());
		mntmNewScript_noBase.setMnemonic(KeyEvent.VK_N);
		mntmNewScript_noBase.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnDatabase.add(mntmNewScript_noBase);

		mntmNewScript_forBase = new JMenuItem("Novo script para a base");
		mntmNewScript_forBase.setMnemonic(KeyEvent.VK_P);
		mntmNewScript_forBase.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnDatabase.add(mntmNewScript_forBase);

		mntmCreateDatabase = new JMenuItem("Nova base");
		mntmCreateDatabase.setMnemonic(KeyEvent.VK_B);
		mntmCreateDatabase.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
		mnDatabase.add(mntmCreateDatabase);

		mntmDropDatabase = new JMenuItem("Eliminar base");
		mntmDropDatabase.setMnemonic(KeyEvent.VK_E);
		mntmDropDatabase.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		mnDatabase.add(mntmDropDatabase);
		menuBar.add(mnHelp);

		mntmDoc = new JMenuItem("Documentação");
		mntmDoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mntmDoc.setMnemonic(KeyEvent.VK_D);
		mnHelp.add(mntmDoc);

		mntmTeam = new JMenuItem("Equipe");
		mntmTeam.setMnemonic(KeyEvent.VK_E);
		mntmTeam.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		mntmTeam.addMouseListener(createSafeMouseListener(Actions::showTeam));
		mnHelp.add(mntmTeam);

		baseDependantMenus = new ArrayList<JMenuItem>(3);
		baseDependantMenus.add(mntmNewScript_forBase);
		baseDependantMenus.add(mntmCreateDatabase);
		baseDependantMenus.add(mntmDropDatabase);

		baseDependantMenus.forEach((menu) -> menu.setEnabled(false));
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

	/**
	 * @deprecated você não deveria estar usando isso... use
	 *             {@link #getActiveCommandPanel()}
	 */
	@Deprecated
	public TabbedPanel getTabbedPanel() {
		return tabbedPanel;
	}

	public TreeMenuPanel getTreeMenu() {
		return treeMenu;
	}

	private MouseListener createSafeMouseListener(Consumer<Principal> action) {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doSafely(action);
			}
		};
	}

	@SuppressWarnings("serial")
	private Action createSafeAction(Consumer<Principal> action, String actionName) {
		return new AbstractAction(actionName) {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSafely(action);
			}
		};
	}

	/**
	 * Executa a ação de forma preventiva, capturando qualquer exceção que
	 * ocorra e exibindo-a em um diálogo.
	 * 
	 * @param p
	 *            função a ser executada. Leva como argument a instância atual
	 *            de {@code Principal}.
	 */
	public void doSafely(Consumer<Principal> p) {
		try {
			p.accept(this);
		} catch (Throwable t) {
			handleUIException(t);
		}
	}

	/**
	 * Trata erros cuja possibilidade de ocorrência é conhecida, imprimindo a
	 * stack trace e exibindo um diálogo de erro na interface.
	 * 
	 * @param t
	 *            exceção ocorrida.
	 */
	private void handleUIException(Throwable t) {
		t.printStackTrace();
		UIUtils.showError(contentPane, t);
	}

	/**
	 * Exibe uma mensagem de erro na tela.
	 * 
	 * @param message
	 *            mensagem do erro.
	 */
	public void handleUIException(String message) {
		UIUtils.showMessage(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Altera a pasta de trabalho atual, atualizando o caminho no título da
	 * janela e fechando todos os scripts abertos.
	 * 
	 * @param newDir
	 *            nova pasta de trabalho
	 */
	public void setWorkingDir(File newDir) {
		this.workingDir = newDir;
		boolean hasDir = newDir != null;
		setTitle("JsonDB - " + (hasDir ? newDir.getAbsolutePath() : "<sem pasta de trabalho>"));
		baseDependantMenus.forEach(menu -> menu.setEnabled(hasDir));
		// TODO
	}

	public File getWorkingDir() {
		return workingDir;
	}

	/**
	 * Retorna a {@link CommandPanel aba de script} ativa.
	 * 
	 * @return aba de script ativa, ou {@code null} caso não haja uma.
	 */
	public CommandPanel getActiveCommandPanel() {
		return this.tabbedPanel.getCommandPanel();
	}

	/**
	 * Adiciona uma nova aba de código.
	 * 
	 * @param commandPanel
	 *            painel do documento.
	 */
	public void addCommandPanel(CommandPanel commandPanel) {
		if (commandPanel == null) {
			return;
		}
		this.tabbedPanel.add(commandPanel.getTitle(), commandPanel);
		this.tabbedPanel.setSelectedComponent(commandPanel);
		commandPanel.setOwner(this.tabbedPanel);
	}

	/**
	 * Remove a aba do painel de comando informado.
	 * 
	 * @param commandPanel
	 *            painel de comando a ser removido.
	 */
	public void closeCommandPanel(CommandPanel commandPanel) {
		this.tabbedPanel.remove(commandPanel);
	}

	/**
	 * Retorna todos os documentos abertos.
	 * 
	 * @return todos os documentos abertos
	 */
	public Collection<CommandPanel> getCommandPanels() {
		return this.tabbedPanel.getTabs();
	}

	/**
	 * Libera recursos e encerra a aplicação.
	 */
	public void exit() {
		setVisible(false);
		dispose();
		System.exit(0);
	}

}
