package controller.view;

import static org.junit.Assert.*;

import org.junit.Test;

import persistency.BaseTestDatabase;
import persistency.Database;
import utils.TimeService;
import view.ViewContainer;
import controller.ControllerCollection;

public class TestViewControllerFactory extends BaseTestDatabase {

	@Test
	public void testCreateViewControllers() {
		// set up
		TimeService timeService = new TimeService();
		
		ControllerCollection controllerCollection = new ControllerCollection(this.db, timeService);
		ViewContainer viewContainer = new ViewContainer();
		ViewControllerFactory.initialize(this.db, viewContainer, controllerCollection);
		
		// initialize
		this.addProjects();
		this.addDevelopers();
		controllerCollection.getLoginController().login("PM");
		
		// assert
		assertNotNull(ViewControllerFactory.CreateCalendarViewController(1));
		assertNotNull(ViewControllerFactory.CreateDevelopersViewController());
		assertNotNull(ViewControllerFactory.CreateLoginViewController());
		assertNotNull(ViewControllerFactory.CreateMenuViewController());
		assertNotNull(ViewControllerFactory.CreateProjectMaintainanceViewController(1));
		assertNotNull(ViewControllerFactory.CreateProjectRapportViewController(1));
		assertNotNull(ViewControllerFactory.CreateProjectsViewController());
	}
}
