package persistency;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Assist;
import model.Developer;


public class AssistRepository extends Repository<Assist> {

	 public AssistRepository(Database db) {
		 super(db);
		 
		 this.table = "assist";
		 this.columns = new String[]{"developer_id", "spent_time"};
	 }
	 
	 public Assist create(Developer developer, Number spentTime) {
		 int id = this.create(new String[]{String.valueOf(developer.getId()), 
				 String.valueOf(spentTime)});
		 Assist assist = new Assist(this.db, id, developer, spentTime);
		 return assist;
	 }

	@Override
	protected List<Assist> parse(ResultSet rs) {
		List<Assist> assists = new ArrayList<Assist>();
		try {
			while (rs.next()) {
				int developer_id = rs.getInt(this.columns[0]);
				Number spent_time = rs.getDouble(this.columns[1]);
				assists.add(new Assist(this.db, rs.getInt("id"), 
						this.db.developer.read(developer_id), 
						spent_time));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assists;
	}
}
