package app;

import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import lokos.lokosTest;
import reporting.ExtentManager;
import util.dateLogic;

public class cutoffMeetings extends lokosTest {

	public static boolean neg_test_flag = false;
	public static int neg_test_count = 0;
	public static boolean invalid_flag = false;

	public static int[] idSelectCutoff(int row) throws Exception {

		String[] idList = { "000" };

		String type = xc.getCellString(row, cutoffCons.typeColNum);

		if (!type.equalsIgnoreCase("New")) {
			String[] typeList = xc.getCellString(row, cutoffCons.typeColNum).split(" ");
			idList = typeList[0].split(",");
		}

		int[] val = cutoffMeetings.cutoff(row, idList);
		return val;

	}

	@SuppressWarnings("unused")
	public static int[] cutoff(int row, String[] idList) throws Exception {

		int count = 0;
		int pass = 0;
		int fail = 0;
		int p = 0;
		int f = 0;
		int id = 000;
		int oldMtngNum = 0;
		int newMtngNum = 0;

		int i = 0;
		if (Integer.valueOf(idList[0]) != 0) {
			for (String s : idList) {
				cutoff_check[0][Integer.valueOf(idList[i])] = Integer.valueOf(idList[i]);
				++i;
			}
		} else {
			for (int j = 0; j < cutoff_check[0].length; j++) {
				cutoff_check[0][j] = j;
			}
		}
		try {
			if (xc.getCellString(row, cutoffCons.typeColNum).contains("Check")) {
				neg_test_flag = true;
				testMeet.log(Status.INFO, "NEGETIVE TESTING");
			}
		} catch (NullPointerException np) {
		}

		if (xc.getCellString(row, cutoffCons.typeColNum).contains("New")) {

			try {
				oldMtngNum = Integer
						.valueOf(appdriver.findElementById("com.microware.cdfi:id/et_old_meeting_no").getText());
			} catch (Exception e) {
				oldMtngNum = 0;
			}
			newMtngNum = (int) xc.getCellDoubleValue(row, cutoffCons.newMeetingNumColNum);

			if (newMtngNum > (oldMtngNum + 11)) {
				appdriver.findElementById("com.microware.cdfi:id/et_new_meeting_no").sendKeys("" + newMtngNum);
				// validation
				f = validCheckString("com.microware.cdfi:id/et_new_meeting_no", "id",
						(int) xc.getCellDoubleValue(row, cutoffCons.newMeetingNumColNum) + "",
						"Validation Failed: New Meeting Number");
				if (f == 1) {
					testMeet.log(Status.FAIL, "New Meeting Number is Not Valid");
					int[] val = { 0, 0, 0 };
					return val;
				}
				dateLogic.datePicker(xc.getCellString(row, cutoffCons.meetingDateColNum),
						"com.microware.cdfi:id/et_Date");

				Thread.sleep(1000);
				appdriver.findElementById("com.microware.cdfi:id/btn_generate_open").click();
				if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
						.equals("Do you want to generate a cutoff meeting?")) {
					appdriver.findElementById("com.microware.cdfi:id/btn_yes").click();

				} else {
					System.out.println(appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText());
					testMeet.log(Status.FAIL, appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText());
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					appdriver.findElementById("com.microware.cdfi:id/btn_cancelmeeting").click();
					int[] val = { 0, 0, 0 };
					return val;
				}

			} else {
				appdriver.findElementById("com.microware.cdfi:id/btn_cancelmeeting").click();
				testMeet.log(Status.FAIL, "Incorrect meeting number for cutoff");
				System.out.println("Incorrect meeting number for cutoff");
				navigateBackToScreen("SHG");
				int[] val = { 0, 0, 0 };
				return val;
			}
		} else {
			// Edit Meeting button
//			appdriver.findElementById("com.microware.cdfi:id/tbl_open_meeting").click();
//			try {
//				if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
//						.equals("No meeting exists for this SHG.")) {
//					testMeet.log(Status.FAIL, "No meeting exists for this SHG.");
//					System.out.println("No meeting exists for this SHG.");
//					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
//					navigateBackToScreen("SHG");
//					int[] val = { 0, 0, 0 };
//					return val;
//				}
//			} catch (Exception e) {
//
//			}
		}

		testMeet.log(Status.INFO, "Cutoff Entry Begin");

		for (int iterations = 0; iterations < idList.length; iterations++) {
			id = Integer.valueOf(idList[iterations]);

			// Attendance
			switch (id) {
			case 0:
			case 1:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_attendence").click();
					Thread.sleep(1500);
					appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());
					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText")
							.clear();
					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText")
							.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.attendenceColNum) + "");
					f = validCheckString(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText",
							"xpath", (int) xc.getCellDoubleValue(row, cutoffCons.attendenceColNum) + "",
							"001:||Validation Failed||");
					if (f == 1) {
						throw new Exception("Validation Failed");
					}
					
					fillColumnFields(row,
							cutoffCons.Attendence_1,
							memNum,
							"//android.widget.EditText",
							"]/android.widget.LinearLayout/android.widget.EditText",
							"]/android.widget.LinearLayout/android.widget.TextView[1]");
//					try {
//						boolean flag=false;
//					java.util.List<AndroidElement> table= appdriver.findElementsByXPath("//android.widget.EditText");					
//					while(true) {
//						int k=0;
//						for(k=1;k<table.size();k++) {							
//							appdriver
//							.findElementByXPath("//android.widget.LinearLayout[" + k
//									+ "]/android.widget.LinearLayout/android.widget.EditText").clear();
//							appdriver
//							.findElementByXPath("//android.widget.LinearLayout[" + k
//									+ "]/android.widget.LinearLayout/android.widget.EditText")
//							.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.Attendence_1) + "");
//							if(appdriver.findElementByXPath("//android.widget.LinearLayout[" + k
//									+ "]/android.widget.LinearLayout/android.widget.TextView[1]").getText().equals(memNum+"")) {
//								flag=true;
//								break;								
//							}
//							
//						}	
//						if(flag) 
//							break;						
//						mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + (k-2)
//									+ "]/android.widget.LinearLayout/android.widget.EditText", "xpath", "top");
//						
//					}
//					}catch(Exception e) {
//						e.printStackTrace();
//					}

