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
		
		Project o = this.data.get(row);
		switch (column) {
			case 0: return o.getName();
			case 1: return o.getHourBudget();
			case 2: return o.getDeadline();
			case 3: return o.getManager() != null ? o.getManager().getName() : "N/A" ;
			default: return null;
		}
	}
}
