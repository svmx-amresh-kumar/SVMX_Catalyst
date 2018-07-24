/*
 *  @author MeghanaRao
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
	String sTestCaseID=null; String sCaseWOID=null; String sCaseSahiFile=null;
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
	{sPrintReportSearch = "Print Service Report";

		System.out.println("Scenario 1");

		String sproformainvoice = commonsPo.generaterandomnumber("Proforma");
		String seventSubject = commonsPo.generaterandomnumber("EventName");
		loginHomePo.login(commonsPo, exploreSearchPo);
		createNewPO.createWorkOrder(commonsPo,sAccountName,sContactName, sProductName, "Medium", "Loan", sproformainvoice);
		toolsPo.syncData(commonsPo);
		Thread.sleep(2000);
		String soqlquery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sproformainvoice+"\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restapisoql(soqlquery);	
		recenItemsPO.clickonWorkOrder(commonsPo, sworkOrderName);
		// To create a new Event for the given Work Order
		workOrderPo.createNewEvent(commonsPo,seventSubject, "Test Description");

		calendarPO.openWofromCalendar(commonsPo, sworkOrderName);
		// To add Labor, Parts , Travel , Expense
		String sProcessname = "EditWoAutoTimesstamp";
		workOrderPo.selectAction(commonsPo,sProcessname);
		Thread.sleep(2000);
		workOrderPo.addParts(commonsPo, workOrderPo,sProductName);
		workOrderPo.addLaborParts(commonsPo, workOrderPo, sProductName, "Calibration", sProcessname);
		workOrderPo.addTravel(commonsPo, workOrderPo, sProcessname);
		workOrderPo.addExpense(commonsPo, workOrderPo, sExpenseType,sProcessname,sLineQty,slinepriceperunit);
		commonsPo.tap(workOrderPo.getEleClickSave());
		Thread.sleep(10000);
		workOrderPo.validateServiceReport(commonsPo, sPrintReportSearch, sworkOrderName);

		// Verifying if the Attachment is NULL before Sync
		String soqlqueryatatchbefore = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sworkOrderName+"\')";
		restServices.getAccessToken();
		String sattachmentidbefore = restServices.restsoql(soqlqueryatatchbefore, "Id");	
		assertNull(sattachmentidbefore); // This will verify if the Id retrived from the Work Order's attachment is not null.
		
		// Verifying the Childline values - Before the SYNC
		String soqlquerychildlinesbefore = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')";
		restServices.getAccessToken();
		String schildlinesbefore = restServices.restsoql(soqlquerychildlinesbefore, "totalSize");	
		if(schildlinesbefore.equals("0"))
				{
				NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines before Sync is "+schildlinesbefore, LogAs.PASSED, null);

				System.out.println("The attachment before Sync is "+schildlinesbefore);
				}
		else
		{
			NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines before Sync is "+schildlinesbefore, LogAs.FAILED, null);
			System.out.println("The attachment before Sync is "+schildlinesbefore);
		}
		// Syncing the Data
		toolsPo.syncData(commonsPo);
		Thread.sleep(5000);
		/**
		 * Verifying the values after the Syncing of the DATA
		 */
		// Verifying the Work details and the service report
		String soqlquery_attachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sworkOrderName+"\')";
		restServices.getAccessToken();
		String sattachmentIDAfter = restServices.restsoql(soqlquery_attachment, "Id");	
		assertNotNull(sattachmentIDAfter);
		
		// Verifying the childlines of the Same Work Order
		String soqlquerychildlineafter = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')";
		restServices.getAccessToken();
		String schildlinesafter = restServices.restsoql(soqlquerychildlineafter, "totalSize");	
		if(schildlinesafter.equals("0"))
		{
		NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines before Sync is "+schildlinesafter, LogAs.FAILED, null);

		System.out.println("The Childlines before Sync is "+schildlinesafter);
		}
		else
		{
			NXGReports.addStep("Testcase " + sTestCaseID + "The Childlines before Sync is "+schildlinesafter, LogAs.PASSED, null);
			System.out.println("The Childlines before Sync is "+schildlinesafter);
		}
		
		Thread.sleep(1000);
		// Verification of the fields of the childlines of Type = Expenses
		JSONArray jsonarrayexpenses = commonsPo.verifyPartsdetails(restServices, sworkOrderName,"Expenses");
		String expensetype = commonsPo.getJsonValue(jsonarrayexpenses, "SVMXC__Expense_Type__c");
		String lineqty = commonsPo.getJsonValue(jsonarrayexpenses, "SVMXC__Actual_Quantity2__c");
		assertEquals(expensetype, sExpenseType);
		assertEquals(lineqty, sLineQty);
		NXGReports.addStep("Testcase " + sTestCaseID + "The fields of Childlines of Type Expenses match", LogAs.PASSED, null);

		
		// Verification of the fields of the childlines of Type = Parts
		JSONArray jsonarrayparts = commonsPo.verifyPartsdetails(restServices, sworkOrderName,"Parts");
		String productID = commonsPo.getJsonValue(jsonarrayparts, "SVMXC__Product__c");
		String soqlproductname = "Select+Name+from+Product2+where+Id=\'"+productID+"\'";
		restServices.getAccessToken();
		String sproductname = restServices.restapisoql(soqlproductname);
		String lineqtyparts = commonsPo.getJsonValue(jsonarrayparts, "SVMXC__Actual_Quantity2__c");
		assertEquals(sproductname, sProductName);
		assertEquals(lineqtyparts, "1.0");
		NXGReports.addStep("Testcase " + sTestCaseID + "The fields of Childlines of Type Parts match", LogAs.PASSED, null);


	}
	

	
}