package view.state;

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
	
	private JLabel nameLabel;
	private JLabel activityCountLabel;
	private JLabel timeUsedLabel;
	private JLabel estimatedPercentageComplete;
	private JLabel estimatedTimeRemaining; 
	
	public ProjectRapportViewState(String projectName, int noOfActivities) {
		this.backButton = new JButton("Back to project");
		this.nameLabel = new JLabel(projectName);
		this.nameLabel.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		this.activityCountLabel = new JLabel("Number of activities: " + String.valueOf(noOfActivities));
		this.timeUsedLabel = new JLabel();
		this.estimatedPercentageComplete = new JLabel();
		this.estimatedTimeRemaining = new JLabel();
		
		JPanel titlePanel = new JPanel();
		GuiUtils.setSize(titlePanel, new Dimension(ViewContainer.WINDOW_WIDTH, 30));
		titlePanel.add(this.backButton);
		titlePanel.add(this.nameLabel);

		JPanel infoPanel = new JPanel();
		GuiUtils.setSize(infoPanel, new Dimension(ViewContainer.WINDOW_WIDTH, 50));
		infoPanel.add(this.activityCountLabel);
		infoPanel.add(this.timeUsedLabel);
		infoPanel.add(this.estimatedPercentageComplete);
		infoPanel.add(this.estimatedTimeRemaining);
		
		
		this.add(titlePanel);
		this.add(infoPanel);
	}
	
	public void setChart(int remainingTime, int registeredTime){
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("Remaining time", remainingTime);
		data.setValue("Registered time", registeredTime);

		JFreeChart chart = ChartFactory.createPieChart("Time-budget breakdown",data,true,true,false );
		this.add(new ChartPanel(chart));
	}
	
	public void setPercentageComplete(float percentage){
		this.estimatedPercentageComplete.setText("Estimated percentage complete: " + String.valueOf((int) percentage)+"%");
	}
	
	public void setTimeUsed(int timeUsed) {
		this.timeUsedLabel.setText("Total time used: " + String.valueOf(timeUsed) + (timeUsed == 1 ? "hour" :" hours"));
	}
	
	public void setTimeRemaining(int timeRemaining){
		this.estimatedTimeRemaining.setText("Estimated time remaining: " + String.valueOf(timeRemaining) + " hours");
	}
	
	public JButton getBackButton() {
		return this.backButton;
	}

}
