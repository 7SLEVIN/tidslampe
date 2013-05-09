package controller.view;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestViewControllerFactory {

	@Test
	public void testViewControllers() {
		ViewControllerFactory.CreateCalendarViewController(1);
	}

}
