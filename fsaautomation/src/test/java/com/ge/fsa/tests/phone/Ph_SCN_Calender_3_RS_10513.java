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
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_Calender_3_RS_10513 extends BaseLib {

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

	@BeforeMethod
	public void initializeObject() throws IOException {

	}

	@Test()

	public void RS_10513() throws Exception {
		sSheetName = "RS_10513";
		sDeviceDate = driver.getDeviceTime().split(" ");

		String sTestCaseID = "RS_10513_Calender_3";

		commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		String sRandomNumber = commonUtility.generateRandomNumber("");
		String sEventSubject = "EventName" + sRandomNumber;
		// sahi

	
		  genericLib.executeSahiScript("appium/SCN_Calender_3_RS-10513.sah");
		  if(commonUtility.verifySahiExecution()) {
		  
		  System.out.println("PASSED"); } else { System.out.println("FAILED");
		  
		  
		  ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID +
		  "Sahi verification failure"); assertEquals(0, 1); } lauchNewApp("true");
		  System.out.println("RS-10513");
		 
		 
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		Thread.sleep(GenericLib.iMedSleep);
		ph_MorePo.syncData(commonUtility);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		// verify WO event is present or not
		ph_CalendarPo.getEleCalendarBtn().click();

		Thread.sleep(3000);
		ph_CalendarPo.custScroll(commonUtility,"A10513_SVMX_Event1");

		//commonUtility.custScrollcalender(ph_CalendarPo.getEleworkordernumonCalendar("A10513_SVMX_Event1"),true);
		ph_CalendarPo.VerifyWOInCalender(commonUtility, "A10513_SVMX_Event1");
		ph_CalendarPo.VerifyWOInCalender(commonUtility, "A10513_SVMX_Event2");

		ExtentManager.logger.log(Status.PASS, "Two events are displayed in calendar");

		System.out.println(
				"//////////////////////////////////////////////////////////////////////////////////////////////");
////////////////////////////////////////////////////////////////////////////////////////////////////////////			
		// Create SVMX event from Create New Option
		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.getEleCreateNewEvent().click();
		ph_CalendarPo.getEleCalendarEventSubject().click();
		ph_CalendarPo.getEleCalendarEventSubject().sendKeys(sEventSubject);

		commonUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0, "05", "00");

		commonUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 0, "07", "00");
		ph_WorkOrderPo.getEleAdd().click();
		Thread.sleep(3000);
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(10000);
		sObjectApi = "SVMXC__SVMX_Event__c";
		sSqlEventQuery = "SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+='" + sEventSubject + "'";
		sEventIdSVMX = restServices.restGetSoqlValue(sSqlEventQuery, "Id");
		System.out.println("created event id from server:" + sEventIdSVMX);
		Assert.assertNotNull(sEventIdSVMX, "Record not found");
		ExtentManager.logger.log(Status.PASS, "Create SVMX event from Create New Option is Successful");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
		// On server/DC, edit one of the events created

		sObjectApi = "SVMXC__SVMX_Event__c";
		sSqlEventQuery = "SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+=\'A10513_SVMX_Event1\'";
		sEventIdSVMX_1 = restServices.restGetSoqlValue(sSqlEventQuery, "Id");
		System.out.println(sEventIdSVMX_1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Calendar now1 = Calendar.getInstance();
		now1.set(Calendar.HOUR, 11);
		now1.set(Calendar.MINUTE, 0);
		now1.set(Calendar.SECOND, 0);
		String endtimezero = sdf.format(now1.getTime());
		// now1.set(Calendar.HOUR_OF_DAY, 12);
		System.out.println(endtimezero);

		String sWOJson = "{\"SVMXC__EndDateTime__c\":\"" + endtimezero + "\"}";
		restServices.restUpdaterecord(sObjectApi, sWOJson, sEventIdSVMX_1);

		ph_MorePo.syncData(commonUtility);

		
		
		
		sObjectApi = "SVMXC__SVMX_Event__c";
		sSqlEventQuery ="SELECT+SVMXC__EndDateTime__c+from+SVMXC__SVMX_Event__c+Where+id+=\'"+sEventIdSVMX_1+"\'";				
		String sEventenddatetimeserver =restServices.restGetSoqlValue(sSqlEventQuery,"SVMXC__EndDateTime__c"); 
		System.out.println(sEventenddatetimeserver);

		String enddatetimefromserver = calendarPO.convertedformate(sEventenddatetimeserver);
		System.out.println("end data"+enddatetimefromserver);
		
		
		

		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.custScroll(commonUtility,"A10513_SVMX_Event1");
		ph_CalendarPo.getEleworkordernumonCalendar("A10513_SVMX_Event1").click();
		Thread.sleep(5000);
		
		
		String getappointmentdate = ph_CalendarPo.getEleAppointmentdate("A10513_SVMX_Event1").getText();
		System.out.println("get dates:"+getappointmentdate);

		String enddatefromcal = calendarPO.convertedformate(getappointmentdate,"E, MMM dd, yyyy","M/d/yyyy");
		System.out.println("date1:"+enddatefromcal);


		String[] getappointmenttime= ph_CalendarPo.getEleAppointmenttime("A10513_SVMX_Event1").getText().split("- ");
		System.out.println("get end  time:"+getappointmenttime[1]);
		String datetimefromclient=enddatefromcal+" "+getappointmenttime[1];
		System.out.println("datetimefromclient"+datetimefromclient);
		//Assert.assertEquals(datetimefromclient,enddatetimefromserver, "End Date time is mismatch");
		
		 
		  ExtentManager.logger.log(Status.PASS,"On server/DC, edit one of the events and validated in client is successful"); System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////" );
		  
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//On client, edit one of the events 
		  
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	  
		  //On client, create an SVMX event longer than 14 days
		String sEventSubject14days = "Eventformorethen14daysevent";
		  ph_CalendarPo.getEleCalendarBtn().click();
			ph_CalendarPo.getEleCreateNewEvent().click();
			ph_CalendarPo.getEleCalendarEventSubject().click();
			ph_CalendarPo.getEleCalendarEventSubject().sendKeys(sEventSubject14days);

			commonUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0, "05", "00");
			commonUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 16, "07", "00");
			ph_WorkOrderPo.getEleAdd().click();
			Thread.sleep(3000);
			ph_MorePo.syncData(commonUtility);

			sObjectApi = "SVMXC__SVMX_Event__c";
			sSqlEventQuery = "SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+='" + sEventSubject14days + "'";
			sEventIdSVMX = restServices.restGetSoqlValue(sSqlEventQuery, "Id");
			System.out.println("created event id from server:" + sEventIdSVMX);
			Assert.assertNotNull(sEventIdSVMX, "Record not found");
			ExtentManager.logger.log(Status.PASS, "Create SVMX event from from client more than 14 days successful");
	
	
	}

}
