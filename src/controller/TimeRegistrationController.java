package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.TimeEntry;

public class TimeRegistrationController {

	public TimeRegistrationController(){
		
	}
	
	public boolean isDateValid(int year, int month, int day, int hour, int minute)
	{ //TODO sanity-check on date provided by user when reserving time.
		String date = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day)+"-"+String.valueOf(hour)+"-"+String.valueOf(minute);
		
		String DATE_FORMAT = "yyyy-MM-dd-kk-mm";
	        try {
	            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
	            df.setLenient(false);
	            df.parse(date);
	            return true;
	        } catch (ParseException e) {
	            return false;
	        }
	}

	public boolean timeAlreadyUsed(TimeEntry timeEntry){
		
		
		return false;
	}
}
