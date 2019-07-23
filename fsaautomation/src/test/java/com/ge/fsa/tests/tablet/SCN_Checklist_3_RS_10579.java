/*
 *  @author Vinod Tharavath
 *  SCN_Checklist_3_RS-10579 Submit and sync Verify Checklist QC,DVR,SOU,ENTRY/EXIT and Help URL
 */
package com.ge.fsa.tests.tablet;

import static org.testng.Assert.assertTrue;

import java.util.Set;
import org.json.JSONArray;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.tablet.WorkOrderPO;

public class SCN_Checklist_3_RS_10579 extends BaseLib {
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sExploreChildSearchTxt = null;
	String sWORecordID = null;
	String sWOName1 = null;
	String sWOName2 = null;
	String sWOName3 = null;
	String sWOName4 = null;
	
	String sOrderStatusVal = null;
	String sEditProcessName = null;
	String sSection1Name="Section One";
	String sSection2Name="Section Two";
	String sSection3Name="Section Three";

	// checklist q's set--;
	
	String sDateq = "Date should not be Today";
	String sNumberq = "Number Should not be greater than 100";
	String sConfirmationDVRq = "";
	String sSectionThreeq1 = "Section Three q1";
	String sSectionThreeq1Ans = "ok";	
	String sDateAns = null;
	String sDateTimeAns = null;
	String sNumberSectionJumpAns = "20";
	String sNumberDVRAns="102";
	String snumberwithoutjump = "100";

	// For ServerSide Validations
	String schecklistStatus = "Completed";	
	String sChecklistQuery = null;
	String sChecklistQueryval = null;
	String ChecklistAnsjson = null;
	String sSoqlqueryWO = null;
	String sSoqlProforma = null;
	String sSoqlNoOfTimes = null;
	String sBillTypeServer =null;
	String sProformaServer = null;
	String sNoOftimesServer = null;
	String sNoOftimesServer1 = null;
	String sProblemDescServer = null;
	String sSoqlqueryWOProb = null;
	String sChecklistAnsjson1 = null;
	String sAttachmentQ = "Question two Section two";
	
	//Sou 	
	String sBillingTpeSOU =null;
	String sProblemDescriptionSOU = null;	
	
