package persistency;

import java.sql.ResultSet;
import java.util.ArrayList;

import app.Developer;

public class DeveloperRepository extends Repository<Developer> {
	
	public DeveloperRepository(Database database) {
		super(database);
		 
		this.table = "developer";
		this.columns = new String[]{"id", "initials", "name"};
	}

	@Override
	protected ArrayList<Developer> parse(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
