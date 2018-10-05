package com.ge.fsa.tests;

import static org.testng.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.ExploreSearchPO;
import com.ge.fsa.pageobjects.WorkOrderPO;

/**
 * 
 * @author meghanarao
 *
 */
public class SCN_GetPriceSCON_RS_10539 extends BaseLib {
	String sTestCaseID= "Scenario_10539";
	String sAccountName = null;
	String sProductName1 = null;
	String sProductName2 = null;
	String sworkOrderName = null;
	String sSCONName = null;
	String sContactName = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sIBName = null;

	@Test(enabled = true)
	public void RS_10539() throws Exception {
	
		System.out.println("SCN_GetPriceSCON_RS_10539");
		// To run the Sahi Script before the Execution of Appium - 10539
//		genericLib.executeSahiScript("appium/Scenario_10539.sah", "sTestCaseID");
//		if(commonsPo.verifySahiExecution()) {
//			
//			System.out.println("PASSED");
//		}
//		else 
//		{
//			System.out.println("FAILED");
//			
//
//			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
//			assertEquals(0, 1);
//		}
//		
		loginHomePo.login(commonsPo, exploreSearchPo);
		// Have a config Sync

		//toolsPo.configSync(commonsPo);
		// Do a Data sync
		//toolsPo.syncData(commonsPo);
		// Get the Work Order from the sheet
		String sTestDataValue = "SCN_GetPriceSCON_RS_10539";
		sworkOrderName = GenericLib.getExcelData(sTestDataValue,"Work Order Number");
		sProductName1 = GenericLib.getExcelData(sTestDataValue,"Product1 Name");
		System.out.println(sProductName1);
		sProductName2 = GenericLib.getExcelData(sTestDataValue,"Product2 Name");
		System.out.println(sProductName2);
		sSCONName = GenericLib.getExcelData(sTestDataValue,"ServiceContract Name");
		System.out.println(sSCONName);
		workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		String sProcessname = "Record T&M";// Standard SFM Process
		Thread.sleep(2000);
		workOrderPo.selectAction(commonsPo,sProcessname);
		
		
	/**
	 * PARTS - Verification of Fields
	 */
		workOrderPo.addParts(commonsPo, workOrderPo, sProductName1);
		// To verify if Billing Type = Warranty
		String sBillingTypeValue = workOrderPo.getEleBillingTypeValue().getAttribute("value");
		Assert.assertEquals("Contract", sBillingTypeValue);
		System.out.println(sBillingTypeValue);
		// Clicking on Get Price button for Parts
		commonsPo.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonsPo.tap(workOrderPo.getEleChildLineTapName(sProductName1));
		
		// Verify Each field value after the Get Price
		String sLinePricePerUnit1 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty1 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice1 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit1.equals("1000"))
		{
			ExtentManager.logger.log(Status.PASS,"Line Price Per Unit is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Line Price Per Unit is not as Expected - Part");
		}
		// Billable Quantity Value verification
		if(sBillableQty1.equals("1.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected - Part");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice1.equals("1000.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected - Part");
		}
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		
		
	// Second Parts verification
		workOrderPo.addParts(commonsPo, workOrderPo, sProductName2);
		// Clicking on Get Price button for Parts
		commonsPo.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonsPo.tap(workOrderPo.getEleChildLineTapName(sProductName2));
		
