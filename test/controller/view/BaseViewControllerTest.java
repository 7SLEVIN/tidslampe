package controller.view;

import org.junit.Before;

import persistency.BaseTestDatabase;
import utils.TimeService;
import view.ViewContainer;
import controller.ControllerCollection;

public class BaseViewControllerTest extends BaseTestDatabase {
	
	protected ControllerCollection controllerCollection;

	@Before
	public void setUp() throws Exception {
		TimeService timeService = new TimeService();
		this.controllerCollection = new ControllerCollection(this.db, timeService);
		ViewContainer viewContainer = new ViewContainer();
		ViewControllerFactory.initialize(this.db, viewContainer, this.controllerCollection);
		
		super.setUp();
	}
}
