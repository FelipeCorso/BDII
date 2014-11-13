package br.furb.json.ui.panel.command;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import br.furb.json.ui.Actions;
import br.furb.json.ui.Principal;
import br.furb.json.ui.panel.tab.TabbedPanel;
import br.furb.json.ui.shortcut.NumberedBorder;
import br.furb.json.ui.status.ModificationStatus;
import br.furb.jsondb.utils.StringUtils;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Painel para inserção e execução de comandos SQL.<br>
 * É composto por um editor, painel de mensagens, rótulo indicando o caminho do
 * arquivo e botões para apagar o conteúdo ou executar o script.<br>
 * É possível verificar e alterar o estado de modificação de um documento por
 * esta classe.
 */
public class CommandPanel extends JPanel {

	private static final long serialVersionUID = -3187283892481573406L;
	private final JTextArea textEditor;
	private final JTextArea textMsg;

	private JScrollPane scrollPaneEditor;
	private JPanel panelEditor;
	private JPanel panelMsg;
	private JScrollPane scrollPaneMsg;
	private JLabel lblExecute;
	private JLabel label;
	private JPanel panelFooter;
	private JLabel lbStatus;
	private JLabel lbFilePath;

	private ModificationStatus modificationStatus = ModificationStatus.UNMODIFIED;
	private File correspondingFile;
	private String title;
	private TabbedPanel owner;

