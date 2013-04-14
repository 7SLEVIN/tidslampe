package controller.view;

import java.awt.event.ActionListener;
import java.beans.EventHandler;

import controller.LoginController;

import persistency.Database;
import utils.TimeService;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.LoginViewState;
import view.state.ViewState;

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
		//XXX hax going on
		LoginController lc = new LoginController(this.database, new TimeService());
		if (lc.login(this.viewState.getInputText())) 
			this.viewContainer.setViewState(ViewState.Menu);
		else 
			this.viewState.setMessage("Invalid login");
	}

	@Override
	public AbstractViewState getViewState() {
		return viewState;
	}
}
