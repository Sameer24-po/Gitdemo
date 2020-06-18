package wipro.automation.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import wipro.automation.processes.MainProcess;

import wipro.automation.utilities.BasePath;
import wipro.automation.utilities.BrowserDriver;
import wipro.automation.utilities.CommonUI;
import wipro.automation.utilities.ElementController;


public class ProcessPage {
	private static String processName = MainProcess.class.getSimpleName();
	ElementController elementController = null;
	CommonUI commonUI = null;
	private String currentWindow = null;
	public String collateralDate =null;// make it null after use of hard code date
	private String milePageHandle;
	public String approveFileTime = null;
	public String createdRecdFilePath=null;
	private String htmlreportName;
	//ExtentReports extent;
	//ExtentTest extentTest;

	
	public ProcessPage() throws Exception {
		elementController = new ElementController(ProcessPage.class.getSimpleName());
		commonUI = new CommonUI(ProcessPage.class.getSimpleName());
		
		//htmlreportName = BasePath.rootDir + "\\" + processName + "_" + ProcessSteps.userid + ".html";
		//extent = new ExtentReports(htmlreportName, true);
		//extentTest = extent.startTest(processName);

	}

	public void navigateToURL(String url) throws Exception {
		try {
			BrowserDriver.getBrowserDriver().get(url);
		} catch (Exception exception) {
			throw new Exception("Unable to navigate to URL '" + url + "'. [goToURL()]: " + exception.getMessage());
		}
	}

