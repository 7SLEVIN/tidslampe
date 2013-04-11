package persistency;

import java.sql.ResultSet;
import java.util.List;

import model.TimeEntry;

public class TimeEntryRepository extends Repository<TimeEntry> {

	public TimeEntryRepository(Database db) {
		super(db);
		this.table = "time_entry";
		this.columns = new String[]{"start_time", "end_time","developer_activity_relation_id","developer_id"};
	}
	
	@Override
	protected List parse(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

}
