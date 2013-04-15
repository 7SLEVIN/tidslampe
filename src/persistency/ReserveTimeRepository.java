package persistency;

import java.util.List;

import utils.Query;
import model.TimeEntry;

public class ReserveTimeRepository extends TimeRepository {

	

	public ReserveTimeRepository(Database db) {
		super(db);
		this.table = "reserve_time";
	}

	@Override
	public TimeEntry create(long startTime, long endTime, int devActRelID,int devID) {
		if(this.isTimeUsed(startTime, endTime, devID))
			return null;
		
		int activityID = this.db.ActivityDeveloperRelation().read(devActRelID).getId();
		if(this.db.Activity().isFixed(activityID)){
//Hvis aktiviteten er fixed, så skal den bare registreres med det samme
			return this.db.RegisterTime().create(startTime, endTime, devActRelID, devID);
		}else{
			int id = this.create(new String[]{String.valueOf(startTime), String.valueOf(endTime),String.valueOf(devActRelID),String.valueOf(devID)});
			
			TimeEntry entry = new TimeEntry(this.db, id, startTime, endTime, devActRelID, devID); 
			return entry;
		}
	}
	
	

	private List<TimeEntry> getCollidingEntries(long startTime, long endTime, int devID){ //Differs from RegisterTime's getColliding, as this returns both REGISTERED and RESERVED entries
		List<TimeEntry> collidingEntries = this.parse(this.db.getConn().execQuery(Query.SelectAllFrom(this.table)
				.WhereLessThan("start_time", endTime)
				.WhereMoreThan("end_time", startTime)
				.WhereEquals("developer_id", devID)));
		
		collidingEntries.addAll(this.db.RegisterTime().getCollidingEntries(startTime, endTime, devID));
		
		return collidingEntries;
	}
	
	private boolean isTimeUsed(long startTime, long endTime, int devID){
		return this.getCollidingEntries(startTime+5*60*1000, endTime-5*60*1000, devID).size() > 0;
	}
}
