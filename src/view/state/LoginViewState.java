package view.state;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utils.GuiUtils;
import utils.TextFieldWithPrompt;
import view.ViewContainer;

/**
 * The main menu view state
 */
@SuppressWarnings("serial")
public class LoginViewState extends AbstractViewState {

	private JLabel label;
	private JButton button;
	private JTextField input;

	public LoginViewState() {
		this.label = new JLabel("Hello, login here:");
		this.input = new TextFieldWithPrompt(5, "Initials");
		this.button = new JButton("Login");
		
		JPanel airPanel = new JPanel();
		GuiUtils.setSize(airPanel, new Dimension(ViewContainer.WINDOW_WIDTH,200));
		
		JPanel welcomePanel = new JPanel();
		GuiUtils.setSize(welcomePanel, new Dimension(ViewContainer.WINDOW_WIDTH, 30));
		
		JPanel panel = new JPanel();
		GuiUtils.setSize(panel, new Dimension(200, 200));

		welcomePanel.add(this.label);
		panel.add(this.input);
		panel.add(this.button);;

		this.add(airPanel);
		this.add(welcomePanel);
		this.add(panel);
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
	
	public JTextField getInputField() {
		return this.input;
	}
}