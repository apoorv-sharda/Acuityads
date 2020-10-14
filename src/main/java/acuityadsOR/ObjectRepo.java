package acuityadsOR;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import config.Reports;
import config.TechnicalComponents;

public class ObjectRepo {

	WebDriver driver;
	public static String ClientID = null;

	public ObjectRepo(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}


	@FindBy(xpath = "(//*[text()='Contact Us'])[1]")
	WebElement contactus;

	@FindBy(xpath = "(//*[@class='form-control'])[1]")
	WebElement firstname;

	@FindBy(xpath = "(//*[@class='form-control'])[2]")
	WebElement lastname;

	@FindBy(xpath = "(//*[@class='form-control'])[3]")
	WebElement emailId;

	@FindBy(xpath = "(//*[@class='options'])[1]//li")
	List<WebElement> regionDD;

	@FindBy(xpath = "(//*[@class='custom-select'])[1]")
	WebElement regionDDoption;

	@FindBy(xpath = "//*[@value='Submit']")
	WebElement btnSubmit;

	@FindBy(xpath = "//h2//span")
	WebElement successmessage;


	@FindBy(id = "loadingImage")
	WebElement loader;


	public void clickonContactUs(){
		TechnicalComponents.waitTill(contactus, "enable", "check for element - contactus");
		TechnicalComponents.click(contactus, "click on contact us");
		Reports.InfoTest("User clicks on Conatact Us button");
	}



	public void enteruserdetails(String fname, String lname, String email, String region){
		TechnicalComponents.scroll(firstname);
		TechnicalComponents.type(firstname, fname, "user enter first name");
		Reports.InfoTest("Enter First Name ---"+fname);
		TechnicalComponents.waitTill(1);
		TechnicalComponents.type(lastname, lname, "user enter last name ---"+lname);
		Reports.InfoTest("Enter Last Name --- "+lname);
		TechnicalComponents.type(emailId, email, "user enter Email ID --- "+email);
		Reports.InfoTest("Enter Email ID --- "+email);
		TechnicalComponents.click(regionDDoption, "dd option click");
		TechnicalComponents.selectvaluefromDD(regionDD, region);
		Reports.InfoTest("Select value for Region as -"+region);

	}


	public void clickonButton(String buttonname){
		TechnicalComponents.scroll(btnSubmit);
		TechnicalComponents.waitTill(btnSubmit, "enable", "");
		TechnicalComponents.click(btnSubmit, "click on submit buuton");
		Reports.InfoTest("Click on Submit buttn");
	}



	public void validatesuccessmessage(String expmessage){
		TechnicalComponents.waitTill(5);
		TechnicalComponents.scroll(successmessage);
		String act = successmessage.getText();
		if(act.equalsIgnoreCase(expmessage)){
			Reports.PassTestwithScreenshot("Request submitted successfully ---- "+expmessage,"success" );
		}
	}




}
