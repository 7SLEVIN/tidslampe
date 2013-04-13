package utils;

public class Query {
	private String query;
	private QueryStatement lastStatement;

	public static Query SelectAllFrom(String table) {
		return new Query()._SelectAllFrom(table);
	}

	public static Query InsertInto(String table, String[] columns,
			String[] values) {
		return new Query()._InsertInto(table, columns, values);
	}

	public Query() {
		this.query = "";
		this.lastStatement = QueryStatement.Empty;
	}

	public String End() {
		return this.query.trim();
	}

	private Query _SelectAllFrom(String table) {
		if (this.lastStatement != QueryStatement.Empty)
			System.err.println("Invalid query");
		// throw new InvalidFormatException("First statement must be SELECT");

		this.query += String.format("SELECT * FROM %s ", table);
		this.lastStatement = QueryStatement.Select;
		return this;
	}

	private Query WhereRaw(String key, String value, String operator) {
		if (this.lastStatement == QueryStatement.Where)
			this.query += "AND ";
		else
			this.query += "WHERE ";

		this.query += String.format("%s %s %s ", key, operator ,value);
		this.lastStatement = QueryStatement.Where;
		return this;
	}

	public Query WhereEquals(String key, String value) {
		return this.WhereRaw(key, "\"" + value + "\"", "=");
	}
	
	public Query WhereEquals(String key, int value) {
		return this.WhereRaw(key, String.valueOf(value), "=");
	}
	
	public Query WhereWeaklyLessThan(String key, int value) {
		return this.WhereRaw(key, String.valueOf(value), "<=");
	}
	
	public Query WhereWeaklyLessThan(String key, String value) {
		return this.WhereRaw(key, value, "<=");
	}
	
	public Query WhereWeaklyMoreThan(String key, int value) {
		return this.WhereRaw(key, String.valueOf(value), ">=");
	}
	
	public Query WhereWeaklyMoreThan(String key, String value) {
		return this.WhereRaw(key, value, ">=");
	}	

	private Query _InsertInto(String table, String[] columns, String[] values) {
		if (this.lastStatement != QueryStatement.Empty || columns.length != values.length)
			System.err.println("Invalid query");
		if (columns.length == 0)
			return this;

		this.query += String.format("INSERT INTO %s (%s) VALUES (%s)", 
				table,
				StringUtils.join(columns, ','), 
				StringUtils.join(ArrayUtils.wrapElementsWith(values, "\""), ','));
		
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
