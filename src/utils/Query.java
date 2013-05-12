package utils;

import java.util.ArrayList;
import java.util.List;

public class Query {
	private String query;
	private QueryStatement lastStatement;

	// Factory methods
	public static Query selectAllFrom(String table) {
		return new Query().innerSelectAllFrom(table);
	}
	
	public static Query select(String field) {
		return new Query().innerSelect(field);
	}
	
	public static Query deleteFrom(String table) {
		return new Query().innerDeleteFrom(table);
	}
	
	public static Query update(String table, String[] columns, String[] values) {
		return new Query().innerUpdate(table, columns, values);
	}
	
	public static Query update(String table, String column, String value) {
		return new Query().innerUpdate(table, new String[]{column}, new String[]{value});
	}

	public static Query insertInto(String table, String[] columns,
			String[] values) {
		return new Query().innerInsertInto(table, columns, values);
	}
	
	public static Query exists(String table, int id) {
		return new Query().innerExists(table, id);
	}
	
	public static Query count(String table) {
		return new Query().innerCount(table);
	}

	// Instance
	public Query() {
		this.query = "";
		this.lastStatement = QueryStatement.Empty;
	}

	public String end() {
		return this.query.trim();
	}

	private Query innerSelectAllFrom(String table) {
		return this.innerSelect("*").from(table);
	}

	private Query innerDeleteFrom(String table) {
		this.query = "DELETE ";
		this.lastStatement = QueryStatement.Delete;
		return this.from(table);
	}

	private Query innerSelect(String field) {
		this.query += String.format("SELECT %s ", field);
		this.lastStatement = QueryStatement.Select;
		return this;
	}

	public Query from(String field) {
		if (this.lastStatement != QueryStatement.Select && this.lastStatement != QueryStatement.Delete)
			System.err.println("Invalid query");

		this.query += String.format("FROM %s ", field);
		this.lastStatement = QueryStatement.Select;
		return this;
	}


	private Query innerUpdate(String table, String[] columns, String[] values) {
		String[] vals = ArrayUtils.wrapElementsWith(values, "'");
		
		this.query = String.format("UPDATE %s SET ", table);
		
		List<String> fields = new ArrayList<String>();
		
		for (int i = 0; i < columns.length; i++) {
			fields.add(String.format("%s=%s", columns[i], vals[i]));
		}
		this.query += ArrayUtils.join(fields.toArray(new String[]{}), ',') + " ";
		this.lastStatement = QueryStatement.Update;
		return this;
	}
	
	private Query whereRaw(String key, long value, String operator) {
		if (this.lastStatement == QueryStatement.Where)
			this.query += "AND ";
		else
			this.query += "WHERE ";

		this.query += String.format("%s %s %s ", key, operator ,value);
		this.lastStatement = QueryStatement.Where;
		return this;
	}
	
	private Query whereRaw(String key, String value, String operator, boolean suppressLiteral) {
		if (this.lastStatement == QueryStatement.Where)
			this.query += "AND ";
		else
			this.query += "WHERE ";
		
		String q = suppressLiteral ? "%s %s %s " : "%s %s '%s' ";
		this.query += String.format(q, key, operator ,value);
		this.lastStatement = QueryStatement.Where;
		return this;
	}

	private Query whereRaw(String key, String value, String operator) {
		return this.whereRaw(key, value, operator, false);
	}

	public Query whereIn(String key, Query query) {
		return this.whereRaw(key, String.format("(%s)", query.end()), "IN", true);
	}

	public Query whereEquals(String key, String value) {
		return this.whereRaw(key, value , "=");
	}
	
	public Query whereEquals(String key, long value) {
		return this.whereRaw(key, value, "=");
	}
	
	public Query whereLessOrEquals(String key, long value) {
		return this.whereRaw(key, value, "<=");
	}
	
	public Query whereLessOrEquals(String key, String value) {
		return this.whereRaw(key,  value, "<=");
	}
	
	public Query whereGreaterOrEquals(String key, long value) {
		return this.whereRaw(key, value, ">=");
	}
	
	public Query whereGreaterOrEquals(String key, String value) {
		return this.whereRaw(key,  value , ">=");
	}	

	private Query innerInsertInto(String table, String[] columns, String[] values) {
		if (columns.length != values.length)
			System.err.println("Invalid query");
		if (columns.length == 0)
			return this;

		this.query += String.format("INSERT INTO %s (%s) VALUES (%s)", 
				table,
				ArrayUtils.join(columns, ','), 
				ArrayUtils.join(ArrayUtils.wrapElementsWith(values, "\""), ','));
		
		this.lastStatement = QueryStatement.Insert;
		return this;
	}
	
	public Query orderBy(String key) {
		return this.orderBy(key, SortDirection.Asc);
	}

	public Query orderBy(String key, SortDirection direction) {
		if (this.lastStatement != QueryStatement.Where
			&& this.lastStatement != QueryStatement.Select)
			System.err.println("Invalid query");
		
		String dir = direction == SortDirection.Asc ? "ASC" : "DESC";
		this.query += String.format("ORDER BY %s %s ", key, dir);
		this.lastStatement = QueryStatement.OrderBy;
		return this;
	}

	public Query limit(int lim) {
		if (this.lastStatement != QueryStatement.Where
			&& this.lastStatement != QueryStatement.Select
			&& this.lastStatement != QueryStatement.OrderBy)
			System.err.println("Invalid query");
			
		this.query += String.format("LIMIT %d", lim);
		this.lastStatement = QueryStatement.Limit;
		return this;
	}
	
	private Query innerExists(String table, int id) {
		this.query += String.format("SELECT EXISTS(SELECT * FROM %s WHERE id=%d)", table, id);
		return this;
	}
	
	private Query innerCount(String table) {
		return this.innerSelect("COUNT(*)").from(table);
	}
	
}
