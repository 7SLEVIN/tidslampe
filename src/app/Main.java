package app;

import persistency.Database;
import view.ViewContainer;
import view.state.ViewState;


public class Main {
	private static Database database;
	
	public static void main(String[] args) {
		database = new Database();
		
		ViewContainer v = new ViewContainer(database);
		v.setViewState(ViewState.Login);
		new ProjectPlanner(database);
	}
	
}
