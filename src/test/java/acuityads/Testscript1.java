package acuityads;

import org.testng.Assert;
import org.testng.annotations.Test;

import config.BusinessComponents;
import config.Reports;
import config.TechnicalComponents;
import config.Utilities;

public class Testscript1 extends BusinessComponents {
	

	@Test
	public void VerifyTestScript1() {	
		try {
			Reports.StartTest( "XXX--------------Execution Starteded-------------------XXX");
			driver = OpenBrowser("chrome");
			navigatetoUrl(Utilities.getProperty("APPLICATION_URL"));
			gotoMenu("contact us");
			TechnicalComponents.waitTill(1);
			enteruserdetails("Apoorv", "Sharda", "abc@gmail.com", "Canada");
			clickonbutton("Submit");
			successmessage("Thank You");
			driver.quit();
			Reports.StartTest(  "XXX--------------Execution Completed-------------------XXX");

		}catch (Exception e) {
			System.out.println(e.getMessage());
			Reports.FailTest("TestScript1 Execution Failed >> " +e);
			Assert.fail();
			Reports.StartTest( "XXX--Execution Completed With Failed--XXX"  );
		}
	}
	

}
