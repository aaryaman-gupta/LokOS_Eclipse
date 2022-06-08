package allAppLogins;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.StaleElementReferenceException;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import appLaunchLogin.loginConstants;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import util.DeviceUtil;
import util.MobileTouchAdv;

public class appLoginTest extends accountsTest{

	public static void login(int row,String role,ExtentTest test) throws InterruptedException {
		
		
		MobileTouchAdv mt = new MobileTouchAdv(appdriver);
		Thread.sleep(5000);
		
			
		String state[] = xc.getCellString(row, logCons.State).split(",");
		for (int i = 0; i < state.length; i++) {
			try {
				if (!state[i].equals("Empty")) {
					startAction = "State";
					appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					appdriver.findElementById("com.microware.cdfi:id/spin_state").click();
					mt.scrollToVisibleElementOnScreen(
							"new UiSelector().textContains(\"" + state[i].toUpperCase() + "\")", "androidUIAutomatior",
							"top", 0.40, 0.60);
					AndroidElement e = appdriver.findElement(MobileBy
							.AndroidUIAutomator("new UiSelector().textContains(\"" + state[i].toUpperCase() + "\")"));
					int x = e.getLocation().x;
					int y = e.getLocation().y;
					@SuppressWarnings("rawtypes")
					TouchAction act = new TouchAction(appdriver);
					act.tap(PointOption.point(x + 10, y + 10)).perform();
					appLoginTest.status(test, row, state[i], "State", "pass");
					endAction = "State";
				}
			} catch (Exception e) {
				appLoginTest.status(test, row, state[i], "State", "fail");
				e.printStackTrace();
			}
		}		
		
		String language[] = xc.getCellString(row, logCons.Second_Language).split(",");
		for (int i = 0; i < language.length; i++) {
			try {
				if (!language[i].equals("Empty")) {
					startAction = "Second Language";
					appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					appdriver
							.findElementById(
									"com.microware.cdfi:id/tv" +language[i].toLowerCase())
							.click();
					appLoginTest.status(test, row, language[i], "Second Language", "pass");
					endAction = "Second Language";
				}
			} catch (Exception e) {
				appLoginTest.status(test, row, language[i], "Second Language", "fail");
				e.printStackTrace();
			}
		}
		startAction="Submit Button";
		appdriver.findElementById(loginConstants.submitButton).click();
		endAction="Submit Button";
		
		startAction="OK Button";
		appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		appdriver.findElementById(loginConstants.okButton).click();
		endAction="OK Button";
		

		Thread.sleep(2000);
		
		String userrole[] = xc.getCellString(row, logCons.UserRole).split(",");
		for (int i = 0; i < userrole.length; i++) {
			try {
				if (!userrole[i].equals("Empty")) {
					startAction = "User Role";
					appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					appdriver.findElementById(loginConstants.userRoleDDown).click();
					appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""+userrole[i]+"\")")).click();
					appLoginTest.status(test, row, userrole[i], "User Role", "pass");
					endAction = "User Role";
				}
			} catch (Exception e) {
				appLoginTest.status(test, row, userrole[i], "User Role", "fail");
				e.printStackTrace();
			}
		}
		
		String userid[] = xc.getCellString(row, logCons.UserID).split(",");
		for (int i = 0; i < userid.length; i++) {
			try {
				if (!userid[i].equals("Empty")) {
					startAction = "User ID";
					appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					appdriver.findElementById(loginConstants.username).sendKeys(xc.getCellString(row, logCons.UserID));
					appLoginTest.status(test, row, userid[i], "User ID", "pass");
					endAction = "User ID";
				}
			} catch (Exception e) {
				appLoginTest.status(test, row, userid[i], "User ID", "fail");
				e.printStackTrace();
			}
		}
		
		String password[] = xc.getCellString(row, logCons.Password).split(",");
		for (int i = 0; i < password.length; i++) {
			try {
				if (!password[i].equals("Empty")) {
					startAction = "Password";
					appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					appdriver.findElementById(loginConstants.password).sendKeys(xc.getCellString(row, logCons.Password));
					appLoginTest.status(test, row, password[i], "Password", "pass");
					endAction = "Password";
				}
			} catch (Exception e) {
				appLoginTest.status(test, row, password[i], "Password", "fail");
				e.printStackTrace();
			}
		}		
		
		startAction="Login Button";
		appdriver.findElementById(loginConstants.loginButton).click();
		endAction="Login Button";
		

		DeviceUtil du = new DeviceUtil(appdriver);
		// check for downloading master data
		boolean message_flag = true;
		Thread.sleep(5000);
		try {
			while (message_flag)
				message_flag = du.isElementPresent(loginConstants.alert1, loginConstants.locStrat1);
		} catch (StaleElementReferenceException err) {
		}
		Thread.sleep(2000);

		startAction="Enter OTP";
		appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		appdriver.findElementById(loginConstants.enterOTP).sendKeys("1111");
		appdriver.findElementById(loginConstants.confirmOTP).sendKeys("1111");
		endAction="Enter OTP";
//		appdriver.findElementById("").click();

		startAction="Sync";
		sync(role);
		endAction="Sync";
	}

	public static void sync(String role) throws InterruptedException {

		appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		appdriver.findElementById(loginConstants.profileVerButton).click();
		
		if (role.equals("SHG")) {
			Thread.sleep(2000);
			appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			appdriver.findElementById(loginConstants.completeData).click();
		}else {
			Thread.sleep(2000);
			appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			appdriver.findElementById(loginConstants.completeData2).click();
		}

		appdriver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
				.equals("Data Downloaded successfully")) {	
			System.out.println("\n  --Complete Data Downloaded--\n");
		}else {
			System.out.println("\n  --Error in syncing data--.\n");
			System.out.println("Error: "+appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText());
		}

		appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
//		appdriver.findElementById(loginConstants.syncHome).click();
		
		Thread.sleep(5000);
	}
	
	public static void status(ExtentTest test,int row,String entry,String field,String result) {
		
		System.out.println(field+" selection--->"+entry);
		if(xc.getCellString(row, logCons.Test_Type).equals("Positive")) {
			if(result.equals("pass")) {
				System.out.println(field+": Pass");
				test.log(Status.PASS, field+" -> "+entry);
			}else if(result.equals("fail")) {
				System.out.println(field+": Fail");
				test.log(Status.FAIL, field+" -> "+entry);
			}
		}else if(xc.getCellString(row, logCons.Test_Type).equals("Negetive")) {
			if(result.equals("pass")) {
				System.out.println(field+": Fail");
				test.log(Status.FAIL, field+" -> "+entry);
			}else if(result.equals("fail")) {
				System.out.println(field+": Pass");
				test.log(Status.PASS, field+" -> "+entry);				
			}
		}		
	}
}
