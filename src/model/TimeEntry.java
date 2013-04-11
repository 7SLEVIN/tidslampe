package model;

import persistency.Database;

public class TimeEntry extends DatabaseObject{
	long startTime; //TODO brugeren skal vælge tid fra liste. 
	long endTime; //TODO endTime = startTime + 0.5h * n?
	int developerActivityRelationID;
	int developerID; //TODO for effektiv database-søgning for kollision med tidligere time-registrering?
	
	public TimeEntry(Database db, int id, long startTime, long endTime, int devActRelID) {
		super(id,db);
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.developerActivityRelationID = devActRelID;
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
	public String[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
