/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests.tablet;

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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

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
	String sObjectAWOID = null;
	String sIBLastModifiedBy=null;
	//String techname="a240t000000GglLAAS";
	WebElement productname=null;
	String sSheetName =null;
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	@Test(retryAnalyzer=Retry.class)
	public void RS_10525_2months_event() throws Exception {
		sSheetName ="RS_10525";
		
		String sProformainVoice = commonUtility.generaterandomnumber("Proforma");
		String sTestIB="RS_10525_Calender_6";
		String sTestIBID = sProformainVoice;
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
	
	//read from file
		sExploreSearch = GenericLib.readExcelData(GenericLib.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.readExcelData(GenericLib.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.readExcelData(GenericLib.sTestDataFile,sSheetName, "ProcessName");
		String TechName = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "TECH_ID");
		
			//Pre Login to app
			loginHomePo.login(commonUtility, exploreSearchPo);
		
		
		//config sync
			//toolsPo.configSync(commonsUtility);
			Thread.sleep(GenericLib.iMedSleep);
			
			//Data Sync for WO's created
			//toolsPo.syncData(commonsUtility);
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
		
			toolsPo.syncData(commonUtility);
		
		//String sWOName="WO-00002608";  String sObjectAWOID ="a2D0t000002Me4tEAC";
			String sSoqlStartDateTime = "SELECT+SVMXC__StartDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sObjectAWOID+"\'";
		 String sSoqlQueryStartDateTime = restServices.restGetSoqlValue(sSoqlStartDateTime, "SVMXC__StartDateTime__c");
			System.out.println(sSoqlQueryStartDateTime);
			
			 String sSoqlEndDateTime = "SELECT+SVMXC__EndDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sObjectAWOID+"\'";
			 String sSoqlQueryEndDateTime = restServices.restGetSoqlValue(sSoqlEndDateTime, "SVMXC__EndDateTime__c");
			System.out.println(sSoqlQueryEndDateTime);
		
		
			//verifing event is present 
			commonUtility.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			commonUtility.tap(calendarPO.getElecalendarmonthtap());
			Thread.sleep(3000);
			String convertedstartday =calendarPO.convertdatetimetoday(sSoqlQueryStartDateTime);
			System.out.println(convertedstartday);
			Thread.sleep(3000);
			commonUtility.tap(calendarPO.getelemonthday(convertedstartday));
			commonUtility.waitforElement(calendarPO.getEleworkordernumonCalendarWeek(sWOName), 30);
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
			commonUtility.tap(calendarPO.getElecalendarmonthtap());
			Thread.sleep(3000);
			
			commonUtility.tap(calendarPO.getelenavigatetonextmonthcalender());
			commonUtility.tap(calendarPO.getelenavigatetonextmonthcalender());
			
			Thread.sleep(3000);
			calendarPO.getelemonthday(convertedendday).getLocation();
			commonUtility.tap(calendarPO.getelemonthday(convertedendday));
			//commonsUtility.tap(calendarPO.geteletaponmonthdayindex());
			
			Thread.sleep(3000);
			commonUtility.waitforElement(calendarPO.getEleworkordernumonCalendarWeek(sWOName), 30);
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
