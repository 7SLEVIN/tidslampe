package controller;

import java.util.Calendar;
import java.util.List;


import model.Developer;
import model.TimeEntry;

import persistency.Database;
import utils.Dialog;
import utils.TimeService;

public class LoginController {
	private boolean loggedin = false;
	private long loginTime;
	private Developer user;
	private Database db;
	protected TimeService ts;

	public LoginController(Database db, TimeService ts) {
		this.db = db;
		this.ts = ts;
	}

	public boolean getLoggedIn(){
		return this.loggedin;
	}
	
	public boolean login(String s) {
		List<Developer> potentialDevs = this.db.developer().readByInitials(s);

		this.loggedin = potentialDevs.size() == 1;
		if (this.loggedin) {
			this.user = potentialDevs.get(0);
			this.loginTime = this.ts.getCurrentDateTime().getTimeInMillis();
		}

		return this.loggedin;
	}

	public long logout() {
		long timeOn = this.ts.getCurrentDateTime().getTimeInMillis() - this.loginTime;
		
		if(this.hasUnregisteredTime()){
			int totalTimeRegisteredToday = this.timeRegisteredToday();
			int totalTimeOn = (int) (this.ts.getCurrentDateTime().getTimeInMillis()-this.loginTime);
			
			int unregisteredTime = totalTimeOn - totalTimeRegisteredToday*60*1000;
			unregisteredTime /= 60*1000; //converts to minutes
//			return totalTimeOn > totalTimeRegisteredToday*60*1000 + 29*60*1000;
			Dialog.message("Log out failed, you have unregistered work! " + unregisteredTime + " minutes have not been registered.");
			return -1L;
		}
			
		
		this.loggedin = false;
		this.user = null;

		return timeOn;
	}
	
	public long getTimeOnline(){
		return this.ts.getCurrentDateTime().getTimeInMillis() - this.loginTime;
	}

	protected long getTodaysEarliestTime(){
		Calendar cal = this.ts.getCurrentDateTime();

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		
		long nowInMillis = cal.getTimeInMillis();
		nowInMillis -= 1000 * (3600*hour + 60 * minute + second);
		
		return nowInMillis;
	}
	
	private int timeRegisteredToday(){
		int devID = this.user.getId();
		long earliestToday = this.getTodaysEarliestTime();

		int totalTimeRegisteredToday = 0;
		
		List<TimeEntry> collidingEntries = this.db.registerTime().getCollidingEntries(earliestToday, earliestToday+3600*24*1000, devID);
		
		for(TimeEntry timeEntry : collidingEntries){
			totalTimeRegisteredToday += timeEntry.getDurationInMinutes();
		}
		return totalTimeRegisteredToday;
	}
	
	private boolean hasUnregisteredTime(){
		int totalTimeRegisteredToday = this.timeRegisteredToday();
		
		int totalTimeOn = (int) (this.ts.getCurrentDateTime().getTimeInMillis()-this.loginTime);
		
		return totalTimeOn > totalTimeRegisteredToday*60*1000 + 29*60*1000;
	}
	
	public Developer getUser() {
		return user;
	}

}
