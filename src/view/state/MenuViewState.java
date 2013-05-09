package view.state;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Project;
import model.gui.ProjectListModel;
import utils.GuiUtils;
import view.ViewContainer;

@SuppressWarnings("serial")
public class MenuViewState extends AbstractViewState {
	private JButton projectsButton;
	private JButton developersButton;
	private JButton calendarButton;
	private JList<Project> projectsList;
	private List<Project> projects;
	private JButton gotoProjectButton;
	private JButton logoutButton;
	
	public MenuViewState() {
		this.projectsButton = new JButton("Projects");
		this.developersButton = new JButton("Developers");
		this.calendarButton = new JButton("Register time");
		
		this.projects = new ArrayList<Project>();
		this.projectsList = new JList<Project>();
		JScrollPane pane = new JScrollPane(this.projectsList);
		pane.setPreferredSize(new Dimension(300, 300));
		this.gotoProjectButton = new JButton("Goto project");
		
		this.logoutButton = new JButton("Logout");
		
		JPanel buttonPanel = new JPanel();
		GuiUtils.setSize(buttonPanel, new Dimension(ViewContainer.WINDOW_WIDTH, 35));
		
		JPanel projectsPanel = new JPanel();
		GuiUtils.setSize(projectsPanel, new Dimension(300, 400));
		
		JPanel logoutPanel = new JPanel();
		GuiUtils.setSize(logoutPanel, new Dimension(ViewContainer.WINDOW_WIDTH, 35));
		
		buttonPanel.add(this.calendarButton);
		buttonPanel.add(this.developersButton);
		buttonPanel.add(this.projectsButton);
		
		projectsPanel.add(new JLabel("My projects:"));
		projectsPanel.add(pane);
		projectsPanel.add(this.gotoProjectButton);
		
		logoutPanel.add(this.logoutButton);
		
		this.add(buttonPanel);
		this.add(projectsPanel);
		this.add(logoutPanel);
	}
	
	public void setProjects(List<Project> projects) {
		this.projects = projects;
		this.projectsList.setModel(new ProjectListModel(projects));
	}
	
	public int getSelectedProject() {
		return this.projects.get(this.projectsList.getSelectedIndex()).getId();
	}

	public JButton getDevelopersButton() {
		return this.developersButton;
	}

	public JButton getCalendarButton() {
		return this.calendarButton;
	}

	public JButton getProjectsButton() {
		return projectsButton;
	}
	
	public JButton getGotoProjectButton() {
		return this.gotoProjectButton;
	}
	
	public JList<Project> getProjectList() {
		return this.projectsList;
	}
	
	public JButton getLogoutButton() {
		return this.logoutButton;
	}
}
