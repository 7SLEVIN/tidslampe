package controller.view;

import javax.swing.JOptionPane;

import persistency.Database;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.ProjectsViewState;
import view.state.ViewState;
import controller.ControllerCollection;
import controller.action.ChangeViewAction;

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
		
//		ActionUtils.addListener(this.viewState.getDeleteButton(), this, "deleteSelectedDeveloper");
//		ActionUtils.addListener(this.viewState.getCreateButton(), this, "createNewDeveloper");
		this.viewState.getBackButton().addActionListener(new ChangeViewAction(this.viewContainer, ViewState.Menu));
		
		this.fillDeveloperList();
	}

	private void fillDeveloperList() {
		this.viewState.setProjects(this.database.project().readAll());
	}
	
//	public void createNewDeveloper() {
//		String initialsInput = this.viewState.getInitialsInput().trim();
//		String nameInput = this.viewState.getNameInput().trim();
//		
//		if (initialsInput.length() == 0 || nameInput.length() == 0) {
//			JOptionPane.showMessageDialog(null, "You must fill out both initials and name");
//			return;
//		}
//		
//		if (this.database.developer().create(initialsInput, nameInput) == null) {
//			JOptionPane.showMessageDialog(null, "Could not create developer");
//		}
//		this.fillDeveloperList();
//	}

}
