package controller.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.ViewContainer;
import view.state.ViewState;

public class ChangeViewAction implements ActionListener {

	private final ViewContainer viewContainer;
	private final ViewState viewState;

	public ChangeViewAction(ViewContainer viewContainer, ViewState viewState) {
		this.viewContainer = viewContainer;
		this.viewState = viewState;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.viewContainer.setViewState(this.viewState);

	}

}
