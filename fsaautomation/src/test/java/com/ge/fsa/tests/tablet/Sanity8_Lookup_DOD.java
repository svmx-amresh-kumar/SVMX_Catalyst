/*
 *  @author MeghanaRao
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/AUT-62"
 */
package com.ge.fsa.tests.tablet;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class Sanity8_Lookup_DOD extends BaseLib {

	int iWhileCnt = 0;
	String sTestCaseID = "Scenario-8";
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = "AUTOMATION SEARCH";
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sFieldServiceName = null;
	String sProductName1 = null;
	String sProductName2 = null;
	String sActivityType = null;
	String sPrintReportSearch = null;
	String sAccountName = null;
	String woID = null;
	String sProcessname = "EditWoAutoTimesstamp";
	String sExploreChildSearchTxt = "Work Orders";
	String sObjectApi,sSqlEventQuery,sWOJson,sEventIdSFDC_2;

	@Test(retryAnalyzer = Retry.class)
	public void Scenario8Test() throws Exception {

		// JiraLink
		if (BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12039");
		} else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12043");

		}

		// running the Sahi Script Pre-requisites - To make All Records to My Records in
		// Mobile Configuration
		//commonUtility.executeSahiScript("appium/setDownloadCriteriaWoToMyRecords.sah");
		sObjectApi = "SVMXC__ServiceMax_Config_Data__c";
		sSqlEventQuery ="SELECT+Id+FROM+SVMXC__ServiceMax_Config_Data__c+WHERE+SVMXC__Object_Name__c='SVMXC__Service_Order__c'+AND+SVMXC__RecordType_Name__c='Mobile Configuration'+ORDER+BY+ID+DESC+LIMIT+1";		
		sEventIdSFDC_2 =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
		System.out.println(sEventIdSFDC_2);
		//updating event
		 sWOJson="{\"SVMXC__Ownership_Type__c\":\"My Records\"}";
		restServices.restUpdaterecord(sObjectApi,sWOJson,sEventIdSFDC_2 );

		System.out.println("Scenario 8");
		loginHomePo.login(commonUtility, exploreSearchPo);
		// Syncing after the Pre-Requisite is done
		toolsPo.syncData(commonUtility);
		// Create a Work Order to verify the Download Criteria
		sWOJsonData = "{\"SVMXC__City__c\":\"Bangalore\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		woID = restServices.restCreate("SVMXC__Service_Order__c?", sWOJsonData);
		System.out.println("WO ID FETCHED " + woID);
		// To extract the work order name from the Work Order ID
		String sSoqlQueryWoName = "Select+Name+from+SVMXC__Service_Order__c+where+Id+=\'" + woID + "\'";
		String sWorkOrderName = restServices.restGetSoqlValue(sSoqlQueryWoName, "Name");
		toolsPo.syncData(commonUtility);
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		workOrderPo.downloadCriteriaDOD(commonUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sWorkOrderName);
		// If the value "Records not Displayed" is Visible then the Work Order is
		// Online.
		if (exploreSearchPo.getEleNorecordsToDisplay().isDisplayed()) {
			commonUtility.tap(exploreSearchPo.getEleOnlineBttn());
			commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
			// If the Cloud button is Visible then need to Tap on it

			if (exploreSearchPo.getEleCloudSymbol().isDisplayed()) {
				commonUtility.tap(exploreSearchPo.getEleCloudSymbol(), 20, 20);
				commonUtility.tap(exploreSearchPo.getEleWorkOrderIDTxt(sWorkOrderName), 10, 10);

			}
			// If the cloud button is not visible then throw an Error in the Report
			else {
				// NXGReports.addStep("Testcase " + sTestCaseID + "DOD of the Work Order didn't
				// meet", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				ExtentManager.logger.log(Status.FAIL, "Testcase " + sTestCaseID + "DOD of the Work Order didn't meet");

				driver.quit();
			}
		}
		// If the value "Records not displayed" is not visible then the WO is not
		// Online.
		else {
			// NXGReports.addStep("Testcase " + sTestCaseID + "Work Order is not Online -
			// DOD not available", LogAs.FAILED, new
			// CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			ExtentManager.logger.log(Status.FAIL, "Testcase " + sTestCaseID + "DOD of the Work Order didn't meet");

			System.out.println("DOD of the Work Order didn't meet");
			driver.quit();
		}

		// Creating Product A
		String sProductAName = commonUtility.generateRandomNumber("ProdA");
		String sProductAId = restServices.restCreate("Product2?", "{\"Name\": \"" + sProductAName + "\", \"IsActive\": \"true\"}");
		String sProductNameA = restServices.restGetSoqlValue("SELECT+Name+from+Product2+Where+id+=\'" + sProductAId + "\'", "Name");
		System.out.println(sProductNameA);
		// Creating Product B
		String sProductBName = commonUtility.generateRandomNumber("ProdB");
		String sProductBId = restServices.restCreate("Product2?", "{\"Name\": \"" + sProductBName + "\", \"IsActive\": \"true\"}");
		String sProductNameB = restServices.restGetSoqlValue("SELECT+Name+from+Product2+Where+id+=\'" + sProductBId + "\'", "Name");
		System.out.println(sProductNameB);

		// Creating Installed Base A
		String sInstalledBaseAName = commonUtility.generateRandomNumber("IBA");
		String sIBIdA = restServices.restCreate("SVMXC__Installed_Product__c?", "{\"Name\": \"" + sInstalledBaseAName + "\", \"SVMXC__Product__c\": \"" + sProductAId + "\"}");
		String sInstalledProductAName = restServices.restGetSoqlValue("SELECT+Name+from+SVMXC__Installed_Product__c+Where+id+=\'" + sIBIdA + "\'", "Name");

		// Syncing the Data
		toolsPo.syncData(commonUtility);

		// Config Sync
		// toolsPo.configSync(commonsUtility);

		// Adding the values to the childlines
		String sProcessname = "Senario8_childlinesSFM";
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		try {
			workOrderPo.selectAction(commonUtility, sProcessname);

		} catch (Exception e) {
			commonUtility.tap(exploreSearchPo.getEleWorkOrderIDTxt(sWorkOrderName));
			workOrderPo.selectAction(commonUtility, sProcessname);

		}

		// Adding Product A to the Header and verifying the child values
		commonUtility.tap(workOrderPo.getEleProductLookup());
		commonUtility.lookupSearch(sProductNameA);
		// Coming to the Childlines and Verifying on the IB Serial Number
		commonUtility.tap(workOrderPo.getElePartLnk());
		commonUtility.lookupSearch(sProductNameA);
		commonUtility.tap(workOrderPo.getEleAddselectedbutton());
		// Tapping on the Parts added and checking the IB Serial Number
		commonUtility.tap(workOrderPo.getEleTaponParts(sProductNameA));
		commonUtility.tap(workOrderPo.getEleIbSerialnumTap());

		Thread.sleep(2000);
		// To verify if the Count of the Element on the Lookup is 1. If it is 1 and
		// visible then click on it.
		assertEquals(workOrderPo.getEleIBSerialNumber().size(), 1);
		// NXGReports.addStep("Testcase " + sTestCaseID + "Passed-The Installed Product
		// added in the Lookup is only 1", LogAs.PASSED, null);
		ExtentManager.logger.log(Status.PASS, "Testcase " + sTestCaseID + "Passed-The Installed Product added in the Lookup is only 1");

		if (workOrderPo.getEleIBSerialNumber().size() == 1) {
			commonUtility.tap(workOrderPo.getEleeleIBId(sInstalledProductAName));
			commonUtility.tap(workOrderPo.getEleDoneBtn());
			commonUtility.tap(workOrderPo.getEleClickSave());
			// NXGReports.addStep("Testcase " + sTestCaseID + "Passed-Clicked on the
			// Installed Product", LogAs.PASSED, null);
			ExtentManager.logger.log(Status.PASS, "Testcase " + sTestCaseID + "Passed-Clicked on the Installed Product");

		}
		// Else print with a Failure because there are more than 1 IB under the Lookup
		else {
			// NXGReports.addStep("Testcase " + sTestCaseID + "More than 1 IB is present
			// under the Lookup of IBSerial number", LogAs.FAILED, new
			// CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			ExtentManager.logger.log(Status.FAIL, "Testcase " + sTestCaseID + "More than 1 IB is present under the Lookup of IBSerial number");

		}

		// running the Sahi Script Pre-requisites - To make My Records to All Records in
		// Mobile Configuration

	}
	@AfterMethod
	public void cleanup() throws Exception {
		
//		try {
//			commonUtility.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah");
//		} catch (Exception e) {
//			commonUtility.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah");
//		}
		sWOJson="{\"SVMXC__Ownership_Type__c\":\"All Records\"}";
		restServices.restUpdaterecord(sObjectApi,sWOJson,sEventIdSFDC_2 );
		toolsPo.syncData(commonUtility);
	}

}
