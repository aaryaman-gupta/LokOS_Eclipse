package web;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import app.profileCons;

public class BPMSection extends LoginTest {

	// GOES INTO SHG VERIFICATION SCREEN OF BPM
	/*
	 * Can only access the (1,1) of BPM SHG Approval
	 */
	public static int bpmApprovalSHG(int row) throws Exception {

		System.out.println("\n*************************");
		System.out.println("We are in BPM user screen");

		Thread.sleep(6000);
		int k = 0;
		while (k == 4) {
			try {
				Thread.sleep(4000);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				driver.findElement(By.linkText("Refresh Duplicate")).click();
			} catch (Exception e) {
				k++;
			}
		}
		while (k == 4) {
			try {
				Thread.sleep(6000);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				driver.findElement(By.linkText("Refresh Duplicate")).click();
			} catch (Exception e) {
				k++;
			}
		}
		while (k == 4) {
			try {
				Thread.sleep(6000);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				driver.findElement(By.linkText("Refresh Duplicate")).click();
			} catch (Exception e) {
				k++;
			}
		}
		Thread.sleep(6000);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		int shgCount = Integer.valueOf(driver.findElement(By.xpath(BPMConstants.shgCountPath)).getText());
		System.out.println("No. of SHGs in queue:" + shgCount);

//		Actions actions = new Actions(driver);
//		for(int i=0;i<6;i++)
//			actions.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).sendKeys(Keys.chord(Keys.ADD)).keyUp(Keys.CONTROL).keyUp(Keys.SHIFT).perform();
				
		Thread.sleep(3000);
		
		 
		//// SHG Count check
		if (shgCount == 0) {
			System.out.println("No SHG in queue for verification");
			return 0;
		} else {
			driver.findElement(By.xpath(BPMConstants.shgVerificationBoxPath)).click();
			boolean shgPresent_flag = false;

			int shgListPageCount = (shgCount / 10) + 1;// default list length is 25
			String shg = xc.getCellString(row, profileCons.shgNameColNum);
			
			
			Actions a = new Actions(driver);
			a.sendKeys(Keys.PAGE_DOWN).build().perform();
			a.sendKeys(Keys.PAGE_DOWN).build().perform();
			a.sendKeys(Keys.PAGE_DOWN).build().perform();
			Thread.sleep(5000);
			a.sendKeys(Keys.PAGE_DOWN).build().perform();
			a.sendKeys(Keys.PAGE_DOWN).build().perform();
			a.sendKeys(Keys.PAGE_DOWN).build().perform();
			
			Thread.sleep(3000);
			
//			driver.findElement(By.xpath("//div[@class='mat-select-arrow-wrapper ng-tns-c65-1']")).click();//TestServer microware
			driver.findElement(By.xpath("//mat-select[@id='mat-select-0']/div[1]/div[2]/div[1]")).click();//CDFI Dev
			driver.findElement(By.xpath("(//span[@class='mat-option-text'])[1]")).click();

			//// Iteration done for each page until SHG linkText is found
			for (int i = 0; i < shgListPageCount; i++) {
				Thread.sleep(1500);
				System.out.println("Name SHG: " + xc.getCellString(row, profileCons.shgNameColNum));
				shgPresent_flag = LoginTest.isElementPresent(By.partialLinkText(shg));
				if ((!shgPresent_flag) && (i < shgListPageCount - 1)) {
					driver.findElement(By.xpath("(//button[contains(@class,'mat-focus-indicator mat-tooltip-trigger')])[2]")).click();
					}
				else if (shgPresent_flag) {
					Thread.sleep(1500);
					driver.findElement(By.partialLinkText(shg)).click();
					Thread.sleep(5000);
					driver.findElement(By.linkText("Members")).click();
					Thread.sleep(2000);
					int shgMemberCount = Integer
							.valueOf(driver.findElement(By.xpath(BPMConstants.memApprovalCountPath)).getText().trim());
					if (shgMemberCount == 0) {
						System.out.println("No members to approve");
					} else {
						System.out.println("Total SHG members:" + shgMemberCount);
						///////////////////////////////////////////
						BPMSection.approveMembers_BPM(shgMemberCount, row);
						/////////////////////////////////////////////
					}
					//////////////////////////////////
					BPMSection.approveSHGProfile_BPM(row);
					//////////////////////////////////
				}
			}

			if (shgPresent_flag == false) {
				return 0;
			} else {
				return 1;
			}

		}
	}
//			Thread.sleep(1500);
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
//					driver.findElement(By.xpath("//a[@class='btn btn-primary'][@href='/my-tasks']")));
//			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -document.body.scrollHeight)");
//			driver.findElement(By.xpath(BPMConstants.shgProfileToSHGListBackPath)).click();
//			driver.back();
//			driver.execute_script("window.history.go(-1)");

//	//APPROVE SHG PROFILE
//	public static void approveSHG_BPM() throws InterruptedException {
//
//		System.out.println("We are in SHG approvals screen");
//
//		boolean flag = true;
//		int i = 1;
//
//		Thread.sleep(2500);
//		
//		////Approve all points one by one
//		while (flag) {
//			driver.findElement(
//					By.xpath(BPMConstants.shgApprovalButtonPath_1 + i + BPMConstants.shgApprovalButtonPath_2)).click();
//			i++;
//			flag = LoginTest.isElementPresent(
//					By.xpath(BPMConstants.shgApprovalButtonPath_1 + i + BPMConstants.shgApprovalButtonPath_2));
//		}
//
//		System.out.println("All approvals given");
//
//	}

