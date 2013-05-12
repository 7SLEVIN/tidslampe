package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestStringUtils {
	
	@Test
	public void testClass() {
		assertNotNull(new StringUtils());
	}

	@Test
	public void testTrailingZeros() {
		assertEquals("05", StringUtils.trailingZero(5));
		assertEquals("09", StringUtils.trailingZero(9));
		assertEquals("10", StringUtils.trailingZero(10));
		assertEquals("11", StringUtils.trailingZero(11));
		assertEquals("100", StringUtils.trailingZero(100));
	}

}
