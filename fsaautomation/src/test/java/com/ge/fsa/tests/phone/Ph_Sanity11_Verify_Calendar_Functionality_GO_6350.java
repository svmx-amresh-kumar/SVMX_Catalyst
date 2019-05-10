/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.ge.fsa.lib.CommonUtility;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Ph_Sanity11_Verify_Calendar_Functionality_GO_6350 extends BaseLib {

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

	public void Calender_6350() throws Exception {
		//sSheetName = "RS_10513";
		sDeviceDate = driver.getDeviceTime().split(" ");

		String sTestCaseID = "Calender_6350";

		// commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		// commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		String sRandomNumber = commonUtility.generaterandomnumber("");
		String sEventSubject = "Event_" + sRandomNumber;
		// sahi

		/*	genericLib.executeSahiScript("appium/Ph_FON_6350.sah", sTestCaseID);
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID +  "Sahi verification is successful");*/
		
		// read from file
		/*sExploreSearch = GenericLib.getExcelData(sTestCaseID, sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, sSheetName, "ProcessName");

		String sWO_SVMX_1 = GenericLib.getExcelData(sTestCaseID, sSheetName, "WO_SVMX_1");
		String sWO_SVMX_2 = GenericLib.getExcelData(sTestCaseID, sSheetName, "WO_SVMX_2");
*/
		/*	// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		ph_MorePo.configSync(commonUtility,ph_CalendarPo);
		Thread.sleep(GenericLib.iMedSleep);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////			
		// Create SVMX event from Create New Option
		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.getEleCreateNewEvent().click();
		ph_CalendarPo.getEleCalendarEventSubject().click();
		ph_CalendarPo.getEleCalendarEventSubject().sendKeys(sEventSubject);

		
		String hrs = commonUtility.gethrsfromdevicetime();//get the device time hrs 
		commonUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0, hrs, "00");
		int hr=Integer.parseInt(hrs)+3;	
		hrs=String.valueOf(hr);//add one hr to device time
		commonUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 0, hrs, "00");
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
		ExtentManager.logger.log(Status.PASS, "Create SVMX event from Create New Option is Successful");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
		// On server/DC, edit one of the events created

		sObjectApi = "SVMXC__SVMX_Event__c";
		sSqlEventQuery = "SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+='" +sEventSubject +"'";
		sEventIdSVMX_1 = restServices.restGetSoqlValue(sSqlEventQuery, "Id");
		System.out.println(sEventIdSVMX_1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Calendar now1 = Calendar.getInstance();
		now1.set(Calendar.HOUR, 6);
		now1.set(Calendar.MINUTE, 0);
		now1.set(Calendar.SECOND, 0);
		String endtimezero = sdf.format(now1.getTime());
		// now1.set(Calendar.HOUR_OF_DAY, 12);
		System.out.println(endtimezero);

		String sWOJson = "{\"SVMXC__EndDateTime__c\":\"" + endtimezero + "\"}";
		restServices.restUpdaterecord(sObjectApi, sWOJson, sEventIdSVMX_1);

		
		sObjectApi = "SVMXC__SVMX_Event__c";
		sSqlEventQuery ="SELECT+SVMXC__EndDateTime__c+from+SVMXC__SVMX_Event__c+Where+id+=\'"+sEventIdSVMX_1+"\'";				
		String sEventenddatetimeserver =restServices.restGetSoqlValue(sSqlEventQuery,"SVMXC__EndDateTime__c"); 
		System.out.println(sEventenddatetimeserver);
		
		String enddatetimefromserver = calendarPO.convertedformate(sEventenddatetimeserver);
		System.out.println("end data"+enddatetimefromserver);
		
		
	ph_MorePo.syncData(commonUtility);
	Thread.sleep(5000);
		ph_CalendarPo.getEleCalendarBtn().click();
		
	
		
		ph_CalendarPo.getEleworkordernumonCalendar(sEventSubject).click();
		Thread.sleep(2000);
		
		
		String getappointmentdate = ph_CalendarPo.getEleeventdate().getAttribute("text");
		System.out.println("get dates:"+getappointmentdate);

		String enddatefromcal = calendarPO.convertedformate(getappointmentdate,"E, MMM dd, yyyy","M/d/yyyy");
		System.out.println("date1:"+enddatefromcal);
		
		
		String[] getappointmenttime= ph_CalendarPo.getEleeventtime().getAttribute("text").split("- ");
		System.out.println("get end  time:"+getappointmenttime[1]);
		String datetimefromclient=enddatefromcal+" "+getappointmenttime[1];
		System.out.println("datetimefromclient"+datetimefromclient);
		Assert.assertEquals(datetimefromclient,enddatetimefromserver, "End Date time is mismatch");
		ExtentManager.logger.log(Status.PASS,"On server/DC, edit one of the events and validated in client is successful");
		System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
		
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\"}";
		sObjectApi = "SVMXC__Service_Order__c?";
		
		String sObjectWOID = restServices.restCreate(sObjectApi,sJsonData);
		String sSqlWOQuery1 = "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectWOID+"\'";				
		String WOname = restServices.restGetSoqlValue(sSqlWOQuery1,"Name"); 
		
		
		
		 sWOJson = "{\"SVMXC__Service_Order__c\":\""+sObjectWOID+"\",\"SVMXC__WhatId__c\":\""+sObjectWOID+"\"}";
		restServices.restUpdaterecord("SVMXC__SVMX_Event__c", sWOJson, sEventIdSVMX_1);
		ph_MorePo.syncData(commonUtility);
		
		Thread.sleep(5000);
		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.getEleworkordernumonCalendar(sEventSubject).click();
		String WOfromcalender = ph_CalendarPo.getEleworkordernumonCalendar(WOname).getAttribute("text");
		System.out.println(WOfromcalender);
		ph_CalendarPo.getEleworkordernumonCalendar(WOname).click();
		Thread.sleep(2000);
		Assert.assertTrue(ph_WorkOrderPo.getEleActionsLnk().isDisplayed());
		ExtentManager.logger.log(Status.PASS," Attach a WO to event verification is sucessful");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//setting to SFDC event
		genericLib.executeSahiScript("appium/SCN_Calender_4_RS-10514_3.sah", sTestCaseID);
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID +  "Sahi verification is successful");
		
		ph_MorePo.configSync(commonUtility,ph_CalendarPo);
		
		
		//create SFDC event from calender
		String SFDC_Event = "SFDC"+sEventSubject;
		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.getEleCreateNewEvent().click();
		ph_CalendarPo.getEleCalendarEventSubject().click();
		ph_CalendarPo.getEleCalendarEventSubject().sendKeys(SFDC_Event);

		
		 String hrs1 = commonUtility.gethrsfromdevicetime();//get the device time hrs 
		commonUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0, hrs1, "00");
		 int hr1 = Integer.parseInt(hrs1)+3;	
		hrs1=String.valueOf(hr1);//add one hr to device time
		commonUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 0, hrs1, "00");
		ph_WorkOrderPo.getEleAdd().click();
		Thread.sleep(3000);
		ph_MorePo.getEleMoreBtn().click();
		ph_MorePo.syncData(commonUtility);

		sObjectApi = "Event";
		sSqlEventQuery = "SELECT+id+from+Event+Where+Subject+='" + SFDC_Event + "'";
		String sEventIdSFDC = restServices.restGetSoqlValue(sSqlEventQuery, "Id");
		System.out.println("created event id from server:" + sEventIdSFDC);
		Assert.assertNotNull(sEventIdSFDC, "Record not found");
		ExtentManager.logger.log(Status.PASS, "Create SFDC event from Create New Option is Successful");
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// On server/DC, edit one of the events created SFDC
		

		 sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		 now1 = Calendar.getInstance();
		now1.set(Calendar.HOUR, 6);
		now1.set(Calendar.MINUTE, 0);
		now1.set(Calendar.SECOND, 0);
		 endtimezero = sdf.format(now1.getTime());
		// now1.set(Calendar.HOUR_OF_DAY, 12);
		System.out.println(endtimezero);

		 sWOJson = "{\"EndDateTime\":\"" + endtimezero + "\"}";
		restServices.restUpdaterecord(sObjectApi, sWOJson, sEventIdSFDC);

		
		sObjectApi = "Event";
		sSqlEventQuery ="SELECT+EndDateTime+from+Event+Where+id+=\'"+sEventIdSFDC+"\'";				
		String sEventenddatetimeserverSFDC =restServices.restGetSoqlValue(sSqlEventQuery,"EndDateTime"); 
		System.out.println(sEventenddatetimeserverSFDC);
		
		String enddatetimefromserverSFDC = calendarPO.convertedformate(sEventenddatetimeserverSFDC);
		System.out.println("end data"+enddatetimefromserverSFDC);
		
		
	ph_MorePo.syncData(commonUtility);
	Thread.sleep(5000);
	ph_CalendarPo.getEleCalendarBtn().click();
	Thread.sleep(2000);
	ph_CalendarPo.getEleworkordernumonCalendar(SFDC_Event).click();
	Thread.sleep(2000);
	
	
	String getappointmentdateSFDC = ph_CalendarPo.getEleeventdate().getAttribute("text");
	System.out.println("get time:"+getappointmentdateSFDC);

	String enddatefromcalSFDC = calendarPO.convertedformate(getappointmentdateSFDC,"E, MMM dd, yyyy","M/d/yyyy");
	System.out.println("get date:"+enddatefromcalSFDC);
	
	
	String[] getappointmenttimeSFDC= ph_CalendarPo.getEleeventtime().getAttribute("text").split("- ");
	System.out.println("get end  time:"+getappointmenttimeSFDC[1]);
	String datetimefromclientSFDC=enddatefromcalSFDC+" "+getappointmenttimeSFDC[1];
	System.out.println("datetimefromclient"+datetimefromclientSFDC);
	Assert.assertEquals(datetimefromclientSFDC,enddatetimefromserverSFDC, "End Date time is mismatch");
	ExtentManager.logger.log(Status.PASS,"On server/DC, edit one of the SFDC events and validated in client is successful");
	System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\"}";
	sObjectApi = "SVMXC__Service_Order__c?";
	
	String sObjectWOID1 = restServices.restCreate(sObjectApi,sJsonData);
	String sSqlWOQuery2 = "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectWOID1+"\'";				
	String WOname1 = restServices.restGetSoqlValue(sSqlWOQuery2,"Name"); 
	
	
	
	 sWOJson = "{\"WhatId\":\""+sObjectWOID1+"\"}";
	restServices.restUpdaterecord("Event", sWOJson, sEventIdSFDC);
	ph_MorePo.syncData(commonUtility);
	
	Thread.sleep(5000);
	ph_CalendarPo.getEleCalendarBtn().click();
	ph_CalendarPo.getEleworkordernumonCalendar(SFDC_Event).click();
	 WOfromcalender = ph_CalendarPo.getEleworkordernumonCalendar(WOname1).getAttribute("text");
	System.out.println(WOfromcalender);
	ph_CalendarPo.getEleworkordernumonCalendar(WOname1).click();
	Thread.sleep(5000);
	Assert.assertTrue(ph_WorkOrderPo.getEleActionsLnk().isDisplayed());
	ExtentManager.logger.log(Status.PASS," Attach a WO to SFDC event verification is sucessful");
	System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
	///////////////////////////////////////////////////////////////////////////////////////////////////
		*/
	//Verify Day view,Verify Month view,Verify Agenda view ,Verify map view
		 ph_CalendarPo.geteleyearandmonth().click();
		 Thread.sleep(2000);
		 
		
		 
		Assert.assertTrue(ph_CalendarPo.getlecurrentdatedot().isDisplayed());//current day
		
		String day = commonUtility.adddaystocurrentday(1);
		Assert.assertTrue(ph_CalendarPo.getElegetdot(day).isDisplayed());
	
		 day = commonUtility.adddaystocurrentday(2);
		Assert.assertTrue(ph_CalendarPo.getElegetdot(day).isDisplayed());
	
		day = commonUtility.adddaystocurrentday(3);
		Assert.assertTrue(ph_CalendarPo.getElegetdot(day).isDisplayed());
		
		
		Point coordinates= ph_CalendarPo.getlecurrentdatedot().getLocation();
		System.out.println("x:"+coordinates.getX()+"y:"+coordinates.getY());
		Dimension dim=driver.manage().window().getSize();
		System.out.println("x:"+dim.getWidth()+"y:"+dim.getHeight());
		new TouchAction(driver).press(new PointOption().withCoordinates(dim.getWidth()-20,coordinates.getY())).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(new PointOption().withCoordinates(coordinates.getX(), coordinates.getY())).release().perform();
	 
		Thread.sleep(5000);
	 
		day = commonUtility.adddaystocurrentday(9);
		//Assert.assertFalse(ph_CalendarPo.getElegetdot(day).isDisplayed());
		
		
		 coordinates= ph_CalendarPo.getelegetday(day).getLocation();
		System.out.println("x:"+coordinates.getX()+"y:"+coordinates.getY());
		 dim=driver.manage().window().getSize();
		System.out.println("x:"+dim.getWidth()+"y:"+dim.getHeight());
		new TouchAction(driver).press(new PointOption().withCoordinates(dim.getWidth()+50,coordinates.getY())).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(new PointOption().withCoordinates(coordinates.getX(), coordinates.getY())).release().perform();
	 
		day = commonUtility.adddaystocurrentday(-1);
		Assert.assertTrue(ph_CalendarPo.getElegetdot(day).isDisplayed());
		
	
	}

}
