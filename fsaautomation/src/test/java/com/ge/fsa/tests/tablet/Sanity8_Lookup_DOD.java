/*
 *  @author MeghanaRao
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/AUT-62"
 */
package com.ge.fsa.tests.tablet;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;


public class Sanity8_Lookup_DOD extends BaseLib
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


	@Test(retryAnalyzer=Retry.class)		
	public void Scenario8Test() throws Exception
	{
	// running the Sahi Script Pre-requisites - To make All Records to My Records in Mobile Configuration
		genericLib.executeSahiScript("appium/setDownloadCriteriaWoToMyRecords.sah");
		Assert.assertTrue(commonsUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		
		System.out.println("Scenario 8");
		loginHomePo.login(commonsUtility, exploreSearchPo);
		// Syncing after the Pre-Requisite is done
		toolsPo.syncData(commonsUtility);
		//Create a Work Order to verify the Download Criteria
		restServices.getAccessToken();
		sWOJsonData = "{\"SVMXC__City__c\":\"Bangalore\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		woID = restServices.restCreate("SVMXC__Service_Order__c?", sWOJsonData);
		System.out.println("WO ID FETCHED " + woID);
		// To extract the work order name from the Work Order ID
		String sSoqlQueryWoName = "Select+Name+from+SVMXC__Service_Order__c+where+Id+=\'"+woID+"\'";
		restServices.getAccessToken();
		String sWorkOrderName = restServices.restGetSoqlValue(sSoqlQueryWoName, "Name");
		toolsPo.syncData(commonsUtility);
		commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
		workOrderPo.downloadCriteriaDOD(commonsUtility, exploreSearchPo,"AUTOMATION SEARCH", "Work Orders", sWorkOrderName);
		// If the value "Records not Displayed" is Visible then the Work Order is Online.
			if(exploreSearchPo.getEleNorecordsToDisplay().isDisplayed())
				{				
				 commonsUtility.tap(exploreSearchPo.getEleOnlineBttn());
				 commonsUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
				 // If the Cloud button is Visible then need to Tap on it
				 
				 
					if(exploreSearchPo.getEleCloudSymbol().isDisplayed())
						{
						commonsUtility.tap(exploreSearchPo.getEleCloudSymbol(),20,20);
						commonsUtility.tap(exploreSearchPo.getEleWorkOrderIDTxt(sWorkOrderName),10,10);
					
						}
					// If the cloud button is not visible then throw an Error in the Report
						else
						{
							//NXGReports.addStep("Testcase " + sTestCaseID + "DOD of the Work Order didn't meet", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
							ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "DOD of the Work Order didn't meet");

							driver.quit();
						}
				}
				// If the value "Records not displayed" is not visible then the WO is not Online.
				else
				{
					//NXGReports.addStep("Testcase " + sTestCaseID + "Work Order is not Online - DOD not available", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "DOD of the Work Order didn't meet");

					System.out.println("DOD of the Work Order didn't meet");
					driver.quit();
				}
				
		
	// Creating Product A 
				String sProductAName = commonsUtility.generaterandomnumber("ProdA");
				String sProductAId = restServices.restCreate("Product2?","{\"Name\": \""+sProductAName+"\", \"IsActive\": \"true\"}");
				String sProductNameA = restServices.restGetSoqlValue("SELECT+Name+from+Product2+Where+id+=\'"+sProductAId+"\'", "Name");
				System.out.println(sProductNameA);
	// Creating Product B
				String sProductBName = commonsUtility.generaterandomnumber("ProdB");
				String sProductBId = restServices.restCreate("Product2?","{\"Name\": \""+sProductBName+"\", \"IsActive\": \"true\"}");
				String sProductNameB = restServices.restGetSoqlValue("SELECT+Name+from+Product2+Where+id+=\'"+sProductBId+"\'", "Name");
				System.out.println(sProductNameB);
				
	// Creating Installed Base A 
				String sInstalledBaseAName = commonsUtility.generaterandomnumber("IBA");
				String sIBIdA = restServices.restCreate("SVMXC__Installed_Product__c?","{\"Name\": \""+sInstalledBaseAName+"\", \"SVMXC__Product__c\": \""+sProductAId+"\"}");
				String sInstalledProductAName = restServices.restGetSoqlValue("SELECT+Name+from+SVMXC__Installed_Product__c+Where+id+=\'"+sIBIdA+"\'", "Name");
				

		// Syncing the Data
				toolsPo.syncData(commonsUtility);
				
		// Config Sync
				//toolsPo.configSync(commonsUtility);
				
		// Adding the values to the childlines 
				String sProcessname = "Senario8_childlinesSFM";
				commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
			try
			{
				workOrderPo.selectAction(commonsUtility,sProcessname);
				
			}
			catch(Exception e)
			{
				commonsUtility.tap(exploreSearchPo.getEleWorkOrderIDTxt(sWorkOrderName));
				workOrderPo.selectAction(commonsUtility,sProcessname);
				
			}
				
		// Adding Product A to the Header and verifying the child values
				commonsUtility.tap(workOrderPo.getEleProductLookup());
				commonsUtility.lookupSearch(sProductNameA);
		// Coming to the Childlines and Verifying on the IB Serial Number
				commonsUtility.tap(workOrderPo.getElePartLnk());
				commonsUtility.lookupSearch(sProductNameA);
				commonsUtility.tap(workOrderPo.getEleAddselectedbutton());
		// Tapping on the Parts added and checking the IB Serial Number
				commonsUtility.tap(workOrderPo.getEleTaponParts(sProductNameA));
				commonsUtility.tap(workOrderPo.getEleIbSerialnumTap());
				
				Thread.sleep(2000);
		// To verify if the Count of the Element on the Lookup is 1. If it is 1 and visible then click on it.
				assertEquals(workOrderPo.getEleIBSerialNumber().size(), 1);
				//NXGReports.addStep("Testcase " + sTestCaseID + "Passed-The Installed Product added in the Lookup is only 1", LogAs.PASSED, null);
				ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Passed-The Installed Product added in the Lookup is only 1");

				if(workOrderPo.getEleIBSerialNumber().size() == 1)
				{
					commonsUtility.tap(workOrderPo.getEleeleIBId(sInstalledProductAName));
					commonsUtility.tap(workOrderPo.getEleDoneBtn());
					commonsUtility.tap(workOrderPo.getEleClickSave());
					//NXGReports.addStep("Testcase " + sTestCaseID + "Passed-Clicked on the Installed Product", LogAs.PASSED, null);
					ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Passed-Clicked on the Installed Product");

				}
		// Else print with a Failure because there are more than 1 IB under the Lookup
				else
				{
					//NXGReports.addStep("Testcase " + sTestCaseID + "More than 1 IB is present under the Lookup of IBSerial number", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "More than 1 IB is present under the Lookup of IBSerial number");

				}
				
				// running the Sahi Script Pre-requisites - To make My Records to All Records in Mobile Configuration
				genericLib.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah", "sTestCaseID");
				try{Assert.assertTrue(commonsUtility.verifySahiExecution(), "Execution of Sahi script is failed");}
				catch(Exception e){genericLib.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah", "sTestCaseID");
				genericLib.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah", "sTestCaseID");
				Assert.assertTrue(commonsUtility.verifySahiExecution(), "Execution of Sahi script is failed");
				}

				
				toolsPo.syncData(commonsUtility);
	
	}
	
	
}
