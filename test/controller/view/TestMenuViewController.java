package controller.view;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import model.Developer;
import model.Project;

import org.junit.Before;

import utils.Dialog;
import view.ViewContainer;
import view.state.MenuViewState;

public class TestMenuViewController extends BaseViewControllerTest {
	
	private Developer developer;
	private Project project;
	private Dialog dialog;
	private ViewContainer viewContainer;
	private MenuViewController controller;
	private MenuViewState viewState;
	
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

	private void mockViewState() {
		this.viewState = mock(MenuViewState.class);
		this.controller.setViewState(this.viewState);
		this.controller.fillProjectList();
	}

//	@Test
//	public void testFillProjects() {
//		this.mockViewState();
//		
//		assertEquals(1, this.viewState.getProjects().size());
//	}

}
