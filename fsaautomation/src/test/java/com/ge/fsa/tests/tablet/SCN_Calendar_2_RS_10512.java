/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests.tablet;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.Color;
import java.io.IOException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.tablet.CalendarPO;

public class SCN_Calendar_2_RS_10512 extends BaseLib {

	int iWhileCnt = 0;
	
	String stechname = null;
	String sSalesforceuser = null;
	String sSqlEventQuery = null;
	String sSqlWOQuery=null;
	String sObjectProID=null;
	String sObjectApi = null;
	String sJsonData = null;
	
	String sAccountName =null;
	String sFieldServiceName = null;
//String sproductname = "Proforma30082018102823product";
	String sproductname =null;
	String sWO_SVMX_1=null;
	String sWO_SVMX_2=null;String sWO_SVMX_3=null;String sWO_SVMX_4=null;
	String techname="a240t000000GglLAAS";
	WebElement productname=null;
	String sSheetName =null;
	
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	@Test(retryAnalyzer=Retry.class)
	
	public void RS_10512() throws Exception {
		sSheetName ="RS_10512";
		
	
		String sTestCaseID="RS_10512_Calender_2";
	
		
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		//sahi
		
		
		
		  commonUtility.executeSahiScript("appium/SCN_Calendar_2_RS-10512.sah");
		 
		  
		 
		lauchNewApp("false");
	
		
		 sWO_SVMX_1 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SVMX_1");
		 sWO_SVMX_2 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SVMX_2");
		 sWO_SVMX_3 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SVMX_3");
		 sWO_SVMX_4 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SVMX_4");
  	//Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
		//config sync
		//toolsPo.configSync(commonsUtility);
		Thread.sleep(3000);
		//toolsPo.syncData(commonsUtility);
		Thread.sleep(3000);
		
		commonUtility.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(5000);
		//verify the Event is displayed or not
	
		calendarPO.geteleWOendpoint("06:00").getLocation();
		  calendarPO.VerifyWOInCalenderafterconfchange(commonUtility,sWO_SVMX_1);
		  calendarPO.VerifyWOInCalenderafterconfchange(commonUtility,sWO_SVMX_2);
		  calendarPO.VerifyWOInCalenderafterconfchange(commonUtility,sWO_SVMX_3);
		  calendarPO.VerifyWOInCalenderafterconfchange(commonUtility,sWO_SVMX_4);
		 
///////////////////////////////////////////////////////////////////////////////////////////////////////
		//verify color codes for events
		String styleWO1 = calendarPO.getelegetcolourcode(sWO_SVMX_1,"High").getAttribute("style");
		String styleWO2 = calendarPO.getelegetcolourcode(sWO_SVMX_2,"Medium").getAttribute("style");
		String styleWO3 = calendarPO.getelegetcolourcode(sWO_SVMX_3,"Low").getAttribute("style");
		String styleWO4= calendarPO.getelegetcolourcode(sWO_SVMX_4,"").getAttribute("style");
		System.out.println(styleWO4);
		
		
		
		System.out.println(":)))))))))))))))):):):):):)");
		String rgb_high=calendarPO.hex2Rgb("#FF0000");
		String rgb_medium=calendarPO.hex2Rgb("#0000FF");
		String rgb_low=calendarPO.hex2Rgb("#FFFF00");
		String rgb_no_priority=calendarPO.hex2Rgb("#008000");
		System.out.println(rgb_no_priority);
		assertEquals(styleWO1, rgb_high);
		assertEquals(styleWO2, rgb_medium);
		assertEquals(styleWO3, rgb_low);
		assertEquals(styleWO4, rgb_no_priority);
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		
		//verify SVMX event subject block display
		Thread.sleep(3000);
	String getsubblock = calendarPO.getelegetsubjectblocke(sWO_SVMX_1).getText();
		System.out.println(getsubblock);
		
		assertTrue(getsubblock.contains(sWO_SVMX_1));
		assertTrue(getsubblock.contains("A10512_SVMX_Event1"));
		
		sObjectApi = "SVMXC__SVMX_Event__c";
		sSqlEventQuery ="SELECT+SVMXC__EndDateTime__c,SVMXC__StartDateTime__c+from+SVMXC__SVMX_Event__c+Where+name+=\'A10512_SVMX_Event1\'";
		String sEventstartdatetimeserver =restServices.restGetSoqlValue(sSqlEventQuery,"SVMXC__StartDateTime__c");
		System.out.println(sEventstartdatetimeserver);
		String sEventenddatetimeserver =restServices.restGetSoqlValue(sSqlEventQuery,"SVMXC__EndDateTime__c");
		System.out.println(sEventenddatetimeserver);
		String converttoipadformatstart = calendarPO.convertedformate(sEventstartdatetimeserver);
		System.out.println(converttoipadformatstart);
		String converttoipadformatend = calendarPO.convertedformate(sEventenddatetimeserver);
		System.out.println(converttoipadformatend);
		
		assertTrue(getsubblock.contains("StartDateTime:"+converttoipadformatstart));
		assertTrue(getsubblock.contains("EndDateTime:"+converttoipadformatend));
		assertTrue(getsubblock.contains("EventStatus:"));
	//////////////////////?**********************************************?///////////////////////////////////////////	
		//verify SFDC event subject block display
		
	String SubblockSFDC=	calendarPO.getelesub("A10512_SFDC_Event5").getText();
	System.out.println(SubblockSFDC);
	
	assertTrue(SubblockSFDC.contains("A10512_SFDC_Event5"));
	assertTrue(SubblockSFDC.contains("Service Duration:"));
	
	sObjectApi = "Event";
	sSqlEventQuery ="SELECT+StartDateTime,EndDateTime+from+Event+Where+Subject+=\'A10512_SFDC_Event5\'";				
	String sEventSFDCstart =restServices.restGetSoqlValue(sSqlEventQuery,"StartDateTime"); 
	System.out.println(sEventSFDCstart);
	String sEventSFDCend =restServices.restGetSoqlValue(sSqlEventQuery,"EndDateTime"); 
	System.out.println(sEventSFDCend);
	
	 converttoipadformatstart = calendarPO.convertedformate(sEventSFDCstart);
	System.out.println(converttoipadformatstart);
	 converttoipadformatend = calendarPO.convertedformate(sEventSFDCend);
	System.out.println(converttoipadformatend);
	
	assertTrue(SubblockSFDC.contains("StartDateTime: "+converttoipadformatstart));
	assertTrue(SubblockSFDC.contains("EndDateTime: "+converttoipadformatend));
	
	 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 			
			
     
	}

	
}
