/*
 *  @author Vinod Tharavath
 *  SCN_Checklist_1_RS-10577 Verify Source Object Update in Checklists
 *  
 */
package com.ge.fsa.tests.phone;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.RestServices;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.phone.Ph_WorkOrderPO;

public class Ph_SCN_Checklist_1_RS_10577 extends BaseLib {
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
	String sScheduleddatePrefill = "08/28/18";
	String sScheduledDateTimePrefill = "08/28/18 02:42";

	// Source object update values

	String sBillingType = null;
	String sBillingTypeSOU = "Loan";
	String sOrderStatus = null;
	String sOrderStatusSOU = "Open";
	String sIdleTime = null;
	String sIdleTimeSOU = "30";
	String sScheduledDate = null;
	String sScheduledDateSOU = null;
	String sScheduledDateTime = null;
	String sScheduledDateTimeSou = "28/08/2018 09:42";
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
	String sPrcessname1 = "vt";
	String sPrcname2 = "inactivetest";
	String sScriptName = "/appium/Scenario_RS-10577_Checklist_SOU.sah";
	String sProcessname = "Default title for Checklist";

	public void prerequisites() throws Exception {

		sSheetName = "RS_10577";
		System.out.println("SCN_RS10577_Checklist_SOU");
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
		sWOName = restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);
		// sWOName = "WO-00005043";

