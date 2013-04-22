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

import model.Developer;
import model.Project;
import model.gui.ProjectTableModel;

@SuppressWarnings("serial")
public class ProjectsViewState extends AbstractViewState {
	
	private JButton backButton;
//	private JButton deleteButton;
	private JButton createButton;
	private JTable table;
	private List<Project> projects;
	private JTextField nameInput;
	private JTextField hourBudgetInput;
	private JTextField deadlineInput;
	//private JComboBox<Developer> managerInput;
	
	public ProjectsViewState() {
		this.table = new JTable();
//		this.deleteButton = new JButton("Delete selected");
		this.backButton = new JButton("Back to menu");

		// Creation GUI
		this.createButton = new JButton("Create new");
		this.nameInput = new JTextField(15);
		this.hourBudgetInput = new JTextField(5);
		this.deadlineInput = new JTextField(5);
		//this.managerInput = new JComboBox<Developer>();
		
		JPanel createPanel = new JPanel();
		createPanel.add(new JLabel("Name"));
		createPanel.add(this.nameInput);
		createPanel.add(new JLabel("Hour Budget"));
		createPanel.add(this.hourBudgetInput);
		createPanel.add(new JLabel("Deadline"));
		createPanel.add(this.deadlineInput);
		createPanel.add(new JLabel("Manager"));
		//createPanel.add(this.managerInput);
		createPanel.add(this.createButton);
		
		JScrollPane scrollPane = new JScrollPane(this.table);
		scrollPane.setPreferredSize(new Dimension(400, 300));
		this.add(this.backButton);
		this.add(scrollPane);
//		this.add(this.getDeleteButton());
		this.add(createPanel);
	}

	public JButton getBackButton() {
		return backButton;
	}
	
	public void setProjects(List<Project> projects) {
		this.projects = projects;
		this.table.setModel(new ProjectTableModel(projects));
	}
	
	public Project getSelectedProject() {
		if (this.table.getSelectedRow() == -1)
			return null;
		return this.projects.get(this.table.getSelectedRow());
	}

	public JTextField getNameInput() {
		return this.nameInput;
	}
}