	public void waitForLoginPageToLoad() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(BrowserDriver.getBrowserDriver(), 10);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath(elementController.elementParams.get("LoginBtn").get("xpath"))));
		} catch (Exception exception) {
			throw new Exception("Unable to wait for page to load " + exception.getMessage());
		}
	}

	public void waitForPageToLoad() throws Exception {
		try {
			switchToMainFrame();
			switchToMainFrame();
			WebDriverWait wait = new WebDriverWait(BrowserDriver.getBrowserDriver(), 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementController.elementParams.get("LogoutBtn").get("xpath"))));
		} catch (Exception exception) {
			throw new Exception("Unable to wait for page to load " + exception.getMessage());
		}
	}
	
	public void waitForMilesBackndPageToLoad() throws Exception {
		try {
			switchToMainFrame();
			switchToMainFrame();
			WebDriverWait wait = new WebDriverWait(BrowserDriver.getBrowserDriver(), 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath(elementController.elementParams.get("EodRepoWait").get("xpath"))));
		} catch (Exception exception) {
			throw new Exception("Unable to wait for reports page to load " + exception.getMessage());
		}
	}

	public void enterLoginCredentials(String loginID, String password) throws Exception {
		try {
			BrowserDriver.getBrowserDriver().switchTo().defaultContent();
			switchToMainFrame();
			commonUI.enterTextIntoTextBox("UserNameTxtBox", loginID);
			commonUI.enterTextIntoTextBox("PasswordTxtBox", password);
		} catch (Exception exception) {
			throw new Exception("Falied to enter login credentials" + exception.getMessage());
		}
	}

	public void clickOnLoginButton() throws Exception {
		try {
			commonUI.clickOnButton("LoginBtn", false);
			BrowserDriver.getBrowserDriver().switchTo().defaultContent();
			currentWindow = BrowserDriver.getBrowserDriver().getWindowHandle();

		} catch (Exception exception) {
			throw new Exception("Failed to click on Login button " + exception.getMessage());
		}
	}

	public void selectMenuOption(String menu, String subMenu) throws Exception {
		try {
			BrowserDriver.getBrowserDriver().switchTo().defaultContent();
			Thread.sleep(2000);
//			this.switchToMainFrame();
			this.switchToMainFrame();
			commonUI.clickOnButton(menu, false);
			elementController.implicitWait(5);
			commonUI.clickOnButtonUsingJS(subMenu, false);
			BrowserDriver.getBrowserDriver().switchTo().defaultContent();
		} catch (Exception exception) {
			throw new Exception("Unable to select menu option:" + exception.getMessage());
		}
	}

	public void logout() throws Exception {
		try {
			BrowserDriver.getBrowserDriver().switchTo().defaultContent();
			this.switchToMainFrame();
			this.switchToMainFrame();
			commonUI.clickOnButtonUsingJS("LogoutBtn", false);
			elementController.implicitWait(1);
		} catch (Exception exception) {
			throw new Exception("Failed to Log out" + exception.getMessage());
		}
	}

	public void uploadFile(String filePath) throws Exception {
		try {
			 elementController.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false, "ChooseFileBtn").sendKeys(filePath);
			Thread.sleep(1000);
			commonUI.clickOnButton("UploadFileBtn", false);
			
			
		} catch (InterruptedException exception) {
			throw new Exception("Failed to uploadFile" + exception.getMessage());
		} catch (AWTException exception) {
			throw new Exception("Failed to uploadFile" + exception.getMessage());
		} catch (Exception exception) {
			throw new Exception("Failed to uploadFile" + exception.getMessage());
		}
	}

	public void saveFile() throws Exception {
		try {
			if (this.milePageHandle != null)
				BrowserDriver.getBrowserDriver().switchTo().window(milePageHandle);
			Robot robot = new Robot();
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_F11);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_F11);
			Thread.sleep(2000);
		} catch (InterruptedException exception) {
			throw new Exception("Failed to saveFile" + exception.getMessage());
		} catch (AWTException exception) {
			throw new Exception("Failed to saveFile" + exception.getMessage());
		} catch (Exception exception) {
			throw new Exception("Failed to saveFile" + exception.getMessage());
		}

	}

	public boolean isLoginPageDisplayed() {
		this.switchToMainFrame();
		if (BrowserDriver.getBrowserDriver().getPageSource().contains("Login Here"))
			return true;
		return false;
	}

	public boolean isUserLoggedIn() {
		this.switchToMainFrame();
		if (BrowserDriver.getBrowserDriver().getPageSource().contains("Logout"))
			return true;
		return false;
	}

	public void waitForElementToBeAvailable(String elementName) throws Exception {
		try {
			BrowserDriver.getBrowserDriver().switchTo().defaultContent();
			String elementXpath = elementController.elementParams.get(elementName).get("xpath");
			this.switchToMainFrame();
			this.switchToMainFrame();
			elementController.implicitWait(10);
			WebDriverWait wait = new WebDriverWait(BrowserDriver.getBrowserDriver(), 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementXpath)));
		} catch (Exception exception) {
			throw new Exception("Unable to wait for page to load " + exception.getMessage());
		}
	}

	public void switchToMainFrame() {
		BrowserDriver.getBrowserDriver().switchTo().frame("MainFrame");
	}

	public void clickOnListButton() throws Exception {
		try {
			commonUI.clickOnButton("ClientSubMenuListBtn", false);
		} catch (Exception e) {
			throw new Exception("Unable to click on list button: " + e.getMessage());
		}

	}

	public void downLoadFile(String filePath) throws Exception {
		try {
			Robot robot = new Robot();
			robot.mouseMove(203, 637);
			Thread.sleep(2000);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(2000);
			StringSelection selection = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
		} catch (InterruptedException exception) {
			throw new Exception("Failed to uploadFile" + exception.getMessage());
		} catch (AWTException exception) {
			throw new Exception("Failed to uploadFile" + exception.getMessage());
		} catch (Exception exception) {
			throw new Exception("Failed to uploadFile" + exception.getMessage());
		}
	}

	public void clickOnExportButton() throws Exception {
		commonUI.clickOnButton("ClientListExportBtn", false);
		elementController.implicitWait(7);
	}

	public void selectProductType() throws Exception {
		try {
			commonUI.selectItemFromDropDownBox("ProductType", "LAS");
			elementController.implicitWait(5);
		} catch (Exception e) {
			throw new Exception("Unable to select product type " + e.getMessage());
		}
	}

	public void switchToMilesPageWindow() throws Exception {
		try {
			if (this.currentWindow != null)
				elementController.implicitWait(3);
			BrowserDriver.getBrowserDriver().switchTo().window(this.currentWindow);
		} catch (Exception ex) {
			throw new Exception("Unable to switch to main window " + ex.getMessage());
		}
	}

	public void selectClientID() throws Exception {
		try {
			commonUI.clickOnButton("ClientIDRadioBtn_BackOfficeCode", false);
		} catch (Exception exception) {
			throw new Exception("Unable to select client ID" + exception.getMessage());
		}

	}

	public void Type() throws Exception {
		try {
			commonUI.selectItemFromDropDownBox("TypeDropDown", "Release Pledge (NonPOA)");
			elementController.implicitWait(5);
		} catch (Exception exception) {
			throw new Exception("Unable to select type " + exception.getMessage());
		}
	}

	public void pageRefresh() throws Exception {

		try {
			BrowserDriver.getBrowserDriver().navigate().refresh();
		} catch (Exception e) {
			throw new Exception("Unable to refresh page " + e.getMessage());
		}

	}

	

	private void createExcelFileToSendMail(String Tomail,String filename,String receiverName,String attchPath) throws FileNotFoundException{
		
		XSSFWorkbook sendMailWB = new XSSFWorkbook();
		 XSSFSheet sheet = sendMailWB.createSheet();
		Row mail = sheet.createRow(1);
		mail.createCell(6).setCellValue(Tomail);
		mail.createCell(8).setCellValue("ShortFall Issue");
		mail.createCell(9).setCellValue(filename+".csv");
		mail.createCell(10).setCellValue(receiverName);
		mail.createCell(11).setCellValue("pawarsameer24@gmail.com");
		mail.createCell(13).setCellValue(attchPath);
		
		try (FileOutputStream outputStream = new FileOutputStream(BasePath.mainpath+"\\sendMail.xlsm")) {
			sendMailWB.write(outputStream);
			sendMailWB.close();
		} catch (IOException e) {
		
			System.out.println("Unable to create send mail file"+e.getMessage());
		}
		
		
	}
	/*
	 * public void checkForAlert() { if (isAlertPresents()) {
	 * BrowserDriver.getBrowserDriver().switchTo().alert().accept();
	 * BrowserDriver.getBrowserDriver().switchTo().defaultContent(); }
	 * 
	 * }// catch
	 */
	public void checkForAlert(String loginPageWindowHandle) {
		try {
			try {

				elementController.implicitWait(2);
				// BrowserDriver.getBrowserDriver().switchTo().window(loginPageWindowHandle);
				Alert alert = BrowserDriver.getBrowserDriver().switchTo().alert();
				alert.accept();
			} catch (NoAlertPresentException Ex) {
				System.out.println("Alert not present");
			} catch (UnhandledAlertException ex) {
				System.out.println("Test :" + ex.getMessage());
			}
		} catch (Exception exception) {
			System.out.println("Test " + exception.getMessage());
		}

	}

	public void getDate() throws Exception {
		try {
			elementController.implicitWait(2);
			// String date =
			// BrowserDriver.getBrowserDriver().findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_Uc1Date_txt_Date']")).click()
			WebElement TxtBoxContent = BrowserDriver.getBrowserDriver()
					.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_Uc1Date_txt_Date']"));
			collateralDate = TxtBoxContent.getAttribute("value");
			System.out.println(collateralDate);

		} catch (Exception e) {
			throw new Exception("Unable to get date" + e.getMessage());
		}

	}

	public void setFromDate() {
		try {
			String testDate = "12/06/2018";
			((JavascriptExecutor) BrowserDriver.getBrowserDriver()).executeScript(
					"document.getElementById('ctl00_ContentPlaceHolder1_txtFromDate_txt_Date').setAttribute('value',\'"
							+ testDate + "\')");
		} catch (Exception e) {

			System.out.println(e);
		}

	}

	public void withdrawalApprovalPrductType() throws Exception {

		try {
			commonUI.selectItemFromDropDownBox("ApproavProductType1", "LAS");
			elementController.implicitWait(5);
		} catch (Exception e) {
			throw new Exception("Unable to select withdrawal approval 1 product type " + e.getMessage());
		}
	}

	public void withdrawalApproval1() throws Exception {
		try {

			selectMenuOption("CollateralMenu", "WithdrawalApproval1");
			waitForElementToBeAvailable("ListRecordButton");
			setFromDate();
			commonUI.selectItemFromDropDownBox("Status", "Pending");
			withdrawalApprovalPrductType();
			clickOnListButton();
			waitForElementToBeAvailable("SelectAllApproval1");
			try {
				if (elementController.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false, "SingleCheckBox")
						.isDisplayed() == true) {
					clickOnSelectAll();
					commonUI.clickOnButton("approveButton", false);
					elementController.implicitWait(180);
					String element = elementController
							.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false, "approveMessage")
							.getText();
					System.out.println("Withdrawal Approval 1=" + element);
				}
			} catch (Exception e) {

				System.out.println(e.getMessage());
			}

		} catch (Exception e) {

			throw new Exception("Unable to complete withdrawal approval " + e.getMessage());
		}

	}

	/*private void waitForVisibleElement(String elementName) throws Exception {
		try {
			BrowserDriver.getBrowserDriver().switchTo().defaultContent();
			String elementXpath = elementController.elementParams.get(elementName).get("xpath");
			this.switchToMainFrame();
			this.switchToMainFrame();
			elementController.implicitWait(15);
			WebDriverWait wait = new WebDriverWait(BrowserDriver.getBrowserDriver(), 20);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXpath)));

		} catch (Exception exception) {
			throw new Exception("Unable to wait for page to load " + exception.getMessage());
		}

	}*/

	private void clickOnSelectAll() throws Exception {
		try {
			// findByElement("").sendKeys(Keys.SPACE);
			this.waitForElementDisplay("SelectAllApproval1");
			commonUI.clickOnButton("SelectAllApproval1", false);

			Thread.sleep(1);

		} catch (Exception e) {
			throw new Exception("Unable to click on select all checkbox button: " + e.getMessage());
		}

	}

	public void withdrawalApprova2() throws Exception {
		try {
			elementController.implicitWait(5);
			selectMenuOption("CollateralMenu", "WithdrawalApproval2");
			waitForElementToBeAvailable("ListRecordButton");
			setFromDate();
			commonUI.selectItemFromDropDownBox("Status", "Pending");
			withdrawalApprovalPrductType();
			clickOnListButton();
			waitForElementToBeAvailable("SelectAllApproval1");
			try {
				if (elementController.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false, "SingleCheckBox")
						.isDisplayed() == true) {
					clickOnSelectAll();
					commonUI.clickOnButton("approveButton", false);
					elementController.implicitWait(120);
					String element = elementController
							.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false, "approveMessage")
							.getText();
					System.out.println("Withdrawal Approval 2=" + element);
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());

			}
		} catch (Exception e) {

			throw new Exception("Unable to complete withdrawal approval " + e.getMessage());
		}

	}

	public void withdrawalApproval3() throws Exception {
		try {
			elementController.implicitWait(5);
			selectMenuOption("CollateralMenu", "WithdrawalApproval3");
			waitForElementToBeAvailable("ListRecordButton");
			setFromDate();
			commonUI.selectItemFromDropDownBox("Status", "Pending");
			withdrawalApprovalPrductType();
			clickOnListButton();
			waitForElementToBeAvailable("SelectAllApproval1");
			try {
				if (elementController.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false, "SingleCheckBox")
						.isDisplayed() == true) {
					clickOnSelectAll();
					commonUI.clickOnButton("approveButton", false);
					elementController.implicitWait(120);
					String element = elementController
							.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false, "approveMessage")
							.getText();
					System.out.println("Withdrawal Approval 3=" + element);
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());

			}
		} catch (Exception e) {

			throw new Exception("Unable to complete withdrawal approval " + e.getMessage());
		}

	}

	public void withdrawalApproval4() throws Exception {
		try {
			elementController.implicitWait(5);
			selectMenuOption("CollateralMenu", "WithdrawalApproval4");
			/*
			 * waitForElementToBeAvailable("ListRecordButton"); setFromDate();
			 * commonUI.selectItemFromDropDownBox("Status", "Pending");
			 * withdrawalApprovalPrductType(); clickOnListButton();
			 * waitForElementToBeAvailable("SelectAllApproval1"); try { if
			 * (elementController.findByElement(BrowserDriver.getBrowserDriver()
			 * ,null, false, "SingleCheckBox").isDisplayed()==true) {
			 * clickOnSelectAll(); commonUI.clickOnButton("approveButton",
			 * false); elementController.implicitWait(120); String element=
			 * elementController.waitForWebElement(BrowserDriver.
			 * getBrowserDriver(),null, false, "approveMessage").getText();
			 * System.out.println("Withdrawal Approval 4="+element); }
			 * 
			 * } catch (Exception e) { System.out.println(e.getMessage());
			 * 
			 * }
			 */
		} catch (Exception e) {

			throw new Exception("Unable to complete withdrawal approval " + e.getMessage());
		}

	}

	public void finalWithdrawalApproval() throws Exception {
		// TODO
		try {
			elementController.implicitWait(5);
			selectMenuOption("CollateralMenu", "FinalWithdrawalApproval");
			waitForElementToBeAvailable("ListButton");
			try {
				String testDate = collateralDate;
				((JavascriptExecutor) BrowserDriver.getBrowserDriver()).executeScript(
						"document.getElementById('ctl00_ContentPlaceHolder1_Uc1FromDate_txt_Date').setAttribute('value',\'"
								+ testDate + "\')");
			} catch (Exception e) {

				System.out.println(e);
			}
			commonUI.selectItemFromDropDownBox("Status", "Approved");
			withdrawalApprovalPrductType();
			commonUI.clickOnButton("ListButton", false);
			waitForElementToBeAvailable("SelectAllApproval1");

			WebElement table_element = elementController.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false,"tableRowCount");
			List<WebElement> tr_collection = table_element.findElements(By.xpath("id('ctl00_ContentPlaceHolder1_DG')/tbody/tr"));
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + tr_collection.size());
			Set<String> set = new HashSet<String>();

			String fileName = "D:\\agilemate\\workspace\\autotest\\src\\TransactionalDocs\\Unpledge_FileFormat.csv";
			String line = "";
			String cvsSplitBy = ",";

			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

				while ((line = br.readLine()) != null) {

					// use comma as separator
					String[] country = line.split(cvsSplitBy);

					set.add(country[0]);

				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			List<String> list = new ArrayList<>();
			list.addAll(set);

			try {
				if (elementController.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false, "SingleCheckBox").isDisplayed() == true) {
					for (int i = 0; i < list.size(); i++) {
						System.out.println(i);
						if (list.get(i) != "Client_Code") {
							int count = 0;
							for (int j = 0; j < (tr_collection.size() - 2); j++) {
								String inc;
								int a = j + 3;
								if (a < 10) {
									inc = "0" + a;
								} else {
									inc = Integer.toString(a);
								}
								String accNoXpath = elementController.elementParams.get("finalApprovalclientID")
										.get("xpath").replace("#", inc);
								String clientCode = elementController
										.waitForWebElement(BrowserDriver.getBrowserDriver(), null, true, accNoXpath)
										.getText();
								System.out.println(list.get(i));
								if (clientCode.equals(list.get(i))) 
								{
									count++;
									String chkBoxXpath = elementController.elementParams.get("finalChkBox").get("xpath").replace("#", inc);
									elementController.waitForWebElement(BrowserDriver.getBrowserDriver(), null, true,chkBoxXpath).click();
								}
							}
							elementController.implicitWait(3);
							if (count > 0) {
								commonUI.clickOnButton("finalApproveButton", false);
								elementController.implicitWait(60);
								if (elementController.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false,
										"shortFallButtonConfirm").isDisplayed() == true) {
									commonUI.clickOnButton("shortFallButtonConfirm", false);
								}
								elementController.implicitWait(60);
								String element = elementController.waitForWebElement(BrowserDriver.getBrowserDriver(),
										null, false, "finalApproveMsg").getText();
								System.out.println("Final Withdrawal Approval=" + element);
							}

						}
						System.out.println(list.get(i));
					}

					elementController.implicitWait(120);
					String element = elementController
							.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false, "approveMessage")
							.getText();
					System.out.println("Final Withdrawal Approval=" + element);
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());

			}
		} catch (Exception e) {

			throw new Exception("Unable to complete withdrawal approval " + e.getMessage());
		}

	}

	
	private boolean isElemementDislay(String elementID) {
		try {
	
			WebDriverWait wait = new WebDriverWait(BrowserDriver.getBrowserDriver(),5);
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementController.elementParams.get(elementID).get("xpath"))));
			} catch (Exception e) {
				System.out.println("Element not present on webpage: "+e.getMessage());
			}
	    
			String elementXpath= elementController.elementParams.get(elementID).get("xpath");
			boolean exists = BrowserDriver.getBrowserDriver().findElements(By.xpath(elementXpath)).size()!=0;
			if (exists){
				return true;
			}
			
		} catch (NoSuchElementException e) {
		
	System.out.println("Unable to wait for element :"+e.getMessage());
		}
		return false;
		
	} 

	
	private CookieStore seleniumCookiesToCookieStore() {
		Set<Cookie> seleniumCookies = BrowserDriver.getBrowserDriver().manage().getCookies();
	    CookieStore cookieStore = new BasicCookieStore();

	    for(Cookie seleniumCookie : seleniumCookies){
	        BasicClientCookie basicClientCookie =
	            new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
	        basicClientCookie.setDomain(seleniumCookie.getDomain());
	        basicClientCookie.setExpiryDate(seleniumCookie.getExpiry());
	        basicClientCookie.setPath(seleniumCookie.getPath());
	        cookieStore.addCookie(basicClientCookie);
	    }
	    return cookieStore;
	}
	
	
	
	


	
	public void liquidationCompletion() {
		// TODO code for liquid completion

		try {
			selectMenuOption("CollateralMenu", "liquidCompletion");
			waitForElementToBeAvailable("liquidCompletionListButton");
			try {
				String testDate = collateralDate;
				((JavascriptExecutor) BrowserDriver.getBrowserDriver()).executeScript(
						"document.getElementById('ctl00_ContentPlaceHolder1_txtFromDate_txt_Date').setAttribute('value',\'"
								+ testDate + "\')");
			} catch (Exception e) {

				System.out.println(e);
			}
			commonUI.clickOnButton("liquidCompletionListButton", false);
			waitForElementToBeAvailable("liquidCompletSelctAll");

			if (elementController
					.waitForWebElement(BrowserDriver.getBrowserDriver(), null, false, "liquidCompletSingleChkBx")
					.isDisplayed() == true) {
				commonUI.clickOnButton("liquidCompletSelctAll", false);
				commonUI.clickOnButton("liquidCompletSelctAll", false);
				commonUI.clickOnButton("liquidCompletbutn", false);

			}
		} catch (Exception e) {
			System.out.println("Unable To Complete Liquidation Completion Process:"+e.getMessage());
		}

	}

	public boolean isValidationMsgNotExist() throws Exception {
		try {
			boolean Error = BrowserDriver.getBrowserDriver().getPageSource().contains("No Record Found");
			if (Error == true) {
				System.out.print("No Record Found");
				return true;
			} else {
				System.out.print("Record Found");
				return false;
			}

		} catch (NoSuchElementException e) {
			return false;

		}

	}

	public boolean isReportPageDisplayed() {
		this.switchToMainFrame();
		if (BrowserDriver.getBrowserDriver().getPageSource().contains("Client_Basic_Report"))
			return true;
		return false;
	
	}


	public void downloadReport(String downloadUrl) {
		try {
			BrowserDriver.getBrowserDriver().get(downloadUrl);
			commonUI.clickOnButton("ReportLink", false);
			commonUI.clickOnButton("ClientBasicReport", false);
			elementController.implicitWait(3);
			String FromDate = "01-01-2000";
			SimpleDateFormat todateformat = new SimpleDateFormat("dd-MM-yyyy");
			Date todate= new Date();
			String ToDate= todateformat.format(todate);
			((JavascriptExecutor) BrowserDriver.getBrowserDriver()).executeScript("document.getElementById('ctl31_ctl04_ctl03_txtValue').setAttribute('value',\'"+ FromDate + "\')");
			((JavascriptExecutor) BrowserDriver.getBrowserDriver()).executeScript("document.getElementById('ctl31_ctl04_ctl05_txtValue').setAttribute('value',\'"+ ToDate + "\')");
            commonUI.selectItemFromDropDownBox("ClientReportProdtDropdn", "LAS");
            commonUI.clickOnButton("ViewReportButn", false);
            elementController.implicitWait(60);
            commonUI.clickOnButton("ClientReportExportBtn", false);
            BrowserDriver.getBrowserDriver().findElement(By.linkText("Excel")).click();
			
			
		} catch (Exception e) {
	
			e.printStackTrace();
		}
		
	}

	private void waitForElementDisplay(String elementID) {
		try {
			WebDriverWait wait = new WebDriverWait(BrowserDriver.getBrowserDriver(), 100);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementController.elementParams.get(elementID).get("xpath"))));
		} catch (Exception e) {
		
	System.out.println("Unable to wait for element :"+e.getMessage());
		}
		
	}
}
