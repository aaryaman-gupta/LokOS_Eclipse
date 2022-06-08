package appVO;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.Status;

import functions.fnFed;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import lokos.lokosVO;
import reporting.ExtentManager;
import util.MobileTouchAdv;
import util.cameraLogic;
import util.randomPressLogic;

public class voProfile extends lokosVO {

	public static boolean neg_test_flag = false;
	public static int neg_test_count = 0;
	public static boolean invalid_flag = false;
	public static int pass = 0;
	public static int fail = 0;
	public static boolean migr = false;
	public static MobileTouchAdv mt = null;
	public static final double x = 0.35;
	public static final double y = 0.70;

	public static int[] idSelect_VO(int row) throws Exception {

		testVO.log(Status.INFO, "Inside VO profile Creation");
		System.out.println("Inside VO profile creation");
		Thread.sleep(1000);

		String[] idList = { "000" };

		String type = xc.getCellString(row, voCons.Type_0);

		if (type.equalsIgnoreCase("Update VO")) {
			String[] typeList = xc.getCellString(row, voCons.Flow_Type_0).split(" ");
			idList = typeList[0].split(",");
		}
		int k = 0;
		if (Integer.valueOf(idList[0]) != 0) {
			for (@SuppressWarnings("unused")
			String s : idList) {
				vo_check[0][Integer.valueOf(idList[k])] = Integer.valueOf(idList[k]);
				++k;
			}
		} else {
			for (int j = 0; j < vo_check[0].length; j++) {
				vo_check[0][j] = j;
			}
		}

		int[] val = null;
		
		val = vo(row, idList);
		
		return val;

	}

