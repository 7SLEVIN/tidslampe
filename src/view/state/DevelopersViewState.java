package view.state;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Developer;
import model.gui.DeveloperTableModel;

@SuppressWarnings("serial")
public class DevelopersViewState extends AbstractViewState {
	private JButton deleteButton;
	private JTable table;
	private List<Developer> developers;

	public DevelopersViewState() {
		this.table = new JTable();
		this.deleteButton = new JButton("Delete selected");
		
		this.add(new JScrollPane(this.table));
		this.add(this.getDeleteButton());
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

	public JButton getDeleteButton() {
		return deleteButton;
	}
}
