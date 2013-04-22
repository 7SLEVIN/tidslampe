package controller.view;

import persistency.Database;
import controller.ControllerCollection;
import view.ViewContainer;
import view.state.AbstractViewState;
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
	
	
	
	
	
}
