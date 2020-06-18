package wipro.automation.steps;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import javax.mail.Message;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

//import wipro.automation.pages.ProcessPage;
import wipro.automation.processes.MainProcess;
import wipro.automation.utilities.BasePath;
import wipro.automation.utilities.BrowserDriver;
import wipro.automation.utilities.CommonUI;
import wipro.automation.utilities.ElementController;
import wipro.automation.utilities.ExcelFileController;
import wipro.automation.utilities.TestDataController;
import wipro.utils.DBUtil;
import wipro.utils.MailUtil;
import wipro.automation.pages.ProcessPage;
public class ProcessSteps {

	public static String processName = MainProcess.class.getSimpleName();

	Hashtable<String, String> testData = null;
	TestDataController testDataController = null;
	ElementController elementController = null;
	ExtentReports extent;
	ExtentTest extentTest;
	CommonUI commonUI = null;
	String currentdateandtime = null;
	String endTime = null;
	ProcessPage ProPage;
	private XSSFSheet Credetialattachment = null;
	private XSSFSheet Testcaseattachment = null;
	
	public static Properties pro;
	 int Makerusername;
	 int Makerpassword;
	 int Checkerusername;
	 int Checkerpassword;
	 
	 int srno;
	 int testcaseid;
	 int testCase;
	 int testdata;
	 int testresult;
	
	public DBUtil dbutil = null;

	int execid = 0;
	private Date todayDate = new Date();
	public long execid1 = 0;
	private String htmlreportName;
	public static String userid;
	private XSSFWorkbook wb = null;;
	String tday;
	String Basepath;

