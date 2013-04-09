package view.state;

import javax.swing.JLabel;

/**
 * The main menu view state
 */
@SuppressWarnings("serial")
public class MenuViewState extends AbstractMenuViewState {

	private JLabel label;

	public MenuViewState() {
		// Label
		this.label = new JLabel();
		this.label.setAlignmentX(CENTER_ALIGNMENT);
		this.label.setText("Hi Karlsson Delight!");

		// Add to panel
		this.add(this.label);
	}
}