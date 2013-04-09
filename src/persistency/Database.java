package persistency;


public class Database {
	
	protected DatabaseConnection conn;

	public ProjectRepository project;
	public DeveloperRepository developer;
	public ActivityRepository activity;
	public ActivityDeveloperRelationRepository activityDeveloperRelation;
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
		
		this.project = new ProjectRepository(this);
		this.developer = new DeveloperRepository(this);
		this.activity = new ActivityRepository(this);
		this.activityDeveloperRelation = new ActivityDeveloperRelationRepository(this);
		this.assist = new AssistRepository(this);
	}

	public DatabaseConnection getConn() {
		return this.conn;
	} 
	
	
	
}
