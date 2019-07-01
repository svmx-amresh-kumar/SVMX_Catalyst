/*
 *  @author Vinod Tharavath
 *  To Validate Scheduled  Sync Due. Validation include - 
 *  first set scheduled data sync value to 5
 *  then validate if scheduled data sync triggers and if the worked out created from api is synced to FSA
 *  then validate changes made in FSA is scheduled synced to server 
 *  then validate if setting is changed back to 10000
 *   */
package com.ge.fsa.tests.phone;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.phone.Ph_MorePO;

public class Ph_SCN_ScheduledDataSync_RS_10569 extends BaseLib {
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

	public void PreRequisites() throws Exception {
		sTestCaseID = "SCN_ScheduledDataSync_RS_10569";
		sCaseWOID = "DATA_SCN_ScheduledDataSync_RS_10569";
		sSheetName = "RS_10569";
		// Reading from the Excel sheet
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreSearch");
		System.out.println(sExploreSearch);

		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ProcessName");
		sEditProcessName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "EditProcessName");
		// sWOName = "WO-00002005";
		// running the Sahi Script Pre-requisites - to set scheduled data sync
		commonUtility.executeSahiScript("appium/Scenario_RS_10569_ScheduledDataSync_Pre.sah", sTestCaseID);
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS, "Testcase " + sTestCaseID + "Sahi verification is successful");
	}

	public void postscript() throws Exception {
		// running the Sahi Script Post check - to reset scheduled data sync back to
		// 1000
		commonUtility.executeSahiScript("appium/Scenario_RS_10569_ScheduledDataSync_Post.sah", sTestCaseID);
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS, "Testcase " + sTestCaseID + "Sahi verification is successful");
		// lauchNewApp("true");
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
	}

	@Test(retryAnalyzer = Retry.class)
	public void RS_10569() throws Exception {

		commonUtility.preReqSetup(genericLib);
		// Resinstall the app
		// lauchNewApp("false");

		PreRequisites();

		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		Thread.sleep(CommonUtility.iMedSleep);

		// Perform Config Sync
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		ph_WorkOrderPo.getEleBackButton().click();
		Thread.sleep(CommonUtility.iMedSleep);

		// Create work Order
		restServices.getAccessToken();
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		sWOName = restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);

		// Wait for 5-8 minutes as scheduled data sync will need to start trigger
		ph_MorePo.getEleDataSync().click();
		commonUtility.waitforElement(ph_MorePo.getEleSyncing(), 500);
		commonUtility.waitForElementNotVisible(ph_MorePo.getEleSyncing(), 300);
		ExtentManager.logger.pass("After Data Sync",
				MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		
		if (commonUtility.isDisplayedCust(ph_MorePo.getEleDataSynccompleted())) {
			System.out.println("Data Sync Completed Sucessfully");
			ExtentManager.logger.log(Status.PASS, "Scheduled Data Sync is successfull");
		} else {
			System.out.println("Data Sync Failed");
			// Verification of successful sync
			ExtentManager.logger.log(Status.FAIL, "Scheduled Data Sync Failed");
		}

		// Navigation to WO
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName,
				sFieldServiceName);
		ExtentManager.logger.log(Status.PASS,
				"Work Order Created before scheduled sync is synced from server to ServiceMax Go");

		// billing type
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_CreateNewPo.getElebillingtype(), sBillingType);
		ph_WorkOrderPo.getEleSaveLnk().click();

		// waiting for 5 bare minimum as we need to see capture the refreshing view for
		// scheduled Data Sync.
		// Wait for 5-8 minutes as scheduled data sync will need to start trigger
		ph_MorePo.getEleDataSync().click();
		commonUtility.waitforElement(ph_MorePo.getEleSyncing(), 500);
		commonUtility.waitForElementNotVisible(ph_MorePo.getEleSyncing(), 300);
		ExtentManager.logger.pass("After Data Sync",
				MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		if (commonUtility.isDisplayedCust(ph_MorePo.getEleDataSynccompleted())) {
			System.out.println("Data Sync Completed Sucessfully");
			ExtentManager.logger.log(Status.PASS, "Scheduled Data Sync is successfull");
		} else {
			System.out.println("Data Sync Failed");
			// Verification of successful sync
			ExtentManager.logger.log(Status.FAIL, "Scheduled Data Sync Failed");
		}
		
		
		//Validating server side
		sSoqlqueryWO = "Select+SVMXC__Billing_Type__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName + "'";
		sBillTypeServer = restServices.restGetSoqlValue(sSoqlqueryWO, "SVMXC__Billing_Type__c");
		Assert.assertTrue(sBillTypeServer.equals(sBillingType),
				"Billing Type in Server has not been updated after scheduled data sync");
		ExtentManager.logger.log(Status.PASS,
				"Billing Type edited from ServiceMaxGo has been updated in server after scheduled Data Sync");
		// postscript();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		postscript();
		System.out.println("Running the post script");
		ExtentManager.logger.log(Status.INFO, "Post script run sucessfully at aftermethod");

	}

}
