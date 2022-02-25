package app;

import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import lokos.lokosTest;
import util.cameraLogic;
import util.dateLogic;

public class memberProfile extends lokosTest {

	public static int[] idSelect_Mem(int row) throws Exception {

		String[] idList = { "000" };

		String type = xc.getCellString(row, memCons.typeColNum);

		if (!type.equalsIgnoreCase("New")) {
			String[] typeList = xc.getCellString(row, memCons.typeColNum).split(" ");
			idList = typeList[0].split(",");
		}

		int[] val = member(row, idList);
		return val;

	}

	@SuppressWarnings("unused")
	public static int[] member(int row, String[] idList) throws Exception {

		xc.changeSheet("Members");
		if (testMem == null)
			testMem = reports.createTest("Member: " + xc.getCellString(row, memCons.nameColNum) + "("
					+ xc.getCellString(row, memCons.typeColNum) + ")");
		System.out.println("Member name: " + xc.getCellString(row, memCons.nameColNum));
		System.out.println("Inside member profile creation");
		int count = 0;
		int pass = 0;
		int fail = 0;
		int p = 0;
		int f = 0;
		int id = 000;
		int t = 0;// flag for back button
		boolean neg_test_flag = false;
		int neg_test_count = 0;

		try {
			if (xc.getCellString(row, memCons.typeColNum).contains("Check")) {
				neg_test_flag = true;
			}
		} catch (NullPointerException np) {
		}

		for (int iterations = 0; iterations < idList.length; iterations++) {
			id = Integer.valueOf(idList[iterations]);

			switch (id) {

			case 000:

			case 001:
				try {
					appdriver.findElementById("com.microware.cdfi:id/et_name")
							.sendKeys(xc.getCellString(row, memCons.nameColNum));
					p = 1;
					pass++;
					System.out.println("001:Name");
				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Name:001----------------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					p = 0;
					f = 0;
				}
				if (id != 000)
					break;
			case 002:
				try {
					appdriver.findElementById("com.microware.cdfi:id/et_namelocal")
							.sendKeys(xc.getCellString(row, memCons.nameLocalColNum));
					p = 1;
					pass++;
					System.out.println("002:Name in Local");
				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Name in Local:002------------Check Here////");
					e.printStackTrace();

				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 003:
				try {
					mt.scrollToText("Gender", "top");
					appdriver.findElementById("com.microware.cdfi:id/spin_gender").click();
					appdriver
							.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, memCons.genderColNum) + "\")"))
							.click();
					p = 1;
					pass++;
					System.out.println("003:Gender");

				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Gender:003----------------Check Here////");
					e.printStackTrace();

				} finally {
					count++;
					p = 0;
					f = 0;
				}
				if (id != 000)
					break;
			case 004:
				try {
					mt.scrollToText("Is Member Date of birth available?", "top");
					appdriver.findElement(MobileBy.AndroidUIAutomator(
							"new UiSelector().text(\"" + xc.getCellString(row, memCons.memDOBAvlColNum) + "\")"))
							.click();

					System.out.print("004:Member DOB Available and ");

					if (xc.getCellString(row, memCons.memDOBAvlColNum).equals("Yes")) {
						mt.scrollToText("Date of Birth", "top");
//							appdriver.findElementById("com.microware.cdfi:id/et_dob").click();
						String date = xc.getCellString(row, memCons.DOBColNum);
						dateLogic.datePicker(date, "com.microware.cdfi:id/et_dob");

					} else {
						mt.scrollToText("Age", "top");
						appdriver.findElementById("com.microware.cdfi:id/et_age")
								.sendKeys((int) xc.getCellDoubleValue(row, memCons.ageColNum) + "");

						mt.scrollToText("As on Date", "top");
//							appdriver.findElementById("com.microware.cdfi:id/et_ason").click();
						String date = xc.getCellString(row, memCons.ageAODColNum);
						dateLogic.datePicker(date, "com.microware.cdfi:id/et_ason");
					}
					p = 1;
					pass++;
					System.out.println(" > Sub Parts Details filled");

				} catch (Exception e) {
					fail++;
					f = 1;
					System.out.println("Error in Member DOB Available or Sub Parts:004-----Check Here////");
					e.printStackTrace();

				} finally {

					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 005:
				try {
					mt.scrollToText("Member Joining Date", "top");
//						appdriver.findElementById("com.microware.cdfi:id/et_joiningDate").click();
					String date = xc.getCellString(row, memCons.dateColNum);
					appdriver.findElementById("com.microware.cdfi:id/et_joiningDate").clear();
					dateLogic.datePicker(date, "com.microware.cdfi:id/et_joiningDate");
					p = 1;
					pass++;
					System.out.println("005:Member Joining Date");

				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Joining Date:005-------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 006:
				try {
					mt.scrollToText("Marital Status", "top");
					appdriver.findElementById("com.microware.cdfi:id/spin_martial").click();
					appdriver.findElement(MobileBy.AndroidUIAutomator(
							"new UiSelector().text(\"" + xc.getCellString(row, memCons.maritalStatColNum) + "\")"))
							.click();
					p = 1;
					pass++;
					System.out.println("006:Marital Status");

				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Marital Stat:006------------Check Here////");
					e.printStackTrace();

				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 007:
				try {
					mt.scrollToText("Mother / Father / Spouse", "top");
					appdriver.findElementById("com.microware.cdfi:id/et_mother_father")
							.sendKeys(xc.getCellString(row, memCons.M_F_SColNum));
					p = 1;
					pass++;
					System.out.println("007:Mother/Father/Spouse");
				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Mother/Father/Spouse:007------Check Here////");
					e.printStackTrace();

				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 8:
				try {
					mt.scrollToText("Mother/Father/Spouse in local", "top");
					appdriver.findElementById("com.microware.cdfi:id/et_mother_fatherlocal")
							.sendKeys(xc.getCellString(row, memCons.M_F_SLocalColNum));
					p = 1;
					pass++;
					System.out.println("008:Mother/Father/Spouse in Local");

				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Mother/Father/Spouse in local:008----Check Here////");
					e.printStackTrace();

				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;

			case 9:
				try {
					mt.scrollToText("Relation", "top");
					appdriver.findElementById("com.microware.cdfi:id/spin_relationmother").click();
					appdriver
							.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, memCons.relationColNum) + "\")"))
							.click();
					p = 1;
					pass++;
					System.out.println("009:Relation");

				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Relation:009-------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 10:
				try {
					mt.scrollToText("Is Member Head of the family?", "top");
					appdriver.findElement(MobileBy.AndroidUIAutomator(
							"new UiSelector().text(\"" + xc.getCellString(row, memCons.familyHeadColNum) + "\")"))
							.click();
					p = 1;
					pass++;
					System.out.println("010:Is member head of the family?");

				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Is member head of the family?:010-------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					p = 0;
					f = 0;
				}
				if (id != 000)
					break;
			case 11:
				try {
					mt.scrollToText("No disability", "top");

					appdriver.findElement(MobileBy.AndroidUIAutomator(
							"new UiSelector().text(\"" + xc.getCellString(row, memCons.disabilityColNum) + "\")"))
							.click();

					if (!xc.getCellString(row, memCons.disabilityColNum).equals("No disability")) {

						mt.scrollToText("Disability Type", "top");
						appdriver.findElementById("com.microware.cdfi:id/spin_disabilityType").click();
						appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
								+ xc.getCellString(row, memCons.disabilityTypeColNum) + "\")")).click();
						if (du.isElementPresent("new UiSelector().text(\"Guardian Name/ Care Taker\")",
								"androidUIAutomatior")) {
							mt.scrollToText("Guardian Name/ Care Taker", "top");
							appdriver.findElementById("com.microware.cdfi:id/et_guardianName")
									.sendKeys(xc.getCellString(row, memCons.guardianNameColNum));
							mt.scrollToText("Guardian Name/ Care Taker in local", "top");
							appdriver.findElementById("com.microware.cdfi:id/et_guardianNamelocal")
									.sendKeys(xc.getCellString(row, memCons.guardianNameLocalColNum));
							mt.scrollToText("Relation with Guardian", "top");
							appdriver.findElementById("com.microware.cdfi:id/spin_relationGuardian").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
									+ xc.getCellString(row, memCons.relationGuardianColNum) + "\")")).click();
						}
						p = 1;
						pass++;
						System.out.println("011:Disability and Sub Parts");
					}

				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Disability and Sub Parts:011-------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 12:
				try {
					mt.scrollToText("Post/Designation", "top");
					if (!xc.getCellString(row, memCons.postColNum).equals("Null")) {
						appdriver.findElementById("com.microware.cdfi:id/spin_Post").click();
						appdriver
								.findElement(MobileBy.AndroidUIAutomator(
										"new UiSelector().text(\"" + xc.getCellString(row, memCons.postColNum) + "\")"))
								.click();
					}
					p = 1;
					pass++;
					System.out.println("012:Post/Designation");
				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Post/Designation:012-------------Check Here////");
					e.printStackTrace();
				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 13:
				try {
					mt.scrollToText("Social Category", "top");
					appdriver.findElementById("com.microware.cdfi:id/spin_socialCategory").click();
					appdriver.findElement(MobileBy.AndroidUIAutomator(
							"new UiSelector().text(\"" + xc.getCellString(row, memCons.socialCatColNum) + "\")"))
							.click();
					p = 1;
					pass++;
					System.out.println("013:Social Category");

				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Social Category:013-------------Check Here////");

				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 14:
				try {
					mt.scrollToText("Religion", "top");
					appdriver.findElementById("com.microware.cdfi:id/spin_religion").click();
					appdriver
							.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, memCons.religionColNum) + "\")"))
							.click();
					p = 1;
					pass++;
					System.out.println("014:Religion");

				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Religion:014-------------Check Here////");

				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 15:
				try {
					mt.scrollToText("Highest Education Level", "top");
					appdriver.findElementById("com.microware.cdfi:id/spin_education").click();
					appdriver.findElement(MobileBy.AndroidUIAutomator(
							"new UiSelector().text(\"" + xc.getCellString(row, memCons.highEduLvlColNum) + "\")"))
							.click();
					p = 1;
					pass++;
					System.out.println("015:Highest Education Level");
				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Highest Education Level:015-------------Check Here////");
				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 51:
				try {
					mt.scrollToText("Insurance", "top");
					appdriver.findElement(MobileBy.AndroidUIAutomator(
							"new UiSelector().text(\"" + xc.getCellString(row, memCons.insuranceColNum) + "\")"))
							.click();
					p = 1;
					pass++;
					System.out.println("051:Insurance");
					testMem.log(Status.PASS, "051:Insurance");
				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Insurance:051-------------Check Here////");
					testMem.log(Status.FAIL, "051:Error in Insurance");
				} finally {
					count++;
					p = 0;
					f = 0;
				}
				if (id != 000)
					break;
			case 16:
				try {
					mt.scrollToText("Primary Livelihood", "top");
					appdriver.findElementById("com.microware.cdfi:id/spin_occupation").click();
					appdriver
							.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, memCons.primaryla) + "\")"))
							.click();
					p = 1;
					pass++;
					System.out.println("016:Primary Livelihood");

				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Primary Livelihood:016-------------Check Here////");

				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 17:
				try {
					mt.scrollToText("Secondary Livelihood", "top");
					appdriver.findElementById("com.microware.cdfi:id/spin_secondaryoccu").click();
					appdriver
							.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, memCons.secondaryla) + "\")"))
							.click();
					p = 1;
					pass++;
					System.out.println("017:Secondary Livelihood");

				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Secondary Livelihood:017-------------Check Here////");

				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 18:
				try {
					mt.scrollToText("Tertiary Livelihood", "top");
					appdriver.findElementById("com.microware.cdfi:id/spin_tertiaryoccu").click();
					appdriver
							.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, memCons.tertiaryla) + "\")"))
							.click();
					p = 1;
					pass++;
					System.out.println("016:Tertiary Livelihood");
				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Tertiary Livelihood:018-------------Check Here////");

				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 19:
				try {
					mt.scrollToText("Member Image", "top");
					appdriver.findElementById("com.microware.cdfi:id/Imgmember").click();
					cameraLogic.click();
					p = 1;
					pass++;
					System.out.println("019:Image Upload");
				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Image Upload:019-------------Check Here////");

				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 050:
				try {
					mt.scrollToText("Consent Form Image", "top");
					appdriver.findElementById("com.microware.cdfi:id/ImgmemberConsent").click();
					cameraLogic.click();
					p = 1;
					pass++;
					System.out.println("050:Consent Form Image Upload");
				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Consent Form Image Upload:050-------------Check Here////");

				} finally {
					count++;
					p = 0;
					f = 0;

				}
				if (id != 000)
					break;
			case 20:

				try {
					if (!xc.getCellString(row, memCons.typeColNum).equalsIgnoreCase("New")) {
						mt.scrollToText("Status", "top");
						appdriver.findElementById("com.microware.cdfi:id/spin_status").click();
						appdriver.findElement(MobileBy.AndroidUIAutomator(
								"new UiSelector().text(\"" + xc.getCellString(row, memCons.statusColNum) + "\")"))
								.click();
					}
					p = 1;
					pass++;
					System.out.println("020:Status");

				} catch (Exception e) {
					f = 1;
					fail++;
					System.out.println("Error in Status:020-------------Check Here////");

				} finally {
					count++;
					p = 0;
					f = 0;

				}

				if (id != 000)
					break;
			default:
				break;

			}
			if (neg_test_flag) {

				appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
				if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
						.equals("Data saved successfully")
						|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
								.equals("Data Updated Successfully")) {
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					System.out.println("Necessary fields for saving SHG filled.");
				} else {
					String err = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
					System.out.println("Error---->" + err);
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					System.out.println("Error---->Data not saved: Cannot Proceed(Resume next text case)");
					try {
						String exp_errs = xc.getCellString(row, memCons.expErrMessColNum);
						if (err.contains(exp_errs)) {
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

					util.randomPressLogic.press(0.5, 0.05);
					mt.scrollToVisibleElementOnScreen("com.microware.cdfi:id/IvVector", "id", "bottom");
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
				System.out.println("Necessary fields for saving SHG filled.");
			} else {
				System.out.println("Error---->" + appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText());
				appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
				System.out.println("Error---->Atleast one field is incorrect");
				System.out.println("Error---->Data not saved: Cannot Proceed(Correct the Errors first)");
				util.randomPressLogic.press(0.5, 0.05);
				appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
				appdriver.findElementById("com.microware.cdfi:id/btn_yes").click();
				fail++;
				t = 1;
				id = 9999;
			}
		}

		if (id != 9999) {
			for (int iterations = 0; iterations < idList.length; iterations++) {
				id = Integer.valueOf(idList[iterations]);
				if (id > 20 || id == 0) {
					switch (id) {
					case 000:
					case 21:
						try {
							appdriver.findElementById("com.microware.cdfi:id/IvPhone").click();
							String[] phnos = xc.getCellString(row, memCons.phoneNosColNum).split(";");
							for (int i = 0; i < phnos.length; i++) {
								appdriver.findElementById("com.microware.cdfi:id/addphone").click();
								String[] s = phnos[i].split(":");
								appdriver.findElementById("com.microware.cdfi:id/et_phoneno").sendKeys(s[1].trim());
								appdriver.findElementById("com.microware.cdfi:id/spin_ownership").click();
								appdriver
										.findElement(MobileBy
												.AndroidUIAutomator("new UiSelector().text(\"" + s[0].trim() + "\")"))
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
								p = 1;
								pass++;
								System.out.println("021:Phone Number");
							}
						} catch (Exception e) {
							f = 1;
							fail++;
							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
							System.out.println("Error in Phone Number:021-------------Check Here////");
							e.printStackTrace();

						} finally {
							count++;
							p = 0;
							f = 0;

						}
						if (id != 000)
							break;

					case 22:
						try {
							Thread.sleep(1000);
							appdriver.findElementById("com.microware.cdfi:id/Ivloc").click();
							Thread.sleep(1000);
							appdriver.findElementById("com.microware.cdfi:id/addAddress").click();
							appdriver.findElementById("com.microware.cdfi:id/spin_addresstype").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
									+ xc.getCellString(row, memCons.addressTypeColNum) + "\")")).click();
							appdriver.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, memCons.addLocColNum) + "\")"))
									.click();
							mt.scrollToText("Address Line 1", "top");
							appdriver.findElementById("com.microware.cdfi:id/et_address1")
									.sendKeys(xc.getCellString(row, memCons.add1ColNum));
							mt.scrollToText("Address Line 2", "top");
							appdriver.findElementById("com.microware.cdfi:id/et_address2")
									.sendKeys(xc.getCellString(row, memCons.add2ColNum));
							appdriver.findElementById("com.microware.cdfi:id/et_pincode")
									.sendKeys(xc.getCellDoubleValue(row, memCons.pinColNum) + "");
							appdriver.findElementById("com.microware.cdfi:id/btn_add").click();
							if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
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
							p = 1;
							pass++;
							System.out.println("022:Address");
						} catch (Exception e) {
							f = 1;
							fail++;
							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
							System.out.println("Error in Address:022-------------Check Here////");
							e.printStackTrace();

						} finally {
							count++;
							p = 0;
							f = 0;

						}
						if (id != 000)
							break;
					case 23:
						try {
							Thread.sleep(1000);
							appdriver.findElementById("com.microware.cdfi:id/IvBank").click();
							appdriver.findElementById("com.microware.cdfi:id/addBank").click();
							appdriver.findElementById("com.microware.cdfi:id/et_nameinbankpassbook")
									.sendKeys(xc.getCellString(row, memCons.passNameColNum));
							appdriver.findElementById("com.microware.cdfi:id/et_ifsc")
									.sendKeys(xc.getCellString(row, memCons.IFSCColNum));
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
										.sendKeys(xc.getCellString(row, memCons.bankNameColNum));
								appdriver.findElementById("com.microware.cdfi:id/tvmyspinner").click();
//								mt.scrollToText("Bank Branch", "top");
//								appdriver.findElementById("com.microware.cdfi:id/lay_branch").click();
//								appdriver.findElementById("android:id/search_src_text")
//									.sendKeys(xc.getCellString(row, memCons.bankBranchColNum));
//								appdriver.findElementByXPath("//android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[2]").click();
							} catch (Exception e) {

							}
							mt.scrollToText("Account number", "top");
							appdriver.findElementById("com.microware.cdfi:id/et_Accountno")
									.sendKeys(xc.getCellString(row, memCons.accNoColNum).substring(1));
							mt.scrollToText("Re-Type Account No.", "top");
							appdriver.findElementById("com.microware.cdfi:id/et_retype_Accountno")
									.sendKeys(xc.getCellString(row, memCons.retypAccNoColNum).substring(1));
							String date = xc.getCellString(row, memCons.accOpDateColNum);
							dateLogic.datePicker(date, "com.microware.cdfi:id/et_opdate");
							appdriver.findElementById("com.microware.cdfi:id/ImgFrntpage").click();
							cameraLogic.click();
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
									+ xc.getCellString(row, memCons.isDefaultColNum) + "\")")).click();
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

							p = 1;
							pass++;
							System.out.println("023:Bank Account");

						} catch (Exception e) {
							f = 1;
							fail++;
							if (!xc.getCellString(row, memCons.typeColNum).equals("New")) {
								appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
							}
							System.out.println("Error in Bank Account:023-------------Check Here////");
							e.printStackTrace();

						} finally {
							count++;
							p = 0;
							f = 0;
							Thread.sleep(3000);
						}
						if (id != 000)
							break;
					case 24:
						try {
							Thread.sleep(1000);
							appdriver.findElementById("com.microware.cdfi:id/lay_kyc").click();
							appdriver.findElementById("com.microware.cdfi:id/addKyc").click();
							appdriver.findElementById("com.microware.cdfi:id/spin_kyctype").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, memCons.docTypeColNum) + "\")"))
									.click();
							appdriver.findElementById("com.microware.cdfi:id/et_kycno")
									.sendKeys(xc.getCellString(row, memCons.docIDColNum).substring(1));
							appdriver.findElementById("com.microware.cdfi:id/IvFrntUpload").click();
							cameraLogic.click();
							appdriver.findElementById("com.microware.cdfi:id/IvrearUpload").click();
							cameraLogic.click();
							appdriver.findElementById("com.microware.cdfi:id/btn_kyc").click();
							if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
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
							p = 1;
							pass++;
							System.out.println("024:KYC");
						} catch (Exception e) {
							f = 1;
							fail++;
							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
							System.out.println("Error in KYC:024-------------Check Here////");
							e.printStackTrace();
						} finally {
							count++;
							p = 0;
							f = 0;

						}
						if (id != 000)
							break;
					case 25:
						try {
							throw new Exception("Document cannot be updated before submission");
//				appdriver.findElementById("com.microware.cdfi:id/lay_systemTag").click();
//				appdriver.findElementById("com.microware.cdfi:id/addSystemTag").click();
//				appdriver.findElementById("com.microware.cdfi:id/spin_Membersystem").click();
//				appdriver
//						.findElement(MobileBy.AndroidUIAutomator(
//								"new UiSelector().text(\"" + xc.getCellString(row, memCons.docTypeColNum) + "\")"))
//						.click();
//				appdriver.findElementById("com.microware.cdfi:id/et_id")
//						.sendKeys(xc.getCellString(row, memCons.idColNum).substring(1));
//				appdriver.findElementById("com.microware.cdfi:id/btn_add").click();
//				appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
//				p=1;
//				pass++;
//				System.out.println("025:Other ID");

						} catch (Exception e) {
							f = 1;
							fail++;
//							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
							System.out.println("Error in ID:025-------------Check Here////");
							System.out.println("Error:"+e.getMessage());
						} finally {
							count++;
							p = 0;
							f = 0;

						}
						if (id != 000)
							break;
					case 26:
						try {
							appdriver.findElementById("com.microware.cdfi:id/lay_Cader").click();
							appdriver.findElementById("com.microware.cdfi:id/addCader").click();
							appdriver.findElementById("com.microware.cdfi:id/spin_category").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, memCons.catColNum) + "\")"))
									.click();
							appdriver.findElementById("com.microware.cdfi:id/spin_role").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, memCons.rolesColNum) + "\")"))
									.click();

