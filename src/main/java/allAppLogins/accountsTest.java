package allAppLogins;

import java.io.IOException;

import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import lokos.lokosTest;
import readingXLS.xlsClasses;
import reporting_accounts.ExtentManager;
import util.DeviceUtil;

public class accountsTest{

	public static xlsClasses xc = null;
	public static AndroidDriver<AndroidElement> appdriver = null;
	public static DeviceUtil du = null;
	public static ExtentReports reports = null;
	public static ExtentTest test = null;
	public static ExtentTest testNode = null;
	public static String startAction = "";
	public static String endAction = "";

	@Test
//	public static void acc() {
	public static void allLogins() {

		try {
		xc = new xlsClasses(System.getProperty("user.dir") + "\\XLS data\\States Login.xlsx", "Login Id");
		int[] dimensions = xc.getRowCols("Login Id");
		int row = dimensions[0];

		ExtentManager.getReports("Login & Sync Tests");
		String role = "";

		
		for (int i = 1; i < row; i++) {
			Thread.sleep(3000);
			try {
			if (xc.getCellString(i,logCons.Y_N ).equalsIgnoreCase("Y")) {
				if (xc.getCellString(i, logCons.UserRole).contains("VO")) {
					role = "VO";
				} else if (xc.getCellString(i, logCons.UserRole).contains("SHG")) {
					role = "SHG";
				} else if (xc.getCellString(i, logCons.UserRole).contains("CLF")) {
					role = "CLF";
				}
				if (xc.getCellString(i, logCons.Install_New_App_Y_N).equalsIgnoreCase("Y")) {
					test = reports.createTest(
							"[" + i + "] " + xc.getCellString(i, logCons.Test_Title) + " (Type: " + xc.getCellString(i, logCons.Test_Type) + ")");
					test.log(Status.INFO, xc.getCellString(i, logCons.Test_Cases));
					test.log(Status.INFO, "Running new instance for: " + xc.getCellString(i, logCons.State));
					System.out.println("Running new instance for: " + xc.getCellString(i, logCons.State));
					int status = allAppLogins.installLokosTest.launchLokosStates(i);
					if (status == 1) {
//						test.log(Status.PASS, "Lokos Successfully Launched");
						System.out.println("Lokos Successfully Launched");
						try {
							allAppLogins.appLoginTest.login(i, role, test);
							System.out.println("Login and Sync Complete");
							xc.changeSheet("Login Id");
							try {
//								if (role.equals("SHG"))
//									lokosTest.startApp(xc.getCellString(i, logCons.Excel_Sheet_Names), xc.getCellString(i, logCons.App));
//								else if (role.equals("VO"))
//									lokosVO.startApp(xc.getCellString(i, logCons.Excel_Sheet_Names), xc.getCellString(i, logCons.App));
//								else if (role.equals("CLF"))
//									lokosCLF.startApp(xc.getCellString(i, logCons.Excel_Sheet_Names), xc.getCellString(i, logCons.App));
							} catch (Exception e) {
								System.out.println("Program startApp Failed");
								e.printStackTrace();
							}
						} catch (Exception e) {

							System.out.println("Login failed for " + xc.getCellString(i, logCons.State) + ".");
							e.printStackTrace();
						}
					} else if (status == 999) {
//						test.log(Status.FAIL, "Launch Error");
						System.out.println("Launch Error");
					}

				} else if (xc.getCellString(i, logCons.Install_New_App_Y_N).equalsIgnoreCase("N")) {
					if (xc.getCellString(i, logCons.UserRole).contains("VO")) {
						role = "VO";
					} else if (xc.getCellString(i, logCons.UserRole).contains("SHG")) {
						role = "SHG";
					} else if (xc.getCellString(i, logCons.UserRole).contains("CLF")) {
						role = "CLF";
					}
//					test = reports.createTest(
//							"[" + i + "] " + xc.getCellString(i, logCons.Test_Title) + " (Type: " + xc.getCellString(i, logCons.Test_Type) + ")");
//					test.log(Status.INFO, xc.getCellString(i, logCons.Test_Cases));
//					test.log(Status.INFO, "Running existing instance for: " + xc.getCellString(i, logCons.State));
					System.out.println("Running exsisting instance for: " + xc.getCellString(i, logCons.State));
					try {
						lokosTest.startApp(xc.getCellString(i, logCons.Excel_Sheet_Names), xc.getCellString(i, logCons.App));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Program startApp Failed");
						e.printStackTrace();
					}
				}

			} else {

			}
			System.out.println("===================================================> " + i + "\n");
		}catch(Exception e) {
			System.out.println("Start Action: "+startAction);
			System.out.println("EndAction"+endAction);
			e.printStackTrace();
		}
		}
//		appdriver.quit();
		try {
			xc.closeReaders();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All States Automation Tests Complete");
		}catch(Exception e) {
			e.printStackTrace();
		}
//		reports.flush();
		
	}
		

}
