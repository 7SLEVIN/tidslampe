package model;

import exceptions.DeleteNonExistingException;
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
	public void delete() throws DeleteNonExistingException {
		this.database.project().removeManager(this.getId());
		this.database.activityDeveloperRelation().deleteRelationsByDevID(this.getId());
		System.out.println(this.database.developer().exists(this.getId()));
		super.delete();
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
