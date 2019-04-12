package com.ge.fsa.tests.phone;
import static org.testng.Assert.assertEquals;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;


public class Ph_Sanity8_Lookup_DOD extends BaseLib
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
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		
		System.out.println("Scenario 8");
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		// Syncing after the Pre-Requisite is done
		ph_MorePo.syncData(commonUtility);
		//Create a Work Order to verify the Download Criteria
		restServices.getAccessToken();
		sWOJsonData = "{\"SVMXC__City__c\":\"Bangalore\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		woID = restServices.restCreate("SVMXC__Service_Order__c?", sWOJsonData);
		System.out.println("WO ID FETCHED " + woID);
		// To extract the work order name from the Work Order ID
		String sSoqlQueryWoName = "Select+Name+from+SVMXC__Service_Order__c+where+Id+=\'"+woID+"\'";
		restServices.getAccessToken();
		String sWorkOrderName = restServices.restGetSoqlValue(sSoqlQueryWoName, "Name");
		ph_MorePo.syncData(commonUtility);
		ph_ExploreSearchPO.geteleExploreIcn().click();;
		ph_WorkOrderPo.downloadCriteriaDOD(commonUtility, ph_ExploreSearchPO,"AUTOMATION SEARCH", "Work Orders", sWorkOrderName);
		Thread.sleep(2000);
		// If the value "Records not Displayed" is Visible then the Work Order is Online.
			if(ph_ExploreSearchPO.getSearchListItems().size()<=1)
			{		
				 Thread.sleep(2000);
				 ph_ExploreSearchPO.getEleOnline().click();
				 Thread.sleep(2000);
				 // If the Cloud button is Visible then need to Tap on it
				 
				 
					if(commonUtility.waitforElement(ph_ExploreSearchPO.getDownloadIcon(sWorkOrderName),1))
						{
						ph_ExploreSearchPO.getDownloadIcon(sWorkOrderName).click();
						Thread.sleep(10000);
						ph_ExploreSearchPO.getEleSearchName(sWorkOrderName).click();
					
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
				String sProductAName = commonUtility.generaterandomnumber("ProdA");
				String sProductAId = restServices.restCreate("Product2?","{\"Name\": \""+sProductAName+"\", \"IsActive\": \"true\"}");
				String sProductNameA = restServices.restGetSoqlValue("SELECT+Name+from+Product2+Where+id+=\'"+sProductAId+"\'", "Name");
				System.out.println(sProductNameA);
	// Creating Product B
//				String sProductBName = commonUtility.generaterandomnumber("ProdB");
//				String sProductBId = restServices.restCreate("Product2?","{\"Name\": \""+sProductBName+"\", \"IsActive\": \"true\"}");
//				String sProductNameB = restServices.restGetSoqlValue("SELECT+Name+from+Product2+Where+id+=\'"+sProductBId+"\'", "Name");
//				System.out.println(sProductNameB);
				
	// Creating Installed Base A 
				String sInstalledBaseAName = commonUtility.generaterandomnumber("IBA");
				String sIBIdA = restServices.restCreate("SVMXC__Installed_Product__c?","{\"Name\": \""+sInstalledBaseAName+"\", \"SVMXC__Product__c\": \""+sProductAId+"\"}");
				String sInstalledProductAName = restServices.restGetSoqlValue("SELECT+Name+from+SVMXC__Installed_Product__c+Where+id+=\'"+sIBIdA+"\'", "Name");
				

		// Syncing the Data
				ph_MorePo.syncData(commonUtility);
				
		// Config Sync
				//toolsPo.configSync(commonUtility);
				
		// Adding the values to the childlines 
				String sProcessname = "Senario8_childlinesSFM";
				ph_ExploreSearchPO.geteleExploreIcn().click();
			try
			{
				ph_WorkOrderPo.selectAction(commonUtility, sProcessname);
				
			}
			catch(Exception e)
			{
				ph_ExploreSearchPO.getEleSearchName(sWorkOrderName).click();
				ph_WorkOrderPo.selectAction(commonUtility, sProcessname);
				
			}
				
		// Adding Product A to the Header and verifying the child values
				ph_WorkOrderPo.selectFromPickList(commonUtility,  ph_WorkOrderPo.getProductLookup(), sProductNameA);
		// Coming to the Childlines and Verifying on the IB Serial Number
				ph_WorkOrderPo.addParts(commonUtility, sProductNameA);
		
		// Tapping on the Parts added and checking the IB Serial Number
				ph_WorkOrderPo.getChildLineAddedItem(sProductNameA).click();
				commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEleIBSerialNumber());
				Thread.sleep(200);
		// To verify if the Count of the Element on the Lookup is 1. If it is 1 and visible then click on it.
				assertEquals(ph_WorkOrderPo.getIBLookup().size(), 1);
				//NXGReports.addStep("Testcase " + sTestCaseID + "Passed-The Installed Product added in the Lookup is only 1", LogAs.PASSED, null);
				ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Passed-The Installed Product added in the Lookup is only 1");

				if(ph_WorkOrderPo.getIBLookup().size() == 1)
				{
					ph_WorkOrderPo.getChildLineAddItem(sInstalledProductAName).click();;
					ph_WorkOrderPo.getElesave().click();
					ph_WorkOrderPo.getElesave().click();
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
				try{Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");}
				catch(Exception e){genericLib.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah", "sTestCaseID");
				genericLib.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah", "sTestCaseID");
				Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
				}

				
				ph_MorePo.syncData(commonUtility);
				JSONArray sJsonArrayExpenses = restServices.restGetSoqlJsonArray("Select+SVMXC__Actual_Quantity2__c,+SVMXC__Actual_Price2__c,+SVMXC__Product__c,+SVMXC__Activity_Type__c,+SVMXC__Start_Date_and_Time__c,+SVMXC__End_Date_and_Time__c,+SVMXC__Expense_Type__c,+SVMXC__Work_Description__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Line_Type__c='Parts'+AND+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sWorkOrderName+"\')");
				String sProductID = restServices.getJsonValue(sJsonArrayExpenses, "SVMXC__Product__c");
				String sSoqlProductName = "Select+Name+from+Product2+where+Id=\'"+sProductID+"\'";
				
				String sProductName2 = restServices.restGetSoqlValue(sSoqlProductName,"Name");
				String sLineQtyParts = restServices.getJsonValue(sJsonArrayExpenses, "SVMXC__Actual_Quantity2__c");
				assertEquals(sProductNameA, sProductName2);
				assertEquals(sLineQtyParts, "1.0");
	
	}
	
	
}