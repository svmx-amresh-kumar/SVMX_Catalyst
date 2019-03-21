/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests.tablet;

import org.testng.annotations.Test;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Sanity5_DVR_Mapping_Qualification_Criteria extends BaseLib {

	int iWhileCnt = 0;
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sWorkOrderID = null;
	String sWOObejctApi = null;
	String sWOJsonData = null;
	String sWOName1 = null;
	String sWOName2 = null;
	String sFieldServiceName = null;
	String sProductName1 = null;
	String sProductName2 = null;
	String sActivityType = null;
	String sPrintReportSearch = null;
	String sIssueTxt = null;
	String sBillingType = null;
	String sWOSqlQuery = null;
	String sSheetName =null;

	private void preRequiste() throws Exception { 


		restServices.getAccessToken();
		sWOObejctApi="SVMXC__Service_Order__c?";

		//Creation of dynamic Work Order1
		sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Canceled\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sWorkOrderID=restServices.restCreate(sWOObejctApi,sWOJsonData);
		sWOSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWorkOrderID+"\'";				
		sWOName1 =restServices.restGetSoqlValue(sWOSqlQuery,"Name"); //"WO-00000456"; 

		//Creation of dynamic Work Order2
		sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sWorkOrderID=restServices.restCreate(sWOObejctApi,sWOJsonData);
		sWOSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWorkOrderID+"\'";				
		sWOName2 =restServices.restGetSoqlValue(sWOSqlQuery,"Name"); //"WO-00000455"; 

	/*	genericLib.executeSahiScript("appium/scenario5_prerequisite.sah", sTestCaseID);
		//Assert.assertTrue(commonsUtility.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Sahi verification is successful");
*/
	}

	@Test(retryAnalyzer=Retry.class)
	public void toTest() throws Exception {
		sSheetName ="SANITY5";
		sTestCaseID = "SANITY5";

		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sIssueTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "IssueText");
		sBillingType = GenericLib.getExcelData(sTestCaseID,sSheetName, "BillingType");
		preRequiste();

		
		//Pre Login to app
		loginHomePo.login(commonsUtility, exploreSearchPo);

		toolsPo.configSync(commonsUtility);
		Thread.sleep(GenericLib.iMedSleep);

		toolsPo.syncData(commonsUtility);
		Thread.sleep(GenericLib.iMedSleep);

		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName1, sFieldServiceName);

		//Validation of not qualifying Work Order
		Assert.assertTrue(workOrderPo.getEleThisRecordDoesNotPopup().isDisplayed(), "Error popup is not displayed");
		ExtentManager.logger.log(Status.PASS,"Error popup This is record does not meet is displayed successfully");
		commonsUtility.tap(workOrderPo.getEleOKBtn());
		Thread.sleep(GenericLib.iLowSleep);


		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName2, sFieldServiceName);
		Thread.sleep(GenericLib.iLowSleep);

		commonsUtility.setPickerWheelValue(workOrderPo.getEleBillingTypeLst(), sBillingType);
		Thread.sleep(GenericLib.iLowSleep);


		commonsUtility.tap(workOrderPo.getEleClickSave());
		Thread.sleep(GenericLib.iLowSleep);

		//Validation of qualifying workorder with Issue found text error.
		Assert.assertTrue(workOrderPo.getEleIssueFoundTxt().isDisplayed(), "Issue found error is not displayed");
		ExtentManager.logger.log(Status.PASS,"Issue found is displayed successfully");

		//Validation of qualifying workorder with Issue found text popup.
		commonsUtility.tap(workOrderPo.getEleIssueFoundTxt());	
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sIssueTxt).isDisplayed(), "Error popup is not displayed");
		ExtentManager.logger.log(Status.PASS,"Error popup Issue found is displayed successfully");

		commonsUtility.tap(workOrderPo.getEleIssueFoundTxt());
		Thread.sleep(GenericLib.iMedSleep);
		commonsUtility.tap(workOrderPo.getEleCancelLink());
		commonsUtility.tap(workOrderPo.getEleDiscardBtn());

		//Navigation to WO
		workOrderPo.selectAction(commonsUtility, sFieldServiceName);
		Thread.sleep(GenericLib.iMedSleep);

		//Selecting Billing Type to contract to make sure sfm is working fine.
		commonsUtility.setPickerWheelValue(workOrderPo.getEleBillingTypeLst(), "Contract");
		commonsUtility.tap(workOrderPo.getEleSaveLnk());

		//Validation of qualifying workorder with Issue found text error.
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Saved successfully is not displayed");
		ExtentManager.logger.log(Status.PASS,"Saved successfully text is displayed successfully");
	}
}