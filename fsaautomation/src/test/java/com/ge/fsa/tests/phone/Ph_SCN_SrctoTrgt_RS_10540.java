/*
 *  @author Vinod tharavath, Lakshmi Tablet script.
 */
package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_SrctoTrgt_RS_10540 extends BaseLib {

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
	String sScriptName = "SCN_SrctoTrgt_RS_10540_prerequisite";
	Boolean bProcessCheckResult = false;
	String sTestCaseID = null;

	private void preRequiste() throws Exception {

		sTestCaseID = "SCN_SrctoTrgt_RS_10540";
		restServices.getAccessToken();
		sSerialNumber = commonUtility.generateRandomNumber("IB_10540_");
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
		sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\", \"Name\": \""+commonUtility.generateRandomNumber("IB_10540_")+"\", \"SVMXC__Serial_Lot_Number__c\": \""+commonUtility.generateRandomNumber("IB_10540_")+"\", \"SVMXC__Product__c\": \""+sObjectProID+"\", \"SVMXC__Country__c\": \"Italy\", \"SVMXC__City__c\": \"Vatican\"}";
		sObjectApi = "SVMXC__Installed_Product__c?";
		sIBRecord = restServices.restCreate(sObjectApi, sJsonData);
		sSqlQuery = "SELECT+name+from+SVMXC__Installed_Product__c+Where+id+=\'" + sIBRecord + "\'";
		sIBName2 = restServices.restGetSoqlValue(sSqlQuery, "Name");
		System.out.println(sIBName2);

		// sIBName1 ="IB_10540_27092018155351";
		// sIBName2 = "IB_10540_27092018155358";

		/*
		 * commonUtility.executeSahiScript(
		 * "appium/SCN_SrctoTrgt_RS_10540_prerequisite.sah", sTestID);
		 * Assert.assertTrue(commonUtility.verifySahiExecution(),
		 * "Failed to execute Sahi script");
		 * ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID +
		 * "Sahi verification is successful");
		 */
		bProcessCheckResult = commonUtility.ProcessCheck(restServices, sFieldServiceName, sScriptName, sTestCaseID);

	}

	@Test(enabled = true, retryAnalyzer = Retry.class)
	public void RS_10540Test() throws Exception {

		// commonUtility.preReqSetup(genericLib);
		// Resinstall the app
		lauchNewApp("false");

		sTestID = "RS_10540";
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sTestID, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sTestID, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sTestID, "ProcessName");
		preRequiste();

		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
		Thread.sleep(CommonUtility.iMedSleep);

		ph_MorePo.getEleDataSync();

		// navigating to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt,
				sIBName1, sFieldServiceName);

		// Validation of not qualifying Work Order
		sQualificationCriteriaValidation = ph_WorkOrderPo.geteleCountryShouldbeItaly().getText();
		Assert.assertTrue(sQualificationCriteriaValidation.equals("Country should be Italy"),
				"Qualification Criteria validation failed");
		ExtentManager.logger.log(Status.PASS,
				"Qualification Criteria message Country should be Italy is displayed successfully");

		ph_WorkOrderPo.getEleBackButton().click();

		// Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt,
				sIBName2, sFieldServiceName);
		Thread.sleep(CommonUtility.iLowSleep);
		ExtentManager.logger.log(Status.INFO, "IB dynamically created and used is :" + sIBName1 + "");

		ph_WorkOrderPo.getEleSaveLnk().click();
		Thread.sleep(CommonUtility.iLowSleep);

		// Data Sync before triggering the API
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);

		// Validation of created custom object from IB
		sSqlQuery = "SELECT+Name+from+Auto_Custom_Object10540__c+Where+Name=\'" + sIBName2 + "\'";
		Assert.assertTrue(restServices.restGetSoqlValue(sSqlQuery, "Name").equals(sIBName2),
				"IB to custom object is not created");
		ExtentManager.logger.log(Status.PASS, "IB to Custome object is successfully created. validated through API");

	}
}
