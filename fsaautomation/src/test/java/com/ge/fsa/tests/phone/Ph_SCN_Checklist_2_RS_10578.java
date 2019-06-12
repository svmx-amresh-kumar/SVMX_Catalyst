/*
 *  @author Vinod Tharavath
 *  SCN_Checklist_2_RS-10578 Verify DataValidation on Checklists
 *  
 */
package com.ge.fsa.tests.phone;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.phone.Ph_ChecklistPO;
import com.ge.fsa.pageobjects.phone.Ph_MorePO;
import com.ge.fsa.pageobjects.tablet.WorkOrderPO;

import io.appium.java_client.android.AndroidDriver;

public class Ph_SCN_Checklist_2_RS_10578 extends BaseLib {
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sExploreChildSearchTxt = null;
	String sOrderStatusVal = null;
	String sEditProcessName = null;
	String sWORecordID = null;
	String sScriptName = "Scenario_RS10578_Checklist_DVR";
	String sProcessname = "Default title for Checklist";

	// checklist q's set--

	String sDateq = "1. Date DVRVS";
	String sDateTimeq = "2. DateTime DVR";
	String sConfirmationDVRq = "3. Confirmation DVR number cannot be 10";

	String sDateAns = null;
	String sDateTimeAns = null;
	String sConfirmationDVRAns = "10";

	String sAdvancedDVRq = "4. Advanced Expression DVR";
	String sAdvanceDVRAns = "20";
	String sAdvancedDVRValidAns = "300";

	// For ServerSide Validations
	String schecklistStatus = "Completed";
	String sSheetName = null;
	boolean bProcessCheckResult = false;

	public void prerequisites() throws Exception

	{
		sSheetName = "RS_10578";
		System.out.println("SCN_RS10578_Checklist_DVR");
		String time = driver.getDeviceTime();
		System.out.println(time);

		sTestCaseID = "SCN_Checklist_2_RS-10578_DVR";
		sCaseWOID = "DATA_SCN_Checklist_2_RS-10578_DVR";

		// Reading from the Excel sheet
		sExploreSearch = GenericLib.readExcelData(GenericLib.sTestDataFile, sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.readExcelData(GenericLib.sTestDataFile, sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.readExcelData(GenericLib.sTestDataFile, sSheetName, "ProcessName");
		sChecklistName = GenericLib.readExcelData(GenericLib.sTestDataFile, sSheetName, "ChecklistName");
		sEditProcessName = GenericLib.readExcelData(GenericLib.sTestDataFile, sSheetName, "EditProcessName");

		// Rest to Create Workorder
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\"}");
		System.out.println(sWORecordID);
		sWOName = restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);

		bProcessCheckResult = commonUtility.ProcessCheck(restServices, genericLib, sChecklistName, sScriptName,
				sTestCaseID);
		// sWOName = "WO-00001266";
	}

	// @Test()
	@Test(retryAnalyzer = Retry.class)
	public void RS_10578() throws Exception {
		// Prerequisite script
		prerequisites();

		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
		// Pre Login to app

		// Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);

		// Navigating to Checklist
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName,
				sProcessname);
		ExtentManager.logger.log(Status.INFO, "WorkOrder dynamically created and used is :" + sWOName + "");

