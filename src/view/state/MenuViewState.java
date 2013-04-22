package view.state;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class MenuViewState extends AbstractViewState {
	private JButton developersButton;
	private JButton calendarButton;
	
	public MenuViewState() {
		this.developersButton = new JButton("Developers");
		this.calendarButton = new JButton("Register time");

		this.add(this.developersButton);
		this.add(this.calendarButton);
	}

	public JButton getDevelopersButton() {
		return this.developersButton;
	}

	public JButton getCalendarButton() {
		return this.calendarButton;
	}
}
