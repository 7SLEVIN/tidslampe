package persistency;

import static org.junit.Assert.*;

import model.ActivityDeveloperRelation;
import model.ActivityType;
import model.Developer;
import model.Activity;
import model.Project;
import model.TimeEntry;

import org.junit.Test;

import utils.TimeService;

public class TestReserveTime extends BaseTestDatabase {
	TimeService timeService = new TimeService();
	
	@Test
	public void testInsertRead() {
		Developer developer = this.db.Developer().create("PM", "Paul McCartney");
		
		String description = "Concert at the Summer Olympics in London";
		int expectedTime = 3;
		int year = 2012;
		int month = 7;
		int day = 27;
		int hour = 20;
		int minute = 30;
		long startTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		long endTime = this.timeService.convertToMillis(year, month, day, hour+3, minute);

		Project project = this.db.Project().create("A day in the life", 10, endTime, developer);
		Activity activity = this.db.Activity().createProjectActivity(project.getId(),description, expectedTime, startTime, endTime);
		ActivityDeveloperRelation relation = this.db.ActivityDeveloperRelation().create(activity, developer);

		TimeEntry reservedTime = this.db.ReserveTime().create(startTime, endTime, relation.getId(), developer.getId());
		assertEquals(false, reservedTime == null);
		
		String descriptionCollision = "go to bed early";
		hour = 22; minute = 0;
		long startTime2 = this.timeService.convertToMillis(year, month, day, hour, minute);
		
		Activity activityCollision = this.db.Activity().createFixedActivity(ActivityType.VACATION, descriptionCollision, startTime2, endTime);
		ActivityDeveloperRelation relationCollision = this.db.ActivityDeveloperRelation().create(activityCollision, developer);
		
		TimeEntry reservedTime2 = this.db.ReserveTime().create(startTime2, endTime, relationCollision.getId(), developer.getId());
		TimeEntry reservedTime3 = this.db.RegisterTime().create(startTime2, endTime, relationCollision.getId(), developer.getId());
		
//Paul is singing from 20:30 to 23:30
//He cannot plan to go to bed at 22:00
		assertEquals(true, reservedTime2 == null);
		
//Although if he actually goes ahead and go to bed at 22:00, and registers it - no problem.
		assertEquals(false, reservedTime3 == null);
		
	}

}