	@SuppressWarnings("unused")
	public static int[] vo(int row, String[] idList) throws Exception {

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
		pass = 0;
		fail = 0;

		mt = new MobileTouchAdv(appdriver);
		fnFed af = new fnFed(xc, appdriver, testVO, mt, du, vo_check);

		if (xc.getCellString(row, voCons.Flow_Type_0).contains("Migration"))
			migr = true;

		try {
			if (xc.getCellString(row, voCons.Flow_Type_0).contains("Check")) {
				neg_test_flag = true;
				testVO.log(Status.INFO, "NEGETIVE TESTING");
			} else if (xc.getCellString(row, voCons.Flow_Type_0).contains("Migration")) {
				migr = true;
				testVO.log(Status.INFO, "MIGRATION DATA TO BE CHECKED");
			}
		} catch (NullPointerException np) {
			System.out.println("Flow type cell not populated");
		}

		for (int iterations = 0; iterations < idList.length; iterations++) {
			id = Integer.valueOf(idList[iterations]);
			if (id <= 17 || id == 0) {

				switch (id) {

				case 000:

				case 1:
					af.pf(1, af.enterStringById( testVO , "VO Name" , "top" , "com.microware.cdfi:id/et_voname" , row , voCons.VO_Name_1 , "1" , "1" , false , "Type here..." , migr ));

					if (id != 000)
						break;
				case 2:
					af.pf(2, af.enterStringById( testVO , "VO Name in Local" , "top" , "com.microware.cdfi:id/et_vonamelocal" , row , voCons.VO_Name_in_Local_2 , "2" , "2" , false , "Type here..." , migr ));
					if (id != 000)
						break;

				case 3:
					af.pf(3, af.dateLogic( testVO , "Formation Date" , "top" , "com.microware.cdfi:id/et_formationDate" , row , voCons.Formation_Date_3 , "3" , "3" , false , "dd-mm-yyyy" , migr ));
					if (id != 000)
						break;
				case 4:
					try {
						String promotedBy = xc.getCellString(row, voCons.Promoted_by_4);
						af.pf(4, af.selectById( testVO , "Promoted by" , "top" , "com.microware.cdfi:id/spin_promotedby" , row , voCons.Promoted_by_4 , "4" , false , "Select" , migr,
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));

						if (promotedBy.equals("NRLM")) {
							String ncr = xc.getCellString(row, voCons.New_Revived_Co_opted_104);
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
									.click();
							if (ncr.equals("Revived")) {
								af.pf(304,
										af.dateLogic( testVO , "Revival Date" , "top" , "com.microware.cdfi:id/et_revivalDate" , row , voCons.Revival_Date_304 , "304" , "304" , false , "dd-mm-yyyy" , migr ));
							}

						} else if (promotedBy.equals("State Project")) {
							
							af.pf(204,
									af.enterStringById( testVO , "Promoters name" , "top" , "com.microware.cdfi:id/et_promoter_name" , row , voCons.Promoters_name_204 , "204" , "204" , false , "Type here..." , migr ));
							String ncr = xc.getCellString(row, voCons.New_Revived_Co_opted_104);
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
									.click();
							if (ncr.equals("Revived")) {
								af.pf(304,
										af.dateLogic( testVO , "Revival Date" , "top" , "com.microware.cdfi:id/et_revivalDate" , row , voCons.Revival_Date_304 , "304" , "304" , false , "dd-mm-yyyy" , migr ));
							} else if (ncr.equals("Co-opted")) {
								af.pf(404,
										af.dateLogic( testVO , "Co-option Date" , "top" , "com.microware.cdfi:id/et_coopt_Date" , row , voCons.Co_option_Date_404 , "404" , "404" , false , "dd-mm-yyyy" , migr ));
							}

						} 
						af.errBool(af.invalidFlag());
						af.pseq(id, "004:Promoted By and Sub Parts");
					} catch (Exception e) {
						af.setInvalidFlag();
						af.fseq(id, "004:Promoted By and Sub Parts", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;
					
				case 5:
					try {
						String mtg = xc.getCellString(row, voCons.Meeting_Frequency_5);
						af.pf(5, af.selectById( testVO , "Meeting Frequency" , "top" , "com.microware.cdfi:id/spin_frequency" , row , voCons.Meeting_Frequency_5 , "5" , false , "Select" , migr,
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));

						if (mtg.equals("Fortnightly")) {
							af.pf(105, af.selectById( testVO , "Date" , "top" , "com.microware.cdfi:id/spin_monthlydate" , row , voCons.Date_105 , "105" , false , "Select" , migr,
									"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView",
									"]/android.widget.TableRow[1]/android.widget.TextView",
									"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
							af.pf(205, af.selectById( testVO , "Date" , "top" , "com.microware.cdfi:id/spin_fortnightdate" , row , voCons.Date_205 , "205" , false , "Select" , migr,
									"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView",
									"]/android.widget.TableRow[1]/android.widget.TextView",
									"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));

						} else if (mtg.equals("Monthly")) {
							String s = xc.getCellString(row, voCons.Monthly_day_305);
							af.pf(305, af.selectById( testVO , "Monthly day" , "top" , "com.microware.cdfi:id/spin_monthly" , row , voCons.Monthly_day_305 , "305" , false , "Select" , migr,
									"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
							if (s.equals("Date")) {
								af.pf(105, af.selectById( testVO , "Date" , "top" , "com.microware.cdfi:id/spin_monthlydate" , row , voCons.Date_105 , "105", false , "Select" , migr,
										"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView",
										"]/android.widget.TableRow[1]/android.widget.TextView",
										"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
							} else {
								af.pf(405, af.selectById( testVO , "Day" , "top" , "com.microware.cdfi:id/spin_weekday" , row , voCons.Day_405 , "405" , false , "Select" , migr,
										"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView",
										"]/android.widget.TableRow[1]/android.widget.TextView",
										"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
							}
						}
						af.errBool(af.invalidFlag());
						af.pseq(id, "007:Meeting Frequency and Sub Parts");
					} catch (Exception e) {
						af.setInvalidFlag();
						af.fseq(id, "007:Meeting Frequency and Sub Parts", e);
					}
					if (id != 000)
						break;
				case 6:
					try {
						int btn = 0;
						if (xc.getCellString(row, voCons.Financial_Intermediation_6).equals("Yes")) {
							btn = 1;
						} else {
							btn = 2;
						}
						af.pf(6, af.rdbtn(testVO, "Financial Intermediation", "top", btn, row,
								voCons.Financial_Intermediation_6, "6", false, 2, migr,
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[2]/android.widget.RadioGroup/android.widget.RadioButton"));
						if (btn == 1) {
							af.pf(106, af.selectById( testVO , "Compulsory Saving Frequency" , "top" , "com.microware.cdfi:id/spin_savingfrequency" , row , voCons.Compulsory_Saving_Frequency_106 , 
									"106" , false , "Select" , migr,
									"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
							af.pf(206, af.enterNumById( testVO , "Compulsory Saving Amount" , "top" , "com.microware.cdfi:id/et_saving" , row , voCons.Compulsory_Saving_Amount_206 , "206" , "206" , false , "Type here..." , migr ));
							af.pf(306, af.enterNumById( testVO , "Compulsory Saving Interest Rate (Annual) %" , "top" , "com.microware.cdfi:id/et_ComsavingRoi" , row , voCons.Compulsory_Saving_Interest_Rate_Annual_306 , "306" , "306" , false , "Type here..." , migr ));
						}
						btn = 0;
						if (xc.getCellString(row, voCons.Voluntary_Saving_406).equals("Yes")) {
							btn = 1;
						} else {
							btn = 2;
						}
						af.pf(406, af.rdbtn( testVO , "Voluntary Saving" , "top" , btn , row , voCons.Voluntary_Saving_406 , "406"  , false , 2 , migr,
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[2]/android.widget.RadioGroup/android.widget.RadioButton"));
//						if (btn == 1) {
//							af.pf(106, af.selectById( testVO , "Compulsory Saving Frequency" , "top" , "com.microware.cdfi:id/spin_savingfrequency" , row , voCons.Compulsory_Saving_Frequency_106 , 
//									"106" , false , "Select" , migr,
//									"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
//									"]/android.widget.TableRow[1]/android.widget.TextView[1]",
//									"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
//							af.pf(206, af.enterNumById( testVO , "Compulsory Saving Amount" , "top" , "com.microware.cdfi:id/et_saving" , row , voCons.Compulsory_Saving_Amount_206 , "206" , "206" , false , "Type here..." , migr ));
//							af.pf(306, af.enterNumById( testVO , "Compulsory Saving Interest Rate (Annual) %" , "top" , "com.microware.cdfi:id/et_ComsavingRoi" , row , voCons.Compulsory_Saving_Interest_Rate_Annual_306 , "306" , "306" , false , "Type here..." , migr ));
//						}
						af.errBool(af.invalidFlag());
						af.pseq(6, "006:Financial Intermediation");
					} catch (Exception e) {
						af.fseq(6, "006:Financial Intermediation", e);
					}
					if (id != 000)
						break;
					
				case 7:
					try {
						int op = 0;
						if (xc.getCellString(row, voCons.Bookkeeper_Identified__7).equals("Yes-Internal"))
							op = 1;
						else if (xc.getCellString(row, voCons.Bookkeeper_Identified__7).equals("Yes-External"))
							op = 2;
						else if (xc.getCellString(row, voCons.Bookkeeper_Identified__7).equals("No"))
							op = 3;

						af.pf(7, af.rdbtn( testVO , "Bookkeeper Identified?" , "top" , op , row , voCons.Bookkeeper_Identified__7 , "7" , false , 1 , migr,
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.RadioGroup/android.widget.RadioButton"));

						if (op == 1) {
							if ((xc.getCellString(row, voCons.Type_0).equals("New VO"))) {
								appdriver.findElement(MobileBy.AndroidUIAutomator(
										"new UiSelector().text(\"No Member Available with the Phone no in this VO\")"))
										.click();
								appdriver.findElementByXPath(
										"//android.widget.LinearLayout/android.widget.ListView/android.widget.TextView")
										.click();
							} else
								af.pf(207, af.selectById( testVO, "Bookkeeper's Name" , "top" , "com.microware.cdfi:id/et_bookkeeper_name" , row , 
										voCons.Bookkeepers_Name_207 , "207" , false , "No Member Available with the Phone no in this VO" , migr,
										"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView",
										"]/android.widget.TableRow[1]/android.widget.TextView",
										"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));

							af.pf(307,
									af.enterNumById( testVO , "Bookkeeper/'s Mobile No." , "top" , "com.microware.cdfi:id/et_bookkeeper_s_mobile_no" , row , voCons.Bookkeeper_s_Mobile_No_307 , "307" , "307" , false , "Type here..." , migr ));
						} else if (op == 2) {
							af.pf(207, af.enterStringById( testVO , "Bookkeeper's Name" , "top" , "com.microware.cdfi:id/et_bookkeeper_name" , row , voCons.Bookkeepers_Name_207 , "207" , "207" , false , "Type here..." , migr ));
							af.pf(307, af.enterNumById( testVO , "Bookkeeper/'s Mobile No." , "top" , "com.microware.cdfi:id/et_bookkeeper_s_mobile_no" , row , voCons.Bookkeeper_s_Mobile_No_307 , "307" , "307" , false , "Type here..." , migr ));
						}
						
						af.errBool(af.invalidFlag());
						af.pseq(12, "012:Bookkeeper");
					} catch (Exception e) {
						af.setInvalidFlag();
						af.fseq(12, "012:Bookkeeper", e);
					}
					if (id != 000)
						break;			

				case 8:
					try {
						af.pf(8,
								af.enterNumById( testVO , "Tenure of elected Office Bearers" , "top" , "com.microware.cdfi:id/et_electiontenure" , row , voCons.Tenure_of_elected_Office_Bearers_8 , "8" , "8" , false , "Type here..." , migr ));
					} catch (Exception e) {
						af.fseq(id, "016:Tenure of elected Office Bearers", e);
					}
					if (id != 000)
						break;
				case 9:
					try {
						mt.scrollToText("VO resolution copy", "top", x, y);
						appdriver.findElementById("com.microware.cdfi:id/Imgmember").click();
						appdriver.findElementById("com.microware.cdfi:id/layout_camera").click();
						util.cameraLogic.click();
						af.errBool(af.invalidFlag());
						af.pseq(id, "9: VO Resolution Copy Upload");
					} catch (Exception e) {
						af.fseq(id, "9: VO Resolution Copy Upload", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;

				default:
					break;

				}
			}

			if (af.invalidFlag()) {
				System.out.println("Error---->Atleast one field is incorrect");
				System.out.println("Error---->Data not saved: Cannot Proceed(Correct the Errors first)");
				testVO.log(Status.INFO, "Error---->Atleast one field is incorrect");
				testVO.log(Status.INFO, "Error---->Data not saved: Cannot Proceed(Correct the Errors first)");
				appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
				appdriver.findElementById("com.microware.cdfi:id/btn_yes").click();
				navigateBackToScreen("VO List");
				t = 1;
				id = 9999;
			}

			if (neg_test_flag) {

				appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
				if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
						.equals("Data saved successfully")
						|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
								.equals("Data Updated Successfully")
						|| !invalid_flag) {
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					testVO.log(Status.INFO, "Necessary fields for saving SHG filled.");
					System.out.println("Necessary fields for saving SHG filled.");
				} else {
					String err = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
					testVO.log(Status.FAIL, "Error---->" + err);
					System.out.println("Error---->" + err);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					testVO.log(Status.INFO, "Data not saved: Cannot Proceed(Resume next text case)");
					System.out.println("Error---->Data not saved: Cannot Proceed(Resume next text case)");
					try {
						String exp_errs = xc.getCellString(row, voCons.Expected_Error_Message_99999);
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
					navigateBackToScreen("VO Basic Details");
					mt.scrollToVisibleElementOnScreen("com.microware.cdfi:id/et_voname", "id", "bottom", x, y);
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
				testVO.log(Status.PASS, "Necessary fields for saving SHG filled.");
				System.out.println("Necessary fields for saving SHG filled.");
			} else {
				String err = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
				testVO.log(Status.FAIL, "Error---->" + err);
				System.out.println("Error---->" + err);
				appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
				testVO.log(Status.INFO, "Atleast one field is incorrect");
				System.out.println("Error---->Atleast one field is incorrect");
				testVO.log(Status.INFO, "Data not saved: Cannot Proceed(Correct the Errors first)");
				System.out.println("Error---->Data not saved: Cannot Proceed(Correct the Errors first)");
				navigateBackToScreen("SHG Basic Details");
				appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
				appdriver.findElementById("com.microware.cdfi:id/btn_yes").click();
				fail++;
				t = 1;
				id = 9999;
			}
		}

		af.resetInvalidFlag();

		if ((id != 9999)) {
			for (int iterations = 0; iterations < idList.length; iterations++) {
				id = Integer.valueOf(idList[iterations]);
				if (id > 18 || id == 0) {
					switch (id) {
					case 000:
//					case 13:
//						try {
//							af.resetInvalidFlag();
//							int k = 0;
//							while (k < 6) {
//								try {
//									Thread.sleep(2000);
//									appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//									appdriver.findElementById("com.microware.cdfi:id/lay_phone").click();
//									k = 6;
//								} catch (Exception e) {
//									System.out.println("Wasn't able to press the phone button");
//									k++;
//								}
//							}
//
//							if (af.checkMigrFields(testVO, row, voCons.phone,
//									"//androidx.appcompat.widget.LinearLayoutCompat/androidx.recyclerview.widget.RecyclerView/androidx.appcompat.widget.LinearLayoutCompat",
//									"//androidx.appcompat.widget.LinearLayoutCompat/androidx.recyclerview.widget.RecyclerView/androidx.appcompat.widget.LinearLayoutCompat[",
//									"]/android.widget.TableRow[3]/android.widget.ImageView[2]",
//									"]/android.widget.TableRow[3]/android.widget.ImageView[1]",
//									"com.microware.cdfi:id/addAddress",
//									"")) {
//
//								String[] phnos = xc.getCellString(row, voCons.mobNosColNUm).split(";");
//								for (int i = 0; i < phnos.length; i++) {
//
//									String[] s = phnos[i].split(":");
//									appdriver.findElementById("com.microware.cdfi:id/spin_belongMember").click();
//
//									appdriver.findElement(MobileBy.AndroidUIAutomator(
//											"new UiSelector().textContains(\"" + s[0].trim() + "\")")).click();
////									appdriver.findElementById("com.microware.cdfi:id/spin_Memberphone").click();
//									Thread.sleep(2000);
////									appdriver.findElement(MobileBy
////											.AndroidUIAutomator("new UiSelector().text(\"" + s[1].trim() + "\")"))
////											.click();
//									af.selectFirstOptionById("Mobile number", 
//											"top",
//											"com.microware.cdfi:id/spin_Memberphone", 
//											"//android.widget.FrameLayout/android.widget.ListView/android.widget.TextView[2]",
//											migr,
//											"Select",
//											"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView", 
//											"]/android.widget.TableRow[1]/android.widget.TextView", 
//											"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView");
//									appdriver.findElementById("com.microware.cdfi:id/btn_add").click();
//									if (!appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
//											.equals("Data Updated Successfully")
//											|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
//													.equals("Data saved successfully"))
//										appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
//									else {
//										String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg")
//												.getText();
//										appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
//										if (neg_test_flag) {
//											try {
//												String exp_errs = xc.getCellString(row, voCons.expErrMessColNum);
//												if (ex.contains(exp_errs)) {
//													System.out.println("|||||||||||||||||||||||||||||");
//													System.out.println("Expected Error is encountered");
//													System.out.println("   ((Negetive Test Passed))");
//													System.out.println("|||||||||||||||||||||||||||||");
//													++neg_test_count;
//												} else {
//													System.out.println("   (((Negetive Test Failed)))\n");
//												}
//											} catch (NullPointerException np) {
//												System.out.println("---->>Expected Errors is empty.");
//											}
//										}
//										throw new Exception(ex);
//									}
//									pseq(id, "019:Phone Number");
//								}
//							}
//						} catch (Exception e) {
//							fseq(id, "019:Phone Number", e);
//							randomPressLogic.press(0.5, 0.05);
//						}
//						if (id != 000)
//							break;

//					case 14:
//						try {
//							af.resetInvalidFlag();
//							int i = 0;
//							while (i < 6) {
//								try {
//									Thread.sleep(2000);
//									appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//									appdriver.findElementById("com.microware.cdfi:id/lay_location").click();
//									i = 6;
//									;
//								} catch (Exception e) {
//									System.out.println("Wasn't able to press the address button");
//									i++;
//								}
//							}
//							
//							Thread.sleep(2000);
//							if (af.checkMigrFields(testVO, row, voCons.address,
//									"//androidx.appcompat.widget.LinearLayoutCompat/androidx.recyclerview.widget.RecyclerView/androidx.appcompat.widget.LinearLayoutCompat",
//									"//androidx.appcompat.widget.LinearLayoutCompat/androidx.recyclerview.widget.RecyclerView/androidx.appcompat.widget.LinearLayoutCompat[",
//									"]/android.widget.TableRow[3]/android.widget.ImageView[2]",
//									"]/android.widget.TableRow[3]/android.widget.ImageView[1]",
//									"com.microware.cdfi:id/addAddress",
//									"")) {
//								appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//								af.pf(20,
//										af.enterStringById(testVO, "Address Line 1", "top",
//												"com.microware.cdfi:id/et_address1", row, voCons.add1ColNum, "020",
//												"020", false, "Type here...", migr));
//								af.pf(20, af.enterNumById(testVO, "Pincode", "top", "com.microware.cdfi:id/et_pincode",
//										row, voCons.pinColNum, "220", "220", false, "Type here...", migr));
//								appdriver.findElementById("com.microware.cdfi:id/btn_add").click();
//								if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
//										.equals("Data Updated Successfully")
//										|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
//												.equals("Data saved successfully"))
//									appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
//								else {
//									String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
//									testVO.log(Status.FAIL, ex);
//									System.out.println("Error->" + ex);
//									appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
//									if (neg_test_flag) {
//										try {
//											String exp_errs = xc.getCellString(row, voCons.expErrMessColNum);
//											if (ex.contains(exp_errs)) {
//												System.out.println("|||||||||||||||||||||||||||||");
//												System.out.println("Expected Error is encountered");
//												System.out.println("   ((Negetive Test Passed))");
//												System.out.println("|||||||||||||||||||||||||||||");
//												++neg_test_count;
//											} else {
//												System.out.println("   (((Negetive Test Failed)))\n");
//											}
//										} catch (NullPointerException np) {
//											System.out.println("---->>Expected Errors is empty.");
//										}
//									}
//									throw new Exception(ex);
//								}
//
//								if (af.invalidFlag())
//									throw new Exception("");
//								af.pseq(id, "020:Address");
//							}
//
//						} catch (Exception e) {
//							af.resetInvalidFlag();
//							af.fseq(id, "020:Address", e);
//						}
//						if (id != 000)
//							break;
						
					case 21:
						try {
							invalid_flag = false;
							int i = 0;
							while (i < 6) {
								try {
									Thread.sleep(2000);
									appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
									appdriver.findElementById("com.microware.cdfi:id/IvBank").click();
									i = 6;
								} catch (Exception e) {
									System.out.println("Wasn't able to press the bank button");
									i++;
								}
							}

							int k = 0;
							while (k < 6) {
								try {
									Thread.sleep(2000);
									appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
									appdriver.findElementById("com.microware.cdfi:id/addBank").click();
									k = 6;
								} catch (Exception e) {
									System.out.println("Wasn't able to press the address button");
									k++;
								}
							}

							af.pf(115, af.enterStringById( testVO , "IFSC Code" , "top" , "com.microware.cdfi:id/et_ifsc" , row , voCons.IFSC_Code_115 , "115" , "115" , false , "Type here..." , migr ));
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
										.sendKeys(xc.getCellString(row, voCons.Bank_name_215));
								appdriver.findElementById("com.microware.cdfi:id/tvmyspinner").click();
//								mt.scrollToText("Bank Branch", "top");
//								appdriver.findElementById("com.microware.cdfi:id/lay_branch").click();
//								appdriver.findElementById("android:id/search_src_text")
//									.sendKeys(xc.getCellString(row, memCons.bankBranchColNum));
//								appdriver.findElementByXPath("//android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[2]").click();
							} catch (Exception e) {

							}
							String acc = "";
							Random objGenerator = new Random();
							acc = String.valueOf(objGenerator.nextInt(10000, 99999))
									+ String.valueOf(objGenerator.nextInt(100000, 999999));
							System.out.println("Account: " + acc);
							try {
								appdriver.findElementById("com.microware.cdfi:id/et_Accountno").sendKeys(acc);
							} catch (Exception e) {
								System.out.println("Unable to enter Account Data. Possible Migrated Data.");
							}

							try {
								appdriver.findElementById("com.microware.cdfi:id/et_retype_Accountno").sendKeys(acc);
							} catch (Exception e) {
								System.out.println("Unable to enter Retype Account Data. Possible Migrated Data.");
							}
//								af.pf(21, af.enterNumById(testVO, 
//										"Re-Type Account No.", "", 
//										"com.microware.cdfi:id/et_retype_Accountno", 
//										row, voCons.retypAccNoColNum, 
//										"521", "521", false, "Type here...", migr));

//								enterLongNum_Id("Re-Type Account No.", "top",
//										"com.microware.cdfi:id/et_retype_Accountno", row, voCons.retypAccNoColNum,
//										"521:||Validation Error||", "#");
							af.pf(615, af.dateLogic( testVO , "Account opening date" , "top" , "com.microware.cdfi:id/et_opdate" , row , voCons.Account_opening_date_615 , "615" , "615" , false , "dd-mm-yyyy" , migr ));
//								String date = xc.getCellString(row, voCons.accOpDateColNum);
//								dateLogic.datePicker(date, "com.microware.cdfi:id/et_opdate");
							appdriver.findElementById("com.microware.cdfi:id/ImgFrntpage").click();
							cameraLogic.click();
							int op = 0;
							if (xc.getCellString(row, voCons.Is_Default_815).equals("Yes"))
								op = 1;
							else if (xc.getCellString(row, voCons.Is_Default_815).equals("No"))
								op = 2;
							af.pf(21, af.rdbtn( testVO , "Is Default" , "top" , op , row , voCons.Is_Default_815 , "815" , false , 1 , migr,
									"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView",
									"]/android.widget.TableRow[1]/android.widget.TextView",
									"]/android.widget.TableRow[2]/android.widget.RadioGroup/android.widget.RadioButton"));
//								appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
//										+ xc.getCellString(row, voCons.isDefaultColNum) + "\")")).click();
							if (invalid_flag)
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
									testVO.log(Status.FAIL, ex);
									System.out.println("Error->" + ex);
									appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
									if (neg_test_flag) {
										try {
											String exp_errs = xc.getCellString(row, voCons.Expected_Error_Message_99999);
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

							af.pseq(id, "15:Bank Account");

						} catch (Exception e) {
							af.fseq(id,  "15:Bank Account",  e);
							randomPressLogic.press(0.5, 0.05);
							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
						} finally {
							count++;
						}
						if (id != 000)
							break;
					
					}

				}
			}
		}

		ExtentManager.addScreenShotsToTest("Save and Check", testVO);

		if (t == 0)
			navigateBackToScreen("VO List");

		if (neg_test_flag)
			pass = neg_test_count;
		
		

		int[] val = { pass, fail, count };
		val=af.pass_fail();
		val[0]+=pass;
		val[1]+=fail;
		val[2]+=count;
		return val;

	}

	

	public static void navigateBackToScreen(String screen_title) throws Exception {
		int i = 0;
		String title = "";
		while (i < 3) {
			try {
				Thread.sleep(2000);
				appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				title = appdriver.findElementById("com.microware.cdfi:id/tv_title").getText();
			} catch (Exception e) {
				Thread.sleep(3000);
				appdriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
				i++;
			}
			break;
		}

		i = 0;
		try {
			while (!title.equals(screen_title)) {
				Thread.sleep(3000);
				appdriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
				Thread.sleep(3000);
				appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				if (appdriver.findElementById("com.microware.cdfi:id/tv_title").getText().equals("SHG"))
					break;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot navigate to " + screen_title + " screen");
			throw new Exception("The app might have crashed");
		}
	}

	

}
