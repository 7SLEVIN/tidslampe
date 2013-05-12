package utils;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestArrayUtils {

	@Test
	public void testClass() {
		assertNotNull(new ArrayUtils());
	}
	
	@Test
	public void testJoinEmpty() {
		assertEquals("", ArrayUtils.join(new String[0], ' '));
	}
	
	@Test
	public void testJoinNull() {
		assertNull(ArrayUtils.join(null, ' '));
	}

}
