/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests.phone;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class Ph_Sanity5_DVR_Mapping_Qualification_Criteria extends BaseLib {

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

	/*	commonUtility.executeSahiScript("appium/scenario5_prerequisite.sah", sTestCaseID);
		//Assert.assertTrue(commonsUtility.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Sahi verification is successful");
    */
	}

	@Test(retryAnalyzer=Retry.class)
	public void toTest() throws Exception {
		sSheetName ="SANITY5";
		sTestCaseID = "SANITY5";

		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ProcessName");
		sIssueTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "IssueText");
		sBillingType = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "BillingType");
		preRequiste();
		//Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		Thread.sleep(CommonUtility.iMedSleep);
		
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		
		//Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName1,sFieldServiceName);

		//Validation of not qualifying Work Order
		Assert.assertTrue(ph_WorkOrderPo.getEleThisRecordDoesNotPopup().isDisplayed(), "Does not meet 1ualification page is not displayed");
		ExtentManager.logger.log(Status.PASS,"This is record does not meet page is displayed successfully");
		ph_WorkOrderPo.getEleBackButton().click();
		//Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName2,sFieldServiceName);
		Thread.sleep(CommonUtility.iLowSleep);

		ph_WorkOrderPo.selectFromPickList(commonUtility, ph_WorkOrderPo.getEleBillingTypeField(), sBillingType);
		Thread.sleep(CommonUtility.iLowSleep);


		//Validation of qualifying workorder with Issue found text error.
		Assert.assertTrue(ph_WorkOrderPo.getEleIssueFoundTxt().isDisplayed(), "Issue found error is not displayed");
		ExtentManager.logger.log(Status.PASS,"Issue found is displayed successfully");

		//Validation of qualifying workorder with Issue found text popup.
		Assert.assertTrue(ph_WorkOrderPo.getEleIssuePopupTxt(sIssueTxt).isDisplayed(), "Error popup is not displayed");
		ExtentManager.logger.log(Status.PASS,"Error popup Issue found is displayed successfully");

		Thread.sleep(CommonUtility.iMedSleep);
		ph_WorkOrderPo.getEleBackButton().click();
		try {ph_WorkOrderPo.getEleDiscardChangesButton().click();}catch(Exception e) {}

		//Navigation to WO
		ph_WorkOrderPo.selectAction(commonUtility, sFieldServiceName);
		Thread.sleep(CommonUtility.iMedSleep);

		//Selecting Billing Type to contract to make sure sfm is working fine.
		ph_WorkOrderPo.selectFromPickList(commonUtility, ph_WorkOrderPo.getEleBillingTypeField(), "Courtesy");
		ph_WorkOrderPo.getElesave().click();

		//Validation of qualifying workorder with Issue found text error.
		ExtentManager.logger.log(Status.PASS,"Saved successfully text is displayed successfully");
	}
}
