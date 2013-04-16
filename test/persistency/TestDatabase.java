package persistency;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.List;

import model.Activity;
import model.ActivityDeveloperRelation;
import model.Assist;
import model.Developer;
import model.Project;

import org.junit.Test;

import utils.TimeService;


public class TestDatabase extends BaseTestDatabase {
	
	@Test
	public void testReadAll() {
		List<Developer> devs = this.db.developer().readAll();
		assertEquals("Empty readAll", 0, devs.size());
		this.db.developer().create("MD", "Moby Dick");
		
		devs = this.db.developer().readAll();
		assertEquals("Single readAll", 1, devs.size());
		this.db.developer().create("AW", "Alan Watts");
		this.db.developer().create("DK", "Donald Knuth");
		
		devs = this.db.developer().readAll();
		assertEquals("Multiple readAll", 3, devs.size());
	}
	
	@Test
	public void testCount() {
		assertEquals("Database count", 0, this.db.developer().count());
		Developer man = this.db.developer().create("MD", "Moby Dick");
		assertEquals("Database count", 1, this.db.developer().count());
		Developer man2 = this.db.developer().create("RS", "Richard Stallman");
		assertEquals("Database count", 2, this.db.developer().count());
		this.db.developer().delete(man.getId());
		this.db.developer().delete(man2.getId());
		assertEquals("Database count", 0, this.db.developer().count());
	}
	
	@Test
	public void testCreateProject() {
		Developer man = this.db.developer().create("MD", "Moby Dick");
		Project proj = this.db.project().create("Tidslampe", 666, 1337, man);
		Project actual = this.db.project().read(proj.getId());
		
		assertEquals("Project name", "Tidslampe", actual.getName());
		assertEquals("Project hour budget", 666, actual.getHourBudget());
		assertEquals("Project deadline", 1337, actual.getDeadline());
		assertEquals("Project manager", "Moby Dick", actual.getManager().getName());
	}

	@Test
	public void testCreateDeveloper() {
		Developer dev = this.db.developer().create("MD", "Moby Dick");
		Developer actual = this.db.developer().read(dev.getId());

		assertEquals("Developer name", "Moby Dick", actual.getName());
		assertEquals("Developer initials", "MD", actual.getInitials());
	}

	@Test
	public void testCreateActivity() throws ParseException {
		TimeService timeService = new TimeService();
		int year = 1991;		int month = 4;		int day = 22;		int hour = 12;		int minute = 30;
		long startTime = timeService.convertToMillis(year, month, day, hour, minute);
		hour = 14;
		long endTime = timeService.convertToMillis(year, month, day, hour, minute);
		
		Activity activity = this.db.activity().createProjectActivity(1,"Catch Moby Dick", 2, startTime, endTime);
		Activity actual = this.db.activity().read(activity.getId());
		
		assertEquals("Activity description", "Catch Moby Dick", actual.getDescription());
		assertEquals("Activity description", "Catch Moby Dick", activity.getDescription());
		assertEquals("Activity expected time", 2, actual.getExpectedTime());
		long actualStart = actual.getStartTime();
		assertEquals("Activity start time", startTime, actualStart);
		long actualEnd = actual.getEndTime();
		assertEquals("Activity end time", endTime, actualEnd);
	}

	@Test
	public void testCreateActivityDeveloperRelation() throws ParseException {
		TimeService timeService = new TimeService();
		int year = 1991;		int month = 4;		int day = 22;		int hour = 12;		int minute = 30;
		long startTime = timeService.convertToMillis(year, month, day, hour, minute);
		hour = 14;
		long endTime = timeService.convertToMillis(year, month, day, hour, minute);
		
		Activity activity = this.db.activity().createProjectActivity(1,"Catch Moby Dick", 2, startTime, endTime);
		Developer dev = this.db.developer().create("MD", "Moby Dick");
		ActivityDeveloperRelation rel = this.db.activityDeveloperRelation().create(activity, dev);
		ActivityDeveloperRelation actual = this.db.activityDeveloperRelation().read(rel.getId());
		
		assertEquals("ActivityDeveloperRelation activity", "Catch Moby Dick", 
				actual.getActivity().getDescription());
		assertEquals("ActivityDeveloperRelation developer", "Moby Dick", 
				actual.getDeveloper().getName());
	}

