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

	public static String screenshotFolderPath = "";

	public static void getReports() {
		if (reports == null)
			reports = new ExtentReports();

		Date d = new Date();
		System.out.println(d.toString().replaceAll(":", "-"));
		String reportsFolder = d.toString().replaceAll(":", "-") + "\\screenshots";

		screenshotFolderPath = System.getProperty("user.dir") + "\\reports\\" + reportsFolder;
		String reportFolderPath = System.getProperty("user.dir") + "\\reports\\" + d.toString().replaceAll(":", "-");
		System.out.println(screenshotFolderPath);
		File f = new File(screenshotFolderPath);
		f.mkdirs();
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFolderPath);
		sparkReporter.config().setReportName("NRLM LokOS App Test");
		sparkReporter.config().setDocumentTitle("Automation Reports");
		sparkReporter.config().setTheme(Theme.STANDARD);
		sparkReporter.config().setEncoding("utf-8");

		reports.attachReporter(sparkReporter);

	}

	public static void addScreenShotsToTest(String filename, ExtentTest test) throws IOException {
		
		String screenShot = screenshotFolderPath + "\\" + filename + ".png";
		File scrFile = ((TakesScreenshot) appdriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(screenShot));

		test.addScreenCaptureFromPath(screenShot, filename);
	}
	
	public static void addScreenShotsToLogFail(String filename, ExtentTest test) throws IOException {
		
		String screenShot = screenshotFolderPath + "\\" + filename + ".png";
		File scrFile = ((TakesScreenshot) appdriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(screenShot));

		test.fail(filename, MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
	}
	
	public static void addScreenShotsToLogPass(String filename, ExtentTest test) throws IOException {
		
		String screenShot = screenshotFolderPath + "\\" + filename + ".png";
		File scrFile = ((TakesScreenshot) appdriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(screenShot));

		test.pass(filename, MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
	}

}

//public class ExtentManager {
//    private static final Logger log = LogManager.getLogger(ExtentManager.class.getName());
//    private static ExtentReports extent;
//
//    public static ExtentReports getInstance() {
//        if (extent == null) {
//            createInstance();
//        }
//        return extent;
//    }
//
//    public static synchronized ExtentReports createInstance() {
//    	Date d = new Date();
//		System.out.println(d.toString().replaceAll(":", "-"));
//		String reportsFolder=d.toString().replaceAll(":", "-") +"//screenshots";
//		
//		String screenshotFolderPath = System.getProperty("user.dir") +"//reports//"+reportsFolder;
//		String reportFolderPath = System.getProperty("user.dir") +"//reports//"+d.toString().replaceAll(":", "-");
//		System.out.println(screenshotFolderPath);
//		File f = new File(screenshotFolderPath);
//		f.mkdirs();
//		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFolderPath);
//		sparkReporter.config().setReportName("NRLM LokOS UsrMgmt Test");
//		sparkReporter.config().setDocumentTitle("Automation Reports");
//		sparkReporter.config().setTheme(Theme.DARK);
//		sparkReporter.config().setEncoding("utf-8");
//		
//		extent.attachReporter(sparkReporter);
//        log.info("*********** Report Path ***********");
//        log.info(reportFolderPath);
//        log.info("*********** Report Path ***********");
//        
//
//        
//
//        return extent;
//    }
