package model.gui;

import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import model.Project;

public class ProjectListModel implements ListModel<Project> {
	
	private List<Project> data;

	public ProjectListModel(List<Project> projects) {
		this.data = projects;
	}

	public Project getElementAt(int index) {
		if (index >= this.data.size()) 
			return null;
		
		Project project = this.data.get(index);
		return project;
	}

	@Override
	public int getSize() {
		return this.data.size();
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}
	
}
