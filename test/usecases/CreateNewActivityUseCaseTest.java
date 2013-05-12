package usecases;

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
import controller.view.BaseViewControllerTest;
import controller.view.ProjectMaintainanceViewController;
import controller.view.ViewControllerFactory;

public class CreateNewActivityUseCaseTest extends BaseViewControllerTest {
	
	private Project project;
	private ProjectMaintainanceViewController controller;
	private ProjectMaintainanceViewState viewState;
	private Dialog dialog;

	@Before
	public void setUp() throws Exception {
		this.dialog = mock(Dialog.class);
		Dialog.setInstance(this.dialog);
		
		super.setUp();
		this.addDevelopers();
		this.addProjects();
		
		this.project = this.db.project().readAll().get(0);
		
		this.controllerCollection = new ControllerCollection(this.db, new TimeService());
		ViewControllerFactory.initialize(this.db, new ViewContainer(), this.controllerCollection);
		this.controller = ViewControllerFactory.CreateProjectMaintainanceViewController(this.project.getId());
		this.controller.initialize();

		// Log in
		this.controllerCollection.getLoginController().login("JL");
	}
	
	@Test
	public void testMainScenario() {
		String description = "Swim with Moby Dick";
		int hourBudget = 123;
		String deadline = "22-04-2666";
		
		this.mockViewState(description, hourBudget, deadline);
		
		// Assert correct output
		List<Activity> found = this.db.activity().readByProjectId(this.project.getId());
		assertEquals(1, found.size());
		Activity actual = found.get(0);
		assertEquals(description, actual.getDescription());
		assertEquals(hourBudget, actual.getExpectedTime());
		assertEquals(deadline, this.projectPlanner.getTimeService().convertLongToString(actual.getEndTime()));
	}
	
	// Alternative scenarios
	@Test
	public void testEmptyDescription() {
		this.mockViewState("", 123, "22-04-2666");
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(this.dialog).showMessage("You must fill out all fields!");
	}
	
	@Test
	public void testInvalidHourBudget() {
		this.mockViewState("Swim with Moby Dick", -1, "22-04-2666");
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(this.dialog).showMessage("Hour budget cannot be negative!");
	}
	
	@Test
	public void testEmptyDeadline() {
		this.mockViewState("Swim with Moby Dick", 123, "");
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(this.dialog).showMessage("You must fill out all fields!");
	}
	
	@Test
	public void testInvalidDeadline_1() {
		this.mockViewState("Swim with Moby Dick", 123, "asdf");
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(this.dialog).showMessage("Invalid date format, you must use dd-mm-yyyy");
	}
	
	@Test
	public void testInvalidDeadline_2() {
		this.mockViewState("Swim with Moby Dick", 123, "01-01-1991");
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(this.dialog).showMessage("You can't make a deadline which is already passed");
	}

	private void mockViewState(String description, int hourBudget,
			String deadline) {
		// Mock view state
		this.viewState = mock(ProjectMaintainanceViewState.class);
		when(this.viewState.getNameInput()).thenReturn(description);
		when(this.viewState.getHourBudgetInput()).thenReturn(String.valueOf(hourBudget));
		when(this.viewState.getDeadlineInput()).thenReturn(deadline);
		this.controller.setViewState(this.viewState);
		
		// Create new activity
		this.controller.addActivity();
	}
}
