
package wipro.automation.utilities;


import java.awt.Robot;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ThreadGuard;

/**
 * Class contains methods to manage browser driver
 * @author Harshal.e
 *
 */
public class BrowserDriverAnnex {

	private static WebDriver driver = null;
	private static ArrayList<String> taskList = new ArrayList<String>();
	private static Robot robot = null;
	public static String browser = null;
	public static int timeOutForFindingWebElementInSeconds = 0;
	static TestDataController testDataController = null;
	static String downloadDirectoryPath = null;
	public static String hostName = null;
	
	/**
	 * Method to get browser driver
	 * @param _browser - Internet explorer
	 * @return - WebDriver type contains the instance of web driver
	 * @throws Exception 
	 */
	public static void setBrowserDriver(String _browser,String _hostName) throws Exception {
		testDataController = new TestDataController();
		browser = "chrome";
		hostName = null;
		moveMouseOutSide();
		try {	
			switch (browser.trim().toLowerCase()) {
			
				case "ie":
					killBrowserInstance();
					DesiredCapabilities dCap = DesiredCapabilities.internetExplorer();
					dCap.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
					dCap.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
					dCap.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
					dCap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
//					dCap.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
//				    dCap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				    dCap.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR,UnexpectedAlertBehaviour.IGNORE);
				    dCap.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
				    dCap.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, "-private");
				
					//dCap.setCapability("enablePersistentHover", false);
					dCap.setJavascriptEnabled(true);

					//dCap.setCapability("ignoreZoomSetting", true);
					if(hostName== null){
						System.setProperty("webdriver.ie.driver", "D:\\abflworkspace\\UnpledgeProcessPart2\\Drivers\\IEDriverServer.exe");
						driver =ThreadGuard.protect( new InternetExplorerDriver(dCap));
						driver.manage().window().setSize(new Dimension(950,650));
						driver.manage().window().setPosition(new Point(5,60));
					    String windowHandle = driver.getWindowHandle();
					    driver.switchTo().window(windowHandle);
					}else{
						System.out.println("into host name ...");
						driver = new RemoteWebDriver(new URL("http://"+hostName+":4444/wd/hub"),dCap);
					    driver.manage().window().setSize(new Dimension(950,650));
						driver.manage().window().setPosition(new Point(5,60));
					    String windowHandle = driver.getWindowHandle();
					    driver.switchTo().window(windowHandle);
					}
					break;
					
				case "chrome":
					killBrowserInstance();
					Map<String,Object> prefs = new HashMap<String,Object>();
					prefs.put("download.default_directory",downloadDirectoryPath);
					DesiredCapabilities dcap = DesiredCapabilities.chrome();
					ChromeOptions options = new ChromeOptions();
					
					options.setExperimentalOption("prefs",prefs);
					options.addArguments("--disable-extensions");
					options.addArguments("start-maximized");
					dcap.setCapability(ChromeOptions.CAPABILITY,options);
					if(hostName== null){
						System.setProperty("webdriver.chrome.driver",  BasePath.basedirpath + "\\ChromeDriver\\chromedriver.exe");
						driver = new ChromeDriver(dcap);
						driver.manage().window().setSize(new Dimension(950,650));
						driver.manage().window().setPosition(new Point(5,60));
					    String windowHandlechrome = driver.getWindowHandle();
					    driver.switchTo().window(windowHandlechrome);
					}else
						driver = new RemoteWebDriver(new URL("http://"+hostName+":4444/wd/hub"),dcap);
					    driver.manage().window().setSize(new Dimension(950,650));
						driver.manage().window().setPosition(new Point(5,60));
					    String windowHandlechrome = driver.getWindowHandle();
					    driver.switchTo().window(windowHandlechrome);
					break;
					
				case "firefox":
					//killBrowserInstance();
					FirefoxProfile profile = new FirefoxProfile();
		 			profile.setPreference("dom.successive_dialog_time_limit", 0);
		 			profile.setPreference("network.proxy.type", 0);
		 			Proxy proxy = new Proxy();
		 			proxy.setProxyType(ProxyType.DIRECT);
		 		//	driver = new RemoteWebDriver(new URL("http://"+hostName+":5555/wd/hub"));
		 			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		 			capabilities.setCapability(FirefoxProfile.PORT_PREFERENCE,profile);
		 			capabilities.setCapability(CapabilityType.PROXY,proxy);
		 			
					if(hostName== null){
						
						driver = new FirefoxDriver(profile);
						driver.manage().window().setSize(new Dimension(950,650));
						driver.manage().window().setPosition(new Point(5,60));
					    String windowHandlefirefox = driver.getWindowHandle();
					    driver.switchTo().window(windowHandlefirefox);						
					}else{
						driver = new RemoteWebDriver(new URL("http://"+hostName+":4444/wd/hub"),capabilities);
					    driver.manage().window().setSize(new Dimension(950,650));
						driver.manage().window().setPosition(new Point(5,60));
					    String windowHandlefirefox = driver.getWindowHandle();
					    driver.switchTo().window(windowHandlefirefox);
					}
					break;
					
				default:
					System.out.println("Invalid browser, Please enter valid browser");
					break;
			}
			/*if(!(_browser.equalsIgnoreCase("chrome")))
				driver.manage().window().maximize();	*/		
		}catch(Exception exception) {
			System.out.println(exception);
		}		
	}
	
	
	public static WebDriver getBrowserDriver() {
			return driver; 
	}
	
	/**
	 * killBrowserInstance() method kills the browser instance if it is running.
	 * 
	 * @param browser	The browser name
	 * 
	 * @return void
	 */
	public static void killBrowserInstance() {
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
			
			switch (browser) {
			
			case "ie":
//				if (isProcessRunning("iexplore.exe")) {
//					Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
//				}
				if (isProcessRunning("IEDriverServer.exe")) {
					Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
				}
				break;
				
			case "chrome":
				if (isProcessRunning("chromedriver.exe")) {
					Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
				}
				break;
				
			case "firefox":
				if (isProcessRunning("firefox.exe")) {
					Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
				}
				break;
				
			default:
				System.out.println("Invalid browser, Please enter valid browser");
				break;
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
	public static boolean isProcessRunning(String process) {
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).contains(process))
				return true;
		}
		return false;
	}

	/**
	 * Method to move mouse pointer out side the browser window
	 * @throws Exception
	 */
	public static void moveMouseOutSide() throws Exception {
		try{
			robot = new Robot();
			java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int width = (int) screenSize.getWidth();
			int height = (int) screenSize.getHeight();
			robot.mouseMove(height, width);
		}catch(Exception exception) {
			throw new Exception("Unable to move mouse pointer out side the window: "+exception.getMessage());
		}
	}
	
	/**
	 * Set time out for finding web element on browser in seconds
	 * @param timeOutInSeconds
	 */
	public static void setTimeOutForFindingWebElementInSeconds(int timeOutInSeconds) {
		timeOutForFindingWebElementInSeconds = timeOutInSeconds;
	}
	
	/**
	 * Get 	time out for finding web element on browser in seconds
	 * @return time out time
	 */
	public static int getTimeOutForFindingWebElementInSeconds() {
		return timeOutForFindingWebElementInSeconds;
	}

	public static void clearCacheOnIE() {
		try{
			Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
		}catch(Exception exception){
			System.err.println("Not worked");
		}
		
	}
}
