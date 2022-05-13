package web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import app.profileCons;

@SuppressWarnings("unused")
public class memRejection extends LoginTest {

	// CODE REJECTION CRITERIA HERE
	public static void rejectMem(int i,int row) {
		if(xc.getCellString(row, profileCons.flowTypeColNum).equals("Approve SHG")) {
			reject=false;
		}else if(xc.getCellString(row, profileCons.flowTypeColNum).equals("Reject SHG")) {
			reject=false;
		}else if(xc.getCellString(row, profileCons.flowTypeColNum).contains("Update")) {
			reject=false;
		}else if(xc.getCellString(row, profileCons.flowTypeColNum).equals("Accept only 5")) {
			if (i <= 5)
				reject = false;
			else
				reject = true;
			
		}else {
			reject=false;
		}

		
	}
	
	public static void rejectSHGProfile(int row) {
		rejectSHG=false;
		if(xc.getCellString(row, profileCons.flowTypeColNum).equals("Approve SHG")) {
			rejectSHG=false;
		}else if(xc.getCellString(row, profileCons.flowTypeColNum).equals("Reject SHG")) {
			rejectSHG=true;
		}else if(xc.getCellString(row, profileCons.flowTypeColNum).contains("Update")) {
			rejectSHG=false;
		}else if(xc.getCellString(row, profileCons.flowTypeColNum).equals("Accept only 5")) {
			rejectSHG=false;		
		}else {
			rejectSHG=false;
		}

		
	}

	// CODE REJECT BUTTON AND REJECTION REMARKS HERE
	public static void rejectionRemarks() throws InterruptedException {

		boolean flag = true;
		int i = 1;
		Thread.sleep(2500);

		try {
		//// Press reject buttons one by one in a member
		while (flag) {
			driver.findElement(
					By.xpath(BPMConstants.memApprovalButtonPath_1 + i + BPMConstants.memRejectionButtonPath_3)).click();
				
			driver.findElement(By.xpath("(//span[text()='Select List'])[1]")).click();
			driver.findElement(By.xpath("//table[contains(@class,'table-new table-hover')]/tbody[1]/tr["+i+"]/td[9]/ng-multiselect-dropdown[1]/div[1]/div[2]/ul[2]/li[1]/div[1]")).click();
			
			//table[contains(@class,'table-new table-hover')]/tbody[1]/tr[1]/td[9]/ng-multiselect-dropdown[1]/div[1]/div[2]/ul[2]/li[1]/div[1]
			//table[contains(@class,'table-new table-hover')]/tbody[1]/tr[1]/td[9]/ng-multiselect-dropdown[1]/div[1]/div[2]/ul[1]/li[1]/div[1]
			//table[contains(@class,'table-new table-hover')]/tbody[1]/tr[1]/td[9]/ng-multiselect-dropdown[1]/div[1]/div[2]/ul[2]/li[2]/div[1]
//			
//			xc.changeSheet("XYZ");
//			driver.findElement(By.xpath("//table[contains(@class,'table-new table-hover')]/tbody[1]/tr["+i+"]/td[9]/ng-multiselect-dropdown[1]/div[1]/div[2]/ul[2]/li[1]/input[@aria-label='"+xc.getCellString(, )+"'][1]"));
//			xc.changeSheet("ABC");
			i++;
			flag = LoginTest.isElementPresent(
					By.xpath(BPMConstants.memApprovalButtonPath_1 + i + BPMConstants.memApprovalButtonPath_2));
		}
		}catch(Exception e) {
			System.out.println("No reject buttons present: "+e.getMessage());
		}

	}

}
