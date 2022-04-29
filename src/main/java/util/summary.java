package util;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class summary {

	public static void display(int[][] check, ExtentTest test) {
		test.log(Status.INFO, "---|Summary of Node|---");
		for (int i = 1; i < check[0].length; i++) {
			if (check[0][i] != 0) {
				if (check[1][i] == -1)
					test.log(Status.INFO, "     ID(" + check[0][i] + "):  FAIL");
				else if (check[1][i] == 1)
					test.log(Status.INFO, "     ID(" + check[0][i] + "):  PASS");
			}
		}
		test.log(Status.INFO, "---|Summary of Node|---");
	}
}
