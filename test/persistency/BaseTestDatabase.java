package persistency;

import model.Activity;
import model.Developer;

import org.junit.Before;

import utils.Dialog;
import utils.DialogChoice;
import app.ProjectPlanner;

public class BaseTestDatabase {
	
	protected Database db;
	protected ProjectPlanner projectPlanner;
	
	public BaseTestDatabase() {
		Dialog.setDebugMode(true, DialogChoice.None);
		this.db = new Database("test_db.db");
		this.projectPlanner = new ProjectPlanner(this.db);
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
	
	protected void addProjects(){
		Long deadline = this.projectPlanner.getTimeService().convertToMillis(2013, 05, 13, 12, 0);
		this.db.project().create("TestProject_01", 100, deadline);
		this.db.project().create("TestProject_02", 100, deadline);
	}
	
	protected void addActivities(){
		Long deadline = this.projectPlanner.getTimeService().convertToMillis(2013, 05, 13, 12, 0);
		Long deadlineLate = this.projectPlanner.getTimeService().convertToMillis(2013, 06, 13, 12, 0);
		Activity activity1 = this.db.activity().createProjectActivity(1, "Test Activity A", 6, deadline, deadline);
		Activity activity2 = this.db.activity().createProjectActivity(1, "Test Activity B", 6, deadlineLate, deadlineLate);
		Developer developer = this.db.developer().readByInitials("PM").get(0);

		this.db.activityDeveloperRelation().create(activity1, developer);
		this.db.activityDeveloperRelation().create(activity2, developer);
	}
	
	protected void addDevelopers(){
		this.db.developer().create("PM", "Paul McCartney");
		this.db.developer().create("JL", "John Lennon");
		this.db.developer().create("GH", "George Harrison");
		this.db.developer().create("RS", "Ringo Starr");
	}

}
