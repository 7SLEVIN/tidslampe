package view;

import java.awt.Dimension;
import java.security.InvalidParameterException;

import javax.swing.JFrame;

import persistency.Database;

import view.state.ViewState;

import controller.ControllerCollection;
import controller.view.AbstractViewController;
import controller.view.LoginViewController;
import controller.view.DevelopersViewController;
import controller.view.MenuViewController;

/**
 * The main window inside which states are rendered
 */

@SuppressWarnings("serial")
public class ViewContainer extends JFrame {
	private Database database;
	private ControllerCollection controllers;
	private AbstractViewController currentViewController;
	
	public ViewContainer(Database database, ControllerCollection controllers) {
		super("Tidslampe");
		
		this.database = database;
		this.controllers = controllers;
		
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
		if (this.currentViewController != null) {
			this.currentViewController.getViewState().dispose();
		}
		
		AbstractViewController viewController = null;
		
		switch(viewState){
		case Login: 
			viewController = new LoginViewController(this.database, this, this.controllers); 
			break;
			
		case Developers: 
			viewController = new DevelopersViewController(this.database, this, this.controllers); 
			break;
			
		case Menu: 
			viewController = new MenuViewController(this.database, this, this.controllers); 
			break;
			
		default: throw new InvalidParameterException("");
		}

		viewController.initialize();
		
		this.setContentPane(viewController.getViewState());

		this.pack();
		
		this.currentViewController = viewController;
	}

	public AbstractViewController getCurrentViewController() {
		return currentViewController;
	}

	public ControllerCollection getControllers() {
		return controllers;
	}
}
