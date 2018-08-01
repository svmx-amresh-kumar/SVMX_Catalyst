/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.ge.fsa.lib.RestServices;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.ExploreSearchPO;

import com.ge.fsa.pageobjects.LoginHomePO;
import com.ge.fsa.pageobjects.CommonsPO;
import com.ge.fsa.pageobjects.ToolsPO;
import com.ge.fsa.pageobjects.WorkOrderPO;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen.ScreenshotOf;

public class Scenario6 extends BaseLib {
	GenericLib genericLib = null;
	RestServices restServices = null;
	LoginHomePO loginHomePo = null;
	ExploreSearchPO exploreSearchPo = null;
	WorkOrderPO workOrderPo = null;
	CommonsPO commonsPo = null;
	ToolsPO toolsPo = null;
	
	int iWhileCnt = 0;
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sWorkOrderID = null;
	String sWOObejctApi = null;
	String sWOJsonData = null;
	String sWOName1 = null;
	String sWOName2 = null;
	String sFieldServiceName = null;
	String sProductName1 = null;
	String sProductName2 = null;
	String sActivityType = null;
	String sPrintReportSearch = null;
	String sIssueTxt = null;
	String sBillingType = null;
	String sWOSqlQuery = null;
	String sCaseID = null;

	@BeforeMethod
	public void initializeObject() throws IOException { 
		genericLib = new GenericLib();
		restServices = new RestServices();
		loginHomePo = new LoginHomePO(driver);
		exploreSearchPo = new ExploreSearchPO(driver);
		workOrderPo = new WorkOrderPO(driver);	
		toolsPo = new ToolsPO(driver);
		commonsPo = new CommonsPO(driver);
		restServices.getAccessToken();
		sWOObejctApi="SVMXC__Service_Order__c?";
		
		//Creation of dynamic Work Order1
		sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Canceled\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
	//	sWorkOrderID=restServices.restCreate(sWOObejctApi,sWOJsonData);
	//	sWOSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWorkOrderID+"\'";				
	//	sWOName1 =restServices.restGetSoqlValue(sWOSqlQuery,"Name"); //"WO-00000456"; 
		
		//Creation of dynamic Work Order2
		sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
	//	sWorkOrderID=restServices.restCreate(sWOObejctApi,sWOJsonData);
	//	sWOSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWorkOrderID+"\'";				
	//	sWOName2 =restServices.restGetSoqlValue(sWOSqlQuery,"Name"); //"WO-00000455"; 
		sCaseID = "00001000";
	}

	@Test(enabled = true)
	public void toTest() throws Exception {
		sTestCaseID = "SANITY6";
		
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sIssueTxt = GenericLib.getExcelData(sTestCaseID, "IssueText");
		sBillingType = GenericLib.getExcelData(sTestCaseID, "BillingType");
		try {
			
			//Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
			
			
			//Navigation to SFM
			workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sCaseID, sFieldServiceName);
			
			NXGReports.addStep("Saved successfully text is displayed successfully", LogAs.PASSED, null);		
		
			NXGReports.addStep("Testcase " + sTestCaseID + " PASSED", LogAs.PASSED, null);
		} catch (Exception e) {
			NXGReports.addStep("Testcase " + sTestCaseID + " FAILED", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			throw e;
		}

	}
	
	
	
	

}
