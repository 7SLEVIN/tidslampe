package model;


public class Developer extends DatabaseObject {
	
	private String initials;
	private String name;
	
	/**
	 * @param id
	 * @param initials
	 * @param name
	 */
	public Developer(int id, String initials, String name) {
		super(id);
		
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

	public String getName() {
		return this.name;
	}

	public String getInitials() {
		return this.initials;
	}

}
