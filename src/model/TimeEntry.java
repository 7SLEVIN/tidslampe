package model;

import persistency.Database;

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

	public long getEndTime() {
		return endTime;
	}

	public int getDeveloperActivityRelationID() {
		return developerActivityRelationID;
	}

	public int getDeveloperID() {
		return developerID;
	}
	
}
