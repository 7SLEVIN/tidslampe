package persistency;

import java.sql.ResultSet;
import java.util.ArrayList;

import app.Project;


public class ProjectRepository extends Repository<Project> {
	
	public ProjectRepository(Database database) {
		super(database);
		 
		this.table = "project";
		this.columns = new String[]{"id", "name", "hourBudget", "deadline", "manager"};
	}

	@Override
	protected ArrayList<Project> parse(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

}
