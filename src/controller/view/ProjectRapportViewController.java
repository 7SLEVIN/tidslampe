package controller.view;

import java.util.ArrayList;
import java.util.List;

import model.Activity;
import model.ActivityDeveloperRelation;
import model.Project;
import model.TimeEntry;
import persistency.Database;
import utils.Query;
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
		this.viewState = new ProjectRapportViewState(this.project);
		
		this.viewState.getBackButton().addActionListener(new ChangeViewAction(this.viewContainer, ViewControllerFactory.CreateProjectMaintainanceViewController(this.project.getId())));
		
		this.setTimeUsed();
	}
	
	private void setTimeUsed() {
		long timeUsed = 0;
		for (TimeEntry entry : this.database.registerTime().readByProjectId(this.project.getId())) {
			timeUsed += entry.getDurationInMinutes();
		}
		this.viewState.setTimeUsed(timeUsed);
	}

}
