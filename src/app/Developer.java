package app;

import persistency.DatabaseObject;

public class Developer extends DatabaseObject {
	
	private String initials;
	private String name;
	
	/**
	 * @param initials
	 * @param name
	 */
	public Developer(String initials, String name) {
		this.initials = initials;
		this.name = name;
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
