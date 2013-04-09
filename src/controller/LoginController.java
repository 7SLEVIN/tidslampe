package controller;

import java.util.List;

import persistency.Database;
import app.Developer;

public class LoginController {
	public boolean loggedin = false;
	private Developer user;
	private Database db;
	
	public LoginController(Database db){
		this.db = db;
	}
	
	public boolean login(String s){
		List<Developer> potentialDevs = this.db.developer.readByInitials(s);

		this.loggedin = potentialDevs.size() == 1;
		if(this.loggedin)
			this.user = potentialDevs.get(0);
		return this.loggedin;
	}
	
	public void logout(){
		this.loggedin = false;
		this.user = null;
	}

	public Developer getUser() {
		return user;
	}

}
