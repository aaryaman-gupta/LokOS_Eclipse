package app;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import lokos.lokosTest;
import reporting.ExtentManager;
import util.cameraLogic;
import util.dateLogic;

public class memberProfile extends lokosTest {

	public static boolean neg_test_flag = false;
	public static int neg_test_count = 0;
	public static boolean invalid_flag = false;
	public static int pass = 0;
	public static int fail = 0;

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
		int p = 0;
		int f = 0;
		int id = 000;
		int t = 0;// flag for back button
		boolean neg_test_flag = false;
		neg_test_count = 0;
		invalid_flag = false;
		pass=0;
		fail=0;

		int k = 0;
		if (Integer.valueOf(idList[0]) != 0) {
			for (String s : idList) {
				mem_check[0][Integer.valueOf(idList[k])] = Integer.valueOf(idList[k]);
				++k;
			}
		} else {
			for (int j = 0; j < mem_check[0].length; j++) {
				mem_check[0][j] = j;
			}
		}
		try {
			if (xc.getCellString(row, memCons.typeColNum).contains("Check")) {
				neg_test_flag = true;
				testMem.log(Status.INFO, "NEGETIVE TESTING");
			}
		} catch (NullPointerException np) {
		}

		for (int iterations = 0; iterations < idList.length; iterations++) {
			id = Integer.valueOf(idList[iterations]);

			switch (id) {

			case 000:

			case 001:
				try {
					enterString_Id("Name (Including Surname)", "top", "com.microware.cdfi:id/et_name", row,
							memCons.nameColNum, "001:||Validation Error");
					pseq(id, "001:Name");
				} catch (Exception e) {
					fseq(id, "001:Name", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 002:
				try {
					enterString_Id("Name in Local", "top", "com.microware.cdfi:id/et_namelocal", row,
							memCons.nameLocalColNum, "001:||Validation Error");
					pseq(id, "002:Name in Local");
				} catch (Exception e) {
					pseq(id, "002:Name in Local");

				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 003:
				try {
					selectById("Gender", "top", "com.microware.cdfi:id/spin_gender", row, memCons.genderColNum);
					pseq(id, "003:Gender");
				} catch (Exception e) {
					fseq(id, "003:Gender", e);
				} finally {
					count++;
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
						enterValue_Id("Age", "top", "com.microware.cdfi:id/et_age", row, memCons.ageColNum,
								"204:||Validation Error");
						mt.scrollToText("As on Date", "top");
//							appdriver.findElementById("com.microware.cdfi:id/et_ason").click();
						String date = xc.getCellString(row, memCons.ageAODColNum);
						dateLogic.datePicker(date, "com.microware.cdfi:id/et_ason");
					}
					pseq(id, "004:Member DOB Available and Sub Parts Details filled");
				} catch (Exception e) {
					fseq(id, "004:Member DOB Available and Sub Parts Details filled", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 005:
				try {
					mt.scrollToText("Member Joining Date", "top");
					String date = xc.getCellString(row, memCons.dateColNum);
					appdriver.findElementById("com.microware.cdfi:id/et_joiningDate").clear();
					dateLogic.datePicker(date, "com.microware.cdfi:id/et_joiningDate");
					pseq(id, "005:Member Joining Date");
				} catch (Exception e) {
					fseq(id, "005:Member Joining Date", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 006:
				try {
					selectById("Marital Status", "top", "com.microware.cdfi:id/spin_martial", row,
							memCons.maritalStatColNum);
					pseq(id, "006:Marital Status");
				} catch (Exception e) {
					fseq(id, "006:Marital Status", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 007:
				try {
					enterString_Id("Mother / Father / Spouse", "top", "com.microware.cdfi:id/et_mother_father", row,
							memCons.M_F_SColNum, "007:||Validation Error");
					pseq(id, "007:Mother/Father/Spouse");
				} catch (Exception e) {
					fseq(id, "007:Mother/Father/Spouse", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 8:
				try {
					mt.scrollToText("Mother/Father/Spouse in local", "top");
					appdriver.findElementById("com.microware.cdfi:id/et_mother_fatherlocal")
							.sendKeys(xc.getCellString(row, memCons.M_F_SLocalColNum));
					enterString_Id("Mother/Father/Spouse in local", "top",
							"com.microware.cdfi:id/et_mother_fatherlocal", row, memCons.M_F_SLocalColNum,
							"008:||Validation Error");
					pseq(id, "008:Mother/Father/Spouse in Local");
				} catch (Exception e) {
					fseq(id, "008:Mother/Father/Spouse in Local", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;

			case 9:
				try {
					selectById("Relation", "top", "com.microware.cdfi:id/spin_relationmother", row,
							memCons.relationColNum);
					pseq(id, "009:Relation");
				} catch (Exception e) {
					fseq(id, "009:Relation", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 10:
				try {
					mt.scrollToText("Is Member Head of the family?", "top");
					appdriver.findElement(MobileBy.AndroidUIAutomator(
							"new UiSelector().text(\"" + xc.getCellString(row, memCons.familyHeadColNum) + "\")"))
							.click();
					pseq(id, "010:Is member head of the family?");
				} catch (Exception e) {
					fseq(id, "010:Is member head of the family?", e);
				} finally {
					count++;
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
						selectById("Disability Type", "top", "com.microware.cdfi:id/spin_disabilityType", row,
								memCons.disabilityTypeColNum);
						if (du.isElementPresent("new UiSelector().text(\"Guardian Name/ Care Taker\")",
								"androidUIAutomatior")) {
							enterString_Id("Guardian Name/ Care Taker", "top", "com.microware.cdfi:id/et_guardianName",
									row, memCons.guardianNameColNum, "211:||Validation Error||");
							enterString_Id("Guardian Name/ Care Taker in local", "top",
									"com.microware.cdfi:id/et_guardianNamelocal", row, memCons.guardianNameLocalColNum,
									"311:||Validation Error||");
							selectById("Relation with Guardian", "top", "com.microware.cdfi:id/spin_relationGuardian",
									row, memCons.relationGuardianColNum);
						}
					}
					pseq(id, "011:Disability and Sub Parts");
				} catch (Exception e) {
					fseq(id, "011:Disability and Sub Parts", e);
				} finally {
					count++;
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
					pseq(id, "012:Post/Designation");
				} catch (Exception e) {
					fseq(id, "012:Post/Designation", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 13:
				try {
					selectById("Social Category", "top", "com.microware.cdfi:id/spin_socialCategory", row,
							memCons.socialCatColNum);
					pseq(id, "013:Social Category");
				} catch (Exception e) {
					fseq(id, "013:Social Category", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 14:
				try {
					selectById("Religion", "top", "com.microware.cdfi:id/spin_religion", row, memCons.religionColNum);
					pseq(id, "014:Religion");
				} catch (Exception e) {
					fseq(id, "014:Religion", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 15:
				try {
					selectById("Highest Education Level", "top", "com.microware.cdfi:id/spin_education", row,
							memCons.highEduLvlColNum);
					pseq(id, "015:Highest Education Level");
					pass++;
					System.out.println("015:Highest Education Level");
				} catch (Exception e) {
					fseq(id, "015:Highest Education Level", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 51:
				try {
					mt.scrollToText("Insurance", "top");
					appdriver.findElement(MobileBy.AndroidUIAutomator(
							"new UiSelector().text(\"" + xc.getCellString(row, memCons.insuranceColNum) + "\")"))
							.click();
					pseq(id, "051:Insurance");
				} catch (Exception e) {
					fseq(id, "051:Insurance", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 16:
				try {
					selectById("Primary Livelihood", "top", "com.microware.cdfi:id/spin_occupation", row,
							memCons.primaryla);
					pseq(id, "016:Primary Livelihood");
				} catch (Exception e) {
					fseq(id, "016:Primary Livelihood", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 17:
				try {
					selectById("Secondary Livelihood", "top", "com.microware.cdfi:id/spin_secondaryoccu", row,
							memCons.secondaryla);
					pseq(id, "017:Secondary Livelihood");
				} catch (Exception e) {
					fseq(id, "017:Secondary Livelihood", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 18:
				try {
					selectById("Tertiary Livelihood", "top", "com.microware.cdfi:id/spin_tertiaryoccu", row,
							memCons.tertiaryla);
					pseq(id, "016:Tertiary Livelihood");
				} catch (Exception e) {
					fseq(id, "016:Tertiary Livelihood", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 19:
				try {
					mt.scrollToText("Member Image", "top");
					appdriver.findElementById("com.microware.cdfi:id/Imgmember").click();
					cameraLogic.click();
					pseq(id, "019:Image Upload");
				} catch (Exception e) {
					fseq(id, "019:Image Upload", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 050:
				try {
					mt.scrollToText("Consent Form Image", "top");
					appdriver.findElementById("com.microware.cdfi:id/ImgmemberConsent").click();
					cameraLogic.click();
					pseq(id, "050:Consent Form Image Upload");
					p = 1;
					pass++;
					System.out.println("050:Consent Form Image Upload");
				} catch (Exception e) {
					fseq(id, "050:Consent Form Image Upload", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			case 20:

				try {
					if (!xc.getCellString(row, memCons.typeColNum).equalsIgnoreCase("New")) {
						selectById("Status", "top", "com.microware.cdfi:id/spin_status", row, memCons.statusColNum);
					}
					pseq(id, "020:Status");
				} catch (Exception e) {
					fseq(id, "020:Status", e);
				} finally {
					count++;
				}
				if (id != 000)
					break;
			default:
				break;

			}

			if (invalid_flag) {
				System.out.println("Error---->Atleast one field is incorrect");
				System.out.println("Error---->Data not saved: Cannot Proceed(Correct the Errors first)");
				testMem.log(Status.INFO,"Error---->Atleast one field is incorrect");
				testMem.log(Status.INFO,"Error---->Data not saved: Cannot Proceed(Correct the Errors first)");
				appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
				appdriver.findElementById("com.microware.cdfi:id/btn_yes").click();
				navigateBackToScreen("SHG");
				t = 1;
				id = 9999;
			}
			if (neg_test_flag) {
				appdriver.findElementById("com.microware.cdfi:id/btn_save").click();

				/////////////// For SHG resolution copy
				if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
						.equals("Please upload resolution copy"))
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
				
				if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
						.equals("Data saved successfully")
						|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
								.equals("Data Updated Successfully")) {
					appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
					System.out.println("Necessary fields for saving SHG filled.");
				} else {
					String err = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
					System.out.println("Error---->" + err);
					testMem.log(Status.INFO, "Error---->" + err);
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
					mt.scrollToText("Name (Including Surname)", "bottom");
					t = 1;
					id = 9999;
				}
				appdriver.findElementById("com.microware.cdfi:id/IvVector").click();
			}

		}
		if (!neg_test_flag) {
			appdriver.findElementById("com.microware.cdfi:id/btn_save").click();

			///////////////For SHG resolution copy
			if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
					.equals("Please upload resolution copy"))
				appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
			if (appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText().equals("Data saved successfully")
					|| appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText()
							.equals("Data Updated Successfully")) {
				appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
				testMem.log(Status.INFO, "Necessary fields for saving SHG filled.");
				System.out.println("Necessary fields for saving SHG filled.");
			} else {
				String err = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
				testMem.log(Status.FAIL, "Error---->" + err);
				System.out.println("Error---->" + err);
				appdriver.findElementById("com.microware.cdfi:id/btn_ok").click();
				testMem.log(Status.INFO, "Atleast one field is incorrect");
				System.out.println("Error---->Atleast one field is incorrect");
				testMem.log(Status.INFO, "Data not saved: Cannot Proceed(Correct the Errors first)");
				System.out.println("Error---->Data not saved: Cannot Proceed(Correct the Errors first)");				
				util.randomPressLogic.press(0.5, 0.05);
				appdriver.findElementById("com.microware.cdfi:id/btn_cancel").click();
				appdriver.findElementById("com.microware.cdfi:id/btn_yes").click();
				ExtentManager.addScreenShotsToTest("Member Fail Back", testMem);
				navigateBackToScreen("SHG");
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
						invalid_flag = false;
						try {
							int z = 0;
							while (z < 6) {
								try {
									Thread.sleep(2000);
									appdriver.findElementById("com.microware.cdfi:id/lay_phone").click();
									z = 6;
								} catch (Exception e) {
									System.out.println("Wasn't able to press the phone button");
									z++;
								}
							}
							
							String[] phnos = xc.getCellString(row, memCons.phoneNosColNum).split(";");
							for (int i = 0; i < phnos.length; i++) {
								Thread.sleep(2000);
								appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
								appdriver.findElementById("com.microware.cdfi:id/addphone").click();
								String[] s = phnos[i].split(":");
								
								Random objGenerator = new Random();
								appdriver.findElementById("com.microware.cdfi:id/et_phoneno").sendKeys("9455"+String.valueOf(objGenerator.nextInt(100000,999999)));
								
//								appdriver.findElementById("com.microware.cdfi:id/et_phoneno").sendKeys(s[1].trim());
//								validCheckLongNum("com.microware.cdfi:id/et_phoneno", "id", s[1].trim(),
//										"021:||Validation Error||","");
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
								System.out.println("021:Phone Number:"+s[1].trim());
								testMem.log(Status.INFO, "Phone number:"+s[1].trim());
							}
							if (invalid_flag)
								throw new Exception("Validation Failed");
							pseq(id, "021:Phone Number");
						} catch (Exception e) {
							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
							fseq(id, "021:Phone Number", e);
						} finally {
							count++;
						}
						if (id != 000)
							break;

					case 22:
						invalid_flag = false;
						try {
							int z = 0;
							while (z < 6) {
								try {
									Thread.sleep(2000);
									appdriver.findElementById("com.microware.cdfi:id/Ivloc").click();
									z = 6;
								} catch (Exception e) {
									System.out.println("Wasn't able to press the address button");
									z++;
								}
							}
							Thread.sleep(1000);
							appdriver.findElementById("com.microware.cdfi:id/addAddress").click();
							appdriver.findElementById("com.microware.cdfi:id/spin_addresstype").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""
									+ xc.getCellString(row, memCons.addressTypeColNum) + "\")")).click();
							appdriver.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, memCons.addLocColNum) + "\")"))
									.click();
							enterString_Id("Address Line 1", "top", "com.microware.cdfi:id/et_address1", row,
									memCons.add1ColNum, "222:||Validation Error");
							enterString_Id("Address Line 2", "top", "com.microware.cdfi:id/et_address2", row,
									memCons.add2ColNum, "322:||Validation Error");
							enterValue_Id("Pincode", "top", "com.microware.cdfi:id/et_pincode", row, memCons.pinColNum,
									"422:||Validation Error");
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
							if (invalid_flag)
								throw new Exception("Validation Failed");
							pseq(id, "022:Address");
						} catch (Exception e) {
							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
							fseq(id, "022:Address", e);
						} finally {
							count++;
						}
						if (id != 000)
							break;
					case 23:
						invalid_flag = false;
						try {
							int z = 0;
							while (z < 6) {
								try {
									Thread.sleep(2000);
									appdriver.findElementById("com.microware.cdfi:id/IvBank").click();
									z = 6;
								} catch (Exception e) {
									System.out.println("Wasn't able to press the bank button");
									z++;
								}
							}							
							appdriver.findElementById("com.microware.cdfi:id/addBank").click();
							enterString_Id("Name in Bank Passbook", "top",
									"com.microware.cdfi:id/et_nameinbankpassbook", row, memCons.passNameColNum,
									"023:||Validation Error");
							enterString_Id("IFSC Code", "top", "com.microware.cdfi:id/et_ifsc", row, memCons.IFSCColNum,
									"123:||Validation Error||");
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
							String acc="";
							Random objGenerator = new Random();
							acc=String.valueOf(objGenerator.nextInt(10000,99999))+String.valueOf(objGenerator.nextInt(100000,999999));
							System.out.println("Account: "+acc);
							try {			
								mt.scrollToText("Account number", "top");
								appdriver.findElementById("com.microware.cdfi:id/et_Accountno").sendKeys(acc);									
							}catch(Exception e) {
								System.out.println("Unable to enter Account Data. Possible Migrated Data.");
							}
							
							try {								
								appdriver.findElementById("com.microware.cdfi:id/et_retype_Accountno").sendKeys(acc);									
							}catch(Exception e) {
								System.out.println("Unable to enter Retype Account Data. Possible Migrated Data.");
							}
//							
//							mt.scrollToText("Account number", "top");							
//							appdriver.findElementById("com.microware.cdfi:id/et_Accountno").sendKeys(xc.getCellString(row,memCons.accNoColNum).substring(1));
//							enterLongNum_Id("Re-Type Account No.", "top", "com.microware.cdfi:id/et_retype_Accountno",
//									row, memCons.retypAccNoColNum, "523:||Validation Error","#");
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
							
							if (invalid_flag)
								throw new Exception("Validation Failed");
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
							if (invalid_flag)
								throw new Exception("Validation Failed");
							pseq(id, "023:Bank Account");
						} catch (Exception e) {
							if (!xc.getCellString(row, memCons.typeColNum).equals("New")) {
								appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
							}
							fseq(id, "023:Bank Account", e);
						} finally {
							count++;
							Thread.sleep(3000);
						}
						if (id != 000)
							break;
					case 24:
						invalid_flag = false;
						try {
							Thread.sleep(1000);
							int z = 0;
							while (z < 6) {
								try {
									Thread.sleep(2000);
									appdriver.findElementById("com.microware.cdfi:id/lay_kyc").click();
									z = 6;
								} catch (Exception e) {
									System.out.println("Wasn't able to press the kyc button");
									z++;
								}
							}	
							appdriver.findElementById("com.microware.cdfi:id/addKyc").click();
							appdriver.findElementById("com.microware.cdfi:id/spin_kyctype").click();
							appdriver.findElement(MobileBy.AndroidUIAutomator(
									"new UiSelector().text(\"" + xc.getCellString(row, memCons.docTypeColNum) + "\")"))
									.click();
							enterLongNum_Id("KYC ID", "top", "com.microware.cdfi:id/et_kycno", row, memCons.docIDColNum,
									"124:||Validation Error||","#");
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
							if (invalid_flag)
								throw new Exception("Validation Failed");
							pseq(id, "024:KYC");
						} catch (Exception e) {
							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
							fseq(id, "024:KYC", e);
						} finally {
							count++;
						}
						if (id != 000)
							break;
					case 25:
						invalid_flag = false;
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
//							f = 1;
//							fail++;
//							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
							System.out.println("Error in ID:025-------------Check Here////");
							System.out.println("Error:" + e.getMessage());
						} finally {
							count++;
						}
						if (id != 000)
							break;
					case 26:
						invalid_flag = false;
						try {
							int z = 0;
							while (z < 6) {
								try {
									Thread.sleep(2000);
									appdriver.findElementById("com.microware.cdfi:id/lay_Cader").click();
									z = 6;
								} catch (Exception e) {
									System.out.println("Wasn't able to press the cadre button");
									z++;
								}
							}
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
							if (invalid_flag)
								throw new Exception("Validation Failed");
							pseq(id, "026:Cadre");
						} catch (Exception e) {
							appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
							fseq(id, "026:Cadre", e);
						} finally {
							count++;
						}
						if (id != 000)
							break;
					case 27:
						invalid_flag = false;
						if (xc.getCellString(row, memCons.insuranceColNum).equals("Yes")) {
							try {
								Thread.sleep(1000);
								int z = 0;
								while (z < 6) {
									try {
										Thread.sleep(2000);
										appdriver.findElementById("com.microware.cdfi:id/lay_Insurance").click();
										z = 6;
									} catch (Exception e) {
										System.out.println("Wasn't able to press the cadre button");
										z++;
									}
								}
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
								if (invalid_flag)
									throw new Exception("Validation Failed");
								pseq(id, "027:Insurance");
							} catch (Exception e) {
								appdriver.findElementById("com.microware.cdfi:id/ivBack").click();
								fseq(id, "027:Insurance", e);
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
		
		Thread.sleep(2000);
		ExtentManager.addScreenShotsToTest("Save and Check", testMem);
		navigateBackToScreen("SHG");

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

	public static void enterLongNum_Id(String title, String dir, String loc, int row, int cons, String err, String prefix) {
		mt.scrollToText(title, dir);
		appdriver.findElementById(loc).sendKeys(xc.getCellString(row, cons).substring(1));
		int f = validCheckLongNum(loc, "id", xc.getCellString(row, cons), err,prefix);
		if (f == 1)
			invalid_flag = true;
	}

	public static int validCheckLongNum(String loc, String locStrat, String field_txt, String text,String prefix) {
		if (locStrat.equalsIgnoreCase("xpath")) {
			if (!(prefix + appdriver.findElementByXPath(loc).getText()).equals(field_txt)) {
				System.out.println(text);
				testMem.log(Status.INFO, text);
				++neg_test_count;
				return 1;
			}
		} else if (locStrat.equalsIgnoreCase("id")) {
			if (!(prefix + appdriver.findElementById(loc).getText()).equals(field_txt)) {
				System.out.println(text);
				testMem.log(Status.INFO, text);
				++neg_test_count;
				return 1;
			}
		} else if (locStrat.equalsIgnoreCase("UiSelectorText")) {
			if (!(prefix + appdriver.findElement(MobileBy.AndroidUIAutomator(loc)).getText()).equals(field_txt)) {
				System.out.println(text);
				testMem.log(Status.INFO, text);
				++neg_test_count;
				return 1;
			}
		}
		return 0;
	}

	public static int validCheckString(String loc, String locStrat, String field_txt, String text) {
		if (locStrat.equalsIgnoreCase("xpath")) {
			if (!appdriver.findElementByXPath(loc).getText().equals(field_txt)) {
				System.out.println(text);
				testMem.log(Status.INFO, text);
				++neg_test_count;
				return 1;
			}
		} else if (locStrat.equalsIgnoreCase("id")) {
			if (!appdriver.findElementById(loc).getText().equals(field_txt)) {
				System.out.println(text);
				testMem.log(Status.INFO, text);
				++neg_test_count;
				return 1;
			}
		} else if (locStrat.equalsIgnoreCase("UiSelectorText")) {
			if (!appdriver.findElement(MobileBy.AndroidUIAutomator(loc)).getText().equals(field_txt)) {
				System.out.println(text);
				testMem.log(Status.INFO, text);
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
			testMem.log(Status.FAIL, "ex");
			ExtentManager.addScreenShotsToLogFail("SHG Meetings "+ex, testMem);
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
						testMem.log(Status.INFO, "Negetive Test Failed");
					}
				} catch (NullPointerException np) {
					System.out.println("---->>Expected Errors is empty.");
					testMem.log(Status.INFO, "Expected Errors is empty.");
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
				Thread.sleep(3000);
				appdriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
				Thread.sleep(3000);
				if (appdriver.findElementById("com.microware.cdfi:id/tv_title").getText().equals("SHG"))
					break;
				i++;
			}
		} catch (Exception e) {
			if(!title.equals("Member")) {
				System.out.println("Cannot navigate to " + screen_title + " screen");
				Thread.sleep(3000);
				appdriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
			}
			else {
				System.out.println(e.getMessage());
				System.out.println("Cannot navigate to " + screen_title + " screen");
				throw new Exception("The app might have crashed");
			}
		}
	}

	public static void pseq(int id, String msg) {
		pass++;
		mem_check[1][id] = 1;
		if (neg_test_flag)
			testMem.log(Status.FAIL, msg);
		else
			testMem.log(Status.PASS, msg);
		System.out.println(msg);
	}

	public static void fseq(int id, String msg, Exception e) {
		fail++;
		mem_check[1][id] = -1;
		if (!neg_test_flag)
			testMem.log(Status.FAIL, msg);
		else
			testMem.log(Status.PASS, msg);
		System.out.println("Error in " + msg + "----------------------Check Here////");
		e.printStackTrace();
	}

}
