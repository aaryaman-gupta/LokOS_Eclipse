package allAppLogins;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import appLaunchLogin.launchAppConstants;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class installLokos extends accounts{

	public static void launchLokosStates(int row) {
		// TODO Auto-generated method stub
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, launchAppConstants.device_name);
		cap.setCapability(MobileCapabilityType.UDID, launchAppConstants.UDID);
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, launchAppConstants.platform_name);
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, launchAppConstants.platform_version);

		cap.setCapability(MobileCapabilityType.APP, new File(
				System.getProperty("user.dir") + "\\apk\\" + xc.getCellString(row, launchAppConstants.apkNameColNum))
						.getAbsolutePath());
		cap.setCapability(MobileCapabilityType.NO_RESET, "false");// clear the cache
		cap.setCapability(MobileCapabilityType.FULL_RESET, "false");// dont uninstall
//		
		cap.setCapability("autoGrantPermissions", "true");
		try {
			appdriver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub"), cap);
			Thread.sleep(7000);
//			appdriver.quit();
		} catch (Exception e) {
			System.out.println("Launch Error");
		}

	}

}