	@Test
	public void testCreateAssist() {
		Developer dev = this.db.developer().create("MD", "Moby Dick");
		Assist assist = this.db.assist().create(dev, 1.5);
		Assist actual = this.db.assist().read(assist.getId());
		
		assertEquals("Assist developer", "Moby Dick", actual.getDeveloper().getName());
		assertEquals("Assist spent time", 1.5, actual.getSpentTime());
	}
	
	@Test
	public void testUpdateProject() {
		Developer man = this.db.developer().create("MD", "Moby Dick");
		Developer newMan = this.db.developer().create("RS", "Richard Stallman");
		Project proj = this.db.project().create("Tidslampe", 666, 1337, man);
		
		proj.setName("Lampekost");
		proj.setHourBudget(123);
		proj.setDeadline(321);
		proj.setManager(newMan);
		
		Project actual = this.db.project().read(proj.getId());
		
		assertEquals("Project name", "Lampekost", actual.getName());
		assertEquals("Project hour budget", 123, actual.getHourBudget());
		assertEquals("Project deadline", 321, actual.getDeadline());
		assertEquals("Project manager", "Richard Stallman", actual.getManager().getName());
	}

	@Test
	public void testUpdateDeveloper() {
		Developer dev = this.db.developer().create("MD", "Moby Dick");
		
		dev.setInitials("RS");
		dev.setName("Richard Stallman");
		
		this.db.developer().update(dev);
		Developer actual = this.db.developer().read(dev.getId());

		assertEquals("Developer initials", "RS", actual.getInitials());
		assertEquals("Developer name", "Richard Stallman", actual.getName());
	}

	@Test
	public void testUpdateActivity() throws ParseException {
		TimeService timeService = new TimeService();
		int year = 1991;		int month = 4;		int day = 22;
		long date = timeService.convertToMillis(year, month, day, 0, 0);
		long date2 = timeService.convertToMillis(year, month, day, 1, 00);
		
		year = 1981; month = 07; day = 2;
		long newDate = timeService.convertToMillis(year, month, day, 10, 0);
		long newDate2 = timeService.convertToMillis(year, month, day, 12, 0);
		
		Activity activity = this.db.activity().createProjectActivity(1,"Catch Moby Dick", 2, date, date2);

		activity.setDescription("Leave Moby Dick alone");
		activity.setExpectedTime(666);
		activity.setStartTime(newDate);
		activity.setEndTime(newDate2);
		
		this.db.activity().update(activity);
		Activity actual = (Activity) this.db.activity().read(activity.getId());
		
		assertEquals("Activity description", "Leave Moby Dick alone", actual.getDescription());
		assertEquals("Activity expected time", 666, actual.getExpectedTime());
		assertEquals("Activity start time", newDate, actual.getStartTime());
		assertEquals("Activity end time", newDate2, actual.getEndTime());
	}

	@Test
	public void testUpdateActivityDeveloperRelation() throws ParseException {
		TimeService timeService = new TimeService();
		int year = 1991;		int month = 4;		int day = 22;
		long date = timeService.convertToMillis(year, month, day, 0, 0);
		
		year = 1981; month = 07; day = 2;
		long newDate = timeService.convertToMillis(year, month, day, 0, 0);

		Activity activity = this.db.activity().createProjectActivity(1,"Catch Moby Dick", 1, date, date);
		Developer dev = this.db.developer().create("MD", "Moby Dick");
		Activity newActivity = this.db.activity().createProjectActivity(1,"Leave Moby Dick alone", 666, newDate, newDate);
		Developer newDev = this.db.developer().create("RS", "Richard Stallman");
		ActivityDeveloperRelation rel = this.db.activityDeveloperRelation().create(activity, dev);
		
		rel.setActivity(newActivity);
		rel.setDeveloper(newDev);
		
		this.db.activityDeveloperRelation().update(rel);
		ActivityDeveloperRelation actual = this.db.activityDeveloperRelation().read(rel.getId());
		
		assertEquals("ActivityDeveloperRelation activity", "Leave Moby Dick alone", 
				actual.getActivity().getDescription());
		assertEquals("ActivityDeveloperRelation developer", "Richard Stallman", 
				actual.getDeveloper().getName());
	}

