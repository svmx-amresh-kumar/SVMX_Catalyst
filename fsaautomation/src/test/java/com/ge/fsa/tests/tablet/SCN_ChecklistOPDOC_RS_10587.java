/*
 *  @author Vinod Tharavath
 *  
 *   
 *   */
package com.ge.fsa.tests.tablet;

import static org.testng.Assert.assertNotNull;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.tablet.CalendarPO;


public class SCN_ChecklistOPDOC_RS_10587 extends BaseLib {
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistNameAllVersions = null;
	String sChecklistNameFirstVersion = null;
	String sChecklistNameLastVersion =null;
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
	// checklist q's set--;
	
	String sFirstversionQuestion1 = "10587 FirstVersion Question";
	String sFirstversionQ1Ans1 = "FirstAttemptOnFirstQuestion";
	String sFirstversionQ1Ans2 = "SecondAttemptOnFirstQuestion";
	
	
	String sLastVersionQuestion1= "10587 LastVersion Question";
	String sLastVersionQ1Ans1 = "LastVersionFirstAttempt";
	String sLastVersionQ1Ans2 = "LastVersionSecondAttempt";
	String sLastVersionQ1Ans3 = "LastVersionLastAttempt";
	
	
	String sAllVersionQuestion1 = "10587 AllVersion Question";
	String sAllVersionQ1Ans1 = "AllVersionFirstAttempt";
	String sAllVersionQ1Ans2 = "AllVersionSecondAttempt";
	String sAllVersionQ1Ans3 = "AllVersionLastAttempt";
	

	// For ServerSide Validations
	String schecklistStatus = "Completed";	
	String sSheetName =null;
	
	//For SFM Process Sahi Script name
	String sScriptName="Scenario_RS10587_ChecklistOPDOC_Versions";
	Boolean bProcessCheckResult  = false;
	
	public void prerequisites() throws Exception
	{
		sSheetName ="RS_10587";
		System.out.println("RS 10587 first version, last version and all version validation");
		
		sTestCaseID = "SCN_ChecklistOPDOC_3_RS-10587";
		//sCaseWOID = "Data_SCN_ChecklistOPDOC_1_RS-10585";

		// Reading from the Excel sheet
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ProcessName");
		sChecklistNameAllVersions = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistName_AllVersions");
		sChecklistNameFirstVersion = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistName_FirstVersion");
		sChecklistNameLastVersion = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistName_LastVersion");

		sEditProcessName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "EditProcessName");
		sChecklistOpDocName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistOpDocName");

		// Rest to Create Workorder - Work Order -
		
