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

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import br.furb.json.ui.Principal;
import br.furb.json.ui.action.ExecuteAction;
import br.furb.json.ui.shortcut.NumberedBorder;
import br.furb.json.ui.status.EStatus;
import br.furb.jsondb.utils.StringUtils;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class CommandPanel extends JPanel {

	/**
	 * 
	 */
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

	/**
	 * Create the frame.
	 */
	public CommandPanel(final Principal principal) {
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
				principal.doSafely(ExecuteAction::executeAction);
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
	}

	private void createStatusPathBar(Principal principal) {

		panelFooter.setLayout(new GridLayout(1, 2, 0, 0));
		panelFooter.addKeyListener(principal.getKeyListener());

		lbStatus = new JLabel(EStatus.NAO_MODIFICADO.toString());
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

	public JLabel getLbStatus() {
		return lbStatus;
	}

	public JLabel getLbFilePath() {
		return lbFilePath;
	}

	/**
	 * FIXME TEST
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("CommandPanel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.getContentPane().add(new CommandPanel(new Principal()));
		frame.setMinimumSize(new Dimension(600, 400));

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
