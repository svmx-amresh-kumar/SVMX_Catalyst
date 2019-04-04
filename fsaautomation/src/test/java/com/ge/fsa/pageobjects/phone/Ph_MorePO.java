package com.ge.fsa.pageobjects.phone;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.tablet.ExploreSearchPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;



public class Ph_MorePO
{
	public Ph_MorePO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;
	

	@FindBy(xpath="//*[@*='TAB_BAR.MORE_TAB']")
	private WebElement eleMoreBtn;
	public WebElement getEleMoreBtn()
	{
		return eleMoreBtn;
	}
	
	@FindBy(xpath="//*[@*='SETTINGS.SYNC']")
	private WebElement eleSyncBtn;
	public WebElement getEleSyncBtn()
	{
//		return eleSyncBtn = driver.findElementByAccessibilityId("SETTINGS.SYNC");
//		try {
//			return eleMoreBtn = driver.findElementByAccessibilityId("Sync");
//
//		} catch (Exception e)
//		{eleMoreBtn = driver.findElement(By.xpath("//*[@text='More']"));}
		return eleSyncBtn;

	}
//return 
	@FindBy(xpath="//*[@*='Sync Now']/*")
	private WebElement eleSyncNow;
	public WebElement getEleSyncNow()
	{
		return eleSyncNow;
	}
	
	@FindBy(xpath="//*[@*='Syncing...']")
	private WebElement eleSyncing;
	public WebElement getEleSyncing()
	{
		return eleSyncing;
	}
	
	@FindBy(xpath="//*[@*='APP.TOOLBAR.SYNC_STATUS.BUTTON']")
	private WebElement eleDataSync;
	public WebElement getEleDataSync()
	{
		return eleDataSync;
	}
	
	
	@FindBy(xpath="//*[@*='SETTING.SYNC.CONFIG.ITEM_BUTTON']")
	private WebElement eleRunConfigSync;
	public WebElement getEleRunConfigSync()
	{
		return eleRunConfigSync	;	
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Sync completed']"),
	@FindBy(xpath="(//*[contains(@label,'Sync completed Last sync time')])")})
	private WebElement eleDataSynccompleted;
	public WebElement getEleDataSynccompleted()
	{
		return eleDataSynccompleted;
	}
	
	
		public String getEleConfigSyncSuccessText()
		{
			if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
				return driver.findElementByAccessibilityId("SETTING.SYNC.CONFIG.ITEM_BUTTON").getAttribute("label");

			}else {
				return driver.findElementByAccessibilityId("SETTING.SYNC.CONFIG.ITEM_BUTTON").getAttribute("text");

			}
		}
		
//		@FindBy(xpath="//*[@text='Perform Config Sync']")
//		private WebElement elePerformConfigSync;
//		public WebElement getElePerformConfigSync()
//		{
//		return elePerformConfigSync;
//		}
		
		@FindBy(xpath="//*[@text='Cancel']")
		private WebElement eleCancel;
		public WebElement geteleCancel()
		{
		return eleCancel;
		}
	
	
	@FindBy(xpath="//*[@*='Perform Config Sync']")
	private WebElement elePerformConfigSync;
	public WebElement getElePerformConfigSync()
	{
		return elePerformConfigSync;// = driver.findElementByAccessibilityId("Perform Config Sync");
	}

	public void configSync(CommonUtility commonUtility, Ph_CalendarPO ph_CalendarPo) throws InterruptedException, IOException {
	getEleMoreBtn().click();
	getEleSyncBtn().click();
	System.out.println("Clicked on Sync button");
	getEleRunConfigSync().click();
	System.out.println("Clicked on run config button");
	try {getElePerformConfigSync().click();}catch(Exception e) {}
	//System.out.println("clicking perform config sync now waiting for calandar new");
	commonUtility.waitforElement(ph_CalendarPo.getEleCalendarViewMenu(), 200);
	getEleMoreBtn().click();
	getEleSyncBtn().click();
	System.out.println("Clicked on Sync button and waiting...");
	ExtentManager.logger.pass("After Config Sync", MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
	System.out.println("Sync Status = "+getEleConfigSyncSuccessText());
	if(getEleConfigSyncSuccessText().contains("successful sync")) {
	ExtentManager.logger.log(Status.PASS,"Config Sync Completed sucessfully");
}else {
	ExtentManager.logger.log(Status.FAIL,"Config Sync Failed");
}

}

	
	public void syncData(CommonUtility commonUtility) throws InterruptedException, IOException
	{
		
		
		//Navigation to more screen
		getEleMoreBtn().click();
		getEleDataSync().click();
		Thread.sleep(1000);
		getEleSyncNow().click();
		System.out.println("Clicked on Sync Now and waiting...");
		Thread.sleep(2000);
		commonUtility.waitForElementNotVisible(getEleSyncing(), 300);
		ExtentManager.logger.pass("After Data Sync", MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		assertTrue(commonUtility.isDisplayedCust(getEleDataSynccompleted()), "Sync not done");
		ExtentManager.logger.log(Status.PASS,"Data Sync Completed sucessfully");
		Thread.sleep(3000);
		//commonsUtility.tap(getEleMoreBtn(),20,20);
		commonUtility.longPress(getEleMoreBtn());
		
		
	}
	
	
}
