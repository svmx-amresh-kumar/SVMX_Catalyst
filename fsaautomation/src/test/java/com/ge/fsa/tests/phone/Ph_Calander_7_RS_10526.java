/*
*@author vinaya
 *  
 */
package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.ge.fsa.lib.CommonUtility;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class Ph_Calander_7_RS_10526 extends BaseLib {

	String sAccountName = null;
	String sProductName = null;
	String sFirstName = null;
	String sLastName = null;
	String sContactName = null;
	String sworkOrderID = null;
	String sSoqlQuery = null;
	String sworkOrderName = null;
	String sSqlWOQuery = null;
	String sEventsuject = null;

	@Test(retryAnalyzer = Retry.class)
	public void Ph_10526() throws Exception {
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-10526");
		}else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12114");

		}
		
		String sTestCaseID = "Ph_10526_Calender_7";

		lauchNewApp("false");
		commonUtility.deleteCalendarEvents(restServices, calendarPO, "SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices, calendarPO, "Event");

		String sRandomNumber = commonUtility.generateRandomNumber("");
		String sProformainVoice = "Proforma" + sRandomNumber;
		String sEventSubject = "Create Event from WO in Client";

		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		// Creating Account from API
		sAccountName = "RS_10526_Account";
		String sAccountId = restServices.restCreate("Account?", "{\"Name\":\"" + sAccountName + "\"}");

		// Creating Product from API
		sProductName = "RS_10526_Product";
		restServices.restCreate("Product2?", "{\"Name\":\"" + sProductName + "\" }");

		// Creating Contact from API
		sFirstName = "RS_10526";
		sLastName = "Contact";
		sContactName = sFirstName + " " + sLastName;
		System.out.println(sContactName);
		restServices.restCreate("Contact?", "{\"FirstName\": \"" + sFirstName + "\", \"LastName\": \"" + sLastName
				+ "\", \"AccountId\": \"" + sAccountId + "\"}");

		Thread.sleep(CommonUtility.iMedSleep);
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		// Creating the Work Order
		ph_CalendarPo.getEleCalendarBtn().click();
		// click on new icon
		ph_CalendarPo.getEleCreateNewBtn().click();

		commonUtility.custScrollToElementAndClick(ph_CalendarPo.getEleSelectProcessNewProcess("Create New Work Order"));

		// Account lookup
		ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_CreateNewPo.getEleAccountLookUp(), sAccountName);

		// contact lookup
		ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_CreateNewPo.getEleContactLookuptap(), sContactName);

		// product
		ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_CreateNewPo.getEleProductLookuptap(), sProductName);

		// priority
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_CreateNewPo.getElePriority(), "Low");

		// billing type
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_CreateNewPo.getElebillingtype(), "Loan");

		commonUtility.custScrollToElementAndClick(ph_CreateNewPo.getEleProformaInvoice());
		ph_CreateNewPo.getEleProformaInvoice().sendKeys(sProformainVoice);
		System.out.println(sProformainVoice);
		ph_WorkOrderPo.getEleAdd().click();

		ph_MorePo.syncData(commonUtility);
		Thread.sleep(5000);
		// Collecting the Work Order number from the Server.
		sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'" + sProformainVoice
				+ "\'";
		sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery, "Name");
		ExtentManager.logger.log(Status.PASS, "WO is created successfully from client");

		// Select the Work Order from the Recent items
		ph_RecentsItemsPo.selectRecentsItem(commonUtility, sworkOrderName);
		// To create a new Event for the given Work Order
		ph_WorkOrderPo.createNewEvent(commonUtility, sEventSubject, ph_CalendarPo);

		// Syncing the Data
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(5000);

		// Open the Work Order from the calendar
		ph_CalendarPo.openWoFromCalendar(sEventSubject);
		ExtentManager.logger.log(Status.PASS, "Event  is created  from WO is successfully from client");
		sSoqlQuery = "SELECT+id+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'" + sProformainVoice
				+ "\'";
		sworkOrderID = restServices.restGetSoqlValue(sSoqlQuery, "Id");

		sSqlWOQuery = "SELECT+name+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c+=\'" + sworkOrderID + "\'";
		sEventsuject = restServices.restGetSoqlValue(sSqlWOQuery, "Name");
		System.out.println(sEventsuject);

		assertEquals(sEventsuject, "Create Event from WO in Client");

		ExtentManager.logger.log(Status.PASS, "Test case passed successfully");

	}

}
