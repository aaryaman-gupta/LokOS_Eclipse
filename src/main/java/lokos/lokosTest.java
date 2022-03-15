package lokos;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import app.loginConstants;

//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;

import app.memCons;
import app.memberProfile;
import app.navigation;
import app.profileCons;
import app.shgMeetings;
import app.shgProfileCreation;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import readingXLS.xlsClasses;
import reporting.ExtentManager;
import util.DeviceUtil;
import util.MobileTouch;
import util.summary;

public class lokosTest {

	public static xlsClasses xc = null;
	public static AndroidDriver<AndroidElement> appdriver = null;
	public static ExtentReports reports = null;
	public static ExtentTest test = null;
	public static ExtentTest testFlow = null;
	public static ExtentTest testMem = null;
	public static ExtentTest testSHG = null;
	public static ExtentTest testMeet = null;
	public static int memberRow = 0;
	public static int cutoffRow = 0;
	public static int regularRow = 0;
	public static MobileTouch mt = null;
	public static DeviceUtil du = null;
	public static int shg_row_counter = 0;
	public static boolean web_process_success_flag = false;
	public static int[][] mem_check = new int[2][58];
	public static int[][] shg_check = new int[2][59];
	public static int[][] cutoff_check = new int[2][91];
	public static int[][] reg_check = new int[2][91];
	

