package persistency;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import utils.Query;

import model.Activity;
import model.ActivityType;


public class ActivityRepository extends Repository<Activity> {

	public ActivityRepository(Database db) {
		super(db);
		
		this.table = "activity";
		this.columns = new String[]{"activity_type" , "description", "expected_time", 
				"start_time", "end_time", "project_id"};	
		
	}
	
	public List<Activity> readByDeveloperId(int developerId) {
		this.db.conn.execQuery("select * from activity where project_id in (select id from project");
		return null;
	}
	
	public Activity createProjectActivity(int projectID, String description, int expectedTime, long startTime, long endTime){
		int id = this.create(new String[]{ActivityType.PROJECT.name(), description, 
				String.valueOf(expectedTime), String.valueOf(startTime),
				String.valueOf(endTime), String.valueOf(projectID)});
		
		Activity activity = new Activity(this.db, id, projectID, description, expectedTime, startTime, endTime);
		return activity;
	}

	public Activity createFixedActivity(ActivityType type, String description, long startTime, long endTime){
		int id = this.create(new String[]{ActivityType.PROJECT.name(), description, 
				"-1", String.valueOf(startTime),
				String.valueOf(endTime), "-1"});
		
		Activity activity = new Activity(this.db, id, type, description, startTime, endTime);
		return activity;
	}

	@Override
	protected List<Activity> parse(ResultSet rs) {
		List<Activity> activities = new ArrayList<Activity>();
		try {
			while (rs.next()) {
				
				if(rs.getString("activity_type").equals(ActivityType.PROJECT.name())){
					//Project-Activity
					activities.add(new Activity(db, rs.getInt("id"), rs.getInt("project_id"),
							rs.getString("description"), rs.getInt("expected_time"),
							rs.getLong("start_time"), rs.getLong("end_time")));
					
				}else{
					//Fixed-Activity
					activities.add(new Activity(db, rs.getInt("id"), ActivityType.valueOf(rs.getString("activity_type")),
							rs.getString("description"), rs.getLong("start_time"), rs.getLong("end_time")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return activities;
	}

	public boolean isFixed(int id){
		boolean isFixed = false;
		ResultSet rs = this.db.conn.execQuery(Query.SelectAllFrom(this.table).WhereEquals("id", id));
		try {
			while (rs.next()) {
				isFixed = rs.getString("activity_type") != ActivityType.PROJECT.name(); //If not project-activity, then fixed-activity
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFixed;
	}
}
