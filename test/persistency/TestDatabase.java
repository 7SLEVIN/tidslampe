package persistency;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Activity;
import model.ActivityDeveloperRelation;
import model.Assist;
import model.Developer;
import model.Project;

import org.junit.Test;


public class TestDatabase extends SetUpDatabase {
	
	@Test
	public void testReadAll() {
		List<Developer> devs = this.db.getDeveloper().readAll();
		assertEquals("Empty readAll", 0, devs.size());
		this.db.getDeveloper().create("MD", "Moby Dick");
		
		devs = this.db.getDeveloper().readAll();
		assertEquals("Single readAll", 1, devs.size());
		this.db.getDeveloper().create("AW", "Alan Watts");
		this.db.getDeveloper().create("DK", "Donald Knuth");
		
		devs = this.db.getDeveloper().readAll();
		assertEquals("Multiple readAll", 3, devs.size());
	}
	
	@Test
	public void testCount() {
		assertEquals("Database count", 0, this.db.getDeveloper().count());
		Developer man = this.db.getDeveloper().create("MD", "Moby Dick");
		assertEquals("Database count", 1, this.db.getDeveloper().count());
		Developer man2 = this.db.getDeveloper().create("RS", "Richard Stallman");
		assertEquals("Database count", 2, this.db.getDeveloper().count());
		this.db.getDeveloper().delete(man.getId());
		this.db.getDeveloper().delete(man2.getId());
		assertEquals("Database count", 0, this.db.getDeveloper().count());
	}
	
	@Test
	public void testCreateProject() {
		Developer man = this.db.getDeveloper().create("MD", "Moby Dick");
		Project proj = this.db.getProject().create("Tidslampe", 666, 1337, man);
		Project actual = this.db.getProject().read(proj.getId());
		
		assertEquals("Project name", "Tidslampe", actual.getName());
		assertEquals("Project hour budget", 666, actual.getHourBudget());
		assertEquals("Project deadline", 1337, actual.getDeadline());
		assertEquals("Project manager", "Moby Dick", actual.getManager().getName());
	}

	@Test
	public void testCreateDeveloper() {
		Developer dev = this.db.getDeveloper().create("MD", "Moby Dick");
		Developer actual = this.db.getDeveloper().read(dev.getId());

		assertEquals("Developer name", "Moby Dick", actual.getName());
		assertEquals("Developer initials", "MD", actual.getInitials());
	}

	@Test
	public void testCreateActivity() throws ParseException {
		Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").parse("22.04.1991 12:23:45 CET");
		Activity activity = this.db.getActivity().create("Catch Moby Dick", 1.5, date, date);
		Activity actual = this.db.getActivity().read(activity.getId());
		
		assertEquals("Activity description", "Catch Moby Dick", actual.getDescription());
		assertEquals("Activity expected time", 1.5, actual.getExpectedTime());
		assertEquals("Activity start time", date, actual.getStartTime());
		assertEquals("Activity end time", date, actual.getEndTime());
	}

	@Test
	public void testCreateActivityDeveloperRelation() throws ParseException {
		Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").parse("22.04.1991 12:23:45 CET");
		Activity activity = this.db.getActivity().create("Catch Moby Dick", 1.5, date, date);
		Developer dev = this.db.getDeveloper().create("MD", "Moby Dick");
		ActivityDeveloperRelation rel = this.db.getActivityDeveloperRelation().create(activity, dev);
		ActivityDeveloperRelation actual = this.db.getActivityDeveloperRelation().read(rel.getId());
		
		assertEquals("ActivityDeveloperRelation activity", "Catch Moby Dick", 
				actual.getActivity().getDescription());
		assertEquals("ActivityDeveloperRelation developer", "Moby Dick", 
				actual.getDeveloper().getName());
	}

