package persistency;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.Activity;

public class ActivityRepository extends Repository<Activity> {

	public ActivityRepository(Database db) {
		super(db);
		
		this.table = "activity";
		this.columns = new String[]{"description", "expected_time", 
				"start_time", "end_time"};	
	}

	public Activity create(String description, Number expectedTime, 
			Date startTime, Date endTime) {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
		
		int id = this.create(new String[]{description, String.valueOf(expectedTime), 
				df.format(startTime), df.format(endTime)});
		Activity activity = new Activity(id, description, expectedTime, 
				startTime, endTime);
		return activity;
	}

	@Override
	protected List<Activity> parse(ResultSet rs) {
		List<Activity> activities = new ArrayList<Activity>();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
		try {
			while (rs.next()) {
				activities.add(new Activity(rs.getInt("id"), 
						rs.getString(this.columns[0]), 
						rs.getDouble(this.columns[1]), 
						df.parse(rs.getString(this.columns[2])), 
						df.parse(rs.getString(this.columns[3]))));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return activities;
	}

}
