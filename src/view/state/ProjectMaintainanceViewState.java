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

@SuppressWarnings("serial")
public class ProjectMaintainanceViewState extends AbstractViewState {
	private JButton backButton;
	private JButton assManagerButton;
	private JButton addDevButton;
	private JButton splitActButton;
	private JLabel labelProjectName;
	private JTable table;
	private List<Activity> activities;
	private JComboBox<Developer> developerInput;
	
	public ProjectMaintainanceViewState(){
		this.backButton = new JButton("Back to menu");
		this.assManagerButton = new JButton("Assign manager");
		this.addDevButton = new JButton("Add developer to activity");
		this.splitActButton = new JButton("Split activity");
		
		//TODO knapper: Assign manager, add developer to activity, split activity
		this.table = new JTable();
		this.developerInput = new JComboBox<Developer>();
		
		JScrollPane scrollPane = new JScrollPane(this.table);
		scrollPane.setPreferredSize(new Dimension(400, 300));
		
		this.labelProjectName = new JLabel();
		this.labelProjectName.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		this.add(this.backButton);
		this.add(this.labelProjectName);
		this.add(scrollPane);
		this.add(this.developerInput);
		
		this.add(this.assManagerButton);
		this.add(this.addDevButton);
		this.add(this.splitActButton);
		//TODO Drop-down med developers(assign manager / add developer)
		
		
		//TODO Tabel/liste med Activities (split activity)
		
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
		this.developerInput.setModel(new GenericComboBoxModel<Developer>(developers));
		this.developerInput.setRenderer(new DeveloperListRenderer());
	}
	
	public void setProjectName(String name) {
		this.labelProjectName.setText(name);
	}
	
	public JButton getBackButton() {
		return backButton;
	}

	public JButton getAssManagerButton() {
		return assManagerButton;
	}

	public JButton getAddDevButton() {
		return addDevButton;
	}

	public JButton getSplitActButton() {
		return splitActButton;
	}
	
}
