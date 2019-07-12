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

	@BeforeMethod
	public void initializeObject() throws IOException {

	}

	@Test()

	public void RS_10514() throws Exception {
	//	sSheetName = "RS_10513";
		sDeviceDate = driver.getDeviceTime().split(" ");

		String sTestCaseID = "Ph_10514_Calender_4";

		commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		String sRandomNumber = commonUtility.generateRandomNumber("");
		String sEventSubject = "EventName" + sRandomNumber;
		// sahi

	
	/*	commonUtility.executeSahiScript("appium/SCN_Calender_4_RS-10514_1.sah", sTestCaseID);
		
		*/

		 
		 
		// Pre Login to app
	//	ph_LoginHomePo.login(commonUtility, ph_MorePo);
/*
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		Thread.sleep(GenericLib.iMedSleep);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.getEleCreateNewBtn().click();
		
		try {
			if (BaseLib.sOSName.equalsIgnoreCase("android")) {
				commonUtility.isDisplayedCust(ph_CalendarPo.getEleSelectProcessNewProcess("Create New Event"));
			} else {
				commonUtility.isDisplayedCust(ph_CalendarPo.getEleSelectProcessNewProcess("Create New Event"));
			}
			}
			catch(Exception e){
				System.out.println(e);
			}
		
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		commonUtility.executeSahiScript("appium/SCN_Calender_4_RS-10514_2.sah", sTestCaseID);
		
		
		lauchNewApp("true");
		
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		Thread.sleep(GenericLib.iMedSleep);

	
		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.getEleCreateNewBtn().click();
		
		
		try {
			if (BaseLib.sOSName.equalsIgnoreCase("android")) {
				ph_CalendarPo.getEleSelectProcessNewProcess("Create New Event").click();
			} else {
				ph_CalendarPo.getEleSelectProcessNewProcess("Create New Event").click();
			}
			}
			catch(Exception e){
				System.out.println(e);
			}
			//commonUtility.tap(calendarPO.geteleNewClick());
		commonUtility.isDisplayedCust(ph_CalendarPo.getvalidatecreatenew());
			ExtentManager.logger.log(Status.PASS," Test case passed successfully");

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			// Create SVMX event from Create New Option
	
			ph_CalendarPo.getEleCalendarEventSubject().click();
			ph_CalendarPo.getEleCalendarEventSubject().sendKeys(sEventSubject);


			String hrs = commonUtility.gethrsfromdevicetime();//get the device time hrs 
			commonUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0, hrs, "00");
			commonUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 0, String.valueOf(Integer.parseInt(hrs)+3), "00");
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
*/	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//String sSalesforceuser = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "SALESFORCE_ID");
			String stechname = "Auto_Tech_1";//Add to config page

			//Globel setting should be set to servicemax event for tech2  
	        sObjectApi = "SVMXC__Service_Group_Members__c";
			sSqlEventQuery ="SELECT+id,SVMXC__Salesforce_User__c+from+SVMXC__Service_Group_Members__c+Where+Name+=\'"+stechname+"\'";	//add to sheet			
			String techID =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
			System.out.println(techID);
			String salesforceuserId =restServices.restGetSoqlValue(sSqlEventQuery,"SVMXC__Salesforce_User__c"); 
			System.out.println(salesforceuserId);
			//updating event
			 String sWOJson = "{\"SVMXC__Salesforce_User__c\":\"\"}";
			restServices.restUpdaterecord(sObjectApi,sWOJson,techID );
		
			
			lauchNewApp("false");
			ph_LoginHomePo.login(commonUtility, ph_MorePo,"TECH_USN_1");
			
			//ph_MorePo.configSync(commonUtility, ph_CalendarPo);
			
			Thread.sleep(3000);
			
			
			ph_CalendarPo.getEleCalendarBtn().click();
			ph_CalendarPo.getEleCreateNewBtn().click();
		
			if (BaseLib.sOSName.equalsIgnoreCase("android")) {
				ph_CalendarPo.getEleSelectProcessNewProcess("Create New Event").click();
			} else {
				ph_CalendarPo.getEleSelectProcessNewProcess("Create New Event").click();
			}
			
			ph_CalendarPo.getEleCalendarEventSubject().click();
			ph_CalendarPo.getEleCalendarEventSubject().sendKeys(sEventSubject);


			 String hrs = commonUtility.gethrsfromdevicetime();//get the device time hrs 
			commonUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0, hrs, "00");
			commonUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 0, String.valueOf(Integer.parseInt(hrs)+3), "00");
			ph_WorkOrderPo.getEleAdd().click();
			Thread.sleep(3000);  
			
			
			
			/*
		Thread.sleep(3000);
		commonUtility.custScrollcalender(ph_CalendarPo.getEleworkordernumonCalendar("A10513_SVMX_Event1"),true);
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
		commonUtility.custScrollcalender(ph_CalendarPo.getEleworkordernumonCalendar("A10513_SVMX_Event1"),true);
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
	
	*/
	}

}
