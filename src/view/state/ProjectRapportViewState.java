package view.state;

import java.awt.Color;
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
	private JButton backButton;

	private long timeUsed;
	private long timeExpected;
	
	private JLabel nameLabel;
	private JLabel activityCountLabel;
	private JLabel timeUsedLabel;
	private JLabel timeExpectedLabel;
	private JLabel timeLeftLabel;
	
	public ProjectRapportViewState(Project project) {
		this.backButton = new JButton("Back to project");
		this.project = project;
		this.nameLabel = new JLabel(project.getName());
		this.nameLabel.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		this.activityCountLabel = new JLabel("Number of activities: " + String.valueOf(this.project.getActivities().size()));
		this.timeExpectedLabel = new JLabel();
		this.timeUsedLabel = new JLabel();
		this.timeLeftLabel = new JLabel();
		
		JPanel titlePanel = new JPanel();
		GuiUtils.setSize(titlePanel, new Dimension(ViewContainer.WINDOW_WIDTH, 30));
		titlePanel.add(this.backButton);
		titlePanel.add(this.nameLabel);

		JPanel infoPanel = new JPanel();
		GuiUtils.setSize(infoPanel, new Dimension(200, 200));
		infoPanel.add(this.activityCountLabel);
		infoPanel.add(this.timeExpectedLabel);
		infoPanel.add(this.timeUsedLabel);
		infoPanel.add(this.timeLeftLabel);
		
		this.add(titlePanel);
		this.add(infoPanel);
	}
	
	public void setTimeUsed(long timeUsed) {
		this.timeUsed = timeUsed;
		this.timeUsedLabel.setText(String.format("Total time used: %d min.", timeUsed));
	}
	
	public void setTimeExpected(long timeExpected) {
		this.timeExpected = timeExpected;
		this.timeExpectedLabel.setText(String.format("Total expected time: %d min.", timeExpected));
	}
	
	public void setTimeLeft() {
		long timeLeft = this.timeExpected - this.timeUsed;
		if (timeLeft < 0) this.timeLeftLabel.setForeground(Color.RED);
		this.timeLeftLabel.setText(String.format("Time left: %d min.", timeLeft));
	}
	
	public JButton getBackButton() {
		return this.backButton;
	}

}
