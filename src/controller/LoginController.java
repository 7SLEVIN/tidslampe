package controller;

import java.util.List;

import persistency.Database;
import app.Developer;

public class LoginController {
	private Developer user;
	private Database db;
	
	public LoginController(Database db){
		this.db = db;
	}
	
	public boolean login(String s){
		List<Developer> potentialDevs = this.db.developer.readByInitials(s);

		if(potentialDevs.size() == 1)
			this.user = potentialDevs.get(0);
		return potentialDevs.size() == 1;
	}

}
