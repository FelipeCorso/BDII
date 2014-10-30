package br.furb.json.ui.panel.command;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import br.furb.json.ui.Principal;
import br.furb.json.ui.button.CopyButton;
import br.furb.json.ui.button.CutButton;
import br.furb.json.ui.button.ExecuteButton;
import br.furb.json.ui.button.NewButton;
import br.furb.json.ui.button.OpenButton;
import br.furb.json.ui.button.PasteButton;
import br.furb.json.ui.button.SaveButton;
import br.furb.json.ui.button.TeamButton;
import br.furb.json.ui.shortcut.NumberedBorder;
import br.furb.json.ui.status.EStatus;

public class CommandPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3187283892481573406L;
	private final NewButton btnNew;
	private final OpenButton btnOpen;
	private final SaveButton btnSave;
	private final CopyButton btnCopy;
	private final PasteButton btnPaste;
	private final CutButton btnCut;
	private final ExecuteButton btnExecute;
	// private final JButton btnGerarCod;
	private final TeamButton btnTeam;
	private final JLabel lbStatus;
	private final JTextArea textEditor;
	private final JTextArea textMsg;

	private JPanel panelFooter;
	private JLabel lbFilePath;
	private JScrollPane scrollPaneEditor;
	private JPanel panelEditor;
	private JPanel panelMsg;
	private JScrollPane scrollPaneMsg;

	/**
	 * Create the frame.
	 */
	public CommandPanel(Principal principal) {
		Font fonte = new Font("Dialog", Font.BOLD, 11);

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
		panelFerramentas.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelFerramentas.setLayout(new GridLayout(0, 8, 0, 0));
		panelFerramentas.addKeyListener(principal.getKeyListener());

		btnExecute = new ExecuteButton("executar [F8]");
		btnExecute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnExecute.executaAcao(principal);
			}
		});
		btnExecute.setIcon(principal.getImageIcon("compile.png"));
		btnExecute.setHorizontalTextPosition(SwingConstants.CENTER);
		btnExecute.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnExecute.addKeyListener(principal.getKeyListener());
		btnExecute.setFont(fonte);
		panelFerramentas.add(btnExecute);

		btnNew = new NewButton("novo [ctrl-n]");
		btnNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnNew.executaAcao(principal);
			}
		});

		btnNew.setIcon(principal.getImageIcon("newFile.png"));
		btnNew.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNew.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNew.addKeyListener(principal.getKeyListener());
		btnNew.setFont(fonte);
		panelFerramentas.add(btnNew);

		btnOpen = new OpenButton("abrir [ctrl-a]");
		btnOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnOpen.executaAcao(principal);
			}
		});
		btnOpen.setIcon(principal.getImageIcon("openFile.png"));
		btnOpen.setHorizontalTextPosition(SwingConstants.CENTER);
		btnOpen.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnOpen.addKeyListener(principal.getKeyListener());
		btnOpen.setFont(fonte);
		panelFerramentas.add(btnOpen);

		btnSave = new SaveButton("salvar [ctrl-s]");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnSave.executaAcao(principal);
			}

		});
		btnSave.setIcon(principal.getImageIcon("saveFile.png"));
		btnSave.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSave.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnSave.addKeyListener(principal.getKeyListener());
		btnSave.setFont(fonte);
		panelFerramentas.add(btnSave);

		btnCopy = new CopyButton("copiar [ctrl-c]");
		btnCopy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnCopy.executaAcao(principal);
			}
		});
		btnCopy.setIcon(principal.getImageIcon("copy.png"));
		btnCopy.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCopy.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCopy.addKeyListener(principal.getKeyListener());
		btnCopy.setFont(fonte);
		panelFerramentas.add(btnCopy);

		btnPaste = new PasteButton("colar [ctrl-v]");
		btnPaste.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnPaste.executaAcao(principal);
			}
		});
		btnPaste.setIcon(principal.getImageIcon("paste.png"));
		btnPaste.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPaste.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPaste.addKeyListener(principal.getKeyListener());
		btnPaste.setFont(fonte);
		panelFerramentas.add(btnPaste);

		btnCut = new CutButton("recortar [ctrl-x]");
		btnCut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnCut.executaAcao(principal);
			}
		});
		btnCut.setIcon(principal.getImageIcon("cut.png"));
		btnCut.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCut.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCut.addKeyListener(principal.getKeyListener());
		btnCut.setFont(fonte);
		panelFerramentas.add(btnCut);

		btnTeam = new TeamButton("equipe [F1]");
		btnTeam.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnTeam.executaAcao(principal);
			}
		});
		btnTeam.setIcon(principal.getImageIcon("group.png"));
		btnTeam.setHorizontalTextPosition(SwingConstants.CENTER);
		btnTeam.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnTeam.addKeyListener(principal.getKeyListener());
		btnTeam.setFont(fonte);
		panelFerramentas.add(btnTeam);

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
		textEditor.getInputMap(JPanel.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK), "none");
		textEditor.getInputMap(JPanel.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK), "none");
		textEditor.getInputMap(JPanel.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK), "none");
		textEditor.getInputMap(JPanel.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK), "none");
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

		panelFooter = new JPanel();
		panelCompilador.add(panelFooter, BorderLayout.SOUTH);
		panelFooter.setLayout(new GridLayout(1, 2, 0, 0));
		panelFooter.addKeyListener(principal.getKeyListener());
		panelFooter.addKeyListener(principal.getKeyListener());

		lbStatus = new JLabel(EStatus.NAO_MODIFICADO.toString());
		lbStatus.addKeyListener(principal.getKeyListener());
		panelFooter.add(lbStatus);

		lbFilePath = new JLabel("");
		lbFilePath.addKeyListener(principal.getKeyListener());
		panelFooter.add(lbFilePath);
		setBorder(new EmptyBorder(5, 5, 5, 5));
	}

	public JTextArea getTextEditor() {
		return textEditor;
	}

	public JLabel getLbStatus() {
		return lbStatus;
	}

	public JLabel getLbFilePath() {
		return lbFilePath;
	}

	public JTextArea getTextMsg() {
		return textMsg;
	}

}
