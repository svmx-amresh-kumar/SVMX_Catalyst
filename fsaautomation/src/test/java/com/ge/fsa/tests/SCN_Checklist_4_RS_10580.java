/*
 *  @author Vinod Tharavath
 *  SCN_Checklist_4_RS-10580 Section level Navigation entry Exit Criteria
 */
package com.ge.fsa.tests;

import org.json.JSONArray;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.WorkOrderPO;

public class SCN_Checklist_4_RS_10580 extends BaseLib {
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sWOName1 = null;
	String sWORecordID = null;
	String sWOName3 = null;
	
	String sExploreChildSearchTxt = null;
	String sOrderStatusVal = null;
	String sEditProcessName = null;
	String sSection1Name="Section One";
	String sSection2Name="Section Two";
	String sSection3Name="Section Three";
	String sSection4Name="Section Four";
	// checklist q's set--;
	
	String sDateq = "Date should not be Today";
	String sNumberq = "Number Should not be greater than 100";
	//String sNumberq1 = "Number cannot be greater than 100";
	String sConfirmationDVRq = "";
	String sSectionThreeErrorQ = "Section Three Error";
	String sSectionThreeErrorAns1 = "102";
	String sSectionThreeQ1ValidAns1 = "20";
	String sSectionThreeConfirmationQ = "Section Three Warning";
	String sSectionThreeConfirmationAns = "10";	
	String sSectionTwoQ1 = "Section Two Question One";
	String sSectionOneQ1 = "Section One Question One";
	String sSectionTwoQAns = "jump";
	String sSectionOneQAns= "Jump";
	String sSectionOneQAnsNoJ= "NOJump";

	
	String sSectionFourQ1 = "Section Four Question One";
	String sSectionFourQ1Ans = "Section Four Question Answer 1";
	
	String sDateAns = null;
	String sDateTimeAns = null;
	String sNumberSectionJumpAns = "19";
	String sNumberDVRAns="102";
	String sSectionOneJump2 = "50";
	String snumberwithoutjump = "100";
	String sNumberSectionwithoutjump2 = "25";

	// For ServerSide Validations
	String schecklistStatus = "Completed";	
	
	//Sou 
	
	String sBillingTpeSOU =null;
	String sProblemDescriptionSOU = null;
	
	
	//SOU ans
	String sBillingTpeSOUServer ="Paid";
	String sProblemDescriptionSOUServer = "Souce Object Update Sucess";
	String sSheetName =null;
	