	@Test
	public void testCreateAssist() {
		Developer dev = this.db.getDeveloper().create("MD", "Moby Dick");
		Assist assist = this.db.getAssist().create(dev, 1.5);
		Assist actual = this.db.getAssist().read(assist.getId());
		
		assertEquals("Assist developer", "Moby Dick", actual.getDeveloper().getName());
		assertEquals("Assist spent time", 1.5, actual.getSpentTime());
	}
	
	@Test
	public void testUpdateProject() {
		Developer man = this.db.getDeveloper().create("MD", "Moby Dick");
		Developer newMan = this.db.getDeveloper().create("RS", "Richard Stallman");
		Project proj = this.db.getProject().create("Tidslampe", 666, 1337, man);
		
		proj.setName("Lampekost");
		proj.setHourBudget(123);
		proj.setDeadline(321);
		proj.setManager(newMan);
		
		this.db.getProject().update(proj);
		Project actual = this.db.getProject().read(proj.getId());
		
		assertEquals("Project name", "Lampekost", actual.getName());
		assertEquals("Project hour budget", 123, actual.getHourBudget());
		assertEquals("Project deadline", 321, actual.getDeadline());
		assertEquals("Project manager", "Richard Stallman", actual.getManager().getName());
	}

	@Test
	public void testUpdateDeveloper() {
		Developer dev = this.db.getDeveloper().create("MD", "Moby Dick");
		
		dev.setInitials("RS");
		dev.setName("Richard Stallman");
		
		this.db.getDeveloper().update(dev);
		Developer actual = this.db.getDeveloper().read(dev.getId());

		assertEquals("Developer initials", "RS", actual.getInitials());
		assertEquals("Developer name", "Richard Stallman", actual.getName());
	}

	@Test
	public void testUpdateActivity() throws ParseException {
		Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").parse("22.04.1991 12:23:45 CET");
		Date newDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").parse("2.07.1981 22:13:55 CET");
		Activity activity = this.db.getActivity().create("Catch Moby Dick", 1.5, date, date);

		activity.setDescription("Leave Moby Dick alone");
		activity.setExpectedTime(666);
		activity.setStartTime(newDate);
		activity.setEndTime(newDate);
		
		this.db.getActivity().update(activity);
		Activity actual = this.db.getActivity().read(activity.getId());
		
		assertEquals("Activity description", "Leave Moby Dick alone", actual.getDescription());
		assertEquals("Activity expected time", 666.0, actual.getExpectedTime());
		assertEquals("Activity start time", newDate, actual.getStartTime());
		assertEquals("Activity end time", newDate, actual.getEndTime());
	}

	@Test
	public void testUpdateActivityDeveloperRelation() throws ParseException {
		Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").parse("22.04.1991 12:23:45 CET");
		Date newDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").parse("2.07.1981 22:13:55 CET");
		Activity activity = this.db.getActivity().create("Catch Moby Dick", 1.5, date, date);
		Developer dev = this.db.getDeveloper().create("MD", "Moby Dick");
		Activity newActivity = this.db.getActivity().create("Leave Moby Dick alone", 666.0, newDate, newDate);
		Developer newDev = this.db.getDeveloper().create("RS", "Richard Stallman");
		ActivityDeveloperRelation rel = this.db.getActivityDeveloperRelation().create(activity, dev);
		
		rel.setActivity(newActivity);
		rel.setDeveloper(newDev);
		
		this.db.getActivityDeveloperRelation().update(rel);
		ActivityDeveloperRelation actual = this.db.getActivityDeveloperRelation().read(rel.getId());
		
		assertEquals("ActivityDeveloperRelation activity", "Leave Moby Dick alone", 
				actual.getActivity().getDescription());
		assertEquals("ActivityDeveloperRelation developer", "Richard Stallman", 
				actual.getDeveloper().getName());
	}

