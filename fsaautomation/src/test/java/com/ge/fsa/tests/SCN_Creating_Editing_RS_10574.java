/*
 *  @author Vinod Tharavath
 *  This Test Script validates the folliwng.
 *  Create a new Work Order and then Event for it and validate if it is visible in server.
 *  Create a work Order in server ( here through API ) validate if it is shown up in data sync
 *  Create a new event from  work order from WO which has been synced from server.
 *  Create d WorkOrder .. perform a sync and verify if workorder is not visible in the APP.
 *   */
package com.ge.fsa.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;


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
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		System.out.println(sExploreSearch);
		
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID,sSheetName, "EditProcessName");
		sSubject = "Testing "+sTestCaseID;
		//sWOName = "WO-00002005";
		// running the Sahi Script Pre-requisites -to set New event from Work Order into Wizard
		genericLib.executeSahiScript("appium/SCN_SelfDispatch_RS_10562_prerequisite.sah", "sTestCaseID");
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Sahi verification is successful");
		
		 sRandomNumber = commonsPo.generaterandomnumber("");
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
		System.out.println("Deleted"+sSqlWOQuery1);
		
	}
	
	
	public void DeletionOfEvents() throws Exception
	{
		
		sObjectApi = "SVMXC__SVMX_Event__c?";
		sSqlWOQuery1 ="SELECT+id+from+SVMXC__SVMX_Event__c+Where+Name+=\'"+sEventSubject+"\'";	
		System.out.println("sSqlWOQuery1 = "+sSqlWOQuery1);
		sDelWo =restServices.restGetSoqlValue(sSqlWOQuery1,"Id"); 
		System.out.println("sDelWo = "+sSqlWOQuery1);
		sObjectApi = "SVMXC__Service_Order__c";
		restServices.restDeleterecord(sObjectApi,sDelWo);
		System.out.println("Deleted"+sSqlWOQuery1);
		
	}
	
	public void syncwithConflict() throws Exception
	
	{
		//Navigation to Tools screen
		commonsPo.tap(toolsPo.getEleToolsIcn());	
		Assert.assertTrue(toolsPo.getEleSyncDataNowLnk().isDisplayed(), "Tools screen is not displayed");
		ExtentManager.logger.log(Status.PASS,"Tools screen is displayed successfully");
		commonsPo.tap(toolsPo.getEleSyncDataNowLnk());
		//getEleSyncDataNowLnk().click();
		commonsPo.tap(toolsPo.getEleSyncDataNowLnk());	
		toolsPo.getEleStartSyncBtn().click();
		commonsPo.tap(toolsPo.getEleStartSyncBtn());
		commonsPo.waitforElement(toolsPo.getEleRefreshingViewTxt(),  GenericLib.lWaitTime);
	try {
			//Verification of successful sync
			Assert.assertTrue(toolsPo.getEleSuccessTxt().isDisplayed(), "Data sync is not successfull");
			ExtentManager.logger.log(Status.PASS,"Data Sync is successfull");
		} catch (Exception e) {
			commonsPo.tap(toolsPo.geteleResolve());
			commonsPo.tap(toolsPo.geteleResolveissue());
			commonsPo.tap(toolsPo.geteleApply());
			commonsPo.tap(toolsPo.getEleOkBtn());
			commonsPo.tap(toolsPo.getEleSyncDataNowLnk());	
			toolsPo.getEleStartSyncBtn().click();
			commonsPo.tap(toolsPo.getEleStartSyncBtn());
			commonsPo.waitforElement(toolsPo.getEleRefreshingViewTxt(),  GenericLib.lWaitTime);
			Assert.assertTrue(toolsPo.getEleSuccessTxt().isDisplayed(), "Data sync is not successfull");
			ExtentManager.logger.log(Status.PASS,"Data Sync is successfull");
		}
	}
	
	
	@Test(enabled = true)
	///@Test(enabled = true,dependsOnMethods= {"SCN_SrctoTrgt_RS_10562Test"})
	public void RS_10574() throws Exception {
		
	//	new SCN_SelfDispatch_RS_10562().SCN_SrctoTrgt_RS_10562();
		
	//	restServices.getAccessToken();
	//	loginHomePo.login(commonsPo, exploreSearchPo);
	//	Thread.sleep(GenericLib.iMedSleep);
		
	// scn_SelfDispatch_RS_10562 =  new SCN_SelfDispatch_RS_10562();
	// scn_SelfDispatch_RS_10562.SCN_SrctoTrgt_RS_10562Test();
		
		
		
		PreRequisites1();	
		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
		Thread.sleep(GenericLib.iMedSleep);
		
		// Perform Config Sync
	//	toolsPo.configSync(commonsPo);		
		Thread.sleep(GenericLib.iMedSleep);
		// Need to sync the data
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iHighSleep);
		// Creating the Work Order
		createNewPO.createWorkOrder(commonsPo,sAccountName,sContactName, sProductName, "Medium", "Loan", sProformainVoice);
		toolsPo.syncData(commonsPo);
		Thread.sleep(2000);
		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");	
		// Select the Work Order from the Recent items
		recenItemsPO.clickonWorkOrder(commonsPo, sworkOrderName);
		ExtentManager.logger.log(Status.PASS,"Picked up work Order from Recent items");

		// To create a new Event for the given Work Order
		workOrderPo.createNewEvent(commonsPo,sEventSubject, "Test Description");
		commonsPo.tap(workOrderPo.geteleBacktoWorkOrderlnk());
			// Open the Work Order from the calendar
		calendarPO.openWofromCalendar(commonsPo, sworkOrderName);
		ExtentManager.logger.log(Status.PASS,"Validated event from Calendar");

		System.out.println("Sucessfully validated Create Event from WorkOrder after creating workOrder from FSA App");
		
		commonsPo.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(GenericLib.iHighSleep);
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());
		toolsPo.syncData(commonsPo);

