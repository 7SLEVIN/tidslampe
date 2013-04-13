package utils;

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
	
	public long convertToMillis(int year, int month, int day, int hour, int minute){		
		if(!this.isDateValid(year, month, day, hour, minute)){
			System.out.println("INVALID DATE! hilsen TimeService");
			return -1L;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hour, minute);
		
		return cal.getTimeInMillis();
	}
	
	public int[] convertToValues(long timestamp){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		
		int[] values = new int[]{cal.YEAR , cal.MONTH , cal.DAY_OF_MONTH , cal.HOUR_OF_DAY , cal.MINUTE};
		
		return values;
	}
	
	public boolean isDateValid(int year, int month, int day, int hour, int minute)
	{
		if(hour == 0)
			hour = 24;
		else if(hour == 24)
			hour = 0;
		
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
	
}
