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

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

public class SCN_Calendar_6_RS_10525 extends BaseLib {

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
	
	WebElement productname=null;
	String sSheetName =null;
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	@Test(enabled = true)
	public void RS_10525() throws Exception {
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
			toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
		
		

			//get Wo is and event start date and end date.
			String sSoqlwoid= "SELECT+id+from+SVMXC__Service_Order__c+Where+Name=\'"+sworkOrderName+"\'";
			String sSoqlwoid1 = restServices.restGetSoqlValue(sSoqlwoid, "Id");
			System.out.println(sSoqlwoid1);
			
			String sSoqlStartDateTime= "SELECT+SVMXC__StartDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sSoqlwoid1+"\'";
			String sSoqlQueryStartDateTime = restServices.restGetSoqlValue(sSoqlStartDateTime, "SVMXC__StartDateTime__c");
			System.out.println(sSoqlQueryStartDateTime);
			
			String sSoqlEndDateTime= "SELECT+SVMXC__EndDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sSoqlwoid1+"\'";
			String sSoqlQueryEndDateTime = restServices.restGetSoqlValue(sSoqlEndDateTime, "SVMXC__EndDateTime__c");
			System.out.println(sSoqlQueryEndDateTime);
			
			//getting hr from datetime
			Thread.sleep(5000);
			String StartDateTimehr=calendarPO.convertdatetimetohr(sSoqlQueryStartDateTime);
			String EndDateTimehr=calendarPO.convertdatetimetohr(sSoqlQueryEndDateTime);
	
			int diff = (Integer.parseInt(EndDateTimehr)-Integer.parseInt(StartDateTimehr))-1;//diff of start and end date
			System.out.println(diff);
			
			//verifing event is present at the rite location in day view
			//Navigation to calender and search for WO
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonsPo,sworkOrderName);
			calendarPO.validateeventlocation(sworkOrderName,StartDateTimehr,EndDateTimehr,diff);
			
