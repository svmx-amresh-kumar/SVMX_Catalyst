/*
 *  @author Vinod Tharavath
 *  SCN_Checklist_3_RS-10579 Submit and sync Verify Checklist QC,DVR,SOU,ENTRY/EXIT and Help URL
 */
package com.ge.fsa.tests;

import java.util.Set;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.WorkOrderPO;

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
	
	//Sou 
	
	String sBillingTpeSOU =null;
	String sProblemDescriptionSOU = null;
	
	
	//SOU ans
	String sBillingTpeSOUServer ="Paid";
	String sProblemDescriptionSOUServer = "Souce Object Update Sucess";

	@Test(enabled = true)
	public void RS_10579() throws Exception {
		
		System.out.println("SCN_RS10579_Checklist_Entry Exit with URL with basic source object update and DVR");

		
		sTestCaseID = "SCN_Checklist_3_RS-10579_Entry_Exit_Criteria";
		sCaseWOID = "Data_SCN_Checklist_3_RS-10579_Entry_Exit_Criteria";

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

	
		//Cancelled Work Order for Qualification Criteria check.
		 sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__Order_Status__c\":\"Canceled\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		String sWOName2 = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName2);
			
		
		// Work Order for checklist entry Criteria check.
		 sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"Low\"}");
		System.out.println(sWORecordID);
		String sWOName3 = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName3);
			
	// Rest to Create Workorder -Standard Work Order - Satisfies Qualification Criteria and Checklist Entry Criteria
		
		 sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		String sWOName4= restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName4);
		
		
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
		checklistPo.geteleChecklistAnsDate(sDateq).click();
	    commonsPo.switchContext("Native");
	    commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
	    commonsPo.switchContext("WebView");
	    sDateAns = checklistPo.geteleChecklistAnsDate(sDateq).getAttribute("value");	    
	    System.out.println("direct sdatetime"+sDateAns);	    
	    Assert.assertTrue(checklistPo.geteleChecklistDVRtxt().isDisplayed(), "DataValidation rule failed for date ");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for date Field Passed");				
		//Thread.sleep(12000);
		commonsPo.setDateYear(checklistPo.geteleChecklistAnsDate(sDateq),"February", "3", "2018");
		checklistPo.geteleChecklistAnsNumber(sNumberq).sendKeys(sNumberDVRAns);
		// tapping next button
		commonsPo.tap(checklistPo.geteleNext());					 
		Assert.assertTrue(checklistPo.geteleChecklistDVRNoGreaterthan100txt().isDisplayed(), "DataValidation rule failed for number confirmation");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for Number Passed");
		checklistPo.geteleChecklistAnsNumber(sNumberq).clear();
		checklistPo.geteleChecklistAnsNumber(sNumberq).sendKeys(sNumberSectionJumpAns);
		commonsPo.tap(checklistPo.geteleNext());	
		Assert.assertFalse(checklistPo.geteleChecklistDVRNoGreaterthan100txt().isDisplayed(), "DataValidation confirmation failed");	 	
		Assert.assertTrue(checklistPo.geteleChecklistSectionNametab(sSection3Name).isDisplayed(), "Exit Criteria in Checklist Failed");	 	
		ExtentManager.logger.log(Status.PASS,"Exit Criteria for section passed");
		checklistPo.geteleChecklistSectionNametab(sSection3Name).click();		
		checklistPo.geteleChecklistAnswerTextArea(sSectionThreeq1).sendKeys(sSectionThreeq1Ans);
		commonsPo.tap(checklistPo.geteleNext());	
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(checklistPo.geteleChecklistSectionNametab(sSection1Name));
		Thread.sleep(genericLib.iLowSleep);
		checklistPo.geteleChecklistAnsNumber(sNumberq).clear();
		checklistPo.geteleChecklistAnsNumber(sNumberq).sendKeys(snumberwithoutjump);
		commonsPo.tap(checklistPo.geteleNext());	
		checklistPo.geteleChecklistSectionNametab(sSection2Name).click();
		//commonsPo.tap(checklistPo.geteleChecklistHelpIcn());
		
		// NEED TO WRITE CODE TO SWITCH TO SAFARI AND VALIDATE.
		//String url = driver.getCurrentUrl();
		//System.out.println(url);		
	
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(checklistPo.geteleSectionNextBtn(2));
		System.out.println("CHECK NOW----------------");
		//commonsPo.tap(checklistPo.geteleNext());
		Thread.sleep(GenericLib.iLowSleep);
		String sTempVal = checklistPo.geteleChecklistAnswerTextArea(sSectionThreeq1).getAttribute("value");
		Assert.assertEquals(sTempVal, sSectionThreeq1Ans, "Section switch has caused data loss");
		ExtentManager.logger.log(Status.PASS,"There is no data loss in switching sections");

		//commonsPo.tap(checklistPo.geteleNext());
		commonsPo.tap(checklistPo.geteleSectionNextBtn(3));
		// submitting the checklist
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(checklistPo.eleChecklistSubmit());			
				

		// tapping on the validation successful checklist popup
		commonsPo.longPress(checklistPo.geteleChecklistPopupSubmit());
		System.out.println("finished clicking on checklist submit popup.");
		
		
		//Navigating back to work Orders
		commonsPo.tap(checklistPo.geteleBacktoWorkOrderlnk());
		
		
		
		String Temp  = workOrderPo.geteleProblemDescriptionlbl().getText();
		System.out.println("gettext value"+Temp );	
		Assert.assertEquals(Temp, sProblemDescriptionSOUServer, "Problem Description source object update not updated sucessfully in Work Order");
		ExtentManager.logger.log(Status.PASS,"Source Object update sucessfull for Problem Description");

		
		String Temp1  = workOrderPo.geteleBillingTypelbl().getText();
		System.out.println("gettext value"+Temp1);	
		Assert.assertEquals(Temp1, sBillingTpeSOUServer, "Billing Type source update not updated sucessfully in Work Order");
		ExtentManager.logger.log(Status.PASS,"Source Object update sucessfull for billing type in Work Order");

		
	
		commonsPo.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());
		//-------------Validating work order not satisfying Qualification Criteria---------------- 
		// Navigation to WO
			workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName2);

			// Navigate to Field Service process
			workOrderPo.selectAction(commonsPo, sFieldServiceName);

			// Navigating to the checklist
			
			try {
				commonsPo.longPress(checklistPo.geteleChecklistName(sChecklistName));
			} catch (Exception e) {
				// TODO: handle exception
				ExtentManager.logger.log(Status.PASS,"Qualification criteria not met checklist not displayed");
			}
			
			Thread.sleep(GenericLib.iLowSleep);

			//Validating Work not satisfying checklist entry criteria		
			
			//Navigating back to work Orders
			commonsPo.tap(checklistPo.geteleBacktoWorkOrderlnk());
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
				
				checklistPo.geteleChecklistSectionNametab(sSection1Name).click();
				commonsPo.tap(checklistPo.geteleChecklistSectionNametab(sSection1Name));
				
			} catch (Exception e) {
				// TODO: handle exception
				ExtentManager.logger.log(Status.PASS,"Entry Criteria for checklist is validated sucessfully");
			}			


			//Navigating back to work Orders
			commonsPo.tap(checklistPo.geteleSubmitLnk());
			commonsPo.longPress(checklistPo.geteleChecklistPopupSubmit());
			
			//Navigating back to work Orders
			commonsPo.tap(checklistPo.geteleBacktoWorkOrderlnk());
			
			
			toolsPo.syncData(commonsPo);
			Thread.sleep(genericLib.iMedSleep);

			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(GenericLib.iLowSleep);
			commonsPo.tap(exploreSearchPo.getEleExploreIcn());
			workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName4);
			workOrderPo.selectAction(commonsPo, sFieldServiceName);

			// Navigating to the checklist
			commonsPo.longPress(checklistPo.geteleChecklistName(sChecklistName));
			commonsPo.tap(checklistPo.geteleChecklistSectionNametab(sSection1Name));
			//Thread.sleep(genericLib.iLowSleep);
			//hecklistPo.geteleChecklistAnsNumber(sNumberq).sendKeys(snumberwithoutjump);
			//commonsPo.tap(checklistPo.geteleNext());	
			commonsPo.tap(checklistPo.geteleSectionNextBtn(1));
		
			commonsPo.tap(checklistPo.geteleChecklistHelpIcn());
			Thread.sleep(genericLib.iLowSleep);
			System.out.println("Context count " + driver.getContextHandles().size());
			Set contextNames = driver.getContextHandles();
			System.out.println(driver.getContext());
			driver.context(contextNames.toArray()[2].toString());
			String url = driver.getCurrentUrl();
	        System.out.println(url);

			Assert.assertEquals(url, "https://www.google.com/", "Help url validated sucessfully");
			ExtentManager.logger.log(Status.PASS,"Help URL validated sucessfully");
	
			//------------------SERVER SIDE VALIDATIONS
			
			System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
			String ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName1+"')";
			String ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");	
			Assert.assertTrue(ChecklistQueryval.contains(schecklistStatus),"checklist completed is not synced to server");
			ExtentManager.logger.log(Status.PASS,"Checklist Completed status is displayed in Salesforce after sync");
			
			
			String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
			Assert.assertTrue(ChecklistAnsjson.contains(sSectionThreeq1Ans), "checklist section three  question answer is not synced to server");
			ExtentManager.logger.log(Status.PASS,"checklist section three text question answer is  synced to server");
			
			String ChecklistAnsjson1 = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
			Assert.assertTrue(ChecklistAnsjson1.contains(sNumberSectionJumpAns), "checklist section three  question answer is not synced to server");
			ExtentManager.logger.log(Status.PASS,"checklist section Number question answer is  synced to server");
			
			String sSoqlqueryWO = "Select+SVMXC__Billing_Type__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName1+"'"; 
			String sSoqlqueryWOProb = "Select+SVMXC__Problem_Description__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName1+"'"; 

			restServices.getAccessToken();
			String sBillTypeServer = restServices.restGetSoqlValue(sSoqlqueryWO,"SVMXC__Billing_Type__c");
			Assert.assertTrue(sBillTypeServer.equals(sBillingTpeSOUServer), "Billing type Picklist source object not syned to server");
			ExtentManager.logger.log(Status.PASS,"Billing type Picklist Source object update has synced to server");
			
			String sProblemDescServer = restServices.restGetSoqlValue(sSoqlqueryWOProb,"SVMXC__Problem_Description__c");
			Assert.assertTrue(sProblemDescServer.equals(sProblemDescriptionSOUServer), "Problem Description source object not syned to server");
			ExtentManager.logger.log(Status.PASS,"Problem Description Source object update has synced to server");
			
			
			
			
			
	}

}
