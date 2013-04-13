package persistency;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Activity;
import model.ActivityDeveloperRelation;
import model.Developer;


public class ActivityDeveloperRelationRepository extends Repository<ActivityDeveloperRelation> {

	public ActivityDeveloperRelationRepository(Database db) {
		super(db);
		
		this.table = "activity_developer_relation";
		this.columns = new String[]{"activity_id", "developer_id"};
	}
	
	public ActivityDeveloperRelation create(Activity activity, Developer developer) {
		int id = this.create(new String[]{String.valueOf(activity.getId()), 
				String.valueOf(developer.getId())});
		ActivityDeveloperRelation rel = new ActivityDeveloperRelation(this.db, id, activity, developer);
		return rel;
	}

	@Override
	protected List<ActivityDeveloperRelation> parse(ResultSet rs) {
		List<ActivityDeveloperRelation> rel = new ArrayList<ActivityDeveloperRelation>();
		try {
			while (rs.next()) {
				int activity_id = rs.getInt(this.columns[0]);
				int developer_id = rs.getInt(this.columns[1]);
				rel.add(new ActivityDeveloperRelation(this.db, rs.getInt("id"), 
						this.db.getActivity().read(activity_id), 
						this.db.getDeveloper().read(developer_id)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rel;
	}

}
