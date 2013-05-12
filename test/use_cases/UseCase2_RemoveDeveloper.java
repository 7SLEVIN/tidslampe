package use_cases;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import model.Activity;
import model.Developer;

import org.junit.Test;

import persistency.BaseTestDatabase;
import utils.Dialog;
import utils.TimeService;
import view.ViewContainer;
import view.state.DevelopersViewState;
import controller.ControllerCollection;
import controller.view.DevelopersViewController;
import controller.view.ViewControllerFactory;

public class UseCase2_RemoveDeveloper extends BaseTestDatabase {

	private void init(){
		Dialog.setInstance(new Dialog());
		this.addDevelopers();
	}
	
	//This should successfully remove a developer
	@Test
	public void testMainScenario() {
		this.init();
		Dialog.setDebugMode(true,1);
		TimeService timeService = new TimeService();
		ControllerCollection controllerCollection = new ControllerCollection(this.db, timeService);
		ViewContainer viewContainer = new ViewContainer();
		ViewControllerFactory.initialize(this.db, viewContainer, controllerCollection);
		
		DevelopersViewController devsViewController = new DevelopersViewController(this.db, viewContainer, controllerCollection);
		
		controllerCollection.getLoginController().login("JL");
		
		Developer developer = this.db.developer().readByInitials("PM").get(0);
		int totalDevelopers = this.db.developer().readAll().size();
		
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
		this.init();
		Dialog.setDebugMode(true,1);
		TimeService timeService = new TimeService();
		ControllerCollection controllerCollection = new ControllerCollection(this.db, timeService);
		ViewContainer viewContainer = new ViewContainer();
		ViewControllerFactory.initialize(this.db, viewContainer, controllerCollection);
		
		DevelopersViewController devsViewController = new DevelopersViewController(this.db, viewContainer, controllerCollection);
		
		controllerCollection.getLoginController().login("PM");
		
		Developer developer2 = this.db.developer().readByInitials("PM").get(0);
		int totalDevelopers = this.db.developer().readAll().size();
		
		DevelopersViewState devsViewState = mock(DevelopersViewState.class);
		when(devsViewState.getSelectedDeveloper()).thenReturn(developer2);
		devsViewController.setViewState(devsViewState);
		
		assertEquals(this.db.developer().readByInitials("PM").size(),1);
		
//Add developer as manager on a project		
		int projectID = this.db.project().create("Test project", 42, "13-05-2014", developer2).getId();
		assertEquals(this.db.project().read(projectID).getManager().getId(),developer2.getId());
		
		
//Add developer to an activity on the project
		Activity activity = this.db.activity().createProjectActivity(projectID, "An activity on the proejct", 2, 1337, 1337);
		activity.addDeveloper(developer2);
		assertEquals(activity.getDevelopers().get(0).getId(), developer2.getId());
		assertEquals(activity.getDevelopers().size(), 1);
		
		devsViewController.deleteSelectedDeveloper();
		
//Is the developer removed from developers, projects and activities?
		assertEquals(this.db.developer().readByInitials("PM").size(),1);
		assertEquals(developer2.getId(),this.db.project().read(projectID).getManager().getId());
		activity = this.db.activity().read(activity.getId());
		assertEquals(activity.getDevelopers().size(), 1);
		assertEquals(totalDevelopers, this.db.developer().readAll().size());
	}
	
	//Alt #2 This should not remove a developer
	@Test
	public void testNoToDeletion() {
		this.init();
		Dialog.setDebugMode(true,-1);
		TimeService timeService = new TimeService();
		ControllerCollection controllerCollection = new ControllerCollection(this.db, timeService);
		ViewContainer viewContainer = new ViewContainer();
		ViewControllerFactory.initialize(this.db, viewContainer, controllerCollection);
		
		DevelopersViewController devsViewController = new DevelopersViewController(this.db, viewContainer, controllerCollection);
		
		controllerCollection.getLoginController().login("JL");
		
		Developer developer2 = this.db.developer().readByInitials("PM").get(0);
		int totalDevelopers = this.db.developer().readAll().size();
		
		DevelopersViewState devsViewState = mock(DevelopersViewState.class);
		when(devsViewState.getSelectedDeveloper()).thenReturn(developer2);
		devsViewController.setViewState(devsViewState);
		
		assertEquals(this.db.developer().readByInitials("PM").size(),1);
		
//Add developer as manager on a project		
		int projectID = this.db.project().create("Test project", 42, "13-05-2014", developer2).getId();
		assertEquals(this.db.project().read(projectID).getManager().getId(),developer2.getId());
		
		
//Add developer to an activity on the project
		Activity activity = this.db.activity().createProjectActivity(projectID, "An activity on the proejct", 2, 1337, 1337);
		activity.addDeveloper(developer2);
		assertEquals(activity.getDevelopers().get(0).getId(), developer2.getId());
		assertEquals(activity.getDevelopers().size(), 1);
		
		devsViewController.deleteSelectedDeveloper();
		
//Is the developer removed from developers, projects and activities?
		assertEquals(this.db.developer().readByInitials("PM").size(),1);
		assertEquals(developer2.getId(),this.db.project().read(projectID).getManager().getId());
		activity = this.db.activity().read(activity.getId());
		assertEquals(activity.getDevelopers().size(), 1);
		assertEquals(totalDevelopers, this.db.developer().readAll().size());
	}
	
	//Alt #3 This should not remove a developer
	@Test
	public void testNoDeveloperSelected() {
		this.init();
		Dialog.setDebugMode(true,1);
		TimeService timeService = new TimeService();
		ControllerCollection controllerCollection = new ControllerCollection(this.db, timeService);
		ViewContainer viewContainer = new ViewContainer();
		ViewControllerFactory.initialize(this.db, viewContainer, controllerCollection);
		
		DevelopersViewController devsViewController = new DevelopersViewController(this.db, viewContainer, controllerCollection);
		
		controllerCollection.getLoginController().login("JL");
		
		Developer developer2 = this.db.developer().readByInitials("PM").get(0);
		int totalDevelopers = this.db.developer().readAll().size();
		
		DevelopersViewState devsViewState = mock(DevelopersViewState.class);
		when(devsViewState.getSelectedDeveloper()).thenReturn(null);
		devsViewController.setViewState(devsViewState);
		
		assertEquals(this.db.developer().readByInitials("PM").size(),1);
		
//Add developer as manager on a project		
		int projectID = this.db.project().create("Test project", 42, "13-05-2014", developer2).getId();
		assertEquals(this.db.project().read(projectID).getManager().getId(),developer2.getId());
		
		
//Add developer to an activity on the project
		Activity activity = this.db.activity().createProjectActivity(projectID, "An activity on the proejct", 2, 1337, 1337);
		activity.addDeveloper(developer2);
		assertEquals(activity.getDevelopers().get(0).getId(), developer2.getId());
		assertEquals(activity.getDevelopers().size(), 1);
		
		devsViewController.deleteSelectedDeveloper();
		
//Is the developer removed from developers, projects and activities?
		assertEquals(this.db.developer().readByInitials("PM").size(),1);
		assertEquals(developer2.getId(),this.db.project().read(projectID).getManager().getId());
		activity = this.db.activity().read(activity.getId());
		assertEquals(activity.getDevelopers().size(), 1);
		assertEquals(totalDevelopers, this.db.developer().readAll().size());
	}

}
