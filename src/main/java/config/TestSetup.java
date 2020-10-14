package config;

import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.AfterClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TestSetup {

	public static long timeOut =30, driverWait, emailTimeOut;	
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static JavascriptExecutor js;
	public static final String systemDir = System.getProperty("user.dir");
	public static String ClassName;
	public String reportName, excelReport, testName ;

	public static String ResultFolderPath;
	public static String ScreenShot_ResultFolderPath;
	public String currentDateAndTime;

	public static ExtentTest test;
	public static ExtentReports extent;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentHtmlReporter htmlReporter_Co;
	public static ExtentReports extent_Co;
	public static ExtentTest test_Co;
	public static Properties prop;
	public static final String DOWNLOAD_FOLDER_PATH = "C:\\Working\\Download";

	public static String ApplicationURL;




	//************************************************************************************************************************************//
	/**
	 * To Open Browser based on Parameter Value.
	 * @param browser: Pass value on which want to open browser (Like - IE, Chrome, Firefox, etc)
	 * @return -- driver
	 * @author Anshul Sharda	  
	 */
	//************************************************************************************************************************************//
	public static WebDriver OpenBrowser(String browser) {
		//if (testCategory.equalsIgnoreCase("web")) {
		String platform = System.getProperty("os.name");
		switch (browser.toLowerCase()) {	
		case "chrome":	
			System.setProperty("webdriver.chrome.driver", systemDir + "\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			break;

		case "firefox":		
			if (platform.contains("Mac")) {
				System.setProperty("webdriver.chrome.driver", "Drivers/" + "chromedriver");
			} else {
				System.setProperty("webdriver.chrome.driver", "Drivers/" + "chromedriver.exe");
			}
			driver = new FirefoxDriver();
			break;


		case "ie":	
			System.setProperty("webdriver.ie.driver", systemDir + "\\Drivers\\IEDriverServer.exe");
			DesiredCapabilities capabilities  =  DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capabilities.setCapability("nativeEvents", true);
			capabilities.setCapability("unexpectedAlertBehaviour", "accept");
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			capabilities.setCapability("disable-popup-blocking", true);
			capabilities.setCapability("enablePersistentHover", true);
			capabilities.setCapability("ignoreZoomSetting", true);
			capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setJavascriptEnabled(true);
			driver = new InternetExplorerDriver(capabilities);
			break;


		case "microsoft edge":
			System.setProperty("webdriver.edge.driver", "Drivers/" + "MicrosoftWebDriver.exe");
			capabilities = new DesiredCapabilities("MicrosoftEdge", "", Platform.WINDOWS);
			capabilities.setJavascriptEnabled(true);
			driver = new EdgeDriver(capabilities);
			break;

		default:
			Reports.FailTest("Browser " +  "---" + browser + " not configured.");
		}
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, timeOut);
		js = ((JavascriptExecutor) driver);
		return driver;
	}



	//************************************************************************************************************************************//
	/**
	 * Before Suite.
	 * @author Anshul Sharda		   
	 */
	//************************************************************************************************************************************//
	@BeforeSuite
	public void beforeSuite() {	

		ResultFolderPath = systemDir + "\\ExecutionReports\\" + TechnicalComponents.getCurrentYear() + "\\";
		Utilities.CreateFolder(ResultFolderPath);		
		ResultFolderPath += TechnicalComponents.getCurrentMonth() + "\\";
		Utilities.CreateFolder(ResultFolderPath);		
		ResultFolderPath += TechnicalComponents.getCurrentdate_MMDDYYYY() + "\\";
		Utilities.CreateFolder(ResultFolderPath);				
		ScreenShot_ResultFolderPath = ResultFolderPath + "\\ScreenShots";		
		Utilities.CreateFolder(ScreenShot_ResultFolderPath);		
		
	}



	//************************************************************************************************************************************//
	/**
	 * Before Test.	
	 * @author Anshul Sharda	   
	 */
	//************************************************************************************************************************************//
	@BeforeTest
	public void beforeClass(ITestContext TCName) {	
		System.out.println("Before Test");
		htmlReporter = Reports.StartHtmlIndivdualTCReport(htmlReporter, Utilities.getProperty("BrowserName"),ResultFolderPath, TCName.getName());
		extent = Reports.StartExtentReport(htmlReporter, extent);
	}



	//************************************************************************************************************************************//
	/**
	 * Before Class.	
	 * @author Anshul Sharda	   
	 */
	//************************************************************************************************************************************//
	@BeforeClass
	public void BeforeClass(ITestContext TCName) {
		System.out.println("Befoe Class");
		String[] className = this.getClass().getName().split("\\.");			
		ClassName = className[1];	
		//Util.CloseAllBrowserProcess();
		test = Reports.testCreate(extent,ClassName);

	}




	//************************************************************************************************************************************//
	/**
	 * After Class.	
	 * @author Anshul Sharda	   
	 * 
	 * If not working report not generating properly as per expected remove code after  >>	Reports.endReport(extent);	
	 */ 
	//************************************************************************************************************************************//

	@AfterClass
	public void AfterClass() {
		Reports.endReport(extent);	

	}











}