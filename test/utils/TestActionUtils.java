package utils;

import static org.junit.Assert.assertNotNull;

import javax.swing.JButton;

import org.junit.Test;

public class TestActionUtils {

	@Test
	public void testClass() {
		assertNotNull(new ActionUtils());
	}
	
	@Test
	public void testAddListener() {
		JButton btn = new JButton();
		ActionUtils.addListener(btn, new Object(), "blah");
		assertNotNull(btn.getActionListeners()[0]);
	}

}
