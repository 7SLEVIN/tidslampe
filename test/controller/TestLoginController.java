package controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.Developer;

import org.junit.Test;

import app.TimeService;

import persistency.SetUpDatabase;

public class TestLoginController extends SetUpDatabase {

	
	@Test
	public void testLogin(){
		
		this.db.getDeveloper().create("PG", "Patrick Gadd");
		this.db.getDeveloper().create("SA", "Simon Altschuler");
		this.db.getDeveloper().create("MF", "Markus F�revaag");
		
		LoginController loginControl = new LoginController(this.db,this.projectPlanner.getTimeService());
		
		List<Developer> devs = this.db.getDeveloper().readAll();
		
		assertEquals(3, devs.size());
		
		assertEquals(devs.get(0).getInitials(), "PG");
		assertEquals(devs.get(2).getInitials(), "MF");
		
		boolean succes = loginControl.login(("PG"));
		assertEquals("login failed",true, succes);
		assertEquals("login failed",true, loginControl.loggedin);
		assertEquals("Patrick Gadd", loginControl.getUser().getName());
	}
	
	@Test
	public void testLogout(){
		this.db.getDeveloper().create("LOL", "Lord Ole Larsen");
		
		LoginController loginControl = new LoginController(this.db,this.projectPlanner.getTimeService());
		
		assertEquals("login failed",true, loginControl.login(("LOL")));
		
		loginControl.logout();
		assertEquals("logout failed",false, loginControl.loggedin);
		
		loginControl.login("fail");
		assertEquals(false, loginControl.loggedin);
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
		
		this.db.getDeveloper().create("LOL", "Lord Ole Larsen");
		
		LoginController loginControl = new LoginController(this.db,this.projectPlanner.getTimeService());
		
		assertEquals("login failed",true, loginControl.login(("LOL")));
		

//Spend some time		
		Calendar newCal = new GregorianCalendar();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.MINUTE, 60 * 5);
		when(timeService.getCurrentDateTime()).thenReturn(newCal);

		long timeOn = loginControl.logout();
		assertEquals("time online not registered",true, timeOn > 1000 * 60 * 60);
		assertEquals("logout failed",false, loginControl.loggedin);
		
		loginControl.login("fail");
		assertEquals(false, loginControl.loggedin);
	}

}
