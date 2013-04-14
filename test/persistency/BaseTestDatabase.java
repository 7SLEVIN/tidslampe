package persistency;

import org.junit.After;
import org.junit.Before;

import app.ProjectPlanner;

public class BaseTestDatabase {
	
	protected Database db;
	protected ProjectPlanner projectPlanner;
	
	public BaseTestDatabase() {
		this.db = new Database("test_db.db");
		this.projectPlanner = new ProjectPlanner(this.db);

		this.db.getConn().execUpdate("create table if not exists project (id integer primary key autoincrement, name string, hour_budget float, deadline integer, manager_id integer)");
		this.db.getConn().execUpdate("create table if not exists developer (id integer primary key autoincrement, initials string, name string)");
		this.db.getConn().execUpdate("create table if not exists activity (id integer primary key autoincrement, description string, expected_time float, start_time BIGINT, end_time BIGINT)");
		this.db.getConn().execUpdate("create table if not exists activity_developer_relation (id integer primary key autoincrement, activity_id integer, developer_id integer)");
		this.db.getConn().execUpdate("create table if not exists assist (id integer primary key autoincrement, developer_id integer, spent_time float)");
		this.db.getConn().execUpdate("create table if not exists time_entry (id integer primary key autoincrement, start_time BIGINT, end_time BIGINT,developer_activity_relation_id integer,developer_id integer)");
	}
	
	@Before
	public void setUp() throws Exception {
		
		this.db.getConn().execUpdate("delete from project");
		this.db.getConn().execUpdate("delete from developer");
		this.db.getConn().execUpdate("delete from activity");
		this.db.getConn().execUpdate("delete from activity_developer_relation");
		this.db.getConn().execUpdate("delete from assist");
 
	}

	@After
	public void tearDown() throws Exception {
//		this.db.getConn().execUpdate("drop table project");
//		this.db.getConn().execUpdate("drop table developer");
//		this.db.getConn().execUpdate("drop table activity");
//		this.db.getConn().execUpdate("drop table activity_developer_relation");
//		this.db.getConn().execUpdate("drop table assist");
	}

}