	@Test
	public void testUpdateAssist() {
		Developer dev = this.db.getDeveloper().create("MD", "Moby Dick");
		Developer newDev = this.db.getDeveloper().create("RS", "Richard Stallman");
		Assist assist = this.db.getAssist().create(dev, 1.5);
		
		assist.setDeveloper(newDev);
		assist.setSpentTime(666.0);
		
		this.db.getAssist().update(assist);
		Assist actual = this.db.getAssist().read(assist.getId());
		
		assertEquals("Assist developer", "Richard Stallman", actual.getDeveloper().getName());
		assertEquals("Assist spent time", 666.0, actual.getSpentTime());
	}
	
//	@Test
//	public void testUpdateNonexisting() {
//		this.db.developer.update(new Developer(-1, "MD", "Moby Dick"));
//	}
	
	@Test
	public void testDeleteProject() {
		Developer man = this.db.getDeveloper().create("MD", "Moby Dick");
		Project proj = this.db.getProject().create("Tidslampe", 666, 1337, man);
		Project actual = this.db.getProject().read(proj.getId());
		
		assertEquals("Project name", "Tidslampe", actual.getName());
		assertEquals("Project hour budget", 666, actual.getHourBudget());
		assertEquals("Project deadline", 1337, actual.getDeadline());
		assertEquals("Project manager", "Moby Dick", actual.getManager().getName());
		
		this.db.getProject().delete(actual.getId());
		
		assertEquals("Project count", 0, this.db.getProject().readAll().size());
	}

	@Test
	public void testDeleteDeveloper() {
		Developer dev = this.db.getDeveloper().create("MD", "Moby Dick");
		Developer actual = this.db.getDeveloper().read(dev.getId());

		assertEquals("Developer name", "Moby Dick", actual.getName());
		assertEquals("Developer initials", "MD", actual.getInitials());
		
		this.db.getDeveloper().delete(actual.getId());
		
		assertEquals("Developer count", 0, this.db.getDeveloper().readAll().size());
	}

	@Test
	public void testDeleteActivity() throws ParseException {
		Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").parse("22.04.1991 12:23:45 CET");
		Activity activity = this.db.getActivity().create("Catch Moby Dick", 1.5, date, date);
		Activity actual = this.db.getActivity().read(activity.getId());
		
		assertEquals("Activity description", "Catch Moby Dick", actual.getDescription());
		assertEquals("Activity expected time", 1.5, actual.getExpectedTime());
		assertEquals("Activity start time", date, actual.getStartTime());
		assertEquals("Activity end time", date, actual.getEndTime());
		
		this.db.getActivity().delete(actual.getId());
		
		assertEquals("Activity count", 0, this.db.getActivity().readAll().size());
	}

	@Test
	public void testDeleteActivityDeveloperRelation() throws ParseException {
		Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").parse("22.04.1991 12:23:45 CET");
		Activity activity = this.db.getActivity().create("Catch Moby Dick", 1.5, date, date);
		Developer dev = this.db.getDeveloper().create("MD", "Moby Dick");
		ActivityDeveloperRelation rel = this.db.getActivityDeveloperRelation().create(activity, dev);
		ActivityDeveloperRelation actual = this.db.getActivityDeveloperRelation().read(rel.getId());
		
		assertEquals("ActivityDeveloperRelation activity", "Catch Moby Dick", 
				actual.getActivity().getDescription());
		assertEquals("ActivityDeveloperRelation developer", "Moby Dick", 
				actual.getDeveloper().getName());
		
		this.db.getActivityDeveloperRelation().delete(actual.getId());
		
		assertEquals("ActivityDeveloperRelation count", 0, this.db.getActivityDeveloperRelation().readAll().size());
	}

	@Test
	public void testDeleteAssist() {
		Developer dev = this.db.getDeveloper().create("MD", "Moby Dick");
		Assist assist = this.db.getAssist().create(dev, 1.5);
		Assist actual = this.db.getAssist().read(assist.getId());
		
		assertEquals("Assist developer", "Moby Dick", actual.getDeveloper().getName());
		assertEquals("Assist spent time", 1.5, actual.getSpentTime());
		
		this.db.getAssist().delete(actual.getId());
		
		assertEquals("Assist count", 0, this.db.getAssist().readAll().size());
	}
	
}
