package app;

import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import lokos.lokosTest;
import reporting.ExtentManager;
import util.cameraLogic;
import util.dateLogic;
import util.randomPressLogic;
import util.summary;

public class shgProfileCreation extends lokosTest {

	public static boolean neg_test_flag = false;
	public static int neg_test_count = 0;
	public static boolean invalid_flag = false;
	public static int pass = 0;
	public static int fail = 0;

	public static int[] idSelect_SHG(int row) throws Exception {

		testSHG.log(Status.INFO, "Inside shg profile Creation");
		System.out.println("Inside shg profile creation");
		Thread.sleep(1000);

		String[] idList = { "000" };

		String type = xc.getCellString(row, profileCons.typeColNum);

		if (type.equalsIgnoreCase("Update SHG")) {
			String[] typeList = xc.getCellString(row, profileCons.flowTypeColNum).split(" ");
			idList = typeList[0].split(",");
		}
		int k = 0;
		if (Integer.valueOf(idList[0]) != 0) {
			for (@SuppressWarnings("unused") String s : idList) {
				shg_check[0][Integer.valueOf(idList[k])] = Integer.valueOf(idList[k]);
				++k;
			}
		} else {
			for (int j = 0; j < shg_check[0].length; j++) {
				shg_check[0][j] = j;
			}
		}

		int[] val = null;
		if ((int) xc.getCellDoubleValue(row, profileCons.numMemAddColNum) == 0)
			val = shg(row, idList, false);
		else
			val = shg(row, idList, true);
		return val;

	}

