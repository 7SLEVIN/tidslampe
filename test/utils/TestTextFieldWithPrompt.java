package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Point;

import org.junit.Test;

public class TestTextFieldWithPrompt {

	@Test
	public void testNull() {
		TextFieldWithPrompt field = new TextFieldWithPrompt(1, "HEllo");
		assertNotNull(field);
	}
	
	@Test
	public void testOffset() {
		TextFieldWithPrompt field = new TextFieldWithPrompt(1, "HEllo");
		assertEquals(new Point(7,20), field.getOffset());
		field.setOffset(new Point(1,1));
		assertEquals(new Point(1,1), field.getOffset());
	}

}
