package app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeService {
	private Calendar date;
	
	public TimeService(){
		this.update();
	}
	
	private void update(){
		this.date = Calendar.getInstance();
	}

	public Calendar getCurrentDateTime() {
		this.update();
		return this.date;
	}
	
	public static boolean isDateValid(String date) 
	{ //TODO sanity-check on date provided by user when reserving time.
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
	
}
