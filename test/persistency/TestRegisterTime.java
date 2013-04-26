package persistency;

import static org.junit.Assert.assertEquals;


import model.Activity;
import model.ActivityDeveloperRelation;
import model.Developer;
import model.Project;
import model.TimeEntry;

import org.junit.Test;
import utils.TimeService;

public class TestRegisterTime extends BaseTestDatabase {
	TimeService timeService = new TimeService();
	/*
	 * 1 - Brugeren skal angive tid
	 * 2 - Lav sanity-check p� den angivne tid
	 * 3 - Tjek om den tid allerede er registreret
	 * 4 - Inds�t
	 */
	
	@Test
	public void testTimeTaken(){
		Developer developer = this.db.developer().create("AL", "Abraham Lincoln");

		
		
		String description = "test-activity";
		int expectedTime = 10;
		int year = 2013;
		int month = 4;
		int day = 13;
		int hour = 10;
		int minute = 30;
		long startTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		long endTime = this.timeService.convertToMillis(year, month+1, day, hour, minute);

		Project project = this.db.project().create("Run for presidency", 5, endTime, developer);
		Activity activity = this.db.activity().createProjectActivity(project.getId(),description, expectedTime, startTime, endTime);
		ActivityDeveloperRelation relation = this.db.activityDeveloperRelation().create(activity, developer);

		hour = 15;
		endTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		
		//Initialization done, moving on to registration
		
//Register legal entry		
		TimeEntry firstEntry = this.db.registerTime().create(startTime, endTime, relation);
		
		assertEquals(false, firstEntry == null);

//Register entry that overlaps in the beginning of legal entry	
		hour = 8;
		minute = 0;
		startTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		hour = 11;
		endTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		
		TimeEntry beginningOverlap = this.db.registerTime().create(startTime, endTime, relation);

		assertEquals(true, beginningOverlap == null);
		
//Register entry that overlaps in the end of legal entry		
		hour = 14;
		minute = 0;
		startTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		hour = 17;
		endTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		
		TimeEntry endOverlap = this.db.registerTime().create(startTime, endTime, relation);

		assertEquals(true, endOverlap == null);
		
//Register entry that overlaps completely	
		hour = 10;
		startTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		hour = 17;
		endTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		
		TimeEntry totalOverlap = this.db.registerTime().create(startTime, endTime, relation);

		assertEquals(true, totalOverlap == null);
		
//Register another legal entry
		hour = 8;
		minute = 0;
		startTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		hour = 10;
		endTime = this.timeService.convertToMillis(year, month, day, hour, minute);
	
		TimeEntry secondEntry = this.db.registerTime().create(startTime, endTime, relation);

		assertEquals(false, secondEntry == null);
		
//Register illegal entry
		hour = 22;
		minute = 30;
		startTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		hour = 24;
		endTime = this.timeService.convertToMillis(year, month, day, hour, minute);
	
		TimeEntry illegalEntry = this.db.registerTime().create(startTime, endTime, relation);

		assertEquals(true, illegalEntry == null);
		
	}
}
