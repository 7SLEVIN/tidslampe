package view.state;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import utils.GuiUtils;
import view.ViewContainer;

import model.Developer;
import model.Project;
import model.gui.GenericComboBoxModel;
import model.gui.DeveloperListRenderer;
import model.gui.ProjectTableModel;

@SuppressWarnings("serial")
public class ProjectsViewState extends AbstractViewState {
	
	private JButton backButton;
	private JButton deleteButton;
	private JButton createButton;
	private JButton maintainButton;
	private JTable table;
	private List<Project> projects;
	private JTextField nameInput;
	private JTextField hourBudgetInput;
	private JTextField deadlineInput;
	private JComboBox<Developer> managerInput;
	
	public ProjectsViewState() {
		this.table = new JTable();
		this.backButton = new JButton("Back to menu");
		this.deleteButton = new JButton("Delete selected");
		this.maintainButton = new JButton("Maintain selected");

		this.createButton = new JButton("Create new");
		this.nameInput = new JTextField(10);
		this.hourBudgetInput = new JTextField(3);
		this.deadlineInput = new JTextField(3);
		this.managerInput = new JComboBox<Developer>();

		JPanel titlePanel = new JPanel();
		GuiUtils.setSize(titlePanel, new Dimension(ViewContainer.WINDOW_WIDTH, 30));
		titlePanel.add(this.backButton);
		
		JScrollPane scrollPane = new JScrollPane(this.table);
		scrollPane.setPreferredSize(new Dimension(400, 300));

		JPanel maintainancePanel = new JPanel();
		GuiUtils.setSize(maintainancePanel, new Dimension(ViewContainer.WINDOW_WIDTH, 30));
		maintainancePanel.add(this.maintainButton);
		maintainancePanel.add(this.deleteButton);
		
		JPanel createPanel = new JPanel();
		GuiUtils.setSize(createPanel, new Dimension(ViewContainer.WINDOW_WIDTH, 50));
		createPanel.add(new JLabel("Name"));
		createPanel.add(this.nameInput);
		createPanel.add(new JLabel("Hour Budget"));
		createPanel.add(this.hourBudgetInput);
		createPanel.add(new JLabel("Deadline"));
		createPanel.add(this.deadlineInput);
		createPanel.add(new JLabel("Manager"));
		createPanel.add(this.managerInput);
		createPanel.add(this.createButton);
		
		this.add(titlePanel);
		this.add(scrollPane);
		this.add(maintainancePanel);
		this.add(createPanel);
	}

	public JButton getBackButton() {
		return backButton;
	}
	
	public JButton getMaintainButton() {
		return maintainButton;
	}
	
	public JButton getDeleteButton() {
		return deleteButton;
	}

	public JButton getCreateButton() {
		return this.createButton;
	}
	
	public JTable getProjectTable() {
		return this.table;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
		this.table.setModel(new ProjectTableModel(projects));
	}

	public void setManagers(List<Developer> developers) {
		this.managerInput.setModel(new GenericComboBoxModel<Developer>(developers));
		this.managerInput.setRenderer(new DeveloperListRenderer());
	}
	
	public Project getSelectedProject() {
		if (this.table.getSelectedRow() == -1)
			return null;
		return this.projects.get(this.table.getSelectedRow());
	}

	public String getNameInput() {
		return this.nameInput.getText();
	}

	public int getHourBudgetInput() {
		if(this.hourBudgetInput.getText().isEmpty())
			return 0;
		return Integer.parseInt(this.hourBudgetInput.getText());
	}

	public String getDeadlineInput() {
		return this.deadlineInput.getText();
	}

	public Developer getManagerInput() {
		return (Developer) this.managerInput.getSelectedItem();
	}
}
