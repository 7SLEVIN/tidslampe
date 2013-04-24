package model.gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import model.Activity;
import model.Developer;
import model.Project;

@SuppressWarnings("serial")
public class ActivityListRenderer extends JLabel implements ListCellRenderer<Activity> {

	@Override
	public Component getListCellRendererComponent(
			JList<? extends Activity> list, Activity value, int index,
			boolean isSelected, boolean cellHasFocus) {
		setText(value != null ? value.getDescription() : "");
		return this;
	}

}
