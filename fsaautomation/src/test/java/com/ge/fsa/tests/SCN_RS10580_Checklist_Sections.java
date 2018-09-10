/*
 *  @author Vinod Tharavath
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
import com.ge.fsa.pageobjects.WorkOrderPO;

public class SCN_RS10580_Checklist_Sections extends BaseLib {
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
	String sSectionTwoQAns = "jump";
	
	String sSectionFourQ1 = "Section Four Question One";
	String sSectionFourQ1Ans = "Section Four Question Answer 1";
	
	String sDateAns = null;
	String sDateTimeAns = null;
	String sNumberSectionJumpAns = "19";
	String sNumberDVRAns="102";
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

	@Test(enabled = true)
	public void SCN_RS10579() throws Exception {
		
		System.out.println("SCN_RS10580_Checklist_Sections");

		
		sTestCaseID = "SCN_Checklist_4_RS-10580_Sections";
		sCaseWOID = "Data_SCN_Checklist_4_RS-10580_Sections";

		// Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID, "ChecklistName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID, "EditProcessName");
		

		// Rest to Create Workorder -Standard Work Order - Satisfies Qualification Criteria and Checklist Entry Criteria
		
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		String sWOName1= restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName1);	
				
		//sWOName1 = "WO-00001615";
		
		// Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);

		// Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		// toolsPo.configSync(commonsPo);

		// Navigation to WO
		workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName1);

		// Navigate to Field Service process
		workOrderPo.selectAction(commonsPo, sFieldServiceName);

		// Navigating to the checklist
		commonsPo.longPress(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(GenericLib.iLowSleep);
		
		checklistPo.geteleChecklistAnsNumber(sNumberq).sendKeys(sNumberDVRAns);
		// tapping next button
		commonsPo.tap(checklistPo.geteleSectionNextBtn(1));					 
		Assert.assertTrue(checklistPo.geteleChecklistDVRNoGreaterthan100txt().isDisplayed(), "DataValidation rule failed for number error");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for Number Passed");
		checklistPo.geteleChecklistAnsNumber(sNumberq).clear();
		checklistPo.geteleChecklistAnsNumber(sNumberq).sendKeys(sNumberSectionJumpAns);
		commonsPo.tap(checklistPo.geteleSectionNextBtn(1));	
		try{driver.findElement(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")).click();}catch(Exception e) {}
		Assert.assertFalse(checklistPo.geteleChecklistDVRNoGreaterthan100txt().isDisplayed(), "DataValidation confirmation failed");	 	
		Assert.assertTrue(checklistPo.geteleChecklistSectionNametab(sSection3Name).isDisplayed(), "Exit Criteria in Checklist Failed");	 	
		ExtentManager.logger.log(Status.PASS,"Exit Criteria for section passed");
		try {
			checklistPo.geteleChecklistSectionNametab(sSection2Name).click();
		} catch (Exception e) {
			// TODO: handle exception
			ExtentManager.logger.log(Status.PASS,"Section two is not clickable as section was jumped from 1 to 3");
		}
		
		
		
		checklistPo.geteleChecklistSectionNametab(sSection3Name).click();		
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
		checklistPo.geteleChecklistSectionNametab(sSection2Name).click();
	    checklistPo.geteleChecklistAnswerTextArea(sSectionTwoQ1).sendKeys(sSectionTwoQAns);
	    commonsPo.tap(checklistPo.geteleSectionNextBtn(2));
	    commonsPo.tap(checklistPo.geteleChecklistokPopUp());
	    ExtentManager.logger.log(Status.PASS,"You may loose previously entered data displayed ");
	    
	    
	    //Section4
	    checklistPo.geteleChecklistSectionNametab(sSection4Name).click();
	    checklistPo.geteleChecklistAnswerTextArea(sSectionFourQ1).sendKeys(sSectionFourQ1Ans);
	    commonsPo.tap(checklistPo.geteleSectionNextBtn(4));
	    
	    
	    //All sections
		commonsPo.tap(checklistPo.eleChecklistSubmit());			
		

		// tapping on the validation successful checklist popup
		commonsPo.longPress(checklistPo.geteleChecklistPopupSubmit());
		
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
			
			
			


}
}