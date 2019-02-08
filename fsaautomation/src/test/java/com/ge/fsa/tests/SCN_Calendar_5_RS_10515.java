/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests;

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
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.CalendarPO;

public class SCN_Calendar_5_RS_10515 extends BaseLib {

	int iWhileCnt = 0;

	String stechname = null;
	String sSalesforceuser = null;
	String sSqlEventQuery = null;
	String sSqlWOQuery = null;
	String sObjectProID = null;
	String sObjectApi = null;
	String sJsonData = null;

	String sAccountName = null;
	String sFieldServiceName = null;
//String sproductname = "Proforma30082018102823product";
	String sproductname = null;
	String sSqlQuery = null;
	String sWO_SVMX_1 = null;
	String sWO_SVMX_2 = null;
	String sWO_SVMX_3 = null;
	String sWO_SVMX_4 = null;
	String sWO_SVMX_5 = null;
	String sWO_SVMX_6 = null;
	String sIBLastModifiedBy = null;
	String sSheetName = null;

	@BeforeMethod
	public void initializeObject() throws IOException {

	}

	@Test()
	public void RS_10512() throws Exception {
		sSheetName = "RS_10515";

		String sTestCaseID = "RS_10515_Calender_5";

		 commonsPo.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		 commonsPo.deleteCalendarEvents(restServices,calendarPO,"Event");

		// sahi

		
		  genericLib.executeSahiScript("appium/SCN_Calender_5_RS-10515.sah");
		  if(commonsPo.verifySahiExecution()) {
		  
		  System.out.println("PASSED"); } else { System.out.println("FAILED");
		  
		  
		  ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID +
		  "Sahi verification failure"); assertEquals(0, 1); } lauchNewApp("false");
		  System.out.println("RS_10515");
		  
		 

		sWO_SVMX_1 = GenericLib.getExcelData(sTestCaseID, sSheetName, "WO_SVMX_1");
		sWO_SVMX_2 = GenericLib.getExcelData(sTestCaseID, sSheetName, "WO_SVMX_2");
		sWO_SVMX_3 = GenericLib.getExcelData(sTestCaseID, sSheetName, "WO_SVMX_3");
		sWO_SVMX_4 = GenericLib.getExcelData(sTestCaseID, sSheetName, "WO_SVMX_4");
		sWO_SVMX_5 = GenericLib.getExcelData(sTestCaseID, sSheetName, "WO_SVMX_5");
		sWO_SVMX_6 = GenericLib.getExcelData(sTestCaseID, sSheetName, "WO_SVMX_6");
		// Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);

		// config sync
		/*
		 * //toolsPo.configSync(commonsPo); Thread.sleep(3000);
		 * toolsPo.syncData(commonsPo); Thread.sleep(3000);
		 */
		commonsPo.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(3000);

		// verify the Event is displayed or not

		calendarPO.VerifyWOInCalenderafterconfchange(commonsPo, sWO_SVMX_1);
		calendarPO.VerifyWOInCalenderafterconfchange(commonsPo, sWO_SVMX_2);
		calendarPO.VerifyWOInCalenderafterconfchange(commonsPo, sWO_SVMX_3);
		calendarPO.VerifyWOInCalenderafterconfchange(commonsPo, sWO_SVMX_4);
		calendarPO.VerifyWOInCalenderafterconfchange(commonsPo, sWO_SVMX_5);
		calendarPO.VerifyWOInCalenderafterconfchange(commonsPo, sWO_SVMX_6);

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		commonsPo.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(5000);
		
		// WO1
		String gettitle = calendarPO.getelegetWOtitle(sWO_SVMX_1).getText();
		System.out.println(gettitle);
		String getAccount = calendarPO.getEleworkordernumonCalendarWeek(sWO_SVMX_1).getText();
		System.out.println(getAccount);
		String getlocation = calendarPO.getelegetWOlocation(sWO_SVMX_1).getText();
		System.out.println(getlocation);

		assertEquals(gettitle, sWO_SVMX_1);
		assertEquals(getAccount, "A10515_SVMX_Event");
		assertEquals(getlocation, "Bangalore, Karntaka");

		// WO2
		gettitle = calendarPO.getelegetWOtitle(sWO_SVMX_2).getText();
		assertEquals(gettitle, sWO_SVMX_2);

