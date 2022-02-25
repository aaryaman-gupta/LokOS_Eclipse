package app;

import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import lokos.lokosTest;
import util.dateLogic;

public class testing{
	@Test
	public static void test() {
		int[] a=new int[1];
		System.out.println(a[0]);
		if(a[0]==0) {
			System.out.println("true");
		}
		else {
			System.out.println("false");
		}
	}

//	@SuppressWarnings("unused")
//	public static int[] shg(int row) throws Exception {
//
//		xc.changeSheet("SHGs");
//
//		int count = 0;
//		int pass = 0;
//		int fail = 0;
//		int p = 0;
//		int f = 0;
//
//		System.out.println("Inside shg profile creation");
//		Thread.sleep(1000);
//		String[] idList = { "000" };
//		int id = 000;
//
//		String type = xc.getCellString(row, memCons.typeColNum);
//
//		if (!type.equals("New SHG")) {
//			String[] typeList = xc.getCellString(row, memCons.typeColNum).split(" ");
//			idList = typeList[0].split(",");
//		}
//
//		for (int iterations = 0; iterations < idList.length; iterations++) {
//			id = Integer.valueOf(idList[iterations]);
//			if (id <= 17 || id == 0) {
//
//				switch (id) {
//
//				case 000:
//
//				case 1:
//					try {
//						appdriver.findElementById("com.microware.cdfi:id/et_groupname")
//								.sendKeys(xc.getCellString(row, profileCons.shgNameColNum));
//						p = 1;
//						pass++;
//						System.out.println("001:SHG Name");
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in SHG Name:001----------------------Check Here////");
//						e.printStackTrace();
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//					}
//					if (id != 000)
//						break;
//				case 2:
//					try {
//						mt.scrollToText("Grampanchayat", "top");
//						appdriver.findElementById("com.microware.cdfi:id/spin_panchayat").click();
//						appdriver.findElement(MobileBy.AndroidUIAutomator(
//								"new UiSelector().text(\"" + xc.getCellString(row, profileCons.gpColNum) + "\")"))
//								.click();
//						p = 1;
//						pass++;
//						System.out.println("002:GP");
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in GP:002------------Check Here////");
//						e.printStackTrace();
//
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//
//				case 3:
//					try {
//						mt.scrollToText("Village", "top");
//						appdriver.findElementById("com.microware.cdfi:id/spin_village").click();
//						appdriver.findElement(MobileBy.AndroidUIAutomator(
//								"new UiSelector().text(\"" + xc.getCellString(row, profileCons.villageColNum) + "\")"))
//								.click();
//						p = 1;
//						pass++;
//						System.out.println("003:Village");
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in Village:002------------Check Here////");
//						e.printStackTrace();
//
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//
//				case 4:
//					try {
//						mt.scrollToText("Formation Date", "top");
//						String date = xc.getCellString(row, profileCons.dateColNum);
//						dateLogic.datePicker(date, "com.microware.cdfi:id/et_formationDate");
//						p = 1;
//						pass++;
//						System.out.println("004:Formation Date");
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in Formation Date:004------------Check Here////");
//						e.printStackTrace();
//
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//				case 5:
//					try {
//						mt.scrollToText("Promoted by", "top");
//
//						String promotedBy = xc.getCellString(row, profileCons.promotedByColNum);
//						appdriver.findElementById("com.microware.cdfi:id/spin_promotedby").click();
//						appdriver
//								.findElement(
//										MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + promotedBy + "\")"))
//								.click();
//
//						if (promotedBy.equals("NRLM")) {
//							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
//							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
//									.click();
//							if (ncr.equals("Revived")) {
//								mt.scrollToText("Revival Date", "top");
//								appdriver.findElementById("com.microware.cdfi:id/et_revivalDate")
//										.sendKeys(xc.getCellString(row, profileCons.revDateColNum));
//							}
//
//						} else if (promotedBy.equals("State Project")) {
//							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
//							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
//									.click();
//							if (ncr.equals("Revived")) {
//								mt.scrollToText("Revival Date", "top");
//								appdriver.findElementById("com.microware.cdfi:id/et_revivalDate")
//										.sendKeys(xc.getCellString(row, profileCons.revDateColNum));
//							} else if (ncr.equals("Co-opted")) {
//								mt.scrollToText("Co-option Date", "top");
//								appdriver.findElementById("com.microware.cdfi:id/et_coopt_Date")
//										.sendKeys(xc.getCellString(row, profileCons.cooptDateColNum));
//							}
//
//						} else if (promotedBy.equals("NGO")) {
//							appdriver.findElementById("com.microware.cdfi:id/spin_FundingAgency").click();
//							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
//									+ xc.getCellString(row, profileCons.promoterColNum) + "\")")).click();
//							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
//							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
//									.click();
//							if (ncr.equals("Revived")) {
//								mt.scrollToText("Revival Date", "top");
//								appdriver.findElementById("com.microware.cdfi:id/et_revivalDate")
//										.sendKeys(xc.getCellString(row, profileCons.revDateColNum));
//							} else if (ncr.equals("Co-opted")) {
//								mt.scrollToText("Co-option Date", "top");
//								appdriver.findElementById("com.microware.cdfi:id/et_coopt_Date")
//										.sendKeys(xc.getCellString(row, profileCons.cooptDateColNum));
//							}
//
//						} else if (promotedBy.equals("SGSY")) {
//							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
//							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
//									.click();
//							if (ncr.equals("Revived")) {
//								mt.scrollToText("Revival Date", "top");
//								appdriver.findElementById("com.microware.cdfi:id/et_revivalDate")
//										.sendKeys(xc.getCellString(row, profileCons.revDateColNum));
//							} else if (ncr.equals("Co-opted")) {
//								mt.scrollToText("Co-option Date", "top");
//								appdriver.findElementById("com.microware.cdfi:id/et_coopt_Date")
//										.sendKeys(xc.getCellString(row, profileCons.cooptDateColNum));
//							}
//
//						} else if (promotedBy.equals("Other")) {
//							appdriver.findElementById("com.microware.cdfi:id/et_promoter_name").click();
//							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
//									+ xc.getCellString(row, profileCons.promoterColNum) + "\")")).click();
//
//							String ncr = xc.getCellString(row, profileCons.New_Coopt_RevColNum);
//							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + ncr + "\")"))
//									.click();
//							if (ncr.equals("Revived")) {
//								mt.scrollToText("Revival Date", "top");
//								appdriver.findElementById("com.microware.cdfi:id/et_revivalDate")
//										.sendKeys(xc.getCellString(row, profileCons.revDateColNum));
//							} else if (ncr.equals("Co-opted")) {
//								mt.scrollToText("Co-option Date", "top");
//								appdriver.findElementById("com.microware.cdfi:id/et_coopt_Date")
//										.sendKeys(xc.getCellString(row, profileCons.cooptDateColNum));
//							}
//						}
//
//						p = 1;
//						pass++;
//						System.out.println("005:Promoted By and Sub Parts");
//
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in Promoted By or Sub Parts:005----------------Check Here////");
//						e.printStackTrace();
//
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//					}
//					if (id != 000)
//						break;
//				case 6:
//					try {
//						mt.scrollToText("SHG Type", "top");
//						String s = xc.getCellString(row, profileCons.Women_Special);
//						if (s.equals("Women(Regular)"))
//							appdriver
//									.findElement(
//											MobileBy.AndroidUIAutomator("new UiSelector().text(\"Women(Regular)\")"))
//									.click();
//						else {
//							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Special\""))
//									.click();
//							appdriver.findElementById("com.microware.cdfi:id/spin_tags").click();
//							appdriver.findElement(MobileBy.AndroidUIAutomator(
//									"new UiSelector().text(\"" + xc.getCellString(row, profileCons.tagsColNum) + "\""))
//									.click();
//							if (xc.getCellString(row, profileCons.tagsColNum).equals("OTHER")) {
//								mt.scrollToText("Other(specify)", "top");
//								appdriver.findElementById("com.microware.cdfi:id/et_OtherTags")
//										.sendKeys(xc.getCellString(row, profileCons.tagsOtherColNum));
//							}
//						}
//
//						p = 1;
//						pass++;
//						System.out.println("006:SHG Type");
//
//					} catch (Exception e) {
//						fail++;
//						f = 1;
//						System.out.println("Error in SHG Type:006-----Check Here////");
//						e.printStackTrace();
//
//					} finally {
//
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//				case 7:
//					try {
//						mt.scrollToText("Meeting Frequency", "top");
//						String mtg = xc.getCellString(row, profileCons.meetingFreqColNum);
//						appdriver.findElementById("com.microware.cdfi:id/spin_frequency").click();
//						appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + mtg + "\""))
//								.click();
//						if (mtg.equals("Weekly")) {
//							mt.scrollToText("Day", "top");
//							appdriver.findElementById("com.microware.cdfi:id/spin_weekday").click();
//							appdriver.findElement(MobileBy.AndroidUIAutomator(
//									"new UiSelector().text(\"" + xc.getCellString(row, profileCons.dayWColNum) + "\""))
//									.click();
//
//						} else if (mtg.equals("Fortnightly")) {
//							mt.scrollToText("Fortnight Week", "top");
//							appdriver.findElementById("com.microware.cdfi:id/spin_fortnight").click();
//							String s = xc.getCellString(row, profileCons.frtn8WeekFColNum);
//							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + s + "\""))
//									.click();
//							if (s.equals("Date")) {
//								mt.scrollToText("Date", "top");
//								appdriver.findElementById("com.microware.cdfi:id/spin_fortnightdate").click();
//								appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
//										+ xc.getCellString(row, profileCons.dateOtherFColNum) + "\"")).click();
//							} else {
//								mt.scrollToText("Day", "top");
//								appdriver.findElementById("com.microware.cdfi:id/spin_weekday").click();
//								appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
//										+ xc.getCellString(row, profileCons.day1_2FColNum) + "\"")).click();
//							}
//
//						} else if (mtg.equals("Monthly")) {
//							mt.scrollToText("Monthly Day", "top");
//							String s = xc.getCellString(row, profileCons.monthlydayMColNum);
//							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + s + "\""))
//									.click();
//							if (s.equals("Date")) {
//								mt.scrollToText("Date", "top");
//								appdriver.findElementById("com.microware.cdfi:id/spin_monthlydate").click();
//								appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
//										+ xc.getCellString(row, profileCons.dateMColNum) + "\"")).click();
//							} else {
//								mt.scrollToText("Day", "top");
//								appdriver.findElementById("com.microware.cdfi:id/spin_monthlydate").click();
//								appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
//										+ xc.getCellString(row, profileCons.dayMColNum) + "\"")).click();
//							}
//
//						}
//						p = 1;
//						pass++;
//						System.out.println("007:Meeting Frequency and Sub Parts");
//
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in Meeting Frequency:007-------Check Here////");
//						e.printStackTrace();
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//				case 8:
//					try {
//						mt.scrollToText("Compulsory Saving Frequency", "top");
//						appdriver.findElementById("com.microware.cdfi:id/spin_savingfrequency").click();
//						appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
//								+ xc.getCellString(row, profileCons.compSavFreqColNum) + "\"")).click();
//						p = 1;
//						pass++;
//						System.out.println("008:Compulsory Saving Frequency");
//
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in Compulsory Saving Frequency:008------------Check Here////");
//						e.printStackTrace();
//
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//				case 9:
//					try {
//						mt.scrollToText("Compulsory Saving Amount", "top");
//						appdriver.findElementById("com.microware.cdfi:id/et_saving")
//								.sendKeys(xc.getCellString(row, profileCons.compSavAmtColNum));
//						p = 1;
//						pass++;
//						System.out.println("009:Compulsory Saving Amount");
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in Compulsory Saving Amount:008------Check Here////");
//						e.printStackTrace();
//
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//				case 10:
//					try {
//						mt.scrollToVisibleElementOnScreen("com.microware.cdfi:id/tv_csroi", "id", "top");
//						appdriver.findElementById("com.microware.cdfi:id/et_ComsavingRoi")
//								.sendKeys(xc.getCellString(row, profileCons.compSavIRColNum));
//
//						p = 1;
//						pass++;
//						System.out.println("010:Compulsory Saving Interest Rate");
//
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in Compulsory Saving Interest Rate:010----Check Here////");
//						e.printStackTrace();
//
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//
//				case 11:
//					try {
//						mt.scrollToText("Voluntary Saving", "top");
//						appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
//								+ xc.getCellString(row, profileCons.voluntarySavColNum) + "\"")).click();
//						if (xc.getCellString(row, profileCons.voluntarySavColNum).equals("Yes"))
//							appdriver.findElementById("com.microware.cdfi:id/et_voluntarysavingROI")
//									.sendKeys(xc.getCellString(row, profileCons.voluntarySavIRColNum));
//						p = 1;
//						pass++;
//						System.out.println("011:Voluntary Savings and Sub-parts");
//
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in Savings and Sub-parts:011-------------Check Here////");
//						e.printStackTrace();
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//				case 12:
//					try {
//						mt.scrollToText("Yes-Internal", "top");
//						appdriver.findElement(MobileBy.AndroidUIAutomator(
//								"new UiSelector().text(\"" + xc.getCellString(row, profileCons.bkIdColNum) + "\")"))
//								.click();
//
//						if (xc.getCellString(row, profileCons.bkIdColNum).equals("Yes-Internal")) {
//							appdriver.findElement(MobileBy.AndroidUIAutomator(
//									"new UiSelector().text(\"No Member available with the phone no in this SHG\""))
//									.click();
//							appdriver.findElementById("android:id/search_src_text")
//									.sendKeys(xc.getCellString(row, profileCons.bkNameColNum));
//							appdriver.findElementByXPath(
//									"//android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[1]")
//									.click();
//							appdriver.findElementById("com.microware.cdfi:id/et_bookkeeper_s_mobile_no")
//									.sendKeys(xc.getCellString(row, profileCons.bkMobColNum));
//
//						} else if (xc.getCellString(row, profileCons.bkIdColNum).equals("Yes-External")) {
//							appdriver.findElementById("com.microware.cdfi:id/et_bookkeeper_name")
//									.sendKeys(xc.getCellString(row, profileCons.bkNameColNum));
//							appdriver.findElementById("com.microware.cdfi:id/et_bookkeeper_s_mobile_no")
//									.sendKeys(xc.getCellString(row, profileCons.bkMobColNum));
//						}
//
//						p = 1;
//						pass++;
//						System.out.println("012:Bookkeeper");
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in Bookkeeper:012-------------Check Here////");
//						e.printStackTrace();
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//				case 13:
//					try {
//						mt.scrollToText("Primary Livelihood Activity", "top");
//						appdriver.findElementById("com.microware.cdfi:id/spin_primaryActivity").click();
//						appdriver.findElement(MobileBy.AndroidUIAutomator(
//								"new UiSelector().text(\"" + xc.getCellString(row, profileCons.primaryla) + "\")"))
//								.click();
//						p = 1;
//						pass++;
//						System.out.println("016:Primary Livelihood");
//
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in Primary Livelihood:013-------------Check Here////");
//
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//				case 14:
//					try {
//						mt.scrollToText("Secondary Livelihood Activity", "top");
//						appdriver.findElementById("com.microware.cdfi:id/spin_secondaryActivity").click();
//						appdriver.findElement(MobileBy.AndroidUIAutomator(
//								"new UiSelector().text(\"" + xc.getCellString(row, profileCons.secondaryla) + "\")"))
//								.click();
//						p = 1;
//						pass++;
//						System.out.println("014:Secondary Livelihood");
//
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in Secondary Livelihood:014-------------Check Here////");
//
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//				case 15:
//					try {
//						mt.scrollToText("Tertiary Livelihood Activity", "top");
//						appdriver.findElementById("com.microware.cdfi:id/spin_tertiaryActivity").click();
//						appdriver.findElement(MobileBy.AndroidUIAutomator(
//								"new UiSelector().text(\"" + xc.getCellString(row, profileCons.tertiaryla) + "\")"))
//								.click();
//						p = 1;
//						pass++;
//						System.out.println("015:Tertiary Livelihood");
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in Tertiary Livelihood:015-------------Check Here////");
//
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//
//				case 16:
//					try {
//						mt.scrollToText("Tenure of elected Office Bearers", "top");
//						appdriver.findElementById("com.microware.cdfi:id/et_electiontenure")
//								.sendKeys(xc.getCellString(row, profileCons.tenureColNum));
//						p = 1;
//						pass++;
//						System.out.println("016:Tenure of elected Office Bearers");
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in Tenure of elected Office Bearers:016-------------Check Here////");
//
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//
//				case 17:
//					try {
//						mt.scrollToText("SHG resolution copy", "top");
//						appdriver.findElementById("com.microware.cdfi:id/Imgmember").click();
//						util.cameraLogic.click();
//						p = 1;
//						pass++;
//						System.out.println("017: SHG Resolution Copy Upload");
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in SHG Resolution Copy Upload:017-------------Check Here////");
//
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//					if (id != 000)
//						break;
//				case 18:
//
//					try {
//						if (!xc.getCellString(row, profileCons.typeColNum).equals("New SHG")) {
//							if (!xc.getCellString(row, memCons.typeColNum).equals("New")) {
//								mt.scrollToText("Status", "top");
//								appdriver.findElementById("com.microware.cdfi:id/spin_status").click();
//								appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
//										+ xc.getCellString(row, memCons.statusColNum) + "\")")).click();
//							}
//
//							p = 1;
//							pass++;
//							System.out.println("020:Status");
//						}
//						else
//							--count;
//
//					} catch (Exception e) {
//						f = 1;
//						fail++;
//						System.out.println("Error in Status:020-------------Check Here////");
//
//					} finally {
//						count++;
//						p = 0;
//						f = 0;
//
//					}
//
//					if (id != 000)
//						break;
//				default:
//					break;
//
//				}
//			}
//
//		}
//
//		appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
//		if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText().equals("Data saved successfully")
//				|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
//						.equals("Data Updated Successfully"))
//			appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
//		else {
//			System.out.println(appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText());
//			appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
//			System.out.println("Data not saved: Cannot Proceed(Correct the Errors first)");
//			id = 999;
//		}
//
////		if (id != 999) {
////			for (int iterations = 0; iterations < idList.length; iterations++) {
////				id = Integer.valueOf(idList[iterations]);
////				if (id > 18 || id == 0) {
////					switch (id) {
////					case 000:
////					case 21:
////						try {
////							appdriver.findElementById("com.microware.cdfi:id/IvPhone").click();
////							String[] phnos = xc.getCellString(row, memCons.phoneNosColNum).split(";");
////							for (int i = 0; i < phnos.length; i++) {
////								appdriver.findElementById("com.microware.cdfi:id/addphone").click();
////								String[] s = phnos[i].split(":");
////								appdriver.findElementById("com.microware.cdfi:id/et_phoneno").sendKeys(s[1].trim());
////								appdriver.findElementById("com.microware.cdfi:id/spin_ownership").click();
////								appdriver
////										.findElement(MobileBy
////												.AndroidUIAutomator("new UiSelector().text(\"" + s[0].trim() + "\")"))
////										.click();
////								appdriver.findElementById("com.microware.cdfi:id/btn_add").click();
////								if (!appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
////										.equals("Data Updated Successfully")
////										|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
////												.equals("Data saved successfully"))
////									appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
////								else {
////									String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
////									appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
////									throw new Exception(ex);
////								}
////								p = 1;
////								pass++;
////								System.out.println("021:Phone Number");
////							}
////						} catch (Exception e) {
////							f = 1;
////							fail++;
////							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
////							System.out.println("Error in Phone Number:021-------------Check Here////");
////							e.printStackTrace();
////
////						} finally {
////							count++;
////							p = 0;
////							f = 0;
////
////						}
////						if (id != 000)
////							break;
////
////					case 22:
////						try {
////							appdriver.findElementById("com.microware.cdfi:id/lay_location").click();
////							appdriver.findElementById("com.microware.cdfi:id/addAddress").click();
////							appdriver.findElementById("com.microware.cdfi:id/spin_addresstype").click();
////							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
////									+ xc.getCellString(row, memCons.addressTypeColNum) + "\")")).click();
////							appdriver.findElement(MobileBy.AndroidUIAutomator(
////									"new UiSelector().text(\"" + xc.getCellString(row, memCons.addLocColNum) + "\")"))
////									.click();
////							mt.scrollToText("Address Line 1", "top");
////							appdriver.findElementById("com.microware.cdfi:id/et_address1")
////									.sendKeys(xc.getCellString(row, memCons.add1ColNum));
////							mt.scrollToText("Address Line 2", "top");
////							appdriver.findElementById("com.microware.cdfi:id/et_address2")
////									.sendKeys(xc.getCellString(row, memCons.add2ColNum));
////							appdriver.findElementById("com.microware.cdfi:id/et_pincode")
////									.sendKeys(xc.getCellDoubleValue(row, memCons.pinColNum) + "");
////							appdriver.findElementById("com.microware.cdfi:id/btn_add").click();
////							if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
////									.equals("Data Updated Successfully")
////									|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
////											.equals("Data saved successfully"))
////								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
////							else {
////								String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
////								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
////								throw new Exception(ex);
////							}
////							p = 1;
////							pass++;
////							System.out.println("022:Address");
////						} catch (Exception e) {
////							f = 1;
////							fail++;
////							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
////							System.out.println("Error in Address:022-------------Check Here////");
////							e.printStackTrace();
////
////						} finally {
////							count++;
////							p = 0;
////							f = 0;
////
////						}
////						if (id != 000)
////							break;
////					case 23:
////						try {
////							appdriver.findElementById("com.microware.cdfi:id/IvBank").click();
////							appdriver.findElementById("com.microware.cdfi:id/addBank").click();
////							appdriver.findElementById("com.microware.cdfi:id/et_nameinbankpassbook")
////									.sendKeys(xc.getCellString(row, memCons.passNameColNum));
////							appdriver.findElementById("com.microware.cdfi:id/et_ifsc")
////									.sendKeys(xc.getCellString(row, memCons.IFSCColNum));
////							appdriver.findElementById("com.microware.cdfi:id/Imgsearch").click();
////							appdriver.findElementById("com.microware.cdfi:id/lay_bankName").click();
////							appdriver.findElementById("android:id/search_src_text")
////									.sendKeys(xc.getCellString(row, memCons.bankNameColNum));
////							appdriver.findElementById("com.microware.cdfi:id/tvmyspinner").click();
//////				mt.scrollToText("Bank Branch", "top");
//////				appdriver.findElementById("com.microware.cdfi:id/lay_branch").click();
//////				appdriver.findElementById("android:id/search_src_text")
//////						.sendKeys(xc.getCellString(row, memCons.bankBranchColNum));
//////				appdriver.findElementByXPath("//android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[1]").click();
////
////							mt.scrollToText("Account number", "top");
////							appdriver.findElementById("com.microware.cdfi:id/et_Accountno")
////									.sendKeys(xc.getCellString(row, memCons.accNoColNum).substring(1));
//////							appdriver.findElementById("com.microware.cdfi:id/et_opdate").click();
////							String date = xc.getCellString(row, memCons.accOpDateColNum);
////							dateLogic.datePicker(date, "com.microware.cdfi:id/et_opdate");
////							appdriver.findElementById("com.microware.cdfi:id/ImgFrntpage").click();
////							appdriver.findElementById("com.sec.android.app.camera:id/normal_center_button").click();
////							Thread.sleep(3000);
////							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"OK\")")).click();
////							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
////									+ xc.getCellString(row, memCons.isDefaultColNum) + "\")")).click();
////							appdriver.findElementById("com.microware.cdfi:id/btn_add").click();
////							if (du.isElementPresent("new UiSelector().text(\"Please enter valid Account No.\")",
////									"androidUIAutomatior")) {
////								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
////								throw new Exception("Invalid Account Number");
////							}
////							if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
////									.equals("Data Updated Successfully")
////									|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
////											.equals("Data saved successfully"))
////								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
////							else {
////								String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
////								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
////								throw new Exception(ex);
////							}
////							p = 1;
////							pass++;
////							System.out.println("023:Bank Account");
////
////						} catch (Exception e) {
////							f = 1;
////							fail++;
////							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
////							System.out.println("Error in Bank Account:023-------------Check Here////");
////							e.printStackTrace();
////
////						} finally {
////							count++;
////							p = 0;
////							f = 0;
////
////						}
////						if (id != 000)
////							break;
////					case 24:
////						try {
////							appdriver.findElementById("com.microware.cdfi:id/lay_kyc").click();
////							appdriver.findElementById("com.microware.cdfi:id/addKyc").click();
////							appdriver.findElementById("com.microware.cdfi:id/spin_kyctype").click();
////							appdriver.findElement(MobileBy.AndroidUIAutomator(
////									"new UiSelector().text(\"" + xc.getCellString(row, memCons.docTypeColNum) + "\")"))
////									.click();
////							appdriver.findElementById("com.microware.cdfi:id/et_kycno")
////									.sendKeys(xc.getCellString(row, memCons.docIDColNum).substring(1));
////							appdriver.findElementById("com.microware.cdfi:id/IvFrntUpload").click();
////							appdriver.findElementById("com.sec.android.app.camera:id/normal_center_button").click();
////							Thread.sleep(3000);
////							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"OK\")")).click();
////							appdriver.findElementById("com.microware.cdfi:id/IvrearUpload").click();
////							appdriver.findElementById("com.sec.android.app.camera:id/normal_center_button").click();
////							Thread.sleep(3000);
////							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"OK\")")).click();
////
////							appdriver.findElementById("com.microware.cdfi:id/btn_kyc").click();
////							if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
////									.equals("Data Updated Successfully")
////									|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
////											.equals("Data saved successfully"))
////								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
////							else {
////								String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
////								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
////								throw new Exception(ex);
////							}
////							p = 1;
////							pass++;
////							System.out.println("024:KYC");
////						} catch (Exception e) {
////							f = 1;
////							fail++;
////							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
////							System.out.println("Error in KYC:024-------------Check Here////");
////							e.printStackTrace();
////						} finally {
////							count++;
////							p = 0;
////							f = 0;
////
////						}
////						if (id != 000)
////							break;
////					case 25:
////						try {
////							throw new Exception("Document cannot be updated before submission");
//////				appdriver.findElementById("com.microware.cdfi:id/lay_systemTag").click();
//////				appdriver.findElementById("com.microware.cdfi:id/addSystemTag").click();
//////				appdriver.findElementById("com.microware.cdfi:id/spin_Membersystem").click();
//////				appdriver
//////						.findElement(MobileBy.AndroidUIAutomator(
//////								"new UiSelector().text(\"" + xc.getCellString(row, memCons.docTypeColNum) + "\")"))
//////						.click();
//////				appdriver.findElementById("com.microware.cdfi:id/et_id")
//////						.sendKeys(xc.getCellString(row, memCons.idColNum).substring(1));
//////				appdriver.findElementById("com.microware.cdfi:id/btn_add").click();
//////				appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
//////				p=1;
//////				pass++;
//////				System.out.println("025:Other ID");
////
////						} catch (Exception e) {
////							f = 1;
////							fail++;
//////							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
////							System.out.println("Error in ID:025-------------Check Here////");
////							e.printStackTrace();
////						} finally {
////							count++;
////							p = 0;
////							f = 0;
////
////						}
////						if (id != 000)
////							break;
////					case 26:
////						try {
////							appdriver.findElementById("com.microware.cdfi:id/lay_Cader").click();
////							appdriver.findElementById("com.microware.cdfi:id/addCader").click();
////							appdriver.findElementById("com.microware.cdfi:id/spin_category").click();
////							appdriver.findElement(MobileBy.AndroidUIAutomator(
////									"new UiSelector().text(\"" + xc.getCellString(row, memCons.catColNum) + "\")"))
////									.click();
////							appdriver.findElementById("com.microware.cdfi:id/spin_role").click();
////							appdriver.findElement(MobileBy.AndroidUIAutomator(
////									"new UiSelector().text(\"" + xc.getCellString(row, memCons.rolesColNum) + "\")"))
////									.click();
////
//////							appdriver.findElementById("com.microware.cdfi:id/et_joiningDate").click();
////							String date = xc.getCellString(row, memCons.joinDateColNum);
////							dateLogic.datePicker(date, "com.microware.cdfi:id/et_joiningDate");
////
//////							appdriver.findElementById("com.microware.cdfi:id/et_leavingDate").click();
////							date = xc.getCellString(row, memCons.leaveDateColNum);
////							dateLogic.datePicker(date, "com.microware.cdfi:id/et_leavingDate");
////
////							appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
////							if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
////									.equals("Data Updated Successfully")
////									|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
////											.equals("Data saved successfully"))
////								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
////							else {
////								String ex = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
////								appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
////								throw new Exception(ex);
////							}
////							p = 1;
////							pass++;
////							System.out.println("026:Cadre");
////
////						} catch (Exception e) {
////							f = 1;
////							fail++;
////							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
////							System.out.println("Error in Cadre:026----------------Check Here////");
////							e.printStackTrace();
////						} finally {
////							count++;
////							p = 0;
////							f = 0;
////
////						}
////						if (id != 000)
////							break;
////					default:
////						break;
////
////					}
////
////				}
////			}
////		}
//
//		
//		Thread.sleep(1000);
//		appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
//		Thread.sleep(1000);
//		appdriver.findElementById("com.microware.cdfi:id/icBack").click();
//
//		int[] val = { pass, fail, count };
//		return val;
//
//	}

}
