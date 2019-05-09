package com.ge.fsa.tests.phone;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.phone.Ph_CalendarPO;

public class Ph_SCN_Creating_Editing_RS_10574 extends BaseLib {

	// For ServerSide Validations
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
	String sBillingType = "Loan";
	String sSoqlqueryWO = null;
	String sBillTypeServer = null;
	String sWORecordID = null;

	String sAccountName = null;
	String sContactName = null;
	String sProductName = null;
	String sProformainVoice = null;
	String sEventSubject = null;
	String sRandomNumber = null;
	String sAccountId = null;
	String sworkOrderName = null;
	String sSoqlQuery = null;
	String sFirstName = null;
	String sLastName = null;
	String sSubject = null;
	String sDelWo = null;

	String sObjectApi = null;
	String sSqlWOQuery1 = null;

	public void PreRequisites1() throws Exception {
		sTestCaseID = "SCN_Creating_Editing_RS_10574";
		sCaseWOID = "SCN_Creating_Editing_RS_10574";
		sSheetName = "RS_10574";
		sSubject = "Testing " + sTestCaseID;
		// sWOName = "WO-00002005";
		// running the Sahi Script Pre-requisites -to set New event from Work Order into
		// Wizard
//		genericLib.executeSahiScript("appium/SCN_SelfDispatch_RS_10562_prerequisite.sah");
//		Assert.assertTrue(commonUtility.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS, "Testcase " + sTestCaseID + "Sahi verification is successful");

		sRandomNumber = commonUtility.generaterandomnumber("");
		sProformainVoice = "Proforma" + sRandomNumber;
		sEventSubject = "EventName_RS10574_1";
		// sEventSubject2 = "EventName_RS10574_2";

		// Creating Account from API
		sAccountName = "auto_account" + sRandomNumber;
		sAccountId = restServices.restCreate("Account?", "{\"Name\":\"" + sAccountName + "\"}");

		// Creating Product from API
		sProductName = "auto_product" + sRandomNumber;
		restServices.restCreate("Product2?", "{\"Name\":\"" + sProductName + "\" }");

		// Creating Contact from API
		sFirstName = "auto_contact";
		sLastName = sRandomNumber;
		sContactName = sFirstName + " " + sLastName;
		System.out.println(sContactName);
		restServices.restCreate("Contact?", "{\"FirstName\": \"" + sFirstName + "\", \"LastName\": \"" + sLastName
				+ "\", \"AccountId\": \"" + sAccountId + "\"}");

		// Create work Order
		restServices.getAccessToken();
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		sWOName = restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);
		sEventSubject = "EventName_RS10574_1"+sWOName;
		// Thread.sleep(genericLib.iHighSleep);