//					for (int j = 2; j <= memNum; j++) {
//						mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + j
//								+ "]/android.widget.LinearLayout/android.widget.EditText", "xpath", "top");
//						appdriver
//						.findElementByXPath("//android.widget.LinearLayout[" + j
//								+ "]/android.widget.LinearLayout/android.widget.EditText").clear();
//						appdriver
//								.findElementByXPath("//android.widget.LinearLayout[" + j
//										+ "]/android.widget.LinearLayout/android.widget.EditText")
//								.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.attendenceColNum) + "");
//					}

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();

					f = validOnSave("Blank", row);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					if (f == 1) {
						throw new Exception("Save Failed");
					}

					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "001:Attendence");
					else
						testMeet.log(Status.PASS, "001:Attendence");
					System.out.println("001:Attendence");
				} catch (Exception e) {
					e.printStackTrace();
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "001:Attendence");
					else
						testMeet.log(Status.PASS, "001:Attendence");
					System.out.println("Error in Attendence:001----------------------Check Here////");
					navigateBackToScreen("SHG");
					int[] val = { 0, 0, 0 };
					e.printStackTrace();
					return val;

				} finally {
					count++;
					navigateBackToScreen("Cut Off Menu");
				}
				if (id != 000)
					break;
				// Member's Saving
			case 2:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_member_saving").click();
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());

					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[1]")
					.clear();
					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[1]")
							.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.compSavColNum) + "");
					f = validCheckString(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[1]",
							"xpath", (int) xc.getCellDoubleValue(row, cutoffCons.compSavColNum) + "",
							"002:||Validation Failed||");
					if (f == 1) {
						throw new Exception("002:||Validation Failed||");
					}
					
					fillColumnFields(row,
							cutoffCons.compSavColNum,
							memNum,
							"//android.widget.EditText[1]",
							"]/android.widget.LinearLayout/android.widget.EditText[1]",
							"]/android.widget.LinearLayout/android.widget.TextView[1]");
					
//					java.util.List<AndroidElement> table= appdriver.findElementsByXPath("//android.widget.EditText[1]");
//					
//					while(true) {
//						if(!appdriver
//						.findElementByXPath("//android.widget.LinearLayout[" + 2
//								+ "]/android.widget.LinearLayout/android.widget.EditText[1]").getText().equals("0"))
//							break;						
//						int k=0;
//						for(k=1;k<table.size();k++) {							
//							appdriver
//							.findElementByXPath("//android.widget.LinearLayout[" + k
//									+ "]/android.widget.LinearLayout/android.widget.EditText[1]").clear();
//							appdriver
//							.findElementByXPath("//android.widget.LinearLayout[" + k
//									+ "]/android.widget.LinearLayout/android.widget.EditText[1]")
//							.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.compSavColNum) + "");
//						}
//						mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + (k-1)
//									+ "]/android.widget.LinearLayout/android.widget.EditText[1]", "xpath", "top");
//					}
					
//					for (int j = 2; j <= memNum; j++) {
//						mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + j
//								+ "]/android.widget.LinearLayout/android.widget.EditText[1]", "xpath", "top");
//
//						appdriver
//								.findElementByXPath("//android.widget.LinearLayout[" + j
//										+ "]/android.widget.LinearLayout/android.widget.EditText[1]")
//								.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.compSavColNum) + "");
//					}

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("", row);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					if (f == 1)
						throw new Exception("");
					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "002:Compulsory Saving");
					else
						testMeet.log(Status.PASS, "002:Compulsory Saving");
					System.out.println("002:Compulsory Saving");
				} catch (Exception e) {
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "002:Compulsory Saving");
					else
						testMeet.log(Status.PASS, "002:Compulsory Saving");
					System.out.println("Error in Compulsory Saving:002----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
				}
				if (id != 000)
					break;
			case 3:
//				try {
//					appdriver.findElementById("com.microware.cdfi:id/tbl_member_saving").click();
//					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());
//					appdriver.findElementByXPath(
//							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[2]").clear();
//					appdriver.findElementByXPath(
//							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[2]")
//							.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.volSavColNum) + "");
//					f = validCheckString(
//							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[2]",
//							"xpath", (int) xc.getCellDoubleValue(row, cutoffCons.volSavColNum) + "",
//							"003:||Validation Failed||");
//					if (f == 1) {
//						throw new Exception("003:||Validation Failed||");
//					}
//					
//
//					fillColumnFields(row,
//							cutoffCons.compSavColNum,
//							memNum,
//							"//android.widget.EditText[2]",
//							"]/android.widget.LinearLayout/android.widget.EditText[2]",
//							"]/android.widget.LinearLayout/android.widget.TextView[1]");
////					for (int j = 2; j <= memNum; j++) {
////						mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + j
////								+ "]/android.widget.LinearLayout/android.widget.EditText[2]", "xpath", "top");
////
////						appdriver
////								.findElementByXPath("//android.widget.LinearLayout[" + j
////										+ "]/android.widget.LinearLayout/android.widget.EditText[2]")
////								.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.compSavColNum) + "");
////					}
//
//					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
//					f = validOnSave("", row);
//					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
//					if (f == 1)
//						throw new Exception("");
//
//					pass++;
//					cutoff_check[1][id] = 1;
//					if (neg_test_flag)
//						testMeet.log(Status.FAIL, "003:Voluntary Saving");
//					else
//						testMeet.log(Status.PASS, "003:Voluntary Saving");
//					System.out.println("003:Voluntary Saving");
//				} catch (Exception e) {
//					fail++;
//					cutoff_check[1][id] = -1;
//					if (!neg_test_flag)
//						testMeet.log(Status.FAIL, "003:Voluntary Saving");
//					else
//						testMeet.log(Status.PASS, "003:Voluntary Saving");
//					System.out.println("Error in Voluntary Saving:003----------------------Check Here////");
//					e.printStackTrace();
//				} finally {
//					count++;
//					appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
//				}
//				if (id != 000)
//					break;
				// Member's Closed Loan
			case 4:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_member_closed_loan").click();
					Thread.sleep(1000);
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());

					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[1]").clear();
					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[1]")
							.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.noLoansColNum) + "");
					f = validCheckString(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[1]",
							"xpath", (int) xc.getCellDoubleValue(row, cutoffCons.noLoansColNum) + "",
							"004:||Validation Failed||");
					if (f == 1)
						throw new Exception("004:||Validation Failed||");


					fillColumnFields(row,
							cutoffCons.noLoansColNum,
							memNum,
							"//android.widget.EditText[1]",
							"]/android.widget.LinearLayout/android.widget.EditText[1]",
							"]/android.widget.LinearLayout/android.widget.TextView[1]");
					
