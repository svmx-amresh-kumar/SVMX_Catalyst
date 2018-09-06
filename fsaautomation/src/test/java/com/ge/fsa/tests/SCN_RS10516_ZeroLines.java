package com.ge.fsa.tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.ExploreSearchPO;
import com.ge.fsa.pageobjects.WorkOrderPO;

public class SCN_RS10516_ZeroLines extends BaseLib {
	String sAccountName = null;
	String sProductName = null;
	String sFirstName = null;
	String sLastName = null;
	String sContactName = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	@Test(enabled = true)
	public void SCN_RS10516() throws Exception {
		
		System.out.println("SCN_RS10516_ZeroLines");
		
		
		loginHomePo.login(commonsPo, exploreSearchPo);
		System.out.println(LocalDate.now().plusDays(1L));
		// Have a config Sync
		toolsPo.configSync(commonsPo);
		
		String sRandomNumber = commonsPo.generaterandomnumber("");
		// Creating Account from API
		sAccountName = "auto_account"+sRandomNumber;
		String sAccountId = restServices.restCreate("Account?","{\"Name\":\""+sAccountName+"\"}");
		
		// Creating Product from API
		sProductName = "auto_product1"+sRandomNumber;
		restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");
		
		// Creating Contact from API
		sFirstName = "auto_contact";
		sLastName = sRandomNumber;
		sContactName = sFirstName+" "+sLastName;
		System.out.println(sContactName);
		String sContactId = restServices.restCreate("Contact?","{\"FirstName\": \""+sFirstName+"\", \"LastName\": \""+sLastName+"\", \"AccountId\": \""+sAccountId+"\"}");
		toolsPo.syncData(commonsPo);
		// Creating the Work Order using API
		
		String sWorkorderID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__Company__c\": \""+sAccountId+"\", \"SVMXC__Contact__c\": \""+sContactId+"\", \"SVMXC__Priority__c\": \"High\"}");
		System.out.println("Work Order Id"+sWorkorderID);
		
		// Getting the Technician ID
		String sTech_Id = GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_ID");
		String sSoqlQueryTech = "SELECT+Id+from+SVMXC__Service_Group_Members__c+Where+SVMXC__Salesforce_User__c+=\'"+sTech_Id+"\'";
		restServices.getAccessToken();
		String sTechnician_ID = restServices.restGetSoqlValue(sSoqlQueryTech,"Id");
		// Assigning the Work order to Technician using Event
		String sEventId = restServices.restCreate("SVMXC__SVMX_Event__c?", "{\"Name\":\""+sRandomNumber+"\", \"SVMXC__Service_Order__c\":\""+sWorkorderID+"\", \"SVMXC__Technician__c\":\""+sTechnician_ID+"\", \"SVMXC__StartDateTime__c\":\""+LocalDate.now()+"\", \"SVMXC__EndDateTime__c\": \""+LocalDate.now().plusDays(1L)+"\"}");
		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+Id+=\'"+sWorkorderID+"\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");	
		
		// Syncing the Data of the Work Order
		toolsPo.syncData(commonsPo);
		// Click on the Work Order
		Thread.sleep(10000);
		workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		String sProcessname = "SFM Process for RS-10516";// Need to pass this from the Excel sheet
		workOrderPo.selectAction(commonsPo,sProcessname);
		Thread.sleep(2000);
		
		// Adding Parts - Warning message must be thrown
		
		// Adding Labor - An Error must be thrown
		
		
	}

}
