package persistency;

import java.sql.ResultSet;
import java.util.ArrayList;

import app.Assist;

public class AssistRepository extends Repository<Assist> {

	 public AssistRepository(Database database) {
		 super(database);
		 
		 this.table = "assist";
		 this.columns = new String[]{"id", "developer", "spentTime"};
	 }

	@Override
	protected ArrayList<Assist> parse(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}
}