	@Test
	public void testUpdateAssist() {
		Developer dev = this.db.developer().create("MD", "Moby Dick");
		Developer newDev = this.db.developer().create("RS", "Richard Stallman");
		Assist assist = this.db.assist().create(dev, 1.5);
		
		assist.setDeveloper(newDev);
		assist.setSpentTime(666.0);
		
		this.db.assist().update(assist);
		Assist actual = this.db.assist().read(assist.getId());
		
		assertEquals("Assist developer", "Richard Stallman", actual.getDeveloper().getName());
		assertEquals("Assist spent time", 666.0, actual.getSpentTime());
	}
	
//	@Test
//	public void testUpdateNonexisting() {
//		this.db.developer.update(new Developer(-1, "MD", "Moby Dick"));
//	}
	
	@Test
	public void testDeleteProject() {
		Developer man = this.db.developer().create("MD", "Moby Dick");
		Project proj = this.db.project().create("Tidslampe", 666, 1337, man);
		Project actual = this.db.project().read(proj.getId());
		
		assertEquals("Project name", "Tidslampe", actual.getName());
		assertEquals("Project hour budget", 666, actual.getHourBudget());
		assertEquals("Project deadline", 1337, actual.getDeadline());
		assertEquals("Project manager", "Moby Dick", actual.getManager().getName());
		
		this.db.project().delete(actual.getId());
		
		assertEquals("Project count", 0, this.db.project().count());
	}

	@Test
	public void testDeleteDeveloper() {
		Developer dev = this.db.developer().create("MD", "Moby Dick");
		Developer actual = this.db.developer().read(dev.getId());

		assertEquals("Developer name", "Moby Dick", actual.getName());
		assertEquals("Developer initials", "MD", actual.getInitials());
		
		this.db.developer().delete(actual.getId());
		
		assertEquals("Developer count", 0, this.db.developer().count());
	}

	@Test
	public void testDeleteActivity() throws ParseException {
		TimeService timeService = new TimeService();
		int year = 1991;		int month = 4;		int day = 22;
		long date = timeService.convertToMillis(year, month, day, 0, 0);
		Activity activity = this.db.activity().createProjectActivity(1,"Catch Moby Dick", 1, date, date);
		Activity actual = this.db.activity().read(activity.getId());
		
		assertEquals("Activity description", "Catch Moby Dick", actual.getDescription());
		assertEquals("Activity expected time", 1, actual.getExpectedTime());
		assertEquals("Activity start time", date, actual.getStartTime());
		assertEquals("Activity end time", date, actual.getEndTime());
		
		this.db.activity().delete(actual.getId());
		
		assertEquals("Activity count", 0, this.db.activity().count());
	}

	@Test
	public void testDeleteActivityDeveloperRelation() throws ParseException {
		TimeService timeService = new TimeService();
		int year = 1991;		int month = 4;		int day = 22;
		long date = timeService.convertToMillis(year, month, day, 0, 0);
		Activity activity = this.db.activity().createProjectActivity(1,"Catch Moby Dick", 1, date, date);
		Developer dev = this.db.developer().create("MD", "Moby Dick");
		ActivityDeveloperRelation rel = this.db.activityDeveloperRelation().create(activity, dev);
		ActivityDeveloperRelation actual = this.db.activityDeveloperRelation().read(rel.getId());
		
		assertEquals("ActivityDeveloperRelation activity", "Catch Moby Dick", 
				actual.getActivity().getDescription());
		assertEquals("ActivityDeveloperRelation developer", "Moby Dick", 
				actual.getDeveloper().getName());
		
		this.db.activityDeveloperRelation().delete(actual.getId());
		
		assertEquals("ActivityDeveloperRelation count", 0, this.db.activityDeveloperRelation().count());
	}

	@Test
	public void testDeleteAssist() {
		Developer dev = this.db.developer().create("MD", "Moby Dick");
		Assist assist = this.db.assist().create(dev, 1.5);
		Assist actual = this.db.assist().read(assist.getId());
		
		assertEquals("Assist developer", "Moby Dick", actual.getDeveloper().getName());
		assertEquals("Assist spent time", 1.5, actual.getSpentTime());
		
		this.db.assist().delete(actual.getId());
		
		assertEquals("Assist count", 0, this.db.assist().count());
	}
	
}
