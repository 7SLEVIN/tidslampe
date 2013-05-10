package persistency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.UpdateNonExistingException;

import utils.Query;

import model.Developer;
import model.Project;

public class ProjectRepository extends Repository<Project> {

	public ProjectRepository(Database db) {
		super(db);

		this.table = "project";
		this.columns = new String[] { "name", "hour_budget", "deadline",
				"manager_id" };
	}
	
	public List<Project> readByDeveloper(int developerId) {
		Query q = Query.selectAllFrom(this.table).whereIn("project.id",
						Query.select("project_id").from("activity").whereIn("activity.id", 
							Query.select("activity_id").from("activity_developer_relation adr").whereEquals("adr.developer_id", developerId)));
		
		ResultSet rs = this.database.getConnnection().execQuery(q);
		
		return this.parse(rs);
	}

	public Project create(String name, int hourBudget, long deadline, Developer manager) {
		if(manager == null)
			return this.create(name, hourBudget, deadline);
		
		int id = this.create(new String[] { name, String.valueOf(hourBudget),
				String.valueOf(deadline), String.valueOf(manager.getId()) });
		Project project = new Project(this.database, id, name, hourBudget, deadline,
				manager);
		return project;
	}
	
	public Project create(String name, int hourBudget, long deadline) {
		int id = this.create(new String[] { name, String.valueOf(hourBudget),
				String.valueOf(deadline), "-1" });
		Project project = new Project(this.database, id, name, hourBudget, deadline);
		return project;
	}
	
	public void removeManager(int id){
		List<Project> projects = this.readAllWhereEquals("manager_id", id);
		for(Project project : projects){
			try {
				project.setManager(null);
			} catch (UpdateNonExistingException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected List<Project> parse(ResultSet rs) {
		List<Project> projects = new ArrayList<Project>();
		List<Integer> managerIDs = new ArrayList<Integer>();
		
		try {
			while (rs.next()) {
				managerIDs.add(rs.getInt(this.columns[3]));
				projects.add(new Project(this.database, rs.getInt("id"), 
						rs.getString(this.columns[0]),
						rs.getInt(this.columns[1]), rs.getLong(this.columns[2])));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < projects.size(); i++) {
			try {
				projects.get(i).setManager(this.database.developer().read(managerIDs.get(i)));
			} catch (UpdateNonExistingException e) {
				e.printStackTrace();
			}
		}
		
		return projects;
	}

}
