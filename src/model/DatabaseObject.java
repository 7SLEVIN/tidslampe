package model;

import persistency.Database;

abstract public class DatabaseObject {

	private int id;
	protected Database db;
	
	public DatabaseObject(int id, Database db){
		this.id = id;
		this.db = db;
	}
	
	abstract protected void save();

	abstract protected void delete();
	
	public abstract String[] toArray();
	
	public int getId() {
		return this.id;
	}
	
	protected void setId(int id) {
		this.id = id;
	}
	
}
