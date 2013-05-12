package controller.view;

import persistency.Database;
import view.ViewContainer;
import view.state.ProjectMaintainanceViewState;
import controller.ControllerCollection;

public class ViewControllerFactory {

	private static Database database;
	private static ViewContainer viewContainer;
	private static ControllerCollection controllerCollection;

	public static void initialize(Database _database, ViewContainer _viewContainer, ControllerCollection _controllerCollection) {
		database = _database;
		viewContainer = _viewContainer;
		controllerCollection = _controllerCollection;
	}
	
	public static CalendarViewController CreateCalendarViewController(int developerId) {
		return new CalendarViewController(database, viewContainer, controllerCollection, developerId);
	}
	
	public static DevelopersViewController CreateDevelopersViewController() {
		return new DevelopersViewController(database, viewContainer, controllerCollection);
	}
	
	public static LoginViewController CreateLoginViewController() {
		return new LoginViewController(database, viewContainer, controllerCollection);
	}
	
	public static MenuViewController CreateMenuViewController() {
		return new MenuViewController(database, viewContainer, controllerCollection);
	}
	
	public static ProjectMaintainanceViewController CreateProjectMaintainanceViewController(int projectId){
		return new ProjectMaintainanceViewController(database, viewContainer, controllerCollection, projectId);
	}
	
	public static ProjectsViewController CreateProjectsViewController() {
		return new ProjectsViewController(database, viewContainer, controllerCollection);
	}
	
	public static ProjectReportViewController CreateProjectRapportViewController(int rapportId) {
		return new ProjectReportViewController(database, viewContainer, controllerCollection, rapportId);
	}

}
