package config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TechnicalComponents extends TestSetup{
	

	public static String getCurrentYear() {
		int MyYear = LocalDateTime.now().getYear(); // yyyy
		return String.valueOf(MyYear);
	}


	public static String getCurrentMonth() {
		DateFormat dateFormat = new SimpleDateFormat("MMM");
		dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
		return dateFormat.format(new Date());
	}



	public static String getCurrentdate_MMDDYYYY() {
		int MyDay = LocalDateTime.now().getDayOfMonth(); // dd
		int MyYear = LocalDateTime.now().getYear(); // yyyy
		int MyMonth = LocalDateTime.now().getMonthValue(); // yyyy
		final String CureentDtTm = (String.valueOf(MyMonth) + "_" + String.valueOf(MyDay) + "_"
				+ String.valueOf(MyYear));
		return CureentDtTm;

	}

	//************************************************************************************************************************************//
	public static void waitTill(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {

		}
	}
	
	

	// ------------ Wait till Page is Load----------------//
	public static void waitForPageToLoad() {
		try {
			String pageLoadStatus;
			System.out.print("Page Loading.");
			do {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				pageLoadStatus = (String) js.executeScript("return document.readyState");
				waitTill(1);
				System.out.print(".");
			} while (!pageLoadStatus.equals("complete"));
			System.out.println();
			System.out.println("Page Loaded.");
		} catch (Exception e) {
			e.getMessage();
		}
	}



	//************************************************************************************************************************************//
	/**
	 * Function to wait for any element to be visible, invisible or enable.
	 * 
	 * @param element
	 *            - Element to be looked for.
	 * @param state
	 *            - Expected state of Element. Expected values: "visible", "enable",
	 *            "invisible"
	 * @throws FrameworkException
	 *             - in case of error.
	 */
	//************************************************************************************************************************************//
	public static void waitTill(WebElement element, String state, String text) {
		try {
			switch (state.toLowerCase()) {
			case "visible":
				wait.until(ExpectedConditions.visibilityOf(element));
				break;
			case "enable":
				wait.until(ExpectedConditions.elementToBeClickable(element));
				break;
			case "invisible":
				wait.until(ExpectedConditions.invisibilityOf(element));
				break;				
			case "text":
				wait.until(ExpectedConditions.textToBePresentInElement(element, text));
				break;
			default:
				wait.until(ExpectedConditions.visibilityOf(element));
			}

		} catch (StaleElementReferenceException e) {
			if (timeOut > 0) {
				timeOut--;
				waitTill(element, state,text);
			} else {
				throw new FrameworkException(
						"Page refreshed while waiting for element : *  '" + element.toString() + "'");
			}
		} catch (TimeoutException e) {
			throw new FrameworkException(
					"Element : *  '" + element.toString() + "' not found within defined time limit.");
		} catch (NoSuchElementException e) {
			throw new FrameworkException("Element : *  '" + element.toString() + "' not found in DOM.");
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while waiting for element: *  '"
					+ element.toString() + "'---" + e.getClass() + "---" + e.getMessage());
		}

	}





	// ------------ Wait till Element is Visisble ----------------//
	public static void waitForVisibilityofElement(WebDriver driver, WebElement element) 
	{
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			e.getMessage();

		}
	}



	// ------------ Wait for Element to Be Clickable ----------------//
	public static void waitForElementClickable(WebDriver driver, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			System.out.println("Expected element not found " + e.getMessage());
		} 

	}


	// ------------ Wait till Element is InVisisble ----------------//
	public static void waitForElementInvisible(WebDriver driver, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.invisibilityOf(element));

		} catch (Exception e) {
			System.out.println(" Exception >> " + e.getMessage());
		}

	}



	// ------------ Wait till some text is not present ----------------//
	public static void waitFortextToBePresentInElement(WebDriver driver, WebElement element, String Exp_Text) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.textToBePresentInElement(element,Exp_Text));

		} catch (Exception e) {
			e.getMessage();

		}
	}

	
	public static void click(WebElement element, String desc) {
		try {
			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					// element.click();
					js.executeScript("arguments[0].click();", element);
				} else {
					throw new FrameworkException("Field: " + desc + " is disabled.");
				}
			} else {
				if (driverWait > 0) {
					driverWait--;
					waitTill(1);
					click(element, desc);
				} else {
					throw new FrameworkException("Field: " + desc + " is not displayed on UI.");
				}
			}
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			System.out.println(desc + " clicked");		
		} catch (NoSuchElementException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				click(element, desc);
			} else {
				throw new FrameworkException("Field " + desc + " not found.");
			}
		} catch (FrameworkException e) {
			throw new FrameworkException(e.getMessage());
		} catch (ElementNotVisibleException e) {
			scroll(element);
			click(element, desc);
		} catch (Exception e) {
			try {
				element.click();
			} catch (Exception ee) {
				throw new FrameworkException("Unknown exception occured while clicking: " + desc + "---" + ee.getClass()
				+ "---" + ee.getMessage() + "---Original Error---" + e.getClass() + "---" + e.getMessage());
			}
		}
	}
	
	
	
	
	public static void scroll(WebElement element) {
		try {
			element.isDisplayed();
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			js.executeScript("window.scrollBy(0,-250)", "");
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			System.out.println("Scrolled to element " + element.toString());
			//loggerForLogs.log(LogStatus.INFO, "Scrolled to element " + element.toString());
		} catch (NoSuchElementException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				scroll(element);
			} else {
				throw new FrameworkException(element.toString() + " not found while scrolling to element.");
			}

		} catch (Exception e) {
			throw new FrameworkException("Unknown exception encountered while scrolling to " + element.toString()
			+ "---" + e.getClass() + "---" + e.getMessage());
		}

	}

	public static void type(WebElement element, String text, String desc) {
		try {
			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					try {
						element.clear();
					} catch (Exception e) {

					}
					element.sendKeys(text);
				} else {
					if (driverWait > 0) {
						driverWait--;
						waitTill(1);
						type(element, text, desc);
					} else {
						throw new FrameworkException("Field " + desc + " is disabled.");
					}
				}
			} else {
				if (driverWait > 0) {
					driverWait--;
					waitTill(1);
					type(element, text, desc);
				} else {
					throw new FrameworkException("Field " + desc + " is not displayed.");
				}
			}
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			//loggerForLogs.log(LogStatus.INFO, "Typed '" + text + "' to " + desc);
		} catch (NoSuchElementException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				type(element, text, desc);
			} else {
				throw new FrameworkException("Field " + desc + " not found.");
			}
		} catch (ElementNotVisibleException e) {
			scroll(element);
			type(element, text, desc);
		} catch (FrameworkException e) {
			throw new FrameworkException(e.getMessage());
		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown exception occured while typing on: " + desc + e.getClass() + "---" + e.getMessage());
		}

	}

	
	public static void selectvaluefromDD(List<WebElement> lists , String ddvalue){
		for(WebElement element:lists){
			String actvalue = element.getText();
			if(actvalue.equalsIgnoreCase(ddvalue)){
				System.out.println("value found to select");
				TechnicalComponents.click(element, "dd value selected");
			}
		}
	}

}
