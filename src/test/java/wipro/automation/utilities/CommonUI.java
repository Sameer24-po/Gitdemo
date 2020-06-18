package wipro.automation.utilities;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import wipro.automation.steps.ProcessSteps;

/**
 * Class contains the common method required for all pages
 * @author Harshal.e
 *
 */
public class CommonUI {
	ElementController elementController;
	TestDataController testDataController;
	ProcessSteps prosteps;
	private ArrayList<String> taskList = new ArrayList<String>();

	public CommonUI() throws Exception{		
		prosteps=new ProcessSteps();
		testDataController = new TestDataController();
		elementController = new ElementController(CommonUI.class.getSimpleName());
	}

	public CommonUI(String elementRepository) {
		testDataController = new TestDataController();
		elementController = new ElementController(elementRepository);
	}

	/**
	 * Method to get current time
	 * @return
	 * @throws Exception 
	 */
	public String getCurrentTime() throws Exception {
		String time = null;
		try {
			Date startDate = new Date();  		  
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
			time = df.format(startDate);
		}catch(Exception exception) {
			throw new Exception("Unable to get current time [getCurrentTime()]:" + exception.getMessage());
		}
		return time;
	}

	/**
	 * Method to switch to window with title
	 * @param windowTitle
	 * @return
	 * @throws Exception
	 */
	public String switchToWindowWithTitle(String windowTitle) throws Exception {
		String parentWindow = null;
		boolean isWindowFind = false;
		int count = 0;
		try {
			elementController.implicitWait(1);
			try {
				parentWindow = BrowserDriver.getBrowserDriver().getWindowHandle();
			} catch(Exception exception) {}
			do {
				Set<String> availableWindows = BrowserDriver.getBrowserDriver().getWindowHandles();		
				for(String winHandle : availableWindows){
					try{
						BrowserDriver.getBrowserDriver().switchTo().window(winHandle);
						if(BrowserDriver.getBrowserDriver().getPageSource().contains(windowTitle)) {
							isWindowFind = true;
							return parentWindow;
						}
						else
							elementController.implicitWait(2);
					}catch(Exception e){System.out.println("Exception: "+e.getMessage());}
				}
				count++;
			} while(isWindowFind == false && count < 5);
			return parentWindow;	
		}
		catch(Exception exception) {
			throw new Exception("Exception occured in switchToWindowWithTitle method: " + exception.getMessage());
		}
	}

	/** 
	 * Method to  take screen shot
	 * @param screenShotName
	 * @throws Exception 
	 */
	public void takeScreenShot(String screenShotName) throws Exception {
		try {
			TakesScreenshot takesScreenshot=(TakesScreenshot)BrowserDriver.getBrowserDriver();
			File srcFile=takesScreenshot.getScreenshotAs(OutputType.FILE);
			String tday;
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");	
			tday = df.format(new Date());
			FileUtils.copyFile(srcFile, new File( BasePath.mainpath +"ScreenShot\\"+ tday + "\\"  + screenShotName + ".png"));
			//FileUtils.copyFile(srcFile, new File(BasePath.rootDir+"\\" +ProcessSteps.processName+"_"+ProcessSteps.userid+".png"));
		} catch (Exception exception) { 
			throw exception;
		}
	}

	/**
	 * Method to quit browser
	 * @throws Exception 
	 */
	public void quitAllBrowser() throws Exception {
		try {
			// Remove all window handles from the map
			BrowserDriver.getBrowserDriver().quit();
			elementController.implicitWait(1);
		}catch(Exception exception) {
			throw new Exception("Exception occured in quitAllBrowser method: " + exception.getMessage());			
		}
	}	

