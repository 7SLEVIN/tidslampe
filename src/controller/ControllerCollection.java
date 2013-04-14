package controller;

import persistency.Database;
import utils.TimeService;

public class ControllerCollection {
	private LoginController loginController;
	
	public ControllerCollection(Database database, TimeService timeService) {
		this.loginController = new LoginController(database, timeService);
	}
	
	public LoginController getLoginController() {
		return loginController;
	}

}
