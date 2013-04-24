package view.state;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuViewState extends AbstractViewState {
	private JButton projectsButton;
	private JButton developersButton;
	private JButton calendarButton;
	private JList favorites;
	
	public MenuViewState() {
		this.projectsButton = new JButton("Projects");
		this.developersButton = new JButton("Developers");
		this.calendarButton = new JButton("Register time");
		
		JPanel favoritesPanel = new JPanel();
		this.favorites = new JList();
		this.favorites.add(new JLabel("Hello"));
		this.favorites.add(new JLabel("Hello"));
		favoritesPanel.add(new JLabel("My favorites:"));
		favoritesPanel.add(this.favorites);

		this.add(this.calendarButton);
		this.add(this.developersButton);
		this.add(this.projectsButton);
		this.add(favoritesPanel);
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
