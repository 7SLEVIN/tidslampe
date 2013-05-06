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
	
	public List<TimeEntry> readByDeveloperId(int developerId) {
//select * from register_time where developer_activity_relation_id in (select id from activity_developer_relation where developer_id = 1);
		String query = String.format("SELECT * FROM %s WHERE developer_activity_relation_id IN (%s) ORDER BY start_time", 
				this.table,
				Query.Select("id").From("activity_developer_relation").WhereEquals("developer_id", developerId).End());
		return this.parse(this.db.conn.execQuery(query));
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
	
	public List<TimeEntry> readByProjectId(int projectId) {
		Query query = Query.SelectAllFrom(this.table).WhereIn("developer_activity_relation_id",
				Query.Select("id").From("activity_developer_relation").WhereIn("activity_id",
						Query.Select("id").From("activity").WhereEquals("project_id", projectId)));
		return this.parse(this.db.conn.execQuery(query));
	}

}
