package app;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import lokos.lokosTest;
import reporting.ExtentManager;
import util.MobileTouchAdv;
import util.cameraLogic;
import util.randomPressLogic;
import util.summary;

public class shgProfileCreation extends lokosTest {

	public static boolean neg_test_flag = false;
	public static int neg_test_count = 0;
	public static boolean invalid_flag = false;
	public static int pass = 0;
	public static int fail = 0;
	public static boolean migr = false;
	public static MobileTouchAdv mt = null;
	public static final double x = 0.35;
	public static final double y = 0.70;

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
			for (@SuppressWarnings("unused")
			String s : idList) {
				shg_check[0][Integer.valueOf(idList[k])] = Integer.valueOf(idList[k]);
				++k;
			}
		} else {
			for (int j = 0; j < shg_check[0].length; j++) {
				shg_check[0][j] = j;
			}
		}

		int[] val = null;
		if (xc.getCellString(row, profileCons.numMemAddColNum).equals("0"))
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
		pass = 0;
		fail = 0;

		mt = new MobileTouchAdv(appdriver);
		allFnxsP af = new allFnxsP(xc, appdriver, testSHG, mt, du, shg_check);

		if (xc.getCellString(row, profileCons.flowTypeColNum).contains("Migration"))
			migr = true;

		try {
			if (xc.getCellString(row, profileCons.flowTypeColNum).contains("Check")) {
				neg_test_flag = true;
				testSHG.log(Status.INFO, "NEGETIVE TESTING");
			} else if (xc.getCellString(row, profileCons.flowTypeColNum).contains("Migration")) {
				migr = true;
				testSHG.log(Status.INFO, "MIGRATION DATA TO BE CHECKED");
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
					af.pf(1, af.enterStringById(testSHG, "SHG Name", "", "com.microware.cdfi:id/et_groupname", row,
							profileCons.shgNameColNum, "001", "001", false, "Type here...", migr));

					if (id != 000)
						break;
				case 2:
					af.pf(2, af.selectById(testSHG, "Grampanchayat", "top", // scroll direction
							"com.microware.cdfi:id/spin_panchayat", // location
							row, // row
							profileCons.gpColNum, "002", false, false, "", "", "", ""));
					if (id != 000)
						break;

				case 3:
					af.pf(3, af.selectById(testSHG, "Village", "top", "com.microware.cdfi:id/spin_village", row,
							profileCons.villageColNum, "003", false, false, "", "", "", ""));
					if (id != 000)
						break;

				case 4:
					af.pf(4, af.dateLogic(testSHG, "Formation Date", "top", row, profileCons.dateColNum,
							"com.microware.cdfi:id/et_formationDate", "004", "004", false, "dd-mm-yyyy", migr));
					if (id != 000)
						break;
				case 5:
					try {
						String promotedBy = xc.getCellString(row, profileCons.promotedByColNum);
						af.pf(5, af.selectById(testSHG, "Promoted by", "top", "com.microware.cdfi:id/spin_promotedby",
								row, profileCons.promotedByColNum, "005", false, migr, "Select",
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));

						if (promotedBy.equals("NRLM")) {
							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
									.click();
							if (ncr.equals("Revived")) {
								af.pf(305,
										af.dateLogic(testSHG, "Revival Date", "top", row, profileCons.revDateColNum,
												"com.microware.cdfi:id/et_revivalDate", "305", "305", false,
												"dd-mm-yyyy", migr));
							}

						} else if (promotedBy.equals("State Project")) {
							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
									.click();
							if (ncr.equals("Revived")) {
								af.pf(305,
										af.dateLogic(testSHG, "Revival Date", "top", row, profileCons.revDateColNum,
												"com.microware.cdfi:id/et_revivalDate", "305", "305", false,
												"dd-mm-yyyy", migr));
							} else if (ncr.equals("Co-opted")) {
								af.pf(405,
										af.dateLogic(testSHG, "Co-option Date", "top", row, profileCons.cooptDateColNum,
												"com.microware.cdfi:id/et_coopt_Date", "405", "405", false,
												"dd-mm-yyyy", migr));
							}

						} else if (promotedBy.equals("NGO")) {
							af.pf(205, af.selectById(testSHG, "Promoters name", "top",
									"com.microware.cdfi:id/spin_FundingAgency", row, profileCons.promoterColNum, "205",
									false, migr, "Select",
									"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
									.click();
							if (ncr.equals("Revived")) {
								af.pf(305,
										af.dateLogic(testSHG, "Revival Date", "top", row, profileCons.revDateColNum,
												"com.microware.cdfi:id/et_revivalDate", "305", "305", false,
												"dd-mm-yyyy", migr));
							} else if (ncr.equals("Co-opted")) {
								af.pf(405,
										af.dateLogic(testSHG, "Co-option Date", "top", row, profileCons.cooptDateColNum,
												"com.microware.cdfi:id/et_coopt_Date", "405", "405", false,
												"dd-mm-yyyy", migr));
							}

						} else if (promotedBy.equals("SGSY")) {
							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
									.click();
							if (ncr.equals("Revived")) {
								af.pf(305,
										af.dateLogic(testSHG, "Revival Date", "top", row, profileCons.revDateColNum,
												"com.microware.cdfi:id/et_revivalDate", "305", "305", false,
												"dd-mm-yyyy", migr));
							} else if (ncr.equals("Co-opted")) {
								af.pf(405,
										af.dateLogic(testSHG, "Co-option Date", "top", row, profileCons.cooptDateColNum,
												"com.microware.cdfi:id/et_coopt_Date", "405", "405", false,
												"dd-mm-yyyy", migr));
							}

						} else if (promotedBy.equals("Other")) {
							af.pf(205,
									af.enterStringById(testSHG, "Promoters name", "top",
											"com.microware.cdfi:id/et_promoter_name", row, profileCons.promoterColNum,
											"205", "205", false, "Type here...", migr));
							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
									.click();
							if (ncr.equals("Revived")) {
								af.pf(305,
										af.dateLogic(testSHG, "Revival Date", "top", row, profileCons.revDateColNum,
												"com.microware.cdfi:id/et_revivalDate", "305", "305", false,
												"dd-mm-yyyy", migr));
							} else if (ncr.equals("Co-opted")) {
								af.pf(405,
										af.dateLogic(testSHG, "Co-option Date", "top", row, profileCons.cooptDateColNum,
												"com.microware.cdfi:id/et_coopt_Date", "405", "405", false,
												"dd-mm-yyyy", migr));
							}
						}
						af.errBool(af.invalidFlag());
						af.pseq(id, "005:Promoted By and Sub Parts");
					} catch (Exception e) {
						af.setInvalidFlag();
						af.fseq(id, "005:Promoted By and Sub Parts", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;
				case 6:
					try {
						String s = xc.getCellString(row, profileCons.Women_Special);
						if (s.equals("Women(Regular)"))
							af.pf(6, af.rdbtn(testSHG, "SHG Type", "top", 1, row, profileCons.Women_Special, "006",
									false, migr, 1,
									"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[2]/android.widget.RadioGroup/android.widget.RadioButton"));
						else {
							af.pf(6, af.rdbtn(testSHG, "SHG Type", "top", 2, row, profileCons.Women_Special, "006",
									false, migr, 1,
									"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[2]/android.widget.RadioGroup/android.widget.RadioButton"));
							af.pf(106, af.selectById(testSHG, "Tags", "top", "com.microware.cdfi:id/spin_tags", row,
									profileCons.tagsColNum, "106", false, migr, "SELECT",
									"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
							if (xc.getCellString(row, profileCons.tagsColNum).equals("OTHER")) {
								af.pf(206,
										af.enterStringById(testSHG, "Other(specify)", "top",
												"com.microware.cdfi:id/et_OtherTags", row, profileCons.tagsOtherColNum,
												"206", "206", false, "Type here...", migr));
							}
						}
					} catch (Exception e) {
						af.setInvalidFlag();
						af.fseq(id, "006:SHG Type", e);
					}
					if (id != 000)
						break;
				case 7:
					try {
						String mtg = xc.getCellString(row, profileCons.meetingFreqColNum);
						af.pf(7, af.selectById(testSHG, "Meeting Frequency", "top",
								"com.microware.cdfi:id/spin_frequency", row, profileCons.meetingFreqColNum, "007",
								false, migr, "Select",
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));

						if (mtg.equals("Weekly")) {
							af.pf(107, af.selectById(testSHG, "Day", "top", "com.microware.cdfi:id/spin_weekday", row,
									profileCons.dayWColNum, "107", false, migr, "Select",
									"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));

						} else if (mtg.equals("Fortnightly")) {
							af.pf(207, af.selectById(testSHG, "Fortnight Week", "top",
									"com.microware.cdfi:id/spin_fortnight", row, profileCons.frtn8WeekFColNum, "207",
									false, migr, "Select",
									"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
							String s = xc.getCellString(row, profileCons.frtn8WeekFColNum);
							if (s.equals("Date")) {
								af.pf(407, af.selectById(testSHG, "Date", "top",
										"com.microware.cdfi:id/spin_fortnightdate", row, profileCons.dateOtherFColNum,
										"407", false, migr, "Select",
										"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
										"]/android.widget.TableRow[1]/android.widget.TextView[1]",
										"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
							} else {
								af.pf(307, af.selectById(testSHG, "Day", "top", "com.microware.cdfi:id/spin_weekday",
										row, profileCons.day1_2FColNum, "307", false, migr, "Select",
										"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
										"]/android.widget.TableRow[1]/android.widget.TextView[1]",
										"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
							}

						} else if (mtg.equals("Monthly")) {
							String s = xc.getCellString(row, profileCons.monthlydayMColNum);
							af.pf(507, af.selectById(testSHG, "Monthly Day", "top",
									"com.microware.cdfi:id/spin_monthly", row, profileCons.monthlydayMColNum, "507",
									false, migr, "Select",
									"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[1]/android.widget.TextView[1]",
									"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
							if (s.equals("Date")) {
								af.pf(707, af.selectById(testSHG, "Date", "top",
										"com.microware.cdfi:id/spin_monthlydate", row, profileCons.dateMColNum, "707",
										false, migr, "Select",
										"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
										"]/android.widget.TableRow[1]/android.widget.TextView[1]",
										"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
							} else {
								af.pf(607, af.selectById(testSHG, "Day", "top", "com.microware.cdfi:id/spin_weekday",
										row, profileCons.dayMColNum, "607", false, migr, "Select",
										"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
										"]/android.widget.TableRow[1]/android.widget.TextView[1]",
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
				case 8:
					try {
						af.pf(8, af.selectById(testSHG, "Compulsory Saving Frequency", "top",
								"com.microware.cdfi:id/spin_savingfrequency", row, profileCons.compSavFreqColNum, "008",
								false, migr, "Select",
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));

					} catch (Exception e) {
						af.fseq(8, "Error 008", e);
					}
				case 9:
					try {
						af.pf(9, af.enterNumById(testSHG, "Compulsory Saving Amount", "top",
								"com.microware.cdfi:id/et_saving", row, profileCons.compSavAmtColNum, "009", "009",
								false, "Type here...", migr));

					} catch (Exception e) {
						af.fseq(id, "009:Compulsory Saving Amount", e);
					}
					if (id != 000)
						break;
				case 10:
					try {
						af.pf(10,
								af.enterNumById(testSHG, "Compulsory Saving Interest Rate (Annual) %", "top",
										"com.microware.cdfi:id/et_ComsavingRoi", row, profileCons.compSavIRColNum,
										"010", "010", false, "0", migr));
					} catch (Exception e) {
						af.fseq(id, "010:Compulsory Saving Interest Rate", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;

				case 11:
					try {
						int op = 0;
						if (xc.getCellString(row, profileCons.voluntarySavColNum).equals("Yes"))
							op = 1;
						else if (xc.getCellString(row, profileCons.voluntarySavColNum).equals("No"))
							op = 2;

						af.pf(11, af.rdbtn(testSHG, "Voluntary Saving", "top", op, row, profileCons.voluntarySavColNum,
								"11", false, migr, 2,
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[2]/android.widget.RadioGroup/android.widget.RadioButton"));
						if (xc.getCellString(row, profileCons.voluntarySavColNum).equals("Yes")) {
							af.pf(111, af.enterNumById(testSHG, "Voluntary Saving interest rate (Annual) %", "top",
									"com.microware.cdfi:id/et_voluntarysavingROI", row,
									profileCons.voluntarySavIRColNum, "111", "111", false, "Type here...", migr));
						}
					} catch (Exception e) {
						af.setInvalidFlag();
						af.fseq(id, "011:Voluntary Savings and Sub-parts", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;
				case 12:
					try {
						int op = 0;
						if (xc.getCellString(row, profileCons.bkIdColNum).equals("Yes-Internal"))
							op = 1;
						else if (xc.getCellString(row, profileCons.bkIdColNum).equals("Yes-External"))
							op = 2;
						else if (xc.getCellString(row, profileCons.bkIdColNum).equals("No"))
							op = 3;

						af.pf(12, af.rdbtn(testSHG, "Bookkeeper Identified?", "top", op, row, profileCons.bkIdColNum,
								"012", false, migr, 1,
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.RadioGroup/android.widget.RadioButton"));

						if (op == 1) {
							if ((xc.getCellString(row, profileCons.flowTypeColNum).equals("New")) && (!addMem)) {
								appdriver.findElement(MobileBy.AndroidUIAutomator(
										"new UiSelector().text(\"No Member available with the phone no in this SHG\")"))
										.click();
								appdriver.findElementByXPath(
										"//android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[2]")
										.click();
							} else
								af.pf(112, af.selectById(testSHG, "Bookkeeper Name", "top",
										"com.microware.cdfi:id/lay_spinmember", row, profileCons.bkNameColNum, "112",
										false, migr, "No Member available with the phone no in this SHG",
										"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
										"]/android.widget.TableRow[1]/android.widget.TextView[1]",
										"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));

							af.pf(212,
									af.enterNumById(testSHG, "Bookkeeper's Mobile No.", "top",
											"com.microware.cdfi:id/et_bookkeeper_s_mobile_no", row,
											profileCons.bkMobColNum, "212", "212", false, "Type here...", migr));
						} else if (op == 2) {
							af.pf(112,
									af.enterStringById(testSHG, "Bookkeeper Name", "top",
											"com.microware.cdfi:id/et_bookkeeper_name", row, profileCons.bkNameColNum,
											"112", "112", false, "Type here...", migr));
							af.pf(212,
									af.enterNumById(testSHG, "Bookkeeper's Mobile No.", "top",
											"com.microware.cdfi:id/et_bookkeeper_s_mobile_no", row,
											profileCons.bkMobColNum, "212", "212", false, "Type here...", migr));
						}

					} catch (Exception e) {
						af.setInvalidFlag();
						af.fseq(id, "012:Bookkeeper", e);
					}
					if (id != 000)
						break;
				case 13:
					try {
						af.pf(13, af.selectById(testSHG, "Primary Livelihood Activity", "top",
								"com.microware.cdfi:id/spin_primaryActivity", row, profileCons.primaryla, "013", false,
								migr, "Select",
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
					} catch (Exception e) {
						af.fseq(id, "013:Primary Livelihood", e);
					}
					if (id != 000)
						break;
				case 14:
					try {
						af.pf(14, af.selectById(testSHG, "Secondary Livelihood Activity", "top",
								"com.microware.cdfi:id/spin_secondaryActivity", row, profileCons.secondaryla, "014",
								false, migr, "Select",
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
					} catch (Exception e) {
						af.fseq(id, "014:Secondary Livelihood", e);
					}
					if (id != 000)
						break;
				case 15:
					try {
						af.pf(15, af.selectById(testSHG, "Tertiary Livelihood Activity", "top",
								"com.microware.cdfi:id/spin_tertiaryActivity", row, profileCons.tertiaryla, "015",
								false, migr, "Select",
								"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[1]/android.widget.TextView[1]",
								"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
					} catch (Exception e) {
						af.fseq(id, "015:Tertiary Livelihood", e);
					}
					if (id != 000)
						break;

				case 16:
					try {
						af.pf(16,
								af.enterNumById(testSHG, "Tenure of elected Office Bearers", "top",
										"com.microware.cdfi:id/et_electiontenure", row, profileCons.tenureColNum, "016",
										"016", false, "Type here...", migr));
					} catch (Exception e) {
						af.fseq(id, "016:Tenure of elected Office Bearers", e);
					}
					if (id != 000)
						break;
				case 17:
					try {
						mt.scrollToText("SHG resolution copy", "top", x, y);
						appdriver.findElementById("com.microware.cdfi:id/Imgmember").click();
						appdriver.findElementById("com.microware.cdfi:id/layout_camera").click();
						util.cameraLogic.click();
						af.pseq(id, "017: SHG Resolution Copy Upload");
					} catch (Exception e) {
						af.fseq(id, "017: SHG Resolution Copy Upload", e);
					} finally {
						count++;
					}
					if (id != 000)
						break;

				case 18:
					try {
						if (!xc.getCellString(row, profileCons.typeColNum).equals("New SHG")) {
							if (!xc.getCellString(row, memCons.typeColNum).equals("New")) {
								af.pf(18, af.selectById(testSHG, "Status", "top", "com.microware.cdfi:id/spin_status",
										row, profileCons.statusColNum, "018", false, migr, "Active",
										"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView[1]",
										"]/android.widget.TableRow[1]/android.widget.TextView[1]",
										"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView"));
							}
							af.pseq(id, "018:Status");
						} else
							--count;

					} catch (Exception e) {
						af.fseq(id, "018:Status", e);
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
				testSHG.log(Status.INFO, "Error---->Atleast one field is incorrect");
				testSHG.log(Status.INFO, "Error---->Data not saved: Cannot Proceed(Correct the Errors first)");
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
								.equals("Data Updated Successfully")
						|| !invalid_flag) {
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
					mt.scrollToVisibleElementOnScreen("com.microware.cdfi:id/tv_shgName", "id", "bottom", x, y);
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

		af.resetInvalidFlag();

		if ((id != 9999) && (!addMem)) {
			for (int iterations = 0; iterations < idList.length; iterations++) {
				id = Integer.valueOf(idList[iterations]);
				if (id > 18 || id == 0) {
					switch (id) {
					case 000:
					case 19:
						try {
							af.resetInvalidFlag();
							int k = 0;
							while (k < 6) {
								try {
									Thread.sleep(2000);
									appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
									appdriver.findElementById("com.microware.cdfi:id/lay_phone").click();
									k = 6;
								} catch (Exception e) {
									System.out.println("Wasn't able to press the phone button");
									k++;
								}
							}

							if (af.checkMigrFields(testSHG, row, profileCons.phone,
									"//androidx.appcompat.widget.LinearLayoutCompat/androidx.recyclerview.widget.RecyclerView/androidx.appcompat.widget.LinearLayoutCompat",
									"//androidx.appcompat.widget.LinearLayoutCompat/androidx.recyclerview.widget.RecyclerView/androidx.appcompat.widget.LinearLayoutCompat[",
									"]/android.widget.TableRow[3]/android.widget.ImageView[2]",
									"]/android.widget.TableRow[3]/android.widget.ImageView[1]",
									"com.microware.cdfi:id/addAddress",
									"")) {

								String[] phnos = xc.getCellString(row, profileCons.mobNosColNUm).split(";");
								for (int i = 0; i < phnos.length; i++) {

									String[] s = phnos[i].split(":");
									appdriver.findElementById("com.microware.cdfi:id/spin_belongMember").click();

									appdriver.findElement(MobileBy.AndroidUIAutomator(
											"new UiSelector().textContains(\"" + s[0].trim() + "\")")).click();
//									appdriver.findElementById("com.microware.cdfi:id/spin_Memberphone").click();
									Thread.sleep(2000);
//									appdriver.findElement(MobileBy
//											.AndroidUIAutomator("new UiSelector().text(\"" + s[1].trim() + "\")"))
//											.click();
									af.selectFirstOptionById("Mobile number", 
											"top",
											"com.microware.cdfi:id/spin_Memberphone", 
											"//android.widget.FrameLayout/android.widget.ListView/android.widget.TextView[2]",
											migr,
											"Select",
											"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView", 
											"]/android.widget.TableRow[1]/android.widget.TextView", 
											"]/android.widget.TableRow[2]/android.widget.Spinner/android.widget.TextView");
									appdriver.findElementById("com.microware.cdfi:id/btn_add").click();
									if (!appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
											.equals("Data Updated Successfully")
											|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
													.equals("Data saved successfully"))
										appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
									else {
										String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg")
												.getText();
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
							}
						} catch (Exception e) {
							fseq(id, "019:Phone Number", e);
							randomPressLogic.press(0.5, 0.05);
						}
						if (id != 000)
							break;

					case 20:
						try {
							af.resetInvalidFlag();
							int i = 0;
							while (i < 6) {
								try {
									Thread.sleep(2000);
									appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
									appdriver.findElementById("com.microware.cdfi:id/lay_location").click();
									i = 6;
									;
								} catch (Exception e) {
									System.out.println("Wasn't able to press the address button");
									i++;
								}
							}
							
							Thread.sleep(2000);
							if (af.checkMigrFields(testSHG, row, profileCons.address,
									"//androidx.appcompat.widget.LinearLayoutCompat/androidx.recyclerview.widget.RecyclerView/androidx.appcompat.widget.LinearLayoutCompat",
									"//androidx.appcompat.widget.LinearLayoutCompat/androidx.recyclerview.widget.RecyclerView/androidx.appcompat.widget.LinearLayoutCompat[",
									"]/android.widget.TableRow[3]/android.widget.ImageView[2]",
									"]/android.widget.TableRow[3]/android.widget.ImageView[1]",
									"com.microware.cdfi:id/addAddress",
									"")) {
								appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
								af.pf(20,
										af.enterStringById(testSHG, "Address Line 1", "top",
												"com.microware.cdfi:id/et_address1", row, profileCons.add1ColNum, "020",
												"020", false, "Type here...", migr));
								af.pf(20, af.enterNumById(testSHG, "Pincode", "top", "com.microware.cdfi:id/et_pincode",
										row, profileCons.pinColNum, "220", "220", false, "Type here...", migr));
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

								if (af.invalidFlag())
									throw new Exception("");
								af.pseq(id, "020:Address");
							}

						} catch (Exception e) {
							af.resetInvalidFlag();
							af.fseq(id, "020:Address", e);
						}
						if (id != 000)
							break;
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

//							int k = 0;
//							while (k < 6) {
//								try {
//									Thread.sleep(2000);
//									appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//									appdriver.findElementById("com.microware.cdfi:id/addBank").click();
//									k = 6;
//								} catch (Exception e) {
//									System.out.println("Wasn't able to press the address button");
//									k++;
//								}
//							}
							if (af.checkMigrFields(testSHG, row, profileCons.bank,
									"//androidx.appcompat.widget.LinearLayoutCompat/androidx.recyclerview.widget.RecyclerView/androidx.appcompat.widget.LinearLayoutCompat",
									"//androidx.appcompat.widget.LinearLayoutCompat/androidx.recyclerview.widget.RecyclerView/androidx.appcompat.widget.LinearLayoutCompat[",
									"]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[7]/android.widget.ImageView[3]",
									"]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[7]/android.widget.ImageView[2]",
									"com.microware.cdfi:id/addBank",
									"]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[7]/android.widget.ImageView[1]")) {
								af.pf(21, af.enterStringById(testSHG,"IFSC Code", "top", "com.microware.cdfi:id/et_ifsc", row,
										profileCons.IFSCColNum, "121","121",false,"Type here...",migr));
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
								String acc="";
								Random objGenerator = new Random();
								acc=String.valueOf(objGenerator.nextInt(10000,99999))+String.valueOf(objGenerator.nextInt(100000,999999));
								System.out.println("Account: "+acc);
								try {								
									appdriver.findElementById("com.microware.cdfi:id/et_Accountno").sendKeys(acc);									
								}catch(Exception e) {
									System.out.println("Unable to enter Account Data. Possible Migrated Data.");
								}
								
								try {
									appdriver.findElementById("com.microware.cdfi:id/et_retype_Accountno").sendKeys(acc);									
								}catch(Exception e) {
									System.out.println("Unable to enter Retype Account Data. Possible Migrated Data.");
								}
//								af.pf(21, af.enterNumById(testSHG, 
//										"Re-Type Account No.", "", 
//										"com.microware.cdfi:id/et_retype_Accountno", 
//										row, profileCons.retypAccNoColNum, 
//										"521", "521", false, "Type here...", migr));
								
//								enterLongNum_Id("Re-Type Account No.", "top",
//										"com.microware.cdfi:id/et_retype_Accountno", row, profileCons.retypAccNoColNum,
//										"521:||Validation Error||", "#");
								af.pf(21, af.dateLogic(testSHG, 
										"Account opening date","top" ,
										row, profileCons.accOpDateColNum, 
										"com.microware.cdfi:id/et_opdate",
										"621", "621", false, "dd-mm-yyyy", migr));
//								String date = xc.getCellString(row, profileCons.accOpDateColNum);
//								dateLogic.datePicker(date, "com.microware.cdfi:id/et_opdate");
								appdriver.findElementById("com.microware.cdfi:id/ImgFrntpage").click();
								cameraLogic.click();
								int op=0;
								if(xc.getCellString(row, profileCons.isDefaultColNum).equals("Yes"))
									op=1;
								else if(xc.getCellString(row, profileCons.isDefaultColNum).equals("No"))
									op=2;
								af.pf(21, af.rdbtn(testSHG,
										"Is Default", "top",op, row, profileCons.isDefaultColNum, "721", false, migr, 1, 
										"//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[1]/android.widget.TextView", 
										"]/android.widget.TableRow[1]/android.widget.TextView", 
										"]/android.widget.TableRow[2]/android.widget.RadioGroup/android.widget.RadioButton"));
//								appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
//										+ xc.getCellString(row, profileCons.isDefaultColNum) + "\")")).click();
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
										String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg")
												.getText();
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
							}
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
							
							if(xc.getCellString(row, profileCons.president).contains("Delete")) {
								
									appdriver.findElementByXPath(
											"//android.widget.FrameLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
											.click();
									appdriver.findElementById("com.microware.cdfi:id/lay_status").click();
									appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Inactive\")")).click();
									appdriver.findElementById("com.microware.cdfi:id/btn_addDes").click();
								
							}else if(xc.getCellString(row, profileCons.president).contains("DeleteAdd")) {
								
									appdriver.findElementByXPath(
											"//android.widget.FrameLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
											.click();
									appdriver.findElementById("com.microware.cdfi:id/lay_status").click();
									appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Inactive\")")).click();
									appdriver.findElementById("com.microware.cdfi:id/btn_addDes").click();
									Thread.sleep(2000);
									appdriver.findElementByXPath(
											"//android.widget.FrameLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
											.click();
									appdriver.findElementById("com.microware.cdfi:id/spin_Member").click();
									appdriver.findElement(MobileBy.AndroidUIAutomator(
											"new UiSelector().text(\"" + xc.getCellString(row, profileCons.presColNum) + "\")"))
											.click();
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
							}else if(xc.getCellString(row, profileCons.president).contains("Edit")) {
							
									Thread.sleep(2000);
									appdriver.findElementByXPath(
											"//android.widget.FrameLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
											.click();
									appdriver.findElementById("com.microware.cdfi:id/spin_Member").click();
									appdriver.findElement(MobileBy.AndroidUIAutomator(
											"new UiSelector().text(\"" + xc.getCellString(row, profileCons.presColNum) + "\")"))
											.click();
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
							}else if(xc.getCellString(row, profileCons.president).contains("Check")) {
								try {
								}catch(Exception e) {
								}
							}else if(xc.getCellString(row, profileCons.president).contains("CheckAdd")) {
								try {
									}catch(Exception e) {
									}
							}else if(xc.getCellString(row, profileCons.president).contains("Do Not Add")){
							}else {
								appdriver.findElementByXPath(
										"//android.widget.FrameLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
										.click();
								appdriver.findElementById("com.microware.cdfi:id/spin_Member").click();
								appdriver.findElement(MobileBy.AndroidUIAutomator(
										"new UiSelector().text(\"" + xc.getCellString(row, profileCons.presColNum) + "\")"))
										.click();
//								appdriver.findElementById("com.microware.cdfi:id/et_fromDate")
//										.sendKeys(xc.getCellString(row, profileCons.presDateColNum));
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
							}
							
							
//							appdriver.findElementByXPath(
//									"//android.widget.FrameLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
//									.click();
//							appdriver.findElementById("com.microware.cdfi:id/spin_Member").click();
//							appdriver.findElement(MobileBy.AndroidUIAutomator(
//									"new UiSelector().text(\"" + xc.getCellString(row, profileCons.presColNum) + "\")"))
//									.click();
////							appdriver.findElementById("com.microware.cdfi:id/et_fromDate")
////									.sendKeys(xc.getCellString(row, profileCons.presDateColNum));
//							appdriver.findElementById("com.microware.cdfi:id/et_fromDate").click();
//							appdriver.findElementById("android:id/button1").click();
//							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
//									+ xc.getCellString(row, profileCons.presSigColNum) + "\")")).click();
//							appdriver.findElementById("com.microware.cdfi:id/btn_addDes").click();
//							sig++;
//							try {
//								if (du.isElementPresent("com.microware.cdfi:id/txt_msg", "id")) {
//									String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
//									testSHG.log(Status.FAIL, ex);
//									System.out.println("Error-->" + ex);
//									appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
//									if (neg_test_flag) {
//										try {
//											String exp_errs = xc.getCellString(row, profileCons.expErrMessColNum);
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
//									randomPressLogic.press(0.5, 0.05);
//									sig--;
//									throw new Exception("President Signatory not assigned");
//								}
//							} catch (Exception e) {
//								System.out.println(e.getMessage());
//							}

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
							
							if(xc.getCellString(row, profileCons.secretary).contains("Delete")) {
								try {
									appdriver.findElementByXPath(
											"//android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
											.click();
									appdriver.findElementById("com.microware.cdfi:id/lay_status").click();
									appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Inactive\")")).click();
									appdriver.findElementById("com.microware.cdfi:id/btn_addDes").click();
								}catch(Exception e) {
									System.out.println("secretary Deleted");
								}
							}else if(xc.getCellString(row, profileCons.secretary).contains("DeleteAdd")) {
								try {
									appdriver.findElementByXPath(
											"//android.widget.FrameLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
											.click();
									appdriver.findElementById("com.microware.cdfi:id/lay_status").click();
									appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Inactive\")")).click();
									appdriver.findElementById("com.microware.cdfi:id/btn_addDes").click();
									Thread.sleep(2000);
									}catch(Exception e) {
										System.out.println("secretary Deleted and Added");
									}
							
							}else if(xc.getCellString(row, profileCons.secretary).contains("Edit")) {
								try {
								}catch(Exception e) {
									System.out.println("secretary Edited");
								}
							}else if(xc.getCellString(row, profileCons.secretary).contains("Check")) {
								try {
								}catch(Exception e) {
								}
							}else if(xc.getCellString(row, profileCons.secretary).contains("CheckAdd")) {
								try {
									}catch(Exception e) {
									}
							}else if(xc.getCellString(row, profileCons.secretary).contains("Do Not Add")){
							}else {
								appdriver.findElementByXPath(
										"//android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
										.click();
								appdriver.findElementById("com.microware.cdfi:id/spin_Member").click();
								appdriver.findElement(MobileBy.AndroidUIAutomator(
										"new UiSelector().text(\"" + xc.getCellString(row, profileCons.secColNum) + "\")"))
										.click();
//								appdriver.findElementById("com.microware.cdfi:id/et_fromDate")
//										.sendKeys(xc.getCellString(row, profileCons.secDateColNum));
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
							
							if(xc.getCellString(row, profileCons.treasurer).contains("Delete")) {
								try {
									appdriver.findElementByXPath(
											"//android.widget.FrameLayout[3]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
											.click();
									appdriver.findElementById("com.microware.cdfi:id/lay_status").click();
									appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Inactive\")")).click();
									appdriver.findElementById("com.microware.cdfi:id/btn_addDes").click();
								}catch(Exception e) {
									System.out.println("treasurer Deleted");
								}
							}else if(xc.getCellString(row, profileCons.treasurer).contains("DeleteAdd")) {
								try {
									appdriver.findElementByXPath(
											"//android.widget.FrameLayout[3]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
											.click();
									appdriver.findElementById("com.microware.cdfi:id/lay_status").click();
									appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Inactive\")")).click();
									appdriver.findElementById("com.microware.cdfi:id/btn_addDes").click();
									Thread.sleep(2000);
									
									appdriver.findElementByXPath(
											"//android.widget.FrameLayout[3]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
											.click();
									appdriver.findElementById("com.microware.cdfi:id/spin_Member").click();
									appdriver.findElement(MobileBy.AndroidUIAutomator(
											"new UiSelector().text(\"" + xc.getCellString(row, profileCons.tresColNum) + "\")"))
											.click();
//									appdriver.findElementById("com.microware.cdfi:id/et_fromDate")
//											.sendKeys(xc.getCellString(row, profileCons.tresDateColNum));
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
									}catch(Exception e) {
										System.out.println("Tresure Deleted and Added");
									}
							
							}else if(xc.getCellString(row, profileCons.treasurer).contains("Edit")) {
								try {
								}catch(Exception e) {
									
								}
							}else if(xc.getCellString(row, profileCons.treasurer).contains("Check")) {
								try {
								}catch(Exception e) {
								}
							}else if(xc.getCellString(row, profileCons.treasurer).contains("CheckAdd")) {
								try {
									}catch(Exception e) {
									}
							}else if(xc.getCellString(row, profileCons.treasurer).contains("Do Not Add")){
							}else {
								appdriver.findElementByXPath(
										"//android.widget.FrameLayout[3]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TableRow[4]/android.widget.ImageView")
										.click();
								appdriver.findElementById("com.microware.cdfi:id/spin_Member").click();
								appdriver.findElement(MobileBy.AndroidUIAutomator(
										"new UiSelector().text(\"" + xc.getCellString(row, profileCons.tresColNum) + "\")"))
										.click();
//								appdriver.findElementById("com.microware.cdfi:id/et_fromDate")
//										.sendKeys(xc.getCellString(row, profileCons.tresDateColNum));
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

			int numMem = Integer.parseInt(xc.getCellString(row, profileCons.numMemAddColNum));

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
			String[] s = { "012", "017", "019", "020", "021", "022", "023", "24" };
			int[] val2 = shgProfileCreation.shg(row, s, false);
			pass += val2[0];
			fail += val2[1];
			count += val2[2];

		}

		if (neg_test_flag)
			pass = neg_test_count;
		
		

		int[] val = { pass, fail, count };
		val=af.pass_fail();
		val[0]+=pass;
		val[1]+=fail;
		val[2]+=count;
		return val;

	}

	public static void enterValue_Id(String title, String dir, String loc, int row, int cons, String err) {
		mt.scrollToText(title, dir, x, y);
		appdriver.findElementById(loc).sendKeys((int) xc.getCellDoubleValue(row, cons) + "");
		int f = validCheckString(loc, "id", (int) xc.getCellDoubleValue(row, cons) + "", err);
		if (f == 1)
			invalid_flag = true;
	}

	public static void enterValue_Xpath(String title, String dir, String loc, int row, int cons, String err) {
		mt.scrollToText(title, dir, x, y);
		appdriver.findElementByXPath(loc).sendKeys((int) xc.getCellDoubleValue(row, cons) + "");
		int f = validCheckString(loc, "xpath", (int) xc.getCellDoubleValue(row, cons) + "", err);
		if (f == 1)
			invalid_flag = true;
	}

	public static void enterDouble_Id(String title, String dir, String loc, int row, int cons, String err) {
		mt.scrollToText(title, dir, x, y);
		appdriver.findElementById(loc).sendKeys(xc.getCellDoubleValue(row, cons) + "");
		int f = validCheckString(loc, "id", xc.getCellDoubleValue(row, cons) + "", err);
		if (f == 1)
			invalid_flag = true;
	}

	public static void enterDouble_Xpath(String title, String dir, String loc, int row, int cons, String err) {
		mt.scrollToText(title, dir, x, y);
		appdriver.findElementByXPath(loc).sendKeys(xc.getCellDoubleValue(row, cons) + "");
		int f = validCheckString(loc, "xpath", xc.getCellDoubleValue(row, cons) + "", err);
		if (f == 1)
			invalid_flag = true;
	}

	public static void enterString_Id(String title, String dir, String loc, int row, int cons, String err) {
		mt.scrollToText(title, dir, x, y);
		appdriver.findElementById(loc).sendKeys(xc.getCellString(row, cons));
		int f = validCheckString(loc, "id", xc.getCellString(row, cons), err);
		if (f == 1)
			invalid_flag = true;
	}

	public static void enterString_Xpath(String title, String dir, String loc, int row, int cons, String err) {
		mt.scrollToText(title, dir, x, y);
		appdriver.findElementByXPath(loc).sendKeys(xc.getCellString(row, cons));
		int f = validCheckString(loc, "xpath", xc.getCellString(row, cons), err);
		if (f == 1)
			invalid_flag = true;
	}

	public static void selectById(String title, String dir, String loc, int row, int cons) {
		mt.scrollToText(title, dir, x, y);
		appdriver.findElementById(loc).click();
		appdriver
				.findElement(
						MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + xc.getCellString(row, cons) + "\")"))
				.click();
	}

	public static void selectByXpath(String title, String dir, String loc, int row, int cons) {
		mt.scrollToText(title, dir, x, y);
		appdriver.findElementByXPath(loc).click();
		appdriver
				.findElement(
						MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + xc.getCellString(row, cons) + "\")"))
				.click();
	}

	public static void enterLongNum_Id(String title, String dir, String loc, int row, int cons, String err,
			String prefix) {
		mt.scrollToText(title, dir, x, y);
		appdriver.findElementById(loc).sendKeys(xc.getCellString(row, cons).substring(1));
		int f = validCheckLongNum(loc, "id", xc.getCellString(row, cons), err, prefix);
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

	public static int validCheckLongNum(String loc, String locStrat, String field_txt, String text, String prefix) {
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
