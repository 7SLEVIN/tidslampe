package use_cases;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.List;

import model.Developer;

import org.junit.Before;
import org.junit.Test;

import persistency.BaseTestDatabase;
import utils.Dialog;
import utils.TimeService;
import view.ViewContainer;
import view.state.DevelopersViewState;
import controller.ControllerCollection;
import controller.view.DevelopersViewController;
import controller.view.ViewControllerFactory;

public class AddDeveloperUseCaseTest extends BaseTestDatabase {

	private ControllerCollection controllerCollection;
	private DevelopersViewController controller;
	private DevelopersViewState viewState;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.addDevelopers();
		
		this.controllerCollection = new ControllerCollection(this.db, new TimeService());
		ViewControllerFactory.initialize(this.db, new ViewContainer(), this.controllerCollection);
		this.controller = ViewControllerFactory.CreateDevelopersViewController();

		// Log in
		controllerCollection.getLoginController().login("JL");
		assertEquals(0, this.db.developer().readByInitials("MD").size());
	}
	
	@Test
	public void testMainScenario() {
		String initials = "MD";
		String name = "Moby Dick";
		
		// Mock view state
		this.viewState = mock(DevelopersViewState.class);
		when(this.viewState.getInitialsInput()).thenReturn(initials);
		when(this.viewState.getNameInput()).thenReturn(name);
		this.controller.setViewState(this.viewState);
		
		// Create the developer
		this.controller.createNewDeveloper();
		
		// Assert correct result
		List<Developer> found = this.db.developer().readByInitials(initials);
		assertEquals(1, found.size());
		Developer actual = found.get(0);
		assertEquals(initials, actual.getInitials());
		assertEquals(name, actual.getName());
	}
	
	// Alternative scenarios:

	@Test
	public void testEmptyInitials() {
		String initials = "";
		String name = "Moby Dick";
		
		// Mock view state
		DevelopersViewState viewState = mock(DevelopersViewState.class);
		when(viewState.getInitialsInput()).thenReturn(initials);
		when(viewState.getNameInput()).thenReturn(name);
		controller.setViewState(viewState);
		Dialog dialog = mock(Dialog.class);
		Dialog.setInstance(dialog);
		
		// Create the developer
		controller.createNewDeveloper();
		
		// Assert correct result
		assertEquals(0, this.db.developer().readByInitials(initials).size());
		verify(dialog).showMessage("You must fill out both initials and name");
	}

	@Test
	public void testEmptyName() {
		String initials = "MD";
		String name = "";
		
		// Mock view state
		DevelopersViewState viewState = mock(DevelopersViewState.class);
		when(viewState.getInitialsInput()).thenReturn(initials);
		when(viewState.getNameInput()).thenReturn(name);
		controller.setViewState(viewState);
		Dialog dialog = mock(Dialog.class);
		Dialog.setInstance(dialog);
		
		// Create the developer
		controller.createNewDeveloper();
		
		// Assert correct result
		assertEquals(0, this.db.developer().readByInitials(initials).size());
		verify(dialog).showMessage("You must fill out both initials and name");
	}
}
