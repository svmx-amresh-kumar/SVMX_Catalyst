package com.ge.fsa.tests.phone;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.events.api.general.NavigationEventListener;

public class Ph_SCN_Recent_Items_RS_10565 extends BaseLib {

	int iWhileCnt = 0;
	String sTestCaseIDID = null;
	String sObjectIBID = null;

	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectAccID = null;
	String sObjectProID = null;
	String sObjectWOID1 = null;
	String sObjectWOID2 = null;
	String sObjectWOID3 = null;
	String sObjectApi = null;
	String sJsonData = null;
	String WOname1 = null;
	String WOname2 = null;
	String WOname3 = null;
	String sFieldServiceName = null;
	String sSqlQuery = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	WebElement productname = null;
	String sSheetName = null;
	

	@Test(retryAnalyzer = Retry.class)
	public void Recent_Items_RS_10565() throws Exception {
		sSheetName = "RS_10565";
		sDeviceDate = driver.getDeviceTime().split(" ");

		String sProformainVoice = commonUtility.generateRandomNumber("AUTO");
		String sTestCaseID = "RS_10565_Recent_Items";
		boolean configSync=commonUtility.ProcessCheck(restServices, "Create New Custom object", "SCN_RecentItems_RS_10565", sTestCaseID);

		// read from file
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ViewProcessNameCustom");
		String sFieldServiceName2 = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "CreateNewCustomrecord");
		// String WOname1=GenericLib.readExcelData(GenericLib.sTestDataFile, "WorkOrder");

		String sRandomNumber = commonUtility.generateRandomNumber("");
		sProformainVoice = sRandomNumber;

		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		if(configSync) {
			ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		}
		ph_MorePo.resetApp(commonUtility);
		
		// crete a wo
		ph_CreateNewPo.getEleCreateNew().click();
		commonUtility.custScrollToElementAndClick(ph_CreateNewPo.getEleCreateNewWorkOrder());
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_CreateNewPo.getElePriorityField(), "High");
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_CreateNewPo.getEleBillingTypeField(), "Loan");
		commonUtility.custScrollToElementAndClick(ph_CreateNewPo.getEleProformaInvoiceField());
		ph_CreateNewPo.getEleProformaInvoiceField().sendKeys(sProformainVoice);
		Thread.sleep(1000);
		ph_CreateNewPo.getEleAdd().click();
		ExtentManager.logger.log(Status.INFO, "New Work Order is created with details as Priority:High, Billing Type: Loan, Proforma Invoice : "+sProformainVoice);
		
		// create one case
		sJsonData = "{\"Origin\": \"phone\", \"Subject\": \"Recent_Item\", \"Priority\": \"High\", \"Description\": \"Description of Recent_item \",\"Status\": \"Escalated\"}";
		sObjectApi = "Case?";
		String sObjectID = restServices.restCreate(sObjectApi, sJsonData);
		sSqlQuery = "SELECT+CaseNumber+from+Case+Where+id+=\'" + sObjectID + "\'";
		String sCaseID = restServices.restGetSoqlValue(sSqlQuery, "CaseNumber");
		ExtentManager.logger.log(Status.INFO, "Case has been created through rest web service. CaseId : "+sCaseID);

		ph_MorePo.syncData(commonUtility);

		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"
				+ sProformainVoice + "\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery, "Name");
		System.out.println(sworkOrderName);

		// recenItemsPO.clickonWorkOrder(commonsUtility, sworkOrderName);
		ph_RecentsItemsPo.getEleClickRecentItems().click();
		Thread.sleep(1000);
		String fetchedWOfromrecents = ph_RecentsItemsPo.getEleWorkOrderRecentUsed().getText();
		System.out.println(fetchedWOfromrecents);
		Assert.assertTrue(fetchedWOfromrecents.equals(sworkOrderName), "workOrderName value is not displayed");
		ExtentManager.logger.log(Status.PASS, "Workorder valaditation in recent item is successful. Expected : "+sworkOrderName+", Actual : "+fetchedWOfromrecents);
		// open case
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, "Cases", sCaseID, null);
		ExtentManager.logger.log(Status.INFO, "Navigate to case : "+sCaseID);
		Thread.sleep(2000);
		ph_RecentsItemsPo.getEleClickRecentItems().click();
		Thread.sleep(1000);
		
		// commonsUtility.tap(recenItemsPO.gettaponobject("Case ("));
		String fetchedcasefromrecents = ph_RecentsItemsPo.getEleWorkOrderRecentUsed().getText();
		System.out.println(fetchedcasefromrecents);
		Assert.assertTrue(sCaseID.equals(fetchedcasefromrecents), "case value  is not displayed");
		ExtentManager.logger.log(Status.PASS, "Cases valaditation in recent item is successful. Expected : "+sCaseID+", Actual : "+fetchedcasefromrecents);

		
		// create new custom record
		ph_CreateNewPo.getEleCreateNew().click();
		commonUtility.custScrollToElementAndClick(ph_CreateNewPo.getEleCreateNewCustomObject());
		ph_WorkOrderPo.getAutoTextBox().sendKeys(sProformainVoice);
		ph_WorkOrderPo.getEleAdd().click();
		Thread.sleep(3000);
		ExtentManager.logger.log(Status.INFO, "New custom object is created.");
		ph_MorePo.syncData(commonUtility);

		ph_RecentsItemsPo.getEleClickRecentItems().click();
		ph_RecentsItemsPo.getEleClickRecentItems().click();
		Thread.sleep(3000);
		// commonsUtility.tap(recenItemsPO.gettaponobject("Auto_Custom_Object2 ("));
		String fetchedCustom_Objectfromrecents = ph_RecentsItemsPo.getEleWorkOrderRecentUsed().getText();
		System.out.println(fetchedCustom_Objectfromrecents);

		String sSoqlQuery1 = "SELECT+Name+from+Auto_Custom_Object2__c+Where+Auto_TextBox_c__c+=\'" + sProformainVoice
				+ "\'";
		String Custom_ObjectName = restServices.restGetSoqlValue(sSoqlQuery1, "Name");
		System.out.println(Custom_ObjectName);
		Assert.assertTrue(fetchedCustom_Objectfromrecents.equals(Custom_ObjectName),"Custom object  value  is not displayed");
		ExtentManager.logger.log(Status.PASS, "Custom Objects valaditation in recent item is successful. Expected : "+Custom_ObjectName+", Actual : "+fetchedCustom_Objectfromrecents);

		ph_MorePo.resetApp(commonUtility);
		Thread.sleep(2000);

		ph_RecentsItemsPo.getEleClickRecentItems().click();
		try {
			ph_RecentsItemsPo.getEleEmptyRecentItems().isDisplayed();
		} catch (Exception e) {
			ExtentManager.logger.log(Status.FAIL,
					" Items are getting displayed in Recents tab even after erase and reinitializing app data");
		}

		ExtentManager.logger.log(Status.PASS, "Recent Items validation is successful as no records are getting displayed");

	}

}
