/*
*@author MeghanaRao
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/AUT-62"
 */
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
		Thread.sleep(3000);
		
	commonsPo.custScrollToElementAndClick(ip_CalendarPo.getEleselectprocessnewprocess("Create New Work Order"));

		Thread.sleep(2000);
		
		//Account lookup 
		ip_CalendarPo.getEleAccountLookUp().click();
		Thread.sleep(2000);
		ip_CalendarPo.getElelookupsearch().click();
		ip_CalendarPo.getElelookupsearch().sendKeys(sAccountName);
		Thread.sleep(5000);
		ip_CalendarPo.getEleSearchListItem().click();
		Thread.sleep(2000);
		
		//contact lookup
		ip_CalendarPo.getEleContactLookuptap().click();
		ip_CalendarPo.getElelookupsearchcontact().click();
		ip_CalendarPo.getElelookupsearchcontact().sendKeys(sContactName);
		ip_CalendarPo.getEleSearchListItem().click();
		
		//product
		
		ip_CalendarPo.getEleproductLookuptap().click();
		ip_CalendarPo.getElelookupsearhproduct().click();
		ip_CalendarPo.getElelookupsearhproduct().sendKeys(sProductName);
		ip_CalendarPo.getEleSearchListItem().click();
		
		//priority
		
		ip_CalendarPo.getElePriority().click();
		ip_CalendarPo.getEleCreatenewpriorityLow().click();
		
		//billing type
		ip_CalendarPo.getElebillingtype().click();
		ip_CalendarPo.getElebillingtypeloan().click();
		Thread.sleep(5000);
		
		
		commonsPo.custScrollToElementAndClick(ip_CalendarPo.getEleProformaInvoice());
		
		ip_CalendarPo.getEleProformaInvoice().sendKeys(sProformainVoice);
		System.out.println(sProformainVoice);
		ip_CalendarPo.getEleAdd().click();
	
		Thread.sleep(3000);
		ip_MorePo.syncData(commonsPo);
	
	// Collecting the Work Order number from the Server.
			String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
			restServices.getAccessToken();
			String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");
		
			//open WO from recents
		
		ip_RecentsPo.clickonWorkOrderfromrecents(sworkOrderName);
		
		
		// To create a new Event for the given Work Order
		ip_WorkOrderPo.createNewEvent(commonsPo,sEventSubject,ip_CalendarPo);
		
		
		
		
		
		// Open the Work Order from the calendar
			ip_CalendarPo.openWoFromCalendar(sEventSubject);
			
			
			//Adding parts to WO
			
		// To add Labor, Parts , Travel , Expense
		
				String sProcessname = "EditWoAutoTimesstamp";
				ip_WorkOrderPo.selectAction(commonsPo,ip_CalendarPo,sProcessname);
				Thread.sleep(2000);
				// Adding the Parts, Labor,Travel, expense childlines to the Work Order
				ip_WorkOrderPo.addParts(ip_CalendarPo ,sProductName);
				Thread.sleep(5000);
				ip_WorkOrderPo.addLabor(commonsPo,ip_CalendarPo ,sProductName);
				ip_WorkOrderPo.getElesave().click();
				Thread.sleep(10000);
			
		
				sPrintReportSearch = "Work Order Service Report";
				ip_WorkOrderPo.selectAction(commonsPo,ip_CalendarPo,sPrintReportSearch);
				Thread.sleep(5000);
				ip_WorkOrderPo.getEleFinalize().click();
				Thread.sleep(10000);
				
}
	
}