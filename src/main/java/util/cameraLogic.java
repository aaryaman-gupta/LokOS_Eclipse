package util;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileBy;
import lokos.lokosTest;

public class cameraLogic extends lokosTest {
	public static void click() throws InterruptedException  {
		if (app.launchAppConstants.UDID.equals("emulator-5554")) {

		} else if (app.launchAppConstants.UDID.equals("RZ8R30JW2VM")) {
			appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			appdriver.findElementById("com.sec.android.app.camera:id/normal_center_button").click();
			appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"OK\")")).click();
			Thread.sleep(1000);
		}
	}
}
