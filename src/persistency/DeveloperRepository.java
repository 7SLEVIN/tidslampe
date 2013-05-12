package persistency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Developer;


public class DeveloperRepository extends Repository<Developer> {
	
	public DeveloperRepository(Database db) {
		super(db);
		 
		this.table = "developer";
		this.columns = new String[]{"initials", "name"};
	}
	
	public Developer create(String initials, String name) {
		List<Developer> collisionDeveloper = this.readByInitials(initials);
		if(collisionDeveloper.size() > 0){
			System.out.println("A developer with initials \"" + initials + "\" already exists!");
			return null;
		}
		
		int id = this.create(new String[]{initials, name});
		Developer dev = new Developer(this.database, id, initials, name); 
		return dev;
	}
	
	public List<Developer> readByInitials(String initials) {
		return this.parse(this.database.getConnnection().readWhereEquals(this.table, this.columns[0], initials));
	}
	
	@Override
	protected List<Developer> parse(ResultSet rs) {
		List<Developer> developers = new ArrayList<Developer>();
		try {
			while (rs.next()) {
				developers.add(new Developer(this.database, rs.getInt("id"), 
						rs.getString(this.columns[0]), rs.getString(this.columns[1])));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return developers;
	}
	
}
