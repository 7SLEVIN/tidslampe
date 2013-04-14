package app;

import persistency.Database;
import view.ViewContainer;
import view.state.ViewState;


public class Main {
	
	public static void main(String[] args) {
		Database database = new Database("dev_db.db");
		ViewContainer viewContainer = new ViewContainer(database);
		viewContainer.setViewState(ViewState.Login);
		
		new ProjectPlanner(database);
	}
	
}
