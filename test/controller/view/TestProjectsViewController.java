package controller.view;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import model.Project;

import org.junit.Before;
import org.junit.Test;

import utils.Dialog;
import view.ViewContainer;
import view.state.ProjectsViewState;
import controller.ControllerCollection;

public class TestProjectsViewController extends BaseViewControllerTest {
	
	private Dialog dialog;
	private ViewContainer viewContainer;
	private ProjectsViewController controller;
	private ProjectsViewState viewState;
	
	@Before
	public void setUpTest() throws Exception {
		super.setUp();
		
		this.dialog = mock(Dialog.class);
		Dialog.setInstance(this.dialog);

		this.viewContainer = new ViewContainer();
		ControllerCollection controllerCollection = new ControllerCollection(this.db, this.projectPlanner.getTimeService());
		ViewControllerFactory.initialize(this.db, this.viewContainer, controllerCollection);
		this.controller = ViewControllerFactory.CreateProjectsViewController();
		this.controller.initialize();
		
		this.addProjects();
		this.addDevelopers();
	}

	private void mockViewState(String name, int hourBudget, String deadline) {
		this.viewState = mock(ProjectsViewState.class);
		when(this.viewState.getNameInput()).thenReturn(name);
		when(this.viewState.getHourBudgetInput()).thenReturn(hourBudget);
		when(this.viewState.getDeadlineInput()).thenReturn(deadline);
		this.controller.setViewState(this.viewState);
	}
	
	@Test
	public void testCreateProject() {
		this.mockViewState("Herro", 123, "12-12-2014");
		assertEquals(this.viewState, this.controller.getViewState());
		
		assertEquals(0, this.db.project().readAllWhereEquals("name", "Herro").size());
		
		this.controller.createNewProject();
		
		assertEquals(1, this.db.project().readAllWhereEquals("name", "Herro").size());
	}
	
	@Test
	public void testCreateProjectWithEmptyName() {
		this.mockViewState("", 123, "12-12-2014");
		
		assertEquals(0, this.db.project().readAllWhereEquals("name", "Herro").size());
		
		this.controller.createNewProject();
		
		assertEquals(0, this.db.project().readAllWhereEquals("name", "Herro").size());
		verify(this.dialog).showMessage("Could not create project");
	}
	
	@Test
	public void testMaintainSelectedProject() {
		Project project = this.db.project().readAll().get(0);

		this.viewContainer = mock(ViewContainer.class);
		this.viewState = mock(ProjectsViewState.class);
		when(this.viewState.getSelectedProject()).thenReturn(project);
		ControllerCollection controllerCollection = new ControllerCollection(this.db, this.projectPlanner.getTimeService());
		ViewControllerFactory.initialize(this.db, this.viewContainer, controllerCollection);
		this.controller = ViewControllerFactory.CreateProjectsViewController();
		this.controller.initialize();
		
		this.controller.setViewState(this.viewState);
		
		this.controller.maintainSelectedProject();
		
		verify(this.viewContainer).setViewState((ProjectMaintainanceViewController)any());
	}
	
	@Test
	public void testMaintainNullSelectedProject() {
		Project project = null;
		
		this.viewState = mock(ProjectsViewState.class);
		when(this.viewState.getSelectedProject()).thenReturn(project);
		this.controller.setViewState(this.viewState);
		
		this.controller.maintainSelectedProject();
		
		verify(this.dialog).showMessage("No project selected"); 
	}
	
	@Test
	public void testDeleteNullSelectedProject() {
		Project project = null;
		
		this.viewState = mock(ProjectsViewState.class);
		when(this.viewState.getSelectedProject()).thenReturn(project);
		this.controller.setViewState(this.viewState);
		
		this.controller.deleteSelectedProject();
		
		verify(this.dialog).showMessage("No project selected"); 
	}
	
	@Test
	public void testDeleteSelectedProject() {
		Project project = this.db.project().readAll().get(0);;
		
		this.viewState = mock(ProjectsViewState.class);
		when(this.viewState.getSelectedProject()).thenReturn(project);
		this.controller.setViewState(this.viewState);
		
		this.controller.deleteSelectedProject();
		
		assertFalse(this.db.project().readAll().contains(project));
	}

}
