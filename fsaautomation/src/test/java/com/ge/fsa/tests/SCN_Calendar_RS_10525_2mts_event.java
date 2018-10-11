/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

public class SCN_Calendar_RS_10525_2mts_event extends BaseLib {

	int iWhileCnt = 0;
	
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectAccID = null;
	String sSqlAccQuery=null;
	String sObjectProID=null;
	String sObjectApi = null;
	String sJsonData = null;
	//String sAccountName = "Proforma30082018102823account";
	String sAccountName =null;
	String sFieldServiceName = null;
//String sproductname = "Proforma30082018102823product";
	String sproductname =null;
	String sSqlQuery = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	String sIBLastModifiedBy=null;
	//String techname="a240t000000GglLAAS";
	WebElement productname=null;
	String sSheetName =null;
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	@Test(enabled = true)
	public void RS_10525_2months_event() throws Exception {
		sSheetName ="RS_10525";
		sDeviceDate = driver.getDeviceTime().split(" ");
		String sProformainVoice = commonsPo.generaterandomnumber("Proforma");
		String sTestIB="RS_10525_Calender_6";
		String sTestIBID = sProformainVoice;
	
	
	//read from file
		sExploreSearch = GenericLib.getExcelData(sTestIB,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestIB,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestIB,sSheetName, "ProcessName");
		String sworkOrderName = GenericLib.getExcelData(sTestIB,sSheetName, "WorkOrder Number");
		String TechName = GenericLib.getExcelData(sTestIB,sSheetName, "TechName");
		
			//Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
		
			
		//config sync
			//toolsPo.configSync(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			
			//Data Sync for WO's created
			//toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
			//create WO and Account
			sObjectApi = "Account?";
			sJsonData = "{\"Name\": \"Two_months_event_Account\"}";
			sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
			sSqlAccQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
			sAccountName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
			//sProductName1="v1";
			System.out.println(sAccountName);
			
			sObjectApi = "SVMXC__Service_Order__c?";
			sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\",\"SVMXC__Order_Status__c\":\"Open\"}";
			String sObjectAWOID=restServices.restCreate(sObjectApi,sJsonData);
			sSqlAccQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectAWOID+"\'";				
			String sWOName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
			//sProductName1="v1";
			System.out.println(sWOName);
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Calendar now = Calendar.getInstance();
			 now.set(Calendar.MONTH, 8);
	        now.set(Calendar.HOUR, 0);
	        now.set(Calendar.MINUTE, 0);
	        now.set(Calendar.SECOND, 0);
	        System.out.println(sdf.format(now.getTime()));
	     String  starttimezero=  sdf.format(now.getTime());
	     System.out.println(starttimezero);
		
	     
	     Calendar now1 = Calendar.getInstance();
	     now1.set(Calendar.MONTH, 10);
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
		
			toolsPo.syncData(commonsPo);
		
		//String sWOName="WO-00002608";  String sObjectAWOID ="a2D0t000002Me4tEAC";
			String sSoqlStartDateTime = "SELECT+SVMXC__StartDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sObjectAWOID+"\'";
		 String sSoqlQueryStartDateTime = restServices.restGetSoqlValue(sSoqlStartDateTime, "SVMXC__StartDateTime__c");
			System.out.println(sSoqlQueryStartDateTime);
			
			 String sSoqlEndDateTime = "SELECT+SVMXC__EndDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sObjectAWOID+"\'";
			 String sSoqlQueryEndDateTime = restServices.restGetSoqlValue(sSoqlEndDateTime, "SVMXC__EndDateTime__c");
			System.out.println(sSoqlQueryEndDateTime);
		
		
			//verifing event is present 
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			commonsPo.tap(calendarPO.getElecalendarmonthtap());
			Thread.sleep(3000);
			String convertedstartday =calendarPO.convertdatetimetoday(sSoqlQueryStartDateTime);
			System.out.println(convertedstartday);
			Thread.sleep(3000);
			commonsPo.tap(calendarPO.geteletaponmonthday(convertedstartday));
			commonsPo.waitforElement(calendarPO.getEleworkordernumonCalendarWeek(sWOName), 300);
			if(calendarPO.getEleworkordernumonCalendarWeek(sWOName) != null){
				System.out.println("Found WO in day View " + sWOName);
				}
					
			else
			{
				System.out.println("Did not Find WO " + sWOName);
				throw new Exception("WorkOrder not found on the Calendar");	
			
			}
			
			//verifing end event
			String convertedendday =calendarPO.convertdatetimetoday(sSoqlQueryEndDateTime);
			System.out.println(convertedendday);
			Thread.sleep(3000);
			commonsPo.tap(calendarPO.getElecalendarmonthtap());
			Thread.sleep(3000);
			
			commonsPo.tap(calendarPO.getelenavigatetonextmonthcalender());
			commonsPo.tap(calendarPO.getelenavigatetonextmonthcalender());
			
			Thread.sleep(3000);
			calendarPO.geteletaponmonthdayindex(convertedendday).getLocation();
			commonsPo.tap(calendarPO.geteletaponmonthdayindex(convertedendday));
			//commonsPo.tap(calendarPO.geteletaponmonthdayindex());
			
			Thread.sleep(3000);
			commonsPo.waitforElement(calendarPO.getEleworkordernumonCalendarWeek(sWOName), 300);
			if(calendarPO.getEleworkordernumonCalendarWeek(sWOName) != null){
				System.out.println("Found WO in day View " + sWOName);
				}
					
			else
			{
				System.out.println("Did not Find WO " + sWOName);
				throw new Exception("WorkOrder not found on the Calendar");	
			
			}
		
			ExtentManager.logger.log(Status.PASS,"Two months event verification is successful");
			
			
			
			
	
	}
	

}
