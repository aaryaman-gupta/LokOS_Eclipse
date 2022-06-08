package util;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class MobileTouchAdv {

	// scroll from different coordinates
	// scroll from different elements

	public static double TOP_MARGIN = .35;
	public static double BOTTOM_MARGIN = .70;
	public static final double LEFT_MARGIN = .05;
	public static final double RIGHT_MARGIN = .95;
	public static final String DIRECTION_TOP = "top";
	public static final String DIRECTION_BOTTOM = "bottom";
	public static final String DIRECTION_RIGHT = "right";
	public static final String DIRECTION_LEFT = "left";
	public static final int SCROLL_DURATION = 1;
	public static final int MAX_SCROLL_COUNTER = 25;

	AndroidDriver<AndroidElement> driver;
	Dimension dim;
	DeviceUtil device;
	@SuppressWarnings("rawtypes")
	TouchAction act;

	@SuppressWarnings("rawtypes")
	public MobileTouchAdv(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
		dim = driver.manage().window().getSize();
		device = new DeviceUtil(driver);
		act = new TouchAction(driver);
	}

	public void scrollToText(String text, String direction,double mrgn1,double mrgn2) {
		// AndroidElement e = driver.findElement(MobileBy.AndroidUIAutomator("new
		// UiSelector().text(\""+text+"\")"));
		// first find the presence
		scrollToVisibleElementOnScreen("new UiSelector().text(\"" + text + "\")", "androidUIAutomatior", direction,mrgn1,mrgn2);

	}

	// viable to make
	public void scrollToVisibleElementOnScreen(String locator, String locatorStrat, String direction,double mrgn1, double mrgn2) {
		int counter = 0;
		boolean exceeded_counter_flag=false;
		
		TOP_MARGIN=mrgn1;
		BOTTOM_MARGIN=mrgn2;
		
		while (!device.isElementPresent(locator, locatorStrat)) {
			counter++;
			if (direction.equals(DIRECTION_TOP)) {
				int startY = getEndPointOfScreen(DIRECTION_BOTTOM);
				int endY = getEndPointOfScreen(DIRECTION_TOP);
				verticalScroll(startY, endY);
			} else if (direction.equals(DIRECTION_BOTTOM)) {
				int endY = getEndPointOfScreen(DIRECTION_BOTTOM);
				int startY = getEndPointOfScreen(DIRECTION_TOP);
				verticalScroll(startY, endY);
			} else if (direction.equals(DIRECTION_RIGHT)) {

			} else if (direction.equals(DIRECTION_LEFT)) {

			}
			if (counter == MAX_SCROLL_COUNTER) {
				System.out.println("Max Scrolling Exceeded");
				exceeded_counter_flag=true;
				break;
			}
		}
		if(!exceeded_counter_flag)
			scrollTowards(locator, locatorStrat, direction);
		// do the adjustment

	}

	// assume that element is found and visible
	public void scrollTowards(String locator, String locatorStrat, String direction) {
		if (direction.equals(DIRECTION_TOP)) {
			int topY = getEndPointOfScreen(DIRECTION_TOP);

			AndroidElement e = null;

			if (locatorStrat.equals("id"))
				e = driver.findElement(By.id(locator));
			else if (locatorStrat.equals("xpath"))
				e = driver.findElement(By.xpath(locator));
			else if (locatorStrat.equals("androidUIAutomatior"))
				e = driver.findElement(MobileBy.AndroidUIAutomator(locator));

			verticalScroll(e.getLocation().y, topY+100);
		} else if (direction.equals(DIRECTION_BOTTOM)) {
			int bottomY = getEndPointOfScreen(DIRECTION_BOTTOM);

			AndroidElement e = null;

			if (locatorStrat.equals("id"))
				e = driver.findElement(By.id(locator));
			else if (locatorStrat.equals("xpath"))
				e = driver.findElement(By.xpath(locator));
			else if (locatorStrat.equals("androidUIAutomatior"))
				e = driver.findElement(MobileBy.AndroidUIAutomator(locator));

			verticalScroll(e.getLocation().y, bottomY - 20);
		}

	}

	public int getCenterXofElement(AndroidElement e) {
		return e.getLocation().x + (e.getSize().width / 2);
	}

	public int getCenterYofElement(AndroidElement e) {
		return e.getLocation().y + (e.getSize().height / 2);
	}

	public int getEndPointOfScreen(String direction) {
		int height = dim.height;
		int width = dim.width;

		if (direction.equals(DIRECTION_TOP)) {
			return (int) (height * TOP_MARGIN);
		} else if (direction.equals(DIRECTION_BOTTOM)) {
			return (int) (height * BOTTOM_MARGIN);
		} else if (direction.equals(DIRECTION_RIGHT)) {
			return (int) (width * RIGHT_MARGIN);
		} else if (direction.equals(DIRECTION_LEFT)) {
			return (int) (width * LEFT_MARGIN);
		}

		return -1;

	}

	public void horizontalScroll(int startX, int endX) {
		int middleY = dim.height / 2;

		act.press(PointOption.point(startX, middleY))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(SCROLL_DURATION)))
				.moveTo(PointOption.point(endX, middleY)).release().perform();

	}

	public void verticalScroll(int startY, int endY) {

		int middleX = dim.width / 2;
		act.press(PointOption.point(middleX, startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(SCROLL_DURATION)))
				.moveTo(PointOption.point(middleX, endY)).release().perform();

	}

}
