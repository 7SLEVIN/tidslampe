package controller.view;

import model.Project;
import persistency.Database;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.ProjectReportViewState;
import controller.ControllerCollection;
import controller.action.ChangeViewAction;


public class ProjectReportViewController extends AbstractViewController {
	private ProjectReportViewState viewState;
	private Project project;

	public ProjectReportViewController(Database database,
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
		this.viewState = new ProjectReportViewState(this.project.getName(),this.project.getActivities().size());
		
		this.viewState.getBackButton().addActionListener(new ChangeViewAction(this.viewContainer, ViewControllerFactory.CreateProjectMaintainanceViewController(this.project.getId())));

		this.setReportData();
	}
	
	private void setReportData(){
		//Setting the values for the report
		this.viewState.setPercentageComplete(this.project.getEstPercentageCompletion());
		this.viewState.setTimeRemaining(this.project.getEstHoursRemaining());
		this.viewState.hoursUnassigned(this.project.getHourBudget(),this.project.getHoursAllocatedToActivities());
		this.viewState.setChart(this.project.getEstHoursRemaining(), this.project.getHoursRegistered());
		this.viewState.setTimeUsed(this.project.getHoursRegistered());
	}
	
	

}
