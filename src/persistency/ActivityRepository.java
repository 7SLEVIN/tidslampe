package persistency;

import java.sql.ResultSet;
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
		int id = this.create(new String[]{description, String.valueOf(expectedTime), 
				String.valueOf(startTime), String.valueOf(endTime)});
		Activity activity = new Activity(id, description, expectedTime, 
				startTime, endTime);
		return activity;
	}

	@Override
	protected List<Activity> parse(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

}
