package com.ge.fsa.tests;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

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

public class Scenario2Test extends BaseLib {

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
		//sWOName="WO-00000695";
		
		
		String sradioQuestion ="checkbox2372018162542";
		String sradioAns = null;		
		String stextQuestion = "text2372018162553";
		String stextAns = "Text Question Answered";
		String snumberQuestion = "number2372018162548";
		String snumberAns ="200";		
		String spicklistQuestion = "picklist2372018162550";
		String spicklistAns = "Answer2372018163046";
		String sdateQuestion = "date2372018162544";
		String sdateAns = null;
		String sdateTimeQuestion = "dateTime2372018162545";
		String sdateTimeAns = null;
		String schecklistStatus = "Completed";
		
	//	GenericLib.setCongigValue(GenericLib.sDataFile, sCaseWOID, sWOName);


			// Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);			
			
			
			
			//toolsPo.configSync(commonsPo);
			toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			
			workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);					
			Thread.sleep(GenericLib.iMedSleep);

			//System.out.println("Going to Enter checklist");
			commonsPo.longPress(checklistPo.geteleChecklistName(sChecklistName));
			Thread.sleep(GenericLib.iLowSleep);
			
			//System.out.println("Entering Text Question Answer");
			checklistPo.geteleChecklistAnswerTextArea(stextQuestion).sendKeys(stextAns);
			
			//System.out.println("Entering Number Question Answer");
			checklistPo.geteleChecklistAnsNumber(snumberQuestion).sendKeys(snumberAns);;
			
			//System.out.println("Selecting Picklist Question Answer");
			commonsPo.pickerWheel(checklistPo.geteleChecklistAnsPicklist(spicklistQuestion), spicklistAns);
			
			//System.out.println("Setting  Date Question Answer");
			checklistPo.geteleChecklistAnsDate(sdateQuestion).click();
			commonsPo.switchContext("Native");
		    commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
		    commonsPo.switchContext("WebView");
		    sdateAns = checklistPo.geteleChecklistAnsDate(sdateQuestion).getAttribute("value");
		    System.out.println("dateANS is "+sdateAns);
		   	    		    
		    
		    SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yy");
	        Date date = parser.parse(sdateAns);
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        String formattedDate = formatter.format(date);
		    System.out.println("formateed date"+formattedDate);    	    	    	    
		      
		    
		    //System.out.println("Setting dateTime Question Answer");
		    checklistPo.geteleChecklistAnsDate(sdateTimeQuestion).click();
		    commonsPo.switchContext("Native");
		    commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
		    commonsPo.switchContext("WebView");
		    sdateTimeAns = checklistPo.geteleChecklistAnsDate(sdateTimeQuestion).getAttribute("value");
		    		    		    
		    SimpleDateFormat parser1 = new SimpleDateFormat("MM/dd/yy hh:mm:ss");
	        Date datetime1 = parser.parse(sdateTimeAns);
	        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	        String formattedDatetime = formatter.format(datetime1);
		    System.out.println("formateed dateTime"+formattedDate);
		    
		
		    
		    //System.out.println("Setting Radio button  Question Answer");
		    checklistPo.geteleChecklistAnsradio(sradioQuestion).click();
		    commonsPo.longPress(checklistPo.geteleChecklistAnsradio(sradioQuestion));
		    sradioAns = checklistPo.geteleChecklistAnsradio(sradioQuestion).getText();
		    
		    
		    // tapping the next button in checklist
		 	commonsPo.tap(checklistPo.geteleNext());

		 	
			// submitting the checklist
			Thread.sleep(GenericLib.iLowSleep);
			commonsPo.tap(checklistPo.eleChecklistSubmit());			
		

			// tapping on the validation sucessfull checklist popup
			commonsPo.longPress(checklistPo.geteleChecklistPopupSubmit());
			System.out.println("finished clicking on submit popup.");

			
			// Tapping on Show Completed Checklists
			//System.out.println("going to tap on show completedchecklists");
			commonsPo.longPress(checklistPo.geteleShowCompletedChecklist());
			//System.out.println("tapped on completed checklist");
			//System.out.println("going to tap on the completedchecklist");
			commonsPo.tap(checklistPo.geteleCompletedChecklistName(sChecklistName));
			//System.out.println("tapped on completed checklist");
			
			
			
			
			//System.out.println("====================Checklist Answers Valdation=========================");
			
			Assert.assertEquals(checklistPo.geteleChecklistAnswerTextArea(stextQuestion).getAttribute("value"), stextAns, "textquestion answered is not displayed");
			NXGReports.addStep("ChecklistText Quesiton Answer validation sucessfull", LogAs.PASSED, null);
		
			
			Assert.assertEquals(checklistPo.geteleChecklistAnsDate(sdateQuestion).getAttribute("value"), sdateAns, "date checklist question answered is not displayed");
			NXGReports.addStep("Checklist Date Quesiton Answer validation sucessfull", LogAs.PASSED, null);
		    
					
			Assert.assertEquals(checklistPo.geteleChecklistAnsDate(sdateTimeQuestion).getAttribute("value"), sdateTimeAns, "datetime checklist question answered is not displayed");
			NXGReports.addStep("Checklist Datetime Quesiton Answer validation sucessfull", LogAs.PASSED, null);
			
		
			Assert.assertEquals(checklistPo.geteleChecklistAnsNumber(snumberQuestion).getAttribute("value"), snumberAns, "number checklist question answered is not displayed");
			NXGReports.addStep("Checklist Number Quesiton Answer validation sucessfull", LogAs.PASSED, null);
			
			
			Assert.assertEquals(checklistPo.geteleChecklistAnsPicklist(spicklistQuestion).getAttribute("value"), spicklistAns, "number checklist question answered is not displayed");
			NXGReports.addStep("Checklist Number Quesiton Answer validation sucessfull", LogAs.PASSED, null);
			
			
			//Navigating back to Work Orders
			checklistPo.navigateBacktoWorkOrder(commonsPo);
			
			
			
			// Sync the Data
			 toolsPo.syncData(commonsPo);		
			 
			
			 									//SERVER SIDE API VALIDATIONS
			 
			 
			System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
			String ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName+"')";
			String ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");	
			Assert.assertTrue(ChecklistQueryval.contains(schecklistStatus),"checklist completed is not synced to server");
			NXGReports.addStep("Checklist Completed status is displayed in Salesforce after sync", LogAs.PASSED, null);
			
			
			
			String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
			Assert.assertTrue(ChecklistAnsjson.contains(stextAns), "checklist text question answer is not synced to server");
			NXGReports.addStep("checklist text question answer is not synced to server", LogAs.PASSED, null);
			
			Assert.assertTrue(ChecklistAnsjson.contains(snumberAns), "checklist number answer sycned to server in checklist answer");
			NXGReports.addStep("checklist number answer sycned to server in checklist answer", LogAs.PASSED, null);
			
			Assert.assertTrue(ChecklistAnsjson.contains(formattedDate), "checklist date answer was not sycned to server in checklist answer");
			NXGReports.addStep("checklist date question answer synced to server", LogAs.PASSED, null);
			
			Assert.assertTrue(ChecklistAnsjson.contains(formattedDatetime), "checklist datetime answer was not sycned to server in checklist answer");
			NXGReports.addStep("checklist datetime question answer synced to server", LogAs.PASSED, null);
			
			Assert.assertTrue(ChecklistAnsjson.contains(spicklistAns), "checklist picklist answer was not sycned to server in checklist answer");
			NXGReports.addStep("checklist picklist question answer synced to server", LogAs.PASSED, null);

			
			Assert.assertTrue(ChecklistAnsjson.contains(sradioAns), "radio picklist answer was not sycned to server in checklist answer");
			NXGReports.addStep("checklist checkbox question answer synced to server", LogAs.PASSED, null);
			
			
																	
		//	genericLib.executeSahiScript(GenericLib.getCongigValue(GenericLib.sDataFile, "RS_2389_SAHISCRIPT"),
		//			sTestCaseID);
		//	NXGReports.addStep("Testcase " + sTestCaseID + " PASSED", LogAs.PASSED, null);
		
	}

	@AfterMethod
	public void tearDownDriver() {
	//	driver.quit();
	}

}

