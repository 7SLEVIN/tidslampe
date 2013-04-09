package persistency;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import app.Activity;
import app.ActivityDeveloperRelation;
import app.Assist;
import app.Developer;
import app.Project;

public class TestDatabase extends SetUpDatabase {
	
	@Test
	public void testReadAll() throws SQLException {
		List<Developer> devs = this.db.developer.readAll();
		assertEquals("Empty readAll", 0, devs.size());
		this.db.developer.create("MD", "Moby Dick");
		
		devs = this.db.developer.readAll();
		assertEquals("Single readAll", 1, devs.size());
		this.db.developer.create("AW", "Alan Watts");
		this.db.developer.create("DK", "Donald Knuth");
		
		devs = this.db.developer.readAll();
		assertEquals("Multiple readAll", 3, devs.size());
	}
	
	@Test
	public void testCreateReadProject() throws SQLException {
		Developer man = this.db.developer.create("MD", "Moby Dick");
		Project proj = this.db.project.create("Tidslampe", 666, 1337, man);
		Project actual = this.db.project.read(proj.getId());
		
		assertEquals("Project name", "Tidslampe", actual.getName());
		assertEquals("Project hour budget", 666, actual.getHourBudget());
		assertEquals("Project deadline", 1337, actual.getDeadline());
		assertEquals("Project manager", "Moby Dick", actual.getManager().getName());
	}

	@Test
	public void testCreateReadDeveloper() throws SQLException {
		Developer dev = this.db.developer.create("MD", "Moby Dick");
		Developer actual = this.db.developer.read(dev.getId());

		assertEquals("Developer name", "Moby Dick", actual.getName());
		assertEquals("Developer initials", "MD", actual.getInitials());
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
