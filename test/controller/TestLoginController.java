package controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.Activity;
import model.ActivityDeveloperRelation;
import model.Developer;
import model.TimeEntry;

import org.junit.Test;


import persistency.SetUpDatabase;
import utils.TimeService;

public class TestLoginController extends SetUpDatabase {

	
	@Test
	public void testLogin(){
		
		this.db.Developer().create("PG", "Patrick Gadd");
		this.db.Developer().create("SA", "Simon Altschuler");
		this.db.Developer().create("MF", "Markus F�revaag");
		
		LoginController loginControl = new LoginController(this.db,this.projectPlanner.getTimeService());
		
		List<Developer> devs = this.db.Developer().readAll();
		
		assertEquals(3, devs.size());
		
		assertEquals(devs.get(0).getInitials(), "PG");
		assertEquals(devs.get(2).getInitials(), "MF");
		
		boolean succes = loginControl.login(("PG"));
		assertEquals("login failed",true, succes);
		assertEquals("login failed",true, loginControl.getLoggedIn());
		assertEquals("Patrick Gadd", loginControl.getUser().getName());
	}
	
	@Test
	public void testLogout(){
		this.db.Developer().create("LOL", "Lord Ole Larsen");
		
		LoginController loginControl = new LoginController(this.db,this.projectPlanner.getTimeService());
		
		assertEquals("login failed",true, loginControl.login(("LOL")));
		
		loginControl.logout();
		assertEquals("logout failed",false, loginControl.getLoggedIn());
		
		loginControl.login("fail");
		assertEquals(false, loginControl.getLoggedIn());
		assertEquals(null, loginControl.getUser());
	
	}
	
	@Test
	public void testLogoutTimeSpent(){
		// Step 1
		TimeService timeService = mock(TimeService.class);
		this.projectPlanner.setTimeService(timeService);

		// Step 2
		Calendar cal = new GregorianCalendar(2011,Calendar.NOVEMBER,11); //Very important date: Google "11 11 11 video game releases" and go to the Wikipedia-page
		when(timeService.getCurrentDateTime()).thenReturn(cal);
		
		this.db.Developer().create("LOL", "Lord Ole Larsen");
		
		LoginController loginControl = new LoginController(this.db,this.projectPlanner.getTimeService());
		
		assertEquals("login failed",true, loginControl.login(("LOL")));
		

//Spend some time		
		Calendar newCal = new GregorianCalendar();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.MINUTE, 60 * 5);
		when(timeService.getCurrentDateTime()).thenReturn(newCal);

		long timeOn = loginControl.logout();
		assertEquals("time online not registered",true, loginControl.getTimeOnline() > 1000 * 60 * 60);
		
//Should not log out, because user has unregistered hours		
		assertEquals("logout failed",true, loginControl.getLoggedIn());
		
		
		
		loginControl.login("fail");
		assertEquals(false, loginControl.getLoggedIn());
	}
	
	@Test
	public void testTodaysEarliest(){

//Initialize
		TimeService realTS = new TimeService();
		TimeService timeService = mock(TimeService.class);
		this.projectPlanner.setTimeService(timeService);
		LoginController loginControl = new LoginController(this.db,this.projectPlanner.getTimeService());
		
		Calendar cal = Calendar.getInstance();
		
		int year = 2011, month = 11, day = 11, hour = 8, minute = 0;
		when(timeService.convertToMillis(year, month, day, hour, minute)).thenReturn(realTS.convertToMillis(year, month, day, hour, minute));
		long testMillis = timeService.convertToMillis(year, month, day, hour, minute);
			
		cal.setTimeInMillis(testMillis);
		when(timeService.getCurrentDateTime()).thenReturn(cal);
		
//Det samme indenfor 10 sekunder		
		assertEquals(testMillis/10000, cal.getTimeInMillis()/10000); 
				

		Developer developer = this.db.Developer().create("LOL", "Lord Ole Larsen");
		Activity activity = this.db.Activity().create("Save the Queen", 10, testMillis, testMillis);
		ActivityDeveloperRelation relation = this.db.ActivityDeveloperRelation().create(activity, developer);
		
		assertEquals("login failed",true, loginControl.login(("LOL")));

//Check that the function getTodaysEarliestTime() works as expected
		assertEquals(true,loginControl.getTodaysEarliestTime() == loginControl.ts.getCurrentDateTime().getTimeInMillis()-1000*(hour*60*60+minute*60));




		//Spend some time		
		Calendar newCal = new GregorianCalendar();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.MINUTE, 60 * 5);
		when(timeService.getCurrentDateTime()).thenReturn(newCal);

		loginControl.logout();
		assertEquals("time online not registered",true, loginControl.getTimeOnline() > 1000 * 60 * 60);
				
//Should not log out, because user has unregistered hours		
		assertEquals("logout should not happend",true, loginControl.getLoggedIn());
		
		
//Register work 
		TimeEntry timeEntry = this.db.TimeEntry().create(testMillis, testMillis+1000*60*120, relation.getId(), developer.getId());
		assertEquals(120, timeEntry.getDurationInMinutes());
		loginControl.logout();
		
//"Register" work 
		TimeEntry nullEntry = this.db.TimeEntry().create(testMillis+1000*60*30, testMillis+1000*60*180, relation.getId(), developer.getId());
		assertEquals(null, nullEntry);
		loginControl.logout();

//Register remaining work 
		TimeEntry secondEntry = this.db.TimeEntry().create(testMillis+1000*60*120, testMillis+1000*60*290, relation.getId(), developer.getId());
		assertEquals(170, secondEntry.getDurationInMinutes());
		loginControl.logout();
		
//Should log out now, since the work has been registered		
		assertEquals("logout should not happend",false, loginControl.getLoggedIn());
	}

}
