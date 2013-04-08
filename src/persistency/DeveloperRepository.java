package persistency;

import java.sql.ResultSet;
import java.util.ArrayList;

import app.Developer;

public class DeveloperRepository extends Repository<Developer> {
	
	public DeveloperRepository(DatabaseConnection conn) {
		super(conn);
		 
		this.table = "developer";
		this.columns = new String[]{"initials", "name"};
	}
	
	public Developer create(String initials, String name) {
		int id = this.create(new String[]{initials, name});
		Developer dev = new Developer(id, initials, name); 
		return dev;
	}

	@Override
	protected ArrayList<Developer> parse(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
