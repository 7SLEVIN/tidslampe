package app;

import java.util.ArrayList;
import java.util.List;

import persistency.DatabaseObject;

public class Project extends DatabaseObject {

	private String name;
	private int hourBudget;
	private int deadline;
	private Developer manager;
	private List<Activity> activities;
	
	/**
	 * @param id
	 * @param name
	 * @param hourBudget
	 * @param deadline
	 * @param manager
	 * @param activities
	 */
	public Project(int id, String name, int hourBudget, 
			int deadline, Developer manager) {
		super(id);
		
		this.name = name;
		this.hourBudget = hourBudget;
		this.deadline = deadline;
		this.manager = manager;
		this.activities = new ArrayList<Activity>();
	}

	@Override
	protected void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void delete() {
		// TODO Auto-generated method stub
		
	}

	public String getName() {
		return name;
	}

	public int getHourBudget() {
		return hourBudget;
	}

	public int getDeadline() {
		return deadline;
	}

	public Developer getManager() {
		return manager;
	}

}
