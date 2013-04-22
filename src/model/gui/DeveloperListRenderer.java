package model.gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import model.Developer;

@SuppressWarnings("serial")
public class DeveloperListRenderer extends JLabel implements ListCellRenderer<Developer> {

	@Override
	public Component getListCellRendererComponent(
			JList<? extends Developer> list, Developer value, int index,
			boolean isSelected, boolean cellHasFocus) {
		setText(value != null ? value.getName() : "");
		return this;
	}

}
