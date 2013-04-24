package model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import persistency.Database;

public class Activity extends DatabaseObject {
	
	private ActivityType type;
	private String description;
	private int expectedTime;
	private List<Assist> assists;
	private long startTime;
	private long endTime;
	private List<Developer> developers;
	private int projectID;
	
	/**
	 * Project-activity
	 * @param db
	 * @param id
	 * @param projectID
	 * @param description
	 * @param expectedTime
	 * @param startTime
	 * @param endTime
	 */
	public Activity(Database db, int id, int projectID, String description, int expectedTime, long startTime, long endTime){
		super(id,db);
		this.type = ActivityType.PROJECT;
		this.description = description;
		this.projectID = projectID;
		this.expectedTime = expectedTime;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	/**
	 * Fixed-activity
	 * @param db
	 * @param id
	 * @param type
	 * @param description
	 * @param startTime
	 * @param endTime
	 */
	public Activity(Database db, int id, ActivityType type, String description, long startTime, long endTime) {
		super(id,db);
		this.type = type;
		this.description = description;
		this.expectedTime = -1;
		this.projectID = -1;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public String[] getValueArray() {
		return new String[]{this.type.name() , this.description , String.valueOf(this.expectedTime) , String.valueOf(this.startTime) , String.valueOf(this.endTime), String.valueOf(this.projectID)};
	}

	
	@Override
	protected void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void delete() {
		// TODO Auto-generated method stub
		
	}
	
	public List<Developer> getDevelopers(){
		if(this.assists == null){
			//TODO panic, lazily
		}
		return this.developers;
	}

	public List<Assist> getAssists() {
		if(this.assists == null){
			//TODO panic, lazily
		}
		return this.assists;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description){
		this.description = description;
		this.db.activity().update(this);
	}

	public int getExpectedTime() {
		return this.expectedTime;
	}
	
	public void setExpectedTime(int expTime){
		this.expectedTime = expTime;
		this.db.activity().update(this);
	}
	
	public long getStartTime() {
		return this.startTime;
	}
	
	public Date getStartDate() {
		return new Date(this.startTime);
	}
	
	public void setStartTime(long newDate) {
		this.startTime = newDate;
		this.db.activity().update(this);
	}
	
	public long getEndTime() {
		return this.endTime;
	}
	
	public Date getEndDate() {
		return new Date(this.endTime);
	}
	
	public void setEndTime(long newDate) {
		this.endTime = newDate;
		this.db.activity().update(this);
	}
	
	public Project getProject() {
		return this.db.project().read(this.projectID);
	}

}
