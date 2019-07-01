/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests.tablet;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class SCN_Calendar_1_RS_10511 extends BaseLib {

	int iWhileCnt = 0;
	
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sSqlEventQuery = null;
	String sSqlWOQuery=null;
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
	String techname="a240t000000GglLAAS";
	WebElement productname=null;
	String sSheetName =null;
	
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 


	@Test(retryAnalyzer=Retry.class)
	public void RS_10511() throws Exception {
		sSheetName ="RS_10511";
		sDeviceDate = driver.getDeviceTime().split(" ");
	
		String sTestCaseID="RS_10511_Calender_1";
		
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		
		//sahi
  		commonUtility.executeSahiScript("appium/SCN_Calendar_1_RS_10511.sah");
  		if(commonUtility.verifySahiExecution()) {
  			
  			System.out.println("PASSED");
  		}
  		else 
  		{
  			System.out.println("FAILED");
  			

  			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
  			assertEquals(0, 1);
  		}
  		lauchNewApp("true"); 
  		System.out.println("RS_10511");
	
	//read from file
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ProcessName");
		
		String sWO_SFDC_1 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SFDC_1");
		String sWO_SFDC_2 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SFDC_2");
		String sWO_SFDC_3 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SFDC_3");		
		String sWO_SVMX_1 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SVMX_1");
		String sWO_SVMX_2 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SVMX_2");
		String sWO_SVMX_3 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "WO_SVMX_3");
		String sSalesforceuser= CommonUtility.readExcelData(CommonUtility.sConfigPropertiesExcelFile,sSelectConfigPropFile, "SALESFORCE_ID");
		String sTechname2 = CommonUtility.readExcelData(CommonUtility.sConfigPropertiesExcelFile,sSelectConfigPropFile, "TECH_ID_1");
	
		//Pre Login to app
			loginHomePo.login(commonUtility, exploreSearchPo);
	
			//config sync
			toolsPo.configSync(commonUtility);
			Thread.sleep(CommonUtility.iMedSleep);
			
			//Data Sync for WO's created
			toolsPo.syncData(commonUtility);
			Thread.sleep(CommonUtility.iMedSleep);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//verify WO event is present or not
			commonUtility.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			commonUtility.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonUtility,sWO_SFDC_1);
			calendarPO.VerifyWOInCalender(commonUtility,sWO_SFDC_2);
			calendarPO.VerifyWOInCalender(commonUtility,sWO_SFDC_3);
			calendarPO.VerifyWOInCalender(commonUtility,sWO_SVMX_1);
			calendarPO.VerifyWOInCalender(commonUtility,sWO_SVMX_2);
			calendarPO.VerifyWOInCalender(commonUtility,sWO_SVMX_3);
			ExtentManager.logger.log(Status.PASS,"Six events are displayed in calendar");
			System.out.println("///////////////////////////////////////////////////////////////////////////////////");
			
////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			//delete one SFDC and one SVMX event
			System.out.println("delete one SFDC and one SVMX event");
			sObjectApi = "Event?";
			sSqlEventQuery ="SELECT+id+from+Event+Where+Subject+=\'A10511_SFDC_Event1\'";				
			String sEventIdSFDC_1 =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
			System.out.println(sEventIdSFDC_1);
			
			sObjectApi = "SVMXC__SVMX_Event__c?";
			sSqlWOQuery ="SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+=\'A10511_SVMX_Event1\'";				
			String sEventIdSVMX_1 =restServices.restGetSoqlValue(sSqlWOQuery,"Id"); 
			System.out.println(sEventIdSVMX_1);
			
			sObjectApi = "Event";
			restServices.restDeleterecord(sObjectApi,sEventIdSFDC_1);
			sObjectApi = "SVMXC__SVMX_Event__c";
			restServices.restDeleterecord(sObjectApi,sEventIdSVMX_1);
			
			toolsPo.syncData(commonUtility);
			
			commonUtility.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			commonUtility.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonUtility,sWO_SFDC_1);
			calendarPO.VerifyWOInCalender(commonUtility,sWO_SVMX_1);
			ExtentManager.logger.log(Status.PASS,"Event deletion is successful");
			System.out.println("///////////////////////////////////////////////////////////////////////////////////");
