package model.gui;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Developer;

@SuppressWarnings("serial")
public class DeveloperTableModel extends DefaultTableModel implements TableModel {
	private List<Developer> data;

	public DeveloperTableModel(List<Developer> developers) {
		super(new String[] {"Initials", "Name"}, developers.size());
		this.data = developers;
	}

	public Object getValueAt(int row, int column) {
		if (row >= this.data.size()) 
			return null;
		
		Developer o = this.data.get(row);
		switch (column) {
			case 0: return o.getInitials();
			case 1: return o.getName();
			default: return null;
		}
	}
}
