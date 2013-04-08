package persistency;

abstract public class DatabaseObject {

	private int id;
	protected static Database database;
	
	public DatabaseObject(int id) {
		this.id = id;
	}
	
	abstract protected void save();
	
	abstract protected void delete();
	
	protected int getId() {
		return this.id;
	}
	
	protected void setId(int id) {
		this.id = id;
	}
	
}