	public ProcessSteps() throws Exception {

		// Get browser driver
		this.commonUI = new CommonUI(ProcessPage.class.getSimpleName());
		elementController = new ElementController(ProcessPage.class.getSimpleName());
		ProPage = new ProcessPage();
		dbutil = new DBUtil();
		
		
		//For Creating Folder date wise
		currentdateandtime = commonUI.getTimeStamp();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");	
		tday = df.format(todayDate);
		
		wb = new XSSFWorkbook();
		//this.duplicateRecords = wb.createSheet();
		
		
		//Creating Folder
		this.createFolder(BasePath.mainpath + "Report\\" + tday);
		this.createFolder(BasePath.mainpath +"ScreenShot\\" + tday);
		
		// Extend Report
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(BasePath.mainpath + "Report\\" + tday + "\\" + "Processname_" + currentdateandtime +".html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName(processName + " Function Report");
		htmlReporter.config().setTheme(Theme.DARK);
		
		extent.setSystemInfo("Hostname", "LocalHost");
		extent.setSystemInfo("Operating System", "Windows10");
		extent.setSystemInfo("Tester Name", "Sameer");
		extent.setSystemInfo("Browser", "Chrome");
		
		//extentTest = extent.cr(processName);
		
		//properties
		System.out.println(System.getProperty("user.dir"));
		FileInputStream input=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Data.properties");
		pro=new Properties();
		pro.load(input);
		//Read date from Excel login credential and TestCase
		
		this.readdatafromTestcasesheet();
	}

	
	public void readdatafromTestcasesheet() throws Exception 
	{
		this.Testcaseattachment= ExcelFileController.readXlsxFile("D:\\Automation\\ProcessName\\Test case\\TestCaseMasterSheet");
		
		this.srno = this.getColumnNumber(Testcaseattachment, "Sr No");
		this.testcaseid = this.getColumnNumber(Testcaseattachment, "TestCase id");
		this.testCase= this.getColumnNumber(Testcaseattachment, "TestCase");
		this.testdata= this.getColumnNumber(Testcaseattachment, "Test Data");
		this.testresult= this.getColumnNumber(Testcaseattachment, "Test Result");
		
	}
	
	
	public int getColumnNumber(XSSFSheet sheet,String columnName) throws Exception {
		try {
			int totalRowCount = sheet.getPhysicalNumberOfRows();
			for (int currentRowNum = 0; currentRowNum < totalRowCount; currentRowNum++) {
				XSSFRow currentRow = sheet.getRow(currentRowNum);
				if (currentRow == null) continue;

				// Get Column cell number of column
				for (int cellNum = 0; cellNum < currentRow.getLastCellNum(); cellNum++){
					XSSFCell currentColumn = currentRow.getCell(cellNum); //
					if(currentColumn.getCellTypeEnum() == CellType.STRING){
						if(currentColumn.getStringCellValue().toString().equalsIgnoreCase(columnName))
							return cellNum;
					}
				}
			}
			
		}catch(Exception exception){
			throw new Exception("Failed get Column number" + exception.getMessage());
		}
		return 0;
	}
	
	public void launchBrowser() throws Exception {
		ExtentTest Logintest = null;

		try {
			Logintest= extent.createTest("Browser test");
			BrowserDriver.setBrowserDriver(pro.getProperty("browser"), null);
			commonUI.takeScreenShot("Launch Browser" + currentdateandtime);
			Logintest.pass("Sucessfully Launch Browser  Application", MediaEntityBuilder.createScreenCaptureFromPath(BasePath.mainpath +"ScreenShot\\"+ tday + "\\" + "Launch Browser" + currentdateandtime + ".png").build());
																													
		} catch (Exception e) {
			// this.sendFailedMail("Hi Team,"+'\n'+ "Unable to Launch Browser due to Driver
			// issue"+'\n'+""+'\n'+"Regards,"+'\n'+"Automation Team","");
			commonUI.takeScreenShot("Launch Browser" + currentdateandtime);
			Logintest.fail("Sucessfully Launch Browser  Application", MediaEntityBuilder.createScreenCaptureFromPath(BasePath.mainpath +"ScreenShot\\"+ tday + "\\" + "Launch Browser" + currentdateandtime + ".png").build());
			throw new Exception("Unable to Launch Browser " + e.getMessage());

		}
	}

	public void openApplication() throws Exception {
		ExtentTest Openapplication = null;
		try {
			
			Assert.assertTrue(true);
			
			Openapplication= extent.createTest("Open Application Test");
  			BrowserDriver.getBrowserDriver().get("https://www.makemytrip.com/");
			System.out.println(pro.getProperty("url"));
			commonUI.takeScreenShot("Open_Url" + currentdateandtime);
			Openapplication.pass("Sucessfully open Application", MediaEntityBuilder.createScreenCaptureFromPath(BasePath.mainpath +"ScreenShot\\"+ tday + "\\" + "Open_Url" + currentdateandtime +".png").build());
			//extentTest.log(Status.PASS, "Open MakeMyTrip Application");
			
		} catch (Exception e) {
		
			
			commonUI.takeScreenShot("Open_Url" + new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date()));
			Openapplication.fail("Failed to open Application", MediaEntityBuilder.createScreenCaptureFromPath(BasePath.mainpath +"ScreenShot\\"+ tday + "\\" + "Open_Url" + currentdateandtime +".png").build());

			throw new Exception("Unable to open navigate to url " + e.getMessage());
		}

	}

