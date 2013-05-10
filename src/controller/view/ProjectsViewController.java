package controller.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Developer;
import model.Project;
import persistency.Database;
import utils.ActionUtils;
import utils.Dialog;
import utils.DialogChoice;
import utils.TimeService;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.ProjectsViewState;
import controller.ControllerCollection;
import controller.action.ChangeViewAction;
import exceptions.DeleteNonExistingException;

public class ProjectsViewController extends AbstractViewController {

	private ProjectsViewState viewState;
	private TimeService timeService;

	public ProjectsViewController(Database database, ViewContainer viewContainer, ControllerCollection controllers) {
		super(database, viewContainer, controllers);
	}

	@Override
	public AbstractViewState getViewState() {
		return viewState;
	}

	@Override
	public void initialize() {
		this.viewState = new ProjectsViewState(); 
		this.timeService = new TimeService();
		
		ActionUtils.addListener(this.viewState.getDeleteButton(), this, "deleteSelectedProject");
		ActionUtils.addListener(this.viewState.getMaintainButton(), this, "maintainSelectedProject");
		ActionUtils.addListener(this.viewState.getCreateButton(), this, "createNewProject");

		this.viewState.getProjectTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					viewContainer.setViewState(ViewControllerFactory.CreateProjectMaintainanceViewController(viewState.getSelectedProject().getId()));
				}
			}
		});
		
		this.fillProjectList();
		this.fillManagerList();
	}

	private void fillProjectList() {
		this.viewState.setProjects(this.database.project().readAll());
	}
	
	private void fillManagerList() {
		this.viewState.setManagers(this.database.developer().readAll());
	}
	
	public void createNewProject() {
		String nameInput = this.viewState.getNameInput().trim();
		int hourBudgetInput = this.viewState.getHourBudgetInput();
		String deadlineInput = this.viewState.getDeadlineInput();
		Developer managerInput = this.viewState.getManagerInput();
		
		long milliDeadline = this.timeService.convertToMillis(deadlineInput);
		
		if (nameInput.length() == 0 ||
				hourBudgetInput <= 0) {
			Dialog.message("You must fill out all fields");
			return;
		}else if(milliDeadline < 0){
			return;
		}else{ 
//			int[] dValues = this.timeService.convertToValues(milliDeadline);
//			if(this.timeService.isDateValid(dValues[0],dValues[1],dValues[2],dValues[3],dValues[4])) 
//				return;
		}
		
		if (this.database.project().create(nameInput, hourBudgetInput, milliDeadline, managerInput) == null) {
			Dialog.message("Could not create project");
		}
		this.fillProjectList();
	}
	
	public void maintainSelectedProject() {
		Project sel = this.viewState.getSelectedProject();
		if (sel == null) 
			return;
		this.viewContainer.setViewState(ViewControllerFactory.CreateProjectMaintainanceViewController(sel.getId()));
	}
	
	public void deleteSelectedProject() {
		Project sel = this.viewState.getSelectedProject();
		if (sel == null) 
			return;
		
		DialogChoice confirm = Dialog.confirm(String.format("Really delete %s?", sel.getName()));
		if (confirm == DialogChoice.Yes) {
			try {
				sel.delete();
			} catch (DeleteNonExistingException e) {
				e.printStackTrace();
			}
			this.fillProjectList();
		}
	}

}
