package com.ge.fsa.tests.tablet;

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

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.tablet.ExploreSearchPO;
import com.ge.fsa.pageobjects.tablet.WorkOrderPO;

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
	String sSheetName =null;
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10539() throws Exception {
		
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-10539");
		}else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12101");

		}
		sSheetName ="RS_10539";
		System.out.println("SCN_GetPriceSCON_RS_10539");
		String sProcessname = "RS_10539_getprice";// Standard SFM Process
		// To run the Sahi Script before the Execution of Appium - 10539
		commonUtility.executeSahiScript("appium/Scenario_10539.sah");
		
		
		loginHomePo.login(commonUtility, exploreSearchPo);
		// Have a config Sync
		toolsPo.configSync(commonUtility);
		// Do a Data sync
		toolsPo.syncData(commonUtility);
		
		// Get the Work Order from the sheet
		String sTestDataValue = "SCN_GetPriceSCON_RS_10539";
		sworkOrderName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName,"Work Order Number");
		System.out.println(sworkOrderName);
		sProductName1 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName,"Product1 Name");
		System.out.println(sProductName1);
		sProductName2 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName,"Product2 Name");
		System.out.println(sProductName2);
		sSCONName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName,"ServiceContract Name");
		System.out.println(sSCONName);
		workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		
		Thread.sleep(2000);
		workOrderPo.selectAction(commonUtility,sProcessname);
		
		
		/**
		 * TRAVEL - VERIFICATION OF THE FIELDS
		 */

		int sEndDateint1 = Integer.parseInt("03") + 4;
		String sEndDate1 = Integer.toString(sEndDateint1);

		workOrderPo.addTravelwithTime(commonUtility, workOrderPo, sProcessname, "03", sEndDate1);
		
		commonUtility.tap(workOrderPo.geteleGetPrice());
		
		
		String sTravelRateValue = workOrderPo.getEleTravelRateValue().getAttribute("value");
		Assert.assertEquals("800", sTravelRateValue);
		

		/**
		 * END OF TRAVEL VERIFICATION
		 */
		
		
		
	
	// PARTS - Verification of Fields
	 
		workOrderPo.addParts(commonUtility, workOrderPo, sProductName1);
		// To verify if Billing Type = Warranty
		
		String sBillingTypeValue = workOrderPo.getEleBillingTypeValue().getAttribute("value");
		Assert.assertEquals("Contract", sBillingTypeValue);
		System.out.println(sBillingTypeValue);
		// Clicking on Get Price button for Parts
		commonUtility.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonUtility.tap(workOrderPo.getEleChildLineTapName(sProductName1));
		commonUtility.tap(workOrderPo.getEleChildLineTapName(sProductName1),10,10);
		
		// Verify Each field value after the Get Price
		String sLinePricePerUnit1 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty1 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice1 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit1.equals("1000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Line Price Per Unit 1:Expected Value is 1000 Actual Value is"+sLinePricePerUnit1);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Line Price Per Unit 1:Expected Value is 1000 Actual Value is"+sLinePricePerUnit1, MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Quantity Value verification
		if(sBillableQty1.equals("1.000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Billable Quantity :Expected Value is 1.000 Actual Value is"+sBillableQty1);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Billable Quantity :Expected Value is 1.000 Actual Value is"+sBillableQty1, MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice1.equals("1000.000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Billable Line Price :Expected Value is 1000.000 Actual Value is"+sBillableLinePrice1);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Billable Line Price :Expected Value is 1000.000 Actual Value is"+sBillableLinePrice1, MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		
		
	// Second Parts verification
		workOrderPo.addParts(commonUtility, workOrderPo, sProductName2);
		// Clicking on Get Price button for Parts
		commonUtility.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonUtility.tap(workOrderPo.getEleChildLineTapName(sProductName2));
		commonUtility.tap(workOrderPo.getEleChildLineTapName(sProductName2),10,10);
		
		// Verify Each field value after the Get Price
		String sLinePricePerUnit2 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty2 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice2 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		String sDiscountpercent = workOrderPo.getelechildlinefields("Discount %").getAttribute("value");
		
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit2.equals("3000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Line Price Per Unit :Expected Value is 3000 Actual Value is"+sLinePricePerUnit2);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Line Price Per Unit :Expected Value is 3000 Actual Value is"+sLinePricePerUnit2,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Quantity Value verification
		if(sBillableQty2.equals("1.000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Billable Quantity :Expected Value is 1.000 Actual Value is"+sBillableQty2);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Billable Quantity :Expected Value is 1.000 Actual Value is"+sBillableQty2,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice2.equals("2550.000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Billable Line Price :Expected Value is 2550.000 Actual Value is"+sBillableLinePrice2);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Billable Line Price :Expected Value is 2550.000 Actual Value is"+sBillableLinePrice2,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		if(sDiscountpercent.equals("15"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Discount:Expected Value is 15 Actual Value is"+sDiscountpercent);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Discount :Expected Value is 15 Actual Value is"+sDiscountpercent,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		commonUtility.tap(workOrderPo.getEleDoneBtn());
	
	/**
	 * PARTS - END OF PARTS VERIFICATION
	 */			
		
	/**
	 * LABOR - Verification of Fields AFTER THE GET PRICE
	*/
//		Date localTime = new Date();
//		DateFormat df = new SimpleDateFormat("HH");
//		 
//		// Tell the DateFormat that I want to show the date in the IST timezone
//		df.setTimeZone(TimeZone.getTimeZone("GMT"));
//		String sdatehours = df.format(localTime);
		int sEndDateint = Integer.parseInt("03") + 2;
		String sEndDate = Integer.toString(sEndDateint);
		
		workOrderPo.addLaborCustomizedDate(commonUtility, workOrderPo,"Installation","03",sEndDate,sProcessname);
		commonUtility.tap(workOrderPo.geteleGetPrice());
		commonUtility.tap(workOrderPo.getEleChildLineTapName("Installation"));
		String sLinePricePUnit_labor = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		System.out.println(sLinePricePUnit_labor);
		String sCoveredPercent_labor = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty_labor = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_labor = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		if(sLinePricePUnit_labor.equals("1000"))
		{
			ExtentManager.logger.log(Status.PASS,"LABOR:Line Price Per Unit:Expected Value is 1000 Actual Value is"+sLinePricePUnit_labor);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"LABOR:Line Price Per Unit:Expected Value is 1000 Actual Value is"+sLinePricePUnit_labor,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Quantity Value verification
		if(sBillableQty_labor.equals("2.000"))
		{
			ExtentManager.logger.log(Status.PASS,"LABOR:Billable Quantity:Expected Value is 2.000 Actual Value is"+sBillableQty_labor);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"LABOR:Billable Quantity:Expected Value is 2.000 Actual Value is"+sBillableQty_labor,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_labor.equals("2000.000"))
		{
			ExtentManager.logger.log(Status.PASS,"LABOR:Billable Line Price:Expected Value is 2000.000 Actual Value is"+sBillableQty_labor);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"LABOR:Billable Line Price:Expected Value is 2000.000 Actual Value is"+sBillableQty_labor,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		
	// For Repair Labor Parts
		workOrderPo.addLaborCustomizedDate(commonUtility, workOrderPo,"Repair","03",sEndDate,sProcessname);
		commonUtility.tap(workOrderPo.geteleGetPrice());
		commonUtility.tap(workOrderPo.getEleChildLineTapName("Repair"));
		commonUtility.tap(workOrderPo.getEleChildLineTapName("Repair"),10,10);
		String sLinePricePUnit_labor2 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sCoveredPercent_labor2 = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty_labor2 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_labor2 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		if(sLinePricePUnit_labor2.equals("600"))
		{
			ExtentManager.logger.log(Status.PASS,"LABOR:Line Price Per Unit:Expected Value is 600 Actual Value is"+sLinePricePUnit_labor2);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"LABOR:Line Price Per Unit:Expected Value is 600 Actual Value is"+sLinePricePUnit_labor2,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Quantity Value verification
		if(sBillableQty_labor2.equals("1"))
		{
			ExtentManager.logger.log(Status.PASS,"LABOR:Billable quantity:Expected Value is 1 Actual Value is"+sBillableQty_labor2);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"LABOR:Billable quantity:Expected Value is 1 Actual Value is"+sBillableQty_labor2,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_labor2.equals("600.000"))
		{
			ExtentManager.logger.log(Status.PASS,"LABOR:Billable Line Price:Expected Value is 600.000  Actual Value is"+sBillableLinePrice_labor2);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"LABOR:Billable Line Price:Expected Value is 600.000  Actual Value is"+sBillableLinePrice_labor2,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		commonUtility.tap(workOrderPo.getEleDoneBtn());
	/**
	 * LABOR - END OF LABOR VERIFICATION
	*/
	/**
	 * EXPENSE - VERIFICATION OF THE FIELDS
	 */
		workOrderPo.addExpense(commonUtility, workOrderPo,"Food - Breakfast",sProcessname,"4","");
		//Verifying the fields of Expenses - 1
		
		commonUtility.tap(workOrderPo.geteleGetPrice());
		commonUtility.tap(workOrderPo.getEleChildLineTapName("Food - Breakfast"));
		String sBillableQty_labor3 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_labor3 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		

		// Billable Quantity Value verification
		if(sBillableQty_labor3.equals("4.000"))
		{
			ExtentManager.logger.log(Status.PASS,"EXPENSE:Billable Quantity:Expected Value is 4.000  Actual Value is"+sBillableQty_labor3);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"EXPENSE:Billable Quantity:Expected Value is 4.000  Actual Value is"+sBillableQty_labor3,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_labor3.equals("50.000"))
		{
			ExtentManager.logger.log(Status.PASS,"EXPENSE:Billable Line Price:Expected Value is 50.000  Actual Value is"+sBillableLinePrice_labor3);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"EXPENSE:Billable Line Price:Expected Value is 50.000  Actual Value is"+sBillableLinePrice_labor3,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		
		
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		// Expenses - 2
		workOrderPo.addExpense(commonUtility, workOrderPo,"Gas",sProcessname,"4","");
		//Verifying the fields of Expenses - 2
		
		commonUtility.tap(workOrderPo.geteleGetPrice());
		commonUtility.tap(workOrderPo.getEleChildLineTapName("Gas"));
		commonUtility.tap(workOrderPo.getEleChildLineTapName("Gas"),10,10);
		String sBillableQty_laborGas = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_laborGas = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		

		// Billable Quantity Value verification
		if(sBillableQty_laborGas.equals("4.000"))
		{
			ExtentManager.logger.log(Status.PASS,"EXPENSE:Billable Quantity:Expected Value is 4.000  Actual Value is"+sBillableQty_laborGas);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"EXPENSE:Billable Quantity:Expected Value is 4.000  Actual Value is"+sBillableQty_laborGas,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_laborGas.equals("320.000"))
		{
			ExtentManager.logger.log(Status.PASS,"EXPENSE:Billable Line Price:Expected Value is 320.000  Actual Value is"+sBillableLinePrice_laborGas);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"EXPENSE:Billable Line Price:Expected Value is 320.000  Actual Value is"+sBillableLinePrice_laborGas,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		
		// Expenses - 3
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		workOrderPo.addExpense(commonUtility, workOrderPo,"Airfare",sProcessname,"4","500");
		//Verifying the fields of Expenses - 3
		
		commonUtility.tap(workOrderPo.geteleGetPrice());
		commonUtility.tap(workOrderPo.getEleChildLineTapName("Airfare"));
		commonUtility.tap(workOrderPo.getEleChildLineTapName("Airfare"),10,10);
		String sBillableQty_Airfare = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_Airfare = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		

		// Billable Quantity Value verification
		if(sBillableQty_Airfare.equals("4.000"))
		{
			ExtentManager.logger.log(Status.PASS,"EXPENSE:Billable Quantity:Expected Value is 4.000  Actual Value is"+sBillableQty_Airfare);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"EXPENSE:Billable Quantity:Expected Value is 4.000  Actual Value is"+sBillableQty_Airfare,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_Airfare.equals("1800.000"))
		{
			ExtentManager.logger.log(Status.PASS,"EXPENSE:Billable Line Price:Expected Value is 1800.000  Actual Value is "+sBillableLinePrice_Airfare);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"EXPENSE:Billable Line Price:Expected Value is 1800.000  Actual Value is "+sBillableLinePrice_Airfare,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}

		commonUtility.tap(workOrderPo.getEleDoneBtn());
		
		if(commonUtility.isDisplayedCust(workOrderPo.getEleDiscardChanges())) {
			commonUtility.tap(workOrderPo.getEleDiscardChanges());
		}
		
		
		
		
		/*
		String sLinePricePerUnit_travel = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty_travel = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_travel = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		System.out.println(sLinePricePerUnit_travel);
		// Line Price Per Unit
		if(sLinePricePerUnit_travel.equals("150.000"))
		{
			ExtentManager.logger.log(Status.PASS,"TRAVEL:Line Price Per Unit:Expected Value is 150.000  Actual Value is"+sLinePricePerUnit_travel);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"TRAVEL:Line Price Per Unit:Expected Value is 150.000  Actual Value is"+sLinePricePerUnit_travel,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Quantity Value verification
		if(sBillableQty_travel.equals("3.000"))
		{
			ExtentManager.logger.log(Status.PASS,"TRAVEL:Billable Quantity:Expected Value is 3.000  Actual Value is"+sBillableQty_travel);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"TRAVEL:Billable Quantity:Expected Value is 3.000  Actual Value is"+sBillableQty_travel,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_travel.equals("450.000"))
		{
			ExtentManager.logger.log(Status.PASS,"TRAVEL:Billable Line Prices:Expected Value is 450.000  Actual Value is"+sBillableLinePrice_travel);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"TRAVEL:Billable Line Prices:Expected Value is 450.000  Actual Value is"+sBillableLinePrice_travel,MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}*/
		
	
		//commonUtility.tap(workOrderPo.getEleDoneBtn());
		commonUtility.tap(workOrderPo.getEleClickSave());
		// Verifying after sync the system
		toolsPo.syncData(commonUtility);
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
