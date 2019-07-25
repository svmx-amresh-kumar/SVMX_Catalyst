/*
 *  @author Vinod Tharavath
 *  SCN_Checklist_3_RS-10579 Submit and sync Verify Checklist QC,DVR,SOU,ENTRY/EXIT and Help URL
 */
package com.ge.fsa.tests.phone;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.MobileElement;

public class Ph_SCN_Checklist_3_RS_10579 extends BaseLib {
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sExploreChildSearchTxt = null;
	String sWORecordID = null;
	String sWOName1 = null;
	String sWOName2 = null;
	String sWOName3 = null;
	String sWOName4 = null;

	String sOrderStatusVal = null;
	String sEditProcessName = null;
	String sSection1Name = "Section One";
	String sSection2Name = "Section Two";
	String sSection3Name = "Section Three";

	// checklist q's set--;
	String sDateq = "2. Date should not be Today";
	String sNumberq = "3. Number Should not be greater than 100";
	String sConfirmationDVRq = "";
	String sSectionThreeq1 = "1. Section Three q1";
	String sSectionThreeq1Ans = "ok";
	String sDateAns = null;
	String sDateTimeAns = null;
	String sNumberSectionJumpAns = "20";
	String sNumberDVRAns = "102";
	String snumberwithoutjump = "100";

	// For ServerSide Validations
	String schecklistStatus = "Completed";
	String sChecklistQuery = null;
	String sChecklistQueryval = null;
	String ChecklistAnsjson = null;
	String sSoqlqueryWO = null;
	String sSoqlProforma = null;
	String sSoqlNoOfTimes = null;
	String sBillTypeServer = null;
	String sProformaServer = null;
	String sNoOftimesServer = null;
	String sNoOftimesServer1 = null;
	String sProblemDescServer = null;
	String sSoqlqueryWOProb = null;
	String sChecklistAnsjson1 = null;
	String sAttachmentQ = "2. Question two Section two";
	String sProcessname = "Default title for Checklist";

	// Sou
	String sBillingTpeSOU = null;
	String sProblemDescriptionSOU = null;

	// SOU ans
	String sBillingTpeSOUServer = "Paid";
	String sProblemDescriptionSOUServer = "Souce Object Update Sucess";
	String sSheetName = null;
	String sScriptName = "/appium/Scenario_RS10579_Checklist_Combo.sah";
	Boolean bProcessCheckResult = false;
	String url = null;

	public void prerequisites() throws Exception {
		sSheetName = "RS_10579";
		System.out.println("SCN_RS10579_Checklist_Entry Exit with URL with basic source object update and DVR");
		sTestCaseID = "SCN_Checklist_3_RS-10579_Entry_Exit_Criteria";
		sCaseWOID = "Data_SCN_Checklist_3_RS-10579_Entry_Exit_Criteria";

		// Reading from the Excel sheet
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ProcessName");
		sChecklistName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ChecklistName");
		sEditProcessName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "EditProcessName");

