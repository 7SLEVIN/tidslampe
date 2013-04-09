package controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import app.Developer;
import persistency.SetUpDatabase;

public class TestLoginController extends SetUpDatabase {

	
	@Test
	public void testLogin(){
		this.db.developer.create("PG", "Patrick Gadd");
		this.db.developer.create("SA", "Simon Altschuler");
		this.db.developer.create("MF", "Markus Færevaag");
		
		LoginController loginControl = new LoginController(this.db);
		
		List<Developer> devs = this.db.developer.readAll();
		
		assertEquals(3, devs.size());
		
		assertEquals(devs.get(0).getInitials(), "PG");
		assertEquals(devs.get(2).getInitials(), "MF");
		
		boolean succes = loginControl.login(("PG"));
		assertEquals("login failed",true, succes);
		assertEquals("login failed",true, loginControl.loggedin);
		assertEquals("Patrick Gadd", loginControl.getUser().getName());
		
		loginControl.logout();
		assertEquals("logout failed",false, loginControl.loggedin);
		
		loginControl.login("fail");
		assertEquals(false, loginControl.loggedin);
	}

}
