package use_cases;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import model.Activity;
import model.Developer;
import model.Project;

import org.junit.Test;

import persistency.BaseTestDatabase;
import utils.Dialog;
import utils.DialogChoice;

public class UseCase3_GenerateReport extends BaseTestDatabase {

	private void init(){
		this.addDevelopers();
	}
	
	private long stringDateTimeToLong(String dateTime){
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		df.setLenient(false);
        Calendar cal = Calendar.getInstance();
        try {
			cal.setTime(df.parse(dateTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal.getTimeInMillis();
	}
	
//This test simply makes sure that the different report-parameters returns the expected values
	@Test
	public void testMainScenario() {
		this.init();
		Dialog.setDebugMode(true, DialogChoice.None);
		
		
        
		Project project = this.db.project().create("Test project", 200, "13-05-2014", null);
		
		assertEquals(project.getActivities().size(), 0);
		assertEquals(project.getEstPercentageCompletion(), 0);
		assertEquals(project.getEstHoursRemaining(), 200);
		assertEquals(project.getHourBudget(), 200);
		assertEquals(project.getHoursAllocatedToActivities(), 0);
		assertEquals(project.getHoursRegistered(), 0);
		
		Activity activity1 = this.db.activity().createProjectActivity(project.getId(), "activity1", 10, this.stringDateTimeToLong("01-05-2013 01:00"), this.stringDateTimeToLong("01-05-2014 01:00"));
		Activity activity2 = this.db.activity().createProjectActivity(project.getId(), "activity2", 10, this.stringDateTimeToLong("01-05-2013 01:00"), this.stringDateTimeToLong("01-05-2014 01:00"));
		
		assertEquals(project.getActivities().size(), 2);
		assertEquals(project.getEstPercentageCompletion(), 0);
		assertEquals(project.getEstHoursRemaining(), 200);
		assertEquals(project.getHourBudget(), 200);
		assertEquals(project.getHoursAllocatedToActivities(), 20);
		assertEquals(project.getHoursRegistered(), 0);
		
		Developer developer = this.db.developer().readByInitials("JL").get(0);
		Developer developer2 = this.db.developer().readByInitials("PM").get(0);
		Developer developer3 = this.db.developer().readByInitials("GH").get(0);
		Developer developer4 = this.db.developer().readByInitials("RS").get(0);
		activity1.addDeveloper(developer);
		activity1.addDeveloper(developer2);
		activity1.addDeveloper(developer3);
		activity1.addDeveloper(developer4);

//Tests that the initials of all developers are stored correctly		
		assertEquals(activity1.getAllDevsInitials(),"JL,PM,GH,RS");
		assertEquals(activity1.getAllDevsInitials() == "The Beatles", false); //Please fail!
		
		this.db.registerTime().create(this.stringDateTimeToLong("11-05-2013 06:30"), this.stringDateTimeToLong("11-05-2013 09:30"), developer.getId(), activity1.getId(), false);
		this.db.registerTime().create(this.stringDateTimeToLong("11-05-2013 06:30"), this.stringDateTimeToLong("11-05-2013 09:30"), developer2.getId(), activity1.getId(), true);
		
//Showed in the project maintainance view, and is relevant to the report
		assertEquals(activity1.getHoursRegistered(),6);
		
//Tests normal registration		
		assertEquals(project.getEstPercentageCompletion(), 3);
		assertEquals(project.getEstHoursRemaining(), 194);
		assertEquals(project.getHourBudget(), 200);
		assertEquals(project.getHoursAllocatedToActivities(), 20);
		assertEquals(project.getHoursRegistered(), 6);
		
		this.db.registerTime().create(this.stringDateTimeToLong("12-05-2013 06:30"), this.stringDateTimeToLong("12-05-2013 09:30"), developer.getId(), activity2.getId(), true);
		this.db.registerTime().create(this.stringDateTimeToLong("12-05-2013 06:30"), this.stringDateTimeToLong("12-05-2013 09:30"), developer2.getId(), activity2.getId(), true);
//Tests that assits also count		
		assertEquals(project.getEstPercentageCompletion(), 6);
		assertEquals(project.getEstHoursRemaining(), 188);
		assertEquals(project.getHourBudget(), 200);
		assertEquals(project.getHoursAllocatedToActivities(), 20);
		assertEquals(project.getHoursRegistered(), 12);
	}

}
