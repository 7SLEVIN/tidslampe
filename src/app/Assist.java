package app;

public class Assist {
	
	private int id;
	private Developer developer;
	private Number spentTime;
	
	/**
	 * @param developer
	 * @param spentTime
	 */
	public Assist(int id, Developer developer, Number spentTime) {
		this.id = id;
		this.developer = developer;
		this.spentTime = spentTime;
	}

}