		// Rest to Create Workorder -Standard Work Order - Satisfies Qualification
		// Criteria and Checklist Entry Criteria

		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName1 = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName1);

		// Cancelled Work Order for Qualification Criteria check.
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__Order_Status__c\":\"Canceled\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName2 = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName2);

		// Work Order for checklist entry Criteria check.
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"Low\"}");
		System.out.println(sWORecordID);
		sWOName3 = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName3);

		// Rest to Create Workorder -Standard Work Order - Satisfies Qualification
		// Criteria and Checklist Entry Criteria

		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName4 = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName4);
		// sWOName1 = "WO-00003525";

		bProcessCheckResult = commonUtility.ProcessCheck(restServices, sChecklistName, sScriptName, sTestCaseID);

	}

	// @Test()

	@Test(retryAnalyzer = Retry.class)
	public void RS_10579() throws Exception {

		prerequisites();
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
		// Pre Login to app

		// Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);

		// Navigating to Checklist
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName1, sProcessname);
		ExtentManager.logger.log(Status.INFO, "WorkOrder dynamically created and used is :" + sWOName1 + "");

		// Scrolling
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			commonUtility.custScrollToElement(sChecklistName, false);
		} else {
			commonUtility.custScrollToElement(sChecklistName);
		}

		// Click on ChecklistName
		ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
		System.out.println("clicked checklistname");
		Thread.sleep(3000);

		// Starting new Checklist
		ph_ChecklistPO.getelecheckliststartnew(sChecklistName).click();
		Thread.sleep(2000);

		// ph_ChecklistPO.geteleInProgress().click();

		// Clicking on Section1
		ph_ChecklistPO.getElementSection(0).click();

		commonUtility.setSpecificDate(ph_ChecklistPO.getelechecklistdatewithMoreinfo(sDateq), "0", "0", "0");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistDVRtxt().isDisplayed(), "DataValidation rule failed for Date error");
		ExtentManager.logger.log(Status.PASS, "DataValidation rule for Date Passed");
		commonUtility.custScrollToElement(sNumberq, false);
		commonUtility.setSpecificDate(ph_ChecklistPO.getelechecklistdatewithMoreinfo(sDateq), "July", "10", "2019");

		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq).sendKeys("105" + "\n");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistNumberDVR().isDisplayed());
		ExtentManager.logger.log(Status.PASS, "DVR MESSAGE PASSED");
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq).clear();
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq).sendKeys(sNumberSectionJumpAns + "\n");

		ph_ChecklistPO.geteleChecklistNextButton().click();
		///////////////////////////////////////////////

		Assert.assertFalse(commonUtility.isDisplayedCust(ph_ChecklistPO.geteleChecklistNumberDVR()), "DataValidation confirmation failed");
		Assert.assertTrue(ph_ChecklistPO.geteleGeneric("Section Three").isDisplayed(), "Exit Criteria in Checklist Failed");
		ExtentManager.logger.log(Status.PASS, "Exit Criteria for section passed");

		Integer iSectionSize = ph_ChecklistPO.geteleGenericList("Section Two").size();
		if (iSectionSize == 0) {
			ExtentManager.logger.log(Status.PASS, "Section two is not clickable as section was jumped from 1 to 3");
		} else
			ExtentManager.logger.log(Status.FAIL, "Section two is visible and section was not jumped from 1 to 3", MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());

		// ph_ChecklistPO.geteleTextQAnswithMoreInfo(sSectionThreeq1).sendKeys(sSectionThreeq1Ans);

		ph_ChecklistPO.geteleBackbutton().click();

		// Clicking on Section1
		ph_ChecklistPO.getElementSection(0).click();

		commonUtility.custScrollToElement(sNumberq, false);
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq).clear();

		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq).sendKeys("100" + "\n");
		// ph_ChecklistPO.geteleChecklistGenericContainsTxt(sNumberq).click();

		ph_ChecklistPO.geteleChecklistNextButton().click();

		Integer iSectionSize1 = ph_ChecklistPO.geteleGenericList("Section Three").size();
		if (iSectionSize1 == 0) {
			ExtentManager.logger.log(Status.PASS, "Section Two is visible and no jump is sucessfull");
		} else
			ExtentManager.logger.log(Status.FAIL, "Section two is not visible and section jumped from 1 to 3", MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());

		ph_ChecklistPO.geteleChecklistNextButton().click();
		ph_ChecklistPO.geteleSubmitbtn().click();
		Thread.sleep(3000);

		// back to Work Order
		ph_ChecklistPO.geteleBackbutton().click();
		Thread.sleep(3000);

		ph_ChecklistPO.geteleBackbutton().click();

		commonUtility.custScrollToElement("Description", true);
		// System.out.println(ans);
		System.out.println("SOU" + ph_WorkOrderPo.geteleProblemDescriptiontxt().getText().trim());
		Assert.assertTrue(ph_WorkOrderPo.geteleProblemDescriptiontxt().getText().trim().contains(sProblemDescriptionSOUServer), "Source Object UPDATE did not happen");
		ExtentManager.logger.log(Status.PASS, "Source Object update sucessfull for Problem Description");

		/*
		 * Assert.assertEquals(ph_WorkOrderPo.getEleBillingTypeField().getText().trim(),
		 * sBillingTpeSOUServer,
		 * "Billing Type source update not updated sucessfully in Work Order");
		 * ExtentManager.logger.log(Status.PASS,
		 * "Source Object update sucessfull for billing type in Work Order");
		 */

		ph_MorePo.syncData(commonUtility);

		Thread.sleep(CommonUtility.iMedSleep);
		ph_ExploreSearchPo.geteleExploreIcn().click();
		// -------------Validating work order not satisfying Qualification
		// Criteria----------------
		System.out.println("Validating work order not satisfying Qualification Criteria");

		// Navigation to WO

		ExtentManager.logger.log(Status.INFO, "WorkOrder dynamically created and used is :" + sWOName2 + "");

		ph_WorkOrderPo.navigatetoWO(commonUtility, ph_ExploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName2);

		// Navigate to Field Service process
		ph_WorkOrderPo.selectAction(commonUtility, sFieldServiceName);

		// Navigating to the checklist
		try {
			// added to try catch because .isdisplayed does not return false when element
			// not found.
			Assert.assertTrue(ph_WorkOrderPo.getEleThisRecordDoesNotPopup().isDisplayed());
			ExtentManager.logger.log(Status.FAIL, "Qualification criteria failure check!");
		} catch (Exception e) {
			ExtentManager.logger.log(Status.PASS, "Qualification criteria not met checklist not displayed");
		}

		Thread.sleep(CommonUtility.iLowSleep);

		// Validating Work not satisfying checklist entry criteria
		System.out.println("Validating workorder not satisfying checklsit entry criteria");

		// Navigating back to work Orders
		ph_WorkOrderPo.getEleBackButton().click();
		ph_ExploreSearchPo.geteleExploreIcn().click();

		ExtentManager.logger.log(Status.INFO, "WorkOrder dynamically created and used is :" + sWOName3 + "");
		ph_WorkOrderPo.navigatetoWO(commonUtility, ph_ExploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName3);

		// Navigate to Field Service process
		ph_WorkOrderPo.selectAction(commonUtility, sFieldServiceName);
		Thread.sleep(CommonUtility.iLowSleep);
		// Navigating to the checklist
		// Scrolling
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			commonUtility.custScrollToElement(sChecklistName, false);
		} else {
			commonUtility.custScrollToElement(sChecklistName);
		}

		// Click on ChecklistName
		ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
		System.out.println("clicked checklistname");
		Thread.sleep(3000);

		// Starting new Checklist
		ph_ChecklistPO.getelecheckliststartnew(sChecklistName).click();
		Thread.sleep(2000);

		Integer iSectionSize3 = ph_ChecklistPO.geteleGenericList("Section One").size();
		if (iSectionSize3 == 0) {
			ExtentManager.logger.log(Status.PASS, "Entry Criteria for checklist is validated sucessfully");
		} else
			ExtentManager.logger.log(Status.FAIL, "Section One is visible and section was not jumped from 1 to 3", MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());

		ph_ChecklistPO.geteleBackbutton().click();
		Thread.sleep(2000);
		ph_ChecklistPO.geteleBackbutton().click();
		Thread.sleep(2000);
		ph_ChecklistPO.geteleBackbutton().click();

		ph_ExploreSearchPo.geteleExploreIcn().click();
		ExtentManager.logger.log(Status.INFO, "WorkOrder dynamically created and used is :" + sWOName4 + "");
		ph_WorkOrderPo.navigatetoWO(commonUtility, ph_ExploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName4);
		ph_WorkOrderPo.selectAction(commonUtility, sFieldServiceName);

		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			commonUtility.custScrollToElement(sChecklistName, false);
		} else {
			commonUtility.custScrollToElement(sChecklistName);
		}

		// Click on ChecklistName
		ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
		System.out.println("clicked checklistname");
		Thread.sleep(3000);

		// Starting new Checklist
		ph_ChecklistPO.getelecheckliststartnew(sChecklistName).click();
		Thread.sleep(2000);

		// Clicking on Section1
		ph_ChecklistPO.getElementSection(0).click();

		ph_ChecklistPO.geteleChecklistNextButton().click();
		ph_ChecklistPO.geteleMoreInfo().click();

		// hard wait required.
		Thread.sleep(60000);
		String sUrl = "";
		Set<String> contextNames = driver.getContextHandles();
		if (sOSName.contains("android")) {
			driver.context("NATIVE_APP");
			sUrl = driver.findElement(By.xpath("//android.widget.TextView[@content-desc='multiLineHeaderTitle']")).getText();
			Assert.assertTrue(sUrl.contains("GE"));
			ExtentManager.logger.log(Status.PASS, "URL launched sucessfully");
		} else {
			Thread.sleep(CommonUtility.i30SecSleep);
			sUrl = driver.findElement(By.xpath("//*[@*[contains(.,'GE')]]")).getText();
			Assert.assertTrue(sUrl.contains("GE"));
		}

		ExtentManager.logger.log(Status.PASS, "The Url is launched succesfully");
		commonUtility.switchContext("Native");

		ph_ChecklistPO.geteleBackbutton().click();
		// ------------------SERVER SIDE VALIDATIONS

		System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
		sChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'" + sWOName1 + "')";
		sChecklistQueryval = restServices.restGetSoqlValue(sChecklistQuery, "SVMXC__Status__c");
		Assert.assertTrue(sChecklistQueryval.contains(schecklistStatus), "checklist completed is not synced to server");
		ExtentManager.logger.log(Status.PASS, "Checklist Completed status is displayed in Salesforce after sync");

		sSoqlqueryWO = "Select+SVMXC__Billing_Type__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName1 + "'";
		sSoqlqueryWOProb = "Select+SVMXC__Problem_Description__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName1 + "'";

		sBillTypeServer = restServices.restGetSoqlValue(sSoqlqueryWO, "SVMXC__Billing_Type__c");
		Assert.assertTrue(sBillTypeServer.equals(sBillingTpeSOUServer), "Billing type Picklist source object not syned to server");
		ExtentManager.logger.log(Status.PASS, "Billing type Picklist Source object update has synced to server");

		sProblemDescServer = restServices.restGetSoqlValue(sSoqlqueryWOProb, "SVMXC__Problem_Description__c");
		Assert.assertTrue(sProblemDescServer.equals(sProblemDescriptionSOUServer), "Problem Description source object not syned to server");
		ExtentManager.logger.log(Status.PASS, "Problem Description Source object update has synced to server");

	}

}
