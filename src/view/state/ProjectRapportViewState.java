package view.state;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import utils.GuiUtils;
import view.ViewContainer;

@SuppressWarnings("serial")
public class ProjectRapportViewState extends AbstractViewState {
	
	private JButton backButton;

	private long timeUsed;
	private long timeAssignedToActivities;
	
	private JLabel nameLabel;
	private JLabel activityCountLabel;
	private JLabel timeUsedLabel;
	private JLabel estimatedPercentageComplete;
	private JLabel estimatedTimeRemaining; 
	private JLabel timeExpectedLabel;
	private JLabel timeLeftLabel;
	
	public ProjectRapportViewState(String projectName, int noOfActivities) {
		this.backButton = new JButton("Back to project");
		this.nameLabel = new JLabel(projectName);
		this.nameLabel.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		this.activityCountLabel = new JLabel("Number of activities: " + String.valueOf(noOfActivities));
		this.timeUsedLabel = new JLabel();
		this.estimatedPercentageComplete = new JLabel();
		this.estimatedTimeRemaining = new JLabel();
		this.timeExpectedLabel = new JLabel();
		this.timeUsedLabel = new JLabel();
		this.timeLeftLabel = new JLabel();
		
		JPanel titlePanel = new JPanel();
		GuiUtils.setSize(titlePanel, new Dimension(ViewContainer.WINDOW_WIDTH, 30));
		titlePanel.add(this.backButton);
		titlePanel.add(this.nameLabel);

		JPanel infoPanel = new JPanel();
		GuiUtils.setSize(infoPanel, new Dimension(350, 150));
		infoPanel.add(this.activityCountLabel);
		infoPanel.add(this.timeExpectedLabel);
		infoPanel.add(this.timeUsedLabel);
		infoPanel.add(this.estimatedPercentageComplete);
		infoPanel.add(this.estimatedTimeRemaining);
		infoPanel.add(this.timeLeftLabel);
		
		this.add(titlePanel);
		this.add(infoPanel);
	}
	
	public void setChart(int remainingTime, int registeredTime){
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("Remaining time", remainingTime);
		data.setValue("Registered time", registeredTime);

		JFreeChart chart = ChartFactory.createPieChart("Time-budget breakdown",data,true,true,false );
		
		ChartPanel chartPanel = new ChartPanel(chart);
		
		this.add(chartPanel);
	}
	
	public void setPercentageComplete(float percentage){
		this.estimatedPercentageComplete.setText("Estimated percentage complete: " + String.valueOf((int) percentage)+"%");
	}
	
	public void setTimeUsed(int timeUsed) {
		this.timeUsed = timeUsed;
		this.timeUsedLabel.setText("Total time registered1: " + String.valueOf(timeUsed) + (timeUsed == 1 ? "hour" :" hours"));
	}
	
	public void setTimeRemaining(int timeRemaining){
		if (timeRemaining < 0) this.estimatedTimeRemaining.setForeground(Color.RED);
		this.estimatedTimeRemaining.setText("Estimated time remaining: " + String.valueOf(timeRemaining) + " hours");
	}

	//TODO percentage of budgeted time assigned to activities

	public void setTimeExpected(long timeAssignedToActivities) {
		this.timeAssignedToActivities = timeAssignedToActivities;
		this.timeExpectedLabel.setText(String.format("Time assigned to activities: %d hours", timeAssignedToActivities/60));
	}

	public void setTimeLeft() {
		//TODO delete this
//		long timeLeft = this.timeAssignedToActivities - this.timeUsed;
//		
//		this.timeLeftLabel.setText(String.format("Time left: %d hours", timeLeft/60));
	}
	
	public void percentageUnassigned(){
		// TODO
//		this.timeLeftLabel.setText("skriv info om hvor mange procent af budgeted hours der er delt ud p� aktiviteter");
	}
	
	public JButton getBackButton() {
		return this.backButton;
	}

}