		bProcessCheckResult = commonUtility.ProcessCheck(restServices, sChecklistName, sScriptName, sTestCaseID);

	}

	@Test(retryAnalyzer = Retry.class)
	public void RS_10577() throws Exception {
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6478");
		}else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6746");

		}
			
		// Running Pre-Req
		commonUtility.preReqSetup();

		// Resinstall the app
		lauchNewApp("false");
		System.out.println("Finished Sanity Pre Req");

		// running the prerequisites for the script
		prerequisites();

		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
		// Pre Login to app

		// Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);

		// Navigating to Checklist
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName,
				sProcessname);
		ExtentManager.logger.log(Status.INFO, "WorkOrder dynamically created and used is :" + sWOName + "");

		// Click on ChecklistName
		ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
		ExtentManager.logger.log(Status.INFO, "Clicked ChecklistProcess" + sChecklistName + "");
		Thread.sleep(2000);

		// Starting new Checklist
		ph_ChecklistPO.getelecheckliststartnew(sChecklistName).click();
		Thread.sleep(2000);
		try {
			//untill app is stable this need to be there as sometimes its inprogress and sometimes completed.
			ph_ChecklistPO.geteleInProgress().click();

		} catch (Exception e) {
			ph_ChecklistPO.geteleChecklistCompleted().click();
		}

		// Submitting Checklist
		ph_ChecklistPO.geteleSubmitbtn().click();
		ExtentManager.logger.log(Status.INFO, "Checklist is submitted");

		// Navigation to WO
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName,
				sEditProcessName);
		// ------------------Validating the Source Object Updates------------------
		// 1.Picklist
		Thread.sleep(2000);
		sBillingType = ph_CreateNewPo.getElebillingtype().getText();
		Assert.assertEquals(sBillingType, sBillingTypeSOU,
				"Picklist source object update failed billing type not set to warranty");
		ExtentManager.logger.log(Status.PASS, "Picklist SOU Header sucessful in Client Expected: " + sBillingTypeSOU
				+ "  Actual : " + sBillingType + "");

		// 5. Text
		sProformaInvoice = ph_WorkOrderPo.geteleProformaInvoice().getText();
		Assert.assertEquals(sProformaInvoice, sProformaInvoiceSOU,
				"Text Source Object is not updated--Expected: " + sProformaInvoiceSOU + "Actual: " + sProformaInvoice);
		ExtentManager.logger.log(Status.PASS, "Source Object Update for Text with Value Sucessfull --Expected: "
				+ sProformaInvoiceSOU + " Actual: " + sProformaInvoice + "");

		// 4.Date
		sScheduledDate = ph_WorkOrderPo.getEleScheduledDate().getText().trim();
		System.out.println(sScheduledDate);
		String sScheduledDateSOU= ph_ChecklistPO.get_device_date(commonUtility);	
		System.out.println("Device retreived date:" + sScheduledDateSOU);
		Assert.assertEquals(sScheduledDate, sScheduledDateSOU, "Date Source Object is not updated");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for Date with function Today Sucessfull");

		// 3.DateTime sScheduledDateTime =
		sScheduledDateTime = ph_WorkOrderPo.getEleScheduledDateTime().getText().trim();
		System.out.println(sScheduledDateTime);
		Assert.assertEquals(sScheduledDateTime, sScheduledDateTimeSou, "DateTime Source Object is not updated");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for DateTime Sucessfull");
		
		// 2.Number
		commonUtility.custScrollToElement(ph_CreateNewPo.getElePriority());
		sNoOfTimesAssigned = ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getText();
		System.out.println(sNoOfTimesAssigned);
		Assert.assertEquals(sNoOfTimesAssigned, sNooftimesAssignedSOU, "Number Source Object is not updated--Expected:"
				+ sNooftimesAssignedSOU + " Actual : " + sNoOfTimesAssigned + "");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for Number with value  Sucessfull--Expected:"
				+ sNooftimesAssignedSOU + " Actual :" + sNoOfTimesAssigned + "");

		// 6. Checkbox
		// Checkbox Button validation
		commonUtility.custScrollToElement(ph_WorkOrderPo.getTxtProblemDescription());
		if (ph_WorkOrderPo.geteleEntPerformedOn().isDisplayed()) {
			ExtentManager.logger.log(Status.PASS,
					"Source Object Update for boolean Is entitlment is set to true: Sucessfull");
		} else {
			ExtentManager.logger.log(Status.FAIL,
					"Source Object Update for boolean Is entitlment is set to true: UNSucessfull");
		}

		// naviagating back to Work Orders
		ph_WorkOrderPo.getEleBackButton().click();
		ph_MorePo.syncData(commonUtility);
		// Navigation to WO
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName,
				sEditProcessName);

		// ------------------Validating the Source Object Updates after datasync-------------------------

		// 1.Picklist
		Thread.sleep(2000);
		sBillingType = ph_CreateNewPo.getElebillingtype().getText();
		Assert.assertEquals(sBillingType, sBillingTypeSOU,
				"Picklist source object update failed billing type not set to warranty");
		ExtentManager.logger.log(Status.PASS, "Picklist SOU Header sucessful in Client Expected: " + sBillingTypeSOU
				+ "  Actual : " + sBillingType + "");

		// 5. Text
		sProformaInvoice = ph_WorkOrderPo.geteleProformaInvoice().getText();
		Assert.assertEquals(sProformaInvoice, sProformaInvoiceSOU,
				"Text Source Object is not updated--Expected: " + sProformaInvoiceSOU + "Actual: " + sProformaInvoice);
		ExtentManager.logger.log(Status.PASS, "Source Object Update for Text with Value Sucessfull --Expected: "
				+ sProformaInvoiceSOU + " Actual: " + sProformaInvoice + "");

		// 4.Date
		sScheduledDate = ph_WorkOrderPo.getEleScheduledDate().getText().trim();
		System.out.println(sScheduledDate);
		System.out.println(sScheduledDateSOU);
		System.out.println("Device retreived date:" + sScheduledDateSOU);
		Assert.assertEquals(sScheduledDate, sScheduledDateSOU, "Date Source Object is not updated--actual :"+sScheduledDate+" Expected: "+sScheduledDateSOU+":");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for Date with function Today Sucessfull actual :"+sScheduledDate +" Expected: "+sScheduledDateSOU+"");
		
		// 3.DateTime sScheduledDateTime =
		sScheduledDateTime = ph_WorkOrderPo.getEleScheduledDateTime().getText().trim();
		System.out.println(sScheduledDateTime);
		Assert.assertEquals(sScheduledDateTime, sScheduledDateTimeSou, "DateTime Source Object is not updated");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for DateTime Sucessfull");

		// 2.Number
		commonUtility.custScrollToElement(ph_CreateNewPo.getElePriority());
		sNoOfTimesAssigned = ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getText();
		System.out.println(sNoOfTimesAssigned);
		Assert.assertEquals(sNoOfTimesAssigned, sNooftimesAssignedSOU, "Number Source Object is not updated--Expected:"
				+ sNooftimesAssignedSOU + " Actual : " + sNoOfTimesAssigned + "");
		ExtentManager.logger.log(Status.PASS, "Source Object Update for Number with value  Sucessfull--Expected:"
				+ sNooftimesAssignedSOU + " Actual :" + sNoOfTimesAssigned + "");

		// Checkbox Button validation
		commonUtility.custScrollToElement(ph_WorkOrderPo.getTxtProblemDescription());
		if (ph_WorkOrderPo.geteleEntPerformedOn().isDisplayed()) {
			ExtentManager.logger.log(Status.PASS,
					"Source Object Update for boolean Is entitlment is set to true: Sucessfull");
		} else {
			ExtentManager.logger.log(Status.FAIL,
					"Source Object Update for boolean Is entitlment is set to true: UNSucessfull");
		}
		
		// validating in server after source object update and if prefilled values are syned to server
		sChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"
				+ sWOName + "')";
		sChecklistQueryval = restServices.restGetSoqlValue(sChecklistQuery, "SVMXC__Status__c");
		Assert.assertTrue(sChecklistQueryval.contains(schecklistStatus), "checklist completed is not synced to server");
		ExtentManager.logger.log(Status.PASS, "Checklist Completed status is displayed in Salesforce after sync");

		ChecklistAnsjson = restServices.restGetSoqlValue(sChecklistQuery, "SVMXC__ChecklistJSON__c");
		Assert.assertTrue(ChecklistAnsjson.contains(sCityPrefill),
				"checklist text question answer is not synced to server");
		ExtentManager.logger.log(Status.PASS, "checklist text question answer with prefill synced to server");

		Assert.assertTrue(ChecklistAnsjson.contains(sIdleTimePrefill),
				"checklist number answer sycned to server in checklist answer");
		ExtentManager.logger.log(Status.PASS, "checklist number answer sycned to server in checklist answer");

		sSoqlqueryWO = "Select+SVMXC__Billing_Type__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName + "'";
		sSoqlProforma = "Select+SVMXC__Proforma_Invoice__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName + "'";
		sSoqlNoOfTimes = "Select+SVMXC__NoOfTimesAssigned__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName
				+ "'";
		
		// String sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlqueryWO,
		// "Id");
		sBillTypeServer = restServices.restGetSoqlValue(sSoqlqueryWO, "SVMXC__Billing_Type__c");
		sProformaServer = restServices.restGetSoqlValue(sSoqlProforma, "SVMXC__Proforma_Invoice__c");
		sNoOftimesServer = restServices.restGetSoqlValue(sSoqlNoOfTimes, "SVMXC__NoOfTimesAssigned__c");
		sNoOftimesServer1 = sNoOftimesServer.substring(0, sNoOftimesServer.length() - 2);

		Assert.assertTrue(sBillTypeServer.equals(sBillingTypeSOU), "Picklist source object not syned to server");
		ExtentManager.logger.log(Status.PASS, "Picklist Source object update has synced to server");

		Assert.assertTrue(sProformaServer.equals(sProformaInvoiceSOU), "Text source object not syned to server");
		ExtentManager.logger.log(Status.PASS, "Text Source object update has synced to server");
	}

}
