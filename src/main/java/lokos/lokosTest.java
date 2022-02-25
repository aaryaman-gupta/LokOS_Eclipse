package lokos;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
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
import readingXLS.xlsClasses;
import reporting.ExtentManager;
import util.DeviceUtil;
import util.MobileTouch;

public class lokosTest {

	public static xlsClasses xc = null;
	public static AndroidDriver<AndroidElement> appdriver = null;
	public static ExtentReports reports = null;
	public static ExtentTest test = null;
	public static ExtentTest testMem = null;
	public static ExtentTest testSHG = null;
	public static ExtentTest testCoff = null;
	public static ExtentTest testReg = null;
	public static int memberRow = 0;
	public static int cutoffRow = 0;
	public static int regularRow = 0;
	public static MobileTouch mt = null;
	public static DeviceUtil du = null;
	public static int shg_row_counter = 0;
	public static boolean web_process_success_flag = false;
	public static int[][] cutoff_check= new int[2][88];
	public static int[][] reg_check= new int[2][88];

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
		Thread.sleep(1000);
		ExtentManager.addScreenShotsToTest("SHG Bookeeper Screen", test);
		
		navigation.shgButton();

		xc.changeSheet("SHGs");

		int[] dimensions = xc.getRowCols("SHGs");
		int row = dimensions[0];

