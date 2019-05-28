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

public class Ph_Calendar_1_RS_10511 extends BaseLib {

int iWhileCnt = 0;
	
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectAccID = null;
	String sSqlAccQuery=null;
	
	String sObjectApi = null;
	String sJsonData = null;
	String sObjectAWOID=null; 
	String sFieldServiceName = null;
	String sSoqlwoid1=null;
	String sSqlQuery = null;
	String sSheetName =null;
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	//@Test(retryAnalyzer=Retry.class)
	@Test()
	public void Ph_10511() throws Exception {
	
	commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		sSheetName ="RS_10511";
		String sTestCaseID="RS_10511_Calender_1";
		//sahi
 		genericLib.executeSahiScript("appium/SCN_Calendar_1_RS_10511.sah");
  		if(commonUtility.verifySahiExecution()) {
  			System.out.println("PASSED");
  		}else{System.out.println("FAILED");
  		ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
  			assertEquals(0, 1);}
  		lauchNewApp("false"); 
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
		
		
		String sSalesforceuser= GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "SALESFORCE_ID");
		String sTechname2 = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "TECH_ID_1");
	
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
			//ph_MorePo.configSync(commonUtility, ph_CalendarPo);
			
			//ph_MorePo.syncData(commonUtility);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//verify WO event is present or not
			ph_CalendarPo.getEleCalendarBtn().click();			
			ph_CalendarPo.custScroll(commonUtility,"A10511_SFDC_Event1");
			ph_CalendarPo.VerifyEventInCalender(commonUtility,"A10511_SFDC_Event1");
			ph_CalendarPo.VerifyEventInCalender(commonUtility,"A10511_SFDC_Event2");
			ph_CalendarPo.VerifyEventInCalender(commonUtility,"A10511_SFDC_Event3");
			ph_CalendarPo.custScroll(commonUtility,"A10511_SVMX_Event1");
			ph_CalendarPo.VerifyEventInCalender(commonUtility,"A10511_SVMX_Event1");
			ph_CalendarPo.custScroll(commonUtility,"A10511_SVMX_Event2");
			ph_CalendarPo.VerifyEventInCalender(commonUtility,"A10511_SVMX_Event2");
			ph_CalendarPo.custScroll(commonUtility,"A10511_SVMX_Event3");
			ph_CalendarPo.VerifyEventInCalender(commonUtility,"A10511_SVMX_Event3");
			ExtentManager.logger.log(Status.PASS,"Six events are displayed in calendar");
			System.out.println("///////////////////////////////////////////////////////////////////////////////////");
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//delete one SFDC and one SVMX event
			System.out.println("delete one SFDC and one SVMX event");
			sObjectApi = "Event?";
			String sSqlEventQuery = "SELECT+id+from+Event+Where+Subject+=\'A10511_SFDC_Event1\'";				
			String sEventIdSFDC_1 =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
			System.out.println(sEventIdSFDC_1);
			
			sObjectApi = "SVMXC__SVMX_Event__c?";
			String sSqlWOQuery = "SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+=\'A10511_SVMX_Event1\'";				
			String sEventIdSVMX_1 =restServices.restGetSoqlValue(sSqlWOQuery,"Id"); 
			System.out.println(sEventIdSVMX_1);
			
			sObjectApi = "Event";
			restServices.restDeleterecord(sObjectApi,sEventIdSFDC_1);
			sObjectApi = "SVMXC__SVMX_Event__c";
			restServices.restDeleterecord(sObjectApi,sEventIdSVMX_1);
			
			ph_MorePo.syncData(commonUtility);
			
			ph_CalendarPo.getEleCalendarBtn().click();
			ph_CalendarPo.custScroll(commonUtility,"A10511_SFDC_Event1");
			ph_CalendarPo.VerifyEventdeletion(commonUtility,"A10511_SFDC_Event1");
			ph_CalendarPo.VerifyEventdeletion(commonUtility,"A10511_SVMX_Event1");
			ph_CalendarPo.VerifyEventInCalender(commonUtility,"A10511_SFDC_Event2");
			ph_CalendarPo.VerifyEventInCalender(commonUtility,"A10511_SFDC_Event3");
			ph_CalendarPo.VerifyEventInCalender(commonUtility,"A10511_SVMX_Event2");
			ph_CalendarPo.VerifyEventInCalender(commonUtility,"A10511_SVMX_Event3");
			ExtentManager.logger.log(Status.PASS,"Event deletion is successful");
			System.out.println("///////////////////////////////////////////////////////////////////////////////////");
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
			
			ph_MorePo.syncData(commonUtility);
			
			ph_CalendarPo.getEleCalendarBtn().click();
			ph_CalendarPo.custScroll(commonUtility,"A10511_SFDC_Event2");
			ph_CalendarPo.VerifyEventdeletion(commonUtility,"A10511_SFDC_Event2");
			ph_CalendarPo.VerifyEventdeletion(commonUtility,"A10511_SVMX_Event2");
			
			ph_MorePo.getEleMoreBtn().click();
			ph_MorePo.getEleSignOut().click();
			Thread.sleep(3000);
			ph_MorePo.getEleSignOut().click();
			
			commonUtility.waitforElement(ph_LoginHomePo.getEleSignInBtn(),200);
			System.out.println("Sign out successfully");
			
			lauchNewApp("false"); 
			ph_LoginHomePo.login(commonUtility, ph_MorePo,"TECH_USN_1");
			
			ph_CalendarPo.getEleCalendarBtn().click();
			ph_CalendarPo.custScroll(commonUtility,"A10511_SFDC_Event2");
			ph_CalendarPo.VerifyEventInCalender(commonUtility,"A10511_SFDC_Event2");
			ph_CalendarPo.VerifyEventInCalender(commonUtility,"A10511_SVMX_Event2");
			
			ph_MorePo.getEleMoreBtn().click();
			ph_MorePo.getEleSignOut().click();
			Thread.sleep(3000);
			ph_MorePo.getEleSignOut().click();
			
			commonUtility.waitforElement(ph_LoginHomePo.getEleSignInBtn(),200);
			System.out.println("Sign out successfully from tech2");
			
			ExtentManager.logger.log(Status.PASS,"Event Re-Assigned to tech2 is successful");
			System.out.println("///////////////////////////////////////////////////////////////////////////////////");
	
		
	}
	
}