	// APPROVE SHG MEMBER
	public static void approveMembers_BPM(int shgMemberCount, int row) throws InterruptedException {

		Thread.sleep(2000);
		System.out.println("We are in SHG Member Verification Screen ");

		Thread.sleep(1000);

		//// Goes into all members one by one
		for (int j = 1; j <= shgMemberCount; j++) {

			memRejection.rejectMem(j, row);// Used for selecting rejection criteria

			Thread.sleep(1000);
			driver.findElement(By.xpath(BPMConstants.memNameButtonPath_1 + 1 + BPMConstants.memNameButtonPath_2))
					.click();

			System.out.print("Getting Approval for member: " + j);
			boolean flag = true;
			int i = 1;// marks row number in the approvals list

			Thread.sleep(2500);

			try {
			//// Approves or Rejects members one by one according to rejection criteria
			while (flag) {
				//
				if (!reject) {
					driver.findElement(
							By.xpath(BPMConstants.memApprovalButtonPath_1 + i + BPMConstants.memApprovalButtonPath_2))
							.click();
					i++;
					flag = LoginTest.isElementPresent(
							By.xpath(BPMConstants.memApprovalButtonPath_1 + i + BPMConstants.memApprovalButtonPath_2));
				} else {
					///////////////////////////////
					memRejection.rejectionRemarks();
					///////////////////////////////
					((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -document.body.scrollHeight)");
					driver.findElement(By.className("common-heading-new")).click();
					Thread.sleep(1500);
					driver.findElement(By.id(BPMConstants.rejectButtonID)).click();
					Thread.sleep(1500);
					driver.findElement(By.id(BPMConstants.rejectFormID)).sendKeys("Test run");
					Thread.sleep(1500);
					driver.findElement(By.xpath(BPMConstants.rejectFormOKButtonPath)).click();
					Thread.sleep(1500);
					driver.findElement(By.xpath("//button[text()='Reject Profile']")).click();
					Thread.sleep(3000);
					flag = false;
				}

			}}catch(Exception e) {
				System.out.println("No approve buttons present: "+e.getMessage());
			}
			if (!reject) {
				driver.findElement(By.xpath("//div[contains(@class,'total-use-wrp ml-2')]//button[1]")).click();
				Thread.sleep(3000);
			}
			if (!reject)
				System.out.println("->Approved");
			else
				System.out.println("--->Rejected");
//			driver.findElement(By.xpath(BPMConstants.memApprovalToMemListBackPath)).click();// Delete on trial
		}

		System.out.println("All members approved/rejected.");

	}

	// APPROVE SHG PROFILE
	public static void approveSHGProfile_BPM(int row) throws InterruptedException {

		Thread.sleep(1500);
		driver.findElement(By.linkText("SHG Profile")).click();
		Thread.sleep(3500);
		System.out.println("We are in SHG Profile verification screen");

		boolean shgProfileApproveButton_flag = true;
		int i = 1;

		Thread.sleep(2500);

		memRejection.rejectSHGProfile(row);

		if (LoginTest.isElementPresent(By.xpath(
				BPMConstants.shgProfileApprovalButtonPath_1 + i + BPMConstants.shgProfileApprovalButtonPath_2))) {

			try {
			while (shgProfileApproveButton_flag) {
				if (!rejectSHG)
					driver.findElement(By.xpath(BPMConstants.shgProfileApprovalButtonPath_1 + i
							+ BPMConstants.shgProfileApprovalButtonPath_2)).click();
				if (rejectSHG) {
					driver.findElement(By.xpath(BPMConstants.shgProfileApprovalButtonPath_1 + i
							+ BPMConstants.shgProfileRejectionButtonPath_3)).click();

					driver.findElement(By.xpath("(//span[text()='Select List'])[1]")).click();
					driver.findElement(By.xpath("//table[contains(@class,'table-new table-hover')]/tbody[1]/tr[" + i
							+ "]/td[8]/ng-multiselect-dropdown[1]/div[1]/div[2]/ul[2]/li[1]/div[1]")).click();
					Thread.sleep(3000);
					// Select All:table[contains(@class,'table-new
					// table-hover')]/tbody[1]/tr[1]/td[8]/ng-multiselect-dropdown[1]/div[1]/div[2]/ul[2]/li[1]
					// Duplicate:table[contains(@class,'table-new
					// table-hover')]/tbody[1]/tr[1]/td[8]/ng-multiselect-dropdown[1]/div[1]/div[2]/ul[2]/li[1]/div[1]
				}
				i++;
				shgProfileApproveButton_flag = LoginTest.isElementPresent(By.xpath(
						BPMConstants.shgProfileApprovalButtonPath_1 + i + BPMConstants.shgProfileApprovalButtonPath_2));

			}}catch(Exception e) {
				System.out.println("No approve/reject buttons present: "+e.getMessage());
			}

		} else
			System.out.println("Nothing to approve in SHG Profile");
		
		web.memRejection.rejectSHGProfile(row);
		
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -document.body.scrollHeight)");
		Thread.sleep(1500);
		driver.findElement(By.linkText("SHG Profile")).click();
		if (rejectSHG) {
			driver.findElement(By.id(BPMConstants.rejectButtonID)).click();
			Thread.sleep(1500);
			driver.findElement(By.id(BPMConstants.rejectFormID)).sendKeys("Test run");
			Thread.sleep(1500);
			driver.findElement(By.xpath(BPMConstants.rejectFormOKButtonPath)).click();
			Thread.sleep(1500);
//			driver.findElement(By.id("reject_btn")).click();
			driver.findElement(By.xpath("//button[text()='Reject Profile']")).click();
			Thread.sleep(2000);
		} else if (!rejectSHG) {
			driver.findElement(By.xpath("//button[text()=' Approve Profile']")).click();
			Thread.sleep(2000);
		}
		System.out.println("All SHG Profile approvals given");
//		driver.findElement(By.xpath(BPMConstants.shgProfileToSHGListBackPath)).click();

	}

}
