package persistency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Query;
import utils.TimeService;

import model.TimeEntry;

public abstract class TimeRepository extends Repository<TimeEntry> {

	protected TimeService timeService;
	
	public TimeRepository(Database db) {
		super(db);
		this.timeService = new TimeService();
		this.columns = new String[]{"start_time", "end_time","developer_activity_relation_id","developer_id"};
	}
	
	public List<TimeEntry> entriesByDeveloperID(int id){
		ResultSet rs = this.db.getConn().execQuery(
				Query.SelectAllFrom(this.table).WhereEquals("developer_id", id));
		return this.parse(rs);
	}
	


	@Override
	protected List<TimeEntry> parse(ResultSet rs) {

		List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
		try {
			while (rs.next()) {
				timeEntries.add(new TimeEntry(
						this.db, rs.getInt("id"), 
						rs.getLong(this.columns[0]), rs.getLong(this.columns[1]), 
						rs.getInt(this.columns[2])));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return timeEntries;
	}

	abstract public TimeEntry create(long startTime, long endTime, int devActRelID,int devID);

}