		try {
			getAccount = calendarPO.getEleworkordernumonCalendarWeek(sWO_SVMX_2).getText();
			System.out.println(getAccount);
		} catch (Exception e) {
			System.out.println("Account is not displayed for WO2");
		}

		try {
			getlocation = calendarPO.getelegetWOlocationontop(sWO_SVMX_2).getText();
			System.out.println(getlocation);
		} catch (Exception e) {
			System.out.println("Location is not displayed for WO2");
		}

		// WO3
		gettitle = calendarPO.getelegetWOtitle(sWO_SVMX_3).getText();
		assertEquals(gettitle, sWO_SVMX_3);

		try {
			getAccount = calendarPO.getEleworkordernumonCalendarWeek(sWO_SVMX_3).getText();
			System.out.println(getAccount);
		} catch (Exception e) {
			System.out.println("Account is not displayed for WO3");
		}

		getlocation = calendarPO.getelegetWOlocationontop(sWO_SVMX_3).getText();
		System.out.println(getlocation);
		assertEquals(getlocation, "KGF, Uttar Pradesh");

		try {
			String getsub = calendarPO.getelegetsubjectcal(sWO_SVMX_3).getText();
			System.out.println(getsub);
			assertEquals(getsub, "A10515_SVMX_Event3");
		} catch (Exception e) {
			System.out.println("Subject is not displayed for WO3");
		}

		// WO4
		gettitle = calendarPO.getelegetWOtitle(sWO_SVMX_4).getText();
		assertEquals(gettitle, sWO_SVMX_4);

		try {
			getAccount = calendarPO.getEleworkordernumonCalendarWeek(sWO_SVMX_4).getText();
			System.out.println(getAccount);
		} catch (Exception e) {
			System.out.println("Account is not displayed for WO4");
		}

		try {
			getlocation = calendarPO.getelegetWOlocationontop(sWO_SVMX_4).getText();
			System.out.println(getlocation);
			assertEquals(getlocation, "Kolar");
		} catch (Exception e) {
			System.out.println("Location is not displayed for WO4");
		}
		try {
			String getsub = calendarPO.getelegetsubjectcal(sWO_SVMX_4).getText();
			System.out.println(getsub);
			assertEquals(getsub, "A10515_SVMX_Event4");
		} catch (Exception e) {
			System.out.println("Subject is not displayed for WO4");
		}

		// WO5
		gettitle = calendarPO.getelegetWOtitle(sWO_SVMX_5).getText();
		assertEquals(gettitle, sWO_SVMX_5);

		try {
			getAccount = calendarPO.getEleworkordernumonCalendarWeek(sWO_SVMX_5).getText();
			System.out.println(getAccount);
		} catch (Exception e) {
			System.out.println("Account is not displayed for WO5");
		}

		try {
			getlocation = calendarPO.getelegetWOlocationontop(sWO_SVMX_5).getText();
			System.out.println(getlocation);
			assertEquals(getlocation, "Delhi");
		} catch (Exception e) {
			System.out.println("Location is not displayedfor WO5");
		}

		try {
			String getsub = calendarPO.getelegetsubjectcal(sWO_SVMX_5).getText();
			System.out.println(getsub);
			assertEquals(getsub, "A10515_SVMX_Event5");
		} catch (Exception e) {
			System.out.println("Subject is not displayed for WO5");
		}
		// WO6
		gettitle = calendarPO.getelegetWOtitle(sWO_SVMX_6).getText();
		assertEquals(gettitle, sWO_SVMX_6);

		try {
			getAccount = calendarPO.getEleworkordernumonCalendarWeek(sWO_SVMX_6).getText();
			System.out.println(getAccount);
		} catch (Exception e) {
			System.out.println("Account is not displayed for WO6");
		}

		try {
			getlocation = calendarPO.getelegetWOlocationontop(sWO_SVMX_6).getText();
			System.out.println(getlocation);
			assertEquals(getlocation, "Kolar");
		} catch (Exception e) {
			System.out.println("Location is not displayed for WO6");
		}

		try {
			String getsub = calendarPO.getelegetsubjectcal(sWO_SVMX_6).getText();
			System.out.println(getsub);
			assertEquals(getsub, "A10515_SVMX_Event6");
		} catch (Exception e) {
			System.out.println("Subject is not displayed for WO6");
		}
	}

}
