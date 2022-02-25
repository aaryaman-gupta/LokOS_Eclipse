package web;



import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import lokos.lokosTest;

public class LoginTest extends lokosTest {

	public static int fail = 0;
	public static int pass = 0;
	public static WebDriver driver = null;
//	public static ExtentTest test;
//	public static ExtentReports reports;
//	public static xlsClasses xc = null;
	public static String browser = "";
	public static boolean reject = false;
	public static boolean rejectSHG=false;
	public int loginfailed = 0;
	public int fail_chrome = 0;
	public int fail_edge = 0;
	public int fail_browser = 0;
	public static int bpmApproval_count = 0;
	public static int shgNotFound=0;
	
	//// main function
	public static void startWeb() throws Exception {

		// int c=0;
		// try {
		
		LoginTest lt = new LoginTest();
		// c=1;
		// lt.generateReports();c++;
		lt.login();
		// c++;
		// lt.quitReporting();
		// }catch(Exception e) {
		// System.out.println("Problem at Step:"+c);
		// e.printStackTrace();
		// }
	}

//	public void generateReports() {
//		reports = ExtentManager.getReports();
//		test = reports.createTest("LokOS UserMgmt test");
//	}
//
//	public void quitReporting() {
//		reports.flush();
//	}

	// LOGIN PROCESS BEGINS HERE
	public void login() throws Exception {

		System.out.println("_____________________________________________________________");
		System.out.println("Test execution Begin!!");
		
		xc.changeSheet("Web Login");
//		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
//		System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\msedgedriver.exe");
		System.setProperty("webdriver.chrome.driver", xc.getCellString(1,loginConstants.chromeDriver));
		System.setProperty("webdriver.edge.driver", xc.getCellString(1,loginConstants.edgeDriver));
		
		browser = "Chrome";//Default Browser

//		xc = new xlsClasses(System.getProperty("user.dir") + "//LokOS testcases.xlsx", "Login");

//		xc = new xlsClasses(System.getProperty("user.dir") + flowCons.xlsName, flowCons.sheetName);
		int[] dimensions = xc.getRowCols("Web Login");
		int row = dimensions[0];

		if (row == -1) {
			throw new Exception("There are no rows in 'Login'");
		}

		/// Iterations Starts for Each Row in Login
		int iterations = 0;
		for (int r = 1; r < 2; r++) {

			xc.changeSheet("Web Login");

			System.out.println("============================");
			System.out.println("Iteration number:" + (++iterations));

			try {
				try {
				browser = xc.getCellString(r, 1);
				}catch(NullPointerException e) {
					System.out.println("Default Browser Selected");
				}
				System.out.println("Testing in " + browser);

				TestBase tb = new TestBase();
				// test.log(Status.INFO,"Opening Browser");
				driver = tb.launchBrowser(browser);

				String url=loginConstants.url_default;
				try {
				url = xc.getCellString(r, 2);
				}catch(NullPointerException e) {
				}
				// test.log(Status.INFO, "Opening URL");
				System.out.println("Opening URL " + url);
				driver.get(url);

//				Thread.sleep(5000);// For testing purpose

				String text_name = xc.getCellString(r, 3);
				System.out.println("We are in:" + text_name);
				// test.log(Status.INFO, "We are opening"+text_name);

				//// National or State Login
				if (text_name.equals("National")) {
					driver.findElement(By.xpath(loginConstants.startPage_NationalPath)).click();
					driver.findElement(By.xpath(loginConstants.userID_NationalPath)).sendKeys(xc.getCellString(r, 5));
					driver.findElement(By.xpath(loginConstants.password_NationalPath)).sendKeys(xc.getCellString(r, 6));
					driver.findElement(By.xpath(loginConstants.submitButton_NationalPath)).click();
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					try {
						driver.findElement(By.xpath(loginConstants.userScreenLoginSuccess_NationalPath));
						// test.log(Status.PASS,"Successfully logged as National User");
						System.out.println("Successfully logged as National User");
					} catch (Exception e) {
						// test.log(Status.FAIL, "Invalid User or Password:Login failed");
						System.out.println("Invalid User or Password:Login failed");
						throw new Exception("Invalid User or Password:Login failed");
					}
				} else if (text_name.equals("State")) {
					
					
					web.counters.flowID_counter.counter(r);

					driver.findElement(By.xpath(loginConstants.startPage_StatePath)).click();
					WebElement ddown = driver.findElement(By.xpath(loginConstants.userRoleDDown_StatePath));
					Select select = new Select(ddown);
					select.selectByVisibleText(xc.getCellString(r, 4));
					
					driver.findElement(By.xpath(loginConstants.userID_StatePath)).sendKeys(xc.getCellString(r, 5));
					driver.findElement(By.xpath(loginConstants.password_StatePath)).sendKeys(xc.getCellString(r, 6));
					Thread.sleep(3000);
					if (browser.equals("Chrome"))
						Thread.sleep(1000);
					driver.findElement(By.xpath(loginConstants.submitButton_StatePath)).click();
					driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);

					try {
						driver.findElement(By.xpath(loginConstants.userScreenLoginSuccess_StatePath));
					} catch (Exception e) {
						// test.log(Status.FAIL,"Invalid User or Password:Login failed");
						++loginfailed;
						System.out.println("Invalid User or Password:Login failed");
						throw new Exception("Invalid User or Password:Login failed");
					}

					stateFlowPath.flowChecklist(r);
					++pass;
				} else {
					// test.log(Status.FAIL, "Wrong Section");
					System.out.println("Should be National/State");
					throw new Exception("Should be National/State");

				}

				Thread.sleep(5000);
				driver.findElement(By.xpath(loginConstants.profileButtonPath)).click();
				driver.findElement(By.xpath(loginConstants.logoutButtonPath)).click();
				Thread.sleep(3000);
				driver.quit();

			} catch (Exception e) {
				Thread.sleep(3000);
				driver.quit();
				++fail;
				if (browser.equals("Chrome"))
					++fail_chrome;
				else if (browser.equals("Edge"))
					++fail_edge;
				else
					++fail_browser;
				// test.log(Status.FAIL, "Process Failed");
				System.out.println("\n........................");
				System.out.println(
						"Process failed in iteration " + iterations + "\nReason for failure: " + e.getMessage());
				continue;
			}

		}
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println("SHGs not found: "+shgNotFound);
		System.out.println("Fails:" + fail + " out of " + iterations);
		System.out.println("Chrome:" + fail_chrome + " || Edge:" + fail_edge + " || Others:" + fail_browser);
		System.out.println("Execution Complete!!");
		System.out.println("_____________________________________________________________");
	}

	// CHECK FOR THE PRESENCE AND THE VISIBILITY OF THE PRESENT ELEMENT
	public static boolean isElementPresent(By locator) {
		WebElement e = null;

		try {
			e = driver.findElement(locator);
		} catch (Exception ex) {
			return false;
		}

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		if (!e.isDisplayed())
			return false;

		return true;
	}
}

//	public String manage_alerts() throws InterruptedException{
//		
////		Thread.sleep(2000);
//		String a="";
////		Alert alert=driver.switchTo().alert();
////		a=alert.getText();
////		System.out.println(a);		
////		alert.accept();
//////		alert.dismiss();
//		return a;
////	
//	}
