package br.furb.json.ui.panel.tab;

import java.awt.Component;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JTabbedPane;

import br.furb.json.ui.Principal;
import br.furb.json.ui.panel.command.CommandPanel;

public class TabbedPanel extends JTabbedPane {

	private static final long serialVersionUID = 4227160448630150355L;
	private Principal principal;
	private Map<String, Component> tabMap = new LinkedHashMap<String, Component>();
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
	 * Caso o usuário tenha selecionado uma base é criada uma aba, para executar os comandos.
	 */
	public void createTabDataBase(String dataBaseName) {
		commandPanel = new CommandPanel(principal);
		add(dataBaseName, commandPanel);
	}

	public CommandPanel getCommandPanel() {
		return (CommandPanel) getSelectedComponent();
	}

	@Override
	public Component add(String title, Component component) {
		if (!tabMap.containsKey(title)) {
			tabMap.put(title, component);
			return super.add(title, component);
		}
		return component;
	}

	public void remove(String dataBaseName) {
		Component component = tabMap.get(dataBaseName);
		if (component != null) {
			tabMap.remove(dataBaseName);
			super.remove(component);
		}
	}
}
