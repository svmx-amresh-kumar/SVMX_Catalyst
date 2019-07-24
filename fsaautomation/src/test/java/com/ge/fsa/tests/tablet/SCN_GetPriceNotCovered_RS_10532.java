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
	String sSheetName1 =null;
	String sSheetName2 =null;
	String sSheetName3 =null;

	@Test(retryAnalyzer=Retry.class)
	public void RS_10532() throws Exception {
		
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-10532");
		}else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12018");

		}
		sSheetName1 ="RS_10532";
		sSheetName2 ="RS_10538";
		sSheetName3 ="RS_10539";
		
		System.out.println("SCN_GetPriceNotCovered_RS_10532");
		commonUtility.executeSahiScript("appium/Scenario_10532.sah");
		
		loginHomePo.login(commonUtility, exploreSearchPo);
		// Have a config Sync
		toolsPo.configSync(commonUtility);
		// Do a Data sync
		toolsPo.syncData(commonUtility);
		// Get the Work Order from the sheet
		String sTestDataValue1 = "SCN_GetPrice_RS_10538";
		String sTestDataValue3 = "SCN_GetPriceSCON_RS_10539";
		sProductName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName2,"Product Name ");
		System.out.println(sProductName);
		
		sProductName2 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName3,"Product2 Name");
		System.out.println(sProductName2);
		sIBName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName2,"Installed Product Name");
		System.out.println(sIBName);	
		
		String sTestDataValue2 = "SCN_GetPriceNotCovered_RS_10532";
		String sworkOrderName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName1,"Work Order Number");
	
		// To navigate to the Work Order
		workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		String sProcessname = "EditWoAutoTimesstamp";// Standard SFM Process
		Thread.sleep(2000);
		workOrderPo.selectAction(commonUtility,sProcessname);
		
		/**
		 * PARTS - Verification of Fields
		 */
		workOrderPo.addParts(commonUtility, workOrderPo, sProductName);
		// To verify if Billing Type = Warranty
		String sBillingTypeValue = workOrderPo.getEleBillingTypeValue().getAttribute("value");
		Assert.assertEquals("--None--", sBillingTypeValue);
		System.out.println(sBillingTypeValue);
		// Clicking on Get Price button for Parts
		commonUtility.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonUtility.tap(workOrderPo.getEleChildLineTapName(sProductName));
		
		String sLinePricePerUnit = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sCoveredPercent = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		System.out.println(sBillableLinePrice);
		
		
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
		if(sBillableLinePrice.equals("10000.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected");
		}
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		
		
		/**
		 * PARTS - Verification of Fields
		 */
		workOrderPo.addParts(commonUtility, workOrderPo, sProductName2);
		// To verify if Billing Type = Warranty
		// Clicking on Get Price button for Parts
		commonUtility.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonUtility.tap(workOrderPo.getEleChildLineTapName(sProductName2));
		
		String sLinePricePerUnit2 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sCoveredPercent2 = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty2= workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice2 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		System.out.println(sBillableLinePrice2);
		
		
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
		if(sBillableLinePrice2.equals("3000.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected");
		}
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		commonUtility.tap(workOrderPo.getEleClickSave());
		// Verifying after sync the system
		toolsPo.syncData(commonUtility);
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