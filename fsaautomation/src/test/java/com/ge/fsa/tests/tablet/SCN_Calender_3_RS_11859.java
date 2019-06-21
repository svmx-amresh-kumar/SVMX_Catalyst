/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests.tablet;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.TouchAction;

public class SCN_Calender_3_RS_11859 extends BaseLib {

	
	
	
	String sSqlEventQuery = null;
	String sObjectApi = null;
	String sEventIdSVMX_1 = null;
	String sEventIdSFDC = null;
	String sSheetName =null;
	


	//@Test(retryAnalyzer=Retry.class)
	@Test()
	public void RS_11859() throws Exception {
		sSheetName ="RS_11859";
		
		String sTestCaseID="RS_11859_Calender_3";
		
	commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		
		//sahi
		
		  genericLib.executeSahiScript("appium/SCN_Calender_3_RS-11859.sah");
		  if(commonUtility.verifySahiExecution()) {
		  
		  System.out.println("PASSED"); } else { System.out.println("FAILED");
		  
		  
		  ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID +
		  "Sahi verification failure"); assertEquals(0, 1); } lauchNewApp("false");
		  System.out.println("RS-11859");
		 
		 
	
	//read from file		
		String sWO_SFDC_1 = GenericLib.readExcelData(GenericLib.sTestDataFile,sSheetName, "WO_SFDC_1");
		String sWO_SFDC_2 = GenericLib.readExcelData(GenericLib.sTestDataFile,sSheetName, "WO_SFDC_2");
		
		
		
		  //Pre Login to app 
		loginHomePo.login(commonUtility, exploreSearchPo);
		  
	
		  
		  /////////////////////////////////////////////////////////////////////////////
		  //////////////////////////////////////////////// //verify WO event is presentor not 
		  commonUtility.tap(calendarPO.getEleCalendarClick()); Thread.sleep(3000);
		  commonUtility.tap(calendarPO.getEleCalendarClick()); Thread.sleep(3000);
		  calendarPO.VerifyWOInCalender(commonUtility,sWO_SFDC_1);
		  calendarPO.VerifyWOInCalender(commonUtility,sWO_SFDC_2);
		  
		  ExtentManager.logger.log(Status.
		  PASS,"Two SFDC events are displayed in calendar");
		  
		  System.out.println(
		  "//////////////////////////////////////////////////////////////////////////////////////////////"
		  );
		  /////////////////////////////////////////////////////////////////////////////
		  /////////////////////////////// //Create SFDC event from Create New Option
		  commonUtility.tap(calendarPO.getEleCalendarClick()); Thread.sleep(3000);
		  commonUtility.tap(calendarPO.geteleNewClick());
		  
		  commonUtility.tap(calendarPO.getelesubjectSFDCtap(),20,20);
		  
		  calendarPO.getelesubjectcal().sendKeys("Create SFDC event from new button");
		  commonUtility.setDateTime24hrs(calendarPO.geteleStartDateTimecal(), 0,"10",
		  "00"); //set start time to Today
		  commonUtility.setDateTime24hrs(calendarPO.geteleEndDateTimecal(), 0,"15","00");
		  
		  commonUtility.tap(workOrderPo.getEleClickSave());
		  
		  toolsPo.syncData(commonUtility);
		  
		  sObjectApi = "Event"; restServices.getAccessToken(); sSqlEventQuery
		  ="SELECT+Id+from+Event+Where+Subject+=\'Create SFDC event from new button\'";
		  sEventIdSFDC =restServices.restGetSoqlValue(sSqlEventQuery,"Id");
		  System.out.println("created event id from server:"+sEventIdSFDC);
		  Assert.assertNotNull(sEventIdSFDC, "Record not found");
		  ExtentManager.logger.log(Status.PASS,"Create SFDC event from Create New Option is Successful");
		  System.out.println( "//////////////////////////////////////////////////////////////////////////////////////////////"
		  );
		   
		  /////////////////////////////////////////////////////////////////////////////
		  /////////////////////////////////////////// // On server/DC, edit one of the events created
		  
