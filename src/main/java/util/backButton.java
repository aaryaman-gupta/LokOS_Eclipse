package util;

import appLaunchLogin.launchAppConstants;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import lokos.lokosTest;

public class backButton extends lokosTest {

	public static void back() {
		if(launchAppConstants.UDID.equals("emulator-5554")) {
			randomPressLogic.press(0.5, 0.05);
			appdriver.findElementById("com.microware.cdfi:id/ic_Back").click();
		}
		else if(launchAppConstants.UDID.equals("RZ8R30JW2VM")) {
			appdriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
		}
	}

}
