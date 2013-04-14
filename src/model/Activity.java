package model;

import java.util.ArrayList;
import java.util.List;

import persistency.Database;

public class Activity extends DatabaseObject {
	
	private String description;
	private List<Developer> developers;
	private Number expectedTime;
	private long startTime;
	private long endTime;
	private List<Assist> assists;
	
	/**
	 * @param id
	 * @param description
	 * @param developer
	 * @param expectedTime
	 * @param startTime
	 * @param endTime
	 */
	public Activity(Database db, int id, String description, Number expectedTime, 
			Long startTime, Long endTime) {
		super(id,db);
		
		this.description = description;
		this.developers = new ArrayList<Developer>();
		this.expectedTime = expectedTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.assists = new ArrayList<Assist>();
	}

	@Override
	public String[] getValueArray() {
		return new String[]{this.description , String.valueOf(this.expectedTime) , String.valueOf(this.startTime) , String.valueOf(this.endTime)};
	}

	
	@Override
	protected void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void delete() {
		// TODO Auto-generated method stub
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description){
		this.description = description;
		this.db.Activity().update(this);
	}

	public List<Developer> getDevelopers() {
		return developers;
	}

	public Number getExpectedTime() {
		return expectedTime;
	}
	
	public void setExpectedTime(Number expTime){
		this.expectedTime = expTime;
		this.db.Activity().update(this);
	}

	public long getStartTime() {
		return startTime;
	}
	
	public void setStartTime(long startTime){
		this.startTime = startTime;
		this.db.Activity().update(this);
	}

	public long getEndTime() {
		return endTime;
	}
	
	public void setEndTime(long endTime){
		this.endTime = endTime;
		this.db.Activity().update(this);
	}

	public List<Assist> getAssists() {
		return assists;
	}


}
