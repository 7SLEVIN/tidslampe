package model;

import persistency.Database;


public class Assist extends DatabaseObject {
	
	private Developer developer;
	private Number spentTime;
	
	/**
	 * @param id
	 * @param developer
	 * @param spentTime
	 */
	public Assist(Database db, int id, Developer developer, Number spentTime) {
		super(id,db);
		
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

	@Override
	public String[] getValueArray() {
		return new String[]{String.valueOf(this.developer.getId()), 
				String.valueOf(this.spentTime)};
	}

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer){
		this.developer = developer;
		this.db.Assist().update(this);
	}
	
	public Number getSpentTime() {
		return spentTime;
	}
	
	public void setSpentTime(Number spentTime){
		this.spentTime = spentTime;
		this.db.Assist().update(this);
	}

}
