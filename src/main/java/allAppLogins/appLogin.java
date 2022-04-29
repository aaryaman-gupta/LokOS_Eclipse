package allAppLogins;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.StaleElementReferenceException;

import app.loginConstants;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import util.DeviceUtil;
import util.MobileTouchAdv;

public class appLogin extends accounts{

	public static void login(int row) throws InterruptedException {

		Thread.sleep(5000);
		appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		appdriver.findElementById(loginConstants.stateDDown).click();
//		AndroidElement e= (AndroidElement) appdriver.findElement(MobileBy.AndroidUIAutomator());

		MobileTouchAdv mt = new MobileTouchAdv(appdriver);
		mt.scrollToVisibleElementOnScreen("new UiSelector().textContains(\""
				+ xc.getCellString(row, loginConstants.stateColNum).toUpperCase() + "\")", "androidUIAutomatior", "top",0.40,0.60);
		AndroidElement e = appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\""
				+ xc.getCellString(row, loginConstants.stateColNum).toUpperCase() + "\")"));
		int x = e.getLocation().x;
		int y = e.getLocation().y;
		@SuppressWarnings("rawtypes")
		TouchAction act = new TouchAction(appdriver);
		act.tap(PointOption.point(x + 10, y + 10)).perform();

		appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		appdriver
				.findElementById(
						"com.microware.cdfi:id/tv" + xc.getCellString(row, loginConstants.languageColNum).toLowerCase())
				.click();
		appdriver.findElementById(loginConstants.submitButton).click();
		appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		appdriver.findElementById(loginConstants.okButton).click();

		Thread.sleep(2000);
		appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		appdriver.findElementById(loginConstants.userRoleDDown).click();
		appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""+xc.getCellString(row, 3)+"\")")).click();
		appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		appdriver.findElementById(loginConstants.username).sendKeys(xc.getCellString(row, loginConstants.usernameColNum));
		appdriver.findElementById(loginConstants.password).sendKeys(xc.getCellString(row, loginConstants.passwordColNum));
		appdriver.findElementById(loginConstants.loginButton).click();

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

		appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		appdriver.findElementById(loginConstants.enterOTP).sendKeys("1111");
		appdriver.findElementById(loginConstants.confirmOTP).sendKeys("1111");
//		appdriver.findElementById("").click();

		sync();

	}

	public static void sync() throws InterruptedException {

		appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		appdriver.findElementById(loginConstants.profileVerButton).click();

		Thread.sleep(2000);
		appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		appdriver.findElementById(loginConstants.completeData).click();

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
}
