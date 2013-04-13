package persistency;

import java.sql.ResultSet;
import java.util.List;

import model.DatabaseObject;

public abstract class Repository<T extends DatabaseObject> {

	protected String table;
	protected String[] columns;
	
	protected Database db;
	
	/**
	 * @param conn
	 */
	public Repository(Database db) {
		this.db = db;
	}
	
	abstract protected List<T> parse(ResultSet rs);

	protected int create(String[] values) {
		return this.db.conn.create(this.table, this.columns, values);
	}

	public List<T> readAllWhere(String key, String value)  {
		ResultSet rs = this.db.conn.readAllWhere(this.table, key, value);
		return this.parse(rs);
	}

	public List<T> readAll()  {
		ResultSet rs = this.db.conn.readAll(this.table);
		return this.parse(rs);
	}
	
	public T read(int id)  {
		ResultSet rs = this.db.conn.readByID(this.table, id);
		List<T> results = this.parse(rs);
		return results.isEmpty() ? null : results.get(0);
	}
	
	protected void update(T entity) {
		String[] values = entity.toArray();
		this.db.conn.update(this.table, entity.getId(), this.columns, values);
	}
	
	public void delete(int id) {
		this.db.conn.delete(this.table, id);
	}
	
	public int count() {
		return this.db.conn.count(this.table);
	}
}
