package util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class DeviceUtil {

	public AndroidDriver<AndroidElement> driver;
	int clickWaitingTime = 4;
	int maxClicks = 2;

	public DeviceUtil(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
	}

	public void waitForClickToBeDone(String locator, String locatorStrat) {
		// attempt to click
		// success - return
		// no success - wait - again try to click

		// extract the element
		AndroidElement e = null;

		if (locatorStrat.equals("id"))
			e = driver.findElement(By.id(locator));
		else if (locatorStrat.equals("xpath"))
			e = driver.findElement(By.xpath(locator));

		waitForClickToBeDone(e);

		// not able to click - report error

	}

	public void waitForClickToBeDone(MobileElement e) {

		int count = 1;
		while (count <= maxClicks) {
			System.out.println("Count " + count);
			String activityBeforeClick = driver.currentActivity();
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(e));
			e.click();
			String activityAfterClick = driver.currentActivity();

			if (!activityBeforeClick.equals(activityAfterClick))// successfully click
				return;
			wait(clickWaitingTime);
			count++;
		}
	}

	// present and visible - true
	// present and not visible - false
	// not present - false
	public boolean isElementPresent(String locator, String locatorStrat) {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		// presence
		MobileElement e = null;
		try {
			if (locatorStrat.equals("id"))
				e = driver.findElement(By.id(locator));
			else if (locatorStrat.equals("xpath"))
				e = driver.findElement(By.xpath(locator));
			else if (locatorStrat.equals("androidUIAutomatior"))
				e = driver.findElement(MobileBy.AndroidUIAutomator(locator));
		} catch (Exception ex) {
			return false;
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		// visibility
		if (e.isDisplayed())
			return true;
		else
			return false;
	}

	public void wait(int waitTimeInSec) {
		try {
			Thread.sleep(waitTimeInSec * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
