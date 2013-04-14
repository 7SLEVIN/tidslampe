package controller.view;

import persistency.Database;
import view.ViewContainer;
import view.state.AbstractViewState;

public abstract class AbstractViewController {
	protected final Database database;
	protected final ViewContainer viewContainer;
	
	public AbstractViewController(Database database, ViewContainer viewContainer) {
		this.database = database;
		this.viewContainer = viewContainer;
	}
	
	abstract public AbstractViewState getViewState();

	abstract public void initialize();
}
