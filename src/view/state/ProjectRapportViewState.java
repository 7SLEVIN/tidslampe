package view.state;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

import model.Project;

@SuppressWarnings("serial")
public class ProjectRapportViewState extends AbstractViewState {
	
	private Project project;
	private JLabel projectNameLabel;
	private JButton backButton;
	
	public ProjectRapportViewState(Project project) {
		this.backButton = new JButton("Back to project");
		this.project = project;
		this.projectNameLabel = new JLabel(project.getName());
		this.projectNameLabel.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		
		this.add(this.backButton);
		this.add(this.projectNameLabel);
	}
	
	public JButton getBackButton() {
		return this.backButton;
	}

}
