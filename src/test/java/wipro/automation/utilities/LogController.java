package wipro.automation.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class contains methods to write logs into the CSV file
 * @author Harshal.e
 *
 */
public class LogController {
	File file = null;
	FileWriter fw = null;
	BufferedWriter bw = null;
	TestDataController testDataController;
	
	public LogController(String fileName) {
		try {			
			/*testDataController = new TestDataController();
//			PropertyConfigurator.configure(BrowserDriver.basedirpath+"\\ElementRepository\\log4j.properties");
			if(fileName != null) {
				file = new File(BasePath.basedirpath+"\\csvreport\\" + fileName +"_"+ new SimpleDateFormat("yyyy_MM_dd_HHmm").format(new Date())+ ".csv");
				if(file.exists())
					file.delete();
				fw = new FileWriter(BasePath.basedirpath+"\\csvreport\\" + fileName + ".csv");
				bw = new BufferedWriter(fw);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to write log in the CSV file
	 * @param step - String type contains test case step / activity 
	 * @param discription - String type contains test case step description
	 * @param testStatus - String type contains test case status
	 */
	public void writeLog(String stepNumber, String stepDetails, String testStatus){
		try {
		/*	bw.write(stepNumber + "," + stepDetails + "," + testStatus);
			bw.newLine();*/
		}catch(Exception exception) {
			System.out.println("Unable to write log: " +exception.getMessage());
		}
	}
	
	/**
	 * Method to close log writer
	 */
	public void closeLogWriter() {
		try {
			/*bw.close();
			fw.close();*/
		}catch(Exception exception) {
			System.out.println("Unable to close log writer instance: " +exception.getMessage());
		}
	}
}