		 sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName= restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);

		bProcessCheckResult =commonUtility.ProcessCheck(restServices, sChecklistNameLastVersion, sScriptName, sTestCaseID);		

		//sWOName = "WO-00002005";
	}
	
	@Test(retryAnalyzer=Retry.class)
	//@Test()
	public void RS_10587() throws Exception {
		prerequisites();
		
		// Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
	    toolsPo.OptionalConfigSync(toolsPo, commonUtility, bProcessCheckResult);

		// Data Sync for WO's created
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		//toolsPo.configSync(commonsUtility);

		// Navigation to WO
		workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);

		// Navigate to Field Service process
		workOrderPo.selectAction(commonUtility, sFieldServiceName);

		//=============================First Version Checklist Submissions===================================
		
		// Navigating to the checklist and entering first version checklist first time
		commonUtility.tap(checklistPo.geteleChecklistName(sChecklistNameFirstVersion));
		Thread.sleep(CommonUtility.iLowSleep);
		checklistPo.geteleChecklistAnswerTextArea(sFirstversionQuestion1).sendKeys("FirstAttemptOnFirstQuestion");
		Thread.sleep(CommonUtility.iHighSleep);
		commonUtility.tap(checklistPo.geteleNext());
		// submitting the checklist
		commonUtility.clickAllowPopUp();
		commonUtility.switchContext("WebView");
		System.out.println("completed allow triess..now will go to submit");
		
		commonUtility.tap(checklistPo.eleChecklistSubmit());
	//	try{commonsUtility.clickAllowPopUp();}catch(Exception e) {}
		// tapping on the validation successful checklist popup
		commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		System.out.println("finished clicking on checklist submit popup.");	
		ExtentManager.logger.log(Status.PASS,"FirstVersion First Attempt Submitted sucessfully");

		Thread.sleep(CommonUtility.iLowSleep);
		
		// First version checklist second time
		
		commonUtility.tap(checklistPo.getEleStartNewLnk(sChecklistNameFirstVersion),20,20);
		checklistPo.geteleChecklistAnswerTextArea(sFirstversionQuestion1).sendKeys("SecondAttemptOnFirstQuestion");
		Thread.sleep(CommonUtility.iHighSleep);
		commonUtility.tap(checklistPo.geteleNext());
		// submitting the checklist
		//checklistPo.Allowlocationbutton();
		commonUtility.tap(checklistPo.eleChecklistSubmit());		
					
		
		// tapping on the validation successful checklist popup
		commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		System.out.println("finished clicking on checklist submit popup.");
		ExtentManager.logger.log(Status.PASS,"FirstVersion second Attempt Submitted sucessfully");
		
		
		//First Version checklist Third time
		commonUtility.tap(checklistPo.getEleStartNewLnk(sChecklistNameFirstVersion),20,20);
		checklistPo.geteleChecklistAnswerTextArea(sFirstversionQuestion1).sendKeys("ThirdAttemptOnFirstQuestion");
		Thread.sleep(CommonUtility.iHighSleep);
		commonUtility.tap(checklistPo.geteleNext());
		// submitting the checklist
		Thread.sleep(CommonUtility.iHighSleep);
		commonUtility.clickAllowPopUp();
		commonUtility.switchContext("WebView");
		//checklistPo.Allowlocationbutton();
		commonUtility.tap(checklistPo.eleChecklistSubmit());
		Thread.sleep(CommonUtility.iHighSleep);
		// tapping on the validation successful checklist popup
		commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		System.out.println("finished clicking on checklist submit popup.");
		ExtentManager.logger.log(Status.PASS,"FirstVersion Third Attempt Submitted sucessfully");

		
		//=============================Last Version Checklist Submissions===================================


		// Navigating to the checklist and entering last version checklist first time
		commonUtility.tap(checklistPo.geteleChecklistName(sChecklistNameLastVersion));
		Thread.sleep(CommonUtility.iLowSleep);
		checklistPo.geteleChecklistAnswerTextArea(sLastVersionQuestion1).sendKeys(sLastVersionQ1Ans1);
		Thread.sleep(CommonUtility.iHighSleep);
		commonUtility.tap(checklistPo.geteleNext());
		// submitting the checklist
		Thread.sleep(CommonUtility.iHighSleep);
		//checklistPo.Allowlocationbutton();
		commonUtility.tap(checklistPo.eleChecklistSubmit());		
		// tapping on the validation successful checklist popup
		commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		System.out.println("finished clicking on checklist submit popup.");	
		ExtentManager.logger.log(Status.PASS,"LastVersion First Attempt Submitted sucessfully");

		Thread.sleep(CommonUtility.iLowSleep);
		
		// Last version checklist second time
		commonUtility.tap(checklistPo.getEleStartNewLnk(sChecklistNameLastVersion),20,20);
		checklistPo.geteleChecklistAnswerTextArea(sLastVersionQuestion1).sendKeys(sLastVersionQ1Ans2);
		Thread.sleep(CommonUtility.iHighSleep);
		commonUtility.tap(checklistPo.geteleNext());
		// submitting the checklist
		//checklistPo.Allowlocationbutton();
		commonUtility.tap(checklistPo.eleChecklistSubmit());		
		// tapping on the validation successful checklist popup
		commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		System.out.println("finished clicking on checklist submit popup.");
		ExtentManager.logger.log(Status.PASS,"LastVersion Second Attempt Submitted sucessfully");

		//Last Version checklist Third time
		commonUtility.tap(checklistPo.getEleStartNewLnk(sChecklistNameLastVersion),20,20);
		checklistPo.geteleChecklistAnswerTextArea(sLastVersionQuestion1).sendKeys(sLastVersionQ1Ans3);
		Thread.sleep(CommonUtility.iHighSleep);
		commonUtility.tap(checklistPo.geteleNext());
		// submitting the checklist
		Thread.sleep(CommonUtility.iHighSleep);
		//checklistPo.Allowlocationbutton();
		Thread.sleep(CommonUtility.iLowSleep);
		commonUtility.tap(checklistPo.eleChecklistSubmit());		
		// tapping on the validation successful checklist popup
		commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		System.out.println("finished clicking on checklist submit popup.");
		ExtentManager.logger.log(Status.PASS,"LastVersion Third Attempt Submitted sucessfully");
	
		
		//=============================All Version Checklist Submissions===================================	
		// Navigating to the checklist and entering all version checklist first time
				commonUtility.tap(checklistPo.geteleChecklistName(sChecklistNameAllVersions));
				Thread.sleep(CommonUtility.iLowSleep);
				checklistPo.geteleChecklistAnswerTextArea(sAllVersionQuestion1).sendKeys(sAllVersionQ1Ans1);
				Thread.sleep(CommonUtility.iHighSleep);
				commonUtility.tap(checklistPo.geteleNext());
				// submitting the checklist
				Thread.sleep(CommonUtility.iHighSleep);
				//checklistPo.Allowlocationbutton();
				commonUtility.tap(checklistPo.eleChecklistSubmit());		
				// tapping on the validation successful checklist popup
				commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
				System.out.println("finished clicking on checklist submit popup.");	
				ExtentManager.logger.log(Status.PASS,"AllVersion First Attempt Submitted sucessfully");

				Thread.sleep(CommonUtility.iLowSleep);
				
				// All version checklist second time
				commonUtility.tap(checklistPo.getEleStartNewLnk(sChecklistNameAllVersions),20,20);
				checklistPo.geteleChecklistAnswerTextArea(sAllVersionQuestion1).sendKeys(sAllVersionQ1Ans2);
				Thread.sleep(CommonUtility.iHighSleep);
				commonUtility.tap(checklistPo.geteleNext());
				// submitting the checklist
				Thread.sleep(CommonUtility.iHighSleep);
				//checklistPo.Allowlocationbutton();
				Thread.sleep(CommonUtility.iLowSleep);
				commonUtility.tap(checklistPo.eleChecklistSubmit());		
				// tapping on the validation successful checklist popup
				commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
				System.out.println("finished clicking on checklist submit popup.");
				ExtentManager.logger.log(Status.PASS,"AllVersion Second Attempt Submitted sucessfully");
				
				//all Version checklist Third time
				commonUtility.tap(checklistPo.getEleStartNewLnk(sChecklistNameAllVersions),20,20);
				checklistPo.geteleChecklistAnswerTextArea(sAllVersionQuestion1).sendKeys(sAllVersionQ1Ans3);
				Thread.sleep(CommonUtility.iHighSleep);
				commonUtility.tap(checklistPo.geteleNext());
				// submitting the checklist
				Thread.sleep(CommonUtility.iHighSleep);
				//checklistPo.Allowlocationbutton();
				commonUtility.tap(checklistPo.eleChecklistSubmit());		
				// tapping on the validation successful checklist popup
				commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
				System.out.println("finished clicking on checklist submit popup.");
				ExtentManager.logger.log(Status.PASS,"AllVersion third Attempt Submitted sucessfully");
			
				commonUtility.tap(checklistPo.geteleBacktoWorkOrderlnk());
				toolsPo.syncData(commonUtility);
				Thread.sleep(CommonUtility.iLowSleep);
				commonUtility.tap(calendarPO.getEleCalendarClick());
				Thread.sleep(CommonUtility.iLowSleep);
				commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		
				// Navigation to WO
				workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);

				// Navigate to Field Service process
				//workOrderPo.selectAction(commonsUtility, sChecklistOpDocName);
				//workOrderPo.selectAction(commonsUtility, sChecklistOpDocName);
				Thread.sleep(CommonUtility.iLowSleep);
				//Navigating to checklistOPDOC process
				checklistPo.validateChecklistServiceReport(commonUtility, workOrderPo, sChecklistOpDocName,sWOName);
			  	checklistPo.geteleChecklistOPDOCRow();	
			  	
			  	
			  	//Validation of OPDOC First Version
				 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sFirstversionQ1Ans1), "firstVersionq1ans1 is not visible");
				 ExtentManager.logger.log(Status.PASS,"FirstVersionans1 is visible- First Version");
				 
				 try {
					 Assert.assertFalse(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sFirstversionQ1Ans2), "firstVersionq1ans1 is not visible");
					 ExtentManager.logger.log(Status.PASS,"FirstVersionans2 is not visible- Only firstVersion should be visible");

				} catch (Exception e) 
				 
				 {
					// TODO: handle exception
				}
			 
				 //Validation of OPDOC All Versions
				 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sAllVersionQ1Ans1), "All VersionQ1 Answer1 is not visible");
				 ExtentManager.logger.log(Status.PASS,"AllVersion first answer is visible");

				 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sAllVersionQ1Ans2), "All VersionQ1 Answer2 is not visible");
				 ExtentManager.logger.log(Status.PASS,"AllVersion second answer is visible");

				 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sAllVersionQ1Ans3), "All VersionQ1 Answer3 is not visible");
				 ExtentManager.logger.log(Status.PASS,"AllVersion Third answer is visible");

				 
				 //Validation of OPDOC Last Version
				 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sLastVersionQ1Ans3), "Last VersionQ1 Answer3 is not visible");
				 ExtentManager.logger.log(Status.PASS,"LastVersion last answer is visible");

				 try {
					 Assert.assertFalse(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sLastVersionQ1Ans2), "Last VersionQ1 Answer2 is not visible");
					 ExtentManager.logger.log(Status.PASS,"LastVersion attempt2 -ans2 is not visible");

				} catch (Exception e) 
				 
				 {
					// TODO: handle exception
				}
				 
				 
				 try {
					 Assert.assertFalse(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sLastVersionQ1Ans1), "Last VersionQ1 Answer1 is not visible");
					 ExtentManager.logger.log(Status.PASS,"LastVersion attempt1 -ans1 is not visible");

				} catch (Exception e) 
				 
				 {
					// TODO: handle exception
				}
			 
			// workOrderPo.getEleDoneLnk().click();
					
			commonUtility.tap(workOrderPo.getEleDoneLnk());
			Thread.sleep(CommonUtility.iHighSleep);
			((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
			Thread.sleep(CommonUtility.i30SecSleep);
			((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
			Thread.sleep(CommonUtility.i30SecSleep);
					
			//Navigation back to Work Order after Service Report
			Assert.assertTrue(checklistPo.getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
			ExtentManager.logger.log(Status.PASS,"Creation of Checklist OPDOC passed");

			Thread.sleep(CommonUtility.iLowSleep);		
			
			//Post Done button sometimes Actions is extended and we will
			// need to click calendar
			
				commonUtility.tap(calendarPO.getEleCalendarClick());
				/*commonsUtility.tap(workOrderPo.getelecancelbutton());
				Thread.sleep(GenericLib.iLowSleep);		
				commonsUtility.tap(workOrderPo.getEleDiscardChanges());*/
			toolsPo.syncData(commonUtility);
			   Thread.sleep(CommonUtility.iHighSleep);
		   Thread.sleep(CommonUtility.iHighSleep);
			Thread.sleep(CommonUtility.i30SecSleep);
			Thread.sleep(CommonUtility.iAttachmentSleep);

		// Verifying the Work details and the service report
			String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
			restServices.getAccessToken();
			String sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlqueryAttachment, "Id");	
			assertNotNull(sAttachmentIDAfter);
			ExtentManager.logger.log(Status.PASS,"OPDOC Synced to Server.");

		
		
	}

}
