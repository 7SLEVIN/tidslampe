package persistency;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.Activity;
import model.ActivityDeveloperRelation;
import model.Assist;
import model.Developer;
import model.Project;

import org.junit.Test;


public class TestDatabase extends SetUpDatabase {
	
	@Test
	public void testReadAll() {
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
	public void testCreateProject() {
		Developer man = this.db.developer.create("MD", "Moby Dick");
		Project proj = this.db.project.create("Tidslampe", 666, 1337, man);
		Project actual = this.db.project.read(proj.getId());
		
		assertEquals("Project name", "Tidslampe", actual.getName());
		assertEquals("Project hour budget", 666, actual.getHourBudget());
		assertEquals("Project deadline", 1337, actual.getDeadline());
		assertEquals("Project manager", "Moby Dick", actual.getManager().getName());
	}

	@Test
	public void testCreateDeveloper() {
		Developer dev = this.db.developer.create("MD", "Moby Dick");
		Developer actual = this.db.developer.read(dev.getId());

		assertEquals("Developer name", "Moby Dick", actual.getName());
		assertEquals("Developer initials", "MD", actual.getInitials());
	}

	@Test
	public void testCreateActivity() throws ParseException {
		Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").parse("22.04.1991 12:23:45 CET");
		Activity activity = this.db.activity.create("Catch Moby Dick", 1.5, date, date);
		Activity actual = this.db.activity.read(activity.getId());
		
		assertEquals("Activity description", "Catch Moby Dick", actual.getDescription());
		assertEquals("Activity expected time", 1.5, actual.getExpectedTime());
		assertEquals("Activity start time", date, actual.getStartTime());
		assertEquals("Activity end time", date, actual.getEndTime());
	}

	@Test
	public void testCreateActivityDeveloperRelation() throws ParseException {
		Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").parse("22.04.1991 12:23:45 CET");
		Activity activity = this.db.activity.create("Catch Moby Dick", 1.5, date, date);
		Developer dev = this.db.developer.create("MD", "Moby Dick");
		ActivityDeveloperRelation rel = this.db.activityDeveloperRelation.create(activity, dev);
		ActivityDeveloperRelation actual = this.db.activityDeveloperRelation.read(rel.getId());
		
		assertEquals("ActivityDeveloperRelation activity", "Catch Moby Dick", 
				actual.getActivity().getDescription());
		assertEquals("ActivityDeveloperRelation developer", "Moby Dick", 
				actual.getDeveloper().getName());
	}

	@Test
	public void testCreateAssist() {
		Developer dev = this.db.developer.create("MD", "Moby Dick");
		Assist assist = this.db.assist.create(dev, 1.5);
		Assist actual = this.db.assist.read(assist.getId());
		
		assertEquals("Assist developer", "Moby Dick", actual.getDeveloper().getName());
		assertEquals("Assist spent time", 1.5, actual.getSpentTime());
	}

}
