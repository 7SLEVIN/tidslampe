package controller.view;

import persistency.Database;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.LoginViewState;
import view.state.ViewState;
import controller.action.ChangeViewAction;

/**
 * Is called by {@link command.SetViewStateCommand} to switch between view
 * states
 */
public class LoginViewController extends AbstractViewController {

	private LoginViewState viewState;
	
	public LoginViewController(Database database, ViewContainer viewContainer) {
		super(database, viewContainer);
		
		this.viewState = new LoginViewState();
		this.viewState.getLoginButton().addActionListener(new ChangeViewAction(this.viewContainer, ViewState.Menu));
		
	}

	@Override
	public AbstractViewState getViewState() {
		return viewState;
	}
}
