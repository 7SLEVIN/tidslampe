package app;

import persistency.Database;
import utils.TimeService;

public class ProjectPlanner {

	private Database database;
	private TimeService timeService;

	public ProjectPlanner(Database db) {
		this.database = db;
		this.timeService = new TimeService();
		
		this.database.project().create("test", 3, 666);
		this.database.project().create("test2", 3, 666);
		
		System.out.println(this.database.project().readAll().size());
		System.out.println(this.database.project().count());
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
