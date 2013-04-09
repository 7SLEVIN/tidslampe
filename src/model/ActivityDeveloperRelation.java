package model;


public class ActivityDeveloperRelation extends DatabaseObject {

	private Activity activity;
	private Developer developer;

	/**
	 * @param id
	 * @param activity
	 * @param developer
	 */
	public ActivityDeveloperRelation(int id, Activity activity,
			Developer developer) {
		super(id);
		
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

	public Activity getActivity() {
		return activity;
	}

	public Developer getDeveloper() {
		return developer;
	}

}
