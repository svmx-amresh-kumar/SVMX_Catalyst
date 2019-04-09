package com.ge.fsa.tests.phone;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.KeysRelatedAction;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.phone.Ph_CalendarPO;
import com.ge.fsa.pageobjects.phone.Ph_LoginHomePO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.Point;

public class Ph_Sanity1_Create_Debrief_EventCreation_OPDOC_Recent_RS_11179 extends BaseLib
{


	int iWhileCnt =0;
	String sTestCaseID="Scenario-1"; String sCaseWOID=null; String sCaseSahiFile=null;
	String sExploreSearch=null;String sWorkOrderID=null; String sWOJsonData=null;String sWOName=null; String sFieldServiceName=null; String sProductName1=null;String sProductName2=null; 
	String sActivityType=null;String sPrintReportSearch="Auto_PrintServiceReport";
	String sAccountName = null;
	String sProductName = null;
	String sFirstName = null;
	String sLastName = null;
	String sContactName = null;
	String sExpenseType = "Airfare";
	String sLineQty = "10.0";
	String slinepriceperunit = "1000";


	@Test()
	//@Test(retryAnalyzer=Retry.class)
	public void iphone() throws Exception
	{	


		genericLib.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah");
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Sahi verification is successful");




		String sRandomNumber = commonUtility.generaterandomnumber("");
		String sProformainVoice = "Proforma"+sRandomNumber;
		String sEventSubject = "EventName"+sRandomNumber;

		// Creating Account from API
		sAccountName = "auto_account"+sRandomNumber;
		String sAccountId = restServices.restCreate("Account?","{\"Name\":\""+sAccountName+"\"}");
		System.out.println(sAccountName);
		// Creating Product from API
		sProductName = "auto_product"+sRandomNumber;
		restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");
		System.out.println(sProductName);

		// Creating Contact from API
		sFirstName = "auto_contact";
		sLastName = sRandomNumber;
		sContactName = sFirstName+" "+sLastName;
		System.out.println(sContactName);
		restServices.restCreate("Contact?","{\"FirstName\": \""+sFirstName+"\", \"LastName\": \""+sLastName+"\", \"AccountId\": \""+sAccountId+"\"}");



		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		ph_MorePo.syncData(commonUtility);

		ph_CalendarPo.getEleCalendarBtn().click();
		//click on new icon
		ph_CalendarPo.getEleCreateNewBtn().click();

		commonUtility.custScrollToElementAndClick(ph_CalendarPo.getEleSelectProcessNewProcess("Create New Work Order"));

		//Account lookup 
		ph_CreateNewPo.selectPickListValue(commonUtility, ph_CreateNewPo.getEleAccountLookUp(), sAccountName);

		//contact lookup
		ph_CreateNewPo.selectPickListValue(commonUtility, ph_CreateNewPo.getEleContactLookuptap(), sContactName);

		//product
		ph_CreateNewPo.selectPickListValue(commonUtility, ph_CreateNewPo.getEleProductLookuptap(), sProductName);

		//priority
		ph_CreateNewPo.selectSelectionlistValue(commonUtility, ph_CreateNewPo.getElePriority(), "Low");

		//billing type
		ph_CreateNewPo.selectSelectionlistValue(commonUtility, ph_CreateNewPo.getElebillingtype(), "Loan");

		commonUtility.custScrollToElementAndClick(ph_CreateNewPo.getEleProformaInvoice());

		ph_CreateNewPo.getEleProformaInvoice().sendKeys(sProformainVoice);
		System.out.println(sProformainVoice);
		ph_WorkOrderPo.getEleAdd().click();

		ph_MorePo.syncData(commonUtility);

		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");

		//open WO from recents
		Thread.sleep(1000);
		ph_RecentsPo.selectRecentsItem(sworkOrderName);
		//Thread.sleep(2000);

		// To create a new Event for the given Work Order
		ph_WorkOrderPo.createNewEvent(commonUtility,sEventSubject,ph_CalendarPo);


		ph_MorePo.syncData(commonUtility);

		// Open the Work Order from the calendar
		ph_CalendarPo.openWoFromCalendar(sEventSubject);


		//Adding parts to WO

		// To add Labor, Parts , Travel , Expense

		String sProcessname = "EditWoAutoTimesstamp";
		ph_WorkOrderPo.selectAction(commonUtility,sProcessname);
		// Adding the Parts, Labor,Travel, expense childlines to the Work Order
		ph_WorkOrderPo.addParts(sProductName);

		ph_WorkOrderPo.addLabor(sProductName);
		ph_WorkOrderPo.getElesave().click();


		sPrintReportSearch = "Work Order Service Report";
		ph_WorkOrderPo.selectAction(commonUtility,sPrintReportSearch);
		ph_WorkOrderPo.getEleFinalize().click();

		// server validation 	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

		// Verifying if the Attachment is NULL before Sync
		String sSoqlQueryattachBefore = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sworkOrderName+"\')";

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
		Thread.sleep(genericLib.i30SecSleep);
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(genericLib.i30SecSleep);

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

		// Verification of the fields of the childlines of Type = Parts
		JSONArray sJsonArrayExpenses = restServices.restGetSoqlJsonArray("Select+SVMXC__Actual_Quantity2__c,+SVMXC__Actual_Price2__c,+SVMXC__Product__c,+SVMXC__Activity_Type__c,+SVMXC__Start_Date_and_Time__c,+SVMXC__End_Date_and_Time__c,+SVMXC__Expense_Type__c,+SVMXC__Work_Description__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Line_Type__c='Parts'+AND+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')");
		String sProductID = restServices.getJsonValue(sJsonArrayExpenses, "SVMXC__Product__c");
		String sSoqlProductName = "Select+Name+from+Product2+where+Id=\'"+sProductID+"\'";

		String sProductName = restServices.restGetSoqlValue(sSoqlProductName,"Name");
		String sLineQtyParts = restServices.getJsonValue(sJsonArrayExpenses, "SVMXC__Actual_Quantity2__c");
		assertEquals(sProductName, sProductName);
		assertEquals(sLineQtyParts, "1.0");
		ExtentManager.logger.log(Status.PASS,"The fields of Childlines of Type Parts match");


	}

}