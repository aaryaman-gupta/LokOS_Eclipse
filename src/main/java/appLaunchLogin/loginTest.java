package appLaunchLogin;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.StaleElementReferenceException;

import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import lokos.lokosTest;
import util.DeviceUtil;
import util.MobileTouch;

public class loginTest extends lokosTest {

	public static void login() throws InterruptedException {

		appdriver.findElementById(loginConstants.stateDDown).click();
//		AndroidElement e= (AndroidElement) appdriver.findElement(MobileBy.AndroidUIAutomator());

		MobileTouch mt = new MobileTouch(appdriver);
		mt.scrollToVisibleElementOnScreen("new UiSelector().textContains(\""
				+ xc.getCellString(1, loginConstants.stateColNum).toUpperCase() + "\")", "androidUIAutomatior", "top");
		AndroidElement e = appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\""
				+ xc.getCellString(1, loginConstants.stateColNum).toUpperCase() + "\")"));
		int x = e.getLocation().x;
		int y = e.getLocation().y;
		@SuppressWarnings("rawtypes")
		TouchAction act = new TouchAction(appdriver);
		act.tap(PointOption.point(x + 10, y + 10)).perform();

		appdriver
				.findElementById(
						"com.microware.cdfi:id/tv" + xc.getCellString(1, loginConstants.languageColNum).toLowerCase())
				.click();
		appdriver.findElementById(loginConstants.submitButton).click();
		appdriver.findElementById(loginConstants.okButton).click();

		Thread.sleep(2000);
		appdriver.findElementById(loginConstants.userRoleDDown).click();
		appdriver.findElement(MobileBy.AndroidUIAutomator(loginConstants.userRole)).click();
		appdriver.findElementById(loginConstants.username).sendKeys(xc.getCellString(1, loginConstants.usernameColNum));
		appdriver.findElementById(loginConstants.password).sendKeys(xc.getCellString(1, loginConstants.passwordColNum));
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

		appdriver.findElementById(loginConstants.enterOTP).sendKeys("1111");
		appdriver.findElementById(loginConstants.confirmOTP).sendKeys("1111");
//		appdriver.findElementById("").click();

		sync();

	}

	public static void sync() throws InterruptedException {

		appdriver.findElementById(loginConstants.profileVerButton).click();

		Thread.sleep(2000);
		appdriver.findElementById(loginConstants.completeData).click();

		appdriver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
				.equals("Data Downloaded successfully")) {
			test.log(Status.PASS,"Complete Data Downloaded");
			System.out.println("\n  --Complete Data Downloaded--\n");
		}else {
			test.log(Status.FAIL, "Error in syncing data");
			System.out.println("\n  --Error in syncing data--.\n");
		}

		appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
		appdriver.findElementById(loginConstants.syncHome).click();

		Thread.sleep(5000);
	}

}
