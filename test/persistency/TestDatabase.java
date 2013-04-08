package persistency;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import app.Activity;
import app.ActivityDeveloperRelation;
import app.Assist;
import app.Developer;
import app.Project;

public class TestDatabase extends SetUpDatabase {

	@Test
	public void testRead() throws SQLException {
		Developer man = this.db.developer.create("MD", "Moby Dick");
		Developer dev = this.db.developer.read(man.getId());

		assertEquals("Developer name", "Moby Dick", dev.getName());
	}
	
	@Test
	public void testCreateProject() {
		Developer man = this.db.developer.create("MD", "Moby Dick");
		Project proj = this.db.project.create("Tidslampe", 666, 1337, man);
		
		assertEquals("Project name", "Tidslampe", proj.getName());
		assertEquals("Project hour budget", 666, proj.getHourBudget());
		assertEquals("Project deadline", 1337, proj.getDeadline());
		assertEquals("Project manager", "Moby Dick", proj.getManager().getName());
	}

	@Test
	public void testCreateDeveloper() {
		Developer dev = this.db.developer.create("MD", "Moby Dick");
		
		assertEquals("Developer initials", "MD", dev.getInitials());
		assertEquals("Developer name", "Moby Dick", dev.getName());
	}

	@Test
	public void testCreateActivity() throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-18");
		Activity activity = this.db.activity.create("Catch Moby Dick", 1.5, date, date);
		
		assertEquals("Activity description", "Catch Moby Dick", activity.getDescription());
		assertEquals("Activity expected time", 1.5, activity.getExpectedTime());
		assertEquals("Activity start time", date, activity.getStartTime());
		assertEquals("Activity end time", date, activity.getEndTime());
	}

	@Test
	public void testCreateActivityDeveloperRelation() throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-18");
		Activity activity = this.db.activity.create("Catch Moby Dick", 1.5, date, date);
		Developer dev = this.db.developer.create("MD", "Moby Dick");
		ActivityDeveloperRelation rel = this.db.activityDeveloperRelation.create(activity, 
				dev);
		
		assertEquals("ActivityDeveloperRelation activity", "Catch Moby Dick", 
				rel.getActivity().getDescription());
		assertEquals("ActivityDeveloperRelation developer", "Moby Dick", 
				rel.getDeveloper().getName());
	}

	@Test
	public void testCreateAssist() {
		Developer dev = this.db.developer.create("MD", "Moby Dick");
		Assist assist = this.db.assist.create(dev, 1.5);
		
		assertEquals("Assist developer", "Moby Dick", assist.getDeveloper().getName());
		assertEquals("Assist spent time", 1.5, assist.getSpentTime());
	}

}
