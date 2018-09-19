/*
 *  @author Vinod Tharavath
 *  
 *  PENDING SAHI SCRIPTS
 *  Checkbox, radiobox Validation in Appium
 *  Date and Datetime Solutions
 *  OPDOC validation with all static and dynamic values... i have validated few.
 */
package com.ge.fsa.tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
	

	@BeforeMethod
	public void initializeObject() throws Exception { // Initialization of objects

	}

	
	@Test(enabled = true)
	public void scenario2_checklist() throws Exception {
		
		sTestCaseID = "SCN_ChecklistOPDOC_2_RS-10586";
		sCaseWOID = "Data_SCN_ChecklistOPDOC_2_RS-10586";
		sCaseSahiFile = "backOffice/appium_verifyWorkDetails.sah";
		//sWOJsonData = "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Bangalore\"}";
		
		//Extracting Excel Data
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID, "ChecklistName");	
		sChecklistOpDocName = GenericLib.getExcelData(sTestCaseID, "ChecklistOpDocName");

		// Creation of dynamic Work Order
	/*	restServices.getAccessToken();
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);*/
		
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-01-01\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-01-01T00:00:00.000+0000\",\"SVMXC__NoOfTimesAssigned__c\":\"10\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__Proforma_Invoice__c\":\"Proforma Dynamic Response\"}");
		System.out.println(sWORecordID);
		String sWOName= restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);	
		//sWOName="WO-00002451";
		//Static Questions and Answers
		String sCheckboxStaticQ = "Checkbox Static Question";
		String sCheckboxStaticAns = "CheckBoxOne";
		String sDateStaticQ = "Date Static Question";
		String sDateStaticAns = "1/1/18";
		String sDateTimeStaticQ = "Datetime Static question";
		String sDateTimeStaticAns = "1/1/18 00:00";
		String sMultiPicklistStaticQ = "MultiPicklist Static Question";
		String sMultiPicklistStaticAns = "MultiOne;MultiTwo";
		String sNumberStaticQ = "Number Static Question";
		String sNumberStaticAns = "10.00";
		String sPicklistStaticQ = "Picklist Static Question";
		String sPicklistStaticAns ="PicklOne";
		String sRadioButtonStaticQ = "RadioButton Static Question";
		String sRadioButtonStaticAns = "RadioOne";
		String sTextStaticQ="Text Static Question";
		String sTextStaticAns = "Static Answer";
		
		String sDateDynamicQ= "Dynamic Date From Scheduled Date";
		String sDateDynamicAns ="1/1/18";		
		String sDateTimeDynamicQ="Dynamic DateTime from ScheduledDateTime";
		String sDateTimeDynamicAns="1/1/18 00:00";
		String sNumberDynamicQ="Dynamic Number from No Of Times Assigned";
		String sNumberDynamicAns="10";
		String sPicklistDynamicQ = "Dynamic Picklist From Billing Type";
		String sPicklistDynamicAns="Contract";
		String sTextDynamicq="Dynamic Text From Proforma Invoice";
		String sTextDynamicAns = "Proforma Dynamic Response";
		
		
		String sChecklistStatus = "Completed";
	
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
			
			
			String sNumberStaticAnsApp = checklistPo.geteleChecklistAnsNumber(sNumberStaticQ).getAttribute("value");
			Assert.assertTrue(sNumberStaticAnsApp.equals(sNumberStaticAns), "Static response with Number datatype failed");
			ExtentManager.logger.log(Status.PASS,"Static response with Number datatype Passed");
			
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

			
			//DYNAMIC RESPONSE VALIDATIONS	
			
			
			String sNumberDynamicAnsApp = checklistPo.geteleChecklistAnsNumber(sNumberDynamicQ).getAttribute("value");
			Assert.assertTrue(sNumberStaticAnsApp.equals(sNumberStaticAns), "Dynamic response with Number datatype failed");
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
			Assert.assertTrue(sPicklistDynamicAnsApp.equals(sPicklistDynamicAns),"Static response with picklist failed");
			ExtentManager.logger.log(Status.PASS, "Static response with picklist datatype Passed");
		/*	
			String sRadioButtonStaticAnsApp = checklistPo.geteleChecklistAnsradio(sRadioButtonStaticQ).getAttribute("type");
			Assert.assertTrue(sRadioButtonStaticAnsApp.equals(sRadioButtonStaticAns),"Static response with picklist failed");
			System.out.println("sRadioButtonStaticAnsApp type"+sRadioButtonStaticAnsApp);
			ExtentManager.logger.log(Status.PASS, "Static response with radio datatype Passed");*/

			String sTextDynamicAnsApp=checklistPo.geteleChecklistAnswerTextArea(sTextDynamicq).getAttribute("value");
			System.out.println("sTextDynamicAnsApp"+sTextDynamicAnsApp);
			Assert.assertTrue(sTextDynamicAnsApp.equals(sTextDynamicAns),"Static response with picklist failed");
			ExtentManager.logger.log(Status.PASS, "Static response with text datatype Passed");
			
	
		    // tapping the next button in checklist
		 	commonsPo.tap(checklistPo.geteleNext());
	 	
			// submitting the checklist
			Thread.sleep(GenericLib.iLowSleep);
			try{driver.findElement(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")).click();}catch(Exception e) {}

			commonsPo.tap(checklistPo.eleChecklistSubmit());	
			
			try{driver.findElement(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")).click();}catch(Exception e) {}

			// tapping on the validation sucessfull checklist popup
			commonsPo.longPress(checklistPo.geteleChecklistPopupSubmit());
			System.out.println("finished clicking on submit popup.");

			
			// Tapping on Show Completed Checklists
			//System.out.println("going to tap on show completedchecklists");
			commonsPo.longPress(checklistPo.geteleShowCompletedChecklist());
			//System.out.println("tapped on completed checklist");
			//System.out.println("going to tap on the completedchecklist");
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
			 Assert.assertTrue(checklistPo.geteleChecklistOPDOCRow().getText().toString().contains(sChecklistName), "Checklist Name is displayed");
					
			 		 
			//validating if it picks the checklist Question and answer.
			 checklistPo.geteleChecklistAnswerOPDOCtbl();
			 System.out.println( checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString());
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sTextDynamicq), "Couldnt find the checklist question in OPDOC");	
			 ExtentManager.logger.log(Status.PASS,"Found Dynamic REsponse Text question in OPDOC");

			 
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sCheckboxStaticQ), "Couldnt find the checklist question in OPDOC");	
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sCheckboxStaticAns), "Couldnt find the checklist question in OPDOC");	

			 
			 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sTextDynamicAns), "Couldnt get the WorkOrder no populated through dynamic response");	 	
			ExtentManager.logger.log(Status.PASS,"WorkORder No populated through dynamic response displayed in OPDOC");

			workOrderPo.getEleDoneLnk().click();
			
			commonsPo.tap(workOrderPo.getEleDoneLnk());
			Thread.sleep(GenericLib.iHighSleep);
			((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
			Thread.sleep(GenericLib.iHighSleep);
			((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
			Thread.sleep(GenericLib.iHighSleep);
			
			//Navigation back to Work Order after Service Report
			Assert.assertTrue(checklistPo.getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
			//NXGReports.addStep("Creation of Checklist OPDOC passed", LogAs.PASSED, null);	
			ExtentManager.logger.log(Status.PASS,"Creation of Checklist OPDOC passed");

			Thread.sleep(GenericLib.iLowSleep);
			// String ans= workOrderPo.geteleProblemDescriptionlbl().getText();
			// System.out.println(ans);
			
			 toolsPo.syncData(commonsPo);
			
			
			
	}
}

