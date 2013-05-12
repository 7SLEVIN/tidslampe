package use_cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import model.Developer;
import model.Project;

import org.junit.Test;

import persistency.BaseTestDatabase;
import utils.TimeService;

public class UseCase1_newProject extends BaseTestDatabase {

	TimeService timeService = new TimeService();
	
	private void init(){
		this.addDevelopers();
	}
	
	//Tests whether the correct input yields a new project
	@Test
	public void testMainScenario() {
		//We don't test the GUI, so we expect that the ViewStates and ViewControllers work correctly.
		this.init();
		
		String nameInput = "Our newest project";
		int hourBudgetInput = 1337;
		String deadlineInput = "13-02-2045";
		Developer developer = this.db.developer().readByInitials("JL").get(0);
		
		Project project = this.db.project().create(nameInput, hourBudgetInput, deadlineInput, developer);
		
//Checks if the returned project has the input values		
		assertEquals(nameInput,project.getName());
		assertEquals(hourBudgetInput,project.getHourBudget());
		assertEquals(this.timeService.convertToMillis(deadlineInput),project.getDeadline());
		assertEquals(developer,project.getManager());
		
		int projectId = project.getId();
		
//Checks if the stored project has the same values
		Project sameProject = this.db.project().read(projectId);
		assertEquals(sameProject.getName(),project.getName());
		assertEquals(sameProject.getHourBudget(),project.getHourBudget());
		assertEquals(sameProject.getDeadline(),project.getDeadline());
		assertEquals(sameProject.getManager().getId(),project.getManager().getId());
	}
	
	//This is Alt. scenario #1
	//It 
	@Test
	public void testUserInputIncomplete(){
		this.init();
//User forgets hour-budget	
		String nameInput = "Our newest project";
		int hourBudgetInput = 0;
		String deadlineInput = "13-02-2045";
		Developer developer = this.db.developer().readByInitials("JL").get(0);
		
		int projectsCount = this.db.project().readAll().size();
		
		Project project = this.db.project().create(nameInput, hourBudgetInput, deadlineInput, developer);
		
		assertEquals(null,project);
		assertEquals(projectsCount , this.db.project().readAll().size()); //No new projects should be added
		
//User forgets name of project
		hourBudgetInput = 1337;
		nameInput = "";
		
		project = this.db.project().create(nameInput, hourBudgetInput, deadlineInput, developer);
		
		assertEquals(null,project);
		assertEquals(projectsCount , this.db.project().readAll().size()); //No new projects should be added
	}

}