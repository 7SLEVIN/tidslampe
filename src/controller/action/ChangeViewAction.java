package controller.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.ViewContainer;
import controller.view.AbstractViewController;

public class ChangeViewAction implements ActionListener {
	private ViewContainer viewContainer;
	private AbstractViewController viewController;

	public ChangeViewAction(ViewContainer viewContainer, AbstractViewController viewController) {
		this.viewContainer = viewContainer;
		this.viewController = viewController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.viewContainer.setViewState(this.viewController);

	}
}
