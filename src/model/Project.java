package model;

import java.util.List;

import exceptions.UpdateNonExistingException;

import persistency.Database;
import utils.TimeService;

public class Project extends DatabaseObject {

	private String name;
	private int hourBudget;
	private long deadline;
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
	public Project(Database db, int id, String name, int hourBudget, 
			long deadline, Developer manager) {
		super(id,db,db.project());
		
		this.name = name;
		this.hourBudget = hourBudget;
		this.deadline = deadline;
		this.manager = manager;
	}
	
	public Project(Database db, int id, String name, int hourBudget, 
			long deadline) {
		super(id,db,db.project());
		
		this.name = name;
		this.hourBudget = hourBudget;
		this.deadline = deadline;
	}

	@Override
	public String[] getValueArray() {
		return new String[]{this.name, String.valueOf(hourBudget), 
				String.valueOf(deadline), 
				this.manager != null ? String.valueOf(this.manager.getId()) : "-1"};
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) throws UpdateNonExistingException{
		this.name = name;
		this.save();
	}

	public int getHourBudget() {
		return hourBudget;
	}
	
	public void setHourBudget(int i) throws UpdateNonExistingException {
		this.hourBudget = i;
		this.save();
	}

	public long getDeadline() {
		return deadline;
	}
	
	public void setDeadline(int deadline) throws UpdateNonExistingException{
		this.deadline = deadline;
		this.save();
	}

	public Developer getManager() {
		return manager;
	}
	
	public void setManager(Developer manager) throws UpdateNonExistingException{
		this.manager = manager;
		this.save();
	}

	public List<Activity> getActivities() {
		this.activities = this.database.activity().readAllWhereEquals("project_id", this.getId());
		return this.activities;
	}

	public int getHoursRegistered(){
		int timeUsed = 0;
		for (TimeEntry entry : this.database.registerTime().readByProjectId(this.getId())) {
			timeUsed += entry.getDurationInMinutes();
		}
		timeUsed /= 60;
		return timeUsed;
	}
	
	public int getEstPercentageCompletion(){
		return this.getHoursRegistered()*100 / (this.getHourBudget());
	}
	
	public int getEstHoursRemaining(){
		return this.getHourBudget() - this.getHoursRegistered();
	}
	
	public int getHoursAllocatedToActivities(){
		int time = 0;
		for (Activity activity : this.getActivities()) {
			time += activity.getExpectedTime();
		}
		return time;
	}
	
	public String getSerialNumber(){
		return ""+((new TimeService()).convertToValues(this.getDeadline())[0]%100) + String.format("%04d", this.getId() % 10000);
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
