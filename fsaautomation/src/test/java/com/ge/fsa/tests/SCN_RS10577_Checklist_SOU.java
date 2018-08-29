package com.ge.fsa.tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.WorkOrderPO;


public class SCN_RS10577_Checklist_SOU extends BaseLib{
	String sTestCaseID= null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sExploreChildSearchTxt = null;
	String sOrderStatusVal =null;
	String sEditProcessName = null;
	
	
	//checklist q's set--
			String sTextq = "Which City you are from?";
			String sPicklistq = "What is the Order Status?";
			String sNumberq = "What is the IdleTime?";
			String sDateq = "What is the Scheduled Date?";
			String sDatetimeq = "What is the ScheduledDatetime?";
			String sTextAns = null;	String sPicklistAns = null;String sNumberAns; String sDateAns=null;String sDatetimeAns=null;				
			
	//Checklist PreFill Values
			String sCityPrefill = "Delhi";
			String sOrderStatusPrefill = "Open";
			String sIdleTimePrefill = "30";
			String sScheduleddatePrefill = "8/28/18";
			String sScheduledDateTimePrefill = "8/28/18 02:42";
	
	//Source object update values
			
			String sBillingType = null;String sBillingTypeSOU= "Loan";
			String sOrderStatus= null;String sOrderStatusSOU = "Open";
			String sIdleTime = null;String sIdleTimeSOU= "30";
			String sScheduledDate = null;
			String[] sScheduledDateSOU=null; 
			String sScheduledDateTime = null;String sScheduledDateTimeSou ="8/28/18 02:42";				
			String sNoofTimesAssigned = null; String sNooftimesAssignedSOU = "2";
			String sProformaInvoice = null; String sProformaInvoiceSOU="Source Object Updated";
			
			
			
	@Test(enabled = true)
	public void SCN_RS10577() throws Exception {
		
		sScheduledDateSOU=driver.getDeviceTime().split(" ");;
		System.out.println(sScheduledDateSOU);
		sTestCaseID = "SCN_Checklist_1_RS-10577_SOU";
		sCaseWOID = "DATA_SCN_Checklist_1_RS-10577_SOU";
		

		//Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID, "ChecklistName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID, "EditProcessName");
																 
	
		//Rest to Create Workorder
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\"}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		
		
		
		//sWOName = "WO-00001266";

							
		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);		
		
		//Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		//toolsPo.configSync(commonsPo);			
		
