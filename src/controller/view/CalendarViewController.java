package controller.view;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;

import model.Activity;
import model.Developer;
import model.Project;
import model.TimeEntry;
import persistency.Database;
import persistency.RegisterTimeRepository;
import utils.Dialog;
import utils.TimeService;
import view.ViewContainer;
import view.state.AbstractViewState;
import view.state.CalendarViewState;
import controller.ControllerCollection;
import controller.action.ChangeViewAction;
import factory.ViewControllerFactory;

public class CalendarViewController extends AbstractViewController {

	private CalendarViewState viewState;
	private Developer developer;
	private TimeService timeService;
	private Calendar currentStartDate;
	
	public CalendarViewController(Database database, ViewContainer viewContainer, ControllerCollection controllers, int developerId) {
		super(database, viewContainer, controllers);
		this.developer = this.database.developer().read(developerId);
	}

	@Override
	public AbstractViewState getViewState() {
		return viewState;
	}

	@Override
	public void initialize() {
		this.timeService = new TimeService();
		this.viewState = new CalendarViewState();
		
		this.currentStartDate = this.timeService.getCurrentDateTime();
		this.currentStartDate.add(Calendar.DAY_OF_YEAR, -3);
		
		this.viewState.setDeveloperName(this.developer.getName());
		this.viewState.setProjects(this.database.project().readAll());
		this.viewState.setDateString(this.timeService.convertCalendarToInputString(this.currentStartDate));
		
		this.initListeners();	
		this.updateStartDate();
	}

	private void initListeners() {
		this.viewState.getBackButton().addActionListener(new ChangeViewAction(this.viewContainer, ViewControllerFactory.CreateMenuViewController()));
	
		this.viewState.getProjectComboBox().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED)
					updateActivities();
			}
		});
		
		this.viewState.getAddButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				addTimeEntry();
			}
		});
		
		this.viewState.getNextButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				currentStartDate.add(Calendar.DAY_OF_YEAR, 1);
				updateStartDate();
			}
		});
		
		this.viewState.getPrevButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				currentStartDate.add(Calendar.DAY_OF_YEAR, -1);
				updateStartDate();
			}
		});
	}
	
	private void addTimeEntry() {
		String startString = viewState.getDateString().trim() + " " + viewState.getStartTimeString().trim();
		String endString = viewState.getDateString().trim() + " " + viewState.getEndTimeString().trim();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        df.setLenient(false);
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        
        try {
            startDate.setTime(df.parse(startString));
            endDate.setTime(df.parse(endString));
            
    		database.registerTime().create(startDate.getTimeInMillis(), endDate.getTimeInMillis(), this.developer.getId(), this.viewState.getSelectedActivity().getId());
        } 
        catch (ParseException e) {
        	Dialog.message("Could not register time entry");
        }

		this.updateStartDate();
	}
	
	private void updateActivities() {
		Project selectedProject = this.viewState.getSelectedProject();
		this.viewState.setActivities(this.database.activity().readAllWhereEquals("project_id", selectedProject.getId()));
	}
	
	private void updateStartDate() {
		List<TimeEntry> timeEntries = this.database.registerTime().readByDeveloperId(this.developer.getId());
		this.viewState.clearTimeEntries();

		DateFormat df = new SimpleDateFormat("dd-MM-YYYY");
		DateFormat labelFormat = new SimpleDateFormat("dd-MM");
		Calendar iterDate = (Calendar) this.currentStartDate.clone();
		for (int j = 0; j < 7; j++) {
			// set date label
			this.viewState.getDayLabels()[j].setText(labelFormat.format(iterDate.getTime()));
			for (TimeEntry entry : timeEntries) {
				String startDateFormat = df.format(entry.getStartDate().getTime());
				String currentDateFormat = df.format(iterDate.getTime());
				if (j == 0) System.out.println(startDateFormat + " --- " + currentDateFormat);
				if (startDateFormat.equals(currentDateFormat)) {
					this.viewState.addTimeEntry(entry, j);
				}
			}
			iterDate.add(Calendar.DAY_OF_YEAR, 1);
		}
	}
}
