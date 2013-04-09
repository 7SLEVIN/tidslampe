package app;

import controller.LoginController;
import controller.ViewController;
import persistency.Database;
import view.ViewContainer;
import view.state.ViewState;

public class ProjectPlanner {
	
	public static void main(String[] args) {
		new ProjectPlanner();
	}
	
	private Database database;
	private LoginController loginControl;
	
	/**
	 * 
	 */
	public ProjectPlanner() {
		ViewContainer v = new ViewContainer();
		ViewController sc = new ViewController(v);
		sc.setViewState(ViewState.Menu);
	}
	
	

}
