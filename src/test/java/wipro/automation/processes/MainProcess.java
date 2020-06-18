package wipro.automation.processes;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.Assert;
import java.sql.SQLException;
import wipro.automation.steps.ProcessSteps;

public class MainProcess {
	
	
	private static ProcessSteps steps;
	

	@BeforeTest 
	public static void setup(){
		
		try {
			steps = new ProcessSteps();
					
		} catch(Exception e) {
		System.out.println("Exception in testBeforeClass method of main process "+e.getMessage());	
		}
 	}
	
	@Test
	public void Process_steps() throws SQLException {
		
       
		try {
			
			
				steps.launchBrowser();
			
				steps.openApplication();

				steps.loginToApp();

				steps.activity();

				steps.logOut();
				
				steps.makingExcelsheet();
				  	
            
		} catch (Exception e) {
			System.out.println("Process terminated :" + e.getMessage());
			e.printStackTrace();
			 
       System.out.println("Failed :"+e.getMessage());
       
       Assert.fail();
		}
		}
	

	@AfterTest
	public static void tearDown(){
		try {
			steps.tearDown();
		} catch (Exception e) {
			Assert.fail();
		}
	}

	
}
