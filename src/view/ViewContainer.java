package view;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * The main window inside which states are rendered
 */

@SuppressWarnings("serial")
public class ViewContainer extends JFrame {
	public ViewContainer() {
		super("Tidslampe");
		this.setResizable(false);
		this.setSize(new Dimension(500, 500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setIconImage(SpriteHandler.getInstance().get("alien.png").getImage());
		this.setVisible(true);
		this.setFocusable(true);
		this.requestFocusInWindow();
		//this.addKeyListener(Input.getInstance());
	}
}
