/*
 *  @author Vinod Tharavath
 *  SCN_Checklist_4_RS-10580 Section level Navigation entry Exit Criteria
 */
package com.ge.fsa.tests.phone;

import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.tablet.WorkOrderPO;

public class Ph_SCN_Checklist_4_RS_10580 extends BaseLib {
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
	String sNumberq = "1. Number Should not be greater than 100";
	//String sNumberq1 = "Number cannot be greater than 100";
	String sConfirmationDVRq = "";
	String sSectionThreeErrorQ = "1. Section Three Error";
	String sSectionThreeErrorAns1 = "102";
	String sSectionThreeQ1ValidAns1 = "20";
	String sSectionThreeConfirmationQ = "2. Section Three Warning";
	String sSectionThreeConfirmationAns = "10";	
	String sSectionTwoQ1 = "2. Section Two Question One";
	String sSectionOneQ1 = "2. Section One Question One";
	String sSectionTwoQAns = "jump";
	String sSectionOneQAns= "Jump";
	String sSectionOneQAnsNoJ= "NOJump";
	String sProcessname = "Default title for Checklist";

	
	String sSectionFourQ1 = "Section Four Question One";
	String sSectionFourQ1Ans = "Section Four Question Answer 1";
	
	String sSectionFourQ3 ="1. Section Four Question Three";
	String sSectionFourQ3Ans ="Section Four Question Answer 3";
	
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
	
	String sScriptName="/appium/Scenario_RS10580_Checklist_Sections.sah";
	Boolean bProcessCheckResult  = false;
	
	
	public void prerequisites() throws Exception
	{
		sSheetName ="RS_10580";
		System.out.println("SCN_RS10580_Checklist_Sections");
		sTestCaseID = "SCN_Checklist_4_RS-10580_Sections";
		sCaseWOID = "Data_SCN_Checklist_4_RS-10580_Sections";

		// Reading from the Excel sheet
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ProcessName");
		sChecklistName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistName");
		sEditProcessName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "EditProcessName");
	

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
		
