package controller.view;

import persistency.Database;
import utils.ActionUtils;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.LoginViewState;
import controller.ControllerCollection;

public class LoginViewController extends AbstractViewController {

	private LoginViewState viewState;
	
	public LoginViewController(Database database, ViewContainer viewContainer, ControllerCollection controllers) {
		super(database, viewContainer, controllers);
	}

	@Override
	public void initialize() {
		this.viewState = new LoginViewState();
		
		ActionUtils.addListener(this.viewState.getLoginButton(), this, "tryLogin");
		this.viewState.setMessage("Hello, login here");
	}
	
	public void tryLogin() {
		if (this.controllers.getLoginController().login(this.viewState.getInputText())) 
			this.viewContainer.setViewState(ViewControllerFactory.CreateMenuViewController());
		else 
			this.viewState.setMessage("Invalid login");
	}

	@Override
	public AbstractViewState getViewState() {
		return viewState;
	}
}
