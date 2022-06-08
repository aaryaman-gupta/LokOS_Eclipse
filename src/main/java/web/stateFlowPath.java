package web;

import org.openqa.selenium.By;

import app.profileCons;

public class stateFlowPath extends LoginTest {

	// Checklist of the all the flows defined in State field
	/*
	 * Just one flow scripted as of 11-02-2022
	 */
	public static void flowChecklist(int row) throws Exception {

		int flag = 0;

		////// BLOCK PROGRAM MANAGER CHECK
		if (xc.getCellString(row, 4).equals("Block Program Manager")) {
			
			

			xc.changeSheet("SHGs");

			int[] dimensions = xc.getRowCols("SHGs");
			int row_bpm = dimensions[0];

			if (row_bpm == -1) {
				throw new Exception("There are no rows");
			}
			
			@SuppressWarnings("unused")
			String shg ="";

			for (int i = 1; i < row_bpm; i++) {
				
				try {
					shg = xc.getCellString(i, profileCons.shgNameColNum);
				}catch(NullPointerException e) {
					System.out.println("Blank Cell in SHGs");
					++shg_row_counter;
					break;
				}
				try {
					//check for blank spaces in shg list in excel for each
					try {
					flag = BPMSection.bpmApprovalSHG(++shg_row_counter);
					}catch(NullPointerException e) {
//						System.out.println("Blank Space in the Sheet");
						continue;
					}
					
					Thread.sleep(3000);
					driver.findElement(By.xpath(BPMConstants.myTaskButtonPath)).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath(BPMConstants.myTaskButton_1Path)).click();

					if (flag == 0) {
						System.out.println("Entered SHG not in queue-------------------------------------------------------\\\\\\\\Check This\\\\\\\\");
						++shgNotFound;
						continue;
					}
				} catch (Exception e) {
					System.out.println("Failed BPM SHG Approval");
					e.printStackTrace();
					throw new Exception("Failed BPM SHG Approval");
				}
			}
		}

		////// FUTURE FLOW
		else if (flag == 1) {
			System.out.print("Test Flow 2");
		}

		////// IF FLOW NOT DEFINED
		else {
			System.out.print("User Role Not Defined");
			throw new Exception("User Role Not Defined");

		}
		web_process_success_flag=true;
		if(!web_process_success_flag)
			--shg_row_counter;
	}

}
