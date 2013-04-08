package persistency;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import app.Activity;
import app.Developer;
import app.Project;


public class ProjectRepository extends Repository<Project> {
	
	public ProjectRepository(DatabaseConnection conn) {
		super(conn);
		 
		this.table = "project";
		this.columns = new String[]{"name", "hourBudget", "deadline", 
				"manager"};
	}
	
	public Project create(String name, int hourBudget, int deadline, 
			Developer manager) {
		int id = this.create(new String[]{name, String.valueOf(hourBudget), 
				String.valueOf(deadline), String.valueOf(manager.getId())});
		Project dev = new Project(id, name, hourBudget, deadline, manager); 
		return dev;
	}

	@Override
	protected ArrayList<Project> parse(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

}
