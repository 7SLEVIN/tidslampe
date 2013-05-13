package usecases;

import static org.junit.Assert.assertEquals;
import model.Developer;
import model.Project;

import org.junit.Before;
import org.junit.Test;

import persistency.BaseTestDatabase;
import utils.Dialog;
import utils.DialogChoice;
import utils.TimeService;

public class TestNewProjectUseCase extends BaseTestDatabase {

	TimeService timeService = new TimeService();
	private Developer developer;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		this.addDevelopers();

		developer = this.db.developer().readByInitials("JL").get(0);
	}
	
	//Tests whether the correct input yields a new project
	@Test
	public void testMainScenario() {
		//We don't test the GUI, so we expect that the ViewStates and ViewControllers work correctly.
		
		String nameInput = "Projektet";
		int hourBudgetInput = 130;
		String deadlineInput = "13-02-2015";
		
		Project project = this.db.project().create(nameInput, hourBudgetInput, deadlineInput, developer);
		
//Checks if the returned project has the input values		
		assertEquals(nameInput,project.getName());
		assertEquals(nameInput,project.toString());
		assertEquals(hourBudgetInput,project.getHourBudget());
		assertEquals(this.timeService.convertToMillis(deadlineInput), project.getDeadline());
		assertEquals(developer,project.getManager());
		
		int projectId = project.getId();
		
//Checks if the stored project has the same values
		Project sameProject = this.db.project().read(projectId);
		assertEquals(sameProject.getName(),project.getName());
		assertEquals(sameProject.getHourBudget(),project.getHourBudget());
		assertEquals(sameProject.getDeadline(),project.getDeadline());
		assertEquals(sameProject.getManager().getId(),project.getManager().getId());
	}
	
	//This is Alternative scenario #1
	@Test
	public void testUserInputIncomplete(){
		//User forgets hour-budget	
		String nameInput = "Projektet";
		int hourBudgetInput = 0;
		String deadlineInput = "13-02-2015";
		Developer developer = this.db.developer().readByInitials("JL").get(0);
		
		int projectsCount = this.db.project().readAll().size();
		
		Project project = this.db.project().create(nameInput, hourBudgetInput, deadlineInput, developer);
		
		assertEquals(null,project);
		assertEquals(projectsCount , this.db.project().readAll().size()); //No new projects should be added
		
		//User forgets to add a name to the project
		hourBudgetInput = 130;
		nameInput = "";
		
		project = this.db.project().create(nameInput, hourBudgetInput, deadlineInput, developer);
		
		assertEquals(null,project);
		assertEquals(projectsCount , this.db.project().readAll().size()); //No new projects should be added
		
		//User forgets to add a deadline to the project
		nameInput = "Projektet";
		deadlineInput = "";
		
		project = this.db.project().create(nameInput, hourBudgetInput, deadlineInput, developer);
		
		assertEquals(null,project);
		assertEquals(projectsCount , this.db.project().readAll().size()); //No new projects should be added
		
		//User accidentally clicks the "create new project"-button
		hourBudgetInput = 0;
		nameInput = "";
		deadlineInput = "";
		
		project = this.db.project().create(nameInput, hourBudgetInput, deadlineInput, null);
		
		assertEquals(null,project);
		assertEquals(projectsCount , this.db.project().readAll().size()); //No new projects should be added
	}
	
	//This is Alternative scenario #2
	@Test
	public void testInvalidDeadline(){
		//User forgets hour-budget	
		String nameInput = "Projektet";
		int hourBudgetInput = 130;
		String deadlineInput = "30-02-2015";
		Developer developer = this.db.developer().readByInitials("JL").get(0);
		
		int projectsCount = this.db.project().readAll().size();
			
		Project project = this.db.project().create(nameInput, hourBudgetInput, deadlineInput, developer);
		
		assertEquals(null,project);
		assertEquals(projectsCount , this.db.project().readAll().size()); //No new projects should be added
	}
	
	//This is Alternative scenario #3
	@Test
	public void testInvalidHourBudget(){
		//User inputs invalid hour-budget (budget <= 0)
		Developer developer = this.db.developer().readByInitials("JL").get(0);
		
		int projectsCount = this.db.project().readAll().size();
			
		Project project = this.db.project().create("Projektet", -10, "13-02-2015", developer);
		
		assertEquals(null,project);
		assertEquals(projectsCount , this.db.project().readAll().size()); //No new projects should be added
	}

}
