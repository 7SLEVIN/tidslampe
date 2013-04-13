package view.state;

import java.awt.Component;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class MenuViewState extends AbstractViewState {
	public MenuViewState() {
		JLabel label = new JLabel();
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setAlignmentY(Component.TOP_ALIGNMENT);
		label.setText("I am le menu!");
		
		this.add(label);
	}

}
