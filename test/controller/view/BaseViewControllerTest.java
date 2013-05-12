package controller.view;

import org.junit.Before;

import persistency.BaseTestDatabase;
import utils.TimeService;
import view.ViewContainer;
import controller.ControllerCollection;

public class BaseViewControllerTest extends BaseTestDatabase {
	
	@Before
	public void setUp() throws Exception {
		TimeService timeService = new TimeService();
		ControllerCollection controllerCollection = new ControllerCollection(this.db, timeService);
		ViewContainer viewContainer = new ViewContainer();
		ViewControllerFactory.initialize(this.db, viewContainer, controllerCollection);
		
		super.setUp();
	}
}
