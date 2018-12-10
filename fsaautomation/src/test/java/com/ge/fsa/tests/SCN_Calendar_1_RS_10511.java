/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests;

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
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

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

	@Test(enabled = true)
	public void RS_10511() throws Exception {
		sSheetName ="RS_10511";
		sDeviceDate = driver.getDeviceTime().split(" ");
	
		String sTestCaseID="RS_10511_Calender_1";
		
		//sahi
  		genericLib.executeSahiScript("appium/SCN_Calendar_1_RS_10511.sah", "sTestCaseID");
  		if(commonsPo.verifySahiExecution()) {
  			
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
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		
		String sWO_SFDC_1 = GenericLib.getExcelData(sTestCaseID,sSheetName, "WO_SFDC_1");
		String sWO_SFDC_2 = GenericLib.getExcelData(sTestCaseID,sSheetName, "WO_SFDC_2");
		String sWO_SFDC_3 = GenericLib.getExcelData(sTestCaseID,sSheetName, "WO_SFDC_3");		
		String sWO_SVMX_1 = GenericLib.getExcelData(sTestCaseID,sSheetName, "WO_SVMX_1");
		String sWO_SVMX_2 = GenericLib.getExcelData(sTestCaseID,sSheetName, "WO_SVMX_2");
		String sWO_SVMX_3 = GenericLib.getExcelData(sTestCaseID,sSheetName, "WO_SVMX_3");
		String sSalesforceuser= GenericLib.getConfigValue(GenericLib.sConfigFile, "salesforce_ID1");
		String sTechname2 = GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_ID1");
	
		//Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
	
			//config sync
			toolsPo.configSync(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			
			//Data Sync for WO's created
			toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//verify WO event is present or not
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonsPo,sWO_SFDC_1);
			calendarPO.VerifyWOInCalender(commonsPo,sWO_SFDC_2);
			calendarPO.VerifyWOInCalender(commonsPo,sWO_SFDC_3);
			calendarPO.VerifyWOInCalender(commonsPo,sWO_SVMX_1);
			calendarPO.VerifyWOInCalender(commonsPo,sWO_SVMX_2);
			calendarPO.VerifyWOInCalender(commonsPo,sWO_SVMX_3);
			ExtentManager.logger.log(Status.PASS,"Six events are displayed in calendar");
			System.out.println("///////////////////////////////////////////////////////////////////////////////////");
			
////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			//delete one SFDC and one SVMX event
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
			
			toolsPo.syncData(commonsPo);
			
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonsPo,sWO_SFDC_1);
			calendarPO.VerifyWOInCalender(commonsPo,sWO_SVMX_1);
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
			
			toolsPo.syncData(commonsPo);
		
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonsPo,sWO_SFDC_2);
			calendarPO.VerifyWOInCalender(commonsPo,sWO_SVMX_2);
			
		
			commonsPo.tap(toolsPo.getEleToolsIcn());
			commonsPo.tap(toolsPo.geteleSignOutBtn());
			commonsPo.tap(toolsPo.getelepopSignOutBtn());
			Thread.sleep(10000);
			System.out.println("Sign out successfully");
			
			//lauchNewApp("false");
			//Login to tech2
			loginHomePo.login_tech2(commonsPo, exploreSearchPo);
			Thread.sleep(3000);
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonsPo,sWO_SFDC_2);
			calendarPO.VerifyWOInCalender(commonsPo,sWO_SVMX_2);
			
			//Signout from Tech 2
			commonsPo.tap(toolsPo.getEleToolsIcn());
			commonsPo.tap(toolsPo.geteleSignOutBtn());
			commonsPo.tap(toolsPo.getelepopSignOutBtn());
			Thread.sleep(3000);
			
			ExtentManager.logger.log(Status.PASS,"Event Re-Assigned to tech2 is successful");
			System.out.println("///////////////////////////////////////////////////////////////////////////////////");
	///////////////////////////////////////////////////////////////////////////////////////////////////		
		
	}
	@AfterClass(enabled = true)
	public void deletedata() throws Exception {
		//Deleting data created
		sSqlEventQuery ="SELECT+id+from+Event+Where+Subject+=\'A10511_SFDC_Event2\'";				
		String sEventIdSFDC_2 =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
		restServices.restDeleterecord("Event",sEventIdSFDC_2); 
		
		sSqlEventQuery ="SELECT+id+from+Event+Where+Subject+=\'A10511_SFDC_Event3\'";				
		String sEventIdSFDC_3 =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
		restServices.restDeleterecord("Event",sEventIdSFDC_3); 
		
		
		sSqlWOQuery ="SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+=\'A10511_SVMX_Event2\'";
		String sEventIdSVMX_2 =restServices.restGetSoqlValue(sSqlWOQuery,"Id"); 
		restServices.restDeleterecord("SVMXC__SVMX_Event__c",sEventIdSVMX_2);
		
		sSqlWOQuery ="SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+=\'A10511_SVMX_Event3\'";				
		String sEventIdSVMX_3 =restServices.restGetSoqlValue(sSqlWOQuery,"Id"); 
		restServices.restDeleterecord("SVMXC__SVMX_Event__c",sEventIdSVMX_3); 
		
					
	}
	

}
