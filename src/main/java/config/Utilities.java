package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utilities {

	static Properties props = new Properties();
	static String strFileName = "config.properties";
	static String strValue;
	
	//----------- Date-Time -------------------//	
		public static String DateTimeString() {

			int MyDay = LocalDateTime.now().getDayOfMonth(); // dd
			int MyYear = LocalDateTime.now().getYear(); // yyyy
			int MyMonth = LocalDateTime.now().getMonthValue(); // yyyy
			int Myhours = LocalDateTime.now().getHour();
			int Mymins = LocalDateTime.now().getMinute();
			int Mysecs = LocalDateTime.now().getSecond();

			final String CureentDtTm = (String.valueOf(MyMonth) + String.valueOf(MyDay) + String.valueOf(MyYear)
			+ String.valueOf(Myhours) + String.valueOf(Mymins) + String.valueOf(Mysecs));
			return CureentDtTm;

		}

		

		//----------------------------------- Create Folder ------------------------------------//
		public static boolean CreateFolder(String FolderPath) {
			boolean result = false;
			try {
				File directory = new File(FolderPath);
				if (!directory.exists()) {
					result = directory.mkdir();
				}
			} catch (Exception e) {
				System.out.println("Error while creating the specific folder. Location " + FolderPath + ". Error message "
						+ e.getMessage());
			}
			return result;

		}
		
		public static String getProperty(String strKey) {
			try {
				File f = new File(strFileName);
				if (f.exists()) {
					FileInputStream in = new FileInputStream(f);
					props.load(in);
					strValue = props.getProperty(strKey);
					in.close();
				} else
					throw new FrameworkException("Configuration File not found.");
			} catch (Exception e) {
				throw new FrameworkException("Unknown Error encountered while reading " + strKey
						+ " from configuration file. ---" + e.getClass() + "---" + e.getMessage());
			}
			if (strValue != null) {
				return strValue;

			} else {

				throw new FrameworkException(
						"Value '" + strKey + "' not configured in config file. Contact automation team");
			}

		}

		

		public static String CaptureScreenshot(WebDriver driver, String screenshotName, String ResultFolderPath) throws IOException {		
			String FileName = "";
			try {
				TakesScreenshot ts = (TakesScreenshot) driver;
				File source = ts.getScreenshotAs(OutputType.FILE);
				FileName = screenshotName + randomNum() + ".jpg";
				String dest = ResultFolderPath + "\\" + screenshotName + randomNum() + ".jpg";
				System.out.println("ScreenShot Created - "+dest);
				File destination = new File(dest);
				FileUtils.copyFile(source, destination);
			} catch (Exception e) {
				System.out.println("Error while capturing the " + e.getMessage());
			}
			return FileName;
		}
		

		public static String randomNum() {
			return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()).toString();

		}
}