		// Scrolling
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			commonUtility.custScrollToElement(sChecklistName, true);
		} else {
			commonUtility.custScrollToElement(sChecklistName);
		}

		// Click on ChecklistName
		ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
		System.out.println("clicked checklistname");
		Thread.sleep(2000);

		// Starting new Checklist
		ph_ChecklistPO.getelecheckliststartnew(sChecklistName).click();
		Thread.sleep(2000);
		ph_ChecklistPO.geteleInProgress().click();
		System.out.println("Clicked in Progress");
		
		// Today Validation
		String sTodaysDate = ph_ChecklistPO.get_device_date(commonUtility);
		System.out.println("Device retreived date:" + sTodaysDate);
		commonUtility.setSpecificDate(ph_ChecklistPO.getelechecklistdate(sDateq), "0", "0", "0");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistDVRtxt().isDisplayed(),
				"DataValidation rule failed for Date DVR actual" + sTodaysDate + "");
		ExtentManager.logger.log(Status.PASS, "DataValidation rule for today Passed actual" + sTodaysDate + "");
		ph_ChecklistPO.geteleSubmitbtn().click();

		// 8th June validation
		commonUtility.setSpecificDate(ph_ChecklistPO.getelechecklistdate(sDateq), "June", "8", "2018");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistDVRtxt().isDisplayed(),
				"DataValidation rule failed for Date 8th June DVR failed");
		ExtentManager.logger.log(Status.PASS, "DataValidation rule for 8th June Passed");

		// More than today validation
		commonUtility.setSpecificDate(ph_ChecklistPO.getelechecklistdate(sDateq), "June", "8", "2020");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistDVRtxt().isDisplayed(),
				"DataValidation rule failed for Date DVR actual");
		ExtentManager.logger.log(Status.PASS, "DataValidation rule for date more than today Passed");

		// Entering less than today
		commonUtility.setSpecificDate(ph_ChecklistPO.getelechecklistdate(sDateq), "June", "1", "2019");
		Assert.assertFalse(commonUtility.isDisplayedCust(ph_ChecklistPO.geteleChecklistDVRtxt()),
				"DataValidation rule failed for Date DVR less than today");
		ExtentManager.logger.log(Status.PASS,
				"DataValidation rule for date less than today Passed no verbiage displayed");

		System.out.println("Validated DATE!!!!");

		commonUtility.setDateTime24hrs(ph_ChecklistPO.getelechecklistdate(sDateTimeq), 0, "0", "0");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistDVRtxt().isDisplayed(),
				"DataValidation rule failed for DateTime DVR actual" + sTodaysDate + "");
		ExtentManager.logger.log(Status.PASS,
				"DataValidation rule for today DateTime Passed actual" + sTodaysDate + "");
		ph_ChecklistPO.geteleSubmitbtn().click();

		// More than today validation
		commonUtility.setDateTime24hrs(ph_ChecklistPO.getelechecklistdate(sDateTimeq), 3, "0", "0");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistDVRtxt().isDisplayed(),
				"DataValidation rule failed for Date DVR actual");
		ExtentManager.logger.log(Status.PASS, "DataValidation rule for dateTime more than today Passed");

		// Entering less than today
		commonUtility.setDateTime24hrs(ph_ChecklistPO.getelechecklistdate(sDateTimeq), -6, "0", "0");
		Assert.assertFalse(commonUtility.isDisplayedCust(ph_ChecklistPO.geteleChecklistDVRtxt()),
				"DataValidation rule failed for Date DVR less than today");
		ExtentManager.logger.log(Status.PASS,
				"DataValidation rule for dateTime less than today Passed no verbiage displayed");

		System.out.println("Validated DateTime");

		/*
		 * //Validating confirmation
		 * ph_ChecklistPO.getelechecklistNumberQAns(sConfirmationDVRq).sendKeys(
		 * sConfirmationDVRAns);
		 * 
		 * ph_ChecklistPO.geteleSubmitbtn().click();
		 * Assert.assertTrue(ph_ChecklistPO.getEleConfirm().isDisplayed(),
		 * "Confirm button is not being displayed for confirmation dvr");
		 * ExtentManager.logger.log(Status.PASS,
		 * "Confirm button is displayed for confirmation DVR");
		 * ExtentManager.logger.log(Status.PASS,
		 * "DataValidation rule for confirmation Passed");
		 * ph_ChecklistPO.getEleConfirm().click();
		 */

		// Navigating to the checklist
		Thread.sleep(GenericLib.iLowSleep);
		ph_ChecklistPO.getelechecklistNumberQAns(sAdvancedDVRq).sendKeys("5");
		ph_ChecklistPO.geteleSubmitbtn().click();
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistAdvanceDVR().isDisplayed(),
				"DataValidation rule failed for Advanced DVR ");
		ExtentManager.logger.log(Status.PASS, "DataValidation rule for Advanced DVR Passed");

		commonUtility.custScrollToElement(ph_ChecklistPO.getelechecklistNumberQAns(sAdvancedDVRq));
		ph_ChecklistPO.getelechecklistNumberQAns(sAdvancedDVRq).sendKeys("300");
		ph_ChecklistPO.geteleSubmitbtn().click();

		ph_MorePo.syncData(commonUtility);
		System.out.println(
				"validating if checklist is synced to server.validate the checklist status and answers through API.");
		String ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"
				+ sWOName + "')";
		String ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");
		Assert.assertTrue(ChecklistQueryval.contains(schecklistStatus), "checklist completed is not synced to server");
		ExtentManager.logger.log(Status.PASS, "Checklist Completed status is displayed in Salesforce after sync");
	}

}
