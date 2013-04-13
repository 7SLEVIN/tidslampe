package persistency;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Activity;


public class ActivityRepository extends Repository<Activity> {

	public ActivityRepository(Database db) {
		super(db);
		
		this.table = "activity";
		this.columns = new String[]{"description", "expected_time", 
				"start_time", "end_time"};	
	}

	public Activity create(String description, Number expectedTime, 
			Long startTime, Long endTime) {
		
		int id = this.create(new String[]{description, String.valueOf(expectedTime), 
				String.valueOf(startTime), String.valueOf(endTime)});
		Activity activity = new Activity(this.db, id, description, expectedTime, 
				startTime, endTime);
		return activity;
	}

	@Override
	protected List<Activity> parse(ResultSet rs) {
		List<Activity> activities = new ArrayList<Activity>();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
		try {
			while (rs.next()) {
				activities.add(new Activity(this.db, rs.getInt("id"), 
						rs.getString(this.columns[0]), 
						rs.getDouble(this.columns[1]), 
						rs.getLong(this.columns[2]), 
						rs.getLong(this.columns[3])));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return activities;
	}

}
