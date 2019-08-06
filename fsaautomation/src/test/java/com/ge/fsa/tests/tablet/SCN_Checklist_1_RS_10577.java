/*
 *  @author Vinod Tharavath
 *  SCN_Checklist_1_RS-10577 Verify Source Object Update in Checklists
 *  
 *  Validates number,text,checkbox and picklist source object update
 *   with pre-filled questions in fsa and server through api
 */
package com.ge.fsa.tests.tablet;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class SCN_Checklist_1_RS_10577 extends BaseLib {
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
	String sSheetName = null;
	String sNoOfTimesAssigned = null;
	String time = null;
	String sWORecordID = null;
	String sProcessCheck = null;
	Boolean bProcessCheckResult;

	// checklist q's set--
	String sTextq = "AUTO_Which City you are from?";
	String sPicklistq = "AUTO_What is the Order Status?";
	String sNumberq = "AUTO_What is the IdleTime?";
	String sDateq = "AUTO_What is the Scheduled Date?";
	String sDatetimeq = "AUTO_What is the ScheduledDatetime?";
	String sTextAns = null;
	String sPicklistAns = null;
	String sNumberAns;
	String sDateAns = null;
	String sDatetimeAns = null;

	// Checklist PreFill Values
	String sCityPrefill = "Delhi";
	String sOrderStatusPrefill = "Open";
	String sIdleTimePrefill = "30";
	String sScheduleddatePrefill = "28/08/2018";
	String sScheduledDateTimePrefill = "28/08/2018 02:42";

	// Source object update values
	String sBillingType = null;
	String sBillingTypeSOU = "Loan";
	String sOrderStatus = null;
	String sOrderStatusSOU = "Open";
	String sIdleTime = null;
	String sIdleTimeSOU = "30";
	String sScheduledDate = null;
	String[] sScheduledDateSOU = null;
	String sScheduledDateTime = null;
	String sScheduledDateTimeSou = "28/08/2018 09:42";
	String sScheduledDatetimesalesforce = "2018-08-28T09:42:00.000+0000";
	// String sScheduledDateTimeSou = "8/28/18 02:42";
	String sNoofTimesAssigned = null;
	String sNooftimesAssignedSOU = "2";
	String sProformaInvoice = null;
	String sProformaInvoiceSOU = "Source Object Updated";

	// For ServerSide Validations
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
	String schecklistStatus = "Completed";
	String sProcessID = null;
	String sPrcname2 = "inactivetest";
	String sScriptName = "Scenario_RS-10577_Checklist_SOU";
	String sSOUDate = null;

	public void prerequisites() throws Exception {
		sSheetName = "RS_10577";
		System.out.println("SCN_RS10577_Checklist_SOU");
		sSOUDate = checklistPo.get_device_date(commonUtility);
		sTestCaseID = "SCN_Checklist_1_RS-10577_SOU";
		sCaseWOID = "DATA_SCN_Checklist_1_RS-10577_SOU";

		// Reading from the Excel sheet
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ProcessName");
		sChecklistName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ChecklistName");
		sEditProcessName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "EditProcessName");

		// Rest to Create Workorder
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\"}");
		System.out.println(sWORecordID);
		sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);
		// sWOName = "WO-00005043";

		bProcessCheckResult = commonUtility.ProcessCheck(restServices, sChecklistName, sScriptName, sTestCaseID);

	}

	// @Test()
	@Test(retryAnalyzer = Retry.class)

	public void RS_10577() throws Exception {

		if (BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-10577");
		} else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12065");
		}
		// Running Pre-Req
		commonUtility.preReqSetup();
		// Resinstall the app
		lauchNewApp("false");
		System.out.println("Finished Sanity Pre Req");

		// running the prerequisites for the script
		prerequisites();

		// Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
		toolsPo.OptionalConfigSync(toolsPo, commonUtility, bProcessCheckResult);

		// Data Sync for WO's created
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);

		// Navigation to WO
		ExtentManager.logger.log(Status.INFO, "Work Order Created Dynamically : " + sWOName);
		workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);

		// Navigate to Field Service process
		workOrderPo.selectAction(commonUtility, sFieldServiceName);

		// Navigating to the checklist
		commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
		ExtentManager.logger.log(Status.INFO, "Checklist Process used : " + sChecklistName);
		Thread.sleep(CommonUtility.iLowSleep);

		// System.out.println("validating pre filled text question is showing up");
		sTextAns = checklistPo.geteleChecklistAnswerTextArea(sTextq).getAttribute("value");
		Assert.assertTrue(sTextAns.equals(sCityPrefill), "Text question not Prefilled--expected: " + sCityPrefill + " actual: " + sTextAns + "");
		ExtentManager.logger.log(Status.PASS, "Text question prefilled sucessfully expected: " + sCityPrefill + " actual: " + sTextAns + "");

		sPicklistAns = checklistPo.geteleChecklistAnsPicklist(sPicklistq).getAttribute("value");
		Assert.assertTrue(sPicklistAns.equals(sOrderStatusPrefill), "Picklist question not Prefilled.--expected: " + sOrderStatusPrefill + " actual: " + sPicklistAns + "");
		ExtentManager.logger.log(Status.PASS, "Picklist question prefilled sucessfully--expected: " + sOrderStatusPrefill + " actual: " + sPicklistAns + "");

		sNumberAns = checklistPo.geteleChecklistAnsNumber(sNumberq).getAttribute("value");
		Assert.assertTrue(sNumberAns.equals(sIdleTimePrefill), "Number question not Prefilled.--expected: " + sIdleTimePrefill + " actual: " + sNumberAns + "");
		ExtentManager.logger.log(Status.PASS, "Number question prefilled sucessfully--expected: " + sIdleTimePrefill + " actual: " + sNumberAns + "");

		sDateAns = checklistPo.geteleChecklistAnsDate(sDateq).getAttribute("value");
		System.out.println("dateReceived" + sDateAns);
		System.out.println("prefilldated date" + sScheduleddatePrefill);
		Assert.assertTrue(sDateAns.equals(sScheduleddatePrefill), "Date question not Prefilled.");
		ExtentManager.logger.log(Status.PASS, "Date question prefilled sucessfully");

		// tapping next button
		commonUtility.tap(checklistPo.geteleNext());
		Thread.sleep(CommonUtility.iLowSleep);

		// submitting of checklist
		commonUtility.clickAllowPopUp();
		commonUtility.switchContext("WebView");
		commonUtility.tap(checklistPo.eleChecklistSubmit());
		commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());

		// Navigating back to work Orders
		commonUtility.tap(checklistPo.geteleBacktoWorkOrderlnk());

		// Navigation back to Work Order
		Assert.assertTrue(checklistPo.getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		ExtentManager.logger.log(Status.PASS, "Back to Work Order after submitting checklist passed");
		Thread.sleep(CommonUtility.iLowSleep);
		// Navigate to SFM processes

		workOrderPo.selectAction(commonUtility, sEditProcessName);
		// ------------------Validating the Source Object Updates------------------
		// 1.Picklist
		sBillingType = workOrderPo.getEleBillingTypeLst().getAttribute("value");
		Assert.assertEquals(workOrderPo.getEleBillingTypeLst().getAttribute("value"), sBillingTypeSOU, "Picklist Source Object is not updated--Expected: " + sBillingTypeSOU + " Actual : " + sBillingType + "");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for Picklist Sucessfull Expected : " + sBillingTypeSOU + " Actual: " + sBillingType + "");
		// 2.Number
		sNoOfTimesAssigned = workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getAttribute("value");
		// sIdleTime = workOrderPo.geteleIdleTimetxt().getAttribute("value");
		System.out.println(sNoOfTimesAssigned);
		Assert.assertEquals(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getAttribute("value"), sNooftimesAssignedSOU, "Number Source Object is not updated--Expected:" + sNooftimesAssignedSOU + " Actual : " + sNoOfTimesAssigned + "");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for Number with value  Sucessfull--Expected:" + sNooftimesAssignedSOU + " Actual :" + sNoOfTimesAssigned + "");

		// DateTime
		sScheduledDateTime = workOrderPo.getEleScheduledDateTimeTxt().getAttribute("value");
		System.out.println(sScheduledDateTime);
		Assert.assertEquals(workOrderPo.getEleScheduledDateTimeTxt().getAttribute("value"), sScheduledDateTimeSou, "DateTime Source Object is not updated expected : " + sScheduledDateTimeSou + " actual : " + workOrderPo.getEleScheduledDateTimeTxt().getAttribute("value") + "");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for DateTime Sucessfull expected : " + sScheduledDateTimeSou + " actual : " + workOrderPo.getEleScheduledDateTimeTxt().getAttribute("value") + "");

		// 4.Date
		sScheduledDate = workOrderPo.getEleScheduledDateLst().getAttribute("value");
		Assert.assertEquals(workOrderPo.getEleScheduledDateLst().getAttribute("value"), sSOUDate, "Date Source Object is not updated expected :" + sSOUDate + " actual : " + workOrderPo.getEleScheduledDateLst().getAttribute("value") + " ");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for Date with function Today Sucessfull expected :" + sSOUDate + " actual :" + workOrderPo.getEleScheduledDateLst().getAttribute("value") + "");

		// 5. Text
		sProformaInvoice = workOrderPo.getEleProformaInvoiceTxt().getAttribute("value");
		Assert.assertEquals(workOrderPo.getEleProformaInvoiceTxt().getAttribute("value"), sProformaInvoiceSOU, "Text Source Object is not updated--Expected: " + sProformaInvoiceSOU + "Actual: " + sProformaInvoice + "");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for Text with Value Sucessfull --Expected: " + sProformaInvoiceSOU + " Actual: " + sProformaInvoice + "");

		// 6. Checkbox
		// Checkbox Button validation

		try {
			commonUtility.tap(workOrderPo.geteleIsEntitlementPerformed_Switch_On());
			ExtentManager.logger.log(Status.PASS, "Checkbox Source Object update with checkbox datatype Passed");
		} catch (Exception e) {
			ExtentManager.logger.log(Status.FAIL, "Checkbox Source Object update with checkbox datatype Failed");
		}

		commonUtility.tap(workOrderPo.getEleCancelLink());
		commonUtility.tap(workOrderPo.getEleDiscardChanges());

		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		// Navigation to WO

		workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);

		workOrderPo.selectAction(commonUtility, sEditProcessName);
		ExtentManager.logger.log(Status.INFO, "Edit Process being used : " + sEditProcessName);

		// ------------------Validating the Source Object Updates after Data sync---------------

		ExtentManager.logger.log(Status.INFO, "Validating Source Object Updates After Data Sync");

		// 1.Picklist
		sBillingType = workOrderPo.getEleBillingTypeLst().getAttribute("value");
		Assert.assertEquals(workOrderPo.getEleBillingTypeLst().getAttribute("value"), sBillingTypeSOU, "Picklist Source Object is not updated--Expected: " + sBillingTypeSOU + " Actual : " + sBillingType + "");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for Picklist Sucessfull Expected : " + sBillingTypeSOU + " Actual: " + sBillingType + "");

		// 2.Number
		sNoOfTimesAssigned = workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getAttribute("value");
		// sIdleTime = workOrderPo.geteleIdleTimetxt().getAttribute("value");
		System.out.println(sNoOfTimesAssigned);
		Assert.assertEquals(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getAttribute("value"), sNooftimesAssignedSOU, "Number Source Object is not updated--Expected: " + sNooftimesAssignedSOU + " Actual : " + sNoOfTimesAssigned + "");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for Number with value  Sucessfull--Expected: " + sNooftimesAssignedSOU + " Actual :" + sNoOfTimesAssigned + "");

		// 4.Date
		sScheduledDate = workOrderPo.getEleScheduledDateLst().getAttribute("value");
		Assert.assertEquals(workOrderPo.getEleScheduledDateLst().getAttribute("value"), sSOUDate, "Date Source Object is not updated expected :" + sSOUDate + " actual : " + workOrderPo.getEleScheduledDateLst().getAttribute("value") + " ");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for Date with function Today Sucessfull expected :" + sSOUDate + " actual :" + workOrderPo.getEleScheduledDateLst().getAttribute("value") + "");

		// 5. Text
		sProformaInvoice = workOrderPo.getEleProformaInvoiceTxt().getAttribute("value");
		Assert.assertEquals(workOrderPo.getEleProformaInvoiceTxt().getAttribute("value"), sProformaInvoiceSOU, "Text Source Object is not updated--Expected: " + sProformaInvoiceSOU + " Actual: " + sProformaInvoice + "");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for Text with Value Sucessfull --Expected: " + sProformaInvoiceSOU + "Actual: " + sProformaInvoice + "");

		// 6. Checkbox
		try {
			commonUtility.tap(workOrderPo.geteleIsEntitlementPerformed_Switch_On());
			ExtentManager.logger.log(Status.PASS, "Checkbox Source Object update with checkbox datatype Passed after datasync");
		} catch (Exception e) {
			ExtentManager.logger.log(Status.FAIL, "Checkbox Source Object update with checkbox datatype Failed after datasync");
		}

		// validating in server after source object update and if prefilled values are syned to server
		sChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'" + sWOName + "')";
		sChecklistQueryval = restServices.restGetSoqlValue(sChecklistQuery, "SVMXC__Status__c");
		Assert.assertTrue(sChecklistQueryval.contains(schecklistStatus), "checklist completed is not synced to server");
		ExtentManager.logger.log(Status.PASS, "Checklist Completed status is displayed in Salesforce after sync");

		ChecklistAnsjson = restServices.restGetSoqlValue(sChecklistQuery, "SVMXC__ChecklistJSON__c");
		Assert.assertTrue(ChecklistAnsjson.contains(sCityPrefill), "checklist text question answer is not synced to server");
		ExtentManager.logger.log(Status.PASS, "checklist text question answer with prefill synced to server");

		Assert.assertTrue(ChecklistAnsjson.contains(sIdleTimePrefill), "checklist number answer sycned to server in checklist answer");
		ExtentManager.logger.log(Status.PASS, "checklist number answer sycned to server in checklist answer");

		Assert.assertTrue(ChecklistAnsjson.contains(sPicklistAns), "checklist picklist answer was not sycned to server in checklist answer");
		ExtentManager.logger.log(Status.PASS, "checklist picklist question answer synced to server");

		String serverDate = checklistPo.server_date(commonUtility, sScheduleddatePrefill);
		System.out.println(sScheduleddatePrefill);
		System.out.println(serverDate);
		System.out.println(ChecklistAnsjson);
		Assert.assertTrue(ChecklistAnsjson.contains(serverDate), "checklist date answer was not sycned to server in checklist answer");
		ExtentManager.logger.log(Status.PASS, "checklist date question answer synced to server");

		sSoqlqueryWO = "Select+SVMXC__Billing_Type__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName + "'";
		sSoqlProforma = "Select+SVMXC__Proforma_Invoice__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName + "'";
		sSoqlNoOfTimes = "Select+SVMXC__NoOfTimesAssigned__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName + "'";
		String sSoqlSchedulesDate = "Select+SVMXC__Scheduled_Date__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName + "'";
		String sSoqlScheduledDateTime = "Select+SVMXC__Scheduled_Date_Time__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName + "'";
		String sScheduledDateServer = restServices.restGetSoqlValue(sSoqlSchedulesDate, "SVMXC__Scheduled_Date__c");
		String sScheduledDateTimeServer = restServices.restGetSoqlValue(sSoqlScheduledDateTime, "SVMXC__Scheduled_Date_Time__c");

		serverDate = checklistPo.server_date(commonUtility, sSOUDate);
		Assert.assertTrue(sScheduledDateServer.equals(serverDate), "Scheduled Date syned to server expected : " + sScheduledDateServer + " actual : " + sSOUDate + "");
		ExtentManager.logger.log(Status.PASS, "Scheduled date has synced to server  expected : " + sScheduledDateServer + " actual : " + serverDate + "");

		System.out.println(sScheduledDateTimeServer);
		Assert.assertTrue(sScheduledDateTimeServer.contains((sScheduledDatetimesalesforce)), "ScheduledTime Date syned to server expected : " + sScheduledDateTimeServer + " actual : " + sScheduledDatetimesalesforce + "");
		ExtentManager.logger.log(Status.PASS, "Scheduled datetime has synced to server expected : " + sScheduledDateTimeServer + " actual : " + sScheduledDatetimesalesforce + "");

		sBillTypeServer = restServices.restGetSoqlValue(sSoqlqueryWO, "SVMXC__Billing_Type__c");
		sProformaServer = restServices.restGetSoqlValue(sSoqlProforma, "SVMXC__Proforma_Invoice__c");
		sNoOftimesServer = restServices.restGetSoqlValue(sSoqlNoOfTimes, "SVMXC__NoOfTimesAssigned__c");
		sNoOftimesServer1 = sNoOftimesServer.substring(0, sNoOftimesServer.length() - 2);

		Assert.assertTrue(sBillTypeServer.equals(sBillingTypeSOU), "Picklist source object not syned to server");
		ExtentManager.logger.log(Status.PASS, "Picklist Source object update has synced to server");

		Assert.assertTrue(sProformaServer.equals(sProformaInvoiceSOU), "Text source object not syned to server");
		ExtentManager.logger.log(Status.PASS, "Text Source object update has synced to server");

		Assert.assertTrue(sNoOftimesServer1.equals(sNooftimesAssignedSOU), "Number source object not syned to server");
		ExtentManager.logger.log(Status.PASS, "Number Source object update has synced to server");
	}

}
