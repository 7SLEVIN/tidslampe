package model.gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import model.Activity;
import model.ActivityType;
import model.Developer;
import model.Project;

@SuppressWarnings("serial")
public class ActivityTypeListRenderer extends JLabel implements ListCellRenderer<ActivityType> {

	@Override
	public Component getListCellRendererComponent(
			JList<? extends ActivityType> list, ActivityType value, int index,
			boolean isSelected, boolean cellHasFocus) {
		setText(value != null ? value.name() : "");
		return this;
	}

}
