package usecases;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import model.Activity;
import model.Project;

import org.junit.Before;
import org.junit.Test;

import utils.Dialog;
import view.ViewContainer;
import view.state.ProjectMaintainanceViewState;
import controller.ControllerCollection;
import controller.view.BaseViewControllerTest;
import controller.view.ProjectMaintainanceViewController;
import controller.view.ViewControllerFactory;

public class TestCreateNewActivityUseCase extends BaseViewControllerTest {
	
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
		
		this.controllerCollection = new ControllerCollection(this.db, this.projectPlanner.getTimeService());
		ViewControllerFactory.initialize(this.db, new ViewContainer(), this.controllerCollection);
		this.controller = ViewControllerFactory.CreateProjectMaintainanceViewController(this.project.getId());
		this.controller.initialize();

		// Log in
		this.controllerCollection.getLoginController().login("JL");
	}

	private void mockViewState(String description, String hourBudget,
			String deadline) {
		// Mock view state
		this.viewState = mock(ProjectMaintainanceViewState.class);
		when(this.viewState.getNameInput()).thenReturn(description);
		when(this.viewState.getHourBudgetInput()).thenReturn(hourBudget);
		when(this.viewState.getDeadlineInput()).thenReturn(deadline);
		this.controller.setViewState(this.viewState);
		
		// Create new activity
		this.controller.addActivity();
	}
	
	@Test
	public void testMainScenario() {
		String description = "Swim with Moby Dick";
		String hourBudget = "123";
		String deadline = "22-04-2666";
		
		this.mockViewState(description, hourBudget, deadline);
		
		// Assert correct output
		List<Activity> found = this.db.activity().readByProjectId(this.project.getId());
		assertEquals(1, found.size());
		Activity actual = found.get(0);
		assertEquals(description, actual.getDescription());
		assertEquals(hourBudget, String.valueOf(actual.getHoursBudgeted()));
		assertEquals(deadline, this.projectPlanner.getTimeService().convertLongToString(actual.getEndTime()));
	}
	
	// Alternative scenarios
	@Test
	public void testEmptyDescription() {
		this.mockViewState("", "123", "22-04-2666");
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(this.dialog).showMessage("You must fill out all fields!");
	}

	@Test
	public void testEmptyHourBudget() {
		this.mockViewState("Swim with Moby Dick", "", "");
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(this.dialog).showMessage("You must fill out all fields!");
	}
	
	@Test
	public void testEmptyDeadline() {
		this.mockViewState("Swim with Moby Dick", "123", "");
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(this.dialog).showMessage("You must fill out all fields!");
	}
	
	@Test
	public void testNegativeHourBudget() {
		this.mockViewState("Swim with Moby Dick", "-1", "22-04-2666");
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(this.dialog).showMessage("Hour budget cannot be negative!");
	}
	
	@Test
	public void testPassedDeadline() {
		this.mockViewState("Swim with Moby Dick", "123", "01-01-1991");
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(this.dialog).showMessage("You can't make a deadline which is already passed");
	}
	
	@Test
	public void testInvalidDeadline() {
		this.mockViewState("Swim with Moby Dick", "123", "asdf");
		
		// Assert correct output
		assertEquals(0, this.db.activity().readByProjectId(this.project.getId()).size());
		verify(this.dialog).showMessage("Invalid date format, you must use dd-mm-yyyy");
	}
}
