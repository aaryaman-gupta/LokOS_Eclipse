package appCLF;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileBy;
import lokos.lokosTest;
import util.MobileTouchAdv;

public class navigationCLF extends lokosTest {

	public static void clfButton() throws InterruptedException {
		appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		appdriver.findElementById("com.microware.cdfi:id/tbl_vo").click();
		Thread.sleep(2000);
	}

	public static void navToBlock(int row) throws InterruptedException {
		xc.changeSheet("CLFs");
		appdriver.findElementById("com.microware.cdfi:id/spin_block").click();
		appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		int i = 0;
		while (i < 6) {
			try {
				Thread.sleep(2000);
				appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				appdriver.findElement(MobileBy
						.AndroidUIAutomator("new UiSelector().text(\"" + xc.getCellString(row, clfCons.Block_0) + "\")"))
						.click();
				i = 6;
			} catch (Exception e) {
				System.out.println("Wasn't able to press the gp button");
				i++;
			}
		}				
		
	}

	public static void existingSHG(int row) throws Exception {
		String shgName=xc.getCellString(row, clfCons.CLF_Name_1).toUpperCase();
		System.out.println("Navigating to "+shgName);
		MobileTouchAdv mta=new MobileTouchAdv();
		mta.scrollToText(shgName, "top",0.60,0.80);
	}
	public static void existingSHG_Error(int row) throws Exception {
		String shgName=xc.getCellString(row, clfCons.CLF_Name_1).toUpperCase();
		System.out.println("Navigating to "+shgName);
		mt.scrollToText(shgName, "top");
	}

	public static void newCLF() {
		appdriver.findElementById("com.microware.cdfi:id/IvAdd").click();
	}

	public static void openSHGProfile(int row) {
//		boolean flag = false;
//		int i = 1;	
//		
//		while (!flag) {
//			appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//			String s = appdriver.findElement(MobileBy
//					.AndroidUIAutomator("new UiSelector().resourceId(\"com.microware.cdfi:id/tv_nam\").index("+(i-1)+")")).getText();
//			if (s.equalsIgnoreCase(xc.getCellString(row, profileCons.shgNameColNum))) {
//				flag = true;
//				appdriver.findElementByXPath(
//						"//android.widget.LinearLayout[" + i + "]/android.widget.LinearLayout/android.widget.ImageView"
//						)
//						.click();
//				appdriver.findElementById("com.microware.cdfi:id/tvShg").click();
//
//			}
//			i++;
//		}
		String shg= xc.getCellString(row,clfCons.CLF_Name_1);
		appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" +shg+ "\")")).click();		
	}
	
	public static void openSHGMeetings(int row) {
		boolean flag = false;
		int i = 1;	
//		
		while (!flag) {
			String s = appdriver.findElementByXPath("//android.widget.LinearLayout["+i+"]/android.widget.LinearLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.TextView[2]").getText();
			if (s.equalsIgnoreCase(xc.getCellString(row, clfCons.CLF_Name_1))) {
				flag = true;
				appdriver.findElementByXPath(
						"//android.widget.LinearLayout[" + i + "]/android.widget.LinearLayout/android.widget.ImageView"
						)
						.click();				
			}
			i++;
		}		
	}
	public static void openSHGMeetings_Error(int row) {
		boolean flag = false;
		int i = 1;	
//		
		while (!flag) {
			String s = appdriver.findElementByXPath("//android.widget.LinearLayout["+i+"]/android.widget.LinearLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.TextView[2]").getText();
			if (s.equalsIgnoreCase(xc.getCellString(row, clfCons.CLF_Name_1))) {
				flag = true;
				appdriver.findElementByXPath(
						"//android.widget.LinearLayout[" + i + "]/android.widget.LinearLayout/android.widget.ImageView"
						)
						.click();				
			}
			i++;
		}		
	}



}
