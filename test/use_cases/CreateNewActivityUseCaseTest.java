package use_cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.List;

import model.Activity;
import model.Developer;
import model.Project;

import org.junit.Before;
import org.junit.Test;

import persistency.BaseTestDatabase;
import utils.Dialog;
import utils.TimeService;
import view.ViewContainer;
import view.state.ProjectMaintainanceViewState;
import controller.ControllerCollection;
import controller.view.ProjectMaintainanceViewController;
import controller.view.ViewControllerFactory;

public class CreateNewActivityUseCaseTest extends BaseTestDatabase {
	
	private Project project;
	private Developer developer;
	private ControllerCollection controllerCollection;
	private ProjectMaintainanceViewController controller;
	private ProjectMaintainanceViewState viewState;

	@Before
	public void setUpUseCase() throws Exception {
		super.setUp();
		this.addDevelopers();
		this.addProjects();
		
		this.project = this.db.project().readAll().get(0);
		assertNotNull(this.project);
		
		this.developer = this.db.developer().readAll().get(0);
		assertNotNull(this.developer);
		
		this.controllerCollection = new ControllerCollection(this.db, new TimeService());
		ViewControllerFactory.initialize(this.db, new ViewContainer(), this.controllerCollection);
		this.controller = ViewControllerFactory.CreateProjectMaintainanceViewController(this.project.getId());
		this.controller.initialize();

		// Log in
		controllerCollection.getLoginController().login("JL");
	}
	
	@Test
	public void testMainScenario() {
		String description = "Swim with Moby Dick";
		int hourBudget = 123;
		String deadline = "22-04-2666";
		
		// Mock view state
		this.viewState = mock(ProjectMaintainanceViewState.class);
		when(this.viewState.getNameInput()).thenReturn(description);
		when(this.viewState.getHourBudgetInput()).thenReturn(String.valueOf(hourBudget));
		when(this.viewState.getDeadlineInput()).thenReturn(deadline);
		this.controller.setViewState(this.viewState);
		
		// Create new activity
		this.controller.addActivity();
		
		// Assert correct output
		List<Activity> found = this.db.activity().readByProjectId(this.project.getId());
		assertEquals(1, found.size());
		Activity actual = found.get(0);
		assertEquals(description, actual.getDescription());
		assertEquals(hourBudget, actual.getExpectedTime());
		assertEquals(deadline, this.projectPlanner.getTimeService().convertLongToString(actual.getEndTime()));
	}
	
	// Alternative scenarios:
	
	@Test
	public void testEmptyDescription() {
		String description = "";
		int hourBudget = 123;
		String deadline = "22-04-2666";
		
		// Mock view state
		this.viewState = mock(ProjectMaintainanceViewState.class);
		when(this.viewState.getNameInput()).thenReturn(description);
		when(this.viewState.getHourBudgetInput()).thenReturn(String.valueOf(hourBudget));
		when(this.viewState.getDeadlineInput()).thenReturn(deadline);
		this.controller.setViewState(this.viewState);
		Dialog dialog = mock(Dialog.class);
		Dialog.setInstance(dialog);
		
		// Create new activity
		this.controller.addActivity();
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(dialog).showMessage("You must fill out all fields!");
	}
	
	@Test
	public void testInvalidHourBudget() {
		String description = "Swim with Moby Dick";
		int hourBudget = -1;
		String deadline = "22-04-2666";
		
		// Mock view state
		this.viewState = mock(ProjectMaintainanceViewState.class);
		when(this.viewState.getNameInput()).thenReturn(description);
		when(this.viewState.getHourBudgetInput()).thenReturn(String.valueOf(hourBudget));
		when(this.viewState.getDeadlineInput()).thenReturn(deadline);
		this.controller.setViewState(this.viewState);
		Dialog dialog = mock(Dialog.class);
		Dialog.setInstance(dialog);
		
		// Create new activity
		this.controller.addActivity();
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(dialog).showMessage("Hour budget cannot be negative!");
	}
	
	@Test
	public void testEmptyDeadline() {
		String description = "Swim with Moby Dick";
		int hourBudget = 123;
		String deadline = "";
		
		// Mock view state
		this.viewState = mock(ProjectMaintainanceViewState.class);
		when(this.viewState.getNameInput()).thenReturn(description);
		when(this.viewState.getHourBudgetInput()).thenReturn(String.valueOf(hourBudget));
		when(this.viewState.getDeadlineInput()).thenReturn(deadline);
		this.controller.setViewState(this.viewState);
		Dialog dialog = mock(Dialog.class);
		Dialog.setInstance(dialog);
		
		// Create new activity
		this.controller.addActivity();
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(dialog).showMessage("You must fill out all fields!");
	}
	
	@Test
	public void testInvalidDeadline_1() {
		String description = "Swim with Moby Dick";
		int hourBudget = 123;
		String deadline = "asdf";
		
		// Mock view state
		this.viewState = mock(ProjectMaintainanceViewState.class);
		when(this.viewState.getNameInput()).thenReturn(description);
		when(this.viewState.getHourBudgetInput()).thenReturn(String.valueOf(hourBudget));
		when(this.viewState.getDeadlineInput()).thenReturn(deadline);
		this.controller.setViewState(this.viewState);
		Dialog dialog = mock(Dialog.class);
		Dialog.setInstance(dialog);
		
		// Create new activity
		this.controller.addActivity();
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(dialog).showMessage("Invalid date format, you must use dd-mm-yyyy");
	}
	
	@Test
	public void testInvalidDeadline_2() {
		String description = "Swim with Moby Dick";
		int hourBudget = 123;
		String deadline = "01-01-1991";
		
		// Mock view state
		this.viewState = mock(ProjectMaintainanceViewState.class);
		when(this.viewState.getNameInput()).thenReturn(description);
		when(this.viewState.getHourBudgetInput()).thenReturn(String.valueOf(hourBudget));
		when(this.viewState.getDeadlineInput()).thenReturn(deadline);
		this.controller.setViewState(this.viewState);
		Dialog dialog = mock(Dialog.class);
		Dialog.setInstance(dialog);
		
		// Create new activity
		this.controller.addActivity();
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(dialog).showMessage("You can't make a deadline which is already passed");
	}

}
