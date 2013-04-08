package persistency;

import java.sql.ResultSet;
import java.util.ArrayList;

import app.Activity;

public class ActivityRepository extends Repository<Activity> {

	public ActivityRepository(Database database) {
		super(database);
		
		this.table = "activity";
		this.columns = new String[]{"id", "description", "developer", 
				"expectedTime", "startTime", "endTime"};	
	}

	@Override
	protected ArrayList<Activity> parse(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

}
