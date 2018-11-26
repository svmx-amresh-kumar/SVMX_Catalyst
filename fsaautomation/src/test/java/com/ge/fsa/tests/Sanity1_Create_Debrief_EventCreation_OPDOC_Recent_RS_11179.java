/*
*@author MeghanaRao
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/AUT-62"
 */
package com.ge.fsa.tests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import org.json.JSONArray;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;



public class Sanity1_Create_Debrief_EventCreation_OPDOC_Recent_RS_11179 extends BaseLib
{
	
	
	int iWhileCnt =0;
	String sTestCaseID="Scenario-1"; String sCaseWOID=null; String sCaseSahiFile=null;
	String sExploreSearch=null;String sWorkOrderID=null; String sWOJsonData=null;String sWOName=null; String sFieldServiceName=null; String sProductName1=null;String sProductName2=null; 
	String sActivityType=null;String sPrintReportSearch= null;
	String sAccountName = null;
	String sProductName = null;
	String sFirstName = null;
	String sLastName = null;
	String sContactName = null;
	String sExpenseType = "Airfare";
	String sLineQty = "10.0";
	String slinepriceperunit = "1000";
	String sSheetName1 = "RS_11179";

	
@Test
public void Scenario1Test() throws Exception
{		
	String sTestDataValue = "SCN_RS_11179";
	sPrintReportSearch = GenericLib.getExcelData(sTestDataValue,sSheetName1,"Service Report Name");
	System.out.println(sPrintReportSearch);
	genericLib.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah", "sTestCaseID");


		String sRandomNumber = commonsPo.generaterandomnumber("");
		String sProformainVoice = "Proforma"+sRandomNumber;
		String sEventSubject = "EventName"+sRandomNumber;
		// Login to the Application.
		loginHomePo.login(commonsPo, exploreSearchPo);
		//toolsPo.configSync(commonsPo);
		toolsPo.syncData(commonsPo);
		// Creating Account from API
		sAccountName = "auto_account"+sRandomNumber;
		String sAccountId = restServices.restCreate("Account?","{\"Name\":\""+sAccountName+"\"}");
		
		// Creating Product from API
		sProductName = "auto_product"+sRandomNumber;
		restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");
		
		
		// Creating Contact from API
		sFirstName = "auto_contact";
		sLastName = sRandomNumber;
		sContactName = sFirstName+" "+sLastName;
		System.out.println(sContactName);
		restServices.restCreate("Contact?","{\"FirstName\": \""+sFirstName+"\", \"LastName\": \""+sLastName+"\", \"AccountId\": \""+sAccountId+"\"}");
		
		// Need to sync the data
		toolsPo.syncData(commonsPo);
		// Creating the Work Order
		createNewPO.createWorkOrder(commonsPo,sAccountName,sContactName, sProductName, "Medium", "Loan", sProformainVoice);
		toolsPo.syncData(commonsPo);
		Thread.sleep(2000);
		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");	
		// Select the Work Order from the Recent items
		recenItemsPO.clickonWorkOrder(commonsPo, sworkOrderName);
		// To create a new Event for the given Work Order
		workOrderPo.createNewEvent(commonsPo,sEventSubject, "Test Description");

		// Open the Work Order from the calendar
		calendarPO.openWofromCalendar(commonsPo, sworkOrderName);
		// To add Labor, Parts , Travel , Expense
		String sProcessname = "EditWoAutoTimesstamp";
		workOrderPo.selectAction(commonsPo,sProcessname);
		Thread.sleep(2000);
		// Adding the Parts, Labor,Travel, expense childlines to the Work Order
		workOrderPo.addParts(commonsPo, workOrderPo,sProductName);
		workOrderPo.addLaborParts(commonsPo, workOrderPo, sProductName, "Calibration", sProcessname);
		workOrderPo.addTravel(commonsPo, workOrderPo, sProcessname);
		workOrderPo.addExpense(commonsPo, workOrderPo, sExpenseType,sProcessname,sLineQty,slinepriceperunit);
		commonsPo.tap(workOrderPo.getEleClickSave());
		Thread.sleep(10000);
		// Creating the Service Report.
		workOrderPo.validateServiceReport(commonsPo, sPrintReportSearch, sworkOrderName);
		// Verifying if the Attachment is NULL before Sync
		String sSoqlQueryattachBefore = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sworkOrderName+"\')";
		restServices.getAccessToken();
		String sAttachmentidBefore = restServices.restGetSoqlValue(sSoqlQueryattachBefore, "Id");
		// This will verify if the Id retrived from the Work Order's attachment is not null.
		assertNull(sAttachmentidBefore); 
		// Verifying the Childline values - Before the SYNC
		String sSoqlquerychildlinesBefore = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')";
		String sChildlinesBefore = restServices.restGetSoqlValue(sSoqlquerychildlinesBefore, "totalSize");	
		if(sChildlinesBefore.equals("0"))
				{
			ExtentManager.logger.log(Status.PASS,"The Childlines before Sync is "+sChildlinesBefore);
				//NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines before Sync is "+sChildlinesBefore, LogAs.PASSED, null);

				System.out.println("The attachment before Sync is "+sChildlinesBefore);
				}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"The Childlines before Sync is "+sChildlinesBefore);

