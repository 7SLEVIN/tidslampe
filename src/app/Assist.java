package app;

import persistency.DatabaseObject;

public class Assist extends DatabaseObject {
	
	private Developer developer;
	private Number spentTime;
	
	/**
	 * @param id
	 * @param developer
	 * @param spentTime
	 */
	public Assist(int id, Developer developer, Number spentTime) {
		super(id);
		
		this.developer = developer;
		this.spentTime = spentTime;
	}

	@Override
	protected void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void delete() {
		// TODO Auto-generated method stub
		
	}

	public Developer getDeveloper() {
		return developer;
	}

	public Number getSpentTime() {
		return spentTime;
	}
	
	

}
