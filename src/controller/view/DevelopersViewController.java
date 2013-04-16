package controller.view;

import javax.swing.JOptionPane;

import model.Developer;

import persistency.Database;
import utils.ActionUtils;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.DevelopersViewState;
import view.state.ViewState;
import controller.ControllerCollection;
import controller.action.ChangeViewAction;
import controller.view.AbstractViewController;

public class DevelopersViewController extends AbstractViewController {

	private DevelopersViewState viewState;
	
	public DevelopersViewController(Database database, ViewContainer viewContainer, ControllerCollection controllers) {
		super(database, viewContainer, controllers);
	}

	@Override
	public AbstractViewState getViewState() {
		return viewState;
	}

	@Override
	public void initialize() {
		this.viewState = new DevelopersViewState();

		ActionUtils.addListener(this.viewState.getDeleteButton(), this, "deleteSelectedDeveloper");
		ActionUtils.addListener(this.viewState.getCreateButton(), this, "createNewDeveloper");
		this.viewState.getBackButton().addActionListener(new ChangeViewAction(this.viewContainer, ViewState.Menu));
		
		this.fillDeveloperList();
	}

	private void fillDeveloperList() {
		this.viewState.setDevelopers(this.database.developer().readAll());
	}
	
	public void createNewDeveloper() {
		String initialsInput = this.viewState.getInitialsInput().trim();
		String nameInput = this.viewState.getNameInput().trim();
		
		if (initialsInput.length() == 0 || nameInput.length() == 0) {
			JOptionPane.showMessageDialog(null, "You must fill out both initials and name");
			return;
		}
		
		if (this.database.developer().create(initialsInput, nameInput) == null) {
			JOptionPane.showMessageDialog(null, "Could not create developer");
		}
		this.fillDeveloperList();
	}
	
	public void deleteSelectedDeveloper() {
		Developer sel = this.viewState.getSelectedDeveloper();
		if (sel == null) 
			return;
		if (sel.getId() == this.controllers.getLoginController().getUser().getId())
		{
			JOptionPane.showMessageDialog(null, "You can't delete yourself. Lol.");
			return;
		}
		
		int confirm = JOptionPane.showConfirmDialog(null, String.format("Really delete %s?", sel.getName()));
		if (confirm == 0) {
			this.database.developer().delete(sel.getId());
			this.fillDeveloperList();
		}
	}
}
