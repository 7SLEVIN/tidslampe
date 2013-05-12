package use_cases;

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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import utils.Dialog;
import utils.DialogChoice;
import utils.TimeService;
import view.state.CalendarViewState;
import controller.view.BaseViewControllerTest;
import controller.view.CalendarViewController;
import controller.view.ViewControllerFactory;

public class RegisterTimeUseCaseTest extends BaseViewControllerTest {

	private Dialog dialogMock;
	private Developer developer;
	private Project project;
	private Activity activity;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		Dialog.setDebugMode(true, DialogChoice.None);
		this.dialogMock = Mockito.mock(Dialog.class);
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
	public void testMainScenarioRegister() {
		CalendarViewController calendarController = this.getMockedCalendarController("10:00", "13:00", "20-04-2002");
		
		// User clicks register
		calendarController.addRegisterTimeEntry();
		
		// Assert
		List<TimeEntry> timeEntries = this.db.registerTime().readAll();
		Assert.assertEquals(1, timeEntries.size());
		
		TimeEntry timeEntry = timeEntries.get(0);
		Assert.assertEquals(this.activity.getDescription(), timeEntry.getActivity().getDescription());
		Assert.assertEquals(this.activity.getId(), timeEntry.getActivity().getId());
		Assert.assertEquals(this.developer.getId(), timeEntry.getDeveloper().getId());
	}
	
	@Test
	public void testMainScenarioReserve() {
		CalendarViewController calendarController = this.getMockedCalendarController("10:00", "13:00", "20-04-2002");
		
		// User clicks reserve
		calendarController.addReserveTimeEntry();
		
		// Assert
		List<TimeEntry> timeEntries = this.db.reserveTime().readAll();
		Assert.assertEquals(1, timeEntries.size());
		
		TimeEntry timeEntry = timeEntries.get(0);
		Assert.assertEquals(this.activity.getDescription(), timeEntry.getActivity().getDescription());
		Assert.assertEquals(this.activity.getId(), timeEntry.getActivity().getId());
		Assert.assertEquals(this.developer.getId(), timeEntry.getDeveloper().getId());
	}

	@Test
	public void testInvalidDate() {
		CalendarViewController calendarController = this.getMockedCalendarController("10:00", "13:00", "20-99-2002");
		
		// User clicks register
		calendarController.addRegisterTimeEntry();
		
		// Assert
		Mockito.verify(dialogMock).showMessage("Specified date is invalid");
		
		List<TimeEntry> timeEntries = this.db.registerTime().readAll();
		Assert.assertEquals(timeEntries.size(), 0);
	}

	@Test
	public void testInvalidStartTime() {
		CalendarViewController calendarController = this.getMockedCalendarController("fake", "13:00", "20-04-2002");
		
		// User clicks register
		calendarController.addRegisterTimeEntry();
		
		// Assert
		Mockito.verify(dialogMock).showMessage("Specified date is invalid");
		
		List<TimeEntry> timeEntries = this.db.registerTime().readAll();
		Assert.assertEquals(timeEntries.size(), 0);
	}

	@Test
	public void testOverlappingTimeEntry() {
		TimeService timeService = new TimeService();
		long startMillis = timeService.convertToMillis(2002, 03, 20, 8, 00);
		long endMillis = timeService.convertToMillis(2002, 03, 20, 11, 00);
		this.db.registerTime().create(startMillis, endMillis, this.developer.getId(), this.activity.getId(), false);
		
		CalendarViewController calendarController = getMockedCalendarController("10:00", "13:00", "20-04-2002");
		
		// User clicks register
		calendarController.addRegisterTimeEntry();
		
		// Assert
		Mockito.verify(dialogMock).showMessage("Specified date and time is overlapping existing time registrations");
		
		List<TimeEntry> timeEntries = this.db.registerTime().readAll();
		Assert.assertEquals(timeEntries.size(), 1);
	}

	private CalendarViewController getMockedCalendarController(String startTime, String endTime, String date) {
		CalendarViewState calView = Mockito.mock(CalendarViewState.class);
		Mockito.when(calView.getStartTimeString())		.thenReturn(startTime);
		Mockito.when(calView.getEndTimeString())		.thenReturn(endTime);
		Mockito.when(calView.getDateString())			.thenReturn(date);
		Mockito.when(calView.getSelectedProject())		.thenReturn(this.project);
		Mockito.when(calView.getSelectedActivity())		.thenReturn(this.activity);
		
		// Swing component mock
		Mockito.when(calView.getFixedToggleButton())	.thenReturn(new JToggleButton());
		Mockito.when(calView.getAssistToggleButton())	.thenReturn(new JToggleButton());
		Mockito.when(calView.getPrevButton())			.thenReturn(new JButton());
		Mockito.when(calView.getNextButton())			.thenReturn(new JButton());
		Mockito.when(calView.getNextDeveloperButton())	.thenReturn(new JButton());
		Mockito.when(calView.getPrevDeveloperButton())	.thenReturn(new JButton());
		Mockito.when(calView.getRegisterButton())		.thenReturn(new JButton());
		Mockito.when(calView.getReserveButton())		.thenReturn(new JButton());
		Mockito.when(calView.getProjectComboBox())		.thenReturn(new JComboBox<Project>());
		Mockito.when(calView.getActivityTypeComboBox())	.thenReturn(new JComboBox<ActivityType>());
		Mockito.when(calView.getReserveButton())		.thenReturn(new JButton());
		Mockito.when(calView.getDayLabels())			.thenReturn(new JLabel[] {new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel()});
		
		// Initialize the controller
		CalendarViewController calendarController = ViewControllerFactory.CreateCalendarViewController(this.developer.getId());
		calendarController.initialize(calView);
		return calendarController;
	}
}
