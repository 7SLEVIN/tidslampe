package utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDialogChoice {

	@Test
	public void testClass() {
		assertTrue(DialogChoice.class.isEnum());
	}
	
	@Test
	public void testChoice() {
		DialogChoice choice = DialogChoice.Cancel;
		assertEquals(DialogChoice.Cancel, choice);
	}

}
