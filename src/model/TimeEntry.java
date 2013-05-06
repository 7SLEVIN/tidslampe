package model;

import java.util.Calendar;

import persistency.Database;
/**
 * TimeEntries bruges b�de til RegisteredEntries og ReservedEntries 
 *
 */
public class TimeEntry extends DatabaseObject{
	private long startTime; 
	private long endTime; 
	private boolean isAssist; 
	private int developerActivityRelationID;
	private ActivityDeveloperRelation activityDeveloperRelation;
	
	public TimeEntry(Database db, int id, long startTime, long endTime, int devActRelID, boolean isAssist) {
		super(id,db);
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.developerActivityRelationID = devActRelID;
		this.isAssist = isAssist;
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

	public boolean getIsAssist() {
		return this.isAssist;
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

	public int getDeveloperActivityRelationID() {
		return this.developerActivityRelationID;
	}

	public Developer getDeveloper() {
		if (this.activityDeveloperRelation == null)
			this.activityDeveloperRelation = this.db.activityDeveloperRelation().read(this.developerActivityRelationID);
		
		return this.activityDeveloperRelation.getDeveloper();
	}

	public Activity getActivity() {
		if (this.activityDeveloperRelation == null)
			this.activityDeveloperRelation = this.db.activityDeveloperRelation().read(this.developerActivityRelationID);

		return this.activityDeveloperRelation.getActivity();
	}
}
