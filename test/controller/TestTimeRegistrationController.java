package controller;

import static org.junit.Assert.assertEquals;


import model.Activity;
import model.ActivityDeveloperRelation;
import model.Developer;
import model.TimeEntry;

import org.junit.Test;
import persistency.SetUpDatabase;
import utils.TimeService;

public class TestTimeRegistrationController extends SetUpDatabase {
	TimeService timeService = new TimeService();
	/*
	 * 1 - Brugeren skal angive tid
	 * 2 - Lav sanity-check på den angivne tid
	 * 3 - Tjek om den tid allerede er registreret
	 * 4 - Indsæt
	 */
	
	@Test
	public void testTimeTaken(){
		TimeRegistrationController trControl = new TimeRegistrationController(this.timeService);
		
		this.db.Developer().create("PG", "Patrick Gadd");
		this.db.Developer().create("SA", "Simon Altschuler");
		this.db.Developer().create("MF", "Markus Færevaag");
		
		Developer developer =this.db.Developer().readAll().get(0);
		
		String description = "test-activity";
		int expectedTime = 10;
		int year = 2013;
		int month = 4;
		int day = 13;
		int hour = 10;
		int minute = 30;
		long startTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		long endTime = this.timeService.convertToMillis(year, month+1, day, hour, minute);

		Activity activity = this.db.Activity().create(description, expectedTime, startTime, endTime);
		ActivityDeveloperRelation relation = this.db.ActivityDeveloperRelation().create(activity, developer);

//Initialization done, moving on to registration		
		
		hour = 15;
		endTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		
		TimeEntry firstEntry = this.db.TimeEntry().create(startTime, endTime, relation.getId(), relation.getDeveloper().getId());
		
		assertEquals(false, firstEntry == null);
		
		hour = 8;
		minute = 0;
		startTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		hour = 11;
		endTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		
		TimeEntry beginningOverlap = this.db.TimeEntry().create(startTime, endTime, relation.getId(), relation.getDeveloper().getId());

		assertEquals(true, beginningOverlap == null);
		
		hour = 14;
		minute = 0;
		startTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		hour = 17;
		endTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		
		TimeEntry endOverlap = this.db.TimeEntry().create(startTime, endTime, relation.getId(), relation.getDeveloper().getId());

		assertEquals(true, endOverlap == null);
		
		hour = 8;
		minute = 0;
		startTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		hour = 10;
		endTime = this.timeService.convertToMillis(year, month, day, hour, minute);
		
		TimeEntry secondEntry = this.db.TimeEntry().create(startTime, endTime, relation.getId(), relation.getDeveloper().getId());

		assertEquals(false, secondEntry == null);
		
	}
}
