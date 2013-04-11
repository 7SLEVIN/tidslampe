package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import persistency.Database;

public class Activity extends DatabaseObject {
	
	private String description;
	private List<Developer> developers;
	private Number expectedTime;
	private Date startTime;
	private Date endTime;
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
			Date startTime, Date endTime) {
		super(id,db);
		
		this.description = description;
		this.developers = new ArrayList<Developer>();
		this.expectedTime = expectedTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.assists = new ArrayList<Assist>();
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
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
		return new String[]{this.description, String.valueOf(this.expectedTime), 
				df.format(this.startTime), df.format(this.endTime)};
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Developer> getDevelopers() {
		return developers;
	}

	public void setDevelopers(List<Developer> developers) {
		this.developers = developers;
	}

	public Number getExpectedTime() {
		return expectedTime;
	}

	public void setExpectedTime(Number expectedTime) {
		this.expectedTime = expectedTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<Assist> getAssists() {
		return assists;
	}

	public void setAssists(List<Assist> assists) {
		this.assists = assists;
	}

}
