package controller;

import java.util.List;


import model.Developer;

import persistency.Database;
import utils.TimeService;

public class LoginController {
	public boolean loggedin = false;
	private long loginTime;
	private Developer user;
	private Database db;
	private TimeService ts;

	public LoginController(Database db, TimeService ts) {
		this.db = db;
		this.ts = ts;
	}

	public boolean login(String s) {
		List<Developer> potentialDevs = this.db.Developer().readByInitials(s);

		this.loggedin = potentialDevs.size() == 1;
		if (this.loggedin) {
			this.user = potentialDevs.get(0);
			this.loginTime = this.ts.getCurrentDateTime().getTimeInMillis();
		}

		return this.loggedin;
	}

	public long logout() {
		long timeOn = this.ts.getCurrentDateTime().getTimeInMillis()
				- this.loginTime;
		System.out
				.println("You have logged out. We should implement something that tells you whether or not you have registered all the hours you've been online.");
		System.out.println("Time since you logged in: " + timeOn + "ms");
		this.loggedin = false;
		this.user = null;

		return timeOn;
	}

	public Developer getUser() {
		return user;
	}

}
