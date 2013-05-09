package persistency;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import exceptions.DeleteNonExistingException;

import model.Activity;
import model.ActivityDeveloperRelation;
import model.Developer;
import utils.Query;


public class ActivityDeveloperRelationRepository extends Repository<ActivityDeveloperRelation> {

	public ActivityDeveloperRelationRepository(Database db) {
		super(db);
		
		this.table = "activity_developer_relation";
		this.columns = new String[]{"activity_id", "developer_id"};
	}
	
	public void deleteRelationsByDevID(int id) throws DeleteNonExistingException{
		List<ActivityDeveloperRelation> relations = this.getRelationsOfDeveloper(id);
		for(ActivityDeveloperRelation relation : relations){
			relation.delete();
		}
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
		List<Integer> activityIDs= new ArrayList<Integer>();
		List<Integer> developerIDs= new ArrayList<Integer>();
		List<Integer> relationIDs= new ArrayList<Integer>(); //Not so smooth, but made to avoid opening a db-connection while another is open.
		try {
			while (rs.next()) {
				activityIDs.add(rs.getInt(this.columns[0]));
				developerIDs.add(rs.getInt(this.columns[1]));
				relationIDs.add(rs.getInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i < activityIDs.size(); i++){
			rel.add(new ActivityDeveloperRelation(this.db, relationIDs.get(i), 
					this.db.activity().read(activityIDs.get(i)), 
					this.db.developer().read(developerIDs.get(i))));
		}
		return rel;
	}
	
	public List<ActivityDeveloperRelation> getRelationsOfActivity(int actID){
		Query query = Query.selectAllFrom(this.table).whereEquals("activity_id", actID);
		List<ActivityDeveloperRelation> matches = this.parse(this.db.getConn().execQuery(query));
		if (matches.isEmpty())
			return null;
		else 
			return matches;
	}
	
	public List<ActivityDeveloperRelation> getRelationsOfDeveloper(int devID){
		Query query = Query.selectAllFrom(this.table).whereEquals("developer_id", devID);
		List<ActivityDeveloperRelation> matches = this.parse(this.db.getConn().execQuery(query));
		return matches;
	}
	
	public void removeRelationsByDev(int id) throws DeleteNonExistingException{
		List<ActivityDeveloperRelation> relations = this.getRelationsOfDeveloper(id);
		for (ActivityDeveloperRelation activityDeveloperRelation : relations) {
			activityDeveloperRelation.delete();
		}
	}
	
	public ActivityDeveloperRelation readByDeveloperAndActivityId(int devID, int actID) {
		Query query = Query.selectAllFrom(this.table).whereEquals("developer_id", devID).whereEquals("activity_id", actID);
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
