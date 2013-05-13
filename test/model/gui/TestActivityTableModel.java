package model.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.Activity;

import org.junit.Before;
import org.junit.Test;

import persistency.BaseTestDatabase;

public class TestActivityTableModel extends BaseTestDatabase {
	
	private List<Activity> activities;
	private ActivityTableModel model;
	
	@Before
	public void setUpTest() throws Exception {
		super.setUp();
		this.addDevelopers();
		this.addProjects();
		this.addActivities();
		this.activities = this.db.activity().readAll();
		this.model = new ActivityTableModel(this.activities); 
		assertTrue(this.activities.size() > 0);
	}
	
	@Test
	public void testNullGetValueAt() {
		ActivityTableModel newModel = new ActivityTableModel(new ArrayList<Activity>()); 
		assertNull(newModel.getValueAt(0, 0));
	}
	
	@Test
	public void testGetValueAt() {
		for (int i = 0; i < this.activities.size(); i++) {
			assertEquals(this.activities.get(i).getDescription(), this.model.getValueAt(i, 0));	
			assertEquals(this.activities.get(i).getExpectedTime(), this.model.getValueAt(i, 1));	
			assertEquals(this.activities.get(i).getHoursRegistered(), this.model.getValueAt(i, 2));	
			assertEquals(this.projectPlanner.getTimeService().convertCalendarToInputString(this.activities.get(i).getEndCalendar()), 
					this.model.getValueAt(i, 3));	
			assertEquals(this.activities.get(i).getAllDevsInitials(), this.model.getValueAt(i, 4));	
			assertNull(this.model.getValueAt(i, 5));	
		}
	}
	
	@Test
	public void testIsCellEditable() {
		assertFalse(this.model.isCellEditable(0, 0));
	}

}
