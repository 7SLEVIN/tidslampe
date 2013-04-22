package view.state;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class MenuViewState extends AbstractViewState {
	private JButton projectsButton;
	private JButton developersButton;
	private JButton calendarButton;
	
	public MenuViewState() {
		this.projectsButton = new JButton("Projects");
		this.developersButton = new JButton("Developers");
		this.calendarButton = new JButton("Register time");

		this.add(this.calendarButton);
		this.add(this.developersButton);
		this.add(this.projectsButton);
	}

	public JButton getDevelopersButton() {
		return this.developersButton;
	}

	public JButton getCalendarButton() {
		return this.calendarButton;
	}

	public JButton getProjectsButton() {
		return projectsButton;
	}
	
	
}
