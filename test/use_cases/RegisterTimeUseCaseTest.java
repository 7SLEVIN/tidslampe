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
import org.junit.Test;
import org.mockito.Mockito;

import utils.Dialog;
import utils.DialogChoice;
import view.state.CalendarViewState;
import controller.view.BaseViewControllerTest;
import controller.view.CalendarViewController;
import controller.view.ViewControllerFactory;

public class RegisterTimeUseCaseTest extends BaseViewControllerTest {
	
	@Test
	public void testMainScenario() {
		
		Dialog.setDebugMode(true, DialogChoice.None);
		
		// Data setup
		this.addDevelopers();
		this.addProjects();
		this.addActivities();
		
		Developer developer = this.db.developer().readByInitials("PM").get(0);
		Project project = this.db.project().readAll().get(0);
		Activity activity = this.db.activity().readAll().get(0);
		
		// Mock view state (user input)
		CalendarViewState calView = Mockito.mock(CalendarViewState.class);
		Mockito.when(calView.getStartTimeString())		.thenReturn("10:00");
		Mockito.when(calView.getEndTimeString())		.thenReturn("13:00");
		Mockito.when(calView.getDateString())			.thenReturn("20-04-2002");
		Mockito.when(calView.getSelectedProject())		.thenReturn(project);
		Mockito.when(calView.getSelectedActivity())		.thenReturn(activity);
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
		
		// Create view controller
		CalendarViewController calController = ViewControllerFactory.CreateCalendarViewController(developer.getId());
		calController.initialize(calView);
		
		// User clicks register
		calController.addRegisterTimeEntry();
		
		// Assert
		List<TimeEntry> timeEntries = this.db.registerTime().readAll();
		Assert.assertTrue(timeEntries.size() > 0);
		
		TimeEntry timeEntry = timeEntries.get(0);
		Assert.assertEquals(activity.getDescription(), timeEntry.getActivity().getDescription());
		Assert.assertEquals(activity.getId(), timeEntry.getActivity().getId());
		Assert.assertEquals(developer.getId(), timeEntry.getDeveloper().getId());
	}
	
//	3. The user then types in what date and time span have been spent on the activity
//	4. The user chooses if the activity should be fixed
//	• If yes, the user then chooses which kind of fixed activity (eg. vacation, sickness, course etc)
//	• If no, the user chooses the project and in turn the activity which has been worked on. Only
//	projects and activities that the developer is assigned to is displayed
//	5. The user presses the “Register” button to register the time
//	6. The time entry is now displayed in the above calendar view as an orange colored slot which size
//	represents the time span
//	Alternative scenarios
//	1. The user inputs an invalid date
//	• The user is told that the date is invalid, and no time entry will be registered
//	2. The user inputs an invalid time in either start or end time
//	• The user is told that the time is invalid, and no time entry will be registered
//	3. The user tries to register time which overlaps existing time entries
//	• The user is told that the time entry overlaps existing entries, and no time entry will be registered

}
