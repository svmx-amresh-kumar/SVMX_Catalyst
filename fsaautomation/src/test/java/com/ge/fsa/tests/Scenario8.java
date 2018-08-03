/*
 *  @author MeghanaRao
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/AUT-62"
 */
package com.ge.fsa.tests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.xpath.XPath;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.testng.annotations.BeforeMethod;
import com.ge.fsa.lib.RestServices;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.ExploreSearchPO;
import com.ge.fsa.pageobjects.LoginHomePO;
import com.ge.fsa.pageobjects.RecentItemsPO;
import com.ge.fsa.pageobjects.CalendarPO;
import com.ge.fsa.pageobjects.CommonsPO;
import com.ge.fsa.pageobjects.CreateNewPO;
import com.ge.fsa.pageobjects.ToolsPO;
import com.ge.fsa.pageobjects.WorkOrderPO;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen.ScreenshotOf;

import net.bytebuddy.implementation.bytecode.Throw;



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
	public void Scenario8Functions() throws Exception
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
						
						workOrderPo.selectAction(commonsPo,sProcessname);
						
					}
					// If the cloud button is not visible then throw an Error in the Report
					else
					{
					NXGReports.addStep("Testcase " + sTestCaseID + "DOD of the Work Order didn't meet", LogAs.FAILED, null);

					}
					}
				// If the value "Records not displayed" is not visible then the WO is not Online.
				else
				{
					NXGReports.addStep("Testcase " + sTestCaseID + "Work Order is not Online - DOD not available", LogAs.FAILED, null);
					System.out.println("DOD of the Work Order didn't meet");
				}
				
		
	// Creating Product A and Product B
				
	// Creating Installed Base A and Installed Base B
	}
	

	
}