package appLaunchLogin;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import lokos.lokosTest;

@SuppressWarnings("unused")
public class launchLokOS extends lokosTest {

	public static void launchLokos(String apk) {
		// TODO Auto-generated method stub
		xc.changeSheet("Login");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, launchAppConstants.device_name);
		cap.setCapability(MobileCapabilityType.UDID, launchAppConstants.UDID);
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, launchAppConstants.platform_name);
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, launchAppConstants.platform_version);
		cap.setCapability(MobileCapabilityType.APP, new File(
				System.getProperty("user.dir") + "\\apk\\" + apk)
						.getAbsolutePath());
		cap.setCapability(MobileCapabilityType.NO_RESET, "true");// clear the cache
		cap.setCapability(MobileCapabilityType.FULL_RESET, "false");// dont uninstall
		cap.setCapability (MobileCapabilityType.NEW_COMMAND_TIMEOUT, "50");
//		
//		cap.setCapability("autoGrantPermissions", "true");
		try {
			System.out.println("Launching appium and opening app");
			Thread.sleep(3000);
			appdriver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub"), cap);
			Thread.sleep(7000);
//			appdriver.quit();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	
}