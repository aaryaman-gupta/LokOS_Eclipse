package app;

import com.aventstack.extentreports.Status;

import lokos.lokosTest;

public class regularMeetings extends lokosTest {

	public static boolean neg_test_flag = false;
	public static int neg_test_count = 0;

	public static int[] idSelectRegular(int row) throws InterruptedException {

		String[] idList = { "000" };

		String type = xc.getCellString(row, cutoffCons.typeColNum);

		if (!type.equalsIgnoreCase("New")) {
			String[] typeList = xc.getCellString(row, cutoffCons.typeColNum).split(" ");
			idList = typeList[0].split(",");
		}

		int[] val = regularMeetings.regular(row, idList);
		return val;
	}
	
	@SuppressWarnings("unused")
	public static int[] regular(int row, String[] idList) throws InterruptedException {
		
		int count = 0;
		int pass = 0;
		int fail = 0;
		int p = 0;
		int f = 0;
		int id = 000;
		int oldMtngNum = 0;
		int newMtngNum = 0;

		int i = 0;
		if (Integer.valueOf(idList[1]) != 0) {
			for (String s : idList) {
				reg_check[0][Integer.valueOf(idList[i])] = Integer.valueOf(idList[i]);
				++i;
			}
		} else {
			for (int j = 0; j < reg_check[0].length; j++) {
				reg_check[0][j] = j;
			}
		}
		try {
			if (xc.getCellString(row, regCons.typeColNum).contains("Check")) {
				neg_test_flag = true;
				testMeet.log(Status.INFO, "NEGETIVE TESTING");
			}
		} catch (NullPointerException np) {			
			int[] val = { 0, 0, 0 };
			return val;
		}
		
		int[] val = { 0, 0, 0 };
		return val;
		
	}

}
