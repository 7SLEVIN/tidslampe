package persistency;

import java.sql.ResultSet;
import java.util.List;

import exceptions.DeleteNonExistingException;
import exceptions.UpdateNonExistingException;

import utils.Query;

import model.DatabaseObject;

public abstract class Repository<T extends DatabaseObject> {

	protected String table;
	protected String[] columns;
	
	protected Database database;
	
	public Repository(Database db) {
		this.database = db;
	}
	
	abstract protected List<T> parse(ResultSet rs);

	protected int create(String[] values) {
		return this.database.getConnnection().create(this.table, this.columns, values);
	}

	public List<T> readAllWhereEquals(String key, String value)  {
		ResultSet rs = this.database.getConnnection().execQuery(Query.selectAllFrom(this.table).whereEquals(key, value));
		return this.parse(rs);
	}

	public List<T> readAllWhereEquals(String key, int value)  {
		ResultSet rs = this.database.getConnnection().execQuery(Query.selectAllFrom(this.table).whereEquals(key, value));
		return this.parse(rs);
	}

	public List<T> readAll()  {
		ResultSet rs = this.database.getConnnection().execQuery(Query.selectAllFrom(this.table));
		return this.parse(rs);
	}
	
	public T read(int id)  {
		ResultSet rs = this.database.getConnnection().execQuery(Query.selectAllFrom(this.table).whereEquals("id", id));
		List<T> results = this.parse(rs);
		return results.isEmpty() ? null : results.get(0);
	}
	
	public void update(T entity) throws UpdateNonExistingException {
		String[] values = entity.getValueArray();
		this.database.getConnnection().update(this.table, entity.getId(), this.columns, values);
	}
	
	public void delete(T entity) throws DeleteNonExistingException {
		this.database.getConnnection().delete(this.table, entity.getId());
	}
	
	public int count() {
		return this.database.getConnnection().count(this.table);
	}
	
	public boolean exists(int id) {
		return this.database.getConnnection().exists(this.table, id);
	}
}
