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
import org.openqa.selenium.By;
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
public class SCN_GetPrice_RS_10537 extends BaseLib {
	String sTestCaseID= "Scenario_10537";
	String sAccountName = null;
	String sProductName10538 = null;
	String sProductName10539 = null;
	String sInstalledProduct10538 = null;
	String sworkOrderName = null;
	String sSCONName = null;
	String sContactName = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sIBName = null;
	String sSheetName1 =null;
	String sSheetName2 =null;
	String sSheetName3 =null;
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10537() throws Exception {
		sSheetName1 ="RS_10539";
		sSheetName2 = "RS_10538";
		sSheetName3 = "RS_10537";
		System.out.println("SCN_GetPriceSCON_RS_10537");
		// To run the Sahi Script before the Execution of Appium - 10539
		genericLib.executeSahiScript("appium/Scenario_10537.sah");
		if(commonsUtility.verifySahiExecution()) {
			
			System.out.println("PASSED");
		}
		else 
		{
			System.out.println("FAILED");
			

			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
			assertEquals(0, 1);
		}
		
		loginHomePo.login(commonsUtility, exploreSearchPo);
		// Have a config Sync

		//toolsPo.configSync(commonsUtility);
		// Do a Data sync
		toolsPo.syncData(commonsUtility);
		Thread.sleep(genericLib.iMedSleep);
		// get Product from the RS-10539
		String sTestDataValue = "SCN_GetPriceSCON_RS_10539";
		sProductName10539 = GenericLib.getExcelData(sTestDataValue,sSheetName1,"Product2 Name");
		System.out.println(sProductName10539);
		// get Product from the RS-10538
		String sTestDataValue2 = "SCN_GetPrice_RS_10538";
		sProductName10538 = GenericLib.getExcelData(sTestDataValue2,sSheetName2,"Product Name ");
		System.out.println(sProductName10538);
		
		// get IB from the RS-10538
		sInstalledProduct10538 = GenericLib.getExcelData(sTestDataValue2,sSheetName2,"Installed Product Name");
		System.out.println(sInstalledProduct10538);
		
		// get Work Order from the RS-10537
		String sTestDataValue3 = "SCN_GetPrice_RS_10537";
		sworkOrderName = GenericLib.getExcelData(sTestDataValue3,sSheetName3,"Work Order Number");
		System.out.println(sworkOrderName);
		
		
		workOrderPo.navigatetoWO(commonsUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		String sProcessname = "Record T&M";// Standard SFM Process
		Thread.sleep(2000);
		workOrderPo.selectAction(commonsUtility,sProcessname);
		
		
	/**
	 * PARTS - Verification of Fields
	 */
		workOrderPo.addParts(commonsUtility, workOrderPo, sProductName10539);
		// To verify if Billing Type = Warranty
		String sBillingTypeValue = workOrderPo.getEleBillingTypeValue().getAttribute("value");
		Assert.assertEquals("Contract", sBillingTypeValue);
		System.out.println(sBillingTypeValue);
		// Clicking on Get Price button for Parts
		commonsUtility.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonsUtility.tap(workOrderPo.getEleChildLineTapName(sProductName10539));
		
		// Verify Each field value after the Get Price
		String sLinePricePerUnit1 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty1 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice1 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		String sDiscount = workOrderPo.getelechildlinefields("Discount %").getAttribute("value");
		
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit1.equals("3000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Line Price Per Unit 1:Expected Value is 3000 Actual Value is"+sLinePricePerUnit1);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Line Price Per Unit 1:Expected Value is 3000 Actual Value is"+sLinePricePerUnit1, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		
		// Verifying the Discount 
		if(sDiscount.equals("15"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Line Price Per Unit 1:Expected Value is 3000 Actual Value is"+sLinePricePerUnit1);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Line Price Per Unit 1:Expected Value is 3000 Actual Value is"+sLinePricePerUnit1, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Billable Quantity Value verification
		if(sBillableQty1.equals("1.000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Billable Quantity :Expected Value is 1.000 Actual Value is"+sBillableQty1);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Billable Quantity:Expected Value is 1.000 Actual Value is"+sBillableQty1, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice1.equals("2550.000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Billable Line Price:Expected Value is 2550.000 Actual Value is"+sBillableLinePrice1);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Billable Line Price:Expected Value is 2550.000 Actual Value is"+sBillableLinePrice1, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}		
	/**
	 * PARTS - END OF PARTS VERIFICATION
	 */			

		commonsUtility.tap(workOrderPo.getEleDoneBtn());
		commonsUtility.tap(workOrderPo.getEleClickSave());
		
		// To verify Linked SFM from the PS Lines
		Thread.sleep(10000);
		workOrderPo.navigatetoWO(commonsUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		String sProcessname2 = "SFM Process for RS-10553";// Need to pass this from the Excel sheet
		Thread.sleep(2000);
		workOrderPo.selectAction(commonsUtility,sProcessname2);
		commonsUtility.tap(workOrderPo.getEleLinkedSFM());
		commonsUtility.tap(workOrderPo.getEleSFMfromLinkedSFM("Manage Work Details for Products Serviced"));
		commonsUtility.tap(workOrderPo.getEleOKBtn());
		workOrderPo.addPartsManageWD(commonsUtility, workOrderPo,sProductName10538);
		commonsUtility.tap(workOrderPo.getEleClickSave());
		Thread.sleep(1000);
		commonsUtility.tap(workOrderPo.getEleClickSave());
		Thread.sleep(2000);
		workOrderPo.selectAction(commonsUtility,sProcessname);
		commonsUtility.tap(workOrderPo.geteleGetPrice());
		commonsUtility.tap((driver.findElement(By.xpath("(//div[text()='"+sProductName10538+"'])[2]"))));
		
		// To verify the values of the Next Addition of PArts
		
		String sLinePricePerUnit2 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty2 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice2 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		String sCovered = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit2.equals("10000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Line Price Per Unit 1:Expected Value is 10000 Actual Value is"+sLinePricePerUnit2);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Line Price Per Unit 1:Expected Value is 10000 Actual Value is"+sLinePricePerUnit2, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		
		// Verifying the Discount 
		if(sCovered.equals("30"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Covered Percent:Expected Value is 30 Actual Value is"+sCovered);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Covered Percent:Expected Value is 30 Actual Value is"+sCovered, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Billable Quantity Value verification
		if(sBillableQty2.equals("1.000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Billable Quantity:Expected Value is 1.000 Actual Value is"+sBillableQty2);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Billable Quantity:Expected Value is 1.000 Actual Value is"+sBillableQty2, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice2.equals("7000.000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Billable Line Price:Expected Value is 7000.000 Actual Value is"+sBillableLinePrice2);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Billable Line Price:Expected Value is 7000.000 Actual Value is"+sBillableLinePrice2, MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
		}
		
		// Verifying after sync the system
		commonsUtility.tap(workOrderPo.getEleDoneBtn());
		commonsUtility.tap(workOrderPo.getEleClickSave());
		toolsPo.syncData(commonsUtility);
		String sSoqlQueryChildlines = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')";
		restServices.getAccessToken();
		String sChildlines = restServices.restGetSoqlValue(sSoqlQueryChildlines, "totalSize");	
		if(sChildlines.equals("3"))
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
