package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



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
	public Activity(int id, String description, Number expectedTime, 
			Date startTime, Date endTime) {
		super(id);
		
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

	public String getDescription() {
		return description;
	}

	public Number getExpectedTime() {
		return expectedTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}
	

}
