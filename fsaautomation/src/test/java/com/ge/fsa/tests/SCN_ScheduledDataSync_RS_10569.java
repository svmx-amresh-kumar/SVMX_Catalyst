/*
 *  @author Vinod Tharavath
 *  To Validate Scheduled  Sync Due. Validation include - 
 *  first set scheduled data sync value to 5
 *  then validate if scheduled data sync triggers and if the worked out created from api is synced to FSA
 *  then validate changes made in FSA is scheduled synced to server 
 *  then validate if setting is changed back to 10000
 *   */
package com.ge.fsa.tests;

import static org.testng.Assert.assertEquals;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

public class SCN_ScheduledDataSync_RS_10569 extends BaseLib {
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
	String sSheetName =null;
	String sBillingType = "Loan";
	String sSoqlqueryWO = null;
	String sBillTypeServer = null;
	
	@Test(enabled = true)
	public void RS_10569() throws Exception {
		
		sTestCaseID = "SCN_ScheduledDataSync_RS_10569";
		sCaseWOID = "DATA_SCN_ScheduledDataSync_RS_10569";	

		//Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID,sSheetName, "EditProcessName");
		
		
		
		sSheetName ="RS_10569";
		System.out.println("RS 10569 Scheduled Data Sync");
		sTestCaseID = "";
		//VT add order type s= field service, order status = open-- this would enable to open Record T&M
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		String sWOName= restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);
		
		//sWOName = "WO-00002005";
		// running the Sahi Script Pre-requisites - to set scheduled data sync
		genericLib.executeSahiScript("appium/Scenario_RS_10569_ScheduledDataSync_Pre.sah", "sTestCaseID");
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Sahi verification is successful");
	
		// Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
		Thread.sleep(GenericLib.iMedSleep);
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		//need to wait for a minimum of 5 minutes for the pop up to show up
	//	Thread.sleep(300000);
	//	Thread.sleep(GenericLib.iVHighSleep);
		// Wait for 5 minutes as scheduled data sync will trigger
		commonsPo.waitforElement(toolsPo.getEleRefreshingViewTxt(),500000);
		//Verification of successful sync
		Assert.assertTrue(toolsPo.getEleSuccessTxt().isDisplayed(), "Data sync is not successfull");
		ExtentManager.logger.log(Status.PASS,"Scheduled Data Sync is successfull");
		Thread.sleep(GenericLib.iHighSleep);
	    workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
		ExtentManager.logger.log(Status.PASS,"Work Order Created before scheduled sync is synced from server to FSA");
	    workOrderPo.getEleBillingTypeLst().sendKeys(sBillingType);
	    workOrderPo.getEleSaveLnk();
	    commonsPo.tap(toolsPo.getEleToolsIcn());	
		Assert.assertTrue(toolsPo.getEleSyncDataNowLnk().isDisplayed(), "Tools screen is not displayed");
		ExtentManager.logger.log(Status.PASS,"Tools screen is displayed successfully");	    
	//waiting for 5 bare minimum as we need to see capture the refreshing view.
		commonsPo.waitforElement(toolsPo.getEleRefreshingViewTxt(),400000);
		Assert.assertTrue(toolsPo.getEleSuccessTxt().isDisplayed(), "Data sync is not successfull");
		restServices.getAccessToken();
		sSoqlqueryWO = "Select+SVMXC__Billing_Type__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"'"; 
		sBillTypeServer = restServices.restGetSoqlValue(sSoqlqueryWO,"SVMXC__Billing_Type__c");
        Assert.assertTrue(sBillTypeServer.equals(sBillingType), "Billing Type in Server has not been updated after scheduled data sync");
		ExtentManager.logger.log(Status.PASS,"Billing Type edited from FSA has been updated in server after scheduled Data Sync");	    
		// running the Sahi Script Pre-requisites - to reset scheduled data sync back to 600
		genericLib.executeSahiScript("appium/Scenario_RS_10569_ScheduledDataSync_Post.sah", "sTestCaseID");
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Sahi verification is successful");
		lauchNewApp("true");
		toolsPo.configSync(commonsPo);
		
	}

}
