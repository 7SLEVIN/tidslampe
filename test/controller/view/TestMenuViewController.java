package controller.view;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import model.Developer;
import model.Project;

import org.junit.Before;
import org.junit.Test;

import utils.Dialog;
import view.ViewContainer;
import view.state.MenuViewState;

public class TestMenuViewController extends BaseViewControllerTest {
	
	private Developer developer;
	private Project project;
	private Dialog dialog;
	private MenuViewController controller;
	
	@Before
	public void setUpTest() throws Exception {
		super.setUp();
		
		this.addProjects();
		this.addDevelopers();
		
		this.developer = this.db.developer().readByInitials("JL").get(0);
		this.project = this.db.project().readAll().get(0);
		assertNotNull(this.developer);
		assertNotNull(this.project);
		
		this.project.setManager(this.developer);
		
		
		this.dialog = mock(Dialog.class);
		Dialog.setInstance(this.dialog);

		this.controllerCollection.getLoginController().login(this.developer.getInitials());
		
		this.controller = ViewControllerFactory.CreateMenuViewController();
		this.controller.initialize();
	}

	@Test
	public void testFillProjects() {
		this.controller.fillProjectList();
		
		assertEquals(1, ((MenuViewState)this.controller.getViewState()).getProjects().size());
	}

	@Test
	public void testGotoNullProject() {
		MenuViewState viewState = mock(MenuViewState.class);
		when(viewState.getSelectedProject()).thenReturn(-1);
		this.controller.setViewState(viewState);
		
		this.controller.gotoProject();
		
		verify(this.dialog).showMessage("No project selected!");
	}

//	@Test
//	public void testGotoProject() {
//		ViewContainer viewContainer = mock(ViewContainer.class);
//		this.controller = ViewControllerFactory.CreateMenuViewController();
//		this.controller.initialize();
//		
//		MenuViewState viewState = mock(MenuViewState.class);
//		when(viewState.getSelectedProject()).thenReturn(-1);
//		this.controller.setViewState(viewState);
//		
//		this.controller.gotoProject();
//		
//		verify(viewContainer).setViewState((MenuViewState)any());
//	}

}
