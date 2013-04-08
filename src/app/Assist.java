package app;

import persistency.DatabaseObject;

public class Assist extends DatabaseObject {
	
	private int id;
	private Developer developer;
	private Number spentTime;
	
	/**
	 * @param developer
	 * @param spentTime
	 */
	public Assist(int id, Developer developer, Number spentTime) {
		this.id = id;
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

}
