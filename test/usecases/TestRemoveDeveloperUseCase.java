package usecases;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import model.Activity;
import model.Developer;

import org.junit.Before;
import org.junit.Test;

import persistency.BaseTestDatabase;
import utils.Dialog;
import utils.DialogChoice;
import utils.TimeService;
import view.ViewContainer;
import view.state.DevelopersViewState;
import controller.ControllerCollection;
import controller.view.BaseViewControllerTest;
import controller.view.DevelopersViewController;
import controller.view.ViewControllerFactory;

public class TestRemoveDeveloperUseCase extends BaseViewControllerTest {
	private DevelopersViewController developerController;
	private Developer developer;
	private int totalDevelopers;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		Dialog.setInstance(new Dialog());
		
		this.addDevelopers();
		
		this.developerController = ViewControllerFactory.CreateDevelopersViewController();
		
		this.developer = this.db.developer().readByInitials("PM").get(0);
		this.totalDevelopers = this.db.developer().readAll().size();
	}
	
	//This should successfully remove a developer
	@Test
	public void testMainScenario() {
		Dialog.setDebugMode(true, DialogChoice.Yes);
		
		DevelopersViewController devsViewController = ViewControllerFactory.CreateDevelopersViewController();
		
		this.controllerCollection.getLoginController().login("JL");
		
		DevelopersViewState devsViewState = mock(DevelopersViewState.class);
		when(devsViewState.getSelectedDeveloper()).thenReturn(developer);
		devsViewController.setViewState(devsViewState);
		
		assertEquals(this.db.developer().readByInitials("PM").size(),1);
		
		//Add developer as manager on a project		
		int projectID = this.db.project().create("Test project", 42, "13-05-2014", developer).getId();
		assertEquals(this.db.project().read(projectID).getManager().getId(),developer.getId());
		
		
		//Add developer to an activity on the project
		Activity activity = this.db.activity().createProjectActivity(projectID, "An activity on the proejct", 2, 1337, 1337);
		activity.addDeveloper(developer);
		assertEquals(activity.getDevelopers().get(0).getId(), developer.getId());
		assertEquals(activity.getDevelopers().size(), 1);
		assertEquals(this.db.activity().readByDeveloperAndProjectId(projectID, developer.getId()).size(),1);
		assertEquals(this.db.project().readByDeveloper(developer.getId()).size(),1);
		assertEquals(this.db.activityDeveloperRelation().getRelationsOfActivity(activity.getId()).size(), 1);
		
		devsViewController.deleteSelectedDeveloper();
		
		//Is the developer removed from developers, projects and activities (and all possible relations)?
		assertEquals(this.db.developer().readByInitials("PM").size(),0);
		assertEquals(null,this.db.project().read(projectID).getManager());
		activity = this.db.activity().read(activity.getId());
		assertEquals(activity.getDevelopers().size(), 0);
		assertEquals(totalDevelopers-1, this.db.developer().readAll().size());
		assertEquals(this.db.activity().readByDeveloperAndProjectId(projectID, developer.getId()).size(),0);
		assertEquals(this.db.project().readByDeveloper(developer.getId()).size(),0);
		assertEquals(this.db.activityDeveloperRelation().getRelationsOfActivity(activity.getId()), null);
		
	}
	
	//Alt #1 This should not remove the developer
	@Test
	public void testDeleteMyself() {
		Dialog.setDebugMode(true, DialogChoice.Yes);
		
		controllerCollection.getLoginController().login("PM");
		
		DevelopersViewState devsViewState = mock(DevelopersViewState.class);
		when(devsViewState.getSelectedDeveloper()).thenReturn(developer);
		developerController.setViewState(devsViewState);
		
		assertEquals(this.db.developer().readByInitials("PM").size(),1);
		
		//Add developer as manager on a project		
		int projectID = this.db.project().create("Test project", 42, "13-05-2014", developer).getId();
		assertEquals(this.db.project().read(projectID).getManager().getId(),developer.getId());
		
		//Add developer to an activity on the project
		Activity activity = this.db.activity().createProjectActivity(projectID, "An activity on the proejct", 2, 1337, 1337);
		activity.addDeveloper(developer);
		assertEquals(activity.getDevelopers().get(0).getId(), developer.getId());
		assertEquals(activity.getDevelopers().size(), 1);
		
		developerController.deleteSelectedDeveloper();
		
		//Is the developer removed from developers, projects and activities?
		assertEquals(this.db.developer().readByInitials("PM").size(),1);
		assertEquals(developer.getId(),this.db.project().read(projectID).getManager().getId());
		activity = this.db.activity().read(activity.getId());
		assertEquals(activity.getDevelopers().size(), 1);
		assertEquals(totalDevelopers, this.db.developer().readAll().size());
	}
	
	//Alt #2 This should not remove a developer
	@Test
	public void testNoToDeletion() {
		Dialog.setDebugMode(true, DialogChoice.No);
		
		controllerCollection.getLoginController().login("JL");
		
		DevelopersViewState devsViewState = mock(DevelopersViewState.class);
		when(devsViewState.getSelectedDeveloper()).thenReturn(developer);
		developerController.setViewState(devsViewState);
		
		assertEquals(this.db.developer().readByInitials("PM").size(),1);
		
		//Add developer as manager on a project		
		int projectID = this.db.project().create("Test project", 42, "13-05-2014", developer).getId();
		assertEquals(this.db.project().read(projectID).getManager().getId(),developer.getId());
		
		
		//Add developer to an activity on the project
		Activity activity = this.db.activity().createProjectActivity(projectID, "An activity on the proejct", 2, 1337, 1337);
		activity.addDeveloper(developer);
		assertEquals(activity.getDevelopers().get(0).getId(), developer.getId());
		assertEquals(activity.getDevelopers().size(), 1);
		
		developerController.deleteSelectedDeveloper();
		
		//Is the developer removed from developers, projects and activities?
		assertEquals(this.db.developer().readByInitials("PM").size(),1);
		assertEquals(developer.getId(),this.db.project().read(projectID).getManager().getId());
		activity = this.db.activity().read(activity.getId());
		assertEquals(activity.getDevelopers().size(), 1);
		assertEquals(totalDevelopers, this.db.developer().readAll().size());
	}
	
	//Alt #3 This should not remove a developer
	@Test
	public void testNoDeveloperSelected() {
		Dialog.setDebugMode(true, DialogChoice.No);
		
		controllerCollection.getLoginController().login("JL");
		
		DevelopersViewState devsViewState = mock(DevelopersViewState.class);
		when(devsViewState.getSelectedDeveloper()).thenReturn(null);
		developerController.setViewState(devsViewState);
		
		assertEquals(this.db.developer().readByInitials("PM").size(),1);
		
		//Add developer as manager on a project		
		int projectID = this.db.project().create("Test project", 42, "13-05-2014", developer).getId();
		assertEquals(this.db.project().read(projectID).getManager().getId(),developer.getId());
		
		//Add developer to an activity on the project
		Activity activity = this.db.activity().createProjectActivity(projectID, "An activity on the proejct", 2, 1337, 1337);
		activity.addDeveloper(developer);
		assertEquals(activity.getDevelopers().get(0).getId(), developer.getId());
		assertEquals(activity.getDevelopers().size(), 1);
		
		developerController.deleteSelectedDeveloper();
		
		//Is the developer removed from developers, projects and activities?
		assertEquals(this.db.developer().readByInitials("PM").size(),1);
		assertEquals(developer.getId(),this.db.project().read(projectID).getManager().getId());
		activity = this.db.activity().read(activity.getId());
		assertEquals(activity.getDevelopers().size(), 1);
		assertEquals(totalDevelopers, this.db.developer().readAll().size());
	}

}
