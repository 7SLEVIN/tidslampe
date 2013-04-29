package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import persistency.Database;
import utils.ArrayUtils;
import utils.Dialog;
import utils.Query;
import utils.StringUtils;

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
	
	public void addDeveloper(Developer dev){
		if(this.isDevAlreadyOnActivity(dev.getId())){
			Dialog.message("The developer is already on the activity.");
		}else{
			ActivityDeveloperRelation relation = this.db.activityDeveloperRelation().create(this, dev);
			Dialog.message(""+relation.getId());
//			System.out.println("devName: "+ dev.getName()+", devID: "+dev.getId());
			Dialog.message("Developer: " + dev.getName()+ " , activty: "+this.getDescription());
			this.db.activity().update(this);
			this.developers = this.getDevelopers();
			this.db.activity().update(this);
		}
	}
	
	public List<Developer> getDevelopers(){
		if(this.developers == null){
			this.developers = new ArrayList<Developer>();
		}else{
			this.developers.clear();
		}
		List<ActivityDeveloperRelation> omgRelations = this.db.activityDeveloperRelation().getRelationsOfActivity(this.getId());
		List<ActivityDeveloperRelation> omg2Relations = this.db.activityDeveloperRelation().getRelationsOfDeveloper(2);
		List<ActivityDeveloperRelation> relations =	this.db.activityDeveloperRelation().readAllWhereEquals("activity_id", this.getId());
		List<ActivityDeveloperRelation> debugRelations =	this.db.activityDeveloperRelation().readAllWhereEquals("developer_id", 2);
		System.out.println(relations.size()+", debugSize: " + debugRelations.size() + ", omgSize: " + omgRelations.size() + ", omgSize_2: " + omg2Relations.size());
		for(ActivityDeveloperRelation relation : relations){
			this.developers.add(relation.getDeveloper());
		}
		
		return this.developers;
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
	
	public Calendar getEndCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.endTime);
		return cal;
	}
	
	public void setEndTime(long newDate) {
		this.endTime = newDate;
		this.db.activity().update(this);
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
		System.out.println("" + devs.size()+", this.ID =" + this.getId());
		String[] devIDs = new String[devs.size()];
		
		for (int i = 0; i < devIDs.length; i++) {
			devIDs[i] = devs.get(i).getInitials();
		}
		
		return ArrayUtils.join(devIDs, ',');
	}
	
	public Project getProject() {
		return this.db.project().read(this.projectID);
	}

}
