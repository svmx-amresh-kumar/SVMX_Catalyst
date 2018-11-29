/*package com.ge.fsa.tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.testng.annotations.Test;

public class trial {
	@Test(enabled = true)
	public  void main() {
 
		Date today = Calendar.getInstance().getTime();
 
		// Constructs a SimpleDateFormat using the given pattern
		SimpleDateFormat crunchifyFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");
 
		// format() formats a Date into a date/time string.
		String currentTime = crunchifyFormat.format(today);
		log("Current Time = " + currentTime);
 
		try {
 
			// parse() parses text from the beginning of the given string to produce a date.
			Date date = crunchifyFormat.parse(currentTime);
 
			// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object.
			long epochTime = date.getTime();
 
			log("Current Time in Epoch: " + epochTime);
 
		} catch (ParseException e) {
			e.printStackTrace();
		}
 
		// Local ZoneID
		ZoneId defaultZoneId = ZoneId.systemDefault();
		log("defaultZoneId: " + defaultZoneId);
 
		Date date = new Date();
 
		// Default Zone: UTC+0
		Instant instant = date.toInstant();
		System.out.println("instant : " + instant);
		 
		
		
		// Local TimeZone
		LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
		System.out.println("localDateTime : " + localDateTime);
 
	}
 
	// Simple logging
	private static void log(String string) {
		System.out.println(string);
 
	}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		 Calendar now = Calendar.getInstance();
	        now.set(Calendar.HOUR, 0);
	        now.set(Calendar.MINUTE, 0);
	        now.set(Calendar.SECOND, 0);
	        System.out.println(sdf.format(now.getTime()));
	        now.set(Calendar.HOUR_OF_DAY, 0);
	     String  timezero=  sdf.format(now.getTime());
	     System.out.println(timezero);
}
	
}	*/



/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

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

public class trial extends BaseLib {

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
	
		String sTestCaseID="RS_11859_Calender_3";
	
			//Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
	
			//config sync
			toolsPo.configSync(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			System.out.println("First time");
			commonsPo.tap(calendarPO.getEleCalendarClick());
			
			workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00004603", "");
			
			
			 toolsPo.configSync(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			System.out.println("second time");
			commonsPo.tap(calendarPO.getEleCalendarClick());
			workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00004603", "");
			
			
			 toolsPo.configSync(commonsPo);
				Thread.sleep(GenericLib.iMedSleep);
				System.out.println("third time");
				commonsPo.tap(calendarPO.getEleCalendarClick());
				workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00004603", "");
				
				

				 toolsPo.configSync(commonsPo);
					Thread.sleep(GenericLib.iMedSleep);
					System.out.println("forth time");
					commonsPo.tap(calendarPO.getEleCalendarClick());
					workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00004603", "");
					

        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
	}
	

}
