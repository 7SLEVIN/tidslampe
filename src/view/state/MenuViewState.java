package view.state;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class MenuViewState extends AbstractViewState {
	private JButton projectsButton;
	private JButton developersButton;
	
	public MenuViewState() {
		this.projectsButton = new JButton("Projects");
		this.developersButton = new JButton("Developers");

		this.add(this.developersButton);
		this.add(this.projectsButton);
	}

	public JButton getDevelopersButton() {
		return developersButton;
	}

	public JButton getProjectsButton() {
		return projectsButton;
	}
	
	
}
