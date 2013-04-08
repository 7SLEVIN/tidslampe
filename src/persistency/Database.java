package persistency;

import java.util.List;
import java.util.ArrayList;

import app.Developer;
import app.Project;

public class Database {

	private List<Project> projects;
	private List<Developer> developers;
	
	/**
	 * @param projects
	 * @param developers
	 */
	public Database() {
		this.projects = new ArrayList<Project>();
		this.developers = new ArrayList<Developer>();
	}
	
	
}
