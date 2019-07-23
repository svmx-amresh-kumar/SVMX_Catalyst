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
public class SCN_GetPrice_RS_10536 extends BaseLib {
	String sTestCaseID= "Scenario_10536";
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
	public void RS_10536() throws Exception {
		
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-10536");
		}else {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12104");

		}
		sSheetName1 ="RS_10539";
		sSheetName2 = "RS_10538";
		sSheetName3 = "RS_10536";
		System.out.println("SCN_GetPriceSCON_RS_10536");
		// To run the Sahi Script before the Execution of Appium - 10539
		commonUtility.executeSahiScript("appium/Scenario_10536.sah");
		
		
		loginHomePo.login(commonUtility, exploreSearchPo);
		// Have a config Sync

		//toolsPo.configSync(commonsUtility);
		// Do a Data sync
		toolsPo.syncData(commonUtility);
		// get Product from the RS-10539
		String sTestDataValue = "SCN_GetPriceSCON_RS_10539";
		sProductName10539 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName1,"Product2 Name");
		System.out.println(sProductName10539);
		// get Product from the RS-10538
		String sTestDataValue2 = "SCN_GetPrice_RS_10538";
		sProductName10538 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName2,"Product Name ");
		System.out.println(sProductName10538);
		
		// get IB from the RS-10538
		sInstalledProduct10538 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName2,"Installed Product Name");
		System.out.println(sInstalledProduct10538);
		
		// get Work Order from the RS-10537
		String sTestDataValue3 = "SCN_GetPrice_RS_10536";
		sworkOrderName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName3,"Work Order Number");
		System.out.println(sworkOrderName);
		
		Thread.sleep(CommonUtility.iMedSleep);
		workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		String sProcessname = "Record T&M";// Standard SFM Process
		Thread.sleep(2000);
		workOrderPo.selectAction(commonUtility,sProcessname);
		
		
	/**
	 * PARTS - Verification of Fields
	 */
		workOrderPo.addParts(commonUtility, workOrderPo, sProductName10538);
		// To verify if Billing Type = Warranty
		String sBillingTypeValue = workOrderPo.getEleBillingTypeValue().getAttribute("value");
		Assert.assertEquals("Warranty", sBillingTypeValue);
		System.out.println(sBillingTypeValue);
		// Clicking on Get Price button for Parts
		commonUtility.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonUtility.tap(workOrderPo.getEleChildLineTapName(sProductName10538));
		commonUtility.tap(workOrderPo.getEleChildLineTapName(sProductName10538),10,10);
		
		// Verify Each field value after the Get Price
		String sLinePricePerUnit1 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty1 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice1 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		String sCovered = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit1.equals("10000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Line Price Per Unit 1:Expected Value is : 10000 Actual Value is"+sLinePricePerUnit1);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Line Price Per Unit 1:Expected Value is : 10000 Actual Value is"+sLinePricePerUnit1, MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		
		// Verifying the Discount 
		if(sCovered.equals("30"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Covered Percent:Expected Value is : 30 Actual Value is"+sCovered);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Covered Percent:Expected Value is : 30 Actual Value is"+sCovered, MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Quantity Value verification
		if(sBillableQty1.equals("1.000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Billing Quantity:Expected Value is :1.000 Actual Value is"+sBillableQty1);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Billing Quantity:Expected Value is :1.000 Actual Value is"+sBillableQty1, MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice1.equals("7000.000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Billable Line Price:Expected Value is :7000.000 Actual Value is"+sBillableLinePrice1);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS:Billable Line Price:Expected Value is :7000.000 Actual Value is"+sBillableLinePrice1, MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}		
	/**
	 * PARTS - END OF PARTS VERIFICATION
	 */			

		commonUtility.tap(workOrderPo.getEleDoneBtn());
		commonUtility.tap(workOrderPo.getEleClickSave());
		
		// To verify Linked SFM from the PS Lines
		Thread.sleep(10000);
		workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		String sProcessname2 = "SFM Process for RS-10553";// Need to pass this from the Excel sheet
		Thread.sleep(2000);
		workOrderPo.selectAction(commonUtility,sProcessname2);
		commonUtility.tap(workOrderPo.getEleLinkedSFM());
		commonUtility.tap(workOrderPo.getEleSFMfromLinkedSFM("Manage Work Details for Products Serviced"));
		commonUtility.tap(workOrderPo.getEleOKBtn());
		workOrderPo.addPartsManageWD(commonUtility, workOrderPo,sProductName10539);
		commonUtility.tap(workOrderPo.getEleClickSave());
		Thread.sleep(1000);
		commonUtility.tap(workOrderPo.getEleClickSave());
		Thread.sleep(2000);
		workOrderPo.selectAction(commonUtility,sProcessname);
		commonUtility.tap(workOrderPo.geteleGetPrice());
		commonUtility.tap((driver.findElement(By.xpath("(//div[text()='"+sProductName10539+"'])[2]"))));
		commonUtility.tap((driver.findElement(By.xpath("(//div[text()='"+sProductName10539+"'])[2]"))),10,10);
		
		// To verify the values of the Next Addition of PArts
		
		String sLinePricePerUnit2 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty2 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice2 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		String sDiscount = workOrderPo.getelechildlinefields("Discount %").getAttribute("value");
		
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit2.equals("3000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS:Line Price Per Unit 1:Expected Value is : 3000 Actual Value is"+sLinePricePerUnit2);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS: Line Price Per Unit 1:Expected Value is : 3000 Actual Value is"+sLinePricePerUnit2, MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		
		// Verifying the Discount 
		if(sDiscount.equals("15"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS: Discount Percent:Expected Value is : 15 Actual Value is"+sDiscount);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS: Discount Percent:Expected Value is : 15 Actual Value is"+sDiscount, MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Quantity Value verification
		if(sBillableQty2.equals("1.000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS: Billable Quantity:Expected Value is : 1.000 Actual Value is"+sBillableQty2);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS: Billable Quantity:Expected Value is : 1.000 Actual Value is"+sBillableQty2, MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice2.equals("2550.000"))
		{
			ExtentManager.logger.log(Status.PASS,"PARTS: Billable Line Price Value is :2550.000 Actual Value is"+sBillableLinePrice2);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"PARTS: Billable Line Price Value is :2550.000 Actual Value is"+sBillableLinePrice2, MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}
		
		// Verifying after sync the system
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		commonUtility.tap(workOrderPo.getEleClickSave());
			toolsPo.syncData(commonUtility);

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
