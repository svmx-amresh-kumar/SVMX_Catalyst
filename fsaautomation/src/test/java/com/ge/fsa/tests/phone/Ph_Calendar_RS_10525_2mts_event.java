package com.ge.fsa.tests.phone;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

public class Ph_Calendar_RS_10525_2mts_event extends BaseLib {

int iWhileCnt = 0;
	
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectAccID = null;
	String sSqlAccQuery=null;
	String sObjectProID=null;
	String sObjectApi = null;
	String sJsonData = null;
	String sObjectAWOID=null; 
	String sFieldServiceName = null;
	String sSoqlwoid1=null;
	String sproductname =null;
	String sSqlQuery = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	String sIBLastModifiedBy=null;
	String WOIDoverlapping=null;
	WebElement productname=null;
	String sSheetName =null;
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	//@Test(retryAnalyzer=Retry.class)
	@Test()
	public void Ph_10525() throws Exception {
	
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
	
	
	String TechName = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "TECH_ID");
			
			
			
			// Pre Login to app
			ph_LoginHomePo.login(commonUtility, ph_MorePo);
		//	ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		

		//create WO and Account
			sObjectApi = "Account?";
			sJsonData = "{\"Name\": \"Two_months_event_Account\"}";
			sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
			sSqlAccQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
			String sAccountName = restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
			//sProductName1="v1";
			System.out.println(sAccountName);
			
			sObjectApi = "SVMXC__Service_Order__c?";
			sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\",\"SVMXC__Order_Status__c\":\"Open\"}";
			 sObjectAWOID=restServices.restCreate(sObjectApi,sJsonData);
			sSqlAccQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectAWOID+"\'";				
			String sWOName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
			//sProductName1="v1";
			System.out.println(sWOName);
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Calendar now = Calendar.getInstance();
	
			now.set(Calendar.HOUR, 0);
	        now.set(Calendar.MINUTE, 0);
	        now.set(Calendar.SECOND, 0);
	     String  starttimezero=  sdf.format(now.getTime());
	     System.out.println(starttimezero);
		
	     int month=now.get(Calendar.MONTH);
		System.out.println(month);
	     
	     Calendar now1 = Calendar.getInstance();
	     now1.set(Calendar.MONTH, month+2);
	        now1.set(Calendar.HOUR, 0);
	        now1.set(Calendar.MINUTE, 0);
	        now1.set(Calendar.SECOND, 0);
	        String  endtimezero=sdf.format(now1.getTime());
	     System.out.println(endtimezero);
	    
	     
	     sObjectApi = "SVMXC__SVMX_Event__c?";
			//sJsonData = "{\"SVMXC__StartDateTime__c\": \""+starttimezero+"\"}";	
			sJsonData = "{\"Name\": \"Two_months_event\",\"SVMXC__Service_Order__c\": \""+sObjectAWOID+"\",\"SVMXC__Technician__c\": \""+TechName+"\",\"SVMXC__StartDateTime__c\": \""+starttimezero+"\", \"SVMXC__EndDateTime__c\":\""+endtimezero+"\",\"SVMXC__WhatId__c\": \""+sObjectAWOID+"\"}";	


		//	sJsonData = "{\"Name\":\"OneDayEvent\",\"SVMXC__Service_Order__c\":\""+sWOName+"\",\"SVMXC__StartDateTime__c\":\""+starttimezero+"\",\"SVMXC__EndDateTime__c\":\""+endtimezero+"\",\"SVMXC__WhatId__c\":\""+sObjectAWOID+"\"}";	
			String sObjecteventID=restServices.restCreate(sObjectApi,sJsonData);
			sSqlAccQuery ="SELECT+name+from+SVMXC__SVMX_Event__c+Where+id+=\'"+sObjecteventID+"\'";				
			String seventName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
			//sProductName1="v1";
			System.out.println(seventName);
	
			
			
			ph_MorePo.syncData(commonUtility);

		
		//String sWOName="WO-00002608";  String sObjectAWOID ="a2D0t000002Me4tEAC";
			String sSoqlStartDateTime = "SELECT+SVMXC__StartDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sObjectAWOID+"\'";
		 String sSoqlQueryStartDateTime = restServices.restGetSoqlValue(sSoqlStartDateTime, "SVMXC__StartDateTime__c");
			System.out.println(sSoqlQueryStartDateTime);
			
			 String sSoqlEndDateTime = "SELECT+SVMXC__EndDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sObjectAWOID+"\'";
			 String sSoqlQueryEndDateTime = restServices.restGetSoqlValue(sSoqlEndDateTime, "SVMXC__EndDateTime__c");
			System.out.println(sSoqlQueryEndDateTime);
		
		
			ph_CalendarPo.getEleCalendarBtn().click();
			ph_CalendarPo.getEleCalendarBtn().click();
			 String startDateTimehr = calendarPO.convertdatetimetohr(sSoqlQueryStartDateTime);
			ph_CalendarPo.custScroll(commonUtility,(startDateTimehr+":00"));
			ph_CalendarPo.VerifyWOInCalender(commonUtility,seventName);
			
			Thread.sleep(3000);

			ph_CalendarPo.geteleyearandmonth().click();
			int i=0;
			for(i=0;i<2;i++) {
			//Swipe once forward 
			Point coordinates= ph_CalendarPo.getGetDates().getLocation();
			System.out.println("x:"+coordinates.getX()+"y:"+coordinates.getY());
			Dimension dim=driver.manage().window().getSize();
			System.out.println("x:"+dim.getWidth()+"y:"+dim.getHeight());
			new TouchAction(driver).press(new PointOption().withCoordinates(dim.getWidth()-5,coordinates.getY())).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
						.moveTo(new PointOption().withCoordinates(coordinates.getX(), coordinates.getY())).release().perform();
			}
	
			ph_CalendarPo.geteleyearandmonth().click();
			Thread.sleep(000);
			ph_CalendarPo.custScroll(commonUtility,(startDateTimehr+":00"));
		        
			ph_CalendarPo.VerifyWOInCalender(commonUtility,seventName);
			
	}
	
}
