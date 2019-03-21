/*
 *  @author Vinod Tharavath
 *   
 *   */
package com.ge.fsa.tests.tablet;

import static org.testng.Assert.assertNotNull;


import java.time.Duration;
import java.util.Set;
import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.WorkOrderPO;

public class SCN_ChecklistOPDOC_RS_10585 extends BaseLib {
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sExploreChildSearchTxt = null;
	String sOrderStatusVal = null;
	String sEditProcessName = null;
	String sSection1Name="Section One";
	String sSection2Name="Section Two";
	String sSection3Name="Section Three";
	String sChecklistOpDocName = null;
	String sWORecordID = null;
	String sSoqlqueryAttachment = null;
	String sAttachmentIDAfter = null;
	String ChecklistQueryval = null;
	String ChecklistQuery = null;
	// checklist q's set--;
	
	String sSection1Q1 = "What is the Work Order Number?";
	String sSection1Q2 = "Number Should not be greater than 100";
	String sSection2Q1 = "Section Two Question One";
	String sSection3q1 = "Section Three Question One";
	String sSection3q1Ans = "ok";	
	String sNumberSectionJumpAns = "19";
	String snumberwithoutjump = "100";

	// For ServerSide Validations
	String schecklistStatusInProgress = "In Process";
	String schecklistStatus = "Completed";	
	String sSheetName =null;
	String sSoqlquerypdf =  null;
	String sSoqlquerypdf1 = null;
	
	//For SFM Process Sahi Script name
	String sScriptName="Scenario_RS10585_ChecklistOPDOC_SkippedSections";
	Boolean bProcessCheckResult  = false;
	public void prerequisites() throws Exception
	{
		sSheetName ="RS_10585";
		System.out.println("RS 10585 In progress checklists with section skip and OPDOC");		
		sTestCaseID = "SCN_ChecklistOPDOC_1_RS-10585";
		sCaseWOID = "Data_SCN_ChecklistOPDOC_1_RS-10585";

		// Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID,sSheetName, "EditProcessName");
		sChecklistOpDocName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistOpDocName");
		// Rest to Create Workorder - Work Order -
		
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName= restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);
		
		
		bProcessCheckResult =commonsPo.ProcessCheck(restServices, genericLib, sChecklistName, sScriptName, sTestCaseID);		


