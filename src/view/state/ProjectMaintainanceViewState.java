package view.state;

import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Activity;
import model.Project;
import model.gui.ActivityTableModel;
import model.gui.ProjectTableModel;

@SuppressWarnings("serial")
public class ProjectMaintainanceViewState extends AbstractViewState {
	private JButton backButton;
	private JLabel labelProjectName;
	private JTable table;
	private List<Activity> activities;
	
	public ProjectMaintainanceViewState(){
		this.backButton = new JButton("Back to menu");
		this.table = new JTable();
		
		JScrollPane scrollPane = new JScrollPane(this.table);
		scrollPane.setPreferredSize(new Dimension(400, 300));
		
		this.labelProjectName = new JLabel();
		this.labelProjectName.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		this.add(this.backButton);
		this.add(this.labelProjectName);
		this.add(scrollPane);
		//TODO Drop-down med developers(assign manager / add developer) 
		//TODO Tabel/liste med Activities (split activity)
		
	}
	
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
		this.table.setModel(new ActivityTableModel(activities));
	}
	
	public void setProjectName(String name) {
		this.labelProjectName.setText(name);
	}
	
	public JButton getBackButton() {
		return backButton;
	}
}
