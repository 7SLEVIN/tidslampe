package app;

import persistency.Database;
import utils.TimeService;

public class ProjectPlanner {

	private Database database;
	private TimeService timeService;

	public ProjectPlanner(Database db) {
		this.database = db;
		this.timeService = new TimeService();
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
