package app;

import persistency.Database;
import view.ViewContainer;
import view.state.ViewState;
import controller.LoginController;

public class ProjectPlanner {

	private Database database;
	private TimeService timeService;
	private LoginController loginControl;

	public ProjectPlanner() {
		this.timeService = new TimeService();
		this.database = new Database();
		this.loginControl = new LoginController(this.database, this.timeService);

		ViewContainer v = new ViewContainer(this.database);
		v.setViewState(ViewState.Login);
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
