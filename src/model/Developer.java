package model;

import exceptions.UpdateNonExistingException;
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
		super(id,db,db.activity());
		
		this.initials = initials;
		this.name = name;
	}
	
	@Override
	public String[] getValueArray() {
		return new String[]{this.initials, this.name};
	}
	
	public String getInitials() {
		return this.initials;
	}

	public void setInitials(String ints) throws UpdateNonExistingException{
		this.initials = ints;
		this.save();
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) throws UpdateNonExistingException{
		this.name = name;
		this.save();
	}
}