		//Navigation to WO
	    workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);							
		
		 // Navigate to Field Service process
		workOrderPo.selectAction(commonsPo, sFieldServiceName);
		
		// Navigating to the checklist
		commonsPo.longPress(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(GenericLib.iLowSleep);
			
		//System.out.println("validating pre filled text question is showing up");
		sTextAns = checklistPo.geteleChecklistAnswerTextArea(sTextq).getAttribute("value");
		Assert.assertTrue(sTextAns.equals(sCityPrefill), "Text question not Prefilled.");
		ExtentManager.logger.log(Status.PASS,"Text question prefilled sucessfully");
			
		sPicklistAns = checklistPo.geteleChecklistAnsPicklist(sPicklistq).getAttribute("value");
		Assert.assertTrue(sPicklistAns.equals(sOrderStatusPrefill), "Picklist question not Prefilled.");
		ExtentManager.logger.log(Status.PASS,"Picklist question prefilled sucessfully");
		
		sNumberAns = checklistPo.geteleChecklistAnsNumber(sNumberq).getAttribute("value");
		Assert.assertTrue(sNumberAns.equals(sIdleTimePrefill), "Number question not Prefilled.");
		ExtentManager.logger.log(Status.PASS,"Number question prefilled sucessfully");

		
		sDateAns = checklistPo.geteleChecklistAnsDate(sDateq).getAttribute("value");
		Assert.assertTrue(sDateAns.equals(sScheduleddatePrefill), "Date question not Prefilled.");
		ExtentManager.logger.log(Status.PASS,"Date question prefilled sucessfully");

		sDatetimeAns = checklistPo.geteleChecklistAnsDate(sDatetimeq).getAttribute("value");
		System.out.println(sDatetimeAns);
		Assert.assertTrue(sDatetimeAns.equals(sScheduledDateTimePrefill), "DateTime question not Prefilled.");
		ExtentManager.logger.log(Status.PASS,"DateTime question prefilled sucessfully");
		
		
		
		
		//tapping next button
		commonsPo.tap(checklistPo.geteleNext());
		// submitting the checklist
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(checklistPo.eleChecklistSubmit());
		
		//Validation of required question lbl and issue found txt.
		Thread.sleep(GenericLib.iLowSleep);
				
		//submitting of checklist
		
		commonsPo.tap(checklistPo.eleChecklistSubmit());
		commonsPo.longPress(checklistPo.geteleChecklistPopupSubmit());
		
		
		//Navigating back to work Orders
		commonsPo.tap(checklistPo.geteleBacktoWorkOrderlnk());
		
		
		//Navigation back to Work Order
		Assert.assertTrue(checklistPo.getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		//NXGReports.addStep("Creation of Checklist OPDOC passed", LogAs.PASSED, null);	
		ExtentManager.logger.log(Status.PASS,"Back to Work Order after submitting checklist passed");

		Thread.sleep(GenericLib.iLowSleep);
		// String ans= workOrderPo.geteleProblemDescriptionlbl().getText();
		// System.out.println(ans);
		
		 toolsPo.syncData(commonsPo);
		
		 Thread.sleep(GenericLib.iMedSleep);
		 commonsPo.tap(exploreSearchPo.getEleExploreIcn());
		 
			
			 // Navigate to Field Service process
		 	workOrderPo.selectAction(commonsPo, sEditProcessName);
			//workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sEditProcessName);
		 	System.out.println(workOrderPo.getEleBillingTypeLst());
		 
		 	sBillingType = workOrderPo.getEleBillingTypeLst().getAttribute("value");
		 	Assert.assertEquals(workOrderPo.getEleBillingTypeLst().getAttribute("value"), sBillingTypeSOU, "Picklist Source Object is not updated");
			ExtentManager.logger.log(Status.PASS,"Source Object Update for Picklist Sucessfull");
			
			sIdleTime = workOrderPo.geteleIdleTimetxt().getAttribute("value");
			System.out.println(sIdleTime);
		 	Assert.assertEquals(workOrderPo.geteleIdleTimetxt().getAttribute("value"), sIdleTimeSOU, "Number Source Object is not updated");
			ExtentManager.logger.log(Status.PASS,"Source Object Update for Number with value  Sucessfull");

			sScheduledDateTime = workOrderPo.getEleScheduledDateTimeTxt().getAttribute("value");
			System.out.println(sScheduledDateTime);
		 	Assert.assertEquals(workOrderPo.getEleScheduledDateTimeTxt().getAttribute("value"), sScheduledDateTimeSou, "DateTime Source Object is not updated");
			ExtentManager.logger.log(Status.PASS,"Source Object Update for DateTime Sucessfull");
		
	
			sScheduledDate = workOrderPo.getEleScheduledDateLst().getAttribute("value");
			System.out.println(sScheduledDate);
			Assert.assertEquals(workOrderPo.getEleScheduledDateLst().getAttribute("value"), sScheduledDate, "Date Source Object is not updated");
			ExtentManager.logger.log(Status.PASS,"Source Object Update for Date with function Today Sucessfull");
			
			
			sProformaInvoice = workOrderPo.getEleProformaInvoiceTxt().getAttribute("value");
			Assert.assertEquals(workOrderPo.getEleProformaInvoiceTxt().getAttribute("value"), sProformaInvoiceSOU, "Text Source Object is not updated");
			ExtentManager.logger.log(Status.PASS,"Source Object Update for Text with Value Sucessfull");
			
			
			
			
	}
}
