package com.ge.fsa.tests;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.json.JSONArray;
import org.testng.Assert;
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
import com.google.gson.internal.bind.DateTypeAdapter;
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
	String sExploreChildSearchTxt = null;

	@BeforeMethod
	public void initializeObject() throws Exception { // Initialization of objects

	}

	@Test(enabled = true)
	public void scenario2_checklist() throws Exception {
		sTestCaseID = "RS_2389_checklist";
		sCaseWOID = "RS_2389_checklistID";
		sCaseSahiFile = "backOffice/appium_verifyWorkDetails.sah";
		//sWOJsonData = "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Bangalore\"}";
		
		//Extracting Excel Data
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID, "ChecklistName");

		// Creation of dynamic Work Order
		restServices.getAccessToken();
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		//sWOName="WO-00000609";
		
		
		String radioQuestion ="checkbox2372018162542";
		String radioAns = null;		
		String textQuestion = "text2372018162553";
		String textAns = "Text Question Answered";
		String numberQuestion = "number2372018162548";
		String numberAns ="200";		
		String picklistQuestion = "picklist2372018162550";
		String picklistAns = "Answer2372018163046";
		String dateQuestion = "date2372018162544";
		String dateAns = null;
		String dateTimeQuestion = "dateTime2372018162545";
		String dateTimeAns = null;
		String checklistStatus = "Completed";
		
	//	GenericLib.setCongigValue(GenericLib.sDataFile, sCaseWOID, sWOName);
		try {

			// Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
			//toolsPo.configSync(commonsPo);
			toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			
			workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);					
			Thread.sleep(GenericLib.iMedSleep);

			System.out.println("Going to Enter checklist");
			commonsPo.longPress(checklistPo.geteleChecklistName(sChecklistName));
			Thread.sleep(GenericLib.iLowSleep);
			
			System.out.println("Entering Text Question Answer");
			checklistPo.geteleChecklistAnswerTextArea(textQuestion).sendKeys(textAns);
			
			System.out.println("Entering Number Question Answer");
			checklistPo.geteleChecklistAnsNumber(numberQuestion).sendKeys(numberAns);;
			
			System.out.println("Selecting Picklist Question Answer");
			commonsPo.pickerWheel(checklistPo.geteleChecklistAnsPicklist(picklistQuestion), picklistAns);
			
			System.out.println("Setting  Date Question Answer");
			checklistPo.geteleChecklistAnsDate(dateQuestion).click();
			commonsPo.switchContext("Native");
		    commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
		    commonsPo.switchContext("WebView");
		    dateAns = checklistPo.geteleChecklistAnsDate(dateQuestion).getAttribute("value");
		    
		    System.out.println("Setting dateTime Question Answer");
		    checklistPo.geteleChecklistAnsDate(dateTimeQuestion).click();
		    commonsPo.switchContext("Native");
		    commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
		    commonsPo.switchContext("WebView");
		    dateTimeAns = checklistPo.geteleChecklistAnsDate(dateTimeQuestion).getAttribute("value");
		 
		    
		    System.out.println("Setting Radio button  Question Answer");
		    checklistPo.geteleChecklistAnsradio(radioQuestion).click();
		    commonsPo.longPress(checklistPo.geteleChecklistAnsradio(radioQuestion));
			
		    // tapping the next button in checklist
		 	commonsPo.tap(checklistPo.eleNext());

		 	
			// submitting the checklist
			Thread.sleep(GenericLib.iLowSleep);
			commonsPo.tap(checklistPo.eleChecklistSubmit());			
		

			// tapping on the validation sucessfull checklist popup
			commonsPo.longPress(checklistPo.eleChecklistPopupSubmit());
			System.out.println("finished clicking on submit popup.");

			
			// Tapping on Show Completed Checklists
			System.out.println("going to tap on show completedchecklists");
			commonsPo.longPress(checklistPo.eleShowCompletedChecklist());
			System.out.println("tapped on completed checklist");
			System.out.println("going to tap on the completedchecklist");
			commonsPo.tap(checklistPo.geteleCompletedChecklistName(sChecklistName));
			System.out.println("tapped on completed checklist");
			
			
			
			
			System.out.println("====================Checklist Answers Valdation=========================");
			
			//String qans = checklistPo.geteleChecklistAnswerTextArea(textQuestion).getAttribute("value");
			Assert.assertEquals(checklistPo.geteleChecklistAnswerTextArea(textQuestion).getAttribute("value"), textAns, "textquestion answered is not displayed");
			NXGReports.addStep("ChecklistText Quesiton Answer validation sucessfull", LogAs.PASSED, null);
		
			
		    //String dateCompletedval = checklistPo.geteleChecklistAnsDate(dateQuestion).getAttribute("value");
			Assert.assertEquals(checklistPo.geteleChecklistAnsDate(dateQuestion).getAttribute("value"), dateAns, "date checklist question answered is not displayed");
			NXGReports.addStep("Checklist Date Quesiton Answer validation sucessfull", LogAs.PASSED, null);
		    
					
			Assert.assertEquals(checklistPo.geteleChecklistAnsDate(dateTimeQuestion).getAttribute("value"), dateTimeAns, "datetime checklist question answered is not displayed");
			NXGReports.addStep("Checklist Datetime Quesiton Answer validation sucessfull", LogAs.PASSED, null);
			
		
			Assert.assertEquals(checklistPo.geteleChecklistAnsNumber(numberQuestion).getAttribute("value"), numberAns, "number checklist question answered is not displayed");
			NXGReports.addStep("Checklist Number Quesiton Answer validation sucessfull", LogAs.PASSED, null);
			
			
			Assert.assertEquals(checklistPo.geteleChecklistAnsPicklist(picklistQuestion).getAttribute("value"), picklistAns, "number checklist question answered is not displayed");
			NXGReports.addStep("Checklist Number Quesiton Answer validation sucessfull", LogAs.PASSED, null);
			
			
			checklistPo.navigateBacktoWorkOrder(commonsPo);
			
			
			
			// Sync the Data
			 toolsPo.syncData(commonsPo);		
			 
			
			 									//SERVER SIDE API VALIDATIONS
			 
			 
			System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
			String ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName+"')";
			String ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");	
			Assert.assertTrue(ChecklistQueryval.contains(checklistStatus),"checklist completed is not synced to server");
			NXGReports.addStep("Checklist Completed status is displayed in Salesforce after sync", LogAs.PASSED, null);
			
			
			
			String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");			
			Assert.assertTrue(ChecklistAnsjson.contains(textAns), "checklist text question answer is not synced to server");
			NXGReports.addStep("checklist text question answer is not synced to server", LogAs.PASSED, null);
			
			Assert.assertTrue(ChecklistAnsjson.contains(numberAns), "checklist number answer sycned to server in checklist answer");
			NXGReports.addStep("checklist number answer sycned to server in checklist answer", LogAs.PASSED, null);
			
			Assert.assertTrue(ChecklistAnsjson.contains(dateAns), "checklist date answer was not sycned to server in checklist answer");
			NXGReports.addStep("checklist date question answer synced to server", LogAs.PASSED, null);
			
			Assert.assertTrue(ChecklistAnsjson.contains(dateTimeAns), "checklist datetime answer was not sycned to server in checklist answer");
			NXGReports.addStep("checklist datetime question answer synced to server", LogAs.PASSED, null);
			
			Assert.assertTrue(ChecklistAnsjson.contains(picklistAns), "checklist picklist answer was not sycned to server in checklist answer");
			NXGReports.addStep("checklist picklist question answer synced to server", LogAs.PASSED, null);

			
			
																	
		//	genericLib.executeSahiScript(GenericLib.getCongigValue(GenericLib.sDataFile, "RS_2389_SAHISCRIPT"),
		//			sTestCaseID);
		//	NXGReports.addStep("Testcase " + sTestCaseID + " PASSED", LogAs.PASSED, null);
		} catch (Exception e) {
			// NXGReports.addStep("Testcase " + sTestCaseID + " FAILED", LogAs.FAILED,new
			// CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			throw e;
		}

	}

	@AfterMethod
	public void tearDownDriver() {
	//	driver.quit();
	}

}

