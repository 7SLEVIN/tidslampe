package view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import controller.view.AbstractViewController;
import controller.view.ViewControllerFactory;

import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import utils.ActionUtils;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/**
 * The main window inside which states are rendered
 */

@SuppressWarnings("serial")
public class ViewContainer extends JFrame {
	public final static int WINDOW_WIDTH = 800;
	public final static int WINDOW_HEIGHT = 600;
	private AbstractViewController currentViewController;
	private JPanel container;
	private JToolBar toolbar;
	
	public ViewContainer() {
		super("Tidslampe");
		
		this.setResizable(false);
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setLayout(new FlowLayout());
		this.setVisible(true);
		this.setFocusable(true);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{798, 0};
		gridBagLayout.rowHeights = new int[] {40, 561, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		this.container = new JPanel();
		GridBagConstraints gbc_container = new GridBagConstraints();
		gbc_container.anchor = GridBagConstraints.NORTH;
		gbc_container.fill = GridBagConstraints.HORIZONTAL;
		gbc_container.gridx = 0;
		gbc_container.gridy = 1;
		getContentPane().add(this.container, gbc_container);

		this.toolbar = new JToolBar();
		this.toolbar.setPreferredSize(new Dimension(798, 40));
		this.add(toolbar);
		
		this.requestFocusInWindow();
	}

	public void setupToolBar(final int developerId) {
		this.toolbar.removeAll();

		JButton homeButton = new JButton("Home");
		homeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				setViewState(ViewControllerFactory.CreateMenuViewController());
			}
		});
		this.toolbar.add(homeButton);

		JButton timeButton = new JButton("Register time");
		timeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				setViewState(ViewControllerFactory.CreateCalendarViewController(developerId));
			}
		});
		this.toolbar.add(timeButton);

		JButton developerButton = new JButton("Developers");
		developerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				setViewState(ViewControllerFactory.CreateDevelopersViewController());
			}
		});
		this.toolbar.add(developerButton);

		JButton projectButton = new JButton("Projects");
		projectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				setViewState(ViewControllerFactory.CreateProjectsViewController());
			}
		});
		this.toolbar.add(projectButton);
		
		// align right
		this.toolbar.add(Box.createHorizontalGlue());
		JButton logoutButton = new JButton("Log out");
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				setViewState(ViewControllerFactory.CreateProjectsViewController());
			}
		});
		this.toolbar.add(logoutButton);
		this.toolbar.setEnabled(false);
	}

	/**
	 * Changes the active view state
	 * 
	 * @param state
	 *            The new state to change to
	 */
	public void setViewState(AbstractViewController viewController) {
		if (this.currentViewController != null) {
			this.currentViewController.getViewState().dispose();
		}

		viewController.initialize();
		this.container.removeAll();
		this.container.add(viewController.getViewState());

		this.pack();
		
		this.currentViewController = viewController;
	}

	public AbstractViewController getCurrentViewController() {
		return currentViewController;
	}

	public void setToolbarVisible(boolean visible) {
		this.toolbar.setVisible(visible);
		
	}
}
