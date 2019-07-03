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
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;

public class Ph_Calendar_2_RS_10512 extends BaseLib {

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
WebElement productname=null;
	String sSheetName =null;
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	//@Test(retryAnalyzer=Retry.class)
	@Test()
	public void Ph_10512() throws Exception {
		sSheetName ="RS_10512";
	
String sTestCaseID="RS_10512_Calender_2";
	
		
		//commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		//commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		//sahi
		/* commonUtility.executeSahiScript("appium/SCN_Calendar_2_RS-10512.sah");
		  
		  lauchNewApp("false");
	*/
		
		 sWO_SVMX_1 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SVMX_1");
		 sWO_SVMX_2 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SVMX_2");
		 sWO_SVMX_3 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SVMX_3");
		 sWO_SVMX_4 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SVMX_4");
  	//Pre Login to app
		 ph_LoginHomePo.login(commonUtility, ph_MorePo);
		//config sync
		//toolsPo.configSync(commonsUtility);
		Thread.sleep(3000);
		//toolsPo.syncData(commonsUtility);
		Thread.sleep(3000);
		

		
	}
	
}
