/*
 *@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.ge.fsa.lib.CommonUtility;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Ph_Sanity11_Verify_Calendar_Functionality_GO_6350 extends BaseLib {

	int iWhileCnt = 0;
	
	
	String sSqlEventQuery = null;
	String sSqlWOQuery = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sEventIdSVMX_1 = null;
	String sEventIdSVMX = null;


	@Test(retryAnalyzer=Retry.class)

	public void Calender_6350() throws Exception {

		commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		String sRandomNumber = commonUtility.generateRandomNumber("");
		String sEventSubject = "Event_" + sRandomNumber;
		// sahi

		commonUtility.executeSahiScript("appium/Ph_FON_6350.sah");
		
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		ph_MorePo.configSync(commonUtility,ph_CalendarPo);
		Thread.sleep(CommonUtility.iMedSleep);
		ph_MorePo.syncData(commonUtility);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

////////////////////////////////////////////////////////////////////////////////////////////////////////////			
	// Create SVMX event from Create New Option
		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.getEleCreateNewEvent().click();
		ph_CalendarPo.getEleCalendarEventSubject().click();
		ph_CalendarPo.getEleCalendarEventSubject().sendKeys(sEventSubject+"\n");


		commonUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0, "10", "00");
		commonUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 0, "11", "00");
		
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
		
	
	/*	sSqlEventQuery ="SELECT+SVMXC__EndDateTime__c+from+SVMXC__SVMX_Event__c+Where+id+=\'"+sEventIdSVMX_1+"\'";				
		String sEventenddatetimeserver =restServices.restGetSoqlValue(sSqlEventQuery,"SVMXC__EndDateTime__c"); 
		System.out.println(sEventenddatetimeserver);*/
		
		
		String endtimezero=	ph_CalendarPo.Addinghrstosfdcformat(3);
		
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


		String getappointmentdate = ph_CalendarPo.getEleeventdate(sEventSubject).getText();
		System.out.println("get dates:"+getappointmentdate);

		String enddatefromcal = calendarPO.convertedformate(getappointmentdate,"E, MMM dd, yyyy","M/d/yyyy");
		System.out.println("date1:"+enddatefromcal);


		String[] getappointmenttime= ph_CalendarPo.getEleeventtime(sEventSubject).getText().split("- ");
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
		Thread.sleep(3000);
		//ph_CalendarPo.getEleworkordernumonCalendar(sEventSubject).click();
		String WOfromcalender = ph_CalendarPo.getEleworkordernumon(WOname).getText();
		System.out.println(WOfromcalender);
		ph_CalendarPo.getEleworkordernumon(WOname).click();
		Thread.sleep(2000);
		Assert.assertTrue(ph_WorkOrderPo.getEleActionsLnk().isDisplayed());
		ExtentManager.logger.log(Status.PASS," Attach a WO to event verification is sucessful");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		//setting to SFDC event
		commonUtility.executeSahiScript("appium/SCN_Calender_4_RS-10514_3.sah");
		
		ph_MorePo.configSync(commonUtility,ph_CalendarPo);


		//create SFDC event from calender
		String SFDC_Event = "SFDC"+sEventSubject;
		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.getEleCreateNewEvent().click();
		ph_CalendarPo.getEleCalendarEventSubject().click();
		ph_CalendarPo.getEleCalendarEventSubject().sendKeys(SFDC_Event+"\n");

		
			commonUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0, "10", "00");
			commonUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 0, "11", "00");
			
		
		
		ph_WorkOrderPo.getEleAdd().click();
		Thread.sleep(3000);
		ph_MorePo.getEleMoreBtn().click();
		ph_MorePo.syncData(commonUtility);

		Thread.sleep(10000);

		sObjectApi = "Event";
		sSqlEventQuery = "SELECT+id+from+Event+Where+Subject+='" + SFDC_Event + "'";
		String sEventIdSFDC = restServices.restGetSoqlValue(sSqlEventQuery, "Id");
		System.out.println("created event id from server:" + sEventIdSFDC);
		Assert.assertNotNull(sEventIdSFDC, "Record not found");
		ExtentManager.logger.log(Status.PASS, "Create SFDC event from Create New Option is Successful");
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// On server/DC, edit one of the events created SFDC


		 endtimezero=	ph_CalendarPo.Addinghrstosfdcformat(3);

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


	String getappointmentdateSFDC = ph_CalendarPo.getEleeventdate(SFDC_Event).getText();
	System.out.println("get time:"+getappointmentdateSFDC);

	String enddatefromcalSFDC = calendarPO.convertedformate(getappointmentdateSFDC,"E, MMM dd, yyyy","M/d/yyyy");
	System.out.println("get date:"+enddatefromcalSFDC);


	String[] getappointmenttimeSFDC= ph_CalendarPo.getEleeventtime(SFDC_Event).getText().split("- ");
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
	Thread.sleep(3000);
	//ph_CalendarPo.getEleworkordernumonCalendar(SFDC_Event).click();
	   WOfromcalender = ph_CalendarPo.getEleworkordernumon(WOname1).getText();
	System.out.println(WOfromcalender);
	ph_CalendarPo.getEleworkordernumon(WOname1).click();
	Thread.sleep(5000);
	Assert.assertTrue(ph_WorkOrderPo.getEleActionsLnk().isDisplayed());
	ExtentManager.logger.log(Status.PASS," Attach a WO to SFDC event verification is sucessful");
		System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
		///////////////////////////////////////////////////////////////////////////////////////////////////
		Thread.sleep(5000);
		//Verifying Dots
		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.geteleyearandmonth().click();
		Thread.sleep(2000);

		Assert.assertTrue(ph_CalendarPo.getlecurrentdatedot().isDisplayed());//current day

		
		int i=1;
		int noOfDays=6;
		int sPassCount =0;
		int sFailCount=0;
		for(i= 1;i<=noOfDays-3;i=i+2) {
			int sDayBack = -i;
			//Backward
			String day = commonUtility.adddaystocurrentday(sDayBack);
			if(commonUtility.waitforElement(ph_CalendarPo.getElegetdot(day), 1)) {
				System.out.println("Found the  back date for "+day);
				sPassCount++;
			}
			
		
				
		else {
				if(day.equalsIgnoreCase("31") || day.equalsIgnoreCase("30") || day.equalsIgnoreCase("29")|| day.equalsIgnoreCase("28")) {
					
				System.out.println("Did not Find in current month the date for back date "+day);

				//Swipe once back ward and check
				Point coordinates= ph_CalendarPo.getlecurrentdatedot().getLocation();
				System.out.println("x:"+coordinates.getX()+"y:"+coordinates.getY());
				Dimension dim=driver.manage().window().getSize();
				System.out.println("x:"+dim.getWidth()+"y:"+dim.getHeight());
				new TouchAction(driver).press(new PointOption().withCoordinates(coordinates.getX(),coordinates.getY())).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
				.moveTo(new PointOption().withCoordinates(dim.getWidth()-20, coordinates.getY())).release().perform();
				}
				if(commonUtility.waitforElement(ph_CalendarPo.getElegetdot(day), 1)) {
					System.out.println("Found the  Back date after swiping for "+day);

					sPassCount++;}
				
				
				}
				

			}

			
		
		System.out.println(sPassCount);
		if(sPassCount==2) {
			ExtentManager.logger.log(Status.PASS,"Pass Past Dates Found ");	
		}
		else {
			ExtentManager.logger.log(Status.FAIL,"Failed Past Dates Not Found");	
			}
		sPassCount=0;
		
		for(i= 1;i<=noOfDays-3;i++) {
				int sDayFront = i;
				//Forward
				String day = commonUtility.adddaystocurrentday(sDayFront);

				if(commonUtility.waitforElement(ph_CalendarPo.getElegetdot(day), 1)) {
					
					System.out.println("Found the  future date for "+day);
					sPassCount++;
				}else { 
					
					if(day.equalsIgnoreCase("1")|| day.equalsIgnoreCase("2") || day.equalsIgnoreCase("3") ||day.equalsIgnoreCase("4") ) {

					//Swipe once forward and check if date is 1 in any case swipe if its 1
						Point coordinates= ph_CalendarPo.getGetDates().getLocation();
						System.out.println("x:"+coordinates.getX()+"y:"+coordinates.getY());
						Dimension dim=driver.manage().window().getSize();
						System.out.println("x:"+dim.getWidth()+"y:"+dim.getHeight());
						new TouchAction(driver).press(new PointOption().withCoordinates(dim.getWidth()-5,coordinates.getY())).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
									.moveTo(new PointOption().withCoordinates(coordinates.getX(), coordinates.getY())).release().perform();
					}
					
					if(commonUtility.waitforElement(ph_CalendarPo.getElegetdot(day), 1)) {
						System.out.println("Found the  future date after swiping for "+day);
						sPassCount++;
					}
				}
			}

		if(sPassCount==3) {
			ExtentManager.logger.log(Status.PASS,"Passed Future Dates Found ");	
					
		}
		else {
			ExtentManager.logger.log(Status.FAIL,"Failed Future Dates Not Found dates");			

		}
		
		sPassCount=0;
			//9th Day it should not be downloaded
			String day = commonUtility.adddaystocurrentday(9);
			if(commonUtility.waitforElement(ph_CalendarPo.getElegetdot(day), 1)) {
				System.out.println("***Failed Dot Found day were it must not be "+day);		

			}else {
				System.out.println("***Passed Dot Not Found day were it must not be "+day);		

			}
			System.out.println("///////////////////////////////////////////////////////////////////////////////////////////////////");		
			//Verify Day view,Verify Agenda view

			
				ph_CalendarPo.getEleselectview().click();
				ph_CalendarPo.getEleAgendaView().click();
				
			Thread.sleep(3000);
		
		int Eventcount=	ph_CalendarPo.getEleAgendaViewcomponent().size();
		System.out.println(Eventcount);
	if(Eventcount>=1)
	{
		ExtentManager.logger.log(Status.PASS,"PASS Verified agenda view for the current day ");	
	}else {
		ExtentManager.logger.log(Status.FAIL,"FAIL Verified agenda view for the current day");
	}
	
			

	}

	}
