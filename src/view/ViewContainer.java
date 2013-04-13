package view;

import java.awt.Dimension;
import java.security.InvalidParameterException;

import javax.swing.JFrame;

import persistency.Database;

import view.state.ViewState;

import controller.view.AbstractViewController;
import controller.view.LoginViewController;
import controller.view.MenuViewController;

/**
 * The main window inside which states are rendered
 */

@SuppressWarnings("serial")
public class ViewContainer extends JFrame {
	private Database database;
	private AbstractViewController currentViewController;
	
	public ViewContainer(Database database) {
		super("Tidslampe");
		
		this.database = database;
		
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
	public void setViewState(ViewState viewState) {
		if (this.getCurrentViewController() != null) {
			this.getCurrentViewController().getViewState().dispose();
		}
		
		AbstractViewController viewController = null;
		
		switch(viewState){
		case Login: 
			viewController = new LoginViewController(this.database, this); 
			break;
			
		case Menu: 
			viewController = new MenuViewController(this.database, this); 
			break;
			
		default: throw new InvalidParameterException("");
		}
		
		this.setContentPane(viewController.getViewState());
		viewController.getViewState().initialize();

		this.pack();
		
		this.currentViewController = viewController;
	}

	public AbstractViewController getCurrentViewController() {
		return currentViewController;
	}
}
