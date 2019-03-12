/*
*@author MeghanaRao
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/AUT-62"
 */
package com.ge.fsa.tests;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

import com.aventstack.extentreports.Status;
import com.ge.fsa.iphone.pageobjects.Ip_LoginHomePO;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

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

public class iPhonePoc extends BaseLib
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


	@Test()

public void iphone() throws Exception
{	
		
		ip_LoginHomePo.login(commonsPo, ip_MorePo);
		
		//click on new icon
		ip_CalendarPo.getEleCreateNew().click();
		Thread.sleep(5000);
		ip_CalendarPo.getEleselectprocess().click();
		Thread.sleep(2000);
		ip_CalendarPo.getEleAccountLookUp().click();
		Thread.sleep(2000);
		ip_CalendarPo.getElelookupsearch().click();
		ip_CalendarPo.getElelookupsearch().sendKeys("05032019204015AccA");
		ip_CalendarPo.getEleSearchListItem().click();
		Thread.sleep(5000);
		
		
		/*
		 * ip_CalendarPo.getEleCalendarplus().click(); Thread.sleep(2000);
		 * ip_CalendarPo.getEleCalendarEventSubject().click();
		 * ip_CalendarPo.getEleCalendarEventSubject().sendKeys("ok");
		 * ip_CalendarPo.getEleCalendarEventAllDayEvent().click();
		 */
//		ip_LoginHomePo.getEleSignInBtn().click();
//		Thread.sleep(4000);
//		ip_LoginHomePo.getEleSettingsbtn().click();
//		ip_LoginHomePo.getEleProductionBtn().click();
//		ip_LoginHomePo.getEleSandbocURlbtn().click();
//		ip_LoginHomePo.getEleChangeEnvironmentBackbtn().click();
//		ip_LoginHomePo.getEleSignInSettingsBackbtn().click();
//		ip_LoginHomePo.getEleSignInBtn().click();
//		
//		ip_LoginHomePo.getEleUserNameTxtFld().click();
//		ip_LoginHomePo.getEleUserNameTxtFld().sendKeys("auto-tech@svmx.com");
//		Thread.sleep(4000);
//
//		ip_LoginHomePo.getElePasswordTxtFld().click();
//		ip_LoginHomePo.getElePasswordTxtFld().sendKeys("Svmx1234");
		
		

}
	
}