		// Verify Each field value after the Get Price
		String sLinePricePerUnit2 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty2 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice2 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		String sDiscountpercent = workOrderPo.getelechildlinefields("Discount %").getAttribute("value");
		
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit2.equals("3000"))
		{
			ExtentManager.logger.log(Status.PASS,"Line Price Per Unit is as Expected - Part ");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Line Price Per Unit is not as Expected - Part");
		}
		// Billable Quantity Value verification
		if(sBillableQty2.equals("1.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected - Part");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice2.equals("2550.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected - Part");
		}
		if(sDiscountpercent.equals("15"))
		{
			ExtentManager.logger.log(Status.PASS,"Discount Percent is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Discount Percent is not as Expected - Part");
		}
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		
	/**
	 * PARTS - END OF PARTS VERIFICATION
	 */			
		
	/**
	 * LABOR - Verification of Fields AFTER THE GET PRICE
	*/
		Date localTime = new Date();
		DateFormat df = new SimpleDateFormat("HH");
		 
		// Tell the DateFormat that I want to show the date in the IST timezone
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		String sdatehours = df.format(localTime);
		int sEndDateint = Integer.parseInt(sdatehours) + 2;
		String sEndDate = Integer.toString(sEndDateint);
		
		workOrderPo.addLaborCustomizedDate(commonsPo, workOrderPo,"Installation","0",sEndDate,sProcessname);
		commonsPo.tap(workOrderPo.geteleGetPrice());
		commonsPo.tap(workOrderPo.getEleChildLineTapName("Installation"));
		String sLinePricePUnit_labor = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		System.out.println(sLinePricePUnit_labor);
		String sCoveredPercent_labor = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty_labor = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_labor = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		if(sLinePricePUnit_labor.equals("500"))
		{
			ExtentManager.logger.log(Status.PASS,"Line Price Per Unit is as Expected - Labor");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Line Price Per Unit is not as Expected- Labor");
		}
		// Billable Quantity Value verification
		if(sBillableQty_labor.equals("2.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected- Labor");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected- Labor");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_labor.equals("1000.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected- Labor");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected- Labor");
		}
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		
	// For Repair Labor Parts
		workOrderPo.addLaborCustomizedDate(commonsPo, workOrderPo,"Repair","0",sEndDate,sProcessname);
		commonsPo.tap(workOrderPo.geteleGetPrice());
		commonsPo.tap(workOrderPo.getEleChildLineTapName("Repair"));
		String sLinePricePUnit_labor2 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sCoveredPercent_labor2 = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty_labor2 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_labor2 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		if(sLinePricePUnit_labor2.equals("600"))
		{
			ExtentManager.logger.log(Status.PASS,"Line Price Per Unit is as Expected- Labor");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Line Price Per Unit is not as Expected- Labor");
		}
		// Billable Quantity Value verification
		if(sBillableQty_labor2.equals("1"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected- Labor");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected- Labor");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_labor2.equals("600.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected- Labor");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected- Labor");
		}
		commonsPo.tap(workOrderPo.getEleDoneBtn());
	/**
	 * LABOR - END OF LABOR VERIFICATION
	*/
	/**
	 * EXPENSE - VERIFICATION OF THE FIELDS
	 */
		workOrderPo.addExpense(commonsPo, workOrderPo,"Food - Breakfast",sProcessname,"4","");
		//Verifying the fields of Expenses - 1
		
		commonsPo.tap(workOrderPo.geteleGetPrice());
		commonsPo.tap(workOrderPo.getEleChildLineTapName("Food - Breakfast"));
		String sBillableQty_labor3 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_labor3 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		

		// Billable Quantity Value verification
		if(sBillableQty_labor3.equals("4.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected- Expense");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected- Expense");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_labor3.equals("50.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected- Expense");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected- Expense");
		}
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		// Expenses - 2
		workOrderPo.addExpense(commonsPo, workOrderPo,"Gas",sProcessname,"4","");
		//Verifying the fields of Expenses - 2
		
		commonsPo.tap(workOrderPo.geteleGetPrice());
		commonsPo.tap(workOrderPo.getEleChildLineTapName("Gas"));
		String sBillableQty_laborGas = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_laborGas = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		

		// Billable Quantity Value verification
		if(sBillableQty_laborGas.equals("4.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected- Expense");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected- Expense");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_laborGas.equals("320.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected- Expense");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected- Expense");
		}
		
		// Expenses - 3
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		workOrderPo.addExpense(commonsPo, workOrderPo,"Airfare",sProcessname,"4","500");
		//Verifying the fields of Expenses - 3
		
		commonsPo.tap(workOrderPo.geteleGetPrice());
		commonsPo.tap(workOrderPo.getEleChildLineTapName("Airfare"));
		String sBillableQty_Airfare = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_Airfare = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		

		// Billable Quantity Value verification
		if(sBillableQty_Airfare.equals("4.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected- Expense");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected- Expense");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_Airfare.equals("1800.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected- Expense");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected- Expense");
		}
		
		/**
		 * TRAVEL - VERIFICATION OF THE FIELDS
		 */
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		Date localTime1 = new Date();
		DateFormat df1 = new SimpleDateFormat("HH");
		 
		// Tell the DateFormat that I want to show the date in the IST timezone
		df1.setTimeZone(TimeZone.getTimeZone("GMT"));
		String sdatehours1 = df1.format(localTime1);


		int sEndDateint1 = Integer.parseInt(sdatehours1) + 4;
		String sEndDate1 = Integer.toString(sEndDateint1);

		workOrderPo.addTravelwithTime(commonsPo, workOrderPo, sProcessname, "0", sEndDate1);
		
		commonsPo.tap(workOrderPo.geteleGetPrice());
		commonsPo.tap(workOrderPo.geteleTraveltap(sEndDate1));
		String sLinePricePerUnit_travel = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty_travel = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_travel = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		System.out.println(sLinePricePerUnit_travel);
		// Line Price Per Unit
		if(sLinePricePerUnit_travel.equals("150.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected- Travel");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected- Travel");
		}
		// Billable Quantity Value verification
		if(sBillableQty_travel.equals("3.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected- Travel");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected- Travel");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_travel.equals("450.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected- Travel");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected- Travel");
		}
		
		/**
		 * END OF TRAVEL VERIFICATION
		 */
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		commonsPo.tap(workOrderPo.getEleClickSave());
		// Verifying after sync the system
		toolsPo.syncData(commonsPo);
		String sSoqlQueryChildlines = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')";
		restServices.getAccessToken();
		String sChildlines = restServices.restGetSoqlValue(sSoqlQueryChildlines, "totalSize");	
		if(sChildlines.equals("8"))
		{
			ExtentManager.logger.log(Status.PASS,"The Childlines After Sync is "+sChildlines);

		

		System.out.println("The Childlines After Sync is "+sChildlines);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"The Childlines After Sync is "+sChildlines);

			
			System.out.println("The Childlines After Sync is "+sChildlines);
		}
		
	}

}
