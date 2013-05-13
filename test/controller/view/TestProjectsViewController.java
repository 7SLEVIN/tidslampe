package controller.view;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import view.ViewContainer;
import controller.ControllerCollection;

public class TestProjectsViewController extends BaseViewControllerTest {
	
	private ControllerCollection controllers;
	private ViewContainer viewContainer;
	
	@Before
	public void setUpTest() throws Exception {
		super.setUp();

		this.controllers = new ControllerCollection(this.db, this.projectPlanner.getTimeService());
		this.viewContainer = new ViewContainer();
		
		this.addProjects();
		this.addDevelopers();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
