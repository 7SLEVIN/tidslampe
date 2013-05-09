package model;

import persistency.Database;
import exceptions.UpdateNonExistingException;


public class ActivityDeveloperRelation extends DatabaseObject {

	private Activity activity;
	private Developer developer;

	/**
	 * @param id
	 * @param activity
	 * @param developer
	 */
	public ActivityDeveloperRelation(Database database, int id, Activity activity,
			Developer developer) {
		super(id,database,database.activityDeveloperRelation());
				
		this.activity = activity;
		this.developer = developer;
	}

	@Override
	public String[] getValueArray() {
		return new String[]{String.valueOf(this.activity.getId()), 
				String.valueOf(this.developer.getId())};
	}

	public Activity getActivity() {
		return activity;
	}
	
	public void setActivity(Activity activity) throws UpdateNonExistingException{
		this.activity = activity;
		this.save();
	}

	public Developer getDeveloper() {
		return developer;
	}
	
	public void setDeveloper(Developer developer) throws UpdateNonExistingException{
		this.developer = developer;
		this.save();
	}


}