		bProcessCheckResult =commonUtility.ProcessCheck(restServices, sChecklistName, sScriptName, sTestCaseID);		

				
		//sWOName1 = "WO-00001615";
		
	}
	//@Test()
	@Test(retryAnalyzer=Retry.class)
	public void RS_10580() throws Exception {
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6475");
		}else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6759");

		}
			
		prerequisites();
		
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);

		// Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		// toolsPo.configSync(commonsUtility);

		// Navigating to Checklist
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName1,
				sProcessname);
		ExtentManager.logger.log(Status.INFO, "WorkOrder dynamically created and used is :" + sWOName1 + "");

		// Scrolling
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			commonUtility.custScrollToElement(sChecklistName, false);
		} else {
			commonUtility.custScrollToElement(sChecklistName);
		}

		// Click on ChecklistName
		ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
		System.out.println("clicked checklistname");
		Thread.sleep(2000);

		// Starting new Checklist
		ph_ChecklistPO.getelecheckliststartnew(sChecklistName).click();
		Thread.sleep(2000);
		
		//Clicking on Section1
		ph_ChecklistPO.getElementSection(0).click();
		
		commonUtility.custScrollToElementAndClick(ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq));
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq).sendKeys(sNumberDVRAns+"\n");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistNumberDVR().isDisplayed(), "DataValidation rule failed for number error");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for Number Passed");
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq).clear();
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq).sendKeys(sNumberSectionJumpAns+"\n");
		
		ph_ChecklistPO.geteleChecklistNextButton().click();
		
		
		commonUtility.clickAllowPopUp();
		commonUtility.switchContext("Native");
		
		
		Assert.assertFalse(commonUtility.isDisplayedCust(ph_ChecklistPO.geteleChecklistNumberDVR()),"DataValidation confirmation failed");
		Assert.assertTrue(ph_ChecklistPO.geteleGeneric("Section Three").isDisplayed(), "Exit Criteria in Checklist Failed");	 	
		ExtentManager.logger.log(Status.PASS,"Exit Criteria for section passed");
			
			 Integer iSectionSize =  ph_ChecklistPO.geteleGenericList("Section Two").size();
			 if(iSectionSize==0)
			 {
					ExtentManager.logger.log(Status.PASS,"Section two is not clickable as section was jumped from 1 to 3");
			 }
			 else
					ExtentManager.logger.log(Status.FAIL,"Section two is visible and section was not jumped from 1 to 3",MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		//moving back to main checklist page sections
		ph_ChecklistPO.geteleBackbutton().click();
		
		// Clicking on Section1
		ph_ChecklistPO.getElementSection(0).click();
		System.out.println("tapped on section one");
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq).clear();
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq).sendKeys(sSectionOneJump2+"\n");
		
		ph_ChecklistPO.geteleChecklistNextButton().click();
		Assert.assertTrue(ph_ChecklistPO.geteleGeneric("Section Three").isDisplayed(), "Exit Criteria in Checklist Failed");	 	
		ExtentManager.logger.log(Status.PASS," Multiple Q same section jump --Exit Criteria for section passed");
		//Assert.assertFalse(commonUtility.isDisplayedCust(ph_ChecklistPO.geteleGeneric("Section Two")), "Exit Criteria in Checklist Failed");	 	
		ExtentManager.logger.log(Status.PASS,"Multiple Q same section jump-Section two is not clickable as section was jumped");
		
		// moving back to main checklist page sections
		ph_ChecklistPO.geteleBackbutton().click();
		
		//Clicking on Section1
		ph_ChecklistPO.getElementSection(0).click();
		
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq).clear();
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq).sendKeys("55"+"\n");
		// Do no Add /n as it is causing section jumps to fail as /n is considered as new character exit criteria wont work
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sSectionOneQ1).sendKeys(sSectionOneQAns);
		driver.findElement(By.xpath("//*[@*='"+sSectionOneQ1+"']")).click();
		ph_ChecklistPO.geteleChecklistNextButton().click();


		iSectionSize =  ph_ChecklistPO.geteleGenericList("Section Two").size();
		
		 if(iSectionSize==0)
		 {
				ExtentManager.logger.log(Status.PASS,"Section two is not clickable as section was jumped from 1 to 4");
		 }
		 else
				ExtentManager.logger.log(Status.FAIL,"Section two is visible and section was not jumped from 1 to 4",MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		ExtentManager.logger.log(Status.PASS,"Multiple Q same section jump-Section Three is not clickable as section was jumped from 1 to 4");
		
		
		// moving back to main checklist page sections
		ph_ChecklistPO.geteleBackbutton().click();
		
		// Clicking on Section1
		ph_ChecklistPO.getElementSection(0).click();
		commonUtility.custScrollToElementAndClick(ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq));
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq).clear();
		//	ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberq).sendKeys("55");
			
			driver.findElement(By.xpath("//*[@*='"+sNumberq+"']")).click();
			commonUtility.custScrollToElementAndClick(ph_ChecklistPO.geteleTextQAnswithMoreInfo(sSectionOneQ1));
			ph_ChecklistPO.geteleTextQAnswithMoreInfo(sSectionOneQ1).clear();
			ph_ChecklistPO.geteleTextQAnswithMoreInfo(sSectionOneQ1).sendKeys(sSectionOneQAnsNoJ);
			driver.findElement(By.xpath("//*[@*='"+sSectionOneQ1+"']")).click();

			//question
			ph_ChecklistPO.geteleChecklistNextButton().click();
			Assert.assertTrue(ph_ChecklistPO.geteleGeneric("Section Three").isDisplayed(), "Exit Criteria in Checklist Failed");	 	
			ExtentManager.logger.log(Status.PASS,"No Jumping section is pass");

			// moving back to main checklist page sections
			ph_ChecklistPO.geteleBackbutton().click();
			
			//Clicking on Section3
			ph_ChecklistPO.getElementSection(2).click();
			
		commonUtility.custScrollToElementAndClick(ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sSectionThreeErrorQ));
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sSectionThreeErrorQ).sendKeys(sNumberDVRAns+"\n");
		Assert.assertTrue(commonUtility.isDisplayedCust(ph_ChecklistPO.geteleChecklistNumberDVR()),"DataValidation confirmation failed");
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sSectionThreeErrorQ).clear();
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sSectionThreeConfirmationQ).sendKeys(sSectionThreeConfirmationAns+"\n");
		ph_ChecklistPO.geteleChecklistNextButton().click();
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistDVRConfirmationtxt().isDisplayed(), "DataValidation rule failed for number confirmation");	
		
		Assert.assertTrue(ph_ChecklistPO.getEleConfirm().isDisplayed(),"Confirm button is not being displayed for confirmation dvr");
		ExtentManager.logger.log(Status.PASS,"Confirm button is displayed for confirmation DVR");
		
		ph_ChecklistPO.getEleConfirm().click();
		Thread.sleep(CommonUtility.iLowSleep);
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sSectionThreeErrorQ).sendKeys(sSectionThreeQ1ValidAns1+"\n");
		//driver.findElement(By.xpath("//*[@*='"+sSectionThreeErrorQ+"']")).click();

		//question

		ph_ChecklistPO.geteleChecklistNextButton().click();
		
		Thread.sleep(2000);
		// moving back to main checklist page sections
		ph_ChecklistPO.geteleBackbutton().click();
		
		//Clicking on Section2
		ph_ChecklistPO.getElementSection(1).click();
		
		
		commonUtility.custScrollToElementAndClick(ph_ChecklistPO.geteleTextQAnswithMoreInfo(sSectionTwoQ1));
		ph_ChecklistPO.geteleTextQAnswithMoreInfo(sSectionTwoQ1).clear();
		driver.findElement(By.xpath("//*[@*='"+sSectionTwoQ1+"']")).click();
		//question
		ph_ChecklistPO.geteleChecklistNextButton().click();
		
		commonUtility.custScrollToElementAndClick(ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sSectionThreeErrorQ));
		ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sSectionThreeErrorQ).clear();
		driver.findElement(By.xpath("//*[@*='"+sSectionThreeErrorQ+"']")).click();

		//ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sSectionThreeErrorQ).sendKeys(sNumberSectionwithoutjump2);

		
		ph_ChecklistPO.geteleChecklistNextButton().click();

		//commonUtility.custScrollToElementAndClick(ph_ChecklistPO.geteleTextQAnswithMoreInfo(sSectionTwoQ1));
		//ph_ChecklistPO.geteleTextQAnswithMoreInfo(sSectionTwoQ1).sendKeys(sSectionTwoQAns);

		//ph_ChecklistPO.geteleChecklistNextButton().click();

		// LOSS of DATA HOW TO VALIDATE CHECK WITH GOAPP TESTERS!!!!!!!!!!
		
		//Section4
		commonUtility.custScrollToElementAndClick(ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sSectionFourQ3));
		ph_ChecklistPO.geteleTextQAnswithMoreInfo(sSectionFourQ3).sendKeys(sSectionFourQ3Ans);
		driver.findElement(By.xpath("//*[@*='"+sSectionFourQ3+"']")).click();
		ph_ChecklistPO.geteleSubmitbtn().click();
	    
		//to return out of checklist
		ph_WorkOrderPo.getEleBackButton().click();
		
		//to return to work order
		ph_WorkOrderPo.getEleBackButton().click();

		ph_MorePo.syncData(commonUtility);
		
		//------------------SERVER SIDE VALIDATIONS
			
			System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
			String ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName1+"')";
			String ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");	
			Assert.assertTrue(ChecklistQueryval.contains(schecklistStatus),"checklist completed is not synced to server");
			ExtentManager.logger.log(Status.PASS,"Checklist Completed status is displayed in Salesforce after sync");
			
			String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
			Assert.assertTrue(ChecklistAnsjson.contains(sSectionFourQ3Ans), "checklist section fourQ1  question answer is not synced to server");
			ExtentManager.logger.log(Status.PASS,"checklist section 4 question answer text question answer is  synced to server");
			
			
			//Validating Work not satisfying checklist entry criteria		
			System.out.println("Validating workorder not satisfying checklsit entry criteria");
		
			
			ph_ExploreSearchPo.geteleExploreIcn().click();
			
			
			
			// Navigating to Checklist
			ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName3,
					sProcessname);
			ExtentManager.logger.log(Status.INFO, "WorkOrder dynamically created and used is :" + sWOName3 + "");

			
			// Scrolling
			if (BaseLib.sOSName.equalsIgnoreCase("android")) {
				commonUtility.custScrollToElement(sChecklistName, false);
			} else {
				commonUtility.custScrollToElement(sChecklistName);
			}

			// Click on ChecklistName
			ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
			
			// Starting new Checklist
			ph_ChecklistPO.getelecheckliststartnew(sChecklistName).click();

			
			iSectionSize =  ph_ChecklistPO.geteleGenericList("Section One").size();
			System.out.println(ph_ChecklistPO.geteleGenericList("Section One").size());
						
			
			 if(iSectionSize==0)
			 {
					ExtentManager.logger.log(Status.PASS,"Entry Criteria of section validated Section two is not displayed");
			 }
			 else
					ExtentManager.logger.log(Status.FAIL,"Entry Criteria of section failed Section two is  displayed",MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());

			
			
			
	
}
}