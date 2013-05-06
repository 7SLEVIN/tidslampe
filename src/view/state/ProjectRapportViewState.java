package view.state;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.GuiUtils;
import view.ViewContainer;

import model.Project;

@SuppressWarnings("serial")
public class ProjectRapportViewState extends AbstractViewState {
	
	private Project project;
	private long timeUsed;
	private JButton backButton;
	
	private JLabel nameLabel;
	private JLabel activityCountLabel;
	private JLabel timeUsedLabel;
	
	public ProjectRapportViewState(Project project) {
		this.backButton = new JButton("Back to project");
		this.project = project;
		this.nameLabel = new JLabel(project.getName());
		this.nameLabel.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		this.activityCountLabel = new JLabel("Number of activities: " + String.valueOf(this.project.getActivities().size()));
		this.timeUsedLabel = new JLabel();
		
		JPanel titlePanel = new JPanel();
		GuiUtils.setSize(titlePanel, new Dimension(ViewContainer.WINDOW_WIDTH, 30));
		titlePanel.add(this.backButton);
		titlePanel.add(this.nameLabel);

		JPanel infoPanel = new JPanel();
		GuiUtils.setSize(infoPanel, new Dimension(ViewContainer.WINDOW_WIDTH, 50));
		infoPanel.add(this.activityCountLabel);
		infoPanel.add(this.timeUsedLabel);
		
		this.add(titlePanel);
		this.add(infoPanel);
	}
	
	public void setTimeUsed(long timeUsed) {
		this.timeUsed = timeUsed;
		this.timeUsedLabel.setText("Total time used: " + String.valueOf(timeUsed));
	}
	
	public JButton getBackButton() {
		return this.backButton;
	}

}
