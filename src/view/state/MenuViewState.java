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
	private JList<Project> projectsList;
	private List<Project> projects;
	private JButton gotoProjectButton;
	
	public MenuViewState() {
		this.projects = new ArrayList<Project>();
		this.projectsList = new JList<Project>();
		JScrollPane pane = new JScrollPane(this.projectsList);
		pane.setPreferredSize(new Dimension(300, 300));
		this.gotoProjectButton = new JButton("Goto project");
		
		JPanel buttonPanel = new JPanel();
		GuiUtils.setSize(buttonPanel, new Dimension(ViewContainer.WINDOW_WIDTH, 35));
		
		JPanel projectsPanel = new JPanel();
		GuiUtils.setSize(projectsPanel, new Dimension(300, 400));
		
		JPanel logoutPanel = new JPanel();
		GuiUtils.setSize(logoutPanel, new Dimension(ViewContainer.WINDOW_WIDTH, 35));
		
		projectsPanel.add(new JLabel("My projects:"));
		projectsPanel.add(pane);
		projectsPanel.add(this.gotoProjectButton);
		
		this.add(buttonPanel);
		this.add(projectsPanel);
		this.add(logoutPanel);
	}
	
	public void setProjects(List<Project> projects) {
		this.projects = projects;
		this.projectsList.setModel(new ProjectListModel(projects));
	}
	
	public List<Project> getProjects() {
		return this.projects;
	}
	
	public int getSelectedProject() {
		if(this.projectsList.getSelectedIndex() == -1)
			return -1;
		return this.projects.get(this.projectsList.getSelectedIndex()).getId();
	}
	
	public JButton getGotoProjectButton() {
		return this.gotoProjectButton;
	}
	
	public JList<Project> getProjectList() {
		return this.projectsList;
	}
}
