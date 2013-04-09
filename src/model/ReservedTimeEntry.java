package model;

public class ReservedTimeEntry extends DatabaseObject {
	long startTime;
	long endTime; //TODO endTime = startTime + 0.5h * n?
	int developerActivityRelationID;
	
	public ReservedTimeEntry(int id, long startTime, long endTime, int devActRelID) {
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
