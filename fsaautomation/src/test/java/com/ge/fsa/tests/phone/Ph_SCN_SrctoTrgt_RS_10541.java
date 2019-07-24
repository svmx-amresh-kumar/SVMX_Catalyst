/*
 *  @author Vinod Tharavath,lakshmibs tablet Script.
 */
package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_SrctoTrgt_RS_10541 extends BaseLib {

	int iWhileCnt = 0;
	String sTestID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sIBRecord = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sIBName1 = null;
	String sIBName2 = null;
	String sFieldServiceName = null;
	String sObjectAccID = null;
	String sSerialNumber = null;
	String sSqlQuery = null;
	String sAccountName = null;
	String sObjectProID = null;
	String sProductName = null;
	String sQualificationCriteriaValidation = null;

	// For SFM Process Sahi Script name
	String sScriptName = "SCN_SrctoTrgt_RS_10541_prerequisite";
	Boolean bProcessCheckResult = false;
	String sTestCaseID = null;

	private void preRequiste() throws Exception {
		sTestCaseID = "SCN_SrctoTrgt_RS_10541";

		restServices.getAccessToken();

		
		//Create a Account
	sSerialNumber = commonUtility.generateRandomNumber("IB_10541_");

		// Create a Account
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \"" + sSerialNumber + "" + "account\"}";
		sObjectAccID = restServices.restCreate(sObjectApi, sJsonData);
		sSqlQuery = "SELECT+name+from+Account+Where+id+=\'" + sObjectAccID + "\'";
		sAccountName = restServices.restGetSoqlValue(sSqlQuery, "Name");

		// Create product
		sJsonData = "{\"Name\": \"" + sSerialNumber + "" + "product\", \"IsActive\": \"true\"}";
		sObjectApi = "Product2?";
		sObjectProID = restServices.restCreate(sObjectApi, sJsonData);
		sSqlQuery = "SELECT+name+from+Product2+Where+id+=\'" + sObjectProID + "\'";
		sProductName = restServices.restGetSoqlValue(sSqlQuery, "Name");

		// Creation of dynamic IB1
		sJsonData = "{\"SVMXC__Company__c\": \"" + sObjectAccID + "\", \"Name\": \"" + sSerialNumber
				+ "\", \"SVMXC__Serial_Lot_Number__c\": \"" + sSerialNumber + "\", \"SVMXC__Product__c\": \""
				+ sObjectProID + "\", \"SVMXC__Country__c\": \"United States\", \"SVMXC__City__c\": \"Liver Pool\"}";
		sObjectApi = "SVMXC__Installed_Product__c?";
		sIBRecord = restServices.restCreate(sObjectApi, sJsonData);
		sSqlQuery = "SELECT+name+from+SVMXC__Installed_Product__c+Where+id+=\'" + sIBRecord + "\'";
		sIBName1 = restServices.restGetSoqlValue(sSqlQuery, "Name");
		System.out.println(sIBName1);

		//Creation of dynamic IB2
		sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\", \"Name\": \""+commonUtility.generateRandomNumber("IB_10541_")+"\", \"SVMXC__Serial_Lot_Number__c\": \""+commonUtility.generateRandomNumber("IB_10541_")+"\", \"SVMXC__Product__c\": \""+sObjectProID+"\", \"SVMXC__Country__c\": \"Italy\", \"SVMXC__City__c\": \"Vatican\"}";
		sObjectApi = "SVMXC__Installed_Product__c?";
		sIBRecord = restServices.restCreate(sObjectApi, sJsonData);
		sSqlQuery = "SELECT+name+from+SVMXC__Installed_Product__c+Where+id+=\'" + sIBRecord + "\'";
		sIBName2 = restServices.restGetSoqlValue(sSqlQuery, "Name");
		System.out.println(sIBName2);

		bProcessCheckResult = commonUtility.ProcessCheck(restServices, sFieldServiceName, sScriptName, sTestCaseID);

	}

	@Test(retryAnalyzer = Retry.class)
	public void RS_10541Test() throws Exception {
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6463");
		}else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6771");

		}
			
		sTestID = "RS_10541";

		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sTestID, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sTestID, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sTestID, "ProcessName");

		preRequiste();
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		// Config Sync
		ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);

		// toolsPo.configSync(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);

		// Data Sync
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);

		// navigating to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt,
				sIBName1, sFieldServiceName);
		ExtentManager.logger.log(Status.INFO, "IB dynamically created and used is :" + sIBName1 + "");

		// Validation of not qualifying Work Order
		sQualificationCriteriaValidation = ph_WorkOrderPo.geteleCountryShouldbeItaly().getText();
		Assert.assertTrue(sQualificationCriteriaValidation.equals("Country should be Italy"),
				"Qualification Criteria validation failed");
		ExtentManager.logger.log(Status.PASS,
				"Qualification Criteria message Country should be Italy is displayed successfully");

		ph_WorkOrderPo.getEleBackButton().click();
		Thread.sleep(CommonUtility.iLowSleep);

		// Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt,
				sIBName2, sFieldServiceName);
		ExtentManager.logger.log(Status.INFO, "IB dynamically created and used is :" + sIBName2 + "");

		Thread.sleep(CommonUtility.iLowSleep);

		ph_WorkOrderPo.getEleSaveLnk().click();
		Thread.sleep(CommonUtility.iLowSleep);

		// Data Sync before triggering the API
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);

		// Validation of created Case object from IB
		sSqlQuery = "SELECT+CaseNumber+from+Case+WHERE+Subject=\'" + sIBName2 + "\'";
		Assert.assertTrue(restServices.restGetSoqlValue(sSqlQuery, "CaseNumber") != null, "IB to Case is not created");
		ExtentManager.logger.log(Status.PASS, "IB to Case is successfully created and validated in server");

	}
}
