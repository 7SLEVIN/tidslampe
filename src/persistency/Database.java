package persistency;

public class Database {
	
	protected DatabaseConnection conn;

	private ProjectRepository project;
	private DeveloperRepository developer;
	private ActivityRepository activity;
	private ActivityDeveloperRelationRepository activityDeveloperRelation;
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
		this.conn = new DatabaseConnection(dbFile);
		this.setUp();
		
		this.project = new ProjectRepository(this);
		this.developer = new DeveloperRepository(this);
		this.activity = new ActivityRepository(this);
		this.activityDeveloperRelation = new ActivityDeveloperRelationRepository(this);
		this.registerTime = new RegisterTimeRepository(this);
		this.reserveTime = new ReserveTimeRepository(this);
	}
	
	private void setUp() {		
		this.conn.execUpdate("create table if not exists project (id integer primary key autoincrement, name string, hour_budget float, deadline BIGINT, manager_id integer)");
		this.conn.execUpdate("create table if not exists developer (id integer primary key autoincrement, initials string, name string)");
		this.conn.execUpdate("create table if not exists activity (id integer primary key autoincrement,activity_type string, description string, expected_time integer, start_time BIGINT, end_time BIGINT, project_id integer)");
		this.conn.execUpdate("create table if not exists activity_developer_relation (id integer primary key autoincrement, activity_id integer, developer_id integer)");
		this.conn.execUpdate("create table if not exists register_time (id integer primary key autoincrement, start_time BIGINT, end_time BIGINT,developer_activity_relation_id integer,developer_id integer, is_assist int)");
		this.conn.execUpdate("create table if not exists reserve_time (id integer primary key autoincrement, start_time BIGINT, end_time BIGINT,developer_activity_relation_id integer,developer_id integer, is_assist int)");
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

	public RegisterTimeRepository registerTime() {
		return registerTime;
	}
	
	public ReserveTimeRepository reserveTime(){
		return this.reserveTime;
	}
}
