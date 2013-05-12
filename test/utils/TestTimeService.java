package utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestTimeService {
	TimeService timeService = new TimeService();
	
	@Before
	public void setUp() {
		Dialog.setDebugMode(true,0);
	}
	
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
		
		year = 2011; month = 123; day = 11; hour = 8; minute = 0;
		assertEquals(false, this.timeService.isDateValid(year, month, day, hour, minute));
		
	}
	
	@Test
	public void testConvertToMillis(){
		
		int year = 2011, month = 11, day = 11, hour = 8, minute = 0;
		long firstMillis = timeService.convertToMillis(year, month, day, hour, minute);
		assertEquals(true, firstMillis > 1000000); //Does it return a reasonable large number?
		
		hour = 0;
		long secondMillis = timeService.convertToMillis(year, month, day, hour, minute);
		assertEquals(true, firstMillis > secondMillis); //"first" is after "second" and should be greater
		
		hour = 23; minute = 59; day = 10;
		long thirdMillis = timeService.convertToMillis(year, month, day, hour, minute);
		assertEquals(true, secondMillis > thirdMillis); //"second" is after "third" and should be greater
		
		int[] values = timeService.convertToValues(thirdMillis);
		
		assertEquals(year, values[0]);
		assertEquals(month, values[1]);
		assertEquals(day, values[2]);
		assertEquals(hour, values[3]);
		assertEquals(minute, values[4]);
	}
	

}
