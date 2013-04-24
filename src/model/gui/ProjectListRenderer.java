package model.gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import model.Developer;
import model.Project;

@SuppressWarnings("serial")
public class ProjectListRenderer extends JLabel implements ListCellRenderer<Project> {

	@Override
	public Component getListCellRendererComponent(
			JList<? extends Project> list, Project value, int index,
			boolean isSelected, boolean cellHasFocus) {
		setText(value != null ? value.getName() : "");
		return this;
	}

}
