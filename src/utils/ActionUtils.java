package utils;

import java.awt.event.ActionListener;
import java.beans.EventHandler;

import javax.swing.JButton;

public class ActionUtils {
	public static void addListener(JButton btn, Object target, String method) {
		btn.addActionListener((ActionListener)EventHandler.create(ActionListener.class, target, method));
	}
}
