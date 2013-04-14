package model;

import persistency.Database;

public class ReservedTimeEntry extends DatabaseObject {
	long startTime;
	long endTime; //TODO endTime = startTime + 0.5h * n?
	int developerActivityRelationID;
	
	public ReservedTimeEntry(Database db, int id, long startTime, long endTime, int devActRelID) {
		super(id, db);
		
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
	public String[] getValueArray() {
		// TODO Auto-generated method stub
		return null;
	}
}
