package com.ge.fsa.tests.phone;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_ExploreSearchRS_10543 extends BaseLib{

	
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
		sSerialNumber = commonUtility.generaterandomnumber("RS_10543_");
		
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
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}

	@Test(enabled = true, retryAnalyzer=Retry.class)
	public void RS_10543Test() throws Exception {
		
		commonUtility.preReqSetup(genericLib);
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
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		//Data Sync for WO's created
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		
		//Config Sync for process
		ph_MorePo.syncData(commonUtility);
		
		//Navigation to SFM
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		ph_ExploreSearchPo.getEleSearchListItem(sExploreChildSearchTxt).click();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sWOName + "\n");
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), "No Records text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records text is successfully displayed");
		
		ph_ExploreSearchPo.getEleOnline().click();
		Thread.sleep(GenericLib.iHighSleep);
		
		Assert.assertTrue(ph_ExploreSearchPo.getDownloadIcon(sWOName).isDisplayed(), "Download Icon is not displayed");
		ExtentManager.logger.log(Status.PASS,"Download Icon  is successfully displayed");
		
		ph_ExploreSearchPo.getDownloadIcon(sWOName).click();
		Thread.sleep(GenericLib.iHighSleep);
		ph_ExploreSearchPo.getEleSearchListItem(sWOName).click();
		
		ph_WorkOrderPo.selectAction(commonUtility, sFieldServiceName);

		//Selecting Billing Type to contract to make sure sfm is working fine.
		ph_WorkOrderPo.selectFromPickList(commonUtility, ph_WorkOrderPo.getEleBillingTypeField(), sBillingType);
		ph_WorkOrderPo.getElesave().click();
		Thread.sleep(3000);
		//Validation of qualifying workorder with Issue found text error.
		Assert.assertTrue(ph_WorkOrderPo.verifyWorkOrder().contains("View Work Order "+sWOName),"Word order event is not saved correctly.");
		ExtentManager.logger.log(Status.PASS,"work order is saved successfully");
		
		//Steps for Case search
		sExploreChildSearchTxt="Cases";
		sFieldServiceName="Add/Edit Case Lines";
		
		//Navigation to SFM
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		ph_ExploreSearchPo.getEleSearchListItem(sExploreChildSearchTxt).click();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sCaseID + "\n");
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), "No Records text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records text is successfully displayed");
		
		ph_ExploreSearchPo.getEleOnline().click();
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(ph_ExploreSearchPo.getDownloadIcon(sCaseID).isDisplayed(), "Download Icon is not displayed");
		ExtentManager.logger.log(Status.PASS,"Download Icon  is successfully displayed");
		
		ph_ExploreSearchPo.getDownloadIcon(sCaseID).click();
		Thread.sleep(GenericLib.iHighSleep);
		ph_ExploreSearchPo.getEleSearchListItem(sCaseID).click();
		
		//Update case reason from server 
		//sCaseObjectID="5003D000003CFGQQA4";
		sObjectApi = "Case";
		sJsonData="{\"Reason\":\""+"Existing problem"+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sCaseObjectID);
	
		//Data Sync for WO's created
		ph_MorePo.syncData(commonUtility); 
		//driver.activateApp(GenericLib.sAppBundleID);
		
		//Navigation to Case edit process
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		ph_ExploreSearchPo.selectFromLookupSearchList(commonUtility,ph_ExploreSearchPo.getEleSearchListItem(sExploreChildSearchTxt), sCaseID);
		Thread.sleep(GenericLib.iMedSleep);
		ph_WorkOrderPo.selectAction(commonUtility, sFieldServiceName);
		Thread.sleep(GenericLib.iMedSleep);
		//Validation that case reason is not updated
		Assert.assertTrue(ph_WorkOrderPo.getEleCaseReasonField().getText().equals("--None--"), " Case reason is updated before refresh from salesforce");
		ExtentManager.logger.log(Status.PASS,"Case reason is not updated, as Refresh from Saleforce is not done");
		ph_WorkOrderPo.getEle("--None--").click();
		ph_WorkOrderPo.getEleBackButton().click();
		Thread.sleep(GenericLib.iMedSleep);
		
		//Selecting Case reason to Existing Problem to make sure sfm is working fine.
		//ph_WorkOrderPo.selectAction(commonUtility, "Refresh from Salesforce");
		ph_MorePo.syncData(commonUtility);
		ph_ExploreSearchPo.geteleExploreIcn().click();
		Thread.sleep(GenericLib.iMedSleep);
		
		ph_WorkOrderPo.selectAction(commonUtility, sFieldServiceName);
		Thread.sleep(GenericLib.iMedSleep);
		
		//Validation of Case reason is not updated.
		Assert.assertTrue(ph_WorkOrderPo.getEleCaseReasonField().getText().equals("Existing problem"), " Case reason is updated after refresh from salesforce");
		ExtentManager.logger.log(Status.PASS,"Case reason is updated correctly after Refresh from Saleforce ");
			
		
		//Validation of qualifying workorder with Issue found text error.
		ph_WorkOrderPo.selectFromPickList(commonUtility, ph_WorkOrderPo.getEleCaseReasonField(), "Complex functionality");
		ph_WorkOrderPo.getElesave().click();
		Assert.assertTrue(ph_WorkOrderPo.verifyWorkOrder().contains("View Support Case Details "+sCaseID),"Case details is not saved correctly.");

		ph_MorePo.syncData(commonUtility);
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
		{	
//			try {
//				postCleanup();ph_MorePo.syncData(commonUtility);
//				Thread.sleep(GenericLib.iMedSleep);
//				if(bExecutionFlag) {
//				ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestID + "Testcase execution failed");}
//
//			}catch(Exception e) {
//				postCleanup();ph_MorePo.syncData(commonUtility);
//				Thread.sleep(GenericLib.iMedSleep);
//				if(bExecutionFlag) {
//				ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestID + "Testcase execution failed");}
//			}
			postCleanup();
			ph_MorePo.syncData(commonUtility);
			Thread.sleep(GenericLib.iMedSleep);
		}
	}
	
	private void postCleanup() throws Exception { 
		genericLib.executeSahiScript("appium/SCN_Explore_RS_10543_postcleanup.sah", sTestID);
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
	
	}

}
