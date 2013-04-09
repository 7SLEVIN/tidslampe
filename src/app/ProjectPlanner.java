package app;

import controller.ViewController;
import persistency.Database;
import view.ViewContainer;
import view.state.ViewState;

public class ProjectPlanner {
	
	public static void main(String[] args) {
		new ProjectPlanner();
	}
	
	private Database database;

	/**
	 * 
	 */
	public ProjectPlanner() {
		ViewContainer v = new ViewContainer();
		ViewController sc = new ViewController(v);
		sc.setViewState(ViewState.Menu);
	}
	
	

}
