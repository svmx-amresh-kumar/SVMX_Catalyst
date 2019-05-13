package com.ge.fsa.tests.phone;

import static org.testng.Assert.assertEquals;

import java.time.LocalDate;

import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.phone.Ph_WorkOrderPO;

public class Ph_SCN_ZeroLines_RS_10516 extends BaseLib{

	String sAccountName = null;
	String sProductName = null;
	String sFirstName = null;
	String sLastName = null;
	String sContactName = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10516() throws Exception {
		
		System.out.println("SCN_RS10516_ZeroLines");
		
		
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		System.out.println(LocalDate.now().plusDays(1L));
		// Have a config Sync
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		
		String sRandomNumber = commonUtility.generaterandomnumber("");
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
		toolsPo.syncData(commonUtility);
		// Creating the Work Order using API
		
		String sWorkorderID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__Company__c\": \""+sAccountId+"\", \"SVMXC__Contact__c\": \""+sContactId+"\", \"SVMXC__Priority__c\": \"High\"}");
		System.out.println("Work Order Id"+sWorkorderID);
		
		// Getting the Technician ID
		String sTech_Id = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "TECH_ID");
		String sSoqlQueryTech = "SELECT+Id+from+SVMXC__Service_Group_Members__c+Where+SVMXC__Salesforce_User__c+=\'"+sTech_Id+"\'";
		restServices.getAccessToken();
		String sTechnician_ID = restServices.restGetSoqlValue(sSoqlQueryTech,"Id");
		// Assigning the Work order to Technician using Event
		//String sEventId = restServices.restCreate("SVMXC__SVMX_Event__c?","{\"Name\":\""+sRandomNumber+"\", \"SVMXC__Service_Order__c\":\""+sWorkorderID+"\", \"SVMXC__Technician__c\":\""+sTechnician_ID+"\", \"SVMXC__StartDateTime__c\":\""+LocalDate.now()+"\", \"SVMXC__EndDateTime__c\": \""+LocalDate.now().plusDays(1L)+"\",\"SVMXC__WhatId__c\":\""+sWorkorderID+"\"}");
		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+Id+=\'"+sWorkorderID+"\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");	
		
		// Syncing the Data of the Work Order
		ph_MorePo.syncData(commonUtility);
		// Click on the Work Order
		Thread.sleep(10000);
		ph_WorkOrderPo.navigatetoWO(commonUtility, ph_ExploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);
		String sProcessname = "SFMProcessforRS_10516";// Need to pass this from the Excel sheet
		Thread.sleep(2000);
		ph_WorkOrderPo.selectAction(commonUtility, sProcessname);
		Thread.sleep(2000);
		
		
		ph_WorkOrderPo.selectFromPickList(commonUtility, ph_WorkOrderPo.getEleBillingTypeField(),"Contract");
		ph_WorkOrderPo.getEleSaveLnk().click();
		if(ph_WorkOrderPo.getEleChildLine2IssuesFound().isDisplayed())
		{
		
			//commonUtility.tap(workOrderPo.getEleChildLine2IssuesFound());
			// Adding Labor - An Error must be thrown
			assertEquals(ph_WorkOrderPo.getEleNoLaborError().isDisplayed(), true);
			// Adding Parts - Warning message must be thrown
			assertEquals(workOrderPo.getEleNoPartsEntry().isDisplayed(), true);
			// To verify if the Parts error is just a warning , Click on the Checkbox
			Thread.sleep(1000);
			workOrderPo.getElePartsIssueCheckbox().click();
			Thread.sleep(3000);
			commonUtility.tap(workOrderPo.getElePartsIssueCheckbox(),20,20);
			// Tap anywhere on the UI 
			commonUtility.tap(workOrderPo.getEleNoPartsEntry());
			ph_WorkOrderPo.getEleSaveLnk().click();
			// Now only 1 Issue must be shown on the UI
			Thread.sleep(2000);
			try {
				ph_WorkOrderPo.getEleChildLine1IssueFound().isDisplayed();
			}
			
			catch(Exception e) {
				ExtentManager.logger.log(Status.FAIL,"Only 1 Issue Found must be on the UI",MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());

			}
			
			ph_WorkOrderPo.addLabor(commonUtility, sProcessname);
			ph_WorkOrderPo.getEleSaveLnk().click();
			try {
				ph_WorkOrderPo.getEleChildLine1IssueFound().isDisplayed();
				ExtentManager.logger.log(Status.FAIL,"1 Issue Found must be on the UI",MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
			}
			
			catch(Exception e) {
				ExtentManager.logger.log(Status.PASS,"After Adding Labor No issues were Found");
			}
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"2 Issues Found Error message is not Popped Up",MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		}

		
	}


}
