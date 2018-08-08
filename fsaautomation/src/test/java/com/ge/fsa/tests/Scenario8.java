/*
 *  @author MeghanaRao
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/AUT-62"
 */
package com.ge.fsa.tests;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import com.ge.fsa.lib.BaseLib;

import com.kirwa.nxgreport.*;
import com.kirwa.nxgreport.logging.LogAs;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen.ScreenshotOf;


public class Scenario8 extends BaseLib
{
	
	
	int iWhileCnt =0;
	String sTestCaseID="Scenario-8"; String sCaseWOID=null; String sCaseSahiFile=null;
	String sExploreSearch="AUTOMATION SEARCH";
	String sWorkOrderID=null; String sWOJsonData=null;String sWOName=null; String sFieldServiceName=null; String sProductName1=null;String sProductName2=null; 
	String sActivityType=null;String sPrintReportSearch=null;
	String sAccountName = null;
	String woID = null;	
	String sProcessname = "EditWoAutoTimesstamp";
	String sExploreChildSearchTxt = "Work Orders";


	@Test		
	public void Scenario8Test() throws Exception
	{
		// running the Sahi Script Pre-requisites
		genericLib.executeSahiScript("appium/scenario8_scenariolevel_afterrun.sah", "sTestCaseID");
		
		System.out.println("Scenario 8");
		loginHomePo.login(commonsPo, exploreSearchPo);
		//Create a Work Order to verify the Download Criteria
		restServices.getAccessToken();
		sWOJsonData = "{\"SVMXC__City__c\":\"Bangalore\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		woID = restServices.restCreate("SVMXC__Service_Order__c?", sWOJsonData);
		System.out.println("WO ID FETCHED " + woID);
		// To extract the work order name from the Work Order ID
		String sSoqlQueryWoName = "Select+Name+from+SVMXC__Service_Order__c+where+Id+=\'"+woID+"\'";
		restServices.getAccessToken();
		String sWorkOrderName = restServices.restGetSoqlValue(sSoqlQueryWoName, "Name");
		toolsPo.syncData(commonsPo);
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());
		workOrderPo.downloadCriteriaDOD(commonsPo, exploreSearchPo,"AUTOMATION SEARCH", "Work Orders", sWorkOrderName);
		// If the value "Records not Displayed" is Visible then the Work Order is Online.
			if(exploreSearchPo.getEleNorecordsToDisplay().isDisplayed())
				{				
				 commonsPo.tap(exploreSearchPo.getEleOnlineBttn());
				 commonsPo.tap(exploreSearchPo.getEleExploreSearchBtn());
				 // If the Cloud button is Visible then need to Tap on it
					if(exploreSearchPo.getEleCloudSymbol().isDisplayed())
						{
						commonsPo.tap(exploreSearchPo.getEleCloudSymbol(),20,20);
						commonsPo.tap(exploreSearchPo.getEleWorkOrderIDTxt(sWorkOrderName),10,10);
					
						}
					// If the cloud button is not visible then throw an Error in the Report
						else
						{
							NXGReports.addStep("Testcase " + sTestCaseID + "DOD of the Work Order didn't meet", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
							driver.quit();
						}
				}
				// If the value "Records not displayed" is not visible then the WO is not Online.
				else
				{
					NXGReports.addStep("Testcase " + sTestCaseID + "Work Order is not Online - DOD not available", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					System.out.println("DOD of the Work Order didn't meet");
					driver.quit();
				}
				
		
	// Creating Product A 
				String sProductAName = commonsPo.generaterandomnumber("ProdA");
				String sProductAId = restServices.restCreate("Product2?","{\"Name\": \""+sProductAName+"\", \"IsActive\": \"true\"}");
				String sProductNameA = restServices.restGetSoqlValue("SELECT+Name+from+Product2+Where+id+=\'"+sProductAId+"\'", "Name");
				System.out.println(sProductNameA);
	// Creating Product B
				String sProductBName = commonsPo.generaterandomnumber("ProdB");
				String sProductBId = restServices.restCreate("Product2?","{\"Name\": \""+sProductBName+"\", \"IsActive\": \"true\"}");
				String sProductNameB = restServices.restGetSoqlValue("SELECT+Name+from+Product2+Where+id+=\'"+sProductBId+"\'", "Name");
				System.out.println(sProductNameB);
				
	// Creating Installed Base A 
				String sInstalledBaseAName = commonsPo.generaterandomnumber("IBA");
				String sIBIdA = restServices.restCreate("SVMXC__Installed_Product__c?","{\"Name\": \""+sInstalledBaseAName+"\", \"SVMXC__Product__c\": \""+sProductAId+"\"}");
				String sInstalledProductAName = restServices.restGetSoqlValue("SELECT+Name+from+SVMXC__Installed_Product__c+Where+id+=\'"+sIBIdA+"\'", "Name");
				

		// Syncing the Data
				toolsPo.syncData(commonsPo);
				
		// Config Sync
				toolsPo.configSync(commonsPo);
				
		// Adding the values to the childlines 
				String sProcessname = "Senario8_childlinesSFM";
				commonsPo.tap(exploreSearchPo.getEleExploreIcn());
				exploreSearchPo.selectWorkOrder(commonsPo,sWorkOrderName);
				workOrderPo.selectAction(commonsPo,sProcessname);
		// Adding Product A to the Header and verifying the child values
				commonsPo.tap(workOrderPo.getEleProductLookup());
				commonsPo.lookupSearch(sProductAName);
		// Coming to the Childlines and Verifying on the IB Serial Number
				commonsPo.tap(workOrderPo.getElePartLnk());
				commonsPo.lookupSearch(sProductAName);
				commonsPo.tap(workOrderPo.getEleAddselectedbutton());
		// Tapping on the Parts added and checking the IB Serial Number
				commonsPo.tap(workOrderPo.getEleTaponParts(sProductAName));
				commonsPo.tap(workOrderPo.getEleIbSerialnumTap());
				Thread.sleep(2000);
		// To verify if the Count of the Element on the Lookup is 1. If it is 1 and visible then click on it.
				assertEquals(workOrderPo.getEleIBSerialNumber().size(), 1);
				NXGReports.addStep("Testcase " + sTestCaseID + "Passed-The Installed Product added in the Lookup is only 1", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				if(workOrderPo.getEleIBSerialNumber().size() == 1)
				{
					commonsPo.tap(workOrderPo.getEleeleIBId(sInstalledProductAName));
					commonsPo.tap(workOrderPo.getEleDoneBtn());
					NXGReports.addStep("Testcase " + sTestCaseID + "Passed-Clicked on the Installed Product", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
		// Else print with a Failure because there are more than 1 IB under the Lookup
				else
				{
					NXGReports.addStep("Testcase " + sTestCaseID + "More than 1 IB is present under the Lookup of IBSerial number", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					
				}

	}

	
}
