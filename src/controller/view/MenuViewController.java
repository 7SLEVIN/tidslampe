package controller.view;

import persistency.Database;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.MenuViewState;
import controller.view.AbstractViewController;

public class MenuViewController extends AbstractViewController {

	private MenuViewState viewState;
	
	public MenuViewController(Database database, ViewContainer viewContainer) {
		super(database, viewContainer);
		this.viewState = new MenuViewState();
	}

	@Override
	public AbstractViewState getViewState() {
		return viewState;
	}

	@Override
	public void initialize() {
		
	}

}
