/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests.tablet;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_ExploreSearchRS_10543 extends BaseLib {
	
	int iWhileCnt = 0;
	String sTestID = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sWoObjectID = null;
	String sCaseObjectID = null;	
	String sFieldServiceName = null;
	String sSerialNumber = null;
	String sSqlQuery = null;
	String sWOName = null;
	String sCaseID = null;
	String sProductName = null;
	String sBillingType = null;
	Boolean bExecutionFlag = false;
	
	private void preRequiste() throws Exception { 

		restServices.getAccessToken();
		sSerialNumber = commonsUtility.generaterandomnumber("RS_10543_");
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sWoObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWoObjectID+"\'";				
		sWOName =restServices.restGetSoqlValue(sSqlQuery,"Name"); //"WO-00000455"; 
		
		sJsonData = "{\"Origin\": \"phone\", \"Subject\": \"Test RS_10543 Validation\", \"Priority\": \"High\", \"Description\": \"Description of RS_10543 \",\"Status\": \"Escalated\"}";
		sObjectApi = "Case?";
		sCaseObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+CaseNumber+from+Case+Where+id+=\'"+sCaseObjectID+"\'";				
		sCaseID  =restServices.restGetSoqlValue(sSqlQuery,"CaseNumber"); 
		
		genericLib.executeSahiScript("appium/SCN_Explore_RS_10543_prerequisite.sah", sTestID);
		Assert.assertTrue(commonsUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}

	@Test(enabled = true, retryAnalyzer=Retry.class)
	public void RS_10543Test() throws Exception {
		
		commonsUtility.preReqSetup(genericLib);
		// Resinstall the app
		lauchNewApp("false");
		
		sTestID = "RS_10543";
		sExploreSearch = GenericLib.getExcelData(sTestID, sTestID,"ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestID, sTestID,"ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestID,sTestID, "ProcessName");
		sBillingType = GenericLib.getExcelData(sTestID,sTestID, "BillingType");
		try {
		preRequiste();
		//sWOName="WO-00007492";
		//sCaseID="00001293";
		
		//Pre Login to app
		loginHomePo.login(commonsUtility, exploreSearchPo);
		
		//Data Sync for WO's created
		toolsPo.syncData(commonsUtility);
		Thread.sleep(GenericLib.iMedSleep); 
		
		//Config Sync for process
		toolsPo.configSync(commonsUtility);
		Thread.sleep(GenericLib.iMedSleep);
		
		//Navigation to SFM
		commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
		exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		commonsUtility.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		commonsUtility.longPress(exploreSearchPo.getEleExploreChildSearchTxt(sExploreChildSearchTxt));
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(GenericLib.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sWOName);
		commonsUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleNoRecordsTxt().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		commonsUtility.longPress(workOrderPo.getEleIncludeOnlineRdBtn());
		commonsUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iHighSleep);
		
		Assert.assertTrue(workOrderPo.getEleCloudIcn().isDisplayed(), "Cloud Icon is not displayed");
		ExtentManager.logger.log(Status.PASS,"Cloud Icon  is successfully displayed");
		
		commonsUtility.tap(workOrderPo.getEleCloudIcn(),20,20);
		Thread.sleep(GenericLib.iHighSleep);
		commonsUtility.tap(exploreSearchPo.getEleWorkOrderIDTxt(sWOName),10,10);
		
		Thread.sleep(GenericLib.iMedSleep);
		//workOrderPo.getEleActionsLnk().click();
		commonsUtility.tap(workOrderPo.getEleActionsLnk());	
		Thread.sleep(GenericLib.iLowSleep);
		commonsUtility.getSearch(workOrderPo.getEleActionsTxt(sFieldServiceName));
		commonsUtility.tap(workOrderPo.getEleActionsTxt(sFieldServiceName),20,20);

		//Selecting Billing Type to contract to make sure sfm is working fine.
		commonsUtility.setPickerWheelValue(workOrderPo.getEleBillingTypeLst(), "Contract");
		commonsUtility.tap(workOrderPo.getEleSaveLnk());
		
		//Validation of qualifying workorder with Issue found text error.
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Saved successfully is not displayed");
		ExtentManager.logger.log(Status.PASS,"Saved successfully text is displayed successfully");
		
		//Steps for Case search
		sExploreChildSearchTxt="Cases";
		sFieldServiceName="Add/Edit Case Lines";
		
		//Navigation to SFM
		commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
		Thread.sleep(GenericLib.iMedSleep);
		commonsUtility.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		commonsUtility.longPress(exploreSearchPo.getEleExploreChildSearchTxt(sExploreChildSearchTxt));
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(GenericLib.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sCaseID);
		commonsUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleNoRecordsTxt().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		commonsUtility.longPress(workOrderPo.getEleIncludeOnlineRdBtn());
		commonsUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleCloudIcn().isDisplayed(), "Cloud Icon is not displayed");
		ExtentManager.logger.log(Status.PASS,"Cloud Icon  is successfully displayed");
		
		commonsUtility.tap(workOrderPo.getEleCloudIcn(),20,20);
		Thread.sleep(GenericLib.iMedSleep);
		commonsUtility.tap(exploreSearchPo.getEleWorkOrderIDTxt(sCaseID),10,10);
		
		//Update case reason from server 
		//sCaseObjectID="5003D000003CFGQQA4";
		sObjectApi = "Case";
		sJsonData="{\"Reason\":\""+"Existing problem"+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sCaseObjectID);

		commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
		Thread.sleep(1000);
	
		//Data Sync for WO's created
		toolsPo.syncData(commonsUtility);
		Thread.sleep(GenericLib.iMedSleep); 
		driver.activateApp(GenericLib.sAppBundleID);
		
		//Navigation to Case edit process
		commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
		Thread.sleep(10000);

		//Navigation to SFM
		Thread.sleep(GenericLib.iMedSleep);
		commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
		Thread.sleep(10000);
		commonsUtility.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		commonsUtility.longPress(exploreSearchPo.getEleExploreChildSearchTxt(sExploreChildSearchTxt));
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(GenericLib.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sCaseID);
		commonsUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iMedSleep);
		commonsUtility.tap(exploreSearchPo.getEleWorkOrderIDTxt(sCaseID),10,10);
		
		commonsUtility.tap(workOrderPo.getEleActionsLnk());	
		commonsUtility.getSearch(workOrderPo.getEleActionsTxt(sFieldServiceName));
		commonsUtility.tap(workOrderPo.getEleActionsTxt(sFieldServiceName),20,20);
		Thread.sleep(GenericLib.iMedSleep);
		//Validation that case reason is not updated
		try{workOrderPo.getEleCaseReasonLst().click();}catch(Exception e) {commonsUtility.tap(workOrderPo.getEleCaseReasonLst());}
		commonsUtility.switchContext("Native");
		
		try {
			Assert.assertTrue(commonsUtility.getElePickerWheelPopUp().getText().equals("--None--"), " Case reason is updated before refresh from salesforce");
			ExtentManager.logger.log(Status.PASS,"Case reason is not updated, Needs Refresh from Saleforce ");
			Thread.sleep(GenericLib.iMedSleep);
			commonsUtility.getEleDonePickerWheelBtn().click();
		}
		catch(Exception e) {
			Assert.assertTrue(commonsUtility.getElePicklistValue("--None--").getText().equals("--None--"), " Case reason is updated before refresh from salesforce");
			ExtentManager.logger.log(Status.PASS,"Case reason is not updated, Needs Refresh from Saleforce ");
			commonsUtility.getElePicklistValue("--None--").click();
		}
		Thread.sleep(GenericLib.iMedSleep);
		commonsUtility.switchContext("Webview");
		commonsUtility.tap(workOrderPo.getEleSaveLnk());
		Thread.sleep(GenericLib.iMedSleep);
		
		//Selecting Case reason to Existing Problem to make sure sfm is working fine.
		commonsUtility.tap(workOrderPo.getEleActionsLnk());	
		commonsUtility.getSearch(workOrderPo.getEleActionsTxt("Refresh from Salesforce"));
		commonsUtility.tap(workOrderPo.getEleActionsTxt("Refresh from Salesforce"),20,20);
		Thread.sleep(GenericLib.iMedSleep);
		
		commonsUtility.tap(workOrderPo.getEleActionsLnk());	
		commonsUtility.getSearch(workOrderPo.getEleActionsTxt(sFieldServiceName));
		commonsUtility.tap(workOrderPo.getEleActionsTxt(sFieldServiceName),20,20);
		Thread.sleep(GenericLib.iMedSleep);
		
		//Validation of Case reason is not updated.
		try{workOrderPo.getEleCaseReasonLst().click();}catch(Exception e) {commonsUtility.tap(workOrderPo.getEleCaseReasonLst());}
		commonsUtility.switchContext("Native");
		Thread.sleep(GenericLib.iMedSleep);
		try {
			Assert.assertTrue(commonsUtility.getElePickerWheelPopUp().getText().equals("Existing problem"), " Case reason is updated before refresh from salesforce");
			ExtentManager.logger.log(Status.PASS,"Case reason is not updated, Needs Refresh from Saleforce ");
			Thread.sleep(GenericLib.iMedSleep);
			commonsUtility.getEleDonePickerWheelBtn().click();
			
		}
		catch(Exception e) {
			Assert.assertTrue(commonsUtility.getElePicklistValue("Existing problem").getText().equals("Existing problem"), " Case reason is updated before refresh from salesforce");
			ExtentManager.logger.log(Status.PASS,"Case reason is not updated, Needs Refresh from Saleforce ");
			commonsUtility.getElePicklistValue("Existing problem").click();
		}
		
		Thread.sleep(GenericLib.iMedSleep);
		commonsUtility.switchContext("Webview");
		
		commonsUtility.setPickerWheelValue(workOrderPo.getEleCaseReasonLst(), "Complex functionality");
		commonsUtility.tap(workOrderPo.getEleSaveLnk());

		//Validation of qualifying workorder with Issue found text error.
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Saved successfully is not displayed");
		ExtentManager.logger.log(Status.PASS,"Saved successfully text is displayed successfully");
		
		toolsPo.syncData(commonsUtility);
		Thread.sleep(GenericLib.iMedSleep);
		
		sSqlQuery ="SELECT+SVMXC__Billing_Type__c+from+SVMXC__Service_Order__c+Where+id+=\'"+sWoObjectID+"\'";				
		Assert.assertTrue(restServices.restGetSoqlValue(sSqlQuery,"SVMXC__Billing_Type__c").equals("Contract"), "Work Order is not updated with contract ");
		ExtentManager.logger.log(Status.PASS,"Work Order is updated with contract type successfully");
		
		sSqlQuery ="SELECT+Reason+from+Case+Where+id+=\'"+sCaseObjectID+"\'";				
		Assert.assertTrue(restServices.restGetSoqlValue(sSqlQuery,"Reason").equals("Complex functionality"), "Case is not updated with case reason from client");
		ExtentManager.logger.log(Status.PASS,"Case is updated with case reason successfully from client");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Test case PASSED");

		}
		catch(Exception e) {
			bExecutionFlag = true;
			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestID + " Testcase FAILED");
			throw e;
		}
		finally
		{	try {
				postCleanup();toolsPo.syncData(commonsUtility);
				Thread.sleep(GenericLib.iMedSleep);
				if(bExecutionFlag) {
				ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestID + "Testcase execution failed");}

			}catch(Exception e) {
				postCleanup();toolsPo.syncData(commonsUtility);
				Thread.sleep(GenericLib.iMedSleep);
				if(bExecutionFlag) {
				ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestID + "Testcase execution failed");}
			}
			toolsPo.syncData(commonsUtility);
			Thread.sleep(GenericLib.iMedSleep);
		}
	}
	
	private void postCleanup() throws Exception { 
		genericLib.executeSahiScript("appium/SCN_Explore_RS_10543_postcleanup.sah", sTestID);
		Assert.assertTrue(commonsUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
	
	}
}
