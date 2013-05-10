package controller.view;

import model.Project;
import persistency.Database;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.ProjectRapportViewState;
import controller.ControllerCollection;
import controller.action.ChangeViewAction;


public class ProjectRapportViewController extends AbstractViewController {
	private ProjectRapportViewState viewState;
	private Project project;

	public ProjectRapportViewController(Database database,
			ViewContainer viewContainer, 
			ControllerCollection controllers,
			int projectId) {
		super(database, viewContainer, controllers);
		this.project = this.database.project().read(projectId);
	}

	@Override
	public AbstractViewState getViewState() {
		return this.viewState;
	}

	@Override
	public void initialize() {
		this.viewState = new ProjectRapportViewState(this.project.getName(),this.project.getActivities().size());
		
		this.viewState.getBackButton().addActionListener(new ChangeViewAction(this.viewContainer, ViewControllerFactory.CreateProjectMaintainanceViewController(this.project.getId())));

		this.setReportData();
	}
	
	private void setReportData(){
		//Setting the values for the report
		this.viewState.setPercentageComplete(this.project.getEstPercentageCompletion());
		this.viewState.setTimeRemaining(this.project.getEstHoursRemaining());
		this.viewState.hoursUnassigned(this.project.getHourBudget(),this.project.getHoursAssignedToActivities());
		this.viewState.setChart(this.project.getEstHoursRemaining(), this.project.getHoursRegistered());
		this.viewState.setTimeUsed(this.project.getHoursRegistered());
		this.viewState.setTimeAssignedToActivities(this.project.getHoursAssignedToActivities());
	}
	
	

}