	/**
	 * Method to enter text into text box
	 * @param textBoxId
	 * @param text
	 * @throws Exception
	 */
	public void enterTextIntoTextBox(String textBoxId, String text) throws Exception {
		try {
			if(text != null) {
				elementController.setWebelement(elementController.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false, textBoxId));	
				elementController.getWebelement().click();
				elementController.getWebelement().clear();
				elementController.getWebelement().sendKeys(text);
			}
		}catch(Exception exception) {
			throw new Exception("Failed to enter text into text field with element ID " + textBoxId + ": " + exception.getMessage());
		}
	}

	
	/**
	 * Generic method to click on button
	 * @param button - String type contains the button xpath 
	 * @param isDynamnicXpath TODO
	 */
	public void clickOnButton(String button, boolean isDynamnicXpath) throws Exception {
		try {			
			if(button != null)
			{
				if(isDynamnicXpath == false)
					
					elementController.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false, button).click();
				else
					elementController.waitForWebElement(BrowserDriver.getBrowserDriver(), null, true, button).click();
			}
		}catch(Exception exception) {
			throw new Exception("Failed to click on button with element ID " + button + " [clickOnButton()]: "  + exception.getMessage());
		}
	}

	/**
	 * Generic method to click on button using JavaScript
	 * @param button - String type contains the button xpath 
	 * @param isDynamicXpath TODO
	 */
	public void clickOnButtonUsingJS(String button, boolean isDynamicXpath) throws Exception 
	{
		try {			
			if(button != null)
			{
				WebElement element = elementController.waitForWebElement(BrowserDriver.getBrowserDriver(), null, isDynamicXpath, button);
				JavascriptExecutor executor = (JavascriptExecutor)BrowserDriver.getBrowserDriver();
				executor.executeScript("arguments[0].click();", element);
			}
		}catch(Exception exception) {
			throw new Exception("Failed to click on button with element ID " + button + " [clickOnButton()]: "  + exception.getMessage());
		}
	}
	
	
	/**
	 * Method to get time start and end time difference
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public String getTimeDifference(String startDateTime, String endDateTime) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date endDate = null;
		Date startDate = null;
		Calendar endCalender = null;
		Calendar startCalender = null;
		String totalExecutionTime = "";
		long diff, diffSeconds, diffMinutes, diffHours, remainder = 0;
		try {
			startDate = df.parse(startDateTime);
			endDate = df.parse(endDateTime);

			startCalender = Calendar.getInstance();
			endCalender = Calendar.getInstance();

			startCalender.setTime(startDate);
			endCalender.setTime(endDate);

			diff = endCalender.getTimeInMillis() - startCalender.getTimeInMillis();

			// Get Hours
			diffHours = diff / (60 * 60 * 1000);

			// Get Minutes
			remainder = diff % (60 * 60 * 1000);
			diffMinutes = remainder / (60*1000);

			// Get Seconds
			remainder = diff % (60*1000);
			diffSeconds = remainder / 1000;

			if(diffHours == 0)
				totalExecutionTime = "00:" + diffMinutes + ":" + diffSeconds;
			else if(diffMinutes == 0)
				totalExecutionTime = diffHours + ":00:" + diffSeconds;
			else if(diffMinutes == 0)
				totalExecutionTime = diffHours + ":" + diffMinutes + ":00";
			else
				totalExecutionTime = diffHours + ":" + diffMinutes + ":" + diffSeconds;
			return totalExecutionTime;
		}catch(Exception exception) {
			throw new Exception("Unable to get current time [getCurrentTime()]"+exception.getMessage());
		}
	}

	/**
	 * killBrowserInstance() method kills the browser instance if it is running.
	 * 
	 * @param browser	The browser name
	 * 
	 * @return void
	 */
	public void killBrowserInstance(String browser) {
		String line = "";
		Process process = null;

		try {
			process = Runtime.getRuntime().exec("tasklist");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = reader.readLine()) != null) {
				// For Debug Purpose : Prints information of running processes under windows.
				// System.out.println(line);
				taskList.add(line);
			}
			if (browser.contains("ie")) {
				if (isProcessRunning("iexplore.exe")) {
					Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
				}
				if (isProcessRunning("IEDriverServer.exe")) {
					Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
				}
			} else if (browser.contains("firefox")) {
				if (isProcessRunning("firefox.exe")) {
					Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
				}
			} else if (browser.contains("chrome")) {
				if (isProcessRunning("chrome.exe")) {
					Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
				}
				if (isProcessRunning("chromedriver.exe")) {
					Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
				}
			}
		} catch (Exception e) {	}
	}

	/**
	 * isProcessRunning() method tells whether the process is running or not.
	 * 
	 * @param process	The name of a process.
	 * 
	 * @return return true if process is running else returns false
	 */
	public boolean isProcessRunning(String process) {
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).contains(process))
				return true;
		}
		return false;
	}

	
	/**
	 * Method to generate maximum 4 digit random number
	 * @return
	 */
	public int getRandomNumber() {
		return (int)(Math.random() * 857331231);
	}
	
	/**
	 * Method to select item from the drop down
	 * @param selectBoxID
	 * @param option
	 * @throws Exception
	 */
	public void selectItemFromDropDownBox(String selectBoxID, String option)throws Exception{
		try{
			Select select = new Select(elementController.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false, selectBoxID));
			select.selectByVisibleText(option);
		}catch(Exception exception){
			throw new Exception("Failed to select "+ option +" from drop down box with element ID "+ selectBoxID +"[selectItemFromDropDownBox()]:" +exception.getMessage());
		}
	}

	/**
	 * Method to click on check box
	 * @param checkBoxID
	 * @param checkOrUncheck
	 * @throws Exception
	 */
	public void clickOnCheckBox(String checkBoxID, Boolean checkOrUncheck)throws Exception{
		try {
			if(null != checkOrUncheck){
				elementController.setWebelement(elementController.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false, checkBoxID));
				if(checkOrUncheck){
					if(!elementController.getWebelement().isSelected())
						elementController.getWebelement().click();
				}else{
					if(elementController.getWebelement().isSelected())
						elementController.getWebelement().click();
				}
			}
		}catch(Exception exception){
			throw new Exception("Exception occured in clickOnCheckBox method:"+exception.getMessage());
		}
	}
	
	/**
	 * Method to get current time stamp
	 * @return
	 * @throws Exception 
	 */
	public String getTimeStamp() throws Exception {
		try {
			return new SimpleDateFormat("yyyy_MM_dd_HHmm").format(new Date());
		}catch(Exception exception) {
			throw new Exception("Unable to get current time [getCurrentTime()]:" + exception.getMessage());
		}
	}
	
	/**
	 * Method to get generic RPA ID's Password
	 * @return
	 * @throws Exception 
	 */
	public String getProxyPassword() throws Exception {
		try {
			return new SimpleDateFormat("yyyy_MM_dd_HHmm").format(new Date());
		}catch(Exception exception) {
			throw new Exception("Unable to get current time [getCurrentTime()]:" + exception.getMessage());
		}
	}
	
	 
	 
}