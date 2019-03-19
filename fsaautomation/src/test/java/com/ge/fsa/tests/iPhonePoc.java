package com.ge.fsa.tests;
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
import com.ge.fsa.iphone.pageobjects.Ip_CalendarPO;
import com.ge.fsa.iphone.pageobjects.Ip_LoginHomePO;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

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

public class iPhonePoc extends BaseLib
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

public void iphone() throws Exception
{	
		
		String sTestID = null;
		genericLib.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah", sTestID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		

		
		
		String sRandomNumber = commonsPo.generaterandomnumber("");
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
		
		
		
		ip_LoginHomePo.login(commonsPo, ip_MorePo);
	
		ip_MorePo.syncData(commonsPo);
		
		ip_CalendarPo.getEleCalendarBtn().click();
		//click on new icon
		ip_CalendarPo.getEleCreateNew().click();
		Thread.sleep(2000);
		
	commonsPo.custScrollToElementAndClick(ip_CalendarPo.getEleselectprocessnewprocess("Create New Work Order"));

		Thread.sleep(2000);
		
		//Account lookup 
		ip_CalendarPo.getEleAccountLookUp().click();
		
		ip_CalendarPo.getElelookupsearch().click();
		ip_CalendarPo.getElelookupsearch().sendKeys(sAccountName);
		
		ip_CalendarPo.getEleSearchListItem(sAccountName).click();
		
		
		//contact lookup
		ip_CalendarPo.getEleContactLookuptap().click();
		ip_CalendarPo.getElelookupsearchcontact().click();
		ip_CalendarPo.getElelookupsearchcontact().sendKeys(sContactName);
		ip_CalendarPo.getEleSearchListItem(sContactName).click();
		
		//product
		
		ip_CalendarPo.getEleproductLookuptap().click();
		ip_CalendarPo.getElelookupsearhproduct().click();
		ip_CalendarPo.getElelookupsearhproduct().sendKeys(sProductName);
		ip_CalendarPo.getEleSearchListItem(sProductName).click();
		
		//priority
		
		ip_CalendarPo.getElePriority().click();
		ip_CalendarPo.getEleCreatenewpriorityLow().click();
		
		//billing type
		ip_CalendarPo.getElebillingtype().click();
		ip_CalendarPo.getElebillingtypeloan().click();
		Thread.sleep(2000);
		
		
		commonsPo.custScrollToElementAndClick(ip_CalendarPo.getEleProformaInvoice());
		
		ip_CalendarPo.getEleProformaInvoice().sendKeys(sProformainVoice);
		System.out.println(sProformainVoice);
		ip_CalendarPo.getEleAdd().click();
	
		Thread.sleep(2000);
		ip_MorePo.syncData(commonsPo);
	
	// Collecting the Work Order number from the Server.
			String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
			restServices.getAccessToken();
			String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");
		
			//open WO from recents
		
		ip_RecentsPo.clickonWorkOrderfromrecents(sworkOrderName);
		Thread.sleep(2000);
		
		// To create a new Event for the given Work Order
		ip_WorkOrderPo.createNewEvent(commonsPo,sEventSubject,ip_CalendarPo);
		
		
		ip_MorePo.syncData(commonsPo);
		Thread.sleep(2000);
		
		// Open the Work Order from the calendar
			ip_CalendarPo.openWoFromCalendar(sEventSubject);
			
			
			//Adding parts to WO
			
		// To add Labor, Parts , Travel , Expense
		
				String sProcessname = "EditWoAutoTimesstamp";
				ip_WorkOrderPo.selectAction(commonsPo,ip_CalendarPo,sProcessname);
				Thread.sleep(2000);
				// Adding the Parts, Labor,Travel, expense childlines to the Work Order
				ip_WorkOrderPo.addParts(ip_CalendarPo ,sProductName);
				
				ip_WorkOrderPo.addLabor(commonsPo,ip_CalendarPo ,sProductName);
				ip_WorkOrderPo.getElesave().click();
				Thread.sleep(3000);
			
		
				sPrintReportSearch = "Work Order Service Report";
				ip_WorkOrderPo.selectAction(commonsPo,ip_CalendarPo,sPrintReportSearch);
				Thread.sleep(2000);
				ip_WorkOrderPo.getEleFinalize().click();
				Thread.sleep(2000);
				
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
				ip_MorePo.syncData(commonsPo);
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