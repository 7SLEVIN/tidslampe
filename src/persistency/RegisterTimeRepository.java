package persistency;

import java.util.List;

import model.ActivityDeveloperRelation;
import model.TimeEntry;
import utils.Query;

public class RegisterTimeRepository extends TimeRepository {

	public RegisterTimeRepository(Database db) {
		super(db);
		this.table = "register_time";
	}
	
	/**
	 * @return returns null if time is already in use!
	 */
	@Override
	public TimeEntry create(long startTime, long endTime, int devID, int actID, boolean isAssist) {
		ActivityDeveloperRelation actDevRel = this.db.activityDeveloperRelation().readOrCreate(devID, actID);
		return this.create(startTime, endTime, actDevRel, isAssist);
	}
	
	public TimeEntry create(long startTime, long endTime, ActivityDeveloperRelation rel, boolean isAssist) {
		String assistString = isAssist ? "1" : "0";
		
		if(this.isTimeUsed(startTime, endTime, rel.getDeveloper().getId()))
			return null;
		
		if(startTime < 0 || endTime < 0)
			return null;
		
		int id = this.create(new String[]{ String.valueOf(startTime), 
				String.valueOf(endTime),
				String.valueOf(rel.getId()),
				String.valueOf(rel.getDeveloper().getId()),
				assistString});
		
		TimeEntry entry = new TimeEntry(this.db, id, startTime, endTime, rel, isAssist); 
		return entry;
	}
	
	public List<TimeEntry> getCollidingEntries(long startTime, long endTime, int devID){
		List<TimeEntry> collidingEntries = this.parse(this.db.getConn().execQuery(Query.selectAllFrom(this.table)
				.whereLessOrEquals("start_time", endTime)
				.whereGreaterOrEquals("end_time", startTime)
				.whereEquals("developer_id", devID)));
		return collidingEntries;
	}
	
	/**
	 * Tells whether or not some time is already in use. Accepts 5 minutes overlap
	 * @param startTime
	 * @param endTime
	 * @param devID
	 * @return
	 */
	private boolean isTimeUsed(long startTime, long endTime, int devID){
		return this.getCollidingEntries(startTime+5*60*1000, endTime-5*60*1000, devID).size() > 0;
	}
}
