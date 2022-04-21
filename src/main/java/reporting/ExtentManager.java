package reporting;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import lokos.lokosTest;

public class ExtentManager extends lokosTest {
	
	public static int i=0;

	public static String screenshotFolderPath = "";

	public static void getReports(String xlsName) {
		if (reports == null)
			reports = new ExtentReports();

		Date d = new Date();
		System.out.println(d.toString().replaceAll(":", "-"));
		String reportsFolder = xlsName+" "+d.toString().replaceAll(":", "-") + "\\screenshots";

		screenshotFolderPath = System.getProperty("user.dir") + "\\reports\\" + reportsFolder;
		String reportFolderPath = System.getProperty("user.dir") + "\\reports\\" + xlsName+" "+d.toString().replaceAll(":", "-");
		System.out.println(screenshotFolderPath);
		File f = new File(screenshotFolderPath);
		f.mkdirs();
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFolderPath);
		sparkReporter.config().setReportName("NRLM LokOS App Test for "+xlsName);
		sparkReporter.config().setDocumentTitle("Automation Reports");
		sparkReporter.config().setTheme(Theme.STANDARD);
		sparkReporter.config().setEncoding("utf-8");

		reports.attachReporter(sparkReporter);

	}

	public static void addScreenShotsToTest(String filename, ExtentTest test) throws IOException {

		String screenShot = screenshotFolderPath + "\\" + filename+" "+(++i) + ".png";
		File scrFile = ((TakesScreenshot) appdriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(screenShot));

		test.addScreenCaptureFromPath(screenShot, filename+(++i));
	}

	public static void addScreenShotsToLogInfo(String filename, ExtentTest test) throws IOException {

		String screenShot = screenshotFolderPath + "\\" + filename +" "+(++i)+ ".png";
		File scrFile = ((TakesScreenshot) appdriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(screenShot));

		test.info(filename+(++i), MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
	}
	
	public static void addScreenShotsToLogFail(String filename, ExtentTest test) throws IOException {

		String screenShot = screenshotFolderPath + "\\" + filename +" "+(++i)+ ".png";
		File scrFile = ((TakesScreenshot) appdriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(screenShot));

		test.fail(filename+(++i), MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
	}

	public static void addScreenShotsToLogPass(String filename, ExtentTest test) throws IOException {

		String screenShot = screenshotFolderPath + "\\" + filename+" "+(++i) + ".png";
		File scrFile = ((TakesScreenshot) appdriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(screenShot));

		test.pass(filename+(++i), MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
	}

}

