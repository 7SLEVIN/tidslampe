package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import java.util.Calendar;

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
}
