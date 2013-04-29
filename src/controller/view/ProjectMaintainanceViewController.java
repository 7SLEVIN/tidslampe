package controller.view;

import java.util.List;

import model.Activity;
import model.Developer;
import model.Project;
import persistency.Database;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.ProjectMaintainanceViewState;
import controller.ControllerCollection;
import controller.action.ChangeViewAction;
import factory.ViewControllerFactory;

public class ProjectMaintainanceViewController extends AbstractViewController {

	private Project project;
	private ProjectMaintainanceViewState viewState;
	
	public ProjectMaintainanceViewController(Database database, ViewContainer viewContainer, ControllerCollection controllers, int projectID){
		super(database,viewContainer,controllers);
		this.project = this.database.project().read(projectID);
	}

	@Override
	public AbstractViewState getViewState() {
		return viewState;
	}

	@Override
	public void initialize() {
		this.viewState = new ProjectMaintainanceViewState(); 
		this.viewState.getBackButton().addActionListener(new ChangeViewAction(this.viewContainer, ViewControllerFactory.CreateMenuViewController()));
		
		this.viewState.setProjectName(this.project.getName());
		this.fillActivityList();
		// TODO Auto-generated method stub
	}
//TODO 
	public void assignManager(int devID){
		this.project.setManager(this.database.developer().read(devID));
	}
	
	public Project getProject(){
		return this.project;
	}
	
	private void fillActivityList() {
		this.viewState.setActivities(this.database.activity().readAllWhereEquals("project_id", this.project.getId()));
	}
	
	public void addNewActivity(String description, int expectedTime, long startTime, long endTime){
		this.database.activity().createProjectActivity(this.project.getId(), description, expectedTime, startTime, endTime);
	}
	
	public void splitActivity(int activityID){
		Activity activity = this.database.activity().read(activityID);
		String description = activity.getDescription();
		int expectedTime = activity.getExpectedTime();
//		private List<Assist> assists;
		long startTime = activity.getStartTime();
		long endTime = activity.getEndTime();
		List<Developer> developers = activity.getDevelopers();
		
		
		
	}
	
	
}
