package controller.view;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

import model.Activity;
import model.ActivityType;
import model.Developer;
import model.Project;
import model.TimeEntry;

import org.junit.Before;
import org.junit.Test;

import utils.Dialog;
import utils.DialogChoice;
import utils.TimeService;
import view.state.CalendarViewState;
import controller.view.BaseViewControllerTest;
import controller.view.CalendarViewController;
import controller.view.ViewControllerFactory;

public class TestCalendarViewController extends BaseViewControllerTest {

	private Dialog dialogMock;
	private Developer developer;
	private Project project;
	private Activity activity;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		Dialog.setDebugMode(true, DialogChoice.None);
		this.dialogMock = mock(Dialog.class);
		Dialog.setInstance(this.dialogMock);
		
		// Data setup
		this.addDevelopers();
		this.addProjects();
		this.addActivities();

		this.developer = this.db.developer().readByInitials("PM").get(0);
		this.project = this.db.project().readAll().get(0);
		this.activity = this.db.activity().readAll().get(0);
	}

	@Test
	public void testInvalidStartTime() {
		CalendarViewController calendarController = this.getMockedCalendarController("fake", "13:00", "20-04-2002");
		
		// User clicks register
		calendarController.addRegisterTimeEntry();
		
		// Assert
		verify(dialogMock).showMessage("Specified date is invalid");
		
		List<TimeEntry> timeEntries = this.db.registerTime().readAll();
		assertEquals(timeEntries.size(), 0);
	}

	@Test
	public void testOverlappingTimeEntryReserve() {
		TimeService timeService = new TimeService();
		long startMillis = timeService.convertToMillis(2002, 03, 20, 8, 00);
		long endMillis = timeService.convertToMillis(2002, 03, 20, 11, 00);
		this.db.registerTime().create(startMillis, endMillis, this.developer.getId(), this.activity.getId(), false);
		
		CalendarViewController calendarController = getMockedCalendarController("10:00", "13:00", "20-04-2002");
		
		// User clicks register
		calendarController.addReserveTimeEntry();
		
		// Assert
		verify(dialogMock).showMessage("Specified date and time is overlapping existing time reservations");
		
		List<TimeEntry> timeEntries = this.db.registerTime().readAll();
		assertEquals(timeEntries.size(), 1);
	}
	
	private CalendarViewController getMockedCalendarController(String startTime, String endTime, String date) {
		return this.getMockedCalendarController(this.project, this.activity, startTime, endTime, date);
	}

	private CalendarViewController getMockedCalendarController(Project project, Activity activity, String startTime, String endTime, String date) {
		CalendarViewState calView = mock(CalendarViewState.class);
		when(calView.getStartTimeString())		.thenReturn(startTime);
		when(calView.getEndTimeString())		.thenReturn(endTime);
		when(calView.getDateString())			.thenReturn(date);
		when(calView.getSelectedProject())		.thenReturn(project);
		when(calView.getSelectedActivity())		.thenReturn(activity);
		
		// Swing component mock
		when(calView.getFixedToggleButton())	.thenReturn(new JToggleButton());
		when(calView.getAssistToggleButton())	.thenReturn(new JToggleButton());
		when(calView.getPrevButton())			.thenReturn(new JButton());
		when(calView.getNextButton())			.thenReturn(new JButton());
		when(calView.getNextDeveloperButton())	.thenReturn(new JButton());
		when(calView.getPrevDeveloperButton())	.thenReturn(new JButton());
		when(calView.getRegisterButton())		.thenReturn(new JButton());
		when(calView.getReserveButton())		.thenReturn(new JButton());
		when(calView.getProjectComboBox())		.thenReturn(new JComboBox<Project>());
		when(calView.getActivityTypeComboBox())	.thenReturn(new JComboBox<ActivityType>());
		when(calView.getReserveButton())		.thenReturn(new JButton());
		when(calView.getDayLabels())			.thenReturn(new JLabel[] {new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel()});
		
		// Initialize the controller
		CalendarViewController calendarController = ViewControllerFactory.CreateCalendarViewController(this.developer.getId());
		calendarController.initialize(calView);
		return calendarController;
	}
}
