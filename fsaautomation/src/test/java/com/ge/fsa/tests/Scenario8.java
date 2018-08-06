/*
 *  @author MeghanaRao
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/AUT-62"
 */
package com.ge.fsa.tests;
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
	String sExploreSearch=null;String sWorkOrderID=null; String sWOJsonData=null;String sWOName=null; String sFieldServiceName=null; String sProductName1=null;String sProductName2=null; 
	String sActivityType=null;String sPrintReportSearch=null;
	String sAccountName = null;
	String woID = null;	
	String sProcessname = "EditWoAutoTimesstamp";
	@Test
	public void Scenario8() throws Exception
	{

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
					// To search the Created Work Order
					toolsPo.syncData(commonsPo);
					//toolsPo.configSync(commonsPo);
				commonsPo.tap(exploreSearchPo.getEleExploreIcn());
				workOrderPo.downloadCriteriaVerification(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sWorkOrderName);
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

					}
				}
				// If the value "Records not displayed" is not visible then the WO is not Online.
				else
				{
					NXGReports.addStep("Testcase " + sTestCaseID + "Work Order is not Online - DOD not available", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					System.out.println("DOD of the Work Order didn't meet");
				}
				
		
	// Creating Product A 
				String sProductAName = commonsPo.generaterandomnumber("Prod");
				String sProductAId = restServices.restCreate("Product2?","{\"Name\": \""+sProductAName+"\", \"IsActive\": \"true\"}");
				System.out.println(sProductAId);
				String sProductNameA = restServices.restGetSoqlValue("SELECT+Name+from+Product2+Where+id+=\'"+sProductAId+"\'", "Name");
				System.out.println(sProductNameA);
	// Creating Product B
				String sProductBName = commonsPo.generaterandomnumber("Prod");
				String sProductBId = restServices.restCreate("Product2?","{\"Name\": \""+sProductBName+"\", \"IsActive\": \"true\"}");
				System.out.println(sProductBId);
				String sProductNameB = restServices.restGetSoqlValue("SELECT+Name+from+Product2+Where+id+=\'"+sProductBId+"\'", "Name");
				System.out.println(sProductNameB);
				
	// Creating Installed Base A 
				String sInstalledBaseAName = commonsPo.generaterandomnumber("IB");
				String sIBIdA = restServices.restCreate("SVMXC__Installed_Product__c?","{\"Name\": \""+sInstalledBaseAName+"\", \"SVMXC__Product__c\": \""+sProductAId+"\"}");
				System.out.println(sIBIdA);
				String sInstalledProductAName = restServices.restGetSoqlValue("SELECT+Name+from+SVMXC__Installed_Product__c+Where+id+=\'"+sIBIdA+"\'", "Name");
				System.out.println(sInstalledProductAName);
				
	// Creating Installed Base B
				String sInstalledBaseBName = commonsPo.generaterandomnumber("IB");
				String sIBIdB = restServices.restCreate("SVMXC__Installed_Product__c?","{\"Name\": \""+sInstalledBaseBName+"\", \"SVMXC__Product__c\": \""+sProductBId+"\"}");
				System.out.println(sIBIdB);
				String sInstalledProductBName = restServices.restGetSoqlValue("SELECT+Name+from+SVMXC__Installed_Product__c+Where+id+=\'"+sIBIdB+"\'", "Name");
				System.out.println(sInstalledProductBName);
				
		// Syncing the Data
				toolsPo.syncData(commonsPo);
				
		// Config Sync
				//toolsPo.configSync(commonsPo);
				
		// Adding the values to the childlines 
				String sProcessname = "Senario8_childlinesSFM";
				System.out.println(sWorkOrderName);
				commonsPo.tap(exploreSearchPo.getEleExploreIcn());
				exploreSearchPo.selectWorkOrder(commonsPo, sWorkOrderName);
				workOrderPo.selectAction(commonsPo,sProcessname);
		// Adding Product A to the Header and verifying the child values
				commonsPo.tap(createNewPO.getEleClickProductfield());
				commonsPo.lookupSearch(sProductAName);
				
		// Coming to the Childlines and Verifying on the IB Serial Number
				
				
				
				
				
			
	}
	

	
}