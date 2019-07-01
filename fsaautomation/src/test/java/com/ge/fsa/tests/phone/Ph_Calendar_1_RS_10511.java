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
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_Calendar_1_RS_10511 extends BaseLib {


	String sObjectAccID = null;
	String sSqlAccQuery=null;
	String sObjectApi = null;
	String sJsonData = null;
	String sSheetName =null;
	String SFDC_Event1="A10511_SFDC_Event1";
	String SFDC_Event2="A10511_SFDC_Event2";
	String SFDC_Event3="A10511_SFDC_Event3";
	String SVMX_Event1="A10511_SVMX_Event1";
	String SVMX_Event2="A10511_SVMX_Event2";
	String SVMX_Event3="A10511_SVMX_Event3";
	

	@Test(retryAnalyzer=Retry.class)
	public void Ph_10511() throws Exception {

	commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		sSheetName ="RS_10511";
		String sTestCaseID="RS_10511_Calender_1";
		//sahi
 	
  		 	commonUtility.executeSahiScript("appium/SCN_Calendar_1_RS_10511.sah", sTestCaseID);
			Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
			ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID +  "Sahi verification is successful");

  		
		String sWO_SFDC_2 = CommonUtility.readExcelData(GenericLib.sTestDataFile,sSheetName, "WO_SFDC_2");
		String sWO_SVMX_2 = CommonUtility.readExcelData(GenericLib.sTestDataFile,sSheetName, "WO_SVMX_2");
		
		
		String sSalesforceuser= CommonUtility.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "SALESFORCE_ID");
		String sTechname2 = CommonUtility.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "TECH_ID_1");
	
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
			
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//verify WO event is present or not
		ph_CalendarPo.getEleCalendarBtn().click();			
			ph_CalendarPo.custScroll(commonUtility,SFDC_Event1);
			ph_CalendarPo.VerifyEventInCalender(commonUtility,SFDC_Event1);
			ph_CalendarPo.VerifyEventInCalender(commonUtility,SFDC_Event2);
			ph_CalendarPo.VerifyEventInCalender(commonUtility,SFDC_Event3);
			ph_CalendarPo.custScroll(commonUtility,SVMX_Event1);
			ph_CalendarPo.VerifyEventInCalender(commonUtility,SVMX_Event1);
			ph_CalendarPo.custScroll(commonUtility,SVMX_Event2);
			ph_CalendarPo.VerifyEventInCalender(commonUtility,SVMX_Event2);
			ph_CalendarPo.custScroll(commonUtility,SVMX_Event3);
			ph_CalendarPo.VerifyEventInCalender(commonUtility,SVMX_Event3);
			ExtentManager.logger.log(Status.PASS,"Six events are displayed in calendar");
			System.out.println("///////////////////////////////////////////////////////////////////////////////////");
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//delete one SFDC and one SVMX event
			System.out.println("delete one SFDC and one SVMX event");
			sObjectApi = "Event?";
			String sSqlEventQuery = "SELECT+id+from+Event+Where+Subject+=\'"+SFDC_Event1+"\'";				
			String sEventIdSFDC_1 =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
			System.out.println(sEventIdSFDC_1);
			
			sObjectApi = "SVMXC__SVMX_Event__c?";
			String sSqlWOQuery = "SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+=\'"+SVMX_Event1+"\'";				
			String sEventIdSVMX_1 =restServices.restGetSoqlValue(sSqlWOQuery,"Id"); 
			System.out.println(sEventIdSVMX_1);
			
			sObjectApi = "Event";
			restServices.restDeleterecord(sObjectApi,sEventIdSFDC_1);
			sObjectApi = "SVMXC__SVMX_Event__c";
			restServices.restDeleterecord(sObjectApi,sEventIdSVMX_1);
			
			ph_MorePo.syncData(commonUtility);
			
			ph_CalendarPo.getEleCalendarBtn().click();
			ph_CalendarPo.custScroll(commonUtility,SFDC_Event1);
			ph_CalendarPo.VerifyEventdeletion(commonUtility,SFDC_Event1);
			ph_CalendarPo.VerifyEventdeletion(commonUtility,SVMX_Event1);
			ph_CalendarPo.VerifyEventInCalender(commonUtility,SFDC_Event2);
			ph_CalendarPo.VerifyEventInCalender(commonUtility,SFDC_Event3);
			ph_CalendarPo.VerifyEventInCalender(commonUtility,SVMX_Event2);
			ph_CalendarPo.VerifyEventInCalender(commonUtility,SVMX_Event3);
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
			sSqlEventQuery ="SELECT+id+from+Event+Where+Subject+=\'"+SFDC_Event2+"\'";				
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
			sSqlEventQuery ="SELECT+id+from+SVMXC__SVMX_Event__c+Where+name+=\'"+SVMX_Event2+"\'";				
			String sEventIdSVMX_2 =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
			System.out.println(sEventIdSVMX_2);
			//updating event
			 sWOJson="{\"SVMXC__Technician__c\":\""+sTechname2+"\"}";
			restServices.restUpdaterecord(sObjectApi,sWOJson,sEventIdSVMX_2 );
			
			ph_MorePo.syncData(commonUtility);
			
			ph_CalendarPo.getEleCalendarBtn().click();
			ph_CalendarPo.custScroll(commonUtility,SFDC_Event2);
			ph_CalendarPo.VerifyEventdeletion(commonUtility,SFDC_Event2);
			ph_CalendarPo.VerifyEventdeletion(commonUtility,SVMX_Event2);
			
			ph_MorePo.getEleMoreBtn().click();
			ph_MorePo.getEleSignOut().click();
			Thread.sleep(3000);
			ph_MorePo.getEleSignOutpopup().click();
			
			commonUtility.waitforElement(ph_LoginHomePo.getEleSignInBtn(),200);
			System.out.println("Sign out successfully");
			
			lauchNewApp("false"); 
			ph_LoginHomePo.login(commonUtility, ph_MorePo,"TECH_USN_1");
			
			ph_CalendarPo.getEleCalendarBtn().click();
			ph_CalendarPo.custScroll(commonUtility,"SFDC_Event2");
			ph_CalendarPo.VerifyEventInCalender(commonUtility,SFDC_Event2);
			ph_CalendarPo.VerifyEventInCalender(commonUtility,SVMX_Event2);
		
			ph_MorePo.getEleMoreBtn().click();
			ph_MorePo.getEleSignOut().click();
			Thread.sleep(3000);
			ph_MorePo.getEleSignOutpopup().click();
			
			commonUtility.waitforElement(ph_LoginHomePo.getEleSignInBtn(),200);
			System.out.println("Sign out successfully from tech2");
			
			ExtentManager.logger.log(Status.PASS,"Event Re-Assigned to tech2 is successful");
			System.out.println("///////////////////////////////////////////////////////////////////////////////////");
	

	
	}
	
}