	/**
	 * Create the frame.
	 * 
	 * @param title
	 *            título do documento
	 */
	public CommandPanel(String title, final Principal principal) {
		this.setTitle(title);
		addKeyListener(principal.getKeyListener());

		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		addKeyListener(principal.getKeyListener());

		JPanel panelCompilador = new JPanel();
		add(panelCompilador, BorderLayout.CENTER);
		panelCompilador.setLayout(new BorderLayout(0, 0));
		panelCompilador.addKeyListener(principal.getKeyListener());

		JPanel panelFerramentas = new JPanel();
		panelCompilador.add(panelFerramentas, BorderLayout.NORTH);
		panelFerramentas.addKeyListener(principal.getKeyListener());
		panelFerramentas
				.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("left:17px"), ColumnSpec.decode("center:24px"), }, new RowSpec[] { RowSpec.decode("20px"), }));

		panelFooter = new JPanel();
		panelCompilador.add(panelFooter, BorderLayout.SOUTH);
		createStatusPathBar(principal);

		lblExecute = new JLabel("");
		lblExecute.setIcon(new ImageIcon(CommandPanel.class.getResource("/Images/execute.png")));
		lblExecute.setToolTipText("Executar [F8]");
		lblExecute.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				principal.doSafely(Actions::executeScript);
			}

		});
		panelFerramentas.add(lblExecute, "1, 1, fill, fill");

		label = new JLabel("");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textEditor.setText(StringUtils.EMPTY_STR);
			}
		});
		label.setIcon(new ImageIcon(CommandPanel.class.getResource("/Images/eraser.png")));
		panelFerramentas.add(label, "2, 1, fill, fill");

		JPanel panelCentral = new JPanel();
		panelCentral.addKeyListener(principal.getKeyListener());
		panelCompilador.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));

		panelEditor = new JPanel();
		panelEditor.addKeyListener(principal.getKeyListener());

		scrollPaneEditor = new JScrollPane(panelEditor);
		scrollPaneEditor.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneEditor.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneEditor.addKeyListener(principal.getKeyListener());

		textEditor = new JTextArea();
		textEditor.setBorder(new NumberedBorder());
		textEditor.addKeyListener(principal.getKeyListener());
		textEditor.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK), "none");
		textEditor.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK), "none");
		textEditor.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), "none");
		textEditor.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK), "none");

		textEditor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				documentChanged();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				documentChanged();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				documentChanged();
			}

			private void documentChanged() {
				if (!isModified()) {
					setModified(true);
				}
			}
		});

		panelEditor.setLayout(new BoxLayout(panelEditor, BoxLayout.X_AXIS));

		panelEditor.add(textEditor);
		panelCentral.add(scrollPaneEditor, BorderLayout.CENTER);

		panelMsg = new JPanel();
		panelMsg.addKeyListener(principal.getKeyListener());
		panelMsg.setLayout(new BoxLayout(panelMsg, BoxLayout.X_AXIS));
		panelMsg.setSize(panelMsg.getSize().width, 100);

		scrollPaneMsg = new JScrollPane(panelMsg);
		scrollPaneMsg.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneMsg.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneMsg.addKeyListener(principal.getKeyListener());
		scrollPaneMsg.setPreferredSize(new Dimension(0, 100));

		textMsg = new JTextArea();
		panelMsg.add(textMsg);
		textMsg.setEditable(false);
		textMsg.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCentral.add(scrollPaneMsg, BorderLayout.SOUTH);
		textMsg.addKeyListener(principal.getKeyListener());
		textMsg.setFont(new Font("Console", Font.PLAIN, 11));

		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setModified(false);
	}
	
	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		this.textEditor.requestFocus();
	}

	private void createStatusPathBar(Principal principal) {
		panelFooter.setLayout(new GridLayout(1, 2, 0, 0));
		panelFooter.addKeyListener(principal.getKeyListener());

		lbStatus = new JLabel();
		lbStatus.addKeyListener(principal.getKeyListener());
		panelFooter.add(lbStatus);

		lbFilePath = new JLabel("");
		lbFilePath.addKeyListener(principal.getKeyListener());
		panelFooter.add(lbFilePath);
	}

	public JTextArea getTextEditor() {
		return textEditor;
	}

	public JTextArea getTextMsg() {
		return textMsg;
	}

	/**
	 * Retorna o arquivo correspondente a este script.
	 * 
	 * @return arquivo correspondente a este script.
	 */
	public File getCorrespondingFile() {
		return correspondingFile;
	}

	/**
	 * Define o arquivo correspondente a este script.
	 * 
	 * @param correspondingFile
	 *            arquivo correspondente a este script
	 */
	public void setCorrespondingFile(File correspondingFile) {
		this.correspondingFile = correspondingFile;
	}

	/**
	 * Método de conveniência para verificar se a enumeração do estado de
	 * modificação do documento é {@link ModificationStatus#MODIFIED}.
	 * 
	 * @return {@code true} se o documento tiver sido modificado. {@code false}
	 *         do contrário.
	 */
	public boolean isModified() {
		return getModificationStatus() == ModificationStatus.MODIFIED;
	}

	/**
	 * Método de conveniência para definir a enumeração correspondente ao estado
	 * de modificação do documento.
	 * 
	 * @param modified
	 *            {@code true} se o documento está
	 *            {@link ModificationStatus#MODIFIED MODIFIED}, {@code false} se
	 *            está {@link ModificationStatus#UNMODIFIED UNMODIFIED}.
	 */
	public void setModified(boolean modified) {
		this.modificationStatus = modified ? ModificationStatus.MODIFIED : ModificationStatus.UNMODIFIED;
		this.lbStatus.setText(this.modificationStatus.toString());
	}

	/**
	 * Retorna o estado de modificação correspondente do documento atual.
	 * 
	 * @return estado de modificação correspondente do documento atual.
	 */
	public ModificationStatus getModificationStatus() {
		return modificationStatus;
	}

	/**
	 * FIXME TEST
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("CommandPanel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.getContentPane().add(new CommandPanel("Sem título", new Principal()));
		frame.setMinimumSize(new Dimension(600, 400));

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Retorna o título do documento.
	 * 
	 * @return título do documento.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Altera o título do documento.
	 * 
	 * @param title
	 *            título do documento.
	 */
	public void setTitle(String title) {
		this.title = title;
		if (owner != null) {
			int index = owner.getTabIndex(this);
			if (index >= 0) {
				owner.setTitleAt(index, title);
			}
		}
	}

	/**
	 * Define o {@link JTabbedPane} ao qual este painel foi vinculado.
	 * 
	 * @param owner
	 *            {@code JTabbedPane} ao qual este painel foi vinculado.
	 */
	public void setOwner(TabbedPanel owner) {
		this.owner = owner;
	}

}