	//SOU ans
	String sBillingTpeSOUServer ="Paid";
	String sProblemDescriptionSOUServer = "Souce Object Update Sucess";
	String sSheetName =null;
	String sScriptName="Scenario_RS10579_Checklist_Combo";
	Boolean bProcessCheckResult  = false;
	String url = null;
	public void prerequisites() throws Exception
	{
		sSheetName ="RS_10579";
		System.out.println("SCN_RS10579_Checklist_Entry Exit with URL with basic source object update and DVR");
		sTestCaseID = "SCN_Checklist_3_RS-10579_Entry_Exit_Criteria";
		sCaseWOID = "Data_SCN_Checklist_3_RS-10579_Entry_Exit_Criteria";

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

	
		//Cancelled Work Order for Qualification Criteria check.
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__Order_Status__c\":\"Canceled\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName2 = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName2);
			
		
		// Work Order for checklist entry Criteria check.
		 sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"Low\"}");
		System.out.println(sWORecordID);
		sWOName3 = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName3);
			
	// Rest to Create Workorder -Standard Work Order - Satisfies Qualification Criteria and Checklist Entry Criteria
		
		 sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName4= restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName4);				
		//sWOName1 = "WO-00001615";		
		
		bProcessCheckResult =commonUtility.ProcessCheck(restServices, sChecklistName, sScriptName, sTestCaseID);		

		
	}

	@Test(retryAnalyzer=Retry.class)
	public void RS_10579() throws Exception {
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-10579");
		}else {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12063");

		}
		prerequisites();
		// Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
	    toolsPo.OptionalConfigSync(toolsPo, commonUtility, bProcessCheckResult);

		// Data Sync for WO's created
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		// toolsPo.configSync(commonsUtility);

		// Navigation to WO
		workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName1);

		// Navigate to Field Service process
		workOrderPo.selectAction(commonUtility, sFieldServiceName);

		// Navigating to the checklist
		commonUtility.longPress(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(CommonUtility.iLowSleep);
		
		
		//commonsUtility.tap(checklistPo.geteleChecklistAnsDate(sDateq));
//		checklistPo.geteleChecklistAnsDate(sDateq).click();
//	    commonsUtility.switchContext("Native");
//	    commonsUtility.tap(commonsUtility.getEleDonePickerWheelBtn());
//	   	//commonsUtility.getEleDonePickerWheelBtn().click();
//	    commonsUtility.switchContext("WebView");
//	    sDateAns = checklistPo.geteleChecklistAnsDate(sDateq).getAttribute("value");
		sDateAns=commonUtility.getDate(checklistPo.geteleChecklistAnsDate(sDateq), "date");
	    System.out.println("direct sdatetime"+sDateAns);	    
	    Assert.assertTrue(checklistPo.geteleChecklistDVRtxt().isDisplayed(), "DataValidation rule failed for date ");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for date Field Passed");
		
		
		commonUtility.setSpecificDate(checklistPo.geteleChecklistAnsDate(sDateq),"February", "3", "2018");
		checklistPo.geteleChecklistAnsNumber(sNumberq).sendKeys(sNumberDVRAns);
		// tapping next button
		commonUtility.tap(checklistPo.geteleNext());					 
		Assert.assertTrue(checklistPo.geteleChecklistDVRNoGreaterthan100txt().isDisplayed(), "DataValidation rule failed for number confirmation");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for Number Passed");
		checklistPo.geteleChecklistAnsNumber(sNumberq).clear();
		checklistPo.geteleChecklistAnsNumber(sNumberq).sendKeys(sNumberSectionJumpAns);
		commonUtility.tap(checklistPo.geteleNext());	
		Assert.assertFalse(checklistPo.geteleChecklistDVRNoGreaterthan100txt().isDisplayed(), "DataValidation confirmation failed");	 	
		Assert.assertTrue(checklistPo.geteleChecklistSectionNametab(sSection3Name).isDisplayed(), "Exit Criteria in Checklist Failed");	 	
		ExtentManager.logger.log(Status.PASS,"Exit Criteria for section passed");
		//checklistPo.geteleChecklistSectionNametab(sSection3Name).click();	
		commonUtility.tap(checklistPo.geteleChecklistSectionNametab(sSection3Name));
		checklistPo.geteleChecklistAnswerTextArea(sSectionThreeq1).sendKeys(sSectionThreeq1Ans);
		commonUtility.tap(checklistPo.geteleSectionNextBtn(3));	
		Thread.sleep(CommonUtility.iLowSleep);
		commonUtility.tap(checklistPo.geteleChecklistSectionNametab(sSection1Name));
		Thread.sleep(CommonUtility.iLowSleep);
		checklistPo.geteleChecklistAnsNumber(sNumberq).clear();
		checklistPo.geteleChecklistAnsNumber(sNumberq).sendKeys(snumberwithoutjump);
		commonUtility.tap(checklistPo.geteleNext());	
		//checklistPo.geteleChecklistSectionNametab(sSection2Name).click();
		commonUtility.tap(checklistPo.geteleChecklistSectionNametab(sSection2Name));
		Thread.sleep(CommonUtility.iLowSleep);
		commonUtility.tap(checklistPo.geteleSectionNextBtn(2));
		Thread.sleep(CommonUtility.iLowSleep);
		String sTempVal = checklistPo.geteleChecklistAnswerTextArea(sSectionThreeq1).getAttribute("value");
		Assert.assertEquals(sTempVal, sSectionThreeq1Ans, "Section switch has caused data loss");
		ExtentManager.logger.log(Status.PASS,"There is no data loss in switching sections");

		//commonsUtility.tap(checklistPo.geteleNext());
		commonUtility.tap(checklistPo.geteleSectionNextBtn(3));
		
		// submitting the checklist
		Thread.sleep(CommonUtility.iLowSleep);
		commonUtility.clickAllowPopUp();
		commonUtility.switchContext("WebView");
		commonUtility.tap(checklistPo.eleChecklistSubmit());			
				

		// tapping on the validation successful checklist popup
		commonUtility.longPress(checklistPo.geteleChecklistPopupSubmit());
		System.out.println("finished clicking on checklist submit popup.");
		
		
		//Navigating back to work Orders
		commonUtility.tap(checklistPo.geteleBacktoWorkOrderlnk());
		Thread.sleep(CommonUtility.iMedSleep);		
		Assert.assertEquals(workOrderPo.geteleProblemDescriptionlbl().getText(), sProblemDescriptionSOUServer, "Problem Description source object update not updated sucessfully in Work Order");
		ExtentManager.logger.log(Status.PASS,"Source Object update sucessfull for Problem Description");

		Assert.assertEquals(workOrderPo.geteleBillingTypelbl().getText(), sBillingTpeSOUServer, "Billing Type source update not updated sucessfully in Work Order");
		ExtentManager.logger.log(Status.PASS,"Source Object update sucessfull for billing type in Work Order");
		commonUtility.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(CommonUtility.iMedSleep);
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		//-------------Validating work order not satisfying Qualification Criteria---------------- 
		System.out.println("Validating work order not satisfying Qualification Criteria");
		
		// Navigation to WO
			workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName2);

			// Navigate to Field Service process
			workOrderPo.selectAction(commonUtility, sFieldServiceName);

			// Navigating to the checklist
			
			try {
				//added to try catch because .isdisplayed does not return false when element not found.
				Assert.assertFalse(checklistPo.geteleChecklistName(sChecklistName).isDisplayed());
				ExtentManager.logger.log(Status.FAIL,"Qualification criteria failure check!");
			} catch (Exception e) {
				ExtentManager.logger.log(Status.PASS,"Qualification criteria not met checklist not displayed");
			}
			
			Thread.sleep(CommonUtility.iLowSleep);

					//Validating Work not satisfying checklist entry criteria		
			System.out.println("Validating workorder not satisfying checklsit entry criteria");
			
			//Navigating back to work Orders
			commonUtility.tap(checklistPo.geteleBacktoWorkOrderlnk());
			commonUtility.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(CommonUtility.iLowSleep);
			commonUtility.tap(exploreSearchPo.getEleExploreIcn());
			workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName3);
			
			// Navigate to Field Service process
			workOrderPo.selectAction(commonUtility, sFieldServiceName);
			Thread.sleep(CommonUtility.iLowSleep);
			// Navigating to the checklist
			commonUtility.longPress(checklistPo.geteleChecklistName(sChecklistName));
			Thread.sleep(CommonUtility.iLowSleep);
			
							
			try {
				//added to try catch because .isdisplayed does not return false when element not found.
				Assert.assertFalse(checklistPo.geteleChecklistSectionNametab(sSection1Name).isDisplayed());
				ExtentManager.logger.log(Status.FAIL,"Entry Criteria for checklist is failure");

			} catch (Exception e) {
				System.out.println("Entered catch block to validate entryt criteria");
				ExtentManager.logger.log(Status.PASS,"Entry Criteria for checklist is validated sucessfully");
			}			


			//Navigating back to work Orders
			commonUtility.tap(checklistPo.geteleSubmitLnk());
			commonUtility.longPress(checklistPo.geteleChecklistPopupSubmit());
			
			//Navigating back to work Orders
			commonUtility.tap(checklistPo.geteleBacktoWorkOrderlnk());		
			
			toolsPo.syncData(commonUtility);
			Thread.sleep(CommonUtility.iMedSleep);

			commonUtility.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(CommonUtility.iLowSleep);
			commonUtility.tap(exploreSearchPo.getEleExploreIcn());
			workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName4);
			workOrderPo.selectAction(commonUtility, sFieldServiceName);

			// Navigating to the checklist
			commonUtility.longPress(checklistPo.geteleChecklistName(sChecklistName));
			commonUtility.tap(checklistPo.geteleChecklistSectionNametab(sSection1Name));
			commonUtility.tap(checklistPo.geteleSectionNextBtn(1));
			//checklistPo.geteleChecklistHelpIcn().click();
			commonUtility.tap(checklistPo.geteleChecklistHelpIcn(sAttachmentQ));
			Thread.sleep(CommonUtility.iLowSleep);
			Thread.sleep(CommonUtility.i30SecSleep);
			System.out.println("Context count " + driver.getContextHandles().size());
			Set contextNames = driver.getContextHandles();
			Thread.sleep(CommonUtility.i30SecSleep);
			System.out.println(driver.getContext());
			
			if(sOSName.contains("android")) {
				driver.context("NATIVE_APP");
				Thread.sleep(5000);
				url = driver.findElement(By.id("com.android.chrome:id/url_bar")).getText();
				driver.context("WEBVIEW_com.servicemaxinc.svmxfieldserviceapp");
			}
			else {
				driver.context(contextNames.toArray()[contextNames.size()-1].toString());
				Thread.sleep(CommonUtility.i30SecSleep);
				url = driver.getCurrentUrl();
			}
			
		//	driver.context(contextNames.toArray()[2].toString());
		//	String url = driver.getCurrentUrl();
	    //    System.out.println(url);
			Assert.assertTrue(url.contains("www.ge.com"),"Help url validated sucessfully");
			//Assert.assertEquals(url, "https://www.ge.com/", "Help url validated sucessfully");
			ExtentManager.logger.log(Status.PASS,"Help URL validated sucessfully");
	
			//------------------SERVER SIDE VALIDATIONS
			
			System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
			sChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName1+"')";
			sChecklistQueryval = restServices.restGetSoqlValue(sChecklistQuery, "SVMXC__Status__c");	
			Assert.assertTrue(sChecklistQueryval.contains(schecklistStatus),"checklist completed is not synced to server");
			ExtentManager.logger.log(Status.PASS,"Checklist Completed status is displayed in Salesforce after sync");		
			
			ChecklistAnsjson = restServices.restGetSoqlValue(sChecklistQuery, "SVMXC__ChecklistJSON__c");
			Assert.assertTrue(ChecklistAnsjson.contains(sSectionThreeq1Ans), "checklist section three  question answer is not synced to server");
			ExtentManager.logger.log(Status.PASS,"checklist section three text question answer is  synced to server");
			
			sChecklistAnsjson1 = restServices.restGetSoqlValue(sChecklistQuery, "SVMXC__ChecklistJSON__c");
			Assert.assertTrue(sChecklistAnsjson1.contains(sNumberSectionJumpAns), "checklist section three  question answer is not synced to server");
			ExtentManager.logger.log(Status.PASS,"checklist section Number question answer is  synced to server");
			
			sSoqlqueryWO = "Select+SVMXC__Billing_Type__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName1+"'"; 
			sSoqlqueryWOProb = "Select+SVMXC__Problem_Description__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName1+"'"; 

			restServices.getAccessToken();
			sBillTypeServer = restServices.restGetSoqlValue(sSoqlqueryWO,"SVMXC__Billing_Type__c");
			Assert.assertTrue(sBillTypeServer.equals(sBillingTpeSOUServer), "Billing type Picklist source object not syned to server");
			ExtentManager.logger.log(Status.PASS,"Billing type Picklist Source object update has synced to server");
			
			sProblemDescServer = restServices.restGetSoqlValue(sSoqlqueryWOProb,"SVMXC__Problem_Description__c");
			Assert.assertTrue(sProblemDescServer.equals(sProblemDescriptionSOUServer), "Problem Description source object not syned to server");
			ExtentManager.logger.log(Status.PASS,"Problem Description Source object update has synced to server");
		
			
	}

}
