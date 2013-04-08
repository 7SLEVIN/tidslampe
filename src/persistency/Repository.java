package persistency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Repository<T extends DatabaseObject> {

	protected String table;
	protected String[] columns;
	
	private DatabaseConnection conn;
	
	/**
	 * @param conn
	 */
	public Repository(DatabaseConnection conn) {
		this.conn = conn;
	}
	
	abstract protected ArrayList<T> parse(ResultSet rs);

	protected int create(String[] values) {
		return this.conn.insert(this.table, this.columns, values);
	}
	
	public T read(int id) throws SQLException {
		ResultSet rs = this.conn.read(this.table, id);
		return this.parse(rs).get(0);
	}
	
	protected void update(T entity) {
		// TODO
	}
	
	public void delete(int id) {
		// TODO
	}
	
}
