package view.state;

import java.awt.Dimension;

import javax.swing.JPanel;

import view.ViewContainer;

/**
 * Base class of states that can be rendered in the {@link ViewContainer}
 */
@SuppressWarnings("serial")
abstract public class AbstractViewState extends JPanel {
	
	
	public AbstractViewState() {
	    this.setPreferredSize(new Dimension(500, 580));
	}

	/**
	 * Lets a view state dispose its resources before being removed
	 */
	public void dispose() {}

	/**
	 * Lets a view state initialize itself after it has been added to a frame
	 */
	public void initialize() {}
        
}
