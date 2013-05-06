package controller.view;

import static org.junit.Assert.*;

import model.Developer;
import model.Project;

import org.junit.Test;

import controller.ControllerCollection;

import app.ProjectPlanner;

import persistency.BaseTestDatabase;
import view.ViewContainer;

public class TestProjectMaintainance extends BaseTestDatabase{
//TODO fjern denne test, den er GUI-agtig
	
	private ControllerCollection controllers;
	private ViewContainer viewContainer;
	
	private void init(){
		this.controllers = new ControllerCollection(this.db, this.projectPlanner.getTimeService());
		this.viewContainer = new ViewContainer();
		
		this.addProjects();
		this.addDevelopers();
	}
	
	@Test
	public void testAssignManager() {
		this.projectPlanner = new ProjectPlanner(this.db);
		this.init();
		
		assertEquals(this.db.project().readAll().size(), this.db.project().count());

		Project testProject = this.db.project().readAll().get(0);
		Developer developer = this.db.developer().readAll().get(0);
		
		int projectID = testProject.getId();
		Project project = this.db.project().read(projectID);
		
		assertEquals(testProject.getName(), project.getName());
		
		ProjectMaintainanceViewController projectMaintainance = new ProjectMaintainanceViewController(this.db, viewContainer, controllers, projectID);
		
		assertEquals(null, projectMaintainance.getProject().getManager());
		
		projectMaintainance.assignManager(developer);
		
		assertEquals(developer.getId(), projectMaintainance.getProject().getManager().getId());
	}
	
	@Test
	public void testSplitActivities(){
		this.projectPlanner = new ProjectPlanner(this.db);
		this.init();
		
		assertEquals(2, this.db.project().count());
		assertEquals(4, this.db.developer().count());
		
		Project testProject = this.db.project().readAll().get(0);
		int projectID = testProject.getId();
		ProjectMaintainanceViewController projectMaintainance = new ProjectMaintainanceViewController(this.db, viewContainer, controllers, projectID);
		
		assertEquals(0, projectMaintainance.getProject().getActivities().size());
		
		Long deadline = this.projectPlanner.getTimeService().convertToMillis(2013, 05, 13, 12, 0);
		projectMaintainance.addNewActivity("test-activity #1", 100, deadline-1000*3600*24*7*4, deadline);
		
		assertEquals(1, projectMaintainance.getProject().getActivities().size());
		
		int activityID = projectMaintainance.getProject().getActivities().get(0).getId();
		
		fail("Activities skal have developer relations");
	}
	
	@Test
	public void testAddDeveloper(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testRequestAssistance(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testAddDevelopersToActivity(){
		fail("Not yet implemented");
	}

}
