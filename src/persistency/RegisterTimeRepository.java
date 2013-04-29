package persistency;

import java.util.List;

import utils.Query;
import model.Activity;
import model.ActivityDeveloperRelation;
import model.TimeEntry;

public class RegisterTimeRepository extends TimeRepository {

	public RegisterTimeRepository(Database db) {
		super(db);
		this.table = "register_time";
	}
	
	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @param devID
	 * @param actID
	 * @return returns null if time is already in use!
	 */
	@Override
	public TimeEntry create(long startTime, long endTime, int devID, int actID) {
		ActivityDeveloperRelation actDevRel = this.db.activityDeveloperRelation().readOrCreate(devID, actID);
		return this.create(startTime, endTime, actDevRel);
	}
	
	public TimeEntry create(long startTime, long endTime, ActivityDeveloperRelation rel) {
		if(this.isTimeUsed(startTime, endTime, rel.getDeveloper().getId()))
			return null;
		
		if(startTime == -1L || endTime == -1L)
			return null;
		
		int id = this.create(new String[]{String.valueOf(startTime), String.valueOf(endTime),String.valueOf(rel.getId()),String.valueOf(rel.getDeveloper().getId())});
		
		TimeEntry entry = new TimeEntry(this.db, id, startTime, endTime, rel.getId()); 
		return entry;
	}
	
	public List<TimeEntry> getCollidingEntries(long startTime, long endTime, int devID){
		List<TimeEntry> collidingEntries = this.parse(this.db.getConn().execQuery(Query.SelectAllFrom(this.table)
				.WhereLessThan("start_time", endTime)
				.WhereMoreThan("end_time", startTime)
				.WhereEquals("developer_id", devID)));
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
