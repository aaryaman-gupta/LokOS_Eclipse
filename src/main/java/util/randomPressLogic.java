package util;

import java.time.Duration;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lokos.lokosTest;

public class randomPressLogic extends lokosTest{
	
	public static void press(double d,double e) {
		
		org.openqa.selenium.Dimension size = appdriver.manage().window().getSize();
		int xWidth = (int) (size.getWidth() * d);
		int yHeight=(int)(size.getHeight()*e);
		@SuppressWarnings("rawtypes")
		TouchAction act=new TouchAction(appdriver);
		
		act
        .tap(PointOption.point(xWidth,yHeight))
        .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).perform();
//		act.press(PointOption.point(xWidth, yWidth))
//		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
//		.moveTo(PointOption.point(xWidth+10, yWidth)).release().perform();
	}

}
