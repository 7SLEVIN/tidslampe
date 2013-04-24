package controller.view;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Activity;
import model.Developer;
import model.TimeEntry;
import persistency.Database;
import utils.TimeService;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.CalendarViewState;
import controller.ControllerCollection;
import controller.action.ChangeViewAction;
import factory.ViewControllerFactory;

public class CalendarViewController extends AbstractViewController {

	private CalendarViewState viewState;
	private int developerId;
	private List<TimeEntry> timeEntries;
	private TimeService timeService;
	
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
		this.timeService = new TimeService();
		this.viewState = new CalendarViewState();
		
		this.viewState.getBackButton().addActionListener(new ChangeViewAction(this.viewContainer, ViewControllerFactory.CreateMenuViewController()));
		
		Developer dev = this.database.developer().read(this.developerId);
		this.timeEntries = this.database.registerTime().readByDeveloperId(dev.getId());
		this.viewState.setDeveloperName(dev.getName());
		Calendar currentDate = this.timeService.getCurrentDateTime();
		currentDate.add(Calendar.DAY_OF_YEAR, -3);
		this.setStartDate(currentDate);
		
		this.viewState.setProjects(this.database.project().readAll());
	}
	
	private void setStartDate(Calendar startDate) {
		List<TimeEntry> entries = new ArrayList<TimeEntry>();
		Calendar endDate = (Calendar) startDate.clone();
		endDate.add(Calendar.DAY_OF_YEAR, 7);
		for (int i = 0; i < this.timeEntries.size(); i++) {
			TimeEntry activity = this.timeEntries.get(i);
			Calendar entryDate = this.timeService.convertToCalendar(activity.getStartTime());
			if (entryDate.after(startDate) && entryDate.before(endDate)) 
				entries.add(activity);
		}
		
		this.viewState.setTimeEntries(this.timeEntries);
	}
}
