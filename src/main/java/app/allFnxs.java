package app;

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

public class allFnxs {

	public xlsClasses xc = null;
	public AndroidDriver<AndroidElement> appdriver = null;
	public ExtentTest test = null;
	public MobileTouch mt = null;
	public DeviceUtil du = null;
	public int check[][]=null;
	public int pass=0;
	public int fail=0;
	public int count=0;
	
	public allFnxs(xlsClasses xc, AndroidDriver<AndroidElement> appdriver, ExtentTest test, MobileTouch mt, DeviceUtil du, int check[][]) {
	
		this.xc=xc;
		this.appdriver=appdriver;
		this.test=test;
		this.mt=mt;
		this.du=du;
		this.check=check;
	}
	
	
	public String enterStringById(ExtentTest test,String title, String dir, String loc, int row, int col, String id, String errid,Boolean neg_test) {
		String err="";
		if(neg_test) {
			try {
				err=xc.getCellString(row,col+1);
			}catch(Exception e) {
				err="p";
			}
		}
		
		try {
		System.out.println("Processing Details: "+title+"(id"+id+")");
		String entry=xc.getCellString(row, col);
		if(!dir.equals(""))
			mt.scrollToText(title, dir);
		appdriver.findElementById(loc).sendKeys(entry);
		String f = validCheckString(test, loc, "id", entry, errid);
		if (f.equals("fail"))
			throw new Exception("Validation Failed");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Failed Field Entry");
			if(!err.equals("n"))
				test.log(Status.FAIL, title+"(id"+id+")");
			else
				test.log(Status.PASS, title+"(id"+id+")");
			return "fail";
		}
		System.out.println("Field Processed");
		if(!err.equals("n"))
			test.log(Status.PASS, title+"(id"+id+")");
		else
			test.log(Status.FAIL, title+"(id"+id+")");
		return "pass";
	}

	public  String enterStringByXpath(ExtentTest test,String title, String dir, String loc, int row, int col, String id, String errid,boolean neg_test) {
		String err="p";
		if(neg_test) {
			try {
				err=xc.getCellString(row,col+1);
			}catch(Exception e) {
				err="p";
			}
		}
		try {
		System.out.println("Processing Details: "+title+"(id"+id+")");
		String entry=xc.getCellString(row, col);
		if(!dir.equals(""))
			mt.scrollToText(title, dir);
		appdriver.findElementByXPath(loc).sendKeys(entry);
		String f = validCheckString(test, loc, "xpath", entry, errid);
		if (f.equals("fail"))
			throw new Exception("Validation Failed");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Failed Field Entry");
			if(!err.equals("n"))
				test.log(Status.FAIL, title+"(id"+id+")");
			else
				test.log(Status.PASS, title+"(id"+id+")");
			return "fail";
		}
		System.out.println("Field Processed");

		if(!err.equals("n"))
			test.log(Status.PASS, title+"(id"+id+")");
		else
			test.log(Status.FAIL, title+"(id"+id+")");
		return "pass";
	}

