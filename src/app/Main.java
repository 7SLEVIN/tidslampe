package app;

import controller.ControllerCollection;
import persistency.Database;
import utils.TimeService;
import view.ViewContainer;
import view.state.ViewState;


public class Main {
	
	public static void main(String[] args) {
		Database database = new Database("dev_db.db");
		
		// Wire up
		TimeService timeService = new TimeService();
		ControllerCollection controllers = new ControllerCollection(database, timeService);
		ViewContainer viewContainer = new ViewContainer(database, controllers);
		viewContainer.setViewState(ViewState.Login);
		
		// Bootstrap
		new ProjectPlanner(database);
	}
	
}
