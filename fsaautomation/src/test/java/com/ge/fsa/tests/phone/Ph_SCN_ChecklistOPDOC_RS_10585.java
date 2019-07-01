/*
 *  @author Vinod Tharavath
 *   
 *   */
package com.ge.fsa.tests.phone;

import static org.testng.Assert.assertNotNull;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_ChecklistOPDOC_RS_10585 extends BaseLib {
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
	String sSection1Name = "Section One";
	String sSection2Name = "Section Two";
	String sSection3Name = "Section Three";
	String sChecklistOpDocName = null;
	String sWORecordID = null;
	String sSoqlqueryAttachment = null;
	String sAttachmentIDAfter = null;
	String ChecklistQueryval = null;
	String ChecklistQuery = null;
	// checklist q's set--;

	String sSection1Q1 = "What is the Work Order Number?";
	String sSection1Q2 = "2. Number Should not be greater than 100";
	String sSection2Q1 = "Section Two Question One";
	String sSection3q1 = "1. Section Three Question One";
	String sSection3q1Ans = "ok";
	String sNumberSectionJumpAns = "19";
	String snumberwithoutjump = "100";

	// For ServerSide Validations
	String schecklistStatusInProgress = "In Process";
	String schecklistStatus = "Completed";
	String sSheetName = null;
	String sSoqlquerypdf = null;
	String sSoqlquerypdf1 = null;
	String sProcessname = "Default title for Checklist";

	// For SFM Process Sahi Script name
	String sScriptName = "Scenario_RS10585_ChecklistOPDOC_SkippedSections";
	Boolean bProcessCheckResult = false;

	public void prerequisites() throws Exception {
		sSheetName = "RS_10585";
		System.out.println("RS 10585 In progress checklists with section skip and OPDOC");
		sTestCaseID = "SCN_ChecklistOPDOC_1_RS-10585";
		sCaseWOID = "Data_SCN_ChecklistOPDOC_1_RS-10585";

		// Reading from the Excel sheet
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ProcessName");
		sChecklistName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ChecklistName");
		sEditProcessName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "EditProcessName");
		sChecklistOpDocName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ChecklistOpDocName");
		// Rest to Create Workorder - Work Order -

		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);

		bProcessCheckResult = commonUtility.ProcessCheck(restServices, sChecklistName, sScriptName, sTestCaseID);

		// sWOName1 = "WO-00001615";
	}

	//@Test()
	@Test(retryAnalyzer=Retry.class)
	public void RS_10585() throws Exception {

		prerequisites();
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
		// Pre Login to app

		// Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);

		// Navigating to Checklist
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sProcessname);
		ExtentManager.logger.log(Status.INFO, "WorkOrder dynamically created and used is :" + sWOName + "");

		// Scrolling
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			commonUtility.custScrollToElement(sChecklistName, false);
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

		// Clicking on Section1
		ph_ChecklistPO.getElementSection(0).click();

		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sSection1Q2).sendKeys(sNumberSectionJumpAns);

		ph_ChecklistPO.geteleChecklistNextButton().click();

		Assert.assertTrue(ph_ChecklistPO.geteleGeneric("Section Three").isDisplayed(), "Exit Criteria in Checklist Failed");
		ExtentManager.logger.log(Status.PASS, "Exit Criteria for section passed");

		Integer iSectionSize = ph_ChecklistPO.geteleGenericList("Section Two").size();
		if (iSectionSize == 0) {
			ExtentManager.logger.log(Status.PASS, "Section two is not clickable as section was jumped from 1 to 3");
		} else
			ExtentManager.logger.log(Status.FAIL, "Section two is visible and section was not jumped from 1 to 3");

		ph_ChecklistPO.geteleBackbutton().click();

		ph_ChecklistPO.geteleBackbutton().click();

		ph_ChecklistPO.geteleBackbutton().click();

		Boolean bInProgress = ph_ChecklistPO.getEle1Progess().isDisplayed();
		Assert.assertTrue(bInProgress, "1-in progress is not displayed");
		ExtentManager.logger.log(Status.PASS, "In Progress Checklist is displayed");

		ph_ChecklistPO.geteleBackbutton().click();

		// Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);

		// Validating in the server if checklist status is synced as inprogress
		System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
		ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'" + sWOName + "')";
		ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");
		Assert.assertTrue(ChecklistQueryval.contains(schecklistStatusInProgress), "checklist in progress is not synced to server");
		ExtentManager.logger.log(Status.PASS, "Checklist In Progress status is displayed in Salesforce after sync");

		// Navigating to Checklist
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sProcessname);

		// Scrolling
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			commonUtility.custScrollToElement(sChecklistName, false);
		} else {
			commonUtility.custScrollToElement(sChecklistName);
		}

		// Click on ChecklistName
		ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
		System.out.println("clicked checklistname");
		Thread.sleep(2000);

		// Starting new Checklist
		// ph_ChecklistPO.getelecheckliststartnew(sChecklistName).click();

		ph_ChecklistPO.getelechecklistinstance().click();

		// Clicking on Section1
		ph_ChecklistPO.getElementSection(2).click();

		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sSection3q1).sendKeys("ok");

		ph_ChecklistPO.geteleSubmitbtn().click();

		// VT check if something can be done for all these back buttons?

		ph_ChecklistPO.geteleBackbutton().click();
		Thread.sleep(3000);

		ph_ChecklistPO.geteleBackbutton().click();
		Thread.sleep(3000);

		ph_ChecklistPO.geteleBackbutton().click();
		Thread.sleep(3000);

		ph_ChecklistPO.geteleBackbutton().click();
		Thread.sleep(3000);

		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);

		// Validating in the server if checklist status is synced as completed
		System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
		ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'" + sWOName + "')";
		ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");
		Assert.assertTrue(ChecklistQueryval.contains(schecklistStatus), "checklist completed is not synced to server");
		ExtentManager.logger.log(Status.PASS, "Checklist Completed status is displayed in Salesforce after sync");

		String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
		Assert.assertTrue(ChecklistAnsjson.contains(sNumberSectionJumpAns), "Answer is synced to server");
		ExtentManager.logger.log(Status.PASS, "Section One Answer is synced to server");

		Thread.sleep(CommonUtility.iLowSleep);

		// ph_WorkOrderPo.navigatetoWO(commonUtility, ph_ExploreSearchPo,
		// sExploreSearch, sExploreChildSearchTxt, sWOName);
		// Navigating to Checklist
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sChecklistOpDocName);
		Thread.sleep(15000);
		commonUtility.waitforElement(ph_ChecklistPO.geteleFinalize(), 15);
		ph_ChecklistPO.geteleFinalize().click();

		// Navigation back to Work Order after Service Report
		Assert.assertTrue(ph_ExploreSearchPo.geteleExploreIcn().isDisplayed(), "Work Order screen is displayed");
		ExtentManager.logger.log(Status.PASS, "Creation of Checklist OPDOC passed");

		// checklistPo.validateChecklistServiceReport(commonUtility, workOrderPo,
		// "Auto_RS10585_ChecklistOPDOC_InProgOP2",sWOName);

		// Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);

		// Verifying if checklistopdoc is synced to server
		System.out.println("Validating if OPDOC attachment is syned to server.");
		Thread.sleep(CommonUtility.i30SecSleep);
		Thread.sleep(CommonUtility.iAttachmentSleep);
		Thread.sleep(CommonUtility.iMedSleep);
		sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName + "\')";
		sSoqlquerypdf = "Select+Name+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName + "\')";
		sSoqlquerypdf1 = restServices.restGetSoqlValue(sSoqlquerypdf, "Name");

		Assert.assertTrue(sSoqlquerypdf1.contains("pdf"), "fileformat stored is not pdf");
		ExtentManager.logger.log(Status.PASS, "file is in pdf format in server");

		restServices.getAccessToken();
		sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlqueryAttachment, "Id");
		assertNotNull(sAttachmentIDAfter);
		ExtentManager.logger.log(Status.PASS, "OPDOC is synced to Server");

	}

}
