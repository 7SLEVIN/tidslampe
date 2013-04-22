package view;

import java.awt.Dimension;

import javax.swing.JFrame;

import controller.view.AbstractViewController;

/**
 * The main window inside which states are rendered
 */

@SuppressWarnings("serial")
public class ViewContainer extends JFrame {
	private AbstractViewController currentViewController;
	
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

	/**
	 * Changes the active view state
	 * 
	 * @param state
	 *            The new state to change to
	 */
	public void setViewState(AbstractViewController viewController) {
		if (this.currentViewController != null) {
			this.currentViewController.getViewState().dispose();
		}

		viewController.initialize();
		
		this.setContentPane(viewController.getViewState());

		this.pack();
		
		this.currentViewController = viewController;
	}

	public AbstractViewController getCurrentViewController() {
		return currentViewController;
	}
}
