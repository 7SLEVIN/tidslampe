package view.state;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.Developer;
import model.gui.DeveloperTableModel;

@SuppressWarnings("serial")
public class DevelopersViewState extends AbstractViewState {
	private JButton deleteButton;
	private JButton createButton;
	private JTable table;
	private List<Developer> developers;
	private JTextField initialsInput;
	private JTextField nameInput;

	public DevelopersViewState() {
		this.table = new JTable();
		this.deleteButton = new JButton("Delete selected");

		// Creation GUI
		this.createButton = new JButton("Create new");
		this.initialsInput = new JTextField(5);
		this.nameInput = new JTextField(15);
		
		JPanel createPanel = new JPanel();
		createPanel.add(new JLabel("Initials"));
		createPanel.add(this.initialsInput);
		createPanel.add(new JLabel("Name"));
		createPanel.add(this.nameInput);
		createPanel.add(this.createButton);
		
		JScrollPane scrollPane = new JScrollPane(this.table);
		scrollPane.setPreferredSize(new Dimension(400, 300));
		this.add(scrollPane);
		this.add(this.getDeleteButton());
		this.add(createPanel);
	}
	
	public String getNameInput() {
		return this.nameInput.getText();
	}
	
	public String getInitialsInput() {
		return this.initialsInput.getText();
	}
	
	public void setDevelopers(List<Developer> developers) {
		this.developers = developers;
		this.table.setModel(new DeveloperTableModel(this.developers));
	}
	
	public Developer getSelectedDeveloper() {
		if (this.table.getSelectedRow() == -1)
			return null;
		return this.developers.get(this.table.getSelectedRow());
	}

	public JButton getCreateButton() {
		return this.createButton;
	}

	public JButton getDeleteButton() {
		return this.deleteButton;
	}
}
