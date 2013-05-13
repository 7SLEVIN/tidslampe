package usecases;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import model.Developer;

import org.junit.Before;
import org.junit.Test;

import utils.Dialog;
import view.state.DevelopersViewState;
import controller.view.BaseViewControllerTest;
import controller.view.DevelopersViewController;
import controller.view.ViewControllerFactory;

public class TestAddDeveloperUseCase extends BaseViewControllerTest {

	private DevelopersViewController controller;
	private DevelopersViewState viewState;
	private Dialog dialog;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.addDevelopers();
		
		this.controller = ViewControllerFactory.CreateDevelopersViewController();

		// Mock dialog
		dialog = mock(Dialog.class);
		Dialog.setInstance(dialog);
		
		// Log in
		controllerCollection.getLoginController().login("JL");
		assertEquals(0, this.db.developer().readByInitials("MD").size());
	}

	private void mockDeveloperView(String initials, String name) {
		this.viewState = mock(DevelopersViewState.class);
		when(this.viewState.getInitialsInput()).thenReturn(initials);
		when(this.viewState.getNameInput()).thenReturn(name);
		this.controller.setViewState(this.viewState);

		// Create the developer
		this.controller.createNewDeveloper();
	}
	
	@Test
	public void testMainScenario() {
		String initials = "MD";
		String name = "Moby Dick";
		
		List<Developer> preFound = this.db.developer().readByInitials(initials);
		assertEquals(0, preFound.size());
		int developerCount = this.db.developer().readAll().size();
		
		// Mock view state
		this.mockDeveloperView(initials, name);
		
		// Assert correct result
		List<Developer> found = this.db.developer().readByInitials(initials);
		assertEquals(1, found.size());
		Developer actual = found.get(0);
		assertEquals(initials, actual.getInitials());
		assertEquals(name, actual.getName());
		assertEquals(developerCount+1, this.db.developer().readAll().size());
	}
	
	// Alternative scenarios:

	@Test
	public void testEmptyInitials() {
		String initials = "";
		
		int developerCount = this.db.developer().readAll().size();
		
		// Mock view state
		this.mockDeveloperView(initials, "Moby Dick");
		
		// Assert correct result
		assertEquals(0, this.db.developer().readByInitials(initials).size());
		verify(dialog).showMessage("You must fill out both initials and name");
		assertEquals(developerCount, this.db.developer().readAll().size());
	}

	@Test
	public void testEmptyName() {
		String initials = "MD";
		int developerCount = this.db.developer().readAll().size();
		
		// Mock view state
		this.mockDeveloperView(initials, "");
		
		// Assert correct result
		assertEquals(0, this.db.developer().readByInitials(initials).size());
		verify(dialog).showMessage("You must fill out both initials and name");
		assertEquals(developerCount, this.db.developer().readAll().size());
	}
	


	@Test
	public void testTooManyInitials() {
		String initials = "12345";
		int developerCount = this.db.developer().readAll().size();
		
		// Mock view state
		this.mockDeveloperView(initials, "Moby Dick");
		
		// Assert correct result
		assertEquals(0, this.db.developer().readByInitials(initials).size());
		verify(dialog).showMessage("The length of initials can at most be 4.");
		assertEquals(developerCount, this.db.developer().readAll().size());
	}
}
