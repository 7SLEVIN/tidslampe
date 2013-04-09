package app;

import controller.LoginController;
import controller.ViewController;
import persistency.Database;
import view.ViewContainer;
import view.state.ViewState;

public class ProjectPlanner {
	
	private Database database;
	private TimeService timeService;
	private LoginController loginControl;
	
	
	/**
	 * 
	 */
	public ProjectPlanner() {
		this.timeService = new TimeService();
		this.database = new Database();
		this.loginControl = new LoginController(this.database,this.timeService);
		
	}
	
	public Database getDatabase(){
		return this.database;
	}
	
	public TimeService getTimeService(){
		return this.timeService;
	}
	
	public void setTimeService(TimeService ts){
		this.timeService = ts;
	}

}
