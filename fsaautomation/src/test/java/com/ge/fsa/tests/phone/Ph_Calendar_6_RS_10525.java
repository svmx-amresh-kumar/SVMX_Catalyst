package com.ge.fsa.tests.phone;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_Calendar_6_RS_10525 extends BaseLib {

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

	@Test(retryAnalyzer=Retry.class)
	
	public void Ph_10525() throws Exception {
		sSheetName ="RS_10525";
	
		String sTestCaseID="RS_10525_Calender_6";
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
	//sahi
	genericLib.executeSahiScript("appium/SCN_Calendar_6_RS_10525.sah");
		if(commonUtility.verifySahiExecution()) {
			System.out.println("PASSED");
		}
		else 
		{System.out.println("FAILED");
		ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
			assertEquals(0, 1);
		}
	
		String sworkOrderName = GenericLib.readExcelData(GenericLib.sTestDataFile,sSheetName, "WorkOrder Number");
			String TechName = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "TECH_ID");
			
			
			
			// Pre Login to app
			ph_LoginHomePo.login(commonUtility, ph_MorePo);
		//	ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		ph_MorePo.syncData(commonUtility);
			Thread.sleep(GenericLib.iMedSleep);
			
			//get Wo is and event start date and end date.
			String sSoqlwoid= "SELECT+id+from+SVMXC__Service_Order__c+Where+Name=\'"+sworkOrderName+"\'";
			 sSoqlwoid1 = restServices.restGetSoqlValue(sSoqlwoid, "Id");
			System.out.println(sSoqlwoid1);
			
			String sSoqlStartDateTime= "SELECT+SVMXC__StartDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sSoqlwoid1+"\'";
			String sSoqlQueryStartDateTime = restServices.restGetSoqlValue(sSoqlStartDateTime, "SVMXC__StartDateTime__c");
			System.out.println(sSoqlQueryStartDateTime);
			
			String sSoqlEndDateTime= "SELECT+SVMXC__EndDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sSoqlwoid1+"\'";
			String sSoqlQueryEndDateTime = restServices.restGetSoqlValue(sSoqlEndDateTime, "SVMXC__EndDateTime__c");
			System.out.println(sSoqlQueryEndDateTime);
			
			
			Thread.sleep(5000);
			 String StartDateTimehr = calendarPO.convertdatetimetohr(sSoqlQueryStartDateTime);
			 String EndDateTimehr = calendarPO.convertdatetimetohr(sSoqlQueryEndDateTime);
		
	
	 int diff = (Integer.parseInt(EndDateTimehr)-Integer.parseInt(StartDateTimehr));//diff of start and end date
		System.out.println(diff);
	
		
		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.validateeventlocation("Eventfor1hr",StartDateTimehr,EndDateTimehr,diff,commonUtility);
		
		ExtentManager.logger.log(Status.PASS,"one day  event verification is successful");
		System.out.println("///////////////////////////////////////////////////////////////////////////////////////////////////////");

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//Creating one day event
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \"onedayAccount\"}";
		sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlAccQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
		String sAccountName = restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
		//sProductName1="v1";
		System.out.println(sAccountName);
		
		sObjectApi = "SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\",\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Order_Type__c\":\"Field Service\",\"SVMXC__Group_Member__c\":\""+TechName+"\"}";
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

		
		String sObjecteventID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlAccQuery ="SELECT+Name+from+SVMXC__SVMX_Event__c+Where+id+=\'"+sObjecteventID+"\'";				
		String seventName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
		System.out.println(seventName);
		
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(5000);
		
		 sSoqlStartDateTime = "SELECT+SVMXC__StartDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sObjectAWOID+"\'";
		  sSoqlQueryStartDateTime = restServices.restGetSoqlValue(sSoqlStartDateTime, "SVMXC__StartDateTime__c");
	System.out.println(sSoqlQueryStartDateTime);
	
		  sSoqlEndDateTime = "SELECT+SVMXC__EndDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sObjectAWOID+"\'";
		  sSoqlQueryEndDateTime = restServices.restGetSoqlValue(sSoqlEndDateTime, "SVMXC__EndDateTime__c");
	System.out.println(sSoqlQueryEndDateTime);
		

	  StartDateTimehr = calendarPO.convertdatetimetohr(sSoqlQueryStartDateTime);
	  EndDateTimehr = calendarPO.convertdatetimetohr(sSoqlQueryEndDateTime);

	   diff = (Integer.parseInt(EndDateTimehr)-Integer.parseInt(StartDateTimehr));//diff of start and end date
		System.out.println(diff);
		ph_CalendarPo.getEleCalendarBtn().click();
	/////////////////////////////////////////////////////////////////////////////////	
	
		ph_CalendarPo.validateeventlocation("OneDayEvent",StartDateTimehr,EndDateTimehr,diff,commonUtility);
		ExtentManager.logger.log(Status.PASS,"All Day Event verification is successful");
		
////////////////////////////////////////////////////////////////////////////////////////////////
		
//		Creating overlapping event
		 
				sObjectApi = "Account?";
				sJsonData = "{\"Name\": \"OverlappingAccount\"}";
				sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
				sSqlAccQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
				sAccountName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
				//sProductName1="v1";
				System.out.println(sAccountName);
				
				sObjectApi = "SVMXC__Service_Order__c?";
				sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\",\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Order_Type__c\":\"Field Service\",\"SVMXC__Group_Member__c\":\""+TechName+"\"}";
				  WOIDoverlapping = restServices.restCreate(sObjectApi,sJsonData);
				sSqlAccQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+WOIDoverlapping+"\'";				
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
				sJsonData = "{\"Name\": \"OverlappingEvent\",\"SVMXC__Service_Order__c\": \""+WOIDoverlapping+"\",\"SVMXC__Technician__c\": \""+TechName+"\",\"SVMXC__StartDateTime__c\": \""+starttimezero+"\", \"SVMXC__EndDateTime__c\":\""+endtimezero+"\",\"SVMXC__WhatId__c\": \""+WOIDoverlapping+"\"}";	


			  sObjecteventID = restServices.restCreate(sObjectApi,sJsonData);
				sSqlAccQuery ="SELECT+name+from+SVMXC__SVMX_Event__c+Where+id+=\'"+sObjecteventID+"\'";				
				 seventName = restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
				//sProductName1="v1";
				System.out.println(seventName);
				ph_MorePo.syncData(commonUtility);
	
				sSoqlStartDateTime= "SELECT+SVMXC__StartDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+WOIDoverlapping+"\'";
				 sSoqlQueryStartDateTime = restServices.restGetSoqlValue(sSoqlStartDateTime, "SVMXC__StartDateTime__c");
			System.out.println(sSoqlQueryStartDateTime);
			
				 sSoqlEndDateTime= "SELECT+SVMXC__EndDateTime__c+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+WOIDoverlapping+"\'";
				 sSoqlQueryEndDateTime = restServices.restGetSoqlValue(sSoqlEndDateTime, "SVMXC__EndDateTime__c");
			System.out.println(sSoqlQueryEndDateTime);
		
			Thread.sleep(5000);
				 StartDateTimehr=calendarPO.convertdatetimetohr(sSoqlQueryStartDateTime);
				 EndDateTimehr=calendarPO.convertdatetimetohr(sSoqlQueryEndDateTime);
			
				 ph_CalendarPo.getEleCalendarBtn().click();	
				ph_CalendarPo.validateeventlocation("OverlappingEvent",StartDateTimehr,EndDateTimehr,diff,commonUtility);
		

				 ExtentManager.logger.log(Status.PASS,"Over lapping  event verification is successful");
			
				 
		
	}
	
}
