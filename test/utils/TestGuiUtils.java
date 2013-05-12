package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.junit.Test;

public class TestGuiUtils {

	@Test
	public void testClass() {
		assertNotNull(new GuiUtils());
	}
	
	@Test
	public void testSetSize() {
		JPanel panel = new JPanel();
		GuiUtils.setSize(panel, new Dimension(200,200));
		assertEquals(200, panel.getPreferredSize().height);
		assertEquals(200, panel.getPreferredSize().width);
		assertEquals(200, panel.getMaximumSize().height);
		assertEquals(200, panel.getMaximumSize().width);
		assertEquals(200, panel.getMinimumSize().width);
		assertEquals(200, panel.getMinimumSize().width);
	}

}
