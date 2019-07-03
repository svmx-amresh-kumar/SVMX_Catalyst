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

public class SCN_GetPrice_RS_10534 extends BaseLib {
	String sTestCaseID= "Scenario_10534";
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
	String sSheetName2 =null;
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10534() throws Exception {
		sSheetName ="RS_10534";
		sSheetName2 = "RS_10538";
		System.out.println("SCN_GetPrice_RS_10534");
		// To run the Sahi Script before the Execution of Appium
		commonUtility.executeSahiScript("appium/Scenario_10534_before.sah");
		
		loginHomePo.login(commonUtility, exploreSearchPo);
		// Have a config Sync
		//toolsPo.configSync(commonsUtility);
		// Do a Data sync
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		// Get the Work Order from the sheet
		String sTestDataValue1 = "SCN_GetPrice_RS_10534";
		String sTestDataValue2 = "SCN_GetPrice_RS_10538";
		String sworkOrderName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "Work Order Number");
		String sworkOrderName2 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "Work Order Number2");
		String sProductName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName2, "Product Name ");
		System.out.println(sworkOrderName2);
	/** 
	 * To enter the DOD of the Work Order	
	 */
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		workOrderPo.downloadCriteriaDOD(commonUtility, exploreSearchPo,"AUTOMATION SEARCH", "Work Orders", sworkOrderName);
		// If the value "Records not Displayed" is Visible then the Work Order is Online.
			if(exploreSearchPo.getEleNorecordsToDisplay().isDisplayed())
				{				
				 Thread.sleep(2000);
				 commonUtility.tap(exploreSearchPo.getEleOnlineBttn());
				 commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
				 // If the Cloud button is Visible then need to Tap on it
					if(exploreSearchPo.getEleCloudSymbol().isDisplayed())
						{
						commonUtility.tap(exploreSearchPo.getEleCloudSymbol(),20,20);
						commonUtility.tap(exploreSearchPo.getEleWorkOrderIDTxt(sworkOrderName),10,10);
					
						}
					// If the cloud button is not visible then throw an Error in the Report
						else
						{
							//NXGReports.addStep("Testcase " + sTestCaseID + "DOD of the Work Order didn't meet", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
							ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "DOD of the Work Order didn't meet");

							driver.quit();
						}
				}
				// If the value "Records not displayed" is not visible then the WO is not Online.
				else
				{
					//NXGReports.addStep("Testcase " + sTestCaseID + "Work Order is not Online - DOD not available", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "DOD of the Work Order didn't meet");

					System.out.println("DOD of the Work Order didn't meet");
					driver.quit();
				}
				
		
			
		/**
		 * PARTS - Verification of Fields
		 */		
			String sProcessname = "Record T&M";
		
				Thread.sleep(2000);
			commonUtility.tap(exploreSearchPo.getEleWorkOrderIDTxt(sworkOrderName));
			workOrderPo.selectAction(commonUtility,sProcessname);
			workOrderPo.addParts(commonUtility, workOrderPo, sProductName);
		// To verify if Billing Type = Warranty
		String sBillingTypeValue = workOrderPo.getEleBillingTypeValue().getAttribute("value");
		Assert.assertEquals("Warranty", sBillingTypeValue);
		System.out.println(sBillingTypeValue);
		// Clicking on Get Price button for Parts
		commonUtility.tap(workOrderPo.geteleGetPrice());
		// Tap on the Product and verify the field values after the Get Price of Parts
		commonUtility.tap(workOrderPo.getEleChildLineTapName(sProductName));
		
		// Verify Each field value after the Get Price
		String sLinePricePerUnit = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sCoveredPercent = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		
		// Verifying The Line Price Per Unit Value
		if(sLinePricePerUnit.equals(sPLinePricePerUnit))
		{
			ExtentManager.logger.log(Status.PASS,"Line Price Per Unit is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Line Price Per Unit is not as Expected");
		}
		// Covered Percent Value verification
		if(sCoveredPercent.equals(sPCoveredPercent))
		{
			ExtentManager.logger.log(Status.PASS,"Covered % is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Covered % is not as Expected");
		}
		// Billable Quantity Value verification
		if(sBillableQty.equals(sPBillingQty))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice.equals(sPBillableLinePrice))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected");
		}
		commonUtility.tap(workOrderPo.getEleDoneBtn());
	/**
	 * PARTS - END OF PARTS VERIFICATION
	 */			
		/**
		 * EXPENSE - VERIFICATION OF THE FIELDS
		 */
			workOrderPo.addExpense(commonUtility, workOrderPo,"Food - Dinner",sProcessname,"5","100");
			//Verifying the fields of Expenses
			
			commonUtility.tap(workOrderPo.geteleGetPrice());
			commonUtility.tap(workOrderPo.getEleChildLineTapName("Food - Dinner"));
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

			//NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines After Sync is "+sChildlinesAfter, LogAs.FAILED, null);

			System.out.println("The Childlines After Sync is "+sChildlines);
			}
			else
			{
				ExtentManager.logger.log(Status.FAIL,"The Childlines After Sync is "+sChildlines);

				//NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines After Sync is "+sChildlinesAfter, LogAs.PASSED, null);
				System.out.println("The Childlines After Sync is "+sChildlines);
			}
			
										// SEARCH FOR WORK ORDER 2
					
			/** 
			 * To enter the DOD of the Work Order	
			 */
				commonUtility.tap(exploreSearchPo.getEleExploreIcn());
				workOrderPo.downloadCriteriaDOD(commonUtility, exploreSearchPo,"AUTOMATION SEARCH", "Work Orders", sworkOrderName2);
				// If the value "Records not Displayed" is Visible then the Work Order is Online.
					if(exploreSearchPo.getEleNorecordsToDisplay().isDisplayed())
						{				
						 commonUtility.tap(exploreSearchPo.getEleOnlineBttn());
						 commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
						 // If the Cloud button is Visible then need to Tap on it
							if(exploreSearchPo.getEleCloudSymbol().isDisplayed())
								{
								commonUtility.tap(exploreSearchPo.getEleCloudSymbol(),20,20);
								commonUtility.tap(exploreSearchPo.getEleWorkOrderIDTxt(sworkOrderName2),10,10);
							
								}
							// If the cloud button is not visible then throw an Error in the Report
								else
								{
									//NXGReports.addStep("Testcase " + sTestCaseID + "DOD of the Work Order didn't meet", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
									ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "DOD of the Work Order didn't meet");

									driver.quit();
								}
						}
						// If the value "Records not displayed" is not visible then the WO is not Online.
						else
						{
							//NXGReports.addStep("Testcase " + sTestCaseID + "Work Order is not Online - DOD not available", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
							ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "DOD of the Work Order didn't meet");

							System.out.println("DOD of the Work Order didn't meet");
							driver.quit();
						}

						
						Thread.sleep(2000);
					commonUtility.tap(exploreSearchPo.getEleWorkOrderIDTxt(sworkOrderName2));
					workOrderPo.selectAction(commonUtility,sProcessname);
				// To verify if Billing Type = Contract
				String sBillingTypeValue2 = workOrderPo.getEleBillingTypeValue().getAttribute("value");
				Assert.assertEquals("Contract", sBillingTypeValue2);
				System.out.println(sBillingTypeValue2);
		
			
	/**
	 * LABOR - Verification of Fields AFTER THE GET PRICE
	*/
		Date localTime = new Date();
		DateFormat df = new SimpleDateFormat("HH");
		 
		// Tell the DateFormat that I want to show the date in the IST timezone
