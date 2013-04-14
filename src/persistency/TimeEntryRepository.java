package persistency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Query;
import model.TimeEntry;

public class TimeEntryRepository extends Repository<TimeEntry> {

	public TimeEntryRepository(Database db) {
		super(db);
		this.table = "time_entry";
		this.columns = new String[]{"start_time", "end_time","developer_activity_relation_id","developer_id"};
	}
	
	public List<TimeEntry> entriesByDeveloperID(int id){
		ResultSet rs = this.db.getConn().execQuery(
				Query.SelectAllFrom(this.table).WhereEquals("developer_id", id));
		return this.parse(rs);
	}
	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @param devActRelID
	 * @param devID
	 * @return returns null if time is already in use!
	 */
	public TimeEntry create(long startTime, long endTime, int devActRelID, int devID) {
		if(this.isTimeUsed(startTime, endTime, devID))
			return null;
		
		int id = this.create(new String[]{String.valueOf(startTime), String.valueOf(endTime),String.valueOf(devActRelID),String.valueOf(devID)});
		
		TimeEntry entry = new TimeEntry(this.db, id, startTime, endTime, devActRelID, devID); 
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
	
	@Override
	protected List<TimeEntry> parse(ResultSet rs) {

		List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
		try {
			while (rs.next()) {
				timeEntries.add(new TimeEntry(
						this.db, rs.getInt("id"), 
						rs.getInt(this.columns[0]), rs.getInt(this.columns[1]), 
						rs.getInt(this.columns[2]), rs.getInt(this.columns[3])));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return timeEntries;
	}

}
