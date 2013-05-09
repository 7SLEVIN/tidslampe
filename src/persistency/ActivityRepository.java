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
		Query query = Query.selectAllFrom(this.table).whereIn("id", 
					Query.select("id").from("activity_developer_relation").whereEquals("developer_id", developerId)).orderBy("start_time");
		return this.parse(this.db.conn.execQuery(query));
	}
	
	public Activity createProjectActivity(int projectID, String description, int expectedTime, long startTime, long endTime){
		int id = this.create(new String[]{ActivityType.PROJECT.name(), description, 
				String.valueOf(expectedTime), String.valueOf(startTime),
				String.valueOf(endTime), String.valueOf(projectID)});
		
		Activity activity = new Activity(this.db, id, projectID, description, expectedTime, startTime, endTime);
		return activity;
	}

	public Activity createFixedActivity(ActivityType type, String description, long startTime, long endTime){
		int id = this.create(new String[]{type.name(), description, 
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
		ResultSet rs = this.db.conn.execQuery(Query.selectAllFrom(this.table).whereEquals("id", id));
		try {
			while (rs.next()) {
				isFixed = !rs.getString("activity_type").equals(ActivityType.PROJECT.name()); //If not project-activity, then fixed-activity
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFixed;
	}

	public List<Activity> readByDeveloperAndProjectId(int projectId, int developerId) {
		Query query = Query.selectAllFrom("activity a").whereEquals("a.project_id", projectId).whereIn("a.id", 
					Query.select("activity_id").from("activity_developer_relation adr").whereEquals("adr.developer_id", developerId));
		ResultSet rs = this.db.conn.execQuery(query);
		return this.parse(rs);
	}
}