//					for (int j = 2; j <= memNum; j++) {
//						mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + j
//								+ "]/android.widget.LinearLayout/android.widget.EditText[1]", "xpath", "top");
//
//						appdriver
//								.findElementByXPath("//android.widget.LinearLayout[" + j
//										+ "]/android.widget.LinearLayout/android.widget.EditText[1]")
//								.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.noLoansColNum) + "");
//					}

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("", row);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					if (f == 1)
						throw new Exception("");

					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "004:Number of Loans");
					else
						testMeet.log(Status.PASS, "004:Number of Loans");
					System.out.println("004:Number of Loans");
				} catch (Exception e) {
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "004:Number of Loans");
					else
						testMeet.log(Status.PASS, "004:Number of Loans");
					System.out.println("Error in Number of Loans:004----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					navigateBackToScreen("Cut Off Menu");
				}
				if (id != 000)
					break;

			case 5:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_member_closed_loan").click();
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());

					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[2]").clear();
					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[2]")
							.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.amtLoansColNum) + "");
					f = validCheckString(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[2]",
							"xpath", (int) xc.getCellDoubleValue(row, cutoffCons.amtLoansColNum) + "",
							"005:||Validation Failed||");
					if (f == 1)
						throw new Exception("005:||Validation Failed||");

					fillColumnFields(row,
							cutoffCons.amtLoansColNum,
							memNum,
							"//android.widget.EditText[2]",
							"]/android.widget.LinearLayout/android.widget.EditText[2]",
							"]/android.widget.LinearLayout/android.widget.TextView[1]");
