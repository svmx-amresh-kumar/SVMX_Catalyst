/*
 *  @author Vinod Tharavath
 *  This Test Script validates the folliwng.
 *  Create a new Work Order and then Event for it and validate if it is visible in server.
 *  Create a work Order in server ( here through API ) validate if it is shown up in data sync
 *  Create a new event from  work order from WO which has been synced from server.
 *  Create d WorkOrder .. perform a sync and verify if workorder is not visible in the APP.
 *   */
package com.ge.fsa.tests.tablet;


import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;


public class SCN_Creating_Editing_RS_10574 extends BaseLib {
	// For ServerSide Validations
	String sTestCaseID= null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sExploreChildSearchTxt = null;
	String sOrderStatusVal =null;
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
	
	
//	new SCN_SelfDispatch_RS_10562().
	
	public void PreRequisites1 () throws Exception
	{
		sTestCaseID = "SCN_Creating_Editing_RS_10574";
		sCaseWOID = "SCN_Creating_Editing_RS_10574";	
		sSheetName = "RS_10574";
		//Reading from the Excel sheet
//		sExploreSearch = GenericLib.readExcelData(GenericLib.sTestDataFile,sSheetName, "ExploreSearch");
//		System.out.println(sExploreSearch);
//		
//		sExploreChildSearchTxt = GenericLib.readExcelData(GenericLib.sTestDataFile,sSheetName, "ExploreChildSearch");
//		sFieldServiceName = GenericLib.readExcelData(GenericLib.sTestDataFile,sSheetName, "ProcessName");
//		sEditProcessName = GenericLib.readExcelData(GenericLib.sTestDataFile,sSheetName, "EditProcessName");
		sSubject = "Testing "+sTestCaseID;
		//sWOName = "WO-00002005";
		// running the Sahi Script Pre-requisites -to set New event from Work Order into Wizard
		genericLib.executeSahiScript("appium/SCN_SelfDispatch_RS_10562_prerequisite.sah");
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Sahi verification is successful");
		
		 sRandomNumber = commonUtility.generateRandomNumber("");
		 sProformainVoice = "Proforma"+sRandomNumber;
		 sEventSubject = "EventName_RS10574_1";
		// sEventSubject2 = "EventName_RS10574_2";

		// Creating Account from API
		sAccountName = "auto_account"+sRandomNumber;
		sAccountId = restServices.restCreate("Account?","{\"Name\":\""+sAccountName+"\"}");
		
		// Creating Product from API
		sProductName = "auto_product"+sRandomNumber;
		restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");
		
		
		// Creating Contact from API
				sFirstName = "auto_contact";
				sLastName = sRandomNumber;
				sContactName = sFirstName+" "+sLastName;
				System.out.println(sContactName);
				restServices.restCreate("Contact?","{\"FirstName\": \""+sFirstName+"\", \"LastName\": \""+sLastName+"\", \"AccountId\": \""+sAccountId+"\"}");
		
		//Create work Order
		restServices.getAccessToken();
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);	
		//Thread.sleep(genericLib.iHighSleep);
		
