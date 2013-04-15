package utils;

import java.util.ArrayList;
import java.util.List;

public class Query {
	private String query;
	private QueryStatement lastStatement;

	// Factory methods
	public static Query SelectAllFrom(String table) {
		return new Query()._SelectAllFrom(table);
	}
	
	public static Query Select(String field) {
		return new Query()._Select(field);
	}
	
	public static Query DeleteFrom(String table) {
		return new Query()._DeleteFrom(table);
	}
	
	public static Query Update(String table, String[] columns, String[] values) {
		return new Query()._Update(table, columns, values);
	}
	
	public static Query Update(String table, String column, String value) {
		return new Query()._Update(table, new String[]{column}, new String[]{value});
	}

	public static Query InsertInto(String table, String[] columns,
			String[] values) {
		return new Query()._InsertInto(table, columns, values);
	}

	// Instance
	public Query() {
		this.query = "";
		this.lastStatement = QueryStatement.Empty;
	}

	public String End() {
		return this.query.trim();
	}

	private Query _SelectAllFrom(String table) {
		return this._Select("*").From(table);
	}

	private Query _DeleteFrom(String table) {
		if (this.lastStatement != QueryStatement.Empty)
			System.err.println("Invalid query");
		this.query = "DELETE ";
		this.lastStatement = QueryStatement.Delete;
		return this.From(table);
	}

	private Query _Select(String field) {
		if (this.lastStatement != QueryStatement.Empty)
			System.err.println("Invalid query");

		this.query += String.format("SELECT %s ", field);
		this.lastStatement = QueryStatement.Select;
		return this;
	}

	public Query From(String field) {
		if (this.lastStatement != QueryStatement.Select && this.lastStatement != QueryStatement.Delete)
			System.err.println("Invalid query");

		this.query += String.format("FROM %s ", field);
		this.lastStatement = QueryStatement.Select;
		return this;
	}


	private Query _Update(String table, String[] columns, String[] values) {
		if (this.lastStatement != QueryStatement.Empty)
			System.err.println("Invalid query");
			
		String[] vals = ArrayUtils.wrapElementsWith(values, "'");
		
		this.query = String.format("UPDATE %s SET ", table);
		
		List<String> fields = new ArrayList<String>();
		
		for (int i = 0; i < columns.length; i++) {
			System.out.print("columns[" + i + "]: ");
			System.out.println(columns[i] + ", vals: " + vals[i]);
			fields.add(String.format("%s=%s", columns[i], vals[i]));
		}
		this.query += ArrayUtils.join(fields.toArray(new String[]{}), ',') + " ";
		this.lastStatement = QueryStatement.Update;
		return this;
	}
	
	private Query WhereRaw(String key, long value, String operator) {
		if (this.lastStatement == QueryStatement.Where)
			this.query += "AND ";
		else
			this.query += "WHERE ";

		this.query += String.format("%s %s %s ", key, operator ,value);
		this.lastStatement = QueryStatement.Where;
		return this;
	}
	
	private Query WhereRaw(String key, String value, String operator) {
		if (this.lastStatement == QueryStatement.Where)
			this.query += "AND ";
		else
			this.query += "WHERE ";

		this.query += String.format("%s %s '%s' ", key, operator ,value);
		this.lastStatement = QueryStatement.Where;
		return this;
	}

	public Query WhereEquals(String key, String value) {
		return this.WhereRaw(key, value , "=");
	}
	
	public Query WhereEquals(String key, long value) {
		return this.WhereRaw(key, value, "=");
	}
	
	public Query WhereLessThan(String key, long value) {
		return this.WhereRaw(key, value, "<=");
	}
	
	public Query WhereLessThan(String key, String value) {
		return this.WhereRaw(key,  value, "<=");
	}
	
	public Query WhereMoreThan(String key, long value) {
		return this.WhereRaw(key, value, ">=");
	}
	
	public Query WhereMoreThan(String key, String value) {
		return this.WhereRaw(key,  value , ">=");
	}	

	private Query _InsertInto(String table, String[] columns, String[] values) {
		if (this.lastStatement != QueryStatement.Empty || columns.length != values.length)
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
	
	public Query OrderBy(String key) {
		return this.OrderBy(key, SortDirection.Asc);
	}

	public Query OrderBy(String key, SortDirection direction) {
		if (this.lastStatement != QueryStatement.Where
			&& this.lastStatement != QueryStatement.Select)
			System.err.println("Invalid query");
		
		String dir = direction == SortDirection.Asc ? "ASC" : "DESC";
		this.query += String.format("ORDER BY %s %s ", key, dir);
		this.lastStatement = QueryStatement.OrderBy;
		return this;
	}

	public Query Limit(int lim) {
		if (this.lastStatement != QueryStatement.Where
			&& this.lastStatement != QueryStatement.Select
			&& this.lastStatement != QueryStatement.OrderBy)
			System.err.println("Invalid query");
			
		this.query += String.format("LIMIT %d", lim);
		this.lastStatement = QueryStatement.Limit;
		return this;
	}
}
