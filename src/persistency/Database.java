package persistency;


public class Database {
	
	private DatabaseConnection conn;

	public ProjectRepository project;
	public DeveloperRepository developer;
	public ActivityRepository activity;
	public AssistRepository assist;
	
	/**
	 * @param project
	 * @param developer
	 * @param activity
	 * @param assist
	 */
	public Database() {
		this.conn = new DatabaseConnection();
		
		this.project = new ProjectRepository(this);
		this.developer = new DeveloperRepository(this);
		this.activity = new ActivityRepository(this);
		this.assist = new AssistRepository(this);
	} 
	
	
	
}
