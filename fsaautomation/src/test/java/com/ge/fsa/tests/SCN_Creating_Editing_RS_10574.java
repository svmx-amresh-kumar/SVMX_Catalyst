/*
 *  @author Vinod Tharavath
 *  To Validate Scheduled  Sync Due. Validation include - 
 *  first set scheduled data sync value to 5
 *  then validate if scheduled data sync triggers and if the worked out created from api is synced to FSA
 *  then validate changes made in FSA is scheduled synced to server 
 *  then validate if setting is changed back to 10000
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
	 SCN_SelfDispatch_RS_10562 scn_SelfDispatch_RS_10562  = null;
	
	
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
		 sEventSubject = "EventName"+sRandomNumber;
		
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
		
		Thread.sleep(genericLib.iHighSleep);
		
		// Collecting the Work Order number from the Server.
		sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		restServices.getAccessToken();
		sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");	
			
	}
	
	public void DeletionOfWorkOrder() throws Exception
	{
		sObjectApi = "SVMXC__Service_Order__c?";
		sSqlWOQuery1 ="SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";			
		sDelWo =restServices.restGetSoqlValue(sSqlWOQuery1,"Id"); 
		restServices.restDeleterecord(sObjectApi,sDelWo);
		System.out.println("Deleted"+sSqlWOQuery1);
	
	
		
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
		toolsPo.configSync(commonsPo);		
		Thread.sleep(GenericLib.iMedSleep);
		// Need to sync the data
		toolsPo.syncData(commonsPo);
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
				ExtentManager.logger.log(Status.PASS,"WorkOrder saved successfully.");
				
				commonsPo.tap(calendarPO.getEleCalendarIcn());
				Thread.sleep(GenericLib.iLowSleep);

				
				//----------------------------Deleting the work Order and sync back
				DeletionOfWorkOrder();
				// Perform Config Sync
				toolsPo.configSync(commonsPo);		
				Thread.sleep(GenericLib.iMedSleep);
				// Need to sync the data
				toolsPo.syncData(commonsPo);
				
				try {
					workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);

				} catch (Exception e) {
					// TODO: handle exception
					ExtentManager.logger.log(Status.PASS,"WorkOrder not found as deleted from server.");

				}
				
	
		
	}

}
