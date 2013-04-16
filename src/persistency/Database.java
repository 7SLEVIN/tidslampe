package persistency;

public class Database {
	
	protected DatabaseConnection conn;

	private ProjectRepository project;
	private DeveloperRepository developer;
	private ActivityRepository activity;
	private ActivityDeveloperRelationRepository activityDeveloperRelation;
	private AssistRepository assist;
	private RegisterTimeRepository registerTime;
	private ReserveTimeRepository reserveTime;
	
	/**
	 * @param conn
	 * @param project
	 * @param developer
	 * @param activity
	 * @param assist
	 */
	public Database(String dbFile) {
		// TODO refactor db filename out of source code
		this.conn = new DatabaseConnection(dbFile);
		
		this.project = new ProjectRepository(this);
		this.developer = new DeveloperRepository(this);
		this.activity = new ActivityRepository(this);
		this.activityDeveloperRelation = new ActivityDeveloperRelationRepository(this);
		this.assist = new AssistRepository(this);
		this.registerTime = new RegisterTimeRepository(this);
		this.reserveTime = new ReserveTimeRepository(this);
	}

	public DatabaseConnection getConn() {
		return this.conn;
	}

	public DeveloperRepository developer() {
		return developer;
	}

	public ProjectRepository project() {
		return project;
	}

	public ActivityRepository activity() {
		return activity;
	}
	
	public ActivityDeveloperRelationRepository activityDeveloperRelation() {
		return activityDeveloperRelation;
	}

	public AssistRepository assist() {
		return assist;
	}

	public RegisterTimeRepository registerTime() {
		return registerTime;
	}
	
	public ReserveTimeRepository reserveTime(){
		return this.reserveTime;
	}
}
