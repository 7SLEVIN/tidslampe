package view.state;

import javax.swing.JButton;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class CalendarViewState extends AbstractViewState {
	
	private JButton backButton;
	private JLabel developerNameLabel;

	public CalendarViewState() {
		this.backButton = new JButton("Back to menu");
		this.developerNameLabel = new JLabel();
		
		this.add(this.backButton);
		this.add(this.developerNameLabel);
	}

	public JButton getBackButton() {
		return this.backButton;
	}

	public void setDeveloperName(String name) {
		this.developerNameLabel.setText(name);
	}
}
