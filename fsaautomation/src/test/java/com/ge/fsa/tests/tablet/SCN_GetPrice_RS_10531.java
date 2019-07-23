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
public class SCN_GetPrice_RS_10531 extends BaseLib {
	String sTestCaseID= "Scenario_10531";
	String sAccountName = null;
	String sProductName10538 = null;
	String sProductName210539 = null;
	String sProductName10531 = null;
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
	public void RS_10531() throws Exception {
		
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-10531");
		}else {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12109");

		}
		sSheetName1 ="RS_10531";
		sSheetName2 = "RS_10538";
		sSheetName3 = "RS_10539";
		System.out.println("SCN_GetPriceSCON_RS_10531");
		// To run the Sahi Script before the Execution of Appium - 10531
		commonUtility.executeSahiScript("appium/SCN_GetPrice_RS_10531.sah");
		
		
		loginHomePo.login(commonUtility, exploreSearchPo);
		// Have a config Sync

		//toolsPo.configSync(commonsUtility);
		// Do a Data sync
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		// get Product from the RS-10531
		String sTestDataValue = "SCN_GetPrice_RS_10531";
		sProductName10531 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName1,"Product Name");
		System.out.println(sProductName10531);
		// To get the Work Order Name
		sworkOrderName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName1,"Work Order Number");
		System.out.println(sworkOrderName);
		
		
		// get Product 2 from the RS-10539
		String sTestDataValue2 = "SCN_GetPrice_RS_10538";
		String sTestDataValue3 = "SCN_GetPriceSCON_RS_10539";
		sProductName210539 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName3,"Product2 Name");
		System.out.println(sProductName210539);
		
		// get IB from the RS-10538
		sInstalledProduct10538 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName2,"Installed Product Name");
		System.out.println(sInstalledProduct10538);
		// get Product from the RS-10538
		sProductName10538 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName2,"Product Name ");
		System.out.println(sProductName10538);
//		
		workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		String sProcessname = "EditWoAutoTimesstamp";// Standard SFM Process
		Thread.sleep(2000);
		workOrderPo.selectAction(commonUtility,sProcessname);
	
		
		
	/**
	 * PARTS - Verification of Fields
	 */
		workOrderPo.addParts(commonUtility, workOrderPo, sProductName10531);
		// To verify if Billing Type = Warranty
		String sBillingTypeValue = workOrderPo.getEleBillingTypeValue().getAttribute("value");
		Assert.assertEquals("Loan", sBillingTypeValue);
		System.out.println(sBillingTypeValue);
		// Clicking on Get Price button for Parts
		commonUtility.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonUtility.tap(workOrderPo.getEleChildLineTapName(sProductName10531));
		
		// Verify Each field value after the Get Price
		String sLinePricePerUnit1 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty1 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice1 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		String sCovered = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		
		
		ExtentManager.logger.log(Status.PASS,"Work Order is not Entitled with Anything");
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit1.equals("750"))
		{
			ExtentManager.logger.log(Status.PASS,"Line Price Per Unit is as Expected - Part = 750");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Line Price Per Unit is not as Expected - Part = 750 Actual = "+sLinePricePerUnit1);
		}
		
		// Verifying the Discount 
		if(sCovered.equals("0"))
		{
			ExtentManager.logger.log(Status.PASS,"Discount % is as Expected - Part = 0");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Discount %  is not as Expected - Part = 0 Actual = "+sCovered);
		}
		// Billable Quantity Value verification
		if(sBillableQty1.equals("1.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected - Part = 1.000");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected - Part = 1.000 Actual = "+sBillableQty1);
		}
		 //Billable Line Price Value verification
		if(sBillableLinePrice1.equals("750.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected - Part = 750.000");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected - Part = 750.000 Actual = "+sBillableLinePrice1);
		}		
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		commonUtility.tap(workOrderPo.getEleClickSave());
		/**
	 * PARTS - END OF PARTS VERIFICATION
	 */			

		//commonsUtility.tap(workOrderPo.getEleDoneBtn());
		
		// ==========================================================================================================
		 //To run the Sahi Automation Script to set the Work Order to Not Covered on Sahi Part
		commonUtility.executeSahiScript("appium/SCN_GetProce_Rs_10531_Middletestcase.sah");
		
		
		// Have a config Sync

		//toolsPo.configSync(commonsUtility);
		// Do a Data sync
		toolsPo.syncData(commonUtility);
		Thread.sleep(1000);
		// To Re-verify the Values with the Work Order not Covered 
		workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);
		Thread.sleep(2000);
		String sProcessname2 = "EditWoAutoTimesstamp";
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		workOrderPo.selectAction(commonUtility,sProcessname2);
		
		ExtentManager.logger.log(Status.PASS,"Work Order is Entitled with Not Covered ");
