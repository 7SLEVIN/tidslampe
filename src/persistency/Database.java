package persistency;

public class Database {
	
	protected DatabaseConnection conn;

	private ProjectRepository project;
	private DeveloperRepository developer;
	private ActivityRepository activity;
	private ActivityDeveloperRelationRepository activityDeveloperRelation;
	private AssistRepository assist;
	private TimeEntryRepository timeEntry;
	
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
		this.timeEntry = new TimeEntryRepository(this);
	}

	public Database() {
		this("dev_db.db");
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

	public TimeEntryRepository TimeEntry() {
		return timeEntry;
	}
}