	String sScriptName="Scenario_RS10580_Checklist_Sections";
	Boolean bProcessCheckResult  = false;
	
	
	public void prerequisites() throws Exception
	{
		sSheetName ="RS_10580";
		System.out.println("SCN_RS10580_Checklist_Sections");
		sTestCaseID = "SCN_Checklist_4_RS-10580_Sections";
		sCaseWOID = "Data_SCN_Checklist_4_RS-10580_Sections";

		// Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID,sSheetName, "EditProcessName");
	

		// Rest to Create Workorder -Standard Work Order - Satisfies Qualification Criteria and Checklist Entry Criteria
		
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName1= restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName1);	
		
		
		// Work Order for checklist entry Criteria check.
				 sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"Low\"}");
				System.out.println(sWORecordID);
				sWOName3 = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
				System.out.println("WO no =" + sWOName3);
		
		bProcessCheckResult =commonsPo.ProcessCheck(restServices, genericLib, sChecklistName, sScriptName, sTestCaseID);		

				
		//sWOName1 = "WO-00001615";
		
	}
	//@Test()
	@Test(retryAnalyzer=Retry.class)
	public void RS_10580() throws Exception {
		
		prerequisites();
		
		// Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);

	    toolsPo.OptionalConfigSync(toolsPo, commonsPo, bProcessCheckResult);

		// Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		// toolsPo.configSync(commonsPo);

		// Navigation to WO
		workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName1);

		// Navigate to Field Service process
		workOrderPo.selectAction(commonsPo, sFieldServiceName);

		// Navigating to the checklist
		commonsPo.tap(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(GenericLib.iLowSleep);
		
		checklistPo.geteleChecklistAnsNumber(sNumberq).sendKeys(sNumberDVRAns);
		// tapping next button
		commonsPo.tap(checklistPo.geteleSectionNextBtn(1));					 
		Assert.assertTrue(checklistPo.geteleChecklistDVRNoGreaterthan100txt().isDisplayed(), "DataValidation rule failed for number error");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for Number Passed");
		checklistPo.geteleChecklistAnsNumber(sNumberq).clear();
		checklistPo.geteleChecklistAnsNumber(sNumberq).sendKeys(sNumberSectionJumpAns);
		commonsPo.tap(checklistPo.geteleSectionNextBtn(1));	
		commonsPo.clickAllowPopUp();
		commonsPo.switchContext("WebView");
		//try{driver.findElement(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")).click();}catch(Exception e) {}
		Assert.assertFalse(checklistPo.geteleChecklistDVRNoGreaterthan100txt().isDisplayed(), "DataValidation confirmation failed");	 	
		Assert.assertTrue(checklistPo.geteleChecklistSectionNametab(sSection3Name).isDisplayed(), "Exit Criteria in Checklist Failed");	 	
		ExtentManager.logger.log(Status.PASS,"Exit Criteria for section passed");
		try {
			
			checklistPo.geteleChecklistSectionNametab(sSection2Name).click();
			//commonsPo.tap(checklistPo.geteleChecklistSectionNametab(sSection2Name));
			//commonsPo.doubleTap(checklistPo.geteleChecklistSectionNametab(sSection2Name));
		} catch (Exception e) {
			// TODO: handle exception
			ExtentManager.logger.log(Status.PASS,"Section two is not clickable as section was jumped from 1 to 3");
		}
		
		commonsPo.tap(checklistPo.geteleChecklistSectionNametab(sSection1Name));
		System.out.println("tapped on section one");
		checklistPo.geteleChecklistAnsNumber(sNumberq).clear();
		checklistPo.geteleChecklistAnsNumber(sNumberq).sendKeys(sSectionOneJump2);
		commonsPo.tap(checklistPo.geteleSectionNextBtn(1));	
		Assert.assertTrue(checklistPo.geteleChecklistSectionNametab(sSection3Name).isDisplayed(), "Exit Criteria in Checklist Failed");	 	
		ExtentManager.logger.log(Status.PASS," Multiple Q same section jump --Exit Criteria for section passed");
		try {
			System.out.println("Entered Try");
			checklistPo.geteleChecklistSectionNametab(sSection2Name).click();

		//	commonsPo.tap(checklistPo.geteleChecklistSectionNametab(sSection2Name));
		//	commonsPo.isDisplayedCust(checklistPo.geteleChecklistSectionNametab(sSection2Name));
		} catch (Exception e) {
			// TODO: handle exception
			ExtentManager.logger.log(Status.PASS,"Multiple Q same section jump-Section two is not clickable as section was jumped");
		}
		
		
		commonsPo.tap(checklistPo.geteleChecklistSectionNametab(sSection1Name));
		checklistPo.geteleChecklistAnsNumber(sNumberq).clear();
		checklistPo.geteleChecklistAnsNumber(sNumberq).sendKeys("55");
		checklistPo.geteleChecklistAnswerTextArea(sSectionOneQ1).sendKeys(sSectionOneQAns);
		commonsPo.tap(checklistPo.geteleSectionNextBtn(1));	
		commonsPo.tap(checklistPo.geteleChecklistOK());

		try {
			checklistPo.geteleChecklistSectionNametab(sSection3Name).click();
			//commonsPo.isDisplayedCust(checklistPo.geteleChecklistSectionNametab(sSection3Name));
			//commonsPo.tap(checklistPo.geteleChecklistSectionNametab(sSection3Name));
		} catch (Exception e) {
			// TODO: handle exception
			ExtentManager.logger.log(Status.PASS,"Multiple Q same section jump-Section Three is not clickable as section was jumped from 1 to 4");
		}
		
		commonsPo.tap(checklistPo.geteleChecklistSectionNametab(sSection1Name));
		checklistPo.geteleChecklistAnswerTextArea(sSectionOneQ1).sendKeys(sSectionOneQAnsNoJ);
		commonsPo.tap(checklistPo.geteleSectionNextBtn(1));	
		commonsPo.tap(checklistPo.geteleChecklistSectionNametab(sSection2Name));
		commonsPo.tap(checklistPo.geteleSectionNextBtn(2));	

		//commonsPo.tap(checklistPo.geteleChecklistOK());
		commonsPo.tap(checklistPo.geteleChecklistSectionNametab(sSection3Name));
		//checklistPo.geteleChecklistSectionNametab(sSection3Name).click();		
		checklistPo.geteleChecklistAnsNumber(sSectionThreeErrorQ).sendKeys(sNumberDVRAns);
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(checklistPo.geteleSectionNextBtn(3));	
		
		Assert.assertFalse(checklistPo.geteleChecklistDVRNoGreaterthan100txt().isDisplayed(), "DataValidation confirmation failed");	 	
		checklistPo.geteleChecklistAnsNumber(sSectionThreeConfirmationQ).sendKeys(sSectionThreeConfirmationAns);
		commonsPo.tap(checklistPo.geteleSectionNextBtn(3));	
		Assert.assertTrue(checklistPo.geteleChecklistDVRConfirmationtxt().isDisplayed(), "DataValidation rule failed for number confirmation");	 	
		Assert.assertTrue(checklistPo.geteleDVRConfirmBtn().isDisplayed(),"Confirm button is not being displayed for confirmation dvr");
		ExtentManager.logger.log(Status.PASS,"Confirm button is displayed for confirmation DVR");
		commonsPo.tap(checklistPo.geteleDVRConfirmBtn());
		Thread.sleep(GenericLib.iLowSleep);
		checklistPo.geteleChecklistAnsNumber(sSectionThreeErrorQ).clear();
		checklistPo.geteleChecklistAnsNumber(sSectionThreeErrorQ).sendKeys(sSectionThreeQ1ValidAns1);	

		commonsPo.tap(checklistPo.geteleSectionNextBtn(3));	
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(checklistPo.geteleChecklistSectionNametab(sSection1Name));
		Thread.sleep(genericLib.iLowSleep);
		
		checklistPo.geteleChecklistAnsNumber(sNumberq).clear();
		checklistPo.geteleChecklistAnsNumber(sNumberq).sendKeys(sNumberSectionwithoutjump2);
		commonsPo.tap(checklistPo.geteleSectionNextBtn(1));
		
		commonsPo.tap(checklistPo.geteleChecklistSectionNametab(sSection2Name));
		//checklistPo.geteleChecklistSectionNametab(sSection2Name).click();
	    checklistPo.geteleChecklistAnswerTextArea(sSectionTwoQ1).sendKeys(sSectionTwoQAns);
	    commonsPo.tap(checklistPo.geteleSectionNextBtn(2));
	    commonsPo.tap(checklistPo.geteleChecklistokPopUp());
	    ExtentManager.logger.log(Status.PASS,"You may loose previously entered data displayed ");
	    
	    
	    //Section4
	    commonsPo.tap(checklistPo.geteleChecklistSectionNametab(sSection4Name));
	   // checklistPo.geteleChecklistSectionNametab(sSection4Name).click();
	    checklistPo.geteleChecklistAnswerTextArea(sSectionFourQ1).sendKeys(sSectionFourQ1Ans);
	    commonsPo.tap(checklistPo.geteleSectionNextBtn(4));
	    
	    
	    //All sections
	    commonsPo.clickAllowPopUp();
		commonsPo.switchContext("WebView");
		commonsPo.tap(checklistPo.eleChecklistSubmit());			
		

		// tapping on the validation successful checklist popup
		commonsPo.tap(checklistPo.geteleChecklistPopupSubmit());
		
		System.out.println("finished clicking on checklist submit popup.");
		ExtentManager.logger.log(Status.PASS,"Checklist Submitted");
		commonsPo.tap(checklistPo.geteleBacktoWorkOrderlnk());

		toolsPo.syncData(commonsPo);
		
			//------------------SERVER SIDE VALIDATIONS
			
			System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
			String ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName1+"')";
			String ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");	
			Assert.assertTrue(ChecklistQueryval.contains(schecklistStatus),"checklist completed is not synced to server");
			ExtentManager.logger.log(Status.PASS,"Checklist Completed status is displayed in Salesforce after sync");
			
			
			String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
			Assert.assertTrue(ChecklistAnsjson.contains(sSectionFourQ1Ans), "checklist section fourQ1  question answer is not synced to server");
			ExtentManager.logger.log(Status.PASS,"checklist section 4 question answer text question answer is  synced to server");
			
			//Validating Work not satisfying checklist entry criteria		
			System.out.println("Validating workorder not satisfying checklsit entry criteria");
		
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(GenericLib.iLowSleep);
			commonsPo.tap(exploreSearchPo.getEleExploreIcn());
			workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName3);
			
			// Navigate to Field Service process
			workOrderPo.selectAction(commonsPo, sFieldServiceName);
			Thread.sleep(GenericLib.iLowSleep);
			// Navigating to the checklist
			commonsPo.longPress(checklistPo.geteleChecklistName(sChecklistName));
			Thread.sleep(GenericLib.iLowSleep);
			
							
			try {
				//added to try catch because .isdisplayed does not return false when element not found.
				Assert.assertFalse(checklistPo.geteleChecklistSectionNametab(sSection1Name).isDisplayed());
				ExtentManager.logger.log(Status.FAIL,"Entry Criteria for checklist is failure");

			} catch (Exception e) {
				System.out.println("Entered catch block to validate entryt criteria");
				ExtentManager.logger.log(Status.PASS,"Entry Criteria for checklist is validated sucessfully");
			}				
			
	
}
}