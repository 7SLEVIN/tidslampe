package app;

import persistency.Database;
import utils.TimeService;
import view.ViewContainer;
import controller.ControllerCollection;
import factory.ViewControllerFactory;


public class Main {
	
	public static void main(String[] args) {
		Database database = new Database("dev_db.db");
		
		// Wire up
		TimeService timeService = new TimeService();
		ControllerCollection controllerCollection = new ControllerCollection(database, timeService);
		ViewContainer viewContainer = new ViewContainer();
		ViewControllerFactory.initialize(database, viewContainer, controllerCollection);
		
		
		// Bootstrap
		viewContainer.setViewState(ViewControllerFactory.CreateLoginViewController());
		new ProjectPlanner(database);
	}
}
