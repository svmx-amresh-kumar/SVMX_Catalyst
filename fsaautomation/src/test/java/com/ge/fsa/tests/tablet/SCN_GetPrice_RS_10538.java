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
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.tablet.ExploreSearchPO;
import com.ge.fsa.pageobjects.tablet.WorkOrderPO;

/**
 * 
 * @author meghanarao
 *
 */
public class SCN_GetPrice_RS_10538 extends BaseLib {
	String sTestCaseID= "Scenario_10538";
	String sAccountName = null;
	String sProductName = null;
	String sFirstName = null;
	String sLastName = null;
	String sContactName = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sIBName = null;
	String sPLinePricePerUnit = "10000";
	String sPCoveredPercent = "30";
	String sPBillingQty = "1.000";
	String sPBillableLinePrice = "7000.000";
	String sSheetName =null;
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10538() throws Exception {
		
		//commonsUtility.preReqSetup(genericLib);
		// Resinstall the app
		lauchNewApp("false");
		
		sSheetName ="RS_10538";
		//Thread.sleep(50000);
		System.out.println("SCN_GetPrice_RS_10538");
		
		loginHomePo.login(commonsUtility, exploreSearchPo);
		Thread.sleep(50000);

	//	 To run the Sahi Script before the Execution of Appium
		genericLib.executeSahiScript("appium/Scenario_10538.sah");
		if(commonsUtility.verifySahiExecution()) {
			
			System.out.println("PASSED");
		}
		else 
		{
			System.out.println("FAILED");
			

			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
			assertEquals(0, 1);
		}
		

		// Have a config Sync
		//toolsPo.configSync(commonsUtility);
		// Do a Data sync
		toolsPo.syncData(commonsUtility);
		Thread.sleep(genericLib.iMedSleep);
//		// Get the Work Order from the sheet
		String sTestDataValue = "SCN_GetPrice_RS_10538";
		String sworkOrderName = GenericLib.getExcelData(sTestDataValue,sSheetName, "Work Order Number");
		String sProductName = GenericLib.getExcelData(sTestDataValue,sSheetName, "Product Name ");
		System.out.println(sworkOrderName);
		workOrderPo.navigatetoWO(commonsUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		String sProcessname = "Record T&M";// Standard SFM Process
		workOrderPo.selectAction(commonsUtility,sProcessname);
		
		
	/**
	 * PARTS - Verification of Fields
	 */
		workOrderPo.addParts(commonsUtility, workOrderPo, sProductName);
		// To verify if Billing Type = Warranty
		String sBillingTypeValue = workOrderPo.getEleBillingTypeValue().getAttribute("value");
		Assert.assertEquals("Warranty", sBillingTypeValue);
		System.out.println(sBillingTypeValue);
		// Clicking on Get Price button for Parts
		commonsUtility.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonsUtility.tap(workOrderPo.getEleChildLineTapName(sProductName));
		//commonsUtility.tap(workOrderPo.getEleChildLineTapName(sProductName),10,10);
		Thread.sleep(10000);
		// Verify Each field value after the Get Price
		String sLinePricePerUnit = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sCoveredPercent = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit.equals(sPLinePricePerUnit))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Line Price Per Unit 1:Expected Value is"+sPLinePricePerUnit+"Actual Value is"+sLinePricePerUnit);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Line Price Per Unit 1:Expected Value is"+sPLinePricePerUnit+"Actual Value is"+sLinePricePerUnit, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Covered Percent Value verification
		if(sCoveredPercent.equals(sPCoveredPercent))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Covered Percent 1 :Expected Value is"+sPCoveredPercent+"Actual Value is"+sCoveredPercent);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Covered Percent 1 :Expected Value is"+sPCoveredPercent+"Actual Value is"+sCoveredPercent, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Billable Quantity Value verification
		if(sBillableQty.equals(sPBillingQty))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Billing Qty :Expected Value is"+sPBillingQty+"Actual Value is"+sBillableQty, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Billing Qty :Expected Value is"+sPBillingQty+"Actual Value is"+sBillableQty, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice.equals(sPBillableLinePrice))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Billable Line Price :Expected Value is"+sPBillableLinePrice+"Actual Value is"+sBillableLinePrice, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Billable Line Price :Expected Value is"+sPBillableLinePrice+"Actual Value is"+sBillableLinePrice, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		
		commonsUtility.tap(workOrderPo.getEleDoneBtn());
		
