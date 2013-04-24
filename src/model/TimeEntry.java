package model;

import java.util.Calendar;

import persistency.Database;
/**
 * TimeEntries bruges b�de til RegisteredEntries og ReservedEntries 
 *
 */
public class TimeEntry extends DatabaseObject{
	long startTime; //TODO brugeren skal v�lge tid fra liste. 
	long endTime; //TODO endTime = startTime + 0.5h * n?
	int developerActivityRelationID;
	int developerID; //TODO for effektiv database-s�gning for kollision med tidligere time-registrering?
	
	public TimeEntry(Database db, int id, long startTime, long endTime, int devActRelID, int devID) {
		super(id,db);
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.developerActivityRelationID = devActRelID;
		this.developerID = devID;
	}
	
	@Override
	protected void save() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getValueArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getDurationInMinutes(){
		return (int) (this.endTime - this.startTime)/1000/60;
	}

	public long getStartTime() {
		return startTime;
	}
	
	public Calendar getStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.startTime);
		return cal;
	}

	public long getEndTime() {
		return endTime;
	}
	
	public Calendar getEndDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.endTime);
		return cal;
	}

	public int getDeveloperActivityRelationID() {
		return developerActivityRelationID;
	}

	public int getDeveloperID() {
		return this.db.activityDeveloperRelation().read(this.developerActivityRelationID).getDeveloper().getId();
	}

	public Activity getActivity() {
		return this.db.activityDeveloperRelation().read(this.developerActivityRelationID).getActivity();
	}
}
