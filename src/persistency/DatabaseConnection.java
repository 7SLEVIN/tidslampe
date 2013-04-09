package persistency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.ArrayUtils;
import utils.StringUtils;

public class DatabaseConnection {

	private Connection conn;
	private Statement stmt;

	public DatabaseConnection(String dbFile) {
		try {
			Class.forName("org.sqlite.JDBC");

			this.conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
			this.stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int create(String table, String[] columns, String[] values) {
		String[] cols = ArrayUtils.wrapElementsWith(columns, "\"");
		String[] vals = ArrayUtils.wrapElementsWith(values, "\"");
		final String query = String
				.format("insert into %s (%s) values (%s)", table,
						StringUtils.join(cols, ','),
						StringUtils.join(vals, ','));
		int id = -1;
		try {
			this.execUpdate(query);

			// Get the id of the inserted row
			ResultSet rs = this.execQuery("select last_insert_rowid()");
			if (rs.next()) {
				id = rs.getInt("last_insert_rowid()");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public ResultSet execQuery(String query) throws SQLException {
//		System.out.println(query);
		return this.stmt.executeQuery(query);
	}

	public int execUpdate(String query) throws SQLException {
//		System.out.println(query);
		return this.stmt.executeUpdate(query);
	}

	public ResultSet read(String table, int id) throws SQLException {
		String query = String.format("select * from %s where id=%d", table, id);
		return this.execQuery(query);
	}

	public ResultSet readAll(String table) throws SQLException {
		String query = String.format("select * from %s", table);
		return this.execQuery(query);
	}
}
