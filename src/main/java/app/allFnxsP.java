package app;

import java.io.IOException;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import readingXLS.xlsClasses;
import reporting.ExtentManager;
import util.DeviceUtil;
import util.MobileTouch;
import util.backButton;

public class allFnxsP {

	public xlsClasses xc = null;
	public AndroidDriver<AndroidElement> appdriver = null;
	public ExtentTest test = null;
	public MobileTouchADV mt = null;
	public DeviceUtil du = null;
	public int check[][]=null;
	public int pass=0;
	public int fail=0;
	public int count=0;
	
	public allFnxsP(xlsClasses xc, AndroidDriver<AndroidElement> appdriver, ExtentTest test, MobileTouch mt, DeviceUtil du, int check[][]) {
	
		this.xc=xc;
		this.appdriver=appdriver;
		this.test=test;
		this.mt=mt;
		this.du=du;
		this.check=check;
	}
	
	
	public String enterStringById(ExtentTest test, String title, String dir, String loc, int row, int col, String id,
			String errid, boolean neg_test) {
		try {
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			Thread.sleep(1500);
			String entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir);
			appdriver.findElementById(loc).clear();
			try {
				appdriver.findElementById(loc).sendKeys(entry);
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
						return "fail";

					} else {
						System.out.println("Field Processed");
						test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
						return "pass";
					}
				}
			}
			String f = validCheckString(test, loc, "id", row, col, entry, errid, neg_test);
			if (f.equals("fail"))
				throw new Exception("Validation Failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed Field Entry");
			test.log(Status.FAIL, title + "(id" + id + ")>>" + xc.getCellString(row, col));
			return "fail";
		}
		System.out.println("Field Processed");
		test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
		return "pass";
	}

	public String enterStringByXpath(ExtentTest test, String title, String dir, String loc, int row, int col, String id,
			String errid, boolean neg_test) {
		try {
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			Thread.sleep(1500);
			String entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir);
			appdriver.findElementById(loc).clear();
			try {
				appdriver.findElementById(loc).sendKeys(entry);
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
						return "fail";

					} else {
						System.out.println("Field Processed");
						test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
						return "pass";
					}
				}
			}
			String f = validCheckString(test, loc, "id", row, col, entry, errid, neg_test);
			if (f.equals("fail"))
				throw new Exception("Validation Failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed Field Entry");
			test.log(Status.FAIL, title + "(id" + id + ")>>" + xc.getCellString(row, col));
			return "fail";
		}
		System.out.println("Field Processed");
		test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
		return "pass";
	}

	public String enterNumById(ExtentTest test, String title, String dir, String loc, int row, int col, String id,
			String errid, boolean neg_test) {

		try {
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			Thread.sleep(1500);
			String entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir);
			appdriver.findElementById(loc).clear();
			try {
				appdriver.findElementById(loc).sendKeys(entry);
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
						return "fail";

					} else {
						System.out.println("Field Processed");
						test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
						return "pass";
					}
				}
			}
			String f = validCheckNum(test, loc, "id", row, col, entry, errid, neg_test);
			if (f.equals("fail"))
				throw new Exception("Validation Failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed Field Entry");
			test.log(Status.FAIL, title + "(id" + id + ")>>" + xc.getCellString(row, col));
			return "fail";
		}
		System.out.println("Field Processed");
		test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
		return "pass";
	}

	public String enterNumByXpath(ExtentTest test, String title, String dir, String loc, int row, int col, String id,
			String errid, boolean neg_test) {

		try {
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			Thread.sleep(1500);
			String entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir);
			appdriver.findElementByXPath(loc).clear();
			try {
				appdriver.findElementByXPath(loc).sendKeys(entry);
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
						return "fail";

					} else {
						System.out.println("Field Processed");
						test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
						return "pass";
					}
				}
			}
			String f = validCheckNum(test, loc, "xpath", row, col, entry, errid, neg_test);
			if (f.equals("fail"))
				throw new Exception("Validation Failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed Field Entry");
			test.log(Status.FAIL, title + "(id" + id + ")>>" + xc.getCellString(row, col));
			return "fail";
		}
		System.out.println("Field Processed");
		test.log(Status.PASS, title + "(id" + id + ")>>" + xc.getCellString(row, col));
		return "pass";
	}

	public String selectById(ExtentTest test, String title, String dir, String loc, int row, int col, String id,
			boolean neg_test) {
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
			System.out.println("Processing Details: " + title + "(id" + id + ")>>"+xc.getCellString(row, col));
			entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir);
			appdriver.findElementById(loc).click();
			try {
			appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + entry + "\")")).click();
			}catch(Exception e) {
				System.out.println(e.getMessage());
				backButton.back();
				throw new Exception("Cannot detect option");
			}
		} catch (Exception e) {			
			e.printStackTrace();
			System.out.println("Failed Selection");
			if (err.equals("n")) {
				test.log(Status.PASS, title + "(id" + id + ")>>"+entry);
				return "pass";
			}else {
				test.log(Status.FAIL, title + "(id" + id + ")>>"+entry);
				return "fail";
			}
		}
		System.out.println("Field Processed");
		if (err.equals("n")) {
			test.log(Status.FAIL, title + "(id" + id + ")>>"+entry);
			return "fail";
		}else {
			test.log(Status.PASS, title + "(id" + id + ")>>"+entry);
			return "pass";
		}		
	}

	public String selectByXPath(ExtentTest test, String title, String dir, String loc, int row, int col, String id,
			boolean neg_test) {
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
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir);
			appdriver.findElementByXPath(loc).click();
			try {
				appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + entry + "\")")).click();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				backButton.back();
				throw new Exception("Cannot detect option");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed Selection");
			if (err.equals("n")) {
				test.log(Status.PASS, title + "(id" + id + ")>>" + entry);
				return "pass";
			} else {
				test.log(Status.FAIL, title + "(id" + id + ")>>" + entry);
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


	public String selectCustomById(ExtentTest test, String title, String dir, String loc, String xpath, int row,
			int col, String id, boolean neg_test) {
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
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir);
			appdriver.findElementById(loc).click();
			try {
				appdriver.findElementByXPath(xpath).click();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				backButton.back();
				throw new Exception("Cannot detect option");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed Selection");
			if (err.equals("n")) {
				test.log(Status.PASS, title + "(id" + id + ")>>" + entry);
				return "pass";
			} else {
				test.log(Status.FAIL, title + "(id" + id + ")>>" + entry);
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

	public String selectCustomByXPath(ExtentTest test, String title, String dir, String loc, String xpath, int row,
			int col, String id, boolean neg_test) {
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
			System.out.println("Processing Details: " + title + "(id" + id + ")>>" + xc.getCellString(row, col));
			entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir);
			appdriver.findElementByXPath(loc).click();
			try {
				appdriver.findElementByXPath(xpath).click();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				backButton.back();
				throw new Exception("Cannot detect option");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed Selection");
			if (err.equals("n")) {
				test.log(Status.PASS, title + "(id" + id + ")>>" + entry);
				return "pass";
			} else {
				test.log(Status.FAIL, title + "(id" + id + ")>>" + entry);
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
	
	public void selectFirstOptionById(String title, String dir, String loc, String xpath) {
		try {
		mt.scrollToText(title, dir);
		appdriver.findElementById(loc).click();
		appdriver.findElementByXPath(xpath).click();
		}
		catch(Exception e) {
			util.randomPressLogic.press(0.5, 0.05);
			System.out.println(e.getMessage());
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
			mt.scrollToVisibleElementOnScreen("//android.widget.LinearLayout[" + (k-2)+ b, "xpath", "top");
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public   void pseq(int id, String msg) {
		pass++;
		count++;
		check[1][id]=1;
		test.log(Status.PASS, msg);
		System.out.println(msg);
	}

	public   void fseq(int id, String msg, Exception e) {
		fail++;
		count++;
		check[1][id]=-1;
		test.log(Status.FAIL, msg);
		System.out.println("Error in " + msg + "----------------------Check Here////");
		e.printStackTrace();
	}

	public void passInc() {
		pass++;
	}
	
	public void failInc() {
		fail++;
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

}
