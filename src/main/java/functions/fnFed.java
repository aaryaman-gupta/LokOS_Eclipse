package functions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import readingXLS.xlsClasses;
import reporting.ExtentManager;
import util.DeviceUtil;
import util.MobileTouchAdv;
import util.backButton;

public class fnFed {

	public xlsClasses xc = null;
	public AndroidDriver<AndroidElement> appdriver = null;
	public ExtentTest test = null;
	public MobileTouchAdv mt = null;
	public DeviceUtil du = null;
	public int check[][]=null;
	public int pass=0;
	public int fail=0;
	public int count=0;
	public final double x=0.35;
	public final double y=0.70;
	public boolean invalid_flag=false;
	
	public fnFed(xlsClasses xc, AndroidDriver<AndroidElement> appdriver, ExtentTest test, MobileTouchAdv mt, DeviceUtil du, int check[][]) {
	
		this.xc=xc;
		this.appdriver=appdriver;
		this.test=test;
		this.mt=mt;
		this.du=du;
		this.check=check;
	}
	
	
	public String enterStringById(ExtentTest test, String title, String dir, String loc, int row, int col, String id,
			String errid, boolean neg_test, String def, boolean migr) {
		try {
			if(!xc.getCellString(row, col).equalsIgnoreCase("Do Not Enter")){
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			Thread.sleep(1500);
			String entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir,x,y);
			try {
				if(migrCheckById(test,loc,def,migr)) {					
					appdriver.findElementById(loc).clear();
					appdriver.findElementById(loc).sendKeys(entry);
				}
			} catch (Exception e) {
				String err = "p";
				if (neg_test) {
					try {
						err = xc.getCellString(row, col + 1);
					} catch (Exception e2) {
						err = "p";
					}
					if (!err.equals("n")) {
						System.out.println(e.getMessage());
						ExtentManager.addScreenShotsToLogFail("Failed Entry", test);
						System.out.println("Failed Field Entry");
						test.log(Status.FAIL, title + "(id" + id + ")>>" + xc.getCellString(row, col));
						invalid_flag=true;
						return "fail";

					} else {
						System.out.println("Field Processed");
						test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
						invalid_flag=true;
						return "pass";
					}
				}
			}
			String f = validCheckString(test, loc, "id", row, col, entry, errid, neg_test);
			if (f.equals("fail"))
				throw new Exception("Validation Failed");
			}else {
				return "pass";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed Field Entry");
			test.log(Status.FAIL, title + "(id" + id + ")>>" + xc.getCellString(row, col));
			invalid_flag=true;
			return "fail";
		}
		System.out.println("Field Processed");
		test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
		return "pass";
	}

	public String enterStringByXpath(ExtentTest test, String title, String dir, String loc, int row, int col, String id,
			String errid, boolean neg_test, String def, boolean migr) {
		try {
			if(!xc.getCellString(row, col).equalsIgnoreCase("Do Not Enter")){
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			Thread.sleep(1500);
			String entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir,x,y);			
			try {
				if(migrCheckByXPath(test,loc,def,migr)) {
					appdriver.findElementByXPath(loc).clear();
					appdriver.findElementByXPath(loc).sendKeys(entry);
				}
			} catch (Exception e) {
				String err = "p";
				if (neg_test) {
					try {
						err = xc.getCellString(row, col + 1);
					} catch (Exception e2) {
						err = "p";
					}
					if (!err.equals("n")) {
						System.out.println(e.getMessage());
						ExtentManager.addScreenShotsToLogFail("Failed Entry", test);
						System.out.println("Failed Field Entry");
						test.log(Status.FAIL, title + "(id" + id + ")>>" + xc.getCellString(row, col));
						invalid_flag=true;
						return "fail";

					} else {
						System.out.println("Field Processed");
						test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
						invalid_flag=true;
						return "pass";
					}
				}
			}
			String f = validCheckString(test, loc, "id", row, col, entry, errid, neg_test);
			if (f.equals("fail"))
				throw new Exception("Validation Failed");
			}else {
				return "pass";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed Field Entry");
			test.log(Status.FAIL, title + "(id" + id + ")>>" + xc.getCellString(row, col));
			invalid_flag=true;
			return "fail";
		}
		System.out.println("Field Processed");
		test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
		return "pass";
	}

