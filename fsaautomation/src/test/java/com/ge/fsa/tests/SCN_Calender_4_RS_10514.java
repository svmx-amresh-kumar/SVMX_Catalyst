/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_Calender_4_RS_10514 extends BaseLib {

	int iWhileCnt = 0;
	
	String stechname = null;
	String sSalesforceuser = null;
	String sSqlEventQuery = null;
	String sSqlWOQuery=null;
	String sObjectProID=null;
	String sObjectApi = null;
	String sJsonData = null;
	
	String sAccountName =null;
	String sFieldServiceName = null;
//String sproductname = "Proforma30082018102823product";
	String sproductname =null;
	String sSqlQuery = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	String sIBLastModifiedBy=null;
	String techname="a240t000000GglLAAS";
	WebElement productname=null;
	String sSheetName =null;
	
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	@Test(retryAnalyzer=Retry.class)
	public void RS_10514() throws Exception {
		sSheetName ="RS_10514";
		sDeviceDate = driver.getDeviceTime().split(" ");
	
		String sTestCaseID="RS_10514_Calender_4";
	
		commonsPo.deleteCalendarEvents(restServices,calendarPO);
		
		//sahi
		genericLib.executeSahiScript("appium/SCN_Calender_4_RS-10514_1.sah");
  		if(commonsPo.verifySahiExecution()) {
  			
  			System.out.println("PASSED");
  		}
  		else 
  		{
  			System.out.println("FAILED");
  			

  			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
  			assertEquals(0, 1);
  		}
  		lauchNewApp("true");
  		System.out.println("RS_10514");
	
  	//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
		//config sync
		toolsPo.configSync(commonsPo);
		
		commonsPo.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(3000);
		
		
		try {
		 calendarPO.geteleNewdisabled().click();
		}
		catch(Exception e){
			System.out.println(e);
		}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		genericLib.executeSahiScript("appium/SCN_Calender_4_RS-10514_2.sah", "sTestCaseID");
  		if(commonsPo.verifySahiExecution()) {
  			
  			System.out.println("PASSED");
  		}
  		else 
  		{
  			System.out.println("FAILED");
  			

  			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
  			assertEquals(0, 1);
  		}
  		lauchNewApp("true");
  		System.out.println("RS_10514");
		
  		loginHomePo.login(commonsPo, exploreSearchPo);
  		
	
  	//config sync
  			toolsPo.configSync(commonsPo);
  			
  			commonsPo.tap(calendarPO.getEleCalendarClick());
  			Thread.sleep(3000);
  			try {
  	  			 calendarPO.geteleNewdisabled().click();
  	  			}
  	  			catch(Exception e){
  	  				System.out.println(e);
  	  			}
  			commonsPo.tap(calendarPO.geteleNewClick());
  			calendarPO.getvalidatecreatenew().isDisplayed();
  			ExtentManager.logger.log(Status.PASS," Test case passed successfully");

  		//Create SVMX event from Create New Option
			
			calendarPO.getelesubjectcal().sendKeys("CreateEventRS_10514");
			commonsPo.setDateTime24hrs(calendarPO.geteleStartDateTimesvmx(), 0,"10", "00"); //set start time to Today
			commonsPo.setDateTime24hrs(calendarPO.geteleEndDateTimesvmx(), 0,"11","00");
			commonsPo.tap(workOrderPo.getEleClickSave());
			
			toolsPo.syncData(commonsPo);
			
			sObjectApi = "SVMXC__SVMX_Event__c";
			sSqlEventQuery ="SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+=\'CreateEventRS_10514\'";				
			String sEventIdSVMX =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
			System.out.println("created event id from server:"+sEventIdSVMX);
			ExtentManager.logger.log(Status.PASS,"Create SVMX event from Create New Option is Successful");
			System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
			
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			 System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
				
		      //  stechname = GenericLib.getExcelData(sTestCaseID,sSheetName, "TechName2");
				sSalesforceuser = GenericLib.getExcelData(sTestCaseID,sSheetName, "Salesforceuser");
				stechname="Auto_Tech_1";//Add to config page
		        
		        //Globel setting should be set to servicemax event for tech2  
		        sObjectApi = "SVMXC__Service_Group_Members__c";
				sSqlEventQuery ="SELECT+id+from+SVMXC__Service_Group_Members__c+Where+Name+=\'"+stechname+"\'";	//add to sheet			
				String techID =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
				System.out.println(techID);
				//updating event
				 String sWOJson = "{\"SVMXC__Salesforce_User__c\":\"\"}";
				restServices.restUpdaterecord(sObjectApi,sWOJson,techID );
				lauchNewApp("false");
							loginHomePo.login_tech2(commonsPo, exploreSearchPo);
			

				commonsPo.tap(calendarPO.getEleCalendarClick());
				Thread.sleep(3000);
				commonsPo.tap(calendarPO.geteleNewClick());
				
			calendarPO.getelesubjectcal().sendKeys("CreateEvent");
				commonsPo.setDateTime24hrs(calendarPO.geteleStartDateTimesvmx(), 0,"10", "00"); //set start time to Today
				commonsPo.setDateTime24hrs(calendarPO.geteleEndDateTimesvmx(), 0,"12","00");
				commonsPo.tap(workOrderPo.getEleClickSave());
		        
			String	gettextonsave=calendarPO.getvalidationmsgonsave().getText();
			System.out.println(gettextonsave);
			assertEquals(gettextonsave, "Unable to create event since there is no technician associated.");
			commonsPo.tap(toolsPo.getEleOkBtn());
			
			commonsPo.tap(workOrderPo.getelecancelbutton());
			commonsPo.tap(workOrderPo.geteleDiscardChangesbutton());
			
			commonsPo.tap(toolsPo.getEleToolsIcn());
			commonsPo.tap(toolsPo.geteleSignOutBtn());
			commonsPo.tap(toolsPo.getelepopSignOutBtn());
			Thread.sleep(5000);
			
				 sWOJson = "{\"SVMXC__Salesforce_User__c\":\""+sSalesforceuser+"\"}";
				restServices.restUpdaterecord(sObjectApi,sWOJson,techID );
				
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 			
		
			genericLib.executeSahiScript("appium/SCN_Calender_4_RS-10514_3.sah", "sTestCaseID");
	  		if(commonsPo.verifySahiExecution()) {
	  			
	  			System.out.println("PASSED");
	  		}
	  		else 
	  		{
	  			System.out.println("FAILED");
	  			

	  			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
	  			assertEquals(0, 1);
	  		}
	  		lauchNewApp("true");
	  		System.out.println("RS_10514");	
	  		
	  		loginHomePo.login(commonsPo, exploreSearchPo);
	  		toolsPo.configSync(commonsPo);
	  		
	 
	  		
	  	//Create SFDC event from Create New Option
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			commonsPo.tap(calendarPO.geteleNewClick());
			
			//commonsPo.tap(calendarPO.getelesubjectSFDCtap(),20,20);
			
			calendarPO.getelesubjectSFDCtap().sendKeys("SFDC event RS-10514");
			//commonsPo.tap(calendarPO.geteleclickupdate());
			commonsPo.setDateTime24hrs(calendarPO.geteleStartDateTimecal(), 0,"10", "00"); //set start time to Today
			commonsPo.setDateTime24hrs(calendarPO.geteleEndDateTimecal(), 0,"15","00");
			
			commonsPo.tap(workOrderPo.getEleClickSave());
			
			toolsPo.syncData(commonsPo);
			
			sObjectApi = "Event";
			restServices.getAccessToken();
			sSqlEventQuery ="SELECT+Id+from+Event+Where+Subject+=\'SFDC event RS-10514\'";				
			String sEventIdSFDC =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
			System.out.println("created event id from server:"+sEventIdSFDC);
			Assert.assertNotNull(sEventIdSFDC, "Record not found");
			ExtentManager.logger.log(Status.PASS,"Create SVMX event from Create New Option is Successful");
			System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");	
	  		
	 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 			
			
     
	}
	
	

}
