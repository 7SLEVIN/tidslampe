package controller.view;

import controller.ControllerCollection;
import persistency.Database;
import view.ViewContainer;
import view.state.AbstractViewState;

public abstract class AbstractViewController {
	protected final Database database;
	protected final ViewContainer viewContainer;
	protected final ControllerCollection controllers;
	
	public AbstractViewController(Database database, ViewContainer viewContainer, ControllerCollection controllers) {
		this.database = database;
		this.viewContainer = viewContainer;
		this.controllers = controllers;
	}
	
	abstract public AbstractViewState getViewState();

	abstract public void initialize();
}