	public String enterNumById(ExtentTest test, String title, String dir, String loc, int row, int col, String id,
			String errid, boolean neg_test, String def, boolean migr) {

		try {
			if(!xc.getCellString(row, col).equalsIgnoreCase("Do Not Enter")){
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			Thread.sleep(1500);
			String entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir,x,y);
			try {
				if(migrCheckById(test,loc,def,migr)) {
					appdriver.findElementById(loc).clear();
					appdriver.findElementById(loc).sendKeys(entry);
				}
			} catch (Exception e) {
				String err = "p";
				if (neg_test) {
					try {
						err = xc.getCellString(row, col + 1);
					} catch (Exception e2) {
						err = "p";
					}
					if (!err.equals("n")) {
						System.out.println(e.getMessage());
						ExtentManager.addScreenShotsToLogFail("Failed Entry", test);
						System.out.println("Failed Field Entry");
						test.log(Status.FAIL, title + "(id" + id + ")>>" + xc.getCellString(row, col));
						invalid_flag=true;
						return "fail";

					} else {
						System.out.println("Field Processed");
						test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
						invalid_flag=true;
						return "pass";
					}
				}
			}
			String f = validCheckNum(test, loc, "id", row, col, entry, errid, neg_test);
			if (f.equals("fail"))
				throw new Exception("Validation Failed");
		}else {
			return "pass";
		}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed Field Entry");
			test.log(Status.FAIL, title + "(id" + id + ")>>" + xc.getCellString(row, col));
			invalid_flag=true;
			return "fail";
		}
		System.out.println("Field Processed");
		test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
		return "pass";
	}

	public String enterNumByXpath(ExtentTest test, String title, String dir, String loc, int row, int col, String id,
			String errid, boolean neg_test, String def, boolean migr) {

		try {
			if(!xc.getCellString(row, col).equalsIgnoreCase("Do Not Enter")){
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			Thread.sleep(1500);
			String entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir,x,y);
			try {
				if(migrCheckByXPath(test,loc,def,migr)) {
					appdriver.findElementByXPath(loc).clear();
					appdriver.findElementByXPath(loc).sendKeys(entry);
				}
			} catch (Exception e) {
				String err = "p";
				if (neg_test) {
					try {
						err = xc.getCellString(row, col + 1);
					} catch (Exception e2) {
						err = "p";
					}
					if (!err.equals("n")) {
						System.out.println(e.getMessage());
						ExtentManager.addScreenShotsToLogFail("Failed Entry", test);
						System.out.println("Failed Field Entry");
						test.log(Status.FAIL, title + "(id" + id + ")>>" + xc.getCellString(row, col));
						invalid_flag=true;
						return "fail";

					} else {
						System.out.println("Field Processed");
						test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
						invalid_flag=true;
						return "pass";
					}
				}
			}
			String f = validCheckNum(test, loc, "xpath", row, col, entry, errid, neg_test);
			if (f.equals("fail"))
				throw new Exception("Validation Failed");
			}else {
				return "pass";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed Field Entry");
			test.log(Status.FAIL, title + "(id" + id + ")>>" + xc.getCellString(row, col));
			invalid_flag=true;
			return "fail";
		}
		System.out.println("Field Processed");
		test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
		return "pass";
	}

	public String selectById(ExtentTest test, String title, String dir, String loc, int row, int col, String id,
			boolean neg_test, String def,boolean migr,String a,String b, String c) {
		String entry="";
		String err = "p";
		if (neg_test) {
			try {
				err = xc.getCellString(row, col + 1);
			} catch (Exception e) {
				err = "p";
			}
		}
		try {
			if(!xc.getCellString(row, col).equalsIgnoreCase("Do Not Enter")){
			System.out.println("Processing Details: " + title + "(id" + id + ")>>"+xc.getCellString(row, col));
			entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir,x,y);
			try {
				if(migrCheckByIdDD(test,title,def,migr,a,b,c)) {
					appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					appdriver.findElementById(loc).click();
					appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + entry + "\")")).click();
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
				backButton.back();
				throw new Exception("Cannot detect option");
			}
		}else {
			return "pass";
		}
		} catch (Exception e) {			
			e.printStackTrace();
			System.out.println("Failed Selection");
			if (err.equals("n")) {
				test.log(Status.PASS, title + "(id" + id + ")>>"+entry);
				invalid_flag=true;
				return "pass";
			}else {
				test.log(Status.FAIL, title + "(id" + id + ")>>"+entry);
				invalid_flag=true;
				return "fail";
			}
		}
		System.out.println("Field Processed");
		if (err.equals("n")) {
			test.log(Status.FAIL, title + "(id" + id + ")>>"+entry);
			invalid_flag=true;
			return "fail";
		}else {
			test.log(Status.PASS, title + "(id" + id + ")>>"+entry);
			return "pass";
		}		
	}

	public String selectByXPath(ExtentTest test, String title, String dir, String loc, int row, int col, String id,
			boolean neg_test, String def,boolean migr,String a,String b, String c) {
		String entry = "";
		String err = "p";
		if (neg_test) {
			try {
				err = xc.getCellString(row, col + 1);
			} catch (Exception e) {
				err = "p";
			}
		}
		try {
			if(!xc.getCellString(row, col).equalsIgnoreCase("Do Not Enter")){
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir,x,y);
			try {
				if(migrCheckByXPathDD(test,title,def,migr,a,b,c)) {
					appdriver.findElementByXPath(loc).click();
					appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + entry + "\")")).click();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				backButton.back();
				throw new Exception("Cannot detect option");
			}
			}else {
				return "pass";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed Selection");
			if (err.equals("n")) {
				test.log(Status.PASS, title + "(id" + id + ")>>" + entry);
				invalid_flag=true;
				return "pass";
			} else {
				test.log(Status.FAIL, title + "(id" + id + ")>>" + entry);
				invalid_flag=true;
				return "fail";
			}
		}
		System.out.println("Field Processed");
		if (err.equals("n")) {
			test.log(Status.FAIL, title + "(id" + id + ")>>" + entry);
			invalid_flag=true;
			return "fail";
		} else {
			test.log(Status.PASS, title + "(id" + id + ")>>" + entry);
			return "pass";
		}
	}


	public String selectCustomById(ExtentTest test, String title, String dir, String loc, String xpath, int row,
			int col, String id, boolean neg_test, String def,boolean migr,String a,String b, String c) {
		String entry="";
		String err = "p";
		if (neg_test) {
			try {
				err = xc.getCellString(row, col + 1);
			} catch (Exception e) {
				err = "p";
			}
		}
		try {
			if(!xc.getCellString(row, col).equalsIgnoreCase("Do Not Enter")){
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir,x,y);
			try {
				if(migrCheckByIdDD(test,title,def, migr,a,b,c)) {
					appdriver.findElementById(loc).click();
					appdriver.findElementByXPath(xpath).click();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				backButton.back();
				throw new Exception("Cannot detect option");
			}
			}else {
				return "pass";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed Selection");
			if (err.equals("n")) {
				test.log(Status.PASS, title + "(id" + id + ")>>" + entry);
				invalid_flag=true;
				return "pass";
			} else {
				test.log(Status.FAIL, title + "(id" + id + ")>>" + entry);
				invalid_flag=true;
				return "fail";
			}
		}
		System.out.println("Field Processed");
		if (err.equals("n")) {
			test.log(Status.FAIL, title + "(id" + id + ")>>" + entry);
			invalid_flag=true;
			return "fail";
		} else {
			test.log(Status.PASS, title + "(id" + id + ")>>" + entry);
			return "pass";
		}
		
	}

	public String selectCustomByXPath(ExtentTest test, String title, String dir, String loc, String xpath, int row,
			int col, String id, boolean neg_test, String def,boolean migr,String a,String b, String c) {
		String entry="";
		String err = "p";
		if (neg_test) {
			try {
				err = xc.getCellString(row, col + 1);
			} catch (Exception e) {
				err = "p";
			}
		}
		try {
			if(!xc.getCellString(row, col).equalsIgnoreCase("Do Not Enter")){
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir,x,y);
			try {
				if(migrCheckByXPathDD(test,title,def,migr,a,b,c)) {
					appdriver.findElementByXPath(loc).click();
					appdriver.findElementByXPath(xpath).click();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				backButton.back();
				throw new Exception("Cannot detect option");
			}
		}else {
			return "pass";
		}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed Selection");
			if (err.equals("n")) {
				test.log(Status.PASS, title + "(id" + id + ")>>" + entry);
				invalid_flag=true;
				return "pass";
			} else {
				test.log(Status.FAIL, title + "(id" + id + ")>>" + entry);
				invalid_flag=true;
				return "fail";
			}
		}
		System.out.println("Field Processed");
		if (err.equals("n")) {
			test.log(Status.FAIL, title + "(id" + id + ")>>" + entry);
			return "fail";
		} else {
			test.log(Status.PASS, title + "(id" + id + ")>>" + entry);
			return "pass";
		}
		
	}	
	
	public String selectFirstOptionById(String title, String dir, String loc, String xpath, int row, int col,
			String def, boolean migr, String a, String b, String c) {
		try {
			if (!xc.getCellString(row, col).equalsIgnoreCase("Do Not Enter")) {
				System.out.println("Processing Details: " + title + "(id)>>First Option");
				if (!dir.equals(""))
					mt.scrollToText(title, dir, x, y);
				if (migrCheckByIdDD(test, title, def, migr, a, b, c)) {
					appdriver.findElementById(loc).click();
					Thread.sleep(2000);
					appdriver.findElementByXPath(xpath).click();
					System.out.println("Field Processed");
					return "pass";
				}
				invalid_flag = true;
				System.out.println("Field Selection Failed");
				return "fail";

			} else {
				return "pass";
			}
		} catch (Exception e) {
			util.randomPressLogic.press(0.5, 0.05);
			System.out.println(e.getMessage());
			invalid_flag = true;
			System.out.println("Field Selection Failed");
			return "fail";
		}
	}

	public String validCheckString(ExtentTest test, String loc, String locStrat, int row, int col, String entry,
			String errid, boolean neg_test) {
		System.out.println("Validating field...");
		String s = "";
		String status = "pass";
		try {
			if (locStrat.equalsIgnoreCase("xpath")) {
				s = appdriver.findElementByXPath(loc).getText();
				if (!s.equals(entry))
					status = "fail";
			} else if (locStrat.equalsIgnoreCase("id")) {
				s = appdriver.findElementById(loc).getText();
				if (!s.equals(entry))
					status = "fail";
			} else if (locStrat.equalsIgnoreCase("UiSelectorText")) {
				s = appdriver.findElement(MobileBy.AndroidUIAutomator(loc)).getText();
				if (!s.equals(entry))
					status = "fail";
			}
		} catch (Exception e) {
			System.out.println("CANNOT VALIDATE FIELD!!!!");
			System.out.println(e.getMessage());
			status = "fail";
		}

		String err = "p";
		if (neg_test) {
			try {
				err = xc.getCellString(row, col + 1);
			} catch (Exception e) {
				err = "p";
			}
		}
		if (!err.equals("n")) {
			if (status.equals("fail")) {
				System.out.println("Validation should have passed but it failed");
				status = "fail";
			} else
				status = "pass";
		} else {
			if (status.equals("pass")) {
				System.out.println("Validation should have failed but it passed");
				status = "fail";
			} else
				status = "pass";
		}

		if (status.equals("fail")) {
			System.out.println(errid + ":||Validation Failed|| Field has " + s + " instead of " + entry);
			test.log(Status.INFO, errid + ":||Validation Failed|| Field has " + s + " instead of " + entry);
			try {
				ExtentManager.addScreenShotsToLogInfo("Entry failed"+entry, test);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Screenshot Cannot be saved");
				e.printStackTrace();
			}
		}

		System.out.println("...validation done");
		return status;
	}

	public String validCheckNum(ExtentTest test, String loc, String locStrat, int row, int col, String entry,
			String errid, boolean neg_test) {
		System.out.println("Validating field...");
		String s = "";
		String status = "pass";
		try {
			if (locStrat.equalsIgnoreCase("xpath")) {
				s = appdriver.findElementByXPath(loc).getText().replace(",", "");
				if (!s.equals(entry))
					status = "fail";
			} else if (locStrat.equalsIgnoreCase("id")) {
				s = appdriver.findElementById(loc).getText().replace(",", "");
				if (!s.equals(entry))
					status = "fail";
			} else if (locStrat.equalsIgnoreCase("UiSelectorText")) {
				s = appdriver.findElement(MobileBy.AndroidUIAutomator(loc)).getText().replace(",", "");
				if (!s.equals(entry))
					status = "fail";
			}
		} catch (Exception e) {
			System.out.println("CANNOT VALIDATE FIELD!!!!");
			System.out.println(e.getMessage());
			status = "fail";
		}

		String err = "p";
		if (neg_test) {
			try {
				err = xc.getCellString(row, col + 1);
			} catch (Exception e) {
				err = "p";
			}
		}
		if (!err.equals("n")) {
			if (status.equals("fail")) {
				System.out.println("Validation should have passed but it failed");
				status = "fail";
			} else
				status = "pass";
		} else {
			if (status.equals("pass")) {
				System.out.println("Validation should have failed but it passed");
				status = "fail";
			} else
				status = "pass";
		}

		if (status.equals("fail")) {
			System.out.println(errid + ":||Validation Failed|| Field has " + s + " instead of " + entry);
			test.log(Status.INFO, errid + ":||Validation Failed|| Field has " + s + " instead of " + entry);
			try {
				ExtentManager.addScreenShotsToLogInfo("Entry failed"+entry, test);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Screenshot Cannot be saved");
				e.printStackTrace();
			}
		}

		System.out.println("...validation done");
		return status;
	}
	
	public String validCheckDate(ExtentTest test, String loc, String locStrat, int row, int col, String entry,
			String errid, boolean neg_test) {
		System.out.println("Validating field...");
		String s = "";
		String status = "pass";
		try {
			if (locStrat.equalsIgnoreCase("xpath")) {
				s = appdriver.findElementByXPath(loc).getText().replace("_", "-");
				if (!s.equals(entry))
					status = "fail";
			} else if (locStrat.equalsIgnoreCase("id")) {
				s = appdriver.findElementById(loc).getText().replace("_", "-");
				if (!s.equals(entry))
					status = "fail";
			} else if (locStrat.equalsIgnoreCase("UiSelectorText")) {
				s = appdriver.findElement(MobileBy.AndroidUIAutomator(loc)).getText().replace("_", "-");
				if (!s.equals(entry))
					status = "fail";
			}
		} catch (Exception e) {
			System.out.println("CANNOT VALIDATE FIELD!!!!");
			System.out.println(e.getMessage());
			status = "fail";
		}

		String err = "p";
		if (neg_test) {
			try {
				err = xc.getCellString(row, col + 1);
			} catch (Exception e) {
				err = "p";
			}
		}
		if (!err.equals("n")) {
			if (status.equals("fail")) {
				System.out.println("Validation should have passed but it failed");
				status = "fail";
			} else
				status = "pass";
		} else {
			if (status.equals("pass")) {
				System.out.println("Validation should have failed but it passed");
				status = "fail";
			} else
				status = "pass";
		}

		if (status.equals("fail")) {
			System.out.println(errid + ":||Validation Failed|| Field has " + s + " instead of " + entry);
			test.log(Status.INFO, errid + ":||Validation Failed|| Field has " + s + " instead of " + entry);
			try {
				ExtentManager.addScreenShotsToLogInfo("Entry failed"+entry, test);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Screenshot Cannot be saved");
				e.printStackTrace();
			}
		}

		System.out.println("...validation done");
		return status;
	}


	public String checkTxtMsg(String txt_msg, int row, int col,boolean neg_test) throws Exception {

		System.out.println("Checking Popup text message...");
		try {
			String exp_err = "";
			String txt="";
			if (neg_test) {
				try {
					exp_err = xc.getCellString(row, col);
				} catch (Exception e) {
					exp_err = "";
				}
			}
			try {
			txt = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
			}catch(Exception e) {
				System.out.println("Cannot Detect Popup text");
				throw new Exception("Cannot Detect Popup text");
			}
			if (txt.equals("Data Updated Successfully") || txt.equals("Data saved successfully") || txt.equals(txt_msg)) {
				test.log(Status.INFO, "Pop-up(Successful Entry): "+txt);
				if(exp_err.equals(""))
					test.log(Status.PASS, txt);
				else
					test.log(Status.FAIL, txt);
				return "pass";
			}
			else {
				String ex = txt;
				System.out.println("Pop-up(Unsuccessful Entry) ");
				ExtentManager.addScreenShotsToLogFail("Pop-up text " + ex, test);
				System.out.println("Error: " + ex);
				if (!exp_err.equals("")) {
					if (exp_err.contains(ex)) {
						System.out.println("|||||||||||||||||||||||||||||");
						System.out.println("Expected Error is encountered");
						System.out.println("   ((Negetive Test Passed))");
						System.out.println("|||||||||||||||||||||||||||||");
						test.log(Status.PASS,"Negetive Test Passed: "+ ex);
					} else {
						System.out.println("   (((Negetive Test Failed)))\n");
						test.log(Status.FAIL, "Negetive Test Failed: "+ex);
					}
				}else
					test.log(Status.FAIL,  "Pop-up(Unsuccessful Entry) " + ex);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "fail";
	}
	
	public boolean invalidFlag() {
		return invalid_flag;
	}
	public void setInvalidFlag() {
		invalid_flag=true;
	}
	public void resetInvalidFlag() {
		invalid_flag=false;
	}
	public void errString(String status) throws Exception {
		if(status.equals("fail"))
			throw new Exception("<<Fail>>");
	}
	public void errBool(boolean status) throws Exception {
		if(status)
			throw new Exception("<<Fail>>");
	}

	public  void navigateBackToScreen(String screen_title) throws Exception {
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
			ExtentManager.addScreenShotsToTest("Navigate Back to Screen", test);
			System.out.println("Cannot navigate to " + screen_title + " screen");
			e.printStackTrace();
		}
	}
	
	public   void fillColumnFields(String entry,int memNum,String a, String b, String c) {
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
			mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + (k-2)+ b, "xpath", "top",x,y);
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String dateLogic(ExtentTest test,String title, String dir, String loc,int row, int col, String id,String errid,boolean neg_test,String def,boolean migr) {
		try {
			if (!xc.getCellString(row, col).equalsIgnoreCase("Do Not Enter")) {
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			Thread.sleep(1500);
			String entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir,x,y);
			try {
				if(migrCheckByXPath(test,loc,def,migr)) {
					entry=entry.replaceAll("_","-");
					if(migrCheckById(test,loc,def,migr))
						appdriver.findElementById(loc).sendKeys(entry);
				}
			} catch (Exception e) {
				String err = "p";
				if (neg_test) {
					try {
						err = xc.getCellString(row, col + 1);
					} catch (Exception e2) {
						err = "p";
					}
					if (!err.equals("n")) {
						System.out.println(e.getMessage());
						ExtentManager.addScreenShotsToLogFail("Failed Entry", test);
						System.out.println("Failed Field Entry");
						test.log(Status.FAIL, title + "(id" + id + ")>>" + xc.getCellString(row, col));
						invalid_flag=true;
						return "fail";

					} else {
						System.out.println("Field Processed");
						test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
						invalid_flag=true;
						return "pass";
					}
				}
			}
			String f = validCheckDate(test, loc, "id", row, col, entry, errid, neg_test);
			if (f.equals("fail"))
				throw new Exception("Validation Failed");
			}else {
				return "pass";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed Field Entry");
			test.log(Status.FAIL, title + "(id" + id + ")>>" + xc.getCellString(row, col));
			invalid_flag=true;
			return "fail";
		}
		System.out.println("Field Processed");
		test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
		return "pass";
	}
	
	public   void pseq(int id, String msg) {
		pass++;
		count++;
//		check[1][id]=1;
		test.log(Status.PASS, msg);
		System.out.println(msg);
		
	}

	public   void fseq(int id, String msg, Exception e) {
		fail++;
		count++;
//		check[1][id]=-1;
		invalid_flag=true;
		test.log(Status.FAIL, msg);
		System.out.println("Error in " + msg + "----------------------Check Here////");
		e.printStackTrace();
	}
	
	public void pf(int id,String s) {
		if(s.equals("fail")){
			fail++;
			count++;
//			check[1][id]=-1;
		}else if(s.equals("pass")) {
			pass++;
			count++;
//			check[1][id]=1;
		}
	}

	public void passInc() {
		pass++;
	}
	
	public void failInc() {
		fail++;
	}
	public void countInc() {
		count++;
	}
	
	public int[] pass_fail() {
		int[] n= {pass,fail,count};
		return n;
	}
	
	public void checkAdd(int id, String flag) {
		if(flag.equals("pass"))
			check[1][id]=1;
		else if(flag.equals("fail"))
			check[1][id]=-1;
	}
	
	public void summary(ExtentTest test) {
		util.summary.display(check, test);
	}
	
	public boolean migrCheckById(ExtentTest test,String loc, String def,boolean migr) {
		try {
			if(!migr)
				return true;
			String s = appdriver.findElementById(loc).getText();
			if (!s.equalsIgnoreCase(def)) {
				System.out.println("Migrated Data: " + s);
				test.log(Status.PASS, "Migrated Data: " + s);
				return true;
			} else {
				System.out.println("No Migrated Data...field entry from excel");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Migrated data not present or interactable field entry from excel");
			return false;
		}
	}
	
	public boolean migrCheckByXPath(ExtentTest test, String loc, String def,boolean migr) {
		try {
			if(!migr)
				return true;
			if (!def.equals("")) {
				String s = appdriver.findElementByXPath(loc).getText();
				if (!s.equalsIgnoreCase(def)) {
					System.out.println("Migrated Data: " + s);
					test.log(Status.PASS, "Migrated Data: " + s);
					return true;
				} else {
					System.out.println("No Migrated Data...field entry from excel");
					return false;
				}
			} else {
				System.out.println("No Migrated Data...field entry from excel");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Migrated data not present or interactable field entry from excel");
			return false;
		}
	}
	
	public boolean migrCheckByIdDD(ExtentTest test, String title,String def,boolean migr,String a,String b,String c) {
		try {
			if(!migr)
				return true;
			String s = extractTextDD(title,a,b,c);
			if (!s.equalsIgnoreCase(def)) {
				System.out.println("Migrated Data: " + s);
				test.log(Status.PASS, "Migrated Data: " + s);
				return true;
			} else {
				System.out.println("No Migrated Data...field entry from excel");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Migrated data not present or interactable field entry from excel");
			return false;
		}
	}
	
	public boolean migrCheckByXPathDD(ExtentTest test,String title, String def, boolean migr,String a,String b,String c) {
		try {
			if(!migr)
				return true;
			if (!def.equals("")) {
				String s = extractTextDD(title,a,b,c);
				if (!s.equalsIgnoreCase(def)) {
					System.out.println("|||||||||||||||>>Migrated Data: " + s);
					test.log(Status.PASS, "||||||||||||||>>Migrated Data: " + s);
					return true;
				} else {
					System.out.println("No Migrated Data...field entry from excel");
					return false;
				}
			} else {
				System.out.println("No Migrated Data...field entry from excel");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Migrated data not present or interactable field entry from excel");
			return false;
		}
	}
	
	public String extractTextDD(String title,String a, String b, String c) {
		
		try {
			java.util.List<AndroidElement> table = appdriver.findElementsByXPath(a);

			int k = 0;
			for (k = 1; k < table.size(); k++) {
				if (appdriver.findElementByXPath("//android.widget.LinearLayout[" + k + b).getText().equals(title)) 
					return appdriver.findElementByXPath("//android.widget.LinearLayout[" + k + c).getText();				
			}

		} catch (Exception e) {
			System.out.println("Extracting text from Dropdown not possible");
			System.out.println(e.getMessage());
		}
		return "";

	}
	
	public String extractCheckedRdBtn(String title, String a, String b, String c,int btn_num) {
		
		try {
			java.util.List<AndroidElement> table = appdriver.findElementsByXPath(a);

			int k = 0;
			for (k = 1; k < table.size(); k++) {
				if (appdriver.findElementByXPath("//android.widget.LinearLayout[" + k + b).getText().equals(title)) 
					 return appdriver.findElementByXPath("//android.widget.LinearLayout[" + k + c+"["+btn_num+"]").getAttribute("checked");				
			}

		} catch (Exception e) {
			System.out.println("Extracting checked from radio button not possible");
			System.out.println(e.getMessage());
		}
		return "";
	}
	
	public boolean migrCheckRdBtn(ExtentTest test,String title, String def, boolean migr,String a,String b,String c,int btn_num) {
		try {
			if(!migr)
				return true;
			if (!def.equals("")) {
				String s = extractCheckedRdBtn(title,a,b,c,btn_num);
				if (s.equalsIgnoreCase("true")) {
					System.out.println("|||||||||||||||>>Migrated Data is possiblein radio button>> " + def);
					test.log(Status.PASS, "||||||||||||||>>Migrated Data is possible in radio button>> " + def);
					return true;
				} else {
					System.out.println("No Migrated Data...field entry from excel");
					return false;
				}
			} else {
				System.out.println("No Migrated Data...field entry from excel");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Migrated data not present or interactable field entry from excel");
			return false;
		}
	}
	
	public String rdbtn(ExtentTest test, String title, String dir, int btn_num, int row, int col, String id,
			boolean neg_test, int def_btn_num,boolean migr,String a,String b, String c) {
		String entry="";
		String err = "p";
		if (neg_test) {
			try {
				err = xc.getCellString(row, col + 1);
			} catch (Exception e) {
				err = "p";
			}
		}
		try {
			if(xc.getCellString(row, col).equalsIgnoreCase("Do Not Enter")) {
			System.out.println("Processing Details: " + title + "(id" + id + ")>>"+xc.getCellString(row, col));
			entry = xc.getCellString(row, col);
			int k = 0;
			if (!dir.equals(""))
				mt.scrollToText(title, dir,0.40,0.65);
			try {
					if(true) {
							java.util.List<AndroidElement> table = appdriver.findElementsByXPath(a);							
							for (k = 1; k < table.size(); k++) {
								try	{
									if (!appdriver.findElementByXPath("//android.widget.LinearLayout/android.widget.LinearLayout[" + k + b).getText().equals(title)) {
										throw new Exception("Next Title");
									}
									System.out.println(appdriver.findElementByXPath("//android.widget.LinearLayout/android.widget.LinearLayout[" + k + b).getText());
									System.out.println(appdriver.findElementByXPath("//android.widget.LinearLayout/android.widget.LinearLayout[" + k + c+"["+def_btn_num+"]").getText());
									System.out.println(appdriver.findElementByXPath("//android.widget.LinearLayout/android.widget.LinearLayout[" + k + c+"["+def_btn_num+"]").getAttribute("checked"));
									System.out.println(appdriver.findElementByXPath("//android.widget.LinearLayout/android.widget.LinearLayout[" + k + c+"["+btn_num+"]").getText());
								}catch(Exception ex){
									ex.printStackTrace();
									continue;
								}
								if (appdriver.findElementByXPath("//android.widget.LinearLayout/android.widget.LinearLayout[" + k + b).getText().equals(title)) {
									 if(appdriver.findElementByXPath("//android.widget.LinearLayout/android.widget.LinearLayout[" + k + c+"["+def_btn_num+"]").getAttribute("checked").equalsIgnoreCase("true")) {
										if(migr) {	
										 System.out.println("|||||||||||||||>>Migrated Data is possible in radio button>> ");
											test.log(Status.PASS, "||||||||||||||>>Migrated Data is possible in radio button>> ");
											}
										appdriver.findElementByXPath("//android.widget.LinearLayout/android.widget.LinearLayout[" + k + c+"["+btn_num+"]").click();
										break;
										} else {
											System.out.println("No Migrated Data...field entry from excel");
											appdriver.findElementByXPath("//android.widget.LinearLayout/android.widget.LinearLayout[" + k + c+"["+btn_num+"]").click();
											break;	
										}
									 }	
							}
					}
//					else {
//						appdriver.findElementByXPath("//android.widget.LinearLayout[" + k + c+"["+btn_num+"]").click();
//					}
					
//				if(!migrCheckRdBtn(test,migr,title,def,a,b,c,def_btn_num)) {
//					appdriver.findElementByXPath("//android.widget.LinearLayout[" + k + c+"["+btn_num+"]").getAttribute("checked");
			}catch(Exception e) {
				System.out.println(e.getMessage());
				throw new Exception("Cannot detect option");
			}
			}else {
				return "pass";
			}
		} catch (Exception e) {			
			e.printStackTrace();
			System.out.println("Failed Selection");
			if (err.equals("n")) {
				test.log(Status.PASS, title + "(id" + id + ")>>"+entry);
				invalid_flag=true;
				return "pass";
			}else {
				test.log(Status.FAIL, title + "(id" + id + ")>>"+entry);
				invalid_flag=true;
				return "fail";
			}
		}
		System.out.println("Field Processed");
		if (err.equals("n")) {
			test.log(Status.FAIL, title + "(id" + id + ")>>"+entry);
			invalid_flag=true;
			return "fail";
		}else {
			test.log(Status.PASS, title + "(id" + id + ")>>"+entry);
			return "pass";
		}		
	}
	
	public boolean checkMigrFields(ExtentTest test,int row,int col,String box,String prefix,String delete,String edit,String add,String tick) {
		
		System.out.println("Checking for existing fields");
		int k=0;
		java.util.List<AndroidElement> table = appdriver.findElementsByXPath(box);
		for (k = 0; k < table.size(); k++) {
			if(xc.getCellString(row, col).contains("Delete")) {
				try {
				appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				appdriver.findElementByXPath(prefix+k+delete).click();
				System.out.println("Previous Record Deleted");
				return false;
				}catch(Exception e) {
					System.out.println("Previous Record Cannot Be Deleted");
				}
			}else if(xc.getCellString(row, col).contains("DeleteAdd")) {
				try {
					appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					appdriver.findElementByXPath(prefix+k+delete).click();
					System.out.println("Previous Record Deleted");
					}catch(Exception e) {
						System.out.println("Previous Record Cannot Be Deleted");
					}
				appdriver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
				appdriver.findElementById(add).click();
				return true;
			}else if(xc.getCellString(row, col).contains("Edit")) {
				try {
					appdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					appdriver.findElementByXPath(prefix+k+edit).click();
					System.out.println("Previous Record to be Edited");
					return true;
					}catch(Exception e) {
						System.out.println("Previous Record Cannot Be Edited");
					}
			}else if(xc.getCellString(row, col).contains("Check")) {
				try {
				appdriver.findElementByXPath(prefix+k+tick);
				System.out.println("Field is Had approved flag on it");
				return false;
				}catch(Exception e) {
					System.out.println("Field Do not have approved flag on it");
				}
			}else if(xc.getCellString(row, col).contains("CheckAdd")) {
				try {
					appdriver.findElementByXPath(prefix+k+tick);
					System.out.println("Field is Had approved flag on it");
					}catch(Exception e) {
						System.out.println("Field Do not have approved flag on it");
					}
				appdriver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
				appdriver.findElementById(add).click();
				return true;
			}else if(xc.getCellString(row, col).contains("Do Not Add")){
				System.out.println("Field not added");
				return false;
			}else {
				appdriver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
				appdriver.findElementById(add).click();
				return true;
			}
		}
		if(xc.getCellString(row, col).contains("Do Not Add")){
			System.out.println("Field not added");
			return false;
		}else if(xc.getCellString(row, col).contains("Add")){
			
			int k1 = 0;
			while (k1 < 6) {
				try {
					Thread.sleep(2000);
					appdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
					appdriver.findElementById(add).click();
					k1 = 6;
				} catch (Exception e) {
					System.out.println("Wasn't able to press the address button");
					k1++;
				}
			}
			
			return true;
		}	else
			return false;
		
	}

}
