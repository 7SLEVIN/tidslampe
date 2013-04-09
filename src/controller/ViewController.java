package controller;

import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.GameOverViewState;
import view.state.MenuViewState;
import view.state.ViewState;

/**
 * Is called by {@link command.SetViewStateCommand} to switch between view
 * states
 */
public class ViewController {

	private ViewContainer viewContainer;
	
	public ViewController(ViewContainer vc) {
		this.viewContainer = vc;
	}

	/**
	 * Changes the active view state
	 * 
	 * @param state
	 *            The new state to change to
	 */
	public void setViewState(ViewState state) {
		AbstractViewState newState;
		AbstractViewState oldState = null;

		try {
			oldState = (AbstractViewState) this.viewContainer.getContentPane();
		} catch (Exception e) {
			// no previous state, dont throw an exception
			// as this is valid on first view
		}

		switch (state) {
		case Menu:
			newState = new MenuViewState();
			break;

		case GameOver:
			newState = new GameOverViewState();
			break;

		default:
			throw new IllegalArgumentException(String.format(
					"No such game state: %d", state));
		}

		if (oldState != null) {
			oldState.dispose();
		}

		//newState.setMainController(this.mainController);
		//newState.update(this.gameModel, null);
		this.viewContainer.setContentPane(newState);
		newState.initialize();

		this.viewContainer.pack();
		System.out.println(this.viewContainer.getContentPane().getComponentCount());
	}
}
