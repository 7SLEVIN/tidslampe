package controller.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import model.Project;
import persistency.Database;
import utils.ActionUtils;
import utils.Dialog;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.MenuViewState;
import controller.ControllerCollection;
import controller.action.ChangeViewAction;

public class MenuViewController extends AbstractViewController {
	private MenuViewState viewState;
	
	public MenuViewController(Database database, ViewContainer viewContainer, ControllerCollection controllers) {
		super(database, viewContainer, controllers);
	}

	@Override
	public AbstractViewState getViewState() {
		return this.viewState;
	}

	@Override
	public void initialize() {
		this.viewState = new MenuViewState();
		int currentDeveloperId = this.controllers.getLoginController().getUser().getId();
		ActionUtils.addListener(this.viewState.getGotoProjectButton(), this, "gotoProject");
		ActionUtils.addListener(this.viewState.getLogoutButton(), this, "logout");

		this.viewState.getProjectList().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					viewContainer.setViewState(ViewControllerFactory.CreateProjectMaintainanceViewController(viewState.getSelectedProject()));
				}
			}
		});
		
		this.fillProjectList();
	}
	
	public void logout() {
		this.controllers.getLoginController().logout();
		this.viewContainer.setViewState(ViewControllerFactory.CreateLoginViewController());
	}
	
	private void fillProjectList() {
		int userId = this.controllers.getLoginController().getUser().getId();
		List<Project> projects = new ArrayList<Project>();
		for (Project project : this.database.project().readAll()) {
			if(project.getManager() == null) continue;
			else if (project.getManager().getId() == userId) projects.add(project);
		}
		this.viewState.setProjects(projects);
	}
	
	public void gotoProject() {
		int projectId = this.viewState.getSelectedProject();
		if (projectId == -1) {
			Dialog.message("No project selected!");
			return;
		}
		this.viewContainer.setViewState(ViewControllerFactory.CreateProjectMaintainanceViewController(projectId));
		
	}

}
