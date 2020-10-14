package config;

import acuityadsOR.ObjectRepo;

public class BusinessComponents extends TechnicalComponents {

	public static void navigatetoUrl(String url) {
		try {			
			if (url != "") {
				driver.get(url);
				System.out.println("Navigated to URL : " + url);
				Reports.InfoTest("Launched URL : "+url);
			}

		} catch (Exception e) {
			throw new FrameworkException(
					"Unable to navigate to URL--- " + url + "---" + e.getClass() + "---" + e.getMessage());
		}
	}
	
	 	 
	
	
	public static void gotoMenu(String btnName){
		switch (btnName.toLowerCase()) {
		case "contact us":		
			ObjectRepo or = new ObjectRepo(driver);
			or.clickonContactUs();
			break;

			
		case "schedule a demo":			
			break;
			
			
		default:
			break;
		}
	}
	
	
	public static void enteruserdetails(String fname, String lname, String email, String region){
		ObjectRepo or = new ObjectRepo(driver);
		or.enteruserdetails(fname, lname, email,region);
	}
	
	
	
	public static void clickonbutton(String btnNamae){
		ObjectRepo or = new ObjectRepo(driver);
		or.clickonButton(btnNamae);
	}
	
	
	public static void successmessage(String expmessage){
		ObjectRepo or = new ObjectRepo(driver);
		or.validatesuccessmessage(expmessage);
	}
	
	
}
