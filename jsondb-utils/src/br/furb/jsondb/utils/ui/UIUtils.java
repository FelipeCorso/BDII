package br.furb.jsondb.utils.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.furb.jsondb.utils.StringUtils;

public class UIUtils {

	/**
	 * LookAndFeel suportado pela API.
	 * 
	 * @author William Leander Seefeld
	 * 
	 */
	public static enum SupportedLookAndFeel {
		SYSTEM_DEFAULT(UIManager.getSystemLookAndFeelClassName()), //
		CROSSPLATFORM(UIManager.getCrossPlatformLookAndFeelClassName()), //
		METAL("javax.swing.plaf.metal.MetalLookAndFeel"), //
		NIMBUS("javax.swing.plaf.nimbus.NimbusLookAndFeel"), //
		MOTIF("com.sun.java.swing.plaf.motif.MotifLookAndFeel"), //
		WINDOWS_CLASSIC("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");

		private String className;

		private SupportedLookAndFeel(String className) {
			this.className = className;
		}

		public String getLookAndFeelClassName() {
			return className;
		}

		public void setAsCurrentLookAndFeel() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
			UIUtils.changeLookAndFeel(this);
		}
	}

	private UIUtils() {
	}

	/**
	 * Tenta alterar o {@link LookAndFeel} para o informado. Caso algum erro
	 * ocorra, silencia a exceção.
	 * 
	 * @param lf
	 *            {@code LookAndFeel} suportado pela API.
	 * @see #changeLookAndFeel(SupportedLookAndFeel)
	 */
	public static void changeLookAndFeelIfPossible(SupportedLookAndFeel lf) {
		try {
			changeLookAndFeel(lf);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Altera o {@link LookAndFeel} para o informado.
	 * 
	 * @param lf
	 *            {@code LookAndFeel} suportado pela API.
	 * 
	 * @see UIManager#setLookAndFeel(LookAndFeel)
	 * @see #changeLookAndFeelIfPossible(SupportedLookAndFeel)
	 */
	public static void changeLookAndFeel(SupportedLookAndFeel lf) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(lf.getLookAndFeelClassName());
	}

	public static void showMessage(Component parent, String message, String title, int dialogType) {
		JOptionPane.showMessageDialog(parent, message, title, dialogType);
	}
	
	public static boolean promptConfirmation(Component parent, String promptMessage, String title) {
		return JOptionPane.showConfirmDialog(parent, promptMessage, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}
	
	public static void showError(Component parent, Throwable t) {
		JOptionPane.showMessageDialog(parent, generateMessage(t), "Erro", JOptionPane.ERROR_MESSAGE);
	}

	private static JPanel generateMessage(Throwable t) {
		StringBuilder sb = new StringBuilder();
		String msg = t.getMessage();
		sb.append("Erro inesperado: ");
		sb.append(StringUtils.isEmpty(msg) ? t : msg);
		sb.append("\n\n");
		sb.append("Stack trace: ");

		JTextArea msgField = new JTextArea(sb.toString());
		msgField.setEditable(false);
		msgField.setBackground(UIManager.getColor("Panel.background"));
		msgField.setFont(UIManager.getFont("Label.font"));

		JList<StackTraceElement> stack = new JList<>(t.getStackTrace());
		JScrollPane listPanel = new JScrollPane(stack);

		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
		panel.setLayout(layout);

		panel.add(msgField);
		panel.add(listPanel);

		return panel;
	}

	public static void centerOnScreen(Component component) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension thisSize = component.getSize();
		Point location = new Point();
		location.x = screenSize.width / 2 - thisSize.width / 2;
		location.y = screenSize.height / 2 - thisSize.height / 2;
		component.setLocation(location);
	}

	public static Dimension getScreenDimensions() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
}
