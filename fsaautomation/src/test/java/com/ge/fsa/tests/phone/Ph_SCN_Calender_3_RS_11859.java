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

public class Ph_SCN_Calender_3_RS_11859 extends BaseLib {

	String sSqlEventQuery = null;
	String sObjectApi = null;
	String sEventIdSVMX_1 = null;
	String sEventIdSFDC = null;
	String sSheetName =null;
	
	@Test()

	public void Calender_3_RS_11859() throws Exception {
		//sSheetName = "RS_10513";
		

		String sTestCaseID = "Calender_3_RS_11859";

		commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		String sRandomNumber = commonUtility.generateRandomNumber("");
		String sEventSubject = "SFDC_" + sRandomNumber;
		// sahi

	
		 commonUtility.executeSahiScript("appium/SCN_Calender_3_RS-11859.sah");
			
			

		 
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		Thread.sleep(CommonUtility.iMedSleep);
		ph_MorePo.syncData(commonUtility);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		// verify WO event is present or not
		ph_CalendarPo.getEleCalendarBtn().click();

		Thread.sleep(3000);
		
		ph_CalendarPo.custScroll(commonUtility,"A11859_SFDC_Event1");
		ph_CalendarPo.VerifyWOInCalender(commonUtility, "A11859_SFDC_Event1");
		ph_CalendarPo.VerifyWOInCalender(commonUtility, "A11859_SFDC_Event2");

		ExtentManager.logger.log(Status.PASS, "Two events are displayed in calendar");

		System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
////////////////////////////////////////////////////////////////////////////////////////////////////////////			
		// Create SVMX event from Create New Option
		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.getEleCreateNewEvent().click();
		ph_CalendarPo.getEleCalendarEventSubject().click();
		ph_CalendarPo.getEleCalendarEventSubject().sendKeys(sEventSubject+"\n");

		commonUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0, "05", "00");

		commonUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 0, "07", "00");
		ph_WorkOrderPo.getEleAdd().click();
		Thread.sleep(3000);
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(5000);
		
		
		sObjectApi = "Event"; 
		restServices.getAccessToken(); 
		sSqlEventQuery="SELECT+Id+from+Event+Where+Subject+=\'"+sEventSubject+"\'";
		  String sEventIdSFDC = restServices.restGetSoqlValue(sSqlEventQuery,"Id");
		  System.out.println("created event id from server:"+sEventIdSFDC);
		  Assert.assertNotNull(sEventIdSFDC, "Record not found");
		  ExtentManager.logger.log(Status.PASS,"Create SFDC event from Create New Option is Successful");
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
		// On server/DC, edit one of the events created

		  sObjectApi = "Event"; 
		  sSqlEventQuery ="SELECT+id+from+Event+Where+Subject+=\'A11859_SFDC_Event1\'"; 
		  sEventIdSVMX_1 =restServices.restGetSoqlValue(sSqlEventQuery,"Id");
		  System.out.println(sEventIdSVMX_1);
		  

		  String endtimezero=	ph_CalendarPo.Addinghrstosfdcformat(11);

		String sWOJson="{\"EndDateTime\":\""+endtimezero+"\"}";
		  restServices.restUpdaterecord(sObjectApi,sWOJson,sEventIdSVMX_1);
		ph_MorePo.syncData(commonUtility);

		
		sObjectApi = "Event";
		sSqlEventQuery ="SELECT+EndDateTime+from+Event+Where+id+=\'"+sEventIdSVMX_1+"\'";				
		String sEventenddatetimeserver =restServices.restGetSoqlValue(sSqlEventQuery,"EndDateTime"); 
		System.out.println(sEventenddatetimeserver);

		String enddatetimefromserver = calendarPO.convertedformate(sEventenddatetimeserver);
		System.out.println("end data"+enddatetimefromserver);
		
		
		

		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.custScroll(commonUtility,"A11859_SFDC_Event1");
		ph_CalendarPo.getEleworkordernumonCalendar("A11859_SFDC_Event1").click();
		Thread.sleep(5000);
		
		
		String getappointmentdate = ph_CalendarPo.getEleAppointmentdate("A11859_SFDC_Event1").getText();
		System.out.println("get dates:"+getappointmentdate);

		String enddatefromcal = calendarPO.convertedformate(getappointmentdate,"E, MMM dd, yyyy","M/d/yyyy");
		System.out.println("date1:"+enddatefromcal);


		String[] getappointmenttime= ph_CalendarPo.getEleAppointmenttime("A11859_SFDC_Event1").getText().split("- ");
		System.out.println("get end  time:"+getappointmenttime[1]);
		String datetimefromclient=enddatefromcal+" "+getappointmenttime[1];
		System.out.println("datetimefromclient"+datetimefromclient);
		Assert.assertEquals(datetimefromclient,enddatetimefromserver, "End Date time is mismatch");
		
		 
		  ExtentManager.logger.log(Status.PASS,"On server/DC, edit one of the events and validated in client is successful"); System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////" );
		  
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//On client, edit one of the events 
		  
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	  
		  //On client, create an SVMX event longer than 14 days
		String sEventSubject14days = "Eventformorethen14daysevent";
		  ph_CalendarPo.getEleCalendarBtn().click();
			ph_CalendarPo.getEleCreateNewEvent().click();
			ph_CalendarPo.getEleCalendarEventSubject().click();
			ph_CalendarPo.getEleCalendarEventSubject().sendKeys(sEventSubject14days+"\n");

			commonUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0, "05", "00");
			commonUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 16, "07", "00");
			ph_WorkOrderPo.getEleAdd().click();
			Thread.sleep(3000);
			

			//resolve conflict
			ph_MorePo.getEleMoreBtn().click();
			ph_MorePo.getEleDataSync().click();
			Thread.sleep(200);
			ph_MorePo.getEleSyncNow().click();
			commonUtility.waitForElementNotVisible(ph_MorePo.getEleSyncing(), 300);
			try {
				// Verification of successful sync
				Assert.assertTrue(commonUtility.isDisplayedCust(ph_MorePo.getEleDataSynccompleted()),
						"Data sync is not successfull");
				ExtentManager.logger.log(Status.PASS, "Data Sync is successfull");
			} catch (Error e) {
		Thread.sleep(5000);
				ph_MorePo.getEleResolveBtn().click();
				ph_MorePo.getEleResolveConflict().click();
				ph_MorePo.getEleSyncNow().click();
				Thread.sleep(3000);
				ph_MorePo.getEleDataSync().click();
				commonUtility.waitforElement(ph_MorePo.getEleDataSynccompleted(), 300);
				Assert.assertTrue(commonUtility.isDisplayedCust(ph_MorePo.getEleDataSynccompleted()),
						"Data sync is not successfull");
				ExtentManager.logger.log(Status.PASS, "Data Sync is successfull");
			}
			

			sObjectApi = "Event";
		
			sSqlEventQuery = "SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+='" + sEventSubject14days + "'";
			String sEventIdSVMX = restServices.restGetSoqlValue(sSqlEventQuery, "Id");
			System.out.println("created event id from server:" + sEventIdSVMX);
			Assert.assertNull(sEventIdSVMX, "Record not found");
			ExtentManager.logger.log(Status.PASS, "Create SFDC event from from client more than 14 days successful");
	
	
	}

}
