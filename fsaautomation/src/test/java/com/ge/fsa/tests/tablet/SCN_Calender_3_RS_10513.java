/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests.tablet;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.TouchAction;

public class SCN_Calender_3_RS_10513 extends BaseLib {

	
	
	String sSqlEventQuery = null;
	String sSqlWOQuery=null;
	String sObjectProID=null;
	String sObjectApi = null;
	String sJsonData = null;
	String sAccountName =null;
	String sFieldServiceName = null;

	String sSqlQuery = null;
	
	String sEventIdSVMX14 = null;
	String sEventIdSVMX_1=null;
	String sEventIdSVMX=null;
	
	
	String sSheetName =null;
	
	@Test(retryAnalyzer=Retry.class)
	//@Test()
	public void RS_10513() throws Exception {
		// JiraLink
		if (BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-10513");
		} else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12123");

		}

		sSheetName ="RS_10513";
		
	
		String sTestCaseID="RS_10513_Calender_3";
	
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		
		//sahi
		
		 
		  commonUtility.executeSahiScript("appium/SCN_Calender_3_RS-10513.sah");
		  lauchNewApp("false");
	
	//read from file
		
		String sWO_SVMX_1 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SVMX_1");
		String sWO_SVMX_2 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SVMX_2");
		
		
		
			//Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
	
			//config sync
			//toolsPo.configSync(commonsUtility);
			Thread.sleep(CommonUtility.iMedSleep);
			
			
			//Data Sync for WO's created
		//toolsPo.syncData(commonsUtility);
		
			Thread.sleep(CommonUtility.iMedSleep);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//verify WO event is present or not
			commonUtility.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			commonUtility.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonUtility,sWO_SVMX_1);
			calendarPO.VerifyWOInCalender(commonUtility,sWO_SVMX_2);
			
			ExtentManager.logger.log(Status.PASS,"Two events are displayed in calendar");
			
			System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			//Create SVMX event from Create New Option
		commonUtility.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			commonUtility.tap(calendarPO.geteleNewClick());
			
			calendarPO.getelesubjectcal().sendKeys("SVMX Event from calender New button");
			commonUtility.setDateTime24hrs(calendarPO.geteleStartDateTimesvmx(), 0,"10", "00"); //set start time to Today
			commonUtility.setDateTime24hrs(calendarPO.geteleEndDateTimesvmx(), 0,"13","00");
			commonUtility.tap(workOrderPo.getEleClickSave());
			
			toolsPo.syncData(commonUtility);
			
			sObjectApi = "SVMXC__SVMX_Event__c";
			sSqlEventQuery ="SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+=\'SVMX Event from calender New button\'";				
			 sEventIdSVMX =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
			System.out.println("created event id from server:"+sEventIdSVMX);
			 Assert.assertNotNull(sEventIdSVMX, "Record not found");;
			ExtentManager.logger.log(Status.PASS,"Create SVMX event from Create New Option is Successful");
			System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
