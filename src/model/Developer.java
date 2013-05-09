package model;

import persistency.Database;


public class Developer extends DatabaseObject {
	
	private String initials;
	private String name;
	
	/**
	 * @param id
	 * @param initials
	 * @param name
	 */
	public Developer(Database db, int id, String initials, String name) {
		super(id,db);
		
		this.initials = initials;
		this.name = name;
	}

	@Override
	protected void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		this.db.project().removeManager(this.getId());
		this.db.activityDeveloperRelation().deleteRelationsByDevID(this.getId());	
		this.db.developer().delete(this.getId());
	}
	
	@Override
	public String[] getValueArray() {
		return new String[]{this.initials, this.name};
	}
	
	public String getInitials() {
		return this.initials;
	}

	public void setInitials(String ints){
		this.initials = ints;
		this.db.developer().update(this);
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name){
		this.name = name;
		this.db.developer().update(this);
	}
}
