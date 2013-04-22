package controller.view;

import model.Project;
import persistency.Database;
import utils.ActionUtils;
import utils.Dialog;
import utils.DialogChoice;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.ProjectsViewState;
import controller.ControllerCollection;
import controller.action.ChangeViewAction;
import factory.ViewControllerFactory;

public class ProjectsViewController extends AbstractViewController {

	private ProjectsViewState viewState;

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
		
		ActionUtils.addListener(this.viewState.getDeleteButton(), this, "deleteSelectedProject");
//		ActionUtils.addListener(this.viewState.getCreateButton(), this, "createNewDeveloper");
		this.viewState.getBackButton().addActionListener(new ChangeViewAction(this.viewContainer, ViewControllerFactory.CreateMenuViewController()));

		this.fillProjectList();
		this.fillManagerList();
	}

	private void fillProjectList() {
		this.viewState.setProjects(this.database.project().readAll());
	}
	
	private void fillManagerList() {
		this.viewState.setManagers(this.database.developer().readAll());
	}
	
//	public void createNewProject() {
//		String initialsInput = this.viewState.getInitialsInput().trim();
//		String nameInput = this.viewState.getNameInput().trim();
//		
//		if (initialsInput.length() == 0 || nameInput.length() == 0) {
//			Dialog.message("You must fill out both initials and name");
//			return;
//		}
//		
//		if (this.database.developer().create(initialsInput, nameInput) == null) {
//			Dialog.message("Could not create developer");
//		}
//		this.fillDeveloperList();
//	}
	
	public void deleteSelectedProject() {
		Project sel = this.viewState.getSelectedProject();
		if (sel == null) 
			return;
		
		DialogChoice confirm = Dialog.confirm(String.format("Really delete %s?", sel.getName()));
		if (confirm == DialogChoice.Yes) {
			this.database.project().delete(sel.getId());
			this.fillProjectList();
		}
	}

}
