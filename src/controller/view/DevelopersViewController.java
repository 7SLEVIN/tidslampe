package controller.view;

import javax.swing.JOptionPane;

import model.Developer;

import persistency.Database;
import utils.ActionUtils;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.DevelopersViewState;
import controller.view.AbstractViewController;

public class DevelopersViewController extends AbstractViewController {

	private DevelopersViewState viewState;
	
	public DevelopersViewController(Database database, ViewContainer viewContainer) {
		super(database, viewContainer);
	}

	@Override
	public AbstractViewState getViewState() {
		return viewState;
	}

	@Override
	public void initialize() {
		this.viewState = new DevelopersViewState();
		
		ActionUtils.addListener(this.viewState.getDeleteButton(), this, "deleteSelectedDeveloper");
		
		this.fillDeveloperList();
	}

	private void fillDeveloperList() {
		this.viewState.setDevelopers(this.database.Developer().readAll());
	}
	
	public void deleteSelectedDeveloper() {
		Developer sel = this.viewState.getSelectedDeveloper();
		if (sel == null) return;
		
		int confirm = JOptionPane.showConfirmDialog(null, String.format("Really delete %s?", sel.getName()));
		if (confirm == 0) {
			this.database.Developer().delete(sel.getId());
			this.fillDeveloperList();
		}
	}
}
