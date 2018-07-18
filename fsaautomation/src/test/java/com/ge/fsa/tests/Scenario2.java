package com.ge.fsa.tests;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.RestServices;
import com.ge.fsa.pageobjects.ChecklistPO;
import com.ge.fsa.pageobjects.CommonsPO;
import com.ge.fsa.pageobjects.ExploreSearchPO;
import com.ge.fsa.pageobjects.LoginHomePO;
import com.ge.fsa.pageobjects.ToolsPO;
import com.ge.fsa.pageobjects.WorkOrderPO;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen.ScreenshotOf;

public class Scenario2 extends BaseLib {
	GenericLib genericLib = null;
	RestServices restServices = null;
	LoginHomePO loginHomePo = null;
	ExploreSearchPO exploreSearchPo = null;
	WorkOrderPO workOrderPo = null;
	CommonsPO commonsPo = null;
	ChecklistPO checklistPo = null;
	
	ToolsPO toolsPo = null;
	int iWhileCnt = 0;
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sFieldServiceName = null;
	String sProductName1 = null;
	String sProductName2 = null;
	String sActivityType = null;
	String sPrintReportSearch = null;
	String sChecklistName = null;

	@BeforeMethod
	public void initializeObject() throws IOException { // Initialization of objects
		genericLib = new GenericLib();
		restServices = new RestServices();
		loginHomePo = new LoginHomePO(driver);
		exploreSearchPo = new ExploreSearchPO(driver);
		workOrderPo = new WorkOrderPO(driver);
		toolsPo = new ToolsPO(driver);
		commonsPo = new CommonsPO(driver);
		checklistPo = new ChecklistPO(driver);
	
	}

	@Test(enabled = true)
	public void scenario2_checklist() throws Exception {
		sTestCaseID = "RS_2389_checklist";
		sCaseWOID = "RS_2389_checklistID";
		sCaseSahiFile = "backOffice/appium_verifyWorkDetails.sah";
		sWOJsonData = "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Bangalore\"}";
		/*
		 * //Creation of dynamic Work Order
		 * sWorkOrderID=restServices.getWOORecordID(sWOJsonData); sWOName
		 * =restServices.getWOName(sWorkOrderID);
		 */
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID, "ChecklistName");
		
		restServices.getAccessToken();
		// Creation of dynamic Work Order
		sWOJsonData = "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";

		sWorkOrderID = restServices.getWOORecordID(sWOJsonData);
		sWOName = restServices.getWOName(sWorkOrderID);

		GenericLib.setCongigValue(GenericLib.sDataFile, sCaseWOID, sWOName);
		try {
			

			// Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
			toolsPo.syncData(commonsPo);
			commonsPo.tap(exploreSearchPo.getEleExploreIcn());
			// Explore and Navigate to the Search Process
			commonsPo.getSearch(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
			exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
			commonsPo.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
			// Select the Work Order
			exploreSearchPo.selectWorkOrder(commonsPo, sWOName);
			// Navigate to Field Service
			workOrderPo.selectAction(commonsPo, sFieldServiceName);	
			//Navigating to the checklist
		
			commonsPo.longPress(checklistPo.geteleChecklistName(sChecklistName));
			// Sync the Data
		//	toolsPo.syncData(commonsPo);
			

			// Execute Sahi for server side validation
			genericLib.executeSahiScript(GenericLib.getCongigValue(GenericLib.sDataFile, "RS_2389_SAHISCRIPT"),
					sTestCaseID);
			NXGReports.addStep("Testcase " + sTestCaseID + " PASSED", LogAs.PASSED, null);
		} 
		catch (Exception e) {
			NXGReports.addStep("Testcase " + sTestCaseID + " FAILED", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			throw e;
		}

	}

}
	
	


