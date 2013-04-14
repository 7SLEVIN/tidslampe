package app;

import persistency.Database;
import utils.TimeService;
import controller.LoginController;

public class ProjectPlanner {

	private Database database;
	private TimeService timeService;
	private LoginController loginControl;

	public ProjectPlanner(Database db) {
		this.database = db;
		this.timeService = new TimeService();
		this.loginControl = new LoginController(this.database, this.timeService);

		
	}

	public Database getDatabase() {
		return this.database;
	}

	public TimeService getTimeService() {
		return this.timeService;
	}

	public void setTimeService(TimeService ts) {
		this.timeService = ts;
	}

}
