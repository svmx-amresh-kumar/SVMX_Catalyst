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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.ExploreSearchPO;
import com.ge.fsa.pageobjects.WorkOrderPO;

/**
 * 
 * @author meghanarao
 *
 */
public class SCN_GetPrice_RS_10533 extends BaseLib {
	String sTestCaseID= "Scenario_10537";
	String sAccountName = null;
	String sProductName10538 = null;
	String sProductName10533 = null;
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
	public void RS_10533() throws Exception {
		sSheetName1 ="RS_10533";
		System.out.println("SCN_GetPrice_RS_10533");
		// To run the Sahi Script before the Execution of Appium - 10539
		genericLib.executeSahiScript("appium/SCN_GetPrice_RS_10533.sah");
		if(commonsPo.verifySahiExecution()) {
			
			System.out.println("PASSED");
		}
		else 
		{
			System.out.println("FAILED");
			

			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
			assertEquals(0, 1);
		}
		
		loginHomePo.login(commonsPo, exploreSearchPo);
		// Have a config Sync

		//toolsPo.configSync(commonsPo);
		// Do a Data sync
		toolsPo.syncData(commonsPo);
		Thread.sleep(genericLib.iMedSleep);
		// get Product from the RS-10533
		String sTestDataValue = "SCN_GetPrice_RS_10533";
		sworkOrderName = GenericLib.getExcelData(sTestDataValue,sSheetName1,"Work Order Number");
		System.out.println(sworkOrderName);
		sProductName10533 = GenericLib.getExcelData(sTestDataValue,sSheetName1,"Product Name");
		System.out.println(sProductName10533);
		
		
		workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		String sProcessname = "Manage Work Order Lines - Estimates";// Standard SFM Process
		Thread.sleep(2000);
		workOrderPo.selectAction(commonsPo,sProcessname);
		
		
	/**
	 * PARTS - Verification of Fields - Estimates
	 */

		commonsPo.tap(workOrderPo.getElePartLnk());
		commonsPo.lookupSearch(sProductName10533);
		commonsPo.tap(workOrderPo.getEleAddselectedbutton());
		commonsPo.tap(workOrderPo.getEleChildLineTapName(sProductName10533));
		workOrderPo.getEleEstimatedQty().sendKeys("4");
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		// To verify if Billing Type = Warranty
		String sBillingTypeValue = workOrderPo.getEleBillingTypeValue().getAttribute("value");
		Assert.assertEquals("Warranty", sBillingTypeValue);
		System.out.println(sBillingTypeValue);
		// Clicking on Get Price button for Parts
		commonsPo.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonsPo.tap(workOrderPo.getEleChildLineTapName(sProductName10533));
		
		// Verify Each field value after the Get Price
		String sEstimatedPricePerUnit = workOrderPo.getelechildlinefields("Estimated Price Per Unit").getAttribute("value");
		String sCoveredPercent = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableLinePrice1 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		String sBillableQty = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		
		
		// Verifying The Line Price Per Unit Value
		if(sEstimatedPricePerUnit.equals("700"))
		{
			ExtentManager.logger.log(Status.PASS,"Estimated Price Per Unit is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Estimated Price Per Unit is not as Expected - Part");
		}
		
		// Verifying the Discount 
		if(sCoveredPercent.equals("30"))
		{
			ExtentManager.logger.log(Status.PASS,"Covered % is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Covered %  is not as Expected - Part");
		}
		// Billable Quantity Value verification
		if(sBillableLinePrice1.equals("1960.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price Per Unit is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price Per Unit is not as Expected - Part");
		}
		// Billable Line Price Value verification
		if(sBillableQty.equals("4.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Qty is as Expected - Part");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Qty is not as Expected - Part");
		}		
	/**
	 * PARTS - END OF PARTS VERIFICATION
	 */			

		commonsPo.tap(workOrderPo.getEleDoneBtn());
		
	/**
	 * Add LABOR LINES to the Work Order
	*/
		Date localTime = new Date();
		DateFormat df = new SimpleDateFormat("HH");
		 
		// Tell the DateFormat that I want to show the date in the IST timezone
//		df.setTimeZone(TimeZone.getTimeZone("GMT"));
//		String sdatehours = df.format(localTime);


		int sEndDateint = Integer.parseInt("03") + 4;
		String sEndDate = Integer.toString(sEndDateint);
		
		workOrderPo.addLaborCustomizedDate(commonsPo, workOrderPo, "Calibration", "03", sEndDate, sProcessname);
		commonsPo.tap(workOrderPo.geteleGetPrice());
		commonsPo.tap(workOrderPo.getEleChildLineTapName("Calibration"));
		String sEstimatedPricePUnit_labor = workOrderPo.getelechildlinefields("Estimated Price Per Unit").getAttribute("value");
		System.out.println(sEstimatedPricePUnit_labor);
		String sCoveredPercent_Labor = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty_Labor = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_Labor= workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		
		if(sEstimatedPricePUnit_labor.equals("1500"))
		{
			ExtentManager.logger.log(Status.PASS,"Estimated Price Per Unit is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Estimated Price Per Unit is not as Expected");
		}
		// Billable Quantity Value verification
		if(sCoveredPercent_Labor.equals("20"))
		{
			ExtentManager.logger.log(Status.PASS,"Covered Percent is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Covered Percent is not as Expected");
		}
		// Billable Line Price Value verification
		if(sBillableQty_Labor.equals("4.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity  is not as Expected");
		}
		if(sBillableLinePrice_Labor.equals("4800.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected");
		}
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		//============================================================================================
		
		/**
		 * Add Another Labor Lines to the Work Order
		 */
		int sEndDateint2 = Integer.parseInt("03") + 4;
		String sEndDate2 = Integer.toString(sEndDateint2);
		
		commonsPo.tap(workOrderPo.geteleGetPrice());
		commonsPo.tap(workOrderPo.getEleChildLineTapName("Cleanup"));
		String sEstimatedPricePUnit_labor2 = workOrderPo.getelechildlinefields("Estimated Price Per Unit").getAttribute("value");
		System.out.println(sEstimatedPricePUnit_labor2);
		String sCoveredPercent_Labor2 = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty_Labor2 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_Labor2= workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		
		if(sEstimatedPricePUnit_labor2.equals("3000"))
		{
			ExtentManager.logger.log(Status.PASS,"Estimated Price Per Unit is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Estimated Price Per Unit is not as Expected");
		}
		// Billable Quantity Value verification
		if(sCoveredPercent_Labor2.equals("20"))
		{
			ExtentManager.logger.log(Status.PASS,"Covered Percent is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Covered Percent is not as Expected");
		}
		// Billable Line Price Value verification
		if(sBillableQty_Labor2.equals("1"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity  is not as Expected");
		}
		if(sBillableLinePrice_Labor2.equals("2400.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected");
		}
		
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		
		/**
		 * Finished Adding the Labor Lines to the Work Order
		 */
		
	
		/**
		 * ADDING EXPENSES TO THE WORK ORDER
		 
		 */
		
		/**
		 * EXPENSE - VERIFICATION OF THE FIELDS
		 */
			workOrderPo.addExpenseEstimates(commonsPo, workOrderPo,"Food - Dinner",sProcessname,"5","100");
			//Verifying the fields of Expenses
			
			commonsPo.tap(workOrderPo.geteleGetPrice());
			commonsPo.tap(workOrderPo.getEleChildLineTapName("Food - Dinner"));
			String sCoveredPercent_labor3 = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
			String sBillableQty_labor3 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
			String sBillableLinePrice_labor3 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
			
			// Covered Percent Value verification
			if(sCoveredPercent_labor3.equals("10"))
			{
				ExtentManager.logger.log(Status.PASS,"Covered % is as Expected");
			}
			else
			{
				ExtentManager.logger.log(Status.FAIL,"Covered % is not as Expected");
			}
			// Billable Quantity Value verification
			if(sBillableQty_labor3.equals("5.000"))
			{
				ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected");
			}
			else
			{
				ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected");
			}
			// Billable Line Price Value verification
			if(sBillableLinePrice_labor3.equals("450.000"))
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
