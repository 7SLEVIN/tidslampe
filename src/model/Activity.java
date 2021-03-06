package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import exceptions.UpdateNonExistingException;

import persistency.Database;
import utils.ArrayUtils;
import utils.Dialog;

public class Activity extends DatabaseObject {
	
	private ActivityType type;
	private String description;
	private int hoursBudgeted;
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
		super(id,db,db.activity());
		this.type = ActivityType.PROJECT;
		this.description = description;
		this.projectID = projectID;
		this.hoursBudgeted = expectedTime;
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
		super(id,db,db.activity());
		this.type = type;
		this.description = description;
		this.hoursBudgeted = -1;
		this.projectID = -1;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public String[] getValueArray() {
		return new String[]{this.type.name() , this.description , String.valueOf(this.hoursBudgeted) , String.valueOf(this.startTime) , String.valueOf(this.endTime), String.valueOf(this.projectID)};
	}

	public int getHoursRegistered(){
		int timeUsed = 0;
		for (TimeEntry entry : this.database.registerTime().readByActivityId(this.getId())) {
			timeUsed += entry.getDurationInMinutes();
		}
		timeUsed /= 60;
		return timeUsed;
	}
	
	@Override
	protected void save() throws UpdateNonExistingException {
		this.database.activity().update(this);
	}
	
	public void addDeveloper(Developer dev){
		if(this.isDevAlreadyOnActivity(dev.getId())){
			Dialog.message("The developer is already on the activity.");
		}else{
			this.database.activityDeveloperRelation().create(this, dev);
			this.developers = this.getDevelopers();
		}
	}
	
	public List<Developer> getDevelopers(){
		if(this.developers == null){
			this.developers = new ArrayList<Developer>();
		}else{
			this.developers.clear();
		}
		List<ActivityDeveloperRelation> relations =	this.database.activityDeveloperRelation().readAllWhereEquals("activity_id", this.getId());
		for(ActivityDeveloperRelation relation : relations){
			this.developers.add(relation.getDeveloper());
		}
		
		return this.developers;
	}

	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) throws UpdateNonExistingException {
		this.description = description;
		this.save();
	}

	public int getHoursBudgeted() {
		return this.hoursBudgeted;
	}
	
	public void setHoursBudgeted(int expTime) throws UpdateNonExistingException {
		this.hoursBudgeted = expTime;
		this.save();
	}
	
	public long getStartTime() {
		return this.startTime;
	}
	
//	public Date getStartDate() {
//		return new Date(this.startTime);
//	}
	
	public void setStartTime(long newDate) throws UpdateNonExistingException {
		this.startTime = newDate;
		this.save();
	}
	
	public long getEndTime() {
		return this.endTime;
	}
	
	public Calendar getEndCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.endTime);
		return cal;
	}
	
	public void setEndTime(long newDate) throws UpdateNonExistingException {
		this.endTime = newDate;
		this.save();
	}
	
	private boolean isDevAlreadyOnActivity(int devID){
		List<Developer> devs;
		if(this.developers == null){
			devs = this.getDevelopers();
		}else{
			devs = this.developers;
		}
		
		for(int i = 0; i < devs.size(); i++){
			if(devs.get(i).getId() == devID)
				return true;
		}
		
		return false;
	}
	
	public String getAllDevsInitials(){
		List<Developer> devs;
		if(this.developers == null)
			devs = this.getDevelopers();
		else
			devs = this.developers;
		String[] devIDs = new String[devs.size()];
		
		for (int i = 0; i < devIDs.length; i++) {
			devIDs[i] = devs.get(i).getInitials();
		}
		
		return ArrayUtils.join(devIDs, ',');
	}

	public ActivityType getType() {
		return this.type;
	}
	
	public boolean isFixed() {
		return this.projectID == -1;
	}
	
	public Project getProject() {
		return this.database.project().read(this.projectID);
	}

}
