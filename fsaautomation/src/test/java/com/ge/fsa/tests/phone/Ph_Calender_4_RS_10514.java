/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

public class Ph_Calender_4_RS_10514 extends BaseLib {

	int iWhileCnt = 0;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sSqlEventQuery = null;
	String sSqlWOQuery = null;
	String sObjectProID = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sAccountName = null;
	String sFieldServiceName = null;
	String sproductname = null;
	String sSqlQuery = null;
	String[] sDeviceDate = null;
	String sEventIdSVMX14 = null;
	String sEventIdSVMX_1 = null;
	String sEventIdSVMX = null;
	String techname = "a240t000000GglLAAS";
	WebElement productname = null;
	String sSheetName = null;


	@Test()

	public void RS_10514() throws Exception {
	
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		String sRandomNumber = commonUtility.generateRandomNumber("");
		String sEventSubject = "SVMX" + sRandomNumber;
		String sEventSubjectSFDC = "SFDC" + sRandomNumber;
		// sahi


		commonUtility.executeSahiScript("appium/SCN_Calender_4_RS-10514_1.sah");

		 
		 
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.getEleCreateNewBtn().click();
		
		try {
			
				commonUtility.isDisplayedCust(ph_CalendarPo.getEleSelectProcessNewProcess("Create New Event"));
			
			}
			catch(Exception e){
				System.out.println(e);
			}
		
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		commonUtility.executeSahiScript("appium/SCN_Calender_4_RS-10514_2.sah");
		
		lauchNewApp("true");
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
	
		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.getEleCreateNewBtn().click();
		
		
		try {
			
				ph_CalendarPo.getEleSelectProcessNewProcess("Create New Event").click();
			}
			catch(Exception e){
				System.out.println(e);
			}
			
		commonUtility.isDisplayedCust(ph_CalendarPo.getvalidatecreatenew());
			ExtentManager.logger.log(Status.PASS," Test case passed successfully");

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			// Create SVMX event from Create New Option
	
			ph_CalendarPo.getEleCalendarEventSubject().click();
			ph_CalendarPo.getEleCalendarEventSubject().sendKeys(sEventSubject);


			String hrs = commonUtility.gethrsfromdevicetime();//get the device time hrs 
			commonUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0, hrs, "00");
			commonUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 0,String.format("%02d", Integer.parseInt(hrs) + 2), "00");
			ph_WorkOrderPo.getEleAdd().click();
			Thread.sleep(3000);
			ph_MorePo.getEleMoreBtn().click();
			ph_MorePo.syncData(commonUtility);
			Thread.sleep(5000);
			sObjectApi = "SVMXC__SVMX_Event__c";
			sSqlEventQuery = "SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+='" + sEventSubject + "'";
			sEventIdSVMX = restServices.restGetSoqlValue(sSqlEventQuery, "Id");
			System.out.println("created event id from server:" + sEventIdSVMX);
			Assert.assertNotNull(sEventIdSVMX, "Record not found");
			ExtentManager.logger.log(Status.PASS, "Create SVMX event from Create New  is Successful");
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			String TechName = CommonUtility.readExcelData(CommonUtility.sConfigPropertiesExcelFile, sSelectConfigPropFile, "TECH_ID_1");


			//Globel setting should be set to servicemax event for tech2  
 	        sObjectApi = "SVMXC__Service_Group_Members__c";
	        String sWOJson = "{\"SVMXC__Salesforce_User__c\":\"\"}";
			restServices.restUpdaterecord(sObjectApi, sWOJson, TechName);
			lauchNewApp("false");
		
			ph_LoginHomePo.login(commonUtility, ph_MorePo,"TECH_USN_1");
			
			Thread.sleep(3000);
			
			ph_CalendarPo.getEleCalendarBtn().click();
			ph_CalendarPo.getEleCreateNewEvent().click();
		
		String errormsg=ph_CalendarPo.getCalendarerrormsg().getText();
			String expectederrormsg="Unable to create a ServiceMax event. User is not associated with a ServiceMax Technician.";
		Assert.assertEquals(expectederrormsg, errormsg);
		
		String salesforceID = CommonUtility.readExcelData(CommonUtility.sConfigPropertiesExcelFile, sSelectConfigPropFile, "SALESFORCE_ID1");
		
		String sWOJson1 = "{\"SVMXC__Salesforce_User__c\":\"" + salesforceID + "\"}";
		restServices.restUpdaterecord(sObjectApi, sWOJson1, TechName);
		
		
		commonUtility.executeSahiScript("appium/SCN_Calender_4_RS-10514_3.sah");
		lauchNewApp("false");
		
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
	
		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.getEleCreateNewEvent().click();
		ph_CalendarPo.getEleCalendarEventSubject().click();
		ph_CalendarPo.getEleCalendarEventSubject().sendKeys(sEventSubjectSFDC+"\n");


		commonUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0, hrs, "00");
		commonUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 0,String.format("%02d", Integer.parseInt(hrs) + 3), "00");
		ph_WorkOrderPo.getEleAdd().click();
		Thread.sleep(3000);
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(5000);
		
		
		sObjectApi = "Event"; 
		restServices.getAccessToken(); 
		sSqlEventQuery="SELECT+Id+from+Event+Where+Subject+=\'"+sEventSubjectSFDC+"\'";
		  String sEventIdSFDC = restServices.restGetSoqlValue(sSqlEventQuery,"Id");
		  System.out.println("created event id from server:"+sEventIdSFDC);
		  Assert.assertNotNull(sEventIdSFDC, "Record not found");
		  ExtentManager.logger.log(Status.PASS,"Create SFDC event from Create New Option is Successful");
			
			
		
	}

}
