package persistency;

import java.sql.ResultSet;
import java.util.ArrayList;

import app.Activity;
import app.ActivityDeveloperRelation;
import app.Developer;

public class ActivityDeveloperRelationRepository extends Repository<ActivityDeveloperRelation> {

	public ActivityDeveloperRelationRepository(DatabaseConnection conn) {
		super(conn);
		
		this.table = "activity_developer_relation";
		this.columns = new String[]{"activity_id", "developer_id"};
	}
	
	public ActivityDeveloperRelation create(Activity activity, Developer developer) {
		int id = this.create(new String[]{String.valueOf(activity.getId()), 
				String.valueOf(developer.getId())});
		ActivityDeveloperRelation rel = new ActivityDeveloperRelation(id, activity, developer);
		return rel;
	}

	@Override
	protected ArrayList parse(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

}
