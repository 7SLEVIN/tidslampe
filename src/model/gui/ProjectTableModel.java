package model.gui;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Project;

@SuppressWarnings("serial")
public class ProjectTableModel extends DefaultTableModel implements TableModel {
	
	private List<Project> data;

	public ProjectTableModel(List<Project> projects) {
		super(new String[] {"Name", "Hour Budget", "Deadline", "Manager"}, projects.size());
		this.data = projects;
	}

	public Object getValueAt(int row, int column) {
		if (row >= this.data.size()) 
			return null;
		
		Project project = this.data.get(row);
		switch (column) {
//Cases:		{ "name", "hour_budget", "deadline","manager_id" };
			case 0: return project.getName();
			case 1: return project.getHourBudget();
			case 2: return project.getDeadline();
			case 3: return project.getManager() != null ? project.getManager().getName() : "N/A" ;
			default: return null;
		}
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
       // None are editable
       return false;
    }
	
}
