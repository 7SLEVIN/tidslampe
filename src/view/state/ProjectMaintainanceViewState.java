package view.state;

import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Activity;
import model.Developer;
import model.Project;
import model.gui.ActivityTableModel;
import model.gui.DeveloperListRenderer;
import model.gui.GenericComboBoxModel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.JTextField;

import utils.GuiUtils;
import utils.TimeService;
import view.ViewContainer;

@SuppressWarnings("serial")
public class ProjectMaintainanceViewState extends AbstractViewState {
	private JButton backButton;
	private JButton assManagerButton;
	private JButton addDevButton;
	private JButton addActivityButton;
	private JLabel labelProjectName;
	private JTable table;
	private List<Activity> activities;
	private JComboBox<Developer> developerInput;
	private JPanel pnlAddActivity;
	private JPanel pnlControls;
	private JPanel pnlNavbar;
	private JPanel pnlList;
	private JTextField txtName;
	private JLabel lblName;
	private JPanel panel;
	private JTextField txtHourBudget;
	private JLabel lblHourBudget;
	private JLabel lblDeadline;
	private JTextField txtDeadline;
	private JButton createReportButton;

	public ProjectMaintainanceViewState() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		pnlNavbar = new JPanel();
		pnlNavbar.setMinimumSize(new Dimension(750, 40));
		pnlNavbar.setPreferredSize(new Dimension(750, 40));
		add(pnlNavbar);
		this.backButton = new JButton("Back to projects");
		pnlNavbar.add(backButton);

		this.labelProjectName = new JLabel();
		labelProjectName.setText("Project name");
		pnlNavbar.add(labelProjectName);
		this.labelProjectName.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));

		pnlList = new JPanel();
		add(pnlList);

		this.table = new JTable();

		JScrollPane scrollPane = new JScrollPane(this.table);
		scrollPane.setMinimumSize(new Dimension(600, 300));
		pnlList.add(scrollPane);
		scrollPane.setPreferredSize(new Dimension(600, 300));

		pnlControls = new JPanel();
		add(pnlControls);
		pnlControls.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		this.developerInput = new JComboBox<Developer>();
		pnlControls.add(developerInput);
		this.assManagerButton = new JButton("Assign manager");
		pnlControls.add(assManagerButton);
		this.addDevButton = new JButton("Add developer to activity");
		pnlControls.add(addDevButton);

		pnlAddActivity = new JPanel();
		pnlAddActivity.setMinimumSize(new Dimension(780, 40));
		pnlAddActivity.setPreferredSize(new Dimension(780, 40));
		add(pnlAddActivity);

		lblName = new JLabel("Name");
		pnlAddActivity.add(lblName);

		txtName = new JTextField();
		pnlAddActivity.add(txtName);
		txtName.setColumns(10);

		lblHourBudget = new JLabel("Hour budget");
		pnlAddActivity.add(lblHourBudget);

		txtHourBudget = new JTextField();
		pnlAddActivity.add(txtHourBudget);
		txtHourBudget.setColumns(10);

		lblDeadline = new JLabel("Deadline");
		pnlAddActivity.add(lblDeadline);

		txtDeadline = new JTextField();
		txtDeadline.setColumns(10);
		pnlAddActivity.add(txtDeadline);

		panel = new JPanel();
		add(panel);
		this.addActivityButton = new JButton("Add activity");
		panel.add(addActivityButton);
		
		JPanel reportPanel = new JPanel();
		this.createReportButton = new JButton("Create report");
		reportPanel.add(this.createReportButton);
		GuiUtils.setSize(reportPanel, new Dimension(ViewContainer.WINDOW_WIDTH, 30));
		this.add(reportPanel);
		// TODO Drop-down med developers(assign manager / add developer)

		// TODO Tabel/liste med Activities (split activity)

	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
		this.table.setModel(new ActivityTableModel(activities));
	}

	public Developer getSelectedDeveloper() {
		return (Developer) this.developerInput.getSelectedItem();
	}

	public Activity getSelectedActivity() {
		if (this.table.getSelectedRow() == -1)
			return null;
		return this.activities.get(this.table.getSelectedRow());
	}

	public void setManagers(List<Developer> developers) {
		this.developerInput.setModel(new GenericComboBoxModel<Developer>(
				developers));
		this.developerInput.setRenderer(new DeveloperListRenderer());
	}

	public void setProjectName(String name) {
		this.labelProjectName.setText(name);
	}

	public JButton getBackButton() {
		return this.backButton;
	}

	public JButton getAssManagerButton() {
		return this.assManagerButton;
	}

	public JButton getAddDevButton() {
		return this.addDevButton;
	}

	public JButton getAddActivityButton() {
		return this.addActivityButton;
	}
	
	public JButton getCreateReportButton() {
		return this.createReportButton;
	}

	// Model getters
	public String getNameInput() {
		return this.txtName.getText();
	}

	public String getHourBudgetInput() {
		return this.txtHourBudget.getText();
	}

	public String getDeadlineInput() {
		return this.txtDeadline.getText();
	}
}
