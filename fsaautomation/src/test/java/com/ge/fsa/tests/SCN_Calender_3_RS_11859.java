/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

import io.appium.java_client.TouchAction;

public class SCN_Calender_3_RS_11859 extends BaseLib {

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
	public void RS_11859() throws Exception {
		sSheetName ="RS_11859";
		sDeviceDate = driver.getDeviceTime().split(" ");
	
		String sTest="RS_11859_Calender_3";
		
	
	
	//read from file
		sExploreSearch = GenericLib.getExcelData(sTest,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTest,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTest,sSheetName, "ProcessName");
		
		String sWO_SVMX_1 = GenericLib.getExcelData(sTest,sSheetName, "WO_SFDC_1");
		String sWO_SVMX_2 = GenericLib.getExcelData(sTest,sSheetName, "WO_SFDC_2");
		
		
		
			//Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
	
			//config sync
			//toolsPo.configSync(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			
			
			//Data Sync for WO's created
		//	toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		/*//verify WO event is present or not
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			calendarPO.VerifyWOInCalender(commonsPo,sWO_SVMX_1);
			calendarPO.VerifyWOInCalender(commonsPo,sWO_SVMX_2);
			
			ExtentManager.logger.log(Status.PASS,"Two SFDC events are displayed in calendar");
			
			System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			//Create SVMX event from Create New Option
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
			commonsPo.tap(calendarPO.geteleNewClick());
			
			commonsPo.tap(calendarPO.getelesubjectSFDCtap(),20,20);
			
			calendarPO.getelesubjectSFDCtextarea().sendKeys("Create SFDC event from new button");
			commonsPo.tap(calendarPO.geteleclickupdate());
			commonsPo.setDateTime24hrs(calendarPO.geteleStartDateTimecal(), 0,"10", "00"); //set start time to Today
			commonsPo.setDateTime24hrs(calendarPO.geteleEndDateTimecal(), 0,"15","00");
			
			commonsPo.tap(workOrderPo.getEleClickSave());
			
			toolsPo.syncData(commonsPo);*/
			
			sObjectApi = "Event";
			restServices.getAccessToken();
			sSqlEventQuery ="SELECT+Id+from+Event+Where+Subject+=\'Create SFDC event from new button\'";				
			String sEventIdSFDC =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
			System.out.println("created event id from server:"+sEventIdSFDC);
			Assert.assertNotNull(sEventIdSFDC, "Record not found");;
			ExtentManager.logger.log(Status.PASS,"Create SVMX event from Create New Option is Successful");
			System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
		//	On server/DC, edit one of the events created
			
			sObjectApi = "Event";
			sSqlEventQuery ="SELECT+id+from+Event+Where+name+=\'A11859_SFDC_Event1\'";				
			String sEventIdSVMX_1 =restServices.restGetSoqlValue(sSqlEventQuery,"Id"); 
			System.out.println(sEventIdSVMX_1);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Calendar now1 = Calendar.getInstance();
	        now1.set(Calendar.HOUR, 10);
	        now1.set(Calendar.MINUTE, 0);
	        now1.set(Calendar.SECOND, 0);
	        String    endtimezero=sdf.format(now1.getTime());
	       // now1.set(Calendar.HOUR_OF_DAY, 14);
	     System.out.println(endtimezero);
			
			String sWOJson="{\"EndDateTime\":\""+endtimezero+"\"}";
			restServices.restUpdaterecord(sObjectApi,sWOJson,sEventIdSVMX_1);
				
				toolsPo.syncData(commonsPo);
			
			String stempDate=calendarPO.convertedformate(endtimezero);
			
			commonsPo.tap(calendarPO.getEleCalendarClick());
			Thread.sleep(3000);
		
			calendarPO.geteleWOendpoint("09:00").getLocation();
			Thread.sleep(3000);
			System.out.println("Before Pencil icon enable");
			commonsPo.Enablepencilicon(calendarPO.getelegetsubject(sWO_SVMX_1));
			Thread.sleep(3000);
			//tap on pencil icon
			System.out.println("tap on pencil icon");
			commonsPo.tap(calendarPO.getelepenciliconcal(sWO_SVMX_2),20,20);
			
		String EndDateTimecal=calendarPO.geteleEndDateTime().getAttribute("value");
		System.out.println(EndDateTimecal);
		
		Assert.assertEquals(stempDate,EndDateTimecal, "End Date time value mapped is not displayed");
		commonsPo.tap(workOrderPo.getEleCancelLnk());
		ExtentManager.logger.log(Status.PASS,"On server/DC, edit one of the events and validated in client is successful");
	System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");	
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//	On client, edit one of the events created 
		commonsPo.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(3000);
		calendarPO.geteleWOendpoint("09:00").getLocation();
		Thread.sleep(3000);
		commonsPo.Enablepencilicon(calendarPO.getelegetsubject(sWO_SVMX_1));
		Thread.sleep(3000);
	
		System.out.println("tap on pencil icon");
		commonsPo.tap(calendarPO.getelepenciliconcal(sWO_SVMX_1),20,20);
		
		commonsPo.tap(calendarPO.geteleEndDateTime());
		commonsPo.setDateTime24hrs(calendarPO.geteleEndDateTime(), 0,"15","00");
	
		String EndDateTimevalidate=calendarPO.geteleEndDateTime().getAttribute("value");
		System.out.println(EndDateTimevalidate);
        
		Thread.sleep(3000);
        commonsPo.tap(workOrderPo.getEleSaveLnk());
        commonsPo.tap(workOrderPo.getEleYesBtn());
      
       toolsPo.syncData(commonsPo);
        
    
      sObjectApi = "Event";
		sSqlEventQuery ="SELECT+EndDateTime+from+Event+Where+id+=\'"+sEventIdSVMX_1+"\'";				
		String sEventenddatetimeserver =restServices.restGetSoqlValue(sSqlEventQuery,"EndDateTime"); 
		System.out.println(sEventenddatetimeserver);
		
		 stempDate = calendarPO.convertedformate(sEventenddatetimeserver);
        
        Assert.assertEquals(stempDate,EndDateTimevalidate, "End Date time  mismatch");
        ExtentManager.logger.log(Status.PASS," On client, edit one of the event is successful");
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
	///////////////////////////////////////////////////////////////////////////////////////////////////		
		//On client, create an SFDC event longer than 14 days.
       
    
    commonsPo.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(3000);
		commonsPo.tap(calendarPO.geteleNewClick());
		commonsPo.tap(calendarPO.getelesubjectSFDCtap(),20,20);
		
		calendarPO.getelesubjectSFDCtextarea().sendKeys("Event for more than 14 days");
		commonsPo.tap(calendarPO.geteleclickupdate());
		commonsPo.setDateTime24hrs(calendarPO.geteleStartDateTimecal(), 0,"10", "00"); //set start time to Today
		commonsPo.setDateTime24hrs(calendarPO.geteleEndDateTimecal(), 18,"10","00");
		
		commonsPo.tap(workOrderPo.getEleClickSave());
		
		toolsPo.syncData(commonsPo);
		commonsPo.tap(toolsPo.getEleToolsIcn());
		commonsPo.tap(toolsPo.geteleResolve());
		String errormsg=toolsPo.getelesyncconflicterror().getText();
		System.out.println(errormsg);
		Assert.assertEquals(errormsg,"An event can't last longer than 14 days.: Duration");
		ExtentManager.logger.log(Status.PASS," On client, create an SVMX event longer than 14 days is successful");
		
		commonsPo.tap(toolsPo.geteleResolveissue());
		commonsPo.tap(toolsPo.geteleApply());
		commonsPo.tap(toolsPo.getEleOkBtn());	
		toolsPo.syncData(commonsPo);
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
	}
	

}
