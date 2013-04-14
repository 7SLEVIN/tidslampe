package controller.view;

import controller.ControllerCollection;
import controller.action.ChangeViewAction;
import persistency.Database;
import utils.ActionUtils;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.MenuViewState;
import view.state.ViewState;

public class MenuViewController extends AbstractViewController {
	private MenuViewState viewState;
	
	public MenuViewController(Database database, ViewContainer viewContainer, ControllerCollection controllers) {
		super(database, viewContainer, controllers);
	}

	@Override
	public AbstractViewState getViewState() {
		return this.viewState;
	}

	@Override
	public void initialize() {
		this.viewState = new MenuViewState();
		this.viewState.getDevelopersButton().addActionListener(new ChangeViewAction(this.viewContainer, ViewState.Developers));
	}

}
