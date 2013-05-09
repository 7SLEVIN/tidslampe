package controller.view;

import model.Project;
import model.TimeEntry;
import persistency.Database;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.ProjectRapportViewState;
import controller.ControllerCollection;
import controller.action.ChangeViewAction;
import factory.ViewControllerFactory;


public class ProjectRapportViewController extends AbstractViewController {
	private ProjectRapportViewState viewState;
	private Project project;
	private int timeUsed;
	private float percentage;
	private int timeRemaining;

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
		
		this.setTimeUsed();
		this.setEstimatedPercentage();
		this.setTimeRemaining();
		this.setTimeChart();
	}
	
	private void setEstimatedPercentage(){
		this.percentage = this.timeUsed*100 / (float)(this.project.getHourBudget());
		this.viewState.setPercentageComplete(this.percentage);
	}
	
	private void setTimeRemaining(){
		this.timeRemaining = (int) ((100.0f - this.percentage)/100.0f*this.project.getHourBudget());
		this.viewState.setTimeRemaining(this.timeRemaining);
	}
	
	private void setTimeChart(){
		this.viewState.setChart(this.timeRemaining, this.timeUsed);
	}
	
	
	private void setTimeUsed() {
		this.timeUsed = 0;
		for (TimeEntry entry : this.database.registerTime().readByProjectId(this.project.getId())) {
			this.timeUsed += entry.getDurationInMinutes();
		}
		this.timeUsed /= 60;
		this.viewState.setTimeUsed(this.timeUsed);
	}
	
	

}
