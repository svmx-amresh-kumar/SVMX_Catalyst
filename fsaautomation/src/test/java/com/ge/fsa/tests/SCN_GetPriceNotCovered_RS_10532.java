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
public class SCN_GetPriceNotCovered_RS_10532 extends BaseLib {
	String sTestCaseID= "Scenario_10532";
	String sAccountName = null;
	String sProductName = null;
	String sworkOrderName = null;
	String sIBName = null;
	String sContactName = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sProductName2 = null;


	@Test(enabled = true)
	public void RS_10532() throws Exception {
	
		System.out.println("SCN_GetPriceNotCovered_RS_10532");
//		genericLib.executeSahiScript("appium/Scenario_10532.sah", "sTestCaseID");
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
		loginHomePo.login(commonsPo, exploreSearchPo);
		// Have a config Sync
		//toolsPo.configSync(commonsPo);
//		// Do a Data sync
		//toolsPo.syncData(commonsPo);
		// Get the Work Order from the sheet
		String sTestDataValue1 = "SCN_GetPrice_RS_10538";
		String sTestDataValue3 = "SCN_GetPriceSCON_RS_10539";
		sProductName = GenericLib.getExcelData(sTestDataValue1,"Product Name ");
		System.out.println(sProductName);
		
		sProductName2 = GenericLib.getExcelData(sTestDataValue3,"Product2 Name");
		System.out.println(sProductName2);
		sIBName = GenericLib.getExcelData(sTestDataValue1,"Installed Product Name");
		System.out.println(sIBName);	
		
		String sTestDataValue2 = "SCN_GetPriceNotCovered_RS_10532";
		String sworkOrderName = GenericLib.getExcelData(sTestDataValue2,"Work Order Number");
	
		// To navigate to the Work Order
		workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		String sProcessname = "EditWoAutoTimesstamp";// Standard SFM Process
		Thread.sleep(2000);
		workOrderPo.selectAction(commonsPo,sProcessname);
		
		/**
		 * PARTS - Verification of Fields
		 */
		workOrderPo.addParts(commonsPo, workOrderPo, sProductName);
		// To verify if Billing Type = Warranty
		String sBillingTypeValue = workOrderPo.getEleBillingTypeValue().getAttribute("value");
		Assert.assertEquals("--None--", sBillingTypeValue);
		System.out.println(sBillingTypeValue);
		// Clicking on Get Price button for Parts
		commonsPo.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonsPo.tap(workOrderPo.getEleChildLineTapName(sProductName));
		
		String sLinePricePerUnit = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sCoveredPercent = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit.equals("10000"))
		{
			ExtentManager.logger.log(Status.PASS,"Line Price Per Unit is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Line Price Per Unit is not as Expected");
		}
		// Covered Percent Value verification
		if(sCoveredPercent.equals("0"))
		{
			ExtentManager.logger.log(Status.PASS,"Covered % is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Covered % is not as Expected");
		}
		// Billable Quantity Value verification
		if(sBillableQty.equals("1.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice.equals("10000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected");
		}
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		
		
		/**
		 * PARTS - Verification of Fields
		 */
		workOrderPo.addParts(commonsPo, workOrderPo, sProductName2);
		// To verify if Billing Type = Warranty
		// Clicking on Get Price button for Parts
		commonsPo.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonsPo.tap(workOrderPo.getEleChildLineTapName(sProductName2));
		
		String sLinePricePerUnit2 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sCoveredPercent2 = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty2= workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice2 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit2.equals("3000"))
		{
			ExtentManager.logger.log(Status.PASS,"Line Price Per Unit is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Line Price Per Unit is not as Expected");
		}
		// Covered Percent Value verification
		if(sCoveredPercent2.equals("0"))
		{
			ExtentManager.logger.log(Status.PASS,"Covered % is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Covered % is not as Expected");
		}
		// Billable Quantity Value verification
		if(sBillableQty2.equals("1.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice2.equals("3000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected");
		}
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		commonsPo.tap(workOrderPo.getEleClickSave());
		// Verifying after sync the system
		toolsPo.syncData(commonsPo);
		String sSoqlQueryChildlines = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')";
		restServices.getAccessToken();
		String sChildlines = restServices.restGetSoqlValue(sSoqlQueryChildlines, "totalSize");	
		if(sChildlines.equals("2"))
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