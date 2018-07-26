package com.ge.fsa.tests;

import java.io.IOException;

import org.json.JSONArray;
import org.testng.annotations.AfterMethod;
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

public class Scenario2 extends BaseLib{
	
	
	
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
	public void initializeObject() throws Exception { // Initialization of objects


}

	@Test(enabled = true)
	public void scenario2_checklist() throws Exception {
		sTestCaseID = "RS_2389_checklist";
		sCaseWOID = "RS_2389_checklistID";
		sCaseSahiFile = "backOffice/appium_verifyWorkDetails.sah";

		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID, "ChecklistName");

		JSONArray sJsonArrayWo = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");

		String sWORecordID = restServices.getJsonValue(sJsonArrayWo, "id");
		
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "name");

System.out.println("WO no ="+sWOName);
	String ChecklistTextQuestion ="text2372018162553";
			

		GenericLib.setCongigValue(GenericLib.sDataFile, sCaseWOID, sWOName);
		try {
			

			// Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
			toolsPo.configSync(commonsPo);
			System.out.println("Config Sync completed.....");
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
			Thread.sleep(2000);
			commonsPo.longPress(checklistPo.geteleChecklistName(sChecklistName));
			Thread.sleep(2000);
			checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion).sendKeys("Text Question Answered");
			
			//tapping the next button in checklist
			commonsPo.tap(checklistPo.eleNext());
			//submitting the checklist
			Thread.sleep(2000);
			commonsPo.tap(checklistPo.eleChecklistSubmit());
		
			//tapping on the validation sucessfull checklist popup		   
		   
		   commonsPo.longPress(checklistPo.eleChecklistPopupSubmit());
		    System.out.println("finished clicking on submit popup.");
		 
		    
		    //Tapping on Show Completed Checklists
		    System.out.println("going to tap on show completedchecklists");
		    commonsPo.longPress(checklistPo.eleShowCompletedChecklist());
		    System.out.println("tapped on completed checklist");
	  
		    
		   System.out.println("going to tap on the completedchecklist");
		   System.out.println(driver.getPageSource());
		    commonsPo.tap(checklistPo.eleCompletedChecklistName(sChecklistName));
		    System.out.println(checklistPo.eleCompletedChecklistName(sChecklistName));
		    System.out.println("tapped on completed checklist");
		    String qans = checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion).getText();
		    System.out.println("tesx====="+qans);
		    if (qans == "Text Question Answered") {
				System.out.println("Answered value sucessfull!");
			}
			// Sync the Data
			//	toolsPo.syncData(commonsPo);
			// Execute Sahi for server side validation
			genericLib.executeSahiScript(GenericLib.getCongigValue(GenericLib.sDataFile, "RS_2389_SAHISCRIPT"),
					sTestCaseID);
			NXGReports.addStep("Testcase " + sTestCaseID + " PASSED", LogAs.PASSED, null);
		} 
		catch (Exception e) {
			//NXGReports.addStep("Testcase " + sTestCaseID + " FAILED", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			throw e;
		}

	}

	
	@AfterMethod
		public void tearDownDriver()
		{
			driver.quit();
		}
		
		
}
	
	
	


