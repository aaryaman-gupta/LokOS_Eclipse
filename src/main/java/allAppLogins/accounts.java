package allAppLogins;


import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import lokos.lokosTest;
import readingXLS.xlsClasses;
import util.DeviceUtil;

public class accounts {
	
	public static xlsClasses xc = null;
	public static AndroidDriver<AndroidElement> appdriver = null;
	public static DeviceUtil du = null;
	
	@Test
//	public static void acc() {
	public static void main(String[] args) {
		
		xc = new xlsClasses(System.getProperty("user.dir") + "\\XLS data\\States Login.xlsx", "Login Id");
		int[] dimensions = xc.getRowCols("Login Id");
		int row = dimensions[0];
		
		for (int i = 1; i < row; i++) {

			if (xc.getCellString(i, 0).equalsIgnoreCase("Y")) {
				if (xc.getCellString(i, 10).equalsIgnoreCase("Y")) {
					System.out.println("Running new instance for: " + xc.getCellString(i, 1));
					allAppLogins.installLokos.launchLokosStates(i);
					System.out.println("Lokos Successfully Launched");
					try {
						allAppLogins.appLogin.login(i);
					
					System.out.println("Login and Sync Complete");
					xc.changeSheet("Login Id");
					try {
						lokosTest.startApp(xc.getCellString(i, 7));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Program startApp Failed");
						e.printStackTrace();
					}
					} catch (Exception e) {

						System.out.println("Login failed for " + i + " row.");
						e.printStackTrace();
					}
				}else if(xc.getCellString(i, 10).equalsIgnoreCase("N")) {
					System.out.println("Running exsisting instance for: " + xc.getCellString(i, 1));					
					try {
						lokosTest.startApp(xc.getCellString(i, 7));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Program startApp Failed");
						e.printStackTrace();
					}
				}

			} else {

			}
		}
	}

}
