/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests.tablet;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
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
		sSerialNumber = commonUtility.generateRandomNumber("RS_10543_");
		
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
		
		commonUtility.executeSahiScript("appium/SCN_Explore_RS_10543_prerequisite.sah", sTestID);
		
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}

	@Test(enabled = true, retryAnalyzer=Retry.class)
	public void RS_10543Test() throws Exception {
		
		commonUtility.preReqSetup();
		// Resinstall the app
		lauchNewApp("false");
		
		sTestID = "RS_10543";
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sTestID,"ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sTestID,"ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sTestID, "ProcessName");
		sBillingType = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sTestID, "BillingType");
		try {
		preRequiste();
		//sWOName="WO-00007492";
		//sCaseID="00001293";
		
		//Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
		
		//Data Sync for WO's created
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep); 
		
		//Config Sync for process
		toolsPo.configSync(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		
		//Navigation to SFM
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		commonUtility.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		commonUtility.longPress(exploreSearchPo.getEleExploreChildSearchTxt(sExploreChildSearchTxt));
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(CommonUtility.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sWOName);
		commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(CommonUtility.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleNoRecordsTxt().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		commonUtility.longPress(workOrderPo.getEleIncludeOnlineRdBtn());
		commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(CommonUtility.iHighSleep);
		
		Assert.assertTrue(workOrderPo.getEleCloudIcn().isDisplayed(), "Cloud Icon is not displayed");
		ExtentManager.logger.log(Status.PASS,"Cloud Icon  is successfully displayed");
		
		commonUtility.tap(workOrderPo.getEleCloudIcn(),20,20);
		Thread.sleep(CommonUtility.iHighSleep);
		commonUtility.tap(exploreSearchPo.getEleWorkOrderIDTxt(sWOName),10,10);
		
		Thread.sleep(CommonUtility.iMedSleep);
		//workOrderPo.getEleActionsLnk().click();
		commonUtility.tap(workOrderPo.getEleActionsLnk());	
		Thread.sleep(CommonUtility.iLowSleep);
		commonUtility.getSearch(workOrderPo.getEleActionsTxt(sFieldServiceName));
		commonUtility.tap(workOrderPo.getEleActionsTxt(sFieldServiceName),20,20);

		//Selecting Billing Type to contract to make sure sfm is working fine.
		commonUtility.setPickerWheelValue(workOrderPo.getEleBillingTypeLst(), "Contract");
		commonUtility.tap(workOrderPo.getEleSaveLnk());
		
		//Validation of qualifying workorder with Issue found text error.
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Saved successfully is not displayed");
		ExtentManager.logger.log(Status.PASS,"Saved successfully text is displayed successfully");
		
		//Steps for Case search
		sExploreChildSearchTxt="Cases";
		sFieldServiceName="Add/Edit Case Lines";
		
		//Navigation to SFM
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		Thread.sleep(CommonUtility.iMedSleep);
		commonUtility.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		commonUtility.longPress(exploreSearchPo.getEleExploreChildSearchTxt(sExploreChildSearchTxt));
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(CommonUtility.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sCaseID);
		commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(CommonUtility.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleNoRecordsTxt().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		commonUtility.longPress(workOrderPo.getEleIncludeOnlineRdBtn());
		commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(CommonUtility.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleCloudIcn().isDisplayed(), "Cloud Icon is not displayed");
		ExtentManager.logger.log(Status.PASS,"Cloud Icon  is successfully displayed");
		
		commonUtility.tap(workOrderPo.getEleCloudIcn(),20,20);
		Thread.sleep(CommonUtility.iMedSleep);
		commonUtility.tap(exploreSearchPo.getEleWorkOrderIDTxt(sCaseID),10,10);
		
		//Update case reason from server 
		//sCaseObjectID="5003D000003CFGQQA4";
		sObjectApi = "Case";
		sJsonData="{\"Reason\":\""+"Existing problem"+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sCaseObjectID);

		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		Thread.sleep(1000);
	
		//Data Sync for WO's created
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep); 
		//driver.activateApp(GenericLib.sAppBundleID);
		
		//Navigation to Case edit process
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		Thread.sleep(10000);

		//Navigation to SFM
		Thread.sleep(CommonUtility.iMedSleep);
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		Thread.sleep(10000);
		commonUtility.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		commonUtility.longPress(exploreSearchPo.getEleExploreChildSearchTxt(sExploreChildSearchTxt));
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(CommonUtility.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sCaseID);
		commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(CommonUtility.iMedSleep);
		commonUtility.tap(exploreSearchPo.getEleWorkOrderIDTxt(sCaseID),10,10);
		
		commonUtility.tap(workOrderPo.getEleActionsLnk());	
		commonUtility.getSearch(workOrderPo.getEleActionsTxt(sFieldServiceName));
		commonUtility.tap(workOrderPo.getEleActionsTxt(sFieldServiceName),20,20);
		Thread.sleep(CommonUtility.iMedSleep);
		//Validation that case reason is not updated
		try{workOrderPo.getEleCaseReasonLst().click();}catch(Exception e) {commonUtility.tap(workOrderPo.getEleCaseReasonLst());}
		commonUtility.switchContext("Native");
		
		try {
			Assert.assertTrue(commonUtility.getElePickerWheelPopUp().getText().equals("--None--"), " Case reason is updated before refresh from salesforce");
			ExtentManager.logger.log(Status.PASS,"Case reason is not updated, Needs Refresh from Saleforce ");
			Thread.sleep(CommonUtility.iMedSleep);
			commonUtility.getEleDonePickerWheelBtn().click();
		}
		catch(Exception e) {
			Assert.assertTrue(commonUtility.getElePicklistValue("--None--").getText().equals("--None--"), " Case reason is updated before refresh from salesforce");
			ExtentManager.logger.log(Status.PASS,"Case reason is not updated, Needs Refresh from Saleforce ");
			commonUtility.getElePicklistValue("--None--").click();
		}
		Thread.sleep(CommonUtility.iMedSleep);
		commonUtility.switchContext("Webview");
		commonUtility.tap(workOrderPo.getEleSaveLnk());
		Thread.sleep(CommonUtility.iMedSleep);
		
		//Selecting Case reason to Existing Problem to make sure sfm is working fine.
		commonUtility.tap(workOrderPo.getEleActionsLnk());	
		commonUtility.getSearch(workOrderPo.getEleActionsTxt("Refresh from Salesforce"));
		commonUtility.tap(workOrderPo.getEleActionsTxt("Refresh from Salesforce"),20,20);
		Thread.sleep(CommonUtility.iMedSleep);
		
		commonUtility.tap(workOrderPo.getEleActionsLnk());	
		commonUtility.getSearch(workOrderPo.getEleActionsTxt(sFieldServiceName));
		commonUtility.tap(workOrderPo.getEleActionsTxt(sFieldServiceName),20,20);
		Thread.sleep(CommonUtility.iMedSleep);
		
		//Validation of Case reason is not updated.
		try{workOrderPo.getEleCaseReasonLst().click();}catch(Exception e) {commonUtility.tap(workOrderPo.getEleCaseReasonLst());}
		commonUtility.switchContext("Native");
		Thread.sleep(CommonUtility.iMedSleep);
		try {
			Assert.assertTrue(commonUtility.getElePickerWheelPopUp().getText().equals("Existing problem"), " Case reason is updated before refresh from salesforce");
			ExtentManager.logger.log(Status.PASS,"Case reason is not updated, Needs Refresh from Saleforce ");
			Thread.sleep(CommonUtility.iMedSleep);
			commonUtility.getEleDonePickerWheelBtn().click();
			
		}
		catch(Exception e) {
			Assert.assertTrue(commonUtility.getElePicklistValue("Existing problem").getText().equals("Existing problem"), " Case reason is updated before refresh from salesforce");
			ExtentManager.logger.log(Status.PASS,"Case reason is not updated, Needs Refresh from Saleforce ");
			commonUtility.getElePicklistValue("Existing problem").click();
		}
		
		Thread.sleep(CommonUtility.iMedSleep);
		commonUtility.switchContext("Webview");
		
		commonUtility.setPickerWheelValue(workOrderPo.getEleCaseReasonLst(), "Complex functionality");
		commonUtility.tap(workOrderPo.getEleSaveLnk());

		//Validation of qualifying workorder with Issue found text error.
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Saved successfully is not displayed");
		ExtentManager.logger.log(Status.PASS,"Saved successfully text is displayed successfully");
		
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		
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
				postCleanup();toolsPo.syncData(commonUtility);
				Thread.sleep(CommonUtility.iMedSleep);
				if(bExecutionFlag) {
				ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestID + "Testcase execution failed");}

			}catch(Exception e) {
				postCleanup();toolsPo.syncData(commonUtility);
				Thread.sleep(CommonUtility.iMedSleep);
				if(bExecutionFlag) {
				ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestID + "Testcase execution failed");}
			}
			toolsPo.syncData(commonUtility);
			Thread.sleep(CommonUtility.iMedSleep);
		}
	}
	
	private void postCleanup() throws Exception { 
		commonUtility.executeSahiScript("appium/SCN_Explore_RS_10543_postcleanup.sah", sTestID);
		
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
	
	}
}
