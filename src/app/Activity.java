package app;

import java.util.ArrayList;
import java.util.List;

import persistency.DatabaseObject;

public class Activity extends DatabaseObject{
	
	private String description;
	private Developer developer;
	private Number expectedTime;
	private Long startTime;
	private Long endTime;
	private List<Assist> assists;
	
	/**
	 * @param id
	 * @param description
	 * @param developer
	 * @param expectedTime
	 * @param startTime
	 * @param endTime
	 */
	public Activity(String description, Developer developer,
			Number expectedTime, Long startTime, Long endTime) {
		this.description = description;
		this.developer = developer;
		this.expectedTime = expectedTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.assists = new ArrayList<Assist>();
	}
	

}
