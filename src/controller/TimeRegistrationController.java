package controller;

import utils.TimeService;
import model.TimeEntry;

public class TimeRegistrationController {
	private TimeService timeService;
	
	public TimeRegistrationController(TimeService ts){
		this.timeService = ts;
	}
	
	public boolean timeAlreadyUsed(TimeEntry timeEntry){
		
		
		return false;
	}
}