		// Collecting the Work Order number from the Server.
		// sSoqlQuery =
		// "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		// restServices.getAccessToken();
		// sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");

	}

	public void DeletionOfWorkOrder() throws Exception {

		sObjectApi = "SVMXC__Service_Order__c?";
		sSqlWOQuery1 = "SELECT+id+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'" + sProformainVoice
				+ "\'";
		System.out.println("sSqlWOQuery1 = " + sSqlWOQuery1);
		sDelWo = restServices.restGetSoqlValue(sSqlWOQuery1, "Id");
//		System.out.println("sDelWo = " + sSqlWOQuery1);
//		sObjectApi = "SVMXC__Service_Order__c";
//		restServices.restDeleterecord(sObjectApi, sDelWo);
//		System.out.println("Deleted" + sDelWo);
		if(sDelWo==null) {
			ExtentManager.logger.log(Status.PASS, "Event got Deleted Sucessfully when work order is deleted");
		}
		else {
			ExtentManager.logger.log(Status.FAIL, "Event not got Deleted when work order is deleted");
		}

	}

	public void DeletionOfEvents() throws Exception {

		sObjectApi = "SVMXC__SVMX_Event__c?";
		sSqlWOQuery1 = "SELECT+id+from+SVMXC__SVMX_Event__c+Where+Name+=\'" + sEventSubject + "\'";
		System.out.println("sSqlWOQuery1 = " + sSqlWOQuery1);
		sDelWo = restServices.restGetSoqlValue(sSqlWOQuery1, "Id");
		System.out.println("sDelWo = " + sSqlWOQuery1);
		sObjectApi = "SVMXC__SVMX_Event__c";
		restServices.restDeleterecord(sObjectApi, sDelWo);
		System.out.println("Deleted" + sDelWo);

	}

	public void syncwithConflict() throws Exception

	{
		// Navigation to Tools screen
		ph_MorePo.getEleDataSync().click();
		Thread.sleep(200);
		ph_MorePo.getEleSyncNow().click();
		commonUtility.waitForElementNotVisible(ph_MorePo.getEleSyncing(), 300);
		try {
			// Verification of successful sync
			Assert.assertTrue(commonUtility.isDisplayedCust(ph_MorePo.getEleDataSynccompleted()),
					"Data sync is not successfull");
			ExtentManager.logger.log(Status.PASS, "Data Sync is successfull");
		} catch (Error e) {
			ph_MorePo.getEleResolveBtn().click();
			ph_MorePo.getEleResolveConflict().click();
			ph_MorePo.getEleSyncNow().click();
			//ph_MorePo.getEleDataSync().click();
			commonUtility.waitforElement(ph_MorePo.getEleDataSynccompleted(), 300);
			Assert.assertTrue(commonUtility.isDisplayedCust(ph_MorePo.getEleDataSynccompleted()),
					"Data sync is not successfull");
			ExtentManager.logger.log(Status.PASS, "Data Sync is successfull");
		}
		ph_MorePo.getEleDataSync().click();
	}

	@Test(retryAnalyzer = Retry.class)
	/// @Test(enabled = true,dependsOnMethods= {"SCN_SrctoTrgt_RS_10562Test"})
	public void RS_10574() throws Exception {

		PreRequisites1();
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		Thread.sleep(GenericLib.iMedSleep);

		Thread.sleep(GenericLib.iMedSleep);
		// Need to sync the data
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.iHighSleep);
		// Creating the Work Order
		ph_CreateNewPo.createWorkOrder(commonUtility, sAccountName, sContactName, sProductName, "Medium", "Loan",
				sProformainVoice);
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(2000);
		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"
				+ sProformainVoice + "\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery, "Name");
		// Select the Work Order from the Recent items
		ph_RecentsItemsPo.selectRecentsItem(commonUtility, sworkOrderName);
		ExtentManager.logger.log(Status.PASS, "Picked up work Order from Recent items");

		// To create a new Event for the given Work Order
		ph_WorkOrderPo.createNewEvent(commonUtility, sEventSubject, ph_CalendarPo);
		ExtentManager.logger.log(Status.PASS, "Created new event for "+sworkOrderName+" with subject "+sEventSubject);
		ph_WorkOrderPo.getEleBackButton().click();
		// Open the Work Order from the calendar
		ph_CalendarPo.openWoFromCalendar(sEventSubject);
		ExtentManager.logger.log(Status.PASS, "Validated event from Calendar");
		Thread.sleep(3000);
		Assert.assertTrue(ph_WorkOrderPo.verifyWorkOrder().contains("View Work Order "+sworkOrderName),
				"Word order event is not created.");
		System.out.println("Sucessfully validated Create Event from WorkOrder after creating workOrder from FSA App");

		ph_CalendarPo.getEleCalendarBtn().click();
		ph_MorePo.syncData(commonUtility);
		//Thread.sleep(GenericLib.iHighSleep);
		// ------------------Script to read a work Order and create an Event for
		// it.----------------

		// Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, "AUTOMATION SEARCH", "Work Orders", sWOName,
				"Create New Event From Work Order");
		commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleStartDateTimeTxtFld(), 0, "0", "0");
		ph_WorkOrderPo.getEleSubjectTxtFld().sendKeys(sSubject);
		commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleEndDateTimeTxtFld(), 0, "0", "0");
		ph_WorkOrderPo.getEleSaveLnk().click();
		Thread.sleep(GenericLib.iLowSleep);

		// Validation of auto update process
		Assert.assertTrue(ph_WorkOrderPo.getEleViewEvent().isDisplayed(), "Update process is not successful.");
		ExtentManager.logger.log(Status.PASS, "Created new event for "+sWOName+" with subject "+sSubject);
		ExtentManager.logger.log(Status.PASS, "Event saved successfully.");
		ph_WorkOrderPo.getEleBackButton().click();
		ph_CalendarPo.getEleCalendarBtn().click();
		Thread.sleep(GenericLib.iLowSleep);
		syncwithConflict();

		// ----------------------------Deleting the work Order,event and sync back
		DeletionOfWorkOrder();
		ExtentManager.logger.log(Status.PASS, "Work Order Deleted Sucessfully");
		DeletionOfEvents();
		ExtentManager.logger.log(Status.PASS, "Event Deleted Sucessfully");

		// Perform Config Sync
		// toolsPo.configSync(commonsUtility);
		Thread.sleep(GenericLib.iMedSleep);

		syncwithConflict();
		// workOrderPo.navigatetoWO(commonsUtility, exploreSearchPo, sExploreSearch,
		// sExploreChildSearchTxt, sWOName);
		//ph_CalendarPo.getEleCalendarBtn().click();
		//Thread.sleep(3000);
		ph_CalendarPo.VerifyWOInCalender(commonUtility, sSubject);
		ExtentManager.logger.log(Status.PASS, "WorkOrder not found as deleted from server.");

		// ExtentManager.logger.log(Status.PASS,"Event is no longer displayed in
		// calendar");

	}

}
