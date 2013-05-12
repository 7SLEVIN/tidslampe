package persistency;

import org.junit.Before;

import utils.Dialog;

import app.ProjectPlanner;

public class BaseTestDatabase {
	
	protected Database db;
	protected ProjectPlanner projectPlanner;
	
	public BaseTestDatabase() {
		Dialog.setDebugMode(true);
		this.db = new Database("test_db.db");
		this.projectPlanner = new ProjectPlanner(this.db);
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
		this.db.getConn().execUpdate("delete from register_time");
		this.db.getConn().execUpdate("delete from reserve_time");
	}

}
