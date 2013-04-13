package utils;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.TimeRegistrationController;

public class TestTimeService {
	TimeService timeService = new TimeService();

	@Test
	public void testSanityCheck(){
//User has given chosen a time in the UI 		
		int year = 2013;
		int month = 2;
		int day = 31;
		int hour = 11;
		int minute = 30 * 1;
		
		assertEquals(false, this.timeService.isDateValid(year, month, day, hour, minute));
		
		day = 28;
		assertEquals(true, this.timeService.isDateValid(year, month, day, hour, minute));
		
		day = 29;
		assertEquals(false, this.timeService.isDateValid(year, month, day, hour, minute));
		
		year = 2012;
		assertEquals(true, this.timeService.isDateValid(year, month, day, hour, minute));
		
		hour= 24;
		assertEquals(false, this.timeService.isDateValid(year, month, day, hour, minute));
		
		hour = 0; minute = 0;
		assertEquals(true, this.timeService.isDateValid(year, month, day, hour, minute));
		
		
	}
	

}
