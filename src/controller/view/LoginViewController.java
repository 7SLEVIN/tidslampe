package controller.view;

import java.awt.event.ActionListener;
import java.beans.EventHandler;

import persistency.Database;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.LoginViewState;

/**
 * Is called by {@link command.SetViewStateCommand} to switch between view
 * states
 */
public class LoginViewController extends AbstractViewController {

	private LoginViewState viewState;
	
	public LoginViewController(Database database, ViewContainer viewContainer) {
		super(database, viewContainer);
	}

	@Override
	public void initialize() {
		this.viewState = new LoginViewState();
		this.viewState.getLoginButton().addActionListener((ActionListener)EventHandler.create(ActionListener.class, this, "tryLogin"));
		this.viewState.setMessage("Hello, login here");
	}
	
	public void tryLogin() {
		
	}

	@Override
	public AbstractViewState getViewState() {
		return viewState;
	}
}
