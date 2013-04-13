package view.state;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * The main menu view state
 */
@SuppressWarnings("serial")
public class LoginViewState extends AbstractViewState {

	private JLabel label;
	private JButton button;

	public LoginViewState() {
		// Label
		this.label = new JLabel();
		this.label.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.label.setAlignmentY(Component.TOP_ALIGNMENT);
		this.label.setText("Hi Karlsson Delight!");

		// Button
		this.button = new JButton("Login");
		

		// Add to panel
		this.add(this.label);
		this.add(this.button);
	}

	public JButton getLoginButton() {
		return this.button;
	}

	public void setLastLogin(String msg) {

	}
}