//		
		
	/**
	 * PARTS - Verification of Fields - After Work Order being Not Covered
	 */

		// To verify if Billing Type = Warranty
		String sBillingTypeValue2 = workOrderPo.getEleBillingTypeValue().getAttribute("value");
		Assert.assertEquals("Loan", sBillingTypeValue2);
		System.out.println(sBillingTypeValue2);
		// Clicking on Get Price button for Parts
		commonUtility.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonUtility.tap((driver.findElement(By.xpath("(//div[text()='"+sProductName10531+"'])[2]"))));
		
		// Verify Each field value after the Get Price
		String sLinePricePerUnit2 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty2 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice2 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		String sCovered2 = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit2.equals("750"))
		{
			ExtentManager.logger.log(Status.PASS,"Line Price Per Unit is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Line Price Per Unit is not as Expected - Part");
		}
		
		// Verifying the Discount 
		if(sCovered2.equals("0"))
		{
			ExtentManager.logger.log(Status.PASS,"Discount % is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Discount %  is not as Expected - Part");
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
		if(sBillableLinePrice2.equals("750.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected - Part");
		}
		
		// To add PS lines For SFM PS lines Processes - P10538
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		commonUtility.tap(workOrderPo.getEleClickSave());
		
		// ==========================================================================================================
		// To verify Linked SFM from the PS Lines
		Thread.sleep(10000);
		workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		String sProcessname21 = "SFM Process for RS-10553";// Need to pass this from the Excel sheet
		Thread.sleep(2000);
		workOrderPo.selectAction(commonUtility,sProcessname21);
		commonUtility.tap(workOrderPo.getEleLinkedSFM());
		commonUtility.tap(workOrderPo.getEleSFMfromLinkedSFM("Manage Work Details for Products Serviced"));
		commonUtility.tap(workOrderPo.getEleOKBtn());
		workOrderPo.addPartsManageWD(commonUtility, workOrderPo,sProductName10538);
		commonUtility.tap(workOrderPo.getEleClickSave());
		Thread.sleep(1000);
		commonUtility.tap(workOrderPo.getEleClickSave());
		Thread.sleep(3000);
		
		workOrderPo.selectAction(commonUtility,sProcessname);
		commonUtility.tap(workOrderPo.geteleGetPrice());
		commonUtility.tap((driver.findElement(By.xpath("(//div[text()='"+sProductName10538+"'])[2]"))));
		
		// Verify Each field value after the Get Price for PS Lines of IB10538
		String sLinePricePerUnit3 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty3 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice3 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		String sCovered3 = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		
		ExtentManager.logger.log(Status.PASS,"Work Detail - PS Lines for Product of RS-10538");
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit3.equals("10000"))
		{
			ExtentManager.logger.log(Status.PASS,"Line Price Per Unit is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Line Price Per Unit is not as Expected - Part");
		}
		
		// Verifying the Discount 
		if(sCovered3.equals("30"))
		{
			ExtentManager.logger.log(Status.PASS,"Discount % is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Discount %  is not as Expected - Part");
		}
		// Billable Quantity Value verification
		if(sBillableQty3.equals("1.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected - Part");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice3.equals("7000.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected - Part");
		}		
	/**
	 * PARTS - END OF PARTS VERIFICATION
	 */	
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		commonUtility.tap(workOrderPo.getEleClickSave());
		
		// ==========================================================================================================
		// To verify Linked SFM from the PS Lines
		Thread.sleep(10000);
		workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		Thread.sleep(2000);
		workOrderPo.selectAction(commonUtility,sProcessname21);
		
		commonUtility.tap((driver.findElement(By.xpath("(//span[@class='x-button-icon x-shown x-fa fa-list'])[2]"))));
		
		commonUtility.tap((driver.findElement(By.xpath("(//span[@class='x-button-label'][text()='Manage Work Details for Products Serviced'])[2]"))));
		commonUtility.tap(workOrderPo.getEleOKBtn());
		workOrderPo.addPartsManageWD(commonUtility, workOrderPo,sProductName210539);
		commonUtility.tap(workOrderPo.getEleClickSave());
		Thread.sleep(1000);
		commonUtility.tap(workOrderPo.getEleClickSave());
		Thread.sleep(2000);
		workOrderPo.selectAction(commonUtility,sProcessname);
		commonUtility.tap(workOrderPo.geteleGetPrice());
		Thread.sleep(2000);
		commonUtility.tap((driver.findElement(By.xpath("(//div[text()='"+sProductName210539+"'])[2]"))));
		
		// To verify the values of the Next Addition of PArts
		
		String sLinePricePerUnit4 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty4 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice4 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		String sDiscount4 = workOrderPo.getelechildlinefields("Discount %").getAttribute("value");
		
		ExtentManager.logger.log(Status.PASS,"Work Detail - PS Lines for Product of RS-10539");
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit4.equals("3000"))
		{
			ExtentManager.logger.log(Status.PASS,"Line Price Per Unit is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Line Price Per Unit is not as Expected - Part");
		}
		
		// Verifying the Discount 
		if(sDiscount4.equals("15"))
		{
			ExtentManager.logger.log(Status.PASS,"Discount % is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Discount %  is not as Expected - Part");
		}
		// Billable Quantity Value verification
		if(sBillableQty4.equals("1.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected - Part");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice4.equals("2550.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected - Part");
		}

		// ==========================================================================================================
		
	}

}
