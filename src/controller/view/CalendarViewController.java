package controller.view;


import model.Developer;
import persistency.Database;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.CalendarViewState;
import controller.ControllerCollection;
import controller.action.ChangeViewAction;
import factory.ViewControllerFactory;

public class CalendarViewController extends AbstractViewController {

	private CalendarViewState viewState;
	private int developerId;
	
	public CalendarViewController(Database database, ViewContainer viewContainer, ControllerCollection controllers, int developerId) {
		super(database, viewContainer, controllers);
		this.developerId = developerId;
	}

	@Override
	public AbstractViewState getViewState() {
		return viewState;
	}

	@Override
	public void initialize() {
		this.viewState = new CalendarViewState();
		
		this.viewState.getBackButton().addActionListener(new ChangeViewAction(this.viewContainer, ViewControllerFactory.CreateMenuViewController()));
		
		Developer dev = this.database.developer().read(this.developerId);
		//List<Activity> acts = this.database.activity().readByDeveloper(dev.getId());
		this.viewState.setDeveloperName(dev.getName());
	}
}
