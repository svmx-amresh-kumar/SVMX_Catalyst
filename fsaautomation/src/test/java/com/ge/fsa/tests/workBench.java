/*
*@author MeghanaRao
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/AUT-62"
 */
package com.ge.fsa.tests;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.KeysRelatedAction;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.browser.Br_LoginHomePO;
import com.ge.fsa.pageobjects.phone.Ph_RecentsItemsPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;

public class workBench extends BaseLib
{
	
	
	int iWhileCnt =0;
	String sTestCaseID="Scenario-1"; String sCaseWOID=null; String sCaseSahiFile=null;
	String sExploreSearch=null;String sWorkOrderID=null; String sWOJsonData=null;String sWOName=null; String sFieldServiceName=null; String sProductName1=null;String sProductName2=null; 
	String sActivityType=null;String sPrintReportSearch="Auto_PrintServiceReport";
	String sAccountName = null;
	String sProductName = null;
	String sFirstName = null;
	String sLastName = null;
	String sContactName = null;
	String sExpenseType = "Airfare";
	String sLineQty = "10.0";
	String slinepriceperunit = "1000";
	
	

//	@FindBy(xpath="//XCUIElementTypeOther[@type='XCUIElementTypeOther']")	
//	private List<WebElement> picPic;
//	public  List<WebElement> getPicPic()
//	{
//		return picPic;
//	}
	
//	@FindBy(id="com.servicemaxinc.svmxfieldserviceapp:id/menu_host")
//	private WebElement eleMenuIcn;
//	public WebElement getEleMenuIcn() 
//	{
//		return eleMenuIcn;
//	}


	//@Test(retryAnalyzer=Retry.class)
	@Test

public void workBenchAnd() throws Exception
{	
		//Thread.sleep(10000);
		String sProductName = "auto_product";
		String  sProcessname = "EditWoAutoTimesstamp";
		String sEventSubject = "susy";
		String sworkOrderName = "WO-00013653";
		
		
		commonUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0, "11", "01");
		commonUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 0, "04" ,"59");
		
		
		/*
		
		System.out.println(commonUtility.getDeviceDate());
		String date=commonUtility.getDeviceDate();
		//String month = calendarPO.convertedformate(date,"E MMM d HH:mm:ss z yyyy","M/d/yyyy");
		//System.out.println(month);
		
		
		SimpleDateFormat parser1 = new SimpleDateFormat("E MMM d HH:mm:ss yyyy");
		 Date  dTempDate1 = parser1.parse("Wed Apr 24 09:12:57 2019");
		 SimpleDateFormat formatter1 = new SimpleDateFormat("M/d/yyyy");
	        String stempDate =  formatter1.format(dTempDate1);
	        System.out.println("Converted to date "+stempDate); 
		
	        Calendar cal=Calendar.getInstance();
	        String[] date1 = commonUtility.getDeviceDate().split(" ");
	        cal.set(Calendar.DATE,Integer.parseInt(date1[2].trim()));
			cal.set(Calendar.YEAR, Integer.parseInt(date1[5].trim()));
	         cal.add(Calendar.DATE, 7);
	         System.out.println(cal.get(Calendar.DATE));*/
////		date = date + " " + commonUtility.getYearPicker().getText();
////		Date currentDate = commonUtility.convertStringToDate("E, MMM dd yyyy", date);
//		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(currentDate);
//		cal.add(Calendar.DAY_OF_MONTH, 7);
//		Date newDate = cal.getTime();
//		System.out.println("Date = ");
	//	String selectDate = converDateToString("dd MMMM yyyy",newDate);
		
//	
}


	
}