//------------------Script to read a work Order and create an Event for it.----------------		
		
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);
		//------------------------------------------------------
		//Navigation to SFM
				
				//Set Start time for event
				workOrderPo.getEleStartDateTimeTxtFld().click();
				Thread.sleep(GenericLib.iMedSleep);
				commonsPo.switchContext("Native");
				commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
				//Edit the subject
				commonsPo.switchContext("Webview");
				workOrderPo.getEleSubjectTxtFld().sendKeys(sSubject);
				
				workOrderPo.getEleEndDateTimeTxtFld().click();
				Thread.sleep(GenericLib.iMedSleep);
				commonsPo.switchContext("Native");
				
				commonsPo.setDatePicker(1, 1);
				commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
				
				commonsPo.switchContext("Webview");
				commonsPo.tap(workOrderPo.getEleSaveLnk());
				Thread.sleep(GenericLib.iLowSleep);

				//Validation of auto update process
				Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Update process is not successful.");
				ExtentManager.logger.log(Status.PASS,"Event saved successfully.");
				
				commonsPo.tap(calendarPO.getEleCalendarIcn());
				Thread.sleep(GenericLib.iLowSleep);
				//----------------------------Deleting the work Order,event and sync back
				DeletionOfWorkOrder();
				ExtentManager.logger.log(Status.PASS,"Work Order Deleted Sucessfully");
				DeletionOfEvents();
				ExtentManager.logger.log(Status.PASS,"Event Deleted Sucessfully");

				// Perform Config Sync
			//	toolsPo.configSync(commonsPo);		
				Thread.sleep(GenericLib.iMedSleep);
			
				syncwithConflict();
				try {
					//workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
					commonsPo.tap(calendarPO.getEleCalendarClick());
					Thread.sleep(3000);
					calendarPO.VerifyWOInCalender(commonsPo,sworkOrderName);
				} catch (Exception e) {
					// TODO: handle exception
					ExtentManager.logger.log(Status.PASS,"WorkOrder not found as deleted from server.");

				}
	}

}
