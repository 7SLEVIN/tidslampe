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
		this.columns = new String[]{"start_time", "end_time","developer_activity_relation_id","developer_id", "is_assist"};
	}
	
	public List<TimeEntry> readByDeveloperId(int developerId) {
		Query query = Query.selectAllFrom(this.table).whereIn("developer_activity_relation_id", 
				Query.select("id").from("activity_developer_relation").whereEquals("developer_id", developerId));
		return this.parse(this.database.getConnnection().execQuery(query));
	}
	
	public List<TimeEntry> readByActivityId(int acitivtyId) {
//		Query query = Query.selectAllFrom(this.table).whereIn("developer_activity_relation_id", 
//				Query.select("id").from("activity_developer_relation").whereEquals("developer_id", developerId));
//		return this.parse(this.database.getConnnection().execQuery(query));
		
		Query query = Query.selectAllFrom(this.table).whereIn("developer_activity_relation_id",
				Query.select("id").from("activity_developer_relation").whereIn("activity_id",
						Query.select("id").from("activity").whereEquals("id", acitivtyId)));
		return this.parse(this.database.getConnnection().execQuery(query));
	}
	
	@Override
	protected List<TimeEntry> parse(ResultSet rs) {
		List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
		List<Integer> actDevRel = new ArrayList<Integer>();
		try {
			while (rs.next()) {
				actDevRel.add(rs.getInt(this.columns[2]));
				timeEntries.add(new TimeEntry(
						this.database, rs.getInt("id"), 
						rs.getLong(this.columns[0]), 
						rs.getLong(this.columns[1]), 
						rs.getBoolean("is_assist")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < timeEntries.size(); i++) {
			Integer id = actDevRel.get(i);
			if (id == null) break;
			timeEntries.get(i).setActivityDeveloperRelation(
					this.database.activityDeveloperRelation().read(id));
		}
		return timeEntries;
	}

	abstract public TimeEntry create(long startTime, long endTime, int devActRelID,int devID, boolean isAssist);
	
	public List<TimeEntry> readByProjectId(int projectId) {
		Query query = Query.selectAllFrom(this.table).whereIn("developer_activity_relation_id",
				Query.select("id").from("activity_developer_relation").whereIn("activity_id",
						Query.select("id").from("activity").whereEquals("project_id", projectId)));
		return this.parse(this.database.getConnnection().execQuery(query));
	}

}
