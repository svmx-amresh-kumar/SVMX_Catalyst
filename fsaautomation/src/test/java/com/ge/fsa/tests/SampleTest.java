/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;
import java.io.IOException;
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

public class SampleTest extends BaseLib {
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
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sFieldServiceName = null;
	String sProductName1 = null;
	String sProductName2 = null;
	String sActivityType = null;
	String sPrintReportSearch = null;

	@BeforeMethod
	public void initializeObject() throws IOException { // Initialization of objects
		genericLib = new GenericLib();
		restServices = new RestServices();

		loginHomePo = new LoginHomePO(driver);
		exploreSearchPo = new ExploreSearchPO(driver);
		workOrderPo = new WorkOrderPO(driver);
		
		toolsPo = new ToolsPO(driver);
		commonsPo = new CommonsPO(driver);
	
	}

	@Test(enabled = true)
	public void toTest() throws Exception {
		sTestCaseID = "RS_2389";
		sCaseWOID = "RS_2389_WOID";
		sCaseSahiFile = "backOffice/appium_verifyWorkDetails.sah";
		sWOJsonData = "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		/*
		 * //Creation of dynamic Work Order
		 * sWorkOrderID=restServices.getWOORecordID(sWOJsonData); sWOName
		 * =restServices.getWOName(sWorkOrderID);
		 */
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sProductName1 = GenericLib.getExcelData(sTestCaseID, "ProductName1");
		sProductName2 = GenericLib.getExcelData(sTestCaseID, "ProductName2");
		sActivityType = GenericLib.getExcelData(sTestCaseID, "ActivityType");
		sPrintReportSearch = "Print Service Report";
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

			// Create new event for Work Order
			workOrderPo.createNewEvent(commonsPo, "Test Subject", "Test Desscription");

			// Navigate to Field Service
			workOrderPo.selectAction(commonsPo, sFieldServiceName);

			// Add labor parts and update the Work Order
			workOrderPo.addLaborParts(commonsPo, workOrderPo, sProductName1, sActivityType);

			// Add Travel and update the Work Order
			workOrderPo.addTravel(commonsPo, workOrderPo);

			// Save the updated Work Order
			commonsPo.tap(workOrderPo.getEleSaveLnk());
			Thread.sleep(GenericLib.iHighSleep);

			// Validate the updated report
			workOrderPo.validateServiceReport(commonsPo, sPrintReportSearch, sWOName);

			// Sync the Data
			toolsPo.syncData(commonsPo);

			// Execute Sahi for server side validation
			genericLib.executeSahiScript(GenericLib.getCongigValue(GenericLib.sDataFile, "RS_2389_SAHISCRIPT"),
					sTestCaseID);
			NXGReports.addStep("Testcase " + sTestCaseID + " PASSED", LogAs.PASSED, null);
		} catch (Exception e) {
			NXGReports.addStep("Testcase " + sTestCaseID + " FAILED", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			throw e;
		}

	}

}
