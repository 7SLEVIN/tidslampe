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

	public DeveloperRepository Developer() {
		return developer;
	}

	public ProjectRepository Project() {
		return project;
	}

	public ActivityRepository Activity() {
		return activity;
	}
	
	public ActivityDeveloperRelationRepository ActivityDeveloperRelation() {
		return activityDeveloperRelation;
	}

	public AssistRepository Assist() {
		return assist;
	}

	public RegisterTimeRepository RegisterTime() {
		return registerTime;
	}
	
	public ReserveTimeRepository ReserveTime(){
		return this.reserveTime;
	}
}
