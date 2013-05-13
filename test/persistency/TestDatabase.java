package persistency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.List;

import model.Activity;
import model.ActivityDeveloperRelation;
import model.Developer;
import model.Project;

import org.junit.Test;

import utils.TimeService;
import exceptions.DeleteNonExistingException;
import exceptions.UpdateNonExistingException;


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
	public void testCount() throws DeleteNonExistingException {
		assertEquals("Database count", 0, this.db.developer().count());
		Developer man = this.db.developer().create("MD", "Moby Dick");
		assertEquals("Database count", 1, this.db.developer().count());
		Developer man2 = this.db.developer().create("RS", "Richard Stallman");
		assertEquals("Database count", 2, this.db.developer().count());
		man.delete();
		man2.delete();
		assertEquals("Database count", 0, this.db.developer().count());
	}
	
//	@Test
//	public void testExists() throws DeleteNonExistingException {
//		Developer dev = this.db.developer().create("MD", "Moby Dick");
//		assertTrue(this.db.developer().exists(dev.getId()));
//		dev.delete();
//		assertFalse(this.db.developer().exists(dev.getId()));
//	}
//	
//	@Test
//	public void testDeleteNonExisting() throws DeleteNonExistingException {
//		Developer dev = this.db.developer().create("MD", "Moby Dick");
//		assertTrue(this.db.developer().exists(dev.getId()));
//		dev.delete();
//		try {
//			dev.delete();
//			fail("This should throw an DeleteNonExistingException");
//		} catch (DeleteNonExistingException e) {
//			assertEquals("Trying to delete non-existing entry.", e.getMessage());
//		}
//	}
	
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
		assertEquals("Activity expected time", 2, actual.getHoursBudgeted());
		long actualStart = actual.getStartTime();
		assertEquals("Activity start time", startTime, actualStart);
		long actualEnd = actual.getEndTime();
		assertEquals("Activity end time", endTime, actualEnd);
	}

	@Test
	public void testCreateActivityDeveloperRelation() throws ParseException {
		TimeService timeService = new TimeService();
		int year = 1991;		
		int month = 4;		
		int day = 22;		
		int hour = 12;		
		int minute = 30;
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
	public void testUpdateProject() throws UpdateNonExistingException {
		Developer existingDev = this.db.developer().create("WE", "What Ever");
		Project existing = this.db.project().create("What Ever", 1, 1, existingDev);
		
		Developer man = this.db.developer().create("MD", "Moby Dick");
		Developer newMan = this.db.developer().create("RS", "Richard Stallman");
		Project proj = this.db.project().create("Tidslampe", 666, 1337, man);
		
		proj.setName("Lampekost");
		proj.setHourBudget(123);
		proj.setDeadline(321);
		proj.setManager(newMan);
		
		Project actual = this.db.project().read(proj.getId());
		
		assertEquals("Project existing name", "What Ever", existing.getName());
		assertEquals("Project existing hour budget", 1, existing.getHourBudget());
		assertEquals("Project existing deadline", 1, existing.getDeadline());
		assertEquals("Project existing manager", "What Ever", existing.getManager().getName());
		
		assertEquals("Project name", "Lampekost", actual.getName());
		assertEquals("Project hour budget", 123, actual.getHourBudget());
		assertEquals("Project deadline", 321, actual.getDeadline());
		assertEquals("Project manager", "Richard Stallman", actual.getManager().getName());
	}

	@Test
	public void testUpdateDeveloper() throws UpdateNonExistingException {
		Developer existing = this.db.developer().create("LO", "Lol");
		Developer dev = this.db.developer().create("MD", "Moby Dick");
		
		dev.setInitials("RS");
		dev.setName("Richard Stallman");
		
		Developer actual = this.db.developer().read(dev.getId());

		assertEquals("Developer existing initials", "LO", existing.getInitials());
		assertEquals("Developer existing name", "Lol", existing.getName());

		assertEquals("Developer initials", "RS", actual.getInitials());
		assertEquals("Developer name", "Richard Stallman", actual.getName());
	}

	@Test
	public void testUpdateActivity() throws ParseException, UpdateNonExistingException {
		TimeService timeService = new TimeService();
		int year = 1991;		int month = 4;		int day = 22;
		long date = timeService.convertToMillis(year, month, day, 0, 0);
		long date2 = timeService.convertToMillis(year, month, day, 1, 00);
		
		year = 1981; month = 07; day = 2;
		long newDate = timeService.convertToMillis(year, month, day, 10, 0);
		long newDate2 = timeService.convertToMillis(year, month, day, 12, 0);
		
		Activity existing = this.db.activity().createProjectActivity(1, "Loadfactor", 3, date, date2);
		Activity activity = this.db.activity().createProjectActivity(1,"Catch Moby Dick", 2, date, date2);

		activity.setDescription("Leave Moby Dick alone");
		activity.setHoursBudgeted(666);
		activity.setStartTime(newDate);
		activity.setEndTime(newDate2);
		
		Activity actual = (Activity) this.db.activity().read(activity.getId());
		
		assertEquals("Activity existing description", "Loadfactor", existing.getDescription());
		assertEquals("Activity existing expected time", 3, existing.getHoursBudgeted());
		assertEquals("Activity existing start time", date, existing.getStartTime());
		assertEquals("Activity existing end time", date2, existing.getEndTime());
		
		assertEquals("Activity description", "Leave Moby Dick alone", actual.getDescription());
		assertEquals("Activity expected time", 666, actual.getHoursBudgeted());
		assertEquals("Activity start time", newDate, actual.getStartTime());
		assertEquals("Activity end time", newDate2, actual.getEndTime());
	}

	@Test
	public void testUpdateActivityDeveloperRelation() throws ParseException, UpdateNonExistingException {
		TimeService timeService = new TimeService();
		int year = 1991;		int month = 4;		int day = 22;
		long date = timeService.convertToMillis(year, month, day, 0, 0);
		
		year = 1981; month = 07; day = 2;
		long newDate = timeService.convertToMillis(year, month, day, 0, 0);

		Activity activity = this.db.activity().createProjectActivity(1,"Catch Moby Dick", 1, date, date);
		Developer dev = this.db.developer().create("MD", "Moby Dick");
		Activity newActivity = this.db.activity().createProjectActivity(1,"Leave Moby Dick alone", 666, newDate, newDate);
		Developer newDev = this.db.developer().create("RS", "Richard Stallman");
		ActivityDeveloperRelation existing = this.db.activityDeveloperRelation().create(activity, dev);
		ActivityDeveloperRelation rel = this.db.activityDeveloperRelation().create(activity, dev);
		
		rel.setActivity(newActivity);
		rel.setDeveloper(newDev);
		
		ActivityDeveloperRelation actual = this.db.activityDeveloperRelation().read(rel.getId());
		
		assertEquals("ActivityDeveloperRelation existing activity", "Catch Moby Dick", 
				existing.getActivity().getDescription());
		assertEquals("ActivityDeveloperRelation existing developer", "Moby Dick", 
				existing.getDeveloper().getName());
		
		assertEquals("ActivityDeveloperRelation activity", "Leave Moby Dick alone", 
				actual.getActivity().getDescription());
		assertEquals("ActivityDeveloperRelation developer", "Richard Stallman", 
				actual.getDeveloper().getName());
	}
	
	@Test
	public void testDeleteProject() throws DeleteNonExistingException {
		Developer man = this.db.developer().create("MD", "Moby Dick");
		Project proj = this.db.project().create("Tidslampe", 666, 1337, man);
		Project actual = this.db.project().read(proj.getId());
		
		assertEquals("Project name", "Tidslampe", actual.getName());
		assertEquals("Project hour budget", 666, actual.getHourBudget());
		assertEquals("Project deadline", 1337, actual.getDeadline());
		assertEquals("Project manager", "Moby Dick", actual.getManager().getName());
		
		actual.delete();
		
		assertEquals("Project count", 0, this.db.project().count());
	}

	@Test
	public void testDeleteDeveloper() throws DeleteNonExistingException {
		Developer dev = this.db.developer().create("MD", "Moby Dick");
		Developer actual = this.db.developer().read(dev.getId());

		assertEquals("Developer name", "Moby Dick", actual.getName());
		assertEquals("Developer initials", "MD", actual.getInitials());
		
		actual.delete();
		
		assertEquals("Developer count", 0, this.db.developer().count());
	}

	@Test
	public void testDeleteActivity() throws ParseException, DeleteNonExistingException {
		TimeService timeService = new TimeService();
		int year = 1991;		int month = 4;		int day = 22;
		long date = timeService.convertToMillis(year, month, day, 0, 0);
		Activity activity = this.db.activity().createProjectActivity(1,"Catch Moby Dick", 1, date, date);
		Activity actual = this.db.activity().read(activity.getId());
		
		assertEquals("Activity description", "Catch Moby Dick", actual.getDescription());
		assertEquals("Activity expected time", 1, actual.getHoursBudgeted());
		assertEquals("Activity start time", date, actual.getStartTime());
		assertEquals("Activity end time", date, actual.getEndTime());
		
		actual.delete();
		
		assertEquals("Activity count", 0, this.db.activity().count());
	}

	@Test
	public void testDeleteActivityDeveloperRelation() throws ParseException, DeleteNonExistingException {
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
		
		actual.delete();
		
		assertEquals("ActivityDeveloperRelation count", 0, this.db.activityDeveloperRelation().count());
	}
	
}
