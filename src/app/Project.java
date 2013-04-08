package app;

import java.util.List;

import persistency.DatabaseObject;

public class Project extends DatabaseObject {

	private String name;
	private int hourBudget;
	private int deadline;
	private Developer manager;
	private List<Activity> activities;
	
	/**
	 * @param name
	 * @param hourBudget
	 * @param deadline
	 * @param manager
	 * @param activities
	 */
	public Project(String name, int hourBudget, int deadline,
			Developer manager, List<Activity> activities) {
		this.name = name;
		this.hourBudget = hourBudget;
		this.deadline = deadline;
		this.manager = manager;
		this.activities = activities;
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