	public void loginToApp() throws Exception {
		ExtentTest Logintoapp = null;
		try {
			Logintoapp= extent.createTest("Login Application Test");
			System.out.println(pro.getProperty("username"));
			System.out.println(pro.getProperty("password"));
			
			commonUI.takeScreenShot("Login Application" + currentdateandtime);
			for (int i=1;i<=5;i++)
			{
				Logintoapp.pass("Login Application sucessfully", MediaEntityBuilder.createScreenCaptureFromPath(BasePath.mainpath +"ScreenShot\\"+ tday + "\\" + "Login Application" + currentdateandtime +".png").build());

			}
			Logintoapp.pass("Login Application sucessfully", MediaEntityBuilder.createScreenCaptureFromPath(BasePath.mainpath +"ScreenShot\\"+ tday + "\\" + "Login Application" + currentdateandtime +".png").build());
		} catch (Exception exception) {
			
			commonUI.takeScreenShot("Login Application" + new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date()));
			Logintoapp.fail("Failed to Login application", MediaEntityBuilder.createScreenCaptureFromPath(BasePath.mainpath +"ScreenShot\\"+ tday + "\\" + "Login Application" + currentdateandtime +".png").build());
			throw new Exception("User is not logged in " + exception.getMessage());
			/*
			 * this.sendFailedMail("Hi Team," + '\n' +
			 * "Unable to login into 'Miles Application' due to Network issue Or bad login credentials enter"
			 * + '\n' + "" + '\n' + "Regards," + '\n' + "RPA Team", "");
			 * extentTest.log(LogStatus.FAIL, "Enter Username and Password");
			 */
			
		}
	}

	public void activity() throws Exception {
		ExtentTest activity = null;
		try {
			activity= extent.createTest("Activity");
			/*
			 * double srno; String testcaseid; String testCase; String testdata; String
			 * testresult; String data; for(int rowNum = 1 ; rowNum
			 * <Testcaseattachment.getPhysicalNumberOfRows(); rowNum++) {
			 * //this.Testcaseattachment.getRow(rowNum).createCell().setCellValue("");
			 * 
			 * srno=this.Testcaseattachment.getRow(rowNum).getCell(this.srno).
			 * getNumericCellValue();
			 * testcaseid=this.Testcaseattachment.getRow(rowNum).getCell(this.testcaseid).
			 * getStringCellValue();
			 * testCase=this.Testcaseattachment.getRow(rowNum).getCell(this.testCase).
			 * getStringCellValue();
			 * testdata=this.Testcaseattachment.getRow(rowNum).getCell(this.testdata).
			 * getStringCellValue();
			 * //testresult=this.Testcaseattachment.getRow(rowNum).getCell(this.testresult).
			 * getStringCellValue();
			 * this.Testcaseattachment.getRow(rowNum).createCell(this.testresult).
			 * setCellValue(""); System.out.println(srno + " " + testcaseid + " " + testCase
			 * + " " + testdata); }
			 */
			 
			
			
			commonUI.clickOnButton("train", false);
			//commonUI.enterTextIntoTextBox("fromcity", "Mumbai");
			//commonUI.selectItemFromDropDownBox(selectBoxID, option);
			commonUI.takeScreenShot("Activity" + currentdateandtime);
			activity.pass("Activity sucess", MediaEntityBuilder.createScreenCaptureFromPath(BasePath.mainpath +"ScreenShot\\"+ tday + "\\" + "Activity" + currentdateandtime +".png").build());
			
			
		} catch (Exception e) {
			
			commonUI.takeScreenShot("Activity" + currentdateandtime);
			activity.fail("Activity Failed", MediaEntityBuilder.createScreenCaptureFromPath(BasePath.mainpath +"ScreenShot\\"+ tday + "\\" + "Activity" + currentdateandtime +".png").build());
			
			throw new Exception("Enter the data:" + e.getMessage());

		}

	}

	
	public void logOut() throws Exception {
		ExtentTest Logouttest = null;
		try {
			Logouttest= extent.createTest("Logout");
			commonUI.takeScreenShot("Logout" + currentdateandtime);
			Logouttest.pass("Logout application", MediaEntityBuilder.createScreenCaptureFromPath(BasePath.mainpath +"ScreenShot\\"+ tday + "\\" + "Logout" + currentdateandtime +".png").build());
			//ProPage.logout();
			
		} catch (Exception exception) {
			
			commonUI.takeScreenShot("Logout" + currentdateandtime);
			Logouttest.pass("Failed Logout application", MediaEntityBuilder.createScreenCaptureFromPath(BasePath.mainpath +"ScreenShot\\"+ tday + "\\" + "Logout" + currentdateandtime +".png").build());
			throw new Exception("Failed to log out" + exception.getMessage());
			/*this.sendFailedMail("Hi Team," + '\n'
					+ "Unable to logout from ' Application' due to Network issue Or  System Down" + '\n' + ""
					+ '\n' + "Regards," + '\n' + "RPA Team", "");*/
			

			
		}
	}

	@AfterMethod
	public void tearDown() {
		try {
			if (wb != null)
				wb.close();
			endTime = commonUI.getCurrentTime();

			//extent.endTest(extentTest);
			//extent.close();
			extent.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//GenHtmlReport();
			BrowserDriver.getBrowserDriver().quit();
			System.out.println("Process Run successfully");
		}
	}

	public void GenHtmlReport() {
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(this.htmlreportName));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
		}
		String content = contentBuilder.toString();
		String replacedStr = content.replaceAll("https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.2/css/",
				"../resources/report/css/");
		String replacedStr1 = replacedStr.replaceAll(
				"https://cdn.rawgit.com/noelboss/featherlight/1.3.4/release/featherlight.min.css",
				"../resources/report/css/featherlight.min.css");
		String replacedStr2 = replacedStr1.replaceAll(
				"http://cdn.rawgit.com/anshooarora/extentreports/005d99e2f2716f6d749c77c65b57ca3c632c35a8/cdn/extent.css",
				"../resources/report/css/extent.css");
		String replacedStr3 = replacedStr2.replaceAll("https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/",
				"../resources/report/js/");
		String replacedStr4 = replacedStr3.replaceAll("https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.2/js/",
				"../resources/report/js/");
		String replacedStr5 = replacedStr4.replaceAll("https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/",
				"../resources/report/js/");
		String replacedStr6 = replacedStr5.replaceAll(
				"https://cdn.rawgit.com/noelboss/featherlight/1.3.4/release/featherlight.min.js",
				"../resources/report/js/featherlight.min.js");
		String replacedStr7 = replacedStr6.replaceAll(
				"http://cdn.rawgit.com/anshooarora/extentreports/005d99e2f2716f6d749c77c65b57ca3c632c35a8/cdn/",
				"../resources/report/js/");
		FileWriter fWriter = null;
		BufferedWriter writer = null;
		try {
			fWriter = new FileWriter(this.htmlreportName);
			writer = new BufferedWriter(fWriter);
			writer.write(replacedStr7);
			writer.close(); // make sure you close the writer object
		} catch (Exception e) {
			// catch any exceptions here
		}
	}

	public String getCellValue(XSSFCell cell) throws Exception {
		try {
			switch (cell.getCellTypeEnum()) {
			case NUMERIC:
				Double value = cell.getNumericCellValue();
				return String.valueOf(value.intValue());
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case STRING:
				return cell.getStringCellValue();
			case BLANK:
				return null;
			default:
				return cell.getStringCellValue();
			}
		} catch (Exception e) {
			throw new Exception("Failed to retrieve cell value " + e.getMessage());
		}
	}
 
	//

	
	public void sendFailedMail(String failMessage, String eodFilePath) {
		try {
			MailUtil mail = new MailUtil();
			String tomail = testData.get("EmailID");
			String cC = testData.get("CcMail ID");
			String subject = "EOD Process Status";
			String file = eodFilePath;
			String bcc = "";

			String message = failMessage;
			System.out.println(tomail + "   " + subject + "    " + message);
			mail.sendEmailWithAtttachment(tomail, cC, bcc, subject, message, file);
		} catch (Exception e) {
			System.out.println("Unable To Send Mail:" + e.getMessage());
		}

	}

	public void createFolder(String Path)
	{
		File theDir = new File(Path);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    System.out.println("creating directory: " + theDir.getName());
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		      
		    }        
		    if(result) {    
		        System.out.println("DIR created");  
		    }
		}
	}


	public void makingExcelsheet() throws Exception 
	{
		try
		{
		Process p= Runtime.getRuntime().exec("wscript.exe " + "D:\\Automation\\ProcessName\\macro\\macrovbsfile.vbs");
		p.waitFor();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}
