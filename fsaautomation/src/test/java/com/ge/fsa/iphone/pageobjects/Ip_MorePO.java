package com.ge.fsa.iphone.pageobjects;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.CommonsPO;
import com.ge.fsa.pageobjects.ExploreSearchPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;



public class Ip_MorePO
{
	public Ip_MorePO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;
	

	@FindAll({@FindBy(xpath="//*[@text='More']"),
	@FindBy(id="More, tab, 4 of 4")})
	private WebElement eleMoreBtn;
	public WebElement getEleMoreBtn()
	{
		return eleMoreBtn;
	}
	
	//@FindBy(xpath="//XCUIElementTypeOther[@name='Sync']")
	@FindBy(xpath="//*[@text='More']")
	private WebElement eleSyncBtn;
	public WebElement getEleSyncBtn()
	{
		try {
			return eleMoreBtn = driver.findElementByAccessibilityId("Sync");

		} catch (Exception e)
		{eleMoreBtn = driver.findElement(By.xpath("//*[@text='More']"));}
		return eleMoreBtn;

	}
//return 

	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Sync Now\"])[2]")
	private WebElement eleSyncNow;
	public WebElement getEleSyncNow()
	{
		return eleSyncNow;
	}
	
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Settings\"])[4]/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]")
	private WebElement eleDataSync;
	public WebElement getEleDataSync()
	{
		return eleDataSync;
	}
	
	
/*	@FindAll({@FindBy(xpath="//XCUIElementTypeOther[contains(.,'Run Configuration Sync']"),
		@FindBy(xpath="//XCUIElementTypeOther[contains(@name,'Run Configuration Sync']"),
		@FindBy(xpath="//XCUIElementTypeOther[contains(@label,'Run Configuration Sync']"),
		@FindBy(xpath="//XCUIElementTypeOther[@label='Run Configuration Sync Last successful sync:']"),
		@FindBy(xpath="//XCUIElementTypeOther[@name='Run Configuration Sync Last successful sync:']"),
		@FindBy(id="Run Configuration Sync Last successful sync:")})*/
	private WebElement eleRunConfigSync;
	public WebElement getEleRunConfigSync()
	{
		return eleRunConfigSync= driver.findElementByAccessibilityId("Run Configuration Sync Last successful sync: ");
	}
	
	@FindBy(xpath="//XCUIElementTypeOther[@name=\"Sync completed Last sync time:\"]")
	private WebElement eleDataSynccompleted;
	public WebElement getEleDataSynccompleted()
	{
		return eleDataSynccompleted;
	}
	
	
	//@FindBy(id="Perform Config Sync")
	private WebElement elePerformConfigSync;
	public WebElement getElePerformConfigSync()
	{
		return elePerformConfigSync = driver.findElementByAccessibilityId("Perform Config Sync");
	}

	public void configSync(CommonsPO commonsPo, Ip_CalendarPO ip_CalendarPo) throws InterruptedException {
	getEleMoreBtn().click();
	commonsPo.waitforElement(getEleSyncBtn(),2);
	getEleSyncBtn().click();
	Thread.sleep(2000);
	commonsPo.waitforElement(getEleRunConfigSync(),2);
	getEleRunConfigSync().click();
	//commonsPo.switchContext("NATIVE_APP");
try {
	getElePerformConfigSync().click();

} catch (Exception e) {
	System.out.println(e);}
	//commonsPo.switchContext("NATIVE_APP");
	commonsPo.waitforElement(ip_CalendarPo.getEleCalendarViewMenu(), 200);
	assertTrue(commonsPo.isDisplayedCust(getEleRunConfigSync()), "Sync not done");
	ExtentManager.logger.log(Status.PASS,"Config Sync Completed sucessfully");
}

	
	public void syncData(CommonsPO commonsPo) throws InterruptedException
	{
		
		
		//Navigation to more screen
		getEleMoreBtn().click();
		getEleDataSync().click();
		getEleSyncNow().click();
		commonsPo.waitforElement(getEleDataSynccompleted(),2000);
		assertTrue(commonsPo.isDisplayedCust(getEleDataSynccompleted()), "Sync not done");
		ExtentManager.logger.log(Status.PASS,"Data Sync Completed sucessfully");
		Thread.sleep(5000);
		getEleDataSync().click();
		
		
	}
	
	
}
