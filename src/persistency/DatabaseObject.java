package persistency;

abstract public class DatabaseObject {

	private int id;
	protected static Database database;
	
	protected int getId() {
		return this.id;
	}
	
	protected void setId(int id) {
		this.id = id;
	}
	
	abstract void save();
	
	abstract void delete();
	
}