	@SuppressWarnings("unused")
	public static int[] shg(int row, String[] idList, boolean addMem) throws Exception {

		xc.changeSheet("SHGs");

		int count = 0;
		int pass = 0;
		int fail = 0;
		int p = 0;
		int f = 0;
		int id = 0;
		int t = 0;// flag for back button
		int sig = 0;// count for signatory
		boolean neg_test_flag = false;
		neg_test_count = 0;
		invalid_flag = false;
		pass=0;
		fail=0;		
		
		try {
			if (xc.getCellString(row, profileCons.flowTypeColNum).contains("Check")) {
				neg_test_flag = true;
				testSHG.log(Status.INFO, "NEGETIVE TESTING");
			}
		} catch (NullPointerException np) {
		}

		for (int iterations = 0; iterations < idList.length; iterations++) {
			id = Integer.valueOf(idList[iterations]);
			if (id <= 17 || id == 0) {

				switch (id) {

				case 000:

				case 1:
					try {
//						 enterString_Id( 
//								 "",//title 
//								 "top",//scroll direction 
//								 "", //location 
//								 row, //row
//								 profileCons.shgNameColNum,//column number 
//								 ":||Validation Error||");//Error Message
						enterString_Id("SHG Name", // title
								"top", // scroll direction
								"com.microware.cdfi:id/et_groupname", // location
								row, // row
								profileCons.shgNameColNum, // column number
								"001:||Validation Error||");// Error Message
						pseq(id, "001:SHG Name");
					} catch (Exception e) {
						fseq(id, "001:SHG Name", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;
				case 2:
					try {
						selectById("Grampanchayat", // title
								"top", // scroll direction
								"com.microware.cdfi:id/spin_panchayat", // location
								row, // row
								profileCons.gpColNum);// column number
						pseq(id, "002:GP");
					} catch (Exception e) {
						fseq(id, "002:GP", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;

				case 3:
					try {
						selectById("Village", "top", "com.microware.cdfi:id/spin_village", row, profileCons.villageColNum);
						pseq(id, "003:Village");
					} catch (Exception e) {
						fseq(id, "003:Village", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;

				case 4:
					try {
						mt.scrollToText("Formation Date", "top");
						String date = xc.getCellString(row, profileCons.dateColNum);
						dateLogic.datePicker(date, "com.microware.cdfi:id/et_formationDate");
						pseq(id, "004:Formation Date");
					} catch (Exception e) {
						fseq(id, "004:Formation Date", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;
				case 5:
					try {
						mt.scrollToText("Promoted by", "top");

						String promotedBy = xc.getCellString(row, profileCons.promotedByColNum);
						selectById("Promoted by", "top", "com.microware.cdfi:id/spin_promotedby", row,
								profileCons.promotedByColNum);

						if (promotedBy.equals("NRLM")) {
							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
									.click();
							if (ncr.equals("Revived")) {
								mt.scrollToText("Revival Date", "top");
								dateLogic.datePicker(xc.getCellString(row, profileCons.revDateColNum),
										"com.microware.cdfi:id/et_revivalDate");
							}

						} else if (promotedBy.equals("State Project")) {
							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
									.click();
							if (ncr.equals("Revived")) {
								mt.scrollToText("Revival Date", "top");
								dateLogic.datePicker(xc.getCellString(row, profileCons.revDateColNum),
										"com.microware.cdfi:id/et_revivalDate");
							} else if (ncr.equals("Co-opted")) {
								mt.scrollToText("Co-option Date", "top");
								dateLogic.datePicker(xc.getCellString(row, profileCons.cooptDateColNum),
										"com.microware.cdfi:id/et_coopt_Date");
							}

						} else if (promotedBy.equals("NGO")) {
							appdriver.findElementById("com.microware.cdfi:id/spin_FundingAgency").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
									+ xc.getCellString(row, profileCons.promoterColNum) + "\")")).click();
							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
									.click();
							if (ncr.equals("Revived")) {
								mt.scrollToText("Revival Date", "top");
								dateLogic.datePicker(xc.getCellString(row, profileCons.revDateColNum),
										"com.microware.cdfi:id/et_revivalDate");
							} else if (ncr.equals("Co-opted")) {
								mt.scrollToText("Co-option Date", "top");
								dateLogic.datePicker(xc.getCellString(row, profileCons.cooptDateColNum),
										"com.microware.cdfi:id/et_coopt_Date");
							}

						} else if (promotedBy.equals("SGSY")) {
							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
									.click();
							if (ncr.equals("Revived")) {
								mt.scrollToText("Revival Date", "top");
								dateLogic.datePicker(xc.getCellString(row, profileCons.revDateColNum),
										"com.microware.cdfi:id/et_revivalDate");
							} else if (ncr.equals("Co-opted")) {
								mt.scrollToText("Co-option Date", "top");
								dateLogic.datePicker(xc.getCellString(row, profileCons.cooptDateColNum),
										"com.microware.cdfi:id/et_coopt_Date");
							}

						} else if (promotedBy.equals("Other")) {
							appdriver.findElementById("com.microware.cdfi:id/et_promoter_name")
									.sendKeys(xc.getCellString(row, profileCons.promoterColNum));

							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
									.click();
							if (ncr.equals("Revived")) {
								mt.scrollToText("Revival Date", "top");
								dateLogic.datePicker(xc.getCellString(row, profileCons.revDateColNum),
										"com.microware.cdfi:id/et_revivalDate");
							} else if (ncr.equals("Co-opted")) {
								mt.scrollToText("Co-option Date", "top");
								dateLogic.datePicker(xc.getCellString(row, profileCons.cooptDateColNum),
										"com.microware.cdfi:id/et_coopt_Date");
							}
						}

						pseq(id, "005:Promoted By and Sub Parts");
					} catch (Exception e) {
						fseq(id, "005:Promoted By and Sub Parts", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;
				case 6:
					try {
						mt.scrollToText("SHG Type", "top");
						String s = xc.getCellString(row, profileCons.Women_Special);
						if (s.equals("Women(Regular)"))
							appdriver
									.findElement(
											MobileBy.AndroidUIAutomator("new UiSelector().text(\"Women(Regular)\")"))
									.click();
						else {
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Special\")"))
									.click();
							appdriver.findElementById("com.microware.cdfi:id/spin_tags").click();
							appdriver
									.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
											+ xc.getCellString(row, profileCons.tagsColNum).toUpperCase() + "\")"))
									.click();
							if (xc.getCellString(row, profileCons.tagsColNum).equals("OTHER")) {
								enterString_Id("Other(specify)", "top", "com.microware.cdfi:id/et_OtherTags", row,
										profileCons.tagsOtherColNum, "206:||Validation Error||");
							}
						}

						pseq(id, "006:SHG Type");
					} catch (Exception e) {
						fseq(id, "006:SHG Type", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;
				case 7:
					try {
						mt.scrollToText("Meeting Frequency", "top");
						String mtg = xc.getCellString(row, profileCons.meetingFreqColNum);
						appdriver.findElementById("com.microware.cdfi:id/spin_frequency").click();
						appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + mtg + "\")"))
								.click();
						if (mtg.equals("Weekly")) {
							mt.scrollToText("Day", "top");
							appdriver.findElementById("com.microware.cdfi:id/spin_weekday").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, profileCons.dayWColNum) + "\")"))
									.click();

						} else if (mtg.equals("Fortnightly")) {
							mt.scrollToText("Fortnight Week", "top");
							appdriver.findElementById("com.microware.cdfi:id/spin_fortnight").click();
							String s = xc.getCellString(row, profileCons.frtn8WeekFColNum);
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + s + "\")"))
									.click();
							if (s.equals("Date")) {
								mt.scrollToText("Date", "top");
								appdriver.findElementById("com.microware.cdfi:id/spin_fortnightdate").click();
								appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
										+ (int) xc.getCellDoubleValue(row, profileCons.dateOtherFColNum) + "\")"))
										.click();
							} else {
								mt.scrollToText("Day", "top");
								appdriver.findElementById("com.microware.cdfi:id/spin_weekday").click();
								appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
										+ xc.getCellString(row, profileCons.day1_2FColNum) + "\")")).click();
							}

						} else if (mtg.equals("Monthly")) {
							mt.scrollToText("Monthly Day", "top");
							String s = xc.getCellString(row, profileCons.monthlydayMColNum);
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + s + "\")"))
									.click();
							if (s.equals("Date")) {
								mt.scrollToText("Date", "top");
								appdriver.findElementById("com.microware.cdfi:id/spin_monthlydate").click();
								appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
										+ xc.getCellString(row, profileCons.dateMColNum) + "\")")).click();
							} else {
								mt.scrollToText("Day", "top");
								appdriver.findElementById("com.microware.cdfi:id/spin_monthlydate").click();
								appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
										+ xc.getCellString(row, profileCons.dayMColNum) + "\")")).click();
							}

						}
						pseq(id, "007:Meeting Frequency and Sub Parts");
					} catch (Exception e) {
						fseq(id, "007:Meeting Frequency and Sub Parts", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;
				case 8:
					try {
						selectById("Compulsory Saving Frequency", "top", "com.microware.cdfi:id/spin_savingfrequency", row,
								profileCons.compSavFreqColNum);
						pseq(id, "008:Compulsory Saving Frequency");
					} catch (Exception e) {
						fseq(id, "008:Compulsory Saving Frequency", e);
					} finally {
						count++;
					}
				case 9:
					try {
						enterValue_Id("Compulsory Saving Amount", "top", "com.microware.cdfi:id/et_saving", row,
								profileCons.compSavAmtColNum, "009:||Validation Error||");
						pseq(id, "009:Compulsory Saving Amount");
					} catch (Exception e) {
						fseq(id, "009:Compulsory Saving Amount", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;
				case 10:
					try {
						enterDouble_Id("Compulsory Saving Interest Rate (Annual) %", "top",
								"com.microware.cdfi:id/et_ComsavingRoi", row, profileCons.compSavIRColNum,
								"010:||Validation Erro||");
						pseq(id, "010:Compulsory Saving Interest Rate");
					} catch (Exception e) {
						fseq(id, "010:Compulsory Saving Interest Rate", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;

				case 11:
					try {
						mt.scrollToText("Voluntary Saving", "top");
						appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
								+ xc.getCellString(row, profileCons.voluntarySavColNum) + "\")")).click();
						if (xc.getCellString(row, profileCons.voluntarySavColNum).equals("Yes")) {
							enterDouble_Id("Voluntary Saving interest rate (Annual) %", "top",
									"com.microware.cdfi:id/et_voluntarysavingROI", row,
									profileCons.voluntarySavIRColNum,"010:||Validation Error");
						}
						pseq(id, "011:Voluntary Savings and Sub-parts");
					} catch (Exception e) {
						fseq(id, "011:Voluntary Savings and Sub-parts", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;
				case 12:
					try {
						Thread.sleep(2000);
						mt.scrollToText("Yes-Internal", "top");
						Thread.sleep(2000);
						appdriver.findElement(MobileBy.AndroidUIAutomator(
								"new UiSelector().text(\"" + xc.getCellString(row, profileCons.bkIdColNum) + "\")"))
								.click();

						if (xc.getCellString(row, profileCons.bkIdColNum).equals("Yes-Internal")) {
							if ((xc.getCellString(row, profileCons.flowTypeColNum).equals("New")) && (!addMem)) {
								appdriver.findElement(MobileBy.AndroidUIAutomator(
										"new UiSelector().text(\"No Member available with the phone no in this SHG\")"))
										.click();
								appdriver.findElementByXPath(
										"//android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[2]")
										.click();
							} else
								selectById("Bookkeeper Name", "top", "com.microware.cdfi:id/lay_spinmember", row,
										profileCons.bkNameColNum);
							enterLongNum_Id("Bookkeeper's Mobile No.", "top",
									"com.microware.cdfi:id/et_bookkeeper_s_mobile_no", row, profileCons.bkMobColNum,
									"212:||Validation Error||","#");
						} else if (xc.getCellString(row, profileCons.bkIdColNum).equals("Yes-External")) {
							enterString_Id("Bookkeeper Name", "top", "com.microware.cdfi:id/et_bookkeeper_name", row,
									profileCons.bkNameColNum, "112:||Validation Error");
							enterLongNum_Id("Bookkeeper's Mobile No.", "top",
									"com.microware.cdfi:id/et_bookkeeper_s_mobile_no", row, profileCons.bkMobColNum,
									"212:||Validation Error","#");
						}
						
						pseq(id, "012:Bookkeeper");
					} catch (Exception e) {
						fseq(id, "012:Bookkeeper", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;
				case 13:
					try {
						selectById("Primary Livelihood Activity", "top", "com.microware.cdfi:id/spin_primaryActivity", row,
								profileCons.primaryla);
						pseq(id, "013:Primary Livelihood");
					} catch (Exception e) {
						fseq(id, "013:Primary Livelihood", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;
				case 14:
					try {
						selectById("Secondary Livelihood Activity", "top", "com.microware.cdfi:id/spin_secondaryActivity",
								row, profileCons.secondaryla);
						pseq(id, "014:Secondary Livelihood");
					} catch (Exception e) {
						fseq(id, "014:Secondary Livelihood", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;
				case 15:
					try {
						selectById("Tertiary Livelihood Activity", "top", "com.microware.cdfi:id/spin_tertiaryActivity",
								row, profileCons.tertiaryla);
						pseq(id, "015:Tertiary Livelihood");
					} catch (Exception e) {
						fseq(id, "015:Tertiary Livelihood", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;

				case 16:
					try {
						enterValue_Id("Tenure of elected Office Bearers", "top",
								"com.microware.cdfi:id/et_electiontenure", row, profileCons.tenureColNum,
								"016:||Validation Error");
						pseq(id, "016:Tenure of elected Office Bearers");
					} catch (Exception e) {
						fseq(id, "016:Tenure of elected Office Bearers", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;

				case 17:
					try {
						mt.scrollToText("SHG resolution copy", "top");
						appdriver.findElementById("com.microware.cdfi:id/Imgmember").click();
						appdriver.findElementById("com.microware.cdfi:id/layout_camera").click();
						util.cameraLogic.click();
						pseq(id, "017: SHG Resolution Copy Upload");
					} catch (Exception e) {
						fseq(id, "017: SHG Resolution Copy Upload", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;

				case 18:
					try {
						if (!xc.getCellString(row, profileCons.typeColNum).equals("New SHG")) {
							if (!xc.getCellString(row, memCons.typeColNum).equals("New")) {
								mt.scrollToText("Status", "top");
								appdriver.findElementById("com.microware.cdfi:id/spin_status").click();
								appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
										+ xc.getCellString(row, memCons.statusColNum) + "\")")).click();
							}
							pseq(id, "018:Status");
						} else
							--count;

					} catch (Exception e) {
						fseq(id, "018:Status", e);
					} finally {
						count++;
					}

					if (id != 000)
						break;
				default:
					break;

				}
			}
			
			if (invalid_flag) {
				System.out.println("Error---->Atleast one field is incorrect");
				System.out.println("Error---->Data not saved: Cannot Proceed(Correct the Errors first)");
				testSHG.log(Status.INFO,"Error---->Atleast one field is incorrect");
				testSHG.log(Status.INFO,"Error---->Data not saved: Cannot Proceed(Correct the Errors first)");
				appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
				appdriver.findElementById("com.microware.cdfi:id/btn_yes").click();
				navigateBackToScreen("SHG");
				t = 1;
				id = 9999;
			}

			if (neg_test_flag) {

				appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
				if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
						.equals("Data saved successfully")
						|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
								.equals("Data Updated Successfully")||!invalid_flag) {
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					testSHG.log(Status.INFO, "Necessary fields for saving SHG filled.");
					System.out.println("Necessary fields for saving SHG filled.");
				} else {
					String err = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
					testSHG.log(Status.FAIL, "Error---->" + err);
					System.out.println("Error---->" + err);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					testSHG.log(Status.INFO, "Data not saved: Cannot Proceed(Resume next text case)");
					System.out.println("Error---->Data not saved: Cannot Proceed(Resume next text case)");
					try {
						String exp_errs = xc.getCellString(row, profileCons.expErrMessColNum);
						if (err.contains(exp_errs)) {
							System.out.println("|||||||||||||||||||||||||||||");
							System.out.println("Expected Error is encountered");
							System.out.println("  ((Negetive Test Passed))");
							System.out.println("|||||||||||||||||||||||||||||");
							++neg_test_count;
						} else {
							System.out.println("   (((Negetive Test Failed)))\n");
						}
					} catch (NullPointerException np) {
						System.out.println("---->>Expected Errors is empty.");
					}
					util.randomPressLogic.press(0.5, 0.05);
					navigateBackToScreen("SHG Basic Details");
					mt.scrollToVisibleElementOnScreen("com.microware.cdfi:id/tv_shgName", "id", "bottom");
					t = 1;
					id = 9999;
				}
				appdriver.findElementById("com.microware.cdfi:id/IvVector").click();
			}

		}
		if (!neg_test_flag) {
			appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
			if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText().equals("Data saved successfully")
					|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
							.equals("Data Updated Successfully")) {
				appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
				testSHG.log(Status.PASS, "Necessary fields for saving SHG filled.");
				System.out.println("Necessary fields for saving SHG filled.");
			} else {
				String err = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
				testSHG.log(Status.FAIL, "Error---->" + err);
				System.out.println("Error---->" + err);
				appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
				testSHG.log(Status.INFO, "Atleast one field is incorrect");
				System.out.println("Error---->Atleast one field is incorrect");
				testSHG.log(Status.INFO, "Data not saved: Cannot Proceed(Correct the Errors first)");
				System.out.println("Error---->Data not saved: Cannot Proceed(Correct the Errors first)");
				navigateBackToScreen("SHG Basic Details");
				appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
				appdriver.findElementById("com.microware.cdfi:id/btn_yes").click();
				fail++;
				t = 1;
				id = 9999;
			}
		}

		if ((id != 9999) && (!addMem)) {
			for (int iterations = 0; iterations < idList.length; iterations++) {
				id = Integer.valueOf(idList[iterations]);
				if (id > 18 || id == 0) {
					switch (id) {
					case 000:
					case 19:
						try {
							int k=0;
							while(k<6) {
								try {
									Thread.sleep(2000);
									appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
									appdriver.findElementById("com.microware.cdfi:id/lay_phone").click();
									k=6;
								}catch(Exception e) {
									System.out.println("Wasn't able to press the phone button");
									k++;
								}
							}		
							Thread.sleep(2000);
							appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
							appdriver.findElementById("com.microware.cdfi:id/addAddress").click();

							String[] phnos = xc.getCellString(row, profileCons.mobNosColNUm).split(";");
							for (int i = 0; i < phnos.length; i++) {

								String[] s = phnos[i].split(":");
								appdriver.findElementById("com.microware.cdfi:id/spin_belongMember").click();

								appdriver.findElement(MobileBy
										.AndroidUIAutomator("new UiSelector().textContains(\"" + s[0].trim() + "\")"))
										.click();
								appdriver.findElementById("com.microware.cdfi:id/spin_Memberphone").click();
								appdriver
										.findElement(MobileBy
												.AndroidUIAutomator("new UiSelector().text(\"" + s[1].trim() + "\")"))
										.click();

								appdriver.findElementById("com.microware.cdfi:id/btn_add").click();
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
											String exp_errs = xc.getCellString(row, profileCons.expErrMessColNum);
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
								pseq(id, "019:Phone Number");
							}
						} catch (Exception e) {
							fseq(id, "019:Phone Number", e);
							randomPressLogic.press(0.5, 0.05);
						} finally {
							count++;
						}
						if (id != 000)
							break;

					case 20:
						try {
							
							int i=0;
							while(i<6) {
								try {
									Thread.sleep(2000);
									appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
									appdriver.findElementById("com.microware.cdfi:id/lay_location").click();
									i=6;;
								}catch(Exception e) {
									System.out.println("Wasn't able to press the address button");
									i++;
								}
							}							
							Thread.sleep(2000);
							appdriver.findElementById("com.microware.cdfi:id/addAddress").click();
							Thread.sleep(2000);
							appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
							enterString_Id(
									"Address Line 1",
									"top",
									"com.microware.cdfi:id/et_address1",
									row, 
									profileCons.add1ColNum,
									"020:||Validation Error||");
							enterString_Id(
									"Address Line 2",
									"top",
									"com.microware.cdfi:id/et_address2",
									row, 
									profileCons.add2ColNum,
									"120:||Validation Error||");
							enterValue_Id(
									"Pincode",
									"top",
									"com.microware.cdfi:id/et_pincode",
									row, 
									profileCons.pinColNum,
									"220:||Validation Error||");
							appdriver.findElementById("com.microware.cdfi:id/btn_add").click();
							if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
									.equals("Data Updated Successfully")
									|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
											.equals("Data saved successfully"))
								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
							else {
								String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
								testSHG.log(Status.FAIL, ex);
								System.out.println("Error->" + ex);
								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
								if (neg_test_flag) {
									try {
										String exp_errs = xc.getCellString(row, profileCons.expErrMessColNum);
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
							pseq(id, "020:Address");

						} catch (Exception e) {
							fseq(id, "020:Address", e);
							randomPressLogic.press(0.5, 0.05);
							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
						} finally {
							count++;
						}
						if (id != 000)
							break;
					case 21:					
						try {
							invalid_flag=false;
							int i=0;
							while(i<6) {
								try {
									Thread.sleep(2000);
									appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
									appdriver.findElementById("com.microware.cdfi:id/IvBank").click();
									i=6;
								}catch(Exception e) {
									System.out.println("Wasn't able to press the bank button");
									i++;
								}
							}
							
							int k=0;
							while(k<6) {
								try {
									Thread.sleep(2000);
									appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
									appdriver.findElementById("com.microware.cdfi:id/addBank").click();
									k=6;
								}catch(Exception e) {
									System.out.println("Wasn't able to press the address button");
									k++;
								}
							}
							enterString_Id("Name in Bank Passbook",
									"top",
									"com.microware.cdfi:id/et_nameinbankpassbook",
									row, 
									profileCons.passNameColNum,
									"021:||Validation Error||");
							enterString_Id("IFSC Code",
									"top",
									"com.microware.cdfi:id/et_ifsc",
									row, 
									profileCons.IFSCColNum,
									"121:||Validation Error||");
							Thread.sleep(1000);
							appdriver.findElementById("com.microware.cdfi:id/Imgsearch").click();
							Thread.sleep(1000);
							try {
								Thread.sleep(2000);
								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
								Thread.sleep(2000);
								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
							} catch (Exception e) {

							}
							try {
								appdriver.findElementById("com.microware.cdfi:id/lay_bankName").click();
								appdriver.findElementById("android:id/search_src_text")
										.sendKeys(xc.getCellString(row, profileCons.bankNameColNum));
								appdriver.findElementById("com.microware.cdfi:id/tvmyspinner").click();
//								mt.scrollToText("Bank Branch", "top");
//								appdriver.findElementById("com.microware.cdfi:id/lay_branch").click();
//								appdriver.findElementById("android:id/search_src_text")
//									.sendKeys(xc.getCellString(row, memCons.bankBranchColNum));
//								appdriver.findElementByXPath("//android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[2]").click();
							} catch (Exception e) {

							}
							
							appdriver.findElementById("com.microware.cdfi:id/et_Accountno").sendKeys(xc.getCellString(row, profileCons.accNoColNum).substring(1));
//							enterLongNum_Id("Account number", 
//									"top",
//									"com.microware.cdfi:id/et_Accountno",
//									row, 
//									profileCons.accNoColNum,
//									"421:||Validation Error||",
//									"#");
							enterLongNum_Id("Re-Type Account No.", 
									"top",
									"com.microware.cdfi:id/et_retype_Accountno",
									row, 
									profileCons.retypAccNoColNum,
									"521:||Validation Error||",
									"#");
							String date = xc.getCellString(row, profileCons.accOpDateColNum);
							dateLogic.datePicker(date, "com.microware.cdfi:id/et_opdate");
							appdriver.findElementById("com.microware.cdfi:id/ImgFrntpage").click();
							cameraLogic.click();
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
									+ xc.getCellString(row, profileCons.isDefaultColNum) + "\")")).click();
							if(invalid_flag)
								throw new Exception("");
							appdriver.findElementById("com.microware.cdfi:id/btn_add").click();
							if (du.isElementPresent("new UiSelector().text(\"Please enter valid Account No.\")",
									"androidUIAutomatior")) {
								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
								throw new Exception("Invalid Account Number");
							}

							try {
								if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
										.equals("Data Updated Successfully")
										|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
												.equals("Data saved successfully")) {
									Thread.sleep(1000);
									appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();

								} else {
									String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
									testSHG.log(Status.FAIL, ex);
									System.out.println("Error->" + ex);
									appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
									if (neg_test_flag) {
										try {
											String exp_errs = xc.getCellString(row, profileCons.expErrMessColNum);
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
							} catch (Exception e) {
								System.out.println("::Additional Dialogue Box displayed");
							}

							try {
								Thread.sleep(1000);
								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();

							} catch (Exception e) {

							}
							try {
								appdriver.findElementById("com.microware.cdfi:id/btn_add").click();
							} catch (Exception e) {
							}
							try {
								Thread.sleep(1000);
								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();

							} catch (Exception e) {

							}

							pseq(id, "021:Bank Account");
						} catch (Exception e) {
							fseq(id, "021:Bank Account", e);
							randomPressLogic.press(0.5, 0.05);
							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
						} finally {
							count++;
						}
						if (id != 000)
							break;
					case 22:
						try {
							appdriver.findElementById("com.microware.cdfi:id/IvKyc").click();
							// President
							appdriver.findElementByXPath(
									"//android.widget.FrameLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
									.click();
							appdriver.findElementById("com.microware.cdfi:id/spin_Member").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, profileCons.presColNum) + "\")"))
									.click();
//							appdriver.findElementById("com.microware.cdfi:id/et_fromDate")
//									.sendKeys(xc.getCellString(row, profileCons.presDateColNum));
							appdriver.findElementById("com.microware.cdfi:id/et_fromDate").click();
							appdriver.findElementById("android:id/button1").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
									+ xc.getCellString(row, profileCons.presSigColNum) + "\")")).click();
							appdriver.findElementById("com.microware.cdfi:id/btn_addDes").click();
							sig++;
							try {
								if (du.isElementPresent("com.microware.cdfi:id/txt_msg", "id")) {
									String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
									testSHG.log(Status.FAIL, ex);
									System.out.println("Error-->" + ex);
									appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
									if (neg_test_flag) {
										try {
											String exp_errs = xc.getCellString(row, profileCons.expErrMessColNum);
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
									randomPressLogic.press(0.5, 0.05);
									sig--;
									throw new Exception("President Signatory not assigned");
								}
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}

							pseq(id, "022:President Signatory");
						} catch (Exception e) {
							randomPressLogic.press(0.5, 0.05);
							fseq(id, "022:President Signatory", e);
						} finally {
							count++;
						}
						if (id != 000)
							break;
					case 23:
						try {

							appdriver.findElementById("com.microware.cdfi:id/IvKyc").click();
							// Secretary
							appdriver.findElementByXPath(
									"//android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
									.click();
							appdriver.findElementById("com.microware.cdfi:id/spin_Member").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, profileCons.secColNum) + "\")"))
									.click();
//							appdriver.findElementById("com.microware.cdfi:id/et_fromDate")
//									.sendKeys(xc.getCellString(row, profileCons.secDateColNum));
							appdriver.findElementById("com.microware.cdfi:id/et_fromDate").click();
							appdriver.findElementById("android:id/button1").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
									+ xc.getCellString(row, profileCons.secSigColNum) + "\")")).click();
							appdriver.findElementById("com.microware.cdfi:id/btn_addDes").click();
							sig++;
							try {
								if (du.isElementPresent("com.microware.cdfi:id/txt_msg", "id")) {
									String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
									testSHG.log(Status.FAIL, ex);
									System.out.println("Error-->" + ex);
									appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
									randomPressLogic.press(0.5, 0.05);
									if (neg_test_flag) {
										try {
											String exp_errs = xc.getCellString(row, profileCons.expErrMessColNum);
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
									sig--;
									throw new Exception("Secretary Signatory not assigned");
								}
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}

							pseq(id, "023:Secretary Signatory");
						} catch (Exception e) {
							randomPressLogic.press(0.5, 0.05);
							fseq(id, "023:Secretary Signatory", e);
						} finally {
							count++;
						}
						if (id != 000)
							break;
					case 24:
						try {
							appdriver.findElementById("com.microware.cdfi:id/IvKyc").click();
							// Treasurer
							appdriver.findElementByXPath(
									"//android.widget.FrameLayout[3]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
									.click();
							appdriver.findElementById("com.microware.cdfi:id/spin_Member").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, profileCons.tresColNum) + "\")"))
									.click();
//							appdriver.findElementById("com.microware.cdfi:id/et_fromDate")
//									.sendKeys(xc.getCellString(row, profileCons.tresDateColNum));
							appdriver.findElementById("com.microware.cdfi:id/et_fromDate").click();
							appdriver.findElementById("android:id/button1").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
									+ xc.getCellString(row, profileCons.tresSigColNum) + "\")")).click();
							appdriver.findElementById("com.microware.cdfi:id/btn_addDes").click();
							sig++;
							try {
								if (du.isElementPresent("com.microware.cdfi:id/txt_msg", "id")) {
									String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
									testSHG.log(Status.FAIL, ex);
									System.out.println("Error-->" + ex);
									appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
									randomPressLogic.press(0.5, 0.05);
									if (neg_test_flag) {
										try {
											String exp_errs = xc.getCellString(row, profileCons.expErrMessColNum);
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
									sig--;
									throw new Exception("Treasurer Signatory not assigned");
								}
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}

							pseq(id, "024:Treasurer Signatory");
						} catch (Exception e) {
							randomPressLogic.press(0.5, 0.05);
							fseq(id, "024:Treasurer Signatory", e);							
						} finally {
							count++;
						}
						if (id != 000)
							break;
					}

				}
			}
		}
		
		ExtentManager.addScreenShotsToTest("Save and Check", testSHG);
		
		if (t == 0)
			navigateBackToScreen("SHG");

//		appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//		appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
//
//		appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//		appdriver.findElementById("com.microware.cdfi:id/btn_yes").click();
		
		if ((id != 9999) && (addMem))

		{

			int numMem = (int) xc.getCellDoubleValue(row, profileCons.numMemAddColNum);

			for (int i = 1; i <= numMem; i++) {
				testSHG.log(Status.INFO, "\n#" + i);
				System.out.println("\n#" + i);
				xc.changeSheet("SHGs");
				navigation.existingSHG(row);
				navigation.openSHGMembers(row);
				navigation.newMember();

				xc.changeSheet("Members");
				++memberRow;
				String memName = xc.getCellString(memberRow, memCons.nameColNum);
				System.out.println("Member no: " + i + " -->" + memName);
				testMem = testSHG
						.createNode("Member: " + memName + "(" + xc.getCellString(memberRow, memCons.typeColNum) + ")");
				/////////////////////////////////////////////////
				int[] val = memberProfile.idSelect_Mem(memberRow);
				/////////////////////////////////////////////////
				int[][] zeroIni = new int[mem_check[0].length][mem_check[1].length];
				summary.display(mem_check, testMem);
				mem_check = zeroIni;
				testMem.log(Status.INFO, "Member " + memName + " Fails: " + val[1] + "/" + val[2]);
				testSHG.log(Status.INFO, "Member " + memName + " Fails: " + val[1] + "/" + val[2]);
				System.out.println("^^^^^^^^^^^^^^");
				System.out.println("Member " + i + " Fails: " + val[1] + "/" + val[2]);

				testSHG.log(Status.INFO, "#" + i);
				System.out.println("#" + i + "\n");
			}

			xc.changeSheet("SHGs");
			System.out.println(row);
			Thread.sleep(2000);
			navigation.existingSHG(row);
			Thread.sleep(2000);
			navigation.openSHGProfile(row);
			String[] s = { "012","017", "019", "020", "021", "022", "023","24"};
			int[] val2 = shgProfileCreation.shg(row, s, false);
			pass += val2[0];
			fail += val2[1];
			count += val2[2];

		}

		if (neg_test_flag)
			pass = neg_test_count;

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
	public static void selectByXpath(String title, String dir, String loc, int row, int cons) {
		mt.scrollToText(title, dir);
		appdriver.findElementByXPath(loc).click();
		appdriver
				.findElement(
						MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + xc.getCellString(row, cons) + "\")"))
				.click();
	}
	public static void enterLongNum_Id(String title, String dir, String loc, int row, int cons, String err,String prefix) {
		mt.scrollToText(title, dir);
		appdriver.findElementById(loc).sendKeys(xc.getCellString(row, cons).substring(1));
		int f = validCheckLongNum(loc, "id", xc.getCellString(row, cons), err,prefix);
		if (f == 1)
			invalid_flag = true;
	}

	public static int validCheckString(String loc, String locStrat, String field_txt, String text) {
		if (locStrat.equalsIgnoreCase("xpath")) {
			if (!appdriver.findElementByXPath(loc).getText().equals(field_txt)) {
				System.out.println(text);
				testSHG.log(Status.INFO, text);
				++neg_test_count;
				return 1;
			}
		} else if (locStrat.equalsIgnoreCase("id")) {
			if (!appdriver.findElementById(loc).getText().equals(field_txt)) {
				System.out.println(text);
				testSHG.log(Status.INFO, text);
				++neg_test_count;
				return 1;
			}
		} else if (locStrat.equalsIgnoreCase("UiSelectorText")) {
			if (!appdriver.findElement(MobileBy.AndroidUIAutomator(loc)).getText().equals(field_txt)) {
				System.out.println(text);
				testSHG.log(Status.INFO, text);
				++neg_test_count;
				return 1;
			}
		}
		return 0;
	}

	public static int validCheckLongNum(String loc, String locStrat, String field_txt, String text,String prefix) {
		if (locStrat.equalsIgnoreCase("xpath")) {
			if (!(prefix + appdriver.findElementByXPath(loc).getText()).equals(field_txt)) {
				System.out.println(text);
				testSHG.log(Status.INFO, text);
				++neg_test_count;
				return 1;
			}
		} else if (locStrat.equalsIgnoreCase("id")) {
			if (!(prefix + appdriver.findElementById(loc).getText()).equals(field_txt)) {
				System.out.println(text);
				testSHG.log(Status.INFO, text);
				++neg_test_count;
				return 1;
			}
		} else if (locStrat.equalsIgnoreCase("UiSelectorText")) {
			if (!(prefix + appdriver.findElement(MobileBy.AndroidUIAutomator(loc)).getText()).equals(field_txt)) {
				System.out.println(text);
				testSHG.log(Status.INFO, text);
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
			testSHG.log(Status.FAIL, "ex");
			ExtentManager.addScreenShotsToLogFail("SHG Meetings:ex", testMeet);
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
						testSHG.log(Status.INFO, "Negetive Test Failed");
					}
				} catch (NullPointerException np) {
					System.out.println("---->>Expected Errors is empty.");
					testSHG.log(Status.INFO, "Expected Errors is empty.");
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
				Thread.sleep(2000);
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
				if (appdriver.findElementById("com.microware.cdfi:id/tv_title").getText().equals("SHG"))
					break;
				i++;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Cannot navigate to " + screen_title + " screen");
			throw new Exception("The app might have crashed");
		}
	}

	public static void pseq(int id, String msg) {
		pass++;
		shg_check[1][id] = 1;
		if (neg_test_flag)
			testSHG.log(Status.FAIL, msg);
		else
			testSHG.log(Status.PASS, msg);
		System.out.println(msg);
	}

	public static void fseq(int id, String msg, Exception e) {
		fail++;
		shg_check[1][id] = -1;
		if (!neg_test_flag)
			testSHG.log(Status.FAIL, msg);
		else
			testSHG.log(Status.PASS, msg);
		System.out.println("Error in " + msg + "----------------------Check Here////");
		e.printStackTrace();
	}

}
