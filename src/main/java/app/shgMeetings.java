package app;

import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.Status;

import lokos.lokosTest;
import util.randomPressLogic;

public class shgMeetings extends lokosTest {

	public static int[] meetingNum(int row) throws Exception {

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
				test.log(Status.INFO, "Generation of Meeting is possible");
				System.out.println("Generation of Meeting is possible");
			} else {
				String err = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
				System.out.println("Err: " + err);
				test.log(Status.FAIL, err);
				appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
				randomPressLogic.press(0.5, 0.95);
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
					test.log(Status.FAIL, err);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					randomPressLogic.press(0.5, 0.95);
					return val;
				}
				if (title.equals("Cut Off Menu")) {
					test.log(Status.INFO, "Opening Existing Cutoff Meeting is possible");
					System.out.println("Opening Existing Cutoff is possible");
				} else {
					System.out.println("Error--> Cannot open Cutoff Menu for Update");
					test.log(Status.FAIL, "Cannot open Cutoff Menu for Update");
					appdriver.findElementById("com.microware.cdfi:id/ic_Back").click();
					return val;
				}

			} else if (ru) {
				String title = "";
				try {
					title = appdriver.findElementById("com.microware.cdfi:id/tv_title").getText();
				} catch (Exception e) {
					String err = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
					System.out.println("Error: " + err);
					test.log(Status.FAIL, err);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					randomPressLogic.press(0.5, 0.95);
					return val;
				}
				if (title.equals("Meeting Menu")) {
					test.log(Status.INFO, "Opening Existing Cutoff Meeting is possible");
					System.out.println("Opening Existing Cutoff is possible");
				} else {
					System.out.println("Error--> Cannot open Cutoff Menu for Update");
					test.log(Status.FAIL, "Cannot open Cutoff Menu for Update");
					appdriver.findElementById("com.microware.cdfi:id/ic_Back").click();
					return val;
				}
			}

		} else {
			System.out.println("Error-->Flow Type Doesn't Exist");
			test.log(Status.FAIL, "Flow Type Doesn't Exist");
			return val;
		}

		if (cu || cn) {
			String shg = xc.getCellString(row, profileCons.shgNameColNum);
			String flow = xc.getCellString(row, profileCons.flowTypeColNum);
			test.log(Status.INFO, "Starting Cutoff Meeting for " + shg + "(" + flow + ")");
			System.out.println("Starting Cutoff Meeting for " + shg + "(" + flow + ")");
			xc.changeSheet("Cutoff Meetings");
			//////////////////////////////////////
			val = cutoffMeetings.idSelectCutoff(++cutoffRow);
			//////////////////////////////////////
		} else if (rn || ru) {
			test.log(Status.INFO, "Starting Regular Meeting for " + xc.getCellString(row, profileCons.shgNameColNum));
			System.out.println("Starting Regular Meeting for " + xc.getCellString(row, profileCons.shgNameColNum));
			testReg = reports.createTest("SHG Meeting: " + xc.getCellString(row, profileCons.shgNameColNum) + "("
					+ xc.getCellString(row, profileCons.flowTypeColNum) + ")");
			xc.changeSheet("Regular Meetings");
			/////////////////////////////////////////
			val = regularMeetings.idSelectRegular(++regularRow);
			/////////////////////////////////////////
		}

		return val;
	}

}
