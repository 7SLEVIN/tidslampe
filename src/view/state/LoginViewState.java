package view.state;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * The main menu view state
 */
@SuppressWarnings("serial")
public class LoginViewState extends AbstractViewState {

	private JLabel label;
	private JButton button;
	private JTextField input;

	public LoginViewState() {
		this.label = new JLabel();
		this.label.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.label.setAlignmentY(Component.TOP_ALIGNMENT);
		
		this.input = new JTextField(5);
		
		this.button = new JButton("Login");
		

		// Add to panel
		this.add(this.label);
		this.add(this.input);
		this.add(this.button);
	}

	public JButton getLoginButton() {
		return this.button;
	}

	public String getInputText() {
		return this.input.getText();
	}

	public void setMessage(String msg) {
		this.label.setText(msg);
	}
}