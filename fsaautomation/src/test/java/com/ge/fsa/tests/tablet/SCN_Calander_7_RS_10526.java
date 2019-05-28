/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/AUT-124"
 */
package com.ge.fsa.tests.tablet;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import org.json.JSONArray;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;



public class SCN_Calander_7_RS_10526 extends BaseLib
{
	
	
	int iWhileCnt =0;
	String sExploreSearch=null;String sWorkOrderID=null; String sWOJsonData=null;String sWOName=null; String sFieldServiceName=null; String sProductName1=null;String sProductName2=null; 
	String sActivityType=null;String sPrintReportSearch="Auto_PrintServiceReport";
	String sAccountName = null;
	String sProductName = null;
	String sFirstName = null;
	String sLastName = null;
	String sContactName = null;
	String sworkOrderID=null;

	@Test(retryAnalyzer=Retry.class)
public void RS_10526() throws Exception
{		
		lauchNewApp("false");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		
		
		String sRandomNumber = commonUtility.generaterandomnumber("");
		String sProformainVoice = "Proforma"+sRandomNumber;
		String sEventSubject = "Create Event from WO in Client";
		// Login to the Application.
		loginHomePo.login(commonUtility, exploreSearchPo);
		
		// Creating Account from API
		sAccountName = "RS_10526_Account";
		String sAccountId = restServices.restCreate("Account?","{\"Name\":\""+sAccountName+"\"}");
		
		// Creating Product from API
		sProductName = "RS_10526_Product";
		restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");
		
		
		// Creating Contact from API
		sFirstName = "RS_10526";
		sLastName = "Contact";
		sContactName = sFirstName+" "+sLastName;
		System.out.println(sContactName);
		restServices.restCreate("Contact?","{\"FirstName\": \""+sFirstName+"\", \"LastName\": \""+sLastName+"\", \"AccountId\": \""+sAccountId+"\"}");
		
		// Need to sync the data
		toolsPo.syncData(commonUtility);
		// Creating the Work Order
		createNewPO.createWorkOrder(commonUtility,sAccountName,sContactName, sProductName, "Medium", "Loan", sProformainVoice);
		toolsPo.syncData(commonUtility);
		Thread.sleep(2000);
		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");	
		// Select the Work Order from the Recent items
		recenItemsPO.clickonWorkOrder(commonUtility, sworkOrderName);
		// To create a new Event for the given Work Order
		workOrderPo.createNewEvent(commonUtility,sEventSubject, "Test Description");

		// Syncing the Data
		toolsPo.syncData(commonUtility);
		Thread.sleep(5000);
		calendarPO.VerifyWOInCalender(commonUtility,sworkOrderName);
		
		 sSoqlQuery = "SELECT+id+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		 sworkOrderID= restServices.restGetSoqlValue(sSoqlQuery,"Id");
		
		  String sSqlWOQuery = "SELECT+name+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c+=\'"+sworkOrderID+"\'";				
		String sEventsuject =restServices.restGetSoqlValue(sSqlWOQuery,"Name"); 
		System.out.println(sEventsuject);
		
		assertEquals(sEventsuject, "Create Event from WO in Client");
		
		ExtentManager.logger.log(Status.PASS,"Test case passed successfully");
}

	
}