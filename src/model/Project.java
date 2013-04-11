package model;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public String[] toArray() {
		return new String[]{this.name, String.valueOf(hourBudget), 
				String.valueOf(deadline), 
				String.valueOf(this.manager.getId())};
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHourBudget() {
		return hourBudget;
	}

	public void setHourBudget(int hourBudget) {
		this.hourBudget = hourBudget;
	}

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	public Developer getManager() {
		return manager;
	}

	public void setManager(Developer manager) {
		this.manager = manager;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

}
