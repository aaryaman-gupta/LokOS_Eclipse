package util;

import java.util.Date;

import io.appium.java_client.MobileBy;
import lokos.lokosTest;

@SuppressWarnings("unused")
public class dateLogic extends lokosTest {

	public static void datePicker(String date) throws Exception {

		String date2 = date.replaceAll("_", " ");
		String[] d = date2.split(" ");

		appdriver.findElementById("android:id/date_picker_header_year").click();
		mt.scrollToVisibleElementOnScreen("//*[@class=\"android.widget.TextView\"][@text=\"" + d[2] + "\"]", "xpath",
				"bottom");
		appdriver.findElementByXPath("//*[@class=\"android.widget.TextView\"][@text=\"" + d[2] + "\"]").click();
		int trial = 0;
		while ((!du.isElementPresent("new UiSelector().description(\"" + date2 + "\")", "androidUIAutomatior"))
				&& (trial < 10)) {
			if (monthLevel(d[1]))
				appdriver.findElementById("android:id/next").click();
			else
				appdriver.findElementById("android:id/prev").click();
			trial++;

		}
		if (trial == 12) {
			throw new Exception("Year not allowed");
		}

		appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().description(\"" + date2 + "\")")).click();
		appdriver.findElementById("android:id/button1").click();

	}

	public static boolean monthLevel(String month) {

//		Date d = new Date();
//		String[] d2=d.toString().replaceAll(":", " ").split(" ");
//		String d3=d.toString().replaceAll(":"," ");
			
		String d = appdriver
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"1\")")).getAttribute("content-desc");
		String[] d2=d.split(" ");
		
		String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		String[] months2 = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

		int d2index = 0;
		int monthindex = 0;
		for (int i = 0; i < 12; i++) {
			if (d2[1].contains(months[i]))
				d2index = i;
			if (month.equals(months[i]))
				monthindex = i;
		}

		if (d2index > monthindex)
			return false;
		else
			return true;

	}
	
	public static void datePicker(String date, String idLocator) {
		date=date.replaceAll("_","-");
		appdriver.findElementById(idLocator).sendKeys(date);
	}

}
