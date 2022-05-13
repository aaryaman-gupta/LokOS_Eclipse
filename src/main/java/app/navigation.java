package app;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileBy;
import lokos.lokosTest;
import util.MobileTouch;
import util.MobileTouchAdv;

public class navigation extends lokosTest {

	public static void shgButton() throws InterruptedException {
		appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		appdriver.findElementById("com.microware.cdfi:id/tbl_shg").click();
		Thread.sleep(2000);
	}

	public static void navToVillage(int row) throws InterruptedException {
		xc.changeSheet("SHGs");
		appdriver.findElementById("com.microware.cdfi:id/spin_panchayat").click();
		appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		appdriver.findElement(MobileBy
				.AndroidUIAutomator("new UiSelector().text(\"" + xc.getCellString(row, profileCons.gpColNum) + "\")"))
				.click();
		appdriver.findElementById("com.microware.cdfi:id/spin_village").click();
		appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		appdriver.findElement(MobileBy.AndroidUIAutomator(
				"new UiSelector().text(\"" + xc.getCellString(row, profileCons.villageColNum) + "\")")).click();
		
	}

	public static void existingSHG(int row) throws Exception {
		String shgName=xc.getCellString(row, profileCons.shgNameColNum).toUpperCase();
		System.out.println("Navigating to "+shgName);
		MobileTouchAdv mta=new MobileTouchAdv(appdriver);
		mta.scrollToText(shgName, "top",0.60,0.80);
	}
	public static void existingSHG_Error(int row) throws Exception {
		String shgName=xc.getCellString(row, regCons.SHG_Reference).toUpperCase();
		System.out.println("Navigating to "+shgName);
		mt.scrollToText(shgName, "top");
	}

	public static void newSHG() {
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
		String shg= xc.getCellString(row, profileCons.shgNameColNum);
		appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" +shg+ "\")")).click();		
	}
	
	public static void openSHGMeetings(int row) {
		boolean flag = false;
		int i = 1;	
//		
		while (!flag) {
			String s = appdriver.findElementByXPath("//android.widget.LinearLayout["+i+"]/android.widget.LinearLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.TextView[2]").getText();
			if (s.equalsIgnoreCase(xc.getCellString(row, profileCons.shgNameColNum))) {
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
			if (s.equalsIgnoreCase(xc.getCellString(row, regCons.SHG_Reference))) {
				flag = true;
				appdriver.findElementByXPath(
						"//android.widget.LinearLayout[" + i + "]/android.widget.LinearLayout/android.widget.ImageView"
						)
						.click();				
			}
			i++;
		}		
	}

	public static void openSHGMembers(int row) throws InterruptedException {
		boolean flag = false;
		int i = 1;
		while (!flag) {
			try {
//				shgProfileCreation.navigateBackToScreen("SHG");
//				Thread.sleep(2000);
//				mt.scrollToText(xc.getCellString(row, profileCons.shgNameColNum), "bottom");
				Thread.sleep(3000);
				appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				String s = appdriver.findElementByXPath("//android.widget.LinearLayout[" + i
						+ "]/android.widget.LinearLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.TextView")
						.getText();
				if (s.equalsIgnoreCase(xc.getCellString(row, profileCons.shgNameColNum))) {
					flag = true;
					Thread.sleep(1500);
					appdriver.findElementByXPath("//android.widget.LinearLayout[" + i
							+ "]/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.TextView")
							.click();

				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Next SHG name");
			}
			i++;
		}
	}

	public static void newMember() {
		appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		appdriver.findElementById("com.microware.cdfi:id/tbl_add").click();
	}

	public static void existingMember(int row) {
		xc.changeSheet("Members");
		MobileTouch mt = new MobileTouch(appdriver);
		mt.scrollToText(xc.getCellString(row, memCons.nameColNum), "top");
		String name=xc.getCellString(row, memCons.nameColNum) ;
		appdriver.findElement(MobileBy
				.AndroidUIAutomator("new UiSelector().text(\"" + name+ "\")"))
				.click();

	}

}
