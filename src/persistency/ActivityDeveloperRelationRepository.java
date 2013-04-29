package persistency;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import utils.Query;

import model.Activity;
import model.ActivityDeveloperRelation;
import model.Developer;


public class ActivityDeveloperRelationRepository extends Repository<ActivityDeveloperRelation> {

	public ActivityDeveloperRelationRepository(Database db) {
		super(db);
		
		this.table = "activity_developer_relation";
		this.columns = new String[]{"activity_id", "developer_id"};
	}
	
	public List<Developer> getDevelopersFromActivityID(int id){
		List<ActivityDeveloperRelation> relations = this.db.activityDeveloperRelation().readAllWhereEquals("activity_id", id);
		List<Developer> devs = new ArrayList<Developer>();
		
		for(ActivityDeveloperRelation relation : relations){
			devs.add(relation.getDeveloper());
		}
		
		return devs;
	}
	
	public ActivityDeveloperRelation create(Activity activity, Developer developer) {
		int id = this.create(new String[]{String.valueOf(activity.getId()), 
				String.valueOf(developer.getId())});
		ActivityDeveloperRelation rel = new ActivityDeveloperRelation(this.db, id, activity, developer);
		return rel;
	}

	public ActivityDeveloperRelation create(int developerId, int activityId) {
		return this.create(this.db.activity().read(activityId), this.db.developer().read(developerId));
	}

	@Override
	protected List<ActivityDeveloperRelation> parse(ResultSet rs) {
		List<ActivityDeveloperRelation> rel = new ArrayList<ActivityDeveloperRelation>();
		try {
			while (rs.next()) {
				int activity_id = rs.getInt(this.columns[0]);
				int developer_id = rs.getInt(this.columns[1]);
				rel.add(new ActivityDeveloperRelation(this.db, rs.getInt("id"), 
						this.db.activity().read(activity_id), 
						this.db.developer().read(developer_id)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rel;
	}
	
	public List<ActivityDeveloperRelation> getRelationsOfActivity(int actID){
		Query query = Query.SelectAllFrom(this.table).WhereEquals("activity_id", actID);
		List<ActivityDeveloperRelation> matches = this.parse(this.db.getConn().execQuery(query));
		if (matches.isEmpty())
			return null;
		else 
			return matches;
	}
	
	public List<ActivityDeveloperRelation> getRelationsOfDeveloper(int devID){
		Query query = Query.SelectAllFrom(this.table).WhereEquals("developer_id", devID);
		List<ActivityDeveloperRelation> matches = this.parse(this.db.getConn().execQuery(query));
		if (matches.isEmpty())
			return null;
		else 
			return matches;
	}
	
	public ActivityDeveloperRelation readByDeveloperAndActivityId(int devID, int actID) {
		Query query = Query.SelectAllFrom(this.table).WhereEquals("developer_id", devID).WhereEquals("activity_id", actID);
		List<ActivityDeveloperRelation> matches = this.parse(this.db.getConn().execQuery(query));
		if (matches.isEmpty())
			return null;
		else 
			return matches.get(0);
	}

	public ActivityDeveloperRelation readOrCreate(int devID, int actID) {
		ActivityDeveloperRelation rel = this.readByDeveloperAndActivityId(devID, actID);
		if (rel == null)
			return this.create(devID, actID);
		
		return rel;
	}

}
