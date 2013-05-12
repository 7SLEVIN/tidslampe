package utils;

import javax.swing.JOptionPane;

public class Dialog {
	private static Dialog instance;
	private static boolean isDebugMode;
	private Dialog() {}
	
	
	public static Dialog getInstance() {
		return instance == null ? instance = new Dialog() : instance;
	}
	
	public static void setDebugMode(boolean enabled) {
		Dialog.isDebugMode = enabled;
	}
	
	public static void message(String msg) {
		getInstance().showMessage(msg);
	}

	public static DialogChoice confirm(String msg) {
		return getInstance().showConfirm(msg);
	}
	
	// Convienience access
	public void showMessage(String msg) {
		if (Dialog.isDebugMode)
			return;
		
		JOptionPane.showMessageDialog(null, msg);
	}
	
	public DialogChoice showConfirm(String msg) {
		switch (JOptionPane.showConfirmDialog(null, msg)) {
		case 0: return DialogChoice.Yes;
		case 1: return DialogChoice.No;
		case 2: return DialogChoice.Cancel;
		default: return DialogChoice.None;
		}
	}
}
