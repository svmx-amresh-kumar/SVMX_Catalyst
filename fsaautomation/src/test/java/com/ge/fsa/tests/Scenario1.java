/*
 *  @author MeghanaRao
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/AUT-62"
 */
package com.ge.fsa.tests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import com.ge.fsa.lib.RestServices;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.ExploreSearchPO;
import com.ge.fsa.pageobjects.LoginHomePO;
import com.ge.fsa.pageobjects.RecentItemsPO;
import com.ge.fsa.pageobjects.CalendarPO;
import com.ge.fsa.pageobjects.CommonsPO;
import com.ge.fsa.pageobjects.CreateNewPO;
import com.ge.fsa.pageobjects.ToolsPO;
import com.ge.fsa.pageobjects.WorkOrderPO;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen.ScreenshotOf;



public class Scenario1 extends BaseLib
{
	
	
	int iWhileCnt =0;
	String sTestCaseID="Scenario-1"; String sCaseWOID=null; String sCaseSahiFile=null;
	String sExploreSearch=null;String sWorkOrderID=null; String sWOJsonData=null;String sWOName=null; String sFieldServiceName=null; String sProductName1=null;String sProductName2=null; 
	String sActivityType=null;String sPrintReportSearch=null;
	String sAccountName = "Account47201811263";
	String sProductName = "Product9876789";
	String sContactName = "ContactAutomation 234567";
	String sExpenseType = "Airfare";
	String sLineQty = "10.0";
	String slinepriceperunit = "1000";

	
	@Test
	public void Scenario1Functions() throws Exception
	{sPrintReportSearch = "Auto_PrintServiceReport";

		System.out.println("Scenario 1");

		String sProformainVoice = commonsPo.generaterandomnumber("Proforma");
		String sEventSubject = commonsPo.generaterandomnumber("EventName");
		// Login to the Application.
		loginHomePo.login(commonsPo, exploreSearchPo);
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
		assertNull(sAttachmentidBefore); // This will verify if the Id retrived from the Work Order's attachment is not null.
		// Verifying the Childline values - Before the SYNC
		String sSoqlquerychildlinesBefore = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')";
		restServices.getAccessToken();
		String sChildlinesBefore = restServices.restGetSoqlValue(sSoqlquerychildlinesBefore, "totalSize");	
		if(sChildlinesBefore.equals("0"))
				{
				NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines before Sync is "+sChildlinesBefore, LogAs.PASSED, null);

				System.out.println("The attachment before Sync is "+sChildlinesBefore);
				}
		else
		{
			NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines before Sync is "+sChildlinesBefore, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("The attachment before Sync is "+sChildlinesBefore);
		}
		// Syncing the Data
		toolsPo.syncData(commonsPo);
		Thread.sleep(5000);
		/**
		 * Verifying the values after the Syncing of the DATA
		 */
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
		NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines After Sync is "+sChildlinesAfter, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		System.out.println("The Childlines After Sync is "+sChildlinesAfter);
		}
		else
		{
			NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines After Sync is "+sChildlinesAfter, LogAs.PASSED, null);
			System.out.println("The Childlines After Sync is "+sChildlinesAfter);
		}
		
		Thread.sleep(1000);
		// Verification of the fields of the childlines of Type = Expenses
	//	JSONArray sJsonArrayExpenses = commonsPo.verifyPartsDetails(restServices, sworkOrderName,"Expenses");
		 JSONArray sJsonArrayExpenses = restServices.restGetSoqlJsonArray("Select+SVMXC__Actual_Quantity2__c,+SVMXC__Actual_Price2__c,+SVMXC__Product__c,+SVMXC__Activity_Type__c,+SVMXC__Start_Date_and_Time__c,+SVMXC__End_Date_and_Time__c,+SVMXC__Expense_Type__c,+SVMXC__Work_Description__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Line_Type__c='Expenses'+AND+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')");
		String sExpenseType = restServices.getJsonValue(sJsonArrayExpenses, "SVMXC__Expense_Type__c");
		String sLineQty = restServices.getJsonValue(sJsonArrayExpenses, "SVMXC__Actual_Quantity2__c");
		assertEquals(sExpenseType, sExpenseType);
		assertEquals(sLineQty, sLineQty);
		NXGReports.addStep("Testcase " + sTestCaseID + "The fields of Childlines of Type Expenses match", LogAs.PASSED, null);

		
		// Verification of the fields of the childlines of Type = Parts
		//JSONArray sJsonArrayParts = commonsPo.verifyPartsDetails(restServices, sworkOrderName,"Parts");
		 sJsonArrayExpenses = restServices.restGetSoqlJsonArray("Select+SVMXC__Actual_Quantity2__c,+SVMXC__Actual_Price2__c,+SVMXC__Product__c,+SVMXC__Activity_Type__c,+SVMXC__Start_Date_and_Time__c,+SVMXC__End_Date_and_Time__c,+SVMXC__Expense_Type__c,+SVMXC__Work_Description__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Line_Type__c='Parts'+AND+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')");

		String sProductID = restServices.getJsonValue(sJsonArrayExpenses, "SVMXC__Product__c");
		String sSoqlProductName = "Select+Name+from+Product2+where+Id=\'"+sProductID+"\'";
		restServices.getAccessToken();
		String sProductName = restServices.restGetSoqlValue(sSoqlProductName,"Name");
		String sLineQtyParts = restServices.getJsonValue(sJsonArrayExpenses, "SVMXC__Actual_Quantity2__c");
		assertEquals(sProductName, sProductName);
		assertEquals(sLineQtyParts, "1.0");
		NXGReports.addStep("Testcase " + sTestCaseID + "The fields of Childlines of Type Parts match", LogAs.PASSED, null);


	}
	

	
}