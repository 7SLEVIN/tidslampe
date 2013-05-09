package model;

import exceptions.DeleteNonExistingException;
import exceptions.UpdateNonExistingException;
import persistency.ActivityDeveloperRelationRepository;
import persistency.Database;
import persistency.Repository;

abstract public class DatabaseObject {

	private int id;
	protected Database database;
	protected Repository repository;
	
	public DatabaseObject(int id, Database database, Repository repository){
		this.id = id;
		this.database = database;
		this.repository = repository;
	}
	
	protected void save() throws UpdateNonExistingException {
		this.repository.update(this);
	}
	
	public void delete() throws DeleteNonExistingException {
		this.repository.delete(this);
	}
	
	public abstract String[] getValueArray();
	
	public int getId() {
		return this.id;
	}
	
}
