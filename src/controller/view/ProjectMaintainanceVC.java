package controller.view;

import java.util.List;

import persistency.Database;
import controller.ControllerCollection;
import view.ViewContainer;
import view.state.AbstractViewState;
import model.Activity;
import model.Assist;
import model.Developer;
import model.Project;

public class ProjectMaintainanceVC extends AbstractViewController {

	private Project project;
	
	public ProjectMaintainanceVC(Database database, ViewContainer viewContainer, ControllerCollection controllers, int projectID){
		super(database,viewContainer,controllers);
		this.project = this.database.project().read(projectID);
	}

	@Override
	public AbstractViewState getViewState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
	}

	public void assignManager(int devID){
		this.project.setManager(this.database.developer().read(devID));
	}
	
	public Project getProject(){
		return this.project;
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
