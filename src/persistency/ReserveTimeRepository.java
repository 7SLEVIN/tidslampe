package persistency;

import java.util.List;

import utils.Query;
import model.ActivityDeveloperRelation;
import model.TimeEntry;

public class ReserveTimeRepository extends TimeRepository {

	

	public ReserveTimeRepository(Database db) {
		super(db);
		this.table = "reserve_time";
	}
	@Override
	public TimeEntry create(long startTime, long endTime, int devID, int actID, boolean isAssist) {
		ActivityDeveloperRelation actDevRel = this.database.activityDeveloperRelation().readOrCreate(devID, actID);
		return this.create(startTime, endTime, actDevRel, isAssist);
	}
	
	public TimeEntry create(long startTime, long endTime, ActivityDeveloperRelation rel, boolean isAssist) {
		int activityID = rel.getActivity().getId();
		int devID = rel.getDeveloper().getId();
		String assistString = isAssist ? "1" : "0";
		if(this.isTimeUsed(startTime, endTime, devID))
			return null;
		
		if (this.database.activity().isFixed(activityID)) {
			//Hvis aktiviteten er fixed, så skal den bare registreres med det samme
			return this.database.registerTime().create(startTime, endTime, rel, isAssist);
		} else {
			int id = this.create(new String[]{String.valueOf(startTime), 
					String.valueOf(endTime),
					String.valueOf(rel.getId()),
					String.valueOf(devID), assistString});
			
			TimeEntry entry = new TimeEntry(this.database, id, startTime, endTime, rel, isAssist); 
			return entry;
		}
	}
	
	private List<TimeEntry> getCollidingEntries(long startTime, long endTime, int devID){ 
		List<TimeEntry> collidingEntries = this.parse(this.database.getConn().execQuery(Query.selectAllFrom(this.table)
				.whereLessOrEquals("start_time", endTime)
				.whereGreaterOrEquals("end_time", startTime)
				.whereEquals("developer_id", devID)));
		
		collidingEntries.addAll(this.database.registerTime().getCollidingEntries(startTime, endTime, devID));
		
		return collidingEntries;
	}
	
	private boolean isTimeUsed(long startTime, long endTime, int devID){
		return this.getCollidingEntries(startTime+5*60*1000, endTime-5*60*1000, devID).size() > 0;
	}
}
