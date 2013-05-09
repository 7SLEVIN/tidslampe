package model;

import java.util.Calendar;

import persistency.Database;
/**
 * TimeEntries bruges både til RegisteredEntries og ReservedEntries 
 *
 */
public class TimeEntry extends DatabaseObject{
	private long startTime; 
	private long endTime; 
	private boolean isAssist; 
	private ActivityDeveloperRelation activityDeveloperRelation;
	
	public TimeEntry(Database db, int id, long startTime, long endTime, boolean isAssist) {
		super(id,db,db.registerTime());
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.isAssist = isAssist;
	}
	
	public TimeEntry(Database db, int id, long startTime, long endTime, ActivityDeveloperRelation rel, boolean isAssist) {
		super(id,db,db.registerTime());
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.activityDeveloperRelation = rel;
		this.isAssist = isAssist;
	}
	
	@Override
	public String[] getValueArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getDurationInMinutes(){
		return (int) (this.endTime - this.startTime)/1000/60;
	}

	public boolean getIsAssist() {
		return this.isAssist;
	}

	public void setActivityDeveloperRelation(ActivityDeveloperRelation activityDeveloperRelation) {
		this.activityDeveloperRelation = activityDeveloperRelation;
	}

	public long getStartTime() {
		return this.startTime;
	}
	
	public Calendar getStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.startTime);
		return cal;
	}

	public long getEndTime() {
		return this.endTime;
	}
	
	public Calendar getEndDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.endTime);
		return cal;
	}

	public Developer getDeveloper() {
		return this.activityDeveloperRelation.getDeveloper();
	}

	public Activity getActivity() {
		return this.activityDeveloperRelation.getActivity();
	}
}
