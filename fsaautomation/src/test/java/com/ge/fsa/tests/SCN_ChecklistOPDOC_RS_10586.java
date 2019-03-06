/*
 *  @author Vinod Tharavath
 *  
 *
 *  Date and Datetime Solutions is not worked on as of now. Will consume once Timezone is completed.
 *  
 */
package com.ge.fsa.tests;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.apache.http.client.utils.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_ChecklistOPDOC_RS_10586 extends BaseLib {

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
	String formattedDate  = null;
	String sformattedDatetime = null;
	String sChecklistOpDocName = null;
	Date dtempDate2;
	Date dTempDate1;
	String sSheetName =null;
	String sWORecordID = null;
	String sTech_Id  = null;
	String sSoqlQueryTech = null;
	String sTechnician_ID = null;
	
	//For SFM Process Sahi Script name
	String sScriptName="Scenario_RS10586_ChecklistOPDOC_DynamicnStaticRes";
	Boolean bProcessCheckResult  = false;
	
	@BeforeMethod
	public void initializeObject() throws Exception { // Initialization of objects

	}

	public void prerequisites() throws Exception
	{
		sSheetName ="RS_10586";
		sTestCaseID = "SCN_ChecklistOPDOC_2_RS-10586";
		sCaseWOID = "Data_SCN_ChecklistOPDOC_2_RS-10586";
		sCaseSahiFile = "backOffice/appium_verifyWorkDetails.sah";
		//sWOJsonData = "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Bangalore\"}";
		
		//Extracting Excel Data
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistName");	
		sChecklistOpDocName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistOpDocName");
		
		//Creation of Work Order
		 sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-01-01\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-01-01T00:00:00.000+0000\",\"SVMXC__NoOfTimesAssigned__c\":\"10\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__Proforma_Invoice__c\":\"Proforma Dynamic Response\"}");
		System.out.println(sWORecordID);
		 sWOName= restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);
		
		//Creating a servicemax event and assigning the work order to it.
		
		 sTech_Id = GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_ID");
		 sSoqlQueryTech = "SELECT+Id+from+SVMXC__Service_Group_Members__c+Where+SVMXC__Salesforce_User__c+=\'"+sTech_Id+"\'";
			restServices.getAccessToken();
			 sTechnician_ID = restServices.restGetSoqlValue(sSoqlQueryTech,"Id");
		//	String sEventName = "AUTO_10586Event";
			//String sEventId = restServices.restCreate("SVMXC__SVMX_Event__c?", "{\"Name\":\""+sEventName+"\", \"SVMXC__Service_Order__c\":\""+sWORecordID+"\", \"SVMXC__Technician__c\":\""+sTechnician_ID+"\", \"SVMXC__StartDateTime__c\":\""+LocalDate.now()+"\", \"SVMXC__EndDateTime__c\": \""+LocalDate.now().plusDays(1L)+"\",\"SVMXC__WhatId__c\":\""+sWORecordID+"\"}");
				bProcessCheckResult =commonsPo.ProcessCheck(restServices, genericLib, sChecklistName, sScriptName, sTestCaseID);		

		//sWOName="WO-00002612";
	}
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10586() throws Exception {
		
		//Static Questions and Answers
		String sCheckboxStaticQ = "Checkbox Static Question";
		String sCheckboxStaticAns = "CheckBoxOne";
		String sDateStaticQ = "Date Static Question";
		String sDateStaticAns = "1/1/2018";
		String sDateTimeStaticQ = "Datetime Static question";
		String sDateTimeStaticAns = "1/1/2018 00:00";
		String sMultiPicklistStaticQ = "MultiPicklist Static Question";
		String sMultiPicklistStaticAns = "MultiOne;MultiTwo";
		String sNumberStaticQ = "Number Static Question";
		String sNumberStaticAns = "10.00";
		String sNumbeStaticAnsOP = "10";
		String sPicklistStaticQ = "Picklist Static Question";
		String sPicklistStaticAns ="PicklOne";
		String sRadioButtonStaticQ = "RadioButton Static Question";
		String sRadioButtonStaticAns = "RadioOne";
		String sTextStaticQ="Text Static Question";
		String sTextStaticAns = "Static Answer";
		
		String sDateDynamicQ= "Dynamic Date From Scheduled Date";
		String sDateDynamicAns ="1/1/2018";		
		String sDateTimeDynamicQ="Dynamic DateTime from ScheduledDateTime";
		String sDateTimeDynamicAns="1/1/2018 00:00";
		String sNumberDynamicQ="Dynamic Number from No Of Times Assigned";
		String sNumberDynamicAns="10";
	
		String sPicklistDynamicQ = "Dynamic Picklist From Billing Type";
		String sPicklistDynamicAns="Contract";
		String sTextDynamicq="Dynamic Text From Proforma Invoice";
		String sTextDynamicAns = "Proforma Dynamic Response";
	
		String sChecklistStatus = "Completed";
	
			prerequisites();
			// Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);	
		    toolsPo.OptionalConfigSync(toolsPo, commonsPo, bProcessCheckResult);

			//toolsPo.configSync(commonsPo);
			toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			
			workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);					
			Thread.sleep(GenericLib.iMedSleep);

			//System.out.println("Going to Enter checklist");
			commonsPo.tap(checklistPo.geteleChecklistName(sChecklistName));
			Thread.sleep(GenericLib.iLowSleep);
			
			String sNumberStaticAnsApp = checklistPo.geteleChecklistAnsNumber(sNumberStaticQ).getAttribute("value");
			Assert.assertTrue(sNumberStaticAnsApp.equals(sNumberStaticAns), "Static response with Number datatype failed");
			ExtentManager.logger.log(Status.PASS,"Static response with Number datatype Passed");
			
			//Date and Datetime validations
			/*String sDateStaticAnsApp  = checklistPo.geteleChecklistAnsDate(sDateStaticQ).getAttribute("value");
			System.out.println(sDateStaticAns);
			Assert.assertTrue(sDateStaticAnsApp.equals(sDateStaticAns), "Static response with Date datatype failed");
			ExtentManager.logger.log(Status.PASS,"Static response with Date datatype Passed");*/
			
			/*String sDateTimeStaticAnsApp  = checklistPo.geteleChecklistAnsDate(sDateTimeStaticQ).getAttribute("value");
			System.out.println(sDateTimeStaticQ);
			Assert.assertTrue(sDateTimeStaticAnsApp.equals(sDateTimeStaticAns), "Static response with DateTime datatype failed");
			ExtentManager.logger.log(Status.PASS,"Static response with DateTime datatype Passed");*/
			
			String sMultiPicklistStaticAnsApp = checklistPo.geteleChecklistAnsNumber(sMultiPicklistStaticQ).getAttribute("value");
			Assert.assertTrue(sMultiPicklistStaticAnsApp.equals(sMultiPicklistStaticAns), "Static response with MultiSelect datatype failed");
			ExtentManager.logger.log(Status.PASS,"Static response with MultiSelect datatype Passed");
		
			String sPicklistStaticAnsApp = checklistPo.geteleChecklistAnsPicklist(sPicklistStaticQ).getAttribute("value");
			Assert.assertTrue(sPicklistStaticAnsApp.equals(sPicklistStaticAns),"Static response with picklist failed");
			ExtentManager.logger.log(Status.PASS, "Static response with picklist datatype Passed");
		
			String sTextStaticAnsApp=checklistPo.geteleChecklistAnswerTextArea(sTextStaticQ).getAttribute("value");
			System.out.println("sTextStaticAnsApp"+sTextStaticAnsApp);
			Assert.assertTrue(sTextStaticAnsApp.equals(sTextStaticAns),"Static response with picklist failed");
			ExtentManager.logger.log(Status.PASS, "Static response with text datatype Passed");
			
			//Checkbox datatype validation
			String val = checklistPo.geteleChecklistCheckboxValue("CheckBoxOne").getAttribute("checked");
			Assert.assertTrue(val.equals("true"),"Static response with Checkbox failed");
			ExtentManager.logger.log(Status.PASS, "checkbox Static response with checkbox datatype Passed");

			
			//Radio Button validation
			String valv = checklistPo.geteleChecklistRadioButtonValue("RadioOne").getAttribute("checked");
			System.out.println("radioOne"+valv);
			Assert.assertTrue(valv.equals("true"),"Static response with Radio failed");
			ExtentManager.logger.log(Status.PASS, "RadioButton Static response with checkbox datatype Passed");

			
			//String sRadioButtonStaticAnsApp = checklistPo.geteleChecklistAnsradio(sRadioButtonStaticQ).getAttribute("type");
			//Assert.assertTrue(sRadioButtonStaticAnsApp.equals(sRadioButtonStaticAns),"Static response with picklist failed");
			//System.out.println("sRadioButtonStaticAnsApp type"+sRadioButtonStaticAnsApp);
			//ExtentManager.logger.log(Status.PASS, "Static response with radio datatype Passed");
			
			//-----------------DYNAMIC RESPONSE VALIDATIONS	in Checklist--------------
			
			String sNumberDynamicAnsApp = checklistPo.geteleChecklistAnsNumber(sNumberDynamicQ).getAttribute("value");
			Assert.assertTrue(sNumberDynamicAnsApp.equals(sNumberDynamicAns), "Dynamic response with Number datatype failed");
			ExtentManager.logger.log(Status.PASS,"dynamic response with Number datatype Passed");
	
			/*String sDateDynamicAnsApp  = checklistPo.geteleChecklistAnsDate(sDateDynamicQ).getAttribute("value");
			System.out.println(sDateDynamicAns);
			Assert.assertTrue(sDateDynamicAnsApp.equals(sDateDynamicAns), "Dynamic response with Date datatype failed");
			ExtentManager.logger.log(Status.PASS,"Dynamic response with Date datatype Passed");*/
		
			/*String sDateTimeDynamicAnsApp  = checklistPo.geteleChecklistAnsDate(sDateTimeDynamicQ).getAttribute("value");
			System.out.println(sDateTimeDynamicQ);
			Assert.assertTrue(sDateTimeDynamicAnsApp.equals(sDateTimeDynamicAns), "Dynamic response with DateTime datatype failed");
			ExtentManager.logger.log(Status.PASS,"Dynamic response with DateTime datatype Passed");*/
			
			String sPicklistDynamicAnsApp = checklistPo.geteleChecklistAnsPicklist(sPicklistDynamicQ).getAttribute("value");
			Assert.assertTrue(sPicklistDynamicAnsApp.equals(sPicklistDynamicAns),"Dynamic response with picklist failed");
			ExtentManager.logger.log(Status.PASS, "Dynamic response with picklist datatype Passed");	

			String sTextDynamicAnsApp=checklistPo.geteleChecklistAnswerTextArea(sTextDynamicq).getAttribute("value");
			System.out.println("sTextDynamicAnsApp"+sTextDynamicAnsApp);
			Assert.assertTrue(sTextDynamicAnsApp.equals(sTextDynamicAns),"Dynamic response with txt area failed");
			ExtentManager.logger.log(Status.PASS, "Dynamic response with textarea datatype Passed");		
	
		    // tapping the next button in checklist
		 	commonsPo.tap(checklistPo.geteleNext());
	 	
			// submitting the checklist
			Thread.sleep(GenericLib.iLowSleep);
			
			//try{driver.findElement(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")).click();}catch(Exception e) {}
			commonsPo.clickAllowPopUp();
			commonsPo.switchContext("WebView");
			commonsPo.tap(checklistPo.eleChecklistSubmit());	
			
			//try{driver.findElement(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")).click();}catch(Exception e) {}

			// tapping on the validation sucessfull checklist popup
			commonsPo.tap(checklistPo.geteleChecklistPopupSubmit());
			System.out.println("finished clicking on submit popup.");
			
			// Tapping on Show Completed Checklists
			//System.out.println("going to tap on show completedchecklists");
			commonsPo.tap(checklistPo.geteleShowCompletedChecklist());
			//System.out.println("tapped on completed checklist");
			commonsPo.tap(checklistPo.geteleCompletedChecklistName(sChecklistName));
			Thread.sleep(genericLib.iLowSleep);
			//System.out.println("tapped on completed checklist");
			
			//-------------------OPDOC------------------------------ 
			
			//Navigating back to work Orders
			checklistPo.navigateBacktoWorkOrder(commonsPo);

			//Navigating to checklistOPDOC process
			checklistPo.validateChecklistServiceReport(commonsPo, workOrderPo, sChecklistOpDocName,sWOName );
		  	checklistPo.geteleChecklistOPDOCRow();
		
		  	//Validating is checklist status in opdoc is completed and also if checklist name is displayed.	  	  	
		  	 Assert.assertTrue(checklistPo.geteleChecklistOPDOCRow().getText().toString().contains(sChecklistStatus), "Checklist status is not displayed as completed.");
			 ExtentManager.logger.log(Status.PASS,"checklist status completed is displayed in OPDOC");
			 Assert.assertTrue(checklistPo.geteleChecklistOPDOCRow().getText().toString().contains(sChecklistName), "Checklist Name is displayed");
			 ExtentManager.logger.log(Status.PASS,"checklist Name is displayed in OPDOC");
		 		 
			//validating if it picks the checklist Question and answer.
			 checklistPo.geteleChecklistAnswerOPDOCtbl();
			 System.out.println( checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString());			 
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sTextDynamicAns), "Couldnt get the WorkOrder no populated through dynamic response");	 	
			 ExtentManager.logger.log(Status.PASS,"WorkORder No populated through dynamic response displayed in OPDOC");
			 
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sCheckboxStaticQ), "Couldnt find the checklist question in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Checkbox StaticQuestion Validation passed");
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sCheckboxStaticAns), "Couldnt find the checklist Answer in OPDOC");	
			 ExtentManager.logger.log(Status.PASS,"Checkbox StaticAnswer Validation passed");
		 			
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sDateStaticQ), "Couldnt find the date checklist question in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Date StaticQuestion Validation passed");
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sDateStaticAns), "Couldnt find the date checklist Answer in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Date Static Answer Validation passed");

			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sDateTimeStaticQ), "Couldnt find the dattimechecklist question in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Date StaticQuestion Validation passed");
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sDateTimeStaticAns), "Couldnt find the checklist Answer in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"DateTime Static Answer Validation passed");	 
			 
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sMultiPicklistStaticQ), "Couldnt find the multipicklist checklist question in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"MultiPicklist StaticQuestion Validation passed");
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sMultiPicklistStaticAns), "Couldnt find the multipicklist checklist Answer in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"MultiPicklist Static Answer Validation passed");
			 
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sNumberStaticQ), "Couldnt find the number checklist question in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Number StaticQuestion Validation passed");
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sNumbeStaticAnsOP), "Couldnt find the number checklist Answer in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Number Static Answer Validation passed");
			 
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sPicklistStaticQ), "Couldnt find the picklist checklist question in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Picklist StaticQuestion Validation passed");
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sPicklistStaticAns), "Couldnt find the picklist checklist Answer in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Picklist Static Answer Validation passed");
			 
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sRadioButtonStaticQ), "Couldnt find the radio checklist question in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Radio StaticQuestion Validation passed");
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sRadioButtonStaticAns), "Couldnt find the radio checklist Answer in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Radiobutton Static Answer Validation passed");
			 
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sTextStaticQ), "Couldnt find the text checklist question in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Text StaticQuestion Validation passed");
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sTextStaticAns), "Couldnt find the text checklist Answer in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Text Static Answer Validation passed");
			 
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sDateDynamicQ), "Couldnt find the Dynamic checklist date question in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Date Dynamic Question Validation passed");
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sDateDynamicAns), "Couldnt find the Dynamic checklist dateAnswer in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Date Dynamic Response Answer Validation passed");

			/* Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sDateTimeDynamicQ), "Couldnt find the Dynamic checklist question in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Date StaticQuestion Validation passed");
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sDateTimeDynamicAns), "Couldnt find the Dynamic checklist Answer in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"DateTime Dynamic Response Answer Validation passed");*/
			 
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sNumberDynamicQ), "Couldnt find the Dynamic checklist number question in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Number Dynamic Question Validation passed");
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sNumberDynamicAns), "Couldnt find the Dynamic checklist number Answer in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Number Dynamic Response Answer Validation passed");
			 		 
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sPicklistDynamicQ), "Couldnt find the Dynamic checklist picklist question in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Picklist Dynamic Question Validation passed");
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sPicklistDynamicAns), "Couldnt find the Dynamic checklist picklist Answer in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Picklist Dynamic Response Answer Validation passed");
			 
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sTextDynamicq), "Couldnt find the Dynamic checklist text uestion in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Text Dynamic Question Validation passed");
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sTextDynamicAns), "Couldnt find the Dynamic checklist text Answer in OPDOC");
			 ExtentManager.logger.log(Status.PASS,"Text Dynamic Response Answer Validation passed");
			 	 			 
			// workOrderPo.getEleDoneLnk().click();
			
			commonsPo.tap(workOrderPo.getEleDoneLnk());
			Thread.sleep(GenericLib.iHighSleep);
			Thread.sleep(GenericLib.i30SecSleep);
			((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
			Thread.sleep(GenericLib.i30SecSleep);
			Thread.sleep(GenericLib.i30SecSleep);
			((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
			Thread.sleep(GenericLib.i30SecSleep);
			
			//Navigation back to Work Order after Service Report
			Assert.assertTrue(checklistPo.getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
			ExtentManager.logger.log(Status.PASS,"Creation of Checklist OPDOC passed");

			Thread.sleep(GenericLib.iVHighSleep);

			// String ans= workOrderPo.geteleProblemDescriptionlbl().getText();
			// System.out.println(ans);
			commonsPo.tap(calendarPO.getEleCalendarIcn());
			 toolsPo.syncData(commonsPo);
			 Thread.sleep(GenericLib.iLowSleep);
			 
			/* commonsPo.tap(exploreSearchPo.getEleExploreIcn());
			 Thread.sleep(genericLib.iLowSleep);
			 commonsPo.tap(workOrderPo.geteleAttachedDocumentLeftPane());
			 commonsPo.tap(workOrderPo.getEleAttachedDocument(sChecklistOpDocName),20,20);
			// commonsPo.tap(workOrderPo.getEleAttachedDocument(sChecklistOpDocName));
			// workOrderPo.getEleAttachedDocument(sChecklistOpDocName).click();
			// commonsPo.tap(workOrderPo.getEleAttachedDocument(sChecklistOpDocName),5,5);
			 Thread.sleep(genericLib.iLowSleep);
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sTextDynamicAns), "Couldnt get the WorkOrder no populated through dynamic response");	 	
			 ExtentManager.logger.log(Status.PASS,"After Sync Dynamic Text response validated sucessfully");
			 */
			 
			 //Validation in Server if OPDOC is synced sucessfully.
	
				Thread.sleep(GenericLib.iHighSleep);
				Thread.sleep(GenericLib.iHighSleep);
				Thread.sleep(GenericLib.i30SecSleep);

				System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
				String ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName+"')";
				String ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");	
				Assert.assertTrue(ChecklistQueryval.contains(sChecklistStatus),"checklist being updated is not synced to server");
				String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
				ExtentManager.logger.log(Status.PASS,"Checklist Completed status is displayed in Salesforce after sync");

				Assert.assertTrue(ChecklistAnsjson.contains(sTextDynamicAns), "dynamicrepsonse text ans was not sycned to server in checklist answer");
				Assert.assertTrue(ChecklistAnsjson.contains(sNumberDynamicAns), "dynamicrepsonse number ans was not sycned to server in checklist answer");
				Assert.assertTrue(ChecklistAnsjson.contains(sPicklistDynamicAns), "dynamicrepsonse Picklist ans was not sycned to server in checklist answer");
				Assert.assertTrue(ChecklistAnsjson.contains(sTextStaticAns), "static response text ans was not sycned to server in checklist answer");
				Assert.assertTrue(ChecklistAnsjson.contains(sNumbeStaticAnsOP), "static number ans was not sycned to server in checklist answer");
				Assert.assertTrue(ChecklistAnsjson.contains(sPicklistStaticAns), "static picklist ans was not sycned to server in checklist answer");

				
				
	}
}

