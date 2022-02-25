package app;

import com.aventstack.extentreports.Status;

import lokos.lokosTest;
import util.dateLogic;

public class cutoffMeetings extends lokosTest {

	public static int[] idSelectCutoff(int row) throws InterruptedException {

		String shg = xc.getCellString(row, cutoffCons.shgColNum);
		testCoff = reports
				.createTest("Cutoff Meeting: " + shg + "(" + xc.getCellString(row, cutoffCons.typeColNum) + ")");

		testCoff.log(Status.INFO, "Cutoff Flow Started");

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
	public static int[] cutoff(int row, String[] idList) throws InterruptedException {

		int count = 0;
		int pass = 0;
		int fail = 0;
		int p = 0;
		int f = 0;
		int id = 000;
		int oldMtngNum = 0;
		int newMtngNum = 0;

		int i = 0;
		if (Integer.valueOf(idList[1]) != 0) {
			for (String s : idList) {
				cutoff_check[0][Integer.valueOf(idList[i])] = Integer.valueOf(idList[i]);
				++i;
			}
		} else {
			for (int j = 0; j < cutoff_check[0].length; j++) {
				cutoff_check[0][j] = j;
			}
		}

		boolean neg_test_flag = false;
		int neg_test_count = 0;

		try {
			if (xc.getCellString(row, cutoffCons.typeColNum).contains("Check")) {
				neg_test_flag = true;
				testCoff.log(Status.INFO, "NEGETIVE TESTING");
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
			newMtngNum = (int) xc.getCellDoubleValue(row, cutoffCons.newMeetingNumColNum);

			if (newMtngNum > (oldMtngNum + 11)) {
				appdriver.findElementById("com.microware.cdfi:id/et_new_meeting_no").sendKeys("" + newMtngNum);
				// validation
				if (!appdriver.findElementById("com.microware.cdfi:id/et_new_meeting_no").getText()
						.equals(xc.getCellString(row, cutoffCons.newMeetingNumColNum))) {
					System.out.println("Validation Failed: New Meeting Number");
					if(neg_test_flag)
						testCoff.log(Status.PASS, "Validation Failed: New Meeting Number");
					else {
						testCoff.log(Status.FAIL, "Validation Failed: New Meeting Number");
					}
					appdriver.findElementById("com.microware.cdfi:id/btn_cancelmeeting").click();
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
					testCoff.log(Status.FAIL, appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText());
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					appdriver.findElementById("com.microware.cdfi:id/btn_cancelmeeting").click();
					int[] val = { 0, 0, 0 };
					return val;
				}

			} else {
				appdriver.findElementById("com.microware.cdfi:id/btn_cancelmeeting").click();
				testCoff.log(Status.FAIL, "Incorrect meeting number for cutoff");
				System.out.println("Incorrect meeting number for cutoff");
				appdriver.findElementById("com.microware.cdfi:id/btn_cancelmeeting").click();
				int[] val = { 0, 0, 0 };
				return val;
			}
		}

		testCoff.log(Status.INFO, "Cutoff Entry Begin");

		for (int iterations = 0; iterations < idList.length; iterations++) {
			id = Integer.valueOf(idList[iterations]);

			// ATTENDENCE
			switch (id) {
			case 0:
			case 1:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_attendence").click();
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());
					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText")
							.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.attendenceColNum) + "");
					
					try {//validation
						if (!appdriver.findElementByXPath(
								"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText")
								.getText().equals(xc.getCellString(row, cutoffCons.attendenceColNum))) {
							if(neg_test_flag) {
								++neg_test_count;
							}
							System.out.println("001:||Validation Failed||");
							testCoff.log(Status.INFO, "001:||Validation Failed||");
							throw new Exception("001:||Validation Failed||");
						}
					} catch (Exception e) {						
						if(neg_test_flag) {
							++neg_test_count;
						}
						System.out.println("001:||Validation Failed||");
						testCoff.log(Status.INFO, "001:||Validation Failed||");
						throw new Exception("001:||Validation Failed||");
					}

					
					for (int j = 2; j <= memNum; j++) {
						mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + j
								+ "]/android.widget.LinearLayout/android.widget.EditText", "xpath", "top");

						appdriver
								.findElementByXPath("//android.widget.LinearLayout[" + j
										+ "]/android.widget.LinearLayout/android.widget.EditText")
								.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.attendenceColNum) + "");
					}

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();

					if (!appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
							.equals("Data Updated Successfully")
							|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
									.equals("Data saved successfully"))
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					else {
						String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
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
								}
							} catch (NullPointerException np) {
								System.out.println("---->>Expected Errors is empty.");
							}
						}
						throw new Exception(ex);
					}
					pass++;
					cutoff_check[1][id]=1;
					if(neg_test_flag)
						testCoff.log(Status.FAIL, "001:Attendence");
					else
						testCoff.log(Status.PASS, "001:Attendence");
					System.out.println("001:Attendence");
				} catch (Exception e) {					
					fail++;
					cutoff_check[1][id]=-1;
					if(!neg_test_flag)
						testCoff.log(Status.FAIL, "001:Attendence");
					else
						testCoff.log(Status.PASS, "001:Attendence");
					System.out.println("Error in Attendence:001----------------------Check Here////");
					id=999;
					e.printStackTrace();
				} finally {
					count++;
					appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
				}
				if (id != 000)
					break;
			default:
				break;
			}
			
			
			// Member's Saving
			switch (id) {
			case 0:
			case 2:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_member_saving").click();
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());
					
					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[1]")
							.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.compSavColNum) + "");
					try {
						if (!appdriver.findElementByXPath(
								"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[1]")
								.getText().equals(xc.getCellString(row, cutoffCons.compSavColNum))) {
							if(neg_test_flag) {
								++neg_test_count;
							}
							System.out.println("002:||Validation Failed||");
							testCoff.log(Status.INFO, "002:||Validation||");
							throw new Exception("002:||Validation Failed||");
						}
					} catch (Exception e) {						
						if(neg_test_flag) {
							++neg_test_count;
						}
						System.out.println("001:||Validation Failed||");
						testCoff.log(Status.INFO, "001:||Validation Failed||");
						throw new Exception("001:||Validation Failed||");
					}

					
					for (int j = 2; j <= memNum; j++) {
						mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + j
								+ "]/android.widget.LinearLayout/android.widget.EditText[1]", "xpath", "top");

						appdriver
								.findElementByXPath("//android.widget.LinearLayout[" + j
										+ "]/android.widget.LinearLayout/android.widget.EditText[1]")
								.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.compSavColNum) + "");
					}

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();

					if (!appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
							.equals("Data Updated Successfully")
							|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
									.equals("Data saved successfully"))
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					else {
						String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
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
								}
							} catch (NullPointerException np) {
								System.out.println("---->>Expected Errors is empty.");
							}
						}
						throw new Exception(ex);
					}
					pass++;
					cutoff_check[1][id]=1;
					if(neg_test_flag)
						testCoff.log(Status.FAIL, "002:Compulsory Saving");
					else
						testCoff.log(Status.PASS, "002:Compulsory Saving");
					System.out.println("002:Compulsory Saving");
				} catch (Exception e) {					
					fail++;
					cutoff_check[1][id]=-1;
					if(!neg_test_flag)
						testCoff.log(Status.FAIL, "002:Compulsory Saving");
					else
						testCoff.log(Status.PASS, "002:Compulsory Saving");
					System.out.println("Error in Compulsory Saving:002----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
				}
				if (id != 000)
					break;
			case 3:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_member_saving").click();
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());
					
					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[2]")
							.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.volSavColNum) + "");
					try {
						if (!appdriver.findElementByXPath(
								"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[2]")
								.getText().equals(xc.getCellString(row, cutoffCons.volSavColNum))) {
							if(neg_test_flag) {
								++neg_test_count;
							}
							System.out.println("003:||Validation Failed||");
							testCoff.log(Status.INFO, "003:||Validation||");
							throw new Exception("003:||Validation Failed||");
						}
					} catch (Exception e) {						
						if(neg_test_flag) {
							++neg_test_count;
						}
						System.out.println("003:||Validation Failed||");
						testCoff.log(Status.INFO, "003:||Validation Failed||");
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

					if (!appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
							.equals("Data Updated Successfully")
							|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
									.equals("Data saved successfully"))
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					else {
						String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
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
								}
							} catch (NullPointerException np) {
								System.out.println("---->>Expected Errors is empty.");
							}
						}
						throw new Exception(ex);
					}
					pass++;
					cutoff_check[1][id]=1;
					if(neg_test_flag)
						testCoff.log(Status.FAIL, "003:Voluntary Saving");
					else
						testCoff.log(Status.PASS, "003:Voluntary Saving");
					System.out.println("003:Voluntary Saving");
				} catch (Exception e) {					
					fail++;
					cutoff_check[1][id]=-1;
					if(!neg_test_flag)
						testCoff.log(Status.FAIL, "003:Voluntary Saving");
					else
						testCoff.log(Status.PASS, "003:Voluntary Saving");
					System.out.println("Error in Voluntary Saving:003----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
				}
				if (id != 000)
					break;
			default:
				break;
			}
			
			
			// Member's Closed Loan
			switch (id) {
			case 0:
			case 4:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_member_closed_loan").click();
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());
					
					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[1]")
							.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.noLoansColNum) + "");
					try {
						if (!appdriver.findElementByXPath(
								"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[1]")
								.getText().equals(xc.getCellString(row, cutoffCons.noLoansColNum))) {
							if(neg_test_flag) {
								++neg_test_count;
							}
							System.out.println("004:||Validation Failed||");
							testCoff.log(Status.INFO, "004:||Validation||");
							throw new Exception("004:||Validation Failed||");
						}
					} catch (Exception e) {						
						if(neg_test_flag) {
							++neg_test_count;
						}
						System.out.println("004:||Validation Failed||");
						testCoff.log(Status.INFO, "004:||Validation Failed||");
						throw new Exception("004:||Validation Failed||");
					}

					
					for (int j = 2; j <= memNum; j++) {
						mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + j
								+ "]/android.widget.LinearLayout/android.widget.EditText[1]", "xpath", "top");

						appdriver
								.findElementByXPath("//android.widget.LinearLayout[" + j
										+ "]/android.widget.LinearLayout/android.widget.EditText[1]")
								.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.noLoansColNum) + "");
					}

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();

					if (!appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
							.equals("Data Updated Successfully")
							|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
									.equals("Data saved successfully"))
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					else {
						String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
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
								}
							} catch (NullPointerException np) {
								System.out.println("---->>Expected Errors is empty.");
							}
						}
						throw new Exception(ex);
					}
					pass++;
					cutoff_check[1][id]=1;
					if(neg_test_flag)
						testCoff.log(Status.FAIL, "004:Number of Loans");
					else
						testCoff.log(Status.PASS, "004:Number of Loans");
					System.out.println("004:Number of Loans");
				} catch (Exception e) {					
					fail++;
					cutoff_check[1][id]=-1;
					if(!neg_test_flag)
						testCoff.log(Status.FAIL, "004:Number of Loans");
					else
						testCoff.log(Status.PASS, "004:Number of Loans");
					System.out.println("Error in Number of Loans:004----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
				}
				if (id != 000)
					break;
			case 5:
				try {
					appdriver.findElementById("com.microware.cdfi:id/tbl_member_closed_loan").click();
					int memNum = Integer.valueOf(appdriver.findElementById("com.microware.cdfi:id/tv_count").getText());
					
					appdriver.findElementByXPath(
							"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[2]")
							.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.amtLoansColNum) + "");
					try {
						if (!appdriver.findElementByXPath(
								"//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.EditText[2]")
								.getText().equals(xc.getCellString(row, cutoffCons.amtLoansColNum))) {
							if(neg_test_flag) {
								++neg_test_count;
							}
							System.out.println("005:||Validation Failed||");
							testCoff.log(Status.INFO, "005:||Validation||");
							throw new Exception("005:||Validation Failed||");
						}
					} catch (Exception e) {						
						if(neg_test_flag) {
							++neg_test_count;
						}
						System.out.println("005:||Validation Failed||");
						testCoff.log(Status.INFO, "005:||Validation Failed||");
						throw new Exception("005:||Validation Failed||");
					}

					
					for (int j = 2; j <= memNum; j++) {
						mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + j
								+ "]/android.widget.LinearLayout/android.widget.EditText[2]", "xpath", "top");

						appdriver
								.findElementByXPath("//android.widget.LinearLayout[" + j
										+ "]/android.widget.LinearLayout/android.widget.EditText[2]")
								.sendKeys((int) xc.getCellDoubleValue(row, cutoffCons.amtLoansColNum) + "");
					}

					appdriver.findElementById("com.microware.cdfi:id/btn_save").click();

					if (!appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
							.equals("Data Updated Successfully")
							|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
									.equals("Data saved successfully"))
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					else {
						String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
						appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
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
								}
							} catch (NullPointerException np) {
								System.out.println("---->>Expected Errors is empty.");
							}
						}
						throw new Exception(ex);
					}
					pass++;
					cutoff_check[1][id]=1;
					if(neg_test_flag)
						testCoff.log(Status.FAIL, "005:Amount of Loans");
					else
						testCoff.log(Status.PASS, "005:Amount of Loans");
					System.out.println("005:Amount of Loans");
				} catch (Exception e) {					
					fail++;
					cutoff_check[1][id]=-1;
					if(!neg_test_flag)
						testCoff.log(Status.FAIL, "005:Amount of Loans");
					else
						testCoff.log(Status.PASS, "005:Amount of Loans");
					System.out.println("Error in Amount of Loans:005----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
				}
				if (id != 000)
					break;				
			default:
				break;
			}
			
			
			// Member's Share Capital
			switch (id) {
			case 0:
			case 1:
				try {
					appdriver.findElementById("").sendKeys(xc.getCellString(row, 1));
					p = 1;
					pass++;
					testCoff.log(Status.PASS, "");
					System.out.println("");
				} catch (Exception e) {
					f = 1;
					fail++;
					testCoff.log(Status.FAIL, "");
					System.out.println("Error in :----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					p = 0;
					f = 0;
				}
			default:
				break;
			}
			// Membership fee
			switch (id) {
			case 0:
			case 1:
				try {
					appdriver.findElementById("").sendKeys(xc.getCellString(row, 1));
					p = 1;
					pass++;
					testCoff.log(Status.PASS, "");
					System.out.println("");
				} catch (Exception e) {
					f = 1;
					fail++;
					testCoff.log(Status.FAIL, "");
					System.out.println("Error in :----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					p = 0;
					f = 0;
				}
			default:
				break;
			}
			// Group Investment
			switch (id) {
			case 0:
			case 1:
				try {
					appdriver.findElementById("").sendKeys(xc.getCellString(row, 1));
					p = 1;
					pass++;
					testCoff.log(Status.PASS, "");
					System.out.println("");
				} catch (Exception e) {
					f = 1;
					fail++;
					testCoff.log(Status.FAIL, "");
					System.out.println("Error in :----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					p = 0;
					f = 0;
				}
			default:
				break;
			}
			// Group's Closed Loan
			switch (id) {
			case 0:
			case 1:
				try {
					appdriver.findElementById("").sendKeys(xc.getCellString(row, 1));
					p = 1;
					pass++;
					testCoff.log(Status.PASS, "");
					System.out.println("");
				} catch (Exception e) {
					f = 1;
					fail++;
					testCoff.log(Status.FAIL, "");
					System.out.println("Error in :----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					p = 0;
					f = 0;
				}
			default:
				break;
			}
			// Group's Active Loan
			switch (id) {
			case 0:
			case 1:
				try {
					appdriver.findElementById("").sendKeys(xc.getCellString(row, 1));
					p = 1;
					pass++;
					testCoff.log(Status.PASS, "");
					System.out.println("");
				} catch (Exception e) {
					f = 1;
					fail++;
					testCoff.log(Status.FAIL, "");
					System.out.println("Error in :----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					p = 0;
					f = 0;
				}
			default:
				break;
			}
			// Fund Received
			switch (id) {
			case 0:
			case 1:
				try {
					appdriver.findElementById("").sendKeys(xc.getCellString(row, 1));
					p = 1;
					pass++;
					testCoff.log(Status.PASS, "");
					System.out.println("");
				} catch (Exception e) {
					f = 1;
					fail++;
					testCoff.log(Status.FAIL, "");
					System.out.println("Error in :----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					p = 0;
					f = 0;
				}
			default:
				break;
			}
			// Group Cash Balance
			switch (id) {
			case 0:
			case 1:
				try {
					appdriver.findElementById("").sendKeys(xc.getCellString(row, 1));
					p = 1;
					pass++;
					testCoff.log(Status.PASS, "");
					System.out.println("");
				} catch (Exception e) {
					f = 1;
					fail++;
					testCoff.log(Status.FAIL, "");
					System.out.println("Error in :----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					p = 0;
					f = 0;
				}
			default:
				break;
			}
			// Group Bank Balance
			switch (id) {
			case 0:
			case 1:
				try {
					appdriver.findElementById("").sendKeys(xc.getCellString(row, 1));
					p = 1;
					pass++;
					testCoff.log(Status.PASS, "");
					System.out.println("");
				} catch (Exception e) {
					f = 1;
					fail++;
					testCoff.log(Status.FAIL, "");
					System.out.println("Error in :----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					p = 0;
					f = 0;
				}
			default:
				break;
			}
		}

		if (neg_test_flag)
			pass = neg_test_count;

		int[] val = { pass, fail, count };
		return val;
	}

}