//							appdriver.findElementById("com.microware.cdfi:id/et_joiningDate").click();
							String date = xc.getCellString(row, memCons.joinDateColNum);
							dateLogic.datePicker(date, "com.microware.cdfi:id/et_joiningDate");

//							appdriver.findElementById("com.microware.cdfi:id/et_leavingDate").click();
							date = xc.getCellString(row, memCons.leaveDateColNum);
							dateLogic.datePicker(date, "com.microware.cdfi:id/et_leavingDate");

							appdriver.findElementById("com.microware.cdfi:id/btn_save").click();
							if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
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
							p = 1;
							pass++;
							System.out.println("026:Cadre");

						} catch (Exception e) {
							f = 1;
							fail++;
							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
							System.out.println("Error in Cadre:026----------------Check Here////");
							e.printStackTrace();
						} finally {
							count++;
							p = 0;
							f = 0;

						}
						if (id != 000)
							break;
					case 27:
						if (xc.getCellString(row, memCons.insuranceColNum).equals("Yes")) {
							try {
								Thread.sleep(1000);
								appdriver.findElementById("com.microware.cdfi:id/lay_Insurance").click();
								appdriver.findElementById("com.microware.cdfi:id/addInsurance").click();
								appdriver.findElementById("com.microware.cdfi:id/spin_insurance").click();
								appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
										+ xc.getCellString(row, memCons.insuranceTypeColNum) + "\")")).click();
								String date = xc.getCellString(row, memCons.insValidColNum);
								dateLogic.datePicker(date, "com.microware.cdfi:id/et_validUpto");
								appdriver.findElementById("com.microware.cdfi:id/btn_save").click();

								if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
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

								p = 1;
								pass++;
								System.out.println("027:Insurance");
								testMem.log(Status.PASS, "027:Insurance");

							} catch (Exception e) {
								f = 1;
								fail++;
								appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
								testMem.log(Status.FAIL, "027:Insurance");
								System.out.println("Error in Insurance:027----------------Check Here////");
								e.printStackTrace();
							} finally {
								count++;
								p = 0;
								f = 0;
							}
						}
						if (id != 000)
							break;
					default:
						break;

					}

				}
			}
		}

		Thread.sleep(1000);
		try {
			if (t == 0)
				appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
			Thread.sleep(1000);
		} catch (Exception e) {

		}
		try {
			appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
			
		} catch (Exception e) {
			System.out.println("Error: com.microware.cdfi:id/ivBack");
		}
		
		

		try {
			if (!appdriver.findElementById("om.microware.cdfi:id/tv_title").getText().equalsIgnoreCase("SHG"))
				appdriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
//				appdriver.findElementById("com.microware.cdfi:id/icBack").click();
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("Error: AndroidKey.BACK");
		}

		if (neg_test_flag)
			pass = neg_test_count;

		int[] val = { pass, fail, count };
		return val;

	}

}
