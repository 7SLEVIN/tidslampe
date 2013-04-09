package persistency;

import java.sql.ResultSet;
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
		 Assist assist = new Assist(id, developer, spentTime);
		 return assist;
	 }

	@Override
	protected List<Assist> parse(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}
}
