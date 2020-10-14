package config;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class Reports extends TestSetup{

	public static SoftAssert s_assert = new SoftAssert();
	static String reportName;
	static String reportName1;


	public static ExtentHtmlReporter StartHtmlConsolidatedReport(ExtentHtmlReporter htmlReporter, String browserName, String ResultFolderPath) {
		reportName	= ResultFolderPath+ browserName +"_Consolidated" + "_" + Utilities.DateTimeString() + ".html";
		htmlReporter = new ExtentHtmlReporter(reportName);		
		htmlReporter.config().setDocumentTitle("Automation Execution Report");
		htmlReporter.config().setReportName("Test Suite Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		return htmlReporter;
	}



	public static ExtentHtmlReporter StartHtmlIndivdualTCReport(ExtentHtmlReporter htmlReporter, String browserName, String ResultFolder, String TCName) {	

		reportName = ResultFolderPath+ Utilities.DateTimeString() + ".html"; 
		htmlReporter = new ExtentHtmlReporter(reportName);		
		htmlReporter.config().setDocumentTitle("Automation Execution Report");
		htmlReporter.config().setReportName("Test Execution Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		return htmlReporter;
	}



	public static void reportLink(ExtentTest test) {
		System.out.println(reportName1);
		test.info(MarkupHelper.createLabel( "  <a href='" + reportName1
				+ "' target=\"_blank\" style=\"color: rgb(255, 255, 255)\"> Test Case link : Execution Reports" +"</a>", ExtentColor.BLUE));
	}


	public static ExtentReports StartExtentReport(ExtentHtmlReporter htmlReporter, ExtentReports extent) {
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
	/*	extent.setSystemInfo("OS", System.getProperty("os.name")); 
		extent.setSystemInfo("Host Name", prop.getProperty("HostName"));
		extent.setSystemInfo("Env", prop.getProperty("Env"));*/		
		return extent;
	}

	public static ExtentTest testCreate(ExtentReports extent, String Stepdetails) {
		return extent.createTest(Stepdetails, Stepdetails);
	}


	public static void StartTest(String Stepdetails) {
		try {			
			test.info(MarkupHelper.createLabel(Stepdetails, ExtentColor.BLACK));
			System.out.println(Stepdetails);
		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
		}
	} 


	public static void PassTest(String Stepdetails) {
		try {
			test.pass(MarkupHelper.createLabel(Stepdetails, ExtentColor.GREEN));
			Assert.assertTrue(true);
		} catch (Exception e) {
			Reports.InfoTest("Error while reporting" + e.getMessage());
		}
	}


	
	//-------------Pass Test With Screenshot -------------//
		public static void PassTestwithScreenshot(String Stepdetails,String screenshotName) {
			try {		
				System.out.println("test PASS :: >>> "+Stepdetails +"   XXXXXXXXXXXXXXXXX");
				test.pass(MarkupHelper.createLabel(" <a href='" + getScreenshotPath(driver,screenshotName )
				+ "' target=\"_blank\" style=\"text-decoration: none; border-bottom: 1px solid white;color: rgb(1, 1, 1)\"> "+ Stepdetails +":" + screenshotName + "</a>", ExtentColor.GREEN));				
			} catch (Exception e) {
				System.out.println("Excecption on passtest >>" +e.getMessage());		
				Reports.FailTest("File IO Exception ");		
			}

		}
		
		



	public static void InfoTest(String Stepdetails) {
		try {
			test.info(MarkupHelper.createLabel(Stepdetails, ExtentColor.BLUE));
			System.out.println("test Info >>> "+Stepdetails +"   XXXXXXXXXXXXXXXXX");
		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
		}
	}



	//-------------Fail Test without Screenshot -------------//
		public static void FailTest(String Stepdetails) {
			try {
				test.fail(MarkupHelper.createLabel(Stepdetails, ExtentColor.RED));
				s_assert.assertTrue(false,Stepdetails);
			} catch (Exception e) {
				test.info("Following error occurred" + e.getMessage());
			}
		}




	public static void ConditionCheckTest(ExtentTest test, String actualstring, String expectedstring, String ObjDesc) {

		s_assert.assertEquals(actualstring, expectedstring);
		if (actualstring.trim().equalsIgnoreCase(expectedstring.trim())) {
			test.log(Status.PASS, MarkupHelper.createLabel(("Condition >>" + ObjDesc + "<<:: Actual value :> "
					+ actualstring + ",  Expected Value :>" + expectedstring), ExtentColor.GREEN));
		} else {

			test.log(Status.FAIL, MarkupHelper.createLabel(("Condition >> " + ObjDesc + "<<:: Actual value :> "
					+ actualstring + ", Expected Value :>" + expectedstring), ExtentColor.RED));
			//
		}
	}



	public static void endReport(ExtentReports extent) {
		extent.flush();
	}



	public static void reportLink(ExtentTest test, String SuiteName) {
		System.out.println(reportName1);
		test.info(MarkupHelper.createLabel( "  <a href='" + reportName
				+ "' target=\"_blank\" style=\"color: rgb(255, 255, 255)\"> Test Suite link : Execution Report of "+SuiteName +"</a>", ExtentColor.BLUE));
	}

	
	
	//-------------- TakeScreenshot and Return path ---------------//
		public static String getScreenshotPath(WebDriver driver,String screenshotName) {
			String relativePath= "";
			try {		
				System.out.println("Screenshot Result Folder Path >>" +ScreenShot_ResultFolderPath);		
				String ScreenshotFileName = Utilities.CaptureScreenshot(driver, screenshotName, ScreenShot_ResultFolderPath);			
				relativePath = "./ScreenShots/" + ScreenshotFileName;
				
			}catch(Exception e) {			
				System.out.println("Following error occurred in getScreenshotPath >>" + e.getMessage());
			}		
			return relativePath;
		}
	
}



