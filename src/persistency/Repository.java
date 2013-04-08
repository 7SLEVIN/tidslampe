package persistency;

import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class Repository<T extends DatabaseObject> {

	protected String table;
	protected String[] columns;
	
	private Database database;
	
	/**
	 * 
	 */
	public Repository(Database database) {
		this.database = database;
	}
	
	abstract protected ArrayList<T> parse(ResultSet rs);

	public T create(String[] columns) {
		// TODO
		return null;
	}
	
	public T read(int id) {
		// TODO
		return null;
	}
	
	public void update(T entity) {
		// TODO
	}
	
	public void delete(int id) {
		// TODO
	}
	
	public void delete(T entity) {
		// TODO
	}
	
}
