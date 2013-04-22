package utils;

import javax.swing.JOptionPane;

public class Dialog {
	public static void message(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
	
	public static DialogChoice confirm(String msg) {
		switch (JOptionPane.showConfirmDialog(null, msg)) {
		case 0: return DialogChoice.Yes;
		case 1: return DialogChoice.No;
		case 2: return DialogChoice.Cancel;
		default: return DialogChoice.None;
		}
	}
}