		//sWOName1 = "WO-00001615";
	}
	
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10585() throws Exception {
	
		prerequisites();
		// Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
	    toolsPo.OptionalConfigSync(toolsPo, commonsPo, bProcessCheckResult);

		// Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		//toolsPo.configSync(commonsPo);

		// Navigation to WO
		workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);

		// Navigate to Field Service process
		workOrderPo.selectAction(commonsPo, sFieldServiceName);

		// Navigating to the checklist
		commonsPo.tap(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(GenericLib.iLowSleep);
		
		checklistPo.geteleChecklistAnsNumber(sSection1Q2).sendKeys(sNumberSectionJumpAns);
		commonsPo.tap(checklistPo.geteleNext());	
		Assert.assertTrue(checklistPo.geteleChecklistSectionNametab(sSection3Name).isDisplayed(), "Exit Criteria in Checklist Failed");	
		ExtentManager.logger.log(Status.PASS,"Exit Criteria for section passed");
		
		try {
			Assert.assertTrue(checklistPo.geteleChecklistSectionNametab(sSection2Name).isDisplayed(), "Exit Criteria in Checklist Failed");
		} catch (Exception e) {
			// TODO: handle exception
			ExtentManager.logger.log(Status.PASS,"Section two is not displayed");
		}
		
		commonsPo.tap(checklistPo.geteleBacktoChecklistslnk());	
		commonsPo.tap(checklistPo.geteleSavePopUp());
		Thread.sleep(GenericLib.iMedSleep);
		System.out.println("checklistname is"+sChecklistName);
		String wAppChecklistStatus = checklistPo.getEleChecklistStatusLbl(sChecklistName).getText();				
		System.out.println("printing wappcheckliststatus"+wAppChecklistStatus);	
		Assert.assertEquals(wAppChecklistStatus,"In Progress", "checklist is in In Progress Status");		
		ExtentManager.logger.log(Status.PASS,"In Progress Checklist is displayed");		
		commonsPo.tap(checklistPo.geteleBacktoWorkOrderlnk());
	
		toolsPo.syncData(commonsPo);
		Thread.sleep(genericLib.iLowSleep);
		commonsPo.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());
		
		//Validating in the server if checklist status is synced as inprogress
		System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
		ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName+"')";
		ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");	
		Assert.assertTrue(ChecklistQueryval.contains(schecklistStatusInProgress),"checklist in progress is not synced to server");
		ExtentManager.logger.log(Status.PASS,"Checklist In Progress status is displayed in Salesforce after sync");
	
		// Navigate to Field Service process
		workOrderPo.selectAction(commonsPo, sFieldServiceName);

		// Navigating to the checklist
		commonsPo.tap(checklistPo.geteleChecklistName(sChecklistName));		
		checklistPo.geteleChecklistAnswerTextArea(sSection3q1).sendKeys("ok");

		commonsPo.tap(checklistPo.geteleSectionNextBtn(3));
		// submitting the checklist
		Thread.sleep(GenericLib.iLowSleep);
		
		
		commonsPo.clickAllowPopUp();
		commonsPo.switchContext("WebView");
		commonsPo.tap(checklistPo.eleChecklistSubmit());		
	//	try{driver.findElement(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")).click();}catch(Exception e) {}
		// tapping on the validation successful checklist popup
		commonsPo.tap(checklistPo.geteleChecklistPopupSubmit());
		System.out.println("finished clicking on checklist submit popup.");
	
		//Navigating back to work Orders
		commonsPo.tap(checklistPo.geteleBacktoWorkOrderlnk());
		
		toolsPo.syncData(commonsPo);
		Thread.sleep(genericLib.iMedSleep);
		
		//Validating in the server if checklist status is synced as completed
		System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
		 ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName+"')";
		 ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");	
		Assert.assertTrue(ChecklistQueryval.contains(schecklistStatus),"checklist completed is not synced to server");
		ExtentManager.logger.log(Status.PASS,"Checklist Completed status is displayed in Salesforce after sync");
		
		String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
		Assert.assertTrue(ChecklistAnsjson.contains(sNumberSectionJumpAns), "Answer is synced to server");
		ExtentManager.logger.log(Status.PASS,"Section One Answer is synced to server");
	
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());		
		// Navigation to WO
		workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);

		// Navigate to Field Service process
		//workOrderPo.selectAction(commonsPo, sChecklistOpDocName);
		Thread.sleep(GenericLib.iLowSleep);
		//Navigating to checklistOPDOC process
		checklistPo.validateChecklistServiceReport(commonsPo, workOrderPo, sChecklistOpDocName,sWOName);
	  	checklistPo.geteleChecklistOPDOCRow();
	
	  	//Validating is checklist status in opdoc is completed and also if checklist name is displayed.	  	  	
	  	 Assert.assertTrue(checklistPo.geteleChecklistOPDOCRow().getText().toString().contains(schecklistStatus), "Checklist status is not displayed as completed.");
		 ExtentManager.logger.log(Status.PASS,"checklist status completed is displayed in OPDOC");
		 Assert.assertTrue(checklistPo.geteleChecklistOPDOCRow().getText().toString().contains(sChecklistName), "Checklist Name is displayed");
		 ExtentManager.logger.log(Status.PASS,"checklist Name is displayed in OPDOC");
		 Assert.assertFalse(checklistPo.geteleChecklistOPDOCRow().getText().toString().contains(sSection1Q2), "Section 1 Question two is not displayed in OPDOC");
		 ExtentManager.logger.log(Status.PASS,"Section 1 question two is displayed in OPDOC");
		 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sNumberSectionJumpAns), "Section 1q2 answer is not displayed in OPDOC");
		 ExtentManager.logger.log(Status.PASS,"Section 1 question two answer is displayed in OPDOC");
		 Assert.assertFalse(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sSection2Q1), "Section 2 answer is not displayed in OPDOC");
		 ExtentManager.logger.log(Status.PASS,"Section two question two is not displayed in the OPDOC as it is a skipped section");

		//workOrderPo.getEleDoneLnk().click();
		commonsPo.tap(workOrderPo.getEleDoneLnk());
		commonsPo.custRotateScreenPortrait();
		//Navigation back to Work Order after Service Report
		Assert.assertTrue(checklistPo.getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		ExtentManager.logger.log(Status.PASS,"Creation of Checklist OPDOC passed");

		
		checklistPo.validateChecklistServiceReport(commonsPo, workOrderPo, "Auto_RS10585_ChecklistOPDOC_InProgOP2",sWOName);
	  	checklistPo.geteleChecklistOPDOCRow();
	  	Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sSection2Q1), "Section 2 answer is not displayed in OPDOC");
		ExtentManager.logger.log(Status.PASS,"Section two question two is displayed in the OPDOC as it is a skipped section- validating include skipped section");
	//	commonsPo.tap(workOrderPo.getEleCancelLink());
		commonsPo.tap(workOrderPo.geteleOPDOCCancelLnk());
		Thread.sleep(GenericLib.i30SecSleep);
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		Thread.sleep(GenericLib.iHighSleep);
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		Thread.sleep(GenericLib.i30SecSleep);
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		Thread.sleep(GenericLib.iMedSleep);
		
		// Verifying if checklistopdoc is synced to server
	  	System.out.println("Validating if OPDOC attachment is syned to server.");
	  	Thread.sleep(GenericLib.i30SecSleep);
	  	Thread.sleep(GenericLib.iMedSleep);
		sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
		sSoqlquerypdf = "Select+Name+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
		sSoqlquerypdf1 = restServices.restGetSoqlValue(sSoqlquerypdf, "Name");	

		Assert.assertTrue(sSoqlquerypdf1.contains("pdf"),"fileformat stored is not pdf");
		 ExtentManager.logger.log(Status.PASS,"file is in pdf format in server");

		restServices.getAccessToken();
		sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlqueryAttachment, "Id");	
		assertNotNull(sAttachmentIDAfter); 
		 ExtentManager.logger.log(Status.PASS,"OPDOC is synced to Server");
		
	}

}
