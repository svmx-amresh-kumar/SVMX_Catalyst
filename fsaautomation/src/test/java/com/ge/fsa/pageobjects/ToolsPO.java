/*
 *  @author lakshmibs
 */
package com.ge.fsa.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.ge.fsa.lib.GenericLib;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class ToolsPO 
{
	public ToolsPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	
	@FindBy(xpath="//div[text()='Tools']")
	private WebElement eleToolsIcn;
	public WebElement getEleToolsIcn()
	{
		return eleToolsIcn;
	}
	@FindBy(xpath="//*[text() = 'Sync Data Now']")
	private WebElement eleSyncDataNowLnk;
	public WebElement getEleSyncDataNowLnk()
	{
		return eleSyncDataNowLnk;
	}
	@FindBy(xpath="//*[text() = 'Sync Config Now']")
	private WebElement eleSyncConfigNowLnk;
	public WebElement geteleSyncConfigNowLnk()
	{
		return eleSyncConfigNowLnk;
	}	
	
	@FindBy(xpath="//*[text()='Start Sync']")
	private WebElement eleStartSyncBtn;
	public WebElement getEleStartSyncBtn()
	{
		return eleStartSyncBtn;
	}
	@FindBy(xpath="//*[text()='Success']")
	private WebElement eleSuccessTxt;
	public WebElement getEleSuccessTxt()
	{
		return eleSuccessTxt;
	}
	
	@FindBy(xpath="//*[text()='Refreshing view']")
	private WebElement eleRefreshingViewTxt;
	public WebElement getEleRefreshingViewTxt()
	{
		return eleRefreshingViewTxt;
	}
	
	@FindBy(xpath="//*[text()='OK']")
	private WebElement eleOkBtn;
	public WebElement getEleOkBtn()
	{
		return eleOkBtn;
	}
	
	@FindBy(xpath = "//*[text()='Config Sync in Progress']")
	private WebElement eleConfigSyncinProgressTxt;
	public WebElement geteleConfigSyncinProgressTxt()
	{
		return eleConfigSyncinProgressTxt;
	}
	
	@FindBy(xpath="//span[text()='Config Sync']")
	private WebElement eleConfigSyncBarMenuLnk;
	public WebElement geteleConfigSyncBarMenuLnk()
	{
		return eleConfigSyncBarMenuLnk;
	}
	
	//@FindBy(xpath="//div")
	//To sync the data
	public void syncData(CommonsPO commonsPo) throws InterruptedException
	{
		GenericLib.lWaitTime=3*60*1000;
		
		//Navigation to Tools screen
		commonsPo.tap(getEleToolsIcn());	
		Assert.assertTrue(getEleSyncDataNowLnk().isDisplayed(), "Tools screen is not displayed");
		NXGReports.addStep("Tools screen is displayed successfully", LogAs.PASSED, null);
		
		getEleSyncDataNowLnk().click();
		commonsPo.tap(getEleSyncDataNowLnk());	
		getEleStartSyncBtn().click();
		commonsPo.longPress(getEleStartSyncBtn());
		commonsPo.waitforElement(getEleRefreshingViewTxt(),  GenericLib.lWaitTime);
		
		//Verification of successful sync
		Assert.assertTrue(getEleSuccessTxt().isDisplayed(), "Data sync is not successfull");
		NXGReports.addStep("Data Sync is successfull", LogAs.PASSED, null);
	}
	
	//Config Sync
		public void configSync(CommonsPO commonsPo) throws InterruptedException
		{
			GenericLib.lWaitTime=3*60*1000;
			
			//Navigation to Tools screen
			commonsPo.tap(getEleToolsIcn());	
			Assert.assertTrue(getEleSyncDataNowLnk().isDisplayed(), "Tools screen is not displayed");
			NXGReports.addStep("Tools screen is displayed successfully", LogAs.PASSED, null);
			
			geteleSyncConfigNowLnk().click();
			commonsPo.tap(geteleSyncConfigNowLnk());	
			getEleOkBtn().click();
			commonsPo.longPress(getEleOkBtn());
			commonsPo.waitforElement(eleSuccessTxt,  GenericLib.lWaitTime);
			//Assert.assertTrue(geteleConfigSyncinProgressTxt().isDisplayed(), "Config sync is in progress");
			System.out.println("begining config sync");
			boolean Syncinprogress =geteleConfigSyncinProgressTxt().isDisplayed();
			System.out.println(Syncinprogress);
																	
			commonsPo.longPress(geteleConfigSyncBarMenuLnk());
			System.out.println("got the bar menu link");
			//commonsPo.waitforElement(getEleRefreshingViewTxt(),  GenericLib.lWaitTime);
			
			//Verification of successful sync
			Assert.assertTrue(getEleSuccessTxt().isDisplayed(), "config sync successfull");
			NXGReports.addStep("Config Sync is successfull", LogAs.PASSED, null);
		}
	
	
}