	@Test
	public static void startApp() throws Exception {
		
		ExtentManager.getReports();
		test = reports.createTest("LokOS App Test");
		System.out.println("=====================================================================================");
		test.log(Status.INFO, "Start Test");
		xc = new xlsClasses(System.getProperty("user.dir") + flowCons.xlsName, flowCons.sheetName);
		app.launchLokOS.launchLokos();
		test.log(Status.PASS, "LokOS Successfully Launched");
		System.out.println("Lokos Successfully Launched");
		mt = new MobileTouch(appdriver);
		du = new DeviceUtil(appdriver);
//		app.loginTest.login();
		appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		appdriver.findElementById("com.microware.cdfi:id/otp_view").sendKeys("1111");
		
		test.log(Status.PASS, "Login Complete");
//		app.loginTest.sync();
		System.out.println("Login and Sync Complete");
		util.mail.ZipAndSendMail.send("Starting Test(Checking Email Function)","PFA report of previous Test");
		Thread.sleep(1000);
		ExtentManager.addScreenShotsToTest("SHG Bookeeper Screen", test);
		
		navigation.shgButton();
		

		xc.changeSheet("SHGs");

		int[] dimensions = xc.getRowCols("SHGs");
		int row = dimensions[0];

		for (int r = 1; r < row; r++) {
			xc.changeSheet("SHGs");
			try {
				if (!(xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("Submit")
						|| xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("Status")))
					testFlow = reports.createTest("[" + r + "] " + xc.getCellString(r, profileCons.shgNameColNum)
							+ " (Flow: " + xc.getCellString(r, profileCons.typeColNum) +"->"+xc.getCellString(r, profileCons.flowTypeColNum)+ ")");
				test.log(Status.INFO, "Flow number " + r + " begins");
				System.out.println("\nFlow number " + r + " begins");
				
				if (xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("New Members")) {
					String shg = xc.getCellString(r, profileCons.shgNameColNum);
					System.out.println("Flow started for New Members in SHG " + shg + "("
							+ xc.getCellString(r, profileCons.flowTypeColNum) + ")");
					navigation.navToVillage(r);
					navigation.existingSHG(r);
					navigation.openSHGMembers(r);
					int numMem = (int) xc.getCellDoubleValue(r, profileCons.numMemAddColNum);
					testFlow.log(Status.INFO, "Number of members to add--> " + numMem);
					System.out.println("Number of members to add--> " + numMem);
					xc.changeSheet("Members");
					for (int i = 1; i <= numMem; i++) {
						System.out.println("");
						System.out.println("________________________");
						xc.changeSheet("Members");
						++memberRow;
						String memName = xc.getCellString(memberRow, memCons.nameColNum);
						System.out.println("Member no: " + i + " -->" + memName);
						testMem = testFlow.createNode(
								"Member: " + memName + "(" + xc.getCellString(memberRow, memCons.typeColNum) + ")");
						navigation.newMember();
						/////////////////////////////////////////////////
						int[] val = memberProfile.idSelect_Mem(memberRow);
						/////////////////////////////////////////////////
						int[][] zeroIni=new int[mem_check[0].length][mem_check[1].length];
						summary.display(mem_check, testMem);
						mem_check=zeroIni;
						testMem.log(Status.INFO, "Member " + i + " Fails: " + val[1] + "/" + val[2]);
						testFlow.log(Status.INFO, "Member " + i + " Fails: " + val[1] + "/" + val[2]);
						System.out.println("^^^^^^^^^^^^^^");
						System.out.println("Member " + i + " Fails: " + val[1] + "/" + val[2]);
						System.out.println("________________________");
					}
				}
				else if (xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("Update Member")) {

					navigation.navToVillage(r);
					navigation.existingSHG(r);
					navigation.openSHGMembers(r);

					String shg = xc.getCellString(r, profileCons.shgNameColNum);
					System.out.println("Flow started for Update Member in SHG " + shg + "("
							+ xc.getCellString(r, profileCons.flowTypeColNum) + ")");

					xc.changeSheet("Members");
					++memberRow;
					String memName = xc.getCellString(memberRow, memCons.nameColNum);
					testMem = testFlow.createNode(
							"Member: " + memName + "(" + xc.getCellString(memberRow, memCons.typeColNum) + ")");
					System.out.println("");
					System.out.println("________________________");

					navigation.existingMember(memberRow);

					///////////////////////////////////////////////////
					int[] val = memberProfile.idSelect_Mem(memberRow);
					///////////////////////////////////////////////////
					int[][] zeroIni=new int[mem_check[0].length][mem_check[1].length];
					summary.display(mem_check, testMem);
					mem_check=zeroIni;
					String resultF = "Member " + memName + " Fails: " + val[1] + "/" + val[2];
					String resultP = "Member " + memName + " Passed: " + val[0] + "/" + val[2];
					testMem.log(Status.INFO, resultF);
					testMem.log(Status.INFO, resultP);
					testFlow.log(Status.INFO, resultF);
					testFlow.log(Status.INFO, resultP);
					System.out.println("^^^^^^^^^^^^^^");
					System.out.println(resultF);
					System.out.println(resultP);
					System.out.println("________________________");

				}
				else if (xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("New SHG")) {

					navigation.navToVillage(r);
					navigation.newSHG();

					String shg = xc.getCellString(r, profileCons.shgNameColNum);
					System.out.println("\nFlow started for New SHG " + shg);
					testSHG = testFlow.createNode(
							"New SHG: " + shg + "(" + xc.getCellString(r, profileCons.flowTypeColNum) + ")");
					System.out.println("________________________");
					//////////////////////////////////////////////
					int[] val = shgProfileCreation.idSelect_SHG(r);
					//////////////////////////////////////////////
					int[][] zeroIni=new int[shg_check[0].length][shg_check[1].length];
					summary.display(shg_check, testSHG);
					shg_check=zeroIni;
					String resultF = "New SHG " + xc.getCellString(r, profileCons.shgNameColNum) + " Fails: " + val[1]
							+ "/" + val[2];
					testSHG.log(Status.INFO, resultF);
					testFlow.log(Status.INFO, resultF);
					System.out.println("^^^^^^^^^^^^^^");
					System.out.println(resultF);
					System.out.println("________________________");

				}
				else if (xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("Update SHG")) {

					navigation.navToVillage(r);
					navigation.existingSHG(r);
					navigation.openSHGProfile(r);

					String shg = xc.getCellString(r, profileCons.shgNameColNum);
					testSHG = testFlow.createNode(
							"Update SHG: " + shg + "(" + xc.getCellString(r, profileCons.flowTypeColNum) + ")");
					System.out.println("\nFlow started for New SHG " + xc.getCellString(r, profileCons.shgNameColNum));
					System.out.println("________________________");
					//////////////////////////////////////////////
					int[] val = shgProfileCreation.idSelect_SHG(r);
					//////////////////////////////////////////////
					int[][] zeroIni=new int[shg_check[0].length][shg_check[1].length];
					summary.display(shg_check, testSHG);
					shg_check=zeroIni;
					String resultF = "Update of " + shg + " Failed: " + val[1] + "/" + val[2];
					String resultP = "Update of " + shg + " Passed: " + val[0] + "/" + val[2];
					testSHG.log(Status.INFO, resultF);
					testSHG.log(Status.INFO, resultP);
					testFlow.log(Status.INFO, resultF);
					testFlow.log(Status.INFO, resultP);
					System.out.println("^^^^^^^^^^^^^^");
					System.out.println(resultF);
					System.out.println(resultP);
					System.out.println("________________________");

				}
				else if (xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("Submit")) {

					boolean webProcess_flag = true;
					try {

						test.log(Status.INFO, "Starting Updation Process...");
						testSHG = test.createNode("Upload Data + Web Flow");
						System.out.println("Starting Updation Process...");
						appdriver.findElementById("com.microware.cdfi:id/icBack").click();
						Thread.sleep(1000);
						appdriver.findElementById("com.microware.cdfi:id/tbl_sync").click();
						Thread.sleep(1000);
						appdriver.findElementById("com.microware.cdfi:id/tbl_upload").click();
						Thread.sleep(1000);
						String s = appdriver.findElementById("com.microware.cdfi:id/tvUploadData").getText();
						System.out.println(s);
						if (s.contains("(0)")) {
							testSHG.log(Status.FAIL, "Data not sufficient to upload");
							System.out.println("Data not sufficient to upload");
							webProcess_flag = false;
							appdriver.quit();
						} else {
							appdriver.findElementById("com.microware.cdfi:id/tvUploadData").click();
							Thread.sleep(30000);
							appdriver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
							if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
									.equals("Record Added To Queue")) {
								testSHG.log(Status.PASS, "Record Added To Queue");
								System.out.println("Record Added To Queue");
								appdriver.quit();
							} else {
								ExtentManager.addScreenShotsToLogFail("Fail Submission Process" + r, testSHG);
								testSHG.log(Status.FAIL, "||||Error->Record was not added to the Queue||||");
								System.out.println("||||Error->Record was not added to the Queue||||");
								appdriver.quit();
								webProcess_flag = false;
							}

						}
//						appdriver.findElementById(loginConstants.syncHome).click();
						test.log(Status.INFO, "...Updation Process Complete ");
						System.out.println("...Updation Process Complete ");
					} catch (Exception e) {
						webProcess_flag=false;
						ExtentManager.addScreenShotsToLogFail("Fail: Submission Process " + r, testSHG);
						testSHG.log(Status.FAIL, "...error in Updation Process");
						System.out.println("...error in Updation Process");
						test.log(Status.INFO, "   ||||Terminating Web Flow||||");
						System.out.println("\n   ||||Terminating Web Flow||||");
						Thread.sleep(2000);
						e.printStackTrace();
						appdriver.quit();
					}

					xc.changeSheet("SHGs");
					
					if (webProcess_flag) {
						test.log(Status.INFO, "Web Flow Commencing in 3 minutes");
						System.out.println("Web Flow Commencing in 3 minutes");
						Thread.sleep(180000);
						web.LoginTest.startWeb();
					}
					
					app.launchLokOS.launchLokos();
					test.log(Status.PASS, "Lokos Successfully Launched");
					System.out.println("Lokos Successfully Launched");
					appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					appdriver.findElementById("com.microware.cdfi:id/otp_view").sendKeys("1111");					
					app.loginTest.sync();
					test.log(Status.PASS, "Login and Sync Complete");
					System.out.println("Login and Sync Complete");
					Thread.sleep(2000);
					ExtentManager.addScreenShotsToLogPass("SHG Entry Screen", test);
					navigation.shgButton();
				}
				else if (xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("Status")) {					
					testSHG=test.createNode("Status Check");
					++shg_row_counter;
					testSHG.log(Status.INFO, "Starting status process...");
					System.out.println("\nStarting status process...");
					appdriver.findElementById("com.microware.cdfi:id/icBack").click();
					Thread.sleep(1000);
					appdriver.findElementById("com.microware.cdfi:id/tbl_sync").click();
					Thread.sleep(1000);
					appdriver.findElementById("com.microware.cdfi:id/tv_approvalstatus").click();

					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					appdriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					String s = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
					testSHG.log(Status.PASS, "Status: " + s);
					System.out.println("Status: " + s);
					ExtentManager.addScreenShotsToTest("Status "+r, test);
					appdriver.findElementById(loginConstants.alertOK2).click();
					appdriver.findElementById(loginConstants.syncHome).click();
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					testSHG.log(Status.INFO, "...status record processed.");
					System.out.println("...status record processed.\n");
					navigation.shgButton();
				}
				else if (xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("SHG Meetings")) {

					appdriver.findElementById("com.microware.cdfi:id/icBack").click();
					Thread.sleep(1000);
					System.out.println("\n-----SHG Meetings-----");
					String shg = xc.getCellString(r, profileCons.shgNameColNum);
					System.out.println("\nFlow started for SHG Meetings " + xc.getCellString(r, profileCons.shgNameColNum));
					/////////////////////////
					int[] val = shgMeetings.meetingNum(r);
					/////////////////////////					
					String resultF = "SHG Meetings: " + shg + " Failed: " + val[1] + "/" + val[2];
					String resultP = "SHG Meetings: " + shg + " Passed: " + val[0] + "/" + val[2];
					testMeet.log(Status.INFO, resultF);
					testMeet.log(Status.INFO, resultP);
					testFlow.log(Status.INFO, resultF);
					testFlow.log(Status.INFO, resultP);
					System.out.println("^^^^^^^^^^^^^^");
					System.out.println(resultF);
					System.out.println(resultP);
					System.out.println("________________________");
					
					navigateBackToScreen("SHG");
					appdriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
					appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					navigation.shgButton();

				}

				test.log(Status.PASS, "Flow number " + r + " complete");
				System.out.println("Flow number " + r + " complete");

			} catch (Exception e) {
				ExtentManager.addScreenShotsToTest("Failed Flow" +r, testFlow);
				testFlow.log(Status.FAIL, "Flow " + r + " failed in between");
				test.log(Status.INFO,"Flow " + r + " failed in between" );
				System.out.println("Flow " + r + " failed in between");
//				test.log(Status.INFO, "Restarting App");
//				appdriver.quit();
//				app.launchLokOS.launchLokos();
//				test.log(Status.PASS, "Lokos Successfully Launched");
//				System.out.println("Lokos Successfully Launched");
//				appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//				appdriver.findElementById("com.microware.cdfi:id/otp_view").sendKeys("1111");
//				test.log(Status.PASS, "Login and Sync Complete");
//				System.out.println("Login and Sync Complete");
//				Thread.sleep(2000);
//				ExtentManager.addScreenShotsToLogPass("SHG Entry Screen", test);
//				navigation.shgButton();
//				test.log(Status.INFO, "Continuing next Flow");
				e.printStackTrace();
			}
			Thread.sleep(2000);
		}
		Thread.sleep(10000);
		appdriver.quit();

