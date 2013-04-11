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
	
}