//Assert nt null     
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
		//	On server/DC, edit one of the events created
			
			sObjectApi = "SVMXC__SVMX_Event__c";
			sSqlEventQuery ="SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+=\'A10513_SVMX_Event1\'";				
			 sEventIdSVMX_1 =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
			System.out.println(sEventIdSVMX_1);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Calendar now1 = Calendar.getInstance();
	        now1.set(Calendar.HOUR, 11);
	        now1.set(Calendar.MINUTE, 0);
	        now1.set(Calendar.SECOND, 0);
	        String    endtimezero=sdf.format(now1.getTime());
	       // now1.set(Calendar.HOUR_OF_DAY, 12);
	     System.out.println(endtimezero);
			
			String sWOJson="{\"SVMXC__EndDateTime__c\":\""+endtimezero+"\"}";
			restServices.restUpdaterecord(sObjectApi,sWOJson,sEventIdSVMX_1);
				
				toolsPo.syncData(commonUtility);
			
			String stempDate=calendarPO.convertedformate(endtimezero);
			
			commonUtility.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
		
			calendarPO.geteleWOendpoint("09:00").getLocation();
			Thread.sleep(3000);
			System.out.println("Before Pencil icon enable");
			Thread.sleep(3000);
			
			try {
				commonUtility.Enablepencilicon(calendarPO.getelegetsubject(sWO_SVMX_1));
				//tap on pencil icon
				System.out.println("tap on pencil icon");
				commonUtility.tap(calendarPO.getelepenciliconcal(sWO_SVMX_1),20,20);
				String EndDateTimecal=calendarPO.geteleEndDateTime().getAttribute("value");//dummy 
}
				catch (Exception e) {
					commonUtility.Enablepencilicon(calendarPO.getsubjectformultiday(sWO_SVMX_1));
					commonUtility.tap(calendarPO.getelepenciliconcalmultiday(sWO_SVMX_1),20,20);
					Thread.sleep(3000);
				}
			
		String EndDateTimecal=calendarPO.geteleEndDateTime().getAttribute("value");
		System.out.println(EndDateTimecal);
	 
		Assert.assertEquals(stempDate,EndDateTimecal, "End Date time value mapped is not displayed");
		commonUtility.tap(workOrderPo.getEleCancelLink());
		ExtentManager.logger.log(Status.PASS,"On server/DC, edit one of the events and validated in client is successful");
	System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//	On client, edit one of the events created 
		commonUtility.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(3000);
		calendarPO.geteleWOendpoint("09:00").getLocation();
		Thread.sleep(3000);
		try {
			commonUtility.Enablepencilicon(calendarPO.getelegetsubject(sWO_SVMX_1));
			Thread.sleep(3000);
			}
			catch (Exception e) {
				commonUtility.Enablepencilicon(calendarPO.getsubjectformultiday(sWO_SVMX_1));
			}
		Thread.sleep(3000);
	
		System.out.println("tap on pencil icon");
		commonUtility.tap(calendarPO.getelepenciliconcal(sWO_SVMX_1),20,20);
		
		//commonsUtility.tap(calendarPO.geteleEndDateTime());
		
	//	commonUtility.setDateTime24hrs(calendarPO.geteleEndDateTimesvmx(), 0,"11","00");
		commonUtility.setDateTime24hrs(calendarPO.geteleEndDateTime(), 0,"15","00");
		if(BaseLib.sOSName=="ios") {
		commonUtility.switchContext("Native");
		commonUtility.getEleDonePickerWheelBtn().click();
		commonUtility.switchContext("Webview");
		}
	
		String EndDateTimevalidate=calendarPO.geteleEndDateTime().getAttribute("value");
		System.out.println(EndDateTimevalidate);
       
		Thread.sleep(3000);
		commonUtility.switchContext("WebView");
        commonUtility.tap(workOrderPo.getEleSaveLnk(),20,20);
        commonUtility.tap(workOrderPo.getEleYesBtn(),20,20);
      
       toolsPo.syncData(commonUtility);
        
    
      sObjectApi = "SVMXC__SVMX_Event__c";
		sSqlEventQuery ="SELECT+SVMXC__EndDateTime__c+from+SVMXC__SVMX_Event__c+Where+id+=\'"+sEventIdSVMX_1+"\'";				
		String sEventenddatetimeserver =restServices.restGetSoqlValue(sSqlEventQuery,"SVMXC__EndDateTime__c"); 
		System.out.println(sEventenddatetimeserver);
		
		stempDate=calendarPO.convertedformate(sEventenddatetimeserver);
        
        Assert.assertEquals(stempDate,EndDateTimevalidate, "End Date time is mismatch");
        ExtentManager.logger.log(Status.PASS," On client, edit one of the event is successful");
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
	///////////////////////////////////////////////////////////////////////////////////////////////////		
		//On client, create an SVMX event longer than 14 days.
        
        commonUtility.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(3000);
		commonUtility.tap(calendarPO.geteleNewClick());
		
		calendarPO.getelesubjectcal().sendKeys("Event for 14 days");
		commonUtility.setDateTime24hrs(calendarPO.geteleStartDateTimesvmx(), 0,"10", "00"); //set start time to Today
		commonUtility.setDateTime24hrs(calendarPO.geteleEndDateTimesvmx(), 16,"10","00");
		//get the values to start and end date to validate
		String startDateTimevalidate=calendarPO.geteleStartDateTimesvmx().getAttribute("value");
		System.out.println(startDateTimevalidate);
		
		 EndDateTimevalidate=calendarPO.geteleEndDateTimesvmx().getAttribute("value");
		System.out.println(EndDateTimevalidate);
		
		commonUtility.tap(workOrderPo.getEleClickSave());
		
		toolsPo.syncData(commonUtility);
		
		sObjectApi = "SVMXC__SVMX_Event__c";
		sSqlEventQuery ="SELECT+id,SVMXC__StartDateTime__c,SVMXC__EndDateTime__c+from+SVMXC__SVMX_Event__c+Where+name+=\'Event for 14 days\'";				
		   sEventIdSVMX14 = restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
		System.out.println("created event id from server:"+sEventIdSVMX14);
        
		String sEventstartdateSVMX =restServices.restGetSoqlValue(sSqlEventQuery,"SVMXC__StartDateTime__c"); 
		System.out.println("created event id from server:"+sEventstartdateSVMX);
        
		String sEventenddateSVMX =restServices.restGetSoqlValue(sSqlEventQuery,"SVMXC__EndDateTime__c"); 
		System.out.println("created event id from server:"+sEventenddateSVMX);
        
		String startdatefromserver=calendarPO.convertedformate(sEventstartdateSVMX);
		
		String enddatefromserver=calendarPO.convertedformate(sEventenddateSVMX);
		
		Assert.assertEquals(startdatefromserver,startDateTimevalidate, "Start Date time is mismatch");
		Assert.assertEquals(enddatefromserver,EndDateTimevalidate, "End Date time is mismatch");
		
			
			
		
		ExtentManager.logger.log(Status.PASS," On client, create an SVMX event longer than 14 days is successful");
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");

	
	 
	 
	}
	
	
}
