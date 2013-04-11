package model;

import persistency.Database;


public class ActivityDeveloperRelation extends DatabaseObject {

	private Activity activity;
	private Developer developer;

	/**
	 * @param id
	 * @param activity
	 * @param developer
	 */
	public ActivityDeveloperRelation(Database db, int id, Activity activity,
			Developer developer) {
		super(id,db);
		
		this.activity = activity;
		this.developer = developer;
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
		return new String[]{String.valueOf(this.activity.getId()), 
				String.valueOf(this.developer.getId())};
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

}