			//verifing event is present at the rite location in Week view
			commonsPo.tap(calendarPO.getElecalendarWeektap());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonsPo,sworkOrderName);
			
			//verifing event is present at the rite location in month view
			
			commonsPo.tap(calendarPO.getElecalendarmonthtap());
			Thread.sleep(3000);
			String convertedstartday =calendarPO.convertdatetimetoday(sSoqlQueryStartDateTime);
			System.out.println(convertedstartday);
			Thread.sleep(3000);
			commonsPo.tap(calendarPO.geteletaponmonthday(convertedstartday));
			calendarPO.VerifyWOInCalender(commonsPo,sworkOrderName);
			
			ExtentManager.logger.log(Status.PASS,"one hour event verification is successful in day,week,month view");
			System.out.println("///////////////////////////////////////////////////////////////////////////////////////////////////////");
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
			//create WO and Account
			sObjectApi = "Account?";
			sJsonData = "{\"Name\": \"onedayAccount\"}";
			sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
			sSqlAccQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
			sAccountName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
			//sProductName1="v1";
			System.out.println(sAccountName);
			
			sObjectApi = "SVMXC__Service_Order__c?";
			sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\",\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Order_Type__c\":\"Field Service\",\"SVMXC__Group_Member__c\":\""+TechName+"\"}";
			String sObjectAWOID=restServices.restCreate(sObjectApi,sJsonData);
			sSqlAccQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectAWOID+"\'";				
			String sWOName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
			//sProductName1="v1";
			System.out.println(sWOName);
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Calendar now = Calendar.getInstance();
			
	        now.set(Calendar.HOUR, 0);
	        now.set(Calendar.MINUTE, 0);
	        now.set(Calendar.SECOND, 0);
	        System.out.println(sdf.format(now.getTime()));
	        now.set(Calendar.HOUR_OF_DAY, 0);
	     String  starttimezero=  sdf.format(now.getTime());
	     System.out.println(starttimezero);
		
	     
	     Calendar now1 = Calendar.getInstance();
	        now1.set(Calendar.HOUR, 12);
	        now1.set(Calendar.MINUTE, 0);
	        now1.set(Calendar.SECOND, 0);
	        String     endtimezero=sdf.format(now1.getTime());
	     System.out.println(endtimezero);
	     
	     
	     sObjectApi = "SVMXC__SVMX_Event__c?";	
			sJsonData = "{\"Name\": \"OneDayEvent\",\"SVMXC__Service_Order__c\": \""+sObjectAWOID+"\",\"SVMXC__Technician__c\": \""+TechName+"\",\"SVMXC__StartDateTime__c\": \""+starttimezero+"\", \"SVMXC__EndDateTime__c\":\""+endtimezero+"\",\"SVMXC__WhatId__c\": \""+sObjectAWOID+"\"}";	

		

		//	sJsonData = "{\"Name\":\"OneDayEvent\",\"SVMXC__Service_Order__c\":\""+sWOName+"\",\"SVMXC__StartDateTime__c\":\""+starttimezero+"\",\"SVMXC__EndDateTime__c\":\""+endtimezero+"\",\"SVMXC__WhatId__c\":\""+sObjectAWOID+"\"}";	
			
			String sObjecteventID=restServices.restCreate(sObjectApi,sJsonData);
			sSqlAccQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjecteventID+"\'";				
			String seventName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
			//sProductName1="v1";
			System.out.println(seventName);
		
			toolsPo.syncData(commonsPo);
			
			//String	sWOName="WO-00002656";String sObjectAWOID="a2D0t000002MeezEAC";
				 sSoqlStartDateTime= "SELECT+SVMXC__StartDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sObjectAWOID+"\'";
				 sSoqlQueryStartDateTime = restServices.restGetSoqlValue(sSoqlStartDateTime, "SVMXC__StartDateTime__c");
			System.out.println(sSoqlQueryStartDateTime);
			
				 sSoqlEndDateTime= "SELECT+SVMXC__EndDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sObjectAWOID+"\'";
				 sSoqlQueryEndDateTime = restServices.restGetSoqlValue(sSoqlEndDateTime, "SVMXC__EndDateTime__c");
			System.out.println(sSoqlQueryEndDateTime);
		
			Thread.sleep(5000);
				 StartDateTimehr=calendarPO.convertdatetimetohr(sSoqlQueryStartDateTime);
				 EndDateTimehr=calendarPO.convertdatetimetohr(sSoqlQueryEndDateTime);
			
		
		 diff = (Integer.parseInt(EndDateTimehr)-Integer.parseInt(StartDateTimehr))-16;//diff of start and end date
			System.out.println(diff);
			
			//verifing event is present at the rite location
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonsPo,sWOName);
			calendarPO.validateeventlocation(sWOName,StartDateTimehr,EndDateTimehr,diff);
			
			//verifing event is present at the rite location in Week view
			commonsPo.tap(calendarPO.getElecalendarWeektap());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonsPo,sWOName);
			
			//verifing event is present at the rite location in month view
			
			commonsPo.tap(calendarPO.getElecalendarmonthtap());
			Thread.sleep(3000);
				  convertedstartday = calendarPO.convertdatetimetoday(sSoqlQueryStartDateTime);
			System.out.println(convertedstartday);
			Thread.sleep(3000);
			commonsPo.tap(calendarPO.geteletaponmonthday(convertedstartday));
			calendarPO.VerifyWOInCalender(commonsPo,sWOName);
			
			
			
			ExtentManager.logger.log(Status.PASS,"one day  event verification is successful");
			System.out.println("///////////////////////////////////////////////////////////////////////////////////////////////////////");

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	
			//	Creating overlapping event
			 
			sObjectApi = "Account?";
			sJsonData = "{\"Name\": \"OverlappingAccount\"}";
			sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
			sSqlAccQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
			sAccountName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
			//sProductName1="v1";
			System.out.println(sAccountName);
			
			sObjectApi = "SVMXC__Service_Order__c?";
			sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\",\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Order_Type__c\":\"Field Service\",\"SVMXC__Group_Member__c\":\""+TechName+"\"}";
			  sObjectAWOID = restServices.restCreate(sObjectApi,sJsonData);
			sSqlAccQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectAWOID+"\'";				
				sWOName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
			//sProductName1="v1";
			System.out.println(sWOName);
			
			
			  sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			   now = Calendar.getInstance();
			 now.set(Calendar.HOUR, 0);
	        now.set(Calendar.MINUTE, 0);
	        now.set(Calendar.SECOND, 0);
	        System.out.println("Set to Zero"+sdf.format(now.getTime()));
	        now.set(Calendar.HOUR_OF_DAY, 0);
	     starttimezero = sdf.format(now.getTime());
	     System.out.println(starttimezero);
		
	     
	     	 now1 = Calendar.getInstance();
	        now1.set(Calendar.HOUR, 5);
	        now1.set(Calendar.MINUTE, 0);
	        now1.set(Calendar.SECOND, 0);
	         endtimezero = sdf.format(now1.getTime());
	     System.out.println("Set to 12"+endtimezero);
	    
	    
	     sObjectApi = "SVMXC__SVMX_Event__c?";
			sJsonData = "{\"Name\": \"OverlappingEvent\",\"SVMXC__Service_Order__c\": \""+sObjectAWOID+"\",\"SVMXC__Technician__c\": \""+TechName+"\",\"SVMXC__StartDateTime__c\": \""+starttimezero+"\", \"SVMXC__EndDateTime__c\":\""+endtimezero+"\",\"SVMXC__WhatId__c\": \""+sObjectAWOID+"\"}";	


		  sObjecteventID = restServices.restCreate(sObjectApi,sJsonData);
			sSqlAccQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjecteventID+"\'";				
			 seventName = restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
			//sProductName1="v1";
			System.out.println(seventName);
		
			toolsPo.syncData(commonsPo);
			
				 sSoqlStartDateTime= "SELECT+SVMXC__StartDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sObjectAWOID+"\'";
				 sSoqlQueryStartDateTime = restServices.restGetSoqlValue(sSoqlStartDateTime, "SVMXC__StartDateTime__c");
			System.out.println(sSoqlQueryStartDateTime);
			
				 sSoqlEndDateTime= "SELECT+SVMXC__EndDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sObjectAWOID+"\'";
				 sSoqlQueryEndDateTime = restServices.restGetSoqlValue(sSoqlEndDateTime, "SVMXC__EndDateTime__c");
			System.out.println(sSoqlQueryEndDateTime);
		
			Thread.sleep(5000);
				 StartDateTimehr=calendarPO.convertdatetimetohr(sSoqlQueryStartDateTime);
				 EndDateTimehr=calendarPO.convertdatetimetohr(sSoqlQueryEndDateTime);
			
		
		//if the diff is 779 then minus with 9 otherwise minus with 1
		  diff = (Integer.parseInt(EndDateTimehr)- Integer.parseInt(StartDateTimehr))-16;//diff of start and end date//-16
			System.out.println(diff);
			
			//verifing event is present at the rite location
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonsPo,sWOName);
			calendarPO.validateeventlocation(sWOName,StartDateTimehr,EndDateTimehr,diff);
			
			
			//verifing event is present at the rite location in Week view
			commonsPo.tap(calendarPO.getElecalendarWeektap());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonsPo,sWOName);
			
			//verifing event is present at the rite location in month view
			commonsPo.tap(calendarPO.getElecalendarmonthtap());
			Thread.sleep(3000);
				 convertedstartday =calendarPO.convertdatetimetoday(sSoqlQueryStartDateTime);
			System.out.println(convertedstartday);
			Thread.sleep(3000);
			commonsPo.tap(calendarPO.geteletaponmonthday(convertedstartday));
			calendarPO.VerifyWOInCalender(commonsPo,sWOName);
			
			
			
			ExtentManager.logger.log(Status.PASS,"Over lapping  event verification is successful");
			
			
			
	}
	

}