//		df.setTimeZone(TimeZone.getTimeZone("GMT"));
//		String sdatehours = df.format(localTime);


		int sEndDateint = Integer.parseInt("03") + 2;
		String sEndDate = Integer.toString(sEndDateint);
		
		workOrderPo.addLaborCustomizedDate(commonUtility, workOrderPo,"Installation","03",sEndDate,sProcessname);
		commonUtility.tap(workOrderPo.geteleGetPrice());
		commonUtility.tap(workOrderPo.getEleChildLineTapName("Installation"));
		String sLinePricePUnit_labor = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		System.out.println(sLinePricePUnit_labor);
		String sBillableQty_labor = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_labor = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		if(sLinePricePUnit_labor.equals("500"))
		{
			ExtentManager.logger.log(Status.PASS,"Line Price Per Unit is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Line Price Per Unit is not as Expected");
		}
		// Billable Quantity Value verification
		if(sBillableQty_labor.equals("2.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_labor.equals("1000.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected");
		}
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		
	// For Repair Labor Parts
		workOrderPo.addLaborCustomizedDate(commonUtility, workOrderPo,"Repair","03",sEndDate,sProcessname);
		commonUtility.tap(workOrderPo.geteleGetPrice());
		commonUtility.tap(workOrderPo.getEleChildLineTapName("Repair"));
		String sLinePricePUnit_labor2 = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sCoveredPercent_labor2 = workOrderPo.getelechildlinefields("Covered %").getAttribute("value");
		String sBillableQty_labor2 = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_labor2 = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		
		if(sLinePricePUnit_labor2.equals("600"))
		{
			ExtentManager.logger.log(Status.PASS,"Line Price Per Unit is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Line Price Per Unit is not as Expected");
		}
		// Billable Quantity Value verification
		if(sBillableQty_labor2.equals("1"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_labor2.equals("600.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected");
		}
		commonUtility.tap(workOrderPo.getEleDoneBtn());
	/**
	 * LABOR - END OF LABOR VERIFICATION
	*/
	
		
		/**
		 * TRAVEL - VERIFICATION OF THE FIELDS
		 */
		
		Date localTime1 = new Date();
		DateFormat df1 = new SimpleDateFormat("HH");
		 
		// Tell the DateFormat that I want to show the date in the IST timezone
//		df1.setTimeZone(TimeZone.getTimeZone("GMT"));
//		String sdatehours1 = df1.format(localTime1);


		int sEndDateint1 = Integer.parseInt("03") + 4;
		String sEndDate1 = Integer.toString(sEndDateint1);
		workOrderPo.addTravelwithTime(commonUtility, workOrderPo, sProcessname, "03", sEndDate1);
		
		commonUtility.tap(workOrderPo.geteleGetPrice());
		commonUtility.tap(workOrderPo.geteleTraveltap(sEndDate1));
		String sLinePricePerUnit_travel = workOrderPo.getelechildlinefields("Line Price Per Unit").getAttribute("value");
		String sBillableQty_travel = workOrderPo.getelechildlinefields("Billable Qty").getAttribute("value");
		String sBillableLinePrice_travel = workOrderPo.getelechildlinefields("Billable Line Price").getAttribute("value");
		System.out.println(sLinePricePerUnit_travel);
		// Line Price Per Unit
		if(sLinePricePerUnit_travel.equals("800.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected");
		}
		// Billable Quantity Value verification
		if(sBillableQty_travel.equals("4.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Quantity is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Quantity is not as Expected");
		}
		// Billable Line Price Value verification
		if(sBillableLinePrice_travel.equals("800.000"))
		{
			ExtentManager.logger.log(Status.PASS,"Billable Line Price is as Expected");
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"Billable Line Price is not as Expected");
		}
		
		/**
		 * END OF TRAVEL VERIFICATION
		 */
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		commonUtility.tap(workOrderPo.getEleClickSave());
		// Verifying after sync the system
		toolsPo.syncData(commonUtility);
		String sSoqlQueryChildlines2 = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName2+"\')";
		restServices.getAccessToken();
		String sChildlines2 = restServices.restGetSoqlValue(sSoqlQueryChildlines2, "totalSize");	
		if(sChildlines2.equals("3"))
		{
			ExtentManager.logger.log(Status.PASS,"The Childlines After Sync is "+sChildlines2);

		//NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines After Sync is "+sChildlinesAfter, LogAs.FAILED, null);

		System.out.println("The Childlines After Sync is "+sChildlines2);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"The Childlines After Sync is "+sChildlines2);

			//NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines After Sync is "+sChildlinesAfter, LogAs.PASSED, null);
			System.out.println("The Childlines After Sync is "+sChildlines2);
		}
		commonUtility.executeSahiScript("appium/Scenario_10534_after.sah");
		
		
	}
	


}
