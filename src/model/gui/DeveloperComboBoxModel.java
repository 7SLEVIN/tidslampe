package model.gui;

import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import model.Developer;

public class DeveloperComboBoxModel implements ComboBoxModel<Developer> {
	
	private List<Developer> developers;
	private Developer selected;

	public DeveloperComboBoxModel(List<Developer> developers) {
		this.developers = developers;
	}
	
	@Override
	public int getSize() {
		return this.developers.size();
	}

	@Override
	public Developer getElementAt(int index) {
		return this.developers.get(index);
	}

	@Override
	public void setSelectedItem(Object selected) {
		this.selected = (Developer) selected;
	}

	@Override
	public Object getSelectedItem() {
		return this.selected;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	
}