//					for (int j = 2; j <= memNum; j++) {
//						mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + j
//								+ "]/android.widget.LinearLayout/android.widget.EditText[2]", "xpath", "top");
//
//						appdriver
//								.findElementByXPath("//android.widget.LinearLayout[" + j
//										+ "]/android.widget.LinearLayout/android.widget.EditText[2]")
//								.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.amtLoansColNum) + "");
//					}
					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("", row);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					if (f == 1)
						throw new Exception("");

					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "005:Amount of Loans");
					else
						testMeet.log(Status.PASS, "005:Amount of Loans");
					System.out.println("005:Amount of Loans");
				} catch (Exception e) {
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "005:Amount of Loans");
					else
						testMeet.log(Status.PASS, "005:Amount of Loans");
					System.out.println("Error in Amount of Loans:005----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					navigateBackToScreen("Cut Off Menu");
				}
				if (id != 000)
					break;
				// Member's Active Loans
			case 6:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_member_active_loan").click();
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());

					if (memNum >= 1) {

						appdriver.findElementByXPath("/android.widget.LinearLayout[1]/android.widget.FrameLayout/"
								+ "android.widget.LinearLayout/android.widget.TableRow/android.widget.TableRow/"
								+ "android.widget.ImageView").click();
						/*
						 * enterValue_Id( "",//title "top",//scroll direction "", //location row, //row
						 * cutoffCons,//column number ":||Validation Error||");//Error Message
						 */
						selectById("Fund Source", // title
								"top", // scroll direction
								"com.microware.cdfi:id/spin_loan_type", // location
								row, // row
								cutoffCons.FundSource_6);// column number
						enterValue_Id("Disbursed Loan Amount", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_original_loan_amount", // location
								row, // row
								cutoffCons.DisbursedLoanAmount_106, // column number
								"106:||Validation Error||");// Error Message
						enterValue_Id("Principal Repaid", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_principal_repaid", // location
								row, // row
								cutoffCons.PrincipalRepaid_206, // column number
								"306:||Validation Error||");// Error Message
						mt.scrollToText("Principal Outstanding (including Arrears)", "top");
						appdriver.findElementById("com.microware.cdfi:id/et_amount").clear();
						enterValue_Id("Principal Outstanding (including Arrears)", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_amount", // location
								row, // row
								cutoffCons.PrincipalOutstandingIncludingArrears_306, // column number
								"306:||Validation Error||");// Error Message
						enterValue_Id("Principal Arrears", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_principal_overdue", // location
								row, // row
								cutoffCons.PrincipalArrears_406, // column number
								"406:||Validation Error||");// Error Message
						enterValue_Id("Interest Rate (Annualy)", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_monthly_interest_rate", // location
								row, // row
								cutoffCons.InterestRateAnnually_506, // column number
								"506:||Validation Error||");// Error Message
						enterValue_Id("Interest Paid", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_interest_paid", // location
								row, // row
								cutoffCons.InterestPaid_606, // column number
								"606:||Validation Error||");// Error Message
						enterValue_Id("Overdue Interest (if any)", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_interest_overdue", // location
								row, // row
								cutoffCons.OverdueInterest_706, // column number
								"706:||Validation Error||");// Error Message
						mt.scrollToText("Total Overdue", "top");
						appdriver.findElementById("com.microware.cdfi:id/et_overdue").clear();
						enterValue_Id("Total Overdue", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_overdue", // location
								row, // row
								cutoffCons.TotalOverdue_806, // column number
								"806:||Validation Error||");// Error Message
						mt.scrollToText("Loan Disbursement Date", "top");
						dateLogic.datePicker(xc.getCellString(row, cutoffCons.LoanDisbursementDateDisbursement_906),
								"com.microware.cdfi:id/et_date_loan_taken");
						selectById("Mode of Receipt", // title
								"top", // scroll direction
								"com.microware.cdfi:id/tv_mode_of_payment", // location
								row, // row
								cutoffCons.ModeofReceipt_1006);// column number
						enterValue_Id("Period (Months)", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_no_of_installment", // location
								row, // row
								cutoffCons.PeriodMonths_1106, // column number
								"1106:||Validation Error||");// Error Message
						selectById("Loan Repayment Frequency", // title
								"top", // scroll direction
								"com.microware.cdfi:id/spin_frequency", // location
								row, // row
								cutoffCons.LoanRepaymentFrequency_1206);// column number
						selectById("Purpose", // title
								"top", // scroll direction
								"com.microware.cdfi:id/spin_purpose", // location
								row, // row
								cutoffCons.Purpose_1306);// column number

					} else {
						invalid_flag = true;
					}

					if (invalid_flag)
						throw new Exception("");

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("Do you want to change the EMI schedule?", row);
					if (f == 1) {
						navigateBackToScreen("Cut off Member Active Loan");
						throw new Exception("");
					}
					appdriver.findElementById("com.microware.cdfi:id/btn_no").click();

					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "006:First Member's Active Loans");
					else
						testMeet.log(Status.PASS, "006:First Member's Active Loans");
					System.out.println("006:First Member's Active Loans");
				} catch (Exception e) {
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "006:First Member's Active Loans");
					else
						testMeet.log(Status.PASS, "006:First Member's Active Loans");
					System.out.println("Error in First Member's Active Loans:006----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					invalid_flag = false;
					navigateBackToScreen("Cut Off Menu");
				}
				if (id != 000)
					break;
			case 7:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_member_active_loan").click();
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());

					if (memNum >= 2) {

						appdriver.findElementByXPath("/android.widget.LinearLayout[2]/android.widget.FrameLayout/"
								+ "android.widget.LinearLayout/android.widget.TableRow/android.widget.TableRow/"
								+ "android.widget.ImageView").click();
						/*
						 * enterValue_Id( "",//title "top",//scroll direction "", //location row, //row
						 * cutoffCons,//column number ":||Validation Error||");//Error Message
						 */
						selectById("Fund Source", // title
								"top", // scroll direction
								"com.microware.cdfi:id/spin_loan_type", // location
								row, // row
								cutoffCons.FundSource_7);// column number
						enterValue_Id("Disbursed Loan Amount", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_original_loan_amount", // location
								row, // row
								cutoffCons.DisbursedLoanAmount_107, // column number
								"107:||Validation Error||");// Error Message
						enterValue_Id("Principal Repaid", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_principal_repaid", // location
								row, // row
								cutoffCons.PrincipalRepaid_207, // column number
								"307:||Validation Error||");// Error Message
						enterValue_Id("Principal Outstanding (including Arrears)", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_amount", // location
								row, // row
								cutoffCons.PrincipalOutstandingIncludingArrears_307, // column number
								"307:||Validation Error||");// Error Message
						enterValue_Id("Principal Arrears", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_principal_overdue", // location
								row, // row
								cutoffCons.PrincipalArrears_407, // column number
								"407:||Validation Error||");// Error Message
						enterValue_Id("Interest Rate (Annualy)", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_monthly_interest_rate", // location
								row, // row
								cutoffCons.InterestRateAnnually_507, // column number
								"507:||Validation Error||");// Error Message
						enterValue_Id("Interest Paid", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_interest_paid", // location
								row, // row
								cutoffCons.InterestPaid_607, // column number
								"607:||Validation Error||");// Error Message
						enterValue_Id("Overdue Interest (if any)", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_interest_overdue", // location
								row, // row
								cutoffCons.OverdueInterest_707, // column number
								"707:||Validation Error||");// Error Message
						enterValue_Id("Total Overdue", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_overdue", // location
								row, // row
								cutoffCons.TotalOverdue_807, // column number
								"807:||Validation Error||");// Error Message
						mt.scrollToText("Loan Disbursement Date", "top");
						dateLogic.datePicker(xc.getCellString(row, cutoffCons.LoanDisbursementDateDisbursement_907),
								"com.microware.cdfi:id/et_date_loan_taken");
						selectById("Mode of Receipt", // title
								"top", // scroll direction
								"com.microware.cdfi:id/tv_mode_of_payment", // location
								row, // row
								cutoffCons.ModeofReceipt_1007);// column number
						enterValue_Id("Period (Months)", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_no_of_installment", // location
								row, // row
								cutoffCons.PeriodMonths_1107, // column number
								"1107:||Validation Error||");// Error Message
						selectById("Loan Repayment Frequency", // title
								"top", // scroll direction
								"com.microware.cdfi:id/spin_frequency", // location
								row, // row
								cutoffCons.LoanRepaymentFrequency_1207);// column number
						selectById("Purpose", // title
								"top", // scroll direction
								"com.microware.cdfi:id/spin_purpose", // location
								row, // row
								cutoffCons.Purpose_1307);// column number

					} else {
						invalid_flag = true;
					}

					if (invalid_flag)
						throw new Exception("");

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("Do you want to change the EMI schedule?", row);
					if (f == 1) {
						navigateBackToScreen("Cut off Member Active Loan");
						throw new Exception("");
					}
					appdriver.findElementById("com.microware.cdfi:id/btn_no").click();

					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "007:Second Member's Active Loans");
					else
						testMeet.log(Status.PASS, "007:Second Member's Active Loans");
					System.out.println("007:Second Member's Active Loans");
				} catch (Exception e) {
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "007:Second Member's Active Loans");
					else
						testMeet.log(Status.PASS, "007:Second Member's Active Loans");
					System.out.println("Error in Second Member's Active Loans:007----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					invalid_flag = false;
					navigateBackToScreen("Cut Off Menu");
				}
				if (id != 000)
					break;
			case 8:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_member_active_loan").click();
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());

					if (memNum >= 2) {

						appdriver.findElementByXPath("/android.widget.LinearLayout[3]/android.widget.FrameLayout/"
								+ "android.widget.LinearLayout/android.widget.TableRow/android.widget.TableRow/"
								+ "android.widget.ImageView").click();
						/*
						 * enterValue_Id( "", //title "top", //scroll direction "", //location row,//row
						 * cutoffCons,//column number ":||Validation Error||");//Error Message
						 */
						selectById("Fund Source", // title
								"top", // scroll direction
								"com.microware.cdfi:id/spin_loan_type", // location
								row, // row
								cutoffCons.FundSource_8);// column number
						enterValue_Id("Disbursed Loan Amount", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_original_loan_amount", // location
								row, // row
								cutoffCons.DisbursedLoanAmount_108, // column number
								"108:||Validation Error||");// Error Message
						enterValue_Id("Principal Repaid", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_principal_repaid", // location
								row, // row
								cutoffCons.PrincipalRepaid_208, // column number
								"308:||Validation Error||");// Error Message
						enterValue_Id("Principal Outstanding (including Arrears)", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_amount", // location
								row, // row
								cutoffCons.PrincipalOutstandingIncludingArrears_308, // column number
								"308:||Validation Error||");// Error Message
						enterValue_Id("Principal Arrears", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_principal_overdue", // location
								row, // row
								cutoffCons.PrincipalArrears_408, // column number
								"408:||Validation Error||");// Error Message
						enterValue_Id("Interest Rate (Annualy)", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_monthly_interest_rate", // location
								row, // row
								cutoffCons.InterestRateAnnually_508, // column number
								"508:||Validation Error||");// Error Message
						enterValue_Id("Interest Paid", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_interest_paid", // location
								row, // row
								cutoffCons.InterestPaid_608, // column number
								"608:||Validation Error||");// Error Message
						enterValue_Id("Overdue Interest (if any)", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_interest_overdue", // location
								row, // row
								cutoffCons.OverdueInterest_708, // column number
								"708:||Validation Error||");// Error Message
						enterValue_Id("Total Overdue", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_overdue", // location
								row, // row
								cutoffCons.TotalOverdue_808, // column number
								"808:||Validation Error||");// Error Message
						mt.scrollToText("Loan Disbursement Date", "top");
						dateLogic.datePicker(xc.getCellString(row, cutoffCons.LoanDisbursementDate_908),
								"com.microware.cdfi:id/et_date_loan_taken");
						selectById("Mode of Receipt", // title
								"top", // scroll direction
								"com.microware.cdfi:id/tv_mode_of_payment", // location
								row, // row
								cutoffCons.ModeofReceipt_1008);// column number
						enterValue_Id("Period (Months)", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_no_of_installment", // location
								row, // row
								cutoffCons.PeriodMonths_1108, // column number
								"1108:||Validation Error||");// Error Message
						selectById("Loan Repayment Frequency", // title
								"top", // scroll direction
								"com.microware.cdfi:id/spin_frequency", // location
								row, // row
								cutoffCons.LoanRepaymentFrequency_1208);// column number
						selectById("Purpose", // title
								"top", // scroll direction
								"com.microware.cdfi:id/spin_purpose", // location
								row, // row
								cutoffCons.Purpose_1308);// column number

					} else {
						invalid_flag = true;
					}

					if (invalid_flag)
						throw new Exception("");

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("Do you want to change the EMI schedule?", row);
					if (f == 1) {
						navigateBackToScreen("Cut off Member Active Loan");
						throw new Exception("");
					}
					appdriver.findElementById("com.microware.cdfi:id/btn_no").click();

					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "008:Third Member's Active Loans");
					else
						testMeet.log(Status.PASS, "008:Third Member's Active Loans");
					System.out.println("008:Third Member's Active Loans");
				} catch (Exception e) {
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "008:Third Member's Active Loans");
					else
						testMeet.log(Status.PASS, "008:Third Member's Active Loans");
					System.out.println("Error in Third Member's Active Loans:008----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					invalid_flag = false;
					navigateBackToScreen("Cut Off Menu");
				}
				if (id != 000)
					break;
				// Member's Share Capital
			case 9:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_share_capital").click();
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());
					enterValue_Id("Share Capital", // title
							"top", // scroll direction
							"//android.widget.LinearLayout[1]/android.widget.FrameLayout/android.widget.TableRow/android.widget.EditText", // location
							row, // row
							cutoffCons.ShareCapital_9, // column number
							"009:||Validation Error||");// Error Message
					if (invalid_flag)
						throw new Exception("Validation Failed");

					for (int j = 2; j <= memNum; j++) {
						mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + j
								+ "]/android.widget.FrameLayout/android.widget.TableRow/android.widget.EditText",
								"xpath", "top");

						appdriver.findElementByXPath("//android.widget.LinearLayout[" + j
								+ "]/android.widget.FrameLayout/android.widget.TableRow/android.widget.EditText")
								.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.ShareCapital_9) + "");
					}

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("Blank", row);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					if (f == 1) {
						throw new Exception("Save Failed");
					}

					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "009:Member's Share Capital");
					else
						testMeet.log(Status.PASS, "009:Member's Share Capital");
					System.out.println("009:Member's Share Capital");
				} catch (Exception e) {
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "009:Member's Share Capital");
					else
						testMeet.log(Status.PASS, "009:Member's Share Capital");
					System.out.println("Error in Member's Share Capital:009----------------------Check Here////");
					e.printStackTrace();

				} finally {
					count++;
					invalid_flag = false;
					navigateBackToScreen("Cut Off Menu");
				}
				if (id != 000)
					break;
				// Membership fee
			case 10:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_membership_fee").click();
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());
					enterValue_Id("Membership Fee", // title
							"top", // scroll direction
							"//android.widget.LinearLayout[1]/android.widget.FrameLayout/android.widget.TableRow/android.widget.EditText", // location
							row, // row
							cutoffCons.MembershipFee_10, // column number
							"010:||Validation Error||");// Error Message
					if (invalid_flag)
						throw new Exception("Validation Failed");

					for (int j = 2; j <= memNum; j++) {
						mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + j
								+ "]/android.widget.FrameLayout/android.widget.TableRow/android.widget.EditText",
								"xpath", "top");

						appdriver.findElementByXPath("//android.widget.LinearLayout[" + j
								+ "]/android.widget.FrameLayout/android.widget.TableRow/android.widget.EditText")
								.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.MembershipFee_10) + "");
					}

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("Blank", row);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					if (f == 1) {
						throw new Exception("Save Failed");
					}

					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "010:Membership Fee");
					else
						testMeet.log(Status.PASS, "010:Membership Fee");
					System.out.println("010:Membership Fee");
				} catch (Exception e) {
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "010:Membership Fee");
					else
						testMeet.log(Status.PASS, "010:Membership Fee");
					System.out.println("Error in Membership Fee:010----------------------Check Here////");
					e.printStackTrace();

				} finally {
					count++;
					invalid_flag = false;
					navigateBackToScreen("Cut Off Menu");
				}
				if (id != 000)
					break;
				// Group Investment
			case 11:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_group_investment").click();
					appdriver.findElementById("com.microware.cdfi:id/ivAdd").click();
					/*
					 * enterValue_Id( "",//title "top",//scroll direction "",//location row,//row
					 * cutoffCons,//column number ":||Validation Error||");//Error Message
					 */
					selectById("Investment at", // title
							"top", // scroll direction
							"com.microware.cdfi:id/spin_source", // location
							row, // row
							cutoffCons.InvestmentAtGroup_11); // column number
					selectById("Type of Saving", // title
							"top", // scroll direction
							"com.microware.cdfi:id/spin_saving_type", // location
							row, // row
							cutoffCons.TypeofSaving_111); // column number
					enterValue_Id("Amount", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_amount", // location
							row, // row
							cutoffCons.Amount_211, // column number
							"211:||Validation Error||");// Error Message
					if (invalid_flag)
						throw new Exception("Validation Failed");

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("Blank", row);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					if (f == 1) {
						throw new Exception("Save Failed");
					}

					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "011:Group Investment");
					else
						testMeet.log(Status.PASS, "011:Group Investment");
					System.out.println("011:Group Investment");
				} catch (Exception e) {
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "011:Group Investment");
					else
						testMeet.log(Status.PASS, "011:Group Investment");
					System.out.println("Error in 011:Group Investment----------------------Check Here////");
					e.printStackTrace();

				} finally {
					count++;
					invalid_flag = false;
					navigateBackToScreen("Cut Off Menu");
				}
				if (id != 000)
					break;
				// Group's Closed Loan
			case 12:
				try {
					mt.scrollToText("Group's Closed Loan", "top");
					appdriver.findElementById("com.microware.cdfi:id/tbl_group_closed_loan_summarry").click();
					appdriver.findElementById("com.microware.cdfi:id/img_Add").click();
					/*
					 * enterValue_Id( "",//title "top",//scroll direction "",//location row,//row
					 * cutoffCons,//column number ":||Validation Error||");//Error Message
					 */
					selectById("Institution", // title
							"top", // scroll direction
							"com.microware.cdfi:id/spin_loan_source", // location
							row, // row
							cutoffCons.InstitutionGrpClosedLoan_12); // column number
					enterString_Id("Borrowed From", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_Org_name", // location
							row, // row
							cutoffCons.BorrowedFrom_112, // column number
							"112:||Validation Error||");// Error Message
					selectById("Fund Source", // title
							"top", // scroll direction
							"com.microware.cdfi:id/spin_fund_type", // location
							row, // row
							cutoffCons.FundSource_212); // column number
					enterValue_Id("Number of Loans", // title
							"top", // scroll direction
							"com.microware.cdfi:id/tv_number_loan", // location
							row, // row
							cutoffCons.NumberofLoans_312, // column number
							"312:||Validation Error||");// Error Message
					if (invalid_flag)
						throw new Exception("Validation Failed");

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("Blank", row);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					if (f == 1) {
						throw new Exception("Save Failed");
					}

					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "012:Group's Closed Loans");
					else
						testMeet.log(Status.PASS, "012:Group's Closed Loans");
					System.out.println("012:Group's Closed Loans");
				} catch (Exception e) {
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "012:Group's Closed Loans");
					else
						testMeet.log(Status.PASS, "012:Group's Closed Loans");
					System.out.println("Error in Group's Closed Loans:012----------------------Check Here////");
					e.printStackTrace();

				} finally {
					count++;
					invalid_flag = false;
					navigateBackToScreen("Cut Off Menu");
				}
				if (id != 000)
					break;
				// Group's Active Loan
			case 13:
				try {
					mt.scrollToText("Group Active Loan", "top");
					appdriver.findElementById("com.microware.cdfi:id/tbl_group_summarry").click();
					appdriver.findElementById("com.microware.cdfi:id/img_Add").click();
					/*
					 * enterValue_Id( "",//title "top",//scroll direction "",//location row,//row
					 * cutoffCons,//column number ":||Validation Error||");//Error Message
					 */
					selectById("Institution", // title
							"top", // scroll direction
							"com.microware.cdfi:id/spin_loan_source", // location
							row, // row
							cutoffCons.InstitutionGrpActiveLoan_13); // column number
					enterString_Id("Borrowed From", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_Org_name", // location
							row, // row
							cutoffCons.BorrowedFrom_112, // column number
							"112:||Validation Error||");// Error Message
					if (xc.getCellString(row, cutoffCons.InstitutionGrpActiveLoan_13).equals("VO")
							|| xc.getCellString(row, cutoffCons.InstitutionGrpActiveLoan_13).equals("CLF"))
						selectById("Fund Source", // title
								"top", // scroll direction
								"com.microware.cdfi:id/spin_fund_type", // location
								row, // row
								cutoffCons.FundSource_212); // column number
					selectById("Type of Loan", // title
							"top", // scroll direction
							"com.microware.cdfi:id/spin_type_loan", // location
							row, // row
							cutoffCons.TypeofLoan_413); // column number
					mt.scrollToText("Loan Disbursement Date", "top");
					dateLogic.datePicker(xc.getCellString(row, cutoffCons.LoanDisbursementDate_513),
							"com.microware.cdfi:id/et_loanDisbursmentDate");
					selectById("Mode of Receipt", // title
							"top", // scroll direction
							"com.microware.cdfi:id/spin_mode_of_payment", // location
							row, // row
							cutoffCons.ModeofReceipt_813); // column number
					if (xc.getCellString(row, cutoffCons.InstitutionGrpActiveLoan_13).equals("Bank")) {
						selectById("Name of the Bank", // title
								"top", // scroll direction
								"com.microware.cdfi:id/spin_bankname", // location
								row, // row
								cutoffCons.BankName_613); // column number
						mt.scrollToText("Cheque number/Transaction number", "top");
						appdriver.findElementById("com.microware.cdfi:id/et_cheque_no_transactio_no")
								.sendKeys(xc.getCellString(row, cutoffCons.TransactionNumber_713).substring(1));
						f = validCheckString("com.microware.cdfi:id/et_cheque_no_transactio_no", "id",
								xc.getCellString(row, cutoffCons.TransactionNumber_713).substring(1),
								"713:||Validation Error||");
						if (f == 1)
							invalid_flag = true;
					}
					enterValue_Id("Interest Rate (Annualy)", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_interest_rate", // location
							row, // row
							cutoffCons.InterestRateAnnually_913, // column number
							"912:||Validation Error||");// Error Message
					enterValue_Id("Amount Withdrawn", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_amount_disbursed", // location
							row, // row
							cutoffCons.AmountWithdrawn_1013, // column number
							"1013:||Validation Error||");// Error Message
					enterValue_Id("Original Period(in Months)", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_original_period_months", // location
							row, // row
							cutoffCons.OriginalPeriodInMonths_1113, // column number
							"1113:||Validation Error||");// Error Message
					enterValue_Id("Total Outstanding Period(in Months)", // title
							"top", // scroll direction
							"com.microware.cdfi:id/tv_outstanding_period_months", // location
							row, // row
							cutoffCons.TotalOutstandingPeriod_1213, // column number
							"1213:||Validation Error||");// Error Message
					enterValue_Id("Remaining Period(in Months)", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_remaining_period_months", // location
							row, // row
							cutoffCons.RemainingPeriod_1313, // column number
							"1313:||Validation Error||");// Error Message
					enterValue_Id("Principal Repaid", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_principal_repaid", // location
							row, // row
							cutoffCons.PrincipalRepaid_1413, // column number
							"1413:||Validation Error||");// Error Message
					enterValue_Id("Principal Arrears", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_principal_overdue", // location
							row, // row
							cutoffCons.PrincipalArrears_1513, // column number
							"1513:||Validation Error||");// Error Message

					enterValue_Id("Interest Arrears", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_interest_overdue", // location
							row, // row
							cutoffCons.InterestArrears_1613, // column number
							"1613:||Validation Error||");// Error Message
					selectById("Repayment Frequency", // title
							"top", // scroll direction
							"com.microware.cdfi:id/tv_repayment_frequency", // location
							row, // row
							cutoffCons.RepaymentFrequency_1713);// column number

					if (invalid_flag)
						throw new Exception("Validation Failed");

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("Do you want to change the EMI schedule?", row);
					if (f == 1) {
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
						throw new Exception("Save Failed");
					}
					appdriver.findElementById("com.microware.cdfi:id/btn_no");

					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "013:Group's Active Loans");
					else
						testMeet.log(Status.PASS, "013:Group's Active Loans");
					System.out.println("013:Group's Active Loans");
				} catch (Exception e) {
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "013:Group's Active Loans");
					else
						testMeet.log(Status.PASS, "013:Group's Active Loans");
					System.out.println("Error in Group's Active Loans:013----------------------Check Here////");
					e.printStackTrace();

				} finally {
					count++;
					invalid_flag = false;
					navigateBackToScreen("Cut Off Menu");
				}
				if (id != 000)
					break;
				// Fund Received
			case 14:
				try {
					mt.scrollToText("Fund Received", "top");
					appdriver.findElementById("com.microware.cdfi:id/tv_fundReceived").click();
					appdriver.findElementById("com.microware.cdfi:id/ivAdd").click();
					/*
					 * enterValue_Id( "",//title "top",//scroll direction "",//location row,//row
					 * cutoffCons,//column number ":||Validation Error||");//Error Message
					 */
					selectById("Source", // title
							"top", // scroll direction
							"com.microware.cdfi:id/spin_received_from", // location
							row, // row
							cutoffCons.SourceFundReceived_14); // column number
					selectById("Fund Source", // title
							"top", // scroll direction
							"com.microware.cdfi:id/spin_receipt", // location
							row, // row
							cutoffCons.FundSource_114); // column number
					selectById("Mode of Receipt", // title
							"top", // scroll direction
							"com.microware.cdfi:id/spin_mode_of_payment", // location
							row, // row
							cutoffCons.ModeofReceipt_214); // column number
					enterValue_Id("Total Amount Received", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_amount", // location
							row, // row
							cutoffCons.TotalAmountReceived_314, // column number
							"314:||Validation Error||");// Error Message
					if (invalid_flag)
						throw new Exception("Validation Failed");

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("Blank", row);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					if (f == 1) {
						throw new Exception("Save Failed");
					}

					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "014:Funds Received");
					else
						testMeet.log(Status.PASS, "014:Funds Received");
					System.out.println("014:Funds Received");
				} catch (Exception e) {
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "014:Funds Received");
					else
						testMeet.log(Status.PASS, "014:Funds Received");
					System.out.println("Error in Funds Received:014----------------------Check Here////");
					e.printStackTrace();

				} finally {
					count++;
					invalid_flag = false;
					navigateBackToScreen("Cut Off Menu");
				}
				if (id != 000)
					break;
				// Group Cash Balance
			case 15:
				try {
					mt.scrollToText("Group Cash Balance", "top");
					appdriver.findElementById("com.microware.cdfi:id/tbl_cashBox").click();
					/*
					 * enterValue_Id( "",//title "top",//scroll direction "",//location row,//row
					 * cutoffCons,//column number ":||Validation Error||");//Error Message
					 */

					enterValue_Id("Cash In Hand", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_cashInHand", // location
							row, // row
							cutoffCons.CashinHand_15, // column number
							"015:||Validation Error||");// Error Message

					enterValue_Id("Cash In Transit", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_TransitCash", // location
							row, // row
							cutoffCons.CashinTransit_115, // column number
							"115:||Validation Error||");// Error Message
					mt.scrollToText("Date of Balance taken", "top");
					dateLogic.datePicker(xc.getCellString(row, cutoffCons.DateofBalanceofTaken_215),
							"com.microware.cdfi:id/et_BalanceDate");
					if (invalid_flag)
						throw new Exception("Validation Failed");

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("Blank", row);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					if (f == 1) {
						throw new Exception("Save Failed");
					}

					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "015:Group Cash Balance");
					else
						testMeet.log(Status.PASS, "015:Group Cash Balance");
					System.out.println("015:Group Cash Balance");
				} catch (Exception e) {
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "015:Group Cash Balance");
					else
						testMeet.log(Status.PASS, "015:Group Cash Balance");
					System.out.println("Error in Group Cash Balance:015----------------------Check Here////");
					e.printStackTrace();

				} finally {
					count++;
					invalid_flag = false;
					navigateBackToScreen("Cut Off Menu");
				}
				if (id != 000)
					break;
				// Group Bank Balance
			case 16:
				try {
					mt.scrollToText("Group Bank Balance", "top");
					appdriver.findElementById("com.microware.cdfi:id/tbl_bank").click();
					appdriver.findElementById("com.microware.cdfi:id/ivEdit").click();
					/*
					 * enterValue_Id( "",//title "top",//scroll direction "",//location row,//row
					 * cutoffCons,//column number ":||Validation Error||");//Error Message
					 */
					enterValue_Id("Cash at Bank", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_cashatBank", // location
							row, // row
							cutoffCons.CashinHand_15, // column number
							"015:||Validation Error||");// Error Message

					enterValue_Id("Cheque issued but amount not debited", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_amountNotRealised", // location
							row, // row
							cutoffCons.ChequeIssuedNotDebited_216, // column number
							"216:||Validation Error||");// Error Message
					enterValue_Id("Cheque received but amount not credited", // title
							"top", // scroll direction
							"com.microware.cdfi:id/et_amountNotCredited", // location
							row, // row
							cutoffCons.ChequeReceivedNotCredited_316, // column number
							"316:||Validation Error||");// Error Message
					mt.scrollToText("Date of Balance taken", "top");
					dateLogic.datePicker(xc.getCellString(row, cutoffCons.DateofBalance_416),
							"com.microware.cdfi:id/et_BalanceDate");
					if (invalid_flag)
						throw new Exception("Validation Failed");

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("Blank", row);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					if (f == 1) {
						throw new Exception("Save Failed");
					}

					pass++;
					cutoff_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "016:Group Bank Balance");
					else
						testMeet.log(Status.PASS, "016:Group Bank Balance");
					System.out.println("016:Group Bank Balance");
				} catch (Exception e) {
					fail++;
					cutoff_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "016:Group Bank Balance");
					else
						testMeet.log(Status.PASS, "016:Group Bank Balance");
					System.out.println("Error in Group Bank Balance:016----------------------Check Here////");
					e.printStackTrace();

				} finally {
					count++;
					invalid_flag = false;
					navigateBackToScreen("Cut Off Menu");
				}
				if (id != 000)
					break;
			default:
				break;
			}
		}

		if (neg_test_flag)
			pass = neg_test_count;

		appdriver.findElementById("com.microware.cdfi:id/btn_close").click();
		f = validOnSave("Are you sure you want to close the meeting?", row);
		if (f == 1) {
			navigateBackToScreen("Cut Off Menu");
		}
		appdriver.findElementById("com.microware.cdfi:id/btn_yes").click();
		reporting.ExtentManager.addScreenShotsToTest("Cutoff Result 1", testFlow);
		mt.scrollToText("Bank Balance", "top");
		reporting.ExtentManager.addScreenShotsToTest("Cutoff Result 2", testFlow);
		appdriver.findElementById("com.microware.cdfi:id/btn_sendApproval").click();

		int[] val = { pass, fail, count };
		return val;
	}

	public static void enterValue_Id(String title, String dir, String loc, int row, int cons, String err) {
		mt.scrollToText(title, dir);
		appdriver.findElementById(loc).sendKeys((int) xc.getCellDoubleValue(row, cons) + "");
		int f = validCheckString(loc, "id", (int) xc.getCellDoubleValue(row, cons) + "", err);
		if (f == 1)
			invalid_flag = true;
	}

	public static void enterValue_Xpath(String title, String dir, String loc, int row, int cons, String err) {
		mt.scrollToText(title, dir);
		appdriver.findElementByXPath(loc).sendKeys((int) xc.getCellDoubleValue(row, cons) + "");
		int f = validCheckString(loc, "xpath", (int) xc.getCellDoubleValue(row, cons) + "", err);
		if (f == 1)
			invalid_flag = true;
	}

	public static void enterDouble_Id(String title, String dir, String loc, int row, int cons, String err) {
		mt.scrollToText(title, dir);
		appdriver.findElementById(loc).sendKeys(xc.getCellDoubleValue(row, cons) + "");
		int f = validCheckString(loc, "id", xc.getCellDoubleValue(row, cons) + "", err);
		if (f == 1)
			invalid_flag = true;
	}

	public static void enterDouble_Xpath(String title, String dir, String loc, int row, int cons, String err) {
		mt.scrollToText(title, dir);
		appdriver.findElementByXPath(loc).sendKeys(xc.getCellDoubleValue(row, cons) + "");
		int f = validCheckString(loc, "xpath", xc.getCellDoubleValue(row, cons) + "", err);
		if (f == 1)
			invalid_flag = true;
	}

	public static void enterString_Id(String title, String dir, String loc, int row, int cons, String err) {
		mt.scrollToText(title, dir);
		appdriver.findElementById(loc).sendKeys(xc.getCellString(row, cons));
		int f = validCheckString(loc, "id", xc.getCellString(row, cons), err);
		if (f == 1)
			invalid_flag = true;
	}

	public static void enterString_Xpath(String title, String dir, String loc, int row, int cons, String err) {
		mt.scrollToText(title, dir);
		appdriver.findElementByXPath(loc).sendKeys(xc.getCellString(row, cons));
		int f = validCheckString(loc, "xpath", xc.getCellString(row, cons), err);
		if (f == 1)
			invalid_flag = true;
	}

	public static void selectById(String title, String dir, String loc, int row, int cons) {
		mt.scrollToText(title, dir);
		appdriver.findElementById(loc).click();
		appdriver
				.findElement(
						MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + xc.getCellString(row, cons) + "\")"))
				.click();
	}

	public static int validCheckString(String loc, String locStrat, String field_txt, String text) {
		if (locStrat.equalsIgnoreCase("xpath")) {
			if (!appdriver.findElementByXPath(loc).getText().equals(field_txt)) {
				System.out.println(text);
				testMeet.log(Status.INFO, text);
				++neg_test_count;
				return 1;
			}
		} else if (locStrat.equalsIgnoreCase("id")) {
			if (!appdriver.findElementById(loc).getText().equals(field_txt)) {
				System.out.println(text);
				testMeet.log(Status.INFO, text);
				++neg_test_count;
				return 1;
			}
		} else if (locStrat.equalsIgnoreCase("UiSelectorText")) {
			if (!appdriver.findElement(MobileBy.AndroidUIAutomator(loc)).getText().equals(field_txt)) {
				System.out.println(text);
				testMeet.log(Status.INFO, text);
				++neg_test_count;
				return 1;
			}
		}
		return 0;
	}

	public static int validOnSave(String txt_msg, int row) throws Exception {

		if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText().equals("Data Updated Successfully")
				|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
						.equals("Data saved successfully")
				|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText().equals(txt_msg))
			return 0;
		else {
			String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
			testMeet.log(Status.FAIL, ex);
			ExtentManager.addScreenShotsToLogFail("SHG Meetings:"+ex, testMeet);
			System.out.println("Error: " + ex);
			if (neg_test_flag) {
				try {
					String exp_errs = xc.getCellString(row, cutoffCons.expErrMessColNum);
					if (ex.contains(exp_errs)) {
						System.out.println("|||||||||||||||||||||||||||||");
						System.out.println("Expected Error is encountered");
						System.out.println("   ((Negetive Test Passed))");
						System.out.println("|||||||||||||||||||||||||||||");
						++neg_test_count;
					} else {
						System.out.println("   (((Negetive Test Failed)))\n");
						testMeet.log(Status.INFO, "Negetive Test Failed");
					}
				} catch (NullPointerException np) {
					System.out.println("---->>Expected Errors is empty.");
					testMeet.log(Status.INFO, "Expected Errors is empty.");
				}
			}
			return 1;
		}

	}

	public static void navigateBackToScreen(String screen_title) throws Exception {
		int i = 0;
		String title = "";
		while (i < 3) {
			try {
				title = appdriver.findElementById("com.microware.cdfi:id/tv_title").getText();
			} catch (Exception e) {
				appdriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
				i++;
			}
			break;
		}

		i = 0;
		try {
			while (!title.equals(screen_title)) {
				appdriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
				if (appdriver.findElementById("com.microware.cdfi:id/tv_title").getText().equals(screen_title))
					break;
				i++;
			}
		} catch (Exception e) {
			ExtentManager.addScreenShotsToTest("Navigate Back to Screen", testMem);
			e.printStackTrace();
			throw new Exception("Cannot navigate to " + screen_title + " screen");
		}
	}
	
	public static void fillColumnFields(int row,int col,int memNum,String a, String b, String c) {
		try {
			boolean flag=false;
			
		java.util.List<AndroidElement> table= appdriver.findElementsByXPath(a);					
		while(true) {
			int k=0;
			for(k=1;k<table.size();k++) {
				appdriver
				.findElementByXPath("//android.widget.LinearLayout[" + k+ b).clear();
				appdriver
				.findElementByXPath("//android.widget.LinearLayout[" + k+ b)
				.sendKeys((int) xc.getCellDoubleValue(row, col) + "");
				if(appdriver.findElementByXPath("//android.widget.LinearLayout[" + k+ c).getText().equals(memNum+"")) {						
					flag=true;
					break;								
				}
				
			}	
			if(flag) 
				break;						
			mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + (k-2)+ b, "xpath", "top");
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
