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
		int id = this.create(new String[]{initials, name});
		Developer dev = new Developer(id, initials, name); 
		return dev;
	}
	
	public List<Developer> readByInitials(String initials) {
		return this.parse(this.db.conn.readSpecific(this.table, this.columns[0], initials));
	}
	
	
	
	@Override
	protected List<Developer> parse(ResultSet rs) {
		List<Developer> developers = new ArrayList<Developer>();
		try {
			while (rs.next()) {
				developers.add(new Developer(rs.getInt("id"), 
						rs.getString(this.columns[0]), rs.getString(this.columns[1])));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return developers;
	}
	
}
