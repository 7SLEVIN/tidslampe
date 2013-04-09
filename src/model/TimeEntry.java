package model;

public class TimeEntry extends DatabaseObject{
	long startTime; //TODO brugeren skal vælge tid fra liste. 
	long endTime; //TODO endTime = startTime + 0.5h * n?
	int developerActivityRelationID;
	
	public TimeEntry(int id, long startTime, long endTime, int devActRelID) {
		super(id);
		
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
	
}