		test.log(Status.INFO, "Testing Ends");
		System.out.println("\nTesting Ends");
		System.out.println("=====================================================================================");
		xc.closeReaders();
		reports.flush();

		util.mail.ZipAndSendMail.send("Automation Test Reports","Please find the reports in attachment");
	}
	
	public static void navigateBackToScreen(String screen_title) throws Exception {
		@SuppressWarnings("unused")
		int i = 0;
		String title = "";
		try {
			title = appdriver.findElementById("com.microware.cdfi:id/tv_title").getText();
		} catch (Exception e) {
			appdriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
		}
		try {
			while (!title.equals(screen_title)) {
				appdriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
				if (appdriver.findElementById("com.microware.cdfi:id/tv_title").getText().equals("SHG"))
					break;
				i++;
			}
		} catch (Exception e) {
			throw new Exception("Cannot navigate to " + screen_title + " screen");
		}
	}

}
//test.log(Status.INFO, "Starting test case Login");
//test.log(Status.FAIL, "404 error");
//// selenium takes screenshot and puts in screesnhot folder
//test.addScreenCaptureFromPath("D:\\Ashish\\Temp.png", "404 Error");
//Assert.fail("404 error");
//test.log(Status.INFO, "Opening Browser");
//test.log(Status.INFO, "Logging In");
//test.log(Status.PASS, "Test Passed");
