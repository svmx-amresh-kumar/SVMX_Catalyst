package com.ge.fsa.tests.phone;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
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

	@BeforeMethod
	public void initializeObject() throws IOException {

	}

	@Test(retryAnalyzer = Retry.class)
	public void Recent_Items_RS_10565() throws Exception {
		sSheetName = "RS_10565";
		sDeviceDate = driver.getDeviceTime().split(" ");

		String sProformainVoice = commonUtility.generaterandomnumber("AUTO");
		String sTestCaseID = "RS_10565_Recent_Items";

		// sahi
		/*
		 * genericLib.executeSahiScript("appium/SCN_RecentItems_RS_10565.sah",
		 * "sTestCaseID"); if(commonsUtility.verifySahiExecution()) {
		 * 
		 * System.out.println("PASSED"); } else { System.out.println("FAILED");
		 * 
		 * 
		 * ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID +
		 * "Sahi verification failure"); assertEquals(0, 1); } lauchNewApp("true");
		 * System.out.println("RS_10565");
		 */

		// read from file
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, sSheetName, "ViewProcessNameCustom");
		String sFieldServiceName2 = GenericLib.getExcelData(sTestCaseID, sSheetName, "CreateNewCustomrecord");
		// String WOname1=GenericLib.getExcelData(sTestCaseID, "WorkOrder");

		String sRandomNumber = commonUtility.generaterandomnumber("");
		sProformainVoice = sRandomNumber;

		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		Thread.sleep(GenericLib.iMedSleep);
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
		// create one case
		sJsonData = "{\"Origin\": \"phone\", \"Subject\": \"Recent_Item\", \"Priority\": \"High\", \"Description\": \"Description of Recent_item \",\"Status\": \"Escalated\"}";
		sObjectApi = "Case?";
		String sObjectID = restServices.restCreate(sObjectApi, sJsonData);
		sSqlQuery = "SELECT+CaseNumber+from+Case+Where+id+=\'" + sObjectID + "\'";
		String sCaseID = restServices.restGetSoqlValue(sSqlQuery, "CaseNumber");

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
		ExtentManager.logger.log(Status.PASS, "Workorder valaditation in recent item is successful");
		// open case
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, "Cases", sCaseID, null);

		Thread.sleep(2000);
		ph_RecentsItemsPo.getEleClickRecentItems().click();
		Thread.sleep(1000);
		// commonsUtility.tap(recenItemsPO.gettaponobject("Case ("));
		String fetchedcasefromrecents = ph_RecentsItemsPo.getEleWorkOrderRecentUsed().getText();
		System.out.println(fetchedcasefromrecents);
		Assert.assertTrue(sCaseID.equals(fetchedcasefromrecents), "case value  is not displayed");
		ExtentManager.logger.log(Status.PASS, " case valaditation in recent item is successful");

		
		// create new custom record
		ph_CreateNewPo.getEleCreateNew().click();
		commonUtility.custScrollToElementAndClick(ph_CreateNewPo.getEleCreateNewCustomObject());
		ph_WorkOrderPo.getAutoTextBox().sendKeys(sProformainVoice);
		ph_WorkOrderPo.getEleAdd().click();
		Thread.sleep(3000);

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
		ExtentManager.logger.log(Status.PASS, " Custom object valaditation in recent item is successful");

		ph_MorePo.resetApp(commonUtility);
		Thread.sleep(2000);

		ph_RecentsItemsPo.getEleClickRecentItems().click();
		try {
			ph_RecentsItemsPo.getEleEmptyRecentItems().isDisplayed();
		} catch (Exception e) {
			ExtentManager.logger.log(Status.FAIL,
					" Items are getting displayed in Recents tab even after erase and reinitializing app data");
		}

		ExtentManager.logger.log(Status.PASS, "Recent Items validation is successful");

	}

}