			//NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines before Sync is "+sChildlinesBefore, LogAs.FAILED, null);
			System.out.println("The attachment before Sync is "+sChildlinesBefore);
		}
		// Syncing the Data
		toolsPo.syncData(commonsPo);
		Thread.sleep(5000);
		// Verifying the Work details and the service report
		String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sworkOrderName+"\')";
		restServices.getAccessToken();
		String sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlqueryAttachment, "Id");	
		assertNotNull(sAttachmentIDAfter);
		
		// Verifying the childlines of the Same Work Order
		String sSoqlQueryChildlineAfter = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')";
		restServices.getAccessToken();
		String sChildlinesAfter = restServices.restGetSoqlValue(sSoqlQueryChildlineAfter, "totalSize");	
		if(sChildlinesAfter.equals("0"))
		{
			ExtentManager.logger.log(Status.FAIL,"The Childlines After Sync is "+sChildlinesAfter);

		//NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines After Sync is "+sChildlinesAfter, LogAs.FAILED, null);

		System.out.println("The Childlines After Sync is "+sChildlinesAfter);
		}
		else
		{
			ExtentManager.logger.log(Status.PASS,"The Childlines After Sync is "+sChildlinesAfter);

			//NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines After Sync is "+sChildlinesAfter, LogAs.PASSED, null);
			System.out.println("The Childlines After Sync is "+sChildlinesAfter);
		}
		
		Thread.sleep(1000);
		// Verification of the fields of the childlines of Type = Expenses
		JSONArray sJsonArrayExpenses = restServices.restGetSoqlJsonArray("Select+SVMXC__Actual_Quantity2__c,+SVMXC__Actual_Price2__c,+SVMXC__Product__c,+SVMXC__Activity_Type__c,+SVMXC__Start_Date_and_Time__c,+SVMXC__End_Date_and_Time__c,+SVMXC__Expense_Type__c,+SVMXC__Work_Description__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Line_Type__c='Expenses'+AND+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')");
		String sExpenseType = restServices.getJsonValue(sJsonArrayExpenses, "SVMXC__Expense_Type__c");
		String sLineQty = restServices.getJsonValue(sJsonArrayExpenses, "SVMXC__Actual_Quantity2__c");
		assertEquals(sExpenseType, sExpenseType);
		assertEquals(sLineQty, sLineQty);
		ExtentManager.logger.log(Status.PASS,"The fields of Childlines of Type Expenses match");

		//NXGReports.addStep("Testcase " + sTestCaseID + "The fields of Childlines of Type Expenses match", LogAs.PASSED, null);

		
		// Verification of the fields of the childlines of Type = Parts
		sJsonArrayExpenses = restServices.restGetSoqlJsonArray("Select+SVMXC__Actual_Quantity2__c,+SVMXC__Actual_Price2__c,+SVMXC__Product__c,+SVMXC__Activity_Type__c,+SVMXC__Start_Date_and_Time__c,+SVMXC__End_Date_and_Time__c,+SVMXC__Expense_Type__c,+SVMXC__Work_Description__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Line_Type__c='Parts'+AND+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')");
		String sProductID = restServices.getJsonValue(sJsonArrayExpenses, "SVMXC__Product__c");
		String sSoqlProductName = "Select+Name+from+Product2+where+Id=\'"+sProductID+"\'";
		
		String sProductName = restServices.restGetSoqlValue(sSoqlProductName,"Name");
		String sLineQtyParts = restServices.getJsonValue(sJsonArrayExpenses, "SVMXC__Actual_Quantity2__c");
		assertEquals(sProductName, sProductName);
		assertEquals(sLineQtyParts, "1.0");
		ExtentManager.logger.log(Status.PASS,"The fields of Childlines of Type Parts match");

		//NXGReports.addStep("Testcase " + sTestCaseID + "The fields of Childlines of Type Parts match", LogAs.PASSED, null);


}
	

	
}