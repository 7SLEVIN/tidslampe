package controller.view;

import persistency.Database;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.MenuViewState;
import controller.ControllerCollection;
import controller.action.ChangeViewAction;
import factory.ViewControllerFactory;

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
		this.viewState.getDevelopersButton().addActionListener(new ChangeViewAction(this.viewContainer, ViewControllerFactory.CreateDevelopersViewController()));
		int currentDeveloperId = this.controllers.getLoginController().getUser().getId();
		this.viewState.getCalendarButton().addActionListener(new ChangeViewAction(this.viewContainer, ViewControllerFactory.CreateCalendarViewController(currentDeveloperId)));
	}

}