		// Collecting the Work Order number from the Server.
	//	sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		//restServices.getAccessToken();
	//	sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");	
			
	}
	
	public void DeletionOfWorkOrder() throws Exception
	{
		
		sObjectApi = "SVMXC__Service_Order__c?";
		sSqlWOQuery1 ="SELECT+id+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";	
		System.out.println("sSqlWOQuery1 = "+sSqlWOQuery1);
		sDelWo =restServices.restGetSoqlValue(sSqlWOQuery1,"Id"); 
		System.out.println("sDelWo = "+sSqlWOQuery1);
		sObjectApi = "SVMXC__Service_Order__c";
		restServices.restDeleterecord(sObjectApi,sDelWo);
		System.out.println("Deleted"+sDelWo);
		
	}
	
	
	public void DeletionOfEvents() throws Exception
	{
		
		sObjectApi = "SVMXC__SVMX_Event__c?";
		sSqlWOQuery1 ="SELECT+id+from+SVMXC__SVMX_Event__c+Where+Name+=\'"+sEventSubject+"\'";	
		System.out.println("sSqlWOQuery1 = "+sSqlWOQuery1);
		sDelWo =restServices.restGetSoqlValue(sSqlWOQuery1,"Id"); 
		System.out.println("sDelWo = "+sSqlWOQuery1);
		sObjectApi = "SVMXC__SVMX_Event__c";
		restServices.restDeleterecord(sObjectApi,sDelWo);
		System.out.println("Deleted"+sDelWo);
		
	}
	
	public void syncwithConflict() throws Exception
	
	{
		//Navigation to Tools screen
		commonUtility.tap(toolsPo.getEleToolsIcn());	
		Assert.assertTrue(toolsPo.getEleSyncDataNowLnk().isDisplayed(), "Tools screen is not displayed");
		ExtentManager.logger.log(Status.PASS,"Tools screen is displayed successfully");
		commonUtility.tap(toolsPo.getEleSyncDataNowLnk());
		//getEleSyncDataNowLnk().click();
		//commonsUtility.tap(toolsPo.getEleSyncDataNowLnk());	
		//toolsPo.getEleStartSyncBtn().click();
		commonUtility.tap(toolsPo.getEleStartSyncBtn());
		commonUtility.waitforElement(toolsPo.getEleRefreshingViewTxt(),180);
	try {
			//Verification of successful sync
			Assert.assertTrue(toolsPo.getEleSuccessTxt().isDisplayed(), "Data sync is not successfull");
			ExtentManager.logger.log(Status.PASS,"Data Sync is successfull");
		} catch (Exception e) {
			commonUtility.tap(toolsPo.geteleResolve());
			commonUtility.tap(toolsPo.geteleResolveissue());
			commonUtility.tap(toolsPo.geteleApply());
			commonUtility.tap(toolsPo.getEleOkBtn());
			commonUtility.tap(toolsPo.getEleSyncDataNowLnk());	
			toolsPo.getEleStartSyncBtn().click();
			commonUtility.tap(toolsPo.getEleStartSyncBtn());
			commonUtility.waitforElement(toolsPo.getEleRefreshingViewTxt(),240);
			Assert.assertTrue(toolsPo.getEleSuccessTxt().isDisplayed(), "Data sync is not successfull");
			ExtentManager.logger.log(Status.PASS,"Data Sync is successfull");
		}
	}
	
	
	@Test(retryAnalyzer=Retry.class)
	///@Test(enabled = true,dependsOnMethods= {"SCN_SrctoTrgt_RS_10562Test"})
	public void RS_10574() throws Exception {
		
	//	new SCN_SelfDispatch_RS_10562().SCN_SrctoTrgt_RS_10562();
		
	//	restServices.getAccessToken();
	//	loginHomePo.login(commonsUtility, exploreSearchPo);
	//	Thread.sleep(GenericLib.iMedSleep);
		
	// scn_SelfDispatch_RS_10562 =  new SCN_SelfDispatch_RS_10562();
	// scn_SelfDispatch_RS_10562.SCN_SrctoTrgt_RS_10562Test();
		
		
		
		PreRequisites1();	
		//Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
		Thread.sleep(GenericLib.iMedSleep);
		
		// Perform Config Sync
		//toolsPo.configSync(commonsUtility);		
		Thread.sleep(GenericLib.iMedSleep);
		// Need to sync the data
		toolsPo.syncData(commonUtility);
		Thread.sleep(GenericLib.iHighSleep);
		// Creating the Work Order
		createNewPO.createWorkOrder(commonUtility,sAccountName,sContactName, sProductName, "Medium", "Loan", sProformainVoice);
		toolsPo.syncData(commonUtility);
		Thread.sleep(2000);
		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");	
		// Select the Work Order from the Recent items
		recenItemsPO.clickonWorkOrder(commonUtility, sworkOrderName);
		ExtentManager.logger.log(Status.PASS,"Picked up work Order from Recent items");

		// To create a new Event for the given Work Order
		workOrderPo.createNewEvent(commonUtility,sEventSubject, "Test Description");
		commonUtility.tap(workOrderPo.geteleBacktoWorkOrderlnk());
			// Open the Work Order from the calendar
		calendarPO.openWoFromCalendar(commonUtility, sworkOrderName);
		ExtentManager.logger.log(Status.PASS,"Validated event from Calendar");

		System.out.println("Sucessfully validated Create Event from WorkOrder after creating workOrder from FSA App");
		
		commonUtility.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(GenericLib.iHighSleep);
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		toolsPo.syncData(commonUtility);
		Thread.sleep(GenericLib.iHighSleep);
//------------------Script to read a work Order and create an Event for it.----------------		
		
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sWOName, "Create New Event From Work Order");
		//------------------------------------------------------
		//Navigation to SFM
				
				//Set Start time for event
//				workOrderPo.getEleStartDateTimeTxtFld().click();
//				Thread.sleep(GenericLib.iMedSleep);
//				commonsUtility.switchContext("Native");
//				commonsUtility.tap(commonsUtility.getEleDonePickerWheelBtn());
				commonUtility.setDateTime24hrs(workOrderPo.getEleStartDateAndTimeTxtFld(), 0,"0","0");
				//Edit the subject
				//commonsUtility.switchContext("Webview");
				workOrderPo.getEleSubjectTxtFld().sendKeys(sSubject);
				
//				workOrderPo.getEleEndDateTimeTxtFld().click();
//				Thread.sleep(GenericLib.iMedSleep);
//				commonsUtility.switchContext("Native");
//				
//				commonsUtility.setDatePicker(1, 1);
//				commonsUtility.tap(commonsUtility.getEleDonePickerWheelBtn());
//				
//				commonsUtility.switchContext("Webview");
				commonUtility.setDateTime24hrs(workOrderPo.getEleEndDateAndTimeTxtFld(), 0,"0","0");
				commonUtility.tap(workOrderPo.getEleSaveLnk());
				Thread.sleep(GenericLib.iLowSleep);

				//Validation of auto update process
				Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Update process is not successful.");
				ExtentManager.logger.log(Status.PASS,"Event saved successfully.");
				
				commonUtility.tap(calendarPO.getEleCalendarIcn());
				Thread.sleep(GenericLib.iLowSleep);
				//toolsPo.syncData(commonsUtility);
				syncwithConflict();

				//----------------------------Deleting the work Order,event and sync back
				DeletionOfWorkOrder();
				ExtentManager.logger.log(Status.PASS,"Work Order Deleted Sucessfully");
				DeletionOfEvents();
				ExtentManager.logger.log(Status.PASS,"Event Deleted Sucessfully");

				// Perform Config Sync
			//	toolsPo.configSync(commonsUtility);		
				Thread.sleep(GenericLib.iMedSleep);
			
				syncwithConflict();
					//workOrderPo.navigatetoWO(commonsUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
					commonUtility.tap(calendarPO.getEleCalendarClick());
					Thread.sleep(3000);
					calendarPO.VerifyWOInCalender(commonUtility,sworkOrderName);
					ExtentManager.logger.log(Status.PASS,"WorkOrder not found as deleted from server.");

					//ExtentManager.logger.log(Status.PASS,"Event is no longer displayed in calendar");

			
	}

}
