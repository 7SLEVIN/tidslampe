package model.gui;

import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import model.DatabaseObject;
import model.Developer;

public class GenericComboBoxModel<T extends DatabaseObject> implements ComboBoxModel<T> {
	
	private List<T> developers;
	private T selected;

	public GenericComboBoxModel(List<T> developers) {
		this.developers = developers;
	}
	
	@Override
	public int getSize() {
		return this.developers.size();
	}

	@Override
	public T getElementAt(int index) {
		return this.developers.get(index);
	}

	@Override
	public void setSelectedItem(Object selected) {
		this.selected = (T) selected;
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
