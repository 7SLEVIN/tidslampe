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

		this.db.getConn().execUpdate("create table if not exists project (id integer primary key autoincrement, name string, hour_budget float, deadline BIGINT, manager_id integer)");
		this.db.getConn().execUpdate("create table if not exists developer (id integer primary key autoincrement, initials string, name string)");
		this.db.getConn().execUpdate("create table if not exists activity (id integer primary key autoincrement,activity_type string, description string, expected_time integer, start_time BIGINT, end_time BIGINT, project_id integer)");
		this.db.getConn().execUpdate("create table if not exists activity_developer_relation (id integer primary key autoincrement, activity_id integer, developer_id integer)");
		this.db.getConn().execUpdate("create table if not exists assist (id integer primary key autoincrement, developer_id integer, spent_time float)");
		this.db.getConn().execUpdate("create table if not exists register_time (id integer primary key autoincrement, start_time BIGINT, end_time BIGINT,developer_activity_relation_id integer,developer_id integer)");
		this.db.getConn().execUpdate("create table if not exists reserve_time (id integer primary key autoincrement, start_time BIGINT, end_time BIGINT,developer_activity_relation_id integer,developer_id integer)");
	}
	
	protected void addProjects(){
		Long deadline = this.projectPlanner.getTimeService().convertToMillis(2013, 05, 13, 12, 0);
		this.db.project().create("TestProject_01", 100, deadline);
		this.db.project().create("TestProject_02", 100, deadline);
	}
	
	protected void addDevelopers(){
		this.db.developer().create("PM", "Paul McCartney");
		this.db.developer().create("JL", "John Lennon");
		this.db.developer().create("GH", "George Harrison");
		this.db.developer().create("RS", "Ringo Starr");
	}
	
	@Before
	public void setUp() throws Exception {
		this.db.getConn().execUpdate("delete from project");
		this.db.getConn().execUpdate("delete from developer");
		this.db.getConn().execUpdate("delete from activity");
		this.db.getConn().execUpdate("delete from activity_developer_relation");
		this.db.getConn().execUpdate("delete from assist");
		this.db.getConn().execUpdate("delete from register_time");
		this.db.getConn().execUpdate("delete from reserve_time");
	}

	@After
	public void tearDown() throws Exception {
//		this.db.getConn().execUpdate("drop table project");
//		this.db.getConn().execUpdate("drop table developer");
//		this.db.getConn().execUpdate("drop table activity");
//		this.db.getConn().execUpdate("drop table activity_developer_relation");
//		this.db.getConn().execUpdate("drop table assist");
//		this.db.getConn().execUpdate("drop table register_time");
//		this.db.getConn().execUpdate("drop table reserve_time");
	}

}
