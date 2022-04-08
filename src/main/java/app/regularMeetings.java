
package app;

import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidElement;
import lokos.lokosTest;
import reporting.ExtentManager;
import util.backButton;
import util.dateLogic;
import util.randomPressLogic;

public class regularMeetings extends lokosTest {

	public static boolean neg_test_flag = false;
	public static int neg_test_count = 0;
	public static boolean invalid_flag = false;
	public static int pass = 0;
	public static int fail = 0;

	public static int[] idSelectRegular(int row) throws Exception {

		String[] idList = { "000" };

		String type = xc.getCellString(row, cutoffCons.typeColNum);

		if (!type.equalsIgnoreCase("New")) {
			String[] typeList = xc.getCellString(row, cutoffCons.typeColNum).split(" ");
			idList = typeList[0].split(",");
		}

		int[] val = regularMeetings.regular(row, idList);
		return val;
	}

	@SuppressWarnings("unused")
	public static int[] regular(int row, String[] idList) throws Exception {

		int count = 0;
		int p = 0;
		int f = 0;
		int id = 000;
		int oldMtngNum = 0;
		int newMtngNum = 0;

		int i = 0;
		if (Integer.valueOf(idList[0]) != 0) {
			for (String s : idList) {
				reg_check[0][Integer.valueOf(idList[i])] = Integer.valueOf(idList[i]);
				++i;
			}
		} else {
			for (int j = 0; j < reg_check[0].length; j++) {
				reg_check[0][j] = j;
			}
		}
		try {
			if (xc.getCellString(row, regCons.typeColNum).contains("Check")) {
				neg_test_flag = true;
				testMeet.log(Status.INFO, "NEGETIVE TESTING");
			}
		} catch (NullPointerException np) {

		}

		if (xc.getCellString(row, cutoffCons.typeColNum).equals("New")) {

			try {
				oldMtngNum = Integer
						.valueOf(appdriver.findElementById("com.microware.cdfi:id/et_old_meeting_no").getText());
			} catch (Exception e) {
				oldMtngNum = 0;
			}
			newMtngNum = Integer.valueOf(xc.getCellString(row, cutoffCons.newMeetingNumColNum));

			if (newMtngNum == (oldMtngNum + 1)) {
				appdriver.findElementById("com.microware.cdfi:id/et_new_meeting_no").sendKeys("" + newMtngNum);
				// validation
				f = validCheckString("com.microware.cdfi:id/et_new_meeting_no", "id",
						xc.getCellString(row, cutoffCons.newMeetingNumColNum),
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

			} else {
				appdriver.findElementById("com.microware.cdfi:id/btn_cancelmeeting").click();
				testMeet.log(Status.FAIL, "Incorrect meeting number for regular meeting");
				System.out.println("Incorrect meeting number for regular meeting");
				appdriver.findElementById("com.microware.cdfi:id/btn_cancelmeeting").click();
				int[] val = { 0, 0, 0 };
				return val;
			}
		} else {
			// Edit Meeting button
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

		testMeet.log(Status.INFO, "Regular Meeting Entry Begin");

		for (int iterations = 0; iterations < idList.length; iterations++) {
			id = Integer.valueOf(idList[iterations]);

			// Attendance
			switch (id) {
			case 0:
			case 1:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_attendence").click();
					appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());

					if (xc.getCellString(row, regCons.Attendance_1).equalsIgnoreCase("Present")) {

					} else if (xc.getCellString(row, regCons.Attendance_1).equalsIgnoreCase("Absent")) {
						try {
							boolean flag = false;
							java.util.List<AndroidElement> table = appdriver.findElementsByXPath(
									"//android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TableRow/android.widget.TableRow[2]/android.widget.ImageView");
							while (true) {
								int k = 0;
								for (k = 1; k <= table.size(); k++) {
									appdriver.findElementByXPath("//android.widget.LinearLayout[" + k
											+ "]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TableRow/android.widget.TableRow[2]/android.widget.ImageView")
											.click();

									if (appdriver.findElementByXPath("//android.widget.LinearLayout[" + k
											+ "]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView[1]")
											.getText().equals(memNum + "")) {
										flag = true;
										break;
									}
								}
								if (flag)
									break;
								mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + (k-1)
										+ "]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TableRow/android.widget.TableRow[2]/android.widget.ImageView",
										"xpath", "top");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					Thread.sleep(2000);
					f = validOnSave("Blank", row);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
					if (f == 1) {
						throw new Exception("Save Failed");
					}

					pass++;
					reg_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "001:Attendance");
					else
						testMeet.log(Status.PASS, "001:Attendance");
					System.out.println("001:Attendence");
				} catch (Exception e) {
					fail++;
					reg_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "001:Attendance");
					else
						testMeet.log(Status.PASS, "001:Attendance");
					System.out.println("Error in Attendance:001----------------------Check Here////");
					navigateBackToScreen("SHG");
					int[] val = { 0, 0, 0 };
					e.printStackTrace();
					return val;

				} finally {
					count++;
					navigateBackToScreen("Meeting Menu");
				}
				if (id != 000)
					break;
				// Compulsory Savings
			case 2:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_compulasory_saving").click();
					int memNum = Integer.valueOf(appdriver.findElementByXPath("//android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.TextView").getText());
					
					String[] cs=((int)xc.getCellDoubleValue(row, cutoffCons.compSavColNum) + "").split(";");
					String comp_sav="";
					for(String sav: cs) {
						sav=sav.trim();
						f=0;
						appdriver.findElementByXPath(
								"//android.widget.LinearLayout[1]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TableRow/android.widget.EditText[1]").clear();
					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TableRow/android.widget.EditText[1]")
							.sendKeys(sav);
					f = validCheckString(
							"//android.widget.LinearLayout[1]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TableRow/android.widget.EditText[1]",
							"xpath", sav,
							"002");
					comp_sav=sav;
					}
					if (f == 1) {
						throw new Exception("002:||Validation Failed||");
					}

					fillColumnFields(comp_sav, 
							memNum,
							"//android.widget.TableRow/android.widget.EditText", 
							"]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TableRow/android.widget.EditText", 
							"]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView[1]");
				

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("", row);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					if (f == 1)
						throw new Exception("");
					pass++;
					reg_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "002:Compulsory Saving");
					else
						testMeet.log(Status.PASS, "002:Compulsory Saving");
					System.out.println("002:Compulsory Saving");
				} catch (Exception e) {
					fail++;
					reg_check[1][id] = -1;
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
			//Loan Repayments
			case 3:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_member_saving").click();
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());
					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[2]")
							.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.volSavColNum) + "");
					f = validCheckString(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[2]",
							"xpath", (int) xc.getCellDoubleValue(row, cutoffCons.volSavColNum) + "",
							"003:||Validation Failed||");
					if (f == 1) {
						throw new Exception("003:||Validation Failed||");
					}

					for (int j = 2; j <= memNum; j++) {
						mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + j
								+ "]/android.widget.LinearLayout/android.widget.EditText[2]", "xpath", "top");

						appdriver
								.findElementByXPath("//android.widget.LinearLayout[" + j
										+ "]/android.widget.LinearLayout/android.widget.EditText[2]")
								.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.compSavColNum) + "");
					}

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
					f = validOnSave("", row);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					if (f == 1)
						throw new Exception("");

					pass++;
					reg_check[1][id] = 1;
					if (neg_test_flag)
						testMeet.log(Status.FAIL, "003:Voluntary Saving");
					else
						testMeet.log(Status.PASS, "003:Voluntary Saving");
					System.out.println("003:Voluntary Saving");
				} catch (Exception e) {
					fail++;
					reg_check[1][id] = -1;
					if (!neg_test_flag)
						testMeet.log(Status.FAIL, "003:Voluntary Saving");
					else
						testMeet.log(Status.PASS, "003:Voluntary Saving");
					System.out.println("Error in Voluntary Saving:003----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
				}
				if (id != 000)
					break;
			// Share Capital/Other/Receipts
			case 4:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_share_capital").click();
					
					for(int j=row+1;j<=(row+Integer.valueOf(xc.getCellString(row, regCons.Receipt_Type_4)));j++) {
						
						appdriver.findElementByXPath("//android.widget.LinearLayout[1]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ImageView[1]").click();
												
						selectById("Receipt Type", "top", "com.microware.cdfi:id/spin_TypeofReceipt", j, regCons.Receipt_Type_4 );
						
						enterString_Id("Amount", "top", "com.microware.cdfi:id/et_amount", j, regCons.Amount_104, "104");
						
						selectById("Mode of Receipt","top","com.microware.cdfi:id/spin_mode_of_payment",j,regCons.Mode_of_Recept_204);
						
						if(!xc.getCellString(j, regCons.Mode_of_Recept_204).equalsIgnoreCase("Cash")) {							
							selectById("Bank name","top","com.microware.cdfi:id/spin_BankName",j,regCons.Bank_304);
							enterString_Id("Cheque number/Transaction number","top","com.microware.cdfi:id/et_cheque_no_transactio_no",j,regCons.Cheque_number_404,"404");
						}
						
						appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
						f = validOnSave2("", j,regCons.Exp_Err_Msg_4999);
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
						if(f==1) {
							try {
							appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}catch(Exception e) {
								randomPressLogic.press(0.5, 0.05);
								appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}
						}
						
					}
									
					
					if (f == 1)
						throw new Exception("");

					pseq(4,"004:Share Capital/Other Receipts");					
				} catch (Exception e) {
					fseq(4,"004:Share Capital/Other Receipts",e);
				} finally {
					count++;
					try {
					navigateBackToScreen("Meeting Menu");
					}catch(Exception e) {
						
					}
				}
				if (id != 000)
					break;
				//Other Payments
			case 5:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_other_payments").click();

					for(int j=row+1;j<=(row+Integer.valueOf(xc.getCellString(row, regCons.Payment_Type_5)));j++) {
						
						appdriver.findElementByXPath("//android.widget.LinearLayout[1]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ImageView[1]").click();
												
						selectById("Payment Type", "top", "com.microware.cdfi:id/spin_TypeofPayment", j, regCons.Payment_Type_5 );
						
						enterString_Id("Amount", "top", "com.microware.cdfi:id/et_amount", j, regCons.Amount_105, "105");
						
						selectById("Mode of Receipt","top","com.microware.cdfi:id/spin_mode_of_payment",j,regCons.Mode_of_Recept_205);
						
						if(!xc.getCellString(j, regCons.Mode_of_Recept_204).equalsIgnoreCase("Cash")) {							
							selectById("Bank name","top","com.microware.cdfi:id/spin_BankName",j,regCons.Bank_305);
							enterString_Id("Cheque number/Transaction number","top","com.microware.cdfi:id/et_cheque_no_transactio_no",j,regCons.Cheque_number_405,"405");
						}
						
						appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
						f = validOnSave2("", j,regCons.Exp_Err_Msg_5999);
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
						if(f==1) {
							try {
							appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}catch(Exception e) {
								randomPressLogic.press(0.5, 0.05);
								appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}
						}
						
					}		
					
					if (f == 1)
						throw new Exception("");

					pseq(5,"005:Other Payments");
				} catch (Exception e) {
					fseq(5,"005:Other Payments",e);					
				} finally {
					count++;
					navigateBackToScreen("Meeting Menu");
				}
				if (id != 000)
					break;
				// Loan Request(Demand)
			case 6:
				try {
					mt.scrollToText("Loan Request", "top");
					appdriver.findElementById("com.microware.cdfi:id/tbl_loanRequest").click();
					
					String purpose="";
					f=0;
					for(int j=row+1;j<=(row+Integer.valueOf(xc.getCellString(row, regCons.MCP_link_6)));j++) {
						
						if((f==1)&&(j>(row+1)))
							appdriver.findElementById("com.microware.cdfi:id/img_Add").click();
						else if(j==(row+1))
							appdriver.findElementById("com.microware.cdfi:id/img_Add").click();
						else
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + purpose + "\")"));
						
						if(f==1)
						selectFirstOptionById("Name of Member","top","com.microware.cdfi:id/spin_entry_member_name",
								"//android.widget.FrameLayout/android.widget.ListView/android.widget.TextView[2]");
						
						//selectById("MCP link","top","com.microware.cdfi:id/spin_mcp_link",j,regCons.MCP_link_6);
						
						enterString_Id("Proposed Amount","top","com.microware.cdfi:id/et_amount",j,regCons.Proposed_Amount_106,"106");
						
						purpose=xc.getCellString(j, regCons.Purpose_206);
						selectById("Purpose", "top", "com.microware.cdfi:id/spin_purpose", j, regCons.Purpose_206);
						
						enterString_Id("Sanction Amount","top","com.microware.cdfi:id/et_sanction_amount",j,regCons.Sanction_Amount_306,"306");
						
						selectById("Fund Source","top","com.microware.cdfi:id/spin_source",j,regCons.Fund_Source_406);
						
						enterString_Id("Priority","top","com.microware.cdfi:id/et_priority",j,regCons.Priority_506,"506");
						
						dateLogic.datePicker(xc.getCellString(j, regCons.Request_Valid_up_to_606), "com.microware.cdfi:id/et_priority_valid_upto");
						
						
						appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
						f = validOnSave2("", j,regCons.Exp_Err_Msg_6999);
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
						if(f==1) {
							try {
							appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}catch(Exception e) {
								randomPressLogic.press(0.5, 0.05);
								appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}
						}
						
					}		
					
					if (f == 1)
						throw new Exception("");

					pseq(6,"006: Loan Request(Add Demand)");
				} catch (Exception e) {
					fseq(6,"006: Loan Request(Add Demand)",e);
				} finally {
					count++;
					navigateBackToScreen("Meeting Menu");
				}
				if (id != 000)
					break;
				//Loan Request(MCP)
			case 7:
				try {
					mt.scrollToText("Loan Request", "top");
					appdriver.findElementById("com.microware.cdfi:id/tbl_loanRequest").click();
					appdriver.findElementById("com.microware.cdfi:id/img_AddMcp").click();

					String purpose="";
					f=0;					
					for(int j=row+1;j<=(row+Integer.valueOf(xc.getCellString(row, regCons.Demand_Amount_7)));j++) {
						
						if((f==1)&&(j>(row+1)))
							appdriver.findElementById("com.microware.cdfi:id/ivAddMcp").click();
						else if(j==(row+1))
							appdriver.findElementById("com.microware.cdfi:id/ivAddMcp").click();
						else
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + purpose + "\")"));
						
						if(f==1)
						selectFirstOptionById("Name of Member","top","com.microware.cdfi:id/spin_entry_membername",
								"/android.widget.FrameLayout/android.widget.ListView/android.widget.TextView[2]");
						
						enterString_Id("Demand (Amount)","top","com.microware.cdfi:id/et_demand_amount",j,regCons.Demand_Amount_7,"007");
												
						selectById("Demand (Purpose)", "top", "com.microware.cdfi:id/spin_demand_purpose", j, regCons.Demand_Purpose_107);
																		
						dateLogic.datePicker(xc.getCellString(j, regCons.Request_Valid_Upto_207), "com.microware.cdfi:id/et_priority_valid_upto");
						
						enterString_Id("Proposed EMI Amount","top","com.microware.cdfi:id/et_proposed_emi_amount",j,regCons.Proposed_EMI_Amount_307,"307");
						
						appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
						f = validOnSave2("", j,regCons.Exp_Err_Msg_7999);
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
						if(f==1) {
							try {
							appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}catch(Exception e) {
								randomPressLogic.press(0.5, 0.05);
								appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}
						}
						
					}		
					
					if (f == 1)
						throw new Exception("");

					pseq(7,"007: Loan Request(MCP)");
				} catch (Exception e) {
					fseq(7,"007: Loan Request(MCP)",e);
				} finally {
					count++;
					navigateBackToScreen("Meeting Menu");
				}
				if (id != 000)
					break;
				//Loan Disbursement
			case 8:
				try {
					mt.scrollToText("Loan Disbursement", "top");
					appdriver.findElementById("com.microware.cdfi:id/tbl_loandisbursement").click();
					
					String name="";
					int editbtn=0;
					String purpose="";
					f=0;
					
					appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());
									
					for(int j=row+1;j<=(row+Integer.valueOf(xc.getCellString(row, regCons.Fund_Source_8)));j++) {
						
						try {
							boolean flag = false;
							java.util.List<AndroidElement> table = appdriver.findElementsByXPath(
									"//android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TableRow/android.widget.TextView[4]");

							while (true) {
								int k = 0;
								for (k = 1; k <= table.size(); k++) {
									if (appdriver.findElementByXPath("//android.widget.LinearLayout[" + k
											+ "]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TableRow/android.widget.TextView[4]")
											.getText().equals("0")) {
										continue;
									} else {
										flag = true;
										appdriver.findElementByXPath("//android.widget.LinearLayout["+k+"]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TableRow/android.widget.TableRow/android.widget.ImageView").click();
										
									}
								}
								if (flag)
									break;
								mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + (k - 1)
										+ "]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TableRow/android.widget.TextView[4]",
										"xpath", "top");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
												
						enterString_Id("Amount","top","com.microware.cdfi:id/et_amount",j,regCons.Amount_108,"108");
												
						selectById("Loan Purpose", "top", "com.microware.cdfi:id/spin_loan_source", j, regCons.Loan_Purpose_208);
						
						selectById("Mode of Receipt", "top", "com.microware.cdfi:id/spin_mode_of_payment", j,
								regCons.Mode_of_Receipt_308);

						if (!xc.getCellString(j, regCons.Mode_of_Receipt_210).equals("Cash")) {
							selectById("Source Bank", "top", "com.microware.cdfi:id/spin_source_bank", j, regCons.Source_Bank_408);
							enterString_Id("Cheque No./Transaction No.", "top",
									"com.microware.cdfi:id/et_cheque_no_transactio_no", j, regCons.Cheque_number_508,
									"508");
						}
						
						enterString_Id("Period (Months)","top","com.microware.cdfi:id/et_no_of_installment",j,regCons.Period_Months_608,"608");
						
						enterString_Id("Interest Rate (Annualy)","top","com.microware.cdfi:id/et_monthly_interest_rate",j,regCons.Interest_Rate_Annually_708,"708");
						
						enterString_Id("Moratorium Period","top","com.microware.cdfi:id/et_moratorium_period",j,regCons.Moratorium_Period_808,"808");
						
						//selectById("Loan Repayment Frequency", "top", "com.microware.cdfi:id/spin_frequency", j, regCons.Loan_Repayment_Frequency_908);
												
						appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
						f = validOnSave2("", j,regCons.Exp_Err_Msg_8999);
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
						if(f==1) {
							try {
							appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}catch(Exception e) {
								randomPressLogic.press(0.5, 0.05);
								appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}
						}
						
					}		
					
					if (f == 1)
						throw new Exception("");

					pseq(8,"008: Loan Disbursement");
				} catch (Exception e) {
					fseq(8,"008: Loan Disbursement",e);
				} finally {
					count++;
					navigateBackToScreen("Meeting Menu");
				}
				if (id != 000)
					break;
				//Withdrawal
			case 9:
				try {
					mt.scrollToText("Withdrawal", "top");
					appdriver.findElementById("com.microware.cdfi:id/tbl_withdrawl").click();

					String purpose="";
					f=0;					
					for(int j=row+1;j<=(row+Integer.valueOf(xc.getCellString(row, regCons.Amount_9)));j++) {
						
						appdriver.findElementByXPath("//android.widget.LinearLayout[1]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView[4]").click();
						enterString_Id("Amount","top","com.microware.cdfi:id/et_amount_paid_to_member",j,regCons.Amount_9,"009");
						
						appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
						f = validOnSave2("", j,regCons.Exp_Err_Msg_9999);
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
						if(f==1) {
							try {
							appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}catch(Exception e) {
								randomPressLogic.press(0.5, 0.05);
								appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}
						}
						
					}		
					
					if (f == 1)
						throw new Exception("");

					pseq(9,"009: Withdrawal");
				} catch (Exception e) {
					fseq(9,"009: Withdrawal",e);
				} finally {
					count++;
					navigateBackToScreen("Meeting Menu");
				}
				if (id != 000)
					break;
				//Group Investment
			case 10:
				try {
					mt.scrollToText("New Borrowings", "top");
					appdriver.findElementById("com.microware.cdfi:id/tbl_group_investment").click();

					String purpose="";
					f=0;					
					for(int j=row+1;j<=(row+Integer.valueOf(xc.getCellString(row, regCons.Investment_at_10)));j++) {
						
						if((f==1)&&(j>(row+1)))
							appdriver.findElementById("com.microware.cdfi:id/ivAdd").click();
						else if(j==(row+1))
							appdriver.findElementById("com.microware.cdfi:id/ivAdd").click();
						else
							appdriver.findElementById("com.microware.cdfi:id/ivEdit").click();
						
						selectById("Investment at","top","com.microware.cdfi:id/spin_source",j,regCons.Investment_at_10);

						selectById("Type of Saving", "top", "com.microware.cdfi:id/spin_saving_type", j, regCons.Type_of_Saving_110);
																							
						selectById("Mode of Receipt", "top", "com.microware.cdfi:id/spin_mode_of_payment", j,
								regCons.Mode_of_Receipt_210);

						if (!xc.getCellString(j, regCons.Mode_of_Receipt_210).equals("Cash")) {
							selectById("Bank name", "top", "com.microware.cdfi:id/spin_BankName", j, regCons.Bank_310);
							enterString_Id("Cheque number/Transaction number", "top",
									"com.microware.cdfi:id/et_cheque_no_transactio_no", j, regCons.Cheque_number_410,
									"410");
						}
						selectById("Type of Loan", "top", "com.microware.cdfi:id/spin_type_of_loan", j, regCons.Type_of_Loan_511);
												
						enterString_Id("Amount Withdrawn","top","com.microware.cdfi:id/et_amount_disbursed",j,regCons.Amount_Withdrawn_611,"611");
						
						enterString_Id("Interest Rate (Annualy)","top","com.microware.cdfi:id/et_interest_rate",j,regCons.Interest_Rate_Annualy_711,"711");
						
						enterString_Id("Period (Months)","top","com.microware.cdfi:id/et_period_months",j,regCons.Period_Months_811,"811");
						
						enterString_Id("Moratorium Period","top","com.microware.cdfi:id/et_moratorium_period",j,regCons.Period_Months_811,"811");
						
						selectById("Loan Repayment Frequency","top","com.microware.cdfi:id/spin_frequency",j,regCons.Loan_Repayment_Frequency_1011);
						
						selectById("Mode of Receipt", "top", "com.microware.cdfi:id/spin_mode_of_payment", j,
								regCons.Mode_of_Receipt_1111);

						if (!xc.getCellString(j, regCons.Mode_of_Receipt_1111).equals("Cash")) {
							selectById("Bank name", "top", "com.microware.cdfi:id/spin_BankName", j, regCons.Bank_1211);
							enterString_Id("Cheque number/Transaction number", "top",
									"com.microware.cdfi:id/et_cheque_no_transactio_no", j, regCons.Cheque_number_1311,
									"1311");
						}
												
						appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
						f = validOnSave2("", j,regCons.Exp_Err_Msg_10999);
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
						if(f==1) {
							try {
							appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}catch(Exception e) {
								randomPressLogic.press(0.5, 0.05);
								appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}
						}

						
						
					}		
					
					if (f == 1)
						throw new Exception("");

					pseq(10,"010: Group Investment");
				} catch (Exception e) {
					fseq(10,"010: Group Investment",e);
				} finally {
					count++;
					navigateBackToScreen("Meeting Menu");
				}
				if (id != 000)
					break;				
				//New Borrowings
			case 11:
				try {
					mt.scrollToText("New Borrowings", "top");
					appdriver.findElementById("com.microware.cdfi:id/tbl_new_borrowing").click();

					String purpose="";
					f=0;					
					for(int j=row+1;j<=(row+Integer.valueOf(xc.getCellString(row, regCons.Institution_11)));j++) {
						
						if((f==1)&&(j>(row+1)))
							appdriver.findElementById("com.microware.cdfi:id/img_Add").click();
						else if(j==(row+1))
							appdriver.findElementById("com.microware.cdfi:id/img_Add").click();
						else
							appdriver.findElementById("com.microware.cdfi:id/img_edit").click();
						
						selectById("Institution","top","com.microware.cdfi:id/spin_institution",j,regCons.Institution_11);
												
						enterString_Id("Loan Account No","top","com.microware.cdfi:id/et_loan_Refno",j,regCons.Loan_Account_No_111,"111");

						dateLogic.datePicker(xc.getCellString(j, regCons.Disbursal_Date_211), "com.microware.cdfi:id/et_date_loan_taken");
						
						dateLogic.datePicker(xc.getCellString(j, regCons.Effective_Date_311), "com.microware.cdfi:id/tv_effective_date");
						
						selectById("Fund Source", "top", "com.microware.cdfi:id/spin_loan_type", j, regCons.Fund_Source_411);
																							
						selectById("Type of Loan", "top", "com.microware.cdfi:id/spin_type_of_loan", j, regCons.Type_of_Loan_511);
												
						enterString_Id("Amount Withdrawn","top","com.microware.cdfi:id/et_amount_disbursed",j,regCons.Amount_Withdrawn_611,"611");
						
						enterString_Id("Interest Rate (Annualy)","top","com.microware.cdfi:id/et_interest_rate",j,regCons.Interest_Rate_Annualy_711,"711");
						
						enterString_Id("Period (Months)","top","com.microware.cdfi:id/et_period_months",j,regCons.Period_Months_811,"811");
						
						enterString_Id("Moratorium Period","top","com.microware.cdfi:id/et_moratorium_period",j,regCons.Period_Months_811,"811");
						
						selectById("Loan Repayment Frequency","top","com.microware.cdfi:id/spin_frequency",j,regCons.Loan_Repayment_Frequency_1011);
						
						selectById("Mode of Receipt", "top", "com.microware.cdfi:id/spin_mode_of_payment", j,
								regCons.Mode_of_Receipt_1111);

						if (!xc.getCellString(j, regCons.Mode_of_Receipt_1111).equals("Cash")) {
							selectById("Bank name", "top", "com.microware.cdfi:id/spin_BankName", j, regCons.Bank_1211);
							enterString_Id("Cheque number/Transaction number", "top",
									"com.microware.cdfi:id/et_cheque_no_transactio_no", j, regCons.Cheque_number_1311,
									"1311");
						}

						
						appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
						f = validOnSave2("", j,regCons.Exp_Err_Msg_11999);
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
						if(f==1) {
							try {
							appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							mt.scrollToText("New Borrowings","top");
							appdriver.findElementById("com.microware.cdfi:id/tbl_new_borrowing").click();
							}catch(Exception e) {
								randomPressLogic.press(0.5, 0.05);
								appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
								mt.scrollToText("New Borrowings","top");
								appdriver.findElementById("com.microware.cdfi:id/tbl_new_borrowing").click();
							}
						}						
					}		
					
					if (f == 1)
						throw new Exception("");

					pseq(11,"011: New Borrowings");
				} catch (Exception e) {
					fseq(11,"011: New Borrowings",e);
				} finally {
					count++;
					navigateBackToScreen("Meeting Menu");
				}
				if (id != 000)
					break;				
				//Receipt and income
			case 12:
				try {
					mt.scrollToText("Receipt & Income", "top");
					appdriver.findElementById("com.microware.cdfi:id/tbl_recepient_and_income").click();

					String purpose="";
					f=0;					
					for (int j = row + 1; j <= (row
							+ Integer.valueOf(xc.getCellString(row, regCons.Received_from_12))); j++) {

						appdriver.findElementById("com.microware.cdfi:id/ivAdd").click();

						selectById("Received from", "top", "com.microware.cdfi:id/spin_received_from", j,
								regCons.Received_from_12);
						selectById("Receipt", "top", "com.microware.cdfi:id/spin_receipt", j, regCons.Receipt_112);
						selectById("Mode of Receipt", "top", "com.microware.cdfi:id/spin_mode_of_payment", j,
								regCons.Mode_of_Receipt_212);

						if (!xc.getCellString(j, regCons.Mode_of_Receipt_212).equals("Cash")) {
							selectById("Bank name", "top", "com.microware.cdfi:id/spin_BankName", j, regCons.Bank_312);
							enterString_Id("Cheque number/Transaction number", "top",
									"com.microware.cdfi:id/et_cheque_no_transactio_no", j, regCons.Cheque_number_412,
									"412");
						}
						enterString_Id("Amount", "top", "com.microware.cdfi:id/et_amount", j, regCons.Amount_512,
								"512");

						appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
						f = validOnSave2("", j,regCons.Exp_Err_Msg_12999);
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
						if (f == 1) {
							try {
								appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							} catch (Exception e) {
								randomPressLogic.press(0.5, 0.05);
								appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}
						}
					}
					
					if (f == 1)
						throw new Exception("");

					pseq(12,"012: Receipt and Income");
				} catch (Exception e) {
					fseq(12,"012: Receipt and Income",e);
				} finally {
					count++;
					navigateBackToScreen("Meeting Menu");
				}
				if (id != 000)
					break;
				//Loan Repayments
			case 13:
				try {
					mt.scrollToText("Loan Repayment", "top");
					appdriver.findElementById("com.microware.cdfi:id/tbl_group_loan_repayment").click();

					String purpose="";
					f=0;					
					for(int j=row+1;j<=(row+Integer.valueOf(xc.getCellString(row, regCons.Demand_Amount_7)));j++) {
						
						
						
					}		
					
					if (f == 1)
						throw new Exception("");

					pseq(13,"013: Loan repayment");
				} catch (Exception e) {
					fseq(13,"013: Loan Repayment",e);
				} finally {
					count++;
					navigateBackToScreen("Meeting Menu");
				}
				if (id != 000)
					break;
				//Expenditure and Payment
			case 14:
				try {
					mt.scrollToText("Expenditure & Payment","top");
					appdriver.findElementById("com.microware.cdfi:id/tbl_expenditure_and_payment").click();

					f = 0;
					for (int j = row + 1; j <= (row
							+ Integer.valueOf(xc.getCellString(row, regCons.Paid_To_14))); j++) {

						appdriver.findElementById("com.microware.cdfi:id/ivAdd").click();

						selectById("Paid To", "top", "com.microware.cdfi:id/spin_paidTo", j, regCons.Paid_To_14);
						selectById("Payment", "top", "com.microware.cdfi:id/spin_payment", j, regCons.Payment_114);
						selectById("Mode of payment", "top", "com.microware.cdfi:id/spin_mode_of_payment", j,
								regCons.Mode_of_Payment_214);

						if (!xc.getCellString(j, regCons.Mode_of_Payment_214).equals("Cash")) {
							selectById("Bank name", "top", "com.microware.cdfi:id/spin_BankName", j, regCons.Bank_314);
							enterString_Id("Cheque number/Transaction number", "top",
									"com.microware.cdfi:id/et_cheque_no_transactio_no", j, regCons.Cheque_number_414,
									"414");
						}
						enterString_Id("", "top", "", j, regCons.Amount_514, "514");

						appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
						f = validOnSave2("", j,regCons.Exp_Err_Msg_14999);
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
						if (f == 1) {
							try {
								appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							} catch (Exception e) {
								randomPressLogic.press(0.5, 0.05);
								appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
							}
						}
					}
					
					if (f == 1)
						throw new Exception("");

					pseq(14,"014: Expenditure and Payment");
				} catch (Exception e) {
					fseq(14,"014: Expenditure and Payment",e);
				} finally {
					count++;
					navigateBackToScreen("Meeting Menu");
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
			navigateBackToScreen("Meeting Menu");
		}
		appdriver.findElementById("com.microware.cdfi:id/btn_yes").click();
		reporting.ExtentManager.addScreenShotsToTest("Cutoff Result 1", testFlow);
		mt.scrollToText("Bank Balance", "top");
		reporting.ExtentManager.addScreenShotsToTest("Cutoff Result 2", testFlow);
		appdriver.findElementById("com.microware.cdfi:id/btn_sendApproval").click();

		int[] val = { 0, 0, 0 };
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
	public static void selectByXpath(String title, String dir, String loc, int row, int cons) {
		mt.scrollToText(title, dir);
		appdriver.findElementByXPath(loc).click();
		appdriver
				.findElement(
						MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + xc.getCellString(row, cons) + "\")"))
				.click();
	}
	public static void selectFirstOptionById(String title, String dir, String loc, String xpath) {
		mt.scrollToText(title, dir);
		appdriver.findElementById(loc).click();
		appdriver.findElementByXPath(xpath).click();
	}
	
	public static void enterLongNum_Id(String title, String dir, String loc, int row, int cons, String err, String prefix) {
		mt.scrollToText(title, dir);
		appdriver.findElementById(loc).sendKeys(xc.getCellString(row, cons).substring(1));
		int f = validCheckLongNum(loc, "id", xc.getCellString(row, cons), err,prefix);
		if (f == 1)
			invalid_flag = true;
	}

	public static int validCheckLongNum(String loc, String locStrat, String field_txt, String text,String prefix) {
		int i=0;
		String s="";
		if (locStrat.equalsIgnoreCase("xpath")) {
			s=(prefix + appdriver.findElementByXPath(loc).getText());
			if (!s.equals(field_txt)) {				
				i= 1;
			}
		} else if (locStrat.equalsIgnoreCase("id")) {
			s=(prefix + appdriver.findElementById(loc).getText());
			if (!s.equals(field_txt)) {
				i= 1;
			}
		} else if (locStrat.equalsIgnoreCase("UiSelectorText")) {
			s=(prefix + appdriver.findElement(MobileBy.AndroidUIAutomator(loc)).getText());
			if (!s.equals(field_txt+" Field has "+s+" instead of "+field_txt)) {
				i= 1;
			}
		}
		if(i==1) {
			System.out.println(text);
			testMem.log(Status.INFO, text);
			++neg_test_count;
			return 1;
		}
		return 0;
	}

	public static int validCheckString(String loc, String locStrat, String field_txt, String text) {
		int i=0;
		String s="";
		if (locStrat.equalsIgnoreCase("xpath")) {
			s=appdriver.findElementByXPath(loc).getText();
			if (!s.equals(field_txt)) i= 1;			
		} else if (locStrat.equalsIgnoreCase("id")) {
			s=appdriver.findElementById(loc).getText();
			if (!s.equals(field_txt)) i= 1;
		} else if (locStrat.equalsIgnoreCase("UiSelectorText")) {
			s=appdriver.findElement(MobileBy.AndroidUIAutomator(loc)).getText();
			if (!s.equals(field_txt)) i= 1;
		}
		if(i==1) {
			System.out.println(text+":||Validation Failed||Field has "+s+" instead of "+field_txt);
			testMeet.log(Status.INFO, text);
			++neg_test_count;
			return 1;
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
			testMeet.log(Status.FAIL, "ex");
			ExtentManager.addScreenShotsToLogFail("SHG Meetings "+ex, testMeet);
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
	
	public static int validOnSave2(String txt_msg, int row,int col) throws Exception {

		if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText().equals("Data Updated Successfully")
				|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
						.equals("Data saved successfully")
				|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText().equals(txt_msg))
			return 0;
		else {
			String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
			testMeet.log(Status.FAIL, "ex");
			ExtentManager.addScreenShotsToLogFail("SHG Meetings "+ex, testMeet);
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
		Thread.sleep(1000);
		int i = 0;
		String title = "";
		while (i < 3) {
			try {
				title = appdriver.findElementById("com.microware.cdfi:id/tv_title").getText();
			} catch (Exception e) {
				backButton.back();
				i++;
			}
			break;
		}

		i = 0;
		try {
			while (!title.equals(screen_title)) {
				backButton.back();
				if (appdriver.findElementById("com.microware.cdfi:id/tv_title").getText().equals(screen_title))
					break;
				i++;
			}
		} catch (Exception e) {
			ExtentManager.addScreenShotsToTest("Navigate Back to Screen", testMem);
			System.out.println("Cannot navigate to " + screen_title + " screen");
			e.printStackTrace();
		}
	}
	public static void fillColumnFields(String entry,int memNum,String a, String b, String c) {
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
				.sendKeys(entry);
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
	
	public static void pseq(int id, String msg) {
		pass++;
		reg_check[1][id] = 1;
		if (neg_test_flag)
			testMeet.log(Status.FAIL, msg);
		else
			testMeet.log(Status.PASS, msg);
		System.out.println(msg);
	}

	public static void fseq(int id, String msg, Exception e) {
		fail++;
		reg_check[1][id] = -1;
		if (!neg_test_flag)
			testMeet.log(Status.FAIL, msg);
		else
			testMeet.log(Status.PASS, msg);
		System.out.println("Error in " + msg + "----------------------Check Here////");
		e.printStackTrace();
	}

}
