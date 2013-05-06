package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
		return (Calendar) this.date.clone();
	}
	
	public String convertCalendarToInputString(Calendar cal) {
		return String.format("%s-%s-%s", 	StringUtils.trailingZero(cal.get(Calendar.DAY_OF_MONTH)), 
											StringUtils.trailingZero(cal.get(Calendar.MONTH)+1), cal.get(Calendar.YEAR));
	}
	
	public String convertLongToString(long longtime){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(longtime);
		return this.convertCalendarToInputString(cal);
	}

	public Calendar convertToCalendar(int year, int month, int day, int hour, int minute){	
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.convertToMillis(year, month, day, hour, minute));
		return cal;
	}
	
	public long convertToMillis(String deadlineString){
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Calendar deadlineDate = Calendar.getInstance();
			deadlineDate.setTime(format.parse(deadlineString.trim()));
			long longDeadline = deadlineDate.getTimeInMillis(); 
			if(this.getCurrentDateTime().getTimeInMillis() > longDeadline){ //Deadline already surpassed? Think not.
				Dialog.message("You can't make a deadline which is already passed");
				return -1L;
			}
			return longDeadline;
			
		} catch (ParseException e) {
			Dialog.message("Invalid date format, must use dd-mm-yyyy");
			return -1L;
		}
		
	}
	
	public long convertToMillis(int year, int month, int day, int hour, int minute){		
		if(!this.isDateValid(year, month, day, hour, minute)){
			return -1L;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hour, minute,0);
		
		return cal.getTimeInMillis();
	}
	
	public int[] convertToValues(long timestamp){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		
		int[] values = new int[]{cal.get(Calendar.YEAR) , cal.get(Calendar.MONTH) ,
				cal.get(Calendar.DAY_OF_MONTH) , cal.get(Calendar.HOUR_OF_DAY) , cal.get(Calendar.MINUTE)};
		
		return values;
	}
	
	public Calendar convertToCalendar(long timestamp){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		return cal;
	}
	
	public boolean isDateValid(int year, int month, int day, int hour, int minute)
	{
//		if(hour == 0)
//			hour = 24;
//		else if(hour >= 24){
//			//System.out.println("INVALID DATE! hilsen TimeService");
//			return false;
//		}
			
		
		String date = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day)+"-"+String.valueOf(hour)+"-"+String.valueOf(minute);
		String DATE_FORMAT = "yyyy-MM-dd-HH-mm";
	        try {
	            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
	            df.setLenient(false);
	            Date d = df.parse(date);
	            return true;
	        } catch (ParseException e) {
	        	//System.out.println("INVALID DATE! hilsen TimeService");
	            return false;
	        }
	}
	
}