		  sObjectApi = "Event"; 
		  sSqlEventQuery ="SELECT+id+from+Event+Where+Subject+=\'A11859_SFDC_Event1\'"; 
		  sEventIdSVMX_1 =restServices.restGetSoqlValue(sSqlEventQuery,"Id");
		  System.out.println(sEventIdSVMX_1);
		  
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		  Calendar now1 = Calendar.getInstance(); now1.set(Calendar.HOUR, 12);
		  now1.set(Calendar.MINUTE, 0); now1.set(Calendar.SECOND, 0); 
		  String endtimezero=sdf.format(now1.getTime()); // now1.set(Calendar.HOUR_OF_DAY,14); 
		  System.out.println(endtimezero);
		  
		  String sWOJson="{\"EndDateTime\":\""+endtimezero+"\"}";
		  restServices.restUpdaterecord(sObjectApi,sWOJson,sEventIdSVMX_1);
		  
		 toolsPo.syncData(commonUtility);
		
	
		  String stempDate=calendarPO.convertedformate(endtimezero);
		   
		  commonUtility.tap(calendarPO.getEleCalendarClick()); Thread.sleep(3000);
		   
		  calendarPO.geteleWOendpoint("09:00").getLocation(); 
		  Thread.sleep(3000);
		  System.out.println("Before Pencil icon enable"); try {
		  commonUtility.Enablepencilicon(calendarPO.getelegetsubject(sWO_SFDC_1)); //tap onpencil icon 
		  System.out.println("tap on pencil icon");
		  commonUtility.tap(calendarPO.getelepenciliconcal(sWO_SFDC_1),20,20); String
		  EndDateTimecal=calendarPO.geteleEndDateTime().getAttribute("value");//dummy 
		  }
		  catch (Exception e) {
			  Thread.sleep(3000);
			  calendarPO.geteleWOendpoint3("09:00").getLocation();  
		  commonUtility.Enablepencilicon(calendarPO.getsubjectformultiday(sWO_SFDC_1));
		  commonUtility.tap(calendarPO.getelepenciliconcalmultiday(sWO_SFDC_1),20,20);
		  Thread.sleep(3000); }
		  
		  
		  String EndDateTimecal=calendarPO.geteleEndDateTime().getAttribute("value");
		  System.out.println(EndDateTimecal);
		  
		  Assert.assertEquals(stempDate,EndDateTimecal,"End Date time value mapped is not displayed");
		  commonUtility.tap(workOrderPo.getEleCancelLink());
		  ExtentManager.logger.log(Status.PASS,"On server/DC, edit one of the events and validated in client is successful"); 
		  System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////" );
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//	On client, edit one of the events created 
		
	
		commonUtility.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(3000);
		calendarPO.geteleWOendpoint("09:00").getLocation();
		Thread.sleep(3000);
		System.out.println("before");
		commonUtility.Enablepencilicon(calendarPO.getsubjectformultiday(sWO_SFDC_2));//getsubjectformultidaysfdc
		System.out.println("tap on pencil icon");
		commonUtility.tap(calendarPO.getelepenciliconcalmultiday(sWO_SFDC_2),20,20);//getelepenciliconcalmultidaysfdc

			Thread.sleep(10000);
		//commonsUtility.tap(calendarPO.geteleEndDateTime());
		commonUtility.setDateTime24hrs(calendarPO.geteleEndDateTime(), -1,"15","00");
	if(BaseLib.sOSName=="ios") {
		commonUtility.switchContext("Native");
		commonUtility.getEleDonePickerWheelBtn().click();
		commonUtility.switchContext("Webview");
	}
		
		String EndDateTimevalidate=calendarPO.geteleEndDateTime().getAttribute("value");
		System.out.println(EndDateTimevalidate);
        
		Thread.sleep(3000);
		commonUtility.tap(workOrderPo.getEleSaveLnk());
	try {commonUtility.tap(workOrderPo.getEleSaveLnk());
	}
	catch (Exception e) {
		commonUtility.tap(workOrderPo.getEleSaveLnk());
	}
	
