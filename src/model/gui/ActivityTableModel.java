package model.gui;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import utils.TimeService;

import model.Activity;

@SuppressWarnings("serial")
public class ActivityTableModel extends DefaultTableModel implements TableModel {
	
	private List<Activity> data;
	private TimeService timeService;

	public ActivityTableModel(List<Activity> activities) {
		super(new String[] {"Description", "Hour Budget", "Deadline", "Developers"}, activities.size());
		this.timeService = new TimeService();
		this.data = activities;
	}

	public Object getValueAt(int row, int column) {
		if (row >= this.data.size()) 
			return null;
		
		Activity activity = this.data.get(row);
		switch (column) {
			case 0: return activity.getDescription();
			case 1: return activity.getExpectedTime();
			case 2: return this.timeService.convertCalendarToInputString(activity.getEndCalendar());
			case 3: return activity.getAllDevsInitials();
			default: return null;
		}
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
       // None are editable
       return false;
    }
}