		for (int r = 1; r < row; r++) {
			xc.changeSheet("SHGs");
			try {
				test.log(Status.INFO, "Flow number " + r + " begins");
				System.out.println("\nFlow number " + r + " begins");
				
				if (xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("New Members")) {
					String shg = xc.getCellString(r, profileCons.shgNameColNum);
					test.log(Status.INFO, "Flow started for New Members in SHG " + shg+"("+profileCons.flowTypeColNum+")");
					System.out.println("Flow started for New Members in SHG " + shg+"("+profileCons.flowTypeColNum+")");
					navigation.navToVillage(r);
					navigation.existingSHG(r);
					navigation.openSHGMembers(r);
					int numMem = (int) xc.getCellDoubleValue(r, profileCons.numMemAddColNum);
					test.log(Status.INFO, "Number of members to add--> " + numMem);
					System.out.println("Number of members to add--> " + numMem);
					xc.changeSheet("Members");
					for (int i = 1; i <= numMem; i++) {
						System.out.println("");
						System.out.println("________________________");
						xc.changeSheet("Members");
						++memberRow;
						String memName=xc.getCellString(memberRow, memCons.nameColNum);
						test.log(Status.INFO, "Member no: " + i + " -->" + memName);
						System.out.println("Member no: " + i + " -->" + memName);
						testMem = test.createNode("Member: " + memName,"("+xc.getCellString(memberRow, memCons.typeColNum)+")");
						testMem.log(Status.INFO, "Member: " + memName);
						reports.flush();
						navigation.newMember();
						/////////////////////////////////////////////////////
						int[] val = memberProfile.idSelect_Mem(memberRow);
						/////////////////////////////////////////////////////
						System.out.println("^^^^^^^^^^^^^^");
						testMem.log(Status.INFO, "Member " + i + " Fails: " + val[1] + "/" + val[2]);
						test.log(Status.INFO, "Member " + i + " Fails: " + val[1] + "/" + val[2]);
						System.out.println("Member " + i + " Fails: " + val[1] + "/" + val[2]);
						System.out.println("________________________");
					}
				}
				if (xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("Update Member")) {

					String shg = xc.getCellString(r, profileCons.shgNameColNum);
					test.log(Status.INFO, "Flow started for Update Member in SHG " + shg);
					System.out.println("Flow started for Update Member in SHG " + shg);
					navigation.navToVillage(r);
					navigation.existingSHG(r);
					navigation.openSHGMembers(r);
					xc.changeSheet("Members");

					System.out.println("");
					System.out.println("________________________");

					navigation.existingMember(r);

					///////////////////////////////////////////////////
					int[] val = memberProfile.idSelect_Mem(++memberRow);
					///////////////////////////////////////////////////
					System.out.println("^^^^^^^^^^^^^^");
					testMem.log(Status.INFO,
							"Member " + xc.getCellString(r, memCons.nameColNum) + " Fails: " + val[1] + "/" + val[2]);
					testMem.log(Status.INFO,
							"Member " + xc.getCellString(r, memCons.nameColNum) + " Passed: " + val[0] + "/" + val[2]);
					test.log(Status.INFO,
							"Member " + xc.getCellString(r, memCons.nameColNum) + " Fails: " + val[1] + "/" + val[2]);
					test.log(Status.INFO,
							"Member " + xc.getCellString(r, memCons.nameColNum) + " Passed: " + val[0] + "/" + val[2]);
					System.out.println(
							"Member " + xc.getCellString(r, memCons.nameColNum) + " Fails: " + val[1] + "/" + val[2]);
					System.out.println(
							"Member " + xc.getCellString(r, memCons.nameColNum) + " Passed: " + val[0] + "/" + val[2]);
					System.out.println("________________________");

				}
				if (xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("New SHG")) {
					String shg = xc.getCellString(r, profileCons.shgNameColNum);
					testSHG = reports.createTest(
							"New SHG: " + shg + "(" + xc.getCellString(r, profileCons.flowTypeColNum) + ")");
					System.out.println("\nFlow started for New SHG " + shg);
					navigation.navToVillage(r);
					navigation.newSHG();
					System.out.println("________________________");
					//////////////////////////////////////////////
					int[] val = shgProfileCreation.idSelect_SHG(r);
					//////////////////////////////////////////////
					System.out.println("^^^^^^^^^^^^^^");
					testSHG.log(Status.INFO, "New SHG " + xc.getCellString(r, profileCons.shgNameColNum) + " Fails: "
							+ val[1] + "/" + val[2]);
					test.log(Status.INFO, "New SHG " + xc.getCellString(r, profileCons.shgNameColNum) + " Fails: "
							+ val[1] + "/" + val[2]);
					System.out.println("New SHG " + xc.getCellString(r, profileCons.shgNameColNum) + " Fails: " + val[1]
							+ "/" + val[2]);
					System.out.println("________________________");

				}
				if (xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("Update SHG")) {
					testSHG = reports.createTest("Update SHG: " + xc.getCellString(r, profileCons.shgNameColNum) + "("
							+ xc.getCellString(r, profileCons.flowTypeColNum) + ")");
					testSHG.log(Status.INFO,
							"Flow started for New SHG " + xc.getCellString(r, profileCons.shgNameColNum));
					test.log(Status.INFO, "Flow started for New SHG " + xc.getCellString(r, profileCons.shgNameColNum));
					System.out.println("\nFlow started for New SHG " + xc.getCellString(r, profileCons.shgNameColNum));
					navigation.navToVillage(r);
					navigation.existingSHG(r);
					navigation.openSHGProfile(r);
					System.out.println("________________________");
					int[] val = shgProfileCreation.idSelect_SHG(r);
					System.out.println("^^^^^^^^^^^^^^");
					testSHG.log(Status.INFO, "Update of " + xc.getCellString(r, profileCons.shgNameColNum) + " Failed: "
							+ val[1] + "/" + val[2]);
					testSHG.log(Status.INFO, "Update of " + xc.getCellString(r, profileCons.shgNameColNum) + " Passed: "
							+ val[0] + "/" + val[2]);
					test.log(Status.INFO, "Update of " + xc.getCellString(r, profileCons.shgNameColNum) + " Failed: "
							+ val[1] + "/" + val[2]);
					test.log(Status.INFO, "Update of " + xc.getCellString(r, profileCons.shgNameColNum) + " Passed: "
							+ val[0] + "/" + val[2]);
					System.out.println("Update of " + xc.getCellString(r, profileCons.shgNameColNum) + " Failed: "
							+ val[1] + "/" + val[2]);
					System.out.println("Update of " + xc.getCellString(r, profileCons.shgNameColNum) + " Passed: "
							+ val[0] + "/" + val[2]);
					System.out.println("________________________");

				}
				if (xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("Submit")) {

					boolean webProcess_flag = true;
					try {
						test.log(Status.INFO, "Starting Updation Process...");
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
							test.log(Status.INFO, "Data not sufficient to upload");
							System.out.println("Data not sufficient to upload");
							webProcess_flag = false;
							appdriver.quit();
						} else {
							appdriver.findElementById("com.microware.cdfi:id/tvUploadData").click();
							Thread.sleep(30000);
							appdriver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
							if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
									.equals("Record Added To Queue")) {
								test.log(Status.PASS, "Record Added To Queue");
								System.out.println("Record Added To Queue");
								appdriver.quit();
							} else {
								test.log(Status.FAIL, "||||Error->Record was not added to the Queue||||");
								System.out.println("||||Error->Record was not added to the Queue||||");
								appdriver.quit();
								webProcess_flag = false;
							}

						}

//						appdriver.findElementById(loginConstants.syncHome).click();
						test.log(Status.INFO, "...Updation Process Complete ");
						System.out.println("...Updation Process Complete ");
					} catch (Exception e) {
						test.log(Status.FAIL, "...error in Updation Process");
						System.out.println("...error in Updation Process");
						test.log(Status.INFO, "   ||||Terminating Test||||");
						System.out.println("\n   ||||Terminating Test||||");
						Thread.sleep(2000);
						e.printStackTrace();
						appdriver.quit();
					}

					if (webProcess_flag) {
						Thread.sleep(180000);
						web.LoginTest.startWeb();

					}
					xc.changeSheet("SHGs");
					app.launchLokOS.launchLokos();
					test.log(Status.PASS, "Lokos Successfully Launched");
					System.out.println("Lokos Successfully Launched");
					appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					appdriver.findElementById("com.microware.cdfi:id/otp_view").sendKeys("1111");
					app.loginTest.sync();

					test.log(Status.PASS, "Login and Sync Complete");
					System.out.println("Login and Sync Complete");
					navigation.shgButton();
				}
				if (xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("Status")) {

					++shg_row_counter;
					test.log(Status.INFO, "Starting status process...");
					System.out.println("\nStarting status process...");
					appdriver.findElementById("com.microware.cdfi:id/icBack").click();
					Thread.sleep(1000);
					appdriver.findElementById("com.microware.cdfi:id/tbl_sync").click();
					Thread.sleep(1000);
					appdriver.findElementById("com.microware.cdfi:id/tv_approvalstatus").click();

					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					appdriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					String s = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
					test.log(Status.PASS, "Status: " + s);
					System.out.println("Status: " + s);
					appdriver.findElementById(loginConstants.alertOK2).click();
					appdriver.findElementById(loginConstants.syncHome).click();
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					test.log(Status.INFO, "...status record processed.");
					System.out.println("...status record processed.\n");
				}
				if (xc.getCellString(r, profileCons.typeColNum).equalsIgnoreCase("SHG Meetings")) {

					xc.changeSheet("SHGs");
					appdriver.findElementById("com.microware.cdfi:id/icBack").click();
					Thread.sleep(1000);

					System.out.println("\n-----SHG Meetings-----");
					
					/////////////////////////
					int[] val=shgMeetings.meetingNum(r);
					/////////////////////////
					
					appdriver.findElementById("com.microware.cdfi:id/ic_Back").click();
					appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					navigation.shgButton();

				}

				test.log(Status.PASS, "Flow number " + r + " complete");
				System.out.println("Flow number " + r + " complete");

			} catch (Exception e) {
				test.log(Status.FAIL, "Flow " + r + " failed in between");
				System.out.println("Flow " + r + " failed in between");
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
		
		util.mail.ZipAndSendMail.send();
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
