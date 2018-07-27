package com.ge.fsa.tests;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
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

import io.appium.java_client.TouchAction;

public class Scenario2 extends BaseLib {

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
//		sWOJsonData = "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
//		sWorkOrderID = restServices.getWOORecordID(sWOJsonData);
//		sWOName = restServices.getWOName(sWorkOrderID);
		// sWOName="WO-00000269";
		String ChecklistTextQuestion = "text2372018162553";

		GenericLib.setCongigValue(GenericLib.sDataFile, sCaseWOID, sWOName);
		try {

			// Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
			//toolsPo.configSync(commonsPo);
			toolsPo.syncData(commonsPo);	
			commonsPo.tap(exploreSearchPo.getEleExploreIcn());
			// Explore and Navigate to the Search Process
			commonsPo.getSearch(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
			exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
			commonsPo.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
			// // Select the Work Order
			exploreSearchPo.selectWorkOrder(commonsPo, sWOName);
			// // Navigate to Field Service
			workOrderPo.selectAction(commonsPo, sFieldServiceName);
			// Navigating to the checklist
			Thread.sleep(5000);
		//	System.out.println(checklistPo.geteleStartNew());
			/*
			 * if (checklistPo.geteleStartNew() != null) {
			 * System.out.println("entered loop -now click start new");
			 * commonsPo.tap(checklistPo.geteleStartNew(),15,18);
			 * 
			 * 
			 * //checklistPo.geteleStartNew().click(); //
			 * System.out.println(checklistPo.geteleStartNew().getLocation()); // Point p =
			 * checklistPo.geteleStartNew().getLocation(); // p.x = p.x*2; //
			 * System.out.println("loc"+p); // p.move(p.x, p.y); // // //
			 * //commonsPo.longPress(checklistPo.geteleStartNew()); //
			 * commonsPo.tap(checklistPo.geteleStartNew(),p.x,p.y); } else
			 */
			commonsPo.longPress(checklistPo.geteleChecklistName(sChecklistName));
			Thread.sleep(2000);
			checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion).sendKeys("Text Question Answered");

			// tapping the next button in checklist
			commonsPo.tap(checklistPo.eleNext());
			// submitting the checklist
			Thread.sleep(2000);
			commonsPo.tap(checklistPo.eleChecklistSubmit());

			// tapping on the validation sucessfull checklist popup

			commonsPo.longPress(checklistPo.eleChecklistPopupSubmit());
			System.out.println("finished clicking on submit popup.");

			// Tapping on Show Completed Checklists
			System.out.println("going to tap on show completedchecklists");
			commonsPo.longPress(checklistPo.eleShowCompletedChecklist());
			System.out.println("tapped on completed checklist");
			System.out.println("going to tap on the completedchecklist");
			commonsPo.tap(checklistPo.eleCompletedChecklistName(sChecklistName));
			//System.out.println(checklistPo.eleCompletedChecklistName(sChecklistName));
			System.out.println("tapped on completed checklist");
		//	System.out.println("printing checklisttextquesion" + ChecklistTextQuestion);
		//	Thread.sleep(5000);
			System.out.println("=============================================");
			//checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion)ChecklistTextQuestion.click();
			String qans = checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion).getText();
			System.out.println("trying with eleone----.......");
			
			//checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion);
			System.out.println(checklistPo.getText(driver, checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion)));
			WebElement ws = checklistPo.getelecsstextarea2();
			System.out.println(checklistPo.getelecsstextarea2().getText());
			
		  //  System.out.println("vinaya printing ws"+ws);
		    checklistPo.expandRootElement(ws);
		    System.out.println("post expanding root");
		    System.out.println(ws);
		    System.err.println("-----------------");
		    System.out.println(ws.getText());
		    System.out.println("innerhtml------");
		    System.out.println(ws.getAttribute("innerHTML"));
		    System.out.println("----innertext-----");		
		    System.out.println(ws.getAttribute("innerText"));
		    
		    
			//System.out.println(checklistPo.getshadowroot(ChecklistTextQuestion).getText());;
			//WebElement shadowroot = (WebElement) ((JavascriptExecutor)driver) .executeScript("return arguments[0].shadowRoot",checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion));
			//System.out.println("shadowroot.text"+shadowroot.getText());
			//*[@id="ext-element-1731"]//div
			// Sync the Data
			// toolsPo.syncData(commonsPo);
			// Execute Sahi for server side validation
			genericLib.executeSahiScript(GenericLib.getCongigValue(GenericLib.sDataFile, "RS_2389_SAHISCRIPT"),
					sTestCaseID);
			NXGReports.addStep("Testcase " + sTestCaseID + " PASSED", LogAs.PASSED, null);
		} catch (Exception e) {
			// NXGReports.addStep("Testcase " + sTestCaseID + " FAILED", LogAs.FAILED,new
			// CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			throw e;
		}

	}

	@AfterMethod
	public void tearDownDriver() {
		driver.quit();
	}

}

