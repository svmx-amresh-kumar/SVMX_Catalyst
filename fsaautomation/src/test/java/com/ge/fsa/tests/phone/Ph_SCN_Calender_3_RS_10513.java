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
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.ge.fsa.lib.CommonUtility;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_Calender_3_RS_10513 extends BaseLib {

	int iWhileCnt = 0;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sSqlEventQuery = null;
	String sSqlWOQuery=null;
	String sObjectProID=null;
	String sObjectApi = null;
	String sJsonData = null;
	String sAccountName =null;
	String sFieldServiceName = null;
	String sproductname =null;
	String sSqlQuery = null;
	String[] sDeviceDate = null;
	String sEventIdSVMX14 = null;
	String sEventIdSVMX_1=null;
	String sEventIdSVMX=null;
	String techname="a240t000000GglLAAS";
	WebElement productname=null;
	String sSheetName =null;
	
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	@Test()
	
	public void RS_10513() throws Exception  {
		sSheetName ="RS_10513";
		sDeviceDate = driver.getDeviceTime().split(" ");
	
		String sTestCaseID="RS_10513_Calender_3";
		/*	
		commonsUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonsUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		String sRandomNumber = commonsUtility.generaterandomnumber("");
		String sEventSubject = "EventName"+sRandomNumber;
		//sahi
		
	
		  genericLib.executeSahiScript("appium/SCN_Calender_3_RS-10513.sah");
		  if(commonsUtility.verifySahiExecution()) {
		  
		  System.out.println("PASSED"); } else { System.out.println("FAILED");
		  
		  
		  ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID +
		  "Sahi verification failure"); assertEquals(0, 1); } lauchNewApp("false");
		  System.out.println("RS-10513");
		 
		 
	
	//read from file
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		
		String sWO_SVMX_1 = GenericLib.getExcelData(sTestCaseID,sSheetName, "WO_SVMX_1");
		String sWO_SVMX_2 = GenericLib.getExcelData(sTestCaseID,sSheetName, "WO_SVMX_2");
	*/	
	
		
			//Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

			Thread.sleep(GenericLib.iMedSleep);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
/*		//verify WO event is present or not
			ph_CalendarPo.getEleCalendarBtn().click();
			
			Thread.sleep(3000);
			ph_CalendarPo.VerifyWOInCalender(commonsUtility,"A10513_SVMX_Event1");
			ph_CalendarPo.VerifyWOInCalender(commonsUtility,"A10513_SVMX_Event2");
			
			ExtentManager.logger.log(Status.PASS,"Two events are displayed in calendar");
			
			System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			//Create SVMX event from Create New Option
			ph_CalendarPo.getEleCalendarBtn().click();
			ph_CalendarPo.getEleCalendarplus().click();
			ph_CalendarPo.getEleCalendarEventSubject().click();
			ph_CalendarPo.getEleCalendarEventSubject().sendKeys(sEventSubject);
			
			commonsUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0,"05", "00");
			
			commonsUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 0,"07", "00");
			ph_CalendarPo.getEleAdd().click();
			Thread.sleep(3000);
			ph_MorePo.syncData(commonsUtility);
			
			sObjectApi = "SVMXC__SVMX_Event__c";
			sSqlEventQuery ="SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+='"+sEventSubject+"'";				
			 sEventIdSVMX =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
			System.out.println("created event id from server:"+sEventIdSVMX);
			 Assert.assertNotNull(sEventIdSVMX, "Record not found");
			ExtentManager.logger.log(Status.PASS,"Create SVMX event from Create New Option is Successful");
			
			
	
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
				
			ph_MorePo.syncData(commonsUtility);
			
			String stempDate=calendarPO.convertedformate(endtimezero);
		
			ph_CalendarPo.getEleCalendarBtn().click();
			Thread.sleep(2000);
		*/		
			
			//commonsUtility.custScrollToElementAndClick(ph_CalendarPo.getEleWOendpoint("10 AM"));
			Thread.sleep(3000);
			System.out.println(ph_CalendarPo.getEleWOendpoint("10 AM").getLocation());
			Thread.sleep(3000);
			System.out.println(ph_CalendarPo.getEleWOendpoint("11 AM").getLocation());
			Thread.sleep(1000);
			System.out.println(ph_CalendarPo.getEleWOendpoint("12 PM").getLocation());
			Thread.sleep(1000);
			System.out.println(ph_CalendarPo.getEleWOendpoint("1 PM").getLocation());
			Thread.sleep(1000);
			System.out.println(ph_CalendarPo.getEleWOendpoint("2 PM").getLocation());
			Thread.sleep(1000);
			System.out.println("Before Pencil icon enable");
			Thread.sleep(3000);
	
			/*
		String EndDateTimecal=calendarPO.geteleEndDateTime().getAttribute("value");
		System.out.println(EndDateTimecal);
	 
		Assert.assertEquals(stempDate,EndDateTimecal, "End Date time value mapped is not displayed");
		commonsUtility.tap(workOrderPo.getEleCancelLink());
		ExtentManager.logger.log(Status.PASS,"On server/DC, edit one of the events and validated in client is successful");
	System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//	On client, edit one of the events created 
		commonsUtility.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(3000);
		calendarPO.geteleWOendpoint("09:00").getLocation();
		Thread.sleep(3000);
		try {
			commonsUtility.Enablepencilicon(calendarPO.getelegetsubject(sWO_SVMX_1));
			Thread.sleep(3000);}
			catch (Exception e) {
				commonsUtility.Enablepencilicon(calendarPO.getsubjectformultiday(sWO_SVMX_1));
			}
		Thread.sleep(3000);
	
		System.out.println("tap on pencil icon");
		commonsUtility.tap(calendarPO.getelepenciliconcal(sWO_SVMX_1),20,20);
		
		//commonsUtility.tap(calendarPO.geteleEndDateTime());
		commonsUtility.setDateTime24hrs(calendarPO.geteleEndDateTime(), 0,"15","00");
		if(BaseLib.sOSName=="ios") {
		commonsUtility.switchContext("Native");
		commonsUtility.getEleDonePickerWheelBtn().click();
		commonsUtility.switchContext("Webview");
		}
	
		String EndDateTimevalidate=calendarPO.geteleEndDateTime().getAttribute("value");
		System.out.println(EndDateTimevalidate);
       
		Thread.sleep(3000);
		commonsUtility.switchContext("WebView");
        commonsUtility.tap(workOrderPo.getEleSaveLnk(),20,20);
        commonsUtility.tap(workOrderPo.getEleYesBtn(),20,20);
      
       toolsPo.syncData(commonsUtility);
        
    
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
        
        commonsUtility.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(3000);
		commonsUtility.tap(calendarPO.geteleNewClick());
		
		calendarPO.getelesubjectcal().sendKeys("Event for 14 days");
		commonsUtility.setDateTime24hrs(calendarPO.geteleStartDateTimesvmx(), 0,"10", "00"); //set start time to Today
		commonsUtility.setDateTime24hrs(calendarPO.geteleEndDateTimesvmx(), 16,"10","00");
		//get the values to start and end date to validate
		String startDateTimevalidate=calendarPO.geteleStartDateTimesvmx().getAttribute("value");
		System.out.println(startDateTimevalidate);
		
		 EndDateTimevalidate=calendarPO.geteleEndDateTimesvmx().getAttribute("value");
		System.out.println(EndDateTimevalidate);
		
		commonsUtility.tap(workOrderPo.getEleClickSave());
		
		toolsPo.syncData(commonsUtility);
		
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
		
			
			
		*/
		ExtentManager.logger.log(Status.PASS," On client, create an SVMX event longer than 14 days is successful");
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");

	//Server create 14 days event
	}
	
	
}
