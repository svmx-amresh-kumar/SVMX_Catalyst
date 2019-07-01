package com.ge.fsa.tests.phone;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
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
	boolean configSync;
	public void PreRequisites1() throws Exception {
		sTestCaseID = "SCN_Creating_Editing_RS_10574";
		sCaseWOID = "SCN_Creating_Editing_RS_10574";
		sSheetName = "RS_10574";
		sSubject = "Testing " + sTestCaseID;
		configSync=commonUtility.ProcessCheck(restServices, genericLib, sFieldServiceName, "SCN_SelfDispatch_RS_10562_prerequisite", sTestCaseID);

		ExtentManager.logger.log(Status.PASS, "Testcase " + sTestCaseID + "Sahi verification is successful");

		sRandomNumber = commonUtility.generateRandomNumber("");
		sProformainVoice = "Proforma" + sRandomNumber;
		sEventSubject = "EventName_RS10574_1";

		// Creating Account from API
		sAccountName = "auto_account" + sRandomNumber;
		sAccountId = restServices.restCreate("Account?", "{\"Name\":\"" + sAccountName + "\"}");
		ExtentManager.logger.log(Status.INFO, "Account has been created through rest web service with Name : "+sAccountName+" and returned AccountId : "+sAccountId);

		// Creating Product from API
		sProductName = "auto_product" + sRandomNumber;
		String sProductId=restServices.restCreate("Product2?", "{\"Name\":\"" + sProductName + "\" }");
		ExtentManager.logger.log(Status.INFO, "Account has been created through rest web service with Name : "+sProductName+" and returned AccountId : "+sProductId);

		// Creating Contact from API
		sFirstName = "auto_contact";
		sLastName = sRandomNumber;
		sContactName = sFirstName + " " + sLastName;
		System.out.println(sContactName);
		String sContactId=restServices.restCreate("Contact?", "{\"FirstName\": \"" + sFirstName + "\", \"LastName\": \"" + sLastName
				+ "\", \"AccountId\": \"" + sAccountId + "\"}");
		ExtentManager.logger.log(Status.INFO, "Account has been created through rest web service with AccountId : "+sAccountId+" and returned AccountId : "+sContactId);

		// Create work Order
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		sWOName = restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);
		ExtentManager.logger.log(Status.INFO, "Work Order has been created through rest web service. WorkOrderId : "+sWORecordID);

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
		System.out.println("sDelWo = " + sSqlWOQuery1);
		sObjectApi = "SVMXC__Service_Order__c";
		restServices.restDeleterecord(sObjectApi, sDelWo);
		ExtentManager.logger.log(Status.PASS, "Work order got deleted Successfully. WorkOrder : "+sDelWo);
	}

	public void DeletionOfEvents() throws Exception {

		sObjectApi = "SVMXC__SVMX_Event__c?";
		sSqlWOQuery1 = "SELECT+id+from+SVMXC__SVMX_Event__c+Where+Name+=\'" + sEventSubject + "\'";
		System.out.println("sSqlWOQuery1 = " + sSqlWOQuery1);
		sDelWo = restServices.restGetSoqlValue(sSqlWOQuery1, "Id");
		System.out.println("sDelWo = " + sSqlWOQuery1);
//		sObjectApi = "SVMXC__SVMX_Event__c";
//		restServices.restDeleterecord(sObjectApi, sDelWo);
//		System.out.println("Deleted" + sDelWo);
		if(sDelWo==null) {
			ExtentManager.logger.log(Status.PASS, "Event got Deleted Sucessfully when work order is deleted");
		}
		else {
			ExtentManager.logger.log(Status.FAIL, "Event not got Deleted when work order is deleted. Event : "+sDelWo);
		}

	}

	public void syncWithConflict() throws Exception

	{
		// Navigation to Tools screen
		ph_MorePo.getEleDataSync().click();
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
		commonUtility.press(ph_MorePo.getEleMoreBtn().getLocation());
	}

	@Test(retryAnalyzer = Retry.class)
	public void RS_10574() throws Exception {

		PreRequisites1();
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		// Need to sync the data
		ph_MorePo.syncData(commonUtility);
		
		// Creating the Work Order
		ph_CreateNewPo.createWorkOrder(commonUtility, sAccountName, sContactName, sProductName, "Medium", "Loan",
				sProformainVoice);
		ph_MorePo.syncData(commonUtility);
		
		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"
				+ sProformainVoice + "\'";
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery, "Name");
		sEventSubject=sEventSubject+sworkOrderName;
		
		// Select the Work Order from the Recent items
		ph_RecentsItemsPo.selectRecentsItem(commonUtility, sworkOrderName);
		ExtentManager.logger.log(Status.INFO, "Picked up work Order from Recent items");

		// To create a new Event for the given Work Order
		ph_WorkOrderPo.createNewEvent(commonUtility, sEventSubject, ph_CalendarPo);
		ExtentManager.logger.log(Status.INFO, "Created new event for "+sworkOrderName+" with subject "+sEventSubject);
		ph_WorkOrderPo.getEleBackButton().click();
		// Open the Work Order from the calendar
		ph_CalendarPo.openWoFromCalendar(sEventSubject);
		ExtentManager.logger.log(Status.INFO, "Validated event from Calendar.");
		Assert.assertTrue(ph_WorkOrderPo.verifyWorkOrder().contains("View Work Order "+sworkOrderName),
				"Word order event is not created.");
		System.out.println("Sucessfully validated Create Event from WorkOrder after creating workOrder from Go App");

		ph_CalendarPo.getEleCalendarBtn().click();
		ph_MorePo.syncData(commonUtility);

		// Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, "AUTOMATION SEARCH", "Work Orders", sWOName,
				"Create New Event From Work Order");
		ExtentManager.logger.log(Status.INFO, "Navigate to Action Create New Event From Work order for work order:"+sWOName);
		int hrs=0;
		try {
			hrs=Integer.parseInt(commonUtility.gethrsfromdevicetime());
		} catch (Exception e) {
			e.printStackTrace();
		}	
		commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleStartDateTimeTxtFld(), 0, Integer.toString(hrs), "00");
		ph_WorkOrderPo.getEleSubjectTxtFld().sendKeys(sSubject);
		commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleEndDateTimeTxtFld(), 0, Integer.toString(hrs+1), "00");
		ph_WorkOrderPo.getEleSaveLnk().click();
		Thread.sleep(CommonUtility.iLowSleep);
		ExtentManager.logger.log(Status.INFO, "Created New Event From Work Order");
		
		// Validation of auto update process
		Assert.assertTrue(ph_WorkOrderPo.getEleViewEvent().isDisplayed(), "Update process is not successful.");
		ExtentManager.logger.log(Status.PASS, "Created new event for "+sWOName+" with subject "+sSubject+" is getting displayed");
		ph_WorkOrderPo.getEleBackButton().click();
		ph_CalendarPo.getEleCalendarBtn().click();
		syncWithConflict();

		// ----------------------------Deleting the work Order,event and sync back
		DeletionOfWorkOrder();
		ExtentManager.logger.log(Status.PASS, "Work Order Deleted Sucessfully");
		DeletionOfEvents();
		ExtentManager.logger.log(Status.PASS, "Event Deleted Sucessfully");


		syncWithConflict();
		ph_CalendarPo.VerifyWOInCalender(commonUtility, sSubject);

	}

}
