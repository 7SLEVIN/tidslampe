package controller;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import model.Activity;
import model.ActivityDeveloperRelation;
import model.Developer;

import org.junit.Test;
import persistency.SetUpDatabase;

public class TestTimeRegistrationController extends SetUpDatabase {

	/*
	 * 1 - Brugeren skal angive tid
	 * 2 - Lav sanity-check på den angivne tid
	 * 3 - Tjek om den tid allerede er registreret
	 * 4 - Indsæt
	 */

	@Test
	public void testSanityCheck(){
		TimeRegistrationController trControl = new TimeRegistrationController();
//User has given chosen a time in the UI 		
		int year = 2013;
		int month = 2;
		int day = 31;
		int hour = 11;
		int minute = 30 * 1;
		
		assertEquals(false, trControl.isDateValid(year, month, day, hour, minute));
		
		day = 28;
		assertEquals(true, trControl.isDateValid(year, month, day, hour, minute));
		
		day = 29;
		assertEquals(false, trControl.isDateValid(year, month, day, hour, minute));
		
		year = 2012;
		assertEquals(true, trControl.isDateValid(year, month, day, hour, minute));
	}
	
	public void testTimeTaken(){
		TimeRegistrationController trControl = new TimeRegistrationController();
		
		this.db.developer.create("PG", "Patrick Gadd");
		this.db.developer.create("SA", "Simon Altschuler");
		this.db.developer.create("MF", "Markus Færevaag");
		
		Developer developer =this.db.developer.readAll().get(0);
		
		String description = "test-activity";
		int expectedTime = 10;
		Date startTime = new Date(2013, 4, 13);
		Date endTime = new Date(2013, 5, 13);
		Activity activity = this.db.activity.create(description, expectedTime, startTime, endTime);
		
		ActivityDeveloperRelation relation = this.db.activityDeveloperRelation.create(activity, developer);
		
		
//		this.db.timeEntry.create(startTime.g, endTime, relation.getId(), relation.getDeveloper().getId());
	}
}