////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Assigning from one tech1 to tech2
				//SFDC event
			sObjectApi = "SVMXC__Service_Order__c";
			String sSqlWOID ="SELECT+id+from+SVMXC__Service_Order__c+Where+name+=\'"+sWO_SFDC_2+"\'";					
			String sWOIdSFDC_2 =restServices.restGetSoqlValue(sSqlWOID,"Id"); 
			System.out.println(sWOIdSFDC_2);
			//updating WO
			String sWOJson="{\"SVMXC__Group_Member__c\":\""+sTechname2+"\"}";
			restServices.restUpdaterecord(sObjectApi,sWOJson,sWOIdSFDC_2 );

			sObjectApi = "Event";
			sSqlEventQuery ="SELECT+id+from+Event+Where+Subject+=\'A10511_SFDC_Event2\'";				
			String sEventIdSFDC_2 =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
			System.out.println(sEventIdSFDC_2);
			//updating event
			 sWOJson="{\"OwnerId\":\""+sSalesforceuser+"\"}";
			restServices.restUpdaterecord(sObjectApi,sWOJson,sEventIdSFDC_2 );
			
			
			
			//SVMX event
			sObjectApi = "SVMXC__Service_Order__c";
			 sSqlWOID ="SELECT+id+from+SVMXC__Service_Order__c+Where+name+=\'"+sWO_SVMX_2+"\'";					
			 String sWOIdSVMX_2 = restServices.restGetSoqlValue(sSqlWOID,"Id"); 
			System.out.println(sWOIdSVMX_2);
			//updating WO
			 sWOJson="{\"SVMXC__Group_Member__c\":\""+sTechname2+"\"}";
			restServices.restUpdaterecord(sObjectApi,sWOJson,sWOIdSVMX_2 );

			sObjectApi = "SVMXC__SVMX_Event__c";
			sSqlEventQuery ="SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+=\'A10511_SVMX_Event2\'";				
			String sEventIdSVMX_2 =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
			System.out.println(sEventIdSVMX_2);
			//updating event
			 sWOJson="{\"SVMXC__Technician__c\":\""+sTechname2+"\"}";
			restServices.restUpdaterecord(sObjectApi,sWOJson,sEventIdSVMX_2 );
			
			toolsPo.syncData(commonUtility);
		
			commonUtility.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonUtility,sWO_SFDC_2);
			calendarPO.VerifyWOInCalender(commonUtility,sWO_SVMX_2);
			
		
			commonUtility.tap(toolsPo.getEleToolsIcn());
			commonUtility.tap(toolsPo.geteleSignOutBtn());
			commonUtility.tap(toolsPo.getelepopSignOutBtn());
			Thread.sleep(10000);
			System.out.println("Sign out successfully");
			
			//lauchNewApp("false");
			//Login to tech2
			loginHomePo.login(commonUtility, exploreSearchPo,"TECH_USN_1");
			Thread.sleep(8000);
			commonUtility.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonUtility,sWO_SFDC_2);
			calendarPO.VerifyWOInCalender(commonUtility,sWO_SVMX_2);
			
			//Signout from Tech 2
			commonUtility.tap(toolsPo.getEleToolsIcn());
			commonUtility.tap(toolsPo.geteleSignOutBtn());
			commonUtility.tap(toolsPo.getelepopSignOutBtn());
			Thread.sleep(3000);
			
			ExtentManager.logger.log(Status.PASS,"Event Re-Assigned to tech2 is successful");
			System.out.println("///////////////////////////////////////////////////////////////////////////////////");
	///////////////////////////////////////////////////////////////////////////////////////////////////		
		
	}
	

}
