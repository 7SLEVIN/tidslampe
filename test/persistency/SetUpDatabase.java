package persistency;

import org.junit.After;
import org.junit.Before;

public class SetUpDatabase {
	
	protected Database db;

	@Before
	public void setUp() throws Exception {
		 this.db = new Database();
		 this.db.getConn().execUpdate("create table project (id integer primary key autoincrement, name string, hour_budget float, deadline integer, manager_id integer)");
		 this.db.getConn().execUpdate("create table developer (id integer primary key autoincrement, initials string, name string)");
		 this.db.getConn().execUpdate("create table activity (id integer primary key autoincrement, description string, expected_time float, start_time string, end_time string)");
		 this.db.getConn().execUpdate("create table activity_developer_relation (id integer primary key autoincrement, activity_id integer, developer_id integer)");
		 this.db.getConn().execUpdate("create table assist (id integer primary key autoincrement, developer_id integer, spent_time float)");
	}

	@After
	public void tearDown() throws Exception {
		this.db.getConn().execUpdate("drop table project");
		this.db.getConn().execUpdate("drop table developer");
		this.db.getConn().execUpdate("drop table activity");
		this.db.getConn().execUpdate("drop table activity_developer_relation");
		this.db.getConn().execUpdate("drop table assist");
	}

}
