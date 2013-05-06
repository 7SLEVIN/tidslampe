package utils;

import java.awt.Dimension;

import javax.swing.JPanel;

public class GuiUtils {
	
	public static void setSize(JPanel panel, Dimension dim) {
		panel.setPreferredSize(dim);
		panel.setMinimumSize(dim);
		panel.setMaximumSize(dim);
	}

}
