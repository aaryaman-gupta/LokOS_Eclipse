package app;

import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.Status;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import lokos.lokosTest;
import util.summary;

public class shgMeetings extends lokosTest {

	public static int[] meetingNum(int row) throws Exception {
		try {
			boolean cn = xc.getCellString(row, profileCons.flowTypeColNum).equalsIgnoreCase("Cutoff New");
			boolean cu = xc.getCellString(row, profileCons.flowTypeColNum).equalsIgnoreCase("Cutoff Update");
			boolean rn = xc.getCellString(row, profileCons.flowTypeColNum).equalsIgnoreCase("Regular New");
			boolean ru = xc.getCellString(row, profileCons.flowTypeColNum).equalsIgnoreCase("Regular Update");

			appdriver.findElementById("com.microware.cdfi:id/tbl_mymeeting").click();
			navigation.existingSHG(row);
			navigation.openSHGMeetings(row);
			appdriver.findElementById("com.microware.cdfi:id/tv_sync").click();
			appdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
			navigation.openSHGMeetings(row);

			int val[] = { 0, 0, 0 };

			if (cn || rn) {
				appdriver.findElementById("com.microware.cdfi:id/tbl_generatemeeting").click();
				if (appdriver.findElementById("com.microware.cdfi:id/tv_title").getText().equals("Generate Meeting")) {
					testFlow.log(Status.INFO, "Generation of Meeting is possible");
					System.out.println("Generation of Meeting is possible");
				} else {
					String err = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
					System.out.println("Err: " + err);
					testFlow.log(Status.FAIL, err);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					navigateBackToScreen("SHG");
					return val;
				}
			} else if (cu || ru) {
				appdriver.findElementById("com.microware.cdfi:id/tbl_open_meeting").click();
				if (cu) {
					String title = "";
					try {
						title = appdriver.findElementById("com.microware.cdfi:id/tv_title").getText();
					} catch (Exception e) {
						String err = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
						System.out.println("Error: " + err);
						testFlow.log(Status.FAIL, err);
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
						navigateBackToScreen("SHG");
						return val;
					}
					if (title.equals("Cut Off Menu")) {
						testFlow.log(Status.INFO, "Opening Existing Cutoff Meeting is possible");
						System.out.println("Opening Existing Cutoff is possible");
					} else {
						System.out.println("Error--> Cannot open Cutoff Menu for Update");
						testFlow.log(Status.FAIL, "Cannot open Cutoff Menu for Update");
						navigateBackToScreen("SHG");
						return val;
					}

				} else if (ru) {
					String title = "";
					try {
						title = appdriver.findElementById("com.microware.cdfi:id/tv_title").getText();
					} catch (Exception e) {
						String err = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
						System.out.println("Error: " + err);
						testFlow.log(Status.FAIL, err);
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
						navigateBackToScreen("SHG");
						return val;
					}
					if (title.equals("Meeting Menu")) {
						testFlow.log(Status.INFO, "Opening Existing Regular Meeting is possible");
						System.out.println("Opening Existing Regular Meeting is possible");
					} else {
						System.out.println("Error--> Cannot open Existing Regular Meeting for Update");
						testFlow.log(Status.FAIL, "Cannot open Existing Regular Meeting for Update");
						navigateBackToScreen("SHG");
						return val;
					}
				}

			} else {
				System.out.println("Error-->Flow Type Doesn't Exist");
				testFlow.log(Status.FAIL, "Flow Type Doesn't Exist");
				navigateBackToScreen("SHG");
				return val;
			}

			if (cu || cn) {
				String shg = xc.getCellString(row, profileCons.shgNameColNum);
				String flow = xc.getCellString(row, profileCons.flowTypeColNum);
				testFlow.log(Status.INFO, "Starting Cutoff Meeting for " + shg + "(" + flow + ")");
				System.out.println("Starting Cutoff Meeting for " + shg + "(" + flow + ")");
				xc.changeSheet("Cutoff Meetings");
				++cutoffRow;
				testMeet = testFlow.createNode("Cutoff Meeting: " + xc.getCellString(cutoffRow, cutoffCons.shgColNum)
						+ "(" + xc.getCellString(cutoffRow, cutoffCons.typeColNum) + ")");
				//////////////////////////////////////
				val = cutoffMeetings.idSelectCutoff(cutoffRow);
				//////////////////////////////////////
				int[][] zeroIni = new int[cutoff_check[0].length][cutoff_check[1].length];
				summary.display(cutoff_check, testMeet);
				cutoff_check = zeroIni;			
				
			} else if (rn || ru) {
				testFlow.log(Status.INFO,
						"Starting Regular Meeting for " + xc.getCellString(row, profileCons.shgNameColNum));
				System.out.println("Starting Regular Meeting for " + xc.getCellString(row, profileCons.shgNameColNum));

				xc.changeSheet("Regular Meetings");
				++regularRow;
				testMeet = testFlow.createNode("SHG Meeting: " + xc.getCellString(regularRow, regCons.shgColNum) + "("
						+ xc.getCellString(regularRow, regCons.typeColNum) + ")");
				/////////////////////////////////////////
				val = regularMeetings.idSelectRegular(regularRow);
				/////////////////////////////////////////
				int[][] zeroIni = new int[reg_check[0].length][reg_check[1].length];
				summary.display(reg_check, testMeet);
				reg_check = zeroIni;
			}
			
			
			return val;

		} catch (NullPointerException e) {
			testMeet.log(Status.FAIL, "Excel Sheet has an empty cells.");
			int val[] = { 0, 0, 0 };
			return val;
		}
	}
	
	

	public static void navigateBackToScreen(String screen_title) throws Exception {
		@SuppressWarnings("unused")
		int i = 0;
		String title = "";
		try {
			title = appdriver.findElementById("com.microware.cdfi:id/tv_title").getText();
		} catch (Exception e) {
			appdriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
		}
		try {
			while (!title.equals(screen_title)) {
				appdriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
				if (appdriver.findElementById("com.microware.cdfi:id/tv_title").getText().equals("SHG"))
					break;
				i++;
			}
		} catch (Exception e) {
			throw new Exception("Cannot navigate to " + screen_title + " screen");
		}
	}

}
