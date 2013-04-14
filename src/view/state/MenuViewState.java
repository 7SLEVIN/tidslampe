package view.state;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class MenuViewState extends AbstractViewState {
	private JButton developersButton;
	
	public MenuViewState() {
		this.developersButton = new JButton("Developers");
		
		this.add(this.developersButton);
	}

	public JButton getDevelopersButton() {
		return developersButton;
	}
}