	/**
	 * PARTS - END OF PARTS VERIFICATION
	 */			
		
	/**
	 * LABOR - Verification of Fields AFTER THE GET PRICE
	*/
		
		int sEndDateint = Integer.parseInt("03") + 4;
		String sEndDate = Integer.toString(sEndDateint);
		System.out.println(sEndDate);

		workOrderPo.addLaborCustomizedDate(commonsUtility, workOrderPo,"Installation","03",sEndDate,"");
		commonsUtility.tap(workOrderPo.geteleGetPrice());
		if(commonsUtility.isDisplayedCust(workOrderPo.getEleDiscardChanges())) {
			commonsUtility.tap(workOrderPo.getEleDiscardChanges());
		}
		commonsUtility.tap(workOrderPo.getEleChildLineTapName("Installation"));
		if(commonsUtility.isDisplayedCust(workOrderPo.getEleDiscardChanges())) {
			commonsUtility.tap(workOrderPo.getEleDiscardChanges());
		}
		commonsUtility.tap(workOrderPo.getEleChildLineTapName("Installation"),10,10);
		if(commonsUtility.isDisplayedCust(workOrderPo.getEleDiscardChanges())) {
			commonsUtility.tap(workOrderPo.getEleDiscardChanges());
		}
		String sLinePricePUnit_labor = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		System.out.println(sLinePricePUnit_labor);
		String sCoveredPercent_labor = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty_labor = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_labor = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		if(sLinePricePUnit_labor.equals("1000"))
		{
			ExtentManager.logger.log(Status.PASS,"Labor : Line Price Per Unit: Expected Value :1000 Actual Value:"+sLinePricePUnit_labor, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Labor : Line Price Per Unit: Expected Value :1000 Actual Value:"+sLinePricePUnit_labor, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Covered Percent Value verification
		if(sCoveredPercent_labor.equals("20"))
		{
			ExtentManager.logger.log(Status.PASS,"Labor : Covered Percent: Expected Value :20 Actual Value:"+sCoveredPercent_labor, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Labor : Covered Percent: Expected Value :20 Actual Value:"+sCoveredPercent_labor, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Billable Quantity Value verification
		if(sBillableQty_labor.equals("4.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Labor : Billable Qty: Expected Value :4.000 Actual Value:"+sBillableQty_labor, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Labor : Billable Qty: Expected Value :4.000 Actual Value:"+sBillableQty_labor, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_labor.equals("3200.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Labor : Billable Line Price: Expected Value :3200.000 Actual Value:"+sBillableLinePrice_labor, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Labor : Billable Line Price: Expected Value :3200.000 Actual Value:"+sBillableLinePrice_labor, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		
		commonsUtility.tap(workOrderPo.getEleDoneBtn());
		
		
	// For Repair Labor Parts
		

		workOrderPo.addLaborCustomizedDate(commonsUtility, workOrderPo,"Repair","03",sEndDate,sProcessname);
		commonsUtility.tap(workOrderPo.geteleGetPrice());
		commonsUtility.tap(workOrderPo.getEleChildLineTapName("Repair"));
	//	commonsUtility.tap(workOrderPo.getEleChildLineTapName("Repair"),10,10);
		String sLinePricePUnit_labor2 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sCoveredPercent_labor2 = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty_labor2 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_labor2 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		if(sLinePricePUnit_labor2.equals("2000"))
		{
			ExtentManager.logger.log(Status.PASS,"Lobor :Line Price Per Unit: Expected Value :2000 Actual Value:"+sLinePricePUnit_labor2, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Lobor :Line Price Per Unit: Expected Value :2000 Actual Value:"+sLinePricePUnit_labor2, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Covered Percent Value verification
		if(sCoveredPercent_labor2.equals("20"))
		{
			ExtentManager.logger.log(Status.PASS,"Lobor :Covered Percent: Expected Value :20 Actual Value:"+sCoveredPercent_labor2, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Lobor :Covered Percent: Expected Value :20 Actual Value:"+sCoveredPercent_labor2, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Billable Quantity Value verification
		if(sBillableQty_labor2.equals("1"))
		{
			ExtentManager.logger.log(Status.PASS,"Lobor :Billable Qty: Expected Value :1 Actual Value:"+sBillableQty_labor2, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Lobor :Billable Qty: Expected Value :1 Actual Value:"+sBillableQty_labor2, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_labor2.equals("1600.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Lobor :Billable Line: Expected Value :1600.000 Actual Value:"+sBillableLinePrice_labor2, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Lobor :Billable Line: Expected Value :1600.000 Actual Value:"+sBillableLinePrice_labor2, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		
		commonsUtility.tap(workOrderPo.getEleDoneBtn());
		
	/**
	 * LABOR - END OF LABOR VERIFICATION
	*/
	/**
	 * EXPENSE - VERIFICATION OF THE FIELDS
	 */
		Thread.sleep(20000);	
		
		workOrderPo.addExpense(commonsUtility, workOrderPo,"Food - Dinner",sProcessname,"5","100");

		//Verifying the fields of Expenses
		
		commonsUtility.tap(workOrderPo.geteleGetPrice());
		commonsUtility.tap(workOrderPo.getEleChildLineTapName("Food - Dinner"));
		//commonsUtility.tap(workOrderPo.getEleChildLineTapName("Food - Dinner"),10,10);
		String sCoveredPercent_labor3 = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty_labor3 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_labor3 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		// Covered Percent Value verification
		if(sCoveredPercent_labor3.equals("10"))
		{
			ExtentManager.logger.log(Status.PASS,"Expense :Billable Line: Expected Value :10 Actual Value:"+sCoveredPercent_labor3, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Expense :Billable Line: Expected Value :10 Actual Value:"+sCoveredPercent_labor3, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Billable Quantity Value verification
		if(sBillableQty_labor3.equals("5.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Expense :Billable Qty: Expected Value :5.000 Actual Value:"+sBillableQty_labor3, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Expense :Billable Qty: Expected Value :5.000 Actual Value:"+sBillableQty_labor3, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_labor3.equals("450.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Expense :Billable Line Price: Expected Value :450.000 Actual Value:"+sBillableLinePrice_labor3, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Expense :Billable Line Price: Expected Value :450.000 Actual Value:"+sBillableLinePrice_labor3, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		
		commonsUtility.tap(workOrderPo.getEleDoneBtn());
		
		commonsUtility.tap(workOrderPo.getEleClickSave());
		// Verifying after sync the system
		toolsPo.syncData(commonsUtility);
		String sSoqlQueryChildlines = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')";
		restServices.getAccessToken();
		String sChildlines = restServices.restGetSoqlValue(sSoqlQueryChildlines, "totalSize");	
		if(sChildlines.equals("4"))
		{
			ExtentManager.logger.log(Status.PASS,"The Childlines After Sync is "+sChildlines);

		//NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines After Sync is "+sChildlinesAfter, LogAs.FAILED, null);

		System.out.println("The Childlines After Sync is "+sChildlines);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"The Childlines After Sync is "+sChildlines);

			//NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines After Sync is "+sChildlinesAfter, LogAs.PASSED, null);
			System.out.println("The Childlines After Sync is "+sChildlines);
		}
		
	}


}
