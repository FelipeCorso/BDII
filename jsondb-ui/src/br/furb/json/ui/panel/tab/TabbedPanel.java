package br.furb.json.ui.panel.tab;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JTabbedPane;

import br.furb.json.ui.Principal;
import br.furb.json.ui.panel.command.CommandPanel;

public class TabbedPanel extends JTabbedPane {

	private static final long serialVersionUID = 4227160448630150355L;
	private Principal principal;
	@Deprecated
	private Map<String, Component> tabMap = new LinkedHashMap<String, Component>(); // FIXME: pra quê?
	private CommandPanel commandPanel;

	/**
	 * Create the panel.
	 * 
	 * @param principal
	 */
	public TabbedPanel(Principal principal, int tabPlacement) {
		this.principal = principal;
		setTabPlacement(tabPlacement);
		addKeyListener(principal.getKeyListener());
	}

	/**
	 * Caso o usuário tenha selecionado uma base é criada uma aba, para executar
	 * os comandos.
	 * 
	 * @deprecated em revisão
	 */
	@Deprecated
	public void createTabDataBase(String dataBaseName) {
		commandPanel = new CommandPanel(dataBaseName, principal);
		add(dataBaseName, commandPanel);
	}

	public CommandPanel getCommandPanel() {
		return (CommandPanel) getSelectedComponent();
	}

	// TODO
	/*@Override
	public Component add(String title, Component component) {
		if (!tabMap.containsKey(title)) {
			tabMap.put(title, component);
			return super.add(title, component);
		}
		return component;
	}*/

	public void remove(String dataBaseName) {
		Component component = tabMap.get(dataBaseName);
		if (component != null) {
			tabMap.remove(dataBaseName);
			super.remove(component);
		}
	}

	/**
	 * Retorna o índice da aba informada.
	 * 
	 * @param c
	 *            aba cujo índice se deseja conhecer.
	 * @return índice da aba informada, ou {@code -1} caso a aba não esteja
	 *         associada a este painel.
	 */
	public int getTabIndex(Component c) {
		Component[] components = getComponents();
		for (int i = 0; i < components.length; i++) {
			if (components[i] == c) {
				return i;
			}
		}
		return -1;
	}

	public Collection<CommandPanel> getTabs() {
		Collection<CommandPanel> documents = new ArrayList<>();
		for (Component tab : getComponents()) {
			if (tab instanceof CommandPanel) {
				documents.add((CommandPanel) tab);
			}
		}
		return documents;
	}

}