	public String selectById(ExtentTest test,String title, String dir, String loc, int row, int col,String id,boolean neg_test) {
		@SuppressWarnings("unused")
		String err="p";
		if(neg_test) {
			try {
				err=xc.getCellString(row,col+1);
			}catch(Exception e) {
				err="p";
			}
		}
		try {
			System.out.println("Processing Details: " + title + "(id" + id + ")");
			String entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir);
			appdriver.findElementById(loc).click();
			appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + entry + "\")")).click();
		} catch (Exception e) {
			util.randomPressLogic.press(0.5, 0.05);
			e.printStackTrace();
			System.out.println("Failed Selection");
			test.log(Status.FAIL, title + "(id" + id + ")");
			return "fail";
		}
		System.out.println("Field Processed");
		test.log(Status.PASS, title + "(id" + id + ")");
		return "pass";
		
	}
	public String selectByXpath(ExtentTest test,String title, String dir, String loc, int row, int col, String id,boolean neg_test) {
		@SuppressWarnings("unused")
		String err="p";
		if(neg_test) {
			try {
				err=xc.getCellString(row,col+1);
			}catch(Exception e) {
				err="p";
			}
		}
		try {
			System.out.println("Processing Details: " + title + "(id" + id + ")");
			String entry = xc.getCellString(row, col);
			if (!dir.equals(""))
				mt.scrollToText(title, dir);
			appdriver.findElementByXPath(loc).click();
			appdriver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + entry + "\")")).click();
		} catch (Exception e) {
			util.randomPressLogic.press(0.5, 0.05);
			e.printStackTrace();
			System.out.println("Failed Selection");
			test.log(Status.FAIL, title + "(id" + id + ")");
			return "fail";
		}
		System.out.println("Field Processed");
		test.log(Status.PASS, title + "(id" + id + ")");
		return "pass";
	}
	public String selectCustomById(ExtentTest test,String title, String dir, String loc, String xpath,int row,int col, String id,boolean neg_test) {
		@SuppressWarnings("unused")
		String err="p";
		if(neg_test) {
			try {
				err=xc.getCellString(row,col+1);
			}catch(Exception e) {
				err="p";
			}
		}
		mt.scrollToText(title, dir);
		appdriver.findElementById(loc).click();
		appdriver.findElementByXPath(xpath).click();
		try {
			System.out.println("Processing Details: " + title + "(id" + id + ")");
			if (!dir.equals(""))
				mt.scrollToText(title, dir);
			appdriver.findElementById(loc).click();
			appdriver.findElementByXPath(xpath).click();
		} catch (Exception e) {
			util.randomPressLogic.press(0.5, 0.05);
			e.printStackTrace();
			System.out.println("Failed Selection");
			test.log(Status.FAIL, title + "(id" + id + ")");
			return "fail";
		}
		System.out.println("Field Processed");
		test.log(Status.PASS, title + "(id" + id + ")");
		return "pass";
	}
	public String selectCustomByXPath(ExtentTest test,String title, String dir, String loc, String xpath,int row, int col,String id,boolean neg_test) {
		@SuppressWarnings("unused")
		String err="p";
		if(neg_test) {
			try {
				err=xc.getCellString(row,col+1);
			}catch(Exception e) {
				err="p";
			}
		}
		mt.scrollToText(title, dir);
		appdriver.findElementById(loc).click();
		appdriver.findElementByXPath(xpath).click();
		try {
			System.out.println("Processing Details: " + title + "(id" + id + ")");
			if (!dir.equals(""))
				mt.scrollToText(title, dir);
			appdriver.findElementByXPath(loc).click();
			appdriver.findElementByXPath(xpath).click();
		} catch (Exception e) {
			util.randomPressLogic.press(0.5, 0.05);
			e.printStackTrace();
			System.out.println("Failed Selection");
			test.log(Status.FAIL, title + "(id" + id + ")");
			return "fail";
		}
		System.out.println("Field Processed");
		test.log(Status.PASS, title + "(id" + id + ")");
		return "pass";
	}
	
	public  String validCheckString(ExtentTest test,String loc, String locStrat, String entry, String errid) {
		System.out.println("Validating field...");
		int i = 0;
		String s = "";
		try {			
			if (locStrat.equalsIgnoreCase("xpath")) {
				s = appdriver.findElementByXPath(loc).getText();
				if (!s.equals(entry))
					i = 1;
			} else if (locStrat.equalsIgnoreCase("id")) {
				s = appdriver.findElementById(loc).getText();
				if (!s.equals(entry))
					i = 1;
			} else if (locStrat.equalsIgnoreCase("UiSelectorText")) {
				s = appdriver.findElement(MobileBy.AndroidUIAutomator(loc)).getText();
				if (!s.equals(entry))
					i = 1;
			}			
		} catch (Exception e) {
			System.out.println("CANNOT VALIDATE FIELD!!!!");
			e.printStackTrace();
			return "fail";
		}
		if (i == 1) {
			System.out.println(errid + ":||Validation Failed||Field has " + s + " instead of " + entry);
			test.log(Status.INFO, errid);
			return "fail";
		}		
		System.out.println("...validation done");
		return "pass";
	}

	public String checktTxtMsg(String txt_msg, int row, int col) throws Exception {

		System.out.println("Checking Popup text message...");
		try {
			String exp_err="";
			try {
					exp_err=xc.getCellString(row,col);
				}catch(Exception e) {
					exp_err="";
				}
			
			String txt = appdriver.findElementById("com.microware.cdfi:id/txt_msg").getText();
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
			e.printStackTrace();
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
