package app;

import view.ViewContainer;
import view.state.ViewState;
import controller.ViewController;

public class Main {
	public static void main(String[] args) {
		new ProjectPlanner();
		ViewContainer v = new ViewContainer();
		ViewController sc = new ViewController(v);
		sc.setViewState(ViewState.Menu);
	}
	
}