        System.out.println("#####################");
        commonUtility.tap(workOrderPo.getEleYesBtn());
        Thread.sleep(3000);
    
        
        
        toolsPo.syncData(commonUtility);
        
       
       sObjectApi = "Event"; 
       sSqlEventQuery ="SELECT+id+from+Event+Where+Subject+=\'A11859_SFDC_Event2\'"; 
       String sEventIdSVMX_2 = restServices.restGetSoqlValue(sSqlEventQuery,"Id");
		  System.out.println(sEventIdSVMX_2);
       
      sObjectApi = "Event";
		sSqlEventQuery ="SELECT+EndDateTime+from+Event+Where+id+=\'"+sEventIdSVMX_2+"\'";				
		String sEventenddatetimeserver =restServices.restGetSoqlValue(sSqlEventQuery,"EndDateTime"); 
		System.out.println(sEventenddatetimeserver);
		
		
		  stempDate = calendarPO.convertedformate(sEventenddatetimeserver);
		  
		  Assert.assertEquals(stempDate,EndDateTimevalidate,
		  "End Date time  mismatch"); ExtentManager.logger.log(Status.
		  PASS," On client, edit one of the event is successful"); System.out.println(
		  "//////////////////////////////////////////////////////////////////////////////////////////////"
		  );
		  /////////////////////////////////////////////////////////////////////////////
		  ////////////////////// //On client, create an SFDC event longer than 14 days.
		  
		  
		  commonUtility.tap(calendarPO.getEleCalendarClick()); Thread.sleep(3000);
		  commonUtility.tap(calendarPO.geteleNewClick());
		  //commonsUtility.tap(calendarPO.getelesubjectSFDCtap(),20,20);
		  
		  calendarPO.getelesubjectSFDCtap().sendKeys("SFDC Event for more than 14 days");
		  //commonsUtility.tap(calendarPO.geteleclickupdate());
		  commonUtility.setDateTime24hrs(calendarPO.geteleStartDateTimecal(), 0,"10","00"); //set start time to Today
		  commonUtility.setDateTime24hrs(calendarPO.geteleEndDateTimecal(), 18,"10","00");
		  
		  commonUtility.tap(workOrderPo.getEleClickSave());
		  
		  commonUtility.tap(toolsPo.getEleToolsIcn());
		 // toolsPo.getEleSyncDataNowLnk().click();
		  commonUtility.tap(toolsPo.getEleSyncDataNowLnk());
		  commonUtility.tap(toolsPo.getEleStartSyncBtn());
		  commonUtility.waitforElement(toolsPo.getEleRefreshingViewTxt(), 120);
		  
		  commonUtility.tap(toolsPo.geteleResolve()); String
		  errormsg=toolsPo.getelesyncconflicterror().getText();
		  System.out.println(errormsg); 
		 // Assert.assertEquals(errormsg,"An event can't last longer than 14 days.: Duration");
		  Assert.assertEquals(errormsg,"An event can't last longer than 14 days.");
		  ExtentManager.logger.log(Status.
		  PASS," On client, create an SVMX event longer than 14 days is successful");
		  
		  commonUtility.tap(toolsPo.geteleResolveissue());
		  commonUtility.tap(toolsPo.geteleApply()); commonUtility.tap(toolsPo.getEleOkBtn());
		 
		  toolsPo.syncData(commonUtility);
		  
		  
		  sObjectApi = "Event"; 
		  restServices.getAccessToken(); 
		  sSqlEventQuery="SELECT+Id+from+Event+Where+Subject+=\'SFDC Event for more than 14 days\'";
		  sEventIdSFDC =restServices.restGetSoqlValue(sSqlEventQuery,"Id");
		  System.out.println("created event id from server:"+sEventIdSFDC);
		  Assert.assertNull(sEventIdSFDC, "Record not found");
		  ExtentManager.logger.log(Status.PASS,"verification of SFDC event more then 14days is Successful");
		  
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
	}
	
	
	
}
