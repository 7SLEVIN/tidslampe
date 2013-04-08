package persistency;


public class Database {
	
	private DatabaseConnection conn;

	public ProjectRepository project;
	public DeveloperRepository developer;
	public ActivityRepository activity;
	public AssistRepository assist;
	
	/**
	 * @param conn
	 * @param project
	 * @param developer
	 * @param activity
	 * @param assist
	 */
	public Database() {
		// TODO refactor db filename out of source code
		this.conn = new DatabaseConnection("data.db");
		
		this.project = new ProjectRepository(conn);
		this.developer = new DeveloperRepository(conn);
		this.activity = new ActivityRepository(conn);
		this.assist = new AssistRepository(conn);
	} 
	
	
	
}
